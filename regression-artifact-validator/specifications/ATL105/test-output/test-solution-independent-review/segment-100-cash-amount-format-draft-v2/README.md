# Segment 100 Cash Amount: Automated Artifact Review v2

**Status:** The 30 linked AI candidates are automatically inspected against the source-local Element 17 scalar domain. No semantic match is approved, and no coverage is credited.

Element 17 is numeric, has a maximum of eight bytes, and has source-listed values 1–99999999. The audit runner validates those exact source anchors and checks each candidate's actual `Standard Segment.CashAmount`, matching sidecar field, case identity, and duplicate-copy hashes. It records only paths, counts, statuses and SHA-256 values; raw payment values are not written to the report.

Run from `regression-artifact-validator`:

```powershell
python scripts/review-segment-100-cash-amount.py --self-test
python scripts/review-segment-100-cash-amount.py
```

The 30 linked cases yield 7 width violations, 7 nonnumeric values, 7 empty values with presence unresolved, and 7 eight-digit zero values below the source minimum. All 28 single-request cases have an exact Cash Amount observation. Two lifecycle cases lack a safely bound Standard Segment Cash Amount per request leg. All payloads parse; sidecars match case IDs and field values; both 10-Oct candidate trees are byte-identical. Eight approved payload copies remain absent from the frozen inventory.

These are automated artifact and source-domain checks only. Producer `expectedResponse` values are not used as oracles, field presence is not inferred, and lifecycle agreement is not inferred from case-level metadata. The 5-Oct lifecycle cases remain separate from the 10-Oct candidate cohort. Test-owner decisions and independent expected outcomes are still required; execution certification is false and coverage credit remains zero.