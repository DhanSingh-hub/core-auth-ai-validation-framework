# Segment 116 — TransArmor Load Data

**Specification:** BUYPASS Platform ATL105, Release 2026-3  
**Training state:** Partial source-derived baseline; complete layout unavailable in the ATL105 source extract.

Sections 11.7.5 and 12.15 redirect to the separately published “BUYPASS Platform ATL105 Specification Updates for TransArmor Processing” document. That document was not available in the source material used for this baseline. Do not infer the missing request or response layouts from the Chapter 13 element catalog alone.

## Known source facts

- Segment Type (Element 85) identifies Segment 116 as the TransArmor Load Data Segment used for Key and Key ID Load.
- Segment Length (Element 84) has a stated range of `01-50`.
- The Chapter 13 catalog identifies Elements 155, 156, and 157 as Key ID, Key Data Length, and Key Data in the TransArmor Load Response.
- Appendix I Table 052 describes Additional TransArmor Data in Segment 111. Its relationship to Segment 116 requires confirmation.
- Section 11.4.1.2 contains an inconsistent reference to Segment 116 for Totals with Proprietary Data Load; the Segment Type catalog and Section 12.17 identify that message as Segment 119.

## Open source and review gaps

- The Segment 116 field-by-field layout and request/response envelope are not present in the extracted ATL105 source.
- The length pattern and field positions beyond the documented Segment Type and Segment Length are not independently confirmed.
- The request/response rules, Table 052 pairing, sanitized fixtures, and independent AI-artifact intake remain review-gated.

See the [rule catalog](coverage/segment-116-rule-catalog.json), [SME/TBA input register](segment-116-sme-tba-input-register.md), [coverage status](coverage/README.md), and [serialization notes](serialization-wire-format/serialization-wire-format-sme-tba-note.md).
