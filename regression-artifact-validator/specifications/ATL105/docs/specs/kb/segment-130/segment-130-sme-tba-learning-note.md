# Segment 130 EMV Request Data Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, Sections 11.8.1, 12.20, Appendix R, Appendix S, and Elements 84, 85, 187-192, 118.

## 1. What Segment 130 Means

Segment 130 is the **EMV Request Data Segment** — the request-side vehicle for chip-card (EMV) transaction data. It plays the same "mandatory anchor" role for EMV Financial Transactions that Segment 100 plays for all financial transactions and Segment 108 plays for Loyalty Card Transactions: the specification explicitly states **"This segment is the only segment required for all EMV financial transactions."**

```text
EMV Financial Transaction Request
  Data Section 2: Segment 100 (standard transaction data)
  Data Section 3: Segment 130 (REQUIRED — EMV chip data)
                  + optionally 101 (Fleet) / 102 (Product Code) / 104 (Purchase Card) / 111 (Variable Information)
```

Note that Segment 103 (EBT) is **not** among Segment 130's companions — the EMV Financial Transaction Request's Data Section 3 list swaps EBT out in favor of Segment 130, unlike the generic Financial Transaction Request's list of {101,102,103,104,111}.

## 2. Segment 130 Layout

| Field | Element | Length | Status | SME meaning |
| --- | ---: | ---: | --- | --- |
| Segment Type | 85 | 3 | Required | Fixed value `130` |
| Segment Length | 84 | 4 | Required | 4-digit encoded length (like Segments 103/114/115/118/120/131) |
| CA Public Key File Checksum | 187 | 25 | Optional | Device's currently-known checksum; echoed back by Segment 131 (response) — a handshake used to detect stale CA key files |
| EMV Card Sequence Number | 188 | 3 | Conditional | The chip card's sequence number |
| EMV Chip Data Length | 189 | 3 | Required | 3-digit length (000-999) of the following EMV Chip Data field |
| EMV Chip Data | 190 | 999 | Required | TLV-encoded (BCD-packed/hex) chip data: AID, cryptogram, terminal verification results, etc. |
| *(repeating)* EMV Additional Information Indicator | 191 | 3 | Required (when section present) | Identifies the type/table of additional information |
| *(repeating)* EMV Additional Information Length | 192 | 3 | Required (when section present) | Length of the following information field |
| *(repeating)* EMV Additional Information | 118 | Var. | Required (when section present) | The additional information payload — repeats up to 2,000 bytes total |

Maximum Segment 130 length is **3,043 alphanumeric characters** per Section 12.20 (`SEG130-R-004`). Independent field-length arithmetic (fixed fields + repeating section + separators ≈ 3,044) closely corroborates this figure; a possible second figure in the generic Financial Transaction Request layout table could not be confidently transcribed from OCR and is flagged for visual confirmation (`[PROVISIONAL SEG130-SME-001]`), not asserted as a genuine conflict.

### A cross-segment quirk: Element 118 is reused

Segment 112's "Additional Information" field and Segment 130's "EMV Additional Information" field **both use Element 118** — but they are structurally and semantically different (Segment 112: single occurrence, max 984; Segment 130: repeating, part of a 3-field group). Do not assume shared element numbers imply shared business meaning across segments (`SEG130-R-012`).

## 3. The EMV Additional Information Section (Repeating Group)

This is structurally the same pattern as Segment 111's Variable Information Section: no Field Separators occur within or between repetitions, and exactly one Field Separator follows the **final** repetition. The section as a whole is capped at 2,000 bytes and is repeated **"by EMV Additional Information Indicator"** — i.e., each repetition's Indicator identifies what table/type of information follows (e.g., the AI/Test crosswalk references "Table ID 001" with EMVYES/EMVNOT values). `[PROVISIONAL SEG130-SME-006]` — the full Appendix T table catalog governing these Indicator values has not been transcribed into this KB pass.

## 4. EMV Chip Data: TLV Structure and Cross-Field Consistency

Element 190 (EMV Chip Data) is not an opaque blob — the pre-existing Test Team appendix (`appendix-r-segment-100-coverage.json`) already documents:

