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
For a Track 2 transaction, Element 2 shall contain the Track 2 account
representation required by the flow, including its separator and required
discretionary data; a plain PAN without Track 2 context shall not pass
Track 2 validation.
```

## Security and Test-Data Guidance

- Use synthetic PANs and synthetic track data only.
- Do not place live PANs, PINs, keys, or production tokens in test artifacts.
- Test masking separately from host-message representation.
- Treat token and TransArmor representations as distinct from a plain PAN.
- Do not assume that a JSON field called `accountNumber` proves the entry method.

## Current Validator Boundary

The current validator checks declared dependency evidence for:

- Manual alphanumeric account representation
- Track 1/Track 2 separator presence
- EMV context not using discretionary track data as a chip-data substitute
- Explicit token/TransArmor representation

It does not yet validate complete Track 1/2 grammar, Luhn behavior, full EMV TLV content, or all Appendix J entry-mode combinations. Those belong to dedicated modules.

## Review Checklist

- Is the entry method explicit?
- Does Element 2 match the declared method?
- Is Track 1/2 data structurally plausible?
- Is Card Discretionary Block Data consistent with the track method?
- Is EMV data carried in the EMV segment when required?
- Is token/TransArmor mode explicit?
- Are follow-up transaction rules different from initial transaction rules?
- Are sensitive values synthetic and appropriately masked?
