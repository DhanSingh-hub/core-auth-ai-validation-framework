# Segment 102: SME and Technical Business Analysis Learning Note

## Purpose

Segment 102 is the Product Code Data Segment in an ATL105 Financial Transaction Request. It is the itemized companion to Segment 100's aggregate amount fields. A card/POS SME explains what was actually sold at the pump or register; a technical business analyst translates that into explicit rules, traceability, and testable outcomes — the same methodology used for [Segment 100](../segment-100/segment-100-sme-tba-learning-note.md) and [Segment 103](../segment-103-ebt-sme-note.md).

This note is a learning guide, not an approved client requirement set.

## 1. The Correct Mental Model

Do not start with JSON field names. Start with the sale:

```text
POS/pump event
  -> products/fuel sold
  -> Segment 100 aggregate amounts (fuel, nonfuel, tax, cash)
  -> Segment 102 itemized product lines
  -> reconciliation between the two
  -> wire serialization
```

| Layer | Business question | Technical representation |
| --- | --- | --- |
| Segment 100 | What are the transaction's total amounts? | Elements 41, 58, 99, 17 |
| Segment 102 | What specific products/fuel make up those totals? | Up to 10 repeated product entries |
| Segment 157 (mutually exclusive) | Is this an adjustment instead of an original sale? | Alternative segment, never combined with 102 |
| Segment 143 (companion, separate scope) | What tax breakdown applies per product? | Per-product tax entries matching Segment 102 order |

Segment 102 is not a second, independent total — it must reconcile with Segment 100.

## 2. SME Reasoning: Pump/POS and Product Intent

A card/POS/fuel SME should answer these questions before defining test data:

1. Is this a fuel sale, a nonfuel/merchandise sale, or both?
2. Is more than one fuel type being dispensed (OTR multi-fuel: e.g., Diesel + DEF + Reefer)?
3. Is this an EV charging transaction?
4. Are discounts or coupons involved (negative Product Codes/amounts)?
5. Is the merchant a fuel merchant that must always report both fuel and nonfuel product data?
6. Is the card type Comdata (Segment 102 does not apply)?
7. Is Segment 157 being used instead of Segment 102 for this flow?
8. Is a per-product tax breakdown (Segment 143) also required?

A technical business analyst converts each answer into:

```text
condition -> required product ordering/field -> valid representation -> expected result
```

Example:

```text
EV charging transaction
  -> EV product code must be first
  -> Unit of Measure is M or W
  -> missing EV-first ordering = invalid request
```

## 3. Segment 102 Field Responsibilities

| Field | Element | SME interpretation | Common analysis risk |
| --- | ---: | --- | --- |
| Segment Type | 85 | Identifies Segment 102 | Confusing it with a product code |
| Segment Length | 84 | Length of encoded Segment 102 | Counting JSON characters instead of wire content |
| Service Level | 87 | Full/self/mini/maxi serve or fuel-not-present | Treating it as a generic status flag |
| Number of Products | 62 | Count of product entries, `01`-`10` | Not matching the actual serialized entry count |
| Product Code | 77 | Type of product or discount/coupon | Assuming any 3-digit value is valid without checking the code table |
| Unit of Measure | 106 | Measurement type, including EV units | Using retail units (gallon/pound) for an EV charging entry |
| Quantity | 81 | Units sold, assumed-decimal encoded | Storing a plain decimal and losing the leading decimal-place digit |
| Unit Price | 107 | Price per unit, assumed-decimal encoded | Same assumed-decimal loss as Quantity |
| Product Amount | 76 | Monetary value of the product/discount | Not reconciling the sum against Segment 100 |

The exact field names in an AI-generated JSON artifact may differ. Canonical source anchors must identify the meaning.

## 4. Product Ordering and EV Rules

