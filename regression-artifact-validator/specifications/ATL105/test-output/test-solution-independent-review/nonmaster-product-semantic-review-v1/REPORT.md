# Non-Mastercard Appendix F semantic derivation

**Overall request remains incomplete. This is a fully reviewed Appendix F batch.**

- 380 legacy row-classification assertions adjudicated.
- All 380 detailed source rows match across ATL105 2026-3 and 2026-4,
  including description, code interval, table and original oracle/source anchor.
- Their intervals partition 000-999 without gaps or overlaps.
- 23 retained source units derived line by line: rows,
  headings, overview metadata, five prose obligations and repeated notes.
- Every one of the retained 71 current BRs and 61 Petroleum companion BRs
  has an explicit comparison partition against the classification assertion.
- Classification is not merchant permission, product eligibility, amount
  reconciliation, fuel uniqueness, message validity or host approval.
- Codes 895/896 retain the overview/detailed-table source conflict.
- Reserved/undefined values are not automatically accepted or rejected.
- Code 800 preserves its stated credit/debit scope; it is not debit-only.

## Petroleum counterparts

- Appendix B membership and Appendix F descriptive classification remain
  different authorities, with ATL105-only 541-549 retained.
- Fleet product restrictions, list uniqueness, sum reconciliation and
  chip limits remain additional assertions, not duplicate classifications.
- All 1,000 numeric codes are cross-checked against the pinned Petroleum
  Appendix B inventory; 541-549 are retained as ATL105-only, not silently allowed.
- Petroleum's 810-889 and 897-899 reservations are explicitly declared in
  prose. The historical numeric-row extractor omits these 83 values;
  that omission is not proof of source absence or transaction permission.
- The comparison covers all 61 retained companion BRs, but not every
  remaining legacy BR or unmodeled Petroleum obligation.

## Remaining work

- 946 of the previously unreviewed
  legacy BRs still need exhaustive semantic overlap/applicability review.
- 2913 retained source units still need full derivation.
- Cross-legacy relationships outside these 380 disjoint row assertions remain open.
- Complete Petroleum-to-legacy review beyond this classification block remains open.
- Denominator approval, conditional-message oracles and real execution remain blocked.

## Evidence

- [Row-level adjudications](legacy-adjudications.json)
- [Line-complete source-unit derivations](source-derivation.json)
- [Derived prose obligations](obligations.json)
- [Exhaustive finite counterpart partition](counterpart-partition.json)
- [All-code Petroleum catalogue crosswalk](petroleum-code-crosswalk.json)
- [Retained source conflict](source-conflict.json)
- [Summary](summary.json)
- [Frozen input identities](input-manifest.json)
