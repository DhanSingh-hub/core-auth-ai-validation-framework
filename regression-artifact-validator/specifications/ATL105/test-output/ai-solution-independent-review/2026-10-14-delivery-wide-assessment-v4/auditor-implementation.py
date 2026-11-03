"""Audit all producer stages without treating producer approval as validation."""

import argparse
import collections
import hashlib
import json
import runpy
import tempfile
from pathlib import Path

import ijson


CATALOGS = {
    "requirement_catalog.json": ("BR", "requirements"),
    "requirement_candidates.json": ("BR", "requirements"),
    "approved_scenarios.json": ("TS", "scenarios"),
    "candidate_scenarios.json": ("TS", "scenarios"),
    "test_case_catalog.json": ("TC", "test_cases"),
    "test_case_candidates.json": ("TC", "test_cases"),
}
PRODUCER_STATE_KEYS = {"approval_status", "approval_note", "approval_audit", "approved_by", "approved_at", "status", "state"}


class UniqueKeys(dict):
    def __setitem__(self, key, value):
        if key in self:
            raise ValueError("Duplicate streaming record key: " + key)
        super().__setitem__(key, value)


def canonical(value):
    return json.dumps(value, sort_keys=True, ensure_ascii=True, separators=(",", ":"))


def fingerprint(value):
    return hashlib.sha256(canonical(value).encode("utf-8")).hexdigest()


