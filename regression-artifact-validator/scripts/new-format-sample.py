"""Build and validate a bounded BR/TS/TC/TD example in the BA business-context format."""

import argparse
import copy
import importlib.util
import json
import re
from pathlib import Path


HELPER_PATH = Path(__file__).with_name("build-all-network-requirements-review.py")
SPEC = importlib.util.spec_from_file_location("network_review", HELPER_PATH)
assert SPEC is not None and SPEC.loader is not None
network = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(network)
shared = network.shared
PACK = shared.PACK
OUTPUT = PACK / "test-output" / "test-solution-independent-review" / "new-format-sequence-sample-v1"
ANCHOR = {"specification": "ATL105", "version": "2026-3", "section": "13.2",
          "segment": "100", "element": "86", "rule": "financial-sequence-six-digits-range"}
CLAIM = {"subject": "Segment100.Element86", "operator": "asciiDigitsRange",
         "width": 6, "minimum": 1, "maximum": 999999, "modality": "must",
         "conditions": {"messageFamily": "STANDARD_FINANCIAL_REQUEST"},
         "exceptions": ["CAPK_FILE_LOAD_PROFILE_OUT_OF_SCOPE"]}
EVIDENCE = {
    "sequence": {"path": "docs/specs/extracted_text.txt", "section": "13.2 Element 86",
                 "anchor": "Number: 86 Name: Sequence Number"},
    "width": {"path": "docs/specs/extracted_text.txt", "section": "13.2 Element 86 representation",
              "anchor": "Representation: Fixed length of six digits"},
    "range": {"path": "docs/specs/extracted_text.txt", "section": "13.2 Element 86 valid values",
              "anchor": "Valid Codes/Values: 000001\u2013999999"},
}


def load(path: Path) -> dict:
    def pairs(items):
        result = {}
        for key, value in items:
            if key in result:
                raise ValueError(f"Duplicate JSON key: {key}")
            result[key] = value
        return result

    return json.loads(path.read_text(encoding="utf-8-sig"), object_pairs_hook=pairs,
                      parse_constant=lambda value: (_ for _ in ()).throw(ValueError(f"Invalid JSON number: {value}")))


def sample() -> dict:
    context = {
        "platform": "BUYPASS", "specification": "ATL105", "version": "2026-3",
        "cardProgram": "CREDIT", "commercialUsage": "UNRESOLVED",
        "groupId": "MASTERCARD", "network": "MASTERCARD", "genericCategory": None,
        "processingProfile": "CREDIT", "transactionCategory": "SALE",
        "transactionCodes": ["0"], "candidateCardCodes": ["020"],
        "messageFamily": "STANDARD_FINANCIAL_REQUEST", "flowId": "SINGLE_STEP_PURCHASE",
        "lifecycleRole": "ORIGINAL", "cardPresence": "PRESENT", "entryMode": "CONTACT_CHIP",
        "channel": "POS", "eligibility": "REVIEW_REQUIRED",
        "mappingBasis": "CONDITIONAL_REVIEW_SELECTION_NOT_APPROVED_COMPATIBILITY",
    }
    br = {"id": "SAMPLE-BR-SEQ", "statement": "For the scoped financial request, Sequence Number "
          "(Element 86) must be exactly six ASCII digits with value 000001 through 999999. "
          "This does not establish Mastercard eligibility, cross-transaction uniqueness or lifecycle continuity.",
          "claim": copy.deepcopy(CLAIM)}
    scenarios = [
        {"id": "SAMPLE-TS-VALID", "requirementIds": [br["id"]],
         "intent": "Accept the source-defined lower and upper sequence boundaries.",
         "preconditions": ["Synthetic, isolated Segment 100 field observation; not a complete host message."]},
        {"id": "SAMPLE-TS-INVALID", "requirementIds": [br["id"]],
         "intent": "Reject zero, wrong width, nonnumeric or missing sequence.",
         "preconditions": ["One field predicate only; no claim that the rest of a financial message is valid."]},
    ]
    cases, data = [], []
    for suffix, value, expected in (
        ("MIN", "000001", "PASS"), ("MAX", "999999", "PASS"), ("ZERO", "000000", "FAIL"),
        ("WIDTH", "12345", "FAIL"), ("NONNUMERIC", "12A456", "FAIL"), ("MISSING", None, "FAIL"),
    ):
        case_id = f"SAMPLE-TC-{suffix}"
        cases.append({
            "id": case_id, "scenarioIds": [scenarios[0 if expected == "PASS" else 1]["id"]],
            "steps": ["Read Element 86 from the logical Segment 100 observation.",
                      "Apply the independent six-ASCII-digit and inclusive-range predicate.",
                      f"Expect local field validation {expected}; do not infer a host response."],
            "expectedOutcome": expected,
            "assertions": [{"requirementId": br["id"], "rule": ANCHOR["rule"], "expectedResult": expected}],
        })
        segment = {} if value is None else {"sequenceNumber": value}
        data.append({"id": f"SAMPLE-TD-{suffix}", "testCaseIds": [case_id],
                     "expectedValidation": expected, "readiness": "LOGICAL_HARNESS_ONLY",
                     "payload": {"standardSegment": segment}, "response": None,
                     "scope": "ISOLATED_FIELD_OBSERVATION_NOT_CONVERTER_READY"})
    for row in [br, *scenarios, *cases, *data]:
        row.update({"businessContext": copy.deepcopy(context), "sourceAnchors": [copy.deepcopy(ANCHOR)],
                    "status": "DRAFT_REVIEW_REQUIRED"})
    return {
        "schemaVersion": 1, "format": "BA_BUSINESS_CONTEXT_REVIEW_V1",
        "manifest": {"packageId": "TEST-SAMPLE-NEW-FORMAT-SEQUENCE-V1",
                     "producer": "INDEPENDENT_TEST_SOLUTION", "specification": "ATL105",
                     "specificationVersion": "2026-3", "strictExecutionContract": False},
        "businessRequirements": [br], "testScenarios": scenarios, "testCases": cases, "testData": data,
        "limitations": ["Illustrative declared Mastercard context, not resolved BIN/processor evidence.",
                        "No network eligibility, cryptography, full wire, host, settlement or certification evidence.",
                        "Local field checks only; not a Java execution-ready canonical package."],
    }


