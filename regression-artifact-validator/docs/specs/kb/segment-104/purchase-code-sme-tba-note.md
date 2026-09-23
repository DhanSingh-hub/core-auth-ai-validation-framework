# Purchase Code: SME and TBA Learning Note

Element 80, Purchase Code, is conditional and identifies the purchase code associated with the transaction. ATL105 Section 12.5 defines it as variable-length alphanumeric, maximum 16 characters.

## Validation Boundary

- When absent or logically empty: no requirement is invented.
- When populated: accept only 1–16 alphanumeric characters.
- Do not convert it to numeric, pad it, or impose a local code enumeration unless a separately approved program specification provides one.

## Test Cases

| Case | Expected |
|---|---|
| Empty conditional value | Pass |
| `PC1234567890` | Pass |
| 16 alphanumeric characters | Pass |
| 17 characters | Fail |
| Spaces or punctuation | Fail under the current machine-readable rule |

The current rule validates ATL105's field envelope, not the business meaning of a merchant-specific purchase-code scheme.
