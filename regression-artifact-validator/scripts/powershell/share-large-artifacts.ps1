param(
    [ValidateSet('Pack', 'Verify', 'Restore')][string]$Mode = 'Verify',
    [string]$Bundle = 'shared-artifacts/2026-10-16-validation-evidence',
    [string]$DestinationRoot
)

$ErrorActionPreference = 'Stop'
$root = (git rev-parse --show-toplevel).Trim()
if ($LASTEXITCODE -ne 0) { throw 'Run inside the Git repository.' }
$bundlePath = [IO.Path]::GetFullPath((Join-Path $root $Bundle))
if (!$DestinationRoot) { $DestinationRoot = $root }
$DestinationRoot = [IO.Path]::GetFullPath($DestinationRoot)

function Get-Hash([string]$Path) {
    $stream = [IO.File]::OpenRead($Path)
    $algorithm = [Security.Cryptography.SHA256]::Create()
    try { return ([BitConverter]::ToString($algorithm.ComputeHash($stream))).Replace('-', '').ToLowerInvariant() }
    finally { $algorithm.Dispose(); $stream.Dispose() }
}

if ($Mode -eq 'Pack') {
    if ([IO.Directory]::Exists($bundlePath)) { throw "Refusing to overwrite $bundlePath" }
    $names = @(git -C $root diff --cached --name-only)
    $objects = @($names | ForEach-Object { ':' + $_ + ' ' + $_ } |
        git -C $root cat-file '--batch-check=%(objectsize) %(objectname) %(rest)')
    if ($LASTEXITCODE -ne 0) { throw 'Cannot inspect staged blobs.' }
    [IO.Directory]::CreateDirectory($bundlePath) | Out-Null
    $entries = @()
    foreach ($row in $objects) {
        if ($row -notmatch '^(\d+) ([0-9a-f]+) (.+)$') { throw "Unexpected blob entry: $row" }
        $bytes = [int64]$Matches[1]
        $blob = $Matches[2]
        $relative = $Matches[3]
        if ($bytes -le 100MB) { continue }
        $directory = 'artifact-{0:D3}' -f ($entries.Count + 1)
        $folder = Join-Path $bundlePath $directory
        [IO.Directory]::CreateDirectory($folder) | Out-Null
        $archive = Join-Path $folder 'packing.tmp'
        $start = New-Object Diagnostics.ProcessStartInfo
        $start.FileName = 'git'
        $start.Arguments = "cat-file blob $blob"
        $start.WorkingDirectory = $root
        $start.UseShellExecute = $false
        $start.RedirectStandardOutput = $true
        $process = [Diagnostics.Process]::Start($start)
        $output = [IO.File]::Create($archive)
        $gzip = New-Object IO.Compression.GZipStream($output, [IO.Compression.CompressionMode]::Compress)
        try { $process.StandardOutput.BaseStream.CopyTo($gzip) }
        finally { $gzip.Dispose(); $output.Dispose() }
        $process.WaitForExit()
        if ($process.ExitCode -ne 0) { throw "Cannot read $relative" }
        $process.Dispose()
        $inputStream = [IO.File]::OpenRead($archive)
        $buffer = New-Object byte[] (1MB)
        $parts = @()
        try {
            while ($inputStream.Position -lt $inputStream.Length) {
                $partName = 'part-{0:D4}.bin' -f ($parts.Count + 1)
                $partPath = Join-Path $folder $partName
                $part = [IO.File]::Create($partPath)
                try {
                    $remaining = 48MB
                    while ($remaining -gt 0) {
                        $count = $inputStream.Read($buffer, 0, [math]::Min($remaining, $buffer.Length))
                        if ($count -eq 0) { break }
                        $part.Write($buffer, 0, $count)
                        $remaining -= $count
                    }
                } finally { $part.Dispose() }
                $parts += [ordered]@{ path = "$directory/$partName"; bytes = ([IO.FileInfo]$partPath).Length; sha256 = (Get-Hash $partPath) }
            }
        } finally { $inputStream.Dispose() }
        [IO.File]::Delete($archive)
        $entries += [ordered]@{ path = $relative; bytes = $bytes; gitBlob = $blob; sha256 = ''; encoding = 'gzip'; parts = $parts }
        Write-Output "Packed $relative into $($parts.Count) part(s)"
    }
    $manifest = [ordered]@{ version = 1; source = 'Git index snapshot'; partLimitBytes = 48MB; files = $entries }
    $manifest | ConvertTo-Json -Depth 10 | Set-Content -LiteralPath (Join-Path $bundlePath 'manifest.json') -Encoding UTF8
}

