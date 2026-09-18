param(
    [string]$PocPipelineRoot = "C:\Users\F5H46GZ\Downloads\POC-DEMO\POC-DEMO\core-auth-test-generation-platform\src\pipeline",
    [string]$OutputDirectory = (Join-Path $PSScriptRoot "test-output\ai-artifacts\coverage-reports"),
    [string]$ProfilePath = (Join-Path $PSScriptRoot "segment-profiles.json")
)

& (Join-Path $PSScriptRoot "compare-poc-segment-coverage.ps1") `
    -SegmentNumber "100" `
    -PocPipelineRoot $PocPipelineRoot `
    -OutputDirectory $OutputDirectory `
    -ProfilePath $ProfilePath