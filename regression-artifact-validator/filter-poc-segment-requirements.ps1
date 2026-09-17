param(
    [Parameter(Mandatory = $true)][string]$SegmentNumber,
    [string]$PocPipelineRoot = "C:\Users\F5H46GZ\Downloads\POC-DEMO\POC-DEMO\core-auth-test-generation-platform\src\pipeline",
    [string]$OutputDirectory = (Join-Path $PSScriptRoot "test-output\ai-artifacts\business-requirements")
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

$filteredRequirements = @($requirementsCatalog.requirements | Where-Object { $segmentRuleIds.Contains($_.source_rule_id) })

New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null

foreach ($requirement in $filteredRequirements) {
    $scope = if ($directSegmentRuleIds.Contains($requirement.source_rule_id)) { "DIRECT_SEGMENT_$SegmentNumber" } else { "SEGMENT_${SegmentNumber}_FIELD_RULE" }
    if ($requirement.PSObject.Properties.Name -contains "segmentScope") {
        $requirement.segmentScope = $scope
    } else {
        $requirement | Add-Member -NotePropertyName "segmentScope" -NotePropertyValue $scope
    }
}

$filteredCatalog = [ordered]@{
    manifest = [ordered]@{
        packageId = "POC-AI-ATL105-SEGMENT-$SegmentNumber-REQUIREMENTS"
        specification = "ATL105"
        specificationVersion = "2025-3"
        segment = "$SegmentNumber"
        segmentName = $segment[0].name
        generatedAt = (Get-Date -Format "o")
        sourceCatalog = "step5_requirements/approved/requirement_catalog.json"
        filterMethod = "source_rule_id joins a deep-extraction business rule related to ENT-SEG-$SegmentNumber or one of its direct field entities"
        totalRequirements = $filteredRequirements.Count
        directSegmentRequirements = @($filteredRequirements | Where-Object { $_.segmentScope -eq "DIRECT_SEGMENT_$SegmentNumber" }).Count
        fieldRuleRequirements = @($filteredRequirements | Where-Object { $_.segmentScope -eq "SEGMENT_${SegmentNumber}_FIELD_RULE" }).Count
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
    "Scope: rules whose source rule is explicitly related to ENT-SEG-$SegmentNumber or a direct field of that segment in the approved POC extraction.",
    '',
    "Count: $($filteredRequirements.Count)",
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
