# Segment 102 Descriptive Business Requirements

**Audience:** AI Solution Team, Test Validation Team, Card/POS/Fuel SMEs, Technical Business Analysts, Client Review Team
**Specification:** BUYPASS Platform ATL105 Message Format Specification 2026-3
**Status:** Working Test-BR baseline for review (extracted independently from the ATL105 specification, not from any AI Solution Team output)
**Scope:** Product Code Data Segment (Segment 102) and its reconciliation boundary with Segment 100

## 1. Scope and Message Model

These requirements apply when a standard ATL105 Financial Transaction Request carries itemized fuel or product data:

```text
TCP/IP Header
  -> Data Section No. 1
  -> Data Section No. 2
       -> exactly one Segment 100 (aggregate amounts)
  -> Data Section No. 3
       -> Segment 102 (itemized product data), when the flow requires it
```

These requirements do not define the complete internal behavior of Segment 157 (Adjusted Product Code Data Segment) or Segment 143 (Tax by Product Data Segment) beyond the mutual-exclusivity and order-consistency rules stated below.

## 2. Business Requirements

### BR-SEG102-001: Segment Type identity

**Requirement**
Segment 102 shall use Segment Type `102`.

**Acceptance criteria**

- Segment Type `102` passes.
- Any other segment type fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 85 / `segment-type`

### BR-SEG102-002: Segment Length representation

**Requirement**
Segment 102 shall carry a Segment Length that represents its encoded content, including Segment Type length and field separators.

**Acceptance criteria**

- A correctly calculated Segment Length passes.
- A Segment Length that does not match the encoded content fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 84 / `segment-length`

### BR-SEG102-003: Service Level value

**Requirement**
Element 87 Service Level shall use an allowed value identifying the sale type.

**Acceptance criteria**

- `F`, `S`, `N`, `X`, `O`, and `0`-`9` pass.
- Any other value fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 87 / `service-level`

### BR-SEG102-004: Number of Products value and consistency

**Requirement**
Element 62 Number of Products shall be a zero-padded value from `01` to `10` and shall equal the number of product entries actually serialized in the segment.

**Acceptance criteria**

- `01`-`10` with a matching entry count passes.
- A value outside `01`-`10` fails.
- A declared count that does not match the actual serialized entry count fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 62 / `number-of-products`, `number-of-products-consistency`

### BR-SEG102-005: Maximum of ten products

**Requirement**
Segment 102 shall contain no more than ten product entries.

**Acceptance criteria**

- Ten or fewer product entries pass.
- Eleven or more product entries fail.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / `max-ten-products`

### BR-SEG102-006: Fuel and EV product ordering

**Requirement**
Fuel products shall be listed first in the segment; for EV charging transactions, the EV product code shall be the first product code. Multi-fuel transactions shall list the primary fuel first.

**Acceptance criteria**

- Fuel-first ordering passes; a nonfuel product listed before a fuel product fails.
- EV-first ordering passes for EV charging transactions.
- A multi-fuel transaction with the primary fuel first passes; primary fuel not listed first fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 77 / `fuel-products-first`, `ev-product-first`, `multi-fuel-primary-first`

**Status:** `[PROVISIONAL]` — enforcement requires a fuel/EV product-code classification that is not yet fully transcribed in this knowledge base (see Section 5).

### BR-SEG102-007: Unique fuel Product Code

**Requirement**
A unique Product Code shall be sent for each type of fuel purchase within the segment.

**Acceptance criteria**

- Distinct fuel types with distinct codes pass.
- Two fuel entries sharing one code for different fuel types fail.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 77 / `unique-fuel-product-code`

### BR-SEG102-008: Product Code validity

**Requirement**
Element 77 Product Code shall be a value recognized by Appendix F, Valid Payment Systems Product Codes.

**Acceptance criteria**

- A code within the verified Appendix F range passes.
- A code known to be invalid fails.
- A code outside the currently-verified Appendix F range is held for review rather than auto-accepted or auto-rejected.

**Source anchor**
ATL105 / 2026-3 / Appendix F / Element 77 / `product-code-enum`

