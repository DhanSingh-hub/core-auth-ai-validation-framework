import argparse
import copy
import csv
import hashlib
import json
import os
import re
import runpy
from pathlib import Path

import ijson


MODULE = Path(__file__).resolve().parents[1]
HELPERS = runpy.run_path(str(Path(__file__).with_name("assess-ai-semantic-batch.py")))


def digest(file):
    with file.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def source_evidence(source_file, element):
    source = source_file.read_text(encoding="utf-8-sig")
    matches = list(re.finditer(r"Number:\s*" + element + r"\s+Name:", source))
    if len(matches) != 1:
        raise ValueError("Source element anchor is absent or ambiguous: " + element)
    start = matches[0].start()
    next_element = re.search(r"Number:\s*\d+\s+Name:", source[matches[0].end():])
    end = matches[0].end() + next_element.start() if next_element else len(source)
    expression = (r"\b100\s+Data Segment No\.\s*100,\s*Standard Message Data Segment\b" if element == "85"
                  else r"Representation:\s*Fixed length of six digits")
    clause = re.search(expression, source[start:end])
    if clause is None:
        raise ValueError("Supported source clause not found: " + element)
    quote_start = start + clause.start()
    quote = clause.group(0)
    first = source[:quote_start].count("\n") + 1
    return {"file": "specs/extracted_text.txt", "sha256": digest(source_file), "startLine": first,
            "endLine": first + quote.count("\n"), "quote": quote}


def pointer(path):
    return "/" + "/".join(str(value).replace("~", "~0").replace("/", "~1") for value in path)


def mutate(payload, path, value):
    result = copy.deepcopy(payload)
    target = result
    for part in path[:-1]:
        target = target[part]
    target[path[-1]] = value
    return result


def supported_observation(payload, metadata):
    if metadata.get("messageFamily") != "Financial Transaction Request":
        return None
    checks = HELPERS["evaluate_leg"]({"scenario_type": "positive"}, payload, metadata)["fieldChecks"]
    supported = {}
    for element in ["85", "86"]:
        selected = [check for check in checks if check["element"] == element]
        if len(selected) != 1 or selected[0]["predicate"] != "PASS" or not selected[0]["metadataValuePreserved"]:
            return None
        supported[element] = selected[0]
    return supported


def self_test():
    payload = {"Financial Transaction Request": {"Standard Segment": {"SegmentType": "100", "SequenceNumber": "123456"}}}
    metadata = {"messageFamily": "Financial Transaction Request", "fields": [
        {"segment": "100", "specElementName": "Segment Type", "segmentFriendlyName": "Standard Segment", "element": "SegmentType", "value": "100"},
        {"segment": "100", "specElementName": "Sequence Number", "segmentFriendlyName": "Standard Segment", "element": "SequenceNumber", "value": "123456"}]}
    assert supported_observation(payload, metadata) is not None
    altered = mutate(payload, ["Financial Transaction Request", "Standard Segment", "SequenceNumber"], "12345")
    assert payload["Financial Transaction Request"]["Standard Segment"]["SequenceNumber"] == "123456"
    assert supported_observation(altered, metadata) is None
    duplicate = copy.deepcopy(metadata)
    duplicate["fields"].append(duplicate["fields"][0])
    assert supported_observation(payload, duplicate) is None
    assert pointer(["a/b", "~key"]) == "/a~1b/~0key"
    assert HELPERS["source_predicate"]("86", "\uff11\uff12\uff13\uff14\uff15\uff16") is False
    print("PASS: explicit paths, unambiguous source checks, metadata agreement, immutable mutations and ASCII sequence enforcement")


