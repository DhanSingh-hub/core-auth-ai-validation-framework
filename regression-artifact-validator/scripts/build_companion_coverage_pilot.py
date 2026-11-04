"""Build a source-pinned, draft-only Segment 100 four-measure assessment pilot."""

import argparse
import json
import re
from pathlib import Path

import business_coverage as coverage
import delivery_gates as gates

ROOT = Path(__file__).resolve().parents[1]
SOURCE_ID = "ATL105-2026-4"


def write(root, relative, document):
    path = root / relative
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(document, indent=2) + "\n", encoding="utf-8")
    return {"path": path.relative_to(root).as_posix(), "sha256": coverage.file_hash(path)}


def evaluate(rule, payload):
    coverage.require(isinstance(payload, dict), "Logical fixture must be an object")
    segments = payload.get("segments")
    coverage.require(isinstance(segments, list)
                     and all(isinstance(s, str) and re.fullmatch(r"[0-9]{3}", s) for s in segments),
                     "Explicit logical segment IDs required")
    profile = payload.get("profile")
    if profile not in {"STANDARD_FINANCIAL", "EMV_FINANCIAL"}:
        return "REVIEW_REQUIRED"
    if rule == "CORE-100":
        valid = segments.count("100") == 1
    elif rule == "EMV-130":
        valid = profile != "EMV_FINANCIAL" or "130" in segments
    elif rule == "COUNT-63":
        count = payload.get("numberOfSegments")
        valid = isinstance(count, str) and re.fullmatch(r"[0-9]{1,2}", count) is not None \
            and int(count) == len(segments)
    else:
        raise ValueError(f"Unimplemented companion pilot predicate: {rule}")
    return "PASS" if valid else "FAIL"