**Status:** `[PROVISIONAL]` — Appendix F is only partially transcribed in this repository (see Section 5).

### BR-SEG102-009: Unit of Measure value

**Requirement**
Element 106 Unit of Measure shall use an allowed value appropriate to the product.

**Acceptance criteria**

- `C`, `G`, `K`, `L`, `M`, `P`, `Q`, `U`, `W`, `Z`, `O`, and `0`-`9` pass.
- Any other value fails.
- EV charging entries use `M` or `W`.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 106 / `unit-of-measure`

### BR-SEG102-010: Quantity assumed-decimal encoding

**Requirement**
Element 81 Quantity shall preserve the assumed-decimal-place leading digit convention.

**Acceptance criteria**

- A value with the correct leading decimal-place digit passes.
- A value omitting the leading decimal-place digit fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 81 / `quantity-assumed-decimal`

### BR-SEG102-011: Unit Price assumed-decimal encoding

**Requirement**
Element 107 Unit Price shall preserve the assumed-decimal-place leading digit convention.

**Acceptance criteria**

- A value with the correct leading decimal-place digit passes.
- A value omitting the leading decimal-place digit fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 107 / `unit-price-assumed-decimal`

### BR-SEG102-012: Product Amount presence

**Requirement**
Element 76 Product Amount shall be present for every product entry.

**Acceptance criteria**

- Every product entry carries a Product Amount.
- An entry missing Product Amount fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 76 / `product-amount-present`

### BR-SEG102-013: Amount reconciliation with Segment 100

**Requirement**
The sum of all Segment 102 Product Amounts shall equal the sum of Segment 100 Element 41 (Fuel Purchase Amount), Element 58 (Nonfuel Amount), Element 99 (Tax Amount), and Element 17 (Cash Amount).

**Acceptance criteria**

- A reconciled sum passes.
- A sum that does not match the Segment 100 aggregate amounts fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 76 / `product-amount-reconciliation`

### BR-SEG102-014: Tax product reconciliation

**Requirement**
When tax product codes are included in Segment 102, their total dollar amount shall also be reported in Segment 100 Element 99 (Tax Amount).

**Acceptance criteria**

- Matching totals pass.
- A mismatch between Segment 102 tax product totals and Segment 100 Tax Amount fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Element 99 / `tax-product-reconciliation`

### BR-SEG102-015: Fuel merchant nonfuel data

**Requirement**
A fuel merchant shall send both fuel and nonfuel product data in Segment 102 when applicable.

**Acceptance criteria**

- Fuel and nonfuel entries both present for a fuel-merchant transaction passes.
- Fuel-only data for a fuel merchant with nonfuel items sold is flagged.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / `fuel-merchant-nonfuel-data`

**Status:** `[PROVISIONAL]` — depends on merchant configuration outside the message format itself (see Section 5).

### BR-SEG102-016: Mutual exclusivity with Segment 157

**Requirement**
Segment 102 and Segment 157 (Adjusted Product Code Data Segment) shall never both be present in the same request.

**Acceptance criteria**

- Segment 102 alone passes.
- Segment 157 alone passes.
- Both present in the same request fails (specification-declared decline condition).

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / Segments 102, 157 / `mutually-exclusive-with-157`

### BR-SEG102-017: Not applicable to Comdata

**Requirement**
Segment 102 shall not be used for Comdata card transactions.

**Acceptance criteria**

- Segment 102 absent for a Comdata card transaction passes.
- Segment 102 present for a Comdata card transaction fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / `not-valid-for-comdata`

### BR-SEG102-018: Segment 102 serialization

**Requirement**
Segment 102 shall use a Field Separator after Segment Type and Segment Length, a Product Data Field Delimiter after Quantity and Unit Price, and either a Field Separator or a Product Data Field Delimiter after Product Amount depending on whether it is the last element in the segment.

**Acceptance criteria**

- Correctly positioned separators/delimiters pass.
- An incorrectly positioned or omitted separator/delimiter fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / `header-field-separators`, `product-data-field-delimiter`, `product-amount-separator-position`

