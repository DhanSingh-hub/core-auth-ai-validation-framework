param(
    [Parameter(Mandatory = $true)][string]$SegmentNumber,
    [string]$PocPipelineRoot = "C:\Users\F5H46GZ\Downloads\POC-DEMO\POC-DEMO\core-auth-test-generation-platform\src\pipeline",
    [string]$OutputDirectory = (Join-Path $PSScriptRoot "test-output\ai-artifacts\coverage-reports")
)

function Get-Tokens([string]$Text) {
    $stopWords = @("a", "an", "and", "are", "as", "at", "be", "by", "for", "from", "in", "is", "must", "of", "on", "or", "shall", "segment", "the", "to", "use", "with")
    return @($Text.ToLowerInvariant() -split "[^a-z0-9]+" | Where-Object { $_.Length -gt 2 -and $_ -notin $stopWords } | Select-Object -Unique)
}

function Get-Elements($Anchors) {
    $elements = @()
    foreach ($anchor in @($Anchors)) {
        if ($null -ne $anchor.element) {
            $elements += [regex]::Matches([string]$anchor.element, "\d+") | ForEach-Object Value
        }
    }
    return @($elements | Select-Object -Unique)
}

function Get-TokenScore($LeftTokens, $RightTokens) {
    $leftTokens = @($LeftTokens)
    $rightTokens = @($RightTokens)
    if ($leftTokens.Count -eq 0 -or $rightTokens.Count -eq 0) { return 0.0 }
    $shared = @($leftTokens | Where-Object { $_ -in $rightTokens }).Count
    return [math]::Round($shared / [math]::Max($leftTokens.Count, $rightTokens.Count), 3)
}

# Segments whose Test Solution BRs live partly or wholly in an existing appendix evidence file
# rather than (or in addition to) their own segment-<n>-core-structure-package.json.
$extraTestSolutionFiles = @{
    "111" = @("appendices\appendix-i-segment-100-coverage.json")
    "130" = @("appendices\appendix-r-segment-100-coverage.json", "appendices\appendix-s-segment-100-coverage.json")
    "135" = @("appendices\appendix-v-segment-100-coverage.json")
}

$aiCatalogPath = Join-Path $PSScriptRoot "test-output\ai-artifacts\business-requirements\POC-AI-ATL105-Segment-$SegmentNumber-Business-Requirements.json"
$aiCatalog = Get-Content $aiCatalogPath -Raw | ConvertFrom-Json
$scenarioCatalog = Get-Content (Join-Path $PocPipelineRoot "scenarios\approved\approved_scenarios.json") -Raw | ConvertFrom-Json
$deepCatalog = Get-Content (Join-Path $PocPipelineRoot "step4_deep_extraction\approved\deep_extraction_catalog.json") -Raw | ConvertFrom-Json

$ruleById = @{}
foreach ($rule in $deepCatalog.business_rules) { $ruleById[$rule.rule_id] = $rule }

$testRequirementsById = @{}
$testRoot = Join-Path $PSScriptRoot "test-output\test-json"
$testFiles = @(Get-Item (Join-Path $testRoot "segment-$SegmentNumber-core-structure-package.json"))
if ($extraTestSolutionFiles.ContainsKey($SegmentNumber)) {
    foreach ($extra in $extraTestSolutionFiles[$SegmentNumber]) {
        $testFiles += Get-Item (Join-Path $testRoot $extra)
    }
}
foreach ($testFile in $testFiles) {
    $document = Get-Content $testFile.FullName -Raw | ConvertFrom-Json
    foreach ($requirement in @($document.businessRequirements)) {
        if ($null -ne $requirement -and -not $testRequirementsById.ContainsKey($requirement.id)) {
            $anchors = if ($null -ne $requirement.sourceAnchors) { @($requirement.sourceAnchors) } else { @($requirement.sourceAnchor) }
            $testRequirementsById[$requirement.id] = [PSCustomObject]@{
                id = $requirement.id
                title = $requirement.title
                sourceFile = $testFile.Name
                sourceAnchors = $anchors
                elements = @(Get-Elements $anchors)
                tokens = @(Get-Tokens $requirement.title)
            }
        }
    }
}

$scenarioByRequirementId = @{}
foreach ($scenario in $scenarioCatalog.scenarios) {
    if ($null -eq $scenarioByRequirementId[$scenario.requirement_id]) { $scenarioByRequirementId[$scenario.requirement_id] = @() }
    $scenarioByRequirementId[$scenario.requirement_id] += $scenario
}