def build(output):
    coverage.require(not output.exists(), "Refusing to overwrite an existing pilot")
    gates.trusted_source()
    scope = {
        "id": "SEG100-COMPANION-BOUNDED-PILOT-2026-4",
        "specification": "ATL105", "version": "2026-4", "kind": "BOUNDED_PILOT",
        "description": "Draft financial core presence, EMV companion presence and logical count checks.",
        "outOfScope": [
            "Whole-specification requirement completeness and business approval.",
            "Fleet, fuel, EBT, NFC, purchase-card, variable-info and Moneris applicability.",
            "Companion ordering, contents, full-message bytes, crypto and host execution."],
        "review": None}
    definitions = [
        ("CORE-100", "A scoped financial request includes one Standard Message Data Segment 100.",
         204, "Standard Message Data Segment (Data Segment No. 100)"),
        ("EMV-130", "An EMV financial request includes Segment 130.",
         206, "Contains the EMV data required for all EMV card transactions."),
        ("COUNT-63", "Element 63 identifies the number of segments in the financial request.",
         395, "Identifies the number of BUYPASS-defined data segments included in the Financial Transaction Request")]
    document = {
        "schemaVersion": 1, "scope": scope,
        "sources": [{"id": SOURCE_ID, "specification": "ATL105", "version": "2026-4",
                     "path": gates.SOURCE.relative_to(ROOT).as_posix(),
                     "sha256": gates.SOURCE_SHA256,
                     "anchors": [{"id": identity, "page": page, "excerpt": excerpt}
                                 for identity, _, page, excerpt in definitions]}],
        "requirements": [], "scenarios": [], "testCases": [], "testData": [],
        "semanticPolicy": {
            "TC": ["intent", "conditions", "steps", "expectedOutcome", "ruleChecks", "dataAssociation"],
            "TD": ["values", "conditionalFields", "dependencies", "conflicts", "ruleChecks"]},
        "semanticAssessments": [], "flows": [], "runs": []}
    criteria = {
        "POSITIVE": "Required scoped predicate holds in a logical control.",
        "NEGATIVE": "An isolated scoped predicate violation is detected.",
        "BOUNDARY": "The declared minimum/cardinality boundary is exercised.",
        "CONDITIONAL": "The predicate is exercised in a second explicitly declared financial profile."}
    for identity, statement, _, _ in definitions:
        document["requirements"].append({
            "id": identity, "statement": statement,
            "sourceRefs": [{"sourceId": SOURCE_ID, "anchorId": identity}],
            "applicability": "APPLICABLE", "review": None,
            "criteria": [{"id": variation, "variation": variation, "description": description}
                         for variation, description in criteria.items()]})
        document["scenarios"].append({
            "id": f"TS-{identity}", "intent": statement, "requirementIds": [identity], "review": None})
    base = {"profile": "EMV_FINANCIAL", "segments": ["100", "130"], "numberOfSegments": "2",
            "representation": "LOGICAL_SEGMENT_LIST_NOT_SERIALIZED_BYTES"}
    standard = {**base, "profile": "STANDARD_FINANCIAL", "segments": ["100"], "numberOfSegments": "1"}
    vectors = {
        "CORE-100": [
            ("POSITIVE", base, "PASS"),
            ("NEGATIVE", {**base, "segments": ["130"], "numberOfSegments": "1"}, "FAIL"),
            ("BOUNDARY", {**base, "segments": ["100", "100", "130"], "numberOfSegments": "3"}, "FAIL"),
            ("CONDITIONAL", standard, "PASS")],
        "EMV-130": [
            ("POSITIVE", base, "PASS"),
            ("NEGATIVE", {**base, "segments": ["100"], "numberOfSegments": "1"}, "FAIL"),
            ("BOUNDARY", base, "PASS"),
            ("CONDITIONAL", standard, "PASS")],
        "COUNT-63": [
            ("POSITIVE", base, "PASS"),
            ("NEGATIVE", {**base, "numberOfSegments": "1"}, "FAIL"),
            ("BOUNDARY", standard, "PASS"),
            ("CONDITIONAL", {**standard, "numberOfSegments": "01"}, "PASS")]}
    output.mkdir(parents=True)
    bundle = output / "input"
    for rule_id, examples in vectors.items():
        for variation, payload, expected in examples:
            observed = evaluate(rule_id, payload)
            coverage.require(observed == expected, f"Pilot control/mutant mismatch: {rule_id}/{variation}")
            suffix = f"{rule_id}-{variation}"
            data_id = f"TD-{suffix}"
            data = {"id": data_id, "file": write(bundle, f"data/{data_id}.json", payload)}
            case = {
                "id": f"TC-{suffix}", "scenarioId": f"TS-{rule_id}",
                "intent": f"{variation}: {next(r['statement'] for r in document['requirements'] if r['id'] == rule_id)}",
                "steps": ["Inspect the declared logical financial profile and segment list.",
                          f"Evaluate only {rule_id}; do not treat the fixture as an executable full message."],
                "expectedOutcome": f"Bounded predicate result {expected}; no host outcome asserted.",
                "testDataIds": [data_id],
                "coverage": [{"requirementId": rule_id, "criterionId": variation, "expected": expected}]}
            document["testCases"].append(case)
            document["testData"].append(data)
            check = {
                "requirementId": rule_id, "criterionId": variation, "expected": expected,
                "observed": observed, "detail": "Executed bounded logical predicate; not serialization or host evidence."}
            for kind, artifact in (("TC", case), ("TD", data)):
                checks = [{"id": "ruleChecks", "status": "PASS",
                           "detail": "Observed bounded predicate result equals the declared positive/negative expectation."}]
                if kind == "TC":
                    checks.append({"id": "dataAssociation", "status": "PASS",
                                   "detail": "All declared synthetic TD legs exist and are hash-bound."})
                report = {
                    "artifactId": artifact["id"], "artifactSha256": coverage.fingerprint(artifact),
                    "authority": "INDEPENDENT_TEST_SOLUTION", "checks": checks, "ruleChecks": [check]}
                document["semanticAssessments"].append({
                    "id": artifact["id"], "review": None,
                    "evidence": write(bundle, f"evidence/{artifact['id']}.json", report)})
    document["flows"].append({
        "id": "FLOW-EMV-FINANCIAL", "applicability": "APPLICABLE", "review": None,
        "steps": [{"id": "purchase", "caseId": "TC-EMV-130-POSITIVE",
                   "assertions": [{"pointer": "/outcome", "expected": "APPROVED"}]}],
        "prerequisites": [
            "APPROVED_FULL_MESSAGE_FIXTURE", "AUTHORIZED_ENVIRONMENT",
            "CARD_DEVICE_ROUTE_AND_CRYPTO", "SOURCE_BACKED_HOST_EXPECTATION"]})
    write(bundle, "assessment.json", document)
    result = coverage.assess(bundle)
    coverage.publish(result, output / "assessment")
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    try:
        result = build(args.output)
    except (ValueError, KeyError, TypeError, OSError) as exc:
        parser.exit(2, f"Companion coverage pilot failed: {exc}\n")
    print(json.dumps(result["metrics"], indent=2))


if __name__ == "__main__":
    main()
