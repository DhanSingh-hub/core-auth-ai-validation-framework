param(
    [string]$PocPipelineRoot = "C:\Users\F8VNU8Y\Downloads\CoreAuthTestingUI\CoreAuthTestingUI\CoreAuthTestingUI\src\pipeline",
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

$aiCatalog = Get-Content (Join-Path $PSScriptRoot "test-output\ai-artifacts\business-requirements\POC-AI-ATL105-Segment-103-Business-Requirements.json") -Raw | ConvertFrom-Json
$scenarioCatalog = Get-Content (Join-Path $PocPipelineRoot "scenarios\approved\approved_scenarios.json") -Raw | ConvertFrom-Json
$deepCatalog = Get-Content (Join-Path $PocPipelineRoot "step4_deep_extraction\approved\deep_extraction_catalog.json") -Raw | ConvertFrom-Json

$ruleById = @{}
foreach ($rule in $deepCatalog.business_rules) { $ruleById[$rule.rule_id] = $rule }

$testRequirementsById = @{}
$testRoot = Join-Path $PSScriptRoot "test-output\test-json"
$testFile = Get-Item (Join-Path $testRoot "segment-103-core-structure-package.json")
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

$scenarioByRequirementId = @{}
foreach ($scenario in $scenarioCatalog.scenarios) {
    if ([string]::IsNullOrEmpty($scenario.requirement_id)) { continue }
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
        aiConfidence = $aiRequirement.confidence_score
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
    comparisonScope = "Real POC AI requirements (CoreAuthTestingUI pipeline, filtered to Segment 103 via segmentScope) versus the resolved Segment 103 Test Solution baseline (24-rule catalog)"
    aiRequirements = $crosswalk.Count
    testSolutionRequirements = $testRequirementsById.Count
    aiRequirementsWithScenario = @($crosswalk | Where-Object { $_.scenarioStatus -eq "SCENARIO_REVIEW_REQUIRED" }).Count
    aiRequirementsMissingScenario = @($crosswalk | Where-Object { $_.scenarioStatus -eq "MISSING_SCENARIO" }).Count
    aiScenarioCertificationStatus = "All linked scenarios are review-required: the CoreAuthTestingUI scenario catalog assigns Level C chatbot-sourced response-oracle authority and has not been execution-certified against a converter."
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
$jsonPath = Join-Path $OutputDirectory "POC-AI-Segment-103-BR-Coverage-Crosswalk.json"
([ordered]@{summary = $summary; aiToTestSolution = $crosswalk; testSolutionWithoutConfirmedAiMatch = $testGaps}) | ConvertTo-Json -Depth 20 | Set-Content -Path $jsonPath -Encoding utf8

$markdown = @(
    '# Segment 103 AI Solution vs Test Solution BR Coverage Ratio',
    '',
    '## Summary',
    '',
    "- AI Segment 103 BRs: $($summary.aiRequirements)",
    "- Test Solution Segment 103 BRs: $($summary.testSolutionRequirements)",
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
    '## Scenario Quality',
    '',
    'Every linked scenario is review-required: the CoreAuthTestingUI scenario catalog assigns Level C chatbot-sourced response-oracle authority and no scenario is execution-certified against a converter.',
    '',
    '## Matching Method',
    '',
    'The solutions use different BR identifiers. A confirmed match requires a shared ATL105 element plus semantic token overlap, or strong semantic overlap. Potential matches are deliberately not counted as coverage. This is a traceability comparison, not proof of executable test-data correctness.',
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
$markdown += @('', '## AI BRs Missing Scenarios', '', '| AI BR | Source rule | Requirement |', '|---|---|---|')
foreach ($record in $crosswalk | Where-Object { $_.scenarioStatus -eq 'MISSING_SCENARIO' } | Sort-Object aiRequirementId) {
    $statement = $record.aiStatement -replace '\|', '\\|' -replace "`r?`n", ' '
    $markdown += "| $($record.aiRequirementId) | $($record.aiSourceRuleId) | $statement |"
}
$markdown += @('', '## Test Solution BRs Without a Confirmed AI Match', '', '| Test Solution BR | Source file | Requirement |', '|---|---|---|')
foreach ($gap in $testGaps) {
    $title = $gap.title -replace '\|', '\\|'
    $markdown += "| $($gap.id) | $($gap.sourceFile) | $title |"
}
$markdownPath = Join-Path $OutputDirectory "POC-AI-Segment-103-BR-Coverage-Report.md"
$markdown | Set-Content -Path $markdownPath -Encoding utf8

function Encode-Html([object]$Value) {
    return [System.Net.WebUtility]::HtmlEncode([string]$Value)
}

$confirmedMatches = $summary.matchedElementAndSemantics + $summary.matchedSemanticsOnly
$testRequirementsCovered = $summary.testSolutionRequirements - $summary.testSolutionRequirementsWithoutConfirmedAiMatch
$matrixRows = foreach ($record in $crosswalk | Sort-Object aiRequirementId) {
    $matchClass = $record.testMatchStatus.ToLowerInvariant()
    $scenarioClass = $record.scenarioStatus.ToLowerInvariant()
    "<tr data-match='$matchClass' data-scenario='$scenarioClass'><td><code>$(Encode-Html $record.aiRequirementId)</code><small>$(Encode-Html $record.aiSourceRuleId)</small></td><td><span class='badge $matchClass'>$(Encode-Html $record.testMatchStatus)</span><code>$(Encode-Html $record.testRequirementId)</code><small>$(Encode-Html $record.testRequirementFile)</small></td><td>$(Encode-Html $record.testRequirementTitle)</td><td><span class='badge $scenarioClass'>$(Encode-Html $record.scenarioStatus)</span><small>$(Encode-Html $record.scenarioIds)</small></td><td>$(Encode-Html $record.sharedElements)</td><td>$(Encode-Html $record.aiStatement)</td></tr>"
}
$potentialRows = foreach ($record in $crosswalk | Where-Object { $_.testMatchStatus -eq 'POTENTIAL_MATCH_REVIEW_REQUIRED' } | Sort-Object aiRequirementId) {
    "<tr><td><code>$(Encode-Html $record.aiRequirementId)</code></td><td>$(Encode-Html $record.aiStatement)</td><td><code>$(Encode-Html $record.testRequirementId)</code><br>$(Encode-Html $record.testRequirementTitle)</td><td>$($record.semanticScore)</td></tr>"
}
$missingScenarioRows = foreach ($record in $crosswalk | Where-Object { $_.scenarioStatus -eq 'MISSING_SCENARIO' } | Sort-Object aiRequirementId) {
    "<tr><td><code>$(Encode-Html $record.aiRequirementId)</code></td><td><code>$(Encode-Html $record.aiSourceRuleId)</code></td><td>$(Encode-Html $record.aiStatement)</td></tr>"
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
<title>Segment 103 BR Coverage Ratio: AI Solution vs Test Solution</title>
<style>
:root { --ink:#132238; --muted:#5b6777; --paper:#f6f3eb; --surface:#ffffff; --line:#d8d0c1; --teal:#087e8b; --green:#2e8b57; --gold:#bd7a00; --red:#b74242; --navy:#235789; }
* { box-sizing:border-box; } body { margin:0; background:var(--paper); color:var(--ink); font:15px Georgia, 'Times New Roman', serif; line-height:1.45; } header { padding:42px max(24px, calc((100vw - 1280px)/2)); background:var(--ink); color:#fff; border-bottom:7px solid var(--teal); } h1,h2 { font-family:Impact, Haettenschweiler, 'Arial Narrow Bold', sans-serif; font-weight:400; letter-spacing:0; } h1 { margin:0; font-size:42px; line-height:1; } h2 { font-size:26px; margin:0 0 12px; } header p { max-width:900px; color:#d6e6ea; margin:14px 0 0; } main { max-width:1280px; margin:0 auto; padding:28px 24px 60px; } .notice { padding:14px 17px; border-left:5px solid var(--gold); background:#fff7e6; margin-bottom:24px; } .stats { display:grid; grid-template-columns:repeat(5,minmax(150px,1fr)); gap:12px; margin-bottom:28px; } .stat { background:var(--surface); border:1px solid var(--line); padding:16px; min-height:110px; } .stat strong { display:block; font:38px/1 Impact, sans-serif; color:var(--navy); } .stat.warn strong { color:var(--gold); } .stat.danger strong { color:var(--red); } .stat.confirmed-stat strong { color:var(--green); } .stat span { color:var(--muted); font-size:13px; } section { margin:26px 0; } .panel { background:var(--surface); border:1px solid var(--line); padding:20px; } .chart-grid { display:grid; grid-template-columns:1.2fr 1fr; gap:24px; } .bar-row { display:grid; grid-template-columns:185px 1fr 110px; gap:10px; align-items:center; margin:14px 0; } .bar { height:27px; display:flex; background:#e8e2d7; overflow:hidden; } .bar span { min-width:2px; } .confirmed { background:var(--green); } .potential { background:var(--gold); } .missing { background:var(--red); } .review { background:var(--teal); } .legend { display:flex; gap:15px; flex-wrap:wrap; color:var(--muted); font-size:13px; } .legend i { width:12px; height:12px; display:inline-block; margin-right:5px; } .flow { display:flex; align-items:center; gap:10px; flex-wrap:wrap; margin:12px 0 18px; } .node { border:2px solid var(--navy); background:#fff; padding:15px; min-width:150px; text-align:center; } .node strong { display:block; font:28px/1 Impact,sans-serif; color:var(--navy); } .arrow { color:var(--teal); font-size:30px; } .filters { display:flex; flex-wrap:wrap; gap:10px; margin:12px 0; } input,select { font:inherit; padding:8px; border:1px solid var(--line); background:#fff; } input { min-width:280px; } .table-wrap { overflow:auto; border:1px solid var(--line); max-height:650px; } table { width:100%; border-collapse:collapse; min-width:1080px; background:#fff; } th { position:sticky; top:0; background:#e8eee9; text-align:left; font-size:12px; z-index:1; } th,td { padding:9px; border-bottom:1px solid #e6e0d6; vertical-align:top; } td { font-size:13px; } tr:nth-child(even) { background:#fbfaf7; } code { display:block; color:#174a6e; font:12px Consolas, monospace; } small { display:block; color:var(--muted); margin-top:4px; } .badge { display:inline-block; font:11px Consolas,monospace; padding:3px 5px; margin-bottom:4px; color:#fff; } .badge.matched_element_and_semantics,.badge.matched_semantics_only { background:var(--green); } .badge.potential_match_review_required { background:var(--gold); } .badge.unmatched_in_test_solution,.badge.missing_scenario { background:var(--red); } .badge.scenario_review_required { background:var(--teal); } .two-columns { display:grid; grid-template-columns:1fr 1fr; gap:24px; } .callout { padding:16px; border-left:5px solid var(--teal); background:#edf7f7; } @media (max-width:800px) { header { padding:30px 20px; } h1 { font-size:34px; } main { padding:20px 14px; } .stats,.chart-grid,.two-columns { grid-template-columns:1fr; } .bar-row { grid-template-columns:1fr; gap:4px; } input { min-width:100%; } }
</style>
</head>
<body>
<header><h1>Segment 103 BR Coverage Ratio</h1><p>Business Requirement coverage ratio between the POC AI Solution and the resolved Segment 103 Test Solution baseline (24-rule catalog). This report covers BR-level matching only; it is not a complete BR &#8594; TS &#8594; TC &#8594; TD mapping matrix. Generated $($summary.generatedAt).</p></header>
<main>
<div class="notice"><strong>Interpretation:</strong> AI and Test Solution use independent BR IDs. Green is a confirmed semantic/element match; amber is a potential match requiring SME review. The matrix does not certify executable test data or response correctness.</div>
<div class="stats"><div class="stat"><strong>$($summary.aiRequirements)</strong><span>AI Segment 103 BRs</span></div><div class="stat"><strong>$($summary.testSolutionRequirements)</strong><span>Expected Test Solution BRs</span></div><div class="stat $(if($summary.confirmedBrCoveragePercent -ge 50){'confirmed-stat'}else{'warn'})"><strong>$($summary.confirmedBrCoveragePercent)%</strong><span>Confirmed BR coverage ($($summary.testSolutionRequirementsCovered)/$($summary.testSolutionRequirements))</span></div><div class="stat warn"><strong>$($summary.aiRequirementsMissingScenario)</strong><span>AI BRs missing scenarios</span></div><div class="stat danger"><strong>$($summary.testSolutionRequirementsWithoutConfirmedAiMatch)</strong><span>Expected BRs lacking a confirmed AI match</span></div></div>
<section class="panel"><h2>Coverage At A Glance</h2><div class="chart-grid"><div><div class="bar-row"><b>AI BR mapping</b><div class="bar"><span class="confirmed" style="width:$([math]::Round(100 * $confirmedMatches / [math]::Max($summary.aiRequirements,1)))%"></span><span class="potential" style="width:$([math]::Round(100 * $summary.potentialMatches / [math]::Max($summary.aiRequirements,1)))%"></span></div><span>$confirmedMatches confirmed / $($summary.potentialMatches) review</span></div><div class="bar-row"><b>Expected BR coverage</b><div class="bar"><span class="confirmed" style="width:$([math]::Round(100 * $testRequirementsCovered / [math]::Max($summary.testSolutionRequirements,1)))%"></span><span class="missing" style="width:$([math]::Round(100 * $summary.testSolutionRequirementsWithoutConfirmedAiMatch / [math]::Max($summary.testSolutionRequirements,1)))%"></span></div><span>$testRequirementsCovered covered / $($summary.testSolutionRequirementsWithoutConfirmedAiMatch) gap</span></div><div class="bar-row"><b>AI scenario linkage</b><div class="bar"><span class="review" style="width:$([math]::Round(100 * $summary.aiRequirementsWithScenario / [math]::Max($summary.aiRequirements,1)))%"></span><span class="missing" style="width:$([math]::Round(100 * $summary.aiRequirementsMissingScenario / [math]::Max($summary.aiRequirements,1)))%"></span></div><span>$($summary.aiRequirementsWithScenario) review-required / $($summary.aiRequirementsMissingScenario) missing</span></div><div class="legend"><span><i class="confirmed"></i>confirmed</span><span><i class="potential"></i>review required</span><span><i class="missing"></i>missing/gap</span><span><i class="review"></i>linked but uncertified</span></div></div><div><h2>Traceability Flow</h2><div class="flow"><div class="node"><strong>$($summary.aiRequirements)</strong>AI BRs</div><div class="arrow">&#8594;</div><div class="node"><strong>$($summary.aiRequirementsWithScenario)</strong>linked scenarios</div><div class="arrow">&#8594;</div><div class="node"><strong>$confirmedMatches</strong>confirmed BR matches</div></div><div class="callout"><strong>Scenario correctness:</strong> no scenario is execution-certified. All linked Segment 103 scenarios require review due to Level C chatbot-sourced response-oracle authority and no converter run against the ATL105 spec.</div></div></div></section>
<section class="panel"><h2>Complete AI-to-Test BR Mapping Matrix</h2><div class="filters"><input id="search" placeholder="Search BR ID, rule, title, or requirement text"><select id="matchFilter"><option value="">All match statuses</option><option value="matched_element_and_semantics">Confirmed: element and semantics</option><option value="matched_semantics_only">Confirmed: semantics only</option><option value="potential_match_review_required">Potential: review required</option><option value="unmatched_in_test_solution">Unmatched</option></select><select id="scenarioFilter"><option value="">All scenario statuses</option><option value="scenario_review_required">Linked, review required</option><option value="missing_scenario">Missing scenario</option></select></div><div class="table-wrap"><table id="matrix"><thead><tr><th>AI BR</th><th>Mapping status</th><th>Expected Test Solution BR</th><th>Scenario status</th><th>Shared elements</th><th>AI requirement</th></tr></thead><tbody>$($matrixRows -join "`n")</tbody></table></div></section>
<section class="two-columns"><div class="panel"><h2>Missing AI Scenarios ($($summary.aiRequirementsMissingScenario))</h2><div class="table-wrap"><table><thead><tr><th>AI BR</th><th>Source rule</th><th>Requirement</th></tr></thead><tbody>$($missingScenarioRows -join "`n")</tbody></table></div></div><div class="panel"><h2>Potential BR Matches ($($summary.potentialMatches))</h2><p>These records have a plausible Test Solution target but do not meet the confirmed-match threshold.</p><div class="table-wrap"><table><thead><tr><th>AI BR</th><th>AI requirement</th><th>Candidate expected BR</th><th>Score</th></tr></thead><tbody>$($potentialRows -join "`n")</tbody></table></div></div></section>
<section class="panel"><h2>Expected Test Solution BRs Missing from AI Coverage ($($summary.testSolutionRequirementsWithoutConfirmedAiMatch))</h2><p>These expected requirements have no confirmed AI mapping. They are the primary backlog for AI Solution BR coverage.</p><div class="table-wrap"><table><thead><tr><th>Test Solution BR</th><th>Source package</th><th>Expected requirement</th></tr></thead><tbody>$($testGapRows -join "`n")</tbody></table></div></section>
</main>
<script>
const search=document.getElementById('search'),match=document.getElementById('matchFilter'),scenario=document.getElementById('scenarioFilter');
function filterRows(){for(const row of document.querySelectorAll('#matrix tbody tr')){const text=row.innerText.toLowerCase();row.hidden=(search.value&& !text.includes(search.value.toLowerCase()))||(match.value&&row.dataset.match!==match.value)||(scenario.value&&row.dataset.scenario!==scenario.value);}}
search.addEventListener('input',filterRows);match.addEventListener('change',filterRows);scenario.addEventListener('change',filterRows);
</script>
</body>
</html>
"@
$htmlPath = Join-Path $OutputDirectory "POC-AI-Segment-103-BR-Coverage-Ratio-Report.html"
$html | Set-Content -Path $htmlPath -Encoding utf8

Write-Output "AI BRs: $($summary.aiRequirements); Test Solution BRs: $($summary.testSolutionRequirements)."
Write-Output "Confirmed BR coverage: $($summary.confirmedBrCoveragePercent)%."
Write-Output "Missing AI scenarios: $($summary.aiRequirementsMissingScenario)."
Write-Output "Report: $markdownPath"
Write-Output "Crosswalk: $jsonPath"
Write-Output "Visual report: $htmlPath"
