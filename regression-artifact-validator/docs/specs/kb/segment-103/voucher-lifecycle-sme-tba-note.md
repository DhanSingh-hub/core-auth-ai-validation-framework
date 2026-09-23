# Segment 103 Voucher and EBT Lifecycle: SME/TBA Learning Note

## Core Idea

Voucher data is a transaction-lifecycle concern, not just a numeric field check.

```text
local approval / outage
  -> voucher number and approval context
  -> Food Stamp Electronic Voucher or eWIC Voucher Clear
  -> Segment 103 Voucher ID and WIC data
  -> host submission and receipt obligations
```

## Food Stamp Electronic Voucher

ATL105 Section 10.5.2 requires the device to support manual entry of the approval number and voucher number. Section 10.5.4.3 requires the manually entered Voucher Number to appear on the receipt for Food Stamp Electronic Voucher transactions.

The Segment 103 consequence is:

- applicability is `REQUIRED` for the electronic-voucher role;
- Element 109 Voucher ID must be present and numeric, up to 10 digits;
- the scenario must distinguish a local approval from an ordinary online purchase;
- receipt and settlement evidence must preserve the voucher identity.

## eWIC Voucher Clear

An eWIC Voucher Clear submits a previously authorized paper voucher after a device, BUYPASS, or eWIC host outage. Prompt Code `0086` is used. Segment 103 carries applicable Voucher ID, WIC Discount Amount, WIC Product Data, and EBT Program Data.

## TBA Artifact Pattern

```text
BR: Voucher ID identifies the preprinted/local-approval voucher.
TS: Submit an electronic voucher and later clear it.
TC: Reject missing, nonnumeric, or overlength Voucher ID.
TD: Voucher ID = 0000011223; invalid variants = ABC, 11 digits, missing.
```

## Do Not Confuse

- Food Stamp Electronic Voucher is an EBT transaction type.
- eWIC Voucher Clear is an eWIC lifecycle role and is optional as a transaction in the spec, but when that role is chosen its Segment 103 voucher/WIC data is required by this training baseline.
- eWIC Return is not supported and must not be modeled as Voucher Clear.

## Current Coverage

`SEG103-R-010`, `SEG103-R-024`, and the lifecycle fixtures under `test-input/ai-solution/test-data/segment-103/lifecycle/` cover the structural and applicability boundary. Full host reconciliation remains a downstream integration concern.
