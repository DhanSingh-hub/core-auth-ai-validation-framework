# ATL105-POS-ENTRY-MODE-SUPPLEMENT-V1

- Source SHA-256: `6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa`
- Atomic BRs: 50
- Scenarios: 61  Cases: 373  Data: 373

## BR families
- CAPABILITY: 7
- CODE_VALUES: 20
- COF: 4
- DISCOVER: 4
- FALLBACK: 2
- FORMAT: 6
- GIFT: 2
- LIFECYCLE: 3
- RFID: 1
- TOKEN: 1

## Coverage
- Every pass-assertable BR has at least one PASS case and at least one FAIL case.
- Network-aware scenarios cover AMEX, VISA, MASTERCARD, DISCOVER, DINERS_CLUB, JCB, STAR_SIGNATURE_DEBIT, DEBIT and EBT.
- Two extra contexts cover gift-program entry modes and direct Appendix J code-table semantics.

## Supersession
- TD-SEG100-APPO-ENTRY-MODE superseded by TD-POSEM-AMEX-TOKEN-R01 with Segment 111/Table 005 payload.
- BR-SEG100-ANN-J decomposed into atomic BR-POSEM-* rules for format, values and lifecycle.
- BR-SEG100-APPO-ENTRY-MODE narrowed to review-only BR-POSEM-045 until token code mapping is source-approved.

## BA/SME questions
- [HIGH] Q-POSEM-001: Should PAN Entry Mode 10 assertions be considered applicable to STAR Signature Debit and generic Debit beyond the explicit Appendix J footnote wording?
- [MEDIUM] Q-POSEM-002: Do you want lifecycle persistence (BR-POSEM-010) to include Reversal with the same 90->02 exception, or keep exception scope strictly to Voids/Completions?
- [MEDIUM] Q-POSEM-003: When lifecycle follows an original with missing/invalid capability digit, should capability persistence remain review-only or be an explicit failure?
- [HIGH] Q-POSEM-004: Appendix J footnote 1 lists credit/debit authorizers but not EBT. Should EBT inherit the same lifecycle persistence and 90->02 exception?
- [MEDIUM] Q-POSEM-005: Capability code 3 is MasterCard mPOS-specific. For other networks/generic groups, should this be FAIL or REVIEW_REQUIRED until network supplements are approved?
- [MEDIUM] Q-POSEM-006: For MasterCard non-mPOS traffic, should capability code 3 be treated as hard fail or conditional review?
- [HIGH] Q-POSEM-007: Appendix O defines token presentment mode conceptually but does not map concrete Table 005 values. Which code(s) are approved for each token presentment channel?
- [LOW] Q-POSEM-008: Appendix J code 04 row combines barcode scan and OCR-for-EMV wording. Should these be split into separate operational constraints?
- [MEDIUM] Q-POSEM-009: When fallback-to-swipe conditions are met but EMV payload still appears, should this be modeled as a separate BR failure or a lifecycle-level conflict?
- [HIGH] Q-POSEM-010: Table 005 is required for all transactions. Please confirm if any host/program exceptions exist that intentionally omit Segment 111.
- [MEDIUM] Q-POSEM-011: Appendix J footnote 2 references subsequent Debit Completions/Reversals after Credit COF. Confirm this applies to STAR Signature Debit in your implementation.
- [MEDIUM] Q-POSEM-012: For Discover exclusions under code 10, do your QR/bar/payment-code and wallet flows include additional sub-conditions not captured in ATL105 text?
