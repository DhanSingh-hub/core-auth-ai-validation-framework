# SME questions: BUYPASS ATL105 MasterCard Credit

Ordered by impact: spec conflicts and gaps that affect the most test cases come first. Each question cites the
template ids in `src/pipeline/config/transaction_testcases.json` (`open_items`) or the pair question id in
`requirements_scenarios/open_pair_questions.csv`.

## Major

1. **What approval response code applies to each follow-on step?** ATL105 states full-approval codes only for
   Purchase `0` and Authorization Only `2` (p.404). Purchase Reversal `8`, Time-out Reversal `Z`, Cancellation
   `S`, Void of a Merchandise Return `U` and Mail/Phone Reversal `C` steps are FLAG until answered. This affects
   about 140 test cases.
2. **Time-out Reversal amount after a partial approval** (T4-PUR-TOR, 7 test cases).
   - p.84 says the requested amount; p.89 (FSA/HRA) says the approved amount. Composed with the requested amount.
   - How does the POS know the original was partial if it did not recognise the response (p.45)?
3. **Mail/Phone Authorization → Completion: which Transaction Type?** ATL105 has no completion code for a
   Mail/Phone authorization (`0` = POS preauthorized completion, `6` = Mail/Phone Purchase). T5-MOA-COMPLETION:
   2 test cases blocked.
4. **What host response / decline code applies to each business-rule decline (T10)?** Only the unmatched TOR
   decline is stated (p.40).
   - Authorization released with a TOR (p.47).
   - Authorization reversed with `8` instead of `S` (p.85).
   - Cancellation with a non-matching Sequence Number (p.85).
   - Reversal of the requested amount after a partial approval (p.84).
   - Cashback on MasterCard credit (p.160): decline, or cashback ignored?
   - Partial requested on a CAT purchase or a Mail/Phone purchase (p.84).
5. **Unconfirmed pairs** (32 test cases, `pending_sme`; `open_pair_questions.csv`):
   - Q-program-entry_mode-CREDIT-CONTACTLESS_MOBILE: does Credit support Contactless Mobile Commerce Device
     (`82`)? It is asked separately for Purchase, Authorization Only, Return and Balance Inquiry.
   - Credential on File with Mail/Phone Purchase, Mail/Phone Authorization Only, Merchandise Return and Balance
     Inquiry.
6. **Settlement** (T8, 7 test cases).
   - Do the totals bucket rules (p.89, stated only for FSA/HRA) apply to all credit?
   - Is the test merchant variable-cut (needed for `222222`, p.78)?
   - What is the test-environment cut time (auto-close, p.79)?

## Secondary

7. Pre-authorization → Completion for a different final amount: may the completion exceed the authorization, and
   with what tolerance (p.545)? Composed lower. (T5-AUT-COMPLETION-DIFF)
8. Completion timed out → TOR → resubmitted completion: new or original Sequence Number? Composed new (p.40).
   (T6-AUT-COMPLETION-TOR)
9. Cancellation after a partial authorization: approved or requested amount? Composed approved, by analogy with
   the p.84 reversal rule. (T4-AUT-CANCEL)
10. AVS mismatch on a Mail/Phone purchase: Mail/Phone Reversal `C` (composed) or Purchase Reversal `8` (p.86
    wording)? (T9-MOP-AVS-REVERSE)
11. Approval code for a $0.00 Account Verification `A` that stores a credential (p.713). (T7-ZERO-MIT)
12. Balance Inquiry `E` on MasterCard credit: is a balance returned, and what is the approval code? (T1-BAL)
13. Is FSA/HRA in MasterCard Credit scope, and which BIN is eligible (p.87)? (T9-FSA, T9-FSA-SPLIT)

## Device behaviour

14. Keyed entry before 3 swipes: p.81 says "should". Is a merchant-initiated keyed entry before 3 swipes
    non-compliant? (T11-KEYED-BEFORE-3)
15. How should expiry dates after `1249` (e.g. `0150`) be handled? p.81 defines only up to December 2049.
    (T11-EXPIRED)

## Client inputs (not SME decisions)

- **Test cards:**
  - partial-approval card with a known balance, plus a second card (split tender);
  - AVS-mismatch card and address;
  - expired EMV card;
  - card with a known online PIN and the test DUKPT key;
  - FSA/HRA card.
- **Real merchant list** to replace the `M01` placeholder.
- **Test harness** able to withhold, delay or corrupt a BUYPASS response (T6 time-out tests).