$crosswalk = @()
foreach ($aiRequirement in $aiCatalog.requirements) {
    $sourceRule = $ruleById[$aiRequirement.source_rule_id]
    $aiTokens = @(Get-Tokens $aiRequirement.statement)
    $aiElements = @()
    if ($null -ne $sourceRule) {
        $aiElements = @($sourceRule.related_entity_ids | ForEach-Object {
            if ($_ -match "^ENT-ELEM-(\d+)$") { $Matches[1] }
        } | Where-Object { $_ } | Select-Object -Unique)
    }

    $candidates = foreach ($testRequirement in $testRequirementsById.Values) {
        $sharedElements = @($aiElements | Where-Object { $_ -in $testRequirement.elements })
        $tokenScore = Get-TokenScore $aiTokens $testRequirement.tokens
        $score = $tokenScore + $(if ($sharedElements.Count -gt 0) { 1.0 } else { 0.0 })
        [PSCustomObject]@{
            requirement = $testRequirement
            sharedElements = $sharedElements
            tokenScore = $tokenScore
            score = $score
        }
    }
    $best = @($candidates | Sort-Object score, tokenScore -Descending | Select-Object -First 1)[0]
    $matchStatus = if ($null -eq $best) {
        "UNMATCHED_IN_TEST_SOLUTION"
    } elseif ($best.sharedElements.Count -gt 0 -and $best.tokenScore -ge 0.1) {
        "MATCHED_ELEMENT_AND_SEMANTICS"
    } elseif ($best.tokenScore -ge 0.45) {
        "MATCHED_SEMANTICS_ONLY"
    } elseif ($best.sharedElements.Count -gt 0 -or $best.tokenScore -ge 0.2) {
        "POTENTIAL_MATCH_REVIEW_REQUIRED"
    } else {
        "UNMATCHED_IN_TEST_SOLUTION"
    }
    $scenarios = if ($null -eq $scenarioByRequirementId[$aiRequirement.id]) { @() } else { @($scenarioByRequirementId[$aiRequirement.id]) }
    $scenarioStatus = if ($scenarios.Count -eq 0) { "MISSING_SCENARIO" } else { "SCENARIO_REVIEW_REQUIRED" }
    $crosswalk += [PSCustomObject]@{
        aiRequirementId = $aiRequirement.id
        aiSourceRuleId = $aiRequirement.source_rule_id
        aiScope = $aiRequirement.segmentScope
        aiSourcePage = $aiRequirement.source_page
        aiStatement = $aiRequirement.statement
        aiConfidence = $aiRequirement.confidence_pct
        aiElements = ($aiElements -join ",")
        scenarioCount = $scenarios.Count
        scenarioIds = ($scenarios.id -join ",")
        scenarioStatus = $scenarioStatus
        testMatchStatus = $matchStatus
        testRequirementId = $(if ($null -eq $best) { $null } else { $best.requirement.id })
        testRequirementTitle = $(if ($null -eq $best) { $null } else { $best.requirement.title })
        testRequirementFile = $(if ($null -eq $best) { $null } else { $best.requirement.sourceFile })
        sharedElements = $(if ($null -eq $best) { "" } else { $best.sharedElements -join "," })
        semanticScore = $(if ($null -eq $best) { 0.0 } else { $best.tokenScore })
    }
}

$matchedTestIds = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
foreach ($record in $crosswalk | Where-Object { $_.testMatchStatus -match "^MATCHED" }) { [void]$matchedTestIds.Add($record.testRequirementId) }
$testGaps = @($testRequirementsById.Values | Where-Object { -not $matchedTestIds.Contains($_.id) } | Sort-Object id)

