# Segment DL7 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Business intent: set terminal language and postal code] --> B[Structured entries]
    B --> C["Entry 001: '001' '003' 'eng'"]
    B --> D["Entry 002: '002' '013' + 13-character postal code"]
    C --> E[Download Data = concatenated entries]
    D --> E
    E --> F["Emit '^' + Segment Length (3 digits) + Download Data"]
    F --> G[No End-of-Data Indicator]
```

Synthetic example (Segment Length counting Download Data only — one of the two readings in `SEGDL7-SME-005`), 32 characters; the postal code `A1B 2C3` is followed by six spaces to fill its 13 characters:

```text
^028001003eng002013A1B 2C3      
```