def file_hash(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def strict_object(pairs):
    result = {}
    for key, value in pairs:
        if key in result:
            raise ValueError("Duplicate JSON key: " + key)
        result[key] = value
    return result


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8-sig"), object_pairs_hook=strict_object)


def write_rows(path, rows):
    with path.open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(canonical(row) + "\n")


def catalog_header(path, array):
    metadata, keys = {}, set()
    array_present = False
    with path.open("rb") as stream:
        for prefix, event, value in ijson.parse(stream, use_float=True):
            if prefix == "" and event == "map_key":
                if value in keys:
                    raise ValueError("Duplicate catalog key: " + value)
                keys.add(value)
            if prefix == array and event == "start_array":
                array_present = True
            if "." not in prefix and prefix and event in ("string", "number", "boolean", "null"):
                metadata[prefix] = value
    if not array_present:
        raise ValueError("Missing required catalog array: " + array)
    return metadata


def references(row, plural, singular):
    values = row.get(plural) or []
    if isinstance(values, str):
        values = [values]
    if not isinstance(values, list):
        raise ValueError("Invalid link collection: " + plural)
    return sorted({str(value) for value in values if value} | ({str(row[singular])} if row.get(singular) else set()))


def safe_path(root, path):
    resolved = path.resolve()
    if not resolved.is_relative_to(root.resolve()):
        raise ValueError("Path escapes delivery root: " + str(path))
    return resolved


def proposed_anchor(row, source_pages):
    evidence = row.get("evidence_ref") or {}
    if not isinstance(evidence, dict):
        evidence = {}
    related = row.get("related_entity_ids") or []
    elements = sorted({str(value)[9:] for value in related if str(value).startswith("ENT-ELEM-")})
    explicit_type = str(row.get("category") or "").upper().replace(" ", "_").replace("-", "_")
    types = {"PROCESS": "PROCESS", "LIFECYCLE": "LIFECYCLE", "RESPONSE": "RESPONSE", "CROSS_FIELD": "CROSS_FIELD", "TRANSPORT": "TRANSPORT"}
    anchor_type = "CROSS_FIELD" if len(elements) > 1 else "ELEMENT" if elements else types.get(explicit_type, "UNRESOLVED")
    page = evidence.get("page", row.get("source_page"))
    quote = evidence.get("quote")
    verified = isinstance(page, int) and isinstance(quote, str) and bool(quote.strip()) and quote in source_pages.get(page, "")
    return {
        "type": anchor_type, "typeBasis": "PRODUCER_DECLARED_METADATA_ONLY",
        "proposedSegmentNumber": row.get("segment_number"), "proposedSegmentIds": row.get("segment_ids") or [],
        "scope": row.get("scope_name"), "sourcePage": page, "sourceId": evidence.get("source_id"),
        "sourceQuote": quote, "quoteVerified": verified,
        "status": "SOURCE_QUOTE_VERIFIED_INTERPRETATION_REVIEW_REQUIRED" if verified else "NOT_ASSESSED_SOURCE_QUOTE_REQUIRED",
    }


def source_page_index(text):
    import re
    parts = re.split(r"--- PAGE ([0-9]+) ---", text)
    return {int(parts[index]): parts[index + 1] for index in range(1, len(parts) - 1, 2)}


def is_physical_data_json(path):
    return "qe_shaped_test_data" in path.parts and path.suffix == ".json" and path.name != "traceability.json"


def physical_case(root, catalog, row, files):
    declared = []
    if row.get("test_data_file"):
        declared.append(row["test_data_file"])
    legs = row.get("test_data_leg_files") or []
    if isinstance(legs, dict):
        legs = list(legs.values())
    if not isinstance(legs, list):
        return {"status": "INCOMPLETE", "issues": ["INVALID_LEG_DECLARATION"], "paths": []}
    declared.extend(legs)
    paths, issues = [], []
    if not declared:
        issues.append("NO_EXPLICIT_TD_DECLARATION")
    for declaration in declared:
        if not isinstance(declaration, str) or not declaration.strip():
            issues.append("INVALID_TD_DECLARATION")
            continue
        try:
            payload = safe_path(root, catalog.parent / declaration.replace("\\", "/"))
        except ValueError:
            issues.append("UNSAFE_TD_PATH")
            continue
        relative = payload.relative_to(root).as_posix()
        paths.append(relative)
        info = files.get(relative)
        if not info:
            issues.append("PAYLOAD_MISSING:" + relative)
            continue
        if info.get("jsonStatus") != "OBJECT":
            issues.append("PAYLOAD_INVALID_OR_EMPTY:" + relative)
        meta_relative = payload.with_suffix(".meta.json").relative_to(root).as_posix()
        meta = files.get(meta_relative)
        if not meta or meta.get("jsonStatus") != "OBJECT":
            issues.append("METADATA_MISSING_OR_INVALID:" + meta_relative)
        elif meta.get("testCaseId") != row.get("id") or meta.get("scenarioId") != row.get("scenario_id"):
            issues.append("METADATA_ID_MISMATCH:" + meta_relative)
    return {"status": "COMPLETE_PHYSICAL_DECLARATIONS" if not issues else "INCOMPLETE", "issues": issues, "paths": sorted(set(paths))}


def audit(root, baseline_dir, source_text, output):
    root, baseline_dir, source_text, output = (Path(path).resolve() for path in (root, baseline_dir, source_text, output))
    if output.exists() or output.is_relative_to(root):
        raise ValueError("Output must be new and outside the immutable delivery")
    baseline_path = baseline_dir / "source-derived-requirement-inventory.json"
    classification_path = baseline_dir / "atomic-composite-rule-classification.json"
    baseline, classification = read_json(baseline_path), read_json(classification_path)
    rules = baseline.get("businessRequirements") or []
    rule_ids = {rule["ruleId"] for rule in rules}
    if not rules or len(rule_ids) != len(rules) or baseline.get("ruleCount") != len(rules) or baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent baseline is empty or inconsistent")
    classified = classification.get("rules") or []
    if {rule["ruleId"] for rule in classified} != rule_ids or len(classified) != len(rules):
        raise ValueError("Classification IDs must match the independent baseline exactly")
    pages = source_page_index(source_text.read_text(encoding="utf-8-sig"))
    output.mkdir(parents=True)
    dependency_snapshot = output / "crosswalk-implementation.py"
    dependency_source = Path(__file__).with_name("export-ai-test-br-crosswalk.py")
    if not dependency_source.is_file():
        dependency_source = Path(__file__).with_name("crosswalk-implementation.py")
    dependency_snapshot.write_bytes(dependency_source.read_bytes())
    auditor_snapshot = output / "auditor-implementation.py"
    auditor_snapshot.write_bytes(Path(__file__).read_bytes())
    crosswalk = runpy.run_path(str(dependency_snapshot))
    index = crosswalk["source_rule_index"](classified)
    input_hashes = {str(path): file_hash(path) for path in (baseline_path, classification_path, source_text, Path(__file__), auditor_snapshot, dependency_snapshot)}
    files = {}
    catalog_paths = []
    with (output / "file-inventory.jsonl").open("w", encoding="utf-8") as inventory:
        for path in sorted(root.rglob("*")):
            if not path.is_file():
                continue
            safe_path(root, path)
            relative = path.relative_to(root).as_posix()
            info = {"path": relative, "bytes": path.stat().st_size, "sha256": file_hash(path), "kind": "AUXILIARY"}
            if path.name in CATALOGS:
                catalog_paths.append(path)
                info["kind"] = "CATALOG"
            if is_physical_data_json(path):
                info["kind"] = "METADATA" if path.name.endswith(".meta.json") else "PAYLOAD"
                try:
                    data = read_json(path)
                    info["jsonStatus"] = "OBJECT" if isinstance(data, dict) and data else "EMPTY_OR_UNSUPPORTED"
                    if info["kind"] == "METADATA" and isinstance(data, dict):
                        info.update({key: data.get(key) for key in ("testCaseId", "scenarioId", "role", "step")})
                except (ValueError, UnicodeError):
                    info["jsonStatus"] = "MALFORMED"
            files[relative] = info
            inventory.write(canonical(info) + "\n")
    print("Inventoried", len(files), "files; reading", len(catalog_paths), "catalogs", flush=True)
    variants = {kind: collections.defaultdict(dict) for kind in ("BR", "TS", "TC")}
    occurrences, chains = [], []
    catalog_counts = {}
    for path in catalog_paths:
        kind, array = CATALOGS[path.name]
        relative = path.relative_to(root).as_posix()
        metadata = catalog_header(path, array)
        count = 0
        seen = set()
        with path.open("rb") as stream:
            for row in ijson.items(stream, array + ".item", use_float=True, map_type=UniqueKeys):
                if not isinstance(row, dict):
                    raise ValueError("Catalog row is not an object: " + relative)
                count += 1
                identifier = row.get("id")
                if not isinstance(identifier, str) or not identifier:
                    identifier = "MISSING_ID:" + relative + ":" + str(count)
                content = fingerprint({key: value for key, value in row.items() if key not in PRODUCER_STATE_KEYS})
                occurrence = {"kind": kind, "id": identifier, "catalog": relative, "ordinal": count, "recordSha256": fingerprint(row), "contentSha256": content,
                              "producerStatus": {key: row[key] for key in sorted(PRODUCER_STATE_KEYS) if key in row}, "sourceTier": "approved" if "approved" in path.parts else "candidates", "duplicateIdWithinCatalog": identifier in seen}
                occurrence["producerCatalogState"] = metadata.get("state", "NOT_DECLARED")
                seen.add(identifier)
                occurrences.append(occurrence)
                compact = variants[kind][identifier].setdefault(content, {"id": identifier, "contentSha256": content, "sourceCatalogs": [], "flags": row.get("flags") or []})
                compact["sourceCatalogs"].append(relative)
                if kind == "BR":
                    segments, elements, status, matches = crosswalk["ai_anchor"](row, index)
                    compact.update({"statement": row.get("statement"), "anchorProposal": proposed_anchor(row, pages), "candidateDisposition": status, "candidateSegments": segments, "candidateElements": elements, "testBrCandidates": [match["ruleId"] for match in matches]})
                elif kind == "TS":
                    compact["requirementIds"] = references(row, "requirement_ids", "requirement_id")
                else:
                    compact["scenarioId"] = row.get("scenario_id")
                    compact["requirementIds"] = references(row, "requirement_ids", "requirement_id")
                    physical = physical_case(root, path, row, files)
                    chains.append({"id": identifier, "contentSha256": content, "catalog": relative, "scenarioId": row.get("scenario_id"), **physical})
        catalog_counts[relative] = {"kind": kind, "records": count, "uniqueIds": len(seen), "sha256": files[relative]["sha256"], "producerMetadata": metadata}
        print(kind, relative, count, flush=True)
    write_rows(output / "artifact-occurrences.jsonl", occurrences)
    write_rows(output / "tc-physical-assessment.jsonl", chains)
    conflicts = []
    for kind, by_id in variants.items():
        for identifier, versions in by_id.items():
            if len(versions) > 1:
                conflicts.append({"kind": kind, "id": identifier, "variants": list(versions), "status": "REVIEW_REQUIRED_CONTENT_CONFLICT"})
    write_rows(output / "content-conflicts.jsonl", conflicts)
    blocked = {(row["kind"], row["id"]) for row in conflicts}
    blocked.update((row["kind"], row["id"]) for row in occurrences if row["duplicateIdWithinCatalog"] or row["id"].startswith("MISSING_ID:"))
    clean = {kind: {identifier: next(iter(versions.values())) for identifier, versions in by_id.items() if (kind, identifier) not in blocked} for kind, by_id in variants.items()}
    scenarios_by_br, cases_by_ts = collections.defaultdict(set), collections.defaultdict(set)
    issues = []
    for identifier, scenario in clean["TS"].items():
        for br in scenario["requirementIds"]:
            if br in clean["BR"]:
                scenarios_by_br[br].add(identifier)
            else:
                issues.append({"kind": "TS", "id": identifier, "issue": "UNRESOLVED_BR_REFERENCE", "target": br})
    for identifier, case in clean["TC"].items():
        if case["scenarioId"] in clean["TS"]:
            scenario_brs = set(clean["TS"][case["scenarioId"]]["requirementIds"])
            if set(case["requirementIds"]) - scenario_brs:
                issues.append({"kind": "TC", "id": identifier, "issue": "TC_BR_LINK_NOT_IN_SCENARIO", "target": sorted(set(case["requirementIds"]) - scenario_brs)})
            else:
                cases_by_ts[case["scenarioId"]].add(identifier)
        else:
            issues.append({"kind": "TC", "id": identifier, "issue": "UNRESOLVED_SCENARIO_REFERENCE", "target": case["scenarioId"]})
    physically_complete = {row["id"] for row in chains if row["status"] == "COMPLETE_PHYSICAL_DECLARATIONS" and row["id"] in clean["TC"]}
    claimed_paths = {path for row in chains for path in row["paths"]}
    orphan_files = [info for path, info in files.items() if info["kind"] == "PAYLOAD" and path not in claimed_paths]
    write_rows(output / "unreferenced-payloads.jsonl", orphan_files)
    write_rows(output / "auxiliary-artifacts-unassessed.jsonl", ({**info, "validationStatus": "NOT_ASSESSED_FORMAT_SPECIFIC_VALIDATOR_REQUIRED"} for info in files.values() if info["kind"] == "AUXILIARY"))
    write_rows(output / "broken-links.jsonl", issues)
    write_rows(output / "incomplete-tc-declarations.jsonl", (row for row in chains if row["issues"]))
    br_rows, pair_rows = [], []
    reached, chain_reached = set(), set()
    candidate_by_rule = collections.defaultdict(set)
    for identifier, versions in variants["BR"].items():
        for content, br in versions.items():
            eligible = identifier in clean["BR"]
            ts_ids = sorted(scenarios_by_br[identifier]) if eligible else []
            tc_ids = sorted({case for scenario in ts_ids for case in cases_by_ts[scenario]})
            complete_ids = sorted(set(tc_ids) & physically_complete)
            row = {**br, "eligibleForStructuralMetrics": eligible, "scenarioIds": ts_ids, "testCaseIds": tc_ids, "physicalCompleteCaseIds": complete_ids, "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_ASSESSED"}
            br_rows.append(row)
            if eligible:
                reached.update(br["testBrCandidates"])
                if complete_ids:
                    chain_reached.update(br["testBrCandidates"])
            for rule in br["testBrCandidates"]:
                candidate_by_rule[rule].add(identifier)
                pair_rows.append({"aiBrId": identifier, "aiContentSha256": content, "testBrId": rule, "candidateDisposition": br["candidateDisposition"], "decisionStatus": "PENDING_AUTHORIZED_SOURCE_REVIEW", "semanticStatus": "NOT_ASSESSED"})
    write_rows(output / "br-assessment.jsonl", br_rows)
    write_rows(output / "candidate-review-queue.jsonl", pair_rows)
    write_rows(output / "anchor-review-queue.jsonl", (row for row in br_rows if row["anchorProposal"]["status"] != "CONFIRMED"))
    write_rows(output / "test-br-ledger.jsonl", ({"testBrId": rule["ruleId"], "sourceAnchor": rule["sourceAnchor"], "candidateAiBrIds": sorted(candidate_by_rule[rule["ruleId"]]), "candidateReached": rule["ruleId"] in reached, "physicalChainCandidateReached": rule["ruleId"] in chain_reached, "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_ASSESSED"} for rule in rules))
    write_rows(output / "candidate-reached-test-br-chain-gaps.jsonl", ({"testBrId": identifier, "candidateAiBrIds": sorted(candidate_by_rule[identifier]), "status": "NO_COMPLETE_PHYSICAL_CANDIDATE_ROUTE"} for identifier in sorted(reached - chain_reached)))
    present_names = {path.name for path in catalog_paths}
    measure = lambda ids: {"numerator": len(ids), "denominator": len(rules), "percent": round(100 * len(ids) / len(rules), 2), "status": "STRUCTURAL_CANDIDATE_ONLY"}
    summary = {"artifact": "ATL105-DELIVERY-WIDE-ASSESSMENT", "runRoot": str(root), "baselineVersion": baseline.get("specificationVersion"), "inputHashes": input_hashes,
               "catalogs": catalog_counts, "fileCount": len(files), "fileInventorySha256": file_hash(output / "file-inventory.jsonl"),
               "physicalFileCountsByKind": dict(collections.Counter(info["kind"] for info in files.values())),
               "missingCatalogNames": sorted(set(CATALOGS) - present_names),
               "uniqueIdsByKind": {kind: len(by_id) for kind, by_id in variants.items()}, "occurrencesByKind": dict(collections.Counter(row["kind"] for row in occurrences)),
               "contentConflictIds": len(conflicts), "brokenLinkCount": len(issues), "unreferencedPayloadFiles": len(orphan_files),
               "completePhysicalTcIds": len(physically_complete), "tcIdsWithoutUnconflictedCompletePhysicalRoute": len(set(variants["TC"]) - physically_complete), "incompletePhysicalOccurrences": sum(bool(row["issues"]) for row in chains),
               "anchorTypeCounts": dict(collections.Counter(row["anchorProposal"]["type"] for row in br_rows)),
               "candidateDispositionCounts": dict(collections.Counter(row["candidateDisposition"] for row in br_rows)),
               "measures": {"candidateReach": measure(reached), "physicalCompleteChainCandidateReach": measure(chain_reached),
                            "semanticConfirmedCoverage": {"numerator": None, "denominator": len(rules), "percent": None, "status": "NOT_ASSESSED_AUTHORIZED_DECISIONS_REQUIRED"},
                            "executedValidationCoverage": {"numerator": None, "denominator": len(rules), "percent": None, "status": "NOT_ASSESSED_EXECUTION_EVIDENCE_REQUIRED"}},
               "executionCertified": False, "limitations": ["Typed anchors are producer proposals; quote verification does not adjudicate interpretation or authenticate producer source/version identity.", "No automatic semantic pair decisions or host execution.", "Physical completeness checks explicit declarations, JSON objects and metadata IDs, not payload semantics.", "Files missing at catalog-relative paths remain gaps; no basename or undeclared-file fallback.", "Auxiliary files, producer matrices, archives and other formats are inventoried/hashed, not semantically validated.", "Only files actually present in the supplied run root can be assessed; absent source copies cannot count as validated artifacts."]}
    current_paths = {path.relative_to(root).as_posix() for path in root.rglob("*") if path.is_file()}
    if current_paths != set(files):
        raise ValueError("Delivery file population changed during assessment")
    for relative, info in files.items():
        if file_hash(root / relative) != info["sha256"]:
            raise ValueError("Delivery file changed during assessment: " + relative)
    for path, digest in input_hashes.items():
        if file_hash(Path(path)) != digest:
            raise ValueError("Input changed during assessment: " + path)
    outputs = {path.name: file_hash(path) for path in output.iterdir() if path.is_file()}
    summary["outputHashes"] = outputs
    (output / "summary.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
    report = "# Delivery-Wide AI Artifact Assessment\n\nAll approved and candidate catalog occurrences are retained. Producer status grants no acceptance.\n\n"
    report += "| Measure | Numerator | Denominator | Percent | Status |\n|---|---:|---:|---:|---|\n"
    for name, metric in summary["measures"].items():
        report += f"| {name} | {metric['numerator']} | {metric['denominator']} | {metric['percent']} | {metric['status']} |\n"
    report += f"\n## Population And Triage\n\nFiles inventoried: {len(files)}. Physical file kinds: {canonical(summary['physicalFileCountsByKind'])}.\n\n"
    report += f"Unique IDs: {canonical(summary['uniqueIdsByKind'])}. Retained occurrences: {canonical(summary['occurrencesByKind'])}.\n\n"
    report += f"Content-conflicting IDs: {len(conflicts)}. TCs with clean complete physical routes: {len(physically_complete)}. TCs without such a route: {summary['tcIdsWithoutUnconflictedCompletePhysicalRoute']}.\n\n"
    report += f"Candidate-reached Test BRs without clean complete routes: {len(reached - chain_reached)}. These are structural follow-up queues, not proof of missing business coverage.\n\n"
    report += "## Outstanding Work\n\nAnchor proposals require source-backed interpretation; candidate pair decisions require authorized source review. Physical file/object checks are not full payload or wire validation. Auxiliary matrices/archives remain unassessed. Semantic confirmation and executed validation remain unavailable.\n"
    report += "\nSee summary.json and the hashed JSONL ledgers for occurrence, conflict, chain, anchor and review evidence. This is not full-message, semantic or host certification.\n"
    (output / "REPORT.md").write_text(report, encoding="utf-8")
    return summary


def self_test():
    assert not is_physical_data_json(Path("qe_shaped_test_data/traceability.json"))
    assert is_physical_data_json(Path("qe_shaped_test_data/TC1.json"))
    assert is_physical_data_json(Path("qe_shaped_test_data/TC1.meta.json"))
    assert references({"requirement_ids": ["BR1"], "requirement_id": "BR1"}, "requirement_ids", "requirement_id") == ["BR1"]
    assert proposed_anchor({"category": "Lifecycle"}, {})["type"] == "LIFECYCLE"
    assert proposed_anchor({"category": "Field"}, {})["type"] == "UNRESOLVED"
    with tempfile.TemporaryDirectory() as temporary:
        root = Path(temporary).resolve()
        catalog = root / "candidates" / "test_case_candidates.json"
        row = {"id": "TC1", "scenario_id": "TS1", "test_data_file": "data/TC1.json"}
        files = {"candidates/data/TC1.json": {"jsonStatus": "OBJECT"}, "candidates/data/TC1.meta.json": {"jsonStatus": "OBJECT", "testCaseId": "TC1", "scenarioId": "TS1"}}
        assert physical_case(root, catalog, row, files)["status"] == "COMPLETE_PHYSICAL_DECLARATIONS"
        files["candidates/data/TC1.meta.json"]["scenarioId"] = "WRONG"
        assert physical_case(root, catalog, row, files)["status"] == "INCOMPLETE"
        row["test_data_file"] = "../../escape.json"
        assert "UNSAFE_TD_PATH" in physical_case(root, catalog, row, files)["issues"]
        assert physical_case(root, catalog, {"id": "TC2"}, files)["issues"] == ["NO_EXPLICIT_TD_DECLARATION"]
        baseline_dir, run = root / "baseline", root / "run"
        baseline_dir.mkdir()
        run.mkdir()
        rule = {"ruleId": "TEST1", "sourceAnchor": {"segment": "100", "element": "55", "version": "2026-3"}, "atomicity": "ATOMIC"}
        (baseline_dir / "source-derived-requirement-inventory.json").write_text(json.dumps({"authority": "INDEPENDENT_TEST_SOLUTION", "aiArtifactsIncluded": False, "ruleCount": 1, "businessRequirements": [rule]}))
        (baseline_dir / "atomic-composite-rule-classification.json").write_text(json.dumps({"rules": [rule]}))
        source = root / "source.txt"
        source.write_text("--- PAGE 1 ---\nVerified rule text.\n")
        br = {"id": "BR1", "related_entity_ids": ["ENT-SEG-100", "ENT-ELEM-55"], "category": "Field"}
        catalogs = {
            "approved/requirement_catalog.json": {"requirements": [br]},
            "candidates/requirement_candidates.json": {"requirements": [br]},
            "approved/approved_scenarios.json": {"scenarios": [{"id": "TS1", "requirement_ids": ["BR1"]}]},
            "candidates/candidate_scenarios.json": {"scenarios": []},
            "approved/test_case_catalog.json": {"test_cases": [{"id": "TC1", "scenario_id": "TS1", "test_data_file": "qe_shaped_test_data/TC1.json"}]},
            "candidates/test_case_candidates.json": {"test_cases": [{"id": "TC2", "scenario_id": "MISSING"}]},
            "approved/qe_shaped_test_data/TC1.json": {"request": {}},
            "approved/qe_shaped_test_data/TC1.meta.json": {"testCaseId": "TC1", "scenarioId": "TS1"},
        }
        for relative, document in catalogs.items():
            path = run / relative
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(json.dumps(document))
        result = audit(run, baseline_dir, source, root / "report")
        assert result["uniqueIdsByKind"] == {"BR": 1, "TS": 1, "TC": 2}
        assert result["occurrencesByKind"]["BR"] == 2
        assert result["completePhysicalTcIds"] == 1
        assert result["brokenLinkCount"] == 1
        assert result["measures"]["physicalCompleteChainCandidateReach"]["numerator"] == 1
        assert result["measures"]["semanticConfirmedCoverage"]["percent"] is None
        assert proposed_anchor({"category": "Response", "evidence_ref": {"page": 1, "quote": "Verified rule text."}}, {1: "Verified rule text."})["quoteVerified"]
        catalogs["candidates/requirement_candidates.json"]["requirements"][0] = {**br, "statement": "Changed"}
        (run / "candidates/requirement_candidates.json").write_text(json.dumps(catalogs["candidates/requirement_candidates.json"]))
        conflicted = audit(run, baseline_dir, source, root / "conflicted")
        assert conflicted["contentConflictIds"] == 1
        assert conflicted["measures"]["candidateReach"]["numerator"] == 0
        import io
        try:
            list(ijson.items(io.BytesIO(b'{"rows":[{"id":"A","id":"B"}]}'), "rows.item", map_type=UniqueKeys))
            raise AssertionError("Duplicate record key was accepted")
        except (ValueError, SystemError):
            pass
    print("PASS: typed anchors stay unconfirmed; direct paths, identity mismatches and unsafe/missing declarations discriminate")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--run-root", type=Path)
    parser.add_argument("--test-solution-directory", type=Path)
    parser.add_argument("--source-text", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.run_root, args.test_solution_directory, args.source_text, args.output_directory)):
        result = audit(args.run_root, args.test_solution_directory, args.source_text, args.output_directory)
        print(json.dumps({key: result[key] for key in ("uniqueIdsByKind", "contentConflictIds", "measures")}, indent=2))
    else:
        parser.error("Supply run root, test solution directory, source text and a new output directory")


if __name__ == "__main__":
    main()