# Appendix X Online Refund/Refund Authorization Training

**Specification:** BUYPASS ATL105 2026-3, Appendix X-1
**Field:** Segment 111, POS Additional Data Information (Table ID `32`), Sub-Table `11`
**Applicability:** Participating merchants' Merchandise Return transactions (Transaction Type `7`) and their reversal/follow-up/Timeout Reversal messages.
**Status:** `PARTIALLY_COVERED`; the bounded field, participation-trigger, and lifecycle checks are implemented. Merchant enablement, rollout cohorts, issuer/cardholder delivery, and issuer outcome decisions require external evidence.

## Source requirements captured

Appendix X describes credit voucher transactions (also called purchase returns or Merchandise
Returns) and the Online Refund/Refund Authorization Indicator used to obtain online authorization:

- A participating merchant requesting online authorization sends the indicator in Table `32`,
  Sub-Table `11`; a merchant that does not want online approval must not send it.
- Appendix I-27 specifies Sub-Table ID `11`, fixed length `001`, and valid code `Y`. Appendix X names
  Visa, MasterCard, Amex, and Discover.
- If the indicator appears on the original Merchandise Return, it should also appear on subsequent
  reversals/follow-ups and Timeout Reversals.
- For certain previously notified Visa merchants, the real-time authorization is required before
  the existing Settlement System Refund. The appendix says all other merchants are expected to
  perform Visa online authorization effective April 2020. These are historical rollout statements;
  a merchant's current cohort and configuration are not encoded in ATL105.
- Appendix X directs merchants to their First Data representative to confirm availability.
- The stated purpose is to provide real-time information that a refund is pending to the issuer
  and/or cardholder. Delivery cannot be verified from the indicator field alone.
- Issuer-unreachable and invalid-account declines are described as expected, but the issuer may
  decline for any reason at its discretion. The oracle does not create a closed decline-reason list.

## Oracle boundary

[`AppendixXOnlineRefundOracle`](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixXOnlineRefundOracle.java)
checks the Table 32/Sub-Table 11 location, fixed subtable length, `Y` value for the four named card
brands, the participating-merchant / transaction-type trigger, the no-online-approval non-send rule,
carry-forward when the original indicator was present, and the Visa pre-settlement sequence when
external inputs confirm the requirement applies.

It does not infer program eligibility from card or transaction data, select a merchant's rollout
cohort, verify a notification reached the issuer/cardholder, or constrain issuer decline discretion.
Those assessments remain `REVIEW_REQUIRED` until supported by the relevant merchant, network,
issuer, or channel evidence. Synthetic fixtures are not approved merchant or AI-produced artifacts.

## Test Solution artifacts

- Oracle tests: [`AppendixXOnlineRefundOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixXOnlineRefundOracleTest.java)
- Coverage package: [`appendix-x-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-x-segment-100-coverage.json)
- Appendix I Table 32 field context: [`extracted_text.txt`](../../extracted_text.txt), around lines 28566-28595
- Appendix X source: [`extracted_text.txt`](../../extracted_text.txt), lines 35841-35870