def valid_sequence(payload: dict) -> bool:
    value = payload["standardSegment"].get("sequenceNumber")
    return isinstance(value, str) and re.fullmatch(r"[0-9]{6}", value) is not None and 1 <= int(value) <= 999999


def validate(document: dict, root: Path = PACK) -> tuple[dict, dict]:
    if document.get("schemaVersion") != 1 or document.get("format") != "BA_BUSINESS_CONTEXT_REVIEW_V1":
        raise ValueError("Unsupported new-format schema/version")
    manifest = document["manifest"]
    if (manifest["specification"] != "ATL105" or manifest["specificationVersion"] != "2026-3"
            or manifest["strictExecutionContract"] is not False
            or not isinstance(manifest["packageId"], str) or not manifest["packageId"].strip()
            or manifest["producer"] not in {"INDEPENDENT_TEST_SOLUTION", "AI_SOLUTION"}):
        raise ValueError("Invalid manifest or unsupported execution-ready claim")
    evidence = shared.resolve_sources(EVIDENCE, root)
    collections = {}
    all_ids = set()
    base_context = None
    for kind in ("businessRequirements", "testScenarios", "testCases", "testData"):
        rows = document[kind]
        if not isinstance(rows, list) or not rows:
            raise ValueError(f"Missing/empty {kind}")
        index = shared.unique_index(rows, "id")
        for row in index.values():
            identifier = row["id"]
            if not isinstance(identifier, str) or not identifier.strip() or identifier in all_ids:
                raise ValueError(f"Invalid/cross-kind duplicate ID: {identifier}")
            all_ids.add(identifier)
            network.validate_business_context(row["businessContext"])
            if row["status"] != "DRAFT_REVIEW_REQUIRED":
                raise ValueError(f"Unsupported status promotion: {identifier}")
            if not row["sourceAnchors"] or any(not isinstance(item, dict) for item in row["sourceAnchors"]):
                raise ValueError(f"Missing/invalid source anchors: {identifier}")
            if base_context is None:
                base_context = row["businessContext"]
            if row["businessContext"] != base_context:
                raise ValueError(f"Cross-chain context mismatch: {identifier}")
        collections[kind] = index
    brs, scenarios, cases, data = (collections[key] for key in
                                 ("businessRequirements", "testScenarios", "testCases", "testData"))
    used_brs, used_scenarios, used_cases = set(), set(), set()

    def links(row, key, target):
        identifiers = row[key]
        if (not isinstance(identifiers, list) or not identifiers
                or any(not isinstance(identifier, str) for identifier in identifiers)
                or len(set(identifiers)) != len(identifiers) or set(identifiers) - target.keys()):
            raise ValueError(f"Invalid/unresolved {key}: {row['id']}")
        return set(identifiers)

    for row in brs.values():
        if not row["statement"].strip() or not isinstance(row["claim"], dict):
            raise ValueError(f"Missing BR statement/claim: {row['id']}")
    for row in scenarios.values():
        used_brs.update(links(row, "requirementIds", brs))
        if (not row["intent"].strip() or not isinstance(row["preconditions"], list)
                or not row["preconditions"] or any(not text.strip() for text in row["preconditions"])):
            raise ValueError(f"Scenario lacks intent/preconditions: {row['id']}")
        for identifier in row["requirementIds"]:
            if row["sourceAnchors"] != brs[identifier]["sourceAnchors"]:
                raise ValueError(f"Source-anchor discontinuity: {row['id']}")
    for row in cases.values():
        selected = links(row, "scenarioIds", scenarios)
        used_scenarios.update(selected)
        if (row["expectedOutcome"] not in {"PASS", "FAIL"} or not row["steps"]
                or any(not step.strip() for step in row["steps"]) or not row["assertions"]):
            raise ValueError(f"Incomplete case: {row['id']}")
        linked_brs = {identifier for key in selected for identifier in scenarios[key]["requirementIds"]}
        if {assertion["requirementId"] for assertion in row["assertions"]} != linked_brs:
            raise ValueError(f"Case assertions do not cover linked BRs: {row['id']}")
        for assertion in row["assertions"]:
            if assertion["expectedResult"] != row["expectedOutcome"]:
                raise ValueError(f"Assertion expectation mismatch: {row['id']}")
        if any(row["sourceAnchors"] != scenarios[key]["sourceAnchors"] for key in selected):
            raise ValueError(f"Source-anchor discontinuity: {row['id']}")
    for row in data.values():
        selected = links(row, "testCaseIds", cases)
        used_cases.update(selected)
        if (row["readiness"] != "LOGICAL_HARNESS_ONLY" or row["response"] is not None
                or row["scope"] != "ISOLATED_FIELD_OBSERVATION_NOT_CONVERTER_READY"):
            raise ValueError(f"TD exceeds bounded harness scope: {row['id']}")
        if not isinstance(row["payload"], dict) or not isinstance(row["payload"].get("standardSegment"), dict):
            raise ValueError(f"TD lacks a logical Segment 100 payload: {row['id']}")
        if set(row["payload"]) != {"standardSegment"} or set(row["payload"]["standardSegment"]) - {"sequenceNumber"}:
            raise ValueError(f"Unsupported payload fields; this harness only assesses Element 86: {row['id']}")
        if any(row["expectedValidation"] != cases[key]["expectedOutcome"] for key in selected):
            raise ValueError(f"TC/TD expectation mismatch: {row['id']}")
        if any(row["sourceAnchors"] != cases[key]["sourceAnchors"] for key in selected):
            raise ValueError(f"Source-anchor discontinuity: {row['id']}")
    if used_brs != brs.keys() or used_scenarios != scenarios.keys() or used_cases != cases.keys():
        raise ValueError("Orphan BR/TS/TC artefacts")

    supported_context = (
        base_context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST"
        and base_context["cardProgram"] == "CREDIT"
        and base_context["processingProfile"] == "CREDIT"
        and base_context["transactionCategory"] == "SALE"
        and base_context["transactionCodes"] == ["0"]
        and base_context["candidateCardCodes"] == ["020"]
        and base_context["flowId"] == "SINGLE_STEP_PURCHASE"
        and base_context["lifecycleRole"] == "ORIGINAL"
    )
    supported_claims = all(
        row["sourceAnchors"] == [ANCHOR] and shared.json_text(row["claim"]) == shared.json_text(CLAIM)
        for row in brs.values()
    )
    supported_assertions = all(assertion["rule"] == ANCHOR["rule"]
                               for row in cases.values() for assertion in row["assertions"])
    supported = supported_context and supported_claims and supported_assertions
    observations = []
    for row in data.values():
        observed = ("PASS" if valid_sequence(row["payload"]) else "FAIL") if supported else "NOT_ASSESSED"
        observations.append({
            "testDataId": row["id"], "testCaseIds": row["testCaseIds"],
            "expectedValidation": row["expectedValidation"], "observedFieldValidation": observed,
            "expectationAgreement": ("PASS" if observed == row["expectedValidation"] else "FAIL")
            if supported else "NOT_ASSESSED",
            "scope": "ELEMENT_86_LOCAL_PREDICATE_ONLY",
        })
    failures = sum(row["expectationAgreement"] == "FAIL" for row in observations)
    assessment = {
        "structuralTraceability": "PASS",
        "supportedStructuredClaim": "PASS" if supported else "NOT_ASSESSED",
        "testExpectationAgreement": ("FAIL" if failures else "PASS") if supported else "NOT_ASSESSED",
        "semanticProseEquivalence": "NOT_ASSESSED", "networkEligibility": "REVIEW_REQUIRED",
        "completeMessageAndWire": "NOT_ASSESSED", "hostOutcome": "NOT_ASSESSED",
        "coveragePromotion": False, "certified": False, "overallStatus": "REVIEW_REQUIRED",
        "counts": {key: len(value) for key, value in collections.items()},
        "assessedTdCount": len(observations) if supported else 0,
        "mismatchedExpectationCount": failures, "observations": observations,
        "independentRule": CLAIM, "independentSourceEvidence": evidence,
        "limits": ["Exact structured-claim equality is not proof of prose meaning or source interpretation.",
                   "Only the declared credit sale Element 86 profile is implemented; unknown claims/contexts remain NOT_ASSESSED.",
                   "Negative field observations are not whole-message valid controls and do not predict issuer declines."],
    }
    normalized = copy.deepcopy(document)
    normalized["adapter"] = {
        "name": "BA_BUSINESS_CONTEXT_REVIEW_V1_TO_REVIEW_ENVELOPE",
        "canonicalExecutionReady": False,
        "policy": "Producer IDs, claims, contexts and payloads preserved. Do not submit as a strict execution-ready canonical package.",
    }
    return assessment, normalized


