# Segment 132 Serialization and Wire-Format Flow

```text
Segment 132 payload ready to serialize
  |
  v
emit SegmentType FS SegmentLength FS
     SequenceNumber TerminalIdentifier LoadType HardwareVersion
     SoftwareVersion FirmwareVersion CAPublicKeyFileChecksum BlockNumber
  (separator behavior for fields 3-10 is [PROVISIONAL SEG132-SME-004] —
   do not assume delimited or non-delimited without confirmation)

Segment Length is 3 digits, max content length 77 (reconciliation
provisional, SEG132-SME-002).
```