- It must begin with a valid three-digit length consistent with the encoded chip data that follows.
- It must preserve valid BCD-packed/hex **TLV** (tag-length-value) structure, with representative tags including `9F06` (AID), `9F02` (authorized amount), `9F26` (application cryptogram), `82` (application interchange profile), `9F36` (ATC), `9C` (transaction type), `5F2A` (transaction currency), `9F1A` (terminal country), and others.
- Certain chip-data values (amount, transaction type, currency, terminal country) must agree with the corresponding Segment 100 fields — `[PROVISIONAL SEG130-SME-003]`, still `REVIEW_REQUIRED` in the pre-existing appendix.
- **Cryptogram authenticity (Tag 9F26) is explicitly out of scope** for this framework: genuine verification requires a certified EMV kernel or HSM, not AI/synthetic generation (`SEG130-R-010`). Do not attempt to fabricate cryptogram validation logic.

## 5. CA Public Key File (Appendix S)

Segment 130's CA Public Key File Checksum ties to a broader CA key-management concern documented in Appendix S: a `CA_KEYS` file with a specific header/record layout (expiry date, SHA-1 hash indicator, RSA algorithm indicator, RID, index, modulus, exponent, checksum). Structural validation (file format, record layout) is synthesizable; genuine AID-to-key resolution requires a real production key file (`[PROVISIONAL SEG130-SME-002]`, `EXTERNAL_FIXTURE_REQUIRED`).

## 6. Validator Rules Planned (`Segment130PayloadValidator`, not yet implemented)

- Segment 130 is required whenever the transaction is an EMV Financial Transaction Request (`SEG130-R-001`).
- Segment Type fixed `130`, Segment Length 4-digit within `0001-3043` (`SEG130-R-002`, `SEG130-R-004`, upper bound pending `SEG130-SME-001`).
- EMV Chip Data Length required, numeric `000-999`; EMV Chip Data required, TLV-structured (`SEG130-R-007`, `SEG130-R-008`).
- EMV Additional Information Section: zero or more repetitions, no internal separators, single trailing separator, ≤ 2,000 bytes total (`SEG130-R-011`, `SEG130-R-013`).
- Cross-field consistency and cryptogram authenticity remain `REVIEW_REQUIRED` / out-of-scope respectively, not silently passed or failed (`SEG130-R-009`, `SEG130-R-010`).

## 7. Suggested Segment 130 Test Scenarios

| ID | Scenario | Expected result |
| --- | --- | --- |
| EMV-130-001 | EMV Financial Transaction Request with valid Segment 130 (type, 4-digit length, chip data) | Pass |
| EMV-130-002 | EMV Financial Transaction Request omits Segment 130 | Fail (`SEG130-R-001`) |
| EMV-130-003 | EMV Chip Data Length outside 000-999 | Fail (`SEG130-R-007`) |
| EMV-130-004 | EMV Chip Data length prefix mismatched with actual TLV content | Fail (`SEG130-R-008`) |
| EMV-130-005 | EMV Additional Information Section with a separator between repetitions | Fail (`SEG130-R-013`) |
| EMV-130-006 | EMV Additional Information Section exceeding 2,000 bytes | Fail (`SEG130-R-011`) |
| EMV-130-007 | EMV chip amount/currency mismatched with Segment 100 | Review pending `SEG130-SME-003` |
| EMV-130-008 | Application Cryptogram (9F26) authenticity check | Out of scope — do not attempt (`SEG130-R-010`) |
| EMV-130-009 | CA Public Key File Checksum echoed correctly in Segment 131 response | Pass (structural only, pending `SEG130-SME-002` for genuine key-file link) |

## 8. SME Checklist

When reviewing an AI-generated Segment 130 artifact, ask:

- Does it treat Segment 130 as required for EMV transactions, not merely optional?
- Does it avoid emitting separators within or between EMV Additional Information Section repetitions?
- Does it avoid asserting cryptogram authenticity validation as achievable without a certified EMV kernel/HSM?
- Does it avoid conflating Segment 112's Element 118 with Segment 130's Element 118?

## Source References

- Section 11.8.1, EMV Financial Transaction Request: lines 9970-10120.
- Section 12.20, EMV Request Data Segment: lines 14013-14100.
- `test-json/segment-130-core-structure-package.json` (pre-existing Test Team baseline).
- `test-json/appendices/appendix-r-segment-100-coverage.json` (EMV Chip Data TLV/cross-field).
- `test-json/appendices/appendix-s-segment-100-coverage.json` (CA Public Key File).
- [Segment 130 Rule Catalog](coverage/segment-130-rule-catalog.json).
- [SME/TBA Input Register](segment-130-sme-tba-input-register.md).
- [AI-Generated vs Test-Generated Requirement Comparison](segment-130-ai-vs-test-requirement-comparison.md).
