# Security Test-BR Precedence Review

## Rule Applied

When an AI BR conflicts with a Test BR, the Test BR controls the comparison. The conflicting AI assertion is not credited as a match to the Test BR. This is a comparison precedence rule only; it does not approve a Test BR whose status is `DRAFT_REVIEW_REQUIRED` or replace authorized source/SME review.

## Segment 114 Conflict

Test BR `SEG114-R-001` states that Segment 114 is exclusive to the Loyalty Card Transaction Request and is not documented as a companion in the Financial Transaction Request, ECA/TeleCheck Service Transaction Request, or CA Public Key File Load Request lists. Its source anchor is ATL105 2026-3 sections 11.1.1, 11.2.1, 11.3.1, and 11.9.1. Its Test Solution record is `DRAFT_REVIEW_REQUIRED`; the linked TS, TC, and TD are review placeholders. Scenario applicability, positive/negative cases, and expected outcomes are not yet derived.

Two physically backed AI claims conflict with that scope:

| AI BR | AI statement | AI chain |
|---|---|---|
| `REQ-SRC-ATL105-PDF-001:4485` | Financial Transaction Request includes Segment 114. | `SC-9859` -> `TC-016360`; v6 marks the case physically complete. |
| `REQ-SRC-ATL105-PDF-001:4487` | EMV Financial Transaction Request includes Segment 114. | `SC-9861` -> `TC-016362`; v6 marks the case physically complete. |

Neither has a v3 Test-BR candidate. The anchor candidate generator requires a numeric element anchor; these AI BRs have segment context but no numeric element, so their absence from the candidate crosswalk is not evidence of no relationship.

## Comparison Disposition

- For the explicit Financial Transaction Request conflict (:4485), apply Test-BR precedence: do not treat the AI inclusion claim as satisfying `SEG114-R-001`.
- For the EMV Financial Transaction Request (:4487), apply the same conservative precedence for comparison; whether the Test BR's cited Financial Transaction Request scope includes this EMV variant requires authorized source-context review.
- The Test requirement remains `DRAFT_REVIEW_REQUIRED`; therefore this is not a confirmed business rule or final `NOT_MATCH` decision. Both AI claims and the Test BR remain `OPEN_REVIEW_REQUIRED` for source adjudication.
- No Test fixture execution, host execution, or semantic approval is claimed.

## Evidence

`conflict-register.csv` records both claims, chain IDs, statuses, and the precedence disposition. `manifest.json` hashes the source and output artifacts used for this review.
