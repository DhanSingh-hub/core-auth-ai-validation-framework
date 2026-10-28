# Account Number and Entry Method: SME/TBA Learning Note

## Core Idea

Element 2, Account Number, is not always a plain PAN. Its representation depends on how the POS obtained the account information.

```text
POS entry method
  -> account representation
  -> Segment 100 Element 2
  -> discretionary/EMV/token data dependencies
  -> Prompt Code and entry-mode context
```

## Common Entry Contexts

| POS entry method | Account representation | Key analysis question |
| --- | --- | --- |
| Manual/keyed | Account number entered by customer or clerk | Is expiration data also required? |
| Magnetic stripe Track 2 | Track data with `=` separator and discretionary content | Was Track 2 sent according to the flow? |
| Magnetic stripe Track 1 | Track 1 data with cardholder/name separators | Is the Track 1 fallback rule applicable? |
| EMV contact chip | Chip/account context plus EMV data | Is chip data represented in the EMV segment rather than substituted with track data? |
| Contactless/NFC | Contactless chip or token context | Is Segment 123 or other required token data present? |
| Network/tokenized account | Token or TransArmor representation | Is the tokenization mode explicit? |

## SME Reasoning

Ask:

1. Did the POS key the account, read a magnetic stripe, read a chip, or receive a token?
2. Is the data full, truncated, encrypted, tokenized, or synthetic?
3. Does the entry method agree with Point-of-Service Entry Mode data where that data is in scope?
4. Does the card type change the preferred track or fallback behavior?
5. Is PIN data required because of debit/EBT context?
6. Is this an initial transaction or a follow-up where truncated/token data is permitted?

The same visible account number can be valid in one context and invalid in another because the entry method changes the expected supporting data.

## TBA Dependency Chain

```text
Entry mode
  -> Account Number format
  -> Card Discretionary Block Data
  -> expiration/PIN/track/token data
  -> companion segment requirement
  -> serialization and security handling
```

A requirement such as “Account Number is valid” is too vague. A useful requirement states the entry method and expected representation.

Example:

```text
For a Track 2 transaction, Element 2 contains the source data before '='
and Element 12 contains the source data after '='. The separator joins
these fields on the wire instead of a Field Separator; it is not part of
the independently represented Element 2 value. Track 1 splits at the first '^'.
```

## Security and Test-Data Guidance

- Use synthetic PANs and synthetic track data only.
- Do not place live PANs, PINs, keys, or production tokens in test artifacts.
- Test masking separately from host-message representation.
- Treat token and TransArmor representations as distinct from a plain PAN.
- Do not assume that a JSON field called `accountNumber` proves the entry method.

## Current Validator Boundary

The local AccountNumberEntryValidator checks explicitly selected predicates for:

- Manual alphanumeric account representation
- Non-TransArmor request Element 2 maximum of 24 bytes
- Track 1/Track 2 source separator and the exact split into Elements 2 and 12
- Supplied Element 12 maximum of 51 bytes
- Manual-card expiration MMYY representation, without rejecting an expired date merely for being expired
- Skipped Element 12 for request card types 093, 095 and 096
- Element 12 expiration-data presence for declared TransArmor context
- Accel/STAR e-commerce and one-time-bill-payment expiration presence, and the post-May-2025 internal-database COF recurring omission context
- Explicit token/TransArmor fixture context (a metadata check only, not validation of token authenticity or encrypted representation)

The focused [execution package](../../../../test-output/test-json/segment-100-elements-2-and-12-execution-package.json)
contains 13 source-hash-bound atomic BR chains and 42 synthetic fixtures. Its strict
execution-readiness gate and local assertions pass; this does not approve source
interpretations or certify transactions. See the [coverage report](../../../../test-output/test-solution-independent-review/segment-100-elements-2-and-12-coverage.md).

This validator does not cover complete Track 1/2 grammar and combined track limits,
token BIN/Luhn qualification, TransArmor header/EDATA decoding, full EMV TLV content,
all Appendix J entry-mode combinations, Real Time Account Updater history,
Certegy/SCAN identity formats, wire serialization, or host outcomes. Other modules
may cover some of these separately; this package does not claim that coverage.
Unknown entry modes fail closed in opted-in account-entry validation. No converter
mapping is inferred for producer aliases such as TrackData or a ':' separator.

## Producer JSON Mapping Boundary

A logical JSON property such as `TrackData` is not an ATL105 element name. Before
validating or converting it, the producer and consumer must agree on a versioned
mapping that declares the POS entry method and explains whether the property is raw
track input or already normalized Element 2/12 content. For Track 2, normalization
must follow the source `=` split; for Track 1, the first `^` split applies. Do not
assume a different delimiter is equivalent, reconstruct a source field from an
example, or infer the entry method from the presence of Segment 130. Keep EMV data
mapped to Segment 130 independently. Until that mapping is agreed, preserve the
input and classify adapter/semantic validation as `REVIEW_REQUIRED` rather than
declaring the request invalid. See open developer discussion [AID-0005](../../../../registers/views/ai-dev-discussion.md#aid-0005).

## Review Checklist

- Is the entry method explicit?
- Does Element 2 match the declared method?
- Is Track 1/2 data structurally plausible?
- Is Card Discretionary Block Data consistent with the track method?
- Is EMV data carried in the EMV segment when required?
- Is token/TransArmor mode explicit?
- Are follow-up transaction rules different from initial transaction rules?
- Are sensitive values synthetic and appropriately masked?
