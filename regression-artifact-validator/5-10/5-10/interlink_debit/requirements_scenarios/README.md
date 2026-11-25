# interlink-debit - Business Requirements and Scenarios

Built by the business model layer (`src/pipeline/business_model/`), separate from the
requirement/scenario pipeline (Step 5 / `scenarios/generator.py`). A **Business Requirement (BR)**
is the filter selection; every spec-evaluated combination under it is a **Scenario**.
Dimension mapping status: **candidate**.

Regenerate:

```
python src/pipeline/business_model/cli.py --build --filter-name interlink-debit --platform BUYPASS --spec ATL105 --network INTERLINK --program DEBIT --allow-unapproved
```

| File | Content |
|---|---|
| [BUSINESS_REQUIREMENTS.md](BUSINESS_REQUIREMENTS.md) | BRs, their scenarios (BRn_TSm), multi-leg flows, SME items, excluded combinations, spec citations |
| `business_requirements.json` | BR records (selection, counts, scenario ids, excluded combinations, citations) |
| `business_scenarios.json` | Scenario records (dimensions, status, spec codes, legs/flows, pair evidence, gaps, supporting rule ids) |
| `business_scenarios.csv` | One row per scenario, with `sme_decision` / `sme_comment` columns |
| `excluded_combinations.csv` | Combinations the spec rules out, with the blocking pair and quote |
| `scenario_supporting_rules.json` / `.csv` | KB rules linked to the scenarios (acceptance criteria, not new requirements) |
| `validation_report.json` | Self-check findings (empty = valid) |
