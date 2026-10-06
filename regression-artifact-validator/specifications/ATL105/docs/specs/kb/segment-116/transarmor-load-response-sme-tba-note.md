# TransArmor Load Response: SME/TBA Learning Note

## Core Idea

The response to a Key/Key ID Load request is not documented as a numbered data segment table anywhere in this workspace's source; it is only ever referred to by name — "the TransArmor Load Response" — inside the Chapter 13 definitions of Elements 155, 156, and 157. This is a narrower but real piece of evidence: we know what fields exist, but not their order, separators, or whether they are wrapped in a Segment 116-style Segment Type/Segment Length container.

```text
Approved Key/Key ID Load
  -> Key ID (155): fixed 11 alphanumeric characters, reused for all subsequent TransArmor transactions
  -> Key Data Length (156): numeric, 000-999
  -> Key Data (157): alphanumeric, up to 999 bytes, carries the new key

Declined Key/Key ID Load
  -> Key ID (155): still required
  -> Key Data Length (156): still required
  -> Key Data (157): carries an error message instead of key material
```

## Source-Confirmed Facts

| Field | Element | Type / Length | Requiredness | Notes |
|---|---|---|---|---|
| Key ID | 155 | AN, fixed 11 | Required | Once approved and used, this Key ID must be reused for all subsequent TransArmor transactions from the device. |
| Key Data Length | 156 | N, max 3 (000-999) | Required | Identifies the length of the following Key Data field. |
| Key Data | 157 | AN, max 999 | Required | New encryption key + Key ID on approval; error message on decline. Signing/Signed Keys can use the full 999 bytes on approval. |

## SME Questions

1. Is the TransArmor Load Response a Segment 116-numbered container (Segment Type `116` + Segment Length + these three fields), or a separate, unnumbered positional response — comparable to how the Electronic Mail Response (Segment 109) is a distinct positional structure, not another Segment 109?
2. Are there other required or conditional fields in the response beyond Key ID, Key Data Length, and Key Data (for example, a response/status code)?
3. What is the exact format of the "error message" carried in Key Data on a decline?

## TBA Rule Pattern

```text
BR: An approved TransArmor Key/Key ID Load shall return a Key ID that the device reuses for all subsequent TransArmor transactions.
TS: Device receives an approved response and a declined response.
TC: Validate Key ID length, Key Data Length range, and Key Data maximum length for both outcomes.
TD: Sanitized converter-ready response pair with synthetic Key ID and Key Data.
```

## Current Boundary

The validator checks the three known fields' formats when a response object is supplied, but does not assert where those fields sit inside the overall response message, because that container structure is unconfirmed (`SEG116-SME-003`).
