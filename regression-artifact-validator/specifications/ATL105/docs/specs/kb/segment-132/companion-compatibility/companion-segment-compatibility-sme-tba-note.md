# Segment 132 Companion-Segment Compatibility: SME and TBA Note

## What Is Known

- Segment 132 belongs to the CA Public Key File Load Request, alongside optional companions 101 (Fleet), 102 (Product Code), 104 (Purchase Card), 111 (Variable Information).
- Segment 132's CA Public Key File Checksum (Element 187) relates to the same checksum concept used in Segment 130 (EMV Request) and Segment 131 (EMV Response) — likely used to detect when a device's CA key file needs reloading via this dedicated message type.

## What Not To Assume

- Do not assume Segment 132 can appear in an ordinary Financial Transaction Request or EMV Financial Transaction Request — it is exclusive to the CA Public Key File Load Request.
- Do not assume the multi-block transfer protocol (Block Number) is fully specified — flag as provisional (`SEG132-SME-003`).
