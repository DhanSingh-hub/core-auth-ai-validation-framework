param(
    [string]$ReportsDirectory = (Join-Path $PSScriptRoot 'test-output\ai-artifacts\coverage-reports'),
    [string]$OutputDirectory = (Join-Path $ReportsDirectory 'all-segments')
)

$files = @(Get-ChildItem $ReportsDirectory -File -Filter 'POC-AI-Segment-*-BR-Coverage-Crosswalk.json' | Sort-Object Name)
if ($files.Count -eq 0) { throw "No segment crosswalk files found in '$ReportsDirectory'." }
$records = @()
$segmentSummaries = @()

foreach ($file in $files) {
    $document = Get-Content $file.FullName -Raw | ConvertFrom-Json
    $segment = [regex]::Match($file.BaseName, 'Segment-(\d+)-').Groups[1].Value
    if ([string]::IsNullOrWhiteSpace($segment)) { continue }
    $segmentSummaries += [PSCustomObject][ordered]@{
        segment = $segment
        aiRequirements = [int]$document.summary.aiRequirements
        testSolutionRequirements = [int]$document.summary.testSolutionRequirements
        confirmedMatches = [int]$document.summary.matchedElementAndSemantics + [int]$document.summary.matchedSemanticsOnly
        baselineCovered = [int]$document.summary.testSolutionRequirementsCovered
        potentialMatches = [int]$document.summary.potentialMatches
        unmatchedAiRequirements = [int]$document.summary.unmatchedAiRequirements
        missingScenarios = [int]$document.summary.aiRequirementsMissingScenario
        reviewQueue = if ($null -ne $document.summary.aiRequirementsRequiringTestSolutionReview) { [int]$document.summary.aiRequirementsRequiringTestSolutionReview } else { [int]$document.summary.potentialMatches + [int]$document.summary.unmatchedAiRequirements }
        baselineCoveragePercent = $document.summary.confirmedBrCoveragePercent
    }
    foreach ($sourceRecord in @($document.aiToTestSolution)) {
        $records += [PSCustomObject][ordered]@{
            segment = $segment
            aiRequirementId = $sourceRecord.aiRequirementId
            aiSourceRuleId = $sourceRecord.aiSourceRuleId
            sourcePage = $sourceRecord.aiSourcePage
            aiScope = $sourceRecord.aiScope
            aiConfidence = $sourceRecord.aiConfidence
            scenarioStatus = $sourceRecord.scenarioStatus
            scenarioIds = $sourceRecord.scenarioIds
            testMatchStatus = $sourceRecord.testMatchStatus
            matchReason = $sourceRecord.matchReason
            testSolutionAction = $sourceRecord.testSolutionAction
            testRequirementId = $sourceRecord.testRequirementId
            testRequirementTitle = $sourceRecord.testRequirementTitle
            sharedElements = if ($null -ne $sourceRecord.sharedElements) { $sourceRecord.sharedElements } else { '' }
            semanticScore = $sourceRecord.semanticScore
            aiStatement = $sourceRecord.aiStatement
            transactionType = if ($null -ne $sourceRecord.transactionType) { $sourceRecord.transactionType } else { 'Not available' }
            responseType = if ($null -ne $sourceRecord.responseType) { $sourceRecord.responseType } else { 'Not available' }
            singleStepFlow = if ($null -ne $sourceRecord.singleStepFlow) { $sourceRecord.singleStepFlow } else { 'Not available' }
            multiStepFlow = if ($null -ne $sourceRecord.multiStepFlow) { $sourceRecord.multiStepFlow } else { 'Not available' }
            messageTemplate = if ($null -ne $sourceRecord.messageTemplate) { $sourceRecord.messageTemplate } else { 'Not available' }
            cardType = if ($null -ne $sourceRecord.cardType) { $sourceRecord.cardType } else { 'Not available' }
            network = if ($null -ne $sourceRecord.network) { $sourceRecord.network } else { 'Not available' }
        }
    }
}

