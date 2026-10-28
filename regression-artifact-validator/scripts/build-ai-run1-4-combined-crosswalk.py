import argparse
import gzip
import hashlib
import ijson
import json
import os
import re
import tempfile
from collections import Counter, defaultdict
from pathlib import Path


RUNS = ("Run1", "Run2", "Run3", "Run4")
COMMON_RUNS = ("Run2", "Run3", "Run4")
RUN_DIRECTORY = re.compile(r"Run[0-9]+$")


def native(path: Path) -> Path:
    path = Path(os.path.normpath(str(path)))
    if os.name == "nt" and not str(path).startswith("\\\\?\\"):
        return Path("\\\\?\\" + str(path.resolve()))
    return path


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with native(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path: Path):
    return json.loads(native(path).read_text(encoding="utf-8-sig"))


def write_jsonl(path: Path, rows) -> None:
    with native(path).open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")


def normalize_relative_path(value) -> str:
    return str(value or "").replace("\\", "/").lstrip("./")


def load_unique_artifact_index(dedup_report_path: Path):
    dedup_report_path = native(dedup_report_path)
    report = read_json(dedup_report_path)
    if report.get("artifact") != "ATL105-AI-RUN-CONTENT-DEDUPLICATION" or report.get("runCount") != 4:
        raise ValueError("A four-run content-deduplication report is required")
    details_path = dedup_report_path.parent / report.get("duplicateArtifactDetailsFile", "")
    if not details_path.is_file() or sha256(details_path) != report.get("duplicateArtifactDetailsSha256"):
        raise ValueError("Duplicate-artifact detail file is missing or its hash does not match the summary")

    path_hash_index = {}
    group_count = 0
    run_groups = Counter()
    with gzip.open(native(details_path), "rt", encoding="utf-8") as source:
        for line in source:
            group = json.loads(line)
            digest = group.get("contentSha256")
            if not digest or len(digest) != 64:
                raise ValueError("Duplicate-artifact detail row lacks a SHA-256 content identity")
            runs = sorted(group.get("runs") or [], key=lambda item: int(str(item.get("runId", "Run0"))[3:]))
            run_ids = [str(run.get("runId")) for run in runs]
            if len(run_ids) < 2 or not set(run_ids).issubset(RUNS):
                raise ValueError("Duplicate-artifact detail row has invalid source-run membership")
            for run in runs:
                run_id = str(run["runId"])
                for relative_path in run.get("relativePaths") or []:
                    key = (run_id, normalize_relative_path(relative_path))
                    previous = path_hash_index.setdefault(key, digest)
                    if previous != digest:
                        raise ValueError(f"A run path maps to conflicting content hashes: {key}")
            run_groups[str(len(run_ids))] += 1
            group_count += 1

    if group_count != report.get("duplicateArtifactGroupCount"):
        raise ValueError("Unique artifact content rows disagree with the dedup report group count")
    if group_count != report.get("uniqueContentCount"):
        raise ValueError("Dedup detail manifest does not enumerate every unique content hash")
    return report, details_path, path_hash_index, group_count, dict(sorted(run_groups.items()))


def write_unique_artifact_index(details_path: Path, output_path: Path, expected_count: int) -> int:
    written = 0
    with gzip.open(native(details_path), "rt", encoding="utf-8") as source, gzip.open(
        native(output_path), "wt", encoding="utf-8", newline="\n"
    ) as target:
        for line in source:
            group = json.loads(line)
            runs = sorted(group.get("runs") or [], key=lambda item: int(str(item.get("runId", "Run0"))[3:]))
            row = {
                "recordType": "UNIQUE_PHYSICAL_ARTIFACT_CONTENT",
                "contentSha256": group.get("contentSha256"),
                "sizeBytes": group.get("sizeBytes"),
                "sourceRunIds": [str(run.get("runId")) for run in runs],
                "relativePathsByRun": {
                    str(run["runId"]): sorted(normalize_relative_path(path) for path in run.get("relativePaths") or [])
                    for run in runs
                },
                "semanticIdentity": "NOT_ASSESSED_CONTENT_IDENTITY_ONLY",
            }
            target.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")
            written += 1
    if written != expected_count:
        raise AssertionError(f"Expected {expected_count} unique content rows, wrote {written}")
    return written


