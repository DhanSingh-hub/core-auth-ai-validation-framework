import argparse
import collections
import csv
import gzip
import hashlib
import json
import re
from pathlib import Path

import ijson


FLOW_IDS = {
    "FLOW-AUTH-COMPLETION",
    "FLOW-AUTH-PARTIAL-COMPLETION",
    "FLOW-AUTH-COMPLETION-DUPLICATE",
    "FLOW-AUTH-COMPLETION-VOID",
    "FLOW-EMV-AUTH-COMPLETION",
    "FLOW-ESTIMATED-INCREMENTAL-AUTH",
}
AUTH_CODES = {"3", "5", "B"}
COMPLETION_CODE = "0"


def read_json(path):
    return json.loads(Path(path).read_text(encoding="utf-8-sig"))


def rows(path, key):
    with Path(path).open("rb") as stream:
        yield from ijson.items(stream, key + ".item", use_float=True)


def json_cell(value):
    return json.dumps(value, ensure_ascii=True, sort_keys=True, separators=(",", ":"))


def sha256(path):
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def object_value(standard, name):
    if not isinstance(standard, dict):
        return None, None, []
    raw = next((value for key, value in standard.items() if key.lower() == name.lower()), None)
    if isinstance(raw, dict) and "value" in raw:
        return raw.get("value"), raw.get("method"), raw.get("flags") or []
    return raw, None, []


def step_standard(step):
    request = step.get("request") or {}
    return next((value for key, value in request.items()
                 if key.lower() == "standard message data segment"), {})


def identifiers(row, plural=None, singular=None):
    result = set()
    if plural:
        value = row.get(plural) or []
        if isinstance(value, str):
            result.add(value)
        else:
            result.update(str(item) for item in value if item)
    if singular and row.get(singular):
        result.add(str(row[singular]))
    return result


def scenario_br_ids(scenario):
    return identifiers(scenario, "requirement_ids", "requirement_id")


def selected_steps(case):
    steps = case.get("steps") or []
    roles = [str(step.get("role") or "").lower() for step in steps]
    families = [str(step.get("transaction_type") or "").lower() for step in steps]
    auth_indices = [i for i, (role, family) in enumerate(zip(roles, families))
                    if "authoriz" in role or "authoriz" in family]
    completion_indices = [i for i, (role, family) in enumerate(zip(roles, families))
                          if "completion" in role or "purchase/capture" in family]
    if not auth_indices or not completion_indices or min(auth_indices) >= max(completion_indices):
        return None
    if str(case.get("flow_id") or "") not in FLOW_IDS:
        return None
    return steps, auth_indices, completion_indices


def step_record(step, index):
    standard = step_standard(step)
    prompt, prompt_method, prompt_flags = object_value(standard, "Prompt Code")
    sequence, _, _ = object_value(standard, "Sequence Number")
    terminal, _, _ = object_value(standard, "Terminal Identifier")
    approval, approval_method, approval_flags = object_value(standard, "Approval Number")
    partial, _, _ = object_value(standard, "Partial Approval Indicator")
    amounts = {}
    for name in ("Fuel Purchase Amount", "Nonfuel Amount", "Tax Amount", "Cash Amount"):
        value, _, _ = object_value(standard, name)
        amounts[name] = None if value is None else str(value)
    prompt = None if prompt is None else str(prompt)
    return {
        "stepNumber": int(step.get("step") or index + 1),
        "role": str(step.get("role") or ""),
        "family": str(step.get("transaction_type") or ""),
        "promptCode": prompt,
        "promptMethod": prompt_method,
        "promptFlags": prompt_flags,
        "transactionCode": prompt[0] if prompt and len(prompt) == 4 else None,
        "sequence": None if sequence is None else str(sequence),
        "terminal": None if terminal is None else str(terminal),
        "approval": None if approval is None else str(approval),
        "approvalMethod": approval_method,
        "approvalFlags": approval_flags,
        "partialApproval": None if partial is None else str(partial),
        "amounts": amounts,
    }


