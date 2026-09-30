# Segment DL4 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Business intent: schedule a software load by dial] --> B[Structured DL4 JSON]
    B --> C{Version 8, Record ID 13, Phone <= 18, MMDDYY, HHMM, F/P?}
    C -->|No| X1[FAIL: logical test-data defect]
    C -->|Yes| D["Emit '@' Version RecordID Phone Date Time LoadType '~'"]
    D --> E{Length <= 52?}
    E -->|No| X2[FAIL SEGDL4-R-002]
    E -->|Yes| F["Software Load Response: ')' DL4 then DL5"]
```

Synthetic example, 52 characters (phone number space-padded to 18 — padding assumed, `SEGDL4-SME-003`):

```text
@APP02.10STR00000001235555550142        1015260200F~
```
