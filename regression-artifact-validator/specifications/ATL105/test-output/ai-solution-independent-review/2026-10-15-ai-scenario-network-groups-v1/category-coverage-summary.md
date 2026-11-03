# AI Test Scenarios by Card-Brand Network Proxy

Grouping uses only the explicit `flow_axis_values.card_brand` field. The normalized network label is a display proxy; it is not a direct ATL105 networkType assertion.

- Approved scenarios: 12,254
- Candidate test cases: 21,254

| Network group | Scenarios | Linked test cases | Status |
|---|---:|---:|---|
| AMERICAN_EXPRESS | 54 | 54 | EXPLICIT_CARD_BRAND |
| DISCOVER | 54 | 54 | EXPLICIT_CARD_BRAND |
| MASTERCARD | 54 | 54 | EXPLICIT_CARD_BRAND |
| NETWORK_NOT_DECLARED | 11,982 | 20,982 | NOT_DECLARED |
| VISA | 110 | 110 | EXPLICIT_CARD_BRAND |

The CSV preserves each source card-brand value and classification basis. Unknown, multiple, and undeclared brands are not assigned a guessed network.
