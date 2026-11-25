Coverage reports of a generated build: coverage/ (copied to --out).

  rule_ledger.csv/.json      every knowledge-base rule -> its final state for this BR: tested_host / tested_device /
                             planned_not_composed / composition_gap / sme / data_element / operations /
                             not_applicable / informational / unclaimed, with the test cases that test it
  theme_coverage.csv/.md     every theme (source document section): state for this BR, host test cases, device /
                             terminal test cases, and a reason for every active theme without a host test case
  technique_coverage.csv     every technique: templates, test cases (host / device), rules claimed, gaps
  dimension_coverage.json    every registry value (goal G1): covered by a host test case, or why not
  realized_coverage.json     planned vs realized (I5): generated tables / response checks / violations that the
                             composed requests actually carry (verified PASS)
  composition_gaps.csv       rules a technique claimed but the composer cannot express (reason class per row)
  sme_queue.csv              rules not generated from: quote not located verbatim / LOW confidence
  unclaimed_rules.csv        testable network rules no technique claimed (must be empty)
  parity_report.md/.csv      every reviewed template (config/transaction_patterns.json) -> reproduced by rule (its
                             cited pages tested with the same transactions) and by shape, or the reason
  theme_duties.csv           every duty (rule x kind / shape x acceptance context): met by which test cases, or why not
  contexts.csv               every acceptance context (industry x attendance): status, bound themes, test cases
  sample_merchants.json      the generated sample merchant of each active context
  payment_completeness.md    card-authorization risk areas matched to this BR's rules (covered / partly / gap / no_rule)
  domain_gap_candidates.csv  checklist areas no KB rule backs (SME questions; never generated)
  coverage_summary.json      the acceptance metrics, met / not met
A rule counts as tested only when a test case sets / breaks / asserts its own item (bm_duties precision); a test case
that only cites it leaves it `cited_only`.
write_shared() writes a rule ledger for the Data Element / Operations BRs built in the same run.
