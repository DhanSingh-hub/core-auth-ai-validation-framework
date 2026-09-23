param(
    [Parameter(Mandatory = $true)][string]$SegmentNumber,
    [string]$PocPipelineRoot = "C:\Users\F5H46GZ\Downloads\POC-DEMO\POC-DEMO\core-auth-test-generation-platform\src\pipeline",
    [string]$OutputDirectory = (Join-Path (Split-Path (Split-Path $PSScriptRoot -Parent) -Parent) "test-output\ai-artifacts\business-requirements"),
    [string]$BaselinePath = (Join-Path (Split-Path (Split-Path $PSScriptRoot -Parent) -Parent) "test-output\test-json\segment-$SegmentNumber-requirements-baseline.json")
)

$requirementsCatalog = Get-Content (Join-Path $PocPipelineRoot "step5_requirements\approved\requirement_catalog.json") -Raw | ConvertFrom-Json
$entityCatalog = Get-Content (Join-Path $PocPipelineRoot "step1_entities\approved\entity_catalog.json") -Raw | ConvertFrom-Json
$deepExtraction = Get-Content (Join-Path $PocPipelineRoot "step4_deep_extraction\approved\deep_extraction_catalog.json") -Raw | ConvertFrom-Json

$segment = @($entityCatalog.segments | Where-Object { $_.segment_number -eq $SegmentNumber })
if ($segment.Count -ne 1) {
    throw "Expected exactly one ENT-SEG-$SegmentNumber record; found $($segment.Count)."
}

$segmentEntityIds = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
[void]$segmentEntityIds.Add("ENT-SEG-$SegmentNumber")
foreach ($field in $segment[0].fields) {
    [void]$segmentEntityIds.Add($field.entity_id)
    [void]$segmentEntityIds.Add($field.element_ref)
}

$directSegmentRuleIds = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
$segmentRuleIds = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
foreach ($rule in $deepExtraction.business_rules) {
    if (@($rule.related_entity_ids | Where-Object { $segmentEntityIds.Contains($_) }).Count -gt 0) {
        [void]$segmentRuleIds.Add($rule.rule_id)
    }
    if ($rule.related_entity_ids -contains "ENT-SEG-$SegmentNumber") {
        [void]$directSegmentRuleIds.Add($rule.rule_id)
    }
}

$directRequirements = @($requirementsCatalog.requirements | Where-Object { $directSegmentRuleIds.Contains($_.source_rule_id) })
$filteredRequirements = $directRequirements
$excludedRelatedRequirements = @($requirementsCatalog.requirements | Where-Object {
    $segmentRuleIds.Contains($_.source_rule_id) -and -not $directSegmentRuleIds.Contains($_.source_rule_id)
})

$validationErrors = [System.Collections.Generic.List[string]]::new()
$seenExactRequirements = @{}
$sourceRuleGroups = @($filteredRequirements | Group-Object source_rule_id)
$exactDuplicateRows = 0
$normalizedRequirements = foreach ($requirement in $filteredRequirements) {
    foreach ($field in @('id', 'statement', 'source_rule_id', 'source_page', 'confidence_pct')) {
        if ($null -eq $requirement.PSObject.Properties[$field] -or [string]::IsNullOrWhiteSpace([string]$requirement.$field)) {
            [void]$validationErrors.Add("Requirement $($requirement.id) is missing required field '$field'.")
        }
    }
    $exactKey = "$($requirement.source_rule_id)|$($requirement.statement)"
    if ($seenExactRequirements.ContainsKey($exactKey)) {
        $exactDuplicateRows++
        continue
    }
    $seenExactRequirements[$exactKey] = $true
    $scope = "DIRECT_SEGMENT_$SegmentNumber"
    $cleanupStatus = if ($requirement.requires_review -eq $true -or $requirement.below_confidence_gate -eq $true) { 'REVIEW_REQUIRED' } else { 'READY_FOR_REVIEW' }
    $sourceGroup = @($sourceRuleGroups | Where-Object Name -eq $requirement.source_rule_id)[0]
    $duplicateGroup = $null -ne $sourceGroup -and $sourceGroup.Count -gt 1
    $duplicateOccurrence = if ($duplicateGroup) { @($sourceGroup.Group | ForEach-Object id).IndexOf($requirement.id) + 1 } else { 1 }
    $requirement | Add-Member -NotePropertyName segmentScope -NotePropertyValue $scope -Force
    $requirement | Add-Member -NotePropertyName cleanupStatus -NotePropertyValue $cleanupStatus -Force
    $requirement | Add-Member -NotePropertyName duplicateSourceRuleGroup -NotePropertyValue $duplicateGroup -Force
    $requirement | Add-Member -NotePropertyName duplicateOccurrence -NotePropertyValue $duplicateOccurrence -Force
    $requirement
}
$filteredRequirements = @($normalizedRequirements)

$baseline = $null
$baselineStatus = 'MISSING'
$baselineRequirementCount = 0
$baselineReviewRequiredCount = 0
if (Test-Path $BaselinePath) {
    $baseline = Get-Content $BaselinePath -Raw | ConvertFrom-Json
    $baselineRequirements = if ($null -ne $baseline.businessRequirements) { @($baseline.businessRequirements) } else { @() }
    $baselineRequirementCount = $baselineRequirements.Count
    $baselineReviewRequiredCount = @($baselineRequirements | Where-Object { $_.status -eq 'REVIEW_REQUIRED' }).Count
    $baselineStatus = 'AVAILABLE'
} else {
    [void]$validationErrors.Add("Baseline package not found: $BaselinePath")
}

