# Segment 100 Canonical Source Anchors

Verified against ATL105 release 2026-3, Appendix A, sections 11.1.1 and 12.1, and the Segment 100 field rules in `src/main/resources/atl105/spec-elements.json`.

These anchors are the shared semantic identity for independently generated business requirements, scenarios, test cases, and test data. Producer-local IDs may differ; the anchor vocabulary must remain stable.

## Anchor Format

```text
ATL105 | version | section | segment | element | rule
```

Use the same values in the canonical artifact contract:

```json
{
  "specification": "ATL105",
  "version": "2026-3",
  "section": "12.1",
  "segment": "100",
  "element": "102",
  "rule": "terminal-identifier"
}
```

Values are compared case-insensitively and with surrounding whitespace removed.

## Transport And Section Anchors

| Canonical rule | Section | Segment | Element | Meaning |
| --- | --- | --- | --- | --- |
| `message-length` | `Appendix A` | `TCP-IP-HEADER` | `message-length` | Two-byte network-order length of all following message data |
| `tpdu-protocol-id` | `Appendix A` | `TCP-IP-HEADER` | `protocol-id` | Fixed value `0x60` |
| `tpdu-destination-address` | `Appendix A` | `TCP-IP-HEADER` | `destination-address` | Reserved address bytes are zero when sending to BUYPASS |
| `tpdu-source-address` | `Appendix A` | `TCP-IP-HEADER` | `source-address` | Reserved address bytes are zero when sending to BUYPASS |
| `message-format-version-identifier` | `11.1.1` | `DATA-SECTION-1` | `55` | Value must be `ATL105` |
| `number-of-segments` | `11.1.1` | `DATA-SECTION-1` | `63` | Declared count equals serialized segment count |
| `section-1-field-separators` | `11.1.1` | `DATA-SECTION-1` | `55-63` | Separators occur between Element 55 and 63 and after Element 63 |

## Segment 100 Anchors

| Canonical rule | Section | Segment | Element | JSON field |
| --- | --- | --- | --- | --- |
| `segment-type` | `12.1` | `100` | `85` | `segmentType` |
| `segment-length` | `12.1` | `100` | `84` | `segmentLength` |
| `information-byte` | `12.1` | `100` | `44` | `informationByte` |
| `terminal-identifier` | `12.1` | `100` | `102` | `terminalIdentifier` |
| `prompt-code` | `12.1` | `100` | `78` | `promptCode` |
| `account-number` | `12.1` | `100` | `2` | `accountNumber` |
| `card-discretionary-block-data` | `12.1` | `100` | `12` | `cardDiscretionaryBlockData` |
| `encrypted-pin-block-data` | `12.1` | `100` | `33` | `encryptedPinBlockData` |
| `pump-lane-number` | `12.1` | `100` | `79` | `pumpLaneNumber` |
| `fuel-purchase-amount` | `12.1` | `100` | `41` | `fuelPurchaseAmount` |
| `nonfuel-amount` | `12.1` | `100` | `58` | `nonfuelAmount` |
| `tax-amount` | `12.1` | `100` | `99` | `taxAmount` |
| `cash-amount` | `12.1` | `100` | `17` | `cashAmount` |
| `sequence-number` | `12.1` | `100` | `86` | `sequenceNumber` |
| `approval-number` | `12.1` | `100` | `5` | `approvalNumber` |
| `local-date-time` | `12.1` | `100` | `21` | `localDateTime` |
| `partial-approval-indicator` | `12.1` | `100` | `121` | `partialApprovalIndicator` |

## Serialization And Eligibility Anchors

These rules do not represent a single data element, so they use a named rule value instead of a numeric element.

| Canonical rule | Section | Segment | Meaning |
| --- | --- | --- | --- |
| `segment-100-required-once` | `12.1` | `100` | Segment 100 appears exactly once in an in-scope request |
| `segment-100-field-order` | `12.1` | `100` | The 17 fields remain in specification order |
| `non-trailing-empty-field-separator` | `12.1` | `100` | Empty non-trailing fields retain their separators |
| `trailing-optional-fields-omitted` | `12.1` | `100` | Unneeded trailing optional fields are not transmitted |
| `section-3-required-for-emv` | `11.8.1` | `DATA-SECTION-3` | EMV requests require Segment 130 |
| `section-3-required-for-additional-data` | `11.1.1` | `DATA-SECTION-3` | NFC, fleet, product, EBT, purchase-card, variable-info, and Moneris flows require their defined segment |

## Usage Rule

An AI artifact and a Test Validation artifact match semantically only when they resolve to the same canonical anchor and compatible expected behavior. Similar text or similar local IDs are not sufficient evidence of equivalence.

## Source References

- Appendix A, TCP/IP Message Header: extracted specification lines 25506-25541.
- Section 11.1.1, Financial Transaction Request: lines 7519-7627.
- Section 11.8.1, EMV Financial Transaction Request: lines 9949-10138.
- Section 12.1, Standard Message Data Segment: lines 11209-11224.
- `src/main/resources/atl105/spec-elements.json`: element numbers and field-level rule metadata.