"""Verify an AI delivery copy byte-for-byte against its source and write an intake manifest."""

import argparse
import hashlib
import json
import os
import sys
from concurrent.futures import ThreadPoolExecutor
from datetime import datetime, timezone
from pathlib import Path


def native(path: Path) -> str:
    resolved = str(path.resolve())
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        return "\\\\?\\" + resolved
    return resolved


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with open(native(path), "rb") as handle:
        for block in iter(lambda: handle.read(1 << 20), b""):
            digest.update(block)
    return digest.hexdigest()


def relative_files(root: Path) -> list[str]:
    base = native(root)
    found = []
    for directory, _, files in os.walk(base):
        for name in files:
            found.append(os.path.relpath(os.path.join(directory, name), base).replace("\\", "/"))
    return sorted(found)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source", required=True, type=Path)
    parser.add_argument("--copy", required=True, type=Path)
    parser.add_argument("--manifest", required=True, type=Path)
    args = parser.parse_args()

    source_files = relative_files(args.source)
    copy_files = relative_files(args.copy)
    missing = sorted(set(source_files) - set(copy_files))
    extra = sorted(set(copy_files) - set(source_files))

    def compare(rel: str) -> dict:
        source_hash = sha256(args.source / rel)
        copy_hash = sha256(args.copy / rel)
        return {
            "path": rel,
            "bytes": os.path.getsize(native(args.copy / rel)),
            "sha256": copy_hash,
            "matchesSource": source_hash == copy_hash,
        }

    shared = sorted(set(source_files) & set(copy_files))
    with ThreadPoolExecutor(max_workers=8) as pool:
        entries = list(pool.map(compare, shared))
    mismatched = [entry["path"] for entry in entries if not entry["matchesSource"]]

    manifest = {
        "generatedAt": datetime.now(timezone.utc).isoformat(),
        "source": str(args.source),
        "copy": str(args.copy),
        "sourceFileCount": len(source_files),
        "copyFileCount": len(copy_files),
        "totalBytes": sum(entry["bytes"] for entry in entries),
        "missingFromCopy": missing,
        "extraInCopy": extra,
        "hashMismatches": mismatched,
        "verified": not (missing or extra or mismatched),
        "files": entries,
    }
    args.manifest.parent.mkdir(parents=True, exist_ok=True)
    args.manifest.write_text(json.dumps(manifest, indent=1), encoding="utf-8")
    print(json.dumps({key: manifest[key] for key in ("sourceFileCount", "copyFileCount", "totalBytes", "verified")}))
    print(f"missing={len(missing)} extra={len(extra)} mismatched={len(mismatched)}")
    return 0 if manifest["verified"] else 1


if __name__ == "__main__":
    sys.exit(main())
