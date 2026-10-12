import argparse
import gzip
import hashlib
import json
import os
import re
import tempfile
from pathlib import Path


RUN_DIRECTORY = re.compile(r"Run[0-9]+$")
SAMPLE_LIMIT_PER_RUN_COUNT = 10


def native(path: Path) -> Path:
    path = Path(os.path.normpath(str(path)))
    if os.name == "nt" and not str(path).startswith("\\\\?\\"):
        return Path("\\\\?\\" + str(path.resolve()))
    return path


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def run_directories(root: Path) -> list[Path]:
    root = native(root)
    if not root.is_dir():
        raise ValueError(f"Run root is not a directory: {root}")
    runs = sorted((path for path in root.iterdir() if path.is_dir() and RUN_DIRECTORY.fullmatch(path.name)),
                  key=lambda path: int(path.name[3:]))
    if not runs:
        raise ValueError(f"No direct Run<number> directories found under: {root}")
    return runs


def analyze(root: Path) -> tuple[dict, list[dict]]:
    runs = run_directories(root)
    artifacts_by_hash: dict[str, dict] = {}
    run_rows = []
    manifest_groups: dict[str, list[str]] = {}
    total_files = 0
    total_bytes = 0

    for run in runs:
        manifest = []
        run_bytes = 0
        run_files = 0
        print(f"starting={run.name}", flush=True)
        for file in sorted(run.rglob("*")):
            if file.is_symlink() or not file.is_file():
                continue
            relative_path = file.relative_to(run).as_posix()
            size = file.stat().st_size
            digest = sha256_file(file)
            manifest.append({"path": relative_path, "sizeBytes": size, "sha256": digest})
            run_files += 1
            run_bytes += size
            artifact = artifacts_by_hash.setdefault(digest, {"sizeBytes": size, "runs": {}})
            artifact["runs"].setdefault(run.name, []).append(relative_path)
            if run_files % 5000 == 0:
                print(f"run={run.name} filesHashed={run_files}", flush=True)

        manifest_bytes = json.dumps(manifest, ensure_ascii=True, sort_keys=True, separators=(",", ":")).encode("utf-8")
        manifest_hash = hashlib.sha256(manifest_bytes).hexdigest()
        manifest_groups.setdefault(manifest_hash, []).append(run.name)
        run_rows.append({"runId": run.name, "fileCount": run_files, "totalBytes": run_bytes,
                         "manifestSha256": manifest_hash})
        total_files += run_files
        total_bytes += run_bytes
        print(f"finished={run.name} files={run_files} bytes={run_bytes}", flush=True)

    duplicate_run_groups = [
        {"manifestSha256": digest, "runIds": run_ids}
        for digest, run_ids in sorted(manifest_groups.items()) if len(run_ids) > 1
    ]
    duplicate_artifacts = []
    for digest, artifact in sorted(artifacts_by_hash.items()):
        if len(artifact["runs"]) < 2:
            continue
        duplicate_artifacts.append({
            "contentSha256": digest,
            "sizeBytes": artifact["sizeBytes"],
            "runCount": len(artifact["runs"]),
            "runs": [
                {"runId": run_id, "relativePaths": paths}
                for run_id, paths in sorted(artifact["runs"].items(), key=lambda item: int(item[0][3:]))
            ],
        })

    duplicate_counts: dict[str, int] = {}
    samples_by_run_count: dict[str, list[dict]] = {}
    for artifact in duplicate_artifacts:
        run_count = str(artifact["runCount"])
        duplicate_counts[run_count] = duplicate_counts.get(run_count, 0) + 1
        samples = samples_by_run_count.setdefault(run_count, [])
        if len(samples) < SAMPLE_LIMIT_PER_RUN_COUNT:
            samples.append(artifact)

    report = {
        "artifact": "ATL105-AI-RUN-CONTENT-DEDUPLICATION",
        "status": "DUPLICATE_CONTENT_CANDIDATES_FOUND" if duplicate_artifacts else "NO_CROSS_RUN_DUPLICATES_FOUND",
        "policy": "Reports byte-identical run manifests and artifacts across runs. It does not delete, merge, or suppress artifacts; identical bytes are deduplication candidates, not proof of semantic equivalence.",
        "runRootName": Path(root).name,
        "runCount": len(run_rows),
        "fileCount": total_files,
        "totalBytes": total_bytes,
        "uniqueContentCount": len(artifacts_by_hash),
        "duplicateRunGroupCount": len(duplicate_run_groups),
        "duplicateArtifactGroupCount": len(duplicate_artifacts),
        "duplicateArtifactGroupsByRunCount": duplicate_counts,
        "duplicateArtifactSampleLimitPerRunCount": SAMPLE_LIMIT_PER_RUN_COUNT,
        "runs": run_rows,
        "identicalRunGroups": duplicate_run_groups,
        "duplicateArtifactSamples": samples_by_run_count,
    }
    return report, duplicate_artifacts


