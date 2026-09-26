# Segment 123 NFC Payment Tokenization Data Segment: SME and TBA Learning Note

Verified against Section 12.19, Elements 84, 85, 195, 196, 197, 202, 203, 204, 237, 238.

## What Segment 123 Means

Carries NFC/tokenization cryptogram data (CAVV, Token Requestor ID, Token PAN Suffix, Cryptogram Token Data, SafeKey data/response, TAVV cryptogram/result) needed to process tokenized transactions. Device-originated, fully Field-Separator-delimited, max 186 characters. Required whenever a transaction involves tokenized data, MasterCard Token/DSRP, or Visa TAVV data.

## Field Layout

Segment Type(85,3, fixed `123`), Segment Length(84,3, includes Segment Type length + separators), CAVV Revised Format(195,20,O), Token Requestor ID(196,11,O), Token PAN Suffix(197,4,C, Issuer/Authorizer-sourced — returned only if supplied), Cryptogram Token Data(202,28-or-56,O), SafeKey Data(203,58,O), SafeKey Response(204,1,O, Authorizer-sourced), TAVV Cryptogram(237,28,O, base64, request-only), TAVV Result Code(238,1,O, Authorizer-sourced).

## Cross-Segment Interaction (TAVV vs UCAF)

When a request carries both a MasterCard DSRP cryptogram and a SecureCode/Identity Check 3DS AAV, the specification splits them across two segments: AAV goes in Segment 111 Table ID 36 (UCAF), and the Token/DSRP cryptogram goes in Segment 123 Element 237 (TAVV) — they are not interchangeable, and the UCAF Security Level Code must equal `21` (Channel Encrypted) when both are present. Pending SME confirmation on exact scope (`SEG123-SME-001`).

## Source References

Section 12.19: lines 13816-13992. Cross-reference: lines 36013-36039. [Rule Catalog](coverage/segment-123-rule-catalog.json).
