param(
    [string[]]$Segments = @('100','101','102','103','104','111','123','130','135'),
    [string]$ReportsDirectory = (Join-Path $PSScriptRoot 'test-output\ai-artifacts\coverage-reports'),
    [string]$OutputDirectory = (Join-Path $ReportsDirectory 'transaction-code-0-sale-purchase')
)

$transactionCodeRule = [ordered]@{
    transactionCode = '0'
    meaning = 'Approved - Purchase/Capture'
    specification = 'ATL105'
    specificationVersion = '2026-3'
    source = 'docs/specs/extracted_text.txt'
    sourceLocation = 'Page 397 / extracted_text.txt around lines 21223-21225'
    validationStatus = 'REVIEW_REQUIRED'
}

$candidates = @()
foreach ($segment in $Segments) {
    $crosswalkPath = Join-Path $ReportsDirectory "POC-AI-Segment-$segment-BR-Coverage-Crosswalk.json"
    if (-not (Test-Path $crosswalkPath)) { continue }
    $crosswalk = Get-Content $crosswalkPath -Raw | ConvertFrom-Json
    foreach ($record in @($crosswalk.aiToTestSolution)) {
        $aiText = "$( $record.aiStatement ) $( $record.aiSourceRuleId )"
        $testText = "$( $record.testRequirementTitle )"
        $searchText = "$aiText $testText"
        $aiMatchedTerms = @([regex]::Matches($aiText, '(?i)purchase/capture|pos purchase|purchase transaction|purchase|sale') | ForEach-Object Value | Select-Object -Unique)
        $testMatchedTerms = @([regex]::Matches($testText, '(?i)purchase/capture|pos purchase|purchase transaction|purchase|sale') | ForEach-Object Value | Select-Object Value -Unique)
        $matchedTerms = @($aiMatchedTerms + $testMatchedTerms | Select-Object -Unique)
        if ($matchedTerms.Count -eq 0) { continue }
        $evidenceSource = if ($aiMatchedTerms.Count -gt 0 -and $testMatchedTerms.Count -gt 0) { 'AI_AND_TEST_SOLUTION' } elseif ($aiMatchedTerms.Count -gt 0) { 'AI_REQUIREMENT' } else { 'TEST_SOLUTION_MATCH' }
        $candidateType = if ($searchText -match '(?i)purchase/capture|pos purchase') { 'PURCHASE_CAPTURE_TERM' } elseif ($searchText -match '(?i)\bsale\b') { 'SALE_TERM' } else { 'PURCHASE_TERM' }
        $candidates += [PSCustomObject][ordered]@{
            segment = $segment
            aiRequirementId = $record.aiRequirementId
            aiSourceRuleId = $record.aiSourceRuleId
            sourcePage = $record.aiSourcePage
            scope = $record.aiScope
            confidence = $record.aiConfidence
            aiStatement = $record.aiStatement
            matchedTerms = ($matchedTerms -join ', ')
            evidenceSource = $evidenceSource
            candidateType = $candidateType
            transactionCode = '0'
            transactionMeaning = 'Approved - Purchase/Capture'
            code0Evidence = 'The ATL105 specification defines Transaction Code 0 as Approved - Purchase/Capture; this BR is a Sale/Purchase candidate based on its extracted wording and still requires context validation.'
            scenarioStatus = $record.scenarioStatus
            scenarioCount = $record.scenarioCount
            scenarioIds = $record.scenarioIds
            testMatchStatus = $record.testMatchStatus
            matchReason = $record.matchReason
            testSolutionAction = $record.testSolutionAction
            testRequirementId = $record.testRequirementId
            testRequirementTitle = $record.testRequirementTitle
            reviewStatus = 'REVIEW_REQUIRED'
            reviewDecision = 'Validate transaction context, confirm code 0 applicability, then accept existing mapping or create a Test Solution BR/TS/TC/TD chain. Do not auto-add.'
        }
    }
}

$candidates = @($candidates | Sort-Object {[int]$_.segment}, aiRequirementId -Unique)
New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null

