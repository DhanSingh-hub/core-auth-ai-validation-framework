"""Build a hash-bound, per-leg review ledger for every linked AI multileg case."""

import argparse
import collections
import csv
import gzip
import hashlib
import json
import os
import re
import shutil
from pathlib import Path


def native_path(path):
    value = os.path.abspath(os.fspath(path))
    if os.name == "nt" and not value.startswith("\\\\?\\"):
        if value.startswith("\\\\"):
            return "\\\\?\\UNC\\" + value[2:]
        return "\\\\?\\" + value
    return value


def sha256(path):
    digest = hashlib.sha256()
    with open(native_path(path), "rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    with open(native_path(path), "r", encoding="utf-8-sig") as stream:
        return json.load(stream)


def read_csv(path):
    csv.field_size_limit(100_000_000)
    with open(native_path(path), "r", encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def jsonl(path):
    with open(native_path(path), "r", encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def gzip_jsonl(path):
    with gzip.open(native_path(path), "rt", encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def normalize(value):
    return re.sub(r"[^a-z0-9]", "", str(value).lower())


def get_value(mapping, name):
    wanted = normalize(name)
    if not isinstance(mapping, dict):
        return None
    for key, value in mapping.items():
        if normalize(key) == wanted:
            return value
    return None


def scalar_text(value):
    return str(value) if isinstance(value, (str, int, float)) else ""


def segment_observations(payload):
    found = []

    def visit(node, role="REQUEST", message_family=""):
        if isinstance(node, dict):
            for key, value in node.items():
                key_text = str(key)
                key_normalized = normalize(key_text)
                child_role = "RESPONSE" if "response" in key_normalized else role
                child_family = key_text if "request" in key_normalized or "response" in key_normalized else message_family
                if key_normalized in {"standardsegment", "segment100"} and isinstance(value, dict):
                    prompt = get_value(value, "PromptCode")
                    found.append({
                        "role": child_role,
                        "messageFamily": message_family,
                        "segmentType": scalar_text(get_value(value, "SegmentType")),
                        "transactionType": scalar_text(get_value(prompt, "TransactionType")),
                        "cardType": scalar_text(get_value(prompt, "CardType")),
                        "sequenceNumber": scalar_text(get_value(value, "SequenceNumber")),
                        "approvalNumber": scalar_text(get_value(value, "ApprovalNumber")),
                        "responseCodePresent": get_value(value, "ResponseCode") is not None,
                    })
                else:
                    visit(value, child_role, child_family)
        elif isinstance(node, list):
            for value in node:
                visit(value, role, message_family)

    visit(payload)
    return found


def classify_pair(original_code, follow_up_code):
    auth = {"3", "5", "B"}
    purchases = {"0", "4", "6"}
    if original_code in auth and follow_up_code == "0":
        return "AUTHORIZATION_COMPLETION", "SAME"
    if original_code in auth and follow_up_code == "S":
        return "AUTHORIZATION_CANCELLATION", "SAME"
    if original_code in auth and follow_up_code == "8":
        return "INVALID_AUTHORIZATION_ONLY_TO_PURCHASE_REVERSAL", "SAME"
    if original_code in {"0", "4"} and follow_up_code == "8":
        return "PURCHASE_REVERSAL_OR_VOID", "SAME"
    if original_code == "6" and follow_up_code == "C":
        return "MAIL_PHONE_REVERSAL_OR_VOID", "SAME"
    if original_code == "7" and follow_up_code == "U":
        return "RETURN_VOID", "SAME"
    if original_code in purchases and follow_up_code == "Z":
        return "TIMEOUT_REVERSAL", "SAME"
    if original_code == "Z" and follow_up_code in purchases:
        return "POST_TIMEOUT_NEXT_TRANSACTION", "DIFFERENT"
    return "UNCLASSIFIED_TRANSACTION_PATH", "UNKNOWN"


def sequence_binding(original, follow_up, expected):
    left = original.get("sequenceNumber", "")
    right = follow_up.get("sequenceNumber", "")
    if not left or not right:
        return "SEQUENCE_NOT_PROVIDED"
    if len(left) != 6 or len(right) != 6 or not left.isdigit() or not right.isdigit():
        return "SEQUENCE_FORMAT_REVIEW_REQUIRED"
    same = left == right
    if expected == "SAME":
        return "OBSERVED_MATCH_REVIEW_REQUIRED" if same else "OBSERVED_MISMATCH_REVIEW_REQUIRED"
    if expected == "DIFFERENT":
        return "OBSERVED_DIFFERENT_REVIEW_REQUIRED" if not same else "OBSERVED_UNEXPECTED_REUSE_REVIEW_REQUIRED"
    return "OBSERVED_EQUALITY_NO_SOURCE_POLICY"


def actual_response_status(response_objects, host_attested=False):
    if not response_objects:
        return "ACTUAL_RESPONSE_NOT_SUPPLIED"
    if not host_attested:
        return "RESPONSE_OBJECT_PRESENT_HOST_PROVENANCE_UNVERIFIED"
    return "HOST_RESPONSE_EVIDENCE_PRESENT"


def approval_binding_status(flow_kind, response_objects, host_attested=False, approval_equal=None):
    if flow_kind not in {"AUTHORIZATION_COMPLETION", "AUTHORIZATION_CANCELLATION"}:
        return "NOT_APPLICABLE_OR_UNCLASSIFIED"
    if not response_objects:
        return "BLOCKED_ACTUAL_AUTHORIZATION_RESPONSE_MISSING"
    if not host_attested:
        return "BLOCKED_RESPONSE_PROVENANCE_UNVERIFIED"
    if approval_equal is None:
        return "ACTUAL_RESPONSE_APPROVAL_UNAVAILABLE"
    return "OBSERVED_APPROVAL_MATCH_REVIEW_REQUIRED" if approval_equal else "OBSERVED_APPROVAL_MISMATCH_REVIEW_REQUIRED"


def logical_step_index(path, fallback):
    match = re.search(r"\.step(\d+)(?:\.|$)", path, re.IGNORECASE)
    return int(match.group(1)) if match else fallback


def ensure_within_root(root, relative):
    root_text = os.path.abspath(os.fspath(root))
    candidate = os.path.abspath(os.path.join(root_text, relative.replace("/", os.sep)))
    if os.path.commonpath([root_text, candidate]).lower() != root_text.lower():
        raise ValueError("Payload path escapes the frozen delivery root")
    return Path(candidate)


def write_csv(path, rows):
    rows = list(rows)
    if not rows:
        raise ValueError("Refusing to write an empty review ledger")
    fields = list(dict.fromkeys(key for row in rows for key in row))
    with open(native_path(path), "w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (dict, list)) else value
                             for key, value in row.items()})


def build_steps(case, root, inventory, input_hashes):
    grouped = collections.defaultdict(list)
    unresolved = []
    for ordinal, observation in enumerate(case.get("payloadObservations", []), start=1):
        relative = observation.get("path")
        if not relative:
            unresolved.append({"path": "", "status": "PATH_NOT_DECLARED"})
            continue
        step_index = logical_step_index(relative, ordinal)
        expected_hash = observation.get("sha256")
        frozen = inventory.get(relative)
        if observation.get("state") != "HASH_VERIFIED_OBSERVATIONS_ONLY" or not expected_hash:
            unresolved.append({"path": relative, "status": observation.get("state") or "HASH_NOT_DECLARED"})
            continue
        if frozen is None:
            unresolved.append({"path": relative, "status": "ABSENT_FROM_FROZEN_INVENTORY"})
            continue
        if frozen.get("sha256") != expected_hash:
            raise ValueError("Observation hash differs from frozen inventory: " + relative)
        payload_path = ensure_within_root(root, relative)
        actual_hash = sha256(payload_path)
        if actual_hash != expected_hash:
            raise ValueError("Frozen flow payload changed: " + relative)
        input_hashes[relative] = actual_hash
        payload = read_json(payload_path)
        segments = segment_observations(payload)
        response_segments = [segment for segment in segments if segment["role"] == "RESPONSE"]
        request_segments = [segment for segment in segments if segment["role"] != "RESPONSE"]
        filename = Path(relative).name
        role_match = re.search(r"\.step\d+\.([^.]+)", filename, re.IGNORECASE)
        leg_name = role_match.group(1) if role_match else "UNNAMED_STEP"
        key = (step_index, actual_hash)
        grouped[key].append({
            "relativePath": relative,
            "filename": filename,
            "sha256": actual_hash,
            "legName": leg_name,
            "requestSegments": request_segments,
            "responseSegments": response_segments,
        })
    steps = []
    for (step_index, digest), copies in sorted(grouped.items()):
        representative = copies[0]
        requests = representative["requestSegments"]
        responses = representative["responseSegments"]
        request = requests[0] if len(requests) == 1 else {}
        response = responses[0] if len(responses) == 1 else {}
        steps.append({
            "stepIndex": step_index,
            "legName": representative["legName"],
            "sha256": digest,
            "physicalCopyCount": len(copies),
            "physicalPaths": sorted({copy["relativePath"] for copy in copies}),
            "requestSegmentCount": len(requests),
            "responseSegmentCount": len(responses),
            "segmentAmbiguity": len(requests) > 1 or len(responses) > 1,
            "transactionType": request.get("transactionType", ""),
            "cardType": request.get("cardType", ""),
            "sequenceNumber": request.get("sequenceNumber", ""),
            "sequenceDigits": len(request.get("sequenceNumber", "")),
            "approvalNumber": request.get("approvalNumber", ""),
            "approvalChars": len(request.get("approvalNumber", "")),
            "responseObjectPresent": bool(responses),
            "responseApprovalPresent": bool(response.get("approvalNumber", "")),
            "responseSequencePresent": bool(response.get("sequenceNumber", "")),
            "responseCodePresent": bool(response.get("responseCodePresent")),
        })
    return steps, unresolved


def case_binding(case, steps, unresolved):
    sorted_steps = sorted(steps, key=lambda row: row["stepIndex"])
    pairs = []
    for original, follow_up in zip(sorted_steps, sorted_steps[1:]):
        if original["segmentAmbiguity"] or follow_up["segmentAmbiguity"]:
            flow_kind, sequence_policy = "AMBIGUOUS_SEGMENT_BINDING", "UNKNOWN"
        else:
            flow_kind, sequence_policy = classify_pair(original["transactionType"], follow_up["transactionType"])
        sequence_status = sequence_binding(original, follow_up, sequence_policy)
        response_status = actual_response_status(original["responseObjectPresent"], host_attested=False)
        approval_status = approval_binding_status(flow_kind, original["responseObjectPresent"], host_attested=False)
        pairs.append({
            "originalStepIndex": original["stepIndex"],
            "followUpStepIndex": follow_up["stepIndex"],
            "originalType": original["transactionType"],
            "followUpType": follow_up["transactionType"],
            "flowKindCandidate": flow_kind,
            "sequencePolicy": sequence_policy,
            "sequenceBindingObservation": sequence_status,
            "actualResponseEvidenceStatus": response_status,
            "approvalBindingStatus": approval_status,
            "semanticStatus": "NOT_ASSESSED",
        })
    return pairs


def self_test():
    assert classify_pair("3", "0") == ("AUTHORIZATION_COMPLETION", "SAME")
    assert classify_pair("3", "S") == ("AUTHORIZATION_CANCELLATION", "SAME")
    assert classify_pair("3", "8")[0] == "INVALID_AUTHORIZATION_ONLY_TO_PURCHASE_REVERSAL"
    assert classify_pair("0", "8")[0] == "PURCHASE_REVERSAL_OR_VOID"
    assert classify_pair("Z", "0") == ("POST_TIMEOUT_NEXT_TRANSACTION", "DIFFERENT")
    assert sequence_binding({"sequenceNumber": "123456"}, {"sequenceNumber": "123456"}, "SAME") == "OBSERVED_MATCH_REVIEW_REQUIRED"
    assert sequence_binding({"sequenceNumber": "123456"}, {"sequenceNumber": "123457"}, "SAME") == "OBSERVED_MISMATCH_REVIEW_REQUIRED"
    assert approval_binding_status("AUTHORIZATION_COMPLETION", False) == "BLOCKED_ACTUAL_AUTHORIZATION_RESPONSE_MISSING"
    assert approval_binding_status("AUTHORIZATION_COMPLETION", True, host_attested=False) == "BLOCKED_RESPONSE_PROVENANCE_UNVERIFIED"
    sample = {"Financial Request": {"Standard Segment": {
        "SegmentType": "100", "PromptCode": {"TransactionType": "3", "CardType": "020"},
        "SequenceNumber": "123456", "ApprovalNumber": ""}}}
    segments = segment_observations(sample)
    assert len(segments) == 1 and segments[0]["transactionType"] == "3"
    assert "123456" == segments[0]["sequenceNumber"]
    print("PASS: source path classification, sequence observation, actual-response gate, and Segment 100 extraction")


def run(review_dir, audit_dir, output_dir):
    if output_dir.exists():
        raise ValueError("Refusing to overwrite existing review output")
    review_manifest = read_json(review_dir / "manifest.json")
    screen_dir = review_dir / "automated-chain-screen-v4"
    screen_manifest = read_json(screen_dir / "manifest.json")
    audit_summary = read_json(audit_dir / "summary.json")
    review_summary = read_json(review_dir / "summary.json")
    register_path = review_dir / "all-ai-br-review-register.csv"
    pairs_path = review_dir / "ai-test-br-ranked-review-leads.csv"
    progress_path = review_dir / "review-progress.csv"
    case_observations_path = screen_dir / "case-field-observations.jsonl.gz"
    screen_summary_path = screen_dir / "summary.json"
    inventory_path = audit_dir / "file-inventory.jsonl"
    for path, expected in [
        (register_path, review_manifest["artifacts"][register_path.name]),
        (pairs_path, review_manifest["artifacts"][pairs_path.name]),
        (progress_path, review_manifest["artifacts"][progress_path.name]),
        (case_observations_path, screen_manifest["artifacts"][case_observations_path.name]),
        (screen_summary_path, screen_manifest["artifacts"][screen_summary_path.name]),
        (inventory_path, audit_summary["fileInventorySha256"]),
    ]:
        if sha256(path) != expected:
            raise ValueError("Frozen input hash mismatch: " + str(path))
    if review_summary["population"]["aiBusinessRequirements"] != 6880:
        raise ValueError("Expected complete 6,880 AI BR register")

    register = read_csv(register_path)
    progress = {row["aiBrId"]: row for row in read_csv(progress_path)}
    ai_case_to_brs = collections.defaultdict(set)
    for row in register:
        for case_id in json.loads(row["aiCaseIds"]):
            ai_case_to_brs[case_id].add(row["aiBrId"])
    test_br_candidates = collections.defaultdict(set)
    for row in read_csv(pairs_path):
        test_br_candidates[row["aiBrId"]].add(row["testBrId"])

    inventory = {row["path"]: row for row in jsonl(inventory_path)}
    root = Path(audit_summary["runRoot"])
    flow_cases = {}
    input_hashes = {
        str(register_path): sha256(register_path),
        str(pairs_path): sha256(pairs_path),
        str(progress_path): sha256(progress_path),
        str(case_observations_path): sha256(case_observations_path),
        str(screen_summary_path): sha256(screen_summary_path),
        str(inventory_path): sha256(inventory_path),
        str(review_dir / "manifest.json"): sha256(review_dir / "manifest.json"),
        str(screen_dir / "manifest.json"): sha256(screen_dir / "manifest.json"),
        str(audit_dir / "summary.json"): sha256(audit_dir / "summary.json"),
    }
    for row in gzip_jsonl(case_observations_path):
        if row.get("metadata", {}).get("scenarioType") != "flow":
            continue
        case_id = row["caseId"]
        if case_id in flow_cases:
            raise ValueError("Duplicate flow case observation: " + case_id)
        steps, unresolved = build_steps(row, root, inventory, input_hashes)
        flow_cases[case_id] = {
            "caseId": case_id,
            "scenarioId": row.get("metadata", {}).get("scenarioId", ""),
            "expectedResponseDeclared": bool(row.get("metadata", {}).get("expectedResponsePresent")),
            "declaredExpectedResponseCodePresent": bool(row.get("metadata", {}).get("declaredResponseCode")),
            "linkedAiBrIds": sorted(ai_case_to_brs.get(case_id, set())),
            "testBrCandidateIds": sorted({test_br for br_id in ai_case_to_brs.get(case_id, set())
                                           for test_br in test_br_candidates.get(br_id, set())}),
            "steps": steps,
            "unresolvedPayloadPaths": unresolved,
            "stepPairs": case_binding(row, steps, unresolved),
            "actualHostResponseProvenance": "NOT_ESTABLISHED_BY_AI_ARTIFACTS",
            "semanticStatus": "NOT_ASSESSED",
        }

    case_rows = []
    leg_rows = []
    flow_cases_by_br = collections.defaultdict(list)
    totals = collections.Counter()
    for case_id, case in sorted(flow_cases.items()):
        totals["logicalSteps"] += len(case["steps"])
        totals["unresolvedPayloadPaths"] += len(case["unresolvedPayloadPaths"])
        for step in case["steps"]:
            totals["physicalPayloadCopies"] += step["physicalCopyCount"]
            totals["stepsWithRequestSegment100"] += step["requestSegmentCount"] == 1
            totals["responseObjectsPresent"] += step["responseObjectPresent"]
            totals["stepsWithSixDigitSequence"] += step["sequenceDigits"] == 6
            totals["stepsWithApprovalNumber"] += step["approvalChars"] == 6
            leg_rows.append({
                "caseId": case_id,
                "aiBrIds": case["linkedAiBrIds"],
                "testBrCandidateIds": case["testBrCandidateIds"],
                "stepIndex": step["stepIndex"],
                "legName": step["legName"],
                "transactionType": step["transactionType"],
                "cardType": step["cardType"],
                "requestSegmentCount": step["requestSegmentCount"],
                "sequenceDigits": step["sequenceDigits"],
                "approvalChars": step["approvalChars"],
                "responseObjectPresent": step["responseObjectPresent"],
                "responseApprovalPresent": step["responseApprovalPresent"],
                "responseSequencePresent": step["responseSequencePresent"],
                "physicalCopyCount": step["physicalCopyCount"],
                "payloadSha256": step["sha256"],
                "physicalPaths": step["physicalPaths"],
                "payloadStatus": "HASH_VERIFIED_STRUCTURE_OBSERVED",
                "actualHostResponseStatus": "RESPONSE_OBJECT_NOT_HOST_AUTHENTICATED" if step["responseObjectPresent"]
                    else "ACTUAL_RESPONSE_NOT_SUPPLIED",
                "semanticStatus": "NOT_ASSESSED",
            })
        for ai_br_id in case["linkedAiBrIds"]:
            flow_cases_by_br[ai_br_id].append(case_id)
        for pair in case["stepPairs"]:
            totals["sequenceObservedMatch"] += pair["sequenceBindingObservation"] == "OBSERVED_MATCH_REVIEW_REQUIRED"
            totals["sequenceObservedMismatch"] += pair["sequenceBindingObservation"] == "OBSERVED_MISMATCH_REVIEW_REQUIRED"
            totals["approvalBindingBlockedResponseMissing"] += pair["approvalBindingStatus"] == "BLOCKED_ACTUAL_AUTHORIZATION_RESPONSE_MISSING"
            totals["approvalBindingBlockedResponseUnverified"] += pair["approvalBindingStatus"] == "BLOCKED_RESPONSE_PROVENANCE_UNVERIFIED"
            totals["invalidAuthorizationOnlyToCode8"] += pair["flowKindCandidate"] == "INVALID_AUTHORIZATION_ONLY_TO_PURCHASE_REVERSAL"
        case_rows.append({
            "caseId": case_id,
            "scenarioId": case["scenarioId"],
            "linkedAiBrIds": case["linkedAiBrIds"],
            "testBrCandidateIds": case["testBrCandidateIds"],
            "expectedResponseDeclared": case["expectedResponseDeclared"],
            "declaredExpectedResponseIsActualEvidence": False,
            "logicalStepCount": len(case["steps"]),
            "physicalPayloadCopies": sum(step["physicalCopyCount"] for step in case["steps"]),
            "stepPairs": case["stepPairs"],
            "unresolvedPayloadPaths": case["unresolvedPayloadPaths"],
            "actualHostResponseProvenance": case["actualHostResponseProvenance"],
            "semanticStatus": "NOT_ASSESSED",
        })

    br_rows = []
    for row in register:
        ai_br_id = row["aiBrId"]
        linked_cases = json.loads(row["aiCaseIds"])
        linked_flows = sorted(set(linked_cases) & set(flow_cases))
        linked_steps = sum(len(flow_cases[case_id]["steps"]) for case_id in linked_flows)
        br_rows.append({
            "aiBrId": ai_br_id,
            "statement": row["aiStatement"],
            "aiSegments": json.loads(row["aiSegments"]),
            "aiElements": json.loads(row["aiElements"]),
            "linkedCaseCount": len(linked_cases),
            "linkedFlowCaseIds": linked_flows,
            "linkedFlowCaseCount": len(linked_flows),
            "associatedFlowLegCount": linked_steps,
            "testBrCandidateIds": sorted(test_br_candidates.get(ai_br_id, set())),
            "priorScreeningStatus": progress.get(ai_br_id, {}).get("screeningStatus", "NOT_FOUND"),
            "semanticReviewStatus": "NOT_COMPLETED",
            "semanticDisposition": "NOT_ASSESSED",
            "approvalSequenceReviewStatus": "REVIEW_REQUIRED_WHEN_FLOW_LINKED" if linked_flows else "NOT_APPLICABLE_NO_LINKED_FLOW_CASE",
            "reviewerId": "",
            "reviewedAt": "",
            "decisionEvidence": "",
        })

    output_dir.mkdir(parents=True)
    write_csv(output_dir / "all-ai-br-semantic-review-register.csv", br_rows)
    write_csv(output_dir / "flow-case-binding-review.csv", case_rows or [{"caseId": "", "semanticStatus": "NOT_ASSESSED"}])
    write_csv(output_dir / "flow-leg-evidence.csv", leg_rows or [{"caseId": "", "semanticStatus": "NOT_ASSESSED"}])
    flow_code_counts = collections.Counter()
    response_status_counts = collections.Counter()
    for case in flow_cases.values():
        for step in case["steps"]:
            flow_code_counts[step["transactionType"] or "NOT_OBSERVED"] += 1
            response_status_counts["RESPONSE_OBJECT_PRESENT"] += bool(step["responseObjectPresent"])
            response_status_counts["RESPONSE_OBJECT_ABSENT"] += not step["responseObjectPresent"]
    summary = {
        "artifact": "AI_FLOW_SEMANTIC_REVIEW_WORKLIST",
        "aiBrPopulation": len(br_rows),
        "flowCases": len(case_rows),
        "logicalFlowSteps": totals["logicalSteps"],
        "declaredPhysicalPayloadCopies": totals["physicalPayloadCopies"],
        "stepFilesHashVerified": len(input_hashes) - 9,
        "sequenceObservedMatchPairs": totals["sequenceObservedMatch"],
        "sequenceObservedMismatchPairs": totals["sequenceObservedMismatch"],
        "approvalBindingsBlockedMissingActualResponse": totals["approvalBindingBlockedResponseMissing"],
        "approvalBindingsBlockedUnverifiedResponse": totals["approvalBindingBlockedResponseUnverified"],
        "invalidAuthorizationOnlyToCode8Pairs": totals["invalidAuthorizationOnlyToCode8"],
        "responseObjectsPresent": totals["responseObjectsPresent"],
        "allHostResponseProvenanceAuthenticated": False,
        "semanticReviewsCompleted": 0,
        "semanticMatchesConfirmed": 0,
        "reviewStatusCounts": dict(collections.Counter(row["semanticReviewStatus"] for row in br_rows)),
        "transactionTypeObservationCounts": dict(flow_code_counts),
        "responseObjectStatusCounts": dict(response_status_counts),
        "inputSha256": input_hashes,
        "limitations": [
            "This builder creates a complete evidence worklist; it does not perform human semantic adjudication or confirm BR equivalence.",
            "Declared expected responses are expectations, not actual response evidence.",
            "Response objects in producer payload files have no authenticated host provenance and cannot satisfy actual-response gates.",
            "Sequence equality is an observation only; source applicability and case intent remain review-required.",
            "Approval binding is blocked unless an actual, provenance-supported Authorization response is supplied.",
            "Physical copies are retained as paths and deduplicated into logical steps by step index and payload hash.",
        ],
    }
    (output_dir / "summary.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
    report = [
        "# AI Flow Semantic Review Worklist",
        "",
        "This is a hash-bound review queue, not a semantic approval report. All AI BRs remain pending individual obligation review.",
        "",
        "| Measure | Count |",
        "|---|---:|",
        f"| AI BRs in review register | {summary['aiBrPopulation']:,} |",
        f"| Linked multileg flow cases | {summary['flowCases']:,} |",
        f"| Logical flow steps | {summary['logicalFlowSteps']:,} |",
        f"| Declared physical payload copies | {summary['declaredPhysicalPayloadCopies']:,} |",
        f"| Sequence equality observations | {summary['sequenceObservedMatchPairs'] + summary['sequenceObservedMismatchPairs']:,} |",
        f"| Approval bindings blocked on missing actual response | {summary['approvalBindingsBlockedMissingActualResponse']:,} |",
        f"| Full semantic reviews completed | {summary['semanticReviewsCompleted']:,} |",
        f"| Confirmed semantic matches | {summary['semanticMatchesConfirmed']:,} |",
        "",
        "## Evidence Rules",
        "",
        "- Per-leg transaction codes, sequence shape, approval shape, path, and SHA-256 are recorded without copying field values.",
        "- Sequence equality is a deterministic observation only; the row remains `NOT_ASSESSED` until its source rule and scenario are reviewed.",
        "- An `expectedResponse` declaration is not an actual response. A response-shaped producer payload is not host evidence without provenance.",
        "- Approval binding for authorization completion/cancellation remains blocked without the actual Authorization response and provenance.",
        "- No BR is promoted to a semantic match by this builder.",
        "",
        "## Files",
        "",
        "- `all-ai-br-semantic-review-register.csv`: all AI BRs, linked flow cases/legs, and reviewer decision placeholders.",
        "- `flow-case-binding-review.csv`: each flow case and adjacent-leg transaction/sequence/approval dispositions.",
        "- `flow-leg-evidence.csv`: each logical request/response leg with physical copies and hash-bound structural observations.",
        "- `manifest.json`: output hashes and exact frozen input hashes.",
    ]
    (output_dir / "REPORT.md").write_text("\n".join(report) + "\n", encoding="utf-8")
    implementation_copy = output_dir / "review-builder-implementation.py"
    shutil.copyfile(Path(__file__), native_path(implementation_copy))
    artifacts = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file() and path.name != "manifest.json"}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts,
        "inputs": input_hashes}, indent=2), encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key != "inputSha256"}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--review-dir", type=Path)
    parser.add_argument("--audit-dir", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.review_dir, args.audit_dir, args.output_dir)):
        run(args.review_dir.resolve(), args.audit_dir.resolve(), args.output_dir.resolve())
    else:
        parser.error("Supply review directory, frozen delivery audit directory and a new output directory")


if __name__ == "__main__":
    main()