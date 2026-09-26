# Card Table Load (Prompt Code 902): SME/TBA Learning Note

## Core Idea

Prompt Code 902 refreshes one of six Dynamic Card Table sub-tables at the POS device. It is response-only and multi-message: "In most cases this will be a multiple message exchange where the indicated tables are provided through a series of exchanges between the Site System and the BUYPASS FEP."

```text
Device's Card Table version is stale
  -> Device sends Proprietary Data Load Request, Prompt Code 902
     (declares its current Device Card Table Version, field 8)
  -> Host responds with Card Table Data (field 14) for one table type
     (echoing Card Table Load Version, Card Table Type, Load Control Key)
  -> Device applies the data; if Response Code says "more pending" (H/O),
     device sends the next block request
```

## Source-Confirmed Facts

| Field | Element | Type / Length | Role |
|---|---|---|---|
| Device Card Table Version | 176 | N, 35 | Device's current versions: 7 x 5-digit sub-versions (Master ID, BIN, RULES, RESTRICTIONS, SAF, PROMPT, PRODUCT); a value beginning with `99999` means no card table is used at the location |
| Card Table Load Version | 177 | N, 35 | Host-supplied version being loaded; echoed back by the device in the next block request |
| Card Table Type | 174 | N, 4 | Which sub-table this exchange addresses: `0001` BIN, `0002` RULES, `0003` RESTRICTIONS, `0004` SAF, `0005` PROMPT, `0006` PRODUCT |
| Load Control Key | 178 | AN, 60 | Host-supplied key; echoed back by the device |
| Card Table Data | 175 | AN, max 3,600 | The actual table content; response-only; length determined by Segment Length (field 2) |
| Block Number | 11 | N, 3 (conditional) | Used by the host to sequence multi-block loads |

## SME Questions

1. Is there a maximum number of block exchanges per Card Table Type, or is it purely driven by the 3,600-byte-per-block limit and total table size?
2. What is the retry/resume policy if a block is lost or the device restarts mid-load?
3. Can multiple Card Table Types be loaded in the same day-close session, and if so, in what order?

## TBA Rule Pattern

```text
BR: A Card Table Load response for Card Table Type 0002 (RULES) shall echo the same Card Table Load Version and Load Control Key the device received in the prior block.
TS: Host sends a 3-block RULES table load; device echoes version/key on blocks 2 and 3.
TC: Validate Card Table Type is one of the six documented values and that echoed fields match.
TD: Sanitized converter-ready multi-block request/response set with synthetic table data.
```

## Current Boundary

The validator checks Card Table Type against its six documented values and the core field formats. It does not yet enforce cross-block echo consistency or a specific block-count limit, pending `SEG118-SME-003`.
