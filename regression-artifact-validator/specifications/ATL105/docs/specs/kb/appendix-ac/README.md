# Appendix AC - Valid Country Codes

## Sources and implementation

Training uses ATL105 2026-3 Appendix AC-1 through AC-4, together with the
Table 059 definition in Appendix I-64/I-65.

- [Source-preserving catalog](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixACCountryCodeCatalog.java)
- [Independent logical oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixACCountryCodeOracle.java)
- [Tests](../../../../../../src/test/java/com/coreauth/validator/AppendixACCountryCodeOracleTest.java)
- [Repeatable fixture generator](../../../../../../src/main/java/com/coreauth/validator/coverage/GenerateAppendixACCountryCodeChains.java)
- [Coverage and complete country-row vector](../../../../test-output/test-json/appendices/appendix-ac-segment-100-coverage.json)

The catalog preserves **254 source rows: 250 numeric rows, 248 unique numeric
codes and 4 nonnumeric/reference rows**. The package has 5 requirements,
2 scenarios, 2 test-case records and 266 executable synthetic fixtures.
Status remains **PARTIALLY_COVERED**, because source recognition does not resolve
country-role conflicts or certify production configuration.

## Enumeration and source anomalies

Country codes are exactly three ASCII digits, including leading zeros such as
Afghanistan `004` and Solomon Islands `090`. No trimming, automatic padding,
Unicode-digit normalization or ISO-registry substitution is performed.

Every numeric source row gets an executable recognition fixture. Blank, N/A and
reference-only rows are preserved but do not enter the numeric allowlist:

| Source entry | Treatment |
|---|---|
| Antarctica (no universal currency) | Blank code preserved; no inferred numeric code |
| Holy See (See Vatican City State.) | N/A preserved; not silently aliased to 336 |
| European Economic and Monetary Union | N/A preserved |
| Virgin Islands reference | Reference preserved; British/United States entries remain separate |

Other notable source entries:

- `840` appears for both American Samoa and United States. Numeric recognition
  passes, but country mapping is REVIEW_REQUIRED, even if a supplied label
  matches one of those rows.
- Northern Mariana Islands `580` appears on both AC-2 and AC-4. Both rows are
  retained; identical duplicate mappings are not ambiguous.
- France, Metropolitan `249`, Mauritania `929`, Sao Tome and Principe `930`,
  UNMIK `900`, and all source spellings are retained as written. This is not a
  claim that the list equals the current ISO registry.
- Vatican City State (Holy See) is separately listed with `336`. The N/A Holy
  See reference is not used to derive an undocumented alias rule.

The test verifies every row against its source page, including the complete
ordered numeric sequence. It also tests all 1,000 three-digit candidates and
each numeric country-label mapping, rather than checking only a few examples.
Unknown labels/renamings are reviewed; a known source label paired with a
different source code fails. Country names are source labels, not a production
free-text field required by ATL105.

## Table 059 role and wire boundaries

AC-1 says these codes identify the country in which the **acquiring institution**
resides. Appendix I-64/I-65 instead names Table 059 **Soft Descriptor - Merchant
Country**, with data described as Merchant country. It explicitly requires the
underlying retailer's country for Discover third-party payment providers.

The oracle does not choose between merchant and acquirer for general use.
`ACQUIRER-CONTEXT` stays REVIEW_REQUIRED. In explicitly supplied Discover
third-party context, the separate retailer assessment compares Table 059 with
source-listed retailer-code evidence. Missing evidence is reviewed, a definite
code mismatch fails, and equal `840` codes still do not resolve country identity.
Matching synthetic codes do not certify actual location.

Appendix I prints the Table ID attribute as `n2` with value `059`, and Table Length
as `n2` with value `03`. These printed header-width inconsistencies are not
silently turned into a full wire parser. The logical country data is three
characters; full Segment 111 framing, ordering, requiredness and lifecycle
validation remain separate contracts.

## Evidence and regeneration

Each assessment set includes an `EXTERNAL-EVIDENCE` review gate. Source-listed
codes do not certify acquirer/merchant/retailer boarding, network eligibility,
current ISO validity or AI-produced artifacts. The country-role conflict,
ambiguous mappings and production evidence remain open.

The generator rebuilds `countryRows` and source-row fixtures from the independent
catalog while retaining hand-defined negative/context fixtures. It is repeatable.
Each source-row fixture carries its page anchor; payloads shallow-merge over
`fixtureDefaults`. Executable review outcomes test guardrails, not resolution.

Run from the Maven module:

```powershell
mvn compile exec:java "-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAppendixACCountryCodeChains"
mvn "-Dtest=AppendixACCountryCodeOracleTest,AppendixZStoredCredentialOracleTest,AppendixITableCatalogTest,ProducerNeutralContractTest,RepositoryStructureTest" test
```