$summary = [ordered]@{
    reportTitle = 'ATL105 Transaction Code 0 Sale/Purchase BR Matching Report'
    generatedAt = (Get-Date -Format 'o')
    includedSegments = $Segments
    transactionCode = $transactionCodeRule
    candidateCount = $candidates.Count
    directTransactionCode0Matches = @($candidates | Where-Object { $_.aiStatement -match '(?i)transaction code\s*[:=]?\s*0\b' -or $_.testRequirementTitle -match '(?i)transaction code\s*[:=]?\s*0\b' }).Count
    transactionCodeEvidenceNote = 'No current AI/Test Solution BR candidate explicitly states Transaction Code 0. Code 0 is authoritative ATL105 context (Approved - Purchase/Capture); candidate rows require validation to determine whether that context applies.'
    candidateSegments = @($candidates | Group-Object segment | ForEach-Object { [ordered]@{ segment = $_.Name; count = $_.Count } })
    candidateTypes = @($candidates | Group-Object candidateType | ForEach-Object { [ordered]@{ type = $_.Name; count = $_.Count } })
    matchStatuses = @($candidates | Group-Object testMatchStatus | ForEach-Object { [ordered]@{ status = $_.Name; count = $_.Count } })
    reviewStatus = 'REVIEW_REQUIRED'
    automaticTestSolutionAddition = $false
}

$jsonPath = Join-Path $OutputDirectory 'atl105-transaction-code-0-sale-purchase-br-matching.json'
[ordered]@{ summary = $summary; candidates = $candidates } | ConvertTo-Json -Depth 20 | Set-Content $jsonPath -Encoding utf8

$markdown = @(
    '# ATL105 Transaction Code 0 Sale/Purchase BR Matching Report',
    '',
    '## Summary',
    '',
    "- AI/Test Solution Sale/Purchase candidates: **$($summary.candidateCount)**",
    "- Direct Transaction Code 0 references: **$($summary.directTransactionCode0Matches)**",
    "- Purchase/Capture candidates: **$(@($candidates | Where-Object candidateType -eq 'PURCHASE_CAPTURE_TERM').Count)**",
    "- Purchase candidates: **$(@($candidates | Where-Object candidateType -eq 'PURCHASE_TERM').Count)**",
    "- Sale candidates: **$(@($candidates | Where-Object candidateType -eq 'SALE_TERM').Count)**",
    "- Confirmed heuristic matches requiring validation: **$(@($candidates | Where-Object testMatchStatus -match '^MATCHED').Count)**",
    "- Potential matches requiring review: **$(@($candidates | Where-Object testMatchStatus -eq 'POTENTIAL_MATCH_REVIEW_REQUIRED').Count)**",
    "- Unmatched AI candidates: **$(@($candidates | Where-Object testMatchStatus -eq 'UNMATCHED_IN_TEST_SOLUTION').Count)**",
    '- **Accepted Transaction Code 0 coverage: 0%**',
    '- **All candidates: REVIEW_REQUIRED**',
    '',
    '## Scope',
    '',
    '- Transaction Code: `0`',
    '- Meaning: **Approved - Purchase/Capture**',
    '- Authority: ATL105 2026-3, `docs/specs/extracted_text.txt`, Page 397 / extracted text around lines 21223-21225',
    "- Candidate BRs found: **$($summary.candidateCount)**",
    "- Direct AI/Test Solution references to Transaction Code 0: **$($summary.directTransactionCode0Matches)**",
    '- Status: **REVIEW_REQUIRED**',
    '',
    'This report identifies BRs whose AI wording contains Sale, Purchase, POS Purchase, or Purchase/Capture terminology. The wording is a candidate signal, not proof that Transaction Code 0 applies. Each row must be validated against the transaction context and source rule before it is accepted or added to the Test Solution.',
    '',
    '## Candidate Summary',
    '',
    '| Segment | Candidates |',
    '|---:|---:|'
)
foreach ($group in @($summary.candidateSegments)) { $markdown += "| $($group.segment) | $($group.count) |" }
$markdown += @('', '## BR Matching Detail', '', '| Segment | AI BR | Source rule | Page | Scope | Evidence source | Candidate type | Matched terms | Code 0 evidence | Scenario | Match status | Match reason | Test Solution BR | Review status | Full AI requirement |', '|---:|---|---|---:|---|---|---|---|---|---|---|---|---|---|---|')
foreach ($record in $candidates) {
    $statement = $record.aiStatement -replace '\|', '\\|' -replace "`r?`n", ' '
    $reason = $record.matchReason -replace '\|', '\\|'
    $evidence = $record.code0Evidence -replace '\|', '\\|'
    $markdown += "| $($record.segment) | $($record.aiRequirementId) | $($record.aiSourceRuleId) | $($record.sourcePage) | $($record.scope) | $($record.evidenceSource) | $($record.candidateType) | $($record.matchedTerms) | $evidence | $($record.scenarioStatus) ($($record.scenarioIds)) | $($record.testMatchStatus) | $reason | $($record.testRequirementId) | $($record.reviewStatus) | $statement |"
}
$markdownPath = Join-Path $OutputDirectory 'atl105-transaction-code-0-sale-purchase-br-matching.md'
$markdown | Set-Content $markdownPath -Encoding utf8

