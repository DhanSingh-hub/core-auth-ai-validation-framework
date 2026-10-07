# Appendix Q National POS Condition Code Training

**Specification:** BUYPASS ATL105 2026-3, Appendix Q-1 through Q-7<br>
**Data segment:** Variable Information Element 113, Table ID 030, carried in Segment 111 and
providing POS/terminal context for Segment 100 transactions<br>
**Terms:** National Point-of-Service Condition Code, Terminal Class, Presentation Type, Security
Condition, Terminal Type<br>
**Status:** `PARTIALLY_COVERED`; the four composition/recognition BRs plus two field-level rules
found outside the Q-1 through Q-7 pages (conditional requiredness and the Visa CAVV trigger) have an
independent, bounded implementation and in-repo test fixtures covering every position's assigned,
reserved-national-use, and reserved-private-use digits, the full 21-entry Terminal Type catalog, and
the context-dependent requiredness/CAVV/eWIC-self-checkout cases. General cross-field consistency
with Prompt Code/POS Entry Mode/transaction lifecycle (beyond the eWIC self-checkout case) remains
out of scope, same as the prior appendices in this family.

## Scope and Field Boundaries

Appendix Q defines a single ten-digit composite code (source lines ~35151-35383) with four
fixed-position sub-fields:

- **Terminal Class** (positions 1-3): Attended/Unattended (pos 1), Customer/Card
  acceptor/Administrative operator (pos 2), On/Off premise (pos 3).
- **Presentation Type** (positions 4-7): customer presence (pos 4), card presence (pos 5), card
  retention capability (pos 6), and presentment/re-presentment state (pos 7).
- **Security Condition** (position 8): no concern, suspected fraud, identification verified, or
  e-commerce transaction with digital signature.
- **Terminal Type** (positions 9-10): a flat two-digit code list (21 assigned entries) describing the
  physical or digital terminal type (ATM, ECR, Internet, VRU, etc.).

Positions 1-3, 4-7, and 8 each explicitly distinguish three digit categories in the source text: an
assigned value with a stated meaning, a range "Reserved for national use," and a range "Reserved for
private use." This mirrors Appendix J's bounded composite-code pattern
([`AppendixJPosEntryModeOracle`](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixJPosEntryModeOracle.java)):
code recognition/classification only, never transaction, channel, or lifecycle eligibility. Unlike
positions 1-3/4-7/8, Terminal Type (positions 9-10) has no reserved-range language anywhere in the
source — it is simply a flat list of assigned codes — so any two-digit value outside that list is
classified `FAIL` rather than `REVIEW_REQUIRED`.

Decomposed into five BRs, matching the appendix's own sub-field boundaries:

- **BR-SEG100-APPQ-COMPOSITION** — overall ten-digit numeric format and decomposition.
- **BR-SEG100-APPQ-TERMINAL** — Terminal Class (positions 1-3) and Terminal Type (positions 9-10)
  recognition.
- **BR-SEG100-APPQ-PRESENTATION** — Presentation Type (positions 4-7) recognition.
- **BR-SEG100-APPQ-SECURITY** — Security Condition (position 8) recognition.
- **BR-SEG100-APPQ-FLOW-CONSISTENCY** — cross-field consistency with Segment 100 Prompt Code, POS
  Entry Mode, transaction lifecycle, and eWIC/channel context. Appendix Q does not itself state this
  rule anywhere, so it stays `REVIEW_REQUIRED` by design (same bounding used for Appendix J's
  lifecycle-persistence and context-restriction BRs). One narrow exception — the eWIC attended
  self-checkout case below — is independently assertable and is `PARTIALLY_COVERED`.

Appendix Q-4 and Q-7 additionally contain "ISO code examples" crosswalk tables mapping specific
(Terminal Class, Terminal Type) and Presentation Type combinations. The source explicitly describes
these as "a few examples... for all possible combinations... refer to" the component tables — i.e.
not an exhaustive enumeration — so they are used only as illustrative worked examples for test
fixtures (see `TD-SEG100-APPQ-COMPOSITION-PASS`), never as a pass/fail completeness oracle.

