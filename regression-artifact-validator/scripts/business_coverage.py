"""Evidence-gated business coverage; never infer approvals or host execution."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import re
import sys
from pathlib import Path

import delivery_gates as gates

ROOT = Path(__file__).resolve().parents[1]
VARIATIONS = {"POSITIVE", "NEGATIVE", "BOUNDARY", "CONDITIONAL", "LIFECYCLE"}
OUTCOMES = {"PASS", "FAIL", "REVIEW_REQUIRED", "NOT_ASSESSED"}


def require(condition, message):
    if not condition:
        raise ValueError(message)


def text(value):
    return isinstance(value, str) and bool(value.strip())


def fingerprint(value):
    return hashlib.sha256(json.dumps(
        value, sort_keys=True, separators=(",", ":"), ensure_ascii=True,
        allow_nan=False).encode("utf-8")).hexdigest()


def file_hash(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def load_object(path):
    value = gates.load(path)
    require(isinstance(value, dict), f"JSON object required: {path.name}")
    return value


def indexed(rows, label):
    require(isinstance(rows, list), f"{label} must be an array")
    result = {}
    for row in rows:
        require(isinstance(row, dict) and isinstance(row.get("id"), str)
                and re.fullmatch(r"[A-Za-z0-9][A-Za-z0-9_.:-]*", row["id"]),
                f"{label}: unambiguous ID required")
        require(row["id"] not in result, f"{label}: duplicate ID {row['id']}")
        result[row["id"]] = row
    return result


def unique_strings(values, label, nonempty=True):
    require(isinstance(values, list) and all(text(v) for v in values),
            f"{label}: string array required")
    require(len(set(values)) == len(values), f"{label}: duplicate value")
    require(not nonempty or bool(values), f"{label}: empty array")
    return set(values)


def bound_file(root, reference):
    require(isinstance(reference, dict), "File reference must contain path and sha256")
    require(isinstance(reference.get("sha256"), str)
            and re.fullmatch(r"[0-9a-f]{64}", reference["sha256"]), "SHA-256 required")
    path = gates.resolve_reference(root, reference.get("path"))
    require(path.is_file(), f"Missing evidence: {reference.get('path')}")
    original = root.joinpath(*reference["path"].replace("\\", "/").split("/"))
    require(not any(p.is_symlink() for p in (original, *original.parents)),
            "Symlink evidence is not supported")
    require(file_hash(path) == reference["sha256"], f"Stale evidence: {reference['path']}")
    require(path.stat().st_size > 0, f"Empty evidence: {reference['path']}")
    return path


def approved(root, reference, subject, roles=("SME", "TBA")):
    if reference is None:
        return False
    record = load_object(bound_file(root, reference))
    require(record.get("subjectSha256") == fingerprint(subject), "Stale review subject")
    require(record.get("decision") in {"APPROVED", "REVIEW_REQUIRED", "REJECTED"},
            "Unknown review decision")
    require(text(record.get("reviewer")) and record.get("role") in roles
            and text(record.get("rationale")), "Reviewer, permitted role and rationale required")
    return record["decision"] == "APPROVED"


def measure(numerator, denominator, eligible=True):
    return {"numerator": numerator, "denominator": denominator,
            "percent": round(100 * numerator / denominator, 2)
            if eligible and denominator else None}


def subject(record):
    return {key: value for key, value in record.items() if key != "review"}


def review_context(document):
    return {
        "scope": subject(document["scope"]), "sources": document["sources"],
        "requirements": [subject(r) for r in document["requirements"]],
        "semanticPolicy": document["semanticPolicy"],
        "inventory": {
            "scenarios": [subject(r) for r in document["scenarios"]],
            "testCases": document["testCases"], "testData": document["testData"],
            "flows": [subject(r) for r in document["flows"]]}}


def source_inventory(document, trusted_root):
    sources = indexed(document["sources"], "sources")
    require(bool(sources), "At least one authoritative source is required")
    for source in sources.values():
        require(source.get("specification") == document["scope"]["specification"]
                and source.get("version") == document["scope"]["version"],
                "Source/specification release mismatch")
        raw = bound_file(trusted_root, source).read_text(encoding="utf-8-sig")
        pages = re.split(r"--- PAGE (\d+) ---", raw)
        page_index = {int(pages[i]): pages[i + 1] for i in range(1, len(pages), 2)}
        anchors = indexed(source.get("anchors"), "source anchors")
        require(bool(anchors), "Source anchors required")
        for anchor in anchors.values():
            require(isinstance(anchor, dict) and type(anchor.get("page")) is int
                    and text(anchor.get("excerpt")), "Page and excerpt required")
            page = page_index.get(anchor["page"], "")
            require(" ".join(anchor["excerpt"].split()) in " ".join(page.split()),
                    f"Source page/excerpt mismatch: {source['id']}/{anchor['page']}")
    return sources


def rule_checks(rows, criteria, label):
    require(isinstance(rows, list), f"{label}: ruleChecks array required")
    result = {}
    for row in rows:
        require(isinstance(row, dict), f"{label}: check must be an object")
        key = (row.get("requirementId"), row.get("criterionId"))
        require(key in criteria and key not in result, f"{label}: unknown/duplicate criterion")
        require(row.get("expected") in {"PASS", "FAIL"}
                and row.get("observed") in OUTCOMES and text(row.get("detail")),
                f"{label}: expected/observed result and detail required")
        result[key] = row["expected"] == row["observed"] if row["observed"] in {"PASS", "FAIL"} else None
    return result


def semantic_row(root, artifact, kind, expected_keys, assessment, policy, context):
    base = {"id": artifact["id"], "kind": kind, "artifactSha256": fingerprint(artifact),
            "status": "NOT_ASSESSED", "complete": False, "accepted": False,
            "checksPassed": 0, "checksAssessed": 0, "missingChecks": sorted(policy),
            "ruleResults": {}, "evidencePath": None, "evidenceSha256": None}
    if assessment is None:
        return base
    evidence_ref = assessment["evidence"]
    evidence = load_object(bound_file(root, evidence_ref))
    require(evidence.get("authority") == "INDEPENDENT_TEST_SOLUTION",
            "Producer self-validation is not independent semantic evidence")
    require(evidence.get("artifactId") == artifact["id"]
            and evidence.get("artifactSha256") == fingerprint(artifact),
            "Semantic evidence is bound to another artifact/build")
    checks = indexed(evidence.get("checks"), "semantic checks")
    require(checks.keys() <= policy, "Unknown semantic check")
    resolved = set()
    passed = set()
    for key, check in checks.items():
        require(check.get("status") in OUTCOMES and text(check.get("detail")),
                "Semantic check status and detail required")
        if check["status"] in {"PASS", "FAIL"}:
            resolved.add(key)
        if check["status"] == "PASS":
            passed.add(key)
    rules = rule_checks(evidence.get("ruleChecks"), expected_keys, artifact["id"])
    require(set(rules) <= expected_keys, "Unrelated rule evidence")
    complete = resolved == policy and set(rules) == expected_keys and all(v is not None for v in rules.values())
    successful = complete and passed == policy and all(rules.values())
    reviewed = approved(root, assessment.get("review"), {
        "context": context, "artifact": artifact, "evidence": evidence_ref})
    base.update(status="FAIL" if resolved - passed or any(v is False for v in rules.values())
                else "PASS_DECLARED_CHECKS" if successful else "PARTIAL",
                complete=complete, accepted=successful and reviewed,
                checksPassed=len(passed), checksAssessed=len(resolved),
                missingChecks=sorted(policy - resolved),
                evidencePath=evidence_ref["path"], evidenceSha256=evidence_ref["sha256"],
                ruleResults={f"{r}/{c}": ok for (r, c), ok in rules.items()})
    return base


def execution_rows(root, document, flows, cases, data, context, baseline_ready):
    runs = indexed(document["runs"], "runs")
    selected = {}
    rows = []
    for run in runs.values():
        require(run.get("flowId") in flows, "Run references unknown flow")
        require(run["flowId"] not in selected, "Select exactly one run per flow; retain retries separately")
        selected[run["flowId"]] = run
    for flow in flows.values():
        require(flow.get("applicability") in {"APPLICABLE", "UNRESOLVED", "EXCLUDED"},
                "Invalid flow applicability")
        flow_reviewed = approved(root, flow.get("review"), {"context": context, "flow": subject(flow)})
        steps = indexed(flow.get("steps"), "flow steps")
        require(bool(steps), "Flow requires at least one step")
        for step in steps.values():
            require(step.get("caseId") in cases, "Flow step references unknown TC")
            require(isinstance(step.get("assertions"), list) and bool(step["assertions"]),
                    "Flow must define expected response assertions before execution")
            for assertion in step["assertions"]:
                require(isinstance(assertion, dict) and text(assertion.get("pointer"))
                        and assertion["pointer"].startswith("/") and "expected" in assertion,
                        "Flow response assertion needs pointer and expected value")
        prerequisites = unique_strings(flow.get("prerequisites"), "flow prerequisites")
        readiness = flow.get("readiness", {})
        require(isinstance(readiness, dict) and readiness.keys() <= prerequisites,
                "Unknown readiness prerequisite")
        ready_without_run = set()
        for key, reference in readiness.items():
            record = load_object(bound_file(root, reference))
            require(text(flow.get("environmentId")) and record.get("id") == key
                    and record.get("environmentId") == flow["environmentId"]
                    and record.get("status") in {"READY", "NOT_READY", "UNKNOWN"},
                    "Readiness evidence must identify the assigned environment and prerequisite")
            if record["status"] == "READY":
                ready_without_run.add(key)
        run = selected.get(flow["id"])
        row = {"id": flow["id"], "applicability": flow["applicability"],
               "reviewed": flow_reviewed, "attempted": False, "executed": False,
               "passed": False, "status": "NOT_RUN",
               "blockers": sorted(prerequisites - ready_without_run), "runId": None,
               "requiredStepIds": sorted(steps), "executedStepIds": [],
               "missingStepIds": sorted(steps)}
        if run is None:
            if row["blockers"]:
                row["status"] = "BLOCKED"
            rows.append(row)
            continue
        require(flow["applicability"] == "APPLICABLE", "Run for unresolved/excluded flow")
        environment = load_object(bound_file(root, run["environment"]))
        require(text(environment.get("id")) and environment.get("mode") in {"HOST", "AUTHORIZED_SIMULATOR"},
                "Offline fixtures are not E2E execution")
        require(not flow.get("environmentId") or flow["environmentId"] == environment["id"],
                "Execution environment differs from the flow readiness environment")
        require(approved(root, run.get("authorization"), {
            "context": context, "environment": run["environment"], "flowId": flow["id"]},
            roles=("EXECUTION_OWNER",)), "Authorized environment evidence required")
        require(approved(root, run.get("attestation"), {
            "context": context, "flow": subject(flow), "run": {
                k: v for k, v in run.items() if k != "attestation"}},
            roles=("EXECUTION_OWNER",)), "Bound run attestation required")
        ready = {}
        for key, reference in run.get("prerequisites", {}).items():
            require(key in prerequisites, "Unknown execution prerequisite")
            record = load_object(bound_file(root, reference))
            require(record.get("id") == key and record.get("status") == "READY"
                    and record.get("environmentId") == environment["id"],
                    "Prerequisite is not READY in this environment")
            ready[key] = reference
        missing = prerequisites - ready.keys()
        require(not missing, "Run supplied with unresolved required prerequisites")
        observed_steps = indexed(run.get("steps"), "run steps")
        require(bool(observed_steps) and observed_steps.keys() <= steps.keys(),
                "Run requires actual known steps")
        passed = True
        for step_id, observed in observed_steps.items():
            case = cases[steps[step_id]["caseId"]]
            require(observed.get("caseSha256") == fingerprint(case), "Stale executed TC")
            expected_data = {identity: data[identity]["file"]["sha256"] for identity in case["testDataIds"]}
            require(observed.get("dataHashes") == expected_data, "Stale/missing executed TD legs")
            bound_file(root, observed["request"])
            response = gates.load(bound_file(root, observed["response"]))
            assertions = observed.get("assertions")
            require(fingerprint(assertions) == fingerprint(steps[step_id]["assertions"]),
                    "Run response assertions differ from the defined flow")
            for assertion in assertions:
                require(isinstance(assertion, dict) and text(assertion.get("pointer"))
                        and "expected" in assertion, "Expected response assertion required")
                actual = response
                pointer = assertion["pointer"]
                require(pointer.startswith("/"), "Response JSON pointer must start with /")
                for part in pointer[1:].split("/"):
                    require(re.search(r"~(?![01])", part) is None, "Invalid JSON pointer escape")
                    token = part.replace("~1", "/").replace("~0", "~")
                    if isinstance(actual, dict) and token in actual:
                        actual = actual[token]
                    elif isinstance(actual, list) and re.fullmatch(r"0|[1-9][0-9]*", token) and int(token) < len(actual):
                        actual = actual[int(token)]
                    else:
                        raise ValueError("Response assertion target missing")
                passed = passed and fingerprint(actual) == fingerprint(assertion["expected"])
        executed = observed_steps.keys() == steps.keys()
        row.update(attempted=True, executed=executed, passed=executed and passed,
                   status="PASS" if executed and passed else "FAIL" if executed else "INCOMPLETE",
                   blockers=[], runId=run["id"], executedStepIds=sorted(observed_steps),
                   missingStepIds=sorted(steps.keys() - observed_steps.keys()))
        rows.append(row)
    applicable = [r for r in rows if r["applicability"] == "APPLICABLE"]
    ready = baseline_ready and all(r["reviewed"] and r["applicability"] != "UNRESOLVED" for r in rows)
    attempted = sum(r["attempted"] for r in applicable)
    return rows, {
        "status": "RECORDED_EXECUTION_EVIDENCE" if attempted and ready else "NOT_VERIFIED",
        "passedOfAttempted": measure(sum(r["passed"] for r in applicable), attempted, ready),
        "executedOfApplicable": measure(sum(r["executed"] for r in applicable), len(applicable), ready),
        "blocked": sum(r["status"] == "BLOCKED" for r in applicable),
        "notRun": sum(r["status"] == "NOT_RUN" for r in applicable),
        "incomplete": sum(r["status"] == "INCOMPLETE" for r in applicable),
        "failed": sum(r["status"] == "FAIL" for r in applicable),
        "unresolved": sum(r["applicability"] == "UNRESOLVED" for r in rows)}


def assess(root, trusted_root=ROOT):
    document = load_object(root / "assessment.json")
    require(type(document.get("schemaVersion")) is int and document["schemaVersion"] == 1,
            "Unsupported coverage contract version")
    scope = document["scope"]
    require(isinstance(scope, dict), "Scope must be an object")
    require(all(text(scope.get(k)) for k in ("id", "specification", "version", "description")),
            "Scope identity, release and description required")
    require(scope.get("kind") in {"BOUNDED_PILOT", "BUSINESS_SCOPE"},
            "Declare bounded pilot or business scope explicitly")
    unique_strings(scope.get("outOfScope"), "scope exclusions", nonempty=False)
    sources = source_inventory(document, trusted_root)
    requirements = indexed(document["requirements"], "requirements")
    scenarios = indexed(document["scenarios"], "scenarios")
    cases = indexed(document["testCases"], "testCases")
    data = indexed(document["testData"], "testData")
    flows = indexed(document["flows"], "flows")
    require(bool(requirements), "Requirement population cannot be empty")
    require(not (scenarios.keys() & cases.keys() or scenarios.keys() & data.keys()
                 or cases.keys() & data.keys()), "TS/TC/TD IDs must be unique across artifact kinds")
    policies = document["semanticPolicy"]
    policies = {kind: unique_strings(policies[kind], f"{kind} semantic policy") for kind in ("TC", "TD")}
    context = review_context(document)
    baseline_reviewed = approved(root, scope.get("review"), context)
    criteria = {}
    rule_ready = {}
    for rule in requirements.values():
        require(text(rule.get("statement")), "Requirement statement required")
        require(rule.get("applicability") in {"APPLICABLE", "UNRESOLVED", "EXCLUDED"},
                "Unknown requirement applicability")
        references = rule.get("sourceRefs")
        require(isinstance(references, list) and bool(references), "Requirement source references required")
        seen_refs = set()
        for reference in references:
            require(isinstance(reference, dict), "Requirement source reference must be an object")
            source_id, anchor_id = reference.get("sourceId"), reference.get("anchorId")
            require(source_id in sources and anchor_id in {
                a["id"] for a in sources[source_id]["anchors"]}, "Requirement source/anchor missing")
            require((source_id, anchor_id) not in seen_refs, "Duplicate requirement source reference")
            seen_refs.add((source_id, anchor_id))
        criterion_index = indexed(rule.get("criteria"), "rule criteria")
        require(bool(criterion_index), "Explicit test-design criteria required")
        for criterion in criterion_index.values():
            require(criterion.get("variation") in VARIATIONS and text(criterion.get("description")),
                    "Variation and criterion description required")
            criteria[(rule["id"], criterion["id"])] = criterion
        rule_ready[rule["id"]] = approved(root, rule.get("review"), {
            "context": context, "requirement": subject(rule)})
    for scenario in scenarios.values():
        require(text(scenario.get("intent")), "Scenario intent required")
        require(unique_strings(scenario.get("requirementIds"), "scenario requirements") <= requirements.keys(),
                "Unknown scenario requirement")
    artifact_keys = {}
    for case in cases.values():
        require(case.get("scenarioId") in scenarios and text(case.get("intent")),
                "TC scenario/intent required")
        unique_strings(case.get("steps"), "TC steps")
        require(text(case.get("expectedOutcome")), "TC expected outcome required")
        require(unique_strings(case.get("testDataIds"), "TC data legs") <= data.keys(), "Unknown TC data leg")
        keys = set()
        for mapping in case.get("coverage", []):
            key = (mapping.get("requirementId"), mapping.get("criterionId"))
            require(key in criteria and key not in keys, "Unknown/duplicate TC coverage criterion")
            require(key[0] in scenarios[case["scenarioId"]]["requirementIds"],
                    "TC coverage contradicts scenario mapping")
            require(mapping.get("expected") in {"PASS", "FAIL"}, "TC expected predicate result required")
            keys.add(key)
        require(bool(keys), "TC requires explicit criterion coverage")
        artifact_keys[case["id"]] = keys
    for item in data.values():
        bound_file(root, item["file"])
        owners = [c for c in cases.values() if item["id"] in c["testDataIds"]]
        artifact_keys[item["id"]] = set().union(*(artifact_keys[c["id"]] for c in owners))
        require(bool(owners), "Unreferenced TD must be resolved, not silently excluded")
    assessments = indexed(document["semanticAssessments"], "semanticAssessments")
    require(assessments.keys() <= cases.keys() | data.keys(), "Unknown semantic-assessment artifact")
    semantic = []
    for kind, artifacts in (("TC", cases), ("TD", data)):
        for artifact in artifacts.values():
            semantic.append(semantic_row(root, artifact, kind, artifact_keys[artifact["id"]],
                                         assessments.get(artifact["id"]), policies[kind], context))
    by_artifact = {row["id"]: row for row in semantic}
    for case in cases.values():
        for mapping in case["coverage"]:
            key = f"{mapping['requirementId']}/{mapping['criterionId']}"
            expected = mapping["expected"]
            for identity in [case["id"], *case["testDataIds"]]:
                evidence = assessments.get(identity)
                if evidence:
                    report = load_object(bound_file(root, evidence["evidence"]))
                    matching = [c for c in report["ruleChecks"]
                                if f"{c['requirementId']}/{c['criterionId']}" == key]
                    require(not matching or matching[0]["expected"] == expected,
                            "Semantic evidence contradicts TC negative/positive intent")
    requirement_rows = []
    for rule in requirements.values():
        linked = [s for s in scenarios.values() if rule["id"] in s["requirementIds"]]
        reviewed_links = [s for s in linked if approved(root, s.get("review"), {
            "context": context, "scenario": subject(s)})]
        covered = set()
        accepted = set()
        for case in cases.values():
            related = [by_artifact[i] for i in [case["id"], *case["testDataIds"]]]
            for mapping in case["coverage"]:
                if mapping["requirementId"] != rule["id"]:
                    continue
                key = f"{rule['id']}/{mapping['criterionId']}"
                if all(row["ruleResults"].get(key) is True for row in related):
                    covered.add(mapping["criterionId"])
                    if (all(row["accepted"] for row in related)
                            and case["scenarioId"] in {s["id"] for s in reviewed_links}):
                        accepted.add(mapping["criterionId"])
        required = {c["id"] for c in rule["criteria"]}
        requirement_rows.append({
            "id": rule["id"], "applicability": rule["applicability"], "reviewed": rule_ready[rule["id"]],
            "statement": rule["statement"], "sourceRefs": rule["sourceRefs"],
            "scenarioIds": [s["id"] for s in linked],
            "reviewedScenarioIds": [s["id"] for s in reviewed_links],
            "requiredCriteria": sorted(required), "observedCriteria": sorted(covered),
            "acceptedCriteria": sorted(accepted), "missingCriteria": sorted(required - covered),
            "missingAcceptedCriteria": sorted(required - accepted),
            "adequate": required == accepted, "provisionalAdequate": required == covered})
    applicable = [r for r in requirement_rows if r["applicability"] == "APPLICABLE"]
    denominator_ready = baseline_reviewed and all(
        r["reviewed"] and r["applicability"] != "UNRESOLVED" for r in requirement_rows)
    metrics = {
        "scenarioCompleteness": {
            "status": "REVIEWED_SCOPE" if denominator_ready else "NOT_ESTABLISHED",
            **measure(sum(bool(r["reviewedScenarioIds"]) for r in applicable), len(applicable), denominator_ready),
            "provisional": measure(sum(bool(r["scenarioIds"]) for r in applicable), len(applicable)),
            "unresolved": sum(r["applicability"] == "UNRESOLVED" for r in requirement_rows),
            "excluded": sum(r["applicability"] == "EXCLUDED" for r in requirement_rows)},
        "ruleAdequacy": {
            "status": "REVIEWED_SCOPE" if denominator_ready else "NOT_QUANTIFIED",
            **measure(sum(r["adequate"] for r in applicable), len(applicable), denominator_ready),
            "provisional": measure(sum(r["provisionalAdequate"] for r in applicable), len(applicable))}}
    for kind in ("TC", "TD"):
        population = [r for r in semantic if r["kind"] == kind]
        assessed = [r for r in population if r["complete"]]
        metrics[f"semantic{kind}"] = {
            "status": "REVIEWED_DECLARED_SCOPE" if denominator_ready and population
            and all(r["accepted"] for r in population) else "NOT_CERTIFIED",
            **measure(sum(r["accepted"] for r in assessed), len(assessed), denominator_ready),
            "population": len(population), "notFullyAssessed": len(population) - len(assessed),
            "failed": sum(r["status"] == "FAIL" for r in population),
            "boundedChecks": measure(sum(r["checksPassed"] for r in population),
                                     sum(r["checksAssessed"] for r in population))}
    flow_rows, metrics["execution"] = execution_rows(
        root, document, flows, cases, data, context, denominator_ready)
    return {
        "schemaVersion": 1, "scope": subject(scope), "baselineReviewed": baseline_reviewed,
        "denominatorEstablished": denominator_ready, "inputSha256": file_hash(root / "assessment.json"),
        "metrics": metrics, "requirements": requirement_rows, "semantics": semantic, "flows": flow_rows,
        "limitations": [
            "Percentages apply only to the declared reviewed scope, never the whole specification.",
            "Provisional mappings and bounded checks are not approved business or semantic coverage.",
            "Review/authorization records are hash-bound attestations; signer identity is not authenticated by this tool.",
            "Execution metrics validate supplied evidence; no host is contacted and execution is not independently certified.",
            "Missing applicability, semantic or execution evidence remains visible; no success fallback is generated."]}


def publish(result, output):
    require(not output.exists(), "Refusing to overwrite an existing assessment output")
    output.mkdir(parents=True)
    (output / "assessment-results.json").write_text(json.dumps(result, indent=2) + "\n", encoding="utf-8")
    for name in ("requirements", "semantics", "flows"):
        rows = result[name]
        with (output / f"{name}.csv").open("w", newline="", encoding="utf-8") as stream:
            if rows:
                writer = csv.DictWriter(stream, fieldnames=list(rows[0]))
                writer.writeheader()
                writer.writerows({k: json.dumps(v) if isinstance(v, (list, dict)) else v
                                  for k, v in row.items()} for row in rows)
    lines = ["# Business coverage assessment", "", f"Scope: {result['scope']['id']}",
             "", f"Reviewed denominator established: {result['denominatorEstablished']}.",
             "Counts remain candidate-scope observations when the denominator gate is not established.",
             "", "| Measure | Status | Numerator | Denominator | Percent |", "|---|---|---:|---:|---:|"]
    for name, value in result["metrics"].items():
        entries = value.items() if name == "execution" else [(name, value)]
        for key, metric in entries:
            if not isinstance(metric, dict) or "percent" not in metric:
                continue
            lines.append(f"| {key} | {value['status']} | {metric['numerator']} | "
                         f"{metric['denominator']} | "
                         f"{str(metric['percent']) + '%' if metric['percent'] is not None else 'Not established'} |")
    lines += ["", "## Provisional and bounded observations", ""]
    for name, metric in result["metrics"].items():
        observed = metric.get("provisional") or metric.get("boundedChecks")
        if observed:
            percentage = f"{observed['percent']}%" if observed["percent"] is not None else "not assessed"
            lines.append(f"- {name}: {observed['numerator']}/{observed['denominator']} "
                         f"({percentage}). Not approved business coverage.")
    lines += ["", "## Semantic assessment completeness", "",
              "| Kind | Population | Fully assessed | Not fully assessed | Failed |",
              "|---|---:|---:|---:|---:|"]
    for kind in ("TC", "TD"):
        metric = result["metrics"][f"semantic{kind}"]
        lines.append(f"| {kind} | {metric['population']} | {metric['denominator']} | "
                     f"{metric['notFullyAssessed']} | {metric['failed']} |")
    lines += ["", "## Execution dispositions", ""]
    execution = result["metrics"]["execution"]
    lines += [f"- {key}: {execution[key]}" for key in ("blocked", "notRun", "incomplete", "failed", "unresolved")]
    lines += ["", "## Limitations", "", *["- " + line for line in result["limitations"]], ""]
    (output / "REPORT.md").write_text("\n".join(lines), encoding="utf-8")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", type=Path, required=True, help="Bundle containing assessment.json")
    parser.add_argument("--output", type=Path, required=True, help="New report directory")
    parser.add_argument("--trusted-root", type=Path, default=ROOT)
    args = parser.parse_args()
    try:
        require(not args.output.resolve().is_relative_to(args.input.resolve()),
                "Assessment reports must be outside the immutable input bundle")
        result = assess(args.input, args.trusted_root)
        publish(result, args.output)
    except (ValueError, KeyError, TypeError, OSError) as exc:
        parser.exit(2, f"Business coverage assessment failed: {exc}\n")
    print(json.dumps(result["metrics"], indent=2))
    return 0


if __name__ == "__main__":
    sys.exit(main())
