# Appendix X — Online Refund / Refund Authorization

**Specification:** BUYPASS Platform ATL105, Release 2026-3  
**Context:** Merchandise Return / Credit Voucher; Table 032, Sub-Table 11

Appendix X describes the Online Refund / Refund Authorization Indicator. It is used by participating merchants that request online authorization for Merchandise Return transactions. The indicator has value `Y`, length `001`, and is carried in Table 032, Sub-Table 11.

## Validation boundaries

- A participating merchant requesting online approval sends the indicator; a merchant that does not request online approval must not send it.
- When the indicator is present on the original Merchandise Return, it is carried on related reversals, follow-up transactions, and Timeout Reversals.
- Visa, MasterCard, American Express, and Discover are named in the source. Merchant participation and current network enablement require external confirmation.
- For Visa merchants subject to the online authorization requirement, authorization precedes the existing Settlement System Refund. Current merchant configuration must be supplied as evidence.
- Issuer declines are not limited to the examples in the appendix.

The oracle checks only supplied logical fields and context. It does not establish merchant participation, issuer response, settlement completion, or host acceptance.
