import argparse
import csv
import hashlib
import json
import os
from pathlib import Path


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def sha256(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def index(root):
    result = {}
    for file in native(root).rglob("*"):
        if file.is_file():
            result[file.relative_to(native(root)).as_posix()] = file
    return result


def verify(source, archive, output):
    source_files = index(source)
    archived_files = index(archive)
    output = native(output)
    if output.exists():
        raise ValueError("Verification output must be new; refusing overwrite")
    output.mkdir(parents=True)
    rows = []
    mismatches = []
    for relative in sorted(set(source_files) | set(archived_files)):
        original = source_files.get(relative)
        copy = archived_files.get(relative)
        source_hash = sha256(original) if original else None
        archive_hash = sha256(copy) if copy else None
        same = source_hash is not None and source_hash == archive_hash
        if not same:
            mismatches.append(relative)
        rows.append({"path": relative, "sourceBytes": original.stat().st_size if original else None,
                     "archiveBytes": copy.stat().st_size if copy else None,
                     "sourceSha256": source_hash, "archiveSha256": archive_hash, "verified": same})
    with (output / "intake-file-hashes.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0]) if rows else ["path", "sourceBytes", "archiveBytes", "sourceSha256", "archiveSha256", "verified"])
        writer.writeheader()
        writer.writerows(rows)
    summary = {"source": str(source), "archive": str(archive), "sourceFiles": len(source_files),
        "archiveFiles": len(archived_files), "sourceBytes": sum(path.stat().st_size for path in source_files.values()),
        "archiveBytes": sum(path.stat().st_size for path in archived_files.values()), "hashesVerified": len(rows) - len(mismatches),
        "mismatchCount": len(mismatches), "mismatchSamples": mismatches[:100], "complete": not mismatches,
        "verification": "SHA256_PER_RELATIVE_PATH"}
    (output / "intake-verification.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
    print(json.dumps(summary, indent=2))
    if mismatches:
        raise SystemExit(2)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("source", type=Path)
    parser.add_argument("archive", type=Path)
    parser.add_argument("new_output", type=Path)
    args = parser.parse_args()
    verify(args.source, args.archive, args.new_output)


if __name__ == "__main__":
    main()