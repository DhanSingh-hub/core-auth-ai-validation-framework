# N12-01 deep dive - AI generation team hand-off

**Prepared:** 12 November 2026 (supplement to [FULL-NETWORK-SWEEP-VALIDATION-REPORT-12-NOV-2026.md](./FULL-NETWORK-SWEEP-VALIDATION-REPORT-12-NOV-2026.md))
**Audience:** AI generation team (test-case composer)
**Purpose:** complete, reproducible evidence for the two confirmed test-case generation defects referenced as N12-01, plus the self-validation reporting gap referenced as N12-06.

## 0. How this list was produced (and why it differs from a first pass)

Do not rely only on `test_cases/test_case_validation.json` to decide whether a package is clean - it misses 2 of the 20 truly-affected packages (N12-06). The authoritative source is each test case's own embedded `verification.status` field in `test_cases/test_cases.jsonl`. Every one of the 22,153 test cases in this delivery was read and its `verification.status` tallied; **309 (1.4%) are `REJECTED_FINAL`**, spread across **20 of 99 packages**. This reconciles exactly with `manifest.json summary.verification.REJECTED_FINAL` and `summary.by_status.blocked_verification` in every package - those manifest-level counters are reliable, they just weren't checked in the first pass of this review.

The complete list of all 309 affected test cases - package, test case ID, scenario ID, template, leg categories, which rule was violated, and the verbatim rejection reason - is in **[n12_01_COMPLETE_affected_test_cases.csv](./n12_01_COMPLETE_affected_test_cases.csv)**.

```
Total REJECTED_FINAL test cases: 309
  rule_check LEG-TXN-TYPE:          293 cases  (18 packages)
  rule_check SPLIT-TENDER-CARD-B:    16 cases  ( 2 packages)
```

## 1. Defect A - `LEG-TXN-TYPE`: follow-on legs carry the wrong Transaction Type digit

**Affected:** all 17 PINLESS_DEBIT networks + Signature Debit (STAR_SIGNATURE_DEBIT) = 18 packages, 293 test cases (16 per PINLESS_DEBIT package, 21 in Signature Debit).
**Field:** Segment 100, element 78 ("Prompt Code"), **character position 1 only** (positions 2-4, the card-brand digits, are correct).
**Leg types hit:** TIMEOUT_REVERSAL (144 cases), RETURN (136 cases), AUTH_ONLY (13 cases). Base PURCHASE legs are not affected.

### Root cause, in the generator's own words

Every affected test case documents the failure itself, inside `completeness_findings` / the element's own `flags`:

```
"COMPOSITE_FROM_SPEC_PARTS: '0' did not fit the financial transaction layout;
 Transaction Type='0' (kept from composed value), Card Type='001' (client example for Card Type)"

"COMPOSITE_CONTEXT_UNSCOPED: context 'financial transaction' matched no Appendix
 table caption for part(s) Transaction Type -- codes drawn from ALL tables in that
 appendix, not scoped to this context"
```

Element 78 ("Prompt Code") is a composite value: position 1 = Transaction Type of the *leg*, positions 2-4 = card brand/type (Appendix E, p.521-522). The compositor tries to look up position 1 from an Appendix table scoped to the leg's context (`TIMEOUT_REVERSAL`/`RETURN`/`AUTH_ONLY`); when it can't find a table caption matching that context, it searches every table in the appendix instead, fails to produce a confident composed digit, and **silently keeps the default Transaction Type `'0'`** (the code for a base Purchase/Capture) rather than resolving - or failing on - the correct leg-specific digit.

Each such test case's `verification` block records the final outcome:
```
"verification": {
  "status": "REJECTED_FINAL",
  "reasons": ["RULE_CHECK_MISMATCH: LEG-TXN-TYPE expected hold but is violated (actual '0')"],
  "level": "REJECTED_FINAL"
}
```
and the governing `rule_checks` entry (present in every test case, including the broken ones) is:
```
{"id": "LEG-TXN-TYPE", "op": "value_slice_in", "expect": "hold",
 "basis": "Appendix G Transaction Type of the leg (element 78 p.400-401)",
 "step": 1, "segment": "100", "element": "Prompt Code",
 "start": 1, "end": 1, "allowed": ["<leg-specific digit>"]}
```

### Broken vs. correct - exact value comparison

