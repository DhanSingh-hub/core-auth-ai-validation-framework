# Segment DL5 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Business intent: schedule a software load over IP] --> B[Structured DL5 JSON]
    B --> C{Version 8, Record ID 13, IP/URL 30, MMDDYY, HHMM, F/P?}
    C -->|No| X1[FAIL: logical test-data defect]
    C -->|Yes| D["Emit '$' Version RecordID IPURL Date Time LoadType '~'"]
    D --> E{Length 64?}
    E -->|No| X2[FAIL or REVIEW SEGDL5-R-002]
    E -->|Yes| F["Software Load Response: ')' DL4 then DL5"]
```

Synthetic example, 64 characters (address letters/digits only, space-padded — padding assumed, `SEGDL5-SME-003`):

```text
$APP02.10STR0000000123DMSHOST01                     1015260200F~
```
