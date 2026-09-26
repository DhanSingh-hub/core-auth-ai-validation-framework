# Segment 103 Applicability: SME/TBA Learning Note

## Purpose

Segment 103 is conditional. The presence of an EBT or WIC card alone does not decide whether the segment is required. The transaction role and the EBT-specific data carried by the message decide applicability.

```text
transaction role
  -> EBT/eWIC data required?
  -> Segment 103 applicability
  -> required fields
  -> lifecycle and receipt obligations
```

## Spec-Grounded Applicability Matrix

| Transaction role | Segment 103 status | Required evidence |
| --- | --- | --- |
| Food Stamp Purchase | Optional | EBT-specific data, Clerk ID, or program data when supplied |
| Food Stamp Reversal/Void | Optional | Data needed to represent the reversal |
| Food Stamp Return | Optional | EBT-specific data when the flow supplies it |
| Cash Benefit Purchase | Optional | Cash-benefit-specific data when supplied |
| Cash Benefit Reversal/Void | Optional | Data needed to represent the reversal |
| Food Stamp Balance Inquiry | Optional | Balance or program data when supplied |
| Food Stamp Electronic Voucher | Required | Voucher ID; Section 10.5.2.3 requires the voucher number to appear |
| Cash Benefit Purchase with Cash Back | Optional | Cash-back/EBT data when supplied |
| Cash Benefit Balance Inquiry | Optional | Balance data when supplied |
| Time-out Reversal | Optional | Preserve the original transaction's Segment 103 context |
| Food Stamp Void of Merchandise Return | Optional | Data needed to represent the void |
| eWIC Authorization / Balance Inquiry | Optional on request; response may carry WIC Product Data | EF/EA response data when returned |
| eWIC Authorization Cancellation | Optional | Cancellation-specific EBT/eWIC data when supplied |
| eWIC Purchase Completion | Required | WIC Discount Amount and/or WIC Product Data |
| eWIC Purchase Reversal/Void | Optional | Reversal data when supplied |
| eWIC Voucher Clear | Required | Voucher and WIC data; Prompt Code `0086` |
| eWIC Return | Prohibited | ATL105 Section 10.5.5.1 does not support eWIC Return |

## TBA Rule Pattern

```text
BR: Food Stamp Electronic Voucher requires Segment 103 and Voucher ID.
TS: Submit an electronic voucher with and without Segment 103.
TC: Omit Segment 103 or Voucher ID.
TD: Prompt/transaction role = electronic voucher; expected result = FAIL.
```

## Review Checklist

- Is the lifecycle role explicit?
- Is Segment 103 required, optional, or prohibited for that role?
- Does the payload include the field that makes the segment necessary?
- Is an eWIC response being confused with an eWIC request?
- Is eWIC Return rejected rather than treated as an ordinary return?
- Does the scenario preserve Segment 100 as the required Section 2 sibling?

## Implementation

`Segment103ApplicabilityValidator` implements `SEG103-R-002` and `SEG103-R-024`. The authoritative matrix is also recorded in `README.md` and `segment-103-rule-catalog.json`.