def build_test_case_rows(case_catalog_path: Path, run1_suite: dict, path_hash_index: dict):
    run1_cases = {}
    for case in run1_suite.get("testCases") or []:
        case_id = str(case.get("id") or "")
        if not case_id or case_id in run1_cases:
            raise ValueError("Run1 suite contains a missing or duplicate test-case ID")
        run1_cases[case_id] = case

    run1_td_by_case = defaultdict(list)
    for datum in run1_suite.get("testData") or []:
        for case_id in record_ids(datum, "testCaseIds", "testCaseId"):
            run1_td_by_case[case_id].append(datum)

    emitted_ids = set()
    emitted_fingerprints = {}
    rows = []
    exact_run1_duplicates = 0
    run1_suite_only = 0
    run1_same_id_different_fingerprint = 0
    case_count = 0
    with native(case_catalog_path).open("rb") as source:
        for case in ijson.items(source, "test_cases.item"):
            case_count += 1
            case_id = str(case.get("id") or "")
            if not case_id or case_id in emitted_ids:
                raise ValueError("Run2–Run4 test-case catalog contains a missing or duplicate ID")
            emitted_ids.add(case_id)
            fingerprint = str(case.get("fingerprint") or "")
            if not fingerprint:
                raise ValueError(f"Test case has no fingerprint: {case_id}")
            emitted_fingerprints[case_id] = fingerprint
            scenario_id = str(case.get("scenario_id") or "")
            linked_run1 = run1_cases.get(case_id)
            matched_run1 = bool(linked_run1 and linked_run1.get("fingerprint") == fingerprint
                                 and record_ids(linked_run1, "scenarioIds", "scenarioId") == ({scenario_id} if scenario_id else set()))
            if linked_run1 and matched_run1:
                exact_run1_duplicates += 1
            elif linked_run1:
                run1_same_id_different_fingerprint += 1

            files = []
            declared_file = case.get("test_data_file")
            declared_files = case.get("test_data_files") or []
            if isinstance(declared_file, str) and declared_file:
                declared_files = [*declared_files, declared_file]
            if isinstance(declared_files, str):
                declared_files = [declared_files]
            for declared in sorted({normalize_relative_path(value) for value in declared_files if value}):
                candidate_path = normalize_relative_path(
                    "pipeline_outputs_2026-10-07/src/pipeline/test_generation/candidates/" + declared
                )
                metadata_path = candidate_path[:-5] + ".meta.json" if candidate_path.lower().endswith(".json") else candidate_path + ".meta.json"
                payload_hashes = {
                    run_id: path_hash_index.get((run_id, candidate_path))
                    for run_id in COMMON_RUNS
                    if (run_id, candidate_path) in path_hash_index
                }
                metadata_hashes = {
                    run_id: path_hash_index.get((run_id, metadata_path))
                    for run_id in COMMON_RUNS
                    if (run_id, metadata_path) in path_hash_index
                }
                files.append({
                    "declaredPath": declared,
                    "runRelativePayloadPath": candidate_path,
                    "payloadContentSha256ByRun": payload_hashes,
                    "metadataContentSha256ByRun": metadata_hashes,
                    "physicalPayloadPresentInRun2Run4": bool(payload_hashes),
                })

            rows.append({
                "recordType": "UNIQUE_AI_TEST_CASE",
                "testCaseId": case_id,
                "contentFingerprint": fingerprint,
                "sourceRunIds": ["Run1", *COMMON_RUNS] if matched_run1 else list(COMMON_RUNS),
                "run1SuiteFingerprintMatched": matched_run1,
                "scenarioId": scenario_id or None,
                "aiRequirementIds": requirement_ids(case),
                "transactionTypeClaim": case.get("transaction_type"),
                "scenarioTypeClaim": case.get("scenario_type"),
                "testDataFiles": files,
                "run1SuiteTestDataDeclarations": [
                    {"testDataId": datum.get("id"), "files": datum.get("files") or [],
                     "provenance": datum.get("provenance")}
                    for datum in run1_td_by_case.get(case_id, [])
                ] if matched_run1 else [],
                "semanticOutcomeStatus": "NOT_ASSESSED",
                "identityPolicy": "Exact producer fingerprint plus local ID; does not establish semantic equivalence.",
            })

    for case_id, case in sorted(run1_cases.items()):
        if case_id in emitted_fingerprints and case.get("fingerprint") == emitted_fingerprints[case_id]:
            continue
        run1_suite_only += 1
        rows.append({
            "recordType": "UNIQUE_RUN1_SUPPLEMENTAL_SUITE_TEST_CASE",
            "testCaseId": case_id,
            "contentFingerprint": case.get("fingerprint"),
            "sourceRunIds": ["Run1"],
            "run1SuiteFingerprintMatched": False,
            "scenarioIds": sorted(record_ids(case, "scenarioIds", "scenarioId")),
            "aiRequirementIds": sorted(record_ids(case, "requirementIds", "requirementId")),
            "testDataFiles": [{"declaredPath": value} for value in case.get("testDataFiles", [])],
            "semanticOutcomeStatus": "NOT_ASSESSED",
            "identityPolicy": "Separate Run1 suite case because no exact fingerprint duplicate was established.",
        })

    return rows, {
        "Run2Run4UniqueCaseCatalogRows": case_count,
        "Run1SuiteCasesExactFingerprintDuplicates": exact_run1_duplicates,
        "Run1SuiteCasesWithSameIdDifferentFingerprint": run1_same_id_different_fingerprint,
        "Run1SuiteCasesRetainedSeparately": run1_suite_only,
        "combinedUniqueTestCaseRows": len(rows),
    }


def normalized_element(value) -> str | None:
    value = str(value or "").strip()
    return str(int(value)) if re.fullmatch(r"\d+", value) else None


def requirement_ids(record: dict) -> list[str]:
    values = record.get("requirement_ids") or []
    if isinstance(values, str):
        values = [values]
    singular = record.get("requirement_id")
    if singular:
        values = [*values, singular]
    return sorted({str(value) for value in values if value})


def record_ids(record: dict, plural: str, singular: str) -> set[str]:
    values = record.get(plural) or []
    if isinstance(values, str):
        values = [values]
    result = {str(value) for value in values if value}
    value = record.get(singular)
    if value:
        result.add(str(value))
    return result


def source_rule_index(rules: list[dict]) -> dict[tuple[str, str], list[dict]]:
    index = defaultdict(list)
    for rule in rules:
        anchor = rule.get("sourceAnchor") or {}
        elements = [normalized_element(value) for value in re.split(r"[,|]", str(anchor.get("element") or ""))]
        segments = [value.strip().upper() for value in str(anchor.get("segment") or "").split(",")]
        for segment in segments:
            for element in elements:
                if segment and element:
                    index[(segment, element)].append(rule)
    return index


def rule_candidates(index: dict, segments: set[str], elements: set[str]) -> list[dict]:
    candidates = {}
    for segment in sorted(segments):
        for element in sorted(elements):
            for rule in index.get((segment, element), []):
                candidates[rule["ruleId"]] = rule
    return [candidates[key] for key in sorted(candidates)]


