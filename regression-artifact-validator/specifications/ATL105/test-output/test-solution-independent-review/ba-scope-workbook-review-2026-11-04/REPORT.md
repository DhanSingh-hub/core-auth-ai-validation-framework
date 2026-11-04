# ATL105 BA BR → TS → TC Workbook Review

**Status:** Supplemental planning input; not approved requirements or a coverage baseline  
**Review date:** 2026-11-04  
**Source:** `C:\Users\F9HBPGC\OneDrive - Fiserv Corp\Documents\Feedbacks\BR & TS Scope updates.xlsx`  
**Source SHA-256:** `f26970e6371d07f19779a420328034b4c76328589267f801ce699a27c0003efa`

## Executive summary

The workbook is useful as a starting point for expanding scenario ideas, including
declines, reversals, exceptions and product applicability. It is not yet a
reliable traceability or execution artifact: the detailed case tabs do not
reconcile to the workbook's own BR → TS scope, TC identifiers collide across
tabs, important expected-result fields are absent or blank, and no test-data
definition is supplied. The workbook also does not identify source specification
releases or map its placeholder BR1–BR13 labels to the current canonical BR
inventory.

Do not modify existing BRs or treat the workbook's decomposition as a replacement
for them. Confirm the crosswalk, applicable source/release and product scope with
the BA before turning these scenarios into approved tests. This review does not
approve a denominator or claim a coverage percentage.

## Workbook inventory

| Area | Observed content |
|---|---|
| Visible sheets | `BR&TS scope`, `Product Applicability matrix`, `BR1 Cases` through `BR5 Cases` |
| Scope sheet | 86 BR-to-TS rows; 13 distinct BR labels and 86 distinct TS IDs |
| Product matrix | 12 product entries |
| Detailed cases | 668 rows: BR1 149, BR2 114, BR3 152, BR4 98, BR5 155 |
| Detailed case coverage | Case tabs exist only for BR1–BR5; no detailed case tabs for BR6–BR13 |
| Data definitions | No dedicated test-data (TD) sheet |
| Formula/link checks | No formulas, external links, or hidden sheets observed |
| Merged cells | No merged ranges on the inspected sheets; the blank case fields below are not explained by merged cells |

The input workbook was inspected read-only and was not changed.

## Findings

### 1. BR → TS → TC traceability does not reconcile

The five case tabs use 105 distinct TS IDs. Of those, 75 appear somewhere in
the scope sheet, but only 7 have a matching parent BR/TS pair, and only 6 rows
match on the full BR + TS + scenario tuple. The other 68 IDs that exist in the
scope sheet are associated there with a different BR; 30 case-tab TS IDs do not
appear in the scope sheet at all.

| Case tab | Case rows | TS IDs used | TS IDs under same BR in scope | Exact BR + TS + scenario matches |
|---|---:|---:|---:|---:|
| BR1 Cases | 149 | 21 | 7 | 6 |
| BR2 Cases | 114 | 18 | 0 | 0 |
| BR3 Cases | 152 | 24 | 0 | 0 |
| BR4 Cases | 98 | 16 | 0 | 0 |
| BR5 Cases | 155 | 26 | 0 | 0 |
| **Total** | **668** | **105** | **7** | **6** |

This prevents reliable requirement-level traceability and makes it unsafe to
infer coverage from the workbook as currently structured.

### 2. TC identifiers are not unique

The 668 case rows contain only 405 distinct TC ID values. There are 114 IDs
that occur on three sheets and 35 IDs that occur on two sheets. In particular,
the generic `TC-###` sequence is reused across BR1, BR2 and BR5. A TC ID cannot
uniquely identify a test case until the IDs are made unique across the full
workbook or replaced with a stable, globally unique key.

### 3. Expected-result and setup detail is incomplete

- **BR1:** 70 of 149 rows have a blank `Preconditions` cell; the same count is
  blank in `Parent Transaction`, and the same count is blank in `Expected
  Result`. The workbook does not establish that the blank cells occur in the
  same 70 rows.
- **BR2–BR4:** the case sheets do not provide precondition or expected-result
  columns.
- **BR5:** it provides more execution detail, but a generic result such as
  “Transaction declined” does not define the response values or business reason
  to assert.

The layouts also vary by tab, so the same test information cannot be consistently
reviewed or executed across the workbook.

### 4. Decline scenarios are named, but decline outcomes are not specified

