# Network-Specific BR Mention Counts

**Assessment date:** 2026-10-22  
**AI delivery:** `181026 Run\181026 Run`  
**AI BR population:** 8,470  
**Independent Test Solution baseline:** 601 source-derived Test BR rules

## AI-generated BR catalog

The AI catalog has **556 of 8,470 BRs (6.6%)** whose requirement text explicitly names at least one network/brand listed below. Network counts are non-exclusive: a BR mentioning multiple brands is counted once for each brand, so do not sum this column as a distinct BR total.

| Network / brand named in BR text | AI BRs mentioning network |
|---|---:|
| Visa | 294 |
| Mastercard | 159 |
| Discover | 123 |
| American Express / Amex | 57 |
| JCB | 11 |
| Maestro | 30 |
| Interlink | 10 |
| NYCE | 1 |
| PULSE | 0 |
| STAR | 20 |
| Shazam | 0 |
| Accel | 5 |
| Diners Club | 6 |
| UnionPay | 0 |
| **Distinct AI BRs mentioning one or more listed names** | **556** |
| No listed network name found in the searched text fields | 7,914 |

## Independent Test Solution baseline

The independent source-derived baseline contains **15 of 601 Test BRs (2.5%)** whose title or statement explicitly names one or more of these network/brand names.

| Network / brand named in Test BR title/statement | Test BRs mentioning network |
|---|---:|
| Visa | 11 |
| Mastercard | 7 |
| Discover | 0 |
| American Express / Amex | 1 |
| JCB | 0 |
| Maestro | 0 |
| Interlink | 0 |
| NYCE | 0 |
| PULSE | 0 |
| STAR | 1 |
| Shazam | 0 |
| Accel | 0 |
| Diners Club | 0 |
| UnionPay | 0 |
| **Distinct Test BRs mentioning one or more listed names** | **15** |
| No listed network name found in the searched title/statement | 586 |

## Method and limitations

- AI BRs were counted once per named network when a whole-word name match occurred in `statement`, `expected_behavior`, `condition_trigger`, `constraint_text`, `scope_name`, `target_transaction`, or `fixed_value`.
- Independent Test BRs were counted when a whole-word name match occurred in the baseline `title` or `statement`.
- Counts are **explicit text mentions**, not a semantic classification of every network-applicable rule. Generic rules that apply to a network without naming it, aliases not in the search list, and network applicability inferred from codes or context are not counted.
- Counts are not mutually exclusive: the same BR may name multiple networks. For the AI catalog, network mention counts sum to more than 556 because some BRs mention multiple brands.
- This is a catalog inventory, not an adjudication of correctness, network coverage, or executable test-chain coverage. The independent baseline's 601 entries remain source-derived rules, not proof that all named networks have complete approved test coverage.

Machine-readable counts: [network-specific-br-counts-2026-10-22.csv](./network-specific-br-counts-2026-10-22.csv)