$records = @($records | Sort-Object { [int]$_.segment }, aiRequirementId)
$segmentSummaries = @($segmentSummaries | Sort-Object { [int]$_.segment })
$allAi = ($segmentSummaries | Measure-Object aiRequirements -Sum).Sum
$allTest = ($segmentSummaries | Measure-Object testSolutionRequirements -Sum).Sum
$allMatches = ($segmentSummaries | Measure-Object confirmedMatches -Sum).Sum
$allCovered = ($segmentSummaries | Measure-Object baselineCovered -Sum).Sum
$allPotential = ($segmentSummaries | Measure-Object potentialMatches -Sum).Sum
$allUnmatched = ($segmentSummaries | Measure-Object unmatchedAiRequirements -Sum).Sum
$allMissing = ($segmentSummaries | Measure-Object missingScenarios -Sum).Sum
$allReview = ($segmentSummaries | Measure-Object reviewQueue -Sum).Sum
$allCoverage = if ($allTest -eq 0) { 0 } else { [math]::Round(100 * $allCovered / $allTest, 1) }
$summary = [ordered]@{
    reportTitle = 'All Segments BR Coverage Ratio: AI Solution vs Test Solution'
    generatedAt = (Get-Date -Format 'o')
    segments = @($segmentSummaries.segment)
    aiRequirements = $allAi
    testSolutionRequirements = $allTest
    confirmedMatches = $allMatches
    baselineCovered = $allCovered
    potentialMatches = $allPotential
    unmatchedAiRequirements = $allUnmatched
    missingScenarios = $allMissing
    reviewQueue = $allReview
    confirmedBaselineCoveragePercent = $allCoverage
    interpretation = 'Confirmed Test Solution baseline coverage is weighted across segment baselines. AI-only and heuristic matches remain review candidates.'
}

New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null
$jsonPath = Join-Path $OutputDirectory 'all-segments-br-ratio.json'
[ordered]@{ summary = $summary; segmentSummaries = $segmentSummaries; records = $records } | ConvertTo-Json -Depth 20 | Set-Content $jsonPath -Encoding utf8

