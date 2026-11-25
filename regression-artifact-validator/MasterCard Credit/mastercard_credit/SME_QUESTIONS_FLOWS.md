# SME questions: client flows, BUYPASS ATL105 MasterCard Credit

Sources (client input RI-004, `docs/client-inputs/2026-10-29-mastercard-credit-flows/`):
- **Flow doc**: `Transaction Flow Document.docx` (82 flows; Credit Purchase/Capture by entry mode, plus Fleet Purchase).
- **Ideas tab**: `Approved Template and possible test_cases.xlsx`, tab "Possible Test Cases by Entry" (147 ideas, 92 unique).

Rule: a client flow or idea becomes a test case only when ATL105 (or the Petroleum spec, PIPS) states it. Everything
else is listed here. Question ids (`FQ-nn`) are used by `src/pipeline/config/client_flow_vocabulary.json`.
"Generated" means a spec-verified test case pattern exists in `src/pipeline/config/transaction_patterns.json`.

## A. Not in the specs (no test case until answered)

- **FQ-01 Chargeback and chargeback reversal.** Flow doc: all 4 entry modes ("Approved → Settlement → Chargeback →
  Chargeback Reversal"). Ideas tab: Chargeback After Purchase, Chargeback Initiation, Chargeback Lifecycle,
  Chargeback Reversal. ATL105 has no chargeback or dispute transaction (Appendix G, p.545-546). It only mentions the
  MasterCard chargeback protection period (p.545), journal retention for retrieval requests (p.92) and re-presentment
  values in the POS Condition Code (p.695).
  - Are chargebacks in scope? If yes, which system or document defines them?
  - Or should we test only what ATL105 controls: preauthorization vs final authorization (p.545) and journal
    retention (p.92)?
- **FQ-02 EMV offline approval and offline decline.** Flow doc: EMV Contact, EMV Contactless. Ideas tab: Offline
  EMV Approval, Offline EMV Decline. ATL105 refers EMV processing to the First Data EMV Implementation Guide
  (p.159). It defines floor limits and a stand-in indicator per card brand (DL8, p.337) and the CA public key load
  (section 11.9), but no offline decision rules (TAC/IAC, Y1/Z1/Y3/Z3). PIPS has "Z3 - Unable to Go Online, offline
  declined" only for WEX cards (p.102). Blocked by a missing document (section F: EMV Implementation Guide).
  - Please provide the guide's sections on offline approval and decline.
  - How is an offline-approved chip transaction sent to BUYPASS? Is an offline-declined one ever sent?
- **FQ-03 Stand-in decline.** Flow doc: all 4 entry modes. Ideas tab: Stand-In Decline in every column. ATL105 gives
  the limits but not the device action: Stand-in Indicator "1 No Stand-in" and Floor Limit "maximum allowable
  stand-in value" (DL8, p.337, p.495-496), fuel-pump off-line limits (p.61), store-and-forward blocking window (card
  type 173, p.520). When the limit is exceeded or stand-in is not allowed, does the device decline locally, ask for a
  voice authorization, or something else? (The BUYPASS time-out decline 4I/6C, p.549, is generated as an Issuer
  Timeout variant, not as a stand-in decline.) Deep check: the Dynamic Card Table Addendum SAF table sets, per card,
  the maximum number and amount of indoor/outdoor sales "while the device is in SAF mode" (DCT p.33-34; example
  "SF NOT ALLOWED" = all 0). If the test merchant uses Dynamic Card Tables, "SAF limit reached → no further off-line
  approval" becomes testable. Is DCT used, and what does the device do when the limit is reached?
- **FQ-04 Multiple partial refunds and refund exceeding the original.** Flow doc: all 4 entry modes. Ideas tab:
  Multiple Partial Refunds, Refund Exceeding Original Purchase (Amount). PIPS p.29 describes one return of "a portion
  or all" of a previously processed purchase; nothing allows several returns against one purchase or caps the total.
  - May several partial returns be made against one purchase? Is there a cap on the total?
  - What response is expected for a return larger than the original purchase?
- **FQ-05 Fallback to magstripe on EMV Contactless.** Flow doc: EMV Contactless. Entry mode 80 is "used for contact
  EMV fall back to swiped" (p.638) and the fallback rule on p.162 is for contact transactions. Is contactless →
  swipe fallback expected, and with which entry mode? (Contact chip → swipe, entry mode 80, is generated.)
