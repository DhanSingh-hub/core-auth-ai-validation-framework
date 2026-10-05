# Combined Appendix G Transaction-Type Mapping Matrix

| Code | Transaction | Scope | Single-step eligible | Multi-step eligible | Lifecycle role | BR | TS | TC | TD refs/drafts | Blocked BRs | Status |
|---|---|---|---:|---:|---|---:|---:|---:|---:|---:|---|
|0|POS Purchase/Capture or Preauthorized Completion|STANDARD_FINANCIAL|true|true|FOLLOW_UP, ORIGINAL|1|1|3|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|3|POS Authorization Only|STANDARD_FINANCIAL|true|true|ORIGINAL|1|1|3|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|4|Customer-activated Purchase/Capture|STANDARD_FINANCIAL|true|false|ORIGINAL|1|1|3|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|5|Customer-activated Authorization Only|STANDARD_FINANCIAL|true|true|ORIGINAL|1|1|3|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|6|Mail/Phone Purchase|STANDARD_FINANCIAL|true|false|ORIGINAL|1|1|3|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|7|Merchandise Return/Refund|STANDARD_FINANCIAL|true|true|ORIGINAL|1|1|3|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|8|Purchase Reversal/Void|STANDARD_FINANCIAL|false|true|FOLLOW_UP|1|1|2|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|A|Account Verification|STANDARD_FINANCIAL|true|false||1|1|2|1|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|B|Mail/Phone Authorization Only|STANDARD_FINANCIAL|true|true|ORIGINAL|1|1|3|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|C|Mail/Phone Reversal/Void|STANDARD_FINANCIAL|false|true|FOLLOW_UP|1|1|2|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|S|Cancellation|STANDARD_FINANCIAL|false|true|FOLLOW_UP|1|1|2|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|U|Void of Merchandise Return|STANDARD_FINANCIAL|false|true|FOLLOW_UP|1|1|2|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|Z|Time-out Reversal|STANDARD_FINANCIAL|false|true|FOLLOW_UP|1|1|2|2|0|STARTER_CHAIN_WITH_AI_INPUT_FIXTURES|
|9|Special Transactions|SPECIAL_NONFINANCIAL|true|false||13|13|13|13|0|DRAFT_CHAIN_REVIEW_REQUIRED|
|D|RBC Loyalty Lookup|SPECIAL_NONFINANCIAL|true|false||1|0|0|0|1|DRAFT_WITH_SOURCE_REVIEW_BLOCKERS|
|E|Balance Inquiry|SPECIAL_NONFINANCIAL|true|false||2|2|2|2|0|DRAFT_CHAIN_REVIEW_REQUIRED|
|K|Activate Stored Value Card|SPECIAL_NONFINANCIAL|true|false||1|1|1|1|0|DRAFT_CHAIN_REVIEW_REQUIRED|
|L|Deactivate Stored Value Card|SPECIAL_NONFINANCIAL|true|false||2|2|2|2|0|DRAFT_CHAIN_REVIEW_REQUIRED|
|M|Balance Merge|SPECIAL_NONFINANCIAL|true|false||2|2|2|2|0|DRAFT_CHAIN_REVIEW_REQUIRED|
|N|Replace Card|SPECIAL_NONFINANCIAL|true|false||1|1|1|1|0|DRAFT_CHAIN_REVIEW_REQUIRED|
|Q|Recharge Card|SPECIAL_NONFINANCIAL|true|false||1|1|1|1|0|DRAFT_CHAIN_REVIEW_REQUIRED|
|T|Token Registration|SPECIAL_NONFINANCIAL|true|false||1|0|0|0|1|DRAFT_WITH_SOURCE_REVIEW_BLOCKERS|
|V|Loyalty Advice Function|SPECIAL_NONFINANCIAL|true|false||10|10|10|10|0|DRAFT_CHAIN_REVIEW_REQUIRED|

Totals: **23 codes**, **47 BRs**, **45 TSs**, **65 TCs**.

Data distinction: 13 AI-input fixture refs; 12 shared lifecycle catalog refs; 32 non-executable special TD stubs; 0 independent executable fixtures. The shared lifecycle catalog contains 9 TD records.

## Single-Step Flow View

Eligible codes (18): 0, 3, 4, 5, 6, 7, A, B, 9, D, E, K, L, M, N, Q, T, V. Counts: 42 BRs, 40 TSs, 48 TCs; independent executable TD fixtures: 0.

## Multi-Step Lifecycle View

Multi-step-eligible codes (10): 0, 3, 5, 7, 8, B, C, S, U, Z. Lifecycle participant codes (12): 0, 3, 4, 5, 6, 7, 8, B, C, S, U, Z. The independent lifecycle catalog has 5 BRs, 5 TSs, 9 TCs, and 9 TD records across 5 flow families.

Per-code lifecycle participants have 12 BRs, 12 TSs, and 17 lifecycle TCs; their references include 5 AI-input fixtures and 12 shared lifecycle catalog links.

The eligibility and participation sets differ: code 4 and 6 occur as lifecycle originals despite not being flagged multi-step eligible.

### Lifecycle patterns

| Pattern | Original code(s) | Follow-up code(s) |
|---|---|---|
|AUTH-COMPLETION|3, 5, B|0|
|PURCHASE-REVERSAL-VOID|0, 4, 6|8, C|
|REFUND-VOID-OF-RETURN|7|U|
|AUTHORIZATION-CANCELLATION|3, 5, B|S|
|TIMEOUT-REVERSAL|0, 3, 4, 5, 6, B|Z|

**Execution ready: false.** AI-owned fixture references and special TD stubs do not count as independent executable coverage.