function Escape([object]$Value) { [System.Net.WebUtility]::HtmlEncode([string]$Value) }
$segmentInventory = @(
    '100|Standard Message Data Segment','101|Fleet Data Segment','102|Product Code Data Segment','103|EBT Data Segment','104|Purchase Card Data Segment','105|Totals Data Segment','108|Loyalty Card Data Segment','109|Electronic Mail Data Segment','110|Check Data Segment','111|Variable Information Data Segment','112|Additional Information Data Segment','113|ECA/TeleCheck Data Segment','114|SKU Data Segment','115|Print Data Segment','118|Proprietary Data Load Segment','119|Totals with Proprietary Data Load Data Segment','120|Print Data 2 Segment','123|NFC Payment Tokenization Data Segment','130|EMV Request Data Segment','131|EMV Response Data Segment','132|CA Public Key File Segment','134|Transaction Attributes Data Segment','135|Moneris Data Request Segment','136|Moneris Data Response Segment','139|Moneris Day End Batch Balance Request Segment','140|Moneris Day End Batch Balance Response Segment','141|Moneris Day End Batch Close Request Segment','142|Moneris Day End Batch Close Response Segment','143|Tax by Product Data Segment','145|Enhanced Fleet Request Segment','146|Enhanced Fleet Response Segment','148|WEX Available Product Fleet Information Data Segment','149|Fuel Price Update Segment','150|Fuel Price Update Response Segment','151|InComm OTC Market Basket Data Input Segment','152|InComm OTC Market Basket Data Segment','153|Network Token Data Request Segment','155|Real Time Account Updater Response Data Segment','156|EV Charging Data Segment','157|Adjusted Product Code Data Segment','DL1|Merchant Data Segment','DL2|Dial String Data Segment','DL3|Date and Time Data Segment','DL4|Software Dial Load Data Segment','DL5|Software IP Load Data Segment','DL6|Store and Forward Data Segment','DL7|Supplemental Terminal Data Segment','DL8|EMV Terminal Floor Limits Data Segment'
)
$transactionInventory = @(
    '0|POS Purchase/Capture','3|POS Authorization Only','4|Customer-activated Purchase/Capture','5|Customer-activated Authorization Only','6|Mail/Phone Purchase','7|Merchandise Return','8|Purchase Reversal','9|Special Transactions','A|Account Verification','B|Mail/Phone Authorization Only','C|Mail/Phone Reversal','D|RBC Loyalty Lookup','E|Balance Inquiry','K|Activate','L|Deactivate','M|Balance Merge','N|Replace Card','Q|Recharge Card','S|Cancellation','T|Token Registration','U|Void of Merchandise Return','V|Loyalty Advice Function','Z|Time-out Reversal'
)
$segmentOptions = (@($segmentInventory | ForEach-Object { $parts=$_ -split '\|',2; "<option value='$($parts[0])'>Segment $($parts[0]) - $(Escape $parts[1])</option>" }) -join '')
$elementOptions = (@($records | ForEach-Object { $_.sharedElements -split ',' } | Where-Object { $_ } | Sort-Object -Unique | ForEach-Object { "<option value='$_'>Element $_</option>" }) -join '')
$transactionOptions = (@($records | ForEach-Object transactionType | Sort-Object -Unique | ForEach-Object { "<option value='$_'>$(Escape $_)</option>" }) -join '')
$flowOptions = (@($records | ForEach-Object { @($_.singleStepFlow, $_.multiStepFlow) } | Sort-Object -Unique | ForEach-Object { "<option value='$_'>$(Escape $_)</option>" }) -join '')
$templateOptions = (@($records | ForEach-Object messageTemplate | Sort-Object -Unique | ForEach-Object { "<option value='$_'>$(Escape $_)</option>" }) -join '')
$cardOptions = (@($records | ForEach-Object cardType | Sort-Object -Unique | ForEach-Object { "<option value='$_'>$(Escape $_)</option>" }) -join '')
$responseOptions = (@($records | ForEach-Object responseType | Sort-Object -Unique | ForEach-Object { "<option value='$_'>$(Escape $_)</option>" }) -join '')
$transactionOptions = (@($transactionInventory | ForEach-Object { $parts=$_ -split '\|',2; "<option value='$($parts[0])'>$(Escape $parts[0]) - $(Escape $parts[1])</option>" }) -join '')
$summaryRows = foreach ($item in $segmentSummaries) { "<tr data-segment='$($item.segment)'><td>$($item.segment)</td><td>$($item.aiRequirements)</td><td>$($item.testSolutionRequirements)</td><td>$($item.baselineCovered)</td><td>$($item.baselineCoveragePercent)%</td><td>$($item.reviewQueue)</td></tr>" }
$matrixRows = foreach ($record in $records) {
    "<tr data-segment='$($record.segment)' data-element='$($record.sharedElements)' data-status='$($record.testMatchStatus.ToLowerInvariant())' data-scenario='$($record.scenarioStatus.ToLowerInvariant())' data-review='review_required' data-transaction='$($record.transactionType)' data-response='$($record.responseType)' data-flow='$($record.singleStepFlow)|$($record.multiStepFlow)' data-template='$($record.messageTemplate)' data-card='$($record.cardType)' data-network='$($record.network)'><td>$($record.segment)</td><td><code>$(Escape $record.aiRequirementId)</code><small>$(Escape $record.aiSourceRuleId)</small></td><td>$(Escape $record.aiScope)</td><td>$(Escape $record.scenarioStatus)<small>$(Escape $record.scenarioIds)</small></td><td>$(Escape $record.testMatchStatus)</td><td>$(Escape $record.matchReason)</td><td><code>$(Escape $record.testRequirementId)</code><small>$(Escape $record.testRequirementTitle)</small></td><td>$(Escape $record.aiStatement)</td></tr>"
}

$csvPath = Join-Path $OutputDirectory 'all-segments-br-ratio.csv'
$records | Export-Csv $csvPath -NoTypeInformation -Encoding utf8
$htmlPath = Join-Path $OutputDirectory 'all-segments-br-ratio.html'
$chartRows = (@($segmentSummaries | ForEach-Object { "<div class='barrow'><b>Segment $($_.segment)</b><div class='bar'><i style='width:$($_.baselineCoveragePercent)%'></i></div><span>$($_.baselineCoveragePercent)%</span></div>" }) -join '')