def candidate_rows(candidates: list[dict]) -> list[dict]:
    return [{
        "ruleId": rule["ruleId"],
        "title": rule.get("title"),
        "ruleClass": rule.get("class"),
        "atomicity": rule.get("atomicity"),
        "sourceAnchor": rule.get("sourceAnchor"),
        "sourceCatalog": rule.get("sourceCatalog"),
    } for rule in candidates]


def run1_anchor_candidate(requirement: dict, index: dict) -> dict:
    related = requirement.get("related_entity_ids") or []
    segments = {
        match.group(1).strip().upper()
        for value in related
        if (match := re.fullmatch(r"ENT-SEG-(.+)", str(value), re.IGNORECASE))
    }
    elements = {
        normalized_element(match.group(1))
        for value in related
        if (match := re.fullmatch(r"ENT-ELEM-(\d+)", str(value), re.IGNORECASE))
    }
    source_rule_id = str(requirement.get("source_rule_id") or "").strip()
    source_element = normalized_element(source_rule_id)
    if source_element:
        elements.add(source_element)

    if not elements:
        status = "NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR"
        candidates = []
    elif not segments:
        status = "NOT_MATCHABLE_SEGMENT_UNRESOLVED"
        candidates = []
    elif len(elements) > 1 or len(segments) > 1:
        status = "REVIEW_REQUIRED_AMBIGUOUS_OR_COMPOSITE_CANDIDATE"
        candidates = rule_candidates(index, segments, elements)
        if not candidates:
            status = "NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED"
    else:
        candidates = rule_candidates(index, segments, elements)
        if not candidates:
            status = "NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED"
        elif len(candidates) == 1 and candidates[0].get("atomicity") == "ATOMIC":
            status = "REVIEW_REQUIRED_LEGACY_ENTITY_ANCHOR_CANDIDATE"
        else:
            status = "REVIEW_REQUIRED_AMBIGUOUS_OR_COMPOSITE_CANDIDATE"

    return {
        "sourceAnchorCandidateStatus": status,
        "candidateSegments": sorted(segments),
        "candidateElements": sorted(elements),
        "candidateTestSolutionRules": candidate_rows(candidates),
        "semanticMatchStatus": "NOT_ASSESSED",
        "candidateBasis": "Run1 legacy source_rule_id / related_entity_ids; review required, never confirmed",
    }


def json_hash(value) -> str:
    encoded = json.dumps(value, sort_keys=True, ensure_ascii=True, separators=(",", ":")).encode("utf-8")
    return hashlib.sha256(encoded).hexdigest()


def scenario_key(scenario: dict) -> tuple[str, str, tuple[str, ...]]:
    scenario_id = str(scenario.get("id") or "")
    fingerprint = str(scenario.get("fingerprint") or json_hash(scenario))
    return scenario_id, fingerprint, tuple(requirement_ids(scenario))


def self_test() -> None:
    index = source_rule_index([{
        "ruleId": "SEG100-R-001", "title": "sample", "class": "structure", "atomicity": "ATOMIC",
        "sourceAnchor": {"segment": "100", "element": "55"},
    }])
    exact = run1_anchor_candidate({
        "source_rule_id": "55", "related_entity_ids": ["ENT-SEG-100", "ENT-ELEM-55"]
    }, index)
    assert exact["sourceAnchorCandidateStatus"] == "REVIEW_REQUIRED_LEGACY_ENTITY_ANCHOR_CANDIDATE"
    assert exact["semanticMatchStatus"] == "NOT_ASSESSED"
    ambiguous = run1_anchor_candidate({
        "source_rule_id": "55", "related_entity_ids": ["ENT-SEG-100", "ENT-SEG-101", "ENT-ELEM-55"]
    }, index)
    assert ambiguous["sourceAnchorCandidateStatus"] == "REVIEW_REQUIRED_AMBIGUOUS_OR_COMPOSITE_CANDIDATE"
    unresolved = run1_anchor_candidate({
        "source_rule_id": "999", "related_entity_ids": ["ENT-SEG-100", "ENT-SEG-101", "ENT-ELEM-999"]
    }, index)
    assert unresolved["sourceAnchorCandidateStatus"] == "NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED"
    scenario = {"id": "SC-1", "fingerprint": "fp", "requirement_ids": ["BR-1"]}
    assert scenario_key(scenario) == scenario_key(dict(scenario))
    print("PASS: legacy anchors remain review-required; ambiguous anchors are not promoted")