$manifestPath = Join-Path $bundlePath 'manifest.json'
$manifest = Get-Content -LiteralPath $manifestPath -Raw | ConvertFrom-Json
foreach ($entry in $manifest.files) {
    $temporaryArchive = [IO.Path]::GetTempFileName()
    $temporaryOriginal = [IO.Path]::GetTempFileName()
    try {
        $archiveStream = [IO.File]::Create($temporaryArchive)
        try {
            foreach ($part in $entry.parts) {
                $partPath = [IO.Path]::GetFullPath((Join-Path $bundlePath $part.path))
                if (!$partPath.StartsWith($bundlePath + [IO.Path]::DirectorySeparatorChar, [StringComparison]::OrdinalIgnoreCase)) { throw 'Unsafe part path.' }
                if (([IO.FileInfo]$partPath).Length -ne $part.bytes -or (Get-Hash $partPath) -ne $part.sha256) { throw "Part hash/size mismatch: $partPath" }
                $partStream = [IO.File]::OpenRead($partPath)
                try { $partStream.CopyTo($archiveStream) } finally { $partStream.Dispose() }
            }
        } finally { $archiveStream.Dispose() }
        $inputStream = [IO.File]::OpenRead($temporaryArchive)
        $gzip = New-Object IO.Compression.GZipStream($inputStream, [IO.Compression.CompressionMode]::Decompress)
        $output = [IO.File]::Create($temporaryOriginal)
        try { $gzip.CopyTo($output) } finally { $output.Dispose(); $gzip.Dispose(); $inputStream.Dispose() }
        $hash = Get-Hash $temporaryOriginal
        if (([IO.FileInfo]$temporaryOriginal).Length -ne $entry.bytes) { throw "Original size mismatch: $($entry.path)" }
        $blob = (git -C $root hash-object --no-filters $temporaryOriginal).Trim()
        if ($LASTEXITCODE -ne 0 -or $blob -ne $entry.gitBlob) { throw "Original Git hash mismatch: $($entry.path)" }
        if ($Mode -eq 'Pack') { $entry.sha256 = $hash }
        elseif ($hash -ne $entry.sha256) { throw "Original SHA256 mismatch: $($entry.path)" }
        if ($Mode -eq 'Restore') {
            $target = [IO.Path]::GetFullPath((Join-Path $DestinationRoot $entry.path))
            if (!$target.StartsWith($DestinationRoot.TrimEnd('\', '/') + [IO.Path]::DirectorySeparatorChar, [StringComparison]::OrdinalIgnoreCase)) { throw 'Unsafe original path.' }
            if ([IO.File]::Exists($target)) {
                if ((Get-Hash $target) -ne $hash) { throw "Refusing to overwrite differing file: $target" }
            } else {
                [IO.Directory]::CreateDirectory([IO.Path]::GetDirectoryName($target)) | Out-Null
                [IO.File]::Copy($temporaryOriginal, $target, $false)
            }
        }
        Write-Output "Verified $($entry.path) SHA256=$hash"
    } finally { [IO.File]::Delete($temporaryArchive); [IO.File]::Delete($temporaryOriginal) }
}
if ($Mode -eq 'Pack') {
    $manifest | ConvertTo-Json -Depth 10 | Set-Content -LiteralPath $manifestPath -Encoding UTF8
}