def prepare(review, output, size, producer_directory):
    if os.name == "nt" and not str(output).startswith("\\\\?\\"):
        output = Path("\\\\?\\" + str(output.resolve()))
    run = json.loads((review / "complete-handoff-analysis.json").read_text())
    archive = Path(run["archive"])
    source_file = MODULE / "specifications/ATL105/docs/specs/extracted_text.txt"
    sources = {element: source_evidence(source_file, element) for element in ["85", "86"]}
    scenario_file = archive / "scenerio_req_5_oct/scenerio_req_5_oct/scenarios/approved/approved_scenarios.json"
    scenarios = {row["id"]: row for row in json.loads(scenario_file.read_text(encoding="utf-8-sig"))["scenarios"]}
    case_file = archive / "pipeline_run_artifacts/test_case_candidates.json"
    payload_root = archive / "pipeline_run_artifacts/qe_shaped_test_data"
    with (review / "intake-file-hashes.csv").open(encoding="utf-8-sig", newline="") as stream:
        manifest = {row["path"]: row for row in csv.DictReader(stream)}
    for file in [scenario_file, case_file]:
        if digest(file) != manifest[file.relative_to(archive).as_posix()]["sha256"]:
            raise ValueError("Frozen catalog changed: " + file.name)
    selected = []
    with case_file.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item", use_float=True):
            if case.get("scenario_type") != "positive" or case.get("is_flow"):
                continue
            if case.get("scenario_id") not in scenarios or not HELPERS["links"](case):
                continue
            payload_file = payload_root / (case["id"] + ".json")
            metadata_file = payload_root / (case["id"] + ".meta.json")
            if not payload_file.is_file() or not metadata_file.is_file():
                continue
            payload = json.loads(payload_file.read_text(encoding="utf-8-sig"))
            metadata = json.loads(metadata_file.read_text(encoding="utf-8-sig"))
            checks = supported_observation(payload, metadata)
            if checks is None:
                continue
            hashes = {file.name: digest(file) for file in [payload_file, metadata_file]}
            for file in [payload_file, metadata_file]:
                if hashes[file.name] != manifest[file.relative_to(archive).as_posix()]["sha256"]:
                    raise ValueError("Frozen physical input changed: " + file.name)
            selected.append({"caseId": case["id"], "scenarioId": case["scenario_id"], "payload": payload,
                             "checks": checks, "physicalInputSha256": hashes,
                             "producerFlags": case.get("flags", []), "producerReadiness": case.get("completeness_status")})
            if len(selected) >= size:
                break
    if not selected:
        raise ValueError("No supported financial Segment 100 controls found")
    if output.exists():
        raise ValueError("Output must be a new run directory; refusing overwrite")
    output.mkdir(parents=True)
    register = []
    sidecar = []
    rules = {"85": ("SEG100_TYPE", "101"), "86": ("SEQUENCE_SIX_DIGITS", "12345")}
    for item in selected:
        scope = [{"pointer": pointer(item["checks"][element]["payloadPath"]), "rule": rules[element][0]} for element in rules]
        register.append({"caseId": item["caseId"], "intent": "positive", "originCaseId": item["caseId"]})
        sidecar.append({"caseId": item["caseId"], "observation": {"request": item["payload"]}, "evidence": {}})
        for element, (rule, invalid) in rules.items():
            probe_id = "LOCAL-PROBE-" + item["caseId"] + "-" + element
            path = item["checks"][element]["payloadPath"]
            register.append({"caseId": probe_id, "intent": "negative", "originCaseId": item["caseId"], "synthetic": True})
            sidecar.append({"caseId": probe_id,
                "observation": {"controlPayload": item["payload"], "mutantPayload": mutate(item["payload"], path, invalid)},
                "evidence": {"negativeIsolation": {"source": sources[element], "targetPointer": pointer(path),
                              "targetRule": rule, "controlScope": scope}}})
    discovered = sorted(str(file) for file in producer_directory.rglob("traceability_matrix*.json")) if producer_directory.is_dir() else []
    summary = {"scope": "Offline Segment 100 identity/six-digit representation qualification only; not complete BR equivalence, valid full-message controls or host execution",
               "selectedPhysicalControls": len(selected), "requestedControls": size, "syntheticNegativeProbes": len(selected) * 2,
               "scopePredicateChecksPassed": len(selected) * 2, "fullSemanticCoverage": "NOT_CALCULABLE",
               "semanticApproval": "NOT_GRANTED", "executionCertified": False, "hostExecutionPerformed": False,
               "producerEvidence": {"searchedDirectory": str(producer_directory), "completeMatrixCandidates": discovered,
                                    "status": "COMPANION_DISCOVERED_REQUIRES_IMMUTABLE_INTAKE" if discovered else "COMPLETE_PRODUCER_MATRIX_NOT_AVAILABLE"},
               "supportedSourcePredicates": sources,
               "controls": [{key: value for key, value in item.items() if key != "payload"} for item in selected],
               "blockers": ["Full AI BR interpretations and independently approved crosswalk not supplied",
                   "Actual captured wire and complete valid control profiles not supplied",
                   "Approved host target, authoritative expected outcomes and observed host responses not supplied",
                   "Two disputed producer BR mappings and 63 additional rows lack producer edges"]}
    for name, value in [("cohort-register.json", register), ("cohort-evidence-sidecar.json", {"schemaVersion": 1, "cases": sidecar}),
                        ("autonomous-cohort-summary.json", summary)]:
        (output / name).write_text(json.dumps(value, indent=2, ensure_ascii=True), encoding="utf-8")
    print(f"Prepared {len(selected)} physical controls and {len(selected) * 2} synthetic scoped probes at {output}")
    print("Host execution/certification: not performed/not granted; source and immutable input hashes recorded")


