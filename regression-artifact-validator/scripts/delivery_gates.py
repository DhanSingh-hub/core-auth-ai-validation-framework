"""Independent, bounded gates for native ATL105 business-model delivery exports."""

import argparse
import csv
import hashlib
import json
import re
import sys
import zipfile
from collections import Counter
from pathlib import Path, PureWindowsPath


SOURCE = Path(__file__).resolve().parents[1] / "specifications" / "ATL105" / "docs" / "specs" / "extracted_text_new.txt"
SOURCE_SHA256 = "93f11336ae817266df786c8a83541f96ae680ea08629117a775d362c8b45a497"


def parse_json(text: str, context: str):
    def pairs(items):
        result = {}
        for key, value in items:
            if key in result:
                raise ValueError(f"Duplicate JSON key in {context}: {key}")
            result[key] = value
        return result

    def constant(value):
        raise ValueError(f"Invalid JSON number in {context}: {value}")

    return json.loads(text, object_pairs_hook=pairs, parse_constant=constant)


def load(path: Path):
    return parse_json(path.read_text(encoding="utf-8-sig"), path.name)


def read_cases(root: Path) -> list[dict]:
    path = root / "test_cases" / "test_cases.jsonl"
    # Apply the same strict JSON parser to each line without temporary files.
    cases = []
    with path.open(encoding="utf-8-sig") as stream:
        for number, line in enumerate(stream, 1):
            if line.strip():
                value = parse_json(line, f"{path.name}:{number}")
                if not isinstance(value, dict):
                    raise ValueError(f"TC line {number} is not an object")
                cases.append(value)
    return cases


def resolve_reference(root: Path, reference: str) -> Path:
    if not isinstance(reference, str) or not reference:
        raise ValueError("Empty or non-string artifact reference")
    win = PureWindowsPath(reference)
    parts = reference.replace("\\", "/").split("/")
    if win.drive or win.root or any(p in {"..", ""} for p in parts) or ":" in reference:
        raise ValueError(f"Unsafe artifact reference: {reference}")
    path = root.joinpath(*parts).resolve()
    if not path.is_relative_to(root.resolve()):
        raise ValueError("Artifact reference escapes package")
    return path


def packages(root: Path) -> list[Path]:
    if (root / "requirements_scenarios" / "business_requirements.json").is_file():
        return [root]
    found = sorted(p for p in root.iterdir() if p.is_dir()
                   and (p / "requirements_scenarios" / "business_requirements.json").is_file())
    if not found:
        raise ValueError("No native business-model delivery packages found")
    return found


def inventory(root: Path) -> dict[str, str]:
    result = {}
    for path in sorted(root.rglob("*")):
        if path.is_symlink():
            raise ValueError(f"Symlink not supported in delivery: {path.name}")
        if path.is_file():
            result[path.relative_to(root).as_posix()] = hashlib.sha256(path.read_bytes()).hexdigest()
    return result


def trusted_source() -> None:
    raw = SOURCE.read_bytes()
    if hashlib.sha256(raw).hexdigest() != SOURCE_SHA256:
        raise ValueError("Independent ATL105 source changed; re-review delivery predicates")
    if "Number: 188 Name: EMV Card Sequence" not in raw.decode("utf-8-sig"):
        raise ValueError("Independent Element 188 anchor missing")


def emv_tags(value: str) -> dict[str, str]:
    raw = bytes.fromhex(value)
    prefix = raw[:2].hex()
    if len(raw) < 2 or not prefix.isdigit() or int(prefix) != len(raw) - 2:
        raise ValueError("Invalid Appendix R packed-BCD table length")
    tags = {}
    pos = 2
    while pos < len(raw):
        start = pos
        first = raw[pos]
        pos += 1
        if first & 31 == 31:
            while True:
                if pos >= len(raw):
                    raise ValueError("Truncated EMV tag")
                byte = raw[pos]
                pos += 1
                if not byte & 128:
                    break
        tag = raw[start:pos].hex().upper()
        if pos >= len(raw):
            raise ValueError("Missing EMV length")
        length = raw[pos]
        pos += 1
        if length & 128:
            count = length & 127
            if not count or pos + count > len(raw):
                raise ValueError("Invalid EMV length")
            length = int.from_bytes(raw[pos:pos + count], "big")
            pos += count
        if pos + length > len(raw):
            raise ValueError("Truncated EMV value")
        if tag in tags:
            raise ValueError("Duplicate EMV tag")
        tags[tag] = raw[pos:pos + length].hex().upper()
        pos += length
    return tags


