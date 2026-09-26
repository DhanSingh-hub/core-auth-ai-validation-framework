# Semantic BR Review Queue

Every AI-to-Test mapping remains pending until source-backed semantic review. Pending items do not count as coverage and do not create Test Solution BRs.

| Mapping status | Review items |
|---|---:|
| Exact candidate | 522 |
| Composite or ambiguous | 226 |
| Missing Test Rule candidate | 5725 |

Allowed decisions: `CONFIRMED_MATCH`, `DUPLICATE`, `NEW_RULE`, `REJECT`, or `REVIEW_REQUIRED`.
