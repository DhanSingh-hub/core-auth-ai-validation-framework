# Segment 104 (Purchase Card Data Segment) — SME / TBA Learning Note

## Purpose

This note captures the reasoning behind Segment 104's rule catalog and the two PROVISIONAL
decisions (P-01, P-02) made in the absence of a live SME during training, so a reviewer can
quickly assess whether the pragmatic defaults are acceptable or need correction.

## What Segment 104 Is

Data Segment No. 104 carries purchase-card (corporate/procurement, Level II/III) line-item
data alongside the mandatory Segment 100. It is optional — included only when the flow
requires purchase-card data (see [segment-compatibility-matrix.md](../segment-compatibility-matrix.md)).
Its wire form has a hard ceiling of 86 alphanumeric characters, matching Element 84's
"001–86" entry in the segment-length valid-values table (Section 13, Element 84).

## Field-by-Field Reasoning

- **Segment Type / Segment Length (Elements 85, 84):** identical pattern to every other data
  segment in ATL105 — fixed 3-digit type, 3-digit length capped at the segment's own maximum.
- **Purchase Code (Element 80):** free-form alphanumeric up to 16 characters; the spec gives
  no enumeration or format beyond "Variable length of up to 16 alphanumeric characters," so
  the validator only enforces the length/charset boundary.
- **PC Tax / Freight / Duty Amount (Elements 74, 73, 72):** each is "Variable length of up to
  seven digits with two assumed decimal places." This means the wire value is digits only
  (no decimal point transmitted); the validator enforces `^[0-9]{0,7}$` and does not attempt
  to interpret the assumed decimal placement, since no business rule in Section 12.5 depends
  on the numeric magnitude.
- **Ship-to Country Code (Element 89):** "Fixed length of three digits" — enforced as exactly
  3 digits when populated (unlike the postal codes, this one is unambiguous).
- **Ship-to / Ship-from Postal Code (Elements 90, 88) — PROVISIONAL P-01:** the spec's own
  "Representation" text ("Fixed length of nine digits plus a hyphen (nnnnn-nnnn)") does not
  match its "Valid Codes/Values" text ("Any valid postal code") for a field typed AN with
  max length 10. A hyphenated 9-digit US ZIP+4 is 10 characters, so it does fit the AN(10)
  envelope, but "any valid postal code" would also permit non-US alphanumeric formats (e.g.,
  Canadian postal codes) that don't match `nnnnn-nnnn`. Without an SME available to confirm
  which shipping regions this segment must support, the validator accepts **either** shape
  and only raises a non-blocking warning (not a hard error) for anything else. This keeps the
  door open for international formats while still flagging clearly malformed values.
- **Direct Marketing Invoice Number (Element 29):** "Variable length of up to 10 alphanumeric
  characters" — straightforward length/charset check.

## Structural Reasoning

- **SEG104-R-001 (companion of Segment 100):** every Data Section 3 segment, including 104,
  is defined as accompanying the mandatory Segment 100 (Section 11.1.1). Mirrors Segment
  101's `SEG101-R-001`.
- **SEG104-R-020 (cardinality) — PROVISIONAL P-02:** Section 12.2 explicitly says Segment 101
  may occur "at most one per message." Section 12.5 has no equivalent sentence for Segment
  104. However, Data Section 3's Field Nos. 4–9 are described as holding "none, one, or more"
  of the listed segment types — read literally, this describes how many *different* segment
  types can appear, not how many instances of the *same* type. Absent a contradicting
  statement, and given no legitimate business scenario requires two Purchase Card Data
  Segments in one message, the validator enforces at most one, matching the Segment 101
  precedent. This should be confirmed with an SME before being treated as final.

## What Was Deliberately *Not* Added

No interdependency rule (e.g., "if PC Tax Amount is present, PC Freight Amount must also be
present") was added, because Section 12.5 does not state one. Inventing such a rule would
create false positives against legitimate real-world purchase-card transactions that only
populate a subset of the conditional fields.