$htmlRows = foreach ($record in $candidates) {
    $encode = { param($value) [System.Net.WebUtility]::HtmlEncode([string]$value) }
    "<tr><td>$(& $encode $record.segment)</td><td><code>$(& $encode $record.aiRequirementId)</code><small>$(& $encode $record.aiSourceRuleId)</small></td><td>$(& $encode $record.sourcePage)</td><td>$(& $encode $record.scope)</td><td>$(& $encode $record.evidenceSource)</td><td>$(& $encode $record.candidateType)</td><td>$(& $encode $record.matchedTerms)</td><td>$(& $encode $record.testMatchStatus)</td><td>$(& $encode $record.matchReason)</td><td><code>$(& $encode $record.testRequirementId)</code><small>$(& $encode $record.testRequirementTitle)</small></td><td>$(& $encode $record.scenarioStatus) $(& $encode $record.scenarioIds)</td><td>REVIEW_REQUIRED</td><td>$(& $encode $record.aiStatement)</td></tr>"
}
$htmlPath = Join-Path $OutputDirectory 'atl105-transaction-code-0-sale-purchase-br-matching.html'
@"
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>ATL105 Transaction Code 0 Sale/Purchase BR Matching</title>
<style>
:root{--ink:#132238;--muted:#5b6777;--paper:#f6f3eb;--surface:#fff;--line:#d8d0c1;--teal:#087e8b;--green:#2e8b57;--gold:#bd7a00;--red:#b74242;--navy:#235789}
*{box-sizing:border-box}body{margin:0;background:var(--paper);color:var(--ink);font:15px Georgia,'Times New Roman',serif;line-height:1.45}header{padding:42px max(24px,calc((100vw - 1600px)/2));background:var(--ink);color:#fff;border-bottom:7px solid var(--teal)}h1,h2{font-family:Impact,Haettenschweiler,'Arial Narrow Bold',sans-serif;font-weight:400;letter-spacing:0}h1{margin:0;font-size:38px;line-height:1}h2{font-size:24px;margin:0 0 12px}header p{max-width:1000px;color:#d6e6ea;margin:14px 0 0}main{max-width:1600px;margin:0 auto;padding:28px 24px 60px}.notice{padding:14px 17px;border-left:5px solid var(--gold);background:#fff7e6;margin-bottom:24px}.stats{display:grid;grid-template-columns:repeat(6,minmax(145px,1fr));gap:12px;margin-bottom:28px}.stat{background:var(--surface);border:1px solid var(--line);padding:16px;min-height:110px}.stat strong{display:block;font:34px/1 Impact,sans-serif;color:var(--navy)}.stat.warn strong{color:var(--gold)}.stat.danger strong{color:var(--red)}.stat span{color:var(--muted);font-size:13px}.panel{background:var(--surface);border:1px solid var(--line);padding:20px;margin:26px 0}.filters{display:flex;flex-wrap:wrap;gap:10px;margin:12px 0}input,select{font:inherit;padding:8px;border:1px solid var(--line);background:#fff}input{min-width:320px}.table-wrap{overflow:auto;border:1px solid var(--line);max-height:720px}table{width:100%;border-collapse:collapse;min-width:1800px;background:#fff}th{position:sticky;top:0;background:#e8eee9;text-align:left;font-size:12px;z-index:1}th,td{padding:9px;border-bottom:1px solid #e6e0d6;vertical-align:top}td{font-size:13px}tr:nth-child(even){background:#fbfaf7}code{display:block;color:#174a6e;font:12px Consolas,monospace}small{display:block;color:var(--muted)}
</style>
</head>
<body>
<header><h1>ATL105 Transaction Code 0 Sale/Purchase BR Matching</h1><p>Transaction Code 0 means Approved - Purchase/Capture. This report identifies candidate BRs across segments; it does not certify code-0 applicability or add AI requirements to the Test Solution.</p></header>
<main>
<div class="notice"><strong>Review gate:</strong> Sale/Purchase wording is a candidate signal. Validate source context, transaction type, code-0 applicability, BR-to-TS-to-TC-to-TD traceability, and independent test-data execution. All rows remain <strong>REVIEW_REQUIRED</strong>; automatic Test Solution addition is disabled.</div>
<div class="stats"><div class="stat"><strong>$($summary.candidateCount)</strong><span>Sale/Purchase candidates</span></div><div class="stat"><strong>$($summary.directTransactionCode0Matches)</strong><span>Direct code-0 references</span></div><div class="stat"><strong>$(@($candidates | Where-Object testMatchStatus -match '^MATCHED').Count)</strong><span>Heuristic matches</span></div><div class="stat warn"><strong>$(@($candidates | Where-Object testMatchStatus -eq 'POTENTIAL_MATCH_REVIEW_REQUIRED').Count)</strong><span>Potential matches</span></div><div class="stat danger"><strong>$(@($candidates | Where-Object testMatchStatus -eq 'UNMATCHED_IN_TEST_SOLUTION').Count)</strong><span>Unmatched candidates</span></div><div class="stat danger"><strong>0%</strong><span>Accepted code-0 coverage</span></div></div>
<section class="panel"><h2>Candidate BR Matching Matrix</h2><div class="filters"><input id="search" placeholder="Search segment, BR, source rule, terms, reason, or requirement"><select id="segmentFilter"><option value="">All segments</option>$((@($candidates | Select-Object -ExpandProperty segment -Unique | Sort-Object) | ForEach-Object { "<option value='$_'>Segment $_</option>" }) -join '')</select><select id="statusFilter"><option value="">All match statuses</option><option value="matched_element_and_semantics">Heuristic element and semantics</option><option value="matched_semantics_only">Heuristic semantics only</option><option value="potential_match_review_required">Potential review required</option><option value="unmatched_in_test_solution">Unmatched</option></select></div><div class="table-wrap"><table id="matrix"><thead><tr><th>Segment</th><th>AI BR / source rule</th><th>Page</th><th>Scope</th><th>Evidence source</th><th>Candidate type</th><th>Matched terms</th><th>Match status</th><th>Match reason</th><th>Test Solution BR</th><th>Scenario</th><th>Review status</th><th>Full AI requirement</th></tr></thead><tbody>$($htmlRows -join "`n")</tbody></table></div></section>
</main>
<script>
const search=document.getElementById('search'),segment=document.getElementById('segmentFilter'),status=document.getElementById('statusFilter');
function filterRows(){for(const row of document.querySelectorAll('#matrix tbody tr')){const text=row.innerText.toLowerCase();row.hidden=(search.value&&!text.includes(search.value.toLowerCase()))||(segment.value&&row.children[0].innerText!==segment.value)||(status.value&&!row.innerText.toLowerCase().includes(status.value.replaceAll('_',' ').toLowerCase()));}}
search.addEventListener('input',filterRows);segment.addEventListener('change',filterRows);status.addEventListener('change',filterRows);
</script>
</body>
</html>
"@ | Set-Content $htmlPath -Encoding utf8

Write-Output "Candidates: $($summary.candidateCount)"
Write-Output "JSON: $jsonPath"
Write-Output "Markdown: $markdownPath"
Write-Output "HTML: $htmlPath"
