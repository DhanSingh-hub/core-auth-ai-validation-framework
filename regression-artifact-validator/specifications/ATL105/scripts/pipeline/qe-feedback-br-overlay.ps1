param(
    [string]$SummaryPath = (Join-Path (Split-Path (Split-Path $PSScriptRoot -Parent) -Parent) 'test-output\ai-solution-independent-review\2026-10-17-qe-feedback-training-v2\summary.json')
)

# Emits the QE-Fiserv feedback section of all-segments-br-ratio.md; links assume the report lives in coverage-reports/all-segments.
$lines = @(
    '## QE-Fiserv Feedback Training (October 2026)','',
    'Source: [QE-Fiserv.xlsx](../../../../../../QE-Fiserv.xlsx) (`1.Feedback`, `2. Feedback-30-sep-26`, `Status-SepDef`, `3.Oct 2026_Feedback`, `Oct-10 AutoRun-FB`, `Action Items`). Every feedback item is trained and enforced as a Test Solution rule regardless of the workbook status column (rule catalog `contract/qe-feedback-rule-catalog.json`, rules `QEF-001`-`QEF-033`). Feedback rules that differ from ATL105 2026-3 text keep a recorded source conflict for SME confirmation. The `Oct-10 AutoRun-FB` decision columns are empty, so its candidate rows are not approved matches.',''
)
if (-not (Test-Path $SummaryPath)) {
    $lines += 'No QE feedback training run summary is available; run `scripts/apply-qe-feedback-rules.py` against a preserved delivery.'
    return $lines
}
$summary = Get-Content $SummaryPath -Raw -Encoding utf8 | ConvertFrom-Json
$p = $summary.population
$folder = Split-Path (Split-Path $SummaryPath -Parent) -Leaf
$base = "../../../ai-solution-independent-review/$folder"
$rules = @{}
foreach ($rule in $summary.rules) { $rules[$rule.ruleId] = $rule }
function Row($id, $meaning) {
    $r = $rules[$id]
    "| $id | $meaning | $('{0:N0}' -f [int]$r.applicable) | $('{0:N0}' -f [int]$r.fail) | $(if ($null -ne $r.failPercent) { "$($r.failPercent)%" } else { 'n/a' }) |"
}
$lines += @(
    '### BR, scenario and test-case chain on the 10 October AI delivery','',
    "Delivery preserved and SHA-256 verified at ``test-input/ai-solution/runs/2026-10-17/Run1/``. The engine validated **$('{0:N0}' -f [int]$p.businessRequirements) AI BRs**, **$('{0:N0}' -f [int]$p.scenarios) scenarios**, **$('{0:N0}' -f [int]$p.testCases) test cases** ($([int]$p.flowTestCases) flows) and **$('{0:N0}' -f [int]$p.payloadFilesValidated)/$('{0:N0}' -f [int]$p.payloadFiles) payload legs**. These counts use the AI BR population as denominator; they do not change the Test Solution baseline coverage above.",'',
    "- BRs without a scenario: **$([int]$p.brWithoutScenario)**; scenarios without a test case: **$('{0:N0}' -f [int]$p.scenarioWithoutTestCase)**; BRs without a test case: **$('{0:N0}' -f [int]$p.brWithoutTestCase)** (silent coverage made visible).",'',
    '| Rule | Feedback requirement | Applicable | Fail | Fail % |','| --- | --- | ---: | ---: | ---: |',
    (Row 'QEF-001' 'BR linked to a segment (Def_SepRun01/04)'),
    (Row 'QEF-002' 'BR has a scenario (Def_SepRun02/05)'),
    (Row 'QEF-003' 'Scenario has a test case (Def_SepRun02/05/21)'),
    (Row 'QEF-004' 'Test case has physical test data (Def_SepRun21)'),
    (Row 'QEF-005' 'BR segment serialized in test data (Def_SepRun15/20)'),
    (Row 'QEF-006' 'Scenario is not a copy of its BR (Def_Sep30Run04)'),
    (Row 'QEF-009' 'Concrete expected response code (Def_Sep30Run03)'),
    (Row 'QEF-010' 'BR has negative coverage (Def_Sep30Run05)'),
    (Row 'QEF-012' 'Canonical source anchor on BR/TS/TC (Def_Sep30Run07)'),
    (Row 'QEF-013' 'BR keeps value constraint (Def_Sep30Run08)'),'',
    "QEF-001 also holds **$([int]$rules['QEF-001'].review)** BRs whose segment was only LLM-proposed for review. Test-data rules QEF-011 to QEF-031 (October findings D001-D020 and the Action Items) are reported in the [QE feedback validation report]($base/QE-FEEDBACK-VALIDATION-REPORT.md).",'',
    "Readable outputs requested by the feedback: [per-segment traceability, 8-column manual tables and Test Case Cards]($base/readable/README.md). Semantic coverage remains ``$($summary.semanticCoverage)``; host execution ``$($summary.hostExecution)``. The workbook's September 3,072/6,291 (48.8%) figure belongs to an earlier delivery and is not comparable."
)
$lines
