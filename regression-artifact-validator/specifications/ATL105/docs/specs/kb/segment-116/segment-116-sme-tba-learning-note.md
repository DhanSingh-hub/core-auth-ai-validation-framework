# Segment 116 TransArmor Load: SME and TBA Learning Note

## Purpose

Segment 116 is the TransArmor Load Data Segment. It carries the Key and Key ID Load request for TransArmor PKI Encryption and Tokenization — a point-to-point encryption and tokenization scheme distinct from the standard financial transaction flow (Segment 100), the check flow (Segment 110), or the electronic-mail flow (Segment 109).

## Why This Segment Trains Differently

Every other segment trained so far (100, 109, 110) has a complete field table in Section 12.x of the extracted ATL105 specification. Segment 116 does not. Both of its defining sections are single-sentence stubs that redirect to a separate, external document ("BUYPASS Platform ATL105 Specification Updates for TransArmor Processing") that is not part of this workspace's source material. This is a **data availability gap**, not an extraction failure: the same stub text appears twice (Section 12.15 and Section 11.7.5), confirming that the base ATL105 specification deliberately factors TransArmor details into a separate document.

## What We Can Say With Confidence

| Fact | Confidence | Source |
|---|---|---|
| Segment number 116 = TransArmor Load Data Segment | Confirmed | Element 85 Segment Type valid-codes table |
| Purpose: Key and Key ID Load | Confirmed | Element 85 processing rule |
| Maximum length 50 alphanumeric characters | Confirmed | Element 84 length table |
| Segment Type (85) fixed value `116`, Required | Confirmed | Element 85 catalog entry, segment-specific processing rule |
| Data Section 2 placement, following Data Section 1 (55, 63) | Confirmed | Element 63 processing rule |
| Segment Length (84) present as field 2 | Pattern-derived, not segment-specific | Universal Chapter 12 convention |
| No Segment 100, no Data Section 3 | Structural analogy only | Element 63 processing rule lists TransArmor alongside Totals/Loyalty/Electronic Mail, all of which exclude Segment 100 |
| Response carries Key ID (155), Key Data Length (156), Key Data (157) | Confirmed field definitions; container structure unconfirmed | Chapter 13 element catalog |
| Fields beyond Segment Type/Segment Length | **Unknown** | Not in this workspace's source |

## Business Decision Model

| Intent | Confirmed behavior | Review boundary |
|---|---|---|
| Load a new TransArmor encryption Key/Key ID | Request travels in Data Section 2 as Segment 116; response returns Key ID/Key Data Length/Key Data | Full request field content; response container structure |
| Supply extended key info (longer Key ID, special device type) | Carried in Segment 111's "Additional TransArmor Data" sub-table (052), not Segment 116 itself | Relationship between this sub-table and the Segment 116 flow |

## BR to Test-Data Decomposition

```text
BR: A TransArmor Key and Key ID Load Request shall use Segment Type 116 in Data Section 2.
TS: Device submits a TransArmor Load request and receives an approved Key ID/Key Data response.
TC: Validate Segment Type, Segment Length format, and (once available) the remaining field content.
TD: Sanitized converter-ready request/response pair with synthetic Key ID and Key Data - BLOCKED until the field layout is known.
```

## Training Boundaries

- Do not present a Segment 100 or Segment 109 fixture as proof of Segment 116 coverage.
- Do not infer or fabricate the fields occupying the remaining ~44 bytes of the 50-byte segment.
- Do not treat the Section 11.4.1.2 "(Data Segment No. 116)" label for "Totals with Proprietary Data Load Request" as a Segment 116 fact; it is a resolved source inconsistency (the correct number is 119).
- Do not expose real encryption keys, Key IDs, or KSN values in training data.
- Do not claim AI coverage from framework-generated fixtures; the supplied requirement-catalog extraction remains independent evidence under test, not certified truth.
