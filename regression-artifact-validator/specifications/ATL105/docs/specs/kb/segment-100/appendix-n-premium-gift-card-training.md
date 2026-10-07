# Appendix N Premium Gift Card Training

**Specification:** BUYPASS ATL105 2026-3, Appendix N-1<br>
**Data segment:** Segment 100 Track II magnetic-stripe data (First Data™ Premium Gift Card flow only)<br>
**Fields:** Field 1 Start Sentinel, Field 2 Account Number, Field 3 Separator, Field 4 Expiration Date,
Field 5 Service Code, Field 6 PVKI, Field 7 PVV, Field 8 Discretionary Data, Field 9 End Sentinel,
Field 10 LRC<br>
**Status:** `PARTIALLY_COVERED`; all six source-anchored BRs have an independent, bounded
implementation and in-repo test fixtures. BIN-registry membership, Service Code table meaning, PVV
cryptographic correctness, and a modeled Segment 100 Track II payload field remain out of scope.

## Scope and Field Boundaries

Appendix N defines a single, fixed Track II layout used only when a Segment 100 transaction context
uses the First Data™ Premium Gift Card magnetic-stripe flow (see source line 5710: "For a detailed
layout of Track II magnetic stripe data, please refer to Appendix N"). Unlike Appendix K (44
selector-driven Element 118 layouts) or Appendix L (151 currency-code rows), Appendix N is a single
table with ten fixed fields plus one derivation note; there is no selector/table-ID indirection.

The source does not define a literal character for the Start Sentinel or End Sentinel (field
descriptions only say the character "is cut from the packet" / "is not included in the packet that is
transmitted"), so this validator treats sentinel exclusion structurally: it assembles the transmitted
packet from fields 2-8 and 10 only, and separately confirms the caller-supplied sentinel literals (if
any) do not appear in that assembled packet. It does not assume a specific sentinel character (such as
`%` or `;`) is mandated by Appendix N itself.

## Test Solution Work Completed

`AppendixNPremiumGiftCardTrackValidator` independently implements and tests all six BRs:

- **BR-SEG100-APPN-TRACK2-ACCOUNT** — Account Number must be exactly 16 numeric digits and pass the
  Visa modulo-10 (Luhn) check digit. This validates representation and arithmetic only; it does not
  certify that the resulting 6-digit BIN is actually registered to the First Data Premium Gift Card
  program, since no BIN registry is part of Appendix N.
- **BR-SEG100-APPN-TRACK2-FORMAT** — Separator must be the literal `=`; Expiration Date must be
  `YYMM` with a valid month `01`-`12`; Service Code must be three numeric digits; PVV must be four
  numeric digits; Discretionary Data must be exactly eight characters. Service Code table meaning and
  PVV cryptographic correctness are not evaluated — Appendix N does not define either.
- **BR-SEG100-APPN-SERIAL-COMPONENT-LENGTHS** — A valid 16-digit Account Number decomposes into a
  6-byte BIN, a 9-byte Partial Account Number, and a 1-byte Visa check digit.
- **BR-SEG100-APPN-SERIAL-NUMBER** — The Appendix N-1 note's serial number derives as the 9-byte
  Partial Account Number (the bytes immediately following the 6-byte BIN) concatenated with the first
  four bytes of Discretionary Data. Downstream use of the derived serial number is not evaluated.
- **BR-SEG100-APPN-SENTINEL-EXCLUSION** — The packet transmitted to the host application is assembled
  from fields 2-8 and 10 only; Start Sentinel (field 1) and End Sentinel (field 9) must not appear in
  it. Proven both for the well-formed case and for a negative fixture where the sentinel character
  leaks into Discretionary Data.
- **BR-SEG100-APPN-PVKI-UNUSED** — PVKI must occupy exactly one character position, but its value is
  never evaluated, per the source note that PVKI "is not currently used by the First Data™ Premium
  Gift Card system."

Package totals: **6 BR / 2 TS / 2 TC / 4 TD**, all `COVERED`/`EXECUTABLE` using in-repo fixtures (see
`AppendixNPremiumGiftCardTrackValidatorTest` and
`specifications/ATL105/test-output/test-json/appendices/appendix-n-segment-100-coverage.json`).

## Deferred Work

1. Confirm Premium Gift Card applicability and the registered BIN range against an authoritative BIN
   registry — not part of Appendix N and therefore not independently assertable from this source alone.
2. Obtain specialized AI-generated Premium Gift Card track-data artifacts to replace the in-repo
   fixtures before implementation certification.
3. Decide whether/how to wire `AppendixNPremiumGiftCardTrackValidator` into
   `Segment100PayloadValidator` if a modeled Track II field is ever added to the Segment 100 payload
   schema; today no such field exists, so this validator is intentionally standalone (mirroring how
   Appendix E/F/G/J are independent Segment-100-context oracles rather than being wired into the
   payload validator).

## Source References

- ATL105 2026-3, "refer to Appendix N" cross-reference: [extracted specification](../../extracted_text.txt), line 5710.
- ATL105 2026-3 Appendix N-1 table and serial-number note: [extracted specification](../../extracted_text.txt), lines 35040-35077.
