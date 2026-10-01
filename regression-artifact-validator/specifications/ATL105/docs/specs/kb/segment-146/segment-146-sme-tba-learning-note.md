# Segment 146 Enhanced Fleet Response Segment: SME and TBA Learning Note

Verified against Section 12.32, Element 239, and its embedded sub-segment tables.

## Sub-Segment Table Catalog (Response Side)

| Table ID | Name | Notes |
| --- | --- | --- |
| 001 | Response Flags | Settlement Indicator: CP/DB/DF(reserved)/RC(reserved)/RF(reserved)/FN |
| 002 | Non-Fuel Product Limits | `\|`-delimited; **NOT present in Comdata responses** (`SEG146-R-003`) |
| 003 | Fuel Product Limits | Uses Appendix F product codes (`[PROVISIONAL SEG146-SME-003]`) |
| 004 | Prompt Formats | Rich edit-mask grammar (`?`,`A/B`,`N`,`I`,`O`,`Z`,`T`,`Mn`,`Xn`,`Vn`,`Pn`) |
| 005 | Customer Information | Fixed-width: name(25)/city(15)/state(2)/account code(rest) |
| 010 | Additional Response Data | `\|`-delimited: FNAM/LNAM/ATHN/ACCT/DMSG |

## The Edit-Mask Grammar (Table 004)

A genuinely rich validation language: `?` (re-prompt with prior format), `A`/`B` (alphanumeric — synonyms since 2015-01-02), `N` (numeric only), `I` (free format), `O` (optional), `Z` (capture but don't resend), `T` (type: N=Number/S=String), `Mn`/`Xn` (min/max, meaning depends on `T`), `Vn` (exact match), `Pn` (pattern: `@`=alpha, `#`=numeric, `*`=alphanumeric, literal chars=required). `[PROVISIONAL SEG146-SME-002]` — full grammar parsing is a nontrivial mini-language; scope confirmation needed before committing to full parser-level validation.

## Source References

Section 12.32: lines 15648-15932. [Rule Catalog](coverage/segment-146-rule-catalog.json).
