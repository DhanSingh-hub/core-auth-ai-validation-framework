# Segment 130 EMV Request Data Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, Sections 11.8.1, 12.20, Appendix R, Appendix S, and Elements 84, 85, 187-192, 118.

## 1. What Segment 130 Means

Segment 130 is the **EMV Request Data Segment** — the request-side vehicle for chip-card (EMV) transaction data. It plays the same "mandatory anchor" role for EMV Financial Transactions that Segment 100 plays for all financial transactions and Segment 108 plays for Loyalty Card Transactions: the specification explicitly states **"This segment is the only segment required for all EMV financial transactions."**

```text
EMV Financial Transaction Request
  Data Section 2: Segment 100 (standard transaction data)
  Data Section 3: Segment 130 (REQUIRED - EMV chip data), Field Nos. 4-8
                  + up to four of: 101, 102, 103, 104, 111, 123,
                                   135, 143, 145, 146, 151, 152, 153
```

**Correction (2026-09-28).** An earlier revision of this note stated that Segment 103 (EBT) is *not* among Segment 130's companions. That was wrong. It was derived from the abbreviated prose bullet list in Section 11.8.1, which is a partial summary immediately followed by "See the Data Section No. 3 table below for specifics." The authoritative table **does** list `103  EBT Data Segment  3,334  C`, and the Chapter 12 segment/transaction matrix independently marks 103 as valid for the EMV Financial Transaction Request column.

Two genuine scope limits do apply:

- **Five field slots, not six.** The EMV request uses Field Nos. 4-8, versus 4-9 for the generic Financial Transaction Request. Segment 130 plus at most four companions (`SEG130-R-018`). Element 63 therefore cannot exceed `06`.
- **Reversals are exempt.** Section 10.14.2.4: *"EMV data is not required on Reversal transactions."* Segment 130 may legitimately be absent from an EMV Purchase Reversal or TOR (`SEG130-R-017`).

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

Maximum Segment 130 length is **3,043 alphanumeric characters** (`SEG130-R-004`). This is now settled: Section 12.20 and the Element 84 valid-codes table both state 3,043, while the Section 11.8.1 layout table states **9,999**. The conflict is genuine — the layout table's Max.Len. column wraps after three characters, confirmed by sibling rows (103 renders `3,33`+`4` = 3,334; 151 renders `230`+`9` = 2,309). **3,043 is adopted** because two independent sources state it and exact field arithmetic (`3+4+25+3+3+999` fixed `+ 2,000` section `+ 6` separators) reproduces it precisely. `SEG130-SME-001` now carries only an administrative action: log a specification defect against the 11.8.1 table. See the [serialization note](serialization-wire-format/serialization-wire-format-sme-tba-note.md).

### A cross-segment quirk: Element 118 is reused

Segment 112's "Additional Information" field and Segment 130's "EMV Additional Information" field **both use Element 118** — but they are structurally and semantically different (Segment 112: single occurrence, max 984; Segment 130: repeating, part of a 3-field group). Do not assume shared element numbers imply shared business meaning across segments (`SEG130-R-012`).

## 3. The EMV Additional Information Section (Repeating Group)

This is structurally the same pattern as Segment 111's Variable Information Section: no Field Separators occur within or between repetitions, and exactly one Field Separator follows the **final** repetition. The section as a whole is capped at 2,000 bytes in the request and is repeated **"by EMV Additional Information Indicator."**

**Appendix T is now transcribed (former `SEG130-SME-006`, resolved).** Appendix T is present in the extracted specification text and defines exactly two indicator values:

| Table ID | Name | Max Len | Values | Direction |
| --- | --- | ---: | --- | --- |
| `001` | EMV Table Data | 6 | `EMVYES` / `EMVNOT` | Request; if returned in a response, the device echoes it on any subsequent advice or batch upload request |
| `002` | Card Authentication Results Code (CARC) | 1 | CARC value from Visa Bit 44.8 | Response-side; request legality unconfirmed (`SEG130-SME-007`) |

Table `001` notifies the chip when the MasterCard X-Code system was unable to go online, ensuring correct processing by a card personalized for full-grade processing. Full detail in the [EMV Additional Information note](emv-additional-information-sme-tba-note.md).

Note the request/response cap asymmetry: Segment 130's section is capped at **2,000** bytes, Segment 131's at **2,800** (`SEG130-R-023`).

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