def trace_rows(trace: dict):
    for row in trace["flat_rows"]:
        if row.get("test_case_id"):
            yield "flat", row["test_case_id"], row.get("test_data_file"), None
    for requirement in trace["requirements"]:
        for scenario in requirement["scenarios"]:
            for case in scenario["test_cases"]:
                data = case.get("test_data", {})
                yield "nested", case["test_case_id"], data.get("test_data_file"), data.get("file_exists")


def validate_package(root: Path, previous: Path | None = None) -> dict:
    br = load(root / "requirements_scenarios" / "business_requirements.json")
    if br["meta"]["spec_version"] != "2026-4":
        raise ValueError("Native delivery gates currently support ATL105 2026-4 only")
    cases = read_cases(root)
    run = load(root / "run_set.json")
    trace = load(root / "reports" / "traceability_matrix_full.json")
    scenarios = load(root / "requirements_scenarios" / "business_scenarios.json")["scenarios"]
    issues = []

    def issue(code, entity, detail, status="FAIL"):
        issues.append({"code": code, "entity_id": entity, "status": status, "detail": detail})

    tmap = {t["id"]: t for t in cases}
    rmap = {r["id"]: r for r in run["index"]}
    if len(tmap) != len(cases) or len(rmap) != len(run["index"]) or set(tmap) != set(rmap):
        issue("IDENTITY_SET", "", "TC/run-set identity sets or uniqueness disagree")
    build = br["meta"]["build_id"]
    if run["build_id"] != build or trace["build"]["build_id"] != build:
        issue("BUILD_PARITY", "", "BR/run-set/trace build differs")
    smap = {s["id"]: s for s in scenarios}
    refs = set()
    emv_count = 0
    for tc in cases:
        tid = tc["id"]
        row = rmap.get(tid, {})
        if tc.get("qualified_id") is not None and tc["qualified_id"] != build + ":" + tid:
            issue("QUALIFIED_ID", tid, "Qualified identity must bind exact build and original TC ID")
        files = row.get("test_data_files", [])
        steps = tc.get("steps") or ([tc] if tc.get("tx_legs") else [])
        if len(steps) != len(files):
            issue("LEG_FILE_COUNT", tid, "Run-set physical leg count differs")
        if tc["scenario_id"] not in smap or tc["build_id"] != build:
            issue("TC_PARENT_BUILD", tid, "Scenario or build does not resolve")
        if row.get("status") != tc["status"]:
            issue("RUN_STATUS", tid, "Run-set status differs from TC")
        if files and tc.get("test_data_file") != "test_data/" + files[0]:
            issue("PRIMARY_REFERENCE", tid, "Primary pointer differs from first run-set leg")
        if len(files) > 1 and tc.get("test_data_leg_files") != ["test_data/" + f for f in files]:
            issue("ALL_LEG_REFERENCES", tid, "All-leg references differ from run-set order")
        if tc.get("awaiting_inputs") and tc["status"] == "ready":
            issue("READY_INPUTS", tid, "ready despite declared awaiting inputs")
        if tc["status"] == "ready" and any(
            flag.startswith("AWAITING_CLIENT_VALUE: Encrypted PIN Block Data required on step")
            for flag in tc.get("flags", [])
        ):
            issue("READY_REQUIRED_PIN", tid, "ready despite explicit required-PIN-awaiting-client flag")
        if tc["status"] == "ready" and tc.get("open_items"):
            issue("READY_OPEN_ITEMS", tid, "Execution prerequisites remain open", "REVIEW_REQUIRED")
        wires = []
        for number, filename in enumerate(files, 1):
            ref = "test_data/" + filename
            path = resolve_reference(root, ref)
            refs.add(path)
            if not path.is_file():
                issue("TD_MISSING", tid, f"Leg {number} file missing")
                continue
            wire = load(path)
            if len(wire) != 1:
                issue("MESSAGE_WRAPPER", tid, f"Leg {number} must have one message")
                continue
            body = next(iter(wire.values()))
            wires.append(body)
            segments = [v for v in body.values() if isinstance(v, dict) and "SegmentType" in v]
            if str(len(segments)).zfill(2) != body.get("NumberofSegments"):
                issue("SEGMENT_COUNT", tid, f"Leg {number} segment count differs")
            emv = body.get("EMV Request Data Segment")
            if emv:
                emv_count += 1
                value = emv.get("EMVCardSequenceNumber")
                if not isinstance(value, str) or not (
                    value == "   " or re.fullmatch(r"0[0-9]{2}", value, flags=re.ASCII)
                ):
                    issue("ELEMENT_188_WIDTH", tid, f"Leg {number}: Element 188 requires three bytes (p.477)")
                try:
                    tags = emv_tags(emv["EMVChipData"])
                except ValueError as exc:
                    issue("EMV_TLV", tid, f"Leg {number}: {exc}")
                    continue
                if "5F34" in tags and (
                    len(tags["5F34"]) != 2 or not tags["5F34"].isdigit()
                    or str(int(tags["5F34"])).zfill(3) != value
                ):
                    issue("ELEMENT_188_TAG", tid, f"Leg {number}: Element 188 differs from tag 5F34")
                if "5F34" not in tags and value != "   ":
                    issue("ELEMENT_188_ABSENT_TAG", tid, f"Leg {number}: absent tag requires three spaces")
                chip_bytes = len(bytes.fromhex(emv["EMVChipData"]))
                if not str(emv["EMVChipDataLength"]).isdigit() or int(emv["EMVChipDataLength"]) != chip_bytes:
                    issue("EMV_CHIP_LENGTH", tid, f"Leg {number}: chip byte count differs")
                # Section 12.20 has seven FS bytes for this producer's single section.
                length = chip_bytes + 7 + sum(
                    len(v.encode("ascii")) for k, v in emv.items()
                    if k != "EMVChipData" and isinstance(v, str))
                if not str(emv["SegmentLength"]).isdigit() or int(emv["SegmentLength"]) != length:
                    issue("EMV_SEGMENT_LENGTH", tid, f"Leg {number}: rendered segment length differs")
                amount = body.get("Standard Message Data Segment", {}).get("NonfuelAmount", "")
                if "<" in str(amount) and "9F02" in tags:
                    issue("EMV_RUNTIME_AMOUNT", tid, f"Leg {number}: dynamic amount requires dependent "
                          "EMV data and cryptogram generation", "REVIEW_REQUIRED")
                if number <= len(steps) and "EMV_PLACEHOLDER" in json.dumps(steps[number - 1]):
                    issue("EMV_CRYPTO_PLACEHOLDER", tid, f"Leg {number}: simulator evidence required",
                          "REVIEW_REQUIRED")
        for number, leg in enumerate(tc.get("tx_legs", [])):
            if leg.get("role") == "split_tender" and number < len(wires):
                first = wires[0]["Standard Message Data Segment"]
                second = wires[number]["Standard Message Data Segment"]
                if second.get("ApprovalNumber"):
                    issue("SPLIT_APPROVAL", tid, "Independent purchase contains an approval (Element 5)")
                if not second.get("AccountNumber") or second.get("AccountNumber") == first.get("AccountNumber"):
                    issue("SPLIT_ACCOUNT", tid, "Second-card binding is absent or reuses first account")
    delivered = {p.resolve() for p in (root / "test_data").glob("*.json")}
    if delivered != refs or run["files"] != len(delivered) or run["test_cases"] != len(cases):
        issue("TD_POPULATION", "", "Run-set counts/references differ from final TD population")
    trace_ids = set()
    for surface, tid, reference, exists in trace_rows(trace):
        trace_ids.add(tid)
        expected = tmap.get(tid, {}).get("test_data_file")
        if reference != expected:
            issue("TRACE_POINTER", tid, f"{surface}: trace pointer differs from final TC pointer")
        actual = bool(reference) and resolve_reference(root, reference).is_file()
        if reference and not actual:
            issue("TRACE_PATH", tid, f"{surface}: final exported path does not resolve")
        if exists is not None and exists != bool(actual):
            issue("TRACE_EXISTS", tid, f"{surface}: file_exists disagrees with delivered bytes")
    for requirement in trace["requirements"]:
        for scenario in requirement["scenarios"]:
            for case in scenario["test_cases"]:
                tid = case["test_case_id"]
                data = case.get("test_data", {})
                expected_files = ["test_data/" + f for f in rmap.get(tid, {}).get("test_data_files", [])]
                if data.get("test_data_files") != expected_files:
                    issue("TRACE_ALL_LEGS", tid, "Nested trace does not list every final physical leg")
    for row in trace["flat_rows"]:
        tid = row.get("test_case_id")
        if tid and row.get("test_data_files") != [
            "test_data/" + f for f in rmap.get(tid, {}).get("test_data_files", [])
        ]:
            issue("TRACE_ALL_LEGS", tid, "Flat trace does not list every final physical leg")
    if trace_ids != set(tmap):
        issue("TRACE_ID_SET", "", "Trace TC set differs from export")
    covered = {t["scenario_id"] for t in cases}
    for sid in set(smap) - covered:
        issue("UNCOVERED_SCENARIO", sid, "Delivered scenario has no TC", "REVIEW_REQUIRED")
    if not trace.get("coverage_basis"):
        issue("COVERAGE_BASIS", "", "Define structural denominators; full-chain linkage is not semantic coverage",
              "REVIEW_REQUIRED")
    else:
        summary = trace["summary"]
        if (summary["total_scenarios"] != len(scenarios)
                or summary["scenarios_with_test_cases"] != len(covered)
                or summary["total_test_cases"] != len(cases)
                or summary["test_cases_with_data_file"] != sum(bool(r["test_data_files"]) for r in run["index"])):
            issue("TRACE_COUNTS", "", "Trace structural coverage counts differ from final population")
        if summary.get("semantic_coverage_percent") is not None:
            issue("SEMANTIC_COVERAGE_UNPROVEN", "", "These bounded gates cannot certify a semantic percentage")
    if previous:
        old = {t["id"]: t for t in read_cases(previous)}
        for tid in set(old) & set(tmap):
            if (old[tid]["name"], old[tid].get("template_id")) != (
                tmap[tid]["name"], tmap[tid].get("template_id")
            ) and not tmap[tid].get("qualified_id"):
                issue("HISTORICAL_ID", tid, "Reassigned bare TC ID requires build-qualified identity")
    for archive in root.glob("*.zip"):
        with zipfile.ZipFile(archive) as zipped:
            name = "test_cases/test_cases.jsonl"
            if name in zipped.namelist():
                archived = [json.loads(line) for line in zipped.read(name).decode("utf-8-sig").splitlines()
                            if line.strip()]
                if len(archived) != len(cases) or any(t.get("build_id") != build for t in archived):
                    issue("STALE_ARCHIVE", archive.name, "Embedded TC archive differs from authoritative build")
    readme = root / "README.md"
    if readme.exists() and build not in readme.read_text(encoding="utf-8-sig"):
        issue("STALE_README", "README.md", "README does not identify authoritative build")
    with (root / "test_cases" / "test_cases.csv").open(encoding="utf-8-sig", newline="") as stream:
        exported = list(csv.DictReader(stream))
    if len(exported) != len(cases) or {r["tc_id"] for r in exported} != set(tmap):
        issue("CSV_ID_SET", "", "CSV TC set differs")
    for row in exported:
        tc = tmap.get(row["tc_id"], {})
        if row["status"] != tc.get("status"):
            issue("CSV_STATUS", row["tc_id"], "CSV status differs from JSONL")
    return {
        "package": root.name, "build_id": build, "counts": {
            "scenarios": len(scenarios), "scenarios_with_tcs": len(covered),
            "test_cases": len(cases), "test_data_files": len(delivered), "emv_files": emv_count,
        },
        "gate": "FAIL" if any(i["status"] == "FAIL" for i in issues) else "PASS",
        "execution_readiness": "BLOCKED", "semantic_certification": "NOT_CERTIFIED",
        "issues": issues, "issue_counts": dict(Counter(i["code"] for i in issues)),
        "limits": "Named offline gates only; no full serializer, host, crypto or whole-spec certification.",
    }


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    parser.add_argument("--previous", type=Path)
    args = parser.parse_args()
    trusted_source()
    if args.output.exists() or args.output.resolve().is_relative_to(args.input.resolve()):
        raise ValueError("Output must be new and outside input")
    before = inventory(args.input)
    inputs = packages(args.input)
    if args.previous and len(inputs) != 1:
        raise ValueError("--previous requires a single current package to avoid cross-network ID comparison")
    reports = [validate_package(p, args.previous) for p in inputs]
    if before != inventory(args.input):
        raise RuntimeError("Input changed during validation")
    args.output.mkdir(parents=True)
    report = {"source_sha256": SOURCE_SHA256, "packages": reports,
              "gate": "FAIL" if any(r["gate"] == "FAIL" for r in reports) else "PASS",
              "execution_readiness": "BLOCKED", "semantic_certification": "NOT_CERTIFIED"}
    (args.output / "validation.json").write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    (args.output / "input-manifest.json").write_text(json.dumps(before, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({r["package"]: {"gate": r["gate"], "issues": r["issue_counts"]} for r in reports}, indent=2))
    return 1 if report["gate"] == "FAIL" else 0


if __name__ == "__main__":
    sys.exit(main())
