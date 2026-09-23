# Segment 108 Account Number and Card-Not-Present Substitution: SME and TBA Learning Note

Mirrors [Segment 100's Account Number and Entry Method note](../segment-100/account-number-sme-tba-note.md), scoped to Loyalty Account Number (Element 139) and its card-not-present substitution.

## Core Idea

Segment 108's identity field is **Loyalty Account Number (Element 139)**, not Segment 100's Element 2. Its representation depends on whether the loyalty card is physically present and readable — directly analogous to Segment 100's account-number entry-method dependency, but with a documented **substitution** path rather than just alternate entry methods.

```text
Loyalty card present and readable
  -> Loyalty Account Number (139) populated from the swipe/read
Loyalty card NOT present or unreadable after 3 swipe attempts
  -> Street Address (144) + Phone Number, Loyalty (145) manually keyed IN LIEU OF Loyalty Account Number (139)
  -> Expiration Date (146) manually keyed (MMYY); if none present on the card, device sends default 1249
```

## Source Text (Section 10.9.1.2, Manual Entry)

> "If the device is unable to read the card's magnetic strip, it should prompt the user to swipe the card again — up to three times. If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (MMYY)... When no expiration data is present, the device sends a default value of 1249 (MMYY)."
>
> "Note: When a loyalty card is not present, the Street Address and Loyalty Phone Number associated with the consumer's existing loyalty account are manually keyed at the device in lieu of the Loyalty Account Number."

## SME Reasoning

1. Was the loyalty card swiped successfully, or did it require manual entry after failed swipe attempts?
2. If the card is not present at all, are Street Address (144) and Phone Number, Loyalty (145) populated **instead of** Loyalty Account Number (139)?
3. If Expiration Date is not printed on the card, is the default sentinel value `1249` sent?
4. Does the Update Code (143) match a function that requires the card (e.g., `S` Sale update) versus one that explicitly supports the not-present substitution (e.g., `A` Add account, `C` Coupon redemption, `E` Expiration date update, `I` Account Inquiry, per Section 10.9.3's own text for each function)?

## TBA Dependency Chain

```text
Card presence/readability
  -> Loyalty Account Number (139) OR Street Address(144)+Phone(145) substitution
  -> Expiration Date (146): real value from card, or default 1249 if absent
  -> Update Code (143) context determines which loyalty advice function is being requested
```

## Current Validator Boundary

Per SME direction (`SEG108-SME-006`, historical), the card-not-present substitution condition (139 vs. 144+145) is cataloged only, not code-enforced — `Segment108PayloadValidator` validates each of Loyalty Account Number, Street Address, and Phone Number independently by type/length, but does not require that exactly one of the two representations be present. Expiration Date (146), per the 2026-09-22 resolution (`SEG108-SME-007`), IS code-enforced for format: numeric MMYY with month `01`-`12` (`SEG108-R-016`).

## Review Checklist

- Is Loyalty Account Number (139) populated when the card is present and readable?
- Is Street Address (144) + Phone Number, Loyalty (145) populated instead when the card is not present?
- Is Expiration Date (146) either a real MMYY value from the card or the documented default `1249`?
- Does the test fixture avoid populating BOTH Loyalty Account Number and the Street Address/Phone Number substitution simultaneously, to keep the scenario realistic?