def build(runs_root: Path, analysis_dir: Path, test_solution_dir: Path,
          dedup_report_path: Path, output_dir: Path) -> dict:
    runs_root = native(runs_root)
    analysis_dir = native(analysis_dir)
    test_solution_dir = native(test_solution_dir)
    output_dir = native(output_dir)
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")

    run_roots = {run_id: runs_root / run_id / "pipeline_outputs_2026-10-07" for run_id in RUNS}
    for run_id, root in run_roots.items():
        if not root.is_dir():
            raise ValueError(f"Missing immutable pipeline output for {run_id}: {root}")

    run1 = run_roots["Run1"]
    run1_requirement_path = run1 / "sample_req_vision/requirements.json"
    run1_scenario_path = run1 / "src/pipeline/prioritization/output/prioritized_scenarios.json"
    run1_suite_path = run1 / "src/pipeline/reporting/output/segment-100-suite-catalog.json"
    run1_matrix_path = run1 / "src/pipeline/reporting/output/traceability_matrix_full.json"
    shared_paths = {
        "requirements": "src/pipeline/step5_requirements/approved/requirement_catalog.json",
        "scenarios": "src/pipeline/scenarios/approved/approved_scenarios.json",
        "testCases": "src/pipeline/test_generation/candidates/test_case_candidates.json",
        "matrix": "src/pipeline/reporting/output/traceability_matrix_full.json",
    }
    shared_hashes = {kind: {} for kind in shared_paths}
    for run_id in COMMON_RUNS:
        for kind, relative in shared_paths.items():
            path = run_roots[run_id] / relative
            if not path.is_file():
                raise ValueError(f"Missing {kind} input for {run_id}: {path}")
            shared_hashes[kind][run_id] = sha256(path)
    nonidentical = {
        kind: hashes for kind, hashes in shared_hashes.items()
        if len(set(hashes.values())) != 1
    }
    if nonidentical:
        raise ValueError(f"Run2–Run4 catalogs are not byte-identical; do not collapse them: {nonidentical}")

    for path in (run1_requirement_path, run1_scenario_path, run1_suite_path, run1_matrix_path):
        if not path.is_file():
            raise ValueError(f"Missing Run1 evidence source: {path}")

    run1_requirement_data = read_json(run1_requirement_path)
    run1_requirements = run1_requirement_data.get("requirements") or []
    run1_scenario_data = read_json(run1_scenario_path)
    run1_scenarios = run1_scenario_data.get("scenarios") or []
    run1_suite = read_json(run1_suite_path)

    shared_root = run_roots["Run2"]
    shared_requirement_data = read_json(shared_root / shared_paths["requirements"])
    shared_requirements = shared_requirement_data.get("requirements") or []
    shared_scenario_data = read_json(shared_root / shared_paths["scenarios"])
    shared_scenarios = shared_scenario_data.get("scenarios") or []
    if not run1_requirements or not shared_requirements or not shared_scenarios:
        raise ValueError("One or more input catalogs have no records")

    run1_by_id = {str(record.get("id")): record for record in run1_requirements}
    shared_by_id = {str(record.get("id")): record for record in shared_requirements}
    if len(run1_by_id) != len(run1_requirements) or len(shared_by_id) != len(shared_requirements):
        raise ValueError("A BR catalog contains missing or duplicate local IDs")
    overlapping_ids = set(run1_by_id) & set(shared_by_id)

    baseline_path = test_solution_dir / "source-derived-requirement-inventory.json"
    classification_path = test_solution_dir / "atomic-composite-rule-classification.json"
    baseline = read_json(baseline_path)
    classification = read_json(classification_path)
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent Test Solution baseline provenance is invalid")
    if baseline.get("ruleCount") != len(baseline.get("businessRequirements", [])):
        raise ValueError("Test Solution rule count disagrees with baseline rows")
    rule_index = source_rule_index(classification.get("rules") or [])

    analysis_dir = native(analysis_dir)
    run4_crosswalk_path = analysis_dir / "run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk.jsonl"
    run4_summary_path = analysis_dir / "run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk-summary.json"
    case_register_path = analysis_dir / "case-register.json"
    for path in (run4_crosswalk_path, run4_summary_path, case_register_path):
        if not path.is_file():
            raise ValueError(f"Missing Run4 independent analysis artifact: {path}")
    shared_crosswalk = [json.loads(line) for line in run4_crosswalk_path.read_text(encoding="utf-8").splitlines() if line]
    run4_summary = read_json(run4_summary_path)
    case_register = read_json(case_register_path)
    if {str(row.get("aiRequirementId")) for row in shared_crosswalk} != set(shared_by_id):
        raise ValueError("Run4 crosswalk does not cover the shared Run2–Run4 BR catalog")

    dedup_report, dedup_details_path, path_hash_index, unique_content_count, duplicate_groups_by_run_count = \
        load_unique_artifact_index(dedup_report_path)
    shared_case_catalog_path = shared_root / shared_paths["testCases"]
    test_case_rows, test_case_counts = build_test_case_rows(shared_case_catalog_path, run1_suite, path_hash_index)
    shared_case_ids = {row["testCaseId"] for row in test_case_rows
                       if row.get("recordType") == "UNIQUE_AI_TEST_CASE"
                       and "Run2" in row.get("sourceRunIds", [])}
    run4_case_ids = {str(case.get("caseId")) for case in case_register if case.get("caseId")}
    if shared_case_ids != run4_case_ids:
        raise ValueError("Combined unique test-case catalog does not reconcile to the Run4 case register")

    test_data_declaration_count = sum(len(row.get("testDataFiles") or []) for row in test_case_rows)
    test_data_physical_hash_count = sum(
        len(file.get("payloadContentSha256ByRun") or [])
        for row in test_case_rows for file in row.get("testDataFiles") or []
    )

    br_rows = []
    run1_statuses = Counter()
    run1_candidate_rules = set()
    for requirement in run1_requirements:
        result = run1_anchor_candidate(requirement, rule_index)
        run1_statuses[result["sourceAnchorCandidateStatus"]] += 1
        run1_candidate_rules.update(rule["ruleId"] for rule in result["candidateTestSolutionRules"])
        br_rows.append({
            "recordType": "AI_BUSINESS_REQUIREMENT",
            "cohortId": "Run1-legacy-requirement-catalog",
            "sourceRunIds": ["Run1"],
            "sourceArtifact": "sample_req_vision/requirements.json",
            "aiRequirementId": requirement.get("id"),
            "contentSha256": json_hash({
                "statement": requirement.get("statement"),
                "sourceRuleId": requirement.get("source_rule_id"),
                "relatedEntityIds": requirement.get("related_entity_ids") or [],
            }),
            "aiSourceRuleId": requirement.get("source_rule_id"),
            "aiSourcePage": requirement.get("source_page"),
            "relatedEntityIds": requirement.get("related_entity_ids") or [],
            **result,
            "directScenarioIds": [],
            "directTestCaseIds": [],
            "directScenarioLinkStatus": "NO_RUN1_CATALOG_LINKS_FOUND",
        })

    shared_scenarios_by_id = {str(item.get("id")): item for item in shared_scenarios}
    run1_scenarios_by_id = {str(item.get("id")): item for item in run1_scenarios}
    common_scenario_ids = set(shared_scenarios_by_id) & set(run1_scenarios_by_id)
    same_scenario_fingerprints = sum(
        run1_scenarios_by_id[key].get("fingerprint") == shared_scenarios_by_id[key].get("fingerprint")
        for key in common_scenario_ids
    )
    same_scenario_requirement_links = sum(
        requirement_ids(run1_scenarios_by_id[key]) == requirement_ids(shared_scenarios_by_id[key])
        for key in common_scenario_ids
    )
    scenario_run1_req_link_ids = {
        ai_id for scenario in run1_scenarios
        for ai_id in requirement_ids(scenario) if ai_id in run1_by_id
    }
    scenario_shared_req_link_ids = {
        ai_id for scenario in run1_scenarios
        for ai_id in requirement_ids(scenario) if ai_id in shared_by_id
    }
    if len(shared_scenarios_by_id) != len(shared_scenarios) or len(run1_scenarios_by_id) != len(run1_scenarios):
        raise ValueError("A scenario catalog contains duplicate IDs; scenario variants require explicit versioning")
    if len(common_scenario_ids) != len(shared_scenarios) or same_scenario_fingerprints != len(common_scenario_ids) \
            or same_scenario_requirement_links != len(common_scenario_ids):
        raise ValueError("Run1 prioritized scenarios differ from Run2–Run4 approved scenario identities/links")

    case_register_by_scenario = defaultdict(list)
    for case in case_register:
        if case.get("scenarioId"):
            case_register_by_scenario[str(case["scenarioId"])].append(case)

    shared_crosswalk_by_br = {str(row["aiRequirementId"]): row for row in shared_crosswalk}
    shared_br_rule_candidates = {
        ai_id: sorted({
            candidate["ruleId"] for candidate in row.get("testSolutionRuleCandidates", []) if candidate.get("ruleId")
        })
        for ai_id, row in shared_crosswalk_by_br.items()
    }
    scenario_rows = []
    for scenario_id, scenario in sorted(shared_scenarios_by_id.items()):
        references = requirement_ids(scenario)
        linked_cases = case_register_by_scenario.get(scenario_id, [])
        candidate_rules = sorted({
            rule_id for ai_id in references for rule_id in shared_br_rule_candidates.get(ai_id, [])
        })
        scenario_rows.append({
            "recordType": "AI_SCENARIO_TO_AI_BR_AND_TC_CROSSWALK",
            "scenarioId": scenario_id,
            "scenarioFingerprint": scenario.get("fingerprint"),
            "sourceRunStages": [
                {"runId": "Run1", "stage": "prioritized_scenarios"},
                {"runId": "Run2", "stage": "approved_scenarios"},
                {"runId": "Run3", "stage": "approved_scenarios"},
                {"runId": "Run4", "stage": "approved_scenarios"},
            ],
            "aiRequirementIds": references,
            "run1LegacyRequirementIdsResolved": sorted(set(references) & set(run1_by_id)),
            "sharedRun2Run4RequirementIdsResolved": sorted(set(references) & set(shared_by_id)),
            "unresolvedRequirementIds": sorted(set(references) - set(run1_by_id) - set(shared_by_id)),
            "targetTransactionClaim": scenario.get("target_transaction"),
            "scenarioTypeClaim": scenario.get("type"),
            "expectedResponseCodeClaim": scenario.get("expected_response_code"),
            "testCaseIdsFromRun4CaseRegister": sorted({str(case.get("caseId")) for case in linked_cases if case.get("caseId")}),
            "candidateTestSolutionRuleIds": candidate_rules,
            "candidateStatus": "REVIEW_REQUIRED" if candidate_rules else "NOT_MATCHABLE_OR_NO_CANDIDATE",
            "semanticScenarioMatchStatus": "NOT_ASSESSED",
            "warning": "This is an AI-internal scenario/BR/TC link plus candidate Test BR anchors; no independent Test Solution scenario catalog was supplied.",
        })

    for row in shared_crosswalk:
        links = row.get("matrixAndDirectChain") or {}
        br_rows.append({
            "recordType": "AI_BUSINESS_REQUIREMENT",
            "cohortId": "Run2-Run4-byte-identical-canonical-catalog",
            "sourceRunIds": list(COMMON_RUNS),
            "sourceArtifact": "step5_requirements/approved/requirement_catalog.json",
            "aiRequirementId": row.get("aiRequirementId"),
            "contentSha256": json_hash({
                "statement": row.get("aiStatement"),
                "sourceRuleId": row.get("aiSourceRuleId"),
                "sourcePage": row.get("aiSourcePage"),
                "segments": row.get("aiSegmentIds") or row.get("aiSegmentNumber"),
            }),
            "aiSourceRuleId": row.get("aiSourceRuleId"),
            "aiSourcePage": row.get("aiSourcePage"),
            "candidateSegments": row.get("candidateSegments") or [],
            "inferredElement": row.get("inferredElement"),
            "sourceAnchorCandidateStatus": row.get("sourceAnchorCandidateStatus"),
            "candidateTestSolutionRules": candidate_rows(row.get("testSolutionRuleCandidates") or []),
            "directScenarioIds": links.get("directScenarioIds") or [],
            "directTestCaseIds": links.get("directTestCaseIdsViaScenario") or [],
            "directPhysicalTestDataCount": len(links.get("directCandidateTdPaths") or []),
            "semanticMatchStatus": "NOT_ASSESSED",
        })

    suite = run1_suite
    suite_cases_by_scenario = defaultdict(list)
    for case in suite.get("testCases") or []:
        for scenario_id in case.get("scenarioIds") or []:
            suite_cases_by_scenario[str(scenario_id)].append(str(case.get("id")))
    suite_br_ids = {str(item.get("id")) for item in suite.get("businessRequirements") or []}
    suite_scenario_groups = {}
    for scenario in suite.get("testScenarios") or []:
        digest = json_hash(scenario)
        group = suite_scenario_groups.setdefault(digest, {"scenario": scenario, "sourceOccurrences": 0})
        group["sourceOccurrences"] += 1
    for digest, group in sorted(suite_scenario_groups.items()):
        scenario = group["scenario"]
        req_ids = ids_from_canonical(scenario.get("requirementIds") or [])
        scenario_rows.append({
            "recordType": "RUN1_SUPPLEMENTAL_SEGMENT100_SUITE_SCENARIO",
            "scenarioContentSha256": digest,
            "sourceOccurrences": group["sourceOccurrences"],
            "scenarioId": scenario.get("id"),
            "sourceRunStages": [{"runId": "Run1", "stage": "segment-100-suite-catalog"}],
            "aiRequirementIds": req_ids,
            "run1SuiteRequirementIdsResolved": sorted(set(req_ids) & suite_br_ids),
            "testCaseIdsFromRun1SuiteCatalog": sorted(set().union(*(
                set(suite_cases_by_scenario.get(str(value), [])) for value in [scenario.get("id")]
            ))),
            "candidateTestSolutionRuleIds": [],
            "semanticScenarioMatchStatus": "NOT_ASSESSED",
            "warning": "Supplemental Run1 Segment 100 suite package; not an independent Test Solution scenario baseline and excluded from the core AI BR count.",
        })

    suite_br_rows = []
    for requirement in suite.get("businessRequirements") or []:
        suite_br_rows.append({
            "recordType": "RUN1_SUPPLEMENTAL_SEGMENT100_SUITE_BUSINESS_REQUIREMENT",
            "cohortId": "Run1-Segment100-suite-catalog",
            "sourceRunIds": ["Run1"],
            "aiRequirementId": requirement.get("id"),
            "title": requirement.get("title"),
            "statement": requirement.get("statement"),
            "sourceRuleId": requirement.get("sourceRuleId"),
            "sourceAnchors": requirement.get("sourceAnchors") or [],
            "semanticMatchStatus": "NOT_ASSESSED",
            "warning": "Supplemental Segment 100 suite-catalog BR; kept separate from core run BR catalogs and the independent Test Solution baseline.",
        })
    br_rows.extend(suite_br_rows)

    unique_ai_ids = set(run1_by_id) | set(shared_by_id)
    ai_br_rows = [row for row in br_rows if row.get("recordType") == "AI_BUSINESS_REQUIREMENT"]
    test_solution_rule_ids = {
        str(item.get("ruleId")) for item in baseline.get("businessRequirements", []) if item.get("ruleId")
    }
    coverage_by_cohort = {}
    candidate_rule_details = {}
    for cohort_id in sorted({row["cohortId"] for row in ai_br_rows}):
        cohort_rows = [row for row in ai_br_rows if row["cohortId"] == cohort_id]
        candidate_ai_rows = [row for row in cohort_rows if row.get("candidateTestSolutionRules")]
        cohort_rule_ids = set()
        for row in cohort_rows:
            row_rule_ids = {
                str(candidate.get("ruleId")) for candidate in row.get("candidateTestSolutionRules", [])
                if candidate.get("ruleId")
            }
            cohort_rule_ids.update(row_rule_ids)
            for rule_id in row_rule_ids:
                detail = candidate_rule_details.setdefault(rule_id, {"candidateAiBrCount": 0, "sourceCohorts": set()})
                detail["candidateAiBrCount"] += 1
                detail["sourceCohorts"].add(cohort_id)
        if not cohort_rule_ids <= test_solution_rule_ids:
            raise ValueError(f"Candidate IDs are outside the independent Test Solution baseline: {cohort_rule_ids - test_solution_rule_ids}")
        ai_denominator = len(cohort_rows)
        test_denominator = int(baseline["ruleCount"])
        coverage_by_cohort[cohort_id] = {
            "aiBrWithAtLeastOneTestBrCandidate": len(candidate_ai_rows),
            "aiBrDenominator": ai_denominator,
            "aiBrCandidateCoveragePercent": round(100 * len(candidate_ai_rows) / ai_denominator, 2),
            "testBrWithAtLeastOneAiCandidate": len(cohort_rule_ids),
            "testBrDenominator": test_denominator,
            "testBrCandidateReachPercent": round(100 * len(cohort_rule_ids) / test_denominator, 2),
        }

    candidate_ai_br_count = sum(value["aiBrWithAtLeastOneTestBrCandidate"] for value in coverage_by_cohort.values())
    candidate_test_rule_ids = set(candidate_rule_details)
    source_anchor_coverage = {
        "assessmentStatus": "ASSESSED_SOURCE_ANCHOR_CANDIDATE_COVERAGE",
        "method": "Counts an AI BR when its exact source-anchor lookup yields at least one rule in the independent Test Solution baseline. This is candidate reach, not semantic equivalence or execution coverage.",
        "smeApprovalRequiredForCalculation": False,
        "aiBrCoverage": {
            "numeratorAiBrsWithAtLeastOneTestBrCandidate": candidate_ai_br_count,
            "denominatorUniqueAiBrs": len(unique_ai_ids),
            "percent": round(100 * candidate_ai_br_count / len(unique_ai_ids), 2),
        },
        "testSolutionBrReach": {
            "numeratorTestBrsWithAtLeastOneAiCandidate": len(candidate_test_rule_ids),
            "denominatorIndependentTestBrs": int(baseline["ruleCount"]),
            "percent": round(100 * len(candidate_test_rule_ids) / int(baseline["ruleCount"]), 2),
        },
        "byAiCatalog": coverage_by_cohort,
        "candidateTestBrDetails": [
            {
                "testSolutionRuleId": rule_id,
                "candidateAiBrCount": detail["candidateAiBrCount"],
                "sourceCohorts": sorted(detail["sourceCohorts"]),
            }
            for rule_id, detail in sorted(candidate_rule_details.items())
        ],
        "semanticEquivalence": {"confirmedMatches": 0, "status": "NOT_ASSESSED"},
    }

    run1_count = len(run1_requirements)
    shared_count = len(shared_requirements)
    unique_ai_ids = set(run1_by_id) | set(shared_by_id)
    if overlapping_ids:
        raise ValueError(f"Run1 and Run2–Run4 BR catalogs unexpectedly share IDs: {len(overlapping_ids)}")
    run1_suite_counts = suite.get("manifest", {}).get("counts", {})
    crosswalk_summary = {
        "artifact": "ATL105-COMBINED-RUN1-RUN4-AI-BR-SCENARIO-TEST-CASE-CROSSWALK",
        "createdBy": Path(__file__).name,
        "sourceRuns": list(RUNS),
        "status": "SOURCE_ANCHOR_COVERAGE_ASSESSED_SEMANTIC_EQUIVALENCE_NOT_ASSESSED",
        "policy": "Deduplicate only byte-identical Run2–Run4 canonical catalogs. Preserve Run1's distinct legacy BR catalog and scenario-reference mismatch. Source-anchor candidate coverage is measurable without SME approval; candidates are not semantic confirmations.",
        "primaryAiBusinessRequirements": {
            "Run1LegacyCatalog": run1_count,
            "Run2Run4UniqueCatalog": shared_count,
            "Run2Run4SourceRunIds": list(COMMON_RUNS),
            "combinedUniqueIds": len(unique_ai_ids),
        },
        "runCatalogIdOverlap": len(overlapping_ids),
        "run1ScenarioAudit": {
            "prioritizedScenarioRows": len(run1_scenarios),
            "Run2ApprovedScenarioRows": len(shared_scenarios),
            "scenarioIdsCommon": len(common_scenario_ids),
            "sameFingerprintAcrossRun1AndRun2": same_scenario_fingerprints,
            "sameRequirementLinksAcrossRun1AndRun2": same_scenario_requirement_links,
            "uniqueRun1PrioritizedScenarioIds": len(run1_scenarios_by_id),
            "scenarioRequirementIdsResolveToRun1LegacyCatalog": len(scenario_run1_req_link_ids),
            "scenarioRequirementIdsResolveToRun2Run4Catalog": len(scenario_shared_req_link_ids),
            "scenarioRequirementIdsResolveToNeither": len({
                value for scenario in run1_scenarios for value in requirement_ids(scenario)
            } - set(run1_by_id) - set(shared_by_id)),
            "issue": "Run1's scenario rows are byte/content-identical by scenario ID, fingerprint, and BR links to the Run2–Run4 approved scenario catalog, but their 5,153 BR references resolve to the Run2–Run4 catalog and none resolve to the Run1 6,240-BR catalog.",
        },
        "uniqueMainScenarioCount": len(shared_scenarios),
        "uniqueTestCaseRows": len(test_case_rows),
        "testCaseDeduplication": test_case_counts,
        "testDataFileDeclarationCount": test_data_declaration_count,
        "testDataPhysicalPayloadRunHashCount": test_data_physical_hash_count,
        "uniquePhysicalArtifactContentCount": unique_content_count,
        "consolidatedArtifactRecordCounts": {
            "coreUniqueAiBusinessRequirements": len(unique_ai_ids),
            "supplementalRun1SuiteBusinessRequirements": len(suite_br_rows),
            "uniqueMainScenarios": len(shared_scenarios),
            "uniqueRun1SuiteScenariosByExactContent": len(suite_scenario_groups),
            "uniqueAiTestCasesByFingerprint": len(test_case_rows),
            "uniquePhysicalArtifactContentsBySha256": unique_content_count,
        },
        "physicalArtifactContentGroupsByRunCount": duplicate_groups_by_run_count,
        "physicalArtifactDeduplicationReport": {
            "path": dedup_report_path.name,
            "sha256": sha256(dedup_report_path),
            "detailsPath": dedup_details_path.name,
            "detailsSha256": dedup_report.get("duplicateArtifactDetailsSha256"),
            "fileCount": dedup_report.get("fileCount"),
            "uniqueContentCount": dedup_report.get("uniqueContentCount"),
            "duplicateArtifactGroupCount": dedup_report.get("duplicateArtifactGroupCount"),
            "identicalRunGroups": dedup_report.get("identicalRunGroups"),
        },
        "run1SupplementalSegment100Suite": {
            "businessRequirements": len(suite.get("businessRequirements") or []),
            "testScenarioOccurrences": len(suite.get("testScenarios") or []),
            "uniqueExactContentScenarios": len(suite_scenario_groups),
            "testCases": len(suite.get("testCases") or []),
            "testData": len(suite.get("testData") or []),
            "flowsDeclared": run1_suite_counts.get("flowsDeclared"),
            "flowsGenerated": run1_suite_counts.get("flowsGenerated"),
            "flowsSkipped": run1_suite_counts.get("flowsSkipped"),
            "treatment": "Supplemental producer suite artifact; not added to the primary BR denominator or an independent Test Solution baseline.",
        },
        "independentTestSolutionBrCount": baseline.get("ruleCount"),
        "run1LegacyAnchorCandidateDispositions": dict(sorted(run1_statuses.items())),
        "run1DistinctCandidateTestSolutionRules": len(run1_candidate_rules),
        "run2Run4MatchDispositionCounts": run4_summary.get("matchDispositionCounts"),
        "run2Run4AiBrAnchorCandidates": run4_summary.get("aiBrsWithSourceAnchorCandidates"),
        "run2Run4DistinctCandidateTestSolutionRules": run4_summary.get("uniqueTestSolutionRulesWithSourceAnchorCandidates"),
        "combinedCandidateAiBrRows": candidate_ai_br_count,
        "sourceAnchorCoverageAssessment": source_anchor_coverage,
        "confirmedSemanticMatches": 0,
        "confirmedMatchStatus": "NOT_ASSESSED",
        "run2Run4MatrixAndChainAudit": run4_summary.get("matrixAndChainAudit"),
        "directCatalogChainCounts": run4_summary.get("directCatalogChainCounts"),
        "sourceHashes": {
            "Run1LegacyRequirementCatalog": sha256(run1_requirement_path),
            "Run1PrioritizedScenarios": sha256(run1_scenario_path),
            "Run1Segment100SuiteCatalog": sha256(run1_suite_path),
            "Run1TraceabilityMatrix": sha256(run1_matrix_path),
            "Run2Run4SharedCatalogHashes": shared_hashes,
            "TestSolutionBaseline": sha256(baseline_path),
            "AtomicityClassification": sha256(test_solution_dir / "atomic-composite-rule-classification.json"),
            "Run4IndependentBrCrosswalk": sha256(run4_crosswalk_path),
            "Run4CaseRegister": sha256(case_register_path),
            "SharedRun2Run4TestCaseCatalog": sha256(shared_case_catalog_path),
            "RunDeduplicationSummary": sha256(dedup_report_path),
            "RunDeduplicationDetails": sha256(dedup_details_path),
        },
        "limitations": [
            "Run1's legacy BR catalog uses a different identity schema and has no direct scenario links in the supplied prioritized scenario catalog.",
            "Run2–Run4 canonical BR/scenario/test-case/matrix inputs are byte-identical; they are counted as one content cohort with three source-run provenances.",
            "Run1 related-entity anchors are candidate-generation evidence only and remain REVIEW_REQUIRED.",
            "Scenario-to-case links are AI-internal structural links; no independent Test Solution scenario baseline was supplied.",
            "Source-anchor candidate coverage is assessed separately; no AI-to-Test Solution semantic equivalence or execution/final test approval is asserted.",
        ],
    }

    output_dir.mkdir(parents=True, exist_ok=False)
    write_jsonl(output_dir / "combined-ai-br-test-solution-crosswalk.jsonl", br_rows)
    write_jsonl(output_dir / "combined-ai-scenario-test-case-crosswalk.jsonl", scenario_rows)
    write_jsonl(output_dir / "combined-ai-test-case-crosswalk.jsonl", test_case_rows)
    write_unique_artifact_index(
        dedup_details_path, output_dir / "combined-unique-physical-artifact-content.jsonl.gz", unique_content_count)
    (output_dir / "combined-crosswalk-summary.json").write_text(
        json.dumps(crosswalk_summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    (output_dir / "ai-br-source-anchor-coverage-assessment.json").write_text(
        json.dumps(source_anchor_coverage, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return crosswalk_summary


def ids_from_canonical(values) -> list[str]:
    if isinstance(values, str):
        values = [values]
    return sorted({str(value) for value in values if value})


def main() -> None:
    parser = argparse.ArgumentParser(description="Build a provenance-preserving combined Run1–Run4 BR/scenario crosswalk.")
    parser.add_argument("--runs-root", type=Path, help="Root containing Run1 through Run4 directories")
    parser.add_argument("--analysis-dir", type=Path, help="Run4 analysis-v2 directory with case/crosswalk registers")
    parser.add_argument("--test-solution-dir", type=Path, help="Directory with independent BR inventory and atomicity classification")
    parser.add_argument("--dedup-report", type=Path, help="Four-run content-deduplication summary with its hash-bound details file")
    parser.add_argument("--output-dir", type=Path, help="New output directory; existing directories are never overwritten")
    parser.add_argument("--self-test", action="store_true", help="Run isolated candidate/identity checks")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not all((args.runs_root, args.analysis_dir, args.test_solution_dir, args.dedup_report, args.output_dir)):
        parser.error("--runs-root, --analysis-dir, --test-solution-dir, --dedup-report, and --output-dir are required unless --self-test is used")
    summary = build(args.runs_root, args.analysis_dir, args.test_solution_dir, args.dedup_report, args.output_dir)
    print(json.dumps({
        "outputDirectory": str(args.output_dir),
        "combinedUniqueAiBrIds": summary["primaryAiBusinessRequirements"]["combinedUniqueIds"],
        "uniqueScenarioRows": summary["uniqueMainScenarioCount"],
        "aiBrSourceAnchorCoveragePercent": summary["sourceAnchorCoverageAssessment"]["aiBrCoverage"]["percent"],
        "testSolutionBrCandidateReachPercent": summary["sourceAnchorCoverageAssessment"]["testSolutionBrReach"]["percent"],
        "confirmedSemanticMatches": summary["confirmedSemanticMatches"],
        "confirmedMatchStatus": summary["confirmedMatchStatus"],
    }, indent=2))


if __name__ == "__main__":
    main()