# Traceability - requirement -> scenario -> test case -> test data

Each business requirement (BR) lists its scenarios; each scenario its test cases; each test case one row per leg with that leg's own test data file. Scenarios of contexts awaiting SME are listed with no test cases. Reverse indexes at the end.

## Summary

| Measure | Count |
|---|---:|
| business requirements | 3 |
| scenarios | 248 |
| test cases | 1993 |
| blocked without test data | 1 |
| legs | 1540 |
| leg files | 1540 |
| missing files | 0 |
| awaiting sme | 96 |
| orphan scenarios | 14 |
| no request by design | 165 |
| brs without scenarios | 0 |

Gate: every BR has >= 1 scenario; every scenario has test cases or is awaiting SME; every leg has a file that exists. Result: **FAIL**

## BUYPASS ATL105 MasterCard Credit (`BR-12B94100`)

Supporting citations: p.511; p.81; p.81; p.81; p.83; p.83; p.83; p.83; p.83; p.83; p.86; p.86; p.86; p.161; p.161; p.161; p.161; p.161; p.162; p.162

### SC-ATL105-6ADB5621 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000525 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only → approv | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000525.json](test_data/TD-TC-000525.json) |
| TC-000526 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only with Fut | Happy path | Positive | needs_client_input | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000526.json](test_data/TD-TC-000526.json) |
| TC-000527 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Single rule broken | Negative | requires_review | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000527.step1.auth-only.json](test_data/TD-TC-000527.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000527.step2.cancellation.json](test_data/TD-TC-000527.step2.cancellation.json) |
| TC-000528 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Single rule broken | Negative | requires_review | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000528.step1.auth-only.json](test_data/TD-TC-000528.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000528.step2.cancellation.json](test_data/TD-TC-000528.step2.cancellation.json) |
| TC-000529 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Single rule broken | Negative | requires_review | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000529.step1.auth-only.json](test_data/TD-TC-000529.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000529.step2.completion.json](test_data/TD-TC-000529.step2.completion.json) |
| TC-000530 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Single rule broken | Negative | requires_review | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000530.step1.auth-only.json](test_data/TD-TC-000530.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000530.step2.completion.json](test_data/TD-TC-000530.step2.completion.json) |
| TC-000531 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Valid flow | Positive | requires_review | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000531.step1.auth-only.json](test_data/TD-TC-000531.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000531.step2.cancellation.json](test_data/TD-TC-000531.step2.cancellation.json) |
| TC-000532 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Valid flow | Positive | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000532.step1.auth-only.json](test_data/TD-TC-000532.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000532.step2.completion.json](test_data/TD-TC-000532.step2.completion.json) |
| TC-000533 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Valid flow | Positive | requires_review | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000533.step1.auth-only.json](test_data/TD-TC-000533.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000533.step2.completion.json](test_data/TD-TC-000533.step2.completion.json) |
| TC-000534 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Valid flow | Positive | requires_review | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000534.step1.auth-only.json](test_data/TD-TC-000534.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000534.step2.completion.json](test_data/TD-TC-000534.step2.completion.json) |
| TC-000535 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Valid flow | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000535.step1.auth-only.json](test_data/TD-TC-000535.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000535.step2.cancellation.json](test_data/TD-TC-000535.step2.cancellation.json) |
| TC-000536 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Au | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000536.step1.auth-only.json](test_data/TD-TC-000536.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000536.step2.split-tender.json](test_data/TD-TC-000536.step2.split-tender.json) |
| TC-000537 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000537.step1.auth-only.json](test_data/TD-TC-000537.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000537.step2.cancellation.json](test_data/TD-TC-000537.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000537.step3.cancellation.json](test_data/TD-TC-000537.step3.cancellation.json) |
| TC-000538 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000538.step1.auth-only.json](test_data/TD-TC-000538.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000538.step2.cancellation.json](test_data/TD-TC-000538.step2.cancellation.json) |
| TC-000539 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000539.step1.auth-only.json](test_data/TD-TC-000539.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000539.step2.cancellation.json](test_data/TD-TC-000539.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000539.step3.totals.json](test_data/TD-TC-000539.step3.totals.json) |
| TC-000540 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000540.step1.auth-only.json](test_data/TD-TC-000540.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000540.step2.completion.json](test_data/TD-TC-000540.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000540.step3.totals.json](test_data/TD-TC-000540.step3.totals.json) |
| TC-000541 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then To | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000541.step1.auth-only.json](test_data/TD-TC-000541.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000541.step2.totals.json](test_data/TD-TC-000541.step2.totals.json) |
| TC-000542 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Response / oracle check | Negative | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000542.step1.auth-only.json](test_data/TD-TC-000542.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000542.step2.completion.json](test_data/TD-TC-000542.step2.completion.json) |
| TC-000543 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only (card is | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000543.json](test_data/TD-TC-000543.json) |

### SC-ATL105-D5FF2646 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000746 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only → | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000746.json](test_data/TD-TC-000746.json) |
| TC-000747 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000747.json](test_data/TD-TC-000747.json) |

### SC-ATL105-FDF8FDF0 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000797 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only → a | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000797.json](test_data/TD-TC-000797.json) |
| TC-000798 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000798.json](test_data/TD-TC-000798.json) |

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
| TC-000487 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000487.json](test_data/TD-TC-000487.json) |
| TC-000488 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Happy path | Positive | needs_client_input | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000488.json](test_data/TD-TC-000488.json) |
| TC-000489 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000489.step1.auth-only.json](test_data/TD-TC-000489.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000489.step2.cancellation.json](test_data/TD-TC-000489.step2.cancellation.json) |
| TC-000490 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000490.step1.auth-only.json](test_data/TD-TC-000490.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000490.step2.cancellation.json](test_data/TD-TC-000490.step2.cancellation.json) |
| TC-000491 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000491.step1.auth-only.json](test_data/TD-TC-000491.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000491.step2.completion.json](test_data/TD-TC-000491.step2.completion.json) |
| TC-000492 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | needs_client_input | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000492.step1.auth-only.json](test_data/TD-TC-000492.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000492.step2.completion.json](test_data/TD-TC-000492.step2.completion.json) |
| TC-000493 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000493.step1.auth-only.json](test_data/TD-TC-000493.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000493.step2.cancellation.json](test_data/TD-TC-000493.step2.cancellation.json) |
| TC-000494 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000494.step1.auth-only.json](test_data/TD-TC-000494.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000494.step2.completion.json](test_data/TD-TC-000494.step2.completion.json) |
| TC-000495 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000495.step1.auth-only.json](test_data/TD-TC-000495.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000495.step2.completion.json](test_data/TD-TC-000495.step2.completion.json) |
| TC-000496 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000496.step1.auth-only.json](test_data/TD-TC-000496.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000496.step2.completion.json](test_data/TD-TC-000496.step2.completion.json) |
| TC-000497 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000497.step1.auth-only.json](test_data/TD-TC-000497.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000497.step2.cancellation.json](test_data/TD-TC-000497.step2.cancellation.json) |
| TC-000498 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000498.step1.auth-only.json](test_data/TD-TC-000498.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000498.step2.split-tender.json](test_data/TD-TC-000498.step2.split-tender.json) |
| TC-000499 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000499.step1.auth-only.json](test_data/TD-TC-000499.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000499.step2.cancellation.json](test_data/TD-TC-000499.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000499.step3.cancellation.json](test_data/TD-TC-000499.step3.cancellation.json) |
| TC-000500 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000500.step1.auth-only.json](test_data/TD-TC-000500.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000500.step2.cancellation.json](test_data/TD-TC-000500.step2.cancellation.json) |
| TC-000501 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000501.step1.auth-only.json](test_data/TD-TC-000501.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000501.step2.cancellation.json](test_data/TD-TC-000501.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000501.step3.totals.json](test_data/TD-TC-000501.step3.totals.json) |
| TC-000502 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000502.step1.auth-only.json](test_data/TD-TC-000502.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000502.step2.completion.json](test_data/TD-TC-000502.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000502.step3.totals.json](test_data/TD-TC-000502.step3.totals.json) |
| TC-000503 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000503.step1.auth-only.json](test_data/TD-TC-000503.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000503.step2.totals.json](test_data/TD-TC-000503.step2.totals.json) |
| TC-000504 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Response / oracle check | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000504.step1.auth-only.json](test_data/TD-TC-000504.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000504.step2.completion.json](test_data/TD-TC-000504.step2.completion.json) |
| TC-000505 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000505.json](test_data/TD-TC-000505.json) |

### SC-ATL105-FCBF0C27 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A4123EFA - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000742 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Author | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000742.json](test_data/TD-TC-000742.json) |
| TC-000743 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Author | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000743.json](test_data/TD-TC-000743.json) |

### SC-ATL105-DB112FC7 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-95140904 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000793 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authoriz | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000793.json](test_data/TD-TC-000793.json) |
| TC-000794 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authoriz | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000794.json](test_data/TD-TC-000794.json) |
| TC-000966 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authoriz | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-181-2 p.181; p. | 1 |  | [TD-TC-000966.json](test_data/TD-TC-000966.json) |

### SC-ATL105-AB7323EA - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000890 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000890.json](test_data/TD-TC-000890.json) |
| TC-000891 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000891.step1.auth-only.json](test_data/TD-TC-000891.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000891.step2.cancellation.json](test_data/TD-TC-000891.step2.cancellation.json) |
| TC-000892 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000892.step1.auth-only.json](test_data/TD-TC-000892.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000892.step2.completion.json](test_data/TD-TC-000892.step2.completion.json) |
| TC-000893 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000893.step1.auth-only.json](test_data/TD-TC-000893.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000893.step2.completion.json](test_data/TD-TC-000893.step2.completion.json) |
| TC-000894 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000894.step1.auth-only.json](test_data/TD-TC-000894.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000894.step2.completion.json](test_data/TD-TC-000894.step2.completion.json) |
| TC-000895 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000895.step1.auth-only.json](test_data/TD-TC-000895.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000895.step2.cancellation.json](test_data/TD-TC-000895.step2.cancellation.json) |
| TC-000896 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000896.step1.auth-only.json](test_data/TD-TC-000896.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000896.step2.split-tender.json](test_data/TD-TC-000896.step2.split-tender.json) |
| TC-000897 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000897.step1.auth-only.json](test_data/TD-TC-000897.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000897.step2.cancellation.json](test_data/TD-TC-000897.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000897.step3.cancellation.json](test_data/TD-TC-000897.step3.cancellation.json) |
| TC-000898 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000898.json](test_data/TD-TC-000898.json) |

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
| TC-000564 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000564.json](test_data/TD-TC-000564.json) |
| TC-000565 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Happy path | Positive | pending_sme | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000565.json](test_data/TD-TC-000565.json) |
| TC-000566 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000566.step1.auth-only.json](test_data/TD-TC-000566.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000566.step2.cancellation.json](test_data/TD-TC-000566.step2.cancellation.json) |
| TC-000567 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000567.step1.auth-only.json](test_data/TD-TC-000567.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000567.step2.cancellation.json](test_data/TD-TC-000567.step2.cancellation.json) |
| TC-000568 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000568.step1.auth-only.json](test_data/TD-TC-000568.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000568.step2.completion.json](test_data/TD-TC-000568.step2.completion.json) |
| TC-000569 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000569.step1.auth-only.json](test_data/TD-TC-000569.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000569.step2.completion.json](test_data/TD-TC-000569.step2.completion.json) |
| TC-000570 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000570.step1.auth-only.json](test_data/TD-TC-000570.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000570.step2.cancellation.json](test_data/TD-TC-000570.step2.cancellation.json) |
| TC-000571 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000571.step1.auth-only.json](test_data/TD-TC-000571.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000571.step2.completion.json](test_data/TD-TC-000571.step2.completion.json) |
| TC-000572 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000572.step1.auth-only.json](test_data/TD-TC-000572.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000572.step2.completion.json](test_data/TD-TC-000572.step2.completion.json) |
| TC-000573 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000573.step1.auth-only.json](test_data/TD-TC-000573.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000573.step2.completion.json](test_data/TD-TC-000573.step2.completion.json) |
| TC-000574 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000574.step1.auth-only.json](test_data/TD-TC-000574.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000574.step2.cancellation.json](test_data/TD-TC-000574.step2.cancellation.json) |
| TC-000575 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000575.step1.auth-only.json](test_data/TD-TC-000575.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000575.step2.split-tender.json](test_data/TD-TC-000575.step2.split-tender.json) |
| TC-000576 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000576.step1.auth-only.json](test_data/TD-TC-000576.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000576.step2.cancellation.json](test_data/TD-TC-000576.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000576.step3.cancellation.json](test_data/TD-TC-000576.step3.cancellation.json) |
| TC-000577 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Time-based | Positive | pending_sme | BR-85-2 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000577.step1.auth-only.json](test_data/TD-TC-000577.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000577.step2.cancellation.json](test_data/TD-TC-000577.step2.cancellation.json) |
| TC-000578 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Running total / state | Positive | pending_sme | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000578.step1.auth-only.json](test_data/TD-TC-000578.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000578.step2.cancellation.json](test_data/TD-TC-000578.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000578.step3.totals.json](test_data/TD-TC-000578.step3.totals.json) |
| TC-000579 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000579.step1.auth-only.json](test_data/TD-TC-000579.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000579.step2.completion.json](test_data/TD-TC-000579.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000579.step3.totals.json](test_data/TD-TC-000579.step3.totals.json) |
| TC-000580 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000580.step1.auth-only.json](test_data/TD-TC-000580.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000580.step2.totals.json](test_data/TD-TC-000580.step2.totals.json) |
| TC-000581 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Response / oracle check | Negative | pending_sme | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000581.step1.auth-only.json](test_data/TD-TC-000581.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000581.step2.completion.json](test_data/TD-TC-000581.step2.completion.json) |
| TC-000582 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000582.json](test_data/TD-TC-000582.json) |

### SC-ATL105-C18E6DCC - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-DF6AFFE2 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000748 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000748.json](test_data/TD-TC-000748.json) |
| TC-000749 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000749.json](test_data/TD-TC-000749.json) |

### SC-ATL105-EDC8B5AD - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-CAEE4401 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000799 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device A | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000799.json](test_data/TD-TC-000799.json) |
| TC-000800 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device A | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000800.json](test_data/TD-TC-000800.json) |

### SC-ATL105-21D68F83 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine)
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000899 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000899.json](test_data/TD-TC-000899.json) |
| TC-000900 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000900.step1.auth-only.json](test_data/TD-TC-000900.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000900.step2.cancellation.json](test_data/TD-TC-000900.step2.cancellation.json) |
| TC-000901 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000901.step1.auth-only.json](test_data/TD-TC-000901.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000901.step2.completion.json](test_data/TD-TC-000901.step2.completion.json) |
| TC-000902 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000902.step1.auth-only.json](test_data/TD-TC-000902.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000902.step2.completion.json](test_data/TD-TC-000902.step2.completion.json) |
| TC-000903 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000903.step1.auth-only.json](test_data/TD-TC-000903.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000903.step2.completion.json](test_data/TD-TC-000903.step2.completion.json) |
| TC-000904 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000904.step1.auth-only.json](test_data/TD-TC-000904.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000904.step2.cancellation.json](test_data/TD-TC-000904.step2.cancellation.json) |
| TC-000905 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000905.step1.auth-only.json](test_data/TD-TC-000905.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000905.step2.split-tender.json](test_data/TD-TC-000905.step2.split-tender.json) |
| TC-000906 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000906.step1.auth-only.json](test_data/TD-TC-000906.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000906.step2.cancellation.json](test_data/TD-TC-000906.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000906.step3.cancellation.json](test_data/TD-TC-000906.step3.cancellation.json) |
| TC-000907 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000907.json](test_data/TD-TC-000907.json) |

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
| TC-000544 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → a | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000544.json](test_data/TD-TC-000544.json) |
| TC-000545 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → a | Happy path | Positive | needs_client_input | BR-83-1 p.83; BR-ATL105-TRANSARMOR-76-5 p.76; p.511 | 1 | Authorization Only | [TD-TC-000545.json](test_data/TD-TC-000545.json) |
| TC-000546 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only wit | Happy path | Positive | needs_client_input | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000546.json](test_data/TD-TC-000546.json) |
| TC-000547 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000547.step1.auth-only.json](test_data/TD-TC-000547.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000547.step2.cancellation.json](test_data/TD-TC-000547.step2.cancellation.json) |
| TC-000548 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000548.step1.auth-only.json](test_data/TD-TC-000548.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000548.step2.cancellation.json](test_data/TD-TC-000548.step2.cancellation.json) |
| TC-000549 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000549.step1.auth-only.json](test_data/TD-TC-000549.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000549.step2.completion.json](test_data/TD-TC-000549.step2.completion.json) |
| TC-000550 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000550.step1.auth-only.json](test_data/TD-TC-000550.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000550.step2.completion.json](test_data/TD-TC-000550.step2.completion.json) |
| TC-000551 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | requires_review | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000551.step1.auth-only.json](test_data/TD-TC-000551.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000551.step2.cancellation.json](test_data/TD-TC-000551.step2.cancellation.json) |
| TC-000552 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000552.step1.auth-only.json](test_data/TD-TC-000552.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000552.step2.completion.json](test_data/TD-TC-000552.step2.completion.json) |
| TC-000553 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | requires_review | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000553.step1.auth-only.json](test_data/TD-TC-000553.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000553.step2.completion.json](test_data/TD-TC-000553.step2.completion.json) |
| TC-000554 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | requires_review | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000554.step1.auth-only.json](test_data/TD-TC-000554.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000554.step2.completion.json](test_data/TD-TC-000554.step2.completion.json) |
| TC-000555 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000555.step1.auth-only.json](test_data/TD-TC-000555.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000555.step2.cancellation.json](test_data/TD-TC-000555.step2.cancellation.json) |
| TC-000556 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000556.step1.auth-only.json](test_data/TD-TC-000556.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000556.step2.split-tender.json](test_data/TD-TC-000556.step2.split-tender.json) |
| TC-000557 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000557.step1.auth-only.json](test_data/TD-TC-000557.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000557.step2.cancellation.json](test_data/TD-TC-000557.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000557.step3.cancellation.json](test_data/TD-TC-000557.step3.cancellation.json) |
| TC-000558 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Time-based | Positive | requires_review | BR-85-3 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000558.step1.auth-only.json](test_data/TD-TC-000558.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000558.step2.cancellation.json](test_data/TD-TC-000558.step2.cancellation.json) |
| TC-000559 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000559.step1.auth-only.json](test_data/TD-TC-000559.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000559.step2.cancellation.json](test_data/TD-TC-000559.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000559.step3.totals.json](test_data/TD-TC-000559.step3.totals.json) |
| TC-000560 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000560.step1.auth-only.json](test_data/TD-TC-000560.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000560.step2.completion.json](test_data/TD-TC-000560.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000560.step3.totals.json](test_data/TD-TC-000560.step3.totals.json) |
| TC-000561 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000561.step1.auth-only.json](test_data/TD-TC-000561.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000561.step2.totals.json](test_data/TD-TC-000561.step2.totals.json) |
| TC-000562 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Response / oracle check | Negative | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000562.step1.auth-only.json](test_data/TD-TC-000562.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000562.step2.completion.json](test_data/TD-TC-000562.step2.completion.json) |
| TC-000563 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000563.json](test_data/TD-TC-000563.json) |

### SC-ATL105-48F4A035 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000420 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only → app | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000420.json](test_data/TD-TC-000420.json) |
| TC-000421 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only with  | Happy path | Positive | needs_client_input | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000421.json](test_data/TD-TC-000421.json) |
| TC-000422 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000422.step1.auth-only.json](test_data/TD-TC-000422.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000422.step2.cancellation.json](test_data/TD-TC-000422.step2.cancellation.json) |
| TC-000423 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000423.step1.auth-only.json](test_data/TD-TC-000423.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000423.step2.cancellation.json](test_data/TD-TC-000423.step2.cancellation.json) |
| TC-000424 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000424.step1.auth-only.json](test_data/TD-TC-000424.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000424.step2.completion.json](test_data/TD-TC-000424.step2.completion.json) |
| TC-000425 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | needs_client_input | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000425.step1.auth-only.json](test_data/TD-TC-000425.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000425.step2.completion.json](test_data/TD-TC-000425.step2.completion.json) |
| TC-000426 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000426.step1.auth-only.json](test_data/TD-TC-000426.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000426.step2.cancellation.json](test_data/TD-TC-000426.step2.cancellation.json) |
| TC-000427 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000427.step1.auth-only.json](test_data/TD-TC-000427.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000427.step2.completion.json](test_data/TD-TC-000427.step2.completion.json) |
| TC-000428 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000428.step1.auth-only.json](test_data/TD-TC-000428.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000428.step2.completion.json](test_data/TD-TC-000428.step2.completion.json) |
| TC-000429 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000429.step1.auth-only.json](test_data/TD-TC-000429.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000429.step2.completion.json](test_data/TD-TC-000429.step2.completion.json) |
| TC-000430 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000430.step1.auth-only.json](test_data/TD-TC-000430.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000430.step2.cancellation.json](test_data/TD-TC-000430.step2.cancellation.json) |
| TC-000431 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000431.step1.auth-only.json](test_data/TD-TC-000431.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000431.step2.split-tender.json](test_data/TD-TC-000431.step2.split-tender.json) |
| TC-000432 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000432.step1.auth-only.json](test_data/TD-TC-000432.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000432.step2.completion.json](test_data/TD-TC-000432.step2.completion.json) |
| TC-000433 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000433.step1.auth-only.json](test_data/TD-TC-000433.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000433.step2.completion.json](test_data/TD-TC-000433.step2.completion.json) |
| TC-000434 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000434.step1.auth-only.json](test_data/TD-TC-000434.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000434.step2.cancellation.json](test_data/TD-TC-000434.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000434.step3.cancellation.json](test_data/TD-TC-000434.step3.cancellation.json) |
| TC-000435 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000435.step1.auth-only.json](test_data/TD-TC-000435.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000435.step2.cancellation.json](test_data/TD-TC-000435.step2.cancellation.json) |
| TC-000436 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000436.step1.auth-only.json](test_data/TD-TC-000436.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000436.step2.cancellation.json](test_data/TD-TC-000436.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000436.step3.totals.json](test_data/TD-TC-000436.step3.totals.json) |
| TC-000437 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000437.step1.auth-only.json](test_data/TD-TC-000437.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000437.step2.completion.json](test_data/TD-TC-000437.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000437.step3.totals.json](test_data/TD-TC-000437.step3.totals.json) |
| TC-000438 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000438.step1.auth-only.json](test_data/TD-TC-000438.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000438.step2.totals.json](test_data/TD-TC-000438.step2.totals.json) |
| TC-000439 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only on a  | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Authorization Only | [TD-TC-000439.json](test_data/TD-TC-000439.json) |
| TC-000440 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Response / oracle check | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000440.step1.auth-only.json](test_data/TD-TC-000440.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000440.step2.completion.json](test_data/TD-TC-000440.step2.completion.json) |
| TC-000441 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only (card | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000441.json](test_data/TD-TC-000441.json) |
| TC-000947 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Parti | Single rule broken | Negative | needs_client_input | BR-89-3 p.89; BR-89-5 p.89; BR-89-10 p.89 | 1 |  | [TD-TC-000947.json](test_data/TD-TC-000947.json) |
| TC-000949 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Requi | Single rule broken | Negative | needs_client_input | BR-639-2 p.639; BR-FISERV-EMV-GUIDE-42-1 p.42; p. | 1 |  | [TD-TC-000949.json](test_data/TD-TC-000949.json) |
| TC-000961 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Varia | Single rule broken | Negative | needs_client_input | BR-149-1 p.149; BR-146-2 p.146; BR-147-1 p.147 | 1 |  | [TD-TC-000961.json](test_data/TD-TC-000961.json) |

### SC-ATL105-5ED2613B - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-BC90B09B - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000736 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Onl | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000736.json](test_data/TD-TC-000736.json) |
| TC-000737 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Onl | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000737.json](test_data/TD-TC-000737.json) |

### SC-ATL105-99B31463 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-677D33AA - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000787 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000787.json](test_data/TD-TC-000787.json) |
| TC-000788 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000788.json](test_data/TD-TC-000788.json) |

### SC-ATL105-A210C900 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000855 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000855.json](test_data/TD-TC-000855.json) |
| TC-000856 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000856.step1.auth-only.json](test_data/TD-TC-000856.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000856.step2.cancellation.json](test_data/TD-TC-000856.step2.cancellation.json) |
| TC-000857 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000857.step1.auth-only.json](test_data/TD-TC-000857.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000857.step2.completion.json](test_data/TD-TC-000857.step2.completion.json) |
| TC-000858 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000858.step1.auth-only.json](test_data/TD-TC-000858.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000858.step2.completion.json](test_data/TD-TC-000858.step2.completion.json) |
| TC-000859 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000859.step1.auth-only.json](test_data/TD-TC-000859.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000859.step2.completion.json](test_data/TD-TC-000859.step2.completion.json) |
| TC-000860 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000860.step1.auth-only.json](test_data/TD-TC-000860.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000860.step2.cancellation.json](test_data/TD-TC-000860.step2.cancellation.json) |
| TC-000861 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000861.step1.auth-only.json](test_data/TD-TC-000861.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000861.step2.split-tender.json](test_data/TD-TC-000861.step2.split-tender.json) |
| TC-000862 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000862.step1.auth-only.json](test_data/TD-TC-000862.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000862.step2.completion.json](test_data/TD-TC-000862.step2.completion.json) |
| TC-000863 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000863.step1.auth-only.json](test_data/TD-TC-000863.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000863.step2.completion.json](test_data/TD-TC-000863.step2.completion.json) |
| TC-000864 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000864.step1.auth-only.json](test_data/TD-TC-000864.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000864.step2.cancellation.json](test_data/TD-TC-000864.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000864.step3.cancellation.json](test_data/TD-TC-000864.step3.cancellation.json) |
| TC-000865 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000865.json](test_data/TD-TC-000865.json) |
| TC-000955 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only  | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-185-2 p.185; p. | 1 |  | [TD-TC-000955.json](test_data/TD-TC-000955.json) |

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
| TC-000442 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only → | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000442.json](test_data/TD-TC-000442.json) |
| TC-000443 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only w | Happy path | Positive | needs_client_input | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000443.json](test_data/TD-TC-000443.json) |
| TC-000444 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000444.step1.auth-only.json](test_data/TD-TC-000444.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000444.step2.cancellation.json](test_data/TD-TC-000444.step2.cancellation.json) |
| TC-000445 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000445.step1.auth-only.json](test_data/TD-TC-000445.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000445.step2.cancellation.json](test_data/TD-TC-000445.step2.cancellation.json) |
| TC-000446 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000446.step1.auth-only.json](test_data/TD-TC-000446.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000446.step2.completion.json](test_data/TD-TC-000446.step2.completion.json) |
| TC-000447 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | needs_client_input | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000447.step1.auth-only.json](test_data/TD-TC-000447.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000447.step2.completion.json](test_data/TD-TC-000447.step2.completion.json) |
| TC-000448 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000448.step1.auth-only.json](test_data/TD-TC-000448.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000448.step2.cancellation.json](test_data/TD-TC-000448.step2.cancellation.json) |
| TC-000449 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000449.step1.auth-only.json](test_data/TD-TC-000449.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000449.step2.completion.json](test_data/TD-TC-000449.step2.completion.json) |
| TC-000450 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000450.step1.auth-only.json](test_data/TD-TC-000450.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000450.step2.completion.json](test_data/TD-TC-000450.step2.completion.json) |
| TC-000451 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000451.step1.auth-only.json](test_data/TD-TC-000451.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000451.step2.completion.json](test_data/TD-TC-000451.step2.completion.json) |
| TC-000452 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000452.step1.auth-only.json](test_data/TD-TC-000452.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000452.step2.cancellation.json](test_data/TD-TC-000452.step2.cancellation.json) |
| TC-000453 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000453.step1.auth-only.json](test_data/TD-TC-000453.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000453.step2.split-tender.json](test_data/TD-TC-000453.step2.split-tender.json) |
| TC-000454 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000454.step1.auth-only.json](test_data/TD-TC-000454.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000454.step2.completion.json](test_data/TD-TC-000454.step2.completion.json) |
| TC-000455 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000455.step1.auth-only.json](test_data/TD-TC-000455.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000455.step2.completion.json](test_data/TD-TC-000455.step2.completion.json) |
| TC-000456 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000456.step1.auth-only.json](test_data/TD-TC-000456.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000456.step2.cancellation.json](test_data/TD-TC-000456.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000456.step3.cancellation.json](test_data/TD-TC-000456.step3.cancellation.json) |
| TC-000457 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000457.step1.auth-only.json](test_data/TD-TC-000457.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000457.step2.cancellation.json](test_data/TD-TC-000457.step2.cancellation.json) |
| TC-000458 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000458.step1.auth-only.json](test_data/TD-TC-000458.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000458.step2.cancellation.json](test_data/TD-TC-000458.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000458.step3.totals.json](test_data/TD-TC-000458.step3.totals.json) |
| TC-000459 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000459.step1.auth-only.json](test_data/TD-TC-000459.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000459.step2.completion.json](test_data/TD-TC-000459.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000459.step3.totals.json](test_data/TD-TC-000459.step3.totals.json) |
| TC-000460 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000460.step1.auth-only.json](test_data/TD-TC-000460.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000460.step2.totals.json](test_data/TD-TC-000460.step2.totals.json) |
| TC-000461 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only o | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Authorization Only | [TD-TC-000461.json](test_data/TD-TC-000461.json) |
| TC-000462 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Response / oracle check | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000462.step1.auth-only.json](test_data/TD-TC-000462.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000462.step2.completion.json](test_data/TD-TC-000462.step2.completion.json) |
| TC-000463 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000463.json](test_data/TD-TC-000463.json) |

### SC-ATL105-70B0E17B - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-4E91C0CE - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000738 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000738.json](test_data/TD-TC-000738.json) |
| TC-000739 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000739.json](test_data/TD-TC-000739.json) |

### SC-ATL105-86BADD94 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-7BE85284 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000789 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization O | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000789.json](test_data/TD-TC-000789.json) |
| TC-000790 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization O | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000790.json](test_data/TD-TC-000790.json) |

### SC-ATL105-C1D2F2DB - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000866 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000866.json](test_data/TD-TC-000866.json) |
| TC-000867 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000867.step1.auth-only.json](test_data/TD-TC-000867.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000867.step2.cancellation.json](test_data/TD-TC-000867.step2.cancellation.json) |
| TC-000868 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000868.step1.auth-only.json](test_data/TD-TC-000868.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000868.step2.completion.json](test_data/TD-TC-000868.step2.completion.json) |
| TC-000869 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000869.step1.auth-only.json](test_data/TD-TC-000869.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000869.step2.completion.json](test_data/TD-TC-000869.step2.completion.json) |
| TC-000870 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000870.step1.auth-only.json](test_data/TD-TC-000870.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000870.step2.completion.json](test_data/TD-TC-000870.step2.completion.json) |
| TC-000871 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000871.step1.auth-only.json](test_data/TD-TC-000871.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000871.step2.cancellation.json](test_data/TD-TC-000871.step2.cancellation.json) |
| TC-000872 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000872.step1.auth-only.json](test_data/TD-TC-000872.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000872.step2.split-tender.json](test_data/TD-TC-000872.step2.split-tender.json) |
| TC-000873 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000873.step1.auth-only.json](test_data/TD-TC-000873.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000873.step2.completion.json](test_data/TD-TC-000873.step2.completion.json) |
| TC-000874 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.545; p.583; p.511 | 1 | Authorization Only | [TD-TC-000874.step1.auth-only.json](test_data/TD-TC-000874.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000874.step2.completion.json](test_data/TD-TC-000874.step2.completion.json) |
| TC-000875 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000875.step1.auth-only.json](test_data/TD-TC-000875.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000875.step2.cancellation.json](test_data/TD-TC-000875.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000875.step3.cancellation.json](test_data/TD-TC-000875.step3.cancellation.json) |
| TC-000876 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000876.json](test_data/TD-TC-000876.json) |
| TC-000953 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-130-3 p.130; BR-BUYPASS-PIPS-140-1 p.140; p. | 1 |  | [TD-TC-000953.json](test_data/TD-TC-000953.json) |

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
| TC-000464 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Happy path | Positive | needs_client_input | p.511; p.83; p.81 | 1 | Authorization Only | [TD-TC-000464.step1.auth-only.json](test_data/TD-TC-000464.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000464.step2.cancellation.json](test_data/TD-TC-000464.step2.cancellation.json) |
| TC-000465 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Happy path | Positive | needs_client_input | p.511; p.83; p.81 | 1 | Authorization Only | [TD-TC-000465.step1.auth-only.json](test_data/TD-TC-000465.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000465.step2.completion.json](test_data/TD-TC-000465.step2.completion.json) |
| TC-000466 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Happy path | Positive | needs_client_input | p.567; p.511; p.83 | 1 | Authorization Only | [TD-TC-000466.step1.auth-only.json](test_data/TD-TC-000466.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000466.step2.cancellation.json](test_data/TD-TC-000466.step2.cancellation.json) |
| TC-000467 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Happy path | Positive | needs_client_input | p.567; p.511; p.83 | 1 | Authorization Only | [TD-TC-000467.step1.auth-only.json](test_data/TD-TC-000467.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000467.step2.completion.json](test_data/TD-TC-000467.step2.completion.json) |
| TC-000468 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only → appr | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000468.json](test_data/TD-TC-000468.json) |
| TC-000469 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only with F | Happy path | Positive | needs_client_input | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000469.json](test_data/TD-TC-000469.json) |
| TC-000470 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000470.step1.auth-only.json](test_data/TD-TC-000470.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000470.step2.cancellation.json](test_data/TD-TC-000470.step2.cancellation.json) |
| TC-000471 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | needs_client_input | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000471.step1.auth-only.json](test_data/TD-TC-000471.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000471.step2.cancellation.json](test_data/TD-TC-000471.step2.cancellation.json) |
| TC-000472 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000472.step1.auth-only.json](test_data/TD-TC-000472.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000472.step2.completion.json](test_data/TD-TC-000472.step2.completion.json) |
| TC-000473 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | needs_client_input | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000473.step1.auth-only.json](test_data/TD-TC-000473.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000473.step2.completion.json](test_data/TD-TC-000473.step2.completion.json) |
| TC-000474 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000474.step1.auth-only.json](test_data/TD-TC-000474.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000474.step2.cancellation.json](test_data/TD-TC-000474.step2.cancellation.json) |
| TC-000475 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000475.step1.auth-only.json](test_data/TD-TC-000475.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000475.step2.completion.json](test_data/TD-TC-000475.step2.completion.json) |
| TC-000476 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000476.step1.auth-only.json](test_data/TD-TC-000476.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000476.step2.completion.json](test_data/TD-TC-000476.step2.completion.json) |
| TC-000477 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000477.step1.auth-only.json](test_data/TD-TC-000477.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000477.step2.completion.json](test_data/TD-TC-000477.step2.completion.json) |
| TC-000478 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000478.step1.auth-only.json](test_data/TD-TC-000478.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000478.step2.cancellation.json](test_data/TD-TC-000478.step2.cancellation.json) |
| TC-000479 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000479.step1.auth-only.json](test_data/TD-TC-000479.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000479.step2.split-tender.json](test_data/TD-TC-000479.step2.split-tender.json) |
| TC-000480 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000480.step1.auth-only.json](test_data/TD-TC-000480.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000480.step2.cancellation.json](test_data/TD-TC-000480.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000480.step3.cancellation.json](test_data/TD-TC-000480.step3.cancellation.json) |
| TC-000481 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000481.step1.auth-only.json](test_data/TD-TC-000481.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000481.step2.cancellation.json](test_data/TD-TC-000481.step2.cancellation.json) |
| TC-000482 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000482.step1.auth-only.json](test_data/TD-TC-000482.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000482.step2.cancellation.json](test_data/TD-TC-000482.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000482.step3.totals.json](test_data/TD-TC-000482.step3.totals.json) |
| TC-000483 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000483.step1.auth-only.json](test_data/TD-TC-000483.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000483.step2.completion.json](test_data/TD-TC-000483.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000483.step3.totals.json](test_data/TD-TC-000483.step3.totals.json) |
| TC-000484 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000484.step1.auth-only.json](test_data/TD-TC-000484.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000484.step2.totals.json](test_data/TD-TC-000484.step2.totals.json) |
| TC-000485 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Response / oracle check | Negative | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000485.step1.auth-only.json](test_data/TD-TC-000485.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000485.step2.completion.json](test_data/TD-TC-000485.step2.completion.json) |
| TC-000486 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only (card  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000486.json](test_data/TD-TC-000486.json) |

### SC-ATL105-F651389F - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-DE8E9D65 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000740 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000740.json](test_data/TD-TC-000740.json) |
| TC-000741 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000741.json](test_data/TD-TC-000741.json) |

### SC-ATL105-F6F1E237 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-97DE7FC1 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000791 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only → | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000791.json](test_data/TD-TC-000791.json) |
| TC-000792 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000792.json](test_data/TD-TC-000792.json) |

### SC-ATL105-B261473D - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000877 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Happy path | Positive | needs_client_input | p.511; p.83; p.81 | 1 | Authorization Only | [TD-TC-000877.step1.auth-only.json](test_data/TD-TC-000877.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000877.step2.cancellation.json](test_data/TD-TC-000877.step2.cancellation.json) |
| TC-000878 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Happy path | Positive | needs_client_input | p.511; p.83; p.81 | 1 | Authorization Only | [TD-TC-000878.step1.auth-only.json](test_data/TD-TC-000878.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000878.step2.completion.json](test_data/TD-TC-000878.step2.completion.json) |
| TC-000879 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Happy path | Positive | needs_client_input | p.567; p.511; p.83 | 1 | Authorization Only | [TD-TC-000879.step1.auth-only.json](test_data/TD-TC-000879.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000879.step2.cancellation.json](test_data/TD-TC-000879.step2.cancellation.json) |
| TC-000880 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Happy path | Positive | needs_client_input | p.567; p.511; p.83 | 1 | Authorization Only | [TD-TC-000880.step1.auth-only.json](test_data/TD-TC-000880.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000880.step2.completion.json](test_data/TD-TC-000880.step2.completion.json) |
| TC-000881 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only → | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000881.json](test_data/TD-TC-000881.json) |
| TC-000882 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000882.step1.auth-only.json](test_data/TD-TC-000882.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000882.step2.cancellation.json](test_data/TD-TC-000882.step2.cancellation.json) |
| TC-000883 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000883.step1.auth-only.json](test_data/TD-TC-000883.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000883.step2.completion.json](test_data/TD-TC-000883.step2.completion.json) |
| TC-000884 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000884.step1.auth-only.json](test_data/TD-TC-000884.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000884.step2.completion.json](test_data/TD-TC-000884.step2.completion.json) |
| TC-000885 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000885.step1.auth-only.json](test_data/TD-TC-000885.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000885.step2.completion.json](test_data/TD-TC-000885.step2.completion.json) |
| TC-000886 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000886.step1.auth-only.json](test_data/TD-TC-000886.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000886.step2.cancellation.json](test_data/TD-TC-000886.step2.cancellation.json) |
| TC-000887 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000887.step1.auth-only.json](test_data/TD-TC-000887.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000887.step2.split-tender.json](test_data/TD-TC-000887.step2.split-tender.json) |
| TC-000888 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000888.step1.auth-only.json](test_data/TD-TC-000888.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000888.step2.cancellation.json](test_data/TD-TC-000888.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000888.step3.cancellation.json](test_data/TD-TC-000888.step3.cancellation.json) |
| TC-000889 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000889.json](test_data/TD-TC-000889.json) |
| TC-000952 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only R | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-130-3 p.130; BR-BUYPASS-PIPS-140-1 p.140; p. | 1 |  | [TD-TC-000952.json](test_data/TD-TC-000952.json) |

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
| TC-000506 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only → appro | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000506.json](test_data/TD-TC-000506.json) |
| TC-000507 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only with Fu | Happy path | Positive | needs_client_input | BR-628-2 p.628; p.511; p.83 | 1 | Authorization Only | [TD-TC-000507.json](test_data/TD-TC-000507.json) |
| TC-000508 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Single rule broken | Negative | requires_review | p.546; p.368; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000508.step1.auth-only.json](test_data/TD-TC-000508.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000508.step2.cancellation.json](test_data/TD-TC-000508.step2.cancellation.json) |
| TC-000509 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Single rule broken | Negative | requires_review | p.546; p.368; BR-40-2 p.40 | 1 | Authorization Only | [TD-TC-000509.step1.auth-only.json](test_data/TD-TC-000509.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000509.step2.cancellation.json](test_data/TD-TC-000509.step2.cancellation.json) |
| TC-000510 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Single rule broken | Negative | requires_review | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Authorization Only | [TD-TC-000510.step1.auth-only.json](test_data/TD-TC-000510.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000510.step2.completion.json](test_data/TD-TC-000510.step2.completion.json) |
| TC-000511 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Single rule broken | Negative | requires_review | p.545; BR-40-2 p.40; p.511 | 1 | Authorization Only | [TD-TC-000511.step1.auth-only.json](test_data/TD-TC-000511.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000511.step2.completion.json](test_data/TD-TC-000511.step2.completion.json) |
| TC-000512 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Valid flow | Positive | requires_review | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-000512.step1.auth-only.json](test_data/TD-TC-000512.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000512.step2.cancellation.json](test_data/TD-TC-000512.step2.cancellation.json) |
| TC-000513 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Valid flow | Positive | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000513.step1.auth-only.json](test_data/TD-TC-000513.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000513.step2.completion.json](test_data/TD-TC-000513.step2.completion.json) |
| TC-000514 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Valid flow | Positive | requires_review | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000514.step1.auth-only.json](test_data/TD-TC-000514.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000514.step2.completion.json](test_data/TD-TC-000514.step2.completion.json) |
| TC-000515 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Valid flow | Positive | requires_review | p.545; p.511; p.83 | 1 | Authorization Only | [TD-TC-000515.step1.auth-only.json](test_data/TD-TC-000515.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000515.step2.completion.json](test_data/TD-TC-000515.step2.completion.json) |
| TC-000516 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Valid flow | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000516.step1.auth-only.json](test_data/TD-TC-000516.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000516.step2.cancellation.json](test_data/TD-TC-000516.step2.cancellation.json) |
| TC-000517 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then A | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Authorization Only | [TD-TC-000517.step1.auth-only.json](test_data/TD-TC-000517.step1.auth-only.json) |
|  |  |  |  |  | 2 | Authorization Only | [TD-TC-000517.step2.split-tender.json](test_data/TD-TC-000517.step2.split-tender.json) |
| TC-000518 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-000518.step1.auth-only.json](test_data/TD-TC-000518.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000518.step2.cancellation.json](test_data/TD-TC-000518.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000518.step3.cancellation.json](test_data/TD-TC-000518.step3.cancellation.json) |
| TC-000519 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Authorization Only | [TD-TC-000519.step1.auth-only.json](test_data/TD-TC-000519.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000519.step2.cancellation.json](test_data/TD-TC-000519.step2.cancellation.json) |
| TC-000520 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-000520.step1.auth-only.json](test_data/TD-TC-000520.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000520.step2.cancellation.json](test_data/TD-TC-000520.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000520.step3.totals.json](test_data/TD-TC-000520.step3.totals.json) |
| TC-000521 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000521.step1.auth-only.json](test_data/TD-TC-000521.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000521.step2.completion.json](test_data/TD-TC-000521.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000521.step3.totals.json](test_data/TD-TC-000521.step3.totals.json) |
| TC-000522 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then T | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-000522.step1.auth-only.json](test_data/TD-TC-000522.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000522.step2.totals.json](test_data/TD-TC-000522.step2.totals.json) |
| TC-000523 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Response / oracle check | Negative | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Authorization Only | [TD-TC-000523.step1.auth-only.json](test_data/TD-TC-000523.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000523.step2.completion.json](test_data/TD-TC-000523.step2.completion.json) |
| TC-000524 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only (card i | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000524.json](test_data/TD-TC-000524.json) |

### SC-ATL105-D552FDE1 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000744 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000744.json](test_data/TD-TC-000744.json) |
| TC-000745 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000745.json](test_data/TD-TC-000745.json) |

### SC-ATL105-37350FC3 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000795 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only →  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-000795.json](test_data/TD-TC-000795.json) |
| TC-000796 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-000796.json](test_data/TD-TC-000796.json) |

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
| TC-000712 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry → approved | Happy path | Positive | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000712.json](test_data/TD-TC-000712.json) |
| TC-000713 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry, then Total | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-000713.step1.balance-inquiry.json](test_data/TD-TC-000713.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000713.step2.totals.json](test_data/TD-TC-000713.step2.totals.json) |
| TC-000714 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry (card issue | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000714.json](test_data/TD-TC-000714.json) |

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
| TC-000706 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inqui | Happy path | Positive | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000706.json](test_data/TD-TC-000706.json) |
| TC-000707 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inqui | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-000707.step1.balance-inquiry.json](test_data/TD-TC-000707.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000707.step2.totals.json](test_data/TD-TC-000707.step2.totals.json) |
| TC-000708 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inqui | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000708.json](test_data/TD-TC-000708.json) |

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
| TC-000718 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balanc | Happy path | Positive | pending_sme | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000718.json](test_data/TD-TC-000718.json) |
| TC-000719 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balanc | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-000719.step1.balance-inquiry.json](test_data/TD-TC-000719.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000719.step2.totals.json](test_data/TD-TC-000719.step2.totals.json) |
| TC-000720 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balanc | Response / oracle check | Negative | pending_sme | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000720.json](test_data/TD-TC-000720.json) |

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
| TC-000715 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry → appr | Happy path | Positive | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000715.json](test_data/TD-TC-000715.json) |
| TC-000716 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry, then  | Running total / state | Positive | pending_sme | BR-181-1 p.181; BR-183-1 p.183; BR-235-7 p.235 | 1 | Balance Inquiry | [TD-TC-000716.step1.balance-inquiry.json](test_data/TD-TC-000716.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000716.step2.totals.json](test_data/TD-TC-000716.step2.totals.json) |
| TC-000717 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry (card  | Response / oracle check | Negative | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000717.json](test_data/TD-TC-000717.json) |

### SC-ATL105-491A7A04 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000695 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry → approv | Happy path | Positive | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000695.json](test_data/TD-TC-000695.json) |
| TC-000696 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry, then To | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-000696.step1.balance-inquiry.json](test_data/TD-TC-000696.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000696.step2.totals.json](test_data/TD-TC-000696.step2.totals.json) |
| TC-000697 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry on a POS | Interaction (pairwise) | Positive | needs_client_input | p.546; BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50 | 1 | Balance Inquiry | [TD-TC-000697.json](test_data/TD-TC-000697.json) |
| TC-000698 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry (card is | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000698.json](test_data/TD-TC-000698.json) |
| TC-000948 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry required | Single rule broken | Negative | ready | BR-162-2 p.162; BR-FISERV-EMV-GUIDE-74-7 p.74; BR-FISERV-EMV-GUIDE-87-6 p.87 | 1 |  | [TD-TC-000948.json](test_data/TD-TC-000948.json) |
| TC-000973 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry Informat | Single rule broken | Boundary | needs_client_input | BR-FISERV-EMV-GUIDE-157-10 p.157; BR-FISERV-EMV-GUIDE-157-11 p.157; p. | 1 |  | [TD-TC-000973.json](test_data/TD-TC-000973.json) |

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
| TC-000699 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry → ap | Happy path | Positive | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000699.json](test_data/TD-TC-000699.json) |
| TC-000700 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry, the | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-000700.step1.balance-inquiry.json](test_data/TD-TC-000700.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000700.step2.totals.json](test_data/TD-TC-000700.step2.totals.json) |
| TC-000701 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry on a | Interaction (pairwise) | Positive | needs_client_input | p.546; BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50 | 1 | Balance Inquiry | [TD-TC-000701.json](test_data/TD-TC-000701.json) |
| TC-000702 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry (car | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000702.json](test_data/TD-TC-000702.json) |

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
| TC-000703 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry → approve | Happy path | Positive | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000703.json](test_data/TD-TC-000703.json) |
| TC-000704 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry, then Tot | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-000704.step1.balance-inquiry.json](test_data/TD-TC-000704.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000704.step2.totals.json](test_data/TD-TC-000704.step2.totals.json) |
| TC-000705 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry (card iss | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000705.json](test_data/TD-TC-000705.json) |

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
| TC-000709 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry → approved | Happy path | Positive | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000709.json](test_data/TD-TC-000709.json) |
| TC-000710 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry, then Tota | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-000710.step1.balance-inquiry.json](test_data/TD-TC-000710.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000710.step2.totals.json](test_data/TD-TC-000710.step2.totals.json) |
| TC-000711 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry (card issu | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-000711.json](test_data/TD-TC-000711.json) |

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
| TC-000612 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Happy path | Positive | pending_sme | p.546; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Authorization Only | [TD-TC-000612.json](test_data/TD-TC-000612.json) |
| TC-000613 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Happy path | Positive | pending_sme | BR-83-1 p.83; BR-ATL105-TRANSARMOR-76-5 p.76; p.511 | 1 | Mail/Phone Authorization Only | [TD-TC-000613.json](test_data/TD-TC-000613.json) |
| TC-000614 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.546; p.368; BR-45-2 p.45 | 1 | Mail/Phone Authorization Only | [TD-TC-000614.step1.moto-auth-only.json](test_data/TD-TC-000614.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000614.step2.cancellation.json](test_data/TD-TC-000614.step2.cancellation.json) |
| TC-000615 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.546; p.368; BR-40-2 p.40 | 1 | Mail/Phone Authorization Only | [TD-TC-000615.step1.moto-auth-only.json](test_data/TD-TC-000615.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000615.step2.cancellation.json](test_data/TD-TC-000615.step2.cancellation.json) |
| TC-000616 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Mail/Phone Authorization Only | [TD-TC-000616.step1.moto-auth-only.json](test_data/TD-TC-000616.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000616.step2.completion.json](test_data/TD-TC-000616.step2.completion.json) |
| TC-000617 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.545; BR-40-2 p.40; p.511 | 1 | Mail/Phone Authorization Only | [TD-TC-000617.step1.moto-auth-only.json](test_data/TD-TC-000617.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000617.step2.completion.json](test_data/TD-TC-000617.step2.completion.json) |
| TC-000618 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Valid flow | Positive | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Mail/Phone Authorization Only | [TD-TC-000618.step1.moto-auth-only.json](test_data/TD-TC-000618.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000618.step2.cancellation.json](test_data/TD-TC-000618.step2.cancellation.json) |
| TC-000619 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Valid flow | Positive | pending_sme | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Mail/Phone Authorization Only | [TD-TC-000619.step1.moto-auth-only.json](test_data/TD-TC-000619.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000619.step2.completion.json](test_data/TD-TC-000619.step2.completion.json) |
| TC-000620 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000620.step1.moto-auth-only.json](test_data/TD-TC-000620.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000620.step2.cancellation.json](test_data/TD-TC-000620.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000620.step3.cancellation.json](test_data/TD-TC-000620.step3.cancellation.json) |
| TC-000621 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Time-based | Positive | pending_sme | BR-85-3 p.85; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000621.step1.moto-auth-only.json](test_data/TD-TC-000621.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000621.step2.cancellation.json](test_data/TD-TC-000621.step2.cancellation.json) |
| TC-000622 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Running total / state | Positive | pending_sme | p.546; p.368; BR-181-1 p.181 | 1 | Mail/Phone Authorization Only | [TD-TC-000622.step1.moto-auth-only.json](test_data/TD-TC-000622.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000622.step2.cancellation.json](test_data/TD-TC-000622.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000622.step3.totals.json](test_data/TD-TC-000622.step3.totals.json) |
| TC-000623 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Authorization Only | [TD-TC-000623.step1.moto-auth-only.json](test_data/TD-TC-000623.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000623.step2.completion.json](test_data/TD-TC-000623.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000623.step3.totals.json](test_data/TD-TC-000623.step3.totals.json) |
| TC-000624 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Authorization Only | [TD-TC-000624.step1.moto-auth-only.json](test_data/TD-TC-000624.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000624.step2.totals.json](test_data/TD-TC-000624.step2.totals.json) |
| TC-000625 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Response / oracle check | Negative | pending_sme | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Mail/Phone Authorization Only | [TD-TC-000625.step1.moto-auth-only.json](test_data/TD-TC-000625.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000625.step2.completion.json](test_data/TD-TC-000625.step2.completion.json) |
| TC-000626 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Response / oracle check | Negative | pending_sme | BR-83-1 p.83; BR-ATL105-TRANSARMOR-76-5 p.76; p.511 | 1 | Mail/Phone Authorization Only | [TD-TC-000626.json](test_data/TD-TC-000626.json) |

### SC-ATL105-12B03219 - BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed @ Mail Order · mail and/or telephone order
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000583 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Happy path | Positive | needs_client_input | p.546; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Authorization Only | [TD-TC-000583.json](test_data/TD-TC-000583.json) |
| TC-000584 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.546; p.368; BR-45-2 p.45 | 1 | Mail/Phone Authorization Only | [TD-TC-000584.step1.moto-auth-only.json](test_data/TD-TC-000584.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000584.step2.cancellation.json](test_data/TD-TC-000584.step2.cancellation.json) |
| TC-000585 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.546; p.368; BR-40-2 p.40 | 1 | Mail/Phone Authorization Only | [TD-TC-000585.step1.moto-auth-only.json](test_data/TD-TC-000585.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000585.step2.cancellation.json](test_data/TD-TC-000585.step2.cancellation.json) |
| TC-000586 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.545; BR-402-1 p.402; BR-45-2 p.45 | 1 | Mail/Phone Authorization Only | [TD-TC-000586.step1.moto-auth-only.json](test_data/TD-TC-000586.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000586.step2.completion.json](test_data/TD-TC-000586.step2.completion.json) |
| TC-000587 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.545; BR-40-2 p.40; p.511 | 1 | Mail/Phone Authorization Only | [TD-TC-000587.step1.moto-auth-only.json](test_data/TD-TC-000587.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000587.step2.completion.json](test_data/TD-TC-000587.step2.completion.json) |
| TC-000588 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Valid flow | Positive | requires_review | p.546; p.368; BR-85-2 p.85 | 1 | Mail/Phone Authorization Only | [TD-TC-000588.step1.moto-auth-only.json](test_data/TD-TC-000588.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000588.step2.cancellation.json](test_data/TD-TC-000588.step2.cancellation.json) |
| TC-000589 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Valid flow | Positive | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Mail/Phone Authorization Only | [TD-TC-000589.step1.moto-auth-only.json](test_data/TD-TC-000589.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000589.step2.completion.json](test_data/TD-TC-000589.step2.completion.json) |
| TC-000590 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000590.step1.moto-auth-only.json](test_data/TD-TC-000590.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000590.step2.cancellation.json](test_data/TD-TC-000590.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-000590.step3.cancellation.json](test_data/TD-TC-000590.step3.cancellation.json) |
| TC-000591 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000591.step1.moto-auth-only.json](test_data/TD-TC-000591.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000591.step2.cancellation.json](test_data/TD-TC-000591.step2.cancellation.json) |
| TC-000592 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Mail/Phone Authorization Only | [TD-TC-000592.step1.moto-auth-only.json](test_data/TD-TC-000592.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-000592.step2.cancellation.json](test_data/TD-TC-000592.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000592.step3.totals.json](test_data/TD-TC-000592.step3.totals.json) |
| TC-000593 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Authorization Only | [TD-TC-000593.step1.moto-auth-only.json](test_data/TD-TC-000593.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000593.step2.completion.json](test_data/TD-TC-000593.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000593.step3.totals.json](test_data/TD-TC-000593.step3.totals.json) |
| TC-000594 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Authorization Only | [TD-TC-000594.step1.moto-auth-only.json](test_data/TD-TC-000594.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000594.step2.totals.json](test_data/TD-TC-000594.step2.totals.json) |
| TC-000595 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Response / oracle check | Negative | requires_review | p.545; BR-402-1 p.402; BR-368-6 p.368 | 1 | Mail/Phone Authorization Only | [TD-TC-000595.step1.moto-auth-only.json](test_data/TD-TC-000595.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-000595.step2.completion.json](test_data/TD-TC-000595.step2.completion.json) |
| TC-000596 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Response / oracle check | Negative | needs_client_input | p.546; BR-139-1 p.139; BR-139-4 p.139 | 1 | Mail/Phone Authorization Only | [TD-TC-000596.json](test_data/TD-TC-000596.json) |
| TC-000597 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000597.json](test_data/TD-TC-000597.json) |
| TC-000598 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000598.json](test_data/TD-TC-000598.json) |
| TC-000599 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000599.json](test_data/TD-TC-000599.json) |
| TC-000600 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000600.json](test_data/TD-TC-000600.json) |
| TC-000601 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000601.json](test_data/TD-TC-000601.json) |
| TC-000602 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000602.json](test_data/TD-TC-000602.json) |
| TC-000603 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000603.json](test_data/TD-TC-000603.json) |
| TC-000604 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000604.json](test_data/TD-TC-000604.json) |
| TC-000605 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-000605.json](test_data/TD-TC-000605.json) |
| TC-000606 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | needs_client_input | p.511; p.83; p.81 | 1 | Mail/Phone Authorization Only | [TD-TC-000606.json](test_data/TD-TC-000606.json) |
| TC-000607 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | needs_client_input | p.511; p.83; p.81 | 1 | Mail/Phone Authorization Only | [TD-TC-000607.json](test_data/TD-TC-000607.json) |
| TC-000608 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | needs_client_input | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000608.json](test_data/TD-TC-000608.json) |
| TC-000609 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | needs_client_input | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000609.json](test_data/TD-TC-000609.json) |
| TC-000610 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | needs_client_input | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000610.json](test_data/TD-TC-000610.json) |
| TC-000611 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | needs_client_input | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-000611.json](test_data/TD-TC-000611.json) |

### SC-ATL105-D2FEACFC - BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File @ Mail Order · mail and/or telephone order
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000406 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase →  | Happy path | Positive | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-000406.json](test_data/TD-TC-000406.json) |
| TC-000407 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase →  | Happy path | Positive | pending_sme | p.545; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Purchase | [TD-TC-000407.json](test_data/TD-TC-000407.json) |
| TC-000408 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Single rule broken | Negative | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-000408.step1.moto-purchase.json](test_data/TD-TC-000408.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000408.step2.moto-reversal.json](test_data/TD-TC-000408.step2.moto-reversal.json) |
| TC-000409 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Single rule broken | Negative | pending_sme | p.546; p.546; BR-40-2 p.40 | 1 | Mail/Phone Purchase | [TD-TC-000409.step1.moto-purchase.json](test_data/TD-TC-000409.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000409.step2.moto-reversal.json](test_data/TD-TC-000409.step2.moto-reversal.json) |
| TC-000410 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (n | Single rule broken | Negative | pending_sme | p.546; p.39; p.40 | 1 | Mail/Phone Purchase | [TD-TC-000410.step1.moto-purchase.json](test_data/TD-TC-000410.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000410.step2.timeout-reversal.json](test_data/TD-TC-000410.step2.timeout-reversal.json) |
| TC-000411 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase wi | Decision table | Positive | pending_sme | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Mail/Phone Purchase | [TD-TC-000411.json](test_data/TD-TC-000411.json) |
| TC-000412 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-000412.step1.moto-purchase.json](test_data/TD-TC-000412.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000412.step2.moto-reversal.json](test_data/TD-TC-000412.step2.moto-reversal.json) |
| TC-000413 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (n | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Mail/Phone Purchase | [TD-TC-000413.step1.moto-purchase.json](test_data/TD-TC-000413.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000413.step2.timeout-reversal.json](test_data/TD-TC-000413.step2.timeout-reversal.json) |
| TC-000414 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Invalid state transition | Negative | pending_sme | p.546; BR-403-3 p.403; p.511 | 1 | Mail/Phone Purchase | [TD-TC-000414.step1.moto-purchase.json](test_data/TD-TC-000414.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000414.step2.moto-reversal.json](test_data/TD-TC-000414.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | Mail/Phone Reversal | [TD-TC-000414.step3.moto-reversal.json](test_data/TD-TC-000414.step3.moto-reversal.json) |
| TC-000415 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (n | Invalid state transition | Negative | pending_sme | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Mail/Phone Purchase | [TD-TC-000415.step1.moto-purchase.json](test_data/TD-TC-000415.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000415.step2.timeout-reversal.json](test_data/TD-TC-000415.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000415.step3.timeout-reversal.json](test_data/TD-TC-000415.step3.timeout-reversal.json) |
| TC-000416 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (n | Fault / recovery | Positive | pending_sme | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Mail/Phone Purchase | [TD-TC-000416.step1.moto-purchase.json](test_data/TD-TC-000416.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000416.step2.timeout-reversal.json](test_data/TD-TC-000416.step2.timeout-reversal.json) |
| TC-000417 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Mail/Phone Purchase | [TD-TC-000417.step1.moto-purchase.json](test_data/TD-TC-000417.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000417.step2.moto-reversal.json](test_data/TD-TC-000417.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000417.step3.totals.json](test_data/TD-TC-000417.step3.totals.json) |
| TC-000418 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Purchase | [TD-TC-000418.step1.moto-purchase.json](test_data/TD-TC-000418.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000418.step2.totals.json](test_data/TD-TC-000418.step2.totals.json) |
| TC-000419 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (c | Response / oracle check | Negative | pending_sme | p.545; BR-72-4 p.72; BR-139-1 p.139 | 1 | Mail/Phone Purchase | [TD-TC-000419.json](test_data/TD-TC-000419.json) |

### SC-ATL105-6BE2183C - BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed @ Mail Order · mail and/or telephone order
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000390 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Happy path | Positive | requires_review | p.511; p.83; p.81 | 1 | Mail/Phone Purchase | [TD-TC-000390.step1.moto-purchase.json](test_data/TD-TC-000390.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000390.step2.moto-reversal.json](test_data/TD-TC-000390.step2.moto-reversal.json) |
| TC-000391 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Happy path | Positive | requires_review | p.567; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-000391.step1.moto-purchase.json](test_data/TD-TC-000391.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000391.step2.moto-reversal.json](test_data/TD-TC-000391.step2.moto-reversal.json) |
| TC-000392 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase → appr | Happy path | Positive | needs_client_input | p.545; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Purchase | [TD-TC-000392.json](test_data/TD-TC-000392.json) |
| TC-000393 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Single rule broken | Negative | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-000393.step1.moto-purchase.json](test_data/TD-TC-000393.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000393.step2.moto-reversal.json](test_data/TD-TC-000393.step2.moto-reversal.json) |
| TC-000394 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Mail/Phone Purchase | [TD-TC-000394.step1.moto-purchase.json](test_data/TD-TC-000394.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000394.step2.moto-reversal.json](test_data/TD-TC-000394.step2.moto-reversal.json) |
| TC-000395 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no re | Single rule broken | Negative | requires_review | p.546; p.39; p.40 | 1 | Mail/Phone Purchase | [TD-TC-000395.step1.moto-purchase.json](test_data/TD-TC-000395.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000395.step2.timeout-reversal.json](test_data/TD-TC-000395.step2.timeout-reversal.json) |
| TC-000396 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase with i | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Mail/Phone Purchase | [TD-TC-000396.json](test_data/TD-TC-000396.json) |
| TC-000397 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase with i | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Mail/Phone Purchase | [TD-TC-000397.json](test_data/TD-TC-000397.json) |
| TC-000398 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-000398.step1.moto-purchase.json](test_data/TD-TC-000398.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000398.step2.moto-reversal.json](test_data/TD-TC-000398.step2.moto-reversal.json) |
| TC-000399 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no re | Valid flow | Positive | requires_review | p.546; p.39; BR-84-4 p.84 | 1 | Mail/Phone Purchase | [TD-TC-000399.step1.moto-purchase.json](test_data/TD-TC-000399.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000399.step2.timeout-reversal.json](test_data/TD-TC-000399.step2.timeout-reversal.json) |
| TC-000400 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Invalid state transition | Negative | requires_review | p.546; BR-403-3 p.403; p.511 | 1 | Mail/Phone Purchase | [TD-TC-000400.step1.moto-purchase.json](test_data/TD-TC-000400.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000400.step2.moto-reversal.json](test_data/TD-TC-000400.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | Mail/Phone Reversal | [TD-TC-000400.step3.moto-reversal.json](test_data/TD-TC-000400.step3.moto-reversal.json) |
| TC-000401 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no re | Invalid state transition | Negative | requires_review | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Mail/Phone Purchase | [TD-TC-000401.step1.moto-purchase.json](test_data/TD-TC-000401.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000401.step2.timeout-reversal.json](test_data/TD-TC-000401.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000401.step3.timeout-reversal.json](test_data/TD-TC-000401.step3.timeout-reversal.json) |
| TC-000402 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no re | Fault / recovery | Positive | requires_review | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Mail/Phone Purchase | [TD-TC-000402.step1.moto-purchase.json](test_data/TD-TC-000402.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000402.step2.timeout-reversal.json](test_data/TD-TC-000402.step2.timeout-reversal.json) |
| TC-000403 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Mail/Phone Purchase | [TD-TC-000403.step1.moto-purchase.json](test_data/TD-TC-000403.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-000403.step2.moto-reversal.json](test_data/TD-TC-000403.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000403.step3.totals.json](test_data/TD-TC-000403.step3.totals.json) |
| TC-000404 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Purchase | [TD-TC-000404.step1.moto-purchase.json](test_data/TD-TC-000404.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000404.step2.totals.json](test_data/TD-TC-000404.step2.totals.json) |
| TC-000405 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (card  | Response / oracle check | Negative | needs_client_input | p.545; BR-72-4 p.72; BR-139-1 p.139 | 1 | Mail/Phone Purchase | [TD-TC-000405.json](test_data/TD-TC-000405.json) |
| TC-000946 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase Requir | Single rule broken | Negative | needs_client_input | BR-89-3 p.89; BR-89-5 p.89; BR-89-10 p.89 | 1 |  | [TD-TC-000946.json](test_data/TD-TC-000946.json) |

### SC-ATL105-873CDFFE - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000671 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return → approv | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000671.json](test_data/TD-TC-000671.json) |
| TC-000672 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Single rule broken | Negative | requires_review | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000672.step1.return.json](test_data/TD-TC-000672.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000672.step2.void-return.json](test_data/TD-TC-000672.step2.void-return.json) |
| TC-000673 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000673.step1.return.json](test_data/TD-TC-000673.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000673.step2.void-return.json](test_data/TD-TC-000673.step2.void-return.json) |
| TC-000674 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000674.step1.return.json](test_data/TD-TC-000674.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000674.step2.void-return.json](test_data/TD-TC-000674.step2.void-return.json) |
| TC-000675 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000675.step1.return.json](test_data/TD-TC-000675.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000675.step2.void-return.json](test_data/TD-TC-000675.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000675.step3.void-return.json](test_data/TD-TC-000675.step3.void-return.json) |
| TC-000676 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000676.step1.return.json](test_data/TD-TC-000676.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000676.step2.void-return.json](test_data/TD-TC-000676.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000676.step3.totals.json](test_data/TD-TC-000676.step3.totals.json) |
| TC-000677 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then To | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000677.step1.return.json](test_data/TD-TC-000677.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000677.step2.totals.json](test_data/TD-TC-000677.step2.totals.json) |
| TC-000678 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return (card is | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000678.json](test_data/TD-TC-000678.json) |

### SC-ATL105-4B839359 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000806 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Merchandise Return → a | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000806.json](test_data/TD-TC-000806.json) |

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
| TC-000653 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Happy path | Positive | needs_client_input | p.511; p.83; p.86 | 1 | Merchandise Return | [TD-TC-000653.step1.return.json](test_data/TD-TC-000653.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000653.step2.void-return.json](test_data/TD-TC-000653.step2.void-return.json) |
| TC-000654 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Happy path | Positive | needs_client_input | p.567; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000654.step1.return.json](test_data/TD-TC-000654.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000654.step2.void-return.json](test_data/TD-TC-000654.step2.void-return.json) |
| TC-000655 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000655.json](test_data/TD-TC-000655.json) |
| TC-000656 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Single rule broken | Negative | needs_client_input | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000656.step1.return.json](test_data/TD-TC-000656.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000656.step2.void-return.json](test_data/TD-TC-000656.step2.void-return.json) |
| TC-000657 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Single rule broken | Negative | needs_client_input | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000657.step1.return.json](test_data/TD-TC-000657.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000657.step2.void-return.json](test_data/TD-TC-000657.step2.void-return.json) |
| TC-000658 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Valid flow | Positive | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000658.step1.return.json](test_data/TD-TC-000658.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000658.step2.void-return.json](test_data/TD-TC-000658.step2.void-return.json) |
| TC-000659 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000659.step1.return.json](test_data/TD-TC-000659.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000659.step2.void-return.json](test_data/TD-TC-000659.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000659.step3.void-return.json](test_data/TD-TC-000659.step3.void-return.json) |
| TC-000660 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000660.step1.return.json](test_data/TD-TC-000660.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000660.step2.void-return.json](test_data/TD-TC-000660.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000660.step3.totals.json](test_data/TD-TC-000660.step3.totals.json) |
| TC-000661 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000661.step1.return.json](test_data/TD-TC-000661.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000661.step2.totals.json](test_data/TD-TC-000661.step2.totals.json) |
| TC-000662 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000662.json](test_data/TD-TC-000662.json) |

### SC-ATL105-D2A34953 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000804 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Merchand | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000804.json](test_data/TD-TC-000804.json) |

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
| TC-000687 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Happy path | Positive | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000687.json](test_data/TD-TC-000687.json) |
| TC-000688 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Single rule broken | Negative | pending_sme | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000688.step1.return.json](test_data/TD-TC-000688.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000688.step2.void-return.json](test_data/TD-TC-000688.step2.void-return.json) |
| TC-000689 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Single rule broken | Negative | pending_sme | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000689.step1.return.json](test_data/TD-TC-000689.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000689.step2.void-return.json](test_data/TD-TC-000689.step2.void-return.json) |
| TC-000690 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000690.step1.return.json](test_data/TD-TC-000690.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000690.step2.void-return.json](test_data/TD-TC-000690.step2.void-return.json) |
| TC-000691 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000691.step1.return.json](test_data/TD-TC-000691.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000691.step2.void-return.json](test_data/TD-TC-000691.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000691.step3.void-return.json](test_data/TD-TC-000691.step3.void-return.json) |
| TC-000692 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000692.step1.return.json](test_data/TD-TC-000692.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000692.step2.void-return.json](test_data/TD-TC-000692.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000692.step3.totals.json](test_data/TD-TC-000692.step3.totals.json) |
| TC-000693 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000693.step1.return.json](test_data/TD-TC-000693.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000693.step2.totals.json](test_data/TD-TC-000693.step2.totals.json) |
| TC-000694 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Response / oracle check | Negative | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000694.json](test_data/TD-TC-000694.json) |

### SC-ATL105-7E0F33A6 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Petroleum · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000807 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device M | Happy path | Positive | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000807.json](test_data/TD-TC-000807.json) |

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
| TC-000679 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return → a | Happy path | Positive | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000679.json](test_data/TD-TC-000679.json) |
| TC-000680 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Single rule broken | Negative | pending_sme | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000680.step1.return.json](test_data/TD-TC-000680.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000680.step2.void-return.json](test_data/TD-TC-000680.step2.void-return.json) |
| TC-000681 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Single rule broken | Negative | pending_sme | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000681.step1.return.json](test_data/TD-TC-000681.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000681.step2.void-return.json](test_data/TD-TC-000681.step2.void-return.json) |
| TC-000682 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000682.step1.return.json](test_data/TD-TC-000682.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000682.step2.void-return.json](test_data/TD-TC-000682.step2.void-return.json) |
| TC-000683 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000683.step1.return.json](test_data/TD-TC-000683.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000683.step2.void-return.json](test_data/TD-TC-000683.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000683.step3.void-return.json](test_data/TD-TC-000683.step3.void-return.json) |
| TC-000684 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000684.step1.return.json](test_data/TD-TC-000684.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000684.step2.void-return.json](test_data/TD-TC-000684.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000684.step3.totals.json](test_data/TD-TC-000684.step3.totals.json) |
| TC-000685 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000685.step1.return.json](test_data/TD-TC-000685.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000685.step2.totals.json](test_data/TD-TC-000685.step2.totals.json) |
| TC-000686 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return (ca | Response / oracle check | Negative | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000686.json](test_data/TD-TC-000686.json) |

### SC-ATL105-09CE3848 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000627 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return → app | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000627.json](test_data/TD-TC-000627.json) |
| TC-000628 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Single rule broken | Negative | requires_review | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000628.step1.return.json](test_data/TD-TC-000628.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000628.step2.void-return.json](test_data/TD-TC-000628.step2.void-return.json) |
| TC-000629 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000629.step1.return.json](test_data/TD-TC-000629.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000629.step2.void-return.json](test_data/TD-TC-000629.step2.void-return.json) |
| TC-000630 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000630.step1.return.json](test_data/TD-TC-000630.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000630.step2.void-return.json](test_data/TD-TC-000630.step2.void-return.json) |
| TC-000631 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000631.step1.return.json](test_data/TD-TC-000631.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000631.step2.void-return.json](test_data/TD-TC-000631.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000631.step3.void-return.json](test_data/TD-TC-000631.step3.void-return.json) |
| TC-000632 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000632.step1.return.json](test_data/TD-TC-000632.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000632.step2.void-return.json](test_data/TD-TC-000632.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000632.step3.totals.json](test_data/TD-TC-000632.step3.totals.json) |
| TC-000633 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000633.step1.return.json](test_data/TD-TC-000633.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000633.step2.totals.json](test_data/TD-TC-000633.step2.totals.json) |
| TC-000634 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return on a  | Interaction (pairwise) | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Merchandise Return | [TD-TC-000634.json](test_data/TD-TC-000634.json) |
| TC-000635 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return (card | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000635.json](test_data/TD-TC-000635.json) |
| TC-000962 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return Varia | Single rule broken | Negative | needs_client_input | BR-149-1 p.149; BR-146-2 p.146; BR-147-1 p.147 | 1 |  | [TD-TC-000962.json](test_data/TD-TC-000962.json) |

### SC-ATL105-D1464CF1 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000801 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return  | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000801.json](test_data/TD-TC-000801.json) |
| TC-000964 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return  | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-179-1 p.179; p. | 1 |  | [TD-TC-000964.json](test_data/TD-TC-000964.json) |
| TC-000967 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return  | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-181-2 p.181; p. | 1 |  | [TD-TC-000967.json](test_data/TD-TC-000967.json) |

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
| TC-000636 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return → | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000636.json](test_data/TD-TC-000636.json) |
| TC-000637 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Single rule broken | Negative | requires_review | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000637.step1.return.json](test_data/TD-TC-000637.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000637.step2.void-return.json](test_data/TD-TC-000637.step2.void-return.json) |
| TC-000638 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000638.step1.return.json](test_data/TD-TC-000638.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000638.step2.void-return.json](test_data/TD-TC-000638.step2.void-return.json) |
| TC-000639 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000639.step1.return.json](test_data/TD-TC-000639.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000639.step2.void-return.json](test_data/TD-TC-000639.step2.void-return.json) |
| TC-000640 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000640.step1.return.json](test_data/TD-TC-000640.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000640.step2.void-return.json](test_data/TD-TC-000640.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000640.step3.void-return.json](test_data/TD-TC-000640.step3.void-return.json) |
| TC-000641 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000641.step1.return.json](test_data/TD-TC-000641.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000641.step2.void-return.json](test_data/TD-TC-000641.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000641.step3.totals.json](test_data/TD-TC-000641.step3.totals.json) |
| TC-000642 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000642.step1.return.json](test_data/TD-TC-000642.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000642.step2.totals.json](test_data/TD-TC-000642.step2.totals.json) |
| TC-000643 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return o | Interaction (pairwise) | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Merchandise Return | [TD-TC-000643.json](test_data/TD-TC-000643.json) |
| TC-000644 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return ( | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000644.json](test_data/TD-TC-000644.json) |

### SC-ATL105-DC794243 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000802 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Merchandise Ret | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000802.json](test_data/TD-TC-000802.json) |

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
| TC-000645 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return → appr | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000645.json](test_data/TD-TC-000645.json) |
| TC-000646 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Single rule broken | Negative | needs_client_input | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000646.step1.return.json](test_data/TD-TC-000646.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000646.step2.void-return.json](test_data/TD-TC-000646.step2.void-return.json) |
| TC-000647 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Single rule broken | Negative | needs_client_input | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000647.step1.return.json](test_data/TD-TC-000647.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000647.step2.void-return.json](test_data/TD-TC-000647.step2.void-return.json) |
| TC-000648 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Valid flow | Positive | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000648.step1.return.json](test_data/TD-TC-000648.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000648.step2.void-return.json](test_data/TD-TC-000648.step2.void-return.json) |
| TC-000649 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000649.step1.return.json](test_data/TD-TC-000649.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000649.step2.void-return.json](test_data/TD-TC-000649.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000649.step3.void-return.json](test_data/TD-TC-000649.step3.void-return.json) |
| TC-000650 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000650.step1.return.json](test_data/TD-TC-000650.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000650.step2.void-return.json](test_data/TD-TC-000650.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000650.step3.totals.json](test_data/TD-TC-000650.step3.totals.json) |
| TC-000651 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000651.step1.return.json](test_data/TD-TC-000651.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000651.step2.totals.json](test_data/TD-TC-000651.step2.totals.json) |
| TC-000652 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return (card  | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000652.json](test_data/TD-TC-000652.json) |

### SC-ATL105-B3DE8567 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Petroleum · attended POS
- Status: spec_implied; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000803 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Merchandise Return → | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000803.json](test_data/TD-TC-000803.json) |

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
| TC-000663 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return → appro | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000663.json](test_data/TD-TC-000663.json) |
| TC-000664 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Single rule broken | Negative | requires_review | p.546; p.546; p.102 | 1 | Merchandise Return | [TD-TC-000664.step1.return.json](test_data/TD-TC-000664.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000664.step2.void-return.json](test_data/TD-TC-000664.step2.void-return.json) |
| TC-000665 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Merchandise Return | [TD-TC-000665.step1.return.json](test_data/TD-TC-000665.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000665.step2.void-return.json](test_data/TD-TC-000665.step2.void-return.json) |
| TC-000666 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-000666.step1.return.json](test_data/TD-TC-000666.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000666.step2.void-return.json](test_data/TD-TC-000666.step2.void-return.json) |
| TC-000667 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-000667.step1.return.json](test_data/TD-TC-000667.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000667.step2.void-return.json](test_data/TD-TC-000667.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-000667.step3.void-return.json](test_data/TD-TC-000667.step3.void-return.json) |
| TC-000668 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-000668.step1.return.json](test_data/TD-TC-000668.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-000668.step2.void-return.json](test_data/TD-TC-000668.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000668.step3.totals.json](test_data/TD-TC-000668.step3.totals.json) |
| TC-000669 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then T | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-000669.step1.return.json](test_data/TD-TC-000669.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000669.step2.totals.json](test_data/TD-TC-000669.step2.totals.json) |
| TC-000670 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return (card i | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000670.json](test_data/TD-TC-000670.json) |

### SC-ATL105-DDF070B6 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000805 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Merchandise Return →  | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-000805.json](test_data/TD-TC-000805.json) |

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
| TC-000248 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture → approved | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000248.json](test_data/TD-TC-000248.json) |
| TC-000249 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Point | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000249.json](test_data/TD-TC-000249.json) |
| TC-000250 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Curre | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000250.json](test_data/TD-TC-000250.json) |
| TC-000251 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with User  | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000251.json](test_data/TD-TC-000251.json) |
| TC-000252 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Card  | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000252.json](test_data/TD-TC-000252.json) |
| TC-000253 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Point | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000253.json](test_data/TD-TC-000253.json) |
| TC-000254 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Fraud | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000254.json](test_data/TD-TC-000254.json) |
| TC-000255 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Count | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000255.json](test_data/TD-TC-000255.json) |
| TC-000256 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Cardh | Happy path | Positive | needs_client_input | p.511; p.83; p.162 | 1 | Purchase/Capture | [TD-TC-000256.json](test_data/TD-TC-000256.json) |
| TC-000257 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Real  | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-000257.json](test_data/TD-TC-000257.json) |
| TC-000258 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000258.json](test_data/TD-TC-000258.json) |
| TC-000259 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000259.json](test_data/TD-TC-000259.json) |
| TC-000260 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000260.json](test_data/TD-TC-000260.json) |
| TC-000261 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000261.json](test_data/TD-TC-000261.json) |
| TC-000262 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-6 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000262.json](test_data/TD-TC-000262.json) |
| TC-000263 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000263.json](test_data/TD-TC-000263.json) |
| TC-000264 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-42-2 p.42; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000264.json](test_data/TD-TC-000264.json) |
| TC-000265 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-142-3 p.142; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000265.json](test_data/TD-TC-000265.json) |
| TC-000266 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000266.json](test_data/TD-TC-000266.json) |
| TC-000267 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000267.json](test_data/TD-TC-000267.json) |
| TC-000268 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000268.json](test_data/TD-TC-000268.json) |
| TC-000269 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000269.json](test_data/TD-TC-000269.json) |
| TC-000270 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000270.json](test_data/TD-TC-000270.json) |
| TC-000271 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Single rule broken | Negative | requires_review | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000271.step1.purchase.json](test_data/TD-TC-000271.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000271.step2.reversal.json](test_data/TD-TC-000271.step2.reversal.json) |
| TC-000272 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000272.step1.purchase.json](test_data/TD-TC-000272.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000272.step2.reversal.json](test_data/TD-TC-000272.step2.reversal.json) |
| TC-000273 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Single rule broken | Negative | requires_review | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000273.step1.purchase.json](test_data/TD-TC-000273.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000273.step2.timeout-reversal.json](test_data/TD-TC-000273.step2.timeout-reversal.json) |
| TC-000274 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cof = | Decision table | Positive | needs_client_input | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-000274.json](test_data/TD-TC-000274.json) |
| TC-000275 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cvm = | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-000275.json](test_data/TD-TC-000275.json) |
| TC-000276 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with incre | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000276.json](test_data/TD-TC-000276.json) |
| TC-000277 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with incre | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000277.json](test_data/TD-TC-000277.json) |
| TC-000278 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Varia | Decision table | Positive | needs_client_input | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000278.json](test_data/TD-TC-000278.json) |
| TC-000279 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000279.step1.purchase.json](test_data/TD-TC-000279.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000279.step2.reversal.json](test_data/TD-TC-000279.step2.reversal.json) |
| TC-000280 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Valid flow | Positive | requires_review | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000280.step1.purchase.json](test_data/TD-TC-000280.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000280.step2.timeout-reversal.json](test_data/TD-TC-000280.step2.timeout-reversal.json) |
| TC-000281 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Valid flow | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000281.step1.purchase.json](test_data/TD-TC-000281.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000281.step2.void.json](test_data/TD-TC-000281.step2.void.json) |
| TC-000282 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000282.step1.purchase.json](test_data/TD-TC-000282.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000282.step2.split-tender.json](test_data/TD-TC-000282.step2.split-tender.json) |
| TC-000283 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Invalid state transition | Negative | requires_review | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000283.step1.purchase.json](test_data/TD-TC-000283.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000283.step2.timeout-reversal.json](test_data/TD-TC-000283.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000283.step3.timeout-reversal.json](test_data/TD-TC-000283.step3.timeout-reversal.json) |
| TC-000284 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (lost_befo | Invalid state transition | Negative | requires_review | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000284.step1.purchase.json](test_data/TD-TC-000284.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000284.step2.timeout-reversal.json](test_data/TD-TC-000284.step2.timeout-reversal.json) |
| TC-000285 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (late_resp | Fault / recovery | Positive | requires_review | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000285.step1.purchase.json](test_data/TD-TC-000285.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000285.step2.timeout-reversal.json](test_data/TD-TC-000285.step2.timeout-reversal.json) |
| TC-000286 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (late_resp | Fault / recovery | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000286.step1.purchase.json](test_data/TD-TC-000286.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000286.step2.timeout-reversal.json](test_data/TD-TC-000286.step2.timeout-reversal.json) |
| TC-000287 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Fault / recovery | Positive | requires_review | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000287.step1.purchase.json](test_data/TD-TC-000287.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000287.step2.timeout-reversal.json](test_data/TD-TC-000287.step2.timeout-reversal.json) |
| TC-000288 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Idempotency / duplicate / retry | Positive | requires_review | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000288.step1.purchase.json](test_data/TD-TC-000288.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000288.step2.timeout-reversal.json](test_data/TD-TC-000288.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000288.step3.timeout-reversal.json](test_data/TD-TC-000288.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000288.step4.timeout-reversal.json](test_data/TD-TC-000288.step4.timeout-reversal.json) |
| TC-000289 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000289.step1.purchase.json](test_data/TD-TC-000289.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000289.step2.reversal.json](test_data/TD-TC-000289.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000289.step3.totals.json](test_data/TD-TC-000289.step3.totals.json) |
| TC-000290 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Tota | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000290.step1.purchase.json](test_data/TD-TC-000290.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000290.step2.totals.json](test_data/TD-TC-000290.step2.totals.json) |
| TC-000291 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture on a POS t | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-50-5 p.50 | 1 | Purchase/Capture | [TD-TC-000291.json](test_data/TD-TC-000291.json) |
| TC-000292 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Real Time | Response / oracle check | Positive | needs_client_input | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000292.json](test_data/TD-TC-000292.json) |
| TC-000293 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transacti | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000293.json](test_data/TD-TC-000293.json) |
| TC-000294 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transacti | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-5 p.58; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000294.json](test_data/TD-TC-000294.json) |
| TC-000295 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transacti | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-70-7 p.70; BR-FISERV-EMV-GUIDE-70-8 p.70; p.511 | 1 | Purchase/Capture | [TD-TC-000295.json](test_data/TD-TC-000295.json) |
| TC-000296 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (card issu | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000296.json](test_data/TD-TC-000296.json) |
| TC-000297 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Me | Response / oracle check | Positive | needs_client_input | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000297.json](test_data/TD-TC-000297.json) |
| TC-000298 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending av | Response / oracle check | Positive | needs_client_input | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000298.json](test_data/TD-TC-000298.json) |
| TC-000299 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with AVS o | Response / oracle check | Positive | needs_client_input | p.643; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000299.json](test_data/TD-TC-000299.json) |
| TC-000963 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture EMV Reques | Single rule broken | Negative | requires_review | BR-162-5 p.162; BR-FISERV-EMV-GUIDE-58-11 p.58; p.511 | 1 |  | [TD-TC-000963.json](test_data/TD-TC-000963.json) |

### SC-ATL105-0B4F31B7 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000732 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000732.json](test_data/TD-TC-000732.json) |
| TC-000733 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000733.json](test_data/TD-TC-000733.json) |

### SC-ATL105-2FB57EC0 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000937 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Chip Fallback Purchase/Capture → appr | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000937.json](test_data/TD-TC-000937.json) |

### SC-ATL105-1CC160A6 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000944 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Chip Fallback Purchase/Capture → appr | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000944.json](test_data/TD-TC-000944.json) |

### SC-ATL105-00955B6A - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000777 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture → app | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000777.json](test_data/TD-TC-000777.json) |
| TC-000778 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture with  | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000778.json](test_data/TD-TC-000778.json) |
| TC-000779 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture, then | Invalid state transition | Negative | requires_review | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-000779.step1.purchase.json](test_data/TD-TC-000779.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000779.step2.reversal.json](test_data/TD-TC-000779.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-000779.step3.reversal.json](test_data/TD-TC-000779.step3.reversal.json) |
| TC-000780 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (Tran | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000780.json](test_data/TD-TC-000780.json) |
| TC-000781 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (card | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000781.json](test_data/TD-TC-000781.json) |

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
| TC-000158 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000158.json](test_data/TD-TC-000158.json) |
| TC-000159 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000159.json](test_data/TD-TC-000159.json) |
| TC-000160 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000160.json](test_data/TD-TC-000160.json) |
| TC-000161 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000161.json](test_data/TD-TC-000161.json) |
| TC-000162 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000162.json](test_data/TD-TC-000162.json) |
| TC-000163 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000163.json](test_data/TD-TC-000163.json) |
| TC-000164 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000164.json](test_data/TD-TC-000164.json) |
| TC-000165 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000165.json](test_data/TD-TC-000165.json) |
| TC-000166 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.511; p.83; p.86 | 1 | Purchase/Capture | [TD-TC-000166.json](test_data/TD-TC-000166.json) |
| TC-000167 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-000167.json](test_data/TD-TC-000167.json) |
| TC-000168 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000168.json](test_data/TD-TC-000168.json) |
| TC-000169 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000169.json](test_data/TD-TC-000169.json) |
| TC-000170 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000170.json](test_data/TD-TC-000170.json) |
| TC-000171 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000171.json](test_data/TD-TC-000171.json) |
| TC-000172 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000172.json](test_data/TD-TC-000172.json) |
| TC-000173 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000173.json](test_data/TD-TC-000173.json) |
| TC-000174 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000174.json](test_data/TD-TC-000174.json) |
| TC-000175 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | needs_client_input | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000175.step1.purchase.json](test_data/TD-TC-000175.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000175.step2.reversal.json](test_data/TD-TC-000175.step2.reversal.json) |
| TC-000176 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | needs_client_input | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000176.step1.purchase.json](test_data/TD-TC-000176.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000176.step2.reversal.json](test_data/TD-TC-000176.step2.reversal.json) |
| TC-000177 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | needs_client_input | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000177.step1.purchase.json](test_data/TD-TC-000177.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000177.step2.timeout-reversal.json](test_data/TD-TC-000177.step2.timeout-reversal.json) |
| TC-000178 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | needs_client_input | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-000178.json](test_data/TD-TC-000178.json) |
| TC-000179 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-000179.json](test_data/TD-TC-000179.json) |
| TC-000180 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000180.json](test_data/TD-TC-000180.json) |
| TC-000181 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000181.json](test_data/TD-TC-000181.json) |
| TC-000182 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | needs_client_input | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000182.json](test_data/TD-TC-000182.json) |
| TC-000183 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | blocked_verification | BR-81-3 p.81; BR-94-6 p.94; BR-95-3 p.95 | 1 | Purchase/Capture | [TD-TC-000183.json](test_data/TD-TC-000183.json) |
| TC-000184 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Valid flow | Positive | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000184.step1.purchase.json](test_data/TD-TC-000184.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000184.step2.reversal.json](test_data/TD-TC-000184.step2.reversal.json) |
| TC-000185 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000185.step1.purchase.json](test_data/TD-TC-000185.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000185.step2.timeout-reversal.json](test_data/TD-TC-000185.step2.timeout-reversal.json) |
| TC-000186 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000186.step1.purchase.json](test_data/TD-TC-000186.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000186.step2.void.json](test_data/TD-TC-000186.step2.void.json) |
| TC-000187 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000187.step1.purchase.json](test_data/TD-TC-000187.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000187.step2.split-tender.json](test_data/TD-TC-000187.step2.split-tender.json) |
| TC-000188 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000188.step1.purchase.json](test_data/TD-TC-000188.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000188.step2.timeout-reversal.json](test_data/TD-TC-000188.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000188.step3.timeout-reversal.json](test_data/TD-TC-000188.step3.timeout-reversal.json) |
| TC-000189 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000189.step1.purchase.json](test_data/TD-TC-000189.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000189.step2.timeout-reversal.json](test_data/TD-TC-000189.step2.timeout-reversal.json) |
| TC-000190 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Fault / recovery | Positive | needs_client_input | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000190.step1.purchase.json](test_data/TD-TC-000190.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000190.step2.timeout-reversal.json](test_data/TD-TC-000190.step2.timeout-reversal.json) |
| TC-000191 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Fault / recovery | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000191.step1.purchase.json](test_data/TD-TC-000191.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000191.step2.timeout-reversal.json](test_data/TD-TC-000191.step2.timeout-reversal.json) |
| TC-000192 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Fault / recovery | Positive | needs_client_input | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000192.step1.purchase.json](test_data/TD-TC-000192.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000192.step2.timeout-reversal.json](test_data/TD-TC-000192.step2.timeout-reversal.json) |
| TC-000193 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Idempotency / duplicate / retry | Positive | needs_client_input | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000193.step1.purchase.json](test_data/TD-TC-000193.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000193.step2.timeout-reversal.json](test_data/TD-TC-000193.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000193.step3.timeout-reversal.json](test_data/TD-TC-000193.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000193.step4.timeout-reversal.json](test_data/TD-TC-000193.step4.timeout-reversal.json) |
| TC-000194 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000194.step1.purchase.json](test_data/TD-TC-000194.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000194.step2.reversal.json](test_data/TD-TC-000194.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000194.step3.totals.json](test_data/TD-TC-000194.step3.totals.json) |
| TC-000195 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000195.step1.purchase.json](test_data/TD-TC-000195.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000195.step2.totals.json](test_data/TD-TC-000195.step2.totals.json) |
| TC-000196 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-50-5 p.50 | 1 | Purchase/Capture | [TD-TC-000196.json](test_data/TD-TC-000196.json) |
| TC-000197 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000197.json](test_data/TD-TC-000197.json) |
| TC-000198 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000198.json](test_data/TD-TC-000198.json) |
| TC-000199 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000199.json](test_data/TD-TC-000199.json) |
| TC-000200 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000200.json](test_data/TD-TC-000200.json) |
| TC-000201 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000201.json](test_data/TD-TC-000201.json) |

### SC-ATL105-DDB01831 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-354E7BE3 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000727 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purcha | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000727.json](test_data/TD-TC-000727.json) |
| TC-000728 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purcha | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000728.json](test_data/TD-TC-000728.json) |

### SC-ATL105-C679544C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-41B94458 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000924 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000924.json](test_data/TD-TC-000924.json) |
| TC-000925 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000925.step1.purchase.json](test_data/TD-TC-000925.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000925.step2.timeout-reversal.json](test_data/TD-TC-000925.step2.timeout-reversal.json) |
| TC-000926 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000926.step1.purchase.json](test_data/TD-TC-000926.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000926.step2.timeout-reversal.json](test_data/TD-TC-000926.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000926.step3.timeout-reversal.json](test_data/TD-TC-000926.step3.timeout-reversal.json) |
| TC-000927 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000927.step1.purchase.json](test_data/TD-TC-000927.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000927.step2.timeout-reversal.json](test_data/TD-TC-000927.step2.timeout-reversal.json) |

### SC-ATL105-71D768CE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000935 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Magnetic Stripe Purchase/ | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000935.json](test_data/TD-TC-000935.json) |

### SC-ATL105-7B5C5BF1 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000942 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Magnetic Stripe Purchase/ | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000942.json](test_data/TD-TC-000942.json) |

### SC-ATL105-000F41EE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000767 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000767.json](test_data/TD-TC-000767.json) |
| TC-000768 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000768.json](test_data/TD-TC-000768.json) |
| TC-000769 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-000769.step1.purchase.json](test_data/TD-TC-000769.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000769.step2.reversal.json](test_data/TD-TC-000769.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-000769.step3.reversal.json](test_data/TD-TC-000769.step3.reversal.json) |
| TC-000770 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000770.json](test_data/TD-TC-000770.json) |
| TC-000771 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000771.json](test_data/TD-TC-000771.json) |

### SC-ATL105-120C1A07 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000837 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000837.json](test_data/TD-TC-000837.json) |
| TC-000838 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000838.json](test_data/TD-TC-000838.json) |
| TC-000839 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000839.json](test_data/TD-TC-000839.json) |
| TC-000840 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000840.step1.purchase.json](test_data/TD-TC-000840.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000840.step2.timeout-reversal.json](test_data/TD-TC-000840.step2.timeout-reversal.json) |
| TC-000841 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000841.step1.purchase.json](test_data/TD-TC-000841.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000841.step2.timeout-reversal.json](test_data/TD-TC-000841.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000841.step3.timeout-reversal.json](test_data/TD-TC-000841.step3.timeout-reversal.json) |
| TC-000842 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000842.step1.purchase.json](test_data/TD-TC-000842.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000842.step2.timeout-reversal.json](test_data/TD-TC-000842.step2.timeout-reversal.json) |
| TC-000843 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000843.json](test_data/TD-TC-000843.json) |
| TC-000844 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000844.json](test_data/TD-TC-000844.json) |
| TC-000845 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000845.json](test_data/TD-TC-000845.json) |

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
| TC-000347 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000347.json](test_data/TD-TC-000347.json) |
| TC-000348 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000348.json](test_data/TD-TC-000348.json) |
| TC-000349 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000349.json](test_data/TD-TC-000349.json) |
| TC-000350 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000350.json](test_data/TD-TC-000350.json) |
| TC-000351 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000351.json](test_data/TD-TC-000351.json) |
| TC-000352 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000352.json](test_data/TD-TC-000352.json) |
| TC-000353 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000353.json](test_data/TD-TC-000353.json) |
| TC-000354 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000354.json](test_data/TD-TC-000354.json) |
| TC-000355 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.511; p.83; p.83 | 1 | Purchase/Capture | [TD-TC-000355.json](test_data/TD-TC-000355.json) |
| TC-000356 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-000356.json](test_data/TD-TC-000356.json) |
| TC-000357 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000357.json](test_data/TD-TC-000357.json) |
| TC-000358 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000358.json](test_data/TD-TC-000358.json) |
| TC-000359 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000359.json](test_data/TD-TC-000359.json) |
| TC-000360 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000360.json](test_data/TD-TC-000360.json) |
| TC-000361 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000361.json](test_data/TD-TC-000361.json) |
| TC-000362 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000362.json](test_data/TD-TC-000362.json) |
| TC-000363 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000363.json](test_data/TD-TC-000363.json) |
| TC-000364 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000364.step1.purchase.json](test_data/TD-TC-000364.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000364.step2.reversal.json](test_data/TD-TC-000364.step2.reversal.json) |
| TC-000365 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000365.step1.purchase.json](test_data/TD-TC-000365.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000365.step2.reversal.json](test_data/TD-TC-000365.step2.reversal.json) |
| TC-000366 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000366.step1.purchase.json](test_data/TD-TC-000366.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000366.step2.timeout-reversal.json](test_data/TD-TC-000366.step2.timeout-reversal.json) |
| TC-000367 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-000367.json](test_data/TD-TC-000367.json) |
| TC-000368 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-000368.json](test_data/TD-TC-000368.json) |
| TC-000369 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000369.json](test_data/TD-TC-000369.json) |
| TC-000370 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000370.json](test_data/TD-TC-000370.json) |
| TC-000371 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000371.json](test_data/TD-TC-000371.json) |
| TC-000372 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000372.step1.purchase.json](test_data/TD-TC-000372.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000372.step2.reversal.json](test_data/TD-TC-000372.step2.reversal.json) |
| TC-000373 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000373.step1.purchase.json](test_data/TD-TC-000373.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000373.step2.timeout-reversal.json](test_data/TD-TC-000373.step2.timeout-reversal.json) |
| TC-000374 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000374.step1.purchase.json](test_data/TD-TC-000374.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000374.step2.void.json](test_data/TD-TC-000374.step2.void.json) |
| TC-000375 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000375.step1.purchase.json](test_data/TD-TC-000375.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000375.step2.split-tender.json](test_data/TD-TC-000375.step2.split-tender.json) |
| TC-000376 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Invalid state transition | Negative | pending_sme | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000376.step1.purchase.json](test_data/TD-TC-000376.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000376.step2.timeout-reversal.json](test_data/TD-TC-000376.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000376.step3.timeout-reversal.json](test_data/TD-TC-000376.step3.timeout-reversal.json) |
| TC-000377 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Invalid state transition | Negative | pending_sme | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000377.step1.purchase.json](test_data/TD-TC-000377.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000377.step2.timeout-reversal.json](test_data/TD-TC-000377.step2.timeout-reversal.json) |
| TC-000378 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Fault / recovery | Positive | pending_sme | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000378.step1.purchase.json](test_data/TD-TC-000378.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000378.step2.timeout-reversal.json](test_data/TD-TC-000378.step2.timeout-reversal.json) |
| TC-000379 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Fault / recovery | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000379.step1.purchase.json](test_data/TD-TC-000379.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000379.step2.timeout-reversal.json](test_data/TD-TC-000379.step2.timeout-reversal.json) |
| TC-000380 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Fault / recovery | Positive | pending_sme | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000380.step1.purchase.json](test_data/TD-TC-000380.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000380.step2.timeout-reversal.json](test_data/TD-TC-000380.step2.timeout-reversal.json) |
| TC-000381 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Idempotency / duplicate / retry | Positive | pending_sme | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000381.step1.purchase.json](test_data/TD-TC-000381.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000381.step2.timeout-reversal.json](test_data/TD-TC-000381.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000381.step3.timeout-reversal.json](test_data/TD-TC-000381.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000381.step4.timeout-reversal.json](test_data/TD-TC-000381.step4.timeout-reversal.json) |
| TC-000382 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000382.step1.purchase.json](test_data/TD-TC-000382.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000382.step2.reversal.json](test_data/TD-TC-000382.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000382.step3.totals.json](test_data/TD-TC-000382.step3.totals.json) |
| TC-000383 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000383.step1.purchase.json](test_data/TD-TC-000383.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000383.step2.totals.json](test_data/TD-TC-000383.step2.totals.json) |
| TC-000384 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Interaction (pairwise) | Positive | pending_sme | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-50-5 p.50 | 1 | Purchase/Capture | [TD-TC-000384.json](test_data/TD-TC-000384.json) |
| TC-000385 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000385.json](test_data/TD-TC-000385.json) |
| TC-000386 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000386.json](test_data/TD-TC-000386.json) |
| TC-000387 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000387.json](test_data/TD-TC-000387.json) |
| TC-000388 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000388.json](test_data/TD-TC-000388.json) |
| TC-000389 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000389.json](test_data/TD-TC-000389.json) |

### SC-ATL105-ACA8835B - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-1DAA6F93 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000734 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000734.json](test_data/TD-TC-000734.json) |
| TC-000735 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000735.json](test_data/TD-TC-000735.json) |

### SC-ATL105-67352694 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-7C4EE598 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000928 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000928.json](test_data/TD-TC-000928.json) |
| TC-000929 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000929.step1.purchase.json](test_data/TD-TC-000929.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000929.step2.timeout-reversal.json](test_data/TD-TC-000929.step2.timeout-reversal.json) |
| TC-000930 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Invalid state transition | Negative | pending_sme | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000930.step1.purchase.json](test_data/TD-TC-000930.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000930.step2.timeout-reversal.json](test_data/TD-TC-000930.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000930.step3.timeout-reversal.json](test_data/TD-TC-000930.step3.timeout-reversal.json) |
| TC-000931 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Invalid state transition | Negative | pending_sme | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000931.step1.purchase.json](test_data/TD-TC-000931.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000931.step2.timeout-reversal.json](test_data/TD-TC-000931.step2.timeout-reversal.json) |

### SC-ATL105-C1EFED34 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000938 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Mobile Commerce Device Pu | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000938.json](test_data/TD-TC-000938.json) |

### SC-ATL105-EB7AFA8C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6211 (high risk security) · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000945 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Mobile Commerce Device Pu | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000945.json](test_data/TD-TC-000945.json) |

### SC-ATL105-6070CD4F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000782 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000782.json](test_data/TD-TC-000782.json) |
| TC-000783 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000783.json](test_data/TD-TC-000783.json) |
| TC-000784 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Invalid state transition | Negative | pending_sme | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-000784.step1.purchase.json](test_data/TD-TC-000784.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000784.step2.reversal.json](test_data/TD-TC-000784.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-000784.step3.reversal.json](test_data/TD-TC-000784.step3.reversal.json) |
| TC-000785 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000785.json](test_data/TD-TC-000785.json) |
| TC-000786 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000786.json](test_data/TD-TC-000786.json) |

### SC-ATL105-74D4DC2F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine)
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000846 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000846.json](test_data/TD-TC-000846.json) |
| TC-000847 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000847.json](test_data/TD-TC-000847.json) |
| TC-000848 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Decision table | Positive | pending_sme | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000848.json](test_data/TD-TC-000848.json) |
| TC-000849 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000849.step1.purchase.json](test_data/TD-TC-000849.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000849.step2.timeout-reversal.json](test_data/TD-TC-000849.step2.timeout-reversal.json) |
| TC-000850 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Invalid state transition | Negative | pending_sme | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000850.step1.purchase.json](test_data/TD-TC-000850.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000850.step2.timeout-reversal.json](test_data/TD-TC-000850.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000850.step3.timeout-reversal.json](test_data/TD-TC-000850.step3.timeout-reversal.json) |
| TC-000851 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Invalid state transition | Negative | pending_sme | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000851.step1.purchase.json](test_data/TD-TC-000851.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000851.step2.timeout-reversal.json](test_data/TD-TC-000851.step2.timeout-reversal.json) |
| TC-000852 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000852.json](test_data/TD-TC-000852.json) |
| TC-000853 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-62-6 p.62; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000853.json](test_data/TD-TC-000853.json) |
| TC-000854 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000854.json](test_data/TD-TC-000854.json) |

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
| TC-000300 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Happy path | Positive | requires_review | p.561; BR-85-4 p.85; BR-561-1 p.561 | 1 | Purchase/Capture | [TD-TC-000300.step1.purchase.json](test_data/TD-TC-000300.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000300.step2.reversal.json](test_data/TD-TC-000300.step2.reversal.json) |
| TC-000301 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → app | Happy path | Positive | needs_client_input | BR-83-1 p.83; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000301.json](test_data/TD-TC-000301.json) |
| TC-000302 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → app | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000302.json](test_data/TD-TC-000302.json) |
| TC-000303 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000303.json](test_data/TD-TC-000303.json) |
| TC-000304 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000304.json](test_data/TD-TC-000304.json) |
| TC-000305 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000305.json](test_data/TD-TC-000305.json) |
| TC-000306 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000306.json](test_data/TD-TC-000306.json) |
| TC-000307 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000307.json](test_data/TD-TC-000307.json) |
| TC-000308 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000308.json](test_data/TD-TC-000308.json) |
| TC-000309 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000309.json](test_data/TD-TC-000309.json) |
| TC-000310 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.511; p.83; p.638 | 1 | Purchase/Capture | [TD-TC-000310.json](test_data/TD-TC-000310.json) |
| TC-000311 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | BR-728-2 p.728; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000311.json](test_data/TD-TC-000311.json) |
| TC-000312 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000312.json](test_data/TD-TC-000312.json) |
| TC-000313 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000313.json](test_data/TD-TC-000313.json) |
| TC-000314 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000314.json](test_data/TD-TC-000314.json) |
| TC-000315 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000315.json](test_data/TD-TC-000315.json) |
| TC-000316 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000316.json](test_data/TD-TC-000316.json) |
| TC-000317 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000317.json](test_data/TD-TC-000317.json) |
| TC-000318 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000318.json](test_data/TD-TC-000318.json) |
| TC-000319 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Single rule broken | Negative | requires_review | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000319.step1.purchase.json](test_data/TD-TC-000319.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000319.step2.reversal.json](test_data/TD-TC-000319.step2.reversal.json) |
| TC-000320 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000320.step1.purchase.json](test_data/TD-TC-000320.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000320.step2.reversal.json](test_data/TD-TC-000320.step2.reversal.json) |
| TC-000321 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Single rule broken | Negative | requires_review | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000321.step1.purchase.json](test_data/TD-TC-000321.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000321.step2.timeout-reversal.json](test_data/TD-TC-000321.step2.timeout-reversal.json) |
| TC-000322 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000322.json](test_data/TD-TC-000322.json) |
| TC-000323 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-61-2 p.61; BR-559-1 p.559; BR-713-12 p.713 | 1 | Purchase/Capture | [TD-TC-000323.json](test_data/TD-TC-000323.json) |
| TC-000324 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000324.json](test_data/TD-TC-000324.json) |
| TC-000325 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-590-3 p.590; BR-594-4 p.594; p.511 | 1 | Purchase/Capture | [TD-TC-000325.json](test_data/TD-TC-000325.json) |
| TC-000326 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-590-3 p.590; BR-594-4 p.594; p.511 | 1 | Purchase/Capture | [TD-TC-000326.json](test_data/TD-TC-000326.json) |
| TC-000327 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000327.step1.purchase.json](test_data/TD-TC-000327.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000327.step2.reversal.json](test_data/TD-TC-000327.step2.reversal.json) |
| TC-000328 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Valid flow | Positive | requires_review | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000328.step1.purchase.json](test_data/TD-TC-000328.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000328.step2.timeout-reversal.json](test_data/TD-TC-000328.step2.timeout-reversal.json) |
| TC-000329 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Valid flow | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000329.step1.purchase.json](test_data/TD-TC-000329.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000329.step2.void.json](test_data/TD-TC-000329.step2.void.json) |
| TC-000330 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000330.step1.purchase.json](test_data/TD-TC-000330.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000330.step2.split-tender.json](test_data/TD-TC-000330.step2.split-tender.json) |
| TC-000331 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Invalid state transition | Negative | requires_review | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000331.step1.purchase.json](test_data/TD-TC-000331.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000331.step2.timeout-reversal.json](test_data/TD-TC-000331.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000331.step3.timeout-reversal.json](test_data/TD-TC-000331.step3.timeout-reversal.json) |
| TC-000332 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (lost | Invalid state transition | Negative | requires_review | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000332.step1.purchase.json](test_data/TD-TC-000332.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000332.step2.timeout-reversal.json](test_data/TD-TC-000332.step2.timeout-reversal.json) |
| TC-000333 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (late | Fault / recovery | Positive | requires_review | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000333.step1.purchase.json](test_data/TD-TC-000333.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000333.step2.timeout-reversal.json](test_data/TD-TC-000333.step2.timeout-reversal.json) |
| TC-000334 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (late | Fault / recovery | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000334.step1.purchase.json](test_data/TD-TC-000334.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000334.step2.timeout-reversal.json](test_data/TD-TC-000334.step2.timeout-reversal.json) |
| TC-000335 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Fault / recovery | Positive | requires_review | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000335.step1.purchase.json](test_data/TD-TC-000335.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000335.step2.timeout-reversal.json](test_data/TD-TC-000335.step2.timeout-reversal.json) |
| TC-000336 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Idempotency / duplicate / retry | Positive | requires_review | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000336.step1.purchase.json](test_data/TD-TC-000336.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000336.step2.timeout-reversal.json](test_data/TD-TC-000336.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000336.step3.timeout-reversal.json](test_data/TD-TC-000336.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000336.step4.timeout-reversal.json](test_data/TD-TC-000336.step4.timeout-reversal.json) |
| TC-000337 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000337.step1.purchase.json](test_data/TD-TC-000337.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000337.step2.reversal.json](test_data/TD-TC-000337.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000337.step3.totals.json](test_data/TD-TC-000337.step3.totals.json) |
| TC-000338 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000338.step1.purchase.json](test_data/TD-TC-000338.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000338.step2.totals.json](test_data/TD-TC-000338.step2.totals.json) |
| TC-000339 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Real | Response / oracle check | Positive | needs_client_input | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000339.json](test_data/TD-TC-000339.json) |
| TC-000340 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Tran | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000340.json](test_data/TD-TC-000340.json) |
| TC-000341 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Tran | Response / oracle check | Positive | needs_client_input | BR-713-9 p.713; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000341.json](test_data/TD-TC-000341.json) |
| TC-000342 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (card | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000342.json](test_data/TD-TC-000342.json) |
| TC-000343 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | needs_client_input | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000343.json](test_data/TD-TC-000343.json) |
| TC-000344 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | needs_client_input | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000344.json](test_data/TD-TC-000344.json) |
| TC-000345 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Response / oracle check | Positive | needs_client_input | p.648; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000345.json](test_data/TD-TC-000345.json) |
| TC-000346 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Response / oracle check | Positive | needs_client_input | p.648; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000346.json](test_data/TD-TC-000346.json) |

### SC-ATL105-93D7F6A8 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000001 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture → appro | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000001.json](test_data/TD-TC-000001.json) |
| TC-000002 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Po | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000002.json](test_data/TD-TC-000002.json) |
| TC-000003 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Cu | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000003.json](test_data/TD-TC-000003.json) |
| TC-000004 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Us | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000004.json](test_data/TD-TC-000004.json) |
| TC-000005 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ca | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000005.json](test_data/TD-TC-000005.json) |
| TC-000006 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Po | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000006.json](test_data/TD-TC-000006.json) |
| TC-000007 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Fr | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000007.json](test_data/TD-TC-000007.json) |
| TC-000008 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Fi | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-000008.json](test_data/TD-TC-000008.json) |
| TC-000009 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Co | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000009.json](test_data/TD-TC-000009.json) |
| TC-000010 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ca | Happy path | Positive | needs_client_input | p.511; p.83; p.160 | 1 | Purchase/Capture | [TD-TC-000010.json](test_data/TD-TC-000010.json) |
| TC-000011 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Re | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-000011.json](test_data/TD-TC-000011.json) |
| TC-000012 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000012.json](test_data/TD-TC-000012.json) |
| TC-000013 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000013.json](test_data/TD-TC-000013.json) |
| TC-000014 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-6 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000014.json](test_data/TD-TC-000014.json) |
| TC-000015 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000015.json](test_data/TD-TC-000015.json) |
| TC-000016 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000016.json](test_data/TD-TC-000016.json) |
| TC-000017 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000017.json](test_data/TD-TC-000017.json) |
| TC-000018 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000018.json](test_data/TD-TC-000018.json) |
| TC-000019 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000019.json](test_data/TD-TC-000019.json) |
| TC-000020 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Single rule broken | Negative | needs_client_input | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000020.step1.purchase.json](test_data/TD-TC-000020.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000020.step2.reversal.json](test_data/TD-TC-000020.step2.reversal.json) |
| TC-000021 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Single rule broken | Negative | needs_client_input | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000021.step1.purchase.json](test_data/TD-TC-000021.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000021.step2.reversal.json](test_data/TD-TC-000021.step2.reversal.json) |
| TC-000022 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Single rule broken | Negative | needs_client_input | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000022.step1.purchase.json](test_data/TD-TC-000022.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000022.step2.timeout-reversal.json](test_data/TD-TC-000022.step2.timeout-reversal.json) |
| TC-000023 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with co | Decision table | Positive | needs_client_input | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-000023.json](test_data/TD-TC-000023.json) |
| TC-000024 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cv | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-000024.json](test_data/TD-TC-000024.json) |
| TC-000025 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with in | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000025.json](test_data/TD-TC-000025.json) |
| TC-000026 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with in | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000026.json](test_data/TD-TC-000026.json) |
| TC-000027 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Va | Decision table | Positive | needs_client_input | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000027.json](test_data/TD-TC-000027.json) |
| TC-000028 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Valid flow | Positive | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000028.step1.purchase.json](test_data/TD-TC-000028.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000028.step2.reversal.json](test_data/TD-TC-000028.step2.reversal.json) |
| TC-000029 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000029.step1.purchase.json](test_data/TD-TC-000029.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000029.step2.timeout-reversal.json](test_data/TD-TC-000029.step2.timeout-reversal.json) |
| TC-000030 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000030.step1.purchase.json](test_data/TD-TC-000030.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000030.step2.void.json](test_data/TD-TC-000030.step2.void.json) |
| TC-000031 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000031.step1.purchase.json](test_data/TD-TC-000031.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000031.step2.split-tender.json](test_data/TD-TC-000031.step2.split-tender.json) |
| TC-000032 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000032.step1.purchase.json](test_data/TD-TC-000032.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000032.step2.timeout-reversal.json](test_data/TD-TC-000032.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000032.step3.timeout-reversal.json](test_data/TD-TC-000032.step3.timeout-reversal.json) |
| TC-000033 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (lost_b | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000033.step1.purchase.json](test_data/TD-TC-000033.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000033.step2.timeout-reversal.json](test_data/TD-TC-000033.step2.timeout-reversal.json) |
| TC-000034 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (late_r | Fault / recovery | Positive | needs_client_input | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000034.step1.purchase.json](test_data/TD-TC-000034.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000034.step2.timeout-reversal.json](test_data/TD-TC-000034.step2.timeout-reversal.json) |
| TC-000035 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (late_r | Fault / recovery | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000035.step1.purchase.json](test_data/TD-TC-000035.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000035.step2.timeout-reversal.json](test_data/TD-TC-000035.step2.timeout-reversal.json) |
| TC-000036 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Fault / recovery | Positive | needs_client_input | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000036.step1.purchase.json](test_data/TD-TC-000036.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000036.step2.timeout-reversal.json](test_data/TD-TC-000036.step2.timeout-reversal.json) |
| TC-000037 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Idempotency / duplicate / retry | Positive | needs_client_input | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000037.step1.purchase.json](test_data/TD-TC-000037.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000037.step2.timeout-reversal.json](test_data/TD-TC-000037.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000037.step3.timeout-reversal.json](test_data/TD-TC-000037.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000037.step4.timeout-reversal.json](test_data/TD-TC-000037.step4.timeout-reversal.json) |
| TC-000038 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000038.step1.purchase.json](test_data/TD-TC-000038.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000038.step2.reversal.json](test_data/TD-TC-000038.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000038.step3.totals.json](test_data/TD-TC-000038.step3.totals.json) |
| TC-000039 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000039.step1.purchase.json](test_data/TD-TC-000039.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000039.step2.totals.json](test_data/TD-TC-000039.step2.totals.json) |
| TC-000040 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture on a PO | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-50-5 p.50 | 1 | Purchase/Capture | [TD-TC-000040.json](test_data/TD-TC-000040.json) |
| TC-000041 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Re | Response / oracle check | Positive | needs_client_input | BR-209-3 p.209; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000041.json](test_data/TD-TC-000041.json) |
| TC-000042 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Re | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56; BR-FISERV-EMV-GUIDE-73-6 p.73 | 1 | Purchase/Capture | [TD-TC-000042.json](test_data/TD-TC-000042.json) |
| TC-000043 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Re | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-11 p.58; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000043.json](test_data/TD-TC-000043.json) |
| TC-000044 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Re | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-8 p.58; BR-FISERV-EMV-GUIDE-63-4 p.63; BR-FISERV-EMV-GUIDE-88-2 p.88 | 1 | Purchase/Capture | [TD-TC-000044.json](test_data/TD-TC-000044.json) |
| TC-000045 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Real T | Response / oracle check | Positive | needs_client_input | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000045.json](test_data/TD-TC-000045.json) |
| TC-000046 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transa | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-141-4 p.141; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000046.json](test_data/TD-TC-000046.json) |
| TC-000047 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transa | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-48-2 p.48; BR-FISERV-EMV-GUIDE-54-2 p.54; BR-FISERV-EMV-GUIDE-54-3 p.54 | 1 | Purchase/Capture | [TD-TC-000047.json](test_data/TD-TC-000047.json) |
| TC-000048 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transa | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000048.json](test_data/TD-TC-000048.json) |
| TC-000049 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transa | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-5 p.58; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000049.json](test_data/TD-TC-000049.json) |
| TC-000050 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (card i | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000050.json](test_data/TD-TC-000050.json) |
| TC-000051 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending | Response / oracle check | Positive | needs_client_input | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000051.json](test_data/TD-TC-000051.json) |
| TC-000052 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending | Response / oracle check | Positive | needs_client_input | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000052.json](test_data/TD-TC-000052.json) |
| TC-000053 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with AV | Response / oracle check | Positive | needs_client_input | p.643; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000053.json](test_data/TD-TC-000053.json) |
| TC-000950 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture NFC Pay | Single rule broken | Negative | requires_review | BR-688-2 p.688; BR-156-7 p.156; BR-688-2 p.688 | 1 |  | [TD-TC-000950.json](test_data/TD-TC-000950.json) |
| TC-000956 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Require | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-55-12 p.55; BR-FISERV-EMV-GUIDE-55-13 p.55; BR-FISERV-EMV-GUIDE-55-14 p.55 | 1 |  | [TD-TC-000956.json](test_data/TD-TC-000956.json) |
| TC-000957 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Chi | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-55-12 p.55; BR-FISERV-EMV-GUIDE-55-13 p.55; BR-FISERV-EMV-GUIDE-55-14 p.55 | 1 |  | [TD-TC-000957.json](test_data/TD-TC-000957.json) |
| TC-000958 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Req | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-77-5 p.77; BR-FISERV-EMV-GUIDE-96-4 p.96; BR-FISERV-EMV-GUIDE-37-1 p.37 | 1 |  | [TD-TC-000958.json](test_data/TD-TC-000958.json) |
| TC-000960 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Variabl | Single rule broken | Negative | needs_client_input | BR-145-2 p.145; BR-148-1 p.148; BR-152-1 p.152 | 1 |  | [TD-TC-000960.json](test_data/TD-TC-000960.json) |
| TC-000969 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Car | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-71-6 p.71; BR-FISERV-EMV-GUIDE-72-4 p.72; BR-FISERV-EMV-GUIDE-72-6 p.72 | 1 |  | [TD-TC-000969.json](test_data/TD-TC-000969.json) |

### SC-ATL105-7C23049D - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-EA8371EB - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000721 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000721.json](test_data/TD-TC-000721.json) |
| TC-000722 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000722.json](test_data/TD-TC-000722.json) |

### SC-ATL105-78392C52 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-B063BCE3 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000908 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000908.json](test_data/TD-TC-000908.json) |
| TC-000909 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-000909.json](test_data/TD-TC-000909.json) |
| TC-000910 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture (no | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000910.step1.purchase.json](test_data/TD-TC-000910.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000910.step2.timeout-reversal.json](test_data/TD-TC-000910.step2.timeout-reversal.json) |
| TC-000911 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture (no | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000911.step1.purchase.json](test_data/TD-TC-000911.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000911.step2.timeout-reversal.json](test_data/TD-TC-000911.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000911.step3.timeout-reversal.json](test_data/TD-TC-000911.step3.timeout-reversal.json) |
| TC-000912 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture (lo | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000912.step1.purchase.json](test_data/TD-TC-000912.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000912.step2.timeout-reversal.json](test_data/TD-TC-000912.step2.timeout-reversal.json) |
| TC-000913 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture on  | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Purchase/Capture | [TD-TC-000913.json](test_data/TD-TC-000913.json) |

### SC-ATL105-5BF8E494 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000932 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contact Chip Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000932.json](test_data/TD-TC-000932.json) |

### SC-ATL105-1768C7CF - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000939 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contact Chip Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000939.json](test_data/TD-TC-000939.json) |

### SC-ATL105-B5496FB5 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000750 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture →  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000750.json](test_data/TD-TC-000750.json) |
| TC-000751 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture wi | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000751.json](test_data/TD-TC-000751.json) |
| TC-000752 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture, t | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-000752.step1.purchase.json](test_data/TD-TC-000752.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000752.step2.reversal.json](test_data/TD-TC-000752.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-000752.step3.reversal.json](test_data/TD-TC-000752.step3.reversal.json) |
| TC-000753 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (E | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-8 p.58; BR-FISERV-EMV-GUIDE-63-4 p.63; BR-FISERV-EMV-GUIDE-88-2 p.88 | 1 | Purchase/Capture | [TD-TC-000753.json](test_data/TD-TC-000753.json) |
| TC-000754 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (T | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000754.json](test_data/TD-TC-000754.json) |
| TC-000755 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000755.json](test_data/TD-TC-000755.json) |
| TC-000954 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Pr | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-135-1 p.135; BR-BUYPASS-PIPS-131-4 p.131; p. | 1 |  | [TD-TC-000954.json](test_data/TD-TC-000954.json) |
| TC-000968 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Ad | Single rule broken | Boundary | needs_client_input | BR-BUYPASS-PIPS-186-1 p.186; p. | 1 |  | [TD-TC-000968.json](test_data/TD-TC-000968.json) |

### SC-ATL105-BF3D1777 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000808 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture →  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000808.json](test_data/TD-TC-000808.json) |
| TC-000809 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture wi | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000809.json](test_data/TD-TC-000809.json) |
| TC-000810 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture wi | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000810.json](test_data/TD-TC-000810.json) |
| TC-000811 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (n | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000811.step1.purchase.json](test_data/TD-TC-000811.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000811.step2.timeout-reversal.json](test_data/TD-TC-000811.step2.timeout-reversal.json) |
| TC-000812 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (n | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000812.step1.purchase.json](test_data/TD-TC-000812.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000812.step2.timeout-reversal.json](test_data/TD-TC-000812.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000812.step3.timeout-reversal.json](test_data/TD-TC-000812.step3.timeout-reversal.json) |
| TC-000813 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (l | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000813.step1.purchase.json](test_data/TD-TC-000813.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000813.step2.timeout-reversal.json](test_data/TD-TC-000813.step2.timeout-reversal.json) |
| TC-000814 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (E | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-8 p.58; BR-FISERV-EMV-GUIDE-63-4 p.63; BR-FISERV-EMV-GUIDE-88-2 p.88 | 1 | Purchase/Capture | [TD-TC-000814.json](test_data/TD-TC-000814.json) |
| TC-000815 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (T | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000815.json](test_data/TD-TC-000815.json) |
| TC-000816 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (T | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000816.json](test_data/TD-TC-000816.json) |
| TC-000817 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000817.json](test_data/TD-TC-000817.json) |
| TC-000951 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture Pr | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-25-1 p.25; p. | 1 |  | [TD-TC-000951.json](test_data/TD-TC-000951.json) |

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
| TC-000054 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000054.json](test_data/TD-TC-000054.json) |
| TC-000055 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000055.json](test_data/TD-TC-000055.json) |
| TC-000056 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000056.json](test_data/TD-TC-000056.json) |
| TC-000057 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000057.json](test_data/TD-TC-000057.json) |
| TC-000058 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000058.json](test_data/TD-TC-000058.json) |
| TC-000059 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000059.json](test_data/TD-TC-000059.json) |
| TC-000060 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000060.json](test_data/TD-TC-000060.json) |
| TC-000061 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-000061.json](test_data/TD-TC-000061.json) |
| TC-000062 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000062.json](test_data/TD-TC-000062.json) |
| TC-000063 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.511; p.83; p.160 | 1 | Purchase/Capture | [TD-TC-000063.json](test_data/TD-TC-000063.json) |
| TC-000064 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-000064.json](test_data/TD-TC-000064.json) |
| TC-000065 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000065.json](test_data/TD-TC-000065.json) |
| TC-000066 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000066.json](test_data/TD-TC-000066.json) |
| TC-000067 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-6 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000067.json](test_data/TD-TC-000067.json) |
| TC-000068 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000068.json](test_data/TD-TC-000068.json) |
| TC-000069 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000069.json](test_data/TD-TC-000069.json) |
| TC-000070 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000070.json](test_data/TD-TC-000070.json) |
| TC-000071 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000071.json](test_data/TD-TC-000071.json) |
| TC-000072 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000072.json](test_data/TD-TC-000072.json) |
| TC-000073 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Single rule broken | Negative | needs_client_input | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000073.step1.purchase.json](test_data/TD-TC-000073.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000073.step2.reversal.json](test_data/TD-TC-000073.step2.reversal.json) |
| TC-000074 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Single rule broken | Negative | needs_client_input | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000074.step1.purchase.json](test_data/TD-TC-000074.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000074.step2.reversal.json](test_data/TD-TC-000074.step2.reversal.json) |
| TC-000075 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Single rule broken | Negative | needs_client_input | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000075.step1.purchase.json](test_data/TD-TC-000075.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000075.step2.timeout-reversal.json](test_data/TD-TC-000075.step2.timeout-reversal.json) |
| TC-000076 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-000076.json](test_data/TD-TC-000076.json) |
| TC-000077 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-000077.json](test_data/TD-TC-000077.json) |
| TC-000078 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-000078.json](test_data/TD-TC-000078.json) |
| TC-000079 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000079.json](test_data/TD-TC-000079.json) |
| TC-000080 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000080.json](test_data/TD-TC-000080.json) |
| TC-000081 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000081.json](test_data/TD-TC-000081.json) |
| TC-000082 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Valid flow | Positive | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000082.step1.purchase.json](test_data/TD-TC-000082.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000082.step2.reversal.json](test_data/TD-TC-000082.step2.reversal.json) |
| TC-000083 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000083.step1.purchase.json](test_data/TD-TC-000083.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000083.step2.timeout-reversal.json](test_data/TD-TC-000083.step2.timeout-reversal.json) |
| TC-000084 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000084.step1.purchase.json](test_data/TD-TC-000084.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000084.step2.void.json](test_data/TD-TC-000084.step2.void.json) |
| TC-000085 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000085.step1.purchase.json](test_data/TD-TC-000085.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000085.step2.split-tender.json](test_data/TD-TC-000085.step2.split-tender.json) |
| TC-000086 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000086.step1.purchase.json](test_data/TD-TC-000086.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000086.step2.timeout-reversal.json](test_data/TD-TC-000086.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000086.step3.timeout-reversal.json](test_data/TD-TC-000086.step3.timeout-reversal.json) |
| TC-000087 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (lo | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000087.step1.purchase.json](test_data/TD-TC-000087.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000087.step2.timeout-reversal.json](test_data/TD-TC-000087.step2.timeout-reversal.json) |
| TC-000088 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (la | Fault / recovery | Positive | needs_client_input | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000088.step1.purchase.json](test_data/TD-TC-000088.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000088.step2.timeout-reversal.json](test_data/TD-TC-000088.step2.timeout-reversal.json) |
| TC-000089 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (la | Fault / recovery | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000089.step1.purchase.json](test_data/TD-TC-000089.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000089.step2.timeout-reversal.json](test_data/TD-TC-000089.step2.timeout-reversal.json) |
| TC-000090 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Fault / recovery | Positive | needs_client_input | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000090.step1.purchase.json](test_data/TD-TC-000090.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000090.step2.timeout-reversal.json](test_data/TD-TC-000090.step2.timeout-reversal.json) |
| TC-000091 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Idempotency / duplicate / retry | Positive | needs_client_input | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000091.step1.purchase.json](test_data/TD-TC-000091.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000091.step2.timeout-reversal.json](test_data/TD-TC-000091.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000091.step3.timeout-reversal.json](test_data/TD-TC-000091.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000091.step4.timeout-reversal.json](test_data/TD-TC-000091.step4.timeout-reversal.json) |
| TC-000092 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000092.step1.purchase.json](test_data/TD-TC-000092.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000092.step2.reversal.json](test_data/TD-TC-000092.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000092.step3.totals.json](test_data/TD-TC-000092.step3.totals.json) |
| TC-000093 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000093.step1.purchase.json](test_data/TD-TC-000093.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000093.step2.totals.json](test_data/TD-TC-000093.step2.totals.json) |
| TC-000094 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EM | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56; BR-FISERV-EMV-GUIDE-73-6 p.73 | 1 | Purchase/Capture | [TD-TC-000094.json](test_data/TD-TC-000094.json) |
| TC-000095 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EM | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-11 p.58; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000095.json](test_data/TD-TC-000095.json) |
| TC-000096 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EM | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-8 p.58; BR-FISERV-EMV-GUIDE-63-4 p.63; BR-FISERV-EMV-GUIDE-88-2 p.88 | 1 | Purchase/Capture | [TD-TC-000096.json](test_data/TD-TC-000096.json) |
| TC-000097 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EM | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-63-7 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000097.json](test_data/TD-TC-000097.json) |
| TC-000098 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EM | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-63-8 p.63; BR-FISERV-EMV-GUIDE-65-1 p.65; p.511 | 1 | Purchase/Capture | [TD-TC-000098.json](test_data/TD-TC-000098.json) |
| TC-000099 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Re | Response / oracle check | Positive | needs_client_input | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000099.json](test_data/TD-TC-000099.json) |
| TC-000100 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-1 p.138; BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138 | 1 | Purchase/Capture | [TD-TC-000100.json](test_data/TD-TC-000100.json) |
| TC-000101 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-48-2 p.48; BR-FISERV-EMV-GUIDE-54-2 p.54; BR-FISERV-EMV-GUIDE-54-3 p.54 | 1 | Purchase/Capture | [TD-TC-000101.json](test_data/TD-TC-000101.json) |
| TC-000102 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000102.json](test_data/TD-TC-000102.json) |
| TC-000103 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-5 p.58; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000103.json](test_data/TD-TC-000103.json) |
| TC-000104 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000104.json](test_data/TD-TC-000104.json) |
| TC-000105 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sen | Response / oracle check | Positive | needs_client_input | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000105.json](test_data/TD-TC-000105.json) |
| TC-000106 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sen | Response / oracle check | Positive | needs_client_input | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000106.json](test_data/TD-TC-000106.json) |
| TC-000107 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Response / oracle check | Positive | needs_client_input | p.643; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000107.json](test_data/TD-TC-000107.json) |
| TC-000959 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture CA  | Single rule broken | Boundary | needs_client_input | BR-FISERV-EMV-GUIDE-94-4 p.94; p. | 1 |  | [TD-TC-000959.json](test_data/TD-TC-000959.json) |
| TC-000970 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture Req | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-74-9 p.74; p. | 1 |  | [TD-TC-000970.json](test_data/TD-TC-000970.json) |

### SC-ATL105-D75CF311 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-2B9A3CD9 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000723 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capt | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000723.json](test_data/TD-TC-000723.json) |
| TC-000724 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capt | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000724.json](test_data/TD-TC-000724.json) |

### SC-ATL105-948B2767 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-87D1178C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000914 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000914.json](test_data/TD-TC-000914.json) |
| TC-000915 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-000915.json](test_data/TD-TC-000915.json) |
| TC-000916 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000916.step1.purchase.json](test_data/TD-TC-000916.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000916.step2.timeout-reversal.json](test_data/TD-TC-000916.step2.timeout-reversal.json) |
| TC-000917 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000917.step1.purchase.json](test_data/TD-TC-000917.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000917.step2.timeout-reversal.json](test_data/TD-TC-000917.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000917.step3.timeout-reversal.json](test_data/TD-TC-000917.step3.timeout-reversal.json) |
| TC-000918 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000918.step1.purchase.json](test_data/TD-TC-000918.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000918.step2.timeout-reversal.json](test_data/TD-TC-000918.step2.timeout-reversal.json) |
| TC-000919 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Purchase/Capture | [TD-TC-000919.json](test_data/TD-TC-000919.json) |

### SC-ATL105-A94BEA8D - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000933 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000933.json](test_data/TD-TC-000933.json) |

### SC-ATL105-881273CB - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000940 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000940.json](test_data/TD-TC-000940.json) |

### SC-ATL105-B4E6A1C1 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000756 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000756.json](test_data/TD-TC-000756.json) |
| TC-000757 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000757.json](test_data/TD-TC-000757.json) |
| TC-000758 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-000758.step1.purchase.json](test_data/TD-TC-000758.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000758.step2.reversal.json](test_data/TD-TC-000758.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-000758.step3.reversal.json](test_data/TD-TC-000758.step3.reversal.json) |
| TC-000759 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-8 p.58; BR-FISERV-EMV-GUIDE-63-4 p.63; BR-FISERV-EMV-GUIDE-88-2 p.88 | 1 | Purchase/Capture | [TD-TC-000759.json](test_data/TD-TC-000759.json) |
| TC-000760 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000760.json](test_data/TD-TC-000760.json) |
| TC-000761 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000761.json](test_data/TD-TC-000761.json) |
| TC-000965 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-181-2 p.181; p. | 1 |  | [TD-TC-000965.json](test_data/TD-TC-000965.json) |
| TC-000971 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-173-2 p.173; p. | 1 |  | [TD-TC-000971.json](test_data/TD-TC-000971.json) |

### SC-ATL105-D4408D33 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000818 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000818.json](test_data/TD-TC-000818.json) |
| TC-000819 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000819.json](test_data/TD-TC-000819.json) |
| TC-000820 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000820.json](test_data/TD-TC-000820.json) |
| TC-000821 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000821.step1.purchase.json](test_data/TD-TC-000821.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000821.step2.timeout-reversal.json](test_data/TD-TC-000821.step2.timeout-reversal.json) |
| TC-000822 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000822.step1.purchase.json](test_data/TD-TC-000822.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000822.step2.timeout-reversal.json](test_data/TD-TC-000822.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000822.step3.timeout-reversal.json](test_data/TD-TC-000822.step3.timeout-reversal.json) |
| TC-000823 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000823.step1.purchase.json](test_data/TD-TC-000823.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000823.step2.timeout-reversal.json](test_data/TD-TC-000823.step2.timeout-reversal.json) |
| TC-000824 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-58-8 p.58; BR-FISERV-EMV-GUIDE-63-4 p.63; BR-FISERV-EMV-GUIDE-88-2 p.88 | 1 | Purchase/Capture | [TD-TC-000824.json](test_data/TD-TC-000824.json) |
| TC-000825 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000825.json](test_data/TD-TC-000825.json) |
| TC-000826 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000826.json](test_data/TD-TC-000826.json) |
| TC-000827 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000827.json](test_data/TD-TC-000827.json) |

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
| TC-000108 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Happy path | Positive | needs_client_input | p.511; p.83; p.81 | 1 | Purchase/Capture | [TD-TC-000108.step1.purchase.json](test_data/TD-TC-000108.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000108.step2.reversal.json](test_data/TD-TC-000108.step2.reversal.json) |
| TC-000109 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Happy path | Positive | needs_client_input | p.567; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000109.step1.purchase.json](test_data/TD-TC-000109.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000109.step2.reversal.json](test_data/TD-TC-000109.step2.reversal.json) |
| TC-000110 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture → approv | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000110.json](test_data/TD-TC-000110.json) |
| TC-000111 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Poi | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000111.json](test_data/TD-TC-000111.json) |
| TC-000112 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cur | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000112.json](test_data/TD-TC-000112.json) |
| TC-000113 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Use | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000113.json](test_data/TD-TC-000113.json) |
| TC-000114 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Car | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000114.json](test_data/TD-TC-000114.json) |
| TC-000115 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Poi | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000115.json](test_data/TD-TC-000115.json) |
| TC-000116 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Fra | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000116.json](test_data/TD-TC-000116.json) |
| TC-000117 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cou | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000117.json](test_data/TD-TC-000117.json) |
| TC-000118 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Car | Happy path | Positive | needs_client_input | p.511; p.83; p.81 | 1 | Purchase/Capture | [TD-TC-000118.json](test_data/TD-TC-000118.json) |
| TC-000119 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Rea | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-000119.json](test_data/TD-TC-000119.json) |
| TC-000120 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-7 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000120.json](test_data/TD-TC-000120.json) |
| TC-000121 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000121.json](test_data/TD-TC-000121.json) |
| TC-000122 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000122.json](test_data/TD-TC-000122.json) |
| TC-000123 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000123.json](test_data/TD-TC-000123.json) |
| TC-000124 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-42-2 p.42; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000124.json](test_data/TD-TC-000124.json) |
| TC-000125 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-142-3 p.142; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000125.json](test_data/TD-TC-000125.json) |
| TC-000126 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000126.json](test_data/TD-TC-000126.json) |
| TC-000127 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000127.json](test_data/TD-TC-000127.json) |
| TC-000128 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000128.json](test_data/TD-TC-000128.json) |
| TC-000129 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000129.json](test_data/TD-TC-000129.json) |
| TC-000130 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Single rule broken | Negative | needs_client_input | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000130.step1.purchase.json](test_data/TD-TC-000130.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000130.step2.reversal.json](test_data/TD-TC-000130.step2.reversal.json) |
| TC-000131 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Single rule broken | Negative | needs_client_input | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000131.step1.purchase.json](test_data/TD-TC-000131.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000131.step2.reversal.json](test_data/TD-TC-000131.step2.reversal.json) |
| TC-000132 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Single rule broken | Negative | needs_client_input | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000132.step1.purchase.json](test_data/TD-TC-000132.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000132.step2.timeout-reversal.json](test_data/TD-TC-000132.step2.timeout-reversal.json) |
| TC-000133 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cof | Decision table | Positive | needs_client_input | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-000133.json](test_data/TD-TC-000133.json) |
| TC-000134 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cvm | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-000134.json](test_data/TD-TC-000134.json) |
| TC-000135 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with inc | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000135.json](test_data/TD-TC-000135.json) |
| TC-000136 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with inc | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000136.json](test_data/TD-TC-000136.json) |
| TC-000137 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Var | Decision table | Positive | needs_client_input | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000137.json](test_data/TD-TC-000137.json) |
| TC-000138 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with tra | Decision table | Positive | blocked_verification | BR-81-3 p.81; BR-94-6 p.94; BR-95-3 p.95 | 1 | Purchase/Capture | [TD-TC-000138.json](test_data/TD-TC-000138.json) |
| TC-000139 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Valid flow | Positive | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000139.step1.purchase.json](test_data/TD-TC-000139.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000139.step2.reversal.json](test_data/TD-TC-000139.step2.reversal.json) |
| TC-000140 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000140.step1.purchase.json](test_data/TD-TC-000140.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000140.step2.timeout-reversal.json](test_data/TD-TC-000140.step2.timeout-reversal.json) |
| TC-000141 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000141.step1.purchase.json](test_data/TD-TC-000141.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000141.step2.void.json](test_data/TD-TC-000141.step2.void.json) |
| TC-000142 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000142.step1.purchase.json](test_data/TD-TC-000142.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000142.step2.split-tender.json](test_data/TD-TC-000142.step2.split-tender.json) |
| TC-000143 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000143.step1.purchase.json](test_data/TD-TC-000143.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000143.step2.timeout-reversal.json](test_data/TD-TC-000143.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000143.step3.timeout-reversal.json](test_data/TD-TC-000143.step3.timeout-reversal.json) |
| TC-000144 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (lost_be | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000144.step1.purchase.json](test_data/TD-TC-000144.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000144.step2.timeout-reversal.json](test_data/TD-TC-000144.step2.timeout-reversal.json) |
| TC-000145 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (late_re | Fault / recovery | Positive | needs_client_input | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000145.step1.purchase.json](test_data/TD-TC-000145.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000145.step2.timeout-reversal.json](test_data/TD-TC-000145.step2.timeout-reversal.json) |
| TC-000146 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (late_re | Fault / recovery | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000146.step1.purchase.json](test_data/TD-TC-000146.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000146.step2.timeout-reversal.json](test_data/TD-TC-000146.step2.timeout-reversal.json) |
| TC-000147 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Fault / recovery | Positive | needs_client_input | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000147.step1.purchase.json](test_data/TD-TC-000147.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000147.step2.timeout-reversal.json](test_data/TD-TC-000147.step2.timeout-reversal.json) |
| TC-000148 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Idempotency / duplicate / retry | Positive | needs_client_input | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000148.step1.purchase.json](test_data/TD-TC-000148.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000148.step2.timeout-reversal.json](test_data/TD-TC-000148.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000148.step3.timeout-reversal.json](test_data/TD-TC-000148.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000148.step4.timeout-reversal.json](test_data/TD-TC-000148.step4.timeout-reversal.json) |
| TC-000149 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000149.step1.purchase.json](test_data/TD-TC-000149.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000149.step2.reversal.json](test_data/TD-TC-000149.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000149.step3.totals.json](test_data/TD-TC-000149.step3.totals.json) |
| TC-000150 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then To | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000150.step1.purchase.json](test_data/TD-TC-000150.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000150.step2.totals.json](test_data/TD-TC-000150.step2.totals.json) |
| TC-000151 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture on a POS | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-50-5 p.50 | 1 | Purchase/Capture | [TD-TC-000151.json](test_data/TD-TC-000151.json) |
| TC-000152 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Real Ti | Response / oracle check | Positive | needs_client_input | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000152.json](test_data/TD-TC-000152.json) |
| TC-000153 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Transac | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000153.json](test_data/TD-TC-000153.json) |
| TC-000154 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (card is | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000154.json](test_data/TD-TC-000154.json) |
| TC-000155 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Response / oracle check | Positive | needs_client_input | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000155.json](test_data/TD-TC-000155.json) |
| TC-000156 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Response / oracle check | Positive | needs_client_input | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000156.json](test_data/TD-TC-000156.json) |
| TC-000157 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Account Verification with | Happy path | Positive | requires_review | BR-713-1 p.713; p.511; p.83 | 1 | Account Verification | [TD-TC-000157.json](test_data/TD-TC-000157.json) |
| TC-000972 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture EMV Requ | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-142-3 p.142; BR-FISERV-EMV-GUIDE-58-8 p.58; BR-FISERV-EMV-GUIDE-63-4 p.63 | 1 |  | [TD-TC-000972.json](test_data/TD-TC-000972.json) |

### SC-ATL105-D3C1F6EE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-1B7DB9D5 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000725 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture → | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000725.json](test_data/TD-TC-000725.json) |
| TC-000726 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000726.json](test_data/TD-TC-000726.json) |

### SC-ATL105-B45D131F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-E3520A16 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000920 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000920.json](test_data/TD-TC-000920.json) |
| TC-000921 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture (no  | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000921.step1.purchase.json](test_data/TD-TC-000921.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000921.step2.timeout-reversal.json](test_data/TD-TC-000921.step2.timeout-reversal.json) |
| TC-000922 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture (no  | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000922.step1.purchase.json](test_data/TD-TC-000922.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000922.step2.timeout-reversal.json](test_data/TD-TC-000922.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000922.step3.timeout-reversal.json](test_data/TD-TC-000922.step3.timeout-reversal.json) |
| TC-000923 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture (los | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000923.step1.purchase.json](test_data/TD-TC-000923.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000923.step2.timeout-reversal.json](test_data/TD-TC-000923.step2.timeout-reversal.json) |

### SC-ATL105-55A48834 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000934 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Magnetic Stripe Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000934.json](test_data/TD-TC-000934.json) |

### SC-ATL105-4ADF7122 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000941 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Magnetic Stripe Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000941.json](test_data/TD-TC-000941.json) |

### SC-ATL105-A28F1156 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000762 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000762.json](test_data/TD-TC-000762.json) |
| TC-000763 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture wit | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000763.json](test_data/TD-TC-000763.json) |
| TC-000764 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture, th | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-000764.step1.purchase.json](test_data/TD-TC-000764.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000764.step2.reversal.json](test_data/TD-TC-000764.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-000764.step3.reversal.json](test_data/TD-TC-000764.step3.reversal.json) |
| TC-000765 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000765.json](test_data/TD-TC-000765.json) |
| TC-000766 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000766.json](test_data/TD-TC-000766.json) |

### SC-ATL105-8E9C2873 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000828 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000828.json](test_data/TD-TC-000828.json) |
| TC-000829 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture wit | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000829.json](test_data/TD-TC-000829.json) |
| TC-000830 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000830.json](test_data/TD-TC-000830.json) |
| TC-000831 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (no | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000831.step1.purchase.json](test_data/TD-TC-000831.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000831.step2.timeout-reversal.json](test_data/TD-TC-000831.step2.timeout-reversal.json) |
| TC-000832 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (no | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000832.step1.purchase.json](test_data/TD-TC-000832.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000832.step2.timeout-reversal.json](test_data/TD-TC-000832.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000832.step3.timeout-reversal.json](test_data/TD-TC-000832.step3.timeout-reversal.json) |
| TC-000833 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (lo | Invalid state transition | Negative | needs_client_input | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000833.step1.purchase.json](test_data/TD-TC-000833.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000833.step2.timeout-reversal.json](test_data/TD-TC-000833.step2.timeout-reversal.json) |
| TC-000834 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000834.json](test_data/TD-TC-000834.json) |
| TC-000835 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000835.json](test_data/TD-TC-000835.json) |
| TC-000836 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000836.json](test_data/TD-TC-000836.json) |

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
| TC-000202 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture → approve | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000202.json](test_data/TD-TC-000202.json) |
| TC-000203 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Poin | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-000203.json](test_data/TD-TC-000203.json) |
| TC-000204 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Curr | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-000204.json](test_data/TD-TC-000204.json) |
| TC-000205 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with User | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000205.json](test_data/TD-TC-000205.json) |
| TC-000206 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Card | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000206.json](test_data/TD-TC-000206.json) |
| TC-000207 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Poin | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-000207.json](test_data/TD-TC-000207.json) |
| TC-000208 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Frau | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000208.json](test_data/TD-TC-000208.json) |
| TC-000209 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Coun | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-000209.json](test_data/TD-TC-000209.json) |
| TC-000210 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Card | Happy path | Positive | needs_client_input | p.511; p.83; p.81 | 1 | Purchase/Capture | [TD-TC-000210.json](test_data/TD-TC-000210.json) |
| TC-000211 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Real | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-000211.json](test_data/TD-TC-000211.json) |
| TC-000212 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000212.json](test_data/TD-TC-000212.json) |
| TC-000213 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000213.json](test_data/TD-TC-000213.json) |
| TC-000214 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000214.json](test_data/TD-TC-000214.json) |
| TC-000215 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000215.json](test_data/TD-TC-000215.json) |
| TC-000216 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000216.json](test_data/TD-TC-000216.json) |
| TC-000217 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000217.json](test_data/TD-TC-000217.json) |
| TC-000218 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000218.json](test_data/TD-TC-000218.json) |
| TC-000219 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Single rule broken | Negative | requires_review | p.546; p.546; p.102 | 1 | Purchase/Capture | [TD-TC-000219.step1.purchase.json](test_data/TD-TC-000219.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000219.step2.reversal.json](test_data/TD-TC-000219.step2.reversal.json) |
| TC-000220 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Single rule broken | Negative | requires_review | p.546; p.546; BR-40-2 p.40 | 1 | Purchase/Capture | [TD-TC-000220.step1.purchase.json](test_data/TD-TC-000220.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000220.step2.reversal.json](test_data/TD-TC-000220.step2.reversal.json) |
| TC-000221 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Single rule broken | Negative | requires_review | p.546; p.39; p.40 | 1 | Purchase/Capture | [TD-TC-000221.step1.purchase.json](test_data/TD-TC-000221.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000221.step2.timeout-reversal.json](test_data/TD-TC-000221.step2.timeout-reversal.json) |
| TC-000222 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cof  | Decision table | Positive | needs_client_input | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-000222.json](test_data/TD-TC-000222.json) |
| TC-000223 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cvm  | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-000223.json](test_data/TD-TC-000223.json) |
| TC-000224 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incr | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-000224.json](test_data/TD-TC-000224.json) |
| TC-000225 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incr | Decision table | Positive | needs_client_input | BR-616-4 p.616; BR-616-6 p.616; BR-637-2 p.637 | 1 | Purchase/Capture | [TD-TC-000225.json](test_data/TD-TC-000225.json) |
| TC-000226 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incr | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-000226.json](test_data/TD-TC-000226.json) |
| TC-000227 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Vari | Decision table | Positive | needs_client_input | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000227.json](test_data/TD-TC-000227.json) |
| TC-000228 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with trac | Decision table | Positive | needs_client_input | BR-81-3 p.81; BR-94-6 p.94; BR-95-3 p.95 | 1 | Purchase/Capture | [TD-TC-000228.json](test_data/TD-TC-000228.json) |
| TC-000229 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-000229.step1.purchase.json](test_data/TD-TC-000229.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000229.step2.reversal.json](test_data/TD-TC-000229.step2.reversal.json) |
| TC-000230 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Valid flow | Positive | requires_review | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-000230.step1.purchase.json](test_data/TD-TC-000230.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000230.step2.timeout-reversal.json](test_data/TD-TC-000230.step2.timeout-reversal.json) |
| TC-000231 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Valid flow | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000231.step1.purchase.json](test_data/TD-TC-000231.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000231.step2.void.json](test_data/TD-TC-000231.step2.void.json) |
| TC-000232 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000232.step1.purchase.json](test_data/TD-TC-000232.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-000232.step2.split-tender.json](test_data/TD-TC-000232.step2.split-tender.json) |
| TC-000233 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Invalid state transition | Negative | requires_review | p.546; BR-40-13 p.40; BR-39-1 p.39 | 1 | Purchase/Capture | [TD-TC-000233.step1.purchase.json](test_data/TD-TC-000233.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000233.step2.timeout-reversal.json](test_data/TD-TC-000233.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000233.step3.timeout-reversal.json](test_data/TD-TC-000233.step3.timeout-reversal.json) |
| TC-000234 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (lost_bef | Invalid state transition | Negative | requires_review | p.546; BR-40-13 p.40; p.511 | 1 | Purchase/Capture | [TD-TC-000234.step1.purchase.json](test_data/TD-TC-000234.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000234.step2.timeout-reversal.json](test_data/TD-TC-000234.step2.timeout-reversal.json) |
| TC-000235 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (late_res | Fault / recovery | Positive | requires_review | BR-43-3 p.43; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000235.step1.purchase.json](test_data/TD-TC-000235.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000235.step2.timeout-reversal.json](test_data/TD-TC-000235.step2.timeout-reversal.json) |
| TC-000236 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (late_res | Fault / recovery | Positive | requires_review | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-000236.step1.purchase.json](test_data/TD-TC-000236.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000236.step2.timeout-reversal.json](test_data/TD-TC-000236.step2.timeout-reversal.json) |
| TC-000237 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Fault / recovery | Positive | requires_review | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-000237.step1.purchase.json](test_data/TD-TC-000237.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000237.step2.timeout-reversal.json](test_data/TD-TC-000237.step2.timeout-reversal.json) |
| TC-000238 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Idempotency / duplicate / retry | Positive | requires_review | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000238.step1.purchase.json](test_data/TD-TC-000238.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-000238.step2.timeout-reversal.json](test_data/TD-TC-000238.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-000238.step3.timeout-reversal.json](test_data/TD-TC-000238.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-000238.step4.timeout-reversal.json](test_data/TD-TC-000238.step4.timeout-reversal.json) |
| TC-000239 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-000239.step1.purchase.json](test_data/TD-TC-000239.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000239.step2.reversal.json](test_data/TD-TC-000239.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-000239.step3.totals.json](test_data/TD-TC-000239.step3.totals.json) |
| TC-000240 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tot | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-000240.step1.purchase.json](test_data/TD-TC-000240.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-000240.step2.totals.json](test_data/TD-TC-000240.step2.totals.json) |
| TC-000241 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture on a POS  | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-50-5 p.50 | 1 | Purchase/Capture | [TD-TC-000241.json](test_data/TD-TC-000241.json) |
| TC-000242 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Real Tim | Response / oracle check | Positive | needs_client_input | BR-315-6 p.315; BR-317-5 p.317; BR-317-9 p.317 | 1 | Purchase/Capture | [TD-TC-000242.json](test_data/TD-TC-000242.json) |
| TC-000243 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Transact | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000243.json](test_data/TD-TC-000243.json) |
| TC-000244 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (card iss | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000244.json](test_data/TD-TC-000244.json) |
| TC-000245 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending M | Response / oracle check | Positive | needs_client_input | BR-664-4 p.664; BR-545-5 p.545; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-000245.json](test_data/TD-TC-000245.json) |
| TC-000246 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending a | Response / oracle check | Positive | needs_client_input | BR-ATL105-TRANSARMOR-52-3 p.52; BR-240-1 p.240; BR-252-3 p.252 | 1 | Purchase/Capture | [TD-TC-000246.json](test_data/TD-TC-000246.json) |
| TC-000247 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with AVS  | Response / oracle check | Positive | needs_client_input | p.643; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-000247.json](test_data/TD-TC-000247.json) |

### SC-ATL105-7B7648CE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000729 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture →  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000729.json](test_data/TD-TC-000729.json) |
| TC-000730 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture wi | Decision table | Positive | needs_client_input | BR-616-4 p.616; BR-616-6 p.616; BR-637-2 p.637 | 1 | Purchase/Capture | [TD-TC-000730.json](test_data/TD-TC-000730.json) |
| TC-000731 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000731.json](test_data/TD-TC-000731.json) |

### SC-ATL105-7CC97653 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000936 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Manual / Keyed Purchase/Capture → app | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000936.json](test_data/TD-TC-000936.json) |

### SC-ATL105-6A7283D0 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000943 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Manual / Keyed Purchase/Capture → app | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-000943.json](test_data/TD-TC-000943.json) |

### SC-ATL105-A5A02764 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000772 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-000772.json](test_data/TD-TC-000772.json) |
| TC-000773 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture with | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-000773.json](test_data/TD-TC-000773.json) |
| TC-000774 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture, the | Invalid state transition | Negative | requires_review | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-000774.step1.purchase.json](test_data/TD-TC-000774.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-000774.step2.reversal.json](test_data/TD-TC-000774.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-000774.step3.reversal.json](test_data/TD-TC-000774.step3.reversal.json) |
| TC-000775 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (Tra | Response / oracle check | Positive | needs_client_input | BR-94-5 p.94; BR-607-3 p.607; BR-ATL105-TRANSARMOR-57-10 p.57 | 1 | Purchase/Capture | [TD-TC-000775.json](test_data/TD-TC-000775.json) |
| TC-000776 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (car | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-000776.json](test_data/TD-TC-000776.json) |

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
| TC-001748 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001748.json](data_element_validation/test_data/TD-TC-001748.json) |
| TC-001749 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001749.json](data_element_validation/test_data/TD-TC-001749.json) |
| TC-001750 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001750.json](data_element_validation/test_data/TD-TC-001750.json) |
| TC-001751 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001751.json](data_element_validation/test_data/TD-TC-001751.json) |
| TC-001752 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001752.json](data_element_validation/test_data/TD-TC-001752.json) |
| TC-001753 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001753.json](data_element_validation/test_data/TD-TC-001753.json) |
| TC-001754 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001754.json](data_element_validation/test_data/TD-TC-001754.json) |
| TC-001755 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001755.json](data_element_validation/test_data/TD-TC-001755.json) |
| TC-001756 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001756.json](data_element_validation/test_data/TD-TC-001756.json) |
| TC-001757 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | BR-190-4 p.190; BR-193-1 p.193; BR-193-4 p.193 | 1 |  | [TD-TC-001757.json](data_element_validation/test_data/TD-TC-001757.json) |
| TC-001758 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | BR-190-4 p.190; BR-193-1 p.193 | 1 |  | [TD-TC-001758.json](data_element_validation/test_data/TD-TC-001758.json) |
| TC-001759 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001759.json](data_element_validation/test_data/TD-TC-001759.json) |
| TC-001760 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | needs_client_input | BR-190-4 p.190; BR-193-1 p.193 | 1 |  | [TD-TC-001760.json](data_element_validation/test_data/TD-TC-001760.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Communications Test Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001520 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Communications Test Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001520.json](data_element_validation/test_data/TD-TC-001520.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Communications Test Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001761 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Communications Test Request – Messa | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-001761.json](data_element_validation/test_data/TD-TC-001761.json) |
| TC-001762 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Communications Test Request – Messa | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-001762.json](data_element_validation/test_data/TD-TC-001762.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001521 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001521.json](data_element_validation/test_data/TD-TC-001521.json) |
| TC-001522 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001522.json](data_element_validation/test_data/TD-TC-001522.json) |
| TC-001523 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001523.json](data_element_validation/test_data/TD-TC-001523.json) |
| TC-001524 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001524.json](data_element_validation/test_data/TD-TC-001524.json) |
| TC-001525 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001525.json](data_element_validation/test_data/TD-TC-001525.json) |
| TC-001526 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001526.json](data_element_validation/test_data/TD-TC-001526.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001772 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001772.json](data_element_validation/test_data/TD-TC-001772.json) |
| TC-001773 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001773.json](data_element_validation/test_data/TD-TC-001773.json) |
| TC-001774 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001774.json](data_element_validation/test_data/TD-TC-001774.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation EMV Financial Transaction Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001639 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001639.json](data_element_validation/test_data/TD-TC-001639.json) |
| TC-001640 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001640.json](data_element_validation/test_data/TD-TC-001640.json) |
| TC-001641 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001641.json](data_element_validation/test_data/TD-TC-001641.json) |
| TC-001642 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001642.json](data_element_validation/test_data/TD-TC-001642.json) |
| TC-001643 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001643.json](data_element_validation/test_data/TD-TC-001643.json) |
| TC-001644 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001644.json](data_element_validation/test_data/TD-TC-001644.json) |
| TC-001645 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001645.json](data_element_validation/test_data/TD-TC-001645.json) |
| TC-001646 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001646.json](data_element_validation/test_data/TD-TC-001646.json) |
| TC-001647 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001647.json](data_element_validation/test_data/TD-TC-001647.json) |
| TC-001648 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001648.json](data_element_validation/test_data/TD-TC-001648.json) |
| TC-001649 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001649.json](data_element_validation/test_data/TD-TC-001649.json) |
| TC-001650 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001650.json](data_element_validation/test_data/TD-TC-001650.json) |
| TC-001651 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001651.json](data_element_validation/test_data/TD-TC-001651.json) |
| TC-001652 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | needs_client_input | BR-575-1 p.575; BR-575-4 p.575; BR-575-5 p.575 | 1 |  | [TD-TC-001652.json](data_element_validation/test_data/TD-TC-001652.json) |
| TC-001653 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | BR-62-7 p.62; BR-112-2 p.112; BR-545-1 p.545 | 1 |  | [TD-TC-001653.json](data_element_validation/test_data/TD-TC-001653.json) |
| TC-001654 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-001654.json](data_element_validation/test_data/TD-TC-001654.json) |
| TC-001655 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p.[205, 219] | 1 |  | [TD-TC-001655.json](data_element_validation/test_data/TD-TC-001655.json) |
| TC-001656 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-001656.json](data_element_validation/test_data/TD-TC-001656.json) |
| TC-001657 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001657.json](data_element_validation/test_data/TD-TC-001657.json) |
| TC-001658 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001658.json](data_element_validation/test_data/TD-TC-001658.json) |
| TC-001659 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001659.json](data_element_validation/test_data/TD-TC-001659.json) |
| TC-001660 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001660.json](data_element_validation/test_data/TD-TC-001660.json) |
| TC-001661 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001661.json](data_element_validation/test_data/TD-TC-001661.json) |
| TC-001662 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001662.json](data_element_validation/test_data/TD-TC-001662.json) |
| TC-001663 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001663.json](data_element_validation/test_data/TD-TC-001663.json) |
| TC-001664 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001664.json](data_element_validation/test_data/TD-TC-001664.json) |
| TC-001665 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001665.json](data_element_validation/test_data/TD-TC-001665.json) |
| TC-001666 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001666.json](data_element_validation/test_data/TD-TC-001666.json) |
| TC-001667 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001667.json](data_element_validation/test_data/TD-TC-001667.json) |
| TC-001668 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001668.json](data_element_validation/test_data/TD-TC-001668.json) |
| TC-001669 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001669.json](data_element_validation/test_data/TD-TC-001669.json) |
| TC-001670 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001670.json](data_element_validation/test_data/TD-TC-001670.json) |
| TC-001671 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001671.json](data_element_validation/test_data/TD-TC-001671.json) |
| TC-001672 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001672.json](data_element_validation/test_data/TD-TC-001672.json) |
| TC-001673 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001673.json](data_element_validation/test_data/TD-TC-001673.json) |
| TC-001674 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001674.json](data_element_validation/test_data/TD-TC-001674.json) |
| TC-001675 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001675.json](data_element_validation/test_data/TD-TC-001675.json) |
| TC-001676 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001676.json](data_element_validation/test_data/TD-TC-001676.json) |
| TC-001677 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001677.json](data_element_validation/test_data/TD-TC-001677.json) |
| TC-001678 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001678.json](data_element_validation/test_data/TD-TC-001678.json) |
| TC-001679 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001679.json](data_element_validation/test_data/TD-TC-001679.json) |
| TC-001680 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001680.json](data_element_validation/test_data/TD-TC-001680.json) |
| TC-001681 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001681.json](data_element_validation/test_data/TD-TC-001681.json) |
| TC-001682 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001682.json](data_element_validation/test_data/TD-TC-001682.json) |
| TC-001683 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001683.json](data_element_validation/test_data/TD-TC-001683.json) |
| TC-001684 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001684.json](data_element_validation/test_data/TD-TC-001684.json) |
| TC-001685 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001685.json](data_element_validation/test_data/TD-TC-001685.json) |
| TC-001686 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001686.json](data_element_validation/test_data/TD-TC-001686.json) |
| TC-001687 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001687.json](data_element_validation/test_data/TD-TC-001687.json) |
| TC-001688 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001688.json](data_element_validation/test_data/TD-TC-001688.json) |
| TC-001689 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001689.json](data_element_validation/test_data/TD-TC-001689.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Electronic Mail Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001696 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001696.json](data_element_validation/test_data/TD-TC-001696.json) |
| TC-001697 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001697.json](data_element_validation/test_data/TD-TC-001697.json) |
| TC-001698 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | ready | BR-ATL105-TRANSARMOR-67-1 p.67 | 1 |  | [TD-TC-001698.json](data_element_validation/test_data/TD-TC-001698.json) |
| TC-001699 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | needs_client_input | BR-185-2 p.185; BR-240-3 p.240 | 1 |  | [TD-TC-001699.json](data_element_validation/test_data/TD-TC-001699.json) |
| TC-001700 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | needs_client_input | p.[185] | 1 |  | [TD-TC-001700.json](data_element_validation/test_data/TD-TC-001700.json) |
| TC-001701 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | needs_client_input | BR-185-2 p.185; BR-240-3 p.240 | 1 |  | [TD-TC-001701.json](data_element_validation/test_data/TD-TC-001701.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Financial Transaction Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001595 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001595.json](data_element_validation/test_data/TD-TC-001595.json) |
| TC-001596 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001596.json](data_element_validation/test_data/TD-TC-001596.json) |
| TC-001597 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001597.json](data_element_validation/test_data/TD-TC-001597.json) |
| TC-001598 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001598.json](data_element_validation/test_data/TD-TC-001598.json) |
| TC-001599 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001599.json](data_element_validation/test_data/TD-TC-001599.json) |
| TC-001600 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001600.json](data_element_validation/test_data/TD-TC-001600.json) |
| TC-001601 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001601.json](data_element_validation/test_data/TD-TC-001601.json) |
| TC-001602 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001602.json](data_element_validation/test_data/TD-TC-001602.json) |
| TC-001603 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | needs_client_input | BR-575-1 p.575; BR-575-4 p.575; BR-575-5 p.575 | 1 |  | [TD-TC-001603.json](data_element_validation/test_data/TD-TC-001603.json) |
| TC-001604 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | BR-62-7 p.62; BR-112-2 p.112; BR-545-1 p.545 | 1 |  | [TD-TC-001604.json](data_element_validation/test_data/TD-TC-001604.json) |
| TC-001605 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | BR-206-7 p.206 | 1 |  | [TD-TC-001605.json](data_element_validation/test_data/TD-TC-001605.json) |
| TC-001606 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-001606.json](data_element_validation/test_data/TD-TC-001606.json) |
| TC-001607 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p.[167, 219] | 1 |  | [TD-TC-001607.json](data_element_validation/test_data/TD-TC-001607.json) |
| TC-001608 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-001608.json](data_element_validation/test_data/TD-TC-001608.json) |
| TC-001609 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001609.json](data_element_validation/test_data/TD-TC-001609.json) |
| TC-001610 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001610.json](data_element_validation/test_data/TD-TC-001610.json) |
| TC-001611 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001611.json](data_element_validation/test_data/TD-TC-001611.json) |
| TC-001612 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001612.json](data_element_validation/test_data/TD-TC-001612.json) |
| TC-001613 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001613.json](data_element_validation/test_data/TD-TC-001613.json) |
| TC-001614 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001614.json](data_element_validation/test_data/TD-TC-001614.json) |
| TC-001615 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001615.json](data_element_validation/test_data/TD-TC-001615.json) |
| TC-001616 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001616.json](data_element_validation/test_data/TD-TC-001616.json) |
| TC-001617 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001617.json](data_element_validation/test_data/TD-TC-001617.json) |
| TC-001618 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001618.json](data_element_validation/test_data/TD-TC-001618.json) |
| TC-001619 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001619.json](data_element_validation/test_data/TD-TC-001619.json) |
| TC-001620 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001620.json](data_element_validation/test_data/TD-TC-001620.json) |
| TC-001621 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001621.json](data_element_validation/test_data/TD-TC-001621.json) |
| TC-001622 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001622.json](data_element_validation/test_data/TD-TC-001622.json) |
| TC-001623 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001623.json](data_element_validation/test_data/TD-TC-001623.json) |
| TC-001624 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001624.json](data_element_validation/test_data/TD-TC-001624.json) |
| TC-001625 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001625.json](data_element_validation/test_data/TD-TC-001625.json) |
| TC-001626 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001626.json](data_element_validation/test_data/TD-TC-001626.json) |
| TC-001627 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001627.json](data_element_validation/test_data/TD-TC-001627.json) |
| TC-001628 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001628.json](data_element_validation/test_data/TD-TC-001628.json) |
| TC-001629 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001629.json](data_element_validation/test_data/TD-TC-001629.json) |
| TC-001630 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001630.json](data_element_validation/test_data/TD-TC-001630.json) |
| TC-001631 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001631.json](data_element_validation/test_data/TD-TC-001631.json) |
| TC-001632 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001632.json](data_element_validation/test_data/TD-TC-001632.json) |
| TC-001633 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001633.json](data_element_validation/test_data/TD-TC-001633.json) |
| TC-001634 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001634.json](data_element_validation/test_data/TD-TC-001634.json) |
| TC-001635 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001635.json](data_element_validation/test_data/TD-TC-001635.json) |
| TC-001636 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001636.json](data_element_validation/test_data/TD-TC-001636.json) |
| TC-001637 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001637.json](data_element_validation/test_data/TD-TC-001637.json) |
| TC-001638 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001638.json](data_element_validation/test_data/TD-TC-001638.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001527 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001527.json](data_element_validation/test_data/TD-TC-001527.json) |
| TC-001528 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001528.json](data_element_validation/test_data/TD-TC-001528.json) |
| TC-001529 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001529.json](data_element_validation/test_data/TD-TC-001529.json) |
| TC-001530 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001530.json](data_element_validation/test_data/TD-TC-001530.json) |
| TC-001531 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001531.json](data_element_validation/test_data/TD-TC-001531.json) |
| TC-001532 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001532.json](data_element_validation/test_data/TD-TC-001532.json) |
| TC-001533 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001533.json](data_element_validation/test_data/TD-TC-001533.json) |
| TC-001534 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001534.json](data_element_validation/test_data/TD-TC-001534.json) |
| TC-001535 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001535.json](data_element_validation/test_data/TD-TC-001535.json) |
| TC-001536 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001536.json](data_element_validation/test_data/TD-TC-001536.json) |
| TC-001537 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001537.json](data_element_validation/test_data/TD-TC-001537.json) |
| TC-001538 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001538.json](data_element_validation/test_data/TD-TC-001538.json) |
| TC-001539 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001539.json](data_element_validation/test_data/TD-TC-001539.json) |
| TC-001540 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001540.json](data_element_validation/test_data/TD-TC-001540.json) |
| TC-001541 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001541.json](data_element_validation/test_data/TD-TC-001541.json) |
| TC-001542 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001542.json](data_element_validation/test_data/TD-TC-001542.json) |
| TC-001543 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001543.json](data_element_validation/test_data/TD-TC-001543.json) |
| TC-001544 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001544.json](data_element_validation/test_data/TD-TC-001544.json) |
| TC-001545 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001545.json](data_element_validation/test_data/TD-TC-001545.json) |
| TC-001546 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001546.json](data_element_validation/test_data/TD-TC-001546.json) |
| TC-001547 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001547.json](data_element_validation/test_data/TD-TC-001547.json) |
| TC-001548 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001548.json](data_element_validation/test_data/TD-TC-001548.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001781 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001781.json](data_element_validation/test_data/TD-TC-001781.json) |
| TC-001782 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001782.json](data_element_validation/test_data/TD-TC-001782.json) |
| TC-001783 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001783.json](data_element_validation/test_data/TD-TC-001783.json) |
| TC-001784 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001784.json](data_element_validation/test_data/TD-TC-001784.json) |
| TC-001785 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001785.json](data_element_validation/test_data/TD-TC-001785.json) |
| TC-001786 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001786.json](data_element_validation/test_data/TD-TC-001786.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Phone Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001549 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001549.json](data_element_validation/test_data/TD-TC-001549.json) |
| TC-001550 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001550.json](data_element_validation/test_data/TD-TC-001550.json) |
| TC-001551 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001551.json](data_element_validation/test_data/TD-TC-001551.json) |
| TC-001552 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001552.json](data_element_validation/test_data/TD-TC-001552.json) |
| TC-001553 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001553.json](data_element_validation/test_data/TD-TC-001553.json) |
| TC-001554 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001554.json](data_element_validation/test_data/TD-TC-001554.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Phone Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001769 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message struct | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001769.json](data_element_validation/test_data/TD-TC-001769.json) |
| TC-001770 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message struct | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001770.json](data_element_validation/test_data/TD-TC-001770.json) |
| TC-001771 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message struct | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001771.json](data_element_validation/test_data/TD-TC-001771.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Proprietary Data Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001713 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001713.json](data_element_validation/test_data/TD-TC-001713.json) |
| TC-001714 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001714.json](data_element_validation/test_data/TD-TC-001714.json) |
| TC-001715 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001715.json](data_element_validation/test_data/TD-TC-001715.json) |
| TC-001716 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001716.json](data_element_validation/test_data/TD-TC-001716.json) |
| TC-001717 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001717.json](data_element_validation/test_data/TD-TC-001717.json) |
| TC-001718 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001718.json](data_element_validation/test_data/TD-TC-001718.json) |
| TC-001719 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001719.json](data_element_validation/test_data/TD-TC-001719.json) |
| TC-001720 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001720.json](data_element_validation/test_data/TD-TC-001720.json) |
| TC-001721 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001721.json](data_element_validation/test_data/TD-TC-001721.json) |
| TC-001722 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001722.json](data_element_validation/test_data/TD-TC-001722.json) |
| TC-001723 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001723.json](data_element_validation/test_data/TD-TC-001723.json) |
| TC-001724 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001724.json](data_element_validation/test_data/TD-TC-001724.json) |
| TC-001725 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | BR-469-3 p.469; BR-180-3 p.180; BR-179-5 p.179 | 1 |  | [TD-TC-001725.json](data_element_validation/test_data/TD-TC-001725.json) |
| TC-001726 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | BR-469-3 p.469; BR-180-3 p.180 | 1 |  | [TD-TC-001726.json](data_element_validation/test_data/TD-TC-001726.json) |
| TC-001727 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | p.[219] | 1 |  | [TD-TC-001727.json](data_element_validation/test_data/TD-TC-001727.json) |
| TC-001728 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | needs_client_input | BR-469-3 p.469; BR-180-3 p.180 | 1 |  | [TD-TC-001728.json](data_element_validation/test_data/TD-TC-001728.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 100 – Standard Message Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-000974 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000974.json](data_element_validation/test_data/TD-TC-000974.json) |
| TC-000975 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000975.json](data_element_validation/test_data/TD-TC-000975.json) |
| TC-000976 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-000976.json](data_element_validation/test_data/TD-TC-000976.json) |
| TC-000977 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000977.json](data_element_validation/test_data/TD-TC-000977.json) |
| TC-000978 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000978.json](data_element_validation/test_data/TD-TC-000978.json) |
| TC-000979 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000979.json](data_element_validation/test_data/TD-TC-000979.json) |
| TC-000980 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-000980.json](data_element_validation/test_data/TD-TC-000980.json) |
| TC-000981 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Te | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-000981.json](data_element_validation/test_data/TD-TC-000981.json) |
| TC-000982 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-000982.json](data_element_validation/test_data/TD-TC-000982.json) |
| TC-000983 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-000983.json](data_element_validation/test_data/TD-TC-000983.json) |
| TC-000984 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000984.json](data_element_validation/test_data/TD-TC-000984.json) |
| TC-000985 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000985.json](data_element_validation/test_data/TD-TC-000985.json) |
| TC-000986 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000986.json](data_element_validation/test_data/TD-TC-000986.json) |
| TC-000987 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ac | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000987.json](data_element_validation/test_data/TD-TC-000987.json) |
| TC-000988 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ac | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-000988.json](data_element_validation/test_data/TD-TC-000988.json) |
| TC-000989 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000989.json](data_element_validation/test_data/TD-TC-000989.json) |
| TC-000990 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000990.json](data_element_validation/test_data/TD-TC-000990.json) |
| TC-000991 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-000991.json](data_element_validation/test_data/TD-TC-000991.json) |
| TC-000992 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000992.json](data_element_validation/test_data/TD-TC-000992.json) |
| TC-000993 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: En | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000993.json](data_element_validation/test_data/TD-TC-000993.json) |
| TC-000994 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: En | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-000994.json](data_element_validation/test_data/TD-TC-000994.json) |
| TC-000995 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: En | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000995.json](data_element_validation/test_data/TD-TC-000995.json) |
| TC-000996 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000996.json](data_element_validation/test_data/TD-TC-000996.json) |
| TC-000997 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-000997.json](data_element_validation/test_data/TD-TC-000997.json) |
| TC-000998 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000998.json](data_element_validation/test_data/TD-TC-000998.json) |
| TC-000999 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-000999.json](data_element_validation/test_data/TD-TC-000999.json) |
| TC-001000 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001000.json](data_element_validation/test_data/TD-TC-001000.json) |
| TC-001001 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001001.json](data_element_validation/test_data/TD-TC-001001.json) |
| TC-001002 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001002.json](data_element_validation/test_data/TD-TC-001002.json) |
| TC-001003 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001003.json](data_element_validation/test_data/TD-TC-001003.json) |
| TC-001004 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001004.json](data_element_validation/test_data/TD-TC-001004.json) |
| TC-001005 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001005.json](data_element_validation/test_data/TD-TC-001005.json) |
| TC-001006 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001006.json](data_element_validation/test_data/TD-TC-001006.json) |
| TC-001007 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001007.json](data_element_validation/test_data/TD-TC-001007.json) |
| TC-001008 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Negative | needs_client_input | p.; p.393 | 1 |  | [TD-TC-001008.json](data_element_validation/test_data/TD-TC-001008.json) |
| TC-001009 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Boundary | needs_client_input | p.; p.393 | 1 |  | [TD-TC-001009.json](data_element_validation/test_data/TD-TC-001009.json) |
| TC-001010 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Negative | needs_client_input | p.; p.393 | 1 |  | [TD-TC-001010.json](data_element_validation/test_data/TD-TC-001010.json) |
| TC-001011 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Negative | needs_client_input | p.; p.393 | 1 |  | [TD-TC-001011.json](data_element_validation/test_data/TD-TC-001011.json) |
| TC-001012 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Boundary | blocked_verification | p.; p.393 | 1 |  | [TD-TC-001012.json](data_element_validation/test_data/TD-TC-001012.json) |
| TC-001013 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001013.json](data_element_validation/test_data/TD-TC-001013.json) |
| TC-001014 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001014.json](data_element_validation/test_data/TD-TC-001014.json) |
| TC-001015 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001015.json](data_element_validation/test_data/TD-TC-001015.json) |
| TC-001016 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001016.json](data_element_validation/test_data/TD-TC-001016.json) |
| TC-001017 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001017.json](data_element_validation/test_data/TD-TC-001017.json) |
| TC-001018 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001018.json](data_element_validation/test_data/TD-TC-001018.json) |
| TC-001019 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001019.json](data_element_validation/test_data/TD-TC-001019.json) |
| TC-001020 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001020.json](data_element_validation/test_data/TD-TC-001020.json) |
| TC-001021 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001021.json](data_element_validation/test_data/TD-TC-001021.json) |
| TC-001022 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001022.json](data_element_validation/test_data/TD-TC-001022.json) |
| TC-001023 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001023.json](data_element_validation/test_data/TD-TC-001023.json) |
| TC-001024 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001024.json](data_element_validation/test_data/TD-TC-001024.json) |
| TC-001025 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001025.json](data_element_validation/test_data/TD-TC-001025.json) |
| TC-001026 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001026.json](data_element_validation/test_data/TD-TC-001026.json) |
| TC-001027 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001027.json](data_element_validation/test_data/TD-TC-001027.json) |
| TC-001028 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001028.json](data_element_validation/test_data/TD-TC-001028.json) |
| TC-001029 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001029.json](data_element_validation/test_data/TD-TC-001029.json) |
| TC-001030 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001030.json](data_element_validation/test_data/TD-TC-001030.json) |
| TC-001031 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001031.json](data_element_validation/test_data/TD-TC-001031.json) |
| TC-001032 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001032.json](data_element_validation/test_data/TD-TC-001032.json) |
| TC-001033 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001033.json](data_element_validation/test_data/TD-TC-001033.json) |
| TC-001034 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001034.json](data_element_validation/test_data/TD-TC-001034.json) |
| TC-001035 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001035.json](data_element_validation/test_data/TD-TC-001035.json) |
| TC-001036 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001036.json](data_element_validation/test_data/TD-TC-001036.json) |
| TC-001037 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001037.json](data_element_validation/test_data/TD-TC-001037.json) |
| TC-001038 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Lo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001038.json](data_element_validation/test_data/TD-TC-001038.json) |
| TC-001039 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Lo | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001039.json](data_element_validation/test_data/TD-TC-001039.json) |
| TC-001040 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Lo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001040.json](data_element_validation/test_data/TD-TC-001040.json) |
| TC-001041 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001041.json](data_element_validation/test_data/TD-TC-001041.json) |
| TC-001042 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001042.json](data_element_validation/test_data/TD-TC-001042.json) |
| TC-001043 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001043.json](data_element_validation/test_data/TD-TC-001043.json) |
| TC-001044 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001044.json](data_element_validation/test_data/TD-TC-001044.json) |
| TC-001045 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001045.json](data_element_validation/test_data/TD-TC-001045.json) |
| TC-001046 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001046.json](data_element_validation/test_data/TD-TC-001046.json) |
| TC-001047 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001047.json](data_element_validation/test_data/TD-TC-001047.json) |
| TC-001048 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | needs_client_input | p.372 | 1 |  | [TD-TC-001048.json](data_element_validation/test_data/TD-TC-001048.json) |
| TC-001049 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001049.json](data_element_validation/test_data/TD-TC-001049.json) |
| TC-001050 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001050.json](data_element_validation/test_data/TD-TC-001050.json) |
| TC-001051 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001051.json](data_element_validation/test_data/TD-TC-001051.json) |
| TC-001052 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001052.json](data_element_validation/test_data/TD-TC-001052.json) |
| TC-001053 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001053.json](data_element_validation/test_data/TD-TC-001053.json) |
| TC-001054 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001054.json](data_element_validation/test_data/TD-TC-001054.json) |
| TC-001055 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001055.json](data_element_validation/test_data/TD-TC-001055.json) |
| TC-001056 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001056.json](data_element_validation/test_data/TD-TC-001056.json) |
| TC-001057 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001057.json](data_element_validation/test_data/TD-TC-001057.json) |
| TC-001058 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001058.json](data_element_validation/test_data/TD-TC-001058.json) |
| TC-001059 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001059.json](data_element_validation/test_data/TD-TC-001059.json) |
| TC-001060 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001060.json](data_element_validation/test_data/TD-TC-001060.json) |
| TC-001061 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001061.json](data_element_validation/test_data/TD-TC-001061.json) |
| TC-001062 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001062.json](data_element_validation/test_data/TD-TC-001062.json) |
| TC-001063 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001063.json](data_element_validation/test_data/TD-TC-001063.json) |
| TC-001064 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001064.json](data_element_validation/test_data/TD-TC-001064.json) |
| TC-001065 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001065.json](data_element_validation/test_data/TD-TC-001065.json) |
| TC-001066 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001066.json](data_element_validation/test_data/TD-TC-001066.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 101 – Fleet Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 102 – Product Code Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 103 – EBT Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 104 – Purchase Card Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 105 – Totals Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001067 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Segment Type | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001067.json](data_element_validation/test_data/TD-TC-001067.json) |
| TC-001068 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001068.json](data_element_validation/test_data/TD-TC-001068.json) |
| TC-001069 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001069.json](data_element_validation/test_data/TD-TC-001069.json) |
| TC-001070 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001070.json](data_element_validation/test_data/TD-TC-001070.json) |
| TC-001071 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001071.json](data_element_validation/test_data/TD-TC-001071.json) |
| TC-001072 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001072.json](data_element_validation/test_data/TD-TC-001072.json) |
| TC-001073 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Terminal Ide | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001073.json](data_element_validation/test_data/TD-TC-001073.json) |
| TC-001074 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Terminal Ide | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001074.json](data_element_validation/test_data/TD-TC-001074.json) |
| TC-001075 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Terminal Ide | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001075.json](data_element_validation/test_data/TD-TC-001075.json) |
| TC-001076 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001076.json](data_element_validation/test_data/TD-TC-001076.json) |
| TC-001077 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Prompt Code  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001077.json](data_element_validation/test_data/TD-TC-001077.json) |
| TC-001078 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001078.json](data_element_validation/test_data/TD-TC-001078.json) |
| TC-001079 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001079.json](data_element_validation/test_data/TD-TC-001079.json) |
| TC-001080 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001080.json](data_element_validation/test_data/TD-TC-001080.json) |
| TC-001081 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001081.json](data_element_validation/test_data/TD-TC-001081.json) |
| TC-001082 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Password one | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001082.json](data_element_validation/test_data/TD-TC-001082.json) |
| TC-001083 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Password exa | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001083.json](data_element_validation/test_data/TD-TC-001083.json) |
| TC-001084 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Password con | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001084.json](data_element_validation/test_data/TD-TC-001084.json) |
| TC-001085 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001085.json](data_element_validation/test_data/TD-TC-001085.json) |
| TC-001086 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001086.json](data_element_validation/test_data/TD-TC-001086.json) |
| TC-001087 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001087.json](data_element_validation/test_data/TD-TC-001087.json) |
| TC-001088 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001088.json](data_element_validation/test_data/TD-TC-001088.json) |
| TC-001089 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001089.json](data_element_validation/test_data/TD-TC-001089.json) |
| TC-001090 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001090.json](data_element_validation/test_data/TD-TC-001090.json) |
| TC-001091 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001091.json](data_element_validation/test_data/TD-TC-001091.json) |
| TC-001092 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Hardware Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001092.json](data_element_validation/test_data/TD-TC-001092.json) |
| TC-001093 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Hardware Ver | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001093.json](data_element_validation/test_data/TD-TC-001093.json) |
| TC-001094 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Hardware Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001094.json](data_element_validation/test_data/TD-TC-001094.json) |
| TC-001095 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001095.json](data_element_validation/test_data/TD-TC-001095.json) |
| TC-001096 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001096.json](data_element_validation/test_data/TD-TC-001096.json) |
| TC-001097 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001097.json](data_element_validation/test_data/TD-TC-001097.json) |
| TC-001098 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001098.json](data_element_validation/test_data/TD-TC-001098.json) |
| TC-001099 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001099.json](data_element_validation/test_data/TD-TC-001099.json) |
| TC-001100 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001100.json](data_element_validation/test_data/TD-TC-001100.json) |
| TC-001101 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001101.json](data_element_validation/test_data/TD-TC-001101.json) |
| TC-001102 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001102.json](data_element_validation/test_data/TD-TC-001102.json) |
| TC-001103 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001103.json](data_element_validation/test_data/TD-TC-001103.json) |
| TC-001104 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001104.json](data_element_validation/test_data/TD-TC-001104.json) |
| TC-001105 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001105.json](data_element_validation/test_data/TD-TC-001105.json) |
| TC-001106 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001106.json](data_element_validation/test_data/TD-TC-001106.json) |
| TC-001107 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001107.json](data_element_validation/test_data/TD-TC-001107.json) |
| TC-001108 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001108.json](data_element_validation/test_data/TD-TC-001108.json) |
| TC-001109 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001109.json](data_element_validation/test_data/TD-TC-001109.json) |
| TC-001110 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001110.json](data_element_validation/test_data/TD-TC-001110.json) |
| TC-001111 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001111.json](data_element_validation/test_data/TD-TC-001111.json) |
| TC-001112 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001112.json](data_element_validation/test_data/TD-TC-001112.json) |
| TC-001113 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001113.json](data_element_validation/test_data/TD-TC-001113.json) |
| TC-001114 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001114.json](data_element_validation/test_data/TD-TC-001114.json) |
| TC-001115 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001115.json](data_element_validation/test_data/TD-TC-001115.json) |
| TC-001116 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001116.json](data_element_validation/test_data/TD-TC-001116.json) |
| TC-001117 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001117.json](data_element_validation/test_data/TD-TC-001117.json) |
| TC-001118 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001118.json](data_element_validation/test_data/TD-TC-001118.json) |
| TC-001119 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001119.json](data_element_validation/test_data/TD-TC-001119.json) |
| TC-001120 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001120.json](data_element_validation/test_data/TD-TC-001120.json) |
| TC-001121 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001121.json](data_element_validation/test_data/TD-TC-001121.json) |
| TC-001122 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001122.json](data_element_validation/test_data/TD-TC-001122.json) |
| TC-001123 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001123.json](data_element_validation/test_data/TD-TC-001123.json) |
| TC-001124 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001124.json](data_element_validation/test_data/TD-TC-001124.json) |
| TC-001125 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label o | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001125.json](data_element_validation/test_data/TD-TC-001125.json) |
| TC-001126 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label e | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001126.json](data_element_validation/test_data/TD-TC-001126.json) |
| TC-001127 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label c | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001127.json](data_element_validation/test_data/TD-TC-001127.json) |
| TC-001128 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001128.json](data_element_validation/test_data/TD-TC-001128.json) |
| TC-001129 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label v | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001129.json](data_element_validation/test_data/TD-TC-001129.json) |
| TC-001130 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label o | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001130.json](data_element_validation/test_data/TD-TC-001130.json) |
| TC-001131 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001131.json](data_element_validation/test_data/TD-TC-001131.json) |
| TC-001132 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001132.json](data_element_validation/test_data/TD-TC-001132.json) |
| TC-001133 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001133.json](data_element_validation/test_data/TD-TC-001133.json) |
| TC-001134 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001134.json](data_element_validation/test_data/TD-TC-001134.json) |
| TC-001135 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001135.json](data_element_validation/test_data/TD-TC-001135.json) |
| TC-001136 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001136.json](data_element_validation/test_data/TD-TC-001136.json) |
| TC-001137 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001137.json](data_element_validation/test_data/TD-TC-001137.json) |
| TC-001138 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001138.json](data_element_validation/test_data/TD-TC-001138.json) |
| TC-001139 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001139.json](data_element_validation/test_data/TD-TC-001139.json) |
| TC-001140 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001140.json](data_element_validation/test_data/TD-TC-001140.json) |
| TC-001141 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001141.json](data_element_validation/test_data/TD-TC-001141.json) |
| TC-001142 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001142.json](data_element_validation/test_data/TD-TC-001142.json) |
| TC-001143 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001143.json](data_element_validation/test_data/TD-TC-001143.json) |
| TC-001144 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001144.json](data_element_validation/test_data/TD-TC-001144.json) |
| TC-001145 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001145.json](data_element_validation/test_data/TD-TC-001145.json) |
| TC-001146 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Segment Leng | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001146.json](data_element_validation/test_data/TD-TC-001146.json) |
| TC-001147 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Segment Leng | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001147.json](data_element_validation/test_data/TD-TC-001147.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 108 – Loyalty Card Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 109 – Electronic Mail Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001148 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seg | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001148.json](data_element_validation/test_data/TD-TC-001148.json) |
| TC-001149 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001149.json](data_element_validation/test_data/TD-TC-001149.json) |
| TC-001150 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001150.json](data_element_validation/test_data/TD-TC-001150.json) |
| TC-001151 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001151.json](data_element_validation/test_data/TD-TC-001151.json) |
| TC-001152 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001152.json](data_element_validation/test_data/TD-TC-001152.json) |
| TC-001153 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001153.json](data_element_validation/test_data/TD-TC-001153.json) |
| TC-001154 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001154.json](data_element_validation/test_data/TD-TC-001154.json) |
| TC-001155 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ter | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001155.json](data_element_validation/test_data/TD-TC-001155.json) |
| TC-001156 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001156.json](data_element_validation/test_data/TD-TC-001156.json) |
| TC-001157 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001157.json](data_element_validation/test_data/TD-TC-001157.json) |
| TC-001158 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pro | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001158.json](data_element_validation/test_data/TD-TC-001158.json) |
| TC-001159 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pro | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001159.json](data_element_validation/test_data/TD-TC-001159.json) |
| TC-001160 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001160.json](data_element_validation/test_data/TD-TC-001160.json) |
| TC-001161 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001161.json](data_element_validation/test_data/TD-TC-001161.json) |
| TC-001162 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001162.json](data_element_validation/test_data/TD-TC-001162.json) |
| TC-001163 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001163.json](data_element_validation/test_data/TD-TC-001163.json) |
| TC-001164 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001164.json](data_element_validation/test_data/TD-TC-001164.json) |
| TC-001165 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001165.json](data_element_validation/test_data/TD-TC-001165.json) |
| TC-001166 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001166.json](data_element_validation/test_data/TD-TC-001166.json) |
| TC-001167 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001167.json](data_element_validation/test_data/TD-TC-001167.json) |
| TC-001168 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001168.json](data_element_validation/test_data/TD-TC-001168.json) |
| TC-001169 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001169.json](data_element_validation/test_data/TD-TC-001169.json) |
| TC-001170 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001170.json](data_element_validation/test_data/TD-TC-001170.json) |
| TC-001171 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pas | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001171.json](data_element_validation/test_data/TD-TC-001171.json) |
| TC-001172 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pas | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001172.json](data_element_validation/test_data/TD-TC-001172.json) |
| TC-001173 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pas | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001173.json](data_element_validation/test_data/TD-TC-001173.json) |
| TC-001174 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001174.json](data_element_validation/test_data/TD-TC-001174.json) |
| TC-001175 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001175.json](data_element_validation/test_data/TD-TC-001175.json) |
| TC-001176 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001176.json](data_element_validation/test_data/TD-TC-001176.json) |
| TC-001177 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001177.json](data_element_validation/test_data/TD-TC-001177.json) |
| TC-001178 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001178.json](data_element_validation/test_data/TD-TC-001178.json) |
| TC-001179 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001179.json](data_element_validation/test_data/TD-TC-001179.json) |
| TC-001180 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001180.json](data_element_validation/test_data/TD-TC-001180.json) |
| TC-001181 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001181.json](data_element_validation/test_data/TD-TC-001181.json) |
| TC-001182 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001182.json](data_element_validation/test_data/TD-TC-001182.json) |
| TC-001183 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001183.json](data_element_validation/test_data/TD-TC-001183.json) |
| TC-001184 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001184.json](data_element_validation/test_data/TD-TC-001184.json) |
| TC-001185 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001185.json](data_element_validation/test_data/TD-TC-001185.json) |
| TC-001186 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001186.json](data_element_validation/test_data/TD-TC-001186.json) |
| TC-001187 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001187.json](data_element_validation/test_data/TD-TC-001187.json) |
| TC-001188 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001188.json](data_element_validation/test_data/TD-TC-001188.json) |
| TC-001189 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001189.json](data_element_validation/test_data/TD-TC-001189.json) |
| TC-001190 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001190.json](data_element_validation/test_data/TD-TC-001190.json) |
| TC-001191 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001191.json](data_element_validation/test_data/TD-TC-001191.json) |
| TC-001192 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001192.json](data_element_validation/test_data/TD-TC-001192.json) |
| TC-001193 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001193.json](data_element_validation/test_data/TD-TC-001193.json) |
| TC-001194 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001194.json](data_element_validation/test_data/TD-TC-001194.json) |
| TC-001195 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001195.json](data_element_validation/test_data/TD-TC-001195.json) |
| TC-001196 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001196.json](data_element_validation/test_data/TD-TC-001196.json) |
| TC-001197 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001197.json](data_element_validation/test_data/TD-TC-001197.json) |
| TC-001198 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001198.json](data_element_validation/test_data/TD-TC-001198.json) |
| TC-001199 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001199.json](data_element_validation/test_data/TD-TC-001199.json) |
| TC-001200 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001200.json](data_element_validation/test_data/TD-TC-001200.json) |
| TC-001201 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001201.json](data_element_validation/test_data/TD-TC-001201.json) |
| TC-001202 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001202.json](data_element_validation/test_data/TD-TC-001202.json) |
| TC-001203 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001203.json](data_element_validation/test_data/TD-TC-001203.json) |
| TC-001204 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001204.json](data_element_validation/test_data/TD-TC-001204.json) |
| TC-001205 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001205.json](data_element_validation/test_data/TD-TC-001205.json) |
| TC-001206 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001206.json](data_element_validation/test_data/TD-TC-001206.json) |
| TC-001207 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seg | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001207.json](data_element_validation/test_data/TD-TC-001207.json) |
| TC-001208 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seg | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001208.json](data_element_validation/test_data/TD-TC-001208.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 110 – Check Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 111 – Variable Information Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001209 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001209.json](data_element_validation/test_data/TD-TC-001209.json) |
| TC-001210 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001210.json](data_element_validation/test_data/TD-TC-001210.json) |
| TC-001211 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001211.json](data_element_validation/test_data/TD-TC-001211.json) |
| TC-001212 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001212.json](data_element_validation/test_data/TD-TC-001212.json) |
| TC-001213 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001213.json](data_element_validation/test_data/TD-TC-001213.json) |
| TC-001214 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001214.json](data_element_validation/test_data/TD-TC-001214.json) |
| TC-001215 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001215.json](data_element_validation/test_data/TD-TC-001215.json) |
| TC-001216 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001216.json](data_element_validation/test_data/TD-TC-001216.json) |
| TC-001217 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001217.json](data_element_validation/test_data/TD-TC-001217.json) |
| TC-001218 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001218.json](data_element_validation/test_data/TD-TC-001218.json) |
| TC-001219 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001219.json](data_element_validation/test_data/TD-TC-001219.json) |
| TC-001220 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001220.json](data_element_validation/test_data/TD-TC-001220.json) |
| TC-001221 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001221.json](data_element_validation/test_data/TD-TC-001221.json) |
| TC-001222 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001222.json](data_element_validation/test_data/TD-TC-001222.json) |
| TC-001223 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001223.json](data_element_validation/test_data/TD-TC-001223.json) |
| TC-001224 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001224.json](data_element_validation/test_data/TD-TC-001224.json) |
| TC-001225 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001225.json](data_element_validation/test_data/TD-TC-001225.json) |
| TC-001226 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001226.json](data_element_validation/test_data/TD-TC-001226.json) |
| TC-001227 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-001228 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | requires_review | p.575; p.575 | 1 |  | [TD-TC-001228.json](data_element_validation/test_data/TD-TC-001228.json) |
| TC-001229 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | requires_review | p.589; p.589 | 1 |  | [TD-TC-001229.json](data_element_validation/test_data/TD-TC-001229.json) |
| TC-001230 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001230.json](data_element_validation/test_data/TD-TC-001230.json) |
| TC-001231 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001231.json](data_element_validation/test_data/TD-TC-001231.json) |
| TC-001232 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001232.json](data_element_validation/test_data/TD-TC-001232.json) |
| TC-001233 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001233.json](data_element_validation/test_data/TD-TC-001233.json) |
| TC-001234 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001234.json](data_element_validation/test_data/TD-TC-001234.json) |
| TC-001235 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001235.json](data_element_validation/test_data/TD-TC-001235.json) |
| TC-001236 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001236.json](data_element_validation/test_data/TD-TC-001236.json) |
| TC-001237 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001237.json](data_element_validation/test_data/TD-TC-001237.json) |
| TC-001238 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-001238.json](data_element_validation/test_data/TD-TC-001238.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 113 – ECA/ TeleCheck Data Segment Format ® @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 114 – SKU Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 116 – TransArmor Load Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001239 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seg | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001239.json](data_element_validation/test_data/TD-TC-001239.json) |
| TC-001240 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001240.json](data_element_validation/test_data/TD-TC-001240.json) |
| TC-001241 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001241.json](data_element_validation/test_data/TD-TC-001241.json) |
| TC-001242 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001242.json](data_element_validation/test_data/TD-TC-001242.json) |
| TC-001243 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001243.json](data_element_validation/test_data/TD-TC-001243.json) |
| TC-001244 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001244.json](data_element_validation/test_data/TD-TC-001244.json) |
| TC-001245 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001245.json](data_element_validation/test_data/TD-TC-001245.json) |
| TC-001246 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001246.json](data_element_validation/test_data/TD-TC-001246.json) |
| TC-001247 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001247.json](data_element_validation/test_data/TD-TC-001247.json) |
| TC-001248 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001248.json](data_element_validation/test_data/TD-TC-001248.json) |
| TC-001249 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Ter | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001249.json](data_element_validation/test_data/TD-TC-001249.json) |
| TC-001250 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001250.json](data_element_validation/test_data/TD-TC-001250.json) |
| TC-001251 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001251.json](data_element_validation/test_data/TD-TC-001251.json) |
| TC-001252 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Loa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001252.json](data_element_validation/test_data/TD-TC-001252.json) |
| TC-001253 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Har | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001253.json](data_element_validation/test_data/TD-TC-001253.json) |
| TC-001254 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Har | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001254.json](data_element_validation/test_data/TD-TC-001254.json) |
| TC-001255 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Har | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001255.json](data_element_validation/test_data/TD-TC-001255.json) |
| TC-001256 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001256.json](data_element_validation/test_data/TD-TC-001256.json) |
| TC-001257 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001257.json](data_element_validation/test_data/TD-TC-001257.json) |
| TC-001258 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001258.json](data_element_validation/test_data/TD-TC-001258.json) |
| TC-001259 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001259.json](data_element_validation/test_data/TD-TC-001259.json) |
| TC-001260 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001260.json](data_element_validation/test_data/TD-TC-001260.json) |
| TC-001261 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001261.json](data_element_validation/test_data/TD-TC-001261.json) |
| TC-001262 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001262.json](data_element_validation/test_data/TD-TC-001262.json) |
| TC-001263 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001263.json](data_element_validation/test_data/TD-TC-001263.json) |
| TC-001264 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001264.json](data_element_validation/test_data/TD-TC-001264.json) |
| TC-001265 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001265.json](data_element_validation/test_data/TD-TC-001265.json) |
| TC-001266 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001266.json](data_element_validation/test_data/TD-TC-001266.json) |
| TC-001267 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001267.json](data_element_validation/test_data/TD-TC-001267.json) |
| TC-001268 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001268.json](data_element_validation/test_data/TD-TC-001268.json) |
| TC-001269 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001269.json](data_element_validation/test_data/TD-TC-001269.json) |
| TC-001270 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001270.json](data_element_validation/test_data/TD-TC-001270.json) |
| TC-001271 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seg | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001271.json](data_element_validation/test_data/TD-TC-001271.json) |
| TC-001272 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seg | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001272.json](data_element_validation/test_data/TD-TC-001272.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 118 – Proprietary Data Load Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001273 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001273.json](data_element_validation/test_data/TD-TC-001273.json) |
| TC-001274 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001274.json](data_element_validation/test_data/TD-TC-001274.json) |
| TC-001275 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001275.json](data_element_validation/test_data/TD-TC-001275.json) |
| TC-001276 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001276.json](data_element_validation/test_data/TD-TC-001276.json) |
| TC-001277 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001277.json](data_element_validation/test_data/TD-TC-001277.json) |
| TC-001278 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001278.json](data_element_validation/test_data/TD-TC-001278.json) |
| TC-001279 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001279.json](data_element_validation/test_data/TD-TC-001279.json) |
| TC-001280 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001280.json](data_element_validation/test_data/TD-TC-001280.json) |
| TC-001281 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001281.json](data_element_validation/test_data/TD-TC-001281.json) |
| TC-001282 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001282.json](data_element_validation/test_data/TD-TC-001282.json) |
| TC-001283 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001283.json](data_element_validation/test_data/TD-TC-001283.json) |
| TC-001284 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001284.json](data_element_validation/test_data/TD-TC-001284.json) |
| TC-001285 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001285.json](data_element_validation/test_data/TD-TC-001285.json) |
| TC-001286 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001286.json](data_element_validation/test_data/TD-TC-001286.json) |
| TC-001287 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001287.json](data_element_validation/test_data/TD-TC-001287.json) |
| TC-001288 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Te | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001288.json](data_element_validation/test_data/TD-TC-001288.json) |
| TC-001289 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001289.json](data_element_validation/test_data/TD-TC-001289.json) |
| TC-001290 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001290.json](data_element_validation/test_data/TD-TC-001290.json) |
| TC-001291 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001291.json](data_element_validation/test_data/TD-TC-001291.json) |
| TC-001292 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001292.json](data_element_validation/test_data/TD-TC-001292.json) |
| TC-001293 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001293.json](data_element_validation/test_data/TD-TC-001293.json) |
| TC-001294 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001294.json](data_element_validation/test_data/TD-TC-001294.json) |
| TC-001295 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001295.json](data_element_validation/test_data/TD-TC-001295.json) |
| TC-001296 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001296.json](data_element_validation/test_data/TD-TC-001296.json) |
| TC-001297 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001297.json](data_element_validation/test_data/TD-TC-001297.json) |
| TC-001298 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001298.json](data_element_validation/test_data/TD-TC-001298.json) |
| TC-001299 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001299.json](data_element_validation/test_data/TD-TC-001299.json) |
| TC-001300 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001300.json](data_element_validation/test_data/TD-TC-001300.json) |
| TC-001301 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001301.json](data_element_validation/test_data/TD-TC-001301.json) |
| TC-001302 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001302.json](data_element_validation/test_data/TD-TC-001302.json) |
| TC-001303 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001303.json](data_element_validation/test_data/TD-TC-001303.json) |
| TC-001304 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001304.json](data_element_validation/test_data/TD-TC-001304.json) |
| TC-001305 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001305.json](data_element_validation/test_data/TD-TC-001305.json) |
| TC-001306 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001306.json](data_element_validation/test_data/TD-TC-001306.json) |
| TC-001307 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001307.json](data_element_validation/test_data/TD-TC-001307.json) |
| TC-001308 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001308.json](data_element_validation/test_data/TD-TC-001308.json) |
| TC-001309 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001309.json](data_element_validation/test_data/TD-TC-001309.json) |
| TC-001310 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001310.json](data_element_validation/test_data/TD-TC-001310.json) |
| TC-001311 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001311.json](data_element_validation/test_data/TD-TC-001311.json) |
| TC-001312 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001312.json](data_element_validation/test_data/TD-TC-001312.json) |
| TC-001313 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001313.json](data_element_validation/test_data/TD-TC-001313.json) |
| TC-001314 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001314.json](data_element_validation/test_data/TD-TC-001314.json) |
| TC-001315 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001315.json](data_element_validation/test_data/TD-TC-001315.json) |
| TC-001316 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001316.json](data_element_validation/test_data/TD-TC-001316.json) |
| TC-001317 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001317.json](data_element_validation/test_data/TD-TC-001317.json) |
| TC-001318 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001318.json](data_element_validation/test_data/TD-TC-001318.json) |
| TC-001319 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001319.json](data_element_validation/test_data/TD-TC-001319.json) |
| TC-001320 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001320.json](data_element_validation/test_data/TD-TC-001320.json) |
| TC-001321 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001321.json](data_element_validation/test_data/TD-TC-001321.json) |
| TC-001322 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001322.json](data_element_validation/test_data/TD-TC-001322.json) |
| TC-001323 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001323.json](data_element_validation/test_data/TD-TC-001323.json) |
| TC-001324 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001324.json](data_element_validation/test_data/TD-TC-001324.json) |
| TC-001325 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001325.json](data_element_validation/test_data/TD-TC-001325.json) |
| TC-001326 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001326.json](data_element_validation/test_data/TD-TC-001326.json) |
| TC-001327 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001327.json](data_element_validation/test_data/TD-TC-001327.json) |
| TC-001328 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001328.json](data_element_validation/test_data/TD-TC-001328.json) |
| TC-001329 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001329.json](data_element_validation/test_data/TD-TC-001329.json) |
| TC-001330 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001330.json](data_element_validation/test_data/TD-TC-001330.json) |
| TC-001331 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001331.json](data_element_validation/test_data/TD-TC-001331.json) |
| TC-001332 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001332.json](data_element_validation/test_data/TD-TC-001332.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 119 – Totals with Proprietary Data Load Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001333 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001333.json](data_element_validation/test_data/TD-TC-001333.json) |
| TC-001334 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001334.json](data_element_validation/test_data/TD-TC-001334.json) |
| TC-001335 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001335.json](data_element_validation/test_data/TD-TC-001335.json) |
| TC-001336 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001336.json](data_element_validation/test_data/TD-TC-001336.json) |
| TC-001337 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001337.json](data_element_validation/test_data/TD-TC-001337.json) |
| TC-001338 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001338.json](data_element_validation/test_data/TD-TC-001338.json) |
| TC-001339 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001339.json](data_element_validation/test_data/TD-TC-001339.json) |
| TC-001340 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001340.json](data_element_validation/test_data/TD-TC-001340.json) |
| TC-001341 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001341.json](data_element_validation/test_data/TD-TC-001341.json) |
| TC-001342 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001342.json](data_element_validation/test_data/TD-TC-001342.json) |
| TC-001343 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001343.json](data_element_validation/test_data/TD-TC-001343.json) |
| TC-001344 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001344.json](data_element_validation/test_data/TD-TC-001344.json) |
| TC-001345 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001345.json](data_element_validation/test_data/TD-TC-001345.json) |
| TC-001346 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001346.json](data_element_validation/test_data/TD-TC-001346.json) |
| TC-001347 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001347.json](data_element_validation/test_data/TD-TC-001347.json) |
| TC-001348 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001348.json](data_element_validation/test_data/TD-TC-001348.json) |
| TC-001349 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001349.json](data_element_validation/test_data/TD-TC-001349.json) |
| TC-001350 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001350.json](data_element_validation/test_data/TD-TC-001350.json) |
| TC-001351 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001351.json](data_element_validation/test_data/TD-TC-001351.json) |
| TC-001352 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001352.json](data_element_validation/test_data/TD-TC-001352.json) |
| TC-001353 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001353.json](data_element_validation/test_data/TD-TC-001353.json) |
| TC-001354 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001354.json](data_element_validation/test_data/TD-TC-001354.json) |
| TC-001355 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001355.json](data_element_validation/test_data/TD-TC-001355.json) |
| TC-001356 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001356.json](data_element_validation/test_data/TD-TC-001356.json) |
| TC-001357 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001357.json](data_element_validation/test_data/TD-TC-001357.json) |
| TC-001358 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001358.json](data_element_validation/test_data/TD-TC-001358.json) |
| TC-001359 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001359.json](data_element_validation/test_data/TD-TC-001359.json) |
| TC-001360 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001360.json](data_element_validation/test_data/TD-TC-001360.json) |
| TC-001361 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001361.json](data_element_validation/test_data/TD-TC-001361.json) |
| TC-001362 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001362.json](data_element_validation/test_data/TD-TC-001362.json) |
| TC-001363 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001363.json](data_element_validation/test_data/TD-TC-001363.json) |
| TC-001364 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001364.json](data_element_validation/test_data/TD-TC-001364.json) |
| TC-001365 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001365.json](data_element_validation/test_data/TD-TC-001365.json) |
| TC-001366 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001366.json](data_element_validation/test_data/TD-TC-001366.json) |
| TC-001367 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001367.json](data_element_validation/test_data/TD-TC-001367.json) |
| TC-001368 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001368.json](data_element_validation/test_data/TD-TC-001368.json) |
| TC-001369 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001369.json](data_element_validation/test_data/TD-TC-001369.json) |
| TC-001370 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001370.json](data_element_validation/test_data/TD-TC-001370.json) |
| TC-001371 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001371.json](data_element_validation/test_data/TD-TC-001371.json) |
| TC-001372 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001372.json](data_element_validation/test_data/TD-TC-001372.json) |
| TC-001373 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001373.json](data_element_validation/test_data/TD-TC-001373.json) |
| TC-001374 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001374.json](data_element_validation/test_data/TD-TC-001374.json) |
| TC-001375 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001375.json](data_element_validation/test_data/TD-TC-001375.json) |
| TC-001376 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001376.json](data_element_validation/test_data/TD-TC-001376.json) |
| TC-001377 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001377.json](data_element_validation/test_data/TD-TC-001377.json) |
| TC-001378 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001378.json](data_element_validation/test_data/TD-TC-001378.json) |
| TC-001379 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001379.json](data_element_validation/test_data/TD-TC-001379.json) |
| TC-001380 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001380.json](data_element_validation/test_data/TD-TC-001380.json) |
| TC-001381 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001381.json](data_element_validation/test_data/TD-TC-001381.json) |
| TC-001382 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001382.json](data_element_validation/test_data/TD-TC-001382.json) |
| TC-001383 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001383.json](data_element_validation/test_data/TD-TC-001383.json) |
| TC-001384 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001384.json](data_element_validation/test_data/TD-TC-001384.json) |
| TC-001385 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001385.json](data_element_validation/test_data/TD-TC-001385.json) |
| TC-001386 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001386.json](data_element_validation/test_data/TD-TC-001386.json) |
| TC-001387 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001387.json](data_element_validation/test_data/TD-TC-001387.json) |
| TC-001388 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001388.json](data_element_validation/test_data/TD-TC-001388.json) |
| TC-001389 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001389.json](data_element_validation/test_data/TD-TC-001389.json) |
| TC-001390 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001390.json](data_element_validation/test_data/TD-TC-001390.json) |
| TC-001391 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001391.json](data_element_validation/test_data/TD-TC-001391.json) |
| TC-001392 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001392.json](data_element_validation/test_data/TD-TC-001392.json) |
| TC-001393 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001393.json](data_element_validation/test_data/TD-TC-001393.json) |
| TC-001394 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001394.json](data_element_validation/test_data/TD-TC-001394.json) |
| TC-001395 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001395.json](data_element_validation/test_data/TD-TC-001395.json) |
| TC-001396 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001396.json](data_element_validation/test_data/TD-TC-001396.json) |
| TC-001397 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001397.json](data_element_validation/test_data/TD-TC-001397.json) |
| TC-001398 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001398.json](data_element_validation/test_data/TD-TC-001398.json) |
| TC-001399 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001399.json](data_element_validation/test_data/TD-TC-001399.json) |
| TC-001400 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001400.json](data_element_validation/test_data/TD-TC-001400.json) |
| TC-001401 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001401.json](data_element_validation/test_data/TD-TC-001401.json) |
| TC-001402 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001402.json](data_element_validation/test_data/TD-TC-001402.json) |
| TC-001403 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001403.json](data_element_validation/test_data/TD-TC-001403.json) |
| TC-001404 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001404.json](data_element_validation/test_data/TD-TC-001404.json) |
| TC-001405 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001405.json](data_element_validation/test_data/TD-TC-001405.json) |
| TC-001406 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001406.json](data_element_validation/test_data/TD-TC-001406.json) |
| TC-001407 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001407.json](data_element_validation/test_data/TD-TC-001407.json) |
| TC-001408 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001408.json](data_element_validation/test_data/TD-TC-001408.json) |
| TC-001409 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001409.json](data_element_validation/test_data/TD-TC-001409.json) |
| TC-001410 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001410.json](data_element_validation/test_data/TD-TC-001410.json) |
| TC-001411 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001411.json](data_element_validation/test_data/TD-TC-001411.json) |
| TC-001412 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001412.json](data_element_validation/test_data/TD-TC-001412.json) |
| TC-001413 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001413.json](data_element_validation/test_data/TD-TC-001413.json) |
| TC-001414 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001414.json](data_element_validation/test_data/TD-TC-001414.json) |
| TC-001415 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001415.json](data_element_validation/test_data/TD-TC-001415.json) |
| TC-001416 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001416.json](data_element_validation/test_data/TD-TC-001416.json) |
| TC-001417 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001417.json](data_element_validation/test_data/TD-TC-001417.json) |
| TC-001418 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001418.json](data_element_validation/test_data/TD-TC-001418.json) |
| TC-001419 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001419.json](data_element_validation/test_data/TD-TC-001419.json) |
| TC-001420 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001420.json](data_element_validation/test_data/TD-TC-001420.json) |
| TC-001421 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001421.json](data_element_validation/test_data/TD-TC-001421.json) |
| TC-001422 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001422.json](data_element_validation/test_data/TD-TC-001422.json) |
| TC-001423 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001423.json](data_element_validation/test_data/TD-TC-001423.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 123 – NFC Payment Tokenization Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 130 – EMV Request Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001424 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001424.json](data_element_validation/test_data/TD-TC-001424.json) |
| TC-001425 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001425.json](data_element_validation/test_data/TD-TC-001425.json) |
| TC-001426 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001426.json](data_element_validation/test_data/TD-TC-001426.json) |
| TC-001427 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001427.json](data_element_validation/test_data/TD-TC-001427.json) |
| TC-001428 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001428.json](data_element_validation/test_data/TD-TC-001428.json) |
| TC-001429 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001429.json](data_element_validation/test_data/TD-TC-001429.json) |
| TC-001430 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001430.json](data_element_validation/test_data/TD-TC-001430.json) |
| TC-001431 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001431.json](data_element_validation/test_data/TD-TC-001431.json) |
| TC-001432 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001432.json](data_element_validation/test_data/TD-TC-001432.json) |
| TC-001433 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001433.json](data_element_validation/test_data/TD-TC-001433.json) |
| TC-001434 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001434.json](data_element_validation/test_data/TD-TC-001434.json) |
| TC-001435 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001435.json](data_element_validation/test_data/TD-TC-001435.json) |
| TC-001436 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001436.json](data_element_validation/test_data/TD-TC-001436.json) |
| TC-001437 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001437.json](data_element_validation/test_data/TD-TC-001437.json) |
| TC-001438 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001438.json](data_element_validation/test_data/TD-TC-001438.json) |
| TC-001439 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001439.json](data_element_validation/test_data/TD-TC-001439.json) |
| TC-001440 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001440.json](data_element_validation/test_data/TD-TC-001440.json) |
| TC-001441 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001441.json](data_element_validation/test_data/TD-TC-001441.json) |
| TC-001442 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001442.json](data_element_validation/test_data/TD-TC-001442.json) |
| TC-001443 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001443.json](data_element_validation/test_data/TD-TC-001443.json) |
| TC-001444 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001444.json](data_element_validation/test_data/TD-TC-001444.json) |
| TC-001445 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001445.json](data_element_validation/test_data/TD-TC-001445.json) |
| TC-001446 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001446.json](data_element_validation/test_data/TD-TC-001446.json) |
| TC-001447 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001447.json](data_element_validation/test_data/TD-TC-001447.json) |
| TC-001448 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001448.json](data_element_validation/test_data/TD-TC-001448.json) |
| TC-001449 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001449.json](data_element_validation/test_data/TD-TC-001449.json) |
| TC-001450 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001450.json](data_element_validation/test_data/TD-TC-001450.json) |
| TC-001451 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001451.json](data_element_validation/test_data/TD-TC-001451.json) |
| TC-001452 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001452.json](data_element_validation/test_data/TD-TC-001452.json) |
| TC-001453 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001453.json](data_element_validation/test_data/TD-TC-001453.json) |
| TC-001454 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001454.json](data_element_validation/test_data/TD-TC-001454.json) |
| TC-001455 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001455.json](data_element_validation/test_data/TD-TC-001455.json) |
| TC-001456 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001456.json](data_element_validation/test_data/TD-TC-001456.json) |
| TC-001457 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Additio | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001457.json](data_element_validation/test_data/TD-TC-001457.json) |
| TC-001458 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Additio | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001458.json](data_element_validation/test_data/TD-TC-001458.json) |
| TC-001459 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Additio | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001459.json](data_element_validation/test_data/TD-TC-001459.json) |
| TC-001460 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001460.json](data_element_validation/test_data/TD-TC-001460.json) |
| TC-001461 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001461.json](data_element_validation/test_data/TD-TC-001461.json) |
| TC-001462 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Segment | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001462.json](data_element_validation/test_data/TD-TC-001462.json) |
| TC-001463 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001463.json](data_element_validation/test_data/TD-TC-001463.json) |
| TC-001464 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001464.json](data_element_validation/test_data/TD-TC-001464.json) |
| TC-001465 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001465.json](data_element_validation/test_data/TD-TC-001465.json) |
| TC-001466 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001466.json](data_element_validation/test_data/TD-TC-001466.json) |
| TC-001467 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001467.json](data_element_validation/test_data/TD-TC-001467.json) |
| TC-001468 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001468.json](data_element_validation/test_data/TD-TC-001468.json) |
| TC-001469 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001469.json](data_element_validation/test_data/TD-TC-001469.json) |
| TC-001470 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001470.json](data_element_validation/test_data/TD-TC-001470.json) |
| TC-001471 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001471.json](data_element_validation/test_data/TD-TC-001471.json) |
| TC-001472 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001472.json](data_element_validation/test_data/TD-TC-001472.json) |
| TC-001473 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001473.json](data_element_validation/test_data/TD-TC-001473.json) |
| TC-001474 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001474.json](data_element_validation/test_data/TD-TC-001474.json) |
| TC-001475 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001475.json](data_element_validation/test_data/TD-TC-001475.json) |
| TC-001476 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001476.json](data_element_validation/test_data/TD-TC-001476.json) |
| TC-001477 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001477.json](data_element_validation/test_data/TD-TC-001477.json) |
| TC-001478 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001478.json](data_element_validation/test_data/TD-TC-001478.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 132 – CA Public Key File Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001479 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001479.json](data_element_validation/test_data/TD-TC-001479.json) |
| TC-001480 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001480.json](data_element_validation/test_data/TD-TC-001480.json) |
| TC-001481 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001481.json](data_element_validation/test_data/TD-TC-001481.json) |
| TC-001482 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001482.json](data_element_validation/test_data/TD-TC-001482.json) |
| TC-001483 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001483.json](data_element_validation/test_data/TD-TC-001483.json) |
| TC-001484 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001484.json](data_element_validation/test_data/TD-TC-001484.json) |
| TC-001485 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001485.json](data_element_validation/test_data/TD-TC-001485.json) |
| TC-001486 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001486.json](data_element_validation/test_data/TD-TC-001486.json) |
| TC-001487 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001487.json](data_element_validation/test_data/TD-TC-001487.json) |
| TC-001488 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001488.json](data_element_validation/test_data/TD-TC-001488.json) |
| TC-001489 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001489.json](data_element_validation/test_data/TD-TC-001489.json) |
| TC-001490 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001490.json](data_element_validation/test_data/TD-TC-001490.json) |
| TC-001491 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001491.json](data_element_validation/test_data/TD-TC-001491.json) |
| TC-001492 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001492.json](data_element_validation/test_data/TD-TC-001492.json) |
| TC-001493 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001493.json](data_element_validation/test_data/TD-TC-001493.json) |
| TC-001494 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001494.json](data_element_validation/test_data/TD-TC-001494.json) |
| TC-001495 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001495.json](data_element_validation/test_data/TD-TC-001495.json) |
| TC-001496 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001496.json](data_element_validation/test_data/TD-TC-001496.json) |
| TC-001497 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001497.json](data_element_validation/test_data/TD-TC-001497.json) |
| TC-001498 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001498.json](data_element_validation/test_data/TD-TC-001498.json) |
| TC-001499 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001499.json](data_element_validation/test_data/TD-TC-001499.json) |
| TC-001500 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001500.json](data_element_validation/test_data/TD-TC-001500.json) |
| TC-001501 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001501.json](data_element_validation/test_data/TD-TC-001501.json) |
| TC-001502 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001502.json](data_element_validation/test_data/TD-TC-001502.json) |
| TC-001503 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001503.json](data_element_validation/test_data/TD-TC-001503.json) |
| TC-001504 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001504.json](data_element_validation/test_data/TD-TC-001504.json) |
| TC-001505 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001505.json](data_element_validation/test_data/TD-TC-001505.json) |
| TC-001506 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001506.json](data_element_validation/test_data/TD-TC-001506.json) |
| TC-001507 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001507.json](data_element_validation/test_data/TD-TC-001507.json) |
| TC-001508 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001508.json](data_element_validation/test_data/TD-TC-001508.json) |
| TC-001509 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001509.json](data_element_validation/test_data/TD-TC-001509.json) |
| TC-001510 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001510.json](data_element_validation/test_data/TD-TC-001510.json) |
| TC-001511 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001511.json](data_element_validation/test_data/TD-TC-001511.json) |
| TC-001512 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001512.json](data_element_validation/test_data/TD-TC-001512.json) |
| TC-001513 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001513.json](data_element_validation/test_data/TD-TC-001513.json) |
| TC-001514 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001514.json](data_element_validation/test_data/TD-TC-001514.json) |
| TC-001515 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001515.json](data_element_validation/test_data/TD-TC-001515.json) |
| TC-001516 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001516.json](data_element_validation/test_data/TD-TC-001516.json) |
| TC-001517 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001517.json](data_element_validation/test_data/TD-TC-001517.json) |
| TC-001518 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001518.json](data_element_validation/test_data/TD-TC-001518.json) |
| TC-001519 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001519.json](data_element_validation/test_data/TD-TC-001519.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 135 – Moneris Data Segment (Request) @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 145 – Enhanced Fleet Request @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 151 – Request InComm OTC Market Basket Data @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 153 – Network Token Data Request @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 156 – EV Charging Data Segment @ General
- Status: valid; context status: None
- **No test cases** (see coverage/composition_gaps.csv)

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Software Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001555 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001555.json](data_element_validation/test_data/TD-TC-001555.json) |
| TC-001556 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001556.json](data_element_validation/test_data/TD-TC-001556.json) |
| TC-001557 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001557.json](data_element_validation/test_data/TD-TC-001557.json) |
| TC-001558 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001558.json](data_element_validation/test_data/TD-TC-001558.json) |
| TC-001559 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001559.json](data_element_validation/test_data/TD-TC-001559.json) |
| TC-001560 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001560.json](data_element_validation/test_data/TD-TC-001560.json) |
| TC-001561 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001561.json](data_element_validation/test_data/TD-TC-001561.json) |
| TC-001562 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001562.json](data_element_validation/test_data/TD-TC-001562.json) |
| TC-001563 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001563.json](data_element_validation/test_data/TD-TC-001563.json) |
| TC-001564 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001564.json](data_element_validation/test_data/TD-TC-001564.json) |
| TC-001565 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001565.json](data_element_validation/test_data/TD-TC-001565.json) |
| TC-001566 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001566.json](data_element_validation/test_data/TD-TC-001566.json) |
| TC-001567 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001567.json](data_element_validation/test_data/TD-TC-001567.json) |
| TC-001568 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001568.json](data_element_validation/test_data/TD-TC-001568.json) |
| TC-001569 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001569.json](data_element_validation/test_data/TD-TC-001569.json) |
| TC-001570 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001570.json](data_element_validation/test_data/TD-TC-001570.json) |
| TC-001571 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001571.json](data_element_validation/test_data/TD-TC-001571.json) |
| TC-001572 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001572.json](data_element_validation/test_data/TD-TC-001572.json) |
| TC-001573 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001573.json](data_element_validation/test_data/TD-TC-001573.json) |
| TC-001574 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001574.json](data_element_validation/test_data/TD-TC-001574.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Software Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001775 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001775.json](data_element_validation/test_data/TD-TC-001775.json) |
| TC-001776 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001776.json](data_element_validation/test_data/TD-TC-001776.json) |
| TC-001777 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001777.json](data_element_validation/test_data/TD-TC-001777.json) |
| TC-001778 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001778.json](data_element_validation/test_data/TD-TC-001778.json) |
| TC-001779 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001779.json](data_element_validation/test_data/TD-TC-001779.json) |
| TC-001780 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001780.json](data_element_validation/test_data/TD-TC-001780.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Table Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001575 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001575.json](data_element_validation/test_data/TD-TC-001575.json) |
| TC-001576 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001576.json](data_element_validation/test_data/TD-TC-001576.json) |
| TC-001577 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001577.json](data_element_validation/test_data/TD-TC-001577.json) |
| TC-001578 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001578.json](data_element_validation/test_data/TD-TC-001578.json) |
| TC-001579 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001579.json](data_element_validation/test_data/TD-TC-001579.json) |
| TC-001580 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001580.json](data_element_validation/test_data/TD-TC-001580.json) |
| TC-001581 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001581.json](data_element_validation/test_data/TD-TC-001581.json) |
| TC-001582 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001582.json](data_element_validation/test_data/TD-TC-001582.json) |
| TC-001583 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001583.json](data_element_validation/test_data/TD-TC-001583.json) |
| TC-001584 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001584.json](data_element_validation/test_data/TD-TC-001584.json) |
| TC-001585 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001585.json](data_element_validation/test_data/TD-TC-001585.json) |
| TC-001586 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001586.json](data_element_validation/test_data/TD-TC-001586.json) |
| TC-001587 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001587.json](data_element_validation/test_data/TD-TC-001587.json) |
| TC-001588 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001588.json](data_element_validation/test_data/TD-TC-001588.json) |
| TC-001589 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001589.json](data_element_validation/test_data/TD-TC-001589.json) |
| TC-001590 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001590.json](data_element_validation/test_data/TD-TC-001590.json) |
| TC-001591 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-001591.json](data_element_validation/test_data/TD-TC-001591.json) |
| TC-001592 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001592.json](data_element_validation/test_data/TD-TC-001592.json) |
| TC-001593 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001593.json](data_element_validation/test_data/TD-TC-001593.json) |
| TC-001594 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001594.json](data_element_validation/test_data/TD-TC-001594.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Table Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001763 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001763.json](data_element_validation/test_data/TD-TC-001763.json) |
| TC-001764 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001764.json](data_element_validation/test_data/TD-TC-001764.json) |
| TC-001765 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001765.json](data_element_validation/test_data/TD-TC-001765.json) |
| TC-001766 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001766.json](data_element_validation/test_data/TD-TC-001766.json) |
| TC-001767 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001767.json](data_element_validation/test_data/TD-TC-001767.json) |
| TC-001768 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001768.json](data_element_validation/test_data/TD-TC-001768.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Totals Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001690 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001690.json](data_element_validation/test_data/TD-TC-001690.json) |
| TC-001691 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001691.json](data_element_validation/test_data/TD-TC-001691.json) |
| TC-001692 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | ready | BR-ATL105-TRANSARMOR-66-5 p.66 | 1 |  | [TD-TC-001692.json](data_element_validation/test_data/TD-TC-001692.json) |
| TC-001693 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | needs_client_input | BR-179-3 p.179; BR-235-2 p.235 | 1 |  | [TD-TC-001693.json](data_element_validation/test_data/TD-TC-001693.json) |
| TC-001694 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | needs_client_input | p.[219, 395] | 1 |  | [TD-TC-001694.json](data_element_validation/test_data/TD-TC-001694.json) |
| TC-001695 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | needs_client_input | BR-179-3 p.179; BR-235-2 p.235 | 1 |  | [TD-TC-001695.json](data_element_validation/test_data/TD-TC-001695.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Totals with Proprietary Data Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001729 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001729.json](data_element_validation/test_data/TD-TC-001729.json) |
| TC-001730 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001730.json](data_element_validation/test_data/TD-TC-001730.json) |
| TC-001731 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001731.json](data_element_validation/test_data/TD-TC-001731.json) |
| TC-001732 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001732.json](data_element_validation/test_data/TD-TC-001732.json) |
| TC-001733 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001733.json](data_element_validation/test_data/TD-TC-001733.json) |
| TC-001734 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001734.json](data_element_validation/test_data/TD-TC-001734.json) |
| TC-001735 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001735.json](data_element_validation/test_data/TD-TC-001735.json) |
| TC-001736 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001736.json](data_element_validation/test_data/TD-TC-001736.json) |
| TC-001737 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001737.json](data_element_validation/test_data/TD-TC-001737.json) |
| TC-001738 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001738.json](data_element_validation/test_data/TD-TC-001738.json) |
| TC-001739 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001739.json](data_element_validation/test_data/TD-TC-001739.json) |
| TC-001740 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001740.json](data_element_validation/test_data/TD-TC-001740.json) |
| TC-001741 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001741.json](data_element_validation/test_data/TD-TC-001741.json) |
| TC-001742 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001742.json](data_element_validation/test_data/TD-TC-001742.json) |
| TC-001743 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001743.json](data_element_validation/test_data/TD-TC-001743.json) |
| TC-001744 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | BR-180-3 p.180; BR-263-2 p.263; BR-34-8 p.34 | 1 |  | [TD-TC-001744.json](data_element_validation/test_data/TD-TC-001744.json) |
| TC-001745 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | BR-180-3 p.180; BR-263-2 p.263 | 1 |  | [TD-TC-001745.json](data_element_validation/test_data/TD-TC-001745.json) |
| TC-001746 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | p.[219, 395] | 1 |  | [TD-TC-001746.json](data_element_validation/test_data/TD-TC-001746.json) |
| TC-001747 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | needs_client_input | BR-180-3 p.180; BR-263-2 p.263 | 1 |  | [TD-TC-001747.json](data_element_validation/test_data/TD-TC-001747.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation TransArmor Key Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001702 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001702.json](data_element_validation/test_data/TD-TC-001702.json) |
| TC-001703 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-001703.json](data_element_validation/test_data/TD-TC-001703.json) |
| TC-001704 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001704.json](data_element_validation/test_data/TD-TC-001704.json) |
| TC-001705 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001705.json](data_element_validation/test_data/TD-TC-001705.json) |
| TC-001706 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-001706.json](data_element_validation/test_data/TD-TC-001706.json) |
| TC-001707 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001707.json](data_element_validation/test_data/TD-TC-001707.json) |
| TC-001708 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-001708.json](data_element_validation/test_data/TD-TC-001708.json) |
| TC-001709 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | BR-ATL105-TRANSARMOR-41-1 p.41; BR-ATL105-TRANSARMOR-41-2 p.41; BR-ATL105-TRANSARMOR-66-1 p.66 | 1 |  | [TD-TC-001709.json](data_element_validation/test_data/TD-TC-001709.json) |
| TC-001710 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | needs_client_input | BR-ATL105-TRANSARMOR-41-1 p.41 | 1 |  | [TD-TC-001710.json](data_element_validation/test_data/TD-TC-001710.json) |
| TC-001711 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | needs_client_input | p.[395] | 1 |  | [TD-TC-001711.json](data_element_validation/test_data/TD-TC-001711.json) |
| TC-001712 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | needs_client_input | BR-ATL105-TRANSARMOR-41-1 p.41 | 1 |  | [TD-TC-001712.json](data_element_validation/test_data/TD-TC-001712.json) |

## BUYPASS ATL105 Terminal & Host Operations (`BR-A0098BFB`)

Supporting citations: p.77; p.522

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001984 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS CA Public Key File Load Request: | Terminal & host operation | Positive | needs_client_input | p.274; p.404; p.511 | 1 | OP_CA_PUBLIC_KEY_LOAD | [TD-TC-001984.json](terminal_and_host_operations/test_data/TD-TC-001984.json) |
| TC-001985 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS CA Public Key File Load Request: | Terminal & host operation | Negative | needs_client_input | p.274; p.404; p.404 | 1 | OP_CA_PUBLIC_KEY_LOAD | [TD-TC-001985.json](terminal_and_host_operations/test_data/TD-TC-001985.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001986 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Communications Test (Transaction | Terminal & host operation | Positive | ready | p.187; p.59; p.404 | 1 | OP_COMMUNICATIONS_TEST | [TD-TC-001986.json](terminal_and_host_operations/test_data/TD-TC-001986.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001787 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sto | Device-only | Negative | requires_review | p.520; p.335; p.335 | 1 |  | - |
| TC-001788 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Ret | Device-only | Negative | ready | p.520; p.511; p.83 | 1 |  | - |
| TC-001789 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Voi | Device-only | Negative | ready | p.520; p.511; p.83 | 1 |  | - |
| TC-001790 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Pre | Device-only | Negative | ready | p.520; p.511; p.83 | 1 |  | - |
| TC-001791 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-110-1 p.110; BR-FISERV-EMV-GUIDE-111-1 p.111; BR-FISERV-EMV-GUIDE-111-2 p.111 | 1 |  | - |
| TC-001792 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-27 p.115; BR-FISERV-EMV-GUIDE-115-6 p.115; p.511 | 1 |  | - |
| TC-001793 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-313-1 p.313; BR-313-2 p.313; p.511 | 1 |  | - |
| TC-001794 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Bef | Terminal & host operation | Negative | ready | p.189; p.189; p.511 | 1 |  | - |
| TC-001795 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-59-3 p.59; BR-FISERV-EMV-GUIDE-59-4 p.59; p.511 | 1 |  | - |
| TC-001796 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-639-6 p.639; BR-639-7 p.639; p.511 | 1 |  | - |
| TC-001797 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Soft constraint | Positive | requires_review | BR-BUYPASS-PIPS-136-1 p.136; p.511; p.83 | 1 |  | - |
| TC-001798 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-120-11 p.120; p.511; p.83 | 1 |  | - |
| TC-001799 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Loc | Terminal & host operation | Positive | ready | p.71; p.72; p.66 | 1 |  | - |
| TC-001800 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-195-2 p.195; p.511; p.83 | 1 |  | - |
| TC-001801 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-712-2 p.712; p.511; p.83 | 1 |  | - |
| TC-001802 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-49-4 p.49; p.511; p.83 | 1 |  | - |
| TC-001803 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sof | Soft constraint | Positive | requires_review | BR-330-4 p.330; p.511; p.83 | 1 |  | - |
| TC-001804 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Soft constraint | Positive | requires_review | BR-BUYPASS-PIPS-18-4 p.18; p.511; p.83 | 1 |  | - |
| TC-001805 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-175-3 p.175; p.511; p.83 | 1 |  | - |
| TC-001806 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Running total / state | Positive | requires_review | BR-63-6 p.63; p.511; p.83 | 1 |  | - |
| TC-001807 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-199-2 p.199; p.511; p.83 | 1 |  | - |
| TC-001808 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-403-3 p.403; p.511; p.83 | 1 |  | - |
| TC-001809 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-404-11 p.404; p.511; p.83 | 1 |  | - |
| TC-001810 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-404-12 p.404; p.511; p.83 | 1 |  | - |
| TC-001811 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-404-7 p.404; p.511; p.83 | 1 |  | - |
| TC-001812 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-404-8 p.404; p.511; p.83 | 1 |  | - |
| TC-001813 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-72-2 p.72; p.511; p.83 | 1 |  | - |
| TC-001814 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-FISERV-EMV-GUIDE-130-1 p.130; p.511; p.83 | 1 |  | - |
| TC-001815 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Run | Running total / state | Positive | requires_review | BR-FISERV-EMV-GUIDE-135-1 p.135; p.511; p.83 | 1 |  | - |
| TC-001816 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Ord | Ordering | Positive | requires_review | BR-ATL105-TRANSARMOR-67-1 p.67; p.511; p.83 | 1 |  | - |
| TC-001817 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Ord | Ordering | Positive | requires_review | BR-718-3 p.718; p.511; p.83 | 1 |  | - |
| TC-001818 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Ord | Ordering | Positive | requires_review | BR-ATL105-TRANSARMOR-67-4 p.67; p.511; p.83 | 1 |  | - |
| TC-001819 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Ord | Ordering | Positive | requires_review | BR-47-7 p.47; p.511; p.83 | 1 |  | - |
| TC-001820 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Ord | Ordering | Positive | requires_review | BR-718-6 p.718; p.511; p.83 | 1 |  | - |
| TC-001821 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Co- | Co-requirement / mutual exclusion | Negative | requires_review | BR-588-1 p.588; p.511; p.83 | 1 |  | - |
| TC-001822 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Co- | Co-requirement / mutual exclusion | Negative | requires_review | BR-FISERV-EMV-GUIDE-79-4 p.79; p.511; p.83 | 1 |  | - |
| TC-001823 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-46-3 p.46; p.511; p.83 | 1 |  | - |
| TC-001824 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-49-5 p.49; p.511; p.83 | 1 |  | - |
| TC-001825 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-87-8 p.87; p.511; p.83 | 1 |  | - |
| TC-001826 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-577-3 p.577; p.511; p.83 | 1 |  | - |
| TC-001827 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-54-6 p.54; p.511; p.83 | 1 |  | - |
| TC-001828 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-74-4 p.74; p.511; p.83 | 1 |  | - |
| TC-001829 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-43-7 p.43; p.511; p.83 | 1 |  | - |
| TC-001830 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-88-14 p.88; p.511; p.83 | 1 |  | - |
| TC-001831 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-74-6 p.74; p.511; p.83 | 1 |  | - |
| TC-001832 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-656-3 p.656; p.511; p.83 | 1 |  | - |
| TC-001833 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-ATL105-TRANSARMOR-14-2 p.14; p.511; p.83 | 1 |  | - |
| TC-001834 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Rou | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-125-1 p.125; p.511; p.83 | 1 |  | - |
| TC-001835 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 |  | - |
| TC-001836 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-63-2 p.63; p.511; p.83 | 1 |  | - |
| TC-001837 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-96-1 p.96; p.511; p.83 | 1 |  | - |
| TC-001838 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-95-2 p.95; p.511; p.83 | 1 |  | - |
| TC-001839 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-100-6 p.100; BR-FISERV-EMV-GUIDE-100-7 p.100; BR-FISERV-EMV-GUIDE-100-8 p.100 | 1 |  | - |
| TC-001840 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-102-1 p.102; BR-FISERV-EMV-GUIDE-102-2 p.102; BR-FISERV-EMV-GUIDE-102-4 p.102 | 1 |  | - |
| TC-001841 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-161-10 p.161; BR-FISERV-EMV-GUIDE-161-11 p.161; BR-FISERV-EMV-GUIDE-161-12 p.161 | 1 |  | - |
| TC-001842 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-161-9 p.161; BR-FISERV-EMV-GUIDE-162-2 p.162; BR-FISERV-EMV-GUIDE-163-11 p.163 | 1 |  | - |
| TC-001843 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-18-2 p.18; BR-BUYPASS-DCT-18-4 p.18; BR-BUYPASS-DCT-21-2 p.21 | 1 |  | - |
| TC-001844 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-343-1 p.343; BR-343-2 p.343; BR-343-3 p.343 | 1 |  | - |
| TC-001845 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-364-4 p.364; BR-370-6 p.370; BR-374-3 p.374 | 1 |  | - |
| TC-001846 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-400-4 p.400; BR-400-5 p.400; BR-401-1 p.401 | 1 |  | - |
| TC-001847 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-416-2 p.416; BR-416-3 p.416; BR-416-4 p.416 | 1 |  | - |
| TC-001848 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-42-3 p.42; BR-FISERV-EMV-GUIDE-42-4 p.42; BR-FISERV-EMV-GUIDE-42-6 p.42 | 1 |  | - |
| TC-001849 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-470-1 p.470; BR-470-4 p.470; BR-471-1 p.471 | 1 |  | - |
| TC-001850 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-63-6 p.63; BR-FISERV-EMV-GUIDE-67-1 p.67; BR-FISERV-EMV-GUIDE-67-5 p.67 | 1 |  | - |
| TC-001851 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-81-10 p.81; BR-81-11 p.81; BR-81-12 p.81 | 1 |  | - |
| TC-001852 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-91-5 p.91; BR-91-6 p.91; BR-91-9 p.91 | 1 |  | - |
| TC-001853 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-98-1 p.98; BR-FISERV-EMV-GUIDE-98-2 p.98; BR-FISERV-EMV-GUIDE-98-3 p.98 | 1 |  | - |
| TC-001854 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-138-1 p.138; BR-138-10 p.138; p.511 | 1 |  | - |
| TC-001855 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-141-3 p.141; BR-FISERV-EMV-GUIDE-142-2 p.142; p.511 | 1 |  | - |
| TC-001856 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-160-10 p.160; BR-160-9 p.160; p.511 | 1 |  | - |
| TC-001857 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-171-1 p.171; BR-172-2 p.172; p.511 | 1 |  | - |
| TC-001858 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-189-2 p.189; BR-200-1 p.200; p.511 | 1 |  | - |
| TC-001859 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-27-1 p.27; BR-ATL105-TRANSARMOR-27-2 p.27; p.511 | 1 |  | - |
| TC-001860 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-27-11 p.27; BR-BUYPASS-PIPS-27-4 p.27; p.511 | 1 |  | - |
| TC-001861 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-33-1 p.33; BR-BUYPASS-PIPS-33-4 p.33; p.511 | 1 |  | - |
| TC-001862 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-34-2 p.34; BR-BUYPASS-PIPS-34-6 p.34; p.511 | 1 |  | - |
| TC-001863 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-547-1 p.547; BR-548-1 p.548; p.511 | 1 |  | - |
| TC-001864 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-81-2 p.81; BR-FISERV-EMV-GUIDE-84-6 p.84; p.511 | 1 |  | - |
| TC-001865 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-34-1 p.34; BR-34-2 p.34; BR-34-4 p.34 | 1 |  | - |
| TC-001866 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-44-2 p.44; BR-45-7 p.45; BR-47-8 p.47 | 1 |  | - |
| TC-001867 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-66-1 p.66; BR-66-4 p.66; BR-66-5 p.66 | 1 |  | - |
| TC-001868 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-8-3 p.8; BR-BUYPASS-DCT-11-1 p.11; BR-BUYPASS-DCT-11-2 p.11 | 1 |  | - |
| TC-001869 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-107-5 p.107; BR-FISERV-EMV-GUIDE-107-7 p.107; BR-FISERV-EMV-GUIDE-108-1 p.108 | 1 |  | - |
| TC-001870 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-131-10 p.131; BR-BUYPASS-PIPS-131-11 p.131; BR-BUYPASS-PIPS-131-6 p.131 | 1 |  | - |
| TC-001871 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-266-1 p.266; BR-266-2 p.266; BR-266-3 p.266 | 1 |  | - |
| TC-001872 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-35-2 p.35; BR-BUYPASS-PIPS-35-3 p.35; BR-BUYPASS-PIPS-35-4 p.35 | 1 |  | - |
| TC-001873 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-2 p.37; BR-FISERV-EMV-GUIDE-37-8 p.37; BR-FISERV-EMV-GUIDE-37-9 p.37 | 1 |  | - |
| TC-001874 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-476-2 p.476; BR-476-3 p.476; BR-481-3 p.481 | 1 |  | - |
| TC-001875 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-61-5 p.61; BR-62-1 p.62; BR-62-10 p.62 | 1 |  | - |
| TC-001876 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-252-1 p.252; BR-252-2 p.252; BR-252-3 p.252 | 1 |  | - |
| TC-001877 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-71-1 p.71; BR-72-3 p.72; BR-76-1 p.76 | 1 |  | - |
| TC-001878 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-93-2 p.93; BR-93-3 p.93; BR-93-4 p.93 | 1 |  | - |
| TC-001879 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-16-10 p.16; BR-BUYPASS-PIPS-16-11 p.16; BR-BUYPASS-PIPS-16-5 p.16 | 1 |  | - |
| TC-001880 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-518-5 p.518; BR-518-6 p.518; BR-519-1 p.519 | 1 |  | - |
| TC-001881 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-254-6 p.254; BR-257-3 p.257; BR-258-1 p.258 | 1 |  | - |
| TC-001882 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-57-4 p.57; BR-ATL105-TRANSARMOR-62-1 p.62; BR-ATL105-TRANSARMOR-62-2 p.62 | 1 |  | - |
| TC-001883 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-173-1 p.173; BR-BUYPASS-PIPS-174-1 p.174; BR-BUYPASS-PIPS-174-2 p.174 | 1 |  | - |
| TC-001884 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-39-3 p.39; BR-BUYPASS-DCT-39-4 p.39; BR-BUYPASS-DCT-39-5 p.39 | 1 |  | - |
| TC-001885 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-217-3 p.217; p.511; p.83 | 1 |  | - |
| TC-001886 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-44-8 p.44; p.511; p.83 | 1 |  | - |
| TC-001887 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-545-6 p.545; p.511; p.83 | 1 |  | - |
| TC-001888 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-658-1 p.658; p.511; p.83 | 1 |  | - |
| TC-001889 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-48-3 p.48; p.511; p.83 | 1 |  | - |
| TC-001890 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-701-3 p.701; p.511; p.83 | 1 |  | - |
| TC-001891 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-32-2 p.32; p.511; p.83 | 1 |  | - |
| TC-001892 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-20-2 p.20; p.511; p.83 | 1 |  | - |
| TC-001893 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-140-6 p.140; p.511; p.83 | 1 |  | - |
| TC-001894 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-17-1 p.17; p.511; p.83 | 1 |  | - |
| TC-001895 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-28-6 p.28; p.511; p.83 | 1 |  | - |
| TC-001896 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-29-7 p.29; p.511; p.83 | 1 |  | - |
| TC-001897 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-210-3 p.210; p.511; p.83 | 1 |  | - |
| TC-001898 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-23-3 p.23; p.511; p.83 | 1 |  | - |
| TC-001899 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-59-2 p.59; p.511; p.83 | 1 |  | - |
| TC-001900 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-188-3 p.188; p.511; p.83 | 1 |  | - |
| TC-001901 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-157-5 p.157; p.511; p.83 | 1 |  | - |
| TC-001902 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-12-2 p.12; p.511; p.83 | 1 |  | - |
| TC-001903 BUYPASS ATL105 Terminal & Host Operations SAMPLE Hospitality POS Device & terminal behavio | Device checkpoint | Positive | requires_review | BR-64-6 p.64; p.511; p.83 | 1 |  | - |
| TC-001904 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-BUYPASS-MTAP-44-2 p.44; p.511; p.83 | 1 |  | - |
| TC-001905 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-577-5 p.577; p.511; p.83 | 1 |  | - |
| TC-001906 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-46-6 p.46; p.511; p.83 | 1 |  | - |
| TC-001907 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 |  | - |
| TC-001908 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-61-2 p.61; p.511; p.83 | 1 |  | - |
| TC-001909 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-18-9 p.18; p.511; p.83 | 1 |  | - |
| TC-001910 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-183-2 p.183; BR-FISERV-EMV-GUIDE-183-3 p.183; BR-FISERV-EMV-GUIDE-183-7 p.183 | 1 |  | - |
| TC-001911 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-24-2 p.24; BR-FISERV-EMV-GUIDE-26-1 p.26; BR-FISERV-EMV-GUIDE-26-2 p.26 | 1 |  | - |
| TC-001912 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-33-5 p.33; BR-FISERV-EMV-GUIDE-33-7 p.33; BR-FISERV-EMV-GUIDE-34-1 p.34 | 1 |  | - |
| TC-001913 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-42-1 p.42; BR-FISERV-EMV-GUIDE-42-5 p.42; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 |  | - |
| TC-001914 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-66-3 p.66; BR-FISERV-EMV-GUIDE-66-4 p.66; BR-FISERV-EMV-GUIDE-66-7 p.66 | 1 |  | - |
| TC-001915 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-8 p.71; BR-FISERV-EMV-GUIDE-72-11 p.72; BR-FISERV-EMV-GUIDE-72-2 p.72 | 1 |  | - |
| TC-001916 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-13-5 p.13; BR-ATL105-TRANSARMOR-13-7 p.13; p.511 | 1 |  | - |
| TC-001917 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-131-3 p.131; BR-BUYPASS-PIPS-131-4 p.131; p.511 | 1 |  | - |
| TC-001918 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-135-3 p.135; BR-FISERV-EMV-GUIDE-135-4 p.135; p.511 | 1 |  | - |
| TC-001919 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-178-3 p.178; BR-FISERV-EMV-GUIDE-178-4 p.178; p.511 | 1 |  | - |
| TC-001920 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-184-1 p.184; BR-185-1 p.185; p.511 | 1 |  | - |
| TC-001921 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-27-7 p.27; BR-BUYPASS-PIPS-27-9 p.27; p.511 | 1 |  | - |
| TC-001922 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-33-2 p.33; BR-ATL105-TRANSARMOR-33-4 p.33; p.511 | 1 |  | - |
| TC-001923 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-503-1 p.503; BR-504-1 p.504; p.511 | 1 |  | - |
| TC-001924 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-169-3 p.169; BR-FISERV-EMV-GUIDE-169-6 p.169; BR-FISERV-EMV-GUIDE-173-2 p.173 | 1 |  | - |
| TC-001925 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-40-5 p.40; BR-ATL105-TRANSARMOR-40-6 p.40; BR-ATL105-TRANSARMOR-40-7 p.40 | 1 |  | - |
| TC-001926 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-81-4 p.81; BR-81-5 p.81; BR-86-4 p.86 | 1 |  | - |
| TC-001927 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-94-1 p.94; BR-FISERV-EMV-GUIDE-95-1 p.95; BR-FISERV-EMV-GUIDE-96-3 p.96 | 1 |  | - |
| TC-001928 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-112-2 p.112; BR-FISERV-EMV-GUIDE-113-2 p.113; BR-FISERV-EMV-GUIDE-114-1 p.114 | 1 |  | - |
| TC-001929 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-74-9 p.74; BR-FISERV-EMV-GUIDE-76-11 p.76; BR-FISERV-EMV-GUIDE-78-4 p.78 | 1 |  | - |
| TC-001930 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-40-12 p.40; BR-40-3 p.40; BR-40-6 p.40 | 1 |  | - |
| TC-001931 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-175-2 p.175; BR-BUYPASS-PIPS-177-2 p.177; BR-BUYPASS-PIPS-177-3 p.177 | 1 |  | - |
| TC-001932 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-151-3 p.151; BR-FISERV-EMV-GUIDE-152-10 p.152; BR-FISERV-EMV-GUIDE-152-11 p.152 | 1 |  | - |
| TC-001933 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-52-1 p.52; BR-FISERV-EMV-GUIDE-53-1 p.53; BR-FISERV-EMV-GUIDE-56-11 p.56 | 1 |  | - |
| TC-001934 BUYPASS ATL105 Terminal & Host Operations SAMPLE Hospitality POS Device & terminal behavio | Device checkpoint | Positive | requires_review | BR-64-2 p.64; p.511; p.83 | 1 |  | - |
| TC-001935 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-370-2 p.370; p.511; p.83 | 1 |  | - |
| TC-001936 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-19-1 p.19; p.511; p.83 | 1 |  | - |
| TC-001937 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum POS Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-140-3 p.140; p.511; p.83 | 1 |  | - |
| TC-001938 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-162-5 p.162; p.511; p.83 | 1 |  | - |
| TC-001939 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-701-2 p.701; p.511; p.83 | 1 |  | - |
| TC-001940 BUYPASS ATL105 Terminal & Host Operations SAMPLE Petroleum CAT Device & terminal behaviour | Device checkpoint | Positive | requires_review | BR-62-4 p.62; p.511; p.83 | 1 |  | - |
| TC-001941 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-53-5 p.53; p.511; p.83 | 1 |  | - |
| TC-001942 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-189-4 p.189; p.511; p.83 | 1 |  | - |
| TC-001943 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-162-1 p.162; p.511; p.83 | 1 |  | - |
| TC-001944 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-46-1 p.46; p.511; p.83 | 1 |  | - |
| TC-001945 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 |  | - |
| TC-001946 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-36-7 p.36; p.511; p.83 | 1 |  | - |
| TC-001947 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-26-1 p.26; p.511; p.83 | 1 |  | - |
| TC-001948 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-BUYPASS-IIN-4-3 p.4; p.511; p.83 | 1 |  | - |
| TC-001949 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-35-9 p.35; p.511; p.83 | 1 |  | - |
| TC-001950 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Dev | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-16-6 p.16; p.511; p.83 | 1 |  | - |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001987 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.190; p.189; p.511 | 1 | OP_TABLE_LOAD | [TD-TC-001987.json](terminal_and_host_operations/test_data/TD-TC-001987.json) |
| TC-001988 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.193; p.189; p.511 | 1 | OP_PHONE_LOAD | [TD-TC-001988.json](terminal_and_host_operations/test_data/TD-TC-001988.json) |
| TC-001989 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.195; p.189; p.511 | 1 | OP_DATE_TIME_LOAD | [TD-TC-001989.json](terminal_and_host_operations/test_data/TD-TC-001989.json) |
| TC-001990 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.197; p.189; p.511 | 1 | OP_SOFTWARE_LOAD | [TD-TC-001990.json](terminal_and_host_operations/test_data/TD-TC-001990.json) |
| TC-001991 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.202; p.189; p.511 | 1 | OP_MONERIS_KEY_LOAD | [TD-TC-001991.json](terminal_and_host_operations/test_data/TD-TC-001991.json) |
| TC-001992 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Negative | needs_client_input | p.190; p.511; p.83 | 1 | OP_TABLE_LOAD | [TD-TC-001992.json](terminal_and_host_operations/test_data/TD-TC-001992.json) |
| TC-001993 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.189; p.189; p.511 | 1 | Purchase/Capture | [TD-TC-001993.step1.purchase.json](terminal_and_host_operations/test_data/TD-TC-001993.step1.purchase.json) |
|  |  |  |  |  | 2 | OP_TABLE_LOAD | [TD-TC-001993.step2.table-load.json](terminal_and_host_operations/test_data/TD-TC-001993.step2.table-load.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001964 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Positive | needs_client_input | p.522; p.511; p.83 | 1 | OP_EMAIL_RETRIEVE | [TD-TC-001964.json](terminal_and_host_operations/test_data/TD-TC-001964.json) |
| TC-001965 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Positive | needs_client_input | p.522; p.511; p.83 | 1 | OP_EMAIL_PROPRIETARY | [TD-TC-001965.json](terminal_and_host_operations/test_data/TD-TC-001965.json) |
| TC-001966 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Reset t | Terminal & host operation | Positive | needs_client_input | p.522; p.511; p.83 | 1 | OP_EMAIL_RESET | [TD-TC-001966.json](terminal_and_host_operations/test_data/TD-TC-001966.json) |
| TC-001967 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Submit  | Terminal & host operation | Positive | needs_client_input | p.522; p.511; p.83 | 1 | OP_EMAIL_SUBMIT | [TD-TC-001967.json](terminal_and_host_operations/test_data/TD-TC-001967.json) |
| TC-001968 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_RETRIEVE | [TD-TC-001968.json](terminal_and_host_operations/test_data/TD-TC-001968.json) |
| TC-001969 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_PROPRIETARY | [TD-TC-001969.json](terminal_and_host_operations/test_data/TD-TC-001969.json) |
| TC-001970 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Reset t | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_RESET | [TD-TC-001970.json](terminal_and_host_operations/test_data/TD-TC-001970.json) |
| TC-001971 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Submit  | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_SUBMIT | [TD-TC-001971.json](terminal_and_host_operations/test_data/TD-TC-001971.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001972 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Positive | needs_client_input | p.522; p.257; p.511 | 1 | OP_DATA_RECEIPT_TEXT | [TD-TC-001972.json](terminal_and_host_operations/test_data/TD-TC-001972.json) |
| TC-001973 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Positive | needs_client_input | p.522; p.258; p.511 | 1 | OP_DATA_CARD_TABLE | [TD-TC-001973.json](terminal_and_host_operations/test_data/TD-TC-001973.json) |
| TC-001974 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Positive | needs_client_input | p.522; p.258; p.511 | 1 | OP_DATA_SITE_CONFIG | [TD-TC-001974.json](terminal_and_host_operations/test_data/TD-TC-001974.json) |
| TC-001975 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Positive | needs_client_input | p.260; p.511; p.83 | 1 | OP_DATA_HOST_DISCOUNT | [TD-TC-001975.json](terminal_and_host_operations/test_data/TD-TC-001975.json) |
| TC-001976 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Positive | needs_client_input | p.522; p.262; p.511 | 1 | OP_DATA_FUEL_VOLUME | [TD-TC-001976.json](terminal_and_host_operations/test_data/TD-TC-001976.json) |
| TC-001977 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Negative | needs_client_input | p.522; p.257; p.404 | 1 | OP_DATA_RECEIPT_TEXT | [TD-TC-001977.json](terminal_and_host_operations/test_data/TD-TC-001977.json) |
| TC-001978 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Negative | needs_client_input | p.522; p.258; p.404 | 1 | OP_DATA_CARD_TABLE | [TD-TC-001978.json](terminal_and_host_operations/test_data/TD-TC-001978.json) |
| TC-001979 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Negative | needs_client_input | p.522; p.258; p.404 | 1 | OP_DATA_SITE_CONFIG | [TD-TC-001979.json](terminal_and_host_operations/test_data/TD-TC-001979.json) |
| TC-001980 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Negative | needs_client_input | p.260; p.404; p.511 | 1 | OP_DATA_HOST_DISCOUNT | [TD-TC-001980.json](terminal_and_host_operations/test_data/TD-TC-001980.json) |
| TC-001981 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Negative | needs_client_input | p.522; p.262; p.404 | 1 | OP_DATA_FUEL_VOLUME | [TD-TC-001981.json](terminal_and_host_operations/test_data/TD-TC-001981.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001951 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the c | Terminal & host operation | Positive | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-001951.json](terminal_and_host_operations/test_data/TD-TC-001951.json) |
| TC-001952 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the m | Terminal & host operation | Positive | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-001952.json](terminal_and_host_operations/test_data/TD-TC-001952.json) |
| TC-001953 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the s | Terminal & host operation | Positive | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-001953.json](terminal_and_host_operations/test_data/TD-TC-001953.json) |
| TC-001954 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the t | Terminal & host operation | Positive | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-001954.json](terminal_and_host_operations/test_data/TD-TC-001954.json) |
| TC-001955 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals since the | Terminal & host operation | Positive | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-001955.json](terminal_and_host_operations/test_data/TD-TC-001955.json) |
| TC-001956 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Clear all totals | Terminal & host operation | Positive | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-001956.json](terminal_and_host_operations/test_data/TD-TC-001956.json) |
| TC-001957 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for a spe | Terminal & host operation | Positive | blocked_verification | p.522; p.237; p.419 | 1 | OP_TOTALS | [TD-TC-001957.step1.totals.json](terminal_and_host_operations/test_data/TD-TC-001957.step1.totals.json) |
|  |  |  |  |  | 2 | OP_TOTALS | [TD-TC-001957.step2.totals.json](terminal_and_host_operations/test_data/TD-TC-001957.step2.totals.json) |
| TC-001958 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for a dat | Terminal & host operation | Negative | needs_client_input | p.419; p.511; p.83 | 1 | OP_TOTALS | [TD-TC-001958.json](terminal_and_host_operations/test_data/TD-TC-001958.json) |
| TC-001959 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals with an e | Terminal & host operation | Negative | needs_client_input | p.77; p.511; p.83 | 1 | OP_TOTALS | [TD-TC-001959.json](terminal_and_host_operations/test_data/TD-TC-001959.json) |
| TC-001960 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the c | Terminal & host operation | Negative | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-001960.json](terminal_and_host_operations/test_data/TD-TC-001960.json) |
| TC-001961 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for a spe | Terminal & host operation | Negative | blocked_verification | p.522; p.237; p.419 | 1 | OP_TOTALS | [TD-TC-001961.step1.totals.json](terminal_and_host_operations/test_data/TD-TC-001961.step1.totals.json) |
|  |  |  |  |  | 2 | OP_TOTALS | [TD-TC-001961.step2.totals.json](terminal_and_host_operations/test_data/TD-TC-001961.step2.totals.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001962 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals with Proprietary Data Loa | Terminal & host operation | Positive | needs_client_input | p.263; p.263; p.511 | 1 | OP_TOTALS_PROPRIETARY | [TD-TC-001962.json](terminal_and_host_operations/test_data/TD-TC-001962.json) |
| TC-001963 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals with Proprietary Data Loa | Terminal & host operation | Negative | needs_client_input | p.263; p.263; p.404 | 1 | OP_TOTALS_PROPRIETARY | [TD-TC-001963.json](terminal_and_host_operations/test_data/TD-TC-001963.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-001982 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS TransArmor Key Load Request: Tra | Terminal & host operation | Positive | needs_client_input | p.253; p.404; p.511 | 1 | OP_TRANSARMOR_KEY_LOAD | [TD-TC-001982.json](terminal_and_host_operations/test_data/TD-TC-001982.json) |
| TC-001983 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS TransArmor Key Load Request: Tra | Terminal & host operation | Negative | needs_client_input | p.253; p.404; p.404 | 1 | OP_TRANSARMOR_KEY_LOAD | [TD-TC-001983.json](terminal_and_host_operations/test_data/TD-TC-001983.json) |

## Reverse index - context -> scenarios

| Context | Scenarios |
|---|---:|
| Credential on File · customer present; card not present | 4 |
| General | 52 |
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
| BR-112-2 p.112 | 6 | TC-001604, TC-001606, TC-001608, TC-001653, TC-001654 |
| BR-138-1 p.138 | 1 | TC-001854 |
| BR-138-10 p.138 | 1 | TC-001854 |
| BR-139-1 p.139 | 12 | TC-000392, TC-000404, TC-000405, TC-000407, TC-000418 |
| BR-139-3 p.139 | 4 | TC-000392, TC-000407, TC-000583, TC-000612 |
| BR-139-4 p.139 | 10 | TC-000392, TC-000405, TC-000407, TC-000419, TC-000583 |
| BR-139-6 p.139 | 4 | TC-000392, TC-000407, TC-000583, TC-000612 |
| BR-145-2 p.145 | 1 | TC-000960 |
| BR-146-2 p.146 | 2 | TC-000961, TC-000962 |
| BR-147-1 p.147 | 2 | TC-000961, TC-000962 |
| BR-148-1 p.148 | 1 | TC-000960 |
| BR-149-1 p.149 | 2 | TC-000961, TC-000962 |
| BR-152-1 p.152 | 2 | TC-000960, TC-000961 |
| BR-153-1 p.153 | 8 | TC-000002, TC-000055, TC-000111, TC-000159, TC-000203 |
| BR-154-2 p.154 | 2 | TC-000961, TC-000962 |
| BR-156-4 p.156 | 2 | TC-000961, TC-000962 |
| BR-156-7 p.156 | 1 | TC-000950 |
| BR-157-5 p.157 | 1 | TC-001901 |
| BR-160-10 p.160 | 1 | TC-001856 |
| BR-160-9 p.160 | 1 | TC-001856 |
| BR-162-2 p.162 | 1 | TC-000948 |
| BR-162-5 p.162 | 2 | TC-000963, TC-001938 |
| BR-171-1 p.171 | 1 | TC-001857 |
| BR-172-2 p.172 | 1 | TC-001857 |
| BR-179-3 p.179 | 2 | TC-001693, TC-001695 |
| BR-179-5 p.179 | 1 | TC-001725 |
| BR-180-3 p.180 | 6 | TC-001725, TC-001726, TC-001728, TC-001744, TC-001745 |
| BR-181-1 p.181 | 74 | TC-000038, TC-000039, TC-000092, TC-000093, TC-000149 |
| BR-183-1 p.183 | 74 | TC-000038, TC-000039, TC-000092, TC-000093, TC-000149 |
| BR-184-1 p.184 | 1 | TC-001920 |
| BR-185-1 p.185 | 1 | TC-001920 |
| BR-185-2 p.185 | 2 | TC-001699, TC-001701 |
| BR-188-3 p.188 | 1 | TC-001900 |
| BR-189-2 p.189 | 1 | TC-001858 |
| BR-189-3 p.189 | 8 | TC-000013, TC-000066, TC-000122, TC-000169, TC-000213 |
| BR-189-4 p.189 | 1 | TC-001942 |
| BR-190-4 p.190 | 3 | TC-001757, TC-001758, TC-001760 |
| BR-193-1 p.193 | 3 | TC-001757, TC-001758, TC-001760 |
| BR-193-4 p.193 | 1 | TC-001757 |
| BR-195-2 p.195 | 1 | TC-001757 |
| BR-199-2 p.199 | 1 | TC-001807 |
| BR-200-1 p.200 | 1 | TC-001858 |
| BR-200-4 p.200 | 1 | TC-001725 |
| BR-206-7 p.206 | 1 | TC-001605 |
| BR-209-3 p.209 | 1 | TC-000041 |
| BR-210-3 p.210 | 1 | TC-001897 |
| BR-217-3 p.217 | 1 | TC-001885 |
| BR-235-2 p.235 | 2 | TC-001693, TC-001695 |
| BR-235-7 p.235 | 74 | TC-000038, TC-000039, TC-000092, TC-000093, TC-000149 |
| BR-240-1 p.240 | 17 | TC-000052, TC-000106, TC-000156, TC-000201, TC-000246 |
| BR-240-3 p.240 | 2 | TC-001699, TC-001701 |
| BR-252-1 p.252 | 1 | TC-001876 |
| BR-252-2 p.252 | 1 | TC-001876 |
| BR-252-3 p.252 | 18 | TC-000052, TC-000106, TC-000156, TC-000201, TC-000246 |
| BR-252-4 p.252 | 1 | TC-001876 |
| BR-252-6 p.252 | 1 | TC-001876 |
| BR-254-6 p.254 | 1 | TC-001881 |
| BR-257-3 p.257 | 1 | TC-001881 |
| BR-258-1 p.258 | 1 | TC-001881 |
| BR-259-1 p.259 | 1 | TC-001881 |
| BR-260-1 p.260 | 1 | TC-001881 |
| BR-260-2 p.260 | 1 | TC-001881 |
| BR-262-1 p.262 | 1 | TC-001881 |
| BR-263-2 p.263 | 3 | TC-001744, TC-001745, TC-001747 |
| BR-266-1 p.266 | 1 | TC-001871 |
| BR-266-2 p.266 | 1 | TC-001871 |
| BR-266-3 p.266 | 18 | TC-000052, TC-000106, TC-000156, TC-000201, TC-000246 |
| BR-266-5 p.266 | 1 | TC-001871 |
| BR-312-2 p.312 | 13 | TC-000025, TC-000079, TC-000135, TC-000180, TC-000224 |
| BR-313-1 p.313 | 1 | TC-001793 |
| BR-313-2 p.313 | 1 | TC-001793 |
| BR-315-4 p.315 | 9 | TC-000597, TC-000598, TC-000599, TC-000600, TC-000601 |
| BR-315-6 p.315 | 8 | TC-000045, TC-000099, TC-000152, TC-000197, TC-000242 |
| BR-317-5 p.317 | 8 | TC-000045, TC-000099, TC-000152, TC-000197, TC-000242 |
| BR-317-9 p.317 | 8 | TC-000045, TC-000099, TC-000152, TC-000197, TC-000242 |
| BR-330-4 p.330 | 1 | TC-001803 |
| BR-34-1 p.34 | 1 | TC-001865 |
| BR-34-2 p.34 | 1 | TC-001865 |
| BR-34-4 p.34 | 1 | TC-001865 |
| BR-34-8 p.34 | 1 | TC-001744 |
| BR-343-1 p.343 | 1 | TC-001844 |
| BR-343-2 p.343 | 1 | TC-001844 |
| BR-343-3 p.343 | 1 | TC-001844 |
| BR-344-1 p.344 | 1 | TC-001844 |
| BR-346-1 p.346 | 1 | TC-001844 |
| BR-346-2 p.346 | 1 | TC-001844 |
| BR-352-1 p.352 | 1 | TC-001844 |
| BR-352-2 p.352 | 1 | TC-001844 |
| BR-364-3 p.364 | 7 | TC-000023, TC-000076, TC-000133, TC-000178, TC-000222 |
| BR-364-4 p.364 | 1 | TC-001845 |
| BR-365-5 p.365 | 7 | TC-000023, TC-000076, TC-000133, TC-000178, TC-000222 |
| BR-368-5 p.368 | 20 | TC-000028, TC-000038, TC-000082, TC-000092, TC-000139 |
| BR-368-6 p.368 | 35 | TC-000427, TC-000437, TC-000440, TC-000449, TC-000459 |
| BR-368-7 p.368 | 50 | TC-000030, TC-000031, TC-000035, TC-000084, TC-000085 |
| BR-370-2 p.370 | 1 | TC-001935 |
| BR-370-6 p.370 | 1 | TC-001845 |
| BR-371-2 p.371 | 3 | TC-000138, TC-000183, TC-000228 |
| BR-372-1 p.372 | 7 | TC-000023, TC-000076, TC-000133, TC-000178, TC-000222 |
| BR-374-3 p.374 | 1 | TC-001845 |
| BR-374-4 p.374 | 1 | TC-001845 |
| BR-377-2 p.377 | 1 | TC-001845 |
| BR-379-1 p.379 | 1 | TC-001845 |
| BR-380-2 p.380 | 1 | TC-001845 |
| BR-380-3 p.380 | 1 | TC-001845 |
| BR-39-1 p.39 | 30 | TC-000032, TC-000036, TC-000086, TC-000090, TC-000143 |
| BR-391-7 p.391 | 6 | TC-000405, TC-000419, TC-000583, TC-000596, TC-000612 |
| BR-395-5 p.395 | 6 | TC-000405, TC-000419, TC-000583, TC-000596, TC-000612 |
| BR-396-3 p.396 | 74 | TC-000038, TC-000039, TC-000092, TC-000093, TC-000149 |
| BR-40-12 p.40 | 1 | TC-001930 |
| BR-40-13 p.40 | 48 | TC-000026, TC-000032, TC-000033, TC-000080, TC-000086 |
| BR-40-2 p.40 | 114 | TC-000021, TC-000028, TC-000074, TC-000082, TC-000131 |
| BR-40-3 p.40 | 1 | TC-001930 |
| BR-40-4 p.40 | 10 | TC-000026, TC-000080, TC-000136, TC-000181, TC-000226 |
| BR-40-6 p.40 | 1 | TC-001930 |
| BR-40-7 p.40 | 1 | TC-001930 |
| BR-400-4 p.400 | 1 | TC-001846 |
| BR-400-5 p.400 | 1 | TC-001846 |
| BR-401-1 p.401 | 1 | TC-001846 |
| BR-401-2 p.401 | 1 | TC-001846 |
| BR-401-3 p.401 | 1 | TC-001846 |
| BR-401-4 p.401 | 27 | TC-000420, TC-000442, TC-000468, TC-000487, TC-000506 |
| BR-401-5 p.401 | 1 | TC-001846 |
| BR-401-6 p.401 | 13 | TC-000025, TC-000079, TC-000135, TC-000180, TC-000224 |
| BR-401-8 p.401 | 1 | TC-001846 |
| BR-401-9 p.401 | 1 | TC-001846 |
| BR-402-1 p.402 | 45 | TC-000424, TC-000427, TC-000437, TC-000440, TC-000446 |
| BR-402-3 p.402 | 54 | TC-000020, TC-000028, TC-000038, TC-000073, TC-000082 |
| BR-403-3 p.403 | 3 | TC-000400, TC-000414, TC-001808 |
| BR-404-10 p.404 | 4 | TC-000405, TC-000419, TC-000596, TC-000626 |
| BR-404-11 p.404 | 1 | TC-001809 |
| BR-404-12 p.404 | 1 | TC-001810 |
| BR-404-3 p.404 | 29 | TC-000441, TC-000463, TC-000486, TC-000505, TC-000524 |
| BR-404-7 p.404 | 1 | TC-001811 |
| BR-404-8 p.404 | 1 | TC-001812 |
| BR-404-9 p.404 | 4 | TC-000405, TC-000419, TC-000596, TC-000626 |
| BR-409-2 p.409 | 38 | TC-000020, TC-000073, TC-000130, TC-000175, TC-000219 |
| BR-416-2 p.416 | 1 | TC-001847 |
| BR-416-3 p.416 | 1 | TC-001847 |
| BR-416-4 p.416 | 1 | TC-001847 |
| BR-43-2 p.43 | 10 | TC-000036, TC-000090, TC-000147, TC-000192, TC-000237 |
| BR-43-3 p.43 | 8 | TC-000034, TC-000088, TC-000145, TC-000190, TC-000235 |
| BR-43-4 p.43 | 1 | TC-001930 |
| BR-437-10 p.437 | 1 | TC-001847 |
| BR-44-1 p.44 | 20 | TC-000026, TC-000036, TC-000080, TC-000090, TC-000136 |
| BR-44-2 p.44 | 1 | TC-001866 |
| BR-440-3 p.440 | 1 | TC-000947 |
| BR-45-2 p.45 | 38 | TC-000020, TC-000073, TC-000130, TC-000175, TC-000219 |
| BR-45-5 p.45 | 10 | TC-000026, TC-000080, TC-000136, TC-000181, TC-000226 |
| BR-45-7 p.45 | 1 | TC-001866 |
| BR-460-3 p.460 | 1 | TC-001847 |
| BR-468-1 p.468 | 1 | TC-001847 |
| BR-468-2 p.468 | 1 | TC-001847 |
| BR-469-1 p.469 | 1 | TC-001847 |
| BR-469-3 p.469 | 3 | TC-001725, TC-001726, TC-001728 |
| BR-47-1 p.47 | 8 | TC-000037, TC-000091, TC-000148, TC-000193, TC-000238 |
| BR-47-3 p.47 | 27 | TC-000441, TC-000463, TC-000486, TC-000505, TC-000524 |
| BR-47-5 p.47 | 1 | TC-001930 |
| BR-47-6 p.47 | 13 | TC-000025, TC-000079, TC-000135, TC-000180, TC-000224 |
| BR-47-7 p.47 | 1 | TC-001819 |
| BR-47-8 p.47 | 1 | TC-001866 |
| BR-47-9 p.47 | 10 | TC-000026, TC-000080, TC-000136, TC-000181, TC-000226 |
| BR-470-1 p.470 | 1 | TC-001849 |
| BR-470-4 p.470 | 1 | TC-001849 |
| BR-471-1 p.471 | 1 | TC-001849 |
| BR-471-2 p.471 | 1 | TC-001849 |
| BR-474-1 p.474 | 1 | TC-001849 |
| BR-475-1 p.475 | 1 | TC-001849 |
| BR-475-2 p.475 | 1 | TC-001849 |
| BR-475-3 p.475 | 1 | TC-001849 |
| BR-475-6 p.475 | 4 | TC-000392, TC-000407, TC-000583, TC-000612 |
| BR-476-2 p.476 | 1 | TC-001874 |
| BR-476-3 p.476 | 1 | TC-001874 |
| BR-48-3 p.48 | 1 | TC-001889 |
| BR-481-3 p.481 | 1 | TC-001874 |
| BR-483-3 p.483 | 13 | TC-000025, TC-000079, TC-000135, TC-000180, TC-000224 |
| BR-484-3 p.484 | 1 | TC-001874 |
| BR-49-4 p.49 | 1 | TC-001802 |
| BR-49-5 p.49 | 1 | TC-001824 |
| BR-503-1 p.503 | 1 | TC-001923 |
| BR-504-1 p.504 | 1 | TC-001923 |
| BR-506-4 p.506 | 47 | TC-000001, TC-000054, TC-000110, TC-000158, TC-000202 |
| BR-518-5 p.518 | 1 | TC-001880 |
| BR-518-6 p.518 | 1 | TC-001880 |
| BR-519-1 p.519 | 1 | TC-001880 |
| BR-520-2 p.520 | 2 | TC-000392, TC-000407 |
| BR-521-1 p.521 | 1 | TC-001880 |
| BR-522-1 p.522 | 1 | TC-001880 |
| BR-522-2 p.522 | 1 | TC-001880 |
| BR-53-2 p.53 | 7 | TC-000023, TC-000076, TC-000133, TC-000178, TC-000222 |
| BR-53-5 p.53 | 1 | TC-001941 |
| BR-545-1 p.545 | 15 | TC-000025, TC-000079, TC-000135, TC-000180, TC-000224 |
| BR-545-2 p.545 | 2 | TC-001604, TC-001653 |
| BR-545-5 p.545 | 8 | TC-000051, TC-000105, TC-000155, TC-000200, TC-000245 |
| BR-545-6 p.545 | 1 | TC-001887 |
| BR-547-1 p.547 | 1 | TC-001863 |
| BR-548-1 p.548 | 1 | TC-001863 |
| BR-555-3 p.555 | 8 | TC-000002, TC-000055, TC-000111, TC-000159, TC-000203 |
| BR-559-1 p.559 | 1 | TC-000323 |
| BR-56-8 p.56 | 7 | TC-000023, TC-000076, TC-000133, TC-000178, TC-000222 |
| BR-561-1 p.561 | 1 | TC-000300 |
| BR-561-2 p.561 | 1 | TC-000300 |
| BR-569-1 p.569 | 20 | TC-000006, TC-000059, TC-000115, TC-000163, TC-000207 |
| BR-572-3 p.572 | 8 | TC-000003, TC-000056, TC-000112, TC-000160, TC-000204 |
| BR-575-1 p.575 | 2 | TC-001603, TC-001652 |
| BR-575-4 p.575 | 2 | TC-001603, TC-001652 |
| BR-575-5 p.575 | 2 | TC-001603, TC-001652 |
| BR-577-1 p.577 | 8 | TC-000016, TC-000069, TC-000126, TC-000171, TC-000215 |
| BR-577-2 p.577 | 8 | TC-000017, TC-000070, TC-000127, TC-000172, TC-000216 |
| BR-577-3 p.577 | 1 | TC-001826 |
| BR-577-5 p.577 | 1 | TC-001905 |
| BR-581-2 p.581 | 9 | TC-000597, TC-000598, TC-000599, TC-000600, TC-000601 |
| BR-583-1 p.583 | 4 | TC-000008, TC-000061, TC-000909, TC-000915 |
| BR-583-2 p.583 | 4 | TC-000008, TC-000061, TC-000909, TC-000915 |
| BR-583-3 p.583 | 12 | TC-000008, TC-000009, TC-000061, TC-000062, TC-000117 |
| BR-588-1 p.588 | 1 | TC-001821 |
| BR-59-2 p.59 | 1 | TC-001899 |
| BR-590-3 p.590 | 2 | TC-000325, TC-000326 |
| BR-594-4 p.594 | 2 | TC-000325, TC-000326 |
| BR-597-1 p.597 | 8 | TC-000018, TC-000071, TC-000128, TC-000173, TC-000217 |
| BR-607-3 p.607 | 20 | TC-000048, TC-000102, TC-000153, TC-000198, TC-000243 |
| BR-61-2 p.61 | 2 | TC-000323, TC-001908 |
| BR-61-5 p.61 | 1 | TC-001875 |
| BR-610-3 p.610 | 2 | TC-001603, TC-001652 |
| BR-614-4 p.614 | 8 | TC-000011, TC-000064, TC-000119, TC-000167, TC-000211 |
| BR-614-8 p.614 | 8 | TC-000011, TC-000064, TC-000119, TC-000167, TC-000211 |
| BR-616-4 p.616 | 2 | TC-000225, TC-000730 |
| BR-616-6 p.616 | 2 | TC-000225, TC-000730 |
| BR-62-1 p.62 | 1 | TC-001875 |
| BR-62-10 p.62 | 1 | TC-001875 |
| BR-62-4 p.62 | 1 | TC-001940 |
| BR-62-6 p.62 | 5 | TC-000816, TC-000826, TC-000835, TC-000844, TC-000853 |
| BR-62-7 p.62 | 6 | TC-001604, TC-001606, TC-001608, TC-001653, TC-001654 |
| BR-622-1 p.622 | 9 | TC-000597, TC-000598, TC-000599, TC-000600, TC-000601 |
| BR-628-2 p.628 | 8 | TC-000421, TC-000443, TC-000469, TC-000488, TC-000507 |
| BR-63-2 p.63 | 1 | TC-001836 |
| BR-63-4 p.63 | 13 | TC-000025, TC-000079, TC-000135, TC-000180, TC-000224 |
| BR-63-6 p.63 | 1 | TC-001806 |
| BR-63-7 p.63 | 1 | TC-001875 |
| BR-637-2 p.637 | 2 | TC-000225, TC-000730 |
| BR-639-2 p.639 | 1 | TC-000949 |
| BR-639-6 p.639 | 1 | TC-001796 |
| BR-639-7 p.639 | 1 | TC-001796 |
| BR-64-1 p.64 | 101 | TC-000001, TC-000050, TC-000054, TC-000104, TC-000110 |
| BR-64-2 p.64 | 1 | TC-001934 |
| BR-64-6 p.64 | 1 | TC-001903 |
| BR-656-3 p.656 | 1 | TC-001832 |
| BR-658-1 p.658 | 1 | TC-001888 |
| BR-66-1 p.66 | 1 | TC-001867 |
| BR-66-2 p.66 | 27 | TC-000420, TC-000442, TC-000468, TC-000487, TC-000506 |
| BR-66-3 p.66 | 27 | TC-000420, TC-000442, TC-000468, TC-000487, TC-000506 |
| BR-66-4 p.66 | 1 | TC-001867 |
| BR-66-5 p.66 | 1 | TC-001867 |
| BR-663-1 p.663 | 8 | TC-000051, TC-000105, TC-000155, TC-000200, TC-000245 |
| BR-663-2 p.663 | 8 | TC-000051, TC-000105, TC-000155, TC-000200, TC-000245 |
| BR-663-4 p.663 | 8 | TC-000051, TC-000105, TC-000155, TC-000200, TC-000245 |
| BR-664-4 p.664 | 8 | TC-000051, TC-000105, TC-000155, TC-000200, TC-000245 |
| BR-688-2 p.688 | 1 | TC-000950 |
| BR-701-2 p.701 | 1 | TC-001939 |
| BR-701-3 p.701 | 1 | TC-001890 |
| BR-71-1 p.71 | 1 | TC-001877 |
| BR-712-2 p.712 | 1 | TC-001801 |
| BR-713-1 p.713 | 1 | TC-000157 |
| BR-713-12 p.713 | 1 | TC-000323 |
| BR-713-9 p.713 | 1 | TC-000341 |
| BR-714-8 p.714 | 1 | TC-000323 |
| BR-715-19 p.715 | 1 | TC-000960 |
| BR-718-3 p.718 | 1 | TC-001817 |
| BR-718-6 p.718 | 1 | TC-001820 |
| BR-72-1 p.72 | 1 | TC-001744 |
| BR-72-2 p.72 | 1 | TC-001813 |
| BR-72-3 p.72 | 1 | TC-001877 |
| BR-72-4 p.72 | 80 | TC-000001, TC-000050, TC-000054, TC-000104, TC-000110 |
| BR-72-6 p.72 | 33 | TC-000627, TC-000633, TC-000634, TC-000635, TC-000636 |
| BR-728-2 p.728 | 1 | TC-000311 |
| BR-730-1 p.730 | 2 | TC-000225, TC-000730 |
| BR-730-4 p.730 | 2 | TC-000225, TC-000730 |
| BR-730-5 p.730 | 2 | TC-000225, TC-000730 |
| BR-76-1 p.76 | 1 | TC-001877 |
| BR-76-2 p.76 | 1 | TC-001877 |
| BR-76-3 p.76 | 1 | TC-001877 |
| BR-81-10 p.81 | 1 | TC-001851 |
| BR-81-11 p.81 | 1 | TC-001851 |
| BR-81-12 p.81 | 1 | TC-001851 |
| BR-81-2 p.81 | 1 | TC-001851 |
| BR-81-3 p.81 | 3 | TC-000138, TC-000183, TC-000228 |
| BR-81-4 p.81 | 1 | TC-001926 |
| BR-81-5 p.81 | 1 | TC-001926 |
| BR-81-9 p.81 | 1 | TC-001851 |
| BR-83-1 p.83 | 18 | TC-000301, TC-000338, TC-000342, TC-000406, TC-000418 |
| BR-83-2 p.83 | 128 | TC-000001, TC-000039, TC-000040, TC-000050, TC-000054 |
| BR-84-3 p.84 | 68 | TC-000028, TC-000030, TC-000031, TC-000035, TC-000082 |
| BR-84-4 p.84 | 20 | TC-000029, TC-000083, TC-000140, TC-000185, TC-000230 |
| BR-84-5 p.84 | 167 | TC-000001, TC-000030, TC-000031, TC-000035, TC-000039 |
| BR-84-6 p.84 | 21 | TC-000030, TC-000084, TC-000141, TC-000186, TC-000231 |
| BR-84-8 p.84 | 18 | TC-000026, TC-000035, TC-000080, TC-000089, TC-000136 |
| BR-85-11 p.85 | 27 | TC-000420, TC-000442, TC-000468, TC-000487, TC-000506 |
| BR-85-2 p.85 | 33 | TC-000426, TC-000435, TC-000436, TC-000448, TC-000457 |
| BR-85-3 p.85 | 2 | TC-000558, TC-000621 |
| BR-85-4 p.85 | 1 | TC-000300 |
| BR-86-4 p.86 | 1 | TC-001926 |
| BR-87-3 p.87 | 50 | TC-000030, TC-000031, TC-000035, TC-000084, TC-000085 |
| BR-89-10 p.89 | 2 | TC-000946, TC-000947 |
| BR-89-3 p.89 | 2 | TC-000946, TC-000947 |
| BR-89-5 p.89 | 2 | TC-000946, TC-000947 |
| BR-90-1 p.90 | 1 | TC-001851 |
| BR-90-2 p.90 | 1 | TC-001851 |
| BR-90-3 p.90 | 1 | TC-001851 |
| BR-91-5 p.91 | 1 | TC-001852 |
| BR-91-6 p.91 | 1 | TC-001852 |
| BR-91-9 p.91 | 1 | TC-001852 |
| BR-92-10 p.92 | 1 | TC-001852 |
| BR-92-2 p.92 | 1 | TC-001852 |
| BR-92-4 p.92 | 1 | TC-001852 |
| BR-92-5 p.92 | 1 | TC-001852 |
| BR-92-6 p.92 | 1 | TC-001852 |
| BR-93-2 p.93 | 1 | TC-001878 |
| BR-93-3 p.93 | 1 | TC-001878 |
| BR-93-4 p.93 | 1 | TC-001878 |
| BR-94-2 p.94 | 1 | TC-001878 |
| BR-94-3 p.94 | 1 | TC-001878 |
| BR-94-5 p.94 | 20 | TC-000048, TC-000102, TC-000153, TC-000198, TC-000243 |
| BR-94-6 p.94 | 3 | TC-000138, TC-000183, TC-000228 |
| BR-95-3 p.95 | 3 | TC-000138, TC-000183, TC-000228 |
| BR-ATL105-TRANSARMOR-12-2 p.12 | 1 | TC-001902 |
| BR-ATL105-TRANSARMOR-13-5 p.13 | 1 | TC-001916 |
| BR-ATL105-TRANSARMOR-13-6 p.13 | 3 | TC-000138, TC-000183, TC-000228 |
| BR-ATL105-TRANSARMOR-13-7 p.13 | 1 | TC-001916 |
| BR-ATL105-TRANSARMOR-14-2 p.14 | 1 | TC-001833 |
| BR-ATL105-TRANSARMOR-16-6 p.16 | 1 | TC-001950 |
| BR-ATL105-TRANSARMOR-19-1 p.19 | 1 | TC-001936 |
| BR-ATL105-TRANSARMOR-20-2 p.20 | 1 | TC-001892 |
| BR-ATL105-TRANSARMOR-21-1 p.21 | 31 | TC-000627, TC-000633, TC-000635, TC-000636, TC-000642 |
| BR-ATL105-TRANSARMOR-23-3 p.23 | 1 | TC-001898 |
| BR-ATL105-TRANSARMOR-26-1 p.26 | 1 | TC-001947 |
| BR-ATL105-TRANSARMOR-27-1 p.27 | 1 | TC-001859 |
| BR-ATL105-TRANSARMOR-27-2 p.27 | 1 | TC-001859 |
| BR-ATL105-TRANSARMOR-28-4 p.28 | 9 | TC-000027, TC-000081, TC-000137, TC-000182, TC-000227 |
| BR-ATL105-TRANSARMOR-32-2 p.32 | 1 | TC-001891 |
| BR-ATL105-TRANSARMOR-33-2 p.33 | 1 | TC-001922 |
| BR-ATL105-TRANSARMOR-33-4 p.33 | 1 | TC-001922 |
| BR-ATL105-TRANSARMOR-35-9 p.35 | 1 | TC-001949 |
| BR-ATL105-TRANSARMOR-40-5 p.40 | 1 | TC-001925 |
| BR-ATL105-TRANSARMOR-40-6 p.40 | 1 | TC-001925 |
| BR-ATL105-TRANSARMOR-40-7 p.40 | 1 | TC-001925 |
| BR-ATL105-TRANSARMOR-41-1 p.41 | 3 | TC-001709, TC-001710, TC-001712 |
| BR-ATL105-TRANSARMOR-41-2 p.41 | 1 | TC-001709 |
| BR-ATL105-TRANSARMOR-46-1 p.46 | 1 | TC-001944 |
| BR-ATL105-TRANSARMOR-46-6 p.46 | 1 | TC-001906 |
| BR-ATL105-TRANSARMOR-52-3 p.52 | 8 | TC-000052, TC-000106, TC-000156, TC-000201, TC-000246 |
| BR-ATL105-TRANSARMOR-52-4 p.52 | 8 | TC-000052, TC-000106, TC-000156, TC-000201, TC-000246 |
| BR-ATL105-TRANSARMOR-57-10 p.57 | 20 | TC-000048, TC-000102, TC-000153, TC-000198, TC-000243 |
| BR-ATL105-TRANSARMOR-57-4 p.57 | 1 | TC-001882 |
| BR-ATL105-TRANSARMOR-57-6 p.57 | 3 | TC-000138, TC-000183, TC-000228 |
| BR-ATL105-TRANSARMOR-62-1 p.62 | 1 | TC-001882 |
| BR-ATL105-TRANSARMOR-62-2 p.62 | 1 | TC-001882 |
| BR-ATL105-TRANSARMOR-62-5 p.62 | 1 | TC-001882 |
| BR-ATL105-TRANSARMOR-62-7 p.62 | 1 | TC-001882 |
| BR-ATL105-TRANSARMOR-66-1 p.66 | 1 | TC-001709 |
| BR-ATL105-TRANSARMOR-66-5 p.66 | 1 | TC-001692 |
| BR-ATL105-TRANSARMOR-67-1 p.67 | 2 | TC-001698, TC-001816 |
| BR-ATL105-TRANSARMOR-67-4 p.67 | 1 | TC-001818 |
| BR-ATL105-TRANSARMOR-76-5 p.76 | 6 | TC-000545, TC-000561, TC-000563, TC-000613, TC-000624 |
| BR-ATL105-TRANSARMOR-77-2 p.77 | 1 | TC-001882 |
| BR-ATL105-TRANSARMOR-77-3 p.77 | 1 | TC-001882 |
| BR-BUYPASS-DCT-11-1 p.11 | 1 | TC-001868 |
| BR-BUYPASS-DCT-11-2 p.11 | 1 | TC-001868 |
| BR-BUYPASS-DCT-18-2 p.18 | 1 | TC-001843 |
| BR-BUYPASS-DCT-18-4 p.18 | 1 | TC-001843 |
| BR-BUYPASS-DCT-21-2 p.21 | 1 | TC-001843 |
| BR-BUYPASS-DCT-21-3 p.21 | 1 | TC-001843 |
| BR-BUYPASS-DCT-21-4 p.21 | 1 | TC-001843 |
| BR-BUYPASS-DCT-25-1 p.25 | 1 | TC-001843 |
| BR-BUYPASS-DCT-25-2 p.25 | 1 | TC-001843 |
| BR-BUYPASS-DCT-28-2 p.28 | 1 | TC-001843 |
| BR-BUYPASS-DCT-39-3 p.39 | 1 | TC-001884 |
| BR-BUYPASS-DCT-39-4 p.39 | 1 | TC-001884 |
| BR-BUYPASS-DCT-39-5 p.39 | 1 | TC-001884 |
| BR-BUYPASS-DCT-39-6 p.39 | 1 | TC-001884 |
| BR-BUYPASS-DCT-41-1 p.41 | 1 | TC-001884 |
| BR-BUYPASS-DCT-41-2 p.41 | 1 | TC-001884 |
| BR-BUYPASS-DCT-41-3 p.41 | 1 | TC-001884 |
| BR-BUYPASS-DCT-43-1 p.43 | 1 | TC-001884 |
| BR-BUYPASS-DCT-44-8 p.44 | 1 | TC-001886 |
| BR-BUYPASS-DCT-8-3 p.8 | 1 | TC-001868 |
| BR-BUYPASS-IIN-4-3 p.4 | 1 | TC-001948 |
| BR-BUYPASS-MTAP-44-2 p.44 | 1 | TC-001904 |
| BR-BUYPASS-PIPS-130-1 p.130 | 12 | TC-000755, TC-000761, TC-000766, TC-000771, TC-000776 |
| BR-BUYPASS-PIPS-130-3 p.130 | 2 | TC-000952, TC-000953 |
| BR-BUYPASS-PIPS-131-10 p.131 | 1 | TC-001870 |
| BR-BUYPASS-PIPS-131-11 p.131 | 1 | TC-001870 |
| BR-BUYPASS-PIPS-131-3 p.131 | 1 | TC-001917 |
| BR-BUYPASS-PIPS-131-4 p.131 | 2 | TC-000954, TC-001917 |
| BR-BUYPASS-PIPS-131-6 p.131 | 1 | TC-001870 |
| BR-BUYPASS-PIPS-131-9 p.131 | 1 | TC-001870 |
| BR-BUYPASS-PIPS-135-1 p.135 | 1 | TC-000954 |
| BR-BUYPASS-PIPS-136-1 p.136 | 1 | TC-001797 |
| BR-BUYPASS-PIPS-140-1 p.140 | 2 | TC-000952, TC-000953 |
| BR-BUYPASS-PIPS-140-3 p.140 | 1 | TC-001937 |
| BR-BUYPASS-PIPS-140-6 p.140 | 1 | TC-001893 |
| BR-BUYPASS-PIPS-16-10 p.16 | 1 | TC-001879 |
| BR-BUYPASS-PIPS-16-11 p.16 | 1 | TC-001879 |
| BR-BUYPASS-PIPS-16-5 p.16 | 1 | TC-001879 |
| BR-BUYPASS-PIPS-16-6 p.16 | 1 | TC-001879 |
| BR-BUYPASS-PIPS-16-8 p.16 | 1 | TC-001879 |
| BR-BUYPASS-PIPS-16-9 p.16 | 1 | TC-001879 |
| BR-BUYPASS-PIPS-17-1 p.17 | 1 | TC-001894 |
| BR-BUYPASS-PIPS-17-2 p.17 | 1 | TC-001907 |
| BR-BUYPASS-PIPS-173-1 p.173 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-174-1 p.174 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-174-2 p.174 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-175-2 p.175 | 1 | TC-001931 |
| BR-BUYPASS-PIPS-175-3 p.175 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-177-2 p.177 | 1 | TC-001931 |
| BR-BUYPASS-PIPS-177-3 p.177 | 1 | TC-001931 |
| BR-BUYPASS-PIPS-179-1 p.179 | 2 | TC-000964, TC-001931 |
| BR-BUYPASS-PIPS-179-2 p.179 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-18-4 p.18 | 1 | TC-001804 |
| BR-BUYPASS-PIPS-18-9 p.18 | 1 | TC-001909 |
| BR-BUYPASS-PIPS-180-1 p.180 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-181-2 p.181 | 4 | TC-000965, TC-000966, TC-000967, TC-001931 |
| BR-BUYPASS-PIPS-182-3 p.182 | 12 | TC-000754, TC-000760, TC-000765, TC-000770, TC-000775 |
| BR-BUYPASS-PIPS-183-1 p.183 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-183-2 p.183 | 1 | TC-001883 |
| BR-BUYPASS-PIPS-183-3 p.183 | 1 | TC-001931 |
| BR-BUYPASS-PIPS-185-2 p.185 | 1 | TC-000955 |
| BR-BUYPASS-PIPS-185-5 p.185 | 12 | TC-000751, TC-000757, TC-000763, TC-000768, TC-000773 |
| BR-BUYPASS-PIPS-186-1 p.186 | 2 | TC-000968, TC-001931 |
| BR-BUYPASS-PIPS-21-2 p.21 | 1 | TC-001835 |
| BR-BUYPASS-PIPS-23-6 p.23 | 12 | TC-000787, TC-000789, TC-000791, TC-000793, TC-000795 |
| BR-BUYPASS-PIPS-24-2 p.24 | 12 | TC-000755, TC-000761, TC-000766, TC-000771, TC-000776 |
| BR-BUYPASS-PIPS-24-3 p.24 | 24 | TC-000755, TC-000761, TC-000766, TC-000771, TC-000776 |
| BR-BUYPASS-PIPS-24-6 p.24 | 12 | TC-000787, TC-000789, TC-000791, TC-000793, TC-000795 |
| BR-BUYPASS-PIPS-24-7 p.24 | 24 | TC-000750, TC-000756, TC-000762, TC-000767, TC-000772 |
| BR-BUYPASS-PIPS-25-1 p.25 | 1 | TC-000951 |
| BR-BUYPASS-PIPS-27-10 p.27 | 12 | TC-000788, TC-000790, TC-000792, TC-000794, TC-000796 |
| BR-BUYPASS-PIPS-27-11 p.27 | 1 | TC-001860 |
| BR-BUYPASS-PIPS-27-4 p.27 | 1 | TC-001860 |
| BR-BUYPASS-PIPS-27-7 p.27 | 1 | TC-001921 |
| BR-BUYPASS-PIPS-27-9 p.27 | 1 | TC-001921 |
| BR-BUYPASS-PIPS-28-6 p.28 | 1 | TC-001895 |
| BR-BUYPASS-PIPS-29-7 p.29 | 1 | TC-001896 |
| BR-BUYPASS-PIPS-30-3 p.30 | 7 | TC-000752, TC-000758, TC-000764, TC-000769, TC-000774 |
| BR-BUYPASS-PIPS-33-1 p.33 | 1 | TC-001861 |
| BR-BUYPASS-PIPS-33-4 p.33 | 1 | TC-001861 |
| BR-BUYPASS-PIPS-34-2 p.34 | 1 | TC-001862 |
| BR-BUYPASS-PIPS-34-6 p.34 | 1 | TC-001862 |
| BR-BUYPASS-PIPS-35-2 p.35 | 1 | TC-001872 |
| BR-BUYPASS-PIPS-35-3 p.35 | 1 | TC-001872 |
| BR-BUYPASS-PIPS-35-4 p.35 | 1 | TC-001872 |
| BR-BUYPASS-PIPS-35-5 p.35 | 1 | TC-001872 |
| BR-FISERV-EMV-GUIDE-100-6 p.100 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-100-7 p.100 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-100-8 p.100 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-100-9 p.100 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-101-11 p.101 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-101-3 p.101 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-101-4 p.101 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-101-5 p.101 | 1 | TC-001839 |
| BR-FISERV-EMV-GUIDE-102-1 p.102 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-102-2 p.102 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-102-4 p.102 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-102-5 p.102 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-103-3 p.103 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-103-4 p.103 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-103-5 p.103 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-104-1 p.104 | 1 | TC-001840 |
| BR-FISERV-EMV-GUIDE-107-5 p.107 | 1 | TC-001869 |
| BR-FISERV-EMV-GUIDE-107-7 p.107 | 1 | TC-001869 |
| BR-FISERV-EMV-GUIDE-108-1 p.108 | 1 | TC-001869 |
| BR-FISERV-EMV-GUIDE-108-2 p.108 | 1 | TC-001869 |
| BR-FISERV-EMV-GUIDE-110-1 p.110 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-111-1 p.111 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-111-2 p.111 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-111-3 p.111 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-112-2 p.112 | 1 | TC-001928 |
| BR-FISERV-EMV-GUIDE-112-4 p.112 | 8 | TC-000002, TC-000055, TC-000111, TC-000159, TC-000203 |
| BR-FISERV-EMV-GUIDE-113-1 p.113 | 8 | TC-000002, TC-000055, TC-000111, TC-000159, TC-000203 |
| BR-FISERV-EMV-GUIDE-113-2 p.113 | 1 | TC-001928 |
| BR-FISERV-EMV-GUIDE-114-1 p.114 | 1 | TC-001928 |
| BR-FISERV-EMV-GUIDE-114-2 p.114 | 1 | TC-001928 |
| BR-FISERV-EMV-GUIDE-115-10 p.115 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-115-13 p.115 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-115-16 p.115 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-115-17 p.115 | 1 | TC-001791 |
| BR-FISERV-EMV-GUIDE-115-20 p.115 | 8 | TC-000015, TC-000068, TC-000123, TC-000170, TC-000214 |
| BR-FISERV-EMV-GUIDE-115-27 p.115 | 1 | TC-001792 |
| BR-FISERV-EMV-GUIDE-115-6 p.115 | 1 | TC-001792 |
| BR-FISERV-EMV-GUIDE-116-9 p.116 | 1 | TC-001928 |
| BR-FISERV-EMV-GUIDE-120-11 p.120 | 1 | TC-001798 |
| BR-FISERV-EMV-GUIDE-120-3 p.120 | 47 | TC-000001, TC-000054, TC-000110, TC-000158, TC-000202 |
| BR-FISERV-EMV-GUIDE-125-1 p.125 | 1 | TC-001834 |
| BR-FISERV-EMV-GUIDE-126-1 p.126 | 8 | TC-000051, TC-000105, TC-000155, TC-000200, TC-000245 |
| BR-FISERV-EMV-GUIDE-130-1 p.130 | 1 | TC-001814 |
| BR-FISERV-EMV-GUIDE-135-1 p.135 | 1 | TC-001815 |
| BR-FISERV-EMV-GUIDE-135-3 p.135 | 1 | TC-001918 |
| BR-FISERV-EMV-GUIDE-135-4 p.135 | 1 | TC-001918 |
| BR-FISERV-EMV-GUIDE-138-1 p.138 | 1 | TC-000100 |
| BR-FISERV-EMV-GUIDE-138-2 p.138 | 1 | TC-000100 |
| BR-FISERV-EMV-GUIDE-138-4 p.138 | 1 | TC-000100 |
| BR-FISERV-EMV-GUIDE-141-1 p.141 | 1 | TC-000956 |
| BR-FISERV-EMV-GUIDE-141-3 p.141 | 1 | TC-001855 |
| BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | TC-000046 |
| BR-FISERV-EMV-GUIDE-142-2 p.142 | 1 | TC-001855 |
| BR-FISERV-EMV-GUIDE-142-3 p.142 | 3 | TC-000125, TC-000265, TC-000972 |
| BR-FISERV-EMV-GUIDE-151-3 p.151 | 1 | TC-001932 |
| BR-FISERV-EMV-GUIDE-152-10 p.152 | 1 | TC-001932 |
| BR-FISERV-EMV-GUIDE-152-11 p.152 | 1 | TC-001932 |
| BR-FISERV-EMV-GUIDE-152-5 p.152 | 7 | TC-000040, TC-000078, TC-000151, TC-000196, TC-000241 |
| BR-FISERV-EMV-GUIDE-153-2 p.153 | 1 | TC-001932 |
| BR-FISERV-EMV-GUIDE-153-7 p.153 | 1 | TC-001932 |
| BR-FISERV-EMV-GUIDE-156-1 p.156 | 1 | TC-000078 |
| BR-FISERV-EMV-GUIDE-156-8 p.156 | 1 | TC-001932 |
| BR-FISERV-EMV-GUIDE-157-10 p.157 | 2 | TC-000973, TC-001932 |
| BR-FISERV-EMV-GUIDE-157-11 p.157 | 2 | TC-000973, TC-001932 |
| BR-FISERV-EMV-GUIDE-161-10 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-11 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-12 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-13 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-14 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-15 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-3 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-4 p.161 | 1 | TC-001841 |
| BR-FISERV-EMV-GUIDE-161-9 p.161 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-162-1 p.162 | 1 | TC-001943 |
| BR-FISERV-EMV-GUIDE-162-2 p.162 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-163-11 p.163 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-163-13 p.163 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-163-14 p.163 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-163-15 p.163 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-163-3 p.163 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-163-4 p.163 | 1 | TC-001842 |
| BR-FISERV-EMV-GUIDE-169-3 p.169 | 1 | TC-001924 |
| BR-FISERV-EMV-GUIDE-169-6 p.169 | 1 | TC-001924 |
| BR-FISERV-EMV-GUIDE-173-2 p.173 | 2 | TC-000971, TC-001924 |
| BR-FISERV-EMV-GUIDE-175-3 p.175 | 1 | TC-001805 |
| BR-FISERV-EMV-GUIDE-176-4 p.176 | 7 | TC-000044, TC-000096, TC-000753, TC-000759, TC-000814 |
| BR-FISERV-EMV-GUIDE-178-3 p.178 | 1 | TC-001919 |
| BR-FISERV-EMV-GUIDE-178-4 p.178 | 1 | TC-001919 |
| BR-FISERV-EMV-GUIDE-183-2 p.183 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-183-3 p.183 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-183-7 p.183 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-186-3 p.186 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-186-6 p.186 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-186-7 p.186 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-195-2 p.195 | 1 | TC-001800 |
| BR-FISERV-EMV-GUIDE-199-3 p.199 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-199-5 p.199 | 1 | TC-001910 |
| BR-FISERV-EMV-GUIDE-24-2 p.24 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-26-1 p.26 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-26-2 p.26 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-26-3 p.26 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-26-4 p.26 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-26-5 p.26 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-27-1 p.27 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-27-2 p.27 | 1 | TC-001911 |
| BR-FISERV-EMV-GUIDE-33-5 p.33 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-33-7 p.33 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-34-1 p.34 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-34-2 p.34 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-34-3 p.34 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-34-5 p.34 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-34-7 p.34 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-34-8 p.34 | 1 | TC-001912 |
| BR-FISERV-EMV-GUIDE-36-7 p.36 | 1 | TC-001946 |
| BR-FISERV-EMV-GUIDE-37-1 p.37 | 1 | TC-000958 |
| BR-FISERV-EMV-GUIDE-37-10 p.37 | 8 | TC-000019, TC-000072, TC-000129, TC-000174, TC-000218 |
| BR-FISERV-EMV-GUIDE-37-2 p.37 | 1 | TC-001873 |
| BR-FISERV-EMV-GUIDE-37-8 p.37 | 1 | TC-001873 |
| BR-FISERV-EMV-GUIDE-37-9 p.37 | 1 | TC-001873 |
| BR-FISERV-EMV-GUIDE-39-5 p.39 | 1 | TC-001873 |
| BR-FISERV-EMV-GUIDE-39-8 p.39 | 1 | TC-000958 |
| BR-FISERV-EMV-GUIDE-39-9 p.39 | 1 | TC-000958 |
| BR-FISERV-EMV-GUIDE-40-1 p.40 | 1 | TC-000958 |
| BR-FISERV-EMV-GUIDE-42-1 p.42 | 2 | TC-000949, TC-001913 |
| BR-FISERV-EMV-GUIDE-42-2 p.42 | 2 | TC-000124, TC-000264 |
| BR-FISERV-EMV-GUIDE-42-3 p.42 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-42-4 p.42 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-42-5 p.42 | 1 | TC-001913 |
| BR-FISERV-EMV-GUIDE-42-6 p.42 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | TC-001913 |
| BR-FISERV-EMV-GUIDE-43-5 p.43 | 1 | TC-001913 |
| BR-FISERV-EMV-GUIDE-43-7 p.43 | 1 | TC-001829 |
| BR-FISERV-EMV-GUIDE-45-1 p.45 | 1 | TC-001913 |
| BR-FISERV-EMV-GUIDE-45-6 p.45 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-46-1 p.46 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-46-3 p.46 | 1 | TC-001823 |
| BR-FISERV-EMV-GUIDE-46-4 p.46 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-48-2 p.48 | 2 | TC-000047, TC-000101 |
| BR-FISERV-EMV-GUIDE-48-5 p.48 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-49-2 p.49 | 15 | TC-000024, TC-000077, TC-000134, TC-000179, TC-000223 |
| BR-FISERV-EMV-GUIDE-49-5 p.49 | 1 | TC-001913 |
| BR-FISERV-EMV-GUIDE-49-6 p.49 | 1 | TC-001913 |
| BR-FISERV-EMV-GUIDE-49-7 p.49 | 1 | TC-001913 |
| BR-FISERV-EMV-GUIDE-50-2 p.50 | 15 | TC-000024, TC-000077, TC-000134, TC-000179, TC-000223 |
| BR-FISERV-EMV-GUIDE-50-3 p.50 | 15 | TC-000024, TC-000077, TC-000134, TC-000179, TC-000223 |
| BR-FISERV-EMV-GUIDE-50-4 p.50 | 1 | TC-001848 |
| BR-FISERV-EMV-GUIDE-50-5 p.50 | 7 | TC-000040, TC-000078, TC-000151, TC-000196, TC-000241 |
| BR-FISERV-EMV-GUIDE-52-1 p.52 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-53-1 p.53 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-54-2 p.54 | 2 | TC-000047, TC-000101 |
| BR-FISERV-EMV-GUIDE-54-3 p.54 | 2 | TC-000047, TC-000101 |
| BR-FISERV-EMV-GUIDE-54-6 p.54 | 1 | TC-001827 |
| BR-FISERV-EMV-GUIDE-55-1 p.55 | 2 | TC-000042, TC-000094 |
| BR-FISERV-EMV-GUIDE-55-12 p.55 | 2 | TC-000956, TC-000957 |
| BR-FISERV-EMV-GUIDE-55-13 p.55 | 2 | TC-000956, TC-000957 |
| BR-FISERV-EMV-GUIDE-55-14 p.55 | 2 | TC-000956, TC-000957 |
| BR-FISERV-EMV-GUIDE-55-15 p.55 | 2 | TC-000956, TC-000957 |
| BR-FISERV-EMV-GUIDE-56-10 p.56 | 2 | TC-000042, TC-000094 |
| BR-FISERV-EMV-GUIDE-56-11 p.56 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-56-12 p.56 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-56-14 p.56 | 1 | TC-000947 |
| BR-FISERV-EMV-GUIDE-56-5 p.56 | 2 | TC-000047, TC-000101 |
| BR-FISERV-EMV-GUIDE-56-6 p.56 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-56-7 p.56 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-56-9 p.56 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-58-10 p.58 | 8 | TC-000052, TC-000106, TC-000156, TC-000201, TC-000246 |
| BR-FISERV-EMV-GUIDE-58-11 p.58 | 3 | TC-000043, TC-000095, TC-000963 |
| BR-FISERV-EMV-GUIDE-58-3 p.58 | 1 | TC-001933 |
| BR-FISERV-EMV-GUIDE-58-5 p.58 | 3 | TC-000049, TC-000103, TC-000294 |
| BR-FISERV-EMV-GUIDE-58-8 p.58 | 7 | TC-000044, TC-000096, TC-000753, TC-000759, TC-000814 |
| BR-FISERV-EMV-GUIDE-59-3 p.59 | 1 | TC-001795 |
| BR-FISERV-EMV-GUIDE-59-4 p.59 | 1 | TC-001795 |
| BR-FISERV-EMV-GUIDE-63-4 p.63 | 7 | TC-000044, TC-000096, TC-000753, TC-000759, TC-000814 |
| BR-FISERV-EMV-GUIDE-63-6 p.63 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-63-7 p.63 | 1 | TC-000097 |
| BR-FISERV-EMV-GUIDE-63-8 p.63 | 1 | TC-000098 |
| BR-FISERV-EMV-GUIDE-64-7 p.64 | 7 | TC-000024, TC-000077, TC-000134, TC-000179, TC-000223 |
| BR-FISERV-EMV-GUIDE-64-8 p.64 | 7 | TC-000024, TC-000077, TC-000134, TC-000179, TC-000223 |
| BR-FISERV-EMV-GUIDE-64-9 p.64 | 7 | TC-000024, TC-000077, TC-000134, TC-000179, TC-000223 |
| BR-FISERV-EMV-GUIDE-65-1 p.65 | 1 | TC-000098 |
| BR-FISERV-EMV-GUIDE-66-3 p.66 | 1 | TC-001914 |
| BR-FISERV-EMV-GUIDE-66-4 p.66 | 2 | TC-000948, TC-001914 |
| BR-FISERV-EMV-GUIDE-66-7 p.66 | 1 | TC-001914 |
| BR-FISERV-EMV-GUIDE-66-8 p.66 | 1 | TC-001914 |
| BR-FISERV-EMV-GUIDE-67-1 p.67 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-67-5 p.67 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-67-7 p.67 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-68-10 p.68 | 1 | TC-001914 |
| BR-FISERV-EMV-GUIDE-68-11 p.68 | 1 | TC-001914 |
| BR-FISERV-EMV-GUIDE-68-6 p.68 | 1 | TC-001914 |
| BR-FISERV-EMV-GUIDE-68-9 p.68 | 1 | TC-001914 |
| BR-FISERV-EMV-GUIDE-69-3 p.69 | 1 | TC-000269 |
| BR-FISERV-EMV-GUIDE-69-4 p.69 | 8 | TC-000002, TC-000055, TC-000111, TC-000159, TC-000203 |
| BR-FISERV-EMV-GUIDE-70-7 p.70 | 1 | TC-000295 |
| BR-FISERV-EMV-GUIDE-70-8 p.70 | 1 | TC-000295 |
| BR-FISERV-EMV-GUIDE-71-4 p.71 | 1 | TC-000261 |
| BR-FISERV-EMV-GUIDE-71-5 p.71 | 1 | TC-000259 |
| BR-FISERV-EMV-GUIDE-71-6 p.71 | 4 | TC-000014, TC-000067, TC-000262, TC-000969 |
| BR-FISERV-EMV-GUIDE-71-7 p.71 | 1 | TC-000120 |
| BR-FISERV-EMV-GUIDE-71-8 p.71 | 1 | TC-001915 |
| BR-FISERV-EMV-GUIDE-72-10 p.72 | 8 | TC-000012, TC-000065, TC-000121, TC-000168, TC-000212 |
| BR-FISERV-EMV-GUIDE-72-11 p.72 | 1 | TC-001915 |
| BR-FISERV-EMV-GUIDE-72-2 p.72 | 3 | TC-000956, TC-000957, TC-001915 |
| BR-FISERV-EMV-GUIDE-72-4 p.72 | 2 | TC-000969, TC-001915 |
| BR-FISERV-EMV-GUIDE-72-5 p.72 | 1 | TC-001915 |
| BR-FISERV-EMV-GUIDE-72-6 p.72 | 2 | TC-000969, TC-001915 |
| BR-FISERV-EMV-GUIDE-72-7 p.72 | 1 | TC-001915 |
| BR-FISERV-EMV-GUIDE-72-9 p.72 | 2 | TC-000969, TC-001915 |
| BR-FISERV-EMV-GUIDE-73-6 p.73 | 2 | TC-000042, TC-000094 |
| BR-FISERV-EMV-GUIDE-74-4 p.74 | 1 | TC-001828 |
| BR-FISERV-EMV-GUIDE-74-6 p.74 | 1 | TC-001831 |
| BR-FISERV-EMV-GUIDE-74-7 p.74 | 1 | TC-000948 |
| BR-FISERV-EMV-GUIDE-74-9 p.74 | 2 | TC-000970, TC-001929 |
| BR-FISERV-EMV-GUIDE-75-2 p.75 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-75-4 p.75 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-75-5 p.75 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-76-11 p.76 | 1 | TC-001929 |
| BR-FISERV-EMV-GUIDE-76-3 p.76 | 1 | TC-001850 |
| BR-FISERV-EMV-GUIDE-76-5 p.76 | 4 | TC-000008, TC-000061, TC-000909, TC-000915 |
| BR-FISERV-EMV-GUIDE-76-6 p.76 | 8 | TC-000002, TC-000055, TC-000111, TC-000159, TC-000203 |
| BR-FISERV-EMV-GUIDE-77-2 p.77 | 4 | TC-000008, TC-000061, TC-000909, TC-000915 |
| BR-FISERV-EMV-GUIDE-77-5 p.77 | 1 | TC-000958 |
| BR-FISERV-EMV-GUIDE-78-2 p.78 | 4 | TC-000008, TC-000061, TC-000909, TC-000915 |
| BR-FISERV-EMV-GUIDE-78-4 p.78 | 1 | TC-001929 |
| BR-FISERV-EMV-GUIDE-79-3 p.79 | 4 | TC-000008, TC-000061, TC-000909, TC-000915 |
| BR-FISERV-EMV-GUIDE-79-4 p.79 | 1 | TC-001822 |
| BR-FISERV-EMV-GUIDE-80-1 p.80 | 1 | TC-000957 |
| BR-FISERV-EMV-GUIDE-81-2 p.81 | 1 | TC-001864 |
| BR-FISERV-EMV-GUIDE-81-4 p.81 | 1 | TC-001929 |
| BR-FISERV-EMV-GUIDE-81-5 p.81 | 1 | TC-001929 |
| BR-FISERV-EMV-GUIDE-84-6 p.84 | 1 | TC-001864 |
| BR-FISERV-EMV-GUIDE-87-2 p.87 | 7 | TC-000040, TC-000078, TC-000151, TC-000196, TC-000241 |
| BR-FISERV-EMV-GUIDE-87-6 p.87 | 1 | TC-000948 |
| BR-FISERV-EMV-GUIDE-87-8 p.87 | 1 | TC-001825 |
| BR-FISERV-EMV-GUIDE-88-14 p.88 | 1 | TC-001830 |
| BR-FISERV-EMV-GUIDE-88-2 p.88 | 7 | TC-000044, TC-000096, TC-000753, TC-000759, TC-000814 |
| BR-FISERV-EMV-GUIDE-94-1 p.94 | 1 | TC-001927 |
| BR-FISERV-EMV-GUIDE-94-4 p.94 | 1 | TC-000959 |
| BR-FISERV-EMV-GUIDE-95-1 p.95 | 1 | TC-001927 |
| BR-FISERV-EMV-GUIDE-95-2 p.95 | 1 | TC-001838 |
| BR-FISERV-EMV-GUIDE-96-1 p.96 | 1 | TC-001837 |
| BR-FISERV-EMV-GUIDE-96-3 p.96 | 1 | TC-001927 |
| BR-FISERV-EMV-GUIDE-96-4 p.96 | 1 | TC-000958 |
| BR-FISERV-EMV-GUIDE-98-1 p.98 | 1 | TC-001853 |
| BR-FISERV-EMV-GUIDE-98-2 p.98 | 1 | TC-001853 |
| BR-FISERV-EMV-GUIDE-98-3 p.98 | 1 | TC-001853 |
| BR-FISERV-EMV-GUIDE-98-4 p.98 | 1 | TC-001853 |
| BR-FISERV-EMV-GUIDE-98-5 p.98 | 1 | TC-001853 |
| BR-FISERV-EMV-GUIDE-99-1 p.99 | 1 | TC-001853 |
| BR-FISERV-EMV-GUIDE-99-2 p.99 | 1 | TC-001853 |
| BR-FISERV-EMV-GUIDE-99-3 p.99 | 1 | TC-001853 |

## Reverse index - test data file -> test case

2380 files; the full list is in `traceability/test_data_index.csv`.

