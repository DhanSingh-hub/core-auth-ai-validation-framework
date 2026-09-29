# Tester Note: Stale Test Snapshots and Specification Cross-Checks

**Date:** 2026-09-29
**Applies to:** All ATL105 segment training (100 through DL8)
**Handbook:** [ATL105 Segment Training Handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md#lessons-learned), lessons L6-L13

This note explains two test failures that were already on `Develop` and the specification defects found while fixing them. The handbook's Lessons Learned section turns each finding into a rule for other testers.

---

## 1. Two tests were failing before any related change

| Test | Assertion | Reality | Root cause |
|---|---|---|---|
| `RuleCatalogBaselineTest` | `assertThat(catalogs).hasSize(17)` | 49 catalogs, one per `kb/segment-*` folder | The count was hard-coded when 17 segments existed and never updated as segments 101–157 and DL1–DL8 were trained. |
| `Segment111ArtifactComparisonTest.loadsIndependentRuleCatalog` | `catalogId` = `"segment-111-rule-catalog"` | `"ATL105-SEG111-RULE-CATALOG-001"` | The catalog ID was renamed to the repository-wide convention, but this one test kept the old ID. |

Both tests fail on a clean checkout. Neither was caused by the Element 63 or ECA/TeleCheck work done the same day.

### Fixes

- **`RuleCatalogBaselineTest`**: the hard-coded `17` is replaced by an invariant: the set of folders that contain a rule catalog must equal the set of `kb/segment-*` folders. Adding a segment no longer breaks the test, but a missing or duplicated catalog still does. Before the change, the real loader was run across all 49 catalogs: 554 requirements, 0 validation errors.
- **`Segment111ArtifactComparisonTest`**: now expects `ATL105-SEG111-RULE-CATALOG-001`. All 49 catalogs use this form, and so does every other catalog-ID assertion (Segments 101, 103, 104, 108, 109, 110, 113, 116, 118).

---

## 2. Specification defects found while cross-checking

Checking §11.1.1, §11.3.1, §11.8.1, Chapter 12 and Chapter 13 against each other turned up conflicts that no single section shows on its own.

| # | Finding | Evidence | Disposition |
|---|---|---|---|
| 1 | Element 63 accepts one or two digits | Chapter 13: *"Variable length of up to two digits"*. Element 62, by contrast, says *"Always precede single digits with a zero"*. | Validators accept `^[0-9]{1,2}$`. Range 1–7 standard, 1–6 EMV, 1–4 ECA/TeleCheck. |
| 2 | Segment 113 listed in the Financial Transaction Request template | §11.1.1 does not list it; §11.3.1 places it in the ECA/TeleCheck request | Removed from `atl105_complete_templates.json`; count 17 → 16 |
| 3 | Segment 111 maximum length 20 vs 999 | §11.3.1 table: 20. §12.10 and §11.1.1: 999 | `SEG111-R-008` added as PROVISIONAL (`P-01`) |
| 4 | Is Segment 111 required in the ECA/TeleCheck request? | §11.3.1 table marks it `R`; the prose says *"none, one, or more"* | `SEG113-R-017` aligned to the table; open item `SEG113-SME-008` |
| 5 | Segment 101 maximum length 308 vs 61 | §11.1.1 table: 308. §12.2 and Element 84: 61 | Already handled: base maximum 61, larger only when tags are present |
| 6 | Segments 146 and 152 in a request table | §11.1.1 lists them; the Chapter 12 matrix marks both response-only | Treated as REVIEW, not as an error |

---

## 3. Verification

Maven cannot reach the Fiserv Nexus from this workstation (`Remote host terminated the handshake`), and the JUnit launcher and AssertJ jars are not cached locally. Instead, `src/main` was compiled with `javac` against the cached Jackson jars, and a throwaway harness reproduced each changed assertion against the real repository files:

- Element 63 changes: 13 of 13 checks passed
- ECA/TeleCheck changes: 17 of 17 checks passed
- Baseline invariant: 49 segment folders and 49 catalog folders; the two sets match

**Action for the next tester on the network:** run `mvn test` to confirm with the real JUnit suite.
