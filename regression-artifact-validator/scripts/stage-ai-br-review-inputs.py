#!/usr/bin/env python3
"""Stage roster-linked AI payloads and sidecars without modifying source files."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import shutil
import sys
from datetime import date
from pathlib import Path, PurePosixPath
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
REVIEW_V3 = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-all-ai-br-chain-test-br-review-v3"
DEFAULT_SOURCE_ROOT = Path(
    r"C:\Users\F5H46GZ\OneDrive - Fiserv Corp\Documents\nitin verma\AI Solutions Output\10_oct_artifacts 3\10_oct_artifacts"
)


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def path_state(value: str) -> str | None:
    parts = PurePosixPath(value).parts
    states = [part for part in parts if part in {"approved", "candidates"}]
    return states[0] if len(states) == 1 else None


def load_roster(register_path: Path, ai_br_id: str) -> dict[str, Any]:
    csv.field_size_limit(100000000)
    with register_path.open(encoding="utf-8-sig", newline="") as stream:
        rows = [row for row in csv.DictReader(stream) if row.get("aiBrId") == ai_br_id]
    if len(rows) != 1:
        raise ValueError(f"Expected exactly one AI BR row for {ai_br_id}, found {len(rows)}")
    row = rows[0]
    return {
        "aiBrId": ai_br_id,
        "caseIds": json.loads(row.get("aiCaseIds") or "[]"),
        "payloadPaths": json.loads(row.get("aiPayloadPaths") or "[]"),
    }


def stage(source_root: Path, destination: Path, review_v3: Path, ai_br_id: str,
          include_states: list[str]) -> dict[str, Any]:
    register_path = review_v3 / "all-ai-br-review-register.csv"
    roster = load_roster(register_path, ai_br_id)
    source_root = source_root.resolve()
    destination.mkdir(parents=True, exist_ok=True)
    if any(destination.iterdir()):
        raise ValueError(f"Refusing to stage into nonempty directory: {destination}")

    files: list[dict[str, Any]] = []
    missing_selected: list[dict[str, str]] = []
    not_selected: list[dict[str, Any]] = []
    seen: set[str] = set()
    for relative_payload in roster["payloadPaths"]:
        state = path_state(relative_payload)
        if state is None:
            not_selected.append({"path": relative_payload, "state": "UNCLASSIFIED"})
            continue
        if state not in include_states:
            original = source_root / Path(relative_payload)
            not_selected.append({"path": relative_payload, "state": state,
                                 "sourceExists": original.is_file()})
            continue
        payload_relative = PurePosixPath(relative_payload)
        sidecar_relative = payload_relative.with_suffix(".meta.json")
        for relative, role in ((payload_relative, "candidate-payload"), (sidecar_relative, "candidate-sidecar")):
            normalized_relative = relative.as_posix()
            if normalized_relative in seen:
                continue
            seen.add(normalized_relative)
            source_path = (source_root / Path(normalized_relative)).resolve()
            try:
                source_path.relative_to(source_root)
            except ValueError as error:
                raise ValueError(f"Roster path escapes source root: {normalized_relative}") from error
            if not source_path.is_file():
                missing_selected.append({"path": normalized_relative, "role": role})
                continue
            destination_path = destination / Path(normalized_relative)
            destination_path.parent.mkdir(parents=True, exist_ok=True)
            if destination_path.exists():
                raise ValueError(f"Refusing to overwrite staged file: {destination_path}")
            source_hash = sha256(source_path)
            shutil.copyfile(source_path, destination_path)
            destination_hash = sha256(destination_path)
            if source_hash != destination_hash:
                raise ValueError(f"SHA-256 mismatch after staging: {normalized_relative}")
            files.append({
                "path": normalized_relative,
                "sourcePath": str(source_path),
                "sha256": source_hash,
                "role": role,
                "sourceState": state,
            })

    selected_states = set(include_states)
    missing_unselected = [item for item in not_selected
                          if item.get("state") in {"approved", "candidates"} and not item.get("sourceExists")]
    manifest = {
        "intakeId": f"ATL105-AI-{ai_br_id.replace(':', '-')}-{date.today().isoformat()}",
        "intakeDate": date.today().isoformat(),
        "aiBrId": ai_br_id,
        "sourceRoot": str(source_root),
        "destinationRoot": str(destination.resolve()),
        "includedStates": sorted(selected_states),
        "caseIds": roster["caseIds"],
        "sourceRegister": {"path": str(register_path), "sha256": sha256(register_path)},
        "files": files,
        "missingSelectedFiles": missing_selected,
        "unselectedRegisteredPaths": not_selected,
        "missingUnselectedRegisteredPaths": missing_unselected,
        "sourceFilesModified": False,
        "valuesInspectedOrExported": False,
    }
    manifest_path = destination / "intake-manifest.json"
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return manifest


def self_test() -> None:
    if path_state("test_generation/test_generation/approved/qe_shaped_test_data/TC-1.json") != "approved":
        raise AssertionError("Approved path state detection failed")
    if path_state("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data/TC-1.json") != "candidates":
        raise AssertionError("Candidate path state detection failed")
    if path_state("unclassified/TC-1.json") is not None:
        raise AssertionError("Unclassified path was assigned a state")
    print("PASS: source-tree status detection; roster paths remain dynamically selected.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--ai-br-id", required=True)
    parser.add_argument("--source-root", type=Path, default=DEFAULT_SOURCE_ROOT)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--destination", type=Path, required=True)
    parser.add_argument("--include-states", nargs="+", choices=("approved", "candidates"),
                        default=["candidates"])
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return 0
    manifest = stage(args.source_root, args.destination, args.review_v3, args.ai_br_id, args.include_states)
    print(json.dumps({"intakeManifest": str(args.destination / "intake-manifest.json"),
                      "caseCount": len(manifest["caseIds"]),
                      "fileCount": len(manifest["files"]),
                      "missingSelectedFiles": len(manifest["missingSelectedFiles"]),
                      "missingUnselectedRegisteredPaths": len(manifest["missingUnselectedRegisteredPaths"])},
                     ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError, csv.Error) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)