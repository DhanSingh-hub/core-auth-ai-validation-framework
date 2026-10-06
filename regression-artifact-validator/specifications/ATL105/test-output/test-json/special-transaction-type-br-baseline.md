# Special Transaction-Type BR Draft Baseline

This is independent Test Solution knowledge derived from ATL105 Appendix G and specialized message-flow sections. It is not certified coverage. Every record remains DRAFT_REVIEW_REQUIRED until BR->TS->TC->Test Data JSON chains and validation evidence exist.

AI artifacts are excluded. Standard financial codes 0, 3, 4, 5, 6, 7, 8, A, B, C, S, U, Z remain in the separate Segment 100 code-flow package.

| Special code | Draft BR candidates | Evidence note |
|---|---:|---|
|9|13|Appendix G plus Appendix E special-prompt operation table|
|D|1|Appendix G description only; request/response details unresolved|
|E|2|Appendix G, 10.5.1.6, and stored-value 10.7.3.11|
|K|1|Stored-value operation detail in 10.7.3|
|L|2|Stored-value operation detail in 10.7.3|
|M|2|Stored-value operation detail in 10.7.3|
|N|1|Stored-value operation detail in 10.7.3|
|Q|1|Stored-value operation detail in 10.7.3|
|T|1|Appendix G only; detailed TransArmor document is external|
|V|10|Loyalty operation details in 10.9.3|

## Training Technique

1. Classify the code/message family before constructing any payload; special Prompt Codes must not be mistaken for financial Prompt Codes.
2. Split operation lists into one BR per independently testable behavior, preserving a shared source anchor where appropriate.
3. Record exact source sections and mark Appendix-G-only or external-spec claims as review-required; do not infer request/response behavior from code names.
4. For every BR derive positive, negative, boundary, conditional, and lifecycle tests as applicable, then create independent synthetic Test Data JSON.
5. Validate the full BR->TS->TC->TD graph and keep AI fixtures outside the Test Solution oracle.
