# Segment 105: SME and Technical Business Analysis Learning Note

## Purpose

Segment 105 is the Totals Data Segment used in an ATL105 Totals Request. It is a settlement and reconciliation operation, not a Financial Transaction Request. The source layout establishes message fields; merchant settlement policy establishes when and why the operation may be issued.

## Correct Mental Model

```text
Merchant settlement need
  -> allowed Totals Date operation
  -> operator authorization, when required
  -> build Segment 105
  -> serialize and send Totals Request
  -> validate Totals Response and reconcile results
```

Do not apply Segment 100 card, amount, EMV, or financial-reversal lifecycle rules to Segment 105 unless the Totals specification explicitly says to do so.

## Source-Defined Segment Model

Segment 105 has these ordered fields: Segment Type, Segment Length, Information Byte, Terminal Identifier, Prompt Code, Employee Number (conditional), Password (conditional), Totals Date, Hardware Version, Software Version, Firmware Version, Sequence Number, Currency Code (optional), Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount.

- Segment Type is `105`.
- Prompt Code is `990`.
- Totals Date controls the requested settlement-date operation.
- The layout identifies requiredness but does not establish local operator, retry, or reconciliation policy.

## SME/TBA Responsibilities

The SME confirms enabled Totals Date operations, settlement cutoff/timezone, operator authorization, response behavior, and Segment 119 applicability. The TBA translates each confirmed decision into an atomic BR, scenario, test case, and synthetic converter-ready fixture.

Unresolved policy must remain `REVIEW_REQUIRED`.