The case names include useful candidate decline conditions such as Do Not Honor,
Insufficient Funds, Lost/Stolen/Restricted Card, Invalid Account, Security
Violation and Transaction Not Permitted. However, the workbook does not supply
a structured decline-reason or response-code field, and the expected results
are generic or blank. It therefore does not establish which network/host
response is expected, how it maps to an internal result, or what exact outcome
the test should assert.

Do not invent or infer response codes from scenario names. Obtain the
authoritative response/reason mapping for each applicable network and processing
context.

### 5. Test data and exception behavior need definition

There is no dedicated TD catalog or consistent structured reference to test
data. Negative and exception cases need reproducible inputs and execution
conditions—for example, the transaction state and identifiers used for a
duplicate, the timeout point and recovery window, and the values that trigger
validation failures. These details must be defined without exposing real
cardholder data.

### 6. BR6–BR13 have scope entries but no detailed cases

The scope sheet includes BR6–BR13, but the workbook has detailed case tabs only
for BR1–BR5. This is a planning gap to resolve if those scope entries are in
the intended delivery; it is not evidence that the underlying BRs are absent or
that a specific number of cases is required.

### 7. Product applicability and source authority need confirmation

The product matrix lists 12 entries, but it is not linked at the individual
case level to the BR → TS rows or to specific expected outcomes. Its product
labels also need to be distinguished from network, card program and processing
context. Contexts represented elsewhere in the ATL105 review—such as Maestro,
STAR, EBT/eWIC and other specialized programs—are not explicitly named in this
matrix. Their omission may be intentional; the BA should confirm in-scope and
out-of-scope products rather than treating the matrix as exhaustive.

The workbook contains no source-document anchors or specification release
identifiers, and BR1–BR13 are not shown cross-referenced to the canonical BR
inventory. The labels must be treated as supplemental placeholders until that
mapping is approved.

## Recommended actions for BA discussion

1. **Confirm the canonical crosswalk.** For every BR1–BR13 label, identify the
   existing canonical BR ID(s), its applicable TS IDs, and whether the label is
   a supplemental grouping, decomposition, or cross-cutting test dimension.
   Preserve existing BR text and structure.
2. **Reconcile the case references.** Correct each case's BR/TS association
   against the approved crosswalk and define explicit applicability where a
   scenario applies to more than one BR.
3. **Make TC keys unique.** Assign stable identifiers across all detailed case
   tabs; do not rely on sheet name to disambiguate a reused TC ID.
4. **Standardize the case schema.** Include, at minimum, canonical BR and TS
   references, applicability, preconditions, test-data reference, execution
   steps, and explicit expected results. For decline tests, record the
   authoritative network/host response, normalized result and reason that the
   implementation is expected to expose.
5. **Define TDs and exception conditions.** Add a controlled test-data catalog
   using synthetic or approved masked data, and specify transaction state,
   timing, identifiers and recovery expectations for timeout, reversal,
   duplicate and validation scenarios.
6. **Complete or disposition BR6–BR13.** Add detailed cases if those scope
   entries are in scope, or record the BA-approved reason and scope treatment
   for entries without cases.
7. **Approve product and release scope.** Confirm whether the 12 matrix entries
   are exhaustive; explicitly decide the treatment of omitted networks and
   programs. Pin each applicable scenario to the authoritative specification
   release and source anchor.

## Questions to resolve with the BA

- What is the approved mapping from BR1–BR13 to the current canonical ATL105 BR
  IDs, and which specification release/source anchors govern each mapping?
- Are BR1–BR13 intended as supplemental decomposition only, or do any labels
  represent new requirements? Existing BRs will remain unchanged unless separately
  approved through the requirements process.
- Is the 12-entry product matrix exhaustive? In particular, should Maestro,
  STAR, EBT/eWIC and other currently modeled network/program contexts be in or
  out of scope?
- Which processor/host response mapping is authoritative for each decline
  scenario, and should expected results assert the raw response, normalized
  result/reason, customer-visible outcome, or a specified combination?
- What are the required test-data, setup, timing, duplicate-detection and
  recovery conditions for negative and exception cases?
- Who will supply or approve detailed case coverage for BR6–BR13?

## Acceptance boundary

Until the crosswalk, release/source authority, product applicability and expected
outcomes are confirmed, this workbook remains candidate planning material. Do
not use its row counts as a test denominator, do not claim approved BR coverage,
and do not alter the existing BR inventory based on this workbook alone.
