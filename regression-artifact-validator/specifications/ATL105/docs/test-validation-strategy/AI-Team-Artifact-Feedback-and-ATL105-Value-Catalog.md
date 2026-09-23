# Feedback to AI Solution Team: ATL105 Test Artifacts

## Purpose

The AI output is consumed by the Fiserv Test Team as test input. The Test Team needs to convert JSON test data into an ATL105 wire-format message and execute or inspect that message. The current AI output does not provide all required deliverables in a consumable form.

The following artifacts are required for every supported scope:

1. Business Requirements file.
2. Test Scenarios file.
3. Test Cases file.
4. Separate JSON Test Data file.
5. BR -> TS -> TC -> TD traceability matrix.
6. Versioned test-data configuration file.

## Required Artifact Contract

### 1. Business Requirements

Provide a dedicated file containing one atomic requirement per record.

Required fields:

```text
businessRequirementId
statement
sourceDocument
sourceSection
sourcePage
segmentId
elementId
conditions
validationStatus
```

### 2. Test Scenarios

Provide a dedicated file containing scenarios linked to one or more business requirements.

Required fields:

```text
testScenarioId
businessRequirementIds
scenarioType
transactionType
preconditions
expectedBehavior
```

### 3. Test Cases

Provide a dedicated file containing executable test cases linked to scenarios.

Required fields:

```text
testCaseId
testScenarioId
businessRequirementIds
testCaseType
objective
preconditions
expectedResult
testDataIds
```

### 4. Separate JSON Test Data

Test data must not exist only inside the test-case catalog. Provide a separate JSON file containing one finalized test vector per test-data record.

Each record must include:

```text
testDataId
businessRequirementIds
testScenarioId
testCaseId
transactionType
messageType
segments
expectedResult
expectedResponse
serializationProfile
```

The `segments` object must contain structured ATL105 element names and values. It must be consumable by the Fiserv converter that creates the ATL105 message. The JSON test-data file is a logical input to the converter; it is not itself the wire-format message.

### 5. Traceability Matrix

Provide a separate matrix with at least these columns:

| Business Requirement ID | Test Scenario ID | Test Case ID | Test Data ID | Transaction Type | Expected Result | Source Anchor |
|---|---|---|---|---|---|---|
| BR-... | TS-... | TC-... | TD-... | Sale | Pass/Fail | ATL105 page/section |

Every accepted test case must reference at least one test-data record. Every test-data record must trace back to a business requirement, scenario, and test case. Missing links must be reported rather than silently omitted.

Required chain:

```text
BR -> TS -> TC -> TD -> Fiserv ATL105 converter -> ATL105 message
```

## Test-Data Configuration Requirement

The AI solution must provide a separate, versioned configuration file containing reusable values, allowed values, defaults, generators, and scenario overrides for every supported segment and element.

The configuration file should distinguish:

- Fixed specification values, such as segment type.
- Reusable valid values, such as test terminal IDs and transaction codes.
- Generated values, such as sequence numbers and timestamps.
- Multiple selectable values, such as card types, PANs, CVV/PIN fixtures, and transaction types.
- Negative and boundary overrides, such as invalid length or missing required values.
- Sensitive or binary/hex fixtures, which must be controlled separately and never generated from live credentials.

The final JSON test data should be generated from this configuration and may apply case-specific overrides.

Suggested logical shape:

```json
{
  "configurationVersion": "1.0",
  "specification": "ATL105",
  "segments": {
    "100": {
      "SegmentType": { "fixed": "100" },
      "InformationByte": { "values": ["0"] },
      "TerminalID": { "values": ["HC375003"] },
      "PromptCode.TransactionType": { "values": ["0", "3", "7", "8", "U"] },
      "PromptCode.CardType": { "values": ["001", "011", "020"] },
      "SequenceNumber": { "generator": "numeric", "length": 6 },
      "LocalDateTime": { "generator": "YYMMDDHHmm" },
      "PartialApprovalIndicator": { "values": ["0", "1"] }
    },
    "111": {
      "SegmentType": { "fixed": "111" },
      "VarInfoTable": { "source": "scenarioOverride" }
    },
    "130": {
      "SegmentType": { "fixed": "130" },
      "EMVChipData": { "source": "controlledHexFixture" }
    }
  }
}
```

The exact configuration schema must be agreed with the Fiserv Test Team because the converter owns the final ATL105 serialization rules.

## ATL105 Converter Expectations

The converter must create the actual ATL105 message from the logical JSON values, including:

- Correct control characters and framing.
- Correct field separator characters.
- Correct segment lengths.
- Correct field ordering.
- Correct fixed-width padding and numeric formatting.
- Correct binary and hexadecimal handling.
- Correct TCP/IP header and transport framing where applicable.

The AI Team must provide one demonstrable example showing:

```text
JSON test data -> converter -> ATL105 output
```

The output should be supplied as a raw/binary fixture and as an escaped or hexadecimal diagnostic representation.

## Values Observed in Existing Valid Transaction Samples

Source folder:

```text
C:\Users\F5H46GZ\Downloads\core_auth\core auth-imp\Valid transactions
```

The folder contains 20 JSON samples covering credit-card, debit-card, and fleet flows. The table below is a starting value catalog extracted from those samples. It is not a substitute for the ATL105 specification or the converter contract.