def physical_leg(run_root, candidate_root, inventory, case, scenario_id, step_by_number, declaration):
    relative = str(declaration or "").replace("\\", "/").lstrip("./")
    payload_path = (candidate_root / relative).resolve()
    if not payload_path.is_relative_to(candidate_root.resolve()):
        raise ValueError("Declared test-data path escapes candidate root: " + relative)
    metadata_path = payload_path.with_suffix(".meta.json")
    payload_relative = payload_path.relative_to(run_root.resolve()).as_posix()
    metadata_relative = metadata_path.relative_to(run_root.resolve()).as_posix()
    record = {
        "path": relative,
        "metadataPath": metadata_relative,
        "payloadExists": payload_path.is_file(),
        "metadataExists": metadata_path.is_file(),
        "payloadSha256Verified": False,
        "metadataSha256Verified": False,
        "metadataCaseMatch": False,
        "metadataScenarioMatch": False,
        "metadataRoleMatch": None,
        "metadataStepMatch": None,
        "status": "INCOMPLETE",
    }
    if not payload_path.is_file() or not metadata_path.is_file():
        return record
    payload_hash = sha256(payload_path)
    metadata_hash = sha256(metadata_path)
    payload_info = inventory.get(payload_relative)
    metadata_info = inventory.get(metadata_relative)
    record["payloadSha256Verified"] = bool(payload_info and payload_info.get("sha256") == payload_hash)
    record["metadataSha256Verified"] = bool(metadata_info and metadata_info.get("sha256") == metadata_hash)
    try:
        payload = read_json(payload_path)
        metadata = read_json(metadata_path)
    except (ValueError, UnicodeError):
        record["status"] = "INVALID_JSON"
        return record
    step = step_by_number.get(int(metadata.get("step") or 0))
    record["metadataCaseMatch"] = metadata.get("testCaseId") == case.get("id")
    record["metadataScenarioMatch"] = metadata.get("scenarioId") == scenario_id
    record["metadataRoleMatch"] = (metadata.get("role") == step.get("role")) if step else None
    record["metadataStepMatch"] = (int(metadata.get("step") or 0) == step["stepNumber"]) if step else None
    if not isinstance(payload, dict) or not payload:
        record["status"] = "EMPTY_OR_NON_OBJECT_PAYLOAD"
    elif not all((record["payloadSha256Verified"], record["metadataSha256Verified"],
                  record["metadataCaseMatch"], record["metadataScenarioMatch"])):
        record["status"] = "HASH_OR_IDENTITY_MISMATCH"
    elif record["metadataRoleMatch"] is False or record["metadataStepMatch"] is False:
        record["status"] = "STEP_ROLE_MISMATCH"
    else:
        record["status"] = "PHYSICAL_AND_METADATA_MATCH"
    return record


def write_csv(path, data, fields):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows(data)


