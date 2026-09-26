# Segment 101: SME and Technical Business Analysis Learning Note

## Purpose

Segment 101 is the **Fleet Data Segment** — the ATL105 Section 3 companion that a device must include whenever a Financial Transaction Request is executed with a fleet-eligible card. A fleet-card SME explains why the fleet program requires each field; a technical business analyst turns that behavior into explicit rules, traceability, and testable outcomes.

This note is a learning guide, not an approved client requirement set. It mirrors the [Segment 100 SME/TBA Learning Note](../segment-100/segment-100-sme-tba-learning-note.md) — read that document first for the shared mental model.

## 1. The Correct Mental Model

Do not start from the Segment 101 JSON fields. Start from the fleet business flow:

```text
Fleet-card POS event
  -> transaction intent (purchase, auth, auth completion, reversal)
  -> Prompt Code and fleet card program
  -> Segment 100 core fields
  -> Segment 101 fleet fields required by the program
  -> Fleet Tags 1-5 (only on Auth Completion 0220 when Host Prompts are supported)
  -> serialize together with Segment 100
  -> host response and lifecycle correlation
```

Segment 101 exists **only** to carry fleet-program data that Segment 100 cannot express. It never travels alone: every message that contains Segment 101 also contains Segment 100 as its sibling.

## 2. SME Reasoning: Fleet Card, Program, and Message Intent

A fleet-card SME should answer these questions before defining Segment 101 test data:

