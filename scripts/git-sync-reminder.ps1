# Every 30 minutes: remind to commit and push, offer a pull request to Develop, and flag conflicts with Develop.
# Every 15 minutes while on Develop: ask to switch to a child branch of Develop.
# Started by the "Git sync reminder" VS Code task when the folder opens. It never commits or pushes.
param(
    [int]$IntervalMinutes = 30,
    [int]$DevelopIntervalMinutes = 15,
    [switch]$Once
)

$DevelopWarning = "You are working directly on Develop. Switch to a child branch of Develop and merge it back through " +
    "a pull request; do not work on Develop directly. To switch: git switch -c <your-branch> (uncommitted changes move " +
    "with you). This reminder repeats every $DevelopIntervalMinutes minutes while you are on Develop."

Add-Type -AssemblyName System.Windows.Forms
Set-Location (Split-Path -Parent $PSScriptRoot)

function Invoke-Git {
    $out = & git @args 2>$null
    [pscustomobject]@{ Lines = @($out); Code = $LASTEXITCODE }
}

function Show-Prompt([string]$Text, [string]$Title, [string]$Buttons, [string]$Icon) {
    # DefaultDesktopOnly keeps the box on top of VS Code.
    [System.Windows.Forms.MessageBox]::Show($Text, $Title, $Buttons, $Icon, 'Button1', 'DefaultDesktopOnly')
}

function Get-CommitCount([string]$Range) {
    [int]((Invoke-Git rev-list --count $Range).Lines[0])
}

function Get-CompareUrl([string]$Branch) {
    $url = (Invoke-Git remote get-url origin).Lines[0]
    if ($url -match 'github\.com[:/](.+?)(\.git)?$') {
        return "https://github.com/$($Matches[1])/compare/Develop...$($Branch)?expand=1"
    }
    return $null
}

function Get-DevelopConflicts {
    # Exit code 1 means conflicts; other codes (for example git older than 2.38) skip the check.
    $result = Invoke-Git merge-tree --write-tree --name-only --no-messages origin/Develop HEAD
    if ($result.Code -eq 1) {
        return @($result.Lines | Select-Object -Skip 1 | Where-Object { $_ })
    }
    return @()
}

function Invoke-Check {
    Invoke-Git fetch --quiet --prune origin | Out-Null
    $branch = (Invoke-Git branch --show-current).Lines[0]
    if (-not $branch) { return }
    $title = "Git reminder: $branch"

    if ($branch -eq 'main') {
        Show-Prompt ("You are on main. Do not commit or push to main: it is updated only by the weekly pull request " +
            "from Develop. Switch to a child branch of Develop.") $title 'OK' 'Warning' | Out-Null
        return
    }

    $notes = @()
    if ($branch -eq 'Develop') { $notes += "- $DevelopWarning" }
    $dirty = @((Invoke-Git status --porcelain).Lines | Where-Object { $_ }).Count
    if ($dirty) { $notes += "- $dirty uncommitted file(s): commit them." }

    $onRemote = (Invoke-Git rev-parse --verify --quiet "refs/remotes/origin/$branch").Code -eq 0
    if (-not $onRemote) {
        $notes += "- The branch is not on the remote yet: push it to origin/$branch."
    } else {
        $unpushed = Get-CommitCount "origin/$branch..HEAD"
        if ($unpushed) { $notes += "- $unpushed commit(s) not pushed: push them to origin/$branch." }
    }

    $conflicts = Get-DevelopConflicts
    if ($conflicts.Count) {
        $files = ($conflicts | Select-Object -First 5) -join ', '
        if ($conflicts.Count -gt 5) { $files += ', ...' }
        $notes += "- Your branch conflicts with Develop in: $files. Do not resolve it alone: contact Mr Dhan Singh."
    }

    $ahead = 0
    $prUrl = $null
    if ($branch -ne 'Develop' -and $onRemote -and -not $conflicts.Count) {
        $ahead = Get-CommitCount "origin/Develop..origin/$branch"
        if ($ahead) { $prUrl = Get-CompareUrl $branch }
    }

    if ($prUrl) {
        $text = (($notes + '') -join "`n") + "$ahead pushed commit(s) are not in Develop yet. Open a pull request to merge $branch into Develop now?"
        if ((Show-Prompt $text.Trim() $title 'YesNo' 'Question') -eq 'Yes') { Start-Process $prUrl }
    } elseif ($notes.Count) {
        $icon = if ($conflicts.Count -or $branch -eq 'Develop') { 'Warning' } else { 'Information' }
        Show-Prompt ($notes -join "`n") $title 'OK' $icon | Out-Null
    }
}

if (-not (Invoke-Git config --get core.hooksPath).Lines[0]) {
    Invoke-Git config core.hooksPath .githooks | Out-Null
}

# Wake every 15 minutes; run the full check every 30 and the Develop warning alone in between.
$ticksPerCheck = [Math]::Max(1, [int]($IntervalMinutes / $DevelopIntervalMinutes))
$tick = 0
do {
    if (-not $Once) { Start-Sleep -Seconds ($DevelopIntervalMinutes * 60) }
    $tick++
    try {
        if ($Once -or $tick % $ticksPerCheck -eq 0) {
            Invoke-Check
        } elseif ((Invoke-Git branch --show-current).Lines[0] -eq 'Develop') {
            Show-Prompt $DevelopWarning 'Git reminder: Develop' 'OK' 'Warning' | Out-Null
        }
    } catch { Write-Warning $_ }
} while (-not $Once)