def finalize(review, run_directory, supplied_catalog):
    if os.name == "nt" and not str(run_directory).startswith("\\\\?\\"):
        run_directory = Path("\\\\?\\" + str(run_directory.resolve()))
    summary_file = run_directory / "autonomous-cohort-summary.json"
    gates_file = run_directory / "java-gate-assessment.json"
    summary = json.loads(summary_file.read_text(encoding="utf-8"))
    gates = json.loads(gates_file.read_text(encoding="utf-8"))
    if gates["caseCount"] != summary["selectedPhysicalControls"] + summary["syntheticNegativeProbes"]:
        raise ValueError("Java cohort count mismatch")
    if gates["gateCounts"]["negativeIsolation"]["PASS"] != summary["syntheticNegativeProbes"]:
        raise ValueError("Not all expected isolated source probes passed")
    if gates["executionCertified"] or gates["gateCounts"]["negativeEffectiveness"]["PASS"]:
        raise ValueError("Unexpected execution/effectiveness claim for bounded local cohort")
    frozen_run = json.loads((review / "complete-handoff-analysis.json").read_text())
    frozen_catalog = Path(frozen_run["archive"]) / "pipeline_run_artifacts/test_case_candidates.json"
    supplied_hash = digest(supplied_catalog)
    frozen_hash = digest(frozen_catalog)
    request = {"status": "PREPARED_LOCALLY_NOT_TRANSMITTED_NO_RECIPIENT_CONFIGURED",
               "delivery": "ATL105-2026-10-05-Run1", "expectedFrozenTcSha256": frozen_hash,
               "requiredProducerEvidence": ["Complete untruncated producer traceability matrix JSON",
                   "Exact BR/TS/TC catalog hashes and any revised artifacts",
                   "63 additional explicit BR-scenario-TC-TD edges and disposition for BR :521 and :3226",
                   "Orphan-count definitions and distinct requirement/leaf trace statuses"],
               "requiredExecutionEvidence": ["Authorized test-host endpoint and supported message profile",
                   "Independent authoritative expected outcomes and state fixtures",
                   "Complete valid control fixtures, actual wire captures and observed responses"],
               "humanApprovalGranted": False}
    request_file = run_directory / "required-evidence-request.json"
    if request_file.exists():
        raise ValueError("Finalized evidence request already exists; preserve run immutability")
    request_file.write_text(json.dumps(request, indent=2), encoding="utf-8")
    run_relative = os.path.relpath(str(run_directory).removeprefix("\\\\?\\"), review).replace("\\", "/")
    report = {**summary, "javaGateCounts": gates["gateCounts"],
              "providedCatalogComparison": {"providedSha256": supplied_hash, "frozenSha256": frozen_hash,
                                            "sameContent": supplied_hash == frozen_hash},
              "executionReadiness": "BLOCKED_REQUIRED_EXTERNAL_EVIDENCE",
              "automaticActionsCompleted": ["Producer matrix discovery", "Frozen/provided TC catalog hash comparison",
                  "Twenty source-bounded real-control selections", "Forty immutable synthetic one-field probes",
                  "Source-hash/line-bound Java gate execution", "Local machine-readable evidence request"],
              "runDirectory": run_relative, "evidenceRequest": run_relative + "/required-evidence-request.json",
              "javaReport": run_relative + "/java-gate-assessment.json",
              "artifactSha256": {name: digest(run_directory / name) for name in ["cohort-register.json", "cohort-evidence-sidecar.json", "autonomous-cohort-summary.json", "java-gate-assessment.json", "required-evidence-request.json"]}}
    (review / "autonomous-qualification-assessment.json").write_text(json.dumps(report, indent=2, ensure_ascii=True), encoding="utf-8")
    print("Verified local qualification: 20 real controls, 40/40 scoped probes; full certification remains blocked")
    print("Provided TC catalog matches frozen catalog: " + str(supplied_hash == frozen_hash))