1. Is the card actually a fleet card and is the fleet program active for this merchant?
2. Is this an initial Financial Transaction Request (purchase, auth) or an Auth Completion (0220)?
3. Which fleet fields does the issuer/program require for this transaction? (Odometer, Driver ID, Vehicle Number, Fleet Employee Number, License, Job ID, Department, Customer Data, User ID, Vehicle ID#)
4. Are Host Prompts supported by the device, and did the prompts capture any Fleet Tag data (DLS, DLN, PON, INV, TRP, UNT, TLH, DOB, ZIP, END, MID, VIN, TRA, HUB, TLR, CBA, VHT)?
5. Is Segment 145 (Enhanced Fleet) applicable? If yes, do **not** send Segment 101 — the two are mutually exclusive.
6. What is the expected lifecycle: Auth followed by Completion, single Purchase, Reversal/Void?

A technical business analyst converts each answer into:

```text
condition -> required field/segment -> valid representation -> expected result
```

Example:

```text
Fleet auth completion with Host Prompts capturing a P.O. Number
  -> Segment 101 present
  -> Fleet Tag 1 = "PON" + up to 31-byte P.O. Number value
  -> Element 63 includes Segment 100 and Segment 101 and any other companions
  -> missing Segment 100 = invalid
  -> Segment 101 + Segment 145 both present = invalid
```

## 3. Segment 101 Field Responsibilities

Segment 101 contains 18 ordered fields — 13 base fields for every fleet message plus 5 Fleet Tag fields that are Auth-Completion-only. Their SME purpose is more important than their JSON spelling.

| # | Element | Name | R/O/C | SME interpretation | Common analysis risk |
| --: | --: | --- | --- | --- | --- |
| 1 | 85 | Segment Type | R | Identifies Segment 101 | Confusing segment type with segment length |
| 2 | 84 | Segment Length | R | Length of encoded Segment 101 content | Counting JSON characters instead of wire content |
| 3 | 64 | Odometer | C | Current vehicle odometer for fleet-program reporting | Populating without an issuer requirement, or truncating to fit |
| 4 | 108 | Vehicle Number | C | Fleet-managed vehicle identifier | Reusing a driver identifier as vehicle |
| 5 | 47 | Job Number | C | Work-order style value the fleet manager assigns to the trip | Mixing with Job ID (field 9) which is a distinct concept |
| 6 | 31 | Driver/Identification Number | C | Driver identity captured at the pump; must be unencrypted per issuer rule | Assuming the value is always numeric; assuming it is always encrypted |
| 7 | 40 | Fleet Employee Number | C | Payroll-style employee identifier used by the fleet | Overloading with Driver ID |
| 8 | 158 | License # | C | Driver license number of the fleet card user | Confusing with License State (a Fleet Tag DLS value) |
| 9 | 159 | Job ID | C | Fleet-program job identifier (12 bytes, wider than Job Number) | Mixing with Job Number (field 5) |
| 10 | 160 | Department # | C | Department/GL code inside the fleet | Treating it as free text without the 12-byte cap |
| 11 | 161 | Customer Data | C | Free-form customer field the fleet chooses | Treating as an approval indicator |
| 12 | 162 | User ID | C | User identity for the fleet system (max 12; must not be all zero) | Sending `"000000000000"` — explicitly disallowed |
| 13 | 163 | Vehicle ID# | C | Vehicle Identification identifier (max 8) | Confusing with Vehicle Number (field 4) |
| 14 | — | Fleet Tag 1 | C (Auth Completion + Host Prompts) | 3-byte code + up to 31-byte payload | Populating in the initial request |
| 15 | — | Fleet Tag 2 | C (Auth Completion + Host Prompts) | same TLV structure | Same |
| 16 | — | Fleet Tag 3 | C (Auth Completion + Host Prompts) | same TLV structure | Same |
| 17 | — | Fleet Tag 4 | C (Auth Completion + Host Prompts) | same TLV structure | Same |
| 18 | — | Fleet Tag 5 | C (Auth Completion + Host Prompts) | same TLV structure | Same |

The exact JSON keys in an AI-generated artifact may differ. Canonical source anchors identify the meaning.

## 4. Fleet Tag Analysis

Fleet Tags are not free text. Each tag is a **3-byte code plus a per-code payload**:

```text
Fleet Tag n
  -> Tag code (fixed 3 bytes, one of the 17 registered codes)
  -> Data payload (per-code format and maximum length)
```

For every test case that carries a Fleet Tag, record:

- Which tag position(s) is populated (Fleet Tag 1 through Fleet Tag 5)
- The 3-byte code (DLS, DLN, PON, INV, TRP, UNT, TLH, DOB, ZIP, END, MID, VIN, TRA, HUB, TLR, CBA, VHT)
- The per-code format applied to the payload (see the code table in the [rule catalog](coverage/segment-101-rule-catalog.json))
- Whether Host Prompts were the source of the value
- The transaction context: this must be an Auth Completion (0220) message

Do not infer that any Fleet Tag is required by ATL105. ATL105 lists which codes exist and which formats they carry; whether a fleet program mandates a particular tag is a program-level decision (PROVISIONAL P-06).

## 5. Segment 101 and Companion Segments

Segment 101 is required exactly once in a fleet-card financial transaction request. It coexists with Segment 100 always. It **must not** coexist with Segment 145.

| Fleet flow | Minimum segment set |
| --- | --- |
| Fleet purchase (non-EMV, non-fuel product data) | 100 + 101 |
| Fleet purchase with fuel/product data | 100 + 101 + 102 |
| Fleet EMV purchase | 100 + 101 + 130 |
| Fleet purchase with variable information | 100 + 101 + 111 |
| Fleet Auth Completion carrying Host-Prompt data | 100 + 101 (with Fleet Tags populated) |
| Fleet enhanced flow (Segment 145 in use) | 100 + 145 (Segment 101 must not be sent) |
| Multiple applicable categories | 100 + 101 + every other required companion |
| Unmapped combination | expected set is unknown; `REVIEW_REQUIRED` |

Card technology, POS configuration, fleet-program subscription, and Host Prompts capability all influence the segment set. Segment 101 alone does not determine the answer.

## 6. Serialization Thinking

A structured JSON Segment 101 representation is not automatically a valid wire message.

Validate these separately:

1. JSON structure and data types.
2. Semantic field values (issuer-required fields present; disallowed User ID = 0 rejected).
3. Segment presence and order (Segment 100 first, then Segment 101 as a Section 3 companion in field 4..9 of Data Section 3).
4. Field separators between Segment 101 fields.
5. Segment 101 field order (Section 12.2 order, fields 1..13 for every message, fields 14..18 only when applicable).
6. Segment Length (Element 84 value 001-061 in the base case; PROVISIONAL P-01 for Auth Completion with tags).
7. Element 63 segment count in Data Section 1 (includes Segment 101).
8. Binary/network byte order for the surrounding TCP/IP header.

For Segment 101 specifically:

- Segment Type is fixed as `101`.
- Empty non-trailing fields retain their separators.
- Trailing optional fields may be omitted only as a suffix.
- Fleet Tags 1..5 must not be sent outside Auth Completion or when Host Prompts are not supported.

A test control such as `"trailingOptionalFieldsOmitted": true` is only a claim until the actual serialized representation proves it.

## 7. Lifecycle and Fleet Business Analysis

Segment 101 is the primary channel through which fleet Auth Completion messages carry Host-Prompt data back to the host. Fleet Auth Completion is a lifecycle follow-up to a prior Auth, not a fresh purchase.

| Fleet flow | What must remain correlated |
| --- | --- |
| Fleet Auth -> Fleet Auth Completion (0220) | Original Sequence Number and approval context in Segment 100; Fleet Tag data added by the completion |
| Fleet Purchase -> Reversal/Void | Original Segment 100 identity, sequence, approval reference; Segment 101 fields consistent with the original request |
| Fleet Purchase -> Chargeback / dispute | Original request identity plus Segment 101 driver/vehicle context that supports the dispute |

A new Sequence Number in the completion (or missing Segment 101 in the auth-completion) may make the message syntactically valid but business-invalid.

## 8. Turning Knowledge Into Requirements

Good BR:

```text
For every fleet-card Financial Transaction Request, Segment 101 shall be present
in addition to Segment 100. Segment 101 shall not coexist with Segment 145 in the
same message. Segment 101 Fleet Tags 1-5 shall be populated only in Auth Completion
(0220) messages when Host Prompts are supported.
```

Weak BR:

```text
The system should support fleet.
```

A good Segment 101 requirement identifies:

- actor or system boundary (device, converter, host)
- fleet condition (card program, message type, Host Prompts capability)
- required behavior (field present, value bounded, tag code from the enumerated list)
- data element or segment
- expected outcome (accept / reject / review)
- source anchor (Section 12.2, field #, Element ID)
- exception or review boundary (PROVISIONAL items)

## 9. Turning Requirements Into Tests

Each scenario should define a fleet-business variation. Each test case should isolate one verifiable behavior. Each test data document should instantiate that behavior.

```text
BR: Segment 101 must not coexist with Segment 145
  -> Scenario: Fleet purchase with both segments present
      -> Test Case: send Segment 100 + Segment 101 + Segment 145
          -> Test Data: fleet card, Fleet segment populated, Enhanced Fleet segment populated
              -> Expected: FAIL (mutual exclusion)
```

Use canonical anchors instead of relying on matching producer IDs:

```text
ATL105 | 2026-3 | 12.2 | 101,145 | | mutual-exclusion-enhanced-fleet
```

## 10. Review Checklist for an AI Deliverable

### Business meaning

- Does the requirement describe a real fleet-card / fleet-program behavior?
- Is the transaction type (Purchase, Auth, Auth Completion, Reversal) explicit?
- Are Fleet Tag rules stated as Auth-Completion-only?
- Are merchant/fleet-configuration assumptions visible?

### Message construction

- Is Segment 100 present in every message that contains Segment 101?
- Is Segment 101 present exactly once (Rule SEG101-R-026)?
- Are the 13 base fields represented in the correct order?
- Are Fleet Tags 1..5 populated only in Auth Completion + Host Prompts context?
- Is Element 63 consistent with the actual serialized segment count?

### Field values

- Do numeric fields (Odometer) obey their numeric type and max length?
- Do alphanumeric fields obey character-set and max-length caps?
- Is User ID (Element 162) explicitly not "0"..."000000000000"?
- Do Fleet Tag codes match the 17-code enumeration?
- Do Fleet Tag payloads match the per-code format (DLS=an3, DLN=an22, PON=an31, INV=an31, TRP=an15, UNT=an31, TLH=n6, DOB=n8, ZIP=an9, END=an31, MID=an31, VIN=an17, TRA=an15, HUB=n9, TLR=an15, CBA=n6, VHT=an10)?

### Companion data

- Is Segment 145 correctly rejected as a coexisting segment?
- Are EMV, product-data, EBT, purchase-card, variable-information, NFC, and Moneris triggers considered on top of the fleet trigger?
- Is a missing Segment 100 treated as an error even when Segment 101 is well-formed?

### Serialization

- Are field order, separators, Segment Length, and Message Length tested?
- Are empty non-trailing fields retained?
- Are trailing optional fields omitted only when appropriate?
- Is the Segment Length within 001..061 in the base case (PROVISIONAL P-01 flagged for tag-carrying messages)?

### Traceability

- Does every artifact have a source anchor (Section 12.2 + field + element + rule)?
- Does BR -> Scenario -> Test Case -> Test Data resolve for every SEG101-R-### rule?
- Are duplicate/orphan/anchor-mismatch artifacts rejected?

## Source References

- Section 11.1.1, Financial Transaction Request Data Section 3 placement.
- Section 12.2, Fleet Data Segment: field layout, Fleet Tag codes, and the Segment 101 / Segment 145 mutual-exclusion note.
- Appendix G, Valid Transaction Type Codes (working assumption for fleet triggers is PROVISIONAL P-04).
- [Segment 101 canonical anchors](../segment-101-canonical-anchors.md).
- [Segment 101 rule catalog](coverage/segment-101-rule-catalog.json).
- [Segment 101 validation rules](segment-101-validation-rules.json).
- [Segment 100 SME/TBA learning note](../segment-100/segment-100-sme-tba-learning-note.md) for the shared mental model.
