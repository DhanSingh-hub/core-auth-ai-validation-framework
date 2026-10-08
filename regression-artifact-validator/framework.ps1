[CmdletBinding()]
param(
    [string]$Task,
    [string]$Tests,
    [string]$ReviewDirectory,
    [string]$JsdomModulePath,
    [string]$PythonPath = 'python.exe',
    [switch]$List,
    [switch]$DryRun
)

$ErrorActionPreference = 'Stop'
$moduleRoot = $PSScriptRoot
$manifest = Get-Content (Join-Path $moduleRoot 'framework-tasks.json') -Raw -Encoding UTF8 | ConvertFrom-Json
if ($manifest.schemaVersion -ne 1 -or @($manifest.tasks).Count -eq 0) {
    throw 'Invalid framework task manifest.'
}
if ($List -or [string]::IsNullOrWhiteSpace($Task)) {
    $manifest.tasks | Select-Object name, description
    return
}
$selected = @($manifest.tasks | Where-Object { $_.name -ceq $Task })
if ($selected.Count -ne 1) { throw "Unknown or duplicate task '$Task'. Use -List." }
$definition = $selected[0]
if ($Tests -and $Task -ne 'test') { throw '-Tests is supported only by the test task.' }
$executable = [string]$definition.executable
if ($executable -eq 'python.exe') { $executable = $PythonPath }
$arguments = @()
if ($definition.script) {
    $scriptPath = Join-Path $moduleRoot $definition.script
    if (-not (Test-Path -LiteralPath $scriptPath -PathType Leaf)) { throw "Task script is missing: $scriptPath" }
    $arguments += $scriptPath
}
foreach ($argument in $definition.arguments) {
    switch -CaseSensitive ($argument) {
        '{ReviewDirectory}' {
            if (-not $ReviewDirectory -or -not (Test-Path -LiteralPath $ReviewDirectory -PathType Container)) {
                throw 'Provide an existing -ReviewDirectory for semantic-view-check.'
            }
            $arguments += (Resolve-Path -LiteralPath $ReviewDirectory).Path
        }
        '{JsdomModulePath}' {
            if (-not $JsdomModulePath -or -not (Test-Path -LiteralPath $JsdomModulePath)) {
                throw 'Provide an installed -JsdomModulePath; the launcher does not install dependencies.'
            }
            $arguments += (Resolve-Path -LiteralPath $JsdomModulePath).Path
        }
        default { $arguments += [string]$argument }
    }
}
if ($Tests) { $arguments = @("-Dtest=$Tests") + $arguments }
if ($DryRun) {
    [PSCustomObject]@{ Task = $Task; WorkingDirectory = $moduleRoot; Executable = $executable; Arguments = $arguments }
    return
}
$command = Get-Command $executable -CommandType Application, ExternalScript -ErrorAction Stop | Select-Object -First 1
Push-Location $moduleRoot
try {
    & $command.Source @arguments
    if ($LASTEXITCODE -ne 0) { throw "Framework task '$Task' failed with exit code $LASTEXITCODE." }
} finally {
    Pop-Location
}