def self_test() -> None:
    with tempfile.TemporaryDirectory() as temporary:
        root = Path(temporary)
        for run_id in ("Run1", "Run2", "Run3"):
            (root / run_id).mkdir()
        (root / "Run1" / "matrix.json").write_text("same matrix", encoding="utf-8")
        (root / "Run1" / "catalog.json").write_text("same catalog", encoding="utf-8")
        (root / "Run2" / "matrix.json").write_text("same matrix", encoding="utf-8")
        (root / "Run2" / "catalog.json").write_text("same catalog", encoding="utf-8")
        (root / "Run3" / "matrix.json").write_text("same matrix", encoding="utf-8")
        (root / "Run3" / "catalog.json").write_text("different catalog", encoding="utf-8")

        report, duplicate_artifacts = analyze(root)
        assert report["duplicateRunGroupCount"] == 1
        assert report["identicalRunGroups"][0]["runIds"] == ["Run1", "Run2"]
        assert report["duplicateArtifactGroupsByRunCount"] == {"2": 1, "3": 1}
        assert len(duplicate_artifacts) == 2
        print("AI run deduplication self-test passed")


def main() -> None:
    parser = argparse.ArgumentParser(description="Report identical AI run manifests and artifacts without modifying intake data.")
    parser.add_argument("runs_root", nargs="?", type=Path, help="Directory containing direct Run<number> folders")
    parser.add_argument("new_output", nargs="?", type=Path, help="New JSON report path outside runs_root")
    parser.add_argument("--self-test", action="store_true", help="Run a temporary-directory behavior check")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if args.runs_root is None or args.new_output is None:
        parser.error("runs_root and new_output are required unless --self-test is used")

    root = args.runs_root.resolve()
    output = args.new_output.resolve()
    try:
        output.relative_to(root)
    except ValueError:
        pass
    else:
        raise ValueError("Deduplication report must be outside runs_root")
    if output.exists():
        raise ValueError(f"Refusing to overwrite existing report: {output}")
    details_output = output.with_name(output.stem + "-duplicate-artifacts.jsonl.gz")
    if details_output.exists():
        raise ValueError(f"Refusing to overwrite existing detail report: {details_output}")

    report, duplicate_artifacts = analyze(root)
    output.parent.mkdir(parents=True, exist_ok=True)
    with gzip.open(details_output, "wt", encoding="utf-8", newline="\n") as stream:
        for artifact in duplicate_artifacts:
            stream.write(json.dumps(artifact, ensure_ascii=True, separators=(",", ":")) + "\n")
    report["duplicateArtifactDetailsFile"] = details_output.name
    report["duplicateArtifactDetailsSha256"] = sha256_file(details_output)
    output.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({key: report[key] for key in (
        "status", "runCount", "fileCount", "duplicateRunGroupCount", "duplicateArtifactGroupCount"
    )}, indent=2))


if __name__ == "__main__":
    main()