### Two additional field-level rules found outside Q-1 through Q-7

A broader search of the whole specification for "Table ID 030" / "National Point-of-Service
Condition Code" (beyond just the Q-1 through Q-7 pages) surfaced Appendix I-19's own attribute
description of this same field, which states two further rules, plus one rule in the eWIC section.
These describe Table ID 030 itself (not merely reference it from another field's definition), so
they are modeled as two additional BRs and one context-aware case of flow consistency, each taking an
optional `TransactionContext` since the rule depends on facts outside the ten-digit value itself:

- **BR-SEG100-APPQ-REQUIREDNESS** — Appendix I-19: "Required for all EMV transactions, all internet
  based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File
  transactions. Optional for all other transactions." `FAIL` only when a triggering transaction type
  is confirmed and the field is confirmed absent; `REVIEW_REQUIRED` whenever transaction type or
  presence is unknown.
- **BR-SEG100-APPQ-VISA-CAVV** — Appendix I-19: "For Visa, Terminal/Merchant should send Visa CAVV
  Data when Terminal Type values '25' or '26' are used." Terminal Type is read directly from the
  code's positions 9-10; `PASS` immediately if Terminal Type is not 25/26 (not applicable); otherwise
  `REVIEW_REQUIRED`/`FAIL`/`PASS` depending on confirmed Visa/CAVV-presence context.
- **eWIC self-checkout case of BR-SEG100-APPQ-FLOW-CONSISTENCY** — section 10.5.5.6 (eWIC Data
  Elements): "Attended self-checkout lanes are considered unattended by the eWIC processors. This
  should be reflected in the value of POS Condition Code." When context confirms both an eWIC
  transaction and a self-checkout lane, Terminal Class position 1 must read `1` (Unattended); `FAIL`
  if it reads `0` (Attended).

Not modeled, because these are rules stated BY *other* elements ABOUT Appendix Q's Terminal Type
codes, not rules Appendix Q (or its own Table ID 030 field description) states — the same boundary
used to keep Appendix O's `BR-SEG100-APPO-ENTRY-MODE` from re-implementing Appendix J's own code
semantics:

- Element 203 "Safekey Data" (Segment 123, NFC payment tokenization): a table ties SafeKey AEVV/AESK
  sub-field mandatory/optional status to Terminal Type 25/26/27. This belongs to a future Segment
  123/Element 203 training effort.
- Appendix I's Visa-India recurring-payment rules: Terminal Type `25` combined with CAVV Cryptogram
  and recurring-transaction-indicator/amount conditions for registration, subsequent, modification,
  and cancellation scenarios. This is a narrow Visa-India recurring-payment business rule, not
  Appendix Q's (or Table ID 030's) own general text, and is deferred to a future
  Visa-recurring-payment-specific training effort.

## Test Solution Work Completed

`AppendixQNationalPosConditionCodeOracle` independently implements and tests all seven BRs:

- **BR-SEG100-APPQ-COMPOSITION** — the code must be exactly ten numeric digits; any other length or
  non-numeric character is `FAIL`.
- **BR-SEG100-APPQ-TERMINAL** — classifies Terminal Class positions 1-3 as `ASSIGNED` /
  `RESERVED_NATIONAL_USE` / `RESERVED_PRIVATE_USE`, and looks up the two-digit Terminal Type against
  the full 21-entry catalog. A reserved Terminal Class digit is `REVIEW_REQUIRED` (syntactically
  well-formed, no source-stated meaning yet); an unrecognized Terminal Type is `FAIL` (the source
  states no reserved-range exception for this sub-field).
- **BR-SEG100-APPQ-PRESENTATION** — classifies all four Presentation Type positions the same way;
  `REVIEW_REQUIRED` if any position falls in a reserved range, `PASS` only if all four are assigned.
- **BR-SEG100-APPQ-SECURITY** — classifies the single Security Condition position; `REVIEW_REQUIRED`
  if reserved, `PASS` if assigned.
