# Segment 101 Canonical Source Anchors

Verified against ATL105 release 2026-3, Section 12.2 "Fleet Data Segment" (pages 12-9 through 12-11) and Section 11.1.1 Data Section 3 companion-segment placement.

These anchors are the shared semantic identity for independently generated business requirements, scenarios, test cases, and test data. Producer-local IDs may differ; the anchor vocabulary must remain stable.

## Anchor Format

```text
ATL105 | version | section | segment | element | rule
```

Example:

```json
{
  "specification": "ATL105",
  "version": "2026-3",
  "section": "12.2",
  "segment": "101",
  "element": "158",
  "rule": "license-number"
}
```

Values are compared case-insensitively and with surrounding whitespace removed.

## Section 3 Placement Anchor (shared with Segment 100 canonical anchors)

| Canonical rule | Section | Segment | Element | Meaning |
| --- | --- | --- | --- | --- |
| `section-3-required-for-fleet` | `11.1.1` | `DATA-SECTION-3` | `—` | Fleet-card requests must carry Segment 101 in Data Section 3 |
| `fleet-request-required` | `12.2` | `101` | `—` | Segment 101 is included in all fleet-card transaction requests |
| `mutual-exclusion-enhanced-fleet` | `12.2` | `101,145` | `—` | Segment 101 and Segment 145 must not be sent together |

## Segment 101 Field Anchors

| Canonical rule | Section | Segment | Element | JSON field |
| --- | --- | --- | --- | --- |
| `segment-type` | `12.2` | `101` | `85` | `segmentType` |
| `segment-length` | `12.2` | `101` | `84` | `segmentLength` |
| `odometer` | `12.2` | `101` | `64` | `odometer` |
| `vehicle-number` | `12.2` | `101` | `108` | `vehicleNumber` |
| `job-number` | `12.2` | `101` | `47` | `jobNumber` |
| `driver-identification-number` | `12.2` | `101` | `31` | `driverIdentificationNumber` |
| `fleet-employee-number` | `12.2` | `101` | `40` | `fleetEmployeeNumber` |
| `license-number` | `12.2` | `101` | `158` | `licenseNumber` |
| `job-id` | `12.2` | `101` | `159` | `jobId` |
| `department-number` | `12.2` | `101` | `160` | `departmentNumber` |
| `customer-data` | `12.2` | `101` | `161` | `customerData` |
| `user-id` | `12.2` | `101` | `162` | `userId` |
| `vehicle-id-number` | `12.2` | `101` | `163` | `vehicleIdNumber` |

## Fleet Tag Anchors (Auth Completion 0220 only, when Host Prompts supported)

| Canonical rule | Section | Segment | Element | Meaning |
| --- | --- | --- | --- | --- |
| `fleet-tag` | `12.2` | `101` | `FLEET-TAG-{1..5}` | 3-byte tag code + up to 31-byte data |
| `fleet-tag-code-enumeration` | `12.2` | `101` | `FLEET-TAG-CODE` | Tag code must be one of DLS, DLN, PON, INV, TRP, UNT, TLH, DOB, ZIP, END, MID, VIN, TRA, HUB, TLR, CBA, VHT |
| `fleet-tag-per-code-format` | `12.2` | `101` | `FLEET-TAG-DATA` | Payload conforms to the format declared in the fleet-tag code table |
| `fleet-tags-auth-completion-only` | `12.2` | `101` | `FLEET-TAG-{1..5}` | Fleet Tag fields appear only in Auth Completion (0220) messages |
| `fleet-tags-host-prompts-required` | `12.2` | `101` | `FLEET-TAG-{1..5}` | Fleet Tag fields appear only when Host Prompts are supported |

## Serialization And Eligibility Anchors

| Canonical rule | Section | Segment | Meaning |
| --- | --- | --- | --- |
| `segment-101-field-order` | `12.2` | `101` | Fields remain in specification order (1-13, then 14-18) |
| `segment-length-max-061-base` | `12.2` | `101` | Base Segment 101 max length is 61 alphanumeric characters (see PROVISIONAL P-01 for Auth Completion length reconciliation) |
| `non-trailing-empty-field-separator` | `12.2` | `101` | Empty non-trailing fields retain their separators |
| `trailing-optional-fields-omitted` | `12.2` | `101` | Unneeded trailing optional fields are not transmitted |
| `origin-device` | `12.2` | `101` | Segment 101 originates at the device |
| `segment-occurrence` | `12` | `101` | Exactly one Segment 101 per message |

## Usage Rule

An AI artifact and a Test Validation artifact match semantically only when they resolve to the same canonical anchor and compatible expected behavior. Similar text or similar local IDs are not sufficient evidence of equivalence.

## Cross-Segment Dependency Anchors

Segment 101 depends on Segment 100 being present in the same message. When Segment 101 appears without Segment 100, or with Segment 145, the message is invalid.

| Canonical rule | Section | Segment | Meaning |
| --- | --- | --- | --- |
| `section-3-companion-of-segment-100` | `11.1.1` | `101` | Segment 101 is placed in Data Section 3 and requires Segment 100 |
| `mutual-exclusion-enhanced-fleet` | `12.2` | `101,145` | Segment 101 and Segment 145 must not coexist |

## Source References

- Section 10.6, Fleet Card Processing Requirements: extracted specification lines 5521-5525 (defers to Petroleum Industry Processing Specifications).
- Section 11.1.1, Financial Transaction Request: lines 7519-7627 (Data Section 3 placement, and Segment 101 listed under "Fleet data required").
- Section 12.2, Fleet Data Segment: lines 11396-11500 (complete field layout and fleet-tag code table).
- Cross-references from Section 13, Data Element table: lines 18090-19305 (elements 31, 40, 47, 64, 108, 158-163).
- `docs/specs/kb/13-data-elements.md`: element numbers and field-level rule metadata.
