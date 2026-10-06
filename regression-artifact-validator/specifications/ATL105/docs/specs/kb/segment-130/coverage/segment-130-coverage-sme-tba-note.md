# Segment 130 Coverage Closure — SME and TBA Note

## Why This Package Exists

Segment 130 (EMV Request Data Segment) is the mandatory chip-data carrier for EMV Financial Transactions — structurally analogous to Segment 100 (mandatory for all financial transactions) and Segment 108 (mandatory for Loyalty Card Transactions). Unlike the segments trained earlier in this pass (108, 114, 115), Segment 130 already has a partial Test Team baseline predating this KB effort.

## Coverage Classification

| Status | Meaning |
| --- | --- |
| `COVERED` | Rule has a BR, scenario, test case, and test data, all sharing the canonical source anchor. |
| `PARTIALLY_COVERED` | Rule has at least one link in the BR→TS→TC→TD chain, but the chain is incomplete. |
| `REVIEW_REQUIRED` | Rule is blocked by a `PROVISIONAL` item pending SME/TBA input. |
| `EXTERNAL_FIXTURE_REQUIRED` | Rule can only be validated with a real production artifact (CA key file, certified EMV kernel/HSM) that cannot be synthesized. |
| `MISSING` | Rule has no BR, scenario, test case, or test data at all. |

## Segment 130 Specific Notes

- Two categories of rules are permanently or currently `EXTERNAL_FIXTURE_REQUIRED`, not a gap to "fix" with more AI generation: CA Public Key File authenticity (needs a real `CA_KEYS` file) and Application Cryptogram (Tag 9F26) authenticity (needs a certified EMV kernel/HSM). Do not attempt to synthesize these.
- Cross-field consistency between EMV chip values and Segment 100 (amount, currency, transaction type, terminal country) is `REVIEW_REQUIRED`, inherited from the pre-existing Appendix R package — confirm the complete rule set with SME before certifying.
- Element 118 is shared between Segment 112 ("Additional Information") and Segment 130 ("EMV Additional Information") — do not conflate these when building traceability matrices; they have independent source anchors.
- **Appendix T is now transcribed** (former P-06, resolved from source). It defines exactly two indicators: Table `001` (EMV Table Data, `EMVYES`/`EMVNOT`, length 6, with a device echo obligation) and Table `002` (CARC, length 1, returned by Visa in Bit 44.8). Table-ID scenarios are no longer blocked, except for the request-side legality of Table `002` (`P-07`).
- **Segment 130's maximum length is settled at 3,043** (former P-01). The Section 11.8.1 table's competing 9,999 is a genuine specification defect, not an extraction artefact, and should be logged as such.
- **Segment 131 must not reuse the Segment 130 parser** (`SEG130-R-023`): the response has no Field Separators, a 3,834 maximum, and a 2,800-byte additional-information cap.
- **Absence of Segment 130 is not always an error.** Reversals/TORs waive EMV data (`SEG130-R-017`) and fallback/MSR entry modes exclude it (`SEG130-R-019`). These must produce distinct diagnostics from an illegal omission.
