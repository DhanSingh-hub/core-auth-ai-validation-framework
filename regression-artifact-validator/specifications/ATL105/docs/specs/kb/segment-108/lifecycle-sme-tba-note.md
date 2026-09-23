# Segment 108 Lifecycle and Correlation: SME and TBA Learning Note

Combines and scopes [Segment 100's Final Closure note](../segment-100/final-closure-sme-tba-note.md) and [Sequence Number and Lifecycle Correlation note](../segment-100/sequence-lifecycle-sme-tba-note.md) to the Loyalty Card Transaction Request context.

## The Segment 108 Lifecycle

```text
Loyalty purchase / points redemption / coupon redemption (original)
  -> Reversal of coupon redeem, or Reversal of points redeemed
  -> correlated via Unit of Work (Element 151), not Segment 100's Sequence Number alone
```

Section 10.9.3 documents "Reversal of coupon redeem" and "Reversal of points redeemed" as distinct loyalty advice functions requiring the **original approval number** to reverse a prior redemption. Element 151 (Unit of Work)'s own definition states it is "required on loyalty reversals to match the original purchase" (`SEG108-R-020`) — this is Segment 108's dedicated reversal-correlation identifier, analogous to Segment 113's ECA/TeleCheck Trace ID and distinct from Segment 100's generic Sequence-Number-based reversal correlation.

| Lifecycle flow | Original transaction | Follow-up | Correlation identifier |
| --- | --- | --- | --- |
| Coupon redemption -> reversal | Coupon redemption request (Update Code `C`) | Reversal request | Unit of Work (Element 151), per Element 151's own processing rule |
| Points redemption -> reversal | Points redemption request (Update Code `P`) | Reversal request | Unit of Work (Element 151) |

### The Open Gap (`SEG108-SME-002` / `P-02`)

Section 10.9.3 names the two reversal functions explicitly, but Element 143 (Update Code)'s enumeration only documents 8 values (A, C, E, I, P, S, T, U) — **none of which is assigned to either reversal function**. This remains genuinely OPEN (not silently resolved): do not fabricate a 9th/10th Update Code value for the reversal functions. Any reversal-scenario test data should flag this gap explicitly rather than guessing a code.

## SME Review Questions

1. Is this transaction an original coupon/points redemption, or a reversal of one?
2. If a reversal, is Unit of Work (151) populated with the value that matches the original redemption's transaction?
3. Since no Update Code value is documented for either reversal function, how does the test data represent a reversal request today (`REVIEW_REQUIRED` per `SEG108-SME-002`)?

## TBA Rule

```text
For every Segment 108 reversal follow-up, the test data shall carry Unit of Work
(Element 151) matching the original coupon/points redemption's value. This condition
is cataloged (SEG108-R-020) but the Update Code value that identifies a reversal
request remains unresolved (SEG108-SME-002, OPEN) - do not invent one.
```

## Current Validator Boundary

`Segment108PayloadValidator` validates Unit of Work's type/length bound only (fixed 19 digits, `SEG108-R-020`). It does not detect that a transaction is a reversal, nor cross-check Unit of Work against a prior transaction's value — both remain manual/TBA review items, and the Update Code gap remains genuinely open pending SME input.

## Review Checklist

- Is the transaction type (original redemption vs. reversal) identifiable from context outside Segment 108 itself?
- Is Unit of Work populated whenever the transaction is a reversal?
- Does the test data avoid fabricating an Update Code value for the undocumented reversal functions?