$summary = [ordered]@{
    generatedAt = (Get-Date -Format "o")
    segment = "$SegmentNumber"
    comparisonScope = "POC approved requirements/scenarios filtered to Segment $SegmentNumber versus the Segment $SegmentNumber Test Solution core-structure baseline"
    aiRequirements = $crosswalk.Count
    testSolutionRequirements = $testRequirementsById.Count
    aiRequirementsWithScenario = @($crosswalk | Where-Object { $_.scenarioStatus -eq "SCENARIO_REVIEW_REQUIRED" }).Count
    aiRequirementsMissingScenario = @($crosswalk | Where-Object { $_.scenarioStatus -eq "MISSING_SCENARIO" }).Count
    aiScenarioCertificationStatus = "All linked scenarios are review-required: scenario catalog state is APPROVED but flagged_for_review is 7191, the scenarios use specification version 2025-3, and response oracles are Level C chatbot-sourced."
    confirmedIncorrectScenarios = "Not determinable from catalog metadata alone; no scenario is execution-certified."
    matchedElementAndSemantics = @($crosswalk | Where-Object { $_.testMatchStatus -eq "MATCHED_ELEMENT_AND_SEMANTICS" }).Count
    matchedSemanticsOnly = @($crosswalk | Where-Object { $_.testMatchStatus -eq "MATCHED_SEMANTICS_ONLY" }).Count
    potentialMatches = @($crosswalk | Where-Object { $_.testMatchStatus -eq "POTENTIAL_MATCH_REVIEW_REQUIRED" }).Count
    unmatchedAiRequirements = @($crosswalk | Where-Object { $_.testMatchStatus -eq "UNMATCHED_IN_TEST_SOLUTION" }).Count
    testSolutionRequirementsWithoutConfirmedAiMatch = $testGaps.Count
}
$summary.testSolutionRequirementsCovered = $summary.testSolutionRequirements - $summary.testSolutionRequirementsWithoutConfirmedAiMatch
$summary.confirmedBrCoveragePercent = if ($summary.testSolutionRequirements -eq 0) { 0 } else { [math]::Round(100 * $summary.testSolutionRequirementsCovered / $summary.testSolutionRequirements, 1) }

New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null
$jsonPath = Join-Path $OutputDirectory "POC-AI-Segment-$SegmentNumber-BR-Coverage-Crosswalk.json"
([ordered]@{summary = $summary; aiToTestSolution = $crosswalk; testSolutionWithoutConfirmedAiMatch = $testGaps}) | ConvertTo-Json -Depth 20 | Set-Content -Path $jsonPath -Encoding utf8

$markdown = @(
    "# Segment $SegmentNumber AI Solution vs Test Solution BR Coverage Ratio",
    '',
    '## Summary',
    '',
    "- AI Segment $SegmentNumber BRs: $($summary.aiRequirements)",
    "- Test Solution Segment $SegmentNumber BRs: $($summary.testSolutionRequirements)",
    "- AI BRs with one or more linked scenarios: $($summary.aiRequirementsWithScenario)",
    "- AI BRs missing a linked scenario: $($summary.aiRequirementsMissingScenario)",
    "- Confirmed AI-to-Test matches (element and semantics): $($summary.matchedElementAndSemantics)",
    "- Confirmed AI-to-Test matches (semantics only): $($summary.matchedSemanticsOnly)",
    "- Potential matches requiring review: $($summary.potentialMatches)",
    "- AI BRs unmatched in Test Solution: $($summary.unmatchedAiRequirements)",
    "- Test Solution BRs covered: $($summary.testSolutionRequirementsCovered) of $($summary.testSolutionRequirements)",
    "- Test Solution BRs without a confirmed AI match: $($summary.testSolutionRequirementsWithoutConfirmedAiMatch)",
    "- **Confirmed BR coverage: $($summary.confirmedBrCoveragePercent)%**",
    '',
    '## Scope Note',
    '',
    "This Test Solution baseline is a core-structure baseline (Segment Type/Length, key structural/business rules) for Segment $SegmentNumber, not yet as exhaustive as the full Segment 100 appendix-level baseline. Coverage percentages reflect this narrower scope.",
    '',
    '## AI BR to Test Solution BR Crosswalk',
    '',
    '| AI BR | AI rule | Scope | Scenarios | Scenario status | Test match | Test Solution BR | Test Solution title | Shared elements | AI statement |',
    '|---|---|---|---:|---|---|---|---|---|---|'
)
foreach ($record in $crosswalk | Sort-Object aiRequirementId) {
    $statement = $record.aiStatement -replace '\|', '\\|' -replace "`r?`n", ' '
    $title = $record.testRequirementTitle -replace '\|', '\\|'
    $markdown += "| $($record.aiRequirementId) | $($record.aiSourceRuleId) | $($record.aiScope) | $($record.scenarioCount) | $($record.scenarioStatus) | $($record.testMatchStatus) | $($record.testRequirementId) | $title | $($record.sharedElements) | $statement |"
}
$markdown += @('', '## Test Solution BRs Without a Confirmed AI Match', '', '| Test Solution BR | Source file | Requirement |', '|---|---|---|')
foreach ($gap in $testGaps) {
    $title = $gap.title -replace '\|', '\\|'
    $markdown += "| $($gap.id) | $($gap.sourceFile) | $title |"
}
$markdownPath = Join-Path $OutputDirectory "POC-AI-Segment-$SegmentNumber-BR-Coverage-Report.md"
$markdown | Set-Content -Path $markdownPath -Encoding utf8