- Fuel products are always the first products in the segment.
- For EV charging transactions, the EV fuel product code is the first product code.
- A unique Product Code is required for each type of fuel purchase.
- Multi-fuel OTR transactions may report more than one Fuel Type Code; the primary fuel (the vehicle's primary need) must be listed first.

Do not infer correct ordering just because amounts sum correctly — ordering is an independent rule.

## 5. Segment 102 and Companion/Alternative Segments

| Condition | Expected relationship to Segment 102 |
| --- | --- |
| Standard fuel/product sale | Segment 102 present with itemized product entries |
| Adjustment instead of original sale | Segment 157 present, Segment 102 absent |
| Both 102 and 157 present | Invalid; transaction is declined |
| Comdata card | Segment 102 not used |
| Per-product tax breakdown required | Segment 143 present, product order/count matches Segment 102 |
| Unmapped combination | Expected set is unknown; review required |

## 6. Cross-Segment Amount Reconciliation

This is the central Segment 102 dependency:

```text
sum(Segment 102 Product Amount, all products)
  == Segment 100 Element 41 (Fuel Purchase Amount)
   + Segment 100 Element 58 (Nonfuel Amount)
   + Segment 100 Element 99 (Tax Amount)
   + Segment 100 Element 17 (Cash Amount)
```

When tax-coded products are included, their total must also be reflected in Segment 100 Element 99. A test artifact that checks "amounts are numeric" without checking this reconciliation has not actually validated Segment 102 against Segment 100.

## 7. Serialization Thinking

Validate these separately, the same way Segment 100 serialization is validated:

1. JSON structure and data types.
2. Semantic field values (Service Level, Unit of Measure, Product Code).
3. Product entry count versus Number of Products.
4. Product ordering (fuel/EV first).
5. Assumed-decimal encoding for Quantity and Unit Price.
6. Field Separator after Segment Type and Segment Length.
7. Product Data Field Delimiter after Quantity and Unit Price.
8. Field Separator or delimiter after Product Amount depending on position.
9. Segment Length and total 381-character bound.
10. Element 63 segment count.

## 8. Turning Knowledge Into Requirements

Good BR:

```text
For a Financial Transaction Request carrying itemized fuel or product data,
Segment 102 shall report each product with a valid Product Code and Unit of
Measure, and the sum of Product Amount across all products shall equal the
sum of Segment 100 Elements 41, 58, 99, and 17.
```

Weak BR:

```text
The system should support product-level data.
```

## 9. Review Checklist for an AI Deliverable

### Business meaning

- Does the requirement describe a real pump/POS product sale?
- Is fuel, EV, or multi-fuel context explicit where applicable?
- Are discount/coupon entries distinguished from ordinary product sales?

### Message construction

- Is Segment 102 present only when the flow requires itemized product data?
- Is Number of Products consistent with the actual entries?
- Is fuel/EV ordering correct?
- Is each fuel-type Product Code unique?

### Companion and exclusivity rules

- Are Segment 102 and Segment 157 never both present?
- Is Segment 102 absent for Comdata cards?
- Is Segment 143's product order checked against Segment 102 when both are present?

### Reconciliation and serialization

- Does the sum of Product Amounts match Segment 100's aggregate amounts?
- Do Quantity and Unit Price preserve assumed-decimal encoding?
- Are separators and delimiters positioned correctly?

### Traceability

- Does every artifact have a source anchor?
- Does BR -> Scenario -> Test Case -> Test Data resolve?
- Are Product Codes outside the currently-verified Appendix F range flagged for review rather than guessed?

## Source References

- Section 12.3, Product Code Data Segment: extracted specification lines 11562-11689.
- Section 12.30, Tax by Product Data Segment (No. 143): lines 14935-15190.
- Section 12.41, Adjusted Product Code Data Segment (No. 157): lines 16904-16963.
- 13.2 Elements 62, 76, 77, 81, 87, 106, 107: `docs/atl105_complete_templates.json`.
- Appendix F, Valid Payment Systems Product Codes (partial transcription): [appendix-code-tables.md](../appendix-code-tables.md).
- [Segment compatibility matrix](../segment-compatibility-matrix.md).
- [Segment 100 SME/TBA learning note](../segment-100/segment-100-sme-tba-learning-note.md).