@"
<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>All Segments BR Coverage Ratio</title><style>
:root{--ink:#132238;--muted:#5b6777;--paper:#f6f3eb;--surface:#fff;--line:#d8d0c1;--teal:#087e8b;--green:#2e8b57;--gold:#bd7a00;--red:#b74242;--navy:#235789}*{box-sizing:border-box}body{margin:0;background:var(--paper);color:var(--ink);font:15px Georgia,serif;line-height:1.45}header{padding:40px max(24px,calc((100vw - 1700px)/2));background:var(--ink);color:#fff;border-bottom:7px solid var(--teal)}h1,h2{font-family:Impact,Haettenschweiler,'Arial Narrow Bold',sans-serif;font-weight:400;letter-spacing:0}h1{margin:0;font-size:40px;line-height:1}h2{font-size:24px;margin:0 0 12px}header p{max-width:1100px;color:#d6e6ea;margin:14px 0 0}main{max-width:1700px;margin:auto;padding:26px 24px 60px}.notice{padding:14px 17px;border-left:5px solid var(--gold);background:#fff7e6;margin-bottom:24px}.stats{display:grid;grid-template-columns:repeat(6,minmax(140px,1fr));gap:12px}.stat{background:var(--surface);border:1px solid var(--line);padding:16px;min-height:105px}.stat strong{display:block;font:34px/1 Impact,sans-serif;color:var(--navy)}.stat.warn strong{color:var(--gold)}.stat.danger strong{color:var(--red)}.stat span{color:var(--muted);font-size:13px}.charts{display:grid;grid-template-columns:1fr 1fr;gap:18px}.panel{background:var(--surface);border:1px solid var(--line);padding:20px;margin:24px 0}.barrow{display:grid;grid-template-columns:190px 1fr 90px;gap:10px;align-items:center;margin:14px 0}.bar{height:24px;background:#e8e2d7}.bar i{display:block;height:100%;background:var(--green)}.filters{display:grid;grid-template-columns:repeat(4,minmax(170px,1fr));gap:10px}label{color:var(--muted);font-size:12px}select,input,button{font:inherit;padding:8px;border:1px solid var(--line);background:#fff;width:100%}.filtergroup{display:grid;gap:4px}.table-wrap{overflow:auto;border:1px solid var(--line);max-height:680px}table{width:100%;border-collapse:collapse;min-width:1350px;background:#fff}th{position:sticky;top:0;background:#e8eee9;text-align:left;font-size:12px;z-index:1}th,td{padding:9px;border-bottom:1px solid #e6e0d6;vertical-align:top}td{font-size:13px}tr:nth-child(even){background:#fbfaf7}tr[data-status*=potential],tr[data-status*=unmatched]{background:#fff3e3}code{display:block;color:#174a6e;font:12px Consolas,monospace}small{display:block;color:var(--muted)}.reviewbox{border-left:5px solid var(--red);background:#fff0ef;padding:14px}.drawer{position:fixed;inset:0;background:#13223866;display:none;z-index:5}.drawer.open{display:block}.drawerbody{position:absolute;right:0;top:0;height:100%;width:min(680px,94vw);background:#fff;overflow:auto;padding:24px;border-left:5px solid var(--teal)}.detailgrid{display:grid;grid-template-columns:150px 1fr;gap:8px;border-top:1px solid var(--line);padding-top:12px}.detailgrid b{color:var(--muted)}.close{float:right;width:auto}@media(max-width:900px){.stats,.charts,.filters{grid-template-columns:1fr 1fr}}@media print{header{padding:18px;color:#000;background:#fff;border-bottom:2px solid #000}.filters,.no-print,.drawer{display:none!important}.panel{break-inside:avoid;border:1px solid #777}body{background:#fff}}
+</style></head><body><header><h1>All Segments BR Coverage Ratio</h1><p>Executive summary and analyst detail view. Default scope: <strong>Segment → All Segments</strong>. Generated $($summary.generatedAt).</p></header><main><div class="notice"><strong>Review gate:</strong> <strong>Confirmed Test Solution baseline coverage</strong> is weighted across segment baselines. Heuristic matches, AI-only requirements, missing scenarios, and unmatched requirements remain review items. No AI requirement is automatically added.</div><section class="stats"><div class="stat"><strong>$allTest</strong><span>Test Solution BRs</span></div><div class="stat"><strong>$allAi</strong><span>AI BRs</span></div><div class="stat"><strong>$allCoverage%</strong><span>Confirmed Test Solution baseline coverage</span></div><div class="stat danger"><strong>$allReview</strong><span>Review queue</span></div><div class="stat warn"><strong>$allMissing</strong><span>Missing scenarios</span></div><div class="stat danger"><strong>$allUnmatched</strong><span>Unmatched AI BRs</span></div></section><section class="panel charts"><div><h2>Segment Baseline Coverage</h2>$chartRows</div><div><h2>Review Workload</h2><div class="barrow"><b>Potential matches</b><div class="bar"><i style="width:$([math]::Round(100*$allPotential/[math]::Max(1,$allReview),1))%;background:var(--gold)"></i></div><span>$allPotential</span></div><div class="barrow"><b>Unmatched AI BRs</b><div class="bar"><i style="width:$([math]::Round(100*$allUnmatched/[math]::Max(1,$allReview),1))%;background:var(--red)"></i></div><span>$allUnmatched</span></div><div class="reviewbox"><strong>All unresolved items require review.</strong><br>Validate source, context, semantic equivalence, scenario, test case, and test data before acceptance.</div></div></section><section class="panel"><h2>Segment Summary</h2><div class="table-wrap"><table><thead><tr><th>Segment</th><th>AI BRs</th><th>Test BRs</th><th>Baseline covered</th><th>Coverage</th><th>Review queue</th></tr></thead><tbody>$($summaryRows -join "`n")</tbody></table></div></section><section class="panel"><h2>Analyst BR Matching Matrix</h2><div class="filters no-print"><div class="filtergroup"><label>View by</label><select id="viewBy"><option value="segment">Segment</option><option value="element">Element</option></select></div><div class="filtergroup"><label>Segment</label><select id="segmentFilter"><option value="">All Segments</option>$segmentOptions</select></div><div class="filtergroup"><label>Transaction Type</label><select id="transactionFilter"><option value="">All Transaction Types</option><option>Not available</option></select></div><div class="filtergroup"><label>Response Type</label><select id="responseFilter"><option value="">All Response Types</option><option>Not available</option></select></div><div class="filtergroup"><label>Single-Step Transaction Flows</label><select id="singleFilter"><option value="">All Single-Step Flows</option><option>Not available</option></select></div><div class="filtergroup"><label>Multi-Step Transaction Flows</label><select id="multiFilter"><option value="">All Multi-Step Flows</option><option>Not available</option></select></div><div class="filtergroup"><label>Message Templates</label><select id="templateFilter"><option value="">All Message Templates</option><option>Not available</option></select></div><div class="filtergroup"><label>Card Type</label><select id="cardFilter"><option value="">All Card Types</option><option>Not available</option></select></div><div class="filtergroup"><label>Network Filters</label><select id="networkFilter"><option value="">All Networks</option><option>Not available</option></select></div><div class="filtergroup"><label>Matrix view</label><select id="matrixFilter"><option value="all">All BRs</option><option value="review">REVIEW_REQUIRED</option><option value="gaps">Unmatched and potential</option></select></div><div class="filtergroup"><label>Search</label><input id="search" placeholder="BR, source rule, reason, requirement"></div><div class="filtergroup"><label>Export</label><button id="csvButton" type="button">Download CSV</button><button id="printButton" type="button">Print report</button></div></div><div class="table-wrap"><table id="matrix"><thead><tr><th>Segment</th><th>AI BR / source rule</th><th>Scope</th><th>Scenario</th><th>Match status</th><th>Match reason</th><th>Test Solution BR</th><th>AI requirement</th></tr></thead><tbody>$($matrixRows -join "`n")</tbody></table></div></section></main><div id="drawer" class="drawer"><aside class="drawerbody"><button class="close" id="closeDrawer">Close</button><h2>BR Detail</h2><div id="detail"></div></aside></div><script>
+const rows=[...document.querySelectorAll('#matrix tbody tr')],search=document.getElementById('search');const controls={segment:'segmentFilter',transaction:'transactionFilter',response:'responseFilter',single:'singleFilter',multi:'multiFilter',template:'templateFilter',card:'cardFilter',network:'networkFilter'};function apply(){const q=search.value.toLowerCase(),mode=document.getElementById('matrixFilter').value;for(const r of rows){const t=r.innerText.toLowerCase(),gap=r.dataset.status.includes('potential')||r.dataset.status.includes('unmatched');const okMode=mode==='all'||mode==='review'||(mode==='gaps'&&gap);const okSearch=!q||t.includes(q);const okFilters=Object.entries(controls).every(([key,id])=>{const v=document.getElementById(id).value;return !v||r.dataset[key]===v});r.hidden=!(okMode&&okSearch&&okFilters)}}document.querySelectorAll('select,input').forEach(e=>e.addEventListener('input',apply));document.getElementById('printButton').addEventListener('click',()=>window.print());document.getElementById('csvButton').addEventListener('click',()=>{const lines=[['Segment','AI BR','Source rule','Scope','Scenario status','Match status','Match reason','Test Solution BR','AI requirement']];for(const r of rows)if(!r.hidden)lines.push([...r.children].map(c=>'"'+c.innerText.replaceAll('"','""')+'"'));const blob=new Blob([lines.map(x=>x.join(',')).join('\n')],{type:'text/csv'}),a=document.createElement('a');a.href=URL.createObjectURL(blob);a.download='all-segments-br-ratio.csv';a.click();URL.revokeObjectURL(a.href)});const drawer=document.getElementById('drawer'),detail=document.getElementById('detail');rows.forEach(r=>r.addEventListener('click',()=>{const c=[...r.children];detail.innerHTML='<div class="detailgrid"><b>Segment</b><span>'+c[0].innerText+'</span><b>AI BR</b><span>'+c[1].innerText+'</span><b>Scope</b><span>'+c[2].innerText+'</span><b>Scenario</b><span>'+c[3].innerText+'</span><b>Match status</b><span>'+c[4].innerText+'</span><b>Match reason</b><span>'+c[5].innerText+'</span><b>Test Solution BR</b><span>'+c[6].innerText+'</span><b>AI requirement</b><span>'+c[7].innerText+'</span><b>Decision</b><span>REVIEW_REQUIRED. Validate BR → TS → TC → TD before acceptance.</span></div>';drawer.classList.add('open')}));document.getElementById('closeDrawer').addEventListener('click',()=>drawer.classList.remove('open'));drawer.addEventListener('click',e=>{if(e.target===drawer)drawer.classList.remove('open')});apply();</script></body></html>
"@ | Set-Content $htmlPath -Encoding utf8

$html = Get-Content $htmlPath -Raw
$html = $html.Replace('&#8594;', '&rarr;')
$brokenArrow = ([string][char]0x00e2) + ([string][char]0x2020) + ([string][char]0x2019)
$html = $html.Replace($brokenArrow, '&rarr;')
$html = [regex]::Replace($html, '(?s)(<div class="trace-flow">.*?<div class="reviewbox")', { param($match) $match.Value -replace '<b>[^<]*</b>', '<b>&rarr;</b>' })
$traceStyles = '.trace-flow{display:flex;align-items:stretch;gap:12px;margin:16px 0 18px}.trace-flow>div{flex:1}.trace-flow>div:first-child,.trace-flow>div:nth-of-type(2),.trace-flow>div:nth-of-type(3){border:1px solid var(--teal);background:#f7fbfb;padding:18px 12px;text-align:center;min-height:88px;display:flex;flex-direction:column;justify-content:center}.trace-flow>div strong{font:32px/1 Impact,sans-serif;color:var(--navy);display:block}.trace-flow>div span{color:var(--muted);font-size:14px;margin-top:8px}.trace-flow>b{align-self:center;color:var(--teal);font-size:26px}@media(max-width:900px){.trace-flow{flex-direction:column}.trace-flow>b{transform:rotate(90deg)}}'
$html = $html.Replace('</style>', $traceStyles + '</style>')
$parallelStyles = '.coverage-glance{display:block}.coverage-glance>h2{margin-bottom:18px}.parallel-panels{display:grid;grid-template-columns:1fr 1fr;gap:24px}.flow-column{min-width:0}.flow-column>h3{font:22px Impact,Haettenschweiler,sans-serif;margin:0 0 14px;color:var(--ink)}@media(max-width:900px){.parallel-panels{grid-template-columns:1fr}}'
$html = $html.Replace('</style>', $parallelStyles + '</style>')
$html = $html.Replace('<section class="panel coverage-glance"><div><h2>Coverage At A Glance</h2>', '<section class="panel coverage-glance"><h2>Coverage At A Glance &amp; Traceability Flow</h2><div class="parallel-panels"><div class="flow-column"><h3>Coverage At A Glance</h3>')
$html = $html.Replace('</div><div><h2>Traceability Flow</h2>', '</div><div class="flow-column"><h3>Traceability Flow</h3>')
$html = $html.Replace('</div></section><details class="panel charts"', '</div></div></section><details class="panel charts"')
$allLinkedScenarios = $allAi - $allMissing
$allBaselineGap = $allTest - $allCovered
$coverageGlance = @"
<section class="panel coverage-glance"><div><h2>Coverage At A Glance</h2><div class="barrow"><b>AI BR mapping</b><div class="bar"><i style="width:$([math]::Round(100*$allMatches/[math]::Max(1,$allAi),1))%"></i></div><span>$allMatches confirmed / $allPotential review</span></div><div class="barrow"><b>Expected BR coverage</b><div class="bar"><i style="width:$([math]::Round(100*$allCovered/[math]::Max(1,$allTest),1))%"></i></div><span>$allCovered covered / $allBaselineGap gap</span></div><div class="barrow"><b>AI scenario linkage</b><div class="bar"><i style="width:$([math]::Round(100*$allLinkedScenarios/[math]::Max(1,$allAi),1))%"></i></div><span>$allLinkedScenarios review-required / $allMissing missing</span></div><p class="chart-note"><strong>Legend:</strong> confirmed | review required | missing/gap | linked but uncertified</p></div><div><h2>Traceability Flow</h2><div class="trace-flow"><div><strong>$allAi</strong><span>AI BRs</span></div><b>&rarr;</b><div><strong>$allLinkedScenarios</strong><span>linked scenarios</span></div><b>&rarr;</b><div><strong>$allMatches</strong><span>confirmed BR matches</span></div></div><div class="reviewbox"><strong>Scenario correctness:</strong> no scenario is execution-certified. Linked scenarios require review due to POC review flags, ATL105 2025-3 version, and Level C response-oracle authority.</div></div></section>
"@
$coverageReplacement = '</section>' + $coverageGlance + '<details class="panel charts"'
$html = $html.Replace('</section><details class="panel charts"', $coverageReplacement)
$html = $html.Replace('<details class="panel charts"', $coverageGlance + '<details class="panel charts"')
$filterMarkup = @"
<div class="filters no-print"><div class="filtergroup"><label>View by</label><select id="viewBy"><option value="segment">Segment</option><option value="transaction">Transaction Type</option><option value="flow">Transaction Flow Steps</option><option value="template">Message Template</option><option value="card">Card Type</option><option value="response">Response Type</option></select></div><div class="filtergroup"><label id="scopeLabel">Segment</label><select id="segmentFilter"><option value="">All Segments</option>$segmentOptions</select></div><div class="filtergroup"><label>Matrix view</label><select id="matrixFilter"><option value="all">All BRs</option><option value="review">REVIEW_REQUIRED</option><option value="gaps">Unmatched and potential</option></select></div><div class="filtergroup"><label>Search</label><input id="search" placeholder="BR, source rule, reason, requirement"></div><div class="filtergroup"><label>Export</label><button id="csvButton" type="button">Download CSV</button><button id="printButton" type="button">Print report</button></div></div>
"@
$filterReplacement = $filterMarkup + '<div class="table-wrap"><table id="matrix">'
$html = [regex]::Replace($html, '(?s)<div class="filters no-print">.*?</div><div class="table-wrap"><table id="matrix">', $filterReplacement)
$scopeScript = @"
<script>
const rows=[...document.querySelectorAll('#matrix tbody tr')];
const search=document.getElementById('search');
const viewBy=document.getElementById('viewBy');
const scopeFilter=document.getElementById('segmentFilter');
const segmentOptions="<option value=''>All Segments</option>$segmentOptions";
const elementOptions="<option value=''>All Elements</option>$elementOptions";
const options={segment:segmentOptions,element:elementOptions,transaction:"<option value=''>All Transaction Types</option>$transactionOptions",flow:"<option value=''>All Transaction Flow Steps</option>$flowOptions",template:"<option value=''>All Message Templates</option>$templateOptions",card:"<option value=''>All Card Types</option>$cardOptions",response:"<option value=''>All Response Types</option>$responseOptions"};
const labels={segment:'Segment',element:'Element',transaction:'Transaction Type',flow:'Transaction Flow Steps',template:'Message Template',card:'Card Type',response:'Response Type'};
function populateScope(){scopeFilter.innerHTML=options[viewBy.value];scopeFilter.previousElementSibling.textContent=labels[viewBy.value];applyFilters();}
function applyFilters(){const query=search.value.toLowerCase();const matrixMode=document.getElementById('matrixFilter').value;for(const row of rows){const text=row.innerText.toLowerCase();const gap=row.dataset.status.includes('potential')||row.dataset.status.includes('unmatched');const scopeValue=scopeFilter.value;const key=viewBy.value;const rowValue=key==='element'?row.dataset.element:key==='flow'?row.dataset.flow.split('|'):row.dataset[key];const scopeMatch=!scopeValue||((Array.isArray(rowValue)?rowValue:rowValue.split(',')).includes(scopeValue));const modeMatch=matrixMode==='all'||matrixMode==='review'||(matrixMode==='gaps'&&gap);row.hidden=!(scopeMatch&&modeMatch&&(!query||text.includes(query)));}}
viewBy.addEventListener('change',populateScope);scopeFilter.addEventListener('change',applyFilters);search.addEventListener('input',applyFilters);document.getElementById('matrixFilter').addEventListener('change',applyFilters);populateScope();
document.querySelectorAll('select,input').forEach(control=>control.addEventListener('input',applyFilters));
document.getElementById('printButton').addEventListener('click',()=>window.print());
document.getElementById('csvButton').addEventListener('click',()=>{const lines=[['Segment','AI BR','Source rule','Scope','Scenario status','Match status','Match reason','Test Solution BR','AI requirement']];for(const row of rows)if(!row.hidden)lines.push([...row.children].map(cell=>'"'+cell.innerText.replaceAll('"','""')+'"'));const blob=new Blob([lines.map(line=>line.join(',')).join('\n')],{type:'text/csv'}),link=document.createElement('a');link.href=URL.createObjectURL(blob);link.download='all-segments-br-ratio.csv';link.click();URL.revokeObjectURL(link.href);});
const drawer=document.getElementById('drawer'),detail=document.getElementById('detail');rows.forEach(row=>row.addEventListener('click',()=>{const cells=[...row.children];detail.innerHTML='<div class="detailgrid"><b>Segment</b><span>'+cells[0].innerText+'</span><b>AI BR</b><span>'+cells[1].innerText+'</span><b>Scope</b><span>'+cells[2].innerText+'</span><b>Scenario</b><span>'+cells[3].innerText+'</span><b>Match status</b><span>'+cells[4].innerText+'</span><b>Match reason</b><span>'+cells[5].innerText+'</span><b>Test Solution BR</b><span>'+cells[6].innerText+'</span><b>AI requirement</b><span>'+cells[7].innerText+'</span><b>Decision</b><span>REVIEW_REQUIRED. Validate BR → TS → TC → TD before acceptance.</span></div>';drawer.classList.add('open')}));document.getElementById('closeDrawer').addEventListener('click',()=>drawer.classList.remove('open'));drawer.addEventListener('click',event=>{if(event.target===drawer)drawer.classList.remove('open')});
</script>
"@
$html = [regex]::Replace($html, '(?s)<script>.*?</script>', $scopeScript, 1)
$html = $html.Replace('<section class="panel charts"><div><h2>Segment Baseline Coverage</h2>', '<details class="panel charts" open><summary>Segment Baseline Coverage &amp; Segment Summary</summary><div><h2>Segment Baseline Coverage</h2>')
$html = $html.Replace('</div></section><section class="panel"><h2>Segment Summary</h2>', '</div><div class="segment-summary"><h2>Segment Summary</h2>')
$html = $html.Replace('</div></section><section class="panel"><h2>Analyst BR Matching Matrix</h2>', '</div></details><section class="panel"><h2>Analyst BR Matching Matrix</h2>')
$html = $html.Replace('<details class="panel charts" open>', $coverageGlance + '<details class="panel charts" open>')
$html = $html.Replace('<section class="panel coverage-glance"><div><h2>Coverage At A Glance</h2>', '<section class="panel coverage-glance"><h2>Coverage At A Glance &amp; Traceability Flow</h2><div class="parallel-panels"><div class="flow-column"><h3>Coverage At A Glance</h3>')
$html = $html.Replace('</div><div><h2>Traceability Flow</h2>', '</div><div class="flow-column"><h3>Traceability Flow</h3>')
$html = $html.Replace('</div></section><details class="panel charts"', '</div></div></section><details class="panel charts"')
$html = $html.Replace('<section class="panel coverage-glance"><h2>Coverage At A Glance &amp; Traceability Flow</h2>', '<details class="panel coverage-glance" open><summary>Coverage At A Glance &amp; Traceability Flow</summary><section><h2>Coverage At A Glance &amp; Traceability Flow</h2>')
$html = $html.Replace('</section><details class="panel charts"', '</section></details><details class="panel charts"')
$html | Set-Content $htmlPath -Encoding utf8

$markdownPath = Join-Path $OutputDirectory 'all-segments-br-ratio.md'
@("# All Segments BR Coverage Ratio: AI Solution vs Test Solution",'',"- Test Solution BRs: **$allTest**","- AI BRs: **$allAi**","- Confirmed Test Solution baseline coverage: **$allCoverage% ($allCovered/$allTest)**","- Review queue: **$allReview**",'', 'All candidates remain reviewable until independently validated.') | Set-Content $markdownPath -Encoding utf8
Write-Output "HTML: $htmlPath"
Write-Output "CSV: $csvPath"
Write-Output "JSON: $jsonPath"
