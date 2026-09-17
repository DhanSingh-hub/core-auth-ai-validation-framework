param(
    [string]$PocPipelineRoot = "C:\Users\F5H46GZ\Downloads\POC-DEMO\POC-DEMO\core-auth-test-generation-platform\src\pipeline",
    [string]$OutputDirectory = (Join-Path $PSScriptRoot "test-output\ai-artifacts\business-requirements")
)

$requirementsCatalog = Get-Content (Join-Path $PocPipelineRoot "step5_requirements\approved\requirement_catalog.json") -Raw | ConvertFrom-Json
$entityCatalog = Get-Content (Join-Path $PocPipelineRoot "step1_entities\approved\entity_catalog.json") -Raw | ConvertFrom-Json
$deepExtraction = Get-Content (Join-Path $PocPipelineRoot "step4_deep_extraction\approved\deep_extraction_catalog.json") -Raw | ConvertFrom-Json

$segment = @($entityCatalog.segments | Where-Object { $_.entity_id -eq "ENT-SEG-100" })
if ($segment.Count -ne 1) {
    throw "Expected exactly one ENT-SEG-100 record; found $($segment.Count)."
}

$segmentEntityIds = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
[void]$segmentEntityIds.Add("ENT-SEG-100")
foreach ($field in $segment[0].fields) {
    [void]$segmentEntityIds.Add($field.entity_id)
    [void]$segmentEntityIds.Add($field.element_ref)
}

$segmentRuleIds = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
$directSegmentRuleIds = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
foreach ($rule in $deepExtraction.business_rules) {
    if (@($rule.related_entity_ids | Where-Object { $segmentEntityIds.Contains($_) }).Count -gt 0) {
        [void]$segmentRuleIds.Add($rule.rule_id)
    }
    if ($rule.related_entity_ids -contains "ENT-SEG-100") {
        [void]$directSegmentRuleIds.Add($rule.rule_id)
    }
}

$filteredRequirements = @($requirementsCatalog.requirements | Where-Object { $segmentRuleIds.Contains($_.source_rule_id) })
if ($filteredRequirements.Count -eq 0) {
    throw "No approved requirements matched Segment 100 source rules."
}

foreach ($requirement in $filteredRequirements) {
    $scope = if ($directSegmentRuleIds.Contains($requirement.source_rule_id)) { "DIRECT_SEGMENT_100" } else { "SEGMENT_100_FIELD_RULE" }
    $requirement | Add-Member -NotePropertyName "segment100Scope" -NotePropertyValue $scope
}

New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null

$filteredCatalog = [ordered]@{
    manifest = [ordered]@{
        packageId = "POC-AI-ATL105-SEGMENT-100-REQUIREMENTS"
        specification = "ATL105"
        specificationVersion = "2025-3"
        segment = "100"
        segmentName = "Standard Message Data Segment"
        generatedAt = (Get-Date -Format "o")
        sourceCatalog = "step5_requirements/approved/requirement_catalog.json"
        filterMethod = "source_rule_id joins a deep-extraction business rule related to ENT-SEG-100 or one of its direct field entities"
        totalRequirements = $filteredRequirements.Count
        directSegmentRequirements = @($filteredRequirements | Where-Object { $_.segment100Scope -eq "DIRECT_SEGMENT_100" }).Count
        fieldRuleRequirements = @($filteredRequirements | Where-Object { $_.segment100Scope -eq "SEGMENT_100_FIELD_RULE" }).Count
        includes = "Direct Segment 100 rules and rules for Segment 100 fields; excludes text-only references without a Segment 100 entity relationship."
    }
    requirements = $filteredRequirements
}

$jsonPath = Join-Path $OutputDirectory "POC-AI-ATL105-Segment-100-Business-Requirements.json"
$filteredCatalog | ConvertTo-Json -Depth 20 | Set-Content -Path $jsonPath -Encoding utf8

$markdown = @(
    '# POC AI Segment 100 Business Requirements',
    '',
    'Source: `step5_requirements/approved/requirement_catalog.json`',
    '',
    'Scope: rules whose source rule is explicitly related to `ENT-SEG-100` or a direct Segment 100 field in the approved POC extraction.',
    '',
    "Count: $($filteredRequirements.Count)",
    '',
    '| ID | Scope | Source rule | Page | Confidence | Requirement |',
    '|---|---|---|---:|---:|---|'
)

foreach ($requirement in $filteredRequirements) {
    $statement = $requirement.statement -replace "\|", "\\|" -replace "`r?`n", " "
    $markdown += "| $($requirement.id) | $($requirement.segment100Scope) | $($requirement.source_rule_id) | $($requirement.source_page) | $($requirement.confidence_pct)% | $statement |"
}

$markdownPath = Join-Path $OutputDirectory "POC-AI-ATL105-Segment-100-Business-Requirements.md"
$markdown | Set-Content -Path $markdownPath -Encoding utf8

Write-Output "Filtered $($filteredRequirements.Count) Segment 100 requirements."
Write-Output "JSON: $jsonPath"
Write-Output "Markdown: $markdownPath"