function Encode-Html([object]$Value) { return [System.Net.WebUtility]::HtmlEncode([string]$Value) }

$confirmedMatches = $summary.matchedElementAndSemantics + $summary.matchedSemanticsOnly
$matrixRows = foreach ($record in $crosswalk | Sort-Object aiRequirementId) {
    $matchClass = $record.testMatchStatus.ToLowerInvariant()
    $scenarioClass = $record.scenarioStatus.ToLowerInvariant()
    "<tr data-match='$matchClass' data-scenario='$scenarioClass'><td><code>$(Encode-Html $record.aiRequirementId)</code><small>$(Encode-Html $record.aiSourceRuleId)</small></td><td><span class='badge $matchClass'>$(Encode-Html $record.testMatchStatus)</span><code>$(Encode-Html $record.testRequirementId)</code><small>$(Encode-Html $record.testRequirementFile)</small></td><td>$(Encode-Html $record.testRequirementTitle)</td><td><span class='badge $scenarioClass'>$(Encode-Html $record.scenarioStatus)</span><small>$(Encode-Html $record.scenarioIds)</small></td><td>$(Encode-Html $record.sharedElements)</td><td>$(Encode-Html $record.aiStatement)</td></tr>"
}
$testGapRows = foreach ($gap in $testGaps) {
    "<tr><td><code>$(Encode-Html $gap.id)</code></td><td>$(Encode-Html $gap.sourceFile)</td><td>$(Encode-Html $gap.title)</td></tr>"
}