if ($validationErrors.Count -gt 0 -and $validationErrors | Where-Object { $_ -like 'Requirement *missing required*' }) {
    throw ($validationErrors -join [Environment]::NewLine)
}

New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null

$filteredCatalog = [ordered]@{
    manifest = [ordered]@{
        packageId = "POC-AI-ATL105-SEGMENT-$SegmentNumber-REQUIREMENTS"
        specification = "ATL105"
        specificationVersion = "2026-3"
        segment = "$SegmentNumber"
        segmentName = $segment[0].name
        generatedAt = (Get-Date -Format "o")
        sourceCatalog = "step5_requirements/approved/requirement_catalog.json"
        filterMethod = "source_rule_id joins a deep-extraction business rule directly related to ENT-SEG-$SegmentNumber; shared-element and related-segment candidates are excluded from the approved-scope package"
        totalRequirements = $filteredRequirements.Count
        directSegmentRequirements = @($filteredRequirements | Where-Object { $_.segmentScope -eq "DIRECT_SEGMENT_$SegmentNumber" }).Count
        fieldRuleRequirements = 0
        excludedRelatedRequirements = $excludedRelatedRequirements.Count
        reviewRequiredRequirements = @($filteredRequirements | Where-Object { $_.requires_review -eq $true }).Count
        belowConfidenceGateRequirements = @($filteredRequirements | Where-Object { $_.below_confidence_gate -eq $true }).Count
        confidenceBands = [ordered]@{
            HIGH = @($filteredRequirements | Where-Object { $_.confidence_band -eq 'HIGH' }).Count
            MEDIUM = @($filteredRequirements | Where-Object { $_.confidence_band -eq 'MEDIUM' }).Count
            LOW = @($filteredRequirements | Where-Object { $_.confidence_band -eq 'LOW' }).Count
        }
        duplicateSourceRuleGroups = @($sourceRuleGroups | Where-Object { $_.Count -gt 1 }).Count
        exactDuplicateRowsRemoved = $exactDuplicateRows
        baselineStatus = $baselineStatus
        baselinePackageId = if ($null -ne $baseline) { $baseline.packageId } else { $null }
        baselineRequirementCount = $baselineRequirementCount
        baselineReviewRequiredCount = $baselineReviewRequiredCount
        validationStatus = if ($validationErrors.Count -eq 0) { 'VALID' } else { 'REVIEW_REQUIRED' }
        validationWarnings = @($validationErrors)
    }
    requirements = $filteredRequirements
}

$jsonPath = Join-Path $OutputDirectory "POC-AI-ATL105-Segment-$SegmentNumber-Business-Requirements.json"
$filteredCatalog | ConvertTo-Json -Depth 20 | Set-Content -Path $jsonPath -Encoding utf8

$markdown = @(
    "# POC AI Segment $SegmentNumber Business Requirements",
    '',
    'Source: `step5_requirements/approved/requirement_catalog.json`',
    '',
    "Scope: rules whose source rule is directly related to ENT-SEG-$SegmentNumber. Shared-element and cross-segment candidates are excluded from this approved-scope package.",
    '',
    "Count: $($filteredRequirements.Count)",
    "Excluded related/cross-segment candidates: $($excludedRelatedRequirements.Count)",
    "Review required: $(@($filteredRequirements | Where-Object { $_.requires_review -eq $true }).Count)",
    "Below confidence gate: $(@($filteredRequirements | Where-Object { $_.below_confidence_gate -eq $true }).Count)",
    "Confidence bands: HIGH=$(@($filteredRequirements | Where-Object { $_.confidence_band -eq 'HIGH' }).Count), MEDIUM=$(@($filteredRequirements | Where-Object { $_.confidence_band -eq 'MEDIUM' }).Count), LOW=$(@($filteredRequirements | Where-Object { $_.confidence_band -eq 'LOW' }).Count)",
    "Duplicate source-rule groups preserved: $(@($sourceRuleGroups | Where-Object { $_.Count -gt 1 }).Count)",
    "Exact duplicate rows removed: $exactDuplicateRows",
    "Baseline status: $baselineStatus ($baselineRequirementCount requirements; $baselineReviewRequiredCount review-required)",
    "Validation status: $(if ($validationErrors.Count -eq 0) { 'VALID' } else { 'REVIEW_REQUIRED' })",
    '',
    '| ID | Scope | Source rule | Page | Confidence | Requirement |',
    '|---|---|---|---:|---:|---|'
)
foreach ($requirement in $filteredRequirements) {
    $statement = $requirement.statement -replace "\|", "\\|" -replace "`r?`n", " "
    $markdown += "| $($requirement.id) | $($requirement.segmentScope) | $($requirement.source_rule_id) | $($requirement.source_page) | $($requirement.confidence_pct)% | $statement |"
}

$markdownPath = Join-Path $OutputDirectory "POC-AI-ATL105-Segment-$SegmentNumber-Business-Requirements.md"
$markdown | Set-Content -Path $markdownPath -Encoding utf8

Write-Output "Filtered $($filteredRequirements.Count) Segment $SegmentNumber requirements."
Write-Output "JSON: $jsonPath"
Write-Output "Markdown: $markdownPath"
