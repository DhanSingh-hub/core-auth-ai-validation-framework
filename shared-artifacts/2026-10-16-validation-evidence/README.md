# Shared Validation Evidence

These 17 binary parts contain nine oversized validation/audit outputs, losslessly gzip-compressed from the staged Git snapshot. They are not standalone JSON files. The manifest preserves original paths, byte counts, SHA-256 hashes, Git blob IDs, and ordered part hashes. No evidence content or approval status is changed.

From the repository root, using PowerShell 7:

```powershell
pwsh -File regression-artifact-validator/scripts/powershell/share-large-artifacts.ps1 -Mode Verify
pwsh -File regression-artifact-validator/scripts/powershell/share-large-artifacts.ps1 -Mode Restore
```

Restore recreates original files at their expected paths, which are intentionally ignored by Git. Existing identical files are accepted; differing files are never overwritten. Use `-DestinationRoot C:\path\to\separate-folder` for a separate copy. Verification checks each part and the decompressed originals. Allow temporary disk space for the compressed archive and the largest original (about 1 GB). All team members must pull these parts and restore before using reports that reference the originals.