$html = @"
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Segment $SegmentNumber BR Coverage Ratio: AI Solution vs Test Solution</title>
<style>
:root { --ink:#132238; --muted:#5b6777; --paper:#f6f3eb; --surface:#ffffff; --line:#d8d0c1; --teal:#087e8b; --green:#2e8b57; --gold:#bd7a00; --red:#b74242; --navy:#235789; }
* { box-sizing:border-box; } body { margin:0; background:var(--paper); color:var(--ink); font:15px Georgia, 'Times New Roman', serif; line-height:1.45; } header { padding:42px max(24px, calc((100vw - 1280px)/2)); background:var(--ink); color:#fff; border-bottom:7px solid var(--teal); } h1,h2 { font-family:Impact, Haettenschweiler, 'Arial Narrow Bold', sans-serif; font-weight:400; letter-spacing:0; } h1 { margin:0; font-size:38px; line-height:1; } h2 { font-size:24px; margin:0 0 12px; } header p { max-width:900px; color:#d6e6ea; margin:14px 0 0; } main { max-width:1280px; margin:0 auto; padding:28px 24px 60px; } .notice { padding:14px 17px; border-left:5px solid var(--gold); background:#fff7e6; margin-bottom:24px; } .stats { display:grid; grid-template-columns:repeat(5,minmax(150px,1fr)); gap:12px; margin-bottom:28px; } .stat { background:var(--surface); border:1px solid var(--line); padding:16px; min-height:110px; } .stat strong { display:block; font:34px/1 Impact, sans-serif; color:var(--navy); } .stat.warn strong { color:var(--gold); } .stat.danger strong { color:var(--red); } .stat.confirmed-stat strong { color:var(--green); } .stat span { color:var(--muted); font-size:13px; } section { margin:26px 0; } .panel { background:var(--surface); border:1px solid var(--line); padding:20px; } .filters { display:flex; flex-wrap:wrap; gap:10px; margin:12px 0; } input,select { font:inherit; padding:8px; border:1px solid var(--line); background:#fff; } input { min-width:280px; } .table-wrap { overflow:auto; border:1px solid var(--line); max-height:650px; } table { width:100%; border-collapse:collapse; min-width:1080px; background:#fff; } th { position:sticky; top:0; background:#e8eee9; text-align:left; font-size:12px; z-index:1; } th,td { padding:9px; border-bottom:1px solid #e6e0d6; vertical-align:top; } td { font-size:13px; } tr:nth-child(even) { background:#fbfaf7; } code { display:block; color:#174a6e; font:12px Consolas, monospace; } small { display:block; color:var(--muted); margin-top:4px; } .badge { display:inline-block; font:11px Consolas,monospace; padding:3px 5px; margin-bottom:4px; color:#fff; } .badge.matched_element_and_semantics,.badge.matched_semantics_only { background:var(--green); } .badge.potential_match_review_required { background:var(--gold); } .badge.unmatched_in_test_solution,.badge.missing_scenario { background:var(--red); } .badge.scenario_review_required { background:var(--teal); } @media (max-width:800px) { header { padding:30px 20px; } h1 { font-size:30px; } main { padding:20px 14px; } .stats { grid-template-columns:1fr; } input { min-width:100%; } }
</style>
</head>
<body>
<header><h1>Segment $SegmentNumber BR Coverage Ratio</h1><p>Business Requirement coverage ratio between the POC AI Solution and the Segment $SegmentNumber Test Solution core-structure baseline. This report covers BR-level matching only. Generated $($summary.generatedAt).</p></header>
<main>
<div class="notice"><strong>Scope:</strong> the Segment $SegmentNumber Test Solution baseline is a core-structure baseline, not yet as exhaustive as Segment 100's full appendix-level baseline. AI and Test Solution use independent BR IDs; green is a confirmed semantic/element match, amber is a potential match requiring SME review.</div>
<div class="stats"><div class="stat"><strong>$($summary.aiRequirements)</strong><span>AI Segment $SegmentNumber BRs</span></div><div class="stat"><strong>$($summary.testSolutionRequirements)</strong><span>Expected Test Solution BRs</span></div><div class="stat $(if($summary.confirmedBrCoveragePercent -ge 50){'confirmed-stat'}else{'warn'})"><strong>$($summary.confirmedBrCoveragePercent)%</strong><span>Confirmed BR coverage ($($summary.testSolutionRequirementsCovered)/$($summary.testSolutionRequirements))</span></div><div class="stat warn"><strong>$($summary.aiRequirementsMissingScenario)</strong><span>AI BRs missing scenarios</span></div><div class="stat danger"><strong>$($summary.testSolutionRequirementsWithoutConfirmedAiMatch)</strong><span>Expected BRs lacking a confirmed AI match</span></div></div>
<section class="panel"><h2>AI BR to Test Solution BR Mapping Matrix</h2><div class="filters"><input id="search" placeholder="Search BR ID, rule, title, or requirement text"></div><div class="table-wrap"><table id="matrix"><thead><tr><th>AI BR</th><th>Mapping status</th><th>Expected Test Solution BR</th><th>Scenario status</th><th>Shared elements</th><th>AI requirement</th></tr></thead><tbody>$($matrixRows -join "`n")</tbody></table></div></section>
<section class="panel"><h2>Expected Test Solution BRs Missing from AI Coverage ($($summary.testSolutionRequirementsWithoutConfirmedAiMatch))</h2><div class="table-wrap"><table><thead><tr><th>Test Solution BR</th><th>Source package</th><th>Expected requirement</th></tr></thead><tbody>$($testGapRows -join "`n")</tbody></table></section>
</main>
<script>
const search=document.getElementById('search');
search.addEventListener('input',()=>{for(const row of document.querySelectorAll('#matrix tbody tr')){row.hidden = search.value && !row.innerText.toLowerCase().includes(search.value.toLowerCase());}});
</script>
</body>
</html>
"@
$htmlPath = Join-Path $OutputDirectory "POC-AI-Segment-$SegmentNumber-BR-Coverage-Ratio-Report.html"
$html | Set-Content -Path $htmlPath -Encoding utf8

Write-Output "Segment $SegmentNumber -- AI BRs: $($summary.aiRequirements); Test Solution BRs: $($summary.testSolutionRequirements); Coverage: $($summary.confirmedBrCoveragePercent)%"
Write-Output "Report: $markdownPath"
Write-Output "Crosswalk: $jsonPath"
Write-Output "Visual report: $htmlPath"