| Segment | Element/path | Observed values | Suggested configuration treatment | Notes |
|---|---|---|---|---|
| 100 | SegmentType | `100` | fixed | Mandatory Segment 100 identifier |
| 100 | SegmentLength | `086`, `092` | calculated | Must be calculated by the converter |
| 100 | InformationByte | `0` | reusable value | Confirm allowed domain |
| 100 | TerminalID | `HC375003` | configurable value | Use approved synthetic/test terminal IDs |
| 100 | PromptCode.TransactionType | `0`, `3`, `7`, `8`, `U` | selectable values | Map each value to transaction meaning |
| 100 | PromptCode.CardType | `001`, `011`, `020` | selectable values | Confirm card-type catalog |
| 100 | TrackData | `5121076312111357:4912`; `5121076312111357:49121360907587` | controlled fixture | Mask or replace with approved test data |
| 100 | PINBlockData | Multiple 32-character hex fixtures observed | controlled hex fixture | Do not generate from live PIN material |
| 100 | PumpNumber | `05` | selectable value | Conditional for fuel/fleet flows |
| 100 | FuelPurchaseAmount | `00004500` | selectable value | Numeric, implied amount format |
| 100 | NonFuelAmount | `00001400`, `00002500` | selectable values | Numeric, implied amount format |
| 100 | TaxAmount | No non-empty value observed | generator/override | Add valid, zero, and boundary cases |
| 100 | CashAmount | No non-empty value observed | generator/override | Add valid, zero, and boundary cases |
| 100 | SequenceNumber | `842262`, `842263`, `842271`, `842280`, `842281`, `842282`, `842283`, `842401` | generator with seed | Must be unique where required |
| 100 | ApprovalNumber | `039038`, `051177` | selectable values | Conditional/lifecycle-specific |
| 100 | LocalDateTime | `2608101430`, `2608101445` | generator | Converter must apply agreed date/time format |
| 100 | PartialApprovalIndicator | `1` | selectable values | Include `0` and `1` if valid per specification |
| 100 | TAPIndicator | `TAP` | conditional value | TAP-only fixture |
| 100 | TAPSequenceNumber | `000321` | generator/value | TAP-only fixture |
| 100 | TAPKeyID | `ABCDE1FGHIJ` | controlled fixture | TAP-only fixture |
| 100 | TAPTokenType | `0201` | selectable value | TAP-only fixture |
| 100 | TAPEdataID | `1` | selectable value | TAP-only fixture |
| 100 | TAPEdata | `5A0851210763121113575F3401019F270180` | controlled hex fixture | TAP-only fixture |
| 111 | SegmentType | `111` | fixed | Variable Information Segment identifier |
| 111 | SegmentLength | `071` | calculated | Must be calculated by the converter |
| 111 | VarInfoTable.TableID | `005`, `030`, `031`, `047` | selectable values | Table/value pairs must remain correlated |
| 111 | VarInfoTable.Value | `051`; `05`; `0000000001`; `000015008DED751C3B497C7`; `000015108DED751C4B1BEAC` | table-specific values | Validate length and allowed domain per table |
| 101 | SegmentType | `101` | fixed | Fleet-only fixture |
| 101 | SegmentLength | `048` | calculated | Must be calculated by the converter |
| 101 | Odometer | `0128345` | selectable/generator | Fleet-only fixture |
| 101 | VehicleNumber | `0042` | selectable/generator | Fleet-only fixture |
| 101 | JobNumber | `9981` | selectable/generator | Fleet-only fixture |
| 101 | DriverNumber | `77812` | selectable/generator | Fleet-only fixture |
| 130 | SegmentType | `130` | fixed | EMV Data Segment identifier |
| 130 | SegmentLength | `203` | calculated | Must be calculated by the converter |
| 130 | CAPublicKeyFileChecksum | `2012091700000100000102171` | controlled fixture | Confirm source and lifecycle rules |
| 130 | EMVCardSequenceNumber | `25` | selectable value | Confirm length/domain |
| 130 | EMVChipDataLength | `147` | calculated | Must match EMVChipData payload |
| 130 | EMVChipData | Controlled hex/TLV fixture from samples | controlled hex fixture | Validate as EMV TLV, not ordinary text |
| 130 | EMVAddlInfo | `001006EMVYES` | controlled fixture | Confirm converter handling |

## Feedback Summary

Please provide the missing deliverables in a versioned package. The current embedded `request` data inside the test-case catalog is not sufficient for Fiserv Test Team execution.

The acceptance flow is:

```text
AI BR file
  -> AI scenario file
  -> AI test-case file
  -> separate JSON test-data file
  -> BR/TS/TC/TD matrix
  -> Fiserv converter
  -> ATL105 message
```

Please also include schemas, configuration-version metadata, source references, expected results, and a sample conversion showing that the generated JSON can be converted into a valid ATL105 message.

## Important Data-Control Note

The values in the source folder are test samples. Before reuse, the Fiserv Test Team must confirm that PAN-like values, track data, PIN blocks, EMV data, terminal IDs, and identifiers are approved synthetic or masked values. The AI Team must not introduce production credentials or unapproved payment data into generated artifacts.
