# Full-Corpus Review Batch 002: DL3 Date and Time Fields

**Preliminary disposition:** Three AI format/length claims align with ATL105, but none has linked cases and the matching chains are incomplete.

The refreshed 1,343-record Test model contains two direct DL3 counterparts: `SEGDL3-R-002` for required Host-sourced date/time fields and `SEGDL3-R-005` for their formats. Both Test chains contain only a symbolic scenario and placeholder case/data. Each AI BR has one linked scenario and zero cases.

ATL105 defines Current Date as fixed six-digit MMDDYY, Current Time as fixed four-digit HHMM, and Cut Time as fixed four-digit HHMM. These fields originate at BUYPASS; Current Time includes time-zone and daylight-saving adjustments. The listed hour/minute ranges (`01-24` and `01-60`) are retained literally and not normalized. Cut Time identifies merchant end-of-day settlement time; its automatic-cut-time rule says settlement should start 30 minutes before Cut Time if the clerk has not completed settlement. That advisory lifecycle rule is separate from the format claim.

All three AI BRs have a linked scenario but zero cases. The direct Test counterparts remain symbolic placeholder chains; no date/time payloads or independent outcome oracles are linked.

See the [detailed review record](dl3-current-date-time-format-review.json) and [batch manifest](manifest.json). The official Test baseline and current Test package are unchanged; semantic status remains unassessed and coverage credit is zero.
