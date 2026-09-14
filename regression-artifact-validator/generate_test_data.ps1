param(
    [string]$InputFile = (Join-Path $PSScriptRoot 'test-output\test-json\ATL105-Section1-Section2-Test-Data.json'),
    [string]$OutputDirectory = (Join-Path $PSScriptRoot 'test-output\test-json\section1-section2')
)

$ErrorActionPreference = 'Stop'

function Copy-JsonObject {
    param([object]$Value)

    return ($Value | ConvertTo-Json -Depth 100 | ConvertFrom-Json)
}

function Set-DottedProperty {
    param(
        [object]$Object,
        [string]$Path,
        [object]$Value
    )

    $parts = $Path.Split('.')
    $current = $Object

    for ($index = 0; $index -lt ($parts.Length - 1); $index++) {
        $part = $parts[$index]
        $next = $current.PSObject.Properties[$part]
        if ($null -eq $next -or $null -eq $next.Value) {
            $current | Add-Member -NotePropertyName $part -NotePropertyValue ([pscustomobject]@{}) -Force
        }
        $current = $current.PSObject.Properties[$part].Value
    }

    $leaf = $parts[$parts.Length - 1]
    $current | Add-Member -NotePropertyName $leaf -NotePropertyValue $Value -Force
}

function Get-RequestOverride {
    param([string]$Path)

    if ($Path -eq 'dataSection2.duplicateStandardSegment') {
        return $null
    }

    if ($Path.StartsWith('tcpIpHeader.') -or
        $Path.StartsWith('dataSection1.') -or
        $Path.StartsWith('dataSection2.') -or
        $Path.StartsWith('dataSection3.')) {
        return "request.$Path"
    }

    return $null
}

if (-not (Test-Path -LiteralPath $InputFile -PathType Leaf)) {
    throw "Input test-data file was not found: $InputFile"
}

New-Item -ItemType Directory -Path $OutputDirectory -Force | Out-Null
$suite = Get-Content -LiteralPath $InputFile -Raw | ConvertFrom-Json
$baseRequest = $suite.baseRequest
$generated = @()

foreach ($case in $suite.testData) {
    $document = [pscustomobject][ordered]@{
        testDataId = "TEST-DATA-$($case.testCaseId -replace '^TEST-CASE-', '')"
        testCaseId = $case.testCaseId
        scenarioId = $case.scenarioId
        expectedValidation = $case.expectedValidation
        objective = $case.objective
        scope = [ordered]@{
            includes = $suite.scope.includes
            excludes = $suite.scope.excludes
            messageModel = $suite.scope.messageModel
        }
        flowContext = if ($case.PSObject.Properties.Name -contains 'flowContext') { $case.flowContext } else { $null }
        lifecycleMessages = if ($case.PSObject.Properties.Name -contains 'lifecycleMessages') { $case.lifecycleMessages } else { $null }
        request = Copy-JsonObject $baseRequest
        testControls = [pscustomobject][ordered]@{}
    }
    $testControls = [ordered]@{}

    if ($case.PSObject.Properties.Name -contains 'overrides' -and $null -ne $case.overrides) {
        foreach ($property in $case.overrides.PSObject.Properties) {
            $requestPath = Get-RequestOverride $property.Name
            if ($null -ne $requestPath) {
                Set-DottedProperty -Object $document -Path $requestPath -Value $property.Value
            }
            else {
                $testControls[$property.Name] = $property.Value
            }
        }
    }

    $document.testControls = [pscustomobject]$testControls

    $fileName = "$($case.testCaseId).json"
    $path = Join-Path $OutputDirectory $fileName
    $document | ConvertTo-Json -Depth 100 | Set-Content -LiteralPath $path -Encoding UTF8
    $generated += $document
}

$generated | ConvertTo-Json -Depth 100 | Set-Content -LiteralPath (Join-Path $OutputDirectory 'ATL105-Section1-Section2-Test-Data-Materialized.json') -Encoding UTF8
Write-Output "Generated $($generated.Count) materialized test-data files in $OutputDirectory"