### BR-SEG102-019: Maximum segment length

**Requirement**
Segment 102 shall not exceed 381 alphanumeric characters.

**Acceptance criteria**

- A segment within the 381-character bound passes.
- A segment exceeding the bound fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.3 / `max-segment-length`

### BR-SEG102-020: Companion-segment order consistency (Segment 143)

**Requirement**
When Segment 143 (Tax by Product Data Segment) is present, its product entries shall be in the same order and count as Segment 102.

**Acceptance criteria**

- Matching order and count pass.
- A mismatch in order or count fails.

**Source anchor**
ATL105 / 2026-3 / Section 12.30 / Segment 143 / `tax-by-product-order-consistency`

**Status:** `[PROVISIONAL]` — scope decision pending (see Section 5).

## 3. Required Test Coverage

The artifact package should include:

- Positive cases (valid fuel, nonfuel, EV, and multi-fuel product entries).
- Negative cases (invalid ordering, invalid enum values, exceeded limits).
- Boundary cases (exactly 10 products, exactly 381 characters).
- Conditional cases (fuel-merchant nonfuel data, Comdata exclusion).
- Reconciliation cases (Segment 102 to Segment 100 amount matching, tax product reconciliation).
- Companion-exclusivity cases (Segment 102 vs. Segment 157, Segment 102 with Segment 143).
- Serialization cases (separators, delimiters, assumed-decimal encoding).
- Unknown or unverifiable Product Codes explicitly marked for manual review.

## 4. Traceability Contract

```text
BR-SEG102-xxx
  -> SCN-SEG102-xxx
      -> TC-SEG102-xxx
          -> TD-SEG102-xxx
```

Every artifact must carry the same canonical source anchor even when producer-local IDs differ.

## 5. Open Items Requiring Manual/SME Input

The following requirements are marked `[PROVISIONAL]` above and cannot be fully certified until resolved:

1. **BR-SEG102-006, BR-SEG102-008** — the complete Appendix F Product Code table and a fuel/nonfuel/EV classification per code are not fully transcribed in this repository (only codes 001-054 of a reported 000-999 range are captured).
2. **BR-SEG102-015** — whether "fuel merchant sends fuel and nonfuel data" is enforced as a hard rule or a review-only flag, since the validator has no independent signal of merchant type.
3. **BR-SEG102-020** — whether Segment 143 (Tax by Product) consistency is in scope for this Segment 102 pass or deferred to its own Segment 143 training pass.

## 6. Approval Status

This document is a descriptive working Test-BR baseline, extracted independently from the ATL105 specification and the [segment-102-rule-catalog.json](../specs/kb/segment-102/coverage/segment-102-rule-catalog.json). It has not been compared against any AI Solution Team output, because no AI-generated Segment 102 business-requirements artifact currently exists in `test-input/ai-solution/business-requirements/` (see the companion note in Section 7). The Test Team and Client/Business Team must review and approve the wording, source interpretation, exclusions, and acceptance criteria before it becomes an approved requirement baseline.

## 7. AI-Generated Requirement Comparison — Status

`test-input/ai-solution/business-requirements/` currently contains only a `.gitkeep` placeholder, and `test-input/ai-solution/manifest.json` is still the unfilled template (`REPLACE-WITH-...` placeholders). No AI Solution Team artifact for Segment 102 — or any segment — has been ingested into this repository yet.

As a result, the BR-to-BR matching this document is meant to support (AI-generated requirement vs. this Test-Team-derived requirement, by shared source anchor) cannot be run yet. Once an AI-generated Segment 102 business-requirements JSON is placed in `test-input/ai-solution/business-requirements/` (with `manifest.json` filled in), the matching pass is:

```text
For each BR-SEG102-xxx above:
  find AI requirement(s) sharing the same source anchor (spec|version|section|segment|element|rule)
  compare: does the AI requirement's stated behavior agree with the acceptance criteria above?
  classify: MATCHED | PARTIAL_MATCH | CONFLICTING | AI_MISSING | TEST_MISSING
```
