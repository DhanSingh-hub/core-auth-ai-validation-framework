# Genericity dry run (plan v10)

The same code and config planned three selections of the ATL105 spec (catalog build only, no test data). The brand
applicability resolver reads each build's network / program from the dimension mapping, so exclusions flip with the
selection; nothing is configured per network.

| Selection | Scenarios | Templates | Network rules not applicable | ... naming only MasterCard | ... naming only Visa | Card-not-present entry modes |
|---|---:|---:|---:|---:|---:|---:|
| MasterCard Credit | 102 (+ MCC context) | 383 | 115 | 0 | 11 | 1 |
| Visa Credit | 95 | 401 | 117 | 14 | 0 | 1 |
| Interlink Debit | 60 | 315 | 135 | 19 | 12 | 1 |

Most frequent other-brand exclusions in every selection: WEX (66), Voyager (10) - petroleum fleet brands.

Synthetic proof: `tests/test_v10_fixes.py` runs the resolver, duplicate merge, unique names, use-case names and the
time-window choice on a made-up spec (its own networks, programs, categories); `tests/test_no_hardcoding.py`
guards the new modules against any KB network label or dimension value.
