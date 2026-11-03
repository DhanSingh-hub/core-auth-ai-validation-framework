# EBT Program Data (Element 164): SME/TBA Learning Note

## Meaning

Element 164 carries program-specific EBT data, including Healthy Incentives Program (HIP) data. It has a three-digit Total Length followed by one to six Program Data subelements.

## Request and Response Tags

| Message direction | Valid TAGs | Meaning |
| --- | --- | --- |
| Request | `50` | HIP purchase/return amount |
| Request | `IT` | HIP Internet purchase shipping address/ZIP |
| Response | `51` | HIP incentive earned/returned |
| Response | `52` | HIP month-to-date incentive earned |

## Appendix M Positional Layout

For TAG `50`, `51`, or `52`:

```text
TAG(2) + LEN(2) + ACCOUNT TYPE(98) + AMOUNT TYPE
+ CURRENCY CODE(840) + AMOUNT DESCRIPTOR(0/C/D) + DETAIL(12 digits)
```

For response TAGs `51` and `52`, Section 13.2 lists matching AMOUNT TYPE values. For request TAG `50`, Section 13.2 and the worked strings use `50`, but Appendix M-2/M-4 prose says `40`; `SEG103-SME-009` remains OPEN. Both source-listed candidates are accepted only with a `REVIEW_REQUIRED` warning until SME/TBA resolves the conflict.

For TAG `IT`:

```text
TAG(2) + LEN(2) + ADDRESS(28) + ZIP(up to 9 digits)
```

The fields omitted for `IT` are explicitly not required by ATL105. Unknown tags are rejected by the current baseline because the catalog is now fully transcribed from Section 13.2 and Appendix M.

## HIP Reasoning

HIP incentives are calculated and tracked by the authorizer. Incentive balances can be returned in addition to normal balances and reset at the beginning of the fiscal month. State support is issuer/configuration dependent, but the wire layout is spec-defined.

## TBA Artifact Pattern

```text
BR: TAG 50 request data uses ACCOUNT TYPE 98 and fixed currency 840.
TS: Submit valid TAG 50, TAG IT, and response TAG 51/52 examples.
TC: Reject unknown TAG, wrong LEN, wrong amount type, wrong currency, or invalid detail.
TD: Appendix M examples plus mutated positional fields.
```

## Validator Boundary

`Segment103PayloadValidator` enforces the full granular layout when the JSON fixture supplies granular fields, while preserving compatibility with legacy flat synthetic blobs.