def outputs(document: dict, input_hash: str) -> dict[str, str]:
    assessment, normalized = validate(document)
    assessment["inputSha256"] = input_hash
    assessment["validatorSha256"] = shared.fingerprint(Path(__file__).read_bytes())
    assessment["helperHashes"] = {
        HELPER_PATH.name: shared.fingerprint(HELPER_PATH.read_bytes()),
        network.HELPER_PATH.name: shared.fingerprint(network.HELPER_PATH.read_bytes()),
    }
    return {"validation-report.json": shared.json_text(assessment),
            "normalized-review-envelope.json": shared.json_text(normalized)}


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", type=Path, help="AI/new-format JSON; read unchanged, never used as the oracle")
    parser.add_argument("--output", type=Path, help="New output directory for external input validation")
    parser.add_argument("--check", action="store_true", help="Read-only check of the published sample")
    args = parser.parse_args()
    if args.input:
        if args.check or args.output is None:
            parser.error("--input requires a new --output directory and cannot be combined with --check")
        if args.output.exists():
            parser.error("External validation requires a new output directory to preserve prior evidence")
        raw = args.input.read_bytes()
        document = load(args.input)
        files = outputs(document, shared.fingerprint(raw))
        files["input-snapshot.json"] = raw.decode("utf-8-sig")
        network.publish(files, args.output, False)
        report = json.loads(files["validation-report.json"])
        print("Local field assessment:", report["testExpectationAgreement"], "; overall: REVIEW_REQUIRED")
        if report["testExpectationAgreement"] != "PASS":
            raise SystemExit(1 if report["testExpectationAgreement"] == "FAIL" else 2)
    else:
        if args.output:
            parser.error("--output is only supported with --input")
        document = sample()
        sample_text = shared.json_text(document)
        files = {"sample-br-ts-tc-td.json": sample_text,
                 **outputs(document, shared.fingerprint(sample_text.encode("utf-8")))}
        network.publish(files, OUTPUT, args.check)
        print("Sample: 1 BR, 2 TS, 6 concrete logical TCs and 6 synthetic field TDs; not host-executable.")


if __name__ == "__main__":
    main()