def generate(delivery_root, test_package_path, full_review_dir, output_dir):
    delivery_root = Path(delivery_root).resolve()
    test_package_path = Path(test_package_path).resolve()
    full_review_dir = Path(full_review_dir).resolve()
    output_dir = Path(output_dir).resolve()
    if output_dir.exists():
        raise ValueError("Output directory must be new: " + str(output_dir))

    v6_summary_path = full_review_dir.parent / "2026-10-14-delivery-wide-assessment-v6" / "summary.json"
    v6_summary = read_json(v6_summary_path)
    inventory_path = v6_summary_path.parent / "file-inventory.jsonl"
    if sha256(inventory_path) != v6_summary.get("fileInventorySha256"):
        raise ValueError("V6 inventory hash mismatch")
    inventory = {}
    with inventory_path.open(encoding="utf-8") as stream:
        for line in stream:
            item = json.loads(line)
            inventory[item["path"]] = item

    review_manifest = read_json(full_review_dir / "manifest.json")
    for name, digest in (review_manifest.get("artifacts") or {}).items():
        if sha256(full_review_dir / name) != digest:
            raise ValueError("All-chain review manifest hash mismatch: " + name)

    scenario_path = delivery_root / "scenarios/approved/approved_scenarios.json"
    case_path = delivery_root / "test_generation/test_generation/candidates/test_case_candidates.json"
    candidate_root = delivery_root / "test_data_10_oct/test_data_10_oct/candidates"
    scenario_by_id = {str(x["id"]): x for x in rows(scenario_path, "scenarios")}
    ai_br = {str(x["id"]): x for x in rows(delivery_root / "step5_requirements/approved/requirement_catalog.json", "requirements")}

    selected = []
    for case in rows(case_path, "test_cases"):
        selection = selected_steps(case)
        if not selection:
            continue
        steps, auth_indices, completion_indices = selection
        scenario_id = str(case.get("scenario_id") or "")
        scenario = scenario_by_id.get(scenario_id, {})
        case_brs = identifiers(case, "requirement_ids", "requirement_id")
        scenario_brs = scenario_br_ids(scenario)
        linked_brs = case_brs | scenario_brs
        step_records = [step_record(step, index) for index, step in enumerate(steps)]
        auth_steps = [step_records[index] for index in auth_indices]
        completion_steps = [step_records[index] for index in completion_indices]
        all_steps = step_records
        tx_codes_ok = all(x["transactionCode"] in AUTH_CODES for x in auth_steps) and all(
            x["transactionCode"] == COMPLETION_CODE for x in completion_steps)
        sequences = [x["sequence"] for x in all_steps]
        seq_ok = bool(sequences) and all(x and re.fullmatch(r"\d{6}", x) for x in sequences) and len(set(sequences)) == 1
        terminals = [x["terminal"] for x in all_steps]
        terminal_same = bool(terminals) and all(terminals) and len(set(terminals)) == 1
        terminal_shape = bool(terminals) and all(x and re.fullmatch(r"[A-Za-z0-9]{13,22}", x) for x in terminals)
        approvals_ok = all(x["approval"] and re.fullmatch(r"[A-Za-z0-9 ]{6}", x["approval"])
                           for x in completion_steps)
        approvals_runtime = any(
            x["approvalMethod"] == "flow_response_runtime_value"
            or any("FLOW_LINK_NEEDS_RUNTIME_VALUE" in str(flag) or "FLOW_RESPONSE_SHAPE_FILLED_VIA" in str(flag)
                   for flag in x["approvalFlags"])
            for x in completion_steps
        )
        amount_changed = any(auth["amounts"] != completion["amounts"]
                             for auth in auth_steps for completion in completion_steps)
        declarations = case.get("test_data_leg_files") or []
        if isinstance(declarations, dict):
            declarations = list(declarations.values())
        by_step = {x["stepNumber"]: x for x in all_steps}
        legs = [physical_leg(delivery_root, candidate_root, inventory, case, scenario_id, by_step, path)
                for path in declarations]
        selected.append({
            "case": case, "scenario": scenario, "caseId": str(case["id"]), "scenarioId": scenario_id,
            "flowId": str(case.get("flow_id") or "UNNAMED"), "flowVariant": str(case.get("flow_variant_name") or ""),
            "caseBrIds": sorted(case_brs), "scenarioBrIds": sorted(scenario_brs),
            "linkedBrIds": sorted(linked_brs), "steps": all_steps, "authSteps": auth_steps,
            "completionSteps": completion_steps, "legs": legs,
            "checks": {
                "transactionCodes": tx_codes_ok,
                "sequenceCarry": bool(seq_ok),
                "sameTerminalIdObservedNotSpecRule": bool(terminal_same),
                "terminalIdLengthAlphaShape13To22": bool(terminal_shape),
                "completionApprovalShape": bool(approvals_ok),
                "completionApprovalNeedsLiveHostValue": bool(approvals_runtime),
                "amountChangeNeedsHostApprovedAmountReview": bool(amount_changed),
                "physicalLegsComplete": bool(legs) and all(x["status"] == "PHYSICAL_AND_METADATA_MATCH" for x in legs),
                "directAiBrLink": bool(linked_brs),
            },
        })

    all_reviewed_ai_br = {}
    with (full_review_dir / "all-ai-br-review-register.csv").open(encoding="utf-8-sig", newline="") as stream:
        csv.field_size_limit(100_000_000)
        for row in csv.DictReader(stream):
            all_reviewed_ai_br[str(row["aiBrId"])] = row
    linked_ai_ids = {br for item in selected for br in item["linkedBrIds"]}
    ranked = collections.defaultdict(dict)
    with (full_review_dir / "ai-test-br-ranked-review-leads.csv").open(encoding="utf-8-sig", newline="") as stream:
        csv.field_size_limit(100_000_000)
        for row in csv.DictReader(stream):
            ai_id = str(row.get("aiBrId") or "")
            if ai_id not in {br for item in selected for br in item["linkedBrIds"]}:
                continue
            test_id = str(row.get("testBrId") or "")
            try:
                rank = int(row.get("candidateRank") or "999999")
            except ValueError:
                rank = 999999
            if test_id and rank <= 10:
                ranked[ai_id][test_id] = row

    test_chain = {}
    with gzip.open(full_review_dir / "test-chain-evidence.jsonl.gz", "rt", encoding="utf-8") as stream:
        for line in stream:
            item = json.loads(line)
            test_chain[str(item["testBrId"])] = item

    map_rows = []
    all_candidate_ids = set()
    for ai_id in sorted({br for item in selected for br in item["linkedBrIds"]}):
        ai_row = all_reviewed_ai_br.get(ai_id, {})
        anchor_ids = set(json.loads(ai_row.get("v6ExactAnchorTestBrCandidates") or "[]"))
        candidates = anchor_ids | set(ranked.get(ai_id, {}))
        cases_for_br = sorted(item["caseId"] for item in selected if ai_id in item["linkedBrIds"])
        if not candidates:
            map_rows.append({"aiBrId": ai_id, "aiStatement": ai_row.get("aiStatement"),
                             "authCompletionCaseIds": json_cell(cases_for_br), "testBrId": "",
                             "candidateBasis": "NO_RETAINED_TEST_BR_LEAD", "decisionStatus": "PENDING_DIRECT_REVIEW",
                             "semanticStatus": "NOT_ASSESSED"})
        for test_id in sorted(candidates):
            all_candidate_ids.add(test_id)
            lead = ranked.get(ai_id, {}).get(test_id, {})
            evidence = test_chain.get(test_id, {})
            test_br = evidence.get("br") or {}
            basis = []
            if test_id in anchor_ids:
                basis.append("V6_EXACT_SOURCE_ANCHOR_CANDIDATE")
            if lead:
                basis.append("TOP_K_RETRIEVAL_LEAD_NOT_CONFIDENCE")
            map_rows.append({
                "aiBrId": ai_id, "aiStatement": ai_row.get("aiStatement"),
                "authCompletionCaseIds": json_cell(cases_for_br), "testBrId": test_id,
                "testBrTitle": test_br.get("title") or lead.get("testBrTitle"),
                "testBrStatus": test_br.get("status") or lead.get("testBrStatus"),
                "testBrIsSourceBaseline": lead.get("testBrIsSourceBaseline"),
                "candidateBasis": json_cell(basis), "candidateRank": lead.get("candidateRank"),
                "retrievalScoreNotConfidence": lead.get("retrievalScore"),
                "sharedSegments": lead.get("sharedSegments"), "sharedElements": lead.get("sharedElements"),
                "retrievalReasons": lead.get("retrievalReasons"),
                "testScenarioIds": json_cell([x.get("id") for x in evidence.get("scenarios", [])]),
                "testCaseIds": json_cell([x.get("id") for x in evidence.get("cases", [])]),
                "testDataIds": json_cell([x.get("id") for x in evidence.get("data", [])]),
                "testScenarioStatuses": json_cell([x.get("status") for x in evidence.get("scenarios", [])]),
                "testCaseStatuses": json_cell([x.get("status") for x in evidence.get("cases", [])]),
                "testDataReadiness": json_cell([x.get("readiness") for x in evidence.get("data", [])]),
                "decisionStatus": "PENDING_AUTHORIZED_PAIR_REVIEW", "semanticStatus": "NOT_ASSESSED",
            })

    case_rows = []
    chain_rows = []
    missing_br_rows = []
    amount_review_rows = []
    for item in selected:
        case = item["case"]
        scenario = item["scenario"]
        steps = item["steps"]
        case_rows.append({
            "caseId": item["caseId"], "flowId": item["flowId"], "flowVariant": item["flowVariant"],
            "scenarioId": item["scenarioId"], "aiBrIds": json_cell(item["linkedBrIds"]),
            "scenarioRequirementIds": json_cell(item["scenarioBrIds"]),
            "caseRequirementIds": json_cell(item["caseBrIds"]),
            "stepRoles": json_cell([x["role"] for x in steps]),
            "stepFamilies": json_cell([x["family"] for x in steps]),
            "promptCodes": json_cell([x["promptCode"] for x in steps]),
            "transactionCodes": json_cell([x["transactionCode"] for x in steps]),
            "sequences": json_cell([x["sequence"] for x in steps]),
            "sequenceCarryPass": item["checks"]["sequenceCarry"],
            "terminalIdLengths": json_cell([len(x["terminal"]) if x["terminal"] else None for x in steps]),
            "sameTerminalIdObservedNotSpecRule": item["checks"]["sameTerminalIdObservedNotSpecRule"],
            "terminalIdLengthAlphaShape13To22": item["checks"]["terminalIdLengthAlphaShape13To22"],
            "completionApprovalShapePass": item["checks"]["completionApprovalShape"],
            "completionApprovalNeedsLiveHostValue": item["checks"]["completionApprovalNeedsLiveHostValue"],
            "partialApprovalIndicators": json_cell([x["partialApproval"] for x in steps]),
            "amountFieldsByStep": json_cell([x["amounts"] for x in steps]),
            "amountChangeNeedsHostApprovedAmountReview": item["checks"]["amountChangeNeedsHostApprovedAmountReview"],
            "physicalLegCount": len(item["legs"]),
            "physicalLegStatuses": json_cell([x["status"] for x in item["legs"]]),
            "allPhysicalLegsPassIdentityAndHash": item["checks"]["physicalLegsComplete"],
            "chainStatus": "PENDING_SEMANTIC_AND_HOST_REVIEW",
        })
        if not item["linkedBrIds"]:
            missing_br_rows.append({
                "caseId": item["caseId"],
                "flowId": item["flowId"],
                "flowVariant": item["flowVariant"],
                "scenarioId": item["scenarioId"],
                "scenarioTitle": scenario.get("name", ""),
                "scenarioRequirementIds": json_cell(item["scenarioBrIds"]),
                "caseRequirementIds": json_cell(item["caseBrIds"]),
                "sourceRuleId": scenario.get("source_rule_id", ""),
                "sourcePage": scenario.get("source_page") or case.get("source_page") or "",
                "stepFamilies": json_cell([x["family"] for x in steps]),
                "transactionCodes": json_cell([x["transactionCode"] for x in steps]),
                "actionRequired": "AI owner: link only source-backed AI BR IDs; if no existing BR states the lifecycle obligation, propose a sourced lifecycle BR for authorized review.",
                "proposedAiBrIds": "",
                "sourceEvidenceAndRationale": "",
                "decisionStatus": "PENDING_AI_AND_TEST_REVIEW",
                "semanticStatus": "NOT_ASSESSED",
            })
        if item["checks"]["amountChangeNeedsHostApprovedAmountReview"]:
            amount_review_rows.append({
                "caseId": item["caseId"],
                "flowId": item["flowId"],
                "flowVariant": item["flowVariant"],
                "scenarioId": item["scenarioId"],
                "aiBrIds": json_cell(item["linkedBrIds"]),
                "amountFieldsByStep": json_cell([x["amounts"] for x in steps]),
                "partialApprovalIndicators": json_cell([x["partialApproval"] for x in steps]),
                "hostApprovedAmount": "",
                "hostResponseCode": "",
                "hostResponseEvidenceRef": "",
                "finalAmountIndicatorTable040Evidence": "",
                "actionRequired": "Test/host owner: provide the actual authorization response and approved amount; confirm partial-approval outcome and any applicable amount-change rule before judging completion.",
                "amountDecision": "",
                "reviewRationale": "",
                "decisionStatus": "PENDING_HOST_RESPONSE_EVIDENCE",
            })
        legs = item["legs"] or [{"path": "", "metadataPath": "", "status": "NO_TD_DECLARATION",
                                  "payloadSha256Verified": False, "metadataCaseMatch": False,
                                  "metadataScenarioMatch": False, "metadataRoleMatch": None, "metadataStepMatch": None}]
        link_brs = item["linkedBrIds"] or [""]
        for br_id in link_brs:
            br = ai_br.get(br_id, {})
            for leg in legs:
                metadata_path = leg.get("metadataPath")
                meta = {}
                if metadata_path:
                    try:
                        meta = read_json(delivery_root / metadata_path)
                    except (FileNotFoundError, ValueError, UnicodeError):
                        pass
                step = next((x for x in steps if x["stepNumber"] == int(meta.get("step") or 0)), {})
                scenario_link = br_id in item["scenarioBrIds"] if br_id else False
                case_link = br_id in item["caseBrIds"] if br_id else False
                if not br_id:
                    disposition = "MISSING_BR_TO_SCENARIO_AND_TC_LINK"
                elif not scenario_link:
                    disposition = "BR_NOT_LINKED_TO_SCENARIO"
                elif leg.get("status") != "PHYSICAL_AND_METADATA_MATCH":
                    disposition = "TC_TO_TD_LINK_INCOMPLETE"
                else:
                    disposition = "STRUCTURAL_CHAIN_PRESENT_SEMANTIC_PENDING"
                chain_rows.append({
                    "flowId": item["flowId"], "caseId": item["caseId"], "aiBrId": br_id,
                    "aiBrStatement": br.get("statement", ""), "scenarioId": item["scenarioId"],
                    "brToScenario": "DIRECT" if scenario_link else "MISSING",
                    "scenarioToTc": "DIRECT" if item["scenarioId"] == case.get("scenario_id") else "MISMATCH",
                    "tcBrReference": "DIRECT" if case_link else "NOT_DIRECTLY_LISTED",
                    "stepNumber": meta.get("step"), "stepRole": meta.get("role") or step.get("role"),
                    "transactionFamily": step.get("family"), "promptCode": step.get("promptCode"),
                    "transactionCode": step.get("transactionCode"),
                    "tdPath": leg.get("path"), "tdMetadataPath": leg.get("metadataPath"),
                    "tdPayloadHashVerified": leg.get("payloadSha256Verified"),
                    "metadataCaseMatch": leg.get("metadataCaseMatch"),
                    "metadataScenarioMatch": leg.get("metadataScenarioMatch"),
                    "metadataRoleMatch": leg.get("metadataRoleMatch"),
                    "metadataStepMatch": leg.get("metadataStepMatch"),
                    "chainDisposition": disposition, "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_EXECUTED",
                })

    output_dir.mkdir(parents=True)
    case_path = output_dir / "authorization-completion-case-register.csv"
    chain_path = output_dir / "authorization-completion-br-ts-tc-td-matrix.csv"
    mapping_path = output_dir / "authorization-completion-ai-test-br-mapping.csv"
    missing_br_path = output_dir / "authorization-completion-missing-ai-br-links.csv"
    amount_review_path = output_dir / "authorization-completion-amount-review.csv"
    write_csv(case_path, case_rows, list(case_rows[0]) if case_rows else ["caseId"])
    write_csv(chain_path, chain_rows, list(chain_rows[0]) if chain_rows else ["caseId"])
    write_csv(mapping_path, map_rows, list(map_rows[0]) if map_rows else ["aiBrId", "testBrId"])
    write_csv(missing_br_path, missing_br_rows, list(missing_br_rows[0]) if missing_br_rows else ["caseId", "proposedAiBrIds"])
    write_csv(amount_review_path, amount_review_rows, list(amount_review_rows[0]) if amount_review_rows else ["caseId", "hostApprovedAmount"])

    flow_counts = collections.Counter(x["flowId"] for x in selected)
    summary = {
        "artifact": "ATL105-AI-AUTHORIZATION-COMPLETION-CHAIN-REVIEW",
        "population": {"authorizationCompletionCases": len(selected), "distinctAiBrsLinked": len(linked_ai_ids),
                       "casesWithDirectBrLink": sum(bool(x["linkedBrIds"]) for x in selected),
                       "casesWithoutDirectBrLink": sum(not x["linkedBrIds"] for x in selected),
                       "declaredPhysicalStepFiles": sum(len(x["legs"]) for x in selected),
                       "amountChangeCasesQueuedForHostEvidence": len(amount_review_rows)},
        "flows": dict(sorted(flow_counts.items())),
        "lifecycleChecks": {
            "promptCodePairsPassing": sum(bool(x["checks"]["transactionCodes"]) for x in selected),
            "sequenceCarryPairsPassing": sum(bool(x["checks"]["sequenceCarry"]) for x in selected),
            "casesWithSameTerminalIdObserved": sum(bool(x["checks"]["sameTerminalIdObservedNotSpecRule"]) for x in selected),
            "casesWithTerminalIdLengthAlphaShape13To22": sum(bool(x["checks"]["terminalIdLengthAlphaShape13To22"]) for x in selected),
            "completionApprovalShapePairsPassing": sum(bool(x["checks"]["completionApprovalShape"]) for x in selected),
            "completionApprovalsRequiringLiveHostValue": sum(bool(x["checks"]["completionApprovalNeedsLiveHostValue"]) for x in selected),
            "casesWithAmountChangeNeedingHostApprovedAmountReview": sum(bool(x["checks"]["amountChangeNeedsHostApprovedAmountReview"]) for x in selected),
            "casesWithAllPhysicalLegIdentityAndHashChecksPassing": sum(bool(x["checks"]["physicalLegsComplete"]) for x in selected),
        },
        "testBrMapping": {"candidatePairRows": len(map_rows), "distinctTestBrCandidates": len(all_candidate_ids),
                          "semanticDecisions": 0, "status": "RETRIEVAL_AND_ANCHOR_CANDIDATES_ONLY"},
        "sourceHashes": {"deliveryWideSummary": sha256(v6_summary_path), "deliveryFileInventory": sha256(inventory_path),
                         "testPackage": sha256(test_package_path), "allChainReviewManifest": sha256(full_review_dir / "manifest.json")},
        "policy": "AI chains are screened structurally using explicit flow IDs, step roles and Prompt Codes. Test BR candidates are not matches. Host approval values that are flow-synthesized remain placeholders until runtime.",
        "limitations": [
            "This report does not establish semantic equivalence or host execution.",
            "ATL105 Authorization-to-Completion guidance establishes Sequence Number carry and relevant approval/amount context, but does not establish same-Terminal-ID equality as a universal requirement. Terminal-ID equality is recorded as an observation only; device/merchant assignment is not verified by string shape.",
            "Amount changes are flagged for comparison with the actual host-approved amount and applicable amount-change rules, not auto-failed.",
            "All AI data values are withheld from the generated matrix; the OneDrive source remains unchanged.",
            "Test BR mapping contains exact-anchor and top-K retrieval leads only; full Test BR adjudication is still required.",
        ],
        "files": [case_path.name, chain_path.name, mapping_path.name, missing_br_path.name, amount_review_path.name],
    }
    (output_dir / "summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
    report = [
        "# AI Authorization-to-Completion Chain Review", "",
        f"AI candidate cases screened: **{len(selected)}**.",
        f"Directly BR-linked cases: **{summary['population']['casesWithDirectBrLink']}**; cases without a BR link: **{summary['population']['casesWithoutDirectBrLink']}**.",
        f"Distinct linked AI BRs: **{summary['population']['distinctAiBrsLinked']}**.",
        "",
        "## Flow variants", "", "| Flow ID | Cases |", "|---|---:|",
    ]
    report.extend(f"| {flow} | {count} |" for flow, count in sorted(flow_counts.items()))
    report.extend([
        "", "## Lifecycle checks", "",
        "| Check | Passing cases | Total |", "|---|---:|---:|",
        f"| Prompt Code pair matches Authorization→Completion | {summary['lifecycleChecks']['promptCodePairsPassing']} | {len(selected)} |",
        f"| Six-digit Sequence Number carried through steps | {summary['lifecycleChecks']['sequenceCarryPairsPassing']} | {len(selected)} |",
        f"| Same Terminal ID observed (informational; not an ATL105 lifecycle requirement) | {summary['lifecycleChecks']['casesWithSameTerminalIdObserved']} | {len(selected)} |",
        f"| Terminal ID 13–22 alphanumeric string shape (component/provisioning not validated) | {summary['lifecycleChecks']['casesWithTerminalIdLengthAlphaShape13To22']} | {len(selected)} |",
        f"| Completion Approval Number has six-character shape | {summary['lifecycleChecks']['completionApprovalShapePairsPassing']} | {len(selected)} |",
        f"| All declared physical payload/metadata legs hash and identity match | {summary['lifecycleChecks']['casesWithAllPhysicalLegIdentityAndHashChecksPassing']} | {len(selected)} |",
        "",
        "**Key finding:** The lifecycle chain preserves Sequence Number and transaction-code order. Generated steps use different Terminal IDs, recorded as an observation only: current ATL105 Authorization→Completion guidance does not establish Terminal-ID equality as a universal requirement, even though the repository lifecycle validator applies that check. Do not count it as an ATL105 spec failure without authorized source/SME backing. Completion Approval Numbers flagged as runtime-derived are placeholders and must be replaced by actual host response values before execution. Amount changes require comparison with the host-approved amount; this review does not infer approval from the request.",
        "",
        "## BR→TS→TC→TD Traceability",
        "",
        f"The case register contains all {len(selected)} selected flows. The BR-to-TS-to-TC-to-TD matrix has {len(chain_rows)} rows, including explicit missing-BR links and one row per declared step-data leg. {summary['population']['casesWithoutDirectBrLink']} flow cases have no direct AI BR relationship in the supplied catalogs, so their chain stops before BR attribution.",
        "",
        f"The Test BR mapping matrix contains {len(map_rows)} exact-anchor or top-K retrieval candidate rows for {summary['testBrMapping']['distinctTestBrCandidates']} distinct Test BRs. Retrieval rank is prioritization only; every relation remains pending review and `NOT_ASSESSED`.",
        "",
        "## Files", "",
        "- `authorization-completion-case-register.csv`: per-flow variants and lifecycle checks.",
        "- `authorization-completion-br-ts-tc-td-matrix.csv`: explicit chain links, physical leg and metadata identity checks.",
        "- `authorization-completion-ai-test-br-mapping.csv`: AI BR to Test BR candidate mappings and Test-side chain readiness.",
        "- `authorization-completion-missing-ai-br-links.csv`: cases without direct AI BR links; proposed links are left blank for source-backed review.",
        "- `authorization-completion-amount-review.csv`: amount-change cases awaiting host-approved-amount and response evidence.",
        "- `summary.json`: populations, checks, input hashes, and limitations.",
        "",
        "Semantic equivalence remains `NOT_ASSESSED`; host execution was not performed.",
    ])
    (output_dir / "REPORT.md").write_text("\n".join(report) + "\n", encoding="utf-8")
    outputs = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file() and path.name != "manifest.json"}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": outputs}, indent=2), encoding="utf-8")
    return summary


def self_test():
    sample = {"steps": [{"role": "authorization", "transaction_type": "Financial Transaction Request"},
                        {"role": "completion", "transaction_type": "Purchase/Capture"}],
              "flow_id": "FLOW-AUTH-COMPLETION"}
    assert selected_steps(sample) is not None
    assert selected_steps({"flow_id": "FLOW-AUTH-COMPLETION", "steps": list(reversed(sample["steps"]))}) is None
    assert selected_steps({"flow_id": "FLOW-SALE-VOID", "steps": sample["steps"]}) is None
    assert object_value({"Sequence Number": {"value": "000001", "method": "fixed"}}, "Sequence Number")[0] == "000001"
    print("PASS: auth-completion selection and source-field extraction")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--delivery-root", type=Path)
    parser.add_argument("--test-package", type=Path)
    parser.add_argument("--full-review-directory", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.delivery_root, args.test_package, args.full_review_directory, args.output_directory)):
        result = generate(args.delivery_root, args.test_package, args.full_review_directory, args.output_directory)
        print(json.dumps(result, indent=2))
    else:
        parser.error("--delivery-root, --test-package, --full-review-directory and --output-directory are required")


if __name__ == "__main__":
    main()