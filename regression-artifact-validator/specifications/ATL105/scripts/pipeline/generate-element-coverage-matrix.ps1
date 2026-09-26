param(
    [string]$SpecificationRoot = (Join-Path (Split-Path (Split-Path $PSScriptRoot -Parent) -Parent) ''),
    [string]$OutputDirectory = (Join-Path (Split-Path (Split-Path $PSScriptRoot -Parent) -Parent) 'test-output\ai-solution-independent-review')
)

$ErrorActionPreference = 'Stop'
$dictionaryPath = Join-Path $SpecificationRoot 'docs\specs\kb\13-data-elements.md'
$ruleCatalogRoot = Join-Path $SpecificationRoot 'docs\specs\kb'
$testRoot = Join-Path $SpecificationRoot 'test-output\test-json'
$aiRoot = Join-Path $SpecificationRoot 'test-output\ai-artifacts\business-requirements'

function Add-ElementReference {
    param([hashtable]$Map, [string]$Element, [string]$Source, [string]$Kind, [string]$RuleId = '')
    if ([string]::IsNullOrWhiteSpace($Element)) { return }
    foreach ($match in [regex]::Matches($Element, '(?<![A-Za-z])\d{1,3}(?![A-Za-z])')) {
        $number = [int]$match.Value
        if (-not $Map.ContainsKey($number)) {
            $Map[$number] = [ordered]@{ ai = @{}; test = @{}; rules = @{} }
        }
        $target = $Map[$number][$Kind]
        $key = if ($RuleId) { "$Source|$RuleId" } else { $Source }
        if (-not $target.ContainsKey($key)) { $target[$key] = $true }
    }
}

function Add-ObjectElementReferences {
    param([hashtable]$Map, [object]$Object, [string]$Source, [string]$Kind)
    if ($null -eq $Object) { return }
    if ($Object -is [System.Collections.IEnumerable] -and $Object -isnot [string]) {
        foreach ($item in $Object) { Add-ObjectElementReferences $Map $item $Source $Kind }
        return
    }
    if ($Object -is [pscustomobject] -or $Object -is [hashtable]) {
        foreach ($property in $Object.PSObject.Properties) {
            if ($property.Name -in @('element', 'elementNumber', 'element_id')) {
                Add-ElementReference $Map ([string]$property.Value) $Source $Kind
            } elseif ($property.Name -eq 'related_entity_ids' -and $Kind -eq 'ai') {
                foreach ($entity in @($property.Value)) {
                    if ([string]$entity -match '^ENT-ELEM-(\d+)$') { Add-ElementReference $Map $Matches[1] $Source $Kind }
                }
            } else {
                Add-ObjectElementReferences $Map $property.Value $Source $Kind
            }
        }
    }
}

$elements = @{}
foreach ($line in Get-Content $dictionaryPath) {
    if ($line -match '^\|\s*(\d+)\s*\|\s*([^|]+?)\s*\|') {
        $number = [int]$Matches[1]
        $elements[$number] = $Matches[2].Trim()
    }
}

$references = @{}
foreach ($file in Get-ChildItem $ruleCatalogRoot -Recurse -File -Filter '*rule-catalog.json') {
    $document = Get-Content $file.FullName -Raw | ConvertFrom-Json
    foreach ($rule in @($document.rules)) {
        if ($null -ne $rule.sourceAnchor) {
            Add-ElementReference $references ([string]$rule.sourceAnchor.element) $file.Name 'rules' ([string]$rule.ruleId)
        }
    }
}

if (Test-Path $testRoot) {
    foreach ($file in Get-ChildItem $testRoot -Recurse -File -Filter '*.json') {
        try { Add-ObjectElementReferences $references (Get-Content $file.FullName -Raw | ConvertFrom-Json) $file.Name 'test' } catch { }
    }
}
if (Test-Path $aiRoot) {
    foreach ($file in Get-ChildItem $aiRoot -Recurse -File -Filter '*.json') {
        try { Add-ObjectElementReferences $references (Get-Content $file.FullName -Raw | ConvertFrom-Json) $file.Name 'ai' } catch { }
    }
}

$rows = foreach ($number in ($elements.Keys | Sort-Object)) {
    $reference = if ($references.ContainsKey($number)) { $references[$number] } else { @{ ai=@{}; test=@{}; rules=@{} } }
    $ruleCount = $reference.rules.Count
    $aiCount = $reference.ai.Count
    $testCount = $reference.test.Count
    $status = if ($ruleCount -eq 0) { 'NOT_YET_TRAINED' } elseif ($aiCount -gt 0 -and $testCount -gt 0) { 'COVERED' } elseif ($aiCount -gt 0 -or $testCount -gt 0) { 'PARTIALLY_COVERED' } else { 'REVIEW_REQUIRED' }
    [pscustomobject][ordered]@{
        element = $number
        name = $elements[$number]
        status = $status
        ruleCount = $ruleCount
        aiReferenceCount = $aiCount
        testReferenceCount = $testCount
        ruleIds = (@($reference.rules.Keys) -join ';')
        aiSources = (@($reference.ai.Keys) -join ';')
        testSources = (@($reference.test.Keys) -join ';')
    }
}

New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null
$base = Join-Path $OutputDirectory 'element-coverage-matrix'
$rows | ConvertTo-Json -Depth 10 | Set-Content "$base.json" -Encoding utf8
$rows | Export-Csv "$base.csv" -NoTypeInformation -Encoding utf8

$summary = $rows | Group-Object status | Sort-Object Name
$markdown = @('# ATL105 Element Coverage Matrix', '', 'Generated from the Chapter 13.2 element dictionary, ATL105 rule catalogs, AI business-requirement packages, and Test Solution JSON packages.', '', "Total unique elements: $($rows.Count)", '', '| Status | Count |', '|---|---:|')
foreach ($group in $summary) { $markdown += "| $($group.Name) | $($group.Count) |" }
$markdown += @('', '| Element | Name | Status | Rules | AI references | Test references |', '|---:|---|---|---:|---:|---:|')
foreach ($row in $rows) { $markdown += "| $($row.element) | $($row.name) | $($row.status) | $($row.ruleCount) | $($row.aiReferenceCount) | $($row.testReferenceCount) |" }
$markdown | Set-Content "$base.md" -Encoding utf8

Write-Output "Matrix: $base.json"
Write-Output "CSV: $base.csv"
Write-Output "Markdown: $base.md"
Write-Output "Elements: $($rows.Count)"
$summary | ForEach-Object { Write-Output "$($_.Name): $($_.Count)" }