- **FQ-06 Card-status declines: pickup, restricted, lost, stolen.** Ideas tab: Pickup / Restricted Card, Lost/Stolen
  Card (Response), Restricted/Lost/Stolen Card, Pickup Card Response. ATL105 has no MasterCard decline-reason list:
  the Decline Code is "BUYPASS defines this code" (p.379) and Appendix H lists only re-entry, time-out, Visa
  recurring and TransArmor codes (p.547-549). Which decline codes apply, and are test cards available? (A plain
  decline, Response Code 1, p.404, is generated.) "Insufficient funds" appears only for EBT (p.116).
- **FQ-07 Amount tiers.** Ideas tab: High-Value, Low-Value, Large Ticket Purchase. The specs define no business
  amount thresholds, only field lengths. Which amounts and expected outcomes apply? (Only product-code amount limits
  exist: DCT RESTRICTIONS table p.35-36, and fleet limits returned by the WEX, COMDATA and WEX OTR hosts, PIPS
  p.103, p.127, p.139.)
- **FQ-08 Contactless cardholder-verification limits, multiple rapid taps, reader communication failure.** Ideas tab:
  EMV Contactless. Not in ATL105, PIPS or DCT; EMV behaviour is in the EMV Implementation Guide (section F).
- **FQ-09 Chip read retry and card removed early.** Ideas tab: EMV Contact. Not in ATL105. PIPS states "card removed
  before a card removal request → transaction aborted" only for Fuelman/Fleetwide, Voyager and WEX chip cards (p.45,
  p.80, p.104), not for MasterCard. Expected device behaviour? (EMV Implementation Guide, section F.)
- **FQ-10 Invalid PAN entry.** Ideas tab: Manual. ATL105 states a check-digit calculation only for the fuel-pump
  controller (p.62); PIPS states Mod 10 for MasterCard/Visa fleet cards (p.52, p.61), not for credit. Card number
  rules per issuer are in the IIN Reference Manual (ATL105 p.365; section F). Must the attended POS validate the
  keyed credit account number, and how?
- **FQ-11 Invalid expiry date format.** Ideas tab: Manual (Invalid Expiry Date). Expired cards and the `1249` rule
  are generated (p.81). An invalid format (e.g. month 13) is not described. Note p.372: "For all authorizations,
  merchants must send a value even for expired cards." Expected device behaviour?
- **FQ-12 Settlement failure recovery.** Ideas tab: every column. ATL105 defines declined totals response codes (7,
  C, E, M, p.404) but no recovery procedure, except batch upload for restaurant terminals (p.64). The end-of-day
  example compares local totals with host totals and prints OUT OF BALANCE when they differ (p.76, stated as an
  example). What recovery is expected, and is the p.76 comparison required?
- **FQ-13 Cancellation before completion.** Flow doc: Manual (Merchant Cancellation). Ideas tab: Merchant
  Cancellation Before Completion, Customer Cancels Purchase. Cancelling before the request is sent produces no host
  message. Is this a device-only check, and what is expected? (Cancellation after approval, Purchase Reversal p.546,
  and "void declined → return/refund", PIPS p.30, are generated.)

## B. Partly in the specs (the verified part is generated)

- **FQ-14 Stand-in approval details.** Generated: off-line approval → queued TORs → purchase forwarded with Deferred
  Transaction indicator `Y` and a new Sequence Number (p.40, p.43, p.588; full track data p.95).
  - Should the forwarded purchase also carry the device's local Approval Number? With an Approval Number, BUYPASS
    treats the purchase as preauthorized and does not go out for authorization (p.368).
  - Which off-line limits apply to an attended POS? The spec states them only for fuel pumps (p.61), the pump
    controller's Local Approval Response (p.62) and stored pump completions (credit: at most 55 minutes, PIPS p.21).
    The DCT SAF table gives per-card SAF limits (DCT p.33-34) if Dynamic Card Tables are used.
  - Is DL8 (floor limits) configured for the test terminal, with what values? Is card type 173 configured?
  - Does BUYPASS perform MasterCard X-Code stand-in, and what EMV table data comes back (Appendix T, p.701)?
  - EMV Contact / Contactless: what chip data does a deferred EMV purchase carry? (see FQ-02)
