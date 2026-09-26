# Partial Approval Indicator: SME/TBA Learning Note

## Meaning

Element 121 tells the host how the card-present flow handles partial approval.

| Value | Meaning |
| --- | --- |
| `0` | Partial approval not supported/default |
| `1` | Partial approval supported |
| `5` | Balance receipt only, Amex prepaid context |

The indicator is not the approval result. It describes transaction capability or handling rules.

## SME Questions

- Is the transaction card-present?
- Does the merchant/POS support split tender after a partial approval?
- Is the card/network context Amex prepaid for value `5`?
- Does the approved amount differ from the requested amount?
- Does the POS prompt for the remaining balance?
- Is this an initial purchase, completion, or reversal?

## TBA Dependency Chain

```text
Card-present context
  -> Partial Approval Indicator required
  -> capability value 0/1/5
  -> card/network-specific interpretation
  -> approved amount and remaining-balance behavior
  -> receipt and split-tender behavior
```

Do not write a requirement that treats `1` as “approved” or `0` as “declined.” Those are different concepts.

## Current Validator Boundary

The validator checks allowed values and the declared Amex prepaid dependency for value `5`. Full approved-amount, split-tender, receipt, and network behavior should be expanded in a dedicated payment-flow module.

## Review Checklist

- Is the indicator present for a card-present flow?
- Is its value one of `0`, `1`, or `5`?
- Is value `5` restricted to the stated context?
- Is the approved amount tested separately from the capability indicator?
- Does the POS behavior after partial approval appear in the scenario and test data?
