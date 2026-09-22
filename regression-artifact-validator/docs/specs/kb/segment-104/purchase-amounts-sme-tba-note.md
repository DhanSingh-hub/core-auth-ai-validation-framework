# Purchase Amounts: SME and TBA Learning Note

Segment 104 has three independent conditional amount fields:

| Element | Field | Specification representation |
|---|---|---|
| 74 | PC Tax Amount | Numeric, up to 7 digits, 2 assumed decimal places |
| 73 | PC Freight Amount | Numeric, up to 7 digits, 2 assumed decimal places |
| 72 | PC Duty Amount | Numeric, up to 7 digits, 2 assumed decimal places |

## Important Distinction

The decimal point is **assumed**, not serialized. For example, `0000225` represents a wire value with seven numeric digits; the meaning of the implied two decimals belongs to the receiving financial process. The Segment 104 validator must reject decimal points, signs, commas, alphabetic characters, and values longer than seven digits.

## No Invented Dependency

Section 12.5 does not state that one amount requires another. The Test Team must not require tax, freight, and duty as an all-or-nothing group.

## TBA Test Design

Create independent positive, nonnumeric, overlength, and empty cases for each field. Add arithmetic reconciliation to a transaction total only if an approved source explicitly supplies that rule.