- **FQ-15 Duplicate prevention.** Flow doc: EMV Contactless, Magstripe, Manual. Ideas tab: Duplicate Purchase /
  Transaction Prevention, Duplicate Manual Purchase Submission. Generated: late approval → TOR → purchase resubmitted
  with a new Sequence Number (p.40, p.43); a repeat reversal is declined as unmatched (p.40).
  - Does BUYPASS detect a duplicate credit purchase (same card, amount, terminal)? With which decline code?
  - What happens if a device reuses a Sequence Number?
  - Is MasterCard Merchant Advice Code "do not retry" (03/21/22, p.663) part of what the client means? Deep check:
    MAC (Table 030, p.663) is "supported only for MasterCard network"; "Merchant should NOT retry" on 03, 21, 22,
    and 02 / 24-30 mean "try again later" / retry after a period. This is testable now (spec-backed): "Purchase
    declined with MAC 03 → not retried". The duplicate decline (Decline Code 24, p.96) is for debit completions only.
- **FQ-16 Contactless read-failure retry.** Flow doc: EMV Contactless (Read Failure Retry). Ideas tab: Retry After
  Contactless Read Failure. Generated: contactless → contact chip, entry mode 86 (p.638). What retry behaviour is
  expected (re-tap count, prompts, fallback)?
- **FQ-17 Missing security verification.** Ideas tab: Manual. Generated: with card type 174 in the table load, the
  device prompts for the card verification data on keyed inside sales (p.520). What is expected when the value is
  not entered or the card has none? Deep check: the CVV response (Table 004, p.648) defines MasterCard results M
  (valid), N (invalid), P (not processed), U (issuer unregistered); "not on card" (S) is listed for other networks,
  not MasterCard. "Keyed sale with CVC2 → M / wrong CVC2 → N" is testable now (spec-backed).
- **FQ-18 Invalid merchant configuration.** Ideas tab: Magstripe. Generated: card types 166/167/168 in the table
  load disable Return/Refund, Void/Reversal and Preauthorization at the device (p.520). Is this what the client
  means, or a host decline for an unknown merchant or terminal? ("160 Invalid merchant", p.652, is a loyalty
  result code, not a credit decline.)
- **FQ-19 Time-out decline codes.** Generated as an Issuer Timeout variant: Decline Code 4I or 6C "Transaction timed
  out" → resubmit (p.549). The codes are listed for authorizers "AR" and "DIAL"; neither is defined anywhere in the
  specs (Appendix C lists authorizers by number). Do they apply to MasterCard credit?

## C. Scope and applicability

- **FQ-20 Petroleum spec rules for the general credit merchant.** Generated with status SME required:
  - partial refund "a portion or all of a previously processed Purchase", return for a previous business day
    (PIPS p.29);
  - void declined → return/refund (PIPS p.30);
  - MasterCard fleet card at a merchant without Terminal Special Code 034 is sent "as a regular MasterCard
    transaction using Prompt Code 20" (PIPS p.52). Does "Prompt Code 20" mean card type `020`?
- **FQ-21 Contactless Mobile (entry mode 82).** Purchase was removed from the approved template, Authorization Only
  was marked valid, Return and Balance Inquiry are still unconfirmed, and the flow doc lists "Mobile Wallet
  Contactless". The scenarios stay generated (pending SME). Which is intended?
- **FQ-22 Entry-mode-independent coverage.** Where the spec rule does not depend on the entry mode, the flow is
  generated once (on the representative entry mode) and covers the client's flow for every entry mode: settlement and
  reconciliation (p.89), refunds after settlement (PIPS p.29), plain decline (p.404), communication failure (p.40),
  stand-in approval (p.40, p.588), duplicate-safe resubmission (p.40). Is that acceptable?
- **FQ-23 Approved-template status changes.** The approved template marks three unconfirmed scenarios as valid:
  Mail/Phone Purchase – Credential on File, Authorization Only – Contactless Mobile, Mail/Phone Authorization Only –
  Credential on File. Intentional?

## D. Client inputs needed (not SME decisions)

- Test cards: plain decline, partial approval, expired (EMV and magstripe), MasterCard fleet card in the fleet BIN
  ranges (PIPS p.52) with a merchant that does not have Terminal Special Code 034.
