# Site Configuration and Fuel Volume (Prompt Codes 903/905): SME/TBA Learning Note

## Core Idea

Unlike 901/902/904 (host-to-device pushes), Prompt Codes 903 and 905 are the two **device-to-host** pushes in Segment 118 — the site reports data upward instead of receiving it.

```text
Prompt Code 903 (Site Configuration Data):
  Configuration change occurs at the site
    -> device accumulates the change
    -> at end of day, device sends the FINAL configuration only
       (if multiple changes occurred during the day, only the last matters)
    -> if no changes occurred, nothing is sent

Prompt Code 905 (Fuel Volume Data):
  Device sends fuel volume tables to the host at end of day
```

## Source-Confirmed Facts

| Field | Prompt Code | Element | Type / Length | Direction | Source |
|---|---|---|---|---|---|
| Site Configuration Data | 903 | 180 | AN, max 3,600 | Request | Vendor |
| Fuel Volume Data | 905 | 201 | AN, max 3,600 | Request | Vendor |

Both are single-field payloads (field 14 only), each with its own Field Separator following field 14 — unlike the response-only payloads (901/902/904), which have no field separators at all.

**Response codes for Site Configuration Data:** `T` Approved, no more data pending; `U` Declined, no more data pending.

## SME Questions

1. Are the same `T`/`U` Response Codes used for Fuel Volume Data (905) responses, or does 905 have its own distinct code pair?
2. Both fields are sourced from "Vendor" rather than "Device" — does this imply a different origination path (e.g., a site-controller/back-office system feeding the POS) compared to the device-originated core fields (1-13)?
3. Is there a Block Number lifecycle for 903/905 comparable to the one documented for general multi-block loads, given both have a 3,600-byte single-field cap?

## TBA Rule Pattern

```text
BR: A Site Configuration Data request shall be sent only when a configuration change occurred during the business day, and only the final configuration state shall be transmitted.
TS: Device makes 3 configuration changes in one day; only the last is sent at end of day.
TC: Validate that Site Configuration Data content matches the final, not intermediate, configuration state.
TD: Sanitized converter-ready request with synthetic configuration data.
```

## Current Boundary

The validator checks presence, source-field format, and the 3,600-byte maximum for both payloads. It does not enforce the "only the final configuration is sent" business rule, since that requires session-level state the Item 1 validator does not track.
