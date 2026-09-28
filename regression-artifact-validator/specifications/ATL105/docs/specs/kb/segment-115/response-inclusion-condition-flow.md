# Segment 115 Response-Inclusion Condition Flow

```text
Host builds a Financial Transaction Response (or EMV variant)
  |
  v
Set Element 115 (Additional Information Data Segment Flag):
  0 = no additional segment follows
  1 = an additional segment follows
  |
  v
If 1: does this transaction have Additional Information (balances, AVS/CVV,
      tokens, fraud scores, etc.) to report?
  |
  yes --> include Segment 112 at Field 16/17/18
  |
  v
Independently: does the request's Loyalty Information Version (Element 150)
equal 2?
  |
  no  --> Segment 115 is omitted regardless of Element 115's value
  |
  yes --> include Segment 115 at Field 17/18/19
  |
  v
[PROVISIONAL SEG115-SME-002]: exact interaction between Element 115's
single flag value and these two independently-triggered segments is not
fully documented — do not assume a specific undocumented flag encoding.
```