- Simulator triggers: withheld / late / unreadable response, line down (four consecutive time-outs, p.40),
  time-out decline 4I/6C (p.549).
- A variable-cut test merchant and the test-environment cut time (p.78-79).
- Terminal table load with card types 166, 167, 168, 173 (and the DL6 window) and 174 (p.520); off-line/forced key
  enabled on the test terminal (p.48).

## E. MasterCard Fleet (separate program; not part of the Credit build)

Flow doc: Fleet Purchase branch (21 flows). Ideas tab: FLEET column (30 ideas). MasterCard Fleet is card type `080`
(Appendix E, p.519); its processing rules are in PIPS chapter 7. Spec-verified fleet flows (fuel and non-fuel
purchase, driver / vehicle / odometer prompts, preauthorization → completion, void, refund, settlement) will be
generated by the Fleet program build. Questions to settle first:

- **FQ-F1** Daily, weekly, monthly, velocity limits and "limit exceeded": not in ATL105 or PIPS chapter 7. PIPS has
  host-returned limits only for WEX (p.103), COMDATA (p.127) and WEX OTR (p.139); DCT has per-card SAF and
  product-code limits (p.33-36). Issuer side? Which decline code?
- **FQ-F2** Restricted merchant: PIPS defines only the card's Product Restriction Code (p.53). DCT RESTRICTIONS can
  block a product code (Max Amt 0, p.36). Issuer-side merchant category rule? Expected response?
- **FQ-F3** Driver ID / vehicle ID failure: Appendix H re-entry codes exist for other fleet authorizers (I2/I3, W2/W3,
  3A, p.548), none for MasterCard Fleet. Which code, and should the device re-prompt?
- **FQ-F4** Chargeback (see FQ-01).
- **FQ-F5** Fleet EMV: MasterCard Enhanced Fleet EMV "no earlier than June 2026" (p.499), and MasterCard is not in
  the EMV fleet card list (p.161). In scope? Fleet EMV prompts (tag 9F6E) are defined in the MasterCard Fleet Card
  specification (PIPS p.44; section F).
- **FQ-F6** Void of Return / Void Refund: PIPS p.51 lists POS Return/Refund and POS Reversal/Void for the MasterCard
  fleet card, but not a void of a return. Is it supported?
- **FQ-F7** Maintenance / non-fuel merchant: PIPS defines only the card's Product Restriction Code ("fuel and other
  products" or "fuel only", p.53). Which merchant setup and product codes should a maintenance purchase use?

## F. Documents the specs refer to that are not in `documents/`

Available: ATL105 2026-4 (and 2026-3), Petroleum Industry Processing Specifications 2025-1, Dynamic Card Table
Addendum 2014-1, ATL105 TransArmor updates 2024.

| Document | Referenced at | Needed for |
|---|---|---|
| First Data EMV Implementation Guide (Direct Platform Specifications Portal; "must be used in conjunction with the ATL105") | ATL105 p.159; PIPS p.14, p.142 | FQ-02, FQ-05, FQ-08, FQ-09, FQ-16, EMV part of FQ-14 |
| BUYPASS Platform Issuer Identification Number (IIN) Reference Manual | ATL105 p.365; TransArmor p.58 | FQ-10, test card ranges |
| BUYPASS Platform Multithreaded Asynchronous Protocol Specifications (Multithreaded Dial Protocol) | ATL105 p.49, p.214, p.274, p.410 | communication failure on dial lines (the p.40 line-down rule is for IP) |
| MasterCard Fleet Card specification (EMV tag 9F6E prompts) | PIPS p.44 | FQ-F5 |
| USPF AFD Chip Fallback Transaction Processing Best Practices | PIPS p.44 | chip fallback at fuel pumps |
| Conexxus PSPC reference document | ATL105 p.666 | Cardholder Additional Information result codes (not in current flows) |
| EMV 4.2 / 4.3 Book 2 (EMVCo) | PIPS p.85, p.110 (WEX/Voyager CDA) | not needed for MasterCard |
| "Any other documents published since" (fleet) | ATL105 p.123 | fleet rules newer than PIPS 2025-1 |

Not referenced by any spec: MasterCard network rules for chargebacks and disputes (FQ-01), card-status decline codes
(FQ-06) and contactless CVM limits (FQ-08).
