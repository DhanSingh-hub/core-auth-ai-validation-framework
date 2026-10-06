# Segment 131 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 131 — EMV Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.2-EMV, 12.11, 12.20, 12.21  
**Oracle:** [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 131 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (4)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG131-R-001` | Section 12.21 states Segment 131 'always appears in Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response's own layout table places it at Field No. 17/18/19/20 of Data Section No. 2 (following Segments 112, 115, and 120) — these two placement statements are contradictory | 12.21,11.1.2-EMV | — | REVIEW_REQUIRED |
| `SEG131-R-002` | Segment 131 is response-only and conditional: it follows in the EMV Financial Transaction Response only when EMV data is required, alongside Segments 112 and 115/120 | 11.1.2-EMV | — | SPEC_DERIVED |
| `SEG131-R-009` | The EMV Additional Information Section (fields 6-8: Indicator, Length, Information) repeats per EMV Additional Information Indicator for a maximum total length of 2,800 bytes — a DIFFERENT cap than Segment 130's 2,000-byte limit for the structurally identical section | 12.21 | 191,192,118 | SPEC_DERIVED |
| `SEG131-R-012` | In the EMV Financial Transaction Response, Segment 131 always follows Segment 130's response counterpart context — i.e., it appears after Segments 112 (Field 16/17/18) and 115/120 (Field 17/18/19) when those are also present | 11.1.2-EMV | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 191 | EMV Additional Information Indicator | N | 3 bytes | Fixed length of 3 digits | Code Description 001 EMV table data |
| 192 | EMV Additional Information Length | N | 3 bytes | Fixed length of 3 digits | 001–985 |
| 118 | Additional Information | AN | 984 bytes | Variable length of up to 984 alphanumeric characters — see [Element 118 reference](#element-118-additional-information) | 001–984 a–z A–Z Please refer to Appendix K. Additional Information Data Layouts. |

## Chapter 13 Reference: Full Element Definitions

Full, untruncated Chapter 13.2 text for the elements listed above, transcribed from the ATL105 specification extract (`docs/specs/extracted_text.txt`). The table above links here instead of truncating long value lists.

### Element 191: EMV Additional Information Indicator

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 digits
- **Purpose:** Identifies the type of EMV additional information transmitted in the Financial Transaction request or response.

**Valid Codes/Values**

Code Description 001 EMV table data

### Element 192: EMV Additional Information Length

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 digits
- **Purpose:** Identifies the length of EMV Additional Information.

**Valid Codes/Values**

001–985

### Element 118: Additional Information

- **Character type:** AN · **Maximum length:** 984 bytes
- **Representation:** Variable length of up to 984 alphanumeric characters Note: Please refer to section 12.11, “Additional Information Data Segment,” for conditions affecting this element’s length.
- **Purpose:** Identifies the additional data being transmitted in the Financial Transaction response.
- **Processing rules:** Used in the Additional Information Data Segment (No. 112). When Element No. 116 (Additional Information Indicator) has a value of 001, gift card, EBT card, credit card, or phone card balance information is included here. When Element No. 116 (Additional Information Indicator) has a value of 003, AVS information is included here. When Element No. 116 (Additional Information Indicator) has a value of 004, card verification value information (CVV, CVV2, CVC2, and CID) is included here. When Element No. 116 (Additional Information Indicator) has a value of 005, ECA/ TeleCheck® Trace ID information is included here. When Element No. 116 (Additional Information Indicator) has a value of 006, ECA/ TeleCheck® Denial Record Number information is included here. When Element No. 116 (Additional Information Indicator) has a value of 007, ECA/ TeleCheck® Return Check Data is included here. When Element No. 116 (Additional Information Indicator) has a value of 008, loyalty information is included here. When Element No. 116 (Additional Information Indicator) has a value of 009, Visa® product result information is included here. When Element No. 116 (Additional Information Indicator) has a value of 010, loyalty information is included here. When Element No. 116 (Additional Information Indicator) has a value of 011, user data information is included here. When Element No. 116 (Additional Information Indicator) has a value of 012, the Discover® Network Retrieval Reference Number is included here. It has a fixed alphanumeric length of 15 bytes. It is included in all Discover® Network follow-on transactions. When Element No. 116 (Additional Information Indicator) has a value of 013, the Expiration Date (MMYY) is included here. It has a fixed length of 4 bytes. When Element No. 116 (Additional Information Indicator) has a value of 016, the PIN-on-Receipt Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 017, the BUYPASS Host Card Type is included here. It has a fixed length of three bytes. When Element No. 116 (Additional Information Indicator) has a value of 018, the PINless transaction processing information is included here. It has a fixed numeric length of 3. When Element No. 116 (Additional Information Indicator) has a value of 019, the Visa Spend Qualified Indicator Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 020, the Host Prompts Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 021, the Re-Price Data Response Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 022, the CAVV Result Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 023, the MCX Reference Number Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 024, the Carwash Indicator Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 025, the Language Indicator Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 026, the DST Response Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 027, the Universal Unique Identifier is included here. When Element No. 116 (Additional Information Indicator) has a value of 028, the Transaction Identifier Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 029, the PAR (Payment Account Reference) Data Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 030, the Merchant Advice code is included here. When Element No. 116 (Additional Information Indicator) has a value of 031, the DAF Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 032, the Agreement ID is included here. When Element No. 116 (Additional Information Indicator) has a value of 034, the Host-based Purchase Restriction is included here. When Element No. 116 (Additional Information Indicator) has a value of 035, the Cardholder Additional Information Result Code is included here. When Element No. 116 (Additional Information Indicator) has a value of 036, the Account Type is included here. When Element No. 116 (Additional Information Indicator) has a value of 037, the Account Funding Source is included here. When Element No. 116 (Additional Information Indicator) has a value of 038, the Transaction Link Identifier is included here. When Element No. 116 (Additional Information Indicator) has a value of 039, theTransaction Link Action Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 040, the Fraud Score is included here. When Element No. 116 (Additional Information Indicator) has a value of 041, the Fraud Score Reason Code is included here. When Element No. 116 (Additional Information Indicator) has a value of 042, the Authentication Data Quality Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 043, the Token Update First Use Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 044, the Merchant Tran ID is included here. When Element No. 116 (Additional Information Indicator) has a value of 045, the Applied Special Service is included here. When Element No. 116 (Additional Information Indicator) has a value of 046, the Voyager Restriction Code is included here. When Element No. 116 (Additional Information Indicator) has a value of 047, the Visa Category Code is included here.

**Valid Codes/Values**

001–984 a–z A–Z Please refer to Appendix K. Additional Information Data Layouts.

## Catalog Notes

- `SEG131-R-001` — PROVISIONAL: independently corroborated by the AI Solution Team's own statement ('The EMV Response Data Segment (Segment 131) follows in Field No. 17/18/19/20 when EMV data is required'), which agrees with the layout table, not with Section 12.21's own opening sentence. Pending SME confirmation (SEG131-SME-001).
- `SEG131-R-009` — Do not copy Segment 130's 2,000-byte cap onto Segment 131 — the two limits are independently documented and different.

## SME Reasoning

Ask:

1. Which message families may carry Segment 131, and in which Data Section?
2. Is Segment 131 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 131 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 131 ever legitimate, and how should that be diagnosed?

## TBA Dependency Chain

```text
transaction / message family
  -> Data Section placement
  -> inclusion condition
  -> companion-segment set
  -> Element 63 (Number of Segments) count where applicable
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Section 12.21 states Segment 131 'always appears in Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response's own layout table places it at Field No. 17/18/19/20 of Data Section No. 2 following Segments 112, 115, and 120 — these two placement statements are contradictory
  -> source: ATL105 2026-3 §12.21,11.1.2-EMV (SEG131-R-001)
  -> a violating payload shall fail validation citing SEG131-R-001
```

## Open Provisional Items

- **P-01** (SEG131-R-001): Section 12.21 states Segment 131 appears in 'Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response layout table (also corroborated by the AI Solution Team's own statement) places it at Field No. 17/18/19/20 of Data Section No. 2. Which governs, or do these describe two different message contexts?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment131PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG131-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