| | Package (not reviewed) | Package (reviewed) |
|---|---|---|
| Example | PULSE / PINLESS_DEBIT, `TC-005007`, leg category `TIMEOUT_REVERSAL` | INTERLINK / DEBIT (reviewed), `TC-002899`, leg category `TIMEOUT_REVERSAL` |
| `tx_legs[0].code` (leg's own shorthand code - correct in both) | `"Z"` | `"Z"` |
| Element 78 "Prompt Code" **value** | **`"0020"`** | **`"Z011"`** |
| Element 78 generation `method` | `"other_valid_value"` | `"scenario_parameter_literal"` |
| Result | position 1 = `'0'` (wrong - this is the Purchase code) | position 1 = `'Z'` (correct - matches `tx_legs[0].code`) |
| `verification.status` | `REJECTED_FINAL` | `PASS` |

The fix is the same shape in every one of the 293 cases: **position 1 of the composed "Prompt Code" value must be set from the leg's own code** (the same single character already present at `tx_legs[].code` for that leg - e.g. `Z` for TIMEOUT_REVERSAL, `7` for RETURN, `3` for AUTH_ONLY, per the message-chain convention already used correctly elsewhere in the same test case), not left at the base-transaction default `'0'`. Positions 2-4 (card brand, e.g. `"020"`/`"011"`) are already being computed correctly and do not need to change.

Full raw records saved for reference: [samples/sample_TC-005007.json](./samples/sample_TC-005007.json) (PULSE, TIMEOUT_REVERSAL), [samples/sample_TC-005168.json](./samples/sample_TC-005168.json) (PULSE, RETURN), [samples/sample_TC-021670.json](./samples/sample_TC-021670.json) (Signature Debit, AUTH_ONLY) - all three show the identical pattern. Comparison (correct) case: [samples/sample_TC-002899_GOOD.json](./samples/sample_TC-002899_GOOD.json) (Interlink Debit, reviewed, TIMEOUT_REVERSAL).

## 2. Defect B - `SPLIT-TENDER-CARD-B`: second card of a split-tender pair reuses a placeholder value instead of a real Account Number

**Affected:** Discover Network Credit (10 cases) and Moneris Canada Credit (6 cases) = 2 packages, 16 test cases.
**Field:** Segment 100, element 2 ("Account Number"), on the second ("split tender") leg of a two-card partial-approval scenario.

### Root cause

The governing rule check is:
```
{"id": "SPLIT-TENDER-CARD-B", "op": "value_differs_from_step", "expect": "hold",
 "step": 2, "other_step": 1, "segment": "100", "element": "Account Number",
 "basis": "split tender: a different form of payment pays the remaining balance (p.84, p.87)"}
```
i.e. the second card's Account Number must differ from the first card's. In every affected test case, **both** steps' Account Number element resolves to the literal placeholder string:
```
"Account Number": { "value": "TOTALS REQUEST", "method": "qe_example_value", ... }
```
on step 1 **and** step 2 - so the two values are identical (both placeholders), and the rule legitimately fails:
```
"verification": {
  "status": "REJECTED_FINAL",
  "reasons": ["RULE_CHECK_MISMATCH: SPLIT-TENDER-CARD-B expected hold but is violated (actual {'this': 'TOTALS REQUEST', 'other': 'TOTALS REQUEST'})"]
}
```
`"TOTALS REQUEST"` is not a PAN at all - it reads like a mislabeled example pulled from an unrelated catalog entry (e.g. a message-type/description example) rather than the Account Number example-value table. This affects only Discover Network and Moneris Canada's split-tender/second-card example-value lookup; the same `qe_example_value` method elsewhere in the delivery correctly returns realistic PANs.

### Broken vs. correct - exact value comparison

| | Package (not reviewed) | Package (reviewed) |
|---|---|---|
| Example | Discover Network Credit, `TC-003871`, split-tender partial approval | Mastercard Credit (reviewed), `TC-001726`, split-tender partial approval |
| Step 1 "Account Number" value | `"TOTALS REQUEST"` (method: `qe_example_value`) | `"5144283846699542"` (method: `realistic_pan`) |
| Step 2 "Account Number" value | `"TOTALS REQUEST"` (method: `qe_example_value`) | `"5172304375757090"` (method: `realistic_pan`) |
| Result | identical placeholder on both legs - rule violated | two distinct realistic PANs - rule holds |
| `verification.status` | `REJECTED_FINAL` | `PASS` |

The fix: correct the Account Number example-value catalog lookup for Discover Network and Moneris Canada's split-tender legs so it returns a `realistic_pan`-style value (as every other network already does), not the `"TOTALS REQUEST"` placeholder, and ensure the two legs of a split-tender pair draw two distinct values.

## 3. N12-06 - close the self-validation blind spot at the same time

`test_cases/test_case_validation.json` for Discover Network and Moneris Canada currently reports `{"valid": true, "findings": []}` even though 10 and 6 of their own test cases (respectively) carry `verification.status: REJECTED_FINAL`. The same file correctly surfaces every `LEG-TXN-TYPE` finding for the other 18 packages, so the generator for this file clearly has access to the right data - it simply isn't reading every test case's `verification.reasons`/`rule_checks` for all rule-check IDs, only some. Regenerate `test_case_validation.json` from the complete set of each test case's own `verification.status`/`reasons` (every `RULE_CHECK_MISMATCH`, not a fixed subset) so a future second defect of this kind cannot ship invisibly again.

## 4. Verifying the fix

For every package, after regeneration:
1. `manifest.json summary.verification.REJECTED_FINAL` should be absent/zero (currently present and non-zero in 20 packages).
2. `manifest.json summary.by_status.blocked_verification` should be absent/zero (same 20 packages).
3. `test_cases/test_case_validation.json` should report `valid:true` with the SAME zero-finding result independently reconfirmed against every test case's own `verification.status` (not just the `LEG-TXN-TYPE` check), closing N12-06.
4. Spot-check: PULSE `TC-005007`'s element 78 "Prompt Code" value should start with `Z` (not `0`); Discover Network `TC-003871`'s two Account Number values should be two distinct realistic PANs (not `"TOTALS REQUEST"` on both).

## Appendix - files in this hand-off

- [n12_01_COMPLETE_affected_test_cases.csv](./n12_01_COMPLETE_affected_test_cases.csv) - all 309 affected test cases (package, TC ID, scenario, rule violated, verbatim reason).
- `samples/` - full raw test-case JSON, broken and correct side by side:
  - Broken: [TC-005007](./samples/sample_TC-005007.json) (PULSE, TIMEOUT_REVERSAL), [TC-005168](./samples/sample_TC-005168.json) (PULSE, RETURN), [TC-021670](./samples/sample_TC-021670.json) (Signature Debit, AUTH_ONLY), [TC-003871](./samples/sample_TC-003871.json) (Discover Network, split tender).
  - Correct, for comparison: [TC-002899](./samples/sample_TC-002899_GOOD.json) (Interlink Debit, reviewed, TIMEOUT_REVERSAL), [TC-001726](./samples/sample_TC-001726_GOOD.json) (Mastercard Credit, reviewed, split tender).
