# Segment 132 End-to-End Flow

## 1. Message-Family Decision

```text
Device needs to (re)load its CA Public Key File
  |
  v
Assemble CA Public Key File Load Request:
  Data Section 2: Segment 100 (Standard Message Data Segment)
  Data Section 3: Segment 132 (CA Public Key File Segment) [PROVISIONAL
                  SEG132-SME-001 on Required vs Conditional]
                  + optionally 101/102/104/111
```

## 2. Segment 132 Field Assembly

```text
SegmentType = 132
SegmentLength = 3-digit encoded length (max 77, provisional reconciliation SEG132-SME-002)
SequenceNumber = merchant-assigned; range 100000-199999 ONLY under
                 Multithreaded Dial Protocol Communications header
TerminalIdentifier = device identity; for EMV, first 2 chars forced to "+*"
LoadType = "K" (fixed — Public Key information requested)
HardwareVersion / SoftwareVersion / FirmwareVersion = device version identifiers
CAPublicKeyFileChecksum = checksum currently in use at host and device
BlockNumber = specific data block being requested/sent (multi-block transfer,
              protocol details provisional — SEG132-SME-003)
```

## 3. Validator Decision Flow (`Segment132PayloadValidator`, planned)

```text
payload
  |
  v
"CA Public Key File Load Request" object present? --no--> error
  |
  v
"CA Public Key File Segment" (132) present? --no--> REVIEW_REQUIRED pending SEG132-SME-001
  |
  v
SegmentType == "132"? --no--> SEG132-R-002
SegmentLength matches ^[0-9]{3}$ and within 01-77? --no--> SEG132-R-003 / SEG132-R-004 [PROVISIONAL P-02]
LoadType == "K"? --no--> SEG132-R-007
TerminalIdentifier's first 2 chars == "+*" when EMV context? --no--> SEG132-R-006
  |
  v
ValidationResult (errors list; empty means PASS)
```