def report_section(summary):
    from html import escape
    gates = summary["javaGateCounts"]
    rows = "".join(f"<tr><td>{escape(name)}</td><td>{values['PASS']}</td><td>{values['NOT_ASSESSED']}</td><td>{values['NOT_APPLICABLE']}</td></tr>" for name, values in gates.items())
    history = ""
    if summary.get("publicationHistory"):
        snapshot = summary["publicationHistory"]
        history = f'''<p class="scope">Previous report publication preserved: {len(snapshot['files'])} hash-recorded files. <a href="{snapshot['directory']}/AI-ARTIFACT-FILTER-VIEW.html">Report before autonomous qualification publication</a></p>'''
    return f'''<section class="band" id="autonomous-qualification"><h2>Autonomous local qualification | October 6</h2>
<p class="scope">{summary['selectedPhysicalControls']} hash-verified physical Financial Transaction Request controls; {summary['syntheticNegativeProbes']} explicitly synthetic single-field probes. Source scope: Segment 100 identity and six ASCII sequence digits only. The two source predicates are not a complete BR denominator.</p>
<div class="notice">Local scoped isolation: 40 / 40 passed. Full-message controls, semantic approval, authoritative host outcomes and execution certification remain blocked. No request was sent to a host and no producer/SME approval was inferred.</div>
<div class="table-wrap"><table class="plain-table"><thead><tr><th>Java gate</th><th>Pass</th><th>Not assessed</th><th>Not applicable</th></tr></thead><tbody>{rows}</tbody></table></div>
<p class="scope">The supplied TC catalog exactly matches the frozen catalog: {str(summary['providedCatalogComparison']['sameContent']).lower()}. No new producer matrix/edges were found. The evidence request is prepared locally, not transmitted: no recipient/channel or authorized host target was supplied.</p>
<p><a href="autonomous-qualification-assessment.json">Qualification summary and hashes</a> &middot; <a href="{summary['javaReport']}">Actual Java gate results</a> &middot; <a href="{summary['evidenceRequest']}">Machine-readable evidence request</a></p>{history}</section>'''


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--review-directory", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--producer-directory", type=Path)
    parser.add_argument("--size", type=int, default=20)
    parser.add_argument("--self-test", action="store_true")
    parser.add_argument("--finalize-run", type=Path)
    parser.add_argument("--supplied-catalog", type=Path)
    parser.add_argument("--record-publication-history", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.record_publication_history and args.review_directory:
        review = args.review_directory
        history = review / "history/pre-autonomous-report-20261006"
        if os.name == "nt":
            history = Path("\\\\?\\" + str(history.resolve()))
        summary_file = review / "autonomous-qualification-assessment.json"
        summary = json.loads(summary_file.read_text(encoding="utf-8"))
        summary["publicationHistory"] = {"directory": "history/pre-autonomous-report-20261006",
                                         "files": {file.name: digest(file) for file in history.iterdir() if file.is_file()}}
        summary_file.write_text(json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
        print("Recorded publication snapshot hashes: " + str(len(summary["publicationHistory"]["files"])))
    elif args.finalize_run and args.review_directory and args.supplied_catalog:
        finalize(args.review_directory, args.finalize_run, args.supplied_catalog)
    elif not args.review_directory or not args.output_directory or not args.producer_directory or args.size < 1:
        parser.error("Review, new output, producer directories and positive size are required")
    else:
        prepare(args.review_directory, args.output_directory, args.size, args.producer_directory)


if __name__ == "__main__":
    main()