- **BR-SEG100-APPQ-FLOW-CONSISTENCY** — `REVIEW_REQUIRED` by default (no-argument overload); Appendix
  Q does not state how its code relates to Prompt Code, POS Entry Mode, or lifecycle stage in general,
  so that broader claim is never independently assertable from Appendix Q text alone. The
  `(code, TransactionContext)` overload additionally asserts the eWIC attended self-checkout case:
  `PASS`/`FAIL` when context confirms an eWIC self-checkout lane, `REVIEW_REQUIRED` otherwise.
- **BR-SEG100-APPQ-REQUIREDNESS** — takes a `TransactionContext`; `PASS`/`FAIL` when EMV/internet-
  based/Credential-on-File status and field presence are both confirmed, `REVIEW_REQUIRED` otherwise.
- **BR-SEG100-APPQ-VISA-CAVV** — reads Terminal Type from positions 9-10; `PASS` immediately outside
  25/26, otherwise `PASS`/`FAIL`/`REVIEW_REQUIRED` depending on confirmed Visa/CAVV context.

All four composition/recognition BRs fail closed: if the overall ten-digit composition is invalid,
every sub-field BR reports `FAIL` rather than attempting to classify malformed input (same pattern as
Appendix O's LUHN/cryptogram checks when the base representation itself is invalid).

Package totals: **7 BR / 4 TS / 8 TC / 43 TD**, all `COVERED`/`EXECUTABLE` except the general
cross-field case of `BR-SEG100-APPQ-FLOW-CONSISTENCY`, which stays `REVIEW_REQUIRED` by design, using
in-repo fixtures, including one `TD` per Terminal Type catalog entry (see
[`AppendixQNationalPosConditionCodeOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixQNationalPosConditionCodeOracleTest.java)
and
[`appendix-q-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-q-segment-100-coverage.json)).

## Deferred Work

1. Confirm with the implementation team how the National POS Condition Code must agree with Segment
   100 Prompt Code, POS Entry Mode, and transaction lifecycle beyond the eWIC self-checkout case —
   none of this is stated in Appendix Q itself, so the general case of `BR-SEG100-APPQ-FLOW-
   CONSISTENCY` cannot move past `REVIEW_REQUIRED` without an SME-confirmed cross-field rule or a
   canonical anchor elsewhere in the spec.
2. Element 203 "Safekey Data" (Segment 123, NFC payment tokenization) ties SafeKey AEVV/AESK
   mandatory/optional status to Terminal Type 25/26/27; model this when Segment 123/Element 203 is
   trained, cross-referencing `AppendixQNationalPosConditionCodeOracle.terminalTypes()` for code
   recognition rather than duplicating the catalog.
3. Appendix I's Visa-India recurring-payment rules require Terminal Type `25` together with CAVV
   Cryptogram and recurring-transaction-indicator/amount conditions for registration, subsequent,
   modification, and cancellation scenarios; defer to a future Visa-recurring-payment-specific
   training effort rather than folding this narrow business rule into Appendix Q's validator.
4. Obtain real AI-generated Table 030 artifacts to replace the in-repo synthetic fixtures before
   implementation certification.
5. If a modeled Table 030/National POS Condition Code field is ever added to the Segment 100 payload
   schema, decide whether to wire `AppendixQNationalPosConditionCodeOracle` into
   `Segment100PayloadValidator`; today no such field exists, so this validator is intentionally
   standalone (same posture as Appendix J/N/O).

## Source References

- ATL105 2026-3 Appendix Q-1 through Q-7 (National Point-of-Service Condition Codes):
  [extracted specification](../../extracted_text.txt), lines ~35151-35383.
- ATL105 2026-3 Appendix I-19 (Table ID 030 field attribute description, requiredness and Visa CAVV
  note): [extracted specification](../../extracted_text.txt), lines ~28185-28235.
- ATL105 2026-3 section 10.5.5.6 (eWIC Data Elements, self-checkout note):
  [extracted specification](../../extracted_text.txt), lines ~5445-5465.
- [Segment 100 Appendix family training index](../segment-100/README.md) and
  [Appendix Family Gap Closure Register](../segment-100/appendix-family-gap-closure-register.md).
- [Appendix O Payment Token Terminology Training](../appendix-o/README.md) (prior appendix in this
  family, same bounded-independent-validator pattern).
