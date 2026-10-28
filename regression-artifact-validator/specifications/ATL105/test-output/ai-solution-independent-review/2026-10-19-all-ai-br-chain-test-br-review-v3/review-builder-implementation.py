import argparse
import csv
import gzip
import hashlib
import json
import math
import re
from collections import Counter, defaultdict
from pathlib import Path

import ijson

TOKEN_RE = re.compile(r"[a-z]+")
STOP_WORDS = {
    "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
    "in", "into", "is", "it", "of", "on", "or", "that", "the", "this",
    "to", "via", "with", "must", "shall", "should", "will", "may", "not",
    "when", "where", "which", "while", "within", "without", "all", "any",
    "each", "every", "using", "used", "use", "value", "values", "field",
    "fields", "data", "segment", "segments", "request", "response", "message",
}


def sha256(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def jsonl(path):
    with path.open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def catalog_rows(path, prefix):
    with path.open("rb") as stream:
        yield from ijson.items(stream, prefix, use_float=True)


def strings(value):
    if isinstance(value, str):
        return [value] if value else []
    if isinstance(value, list):
        return [item for item in value if isinstance(item, str) and item]
    return []


def linked_ids(record, plural, singular):
    result = set(strings(record.get(plural)))
    result.update(strings(record.get(singular)))
    return result


def text_tokens(*values):
    result = set()
    for value in values:
        if not isinstance(value, str):
            continue
        for token in TOKEN_RE.findall(value.lower()):
            if token in STOP_WORDS or len(token) < 3:
                continue
            if token.endswith("ies") and len(token) > 5:
                token = token[:-3] + "y"
            elif token.endswith("s") and len(token) > 4 and not token.endswith("ss"):
                token = token[:-1]
            result.add(token)
    return result


def nested_keys(value):
    result = set()
    if isinstance(value, dict):
        for key, child in value.items():
            if key not in {"value", "method", "source_page", "flags", "data_ref"}:
                result.add(str(key))
            result.update(nested_keys(child))
    elif isinstance(value, list):
        for child in value:
            result.update(nested_keys(child))
    return result


def anchor_features(ai_record):
    segments = set()
    elements = set()
    segment = ai_record.get("segment_number")
    if segment is not None and str(segment).strip():
        segments.add(str(segment).strip().upper())
    for value in [*(ai_record.get("segment_ids") or []), *(ai_record.get("related_entity_ids") or [])]:
        match = re.fullmatch(r"ENT-SEG-(.+)", str(value), re.IGNORECASE)
        if match:
            segments.add(match.group(1).strip().upper())
    for value in [ai_record.get("source_rule_id"), (ai_record.get("evidence_ref") or {}).get("entity_id"),
                  *(ai_record.get("related_entity_ids") or [])]:
        match = re.fullmatch(r"ENT-ELEM-(\d+)", str(value or ""), re.IGNORECASE)
        if match:
            elements.add(str(int(match.group(1))))
    if re.fullmatch(r"\d+", str(ai_record.get("source_rule_id") or "")):
        elements.add(str(int(ai_record["source_rule_id"])))
    return segments, elements


def test_anchor_features(rule):
    segments = set()
    elements = set()
    for anchor in rule.get("sourceAnchors") or []:
        for item in str(anchor.get("segment") or "").split(","):
            item = item.strip().upper()
            if item:
                segments.add(item)
        for item in re.split(r"[,|]", str(anchor.get("element") or "")):
            item = item.strip()
            if re.fullmatch(r"\d+", item):
                elements.add(str(int(item)))
    return segments, elements


def weighted_cosine(left, right, idf, left_norm=None, right_norm=None):
    if not left or not right:
        return 0.0
    common = left & right
    if not common:
        return 0.0
    if left_norm is None:
        left_norm = math.sqrt(sum(idf.get(token, 1.0) ** 2 for token in left))
    if right_norm is None:
        right_norm = math.sqrt(sum(idf.get(token, 1.0) ** 2 for token in right))
    dot = sum(idf.get(token, 1.0) ** 2 for token in common)
    return dot / (left_norm * right_norm) if left_norm and right_norm else 0.0


def test_self_similarity():
    first = text_tokens("Sequence Number persists through subsequent transactions")
    second = text_tokens("Sequence Number must persist throughout the lifecycle")
    unrelated = text_tokens("Cardholder postal address must contain a valid ZIP code")
    assert weighted_cosine(first, second, {}) > weighted_cosine(first, unrelated, {})
    ai = {"segment_number": "100", "source_rule_id": "ENT-ELEM-86", "related_entity_ids": []}
    rule = {"sourceAnchors": [{"segment": "100", "element": "086"}]}
    assert anchor_features(ai) == test_anchor_features(rule)
    print("PASS: text retrieval favors related lifecycle language and normalizes source anchors")


def collect_chain_data(delivery_root, review_root):
    ai_br_path = delivery_root / "step5_requirements/candidates/requirement_candidates.json"
    scenario_path = delivery_root / "scenarios/candidates/candidate_scenarios.json"
    case_path = delivery_root / "test_generation/test_generation/candidates/test_case_candidates.json"
    summary_path = review_root / "summary.json"
    inventory_path = review_root / "file-inventory.jsonl"
    assessment_path = review_root / "br-assessment.jsonl"
    physical_path = review_root / "tc-physical-assessment.jsonl"
    candidate_path = review_root / "candidate-review-queue.jsonl"

    summary = read_json(summary_path)
    frozen_inventory = {row["path"]: row for row in jsonl(inventory_path)}
    for path in (ai_br_path, scenario_path, case_path):
        relative = path.relative_to(delivery_root).as_posix()
        expected = frozen_inventory.get(relative, {}).get("sha256")
        if not expected or sha256(path) != expected:
            raise ValueError("Candidate catalog differs from frozen delivery inventory: " + relative)
    for path in (assessment_path, physical_path, candidate_path):
        expected = summary.get("outputHashes", {}).get(path.name)
        if not expected or sha256(path) != expected:
            raise ValueError("Frozen v6 review ledger hash differs: " + path.name)

    requirements = list(catalog_rows(ai_br_path, "requirements.item"))
    if len(requirements) != 6880 or len({row.get("id") for row in requirements}) != len(requirements):
        raise ValueError("Expected 6,880 unique candidate AI BR records")
    requirement_by_id = {row["id"]: row for row in requirements}

    scenarios_by_id = {}
    scenarios_by_br = defaultdict(set)
    with scenario_path.open("rb") as stream:
        for scenario in ijson.items(stream, "scenarios.item", use_float=True):
            identifier = scenario.get("id")
            if not identifier:
                continue
            scenarios_by_id[identifier] = scenario
            for br_id in linked_ids(scenario, "requirement_ids", "requirement_id"):
                scenarios_by_br[br_id].add(identifier)

    cases_by_id = {}
    cases_by_br = defaultdict(set)
    cases_by_scenario = defaultdict(set)
    case_tokens = {}
    case_summaries = {}
    with case_path.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item", use_float=True):
            case_id = case.get("id")
            if not case_id:
                continue
            for br_id in linked_ids(case, "requirement_ids", "requirement_id"):
                cases_by_br[br_id].add(case_id)
            scenario_id = case.get("scenario_id")
            if scenario_id:
                cases_by_scenario[scenario_id].add(case_id)
            request_keys = nested_keys(case.get("request") or {})
            expected = case.get("expected_response")
            expected_keys = nested_keys(expected)
            case_text = text_tokens(
                case.get("transaction_type"), case.get("scenario_type"),
                case.get("violated_element_name"), case.get("negative_class"),
                case.get("scenario_oracle_hint"), case.get("completeness_status"),
                " ".join(request_keys), " ".join(expected_keys))
            case_tokens[case_id] = case_text
            case_summaries[case_id] = {
                "id": case_id,
                "scenarioId": scenario_id,
                "scenarioType": case.get("scenario_type"),
                "transactionType": case.get("transaction_type"),
                "requirementIds": sorted(linked_ids(case, "requirement_ids", "requirement_id")),
                "violatedElementName": case.get("violated_element_name"),
                "negativeClass": case.get("negative_class"),
                "completenessStatus": case.get("completeness_status"),
                "flags": case.get("flags") or [],
                "requestFieldNames": sorted(request_keys),
                "expectedResponseFieldNames": sorted(expected_keys),
            }
            cases_by_id[case_id] = case_summaries[case_id]

    assessments = {row["id"]: row for row in jsonl(assessment_path)}
    if set(assessments) != set(requirement_by_id):
        raise ValueError("v6 BR assessment population differs from candidate AI BR catalog")
    physical_by_case = defaultdict(list)
    for row in jsonl(physical_path):
        physical_by_case[row["id"]].append(row)
    anchor_candidates = defaultdict(set)
    for row in jsonl(candidate_path):
        anchor_candidates[row["aiBrId"]].add(row["testBrId"])

    return {
        "requirements": requirements,
        "requirementById": requirement_by_id,
        "scenariosById": scenarios_by_id,
        "scenariosByBr": scenarios_by_br,
        "casesById": cases_by_id,
        "casesByBr": cases_by_br,
        "casesByScenario": cases_by_scenario,
        "caseTokens": case_tokens,
        "caseSummaries": case_summaries,
        "assessments": assessments,
        "physicalByCase": physical_by_case,
        "anchorCandidates": anchor_candidates,
        "frozenInventory": frozen_inventory,
        "inputPaths": [ai_br_path, scenario_path, case_path, summary_path, inventory_path,
                       assessment_path, physical_path, candidate_path],
    }


def load_test_model(test_baseline_path, test_package_path):
    baseline = read_json(test_baseline_path)
    package = read_json(test_package_path)
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent source-derived Test BR baseline required")
    if package.get("manifest", {}).get("authority") != "INDEPENDENT_TEST_SOLUTION" or package.get("manifest", {}).get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent Test chain package required")
    baseline_ids = {row["ruleId"] for row in baseline["businessRequirements"]}
    rule_rows = package.get("businessRequirements", [])
    test_by_id = {row["id"].removeprefix("BR-RULE-"): row for row in rule_rows}
    catalog_count = sum(row["id"].startswith("BR-RULE-") for row in rule_rows)
    if len(test_by_id) != len(rule_rows) or catalog_count != package.get("summary", {}).get("independentRuleDenominator"):
        raise ValueError("Test package uniqueness or catalog denominator check failed")
    if not baseline_ids.issubset(test_by_id):
        raise ValueError("Test package omits source-derived baseline BRs")

    baseline_by_id = {row["ruleId"]: row for row in baseline["businessRequirements"]}
    scenario_by_id = {row["id"]: row for row in package.get("testScenarios", [])}
    case_by_id = {row["id"]: row for row in package.get("testCases", [])}
    data_by_id = {row["id"]: row for row in package.get("testData", [])}
    scenarios_by_rule = defaultdict(set)
    cases_by_scenario = defaultdict(set)
    data_by_case = defaultdict(set)
    for scenario in scenario_by_id.values():
        for requirement_id in linked_ids(scenario, "requirementIds", "requirementId"):
            scenarios_by_rule[requirement_id.removeprefix("BR-RULE-")].add(scenario["id"])
    for case in case_by_id.values():
        for scenario_id in linked_ids(case, "scenarioIds", "scenarioId"):
            cases_by_scenario[scenario_id].add(case["id"])
    for data in data_by_id.values():
        for case_id in linked_ids(data, "testCaseIds", "testCaseId"):
            data_by_case[case_id].add(data["id"])

    rules = {}
    for rule_id, record in test_by_id.items():
        source = baseline_by_id.get(rule_id, {})
        anchors = record.get("sourceAnchors") or ([source.get("sourceAnchor")] if source.get("sourceAnchor") else [])
        segments = set()
        elements = set()
        for anchor in anchors:
            for value in str(anchor.get("segment") or "").split(","):
                value = value.strip().upper()
                if value:
                    segments.add(value)
            for value in re.split(r"[,|]", str(anchor.get("element") or "")):
                value = value.strip()
                if re.fullmatch(r"\d+", value):
                    elements.add(str(int(value)))
        scenario_ids = scenarios_by_rule[rule_id]
        case_ids = {case_id for scenario_id in scenario_ids for case_id in cases_by_scenario[scenario_id]}
        data_ids = {data_id for case_id in case_ids for data_id in data_by_case[case_id]}
        scenario_text = []
        for scenario_id in scenario_ids:
            scenario = scenario_by_id[scenario_id]
            scenario_text.extend([scenario_id, scenario.get("status")])
            scenario_text.extend(anchor.get("rule", "") for anchor in scenario.get("sourceAnchors", []))
        case_text = []
        for case_id in case_ids:
            case = case_by_id[case_id]
            case_text.extend([case_id, case.get("expectedOutcome"), case.get("status")])
            case_text.extend(anchor.get("rule", "") for anchor in case.get("sourceAnchors", []))
        data_text = []
        data_readiness = set()
        for data_id in data_ids:
            data = data_by_id[data_id]
            data_text.extend([data_id, data.get("fixtureKind"), data.get("expectedValidation")])
            data_readiness.add(str(data.get("readiness") or "NOT_SET"))
        text = text_tokens(record.get("title"), source.get("class"),
                           " ".join(anchor.get("section", "") + " " + anchor.get("rule", "") for anchor in anchors))
        chain_text = text_tokens(" ".join(str(item or "") for item in scenario_text),
                     " ".join(str(item or "") for item in case_text),
                     " ".join(str(item or "") for item in data_text))
        rules[rule_id] = {
            "id": rule_id,
            "title": record.get("title", ""),
            "status": record.get("status", "NOT_SET"),
            "baselineMember": rule_id in baseline_ids,
            "class": source.get("class", "catalogued-addition" if record["id"].startswith("BR-RULE-") else "authored-test-br"),
            "sourceAnchors": anchors,
            "segments": segments,
            "elements": elements,
            "tokens": text,
            "chainTokens": chain_text,
            "scenarioIds": sorted(scenario_ids),
            "caseIds": sorted(case_ids),
            "dataIds": sorted(data_ids),
            "dataReadiness": sorted(data_readiness),
        }
    return rules, baseline, package


def scenario_summary(scenario):
    parameters = scenario.get("parameters") or {}
    return {
        "id": scenario.get("id"),
        "name": scenario.get("name"),
        "type": scenario.get("type"),
        "targetTransaction": scenario.get("target_transaction"),
        "requirementIds": sorted(linked_ids(scenario, "requirement_ids", "requirement_id")),
        "parameterNames": sorted(str(key) for key in parameters),
        "expectedResponseCode": scenario.get("expected_response_code"),
        "expectedDeclineCode": scenario.get("expected_decline_code"),
        "flags": scenario.get("flags") or [],
    }


def score_pair(ai_record, ai_text, ai_chain, test_rule, idf, prepared=None):
    if prepared is None:
        prepared = {"segments": anchor_features(ai_record)[0], "elements": anchor_features(ai_record)[1],
                    "combined": ai_text | ai_chain, "textNorm": None, "chainNorm": None, "combinedNorm": None}
    text_score = weighted_cosine(ai_text, test_rule["tokens"], idf, prepared["textNorm"], test_rule.get("textNorm"))
    chain_score = weighted_cosine(ai_chain, test_rule["tokens"], idf, prepared["chainNorm"], test_rule.get("textNorm"))
    test_chain_score = weighted_cosine(prepared["combined"], test_rule["chainTokens"], idf, prepared["combinedNorm"], test_rule.get("chainNorm"))
    ai_segments, ai_elements = prepared["segments"], prepared["elements"]
    shared_segments = sorted(ai_segments & test_rule["segments"])
    shared_elements = sorted(ai_elements & test_rule["elements"])
    score = 100 * (0.55 * text_score + 0.30 * chain_score + 0.15 * test_chain_score)
    if shared_elements:
        score += 15
    if shared_segments:
        score += 10
    if shared_elements and shared_segments:
        score += 20
    matched_terms = sorted((ai_text & test_rule["tokens"]) | (ai_chain & test_rule["tokens"]) | ((ai_text | ai_chain) & test_rule["chainTokens"]))
    reasons = []
    if shared_segments:
        reasons.append("SHARED_SEGMENT:" + ";".join(shared_segments))
    if shared_elements:
        reasons.append("SHARED_ELEMENT:" + ";".join(shared_elements))
    if matched_terms:
        reasons.append("SHARED_TERMS:" + ";".join(matched_terms[:10]))
    return {
        "score": round(score, 3),
        "statementSimilarity": round(text_score, 4),
        "aiChainSimilarity": round(chain_score, 4),
        "testChainSimilarity": round(test_chain_score, 4),
        "sharedSegments": shared_segments,
        "sharedElements": shared_elements,
        "sharedTerms": matched_terms[:20],
        "retrievalReasons": reasons,
    }


def write_csv(path, rows, fields):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True, separators=(",", ":"))
                             if isinstance(value, (list, dict)) else value for key, value in row.items()})


def write_jsonl_gzip(path, rows):
    with gzip.open(path, "wt", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")


def build(delivery_root, review_root, test_baseline_path, test_package_path, output_dir, top_k, prior_review_dir=None):
    delivery_root = delivery_root.resolve()
    review_root = review_root.resolve()
    test_baseline_path = test_baseline_path.resolve()
    test_package_path = test_package_path.resolve()
    output_dir = output_dir.resolve()
    if output_dir.exists():
        raise ValueError("Refusing to overwrite output directory: " + str(output_dir))

    input_paths = [test_baseline_path, test_package_path, Path(__file__).resolve(),
                   review_root / "file-inventory.jsonl", review_root / "summary.json",
                   review_root / "br-assessment.jsonl", review_root / "tc-physical-assessment.jsonl",
                   review_root / "candidate-review-queue.jsonl",
                   delivery_root / "step5_requirements/candidates/requirement_candidates.json",
                   delivery_root / "scenarios/candidates/candidate_scenarios.json",
                   delivery_root / "test_generation/test_generation/candidates/test_case_candidates.json"]
    prior_records = {}
    if prior_review_dir:
        prior_paths = sorted(prior_review_dir.glob("preliminary-review-batch-*.json"))
        prior_paths.extend(sorted(prior_review_dir.glob("continuation-*.json")))
        for path in prior_paths:
            input_paths.append(path.resolve())
            for row in read_json(path).get("records", []):
                if row["aiBrId"] in prior_records:
                    raise ValueError("Duplicate preliminary review ID: " + row["aiBrId"])
                prior_records[row["aiBrId"]] = {"record": row, "sourceFile": str(path.resolve())}
    source_hashes = {str(path): sha256(path) for path in input_paths}
    ai = collect_chain_data(delivery_root, review_root)
    test_rules, baseline, test_package = load_test_model(test_baseline_path, test_package_path)
    test_docs = {rule_id: rule["tokens"] | rule["chainTokens"] for rule_id, rule in test_rules.items()}
    doc_frequency = Counter(token for tokens in test_docs.values() for token in tokens)
    idf = {token: math.log((len(test_docs) + 1) / (count + 1)) + 1.0
           for token, count in doc_frequency.items()}
    def norm(tokens):
        return math.sqrt(sum(idf.get(token, 1.0) ** 2 for token in tokens))

    for rule in test_rules.values():
        rule["textNorm"] = norm(rule["tokens"])
        rule["chainNorm"] = norm(rule["chainTokens"])

    candidate_rows = []
    register_rows = []
    chain_rows = []
    metrics = Counter()
    for requirement in ai["requirements"]:
        ai_id = requirement["id"]
        assessment = ai["assessments"][ai_id]
        scenario_ids = set(ai["scenariosByBr"][ai_id]) | set(assessment.get("scenarioIds") or [])
        scenario_ids &= set(ai["scenariosById"])
        case_ids = set(ai["casesByBr"][ai_id])
        case_ids.update(case_id for scenario_id in scenario_ids for case_id in ai["casesByScenario"][scenario_id])
        case_ids.update(assessment.get("testCaseIds") or [])
        scenario_records = [ai["scenariosById"][identifier] for identifier in sorted(scenario_ids)]
        case_records = [ai["casesById"][identifier] for identifier in sorted(case_ids) if identifier in ai["casesById"]]
        ai_text = text_tokens(requirement.get("statement"), requirement.get("expected_behavior"),
                              requirement.get("category"), requirement.get("requirement_type"),
                              requirement.get("source_rule_id"))
        ai_chain = set()
        scenario_summaries = []
        for scenario in scenario_records:
            summary = scenario_summary(scenario)
            scenario_summaries.append(summary)
            ai_chain.update(text_tokens(summary.get("name"), summary.get("type"), summary.get("targetTransaction"),
                                        " ".join(summary.get("parameterNames", []))))
        case_summaries = []
        for case in case_records:
            case_id = case["id"]
            ai_chain.update(ai["caseTokens"].get(case_id, set()))
            physical = ai["physicalByCase"].get(case_id, [])
            physical_evidence = []
            for entry in physical:
                for path in entry.get("paths") or []:
                    frozen = ai["frozenInventory"].get(path, {})
                    physical_evidence.append({
                        "path": path,
                        "assessmentStatus": entry.get("status"),
                        "issues": entry.get("issues") or [],
                        "frozenInventorySha256": frozen.get("sha256"),
                        "presentInFrozenInventory": bool(frozen),
                    })
            case_summaries.append({**ai["caseSummaries"][case_id], "physicalEvidence": physical_evidence})

        ai_segments, ai_elements = anchor_features(requirement)
        prepared = {"segments": ai_segments, "elements": ai_elements, "combined": ai_text | ai_chain,
                "textNorm": norm(ai_text), "chainNorm": norm(ai_chain), "combinedNorm": norm(ai_text | ai_chain)}
        exact_anchor_ids = sorted(ai["anchorCandidates"].get(ai_id, set()))
        scored = []
        for test_id, test_rule in test_rules.items():
            result = score_pair(requirement, ai_text, ai_chain, test_rule, idf, prepared)
            scored.append((test_id, result))
        scored.sort(key=lambda item: (-item[1]["score"], item[0]))
        selected = {test_id: result for test_id, result in scored[:top_k] if result["score"] > 0}
        for test_id in exact_anchor_ids:
            if test_id in test_rules:
                selected[test_id] = next(result for candidate_id, result in scored if candidate_id == test_id)
        ranked = sorted(selected.items(), key=lambda item: (-item[1]["score"], item[0]))

        for rank, (test_id, result) in enumerate(ranked, start=1):
            rule = test_rules[test_id]
            origin = []
            if test_id in exact_anchor_ids:
                origin.append("V6_SOURCE_ANCHOR_QUEUE")
            if result["score"] > 0:
                origin.append("MULTI_SIGNAL_TEXT_CHAIN_RETRIEVAL")
            pair = {
                "aiBrId": ai_id,
                "aiStatement": requirement.get("statement", ""),
                "aiExpectedBehavior": requirement.get("expected_behavior", ""),
                "aiCategory": requirement.get("category", ""),
                "aiSourcePage": requirement.get("source_page"),
                "aiSourceRuleId": requirement.get("source_rule_id", ""),
                "testBrId": test_id,
                "testBrTitle": rule["title"],
                "testBrStatus": rule["status"],
                "testBrClass": rule["class"],
                "testBrIsSourceBaseline": rule["baselineMember"],
                "testBrSourceAnchors": rule["sourceAnchors"],
                "candidateRank": rank,
                "retrievalScore": result["score"],
                "statementSimilarity": result["statementSimilarity"],
                "aiChainSimilarity": result["aiChainSimilarity"],
                "testChainSimilarity": result["testChainSimilarity"],
                "sharedSegments": result["sharedSegments"],
                "sharedElements": result["sharedElements"],
                "sharedTerms": result["sharedTerms"],
                "retrievalReasons": result["retrievalReasons"],
                "candidateOrigin": origin,
                "testScenarioIds": rule["scenarioIds"],
                "testCaseIds": rule["caseIds"],
                "testDataIds": rule["dataIds"],
                "testDataReadiness": rule["dataReadiness"],
                "reviewStatus": "PENDING_REVIEW",
                "semanticMatchStatus": "NOT_ASSESSED",
            }
            candidate_rows.append(pair)

        clean_case_ids = set(assessment.get("physicalCompleteCaseIds") or [])
        payload_paths = sorted({evidence["path"] for case in case_summaries
                                for evidence in case["physicalEvidence"]})
        chain_status = "SCENARIO_CASE_CHAIN_WITH_PHYSICAL_EVIDENCE" if clean_case_ids else (
            "SCENARIO_CASE_CHAIN_WITHOUT_CLEAN_PHYSICAL_CASE" if case_ids else (
                "SCENARIO_LINK_WITHOUT_CASE" if scenario_ids else "NO_LINKED_SCENARIO_OR_CASE"))
        chain_rows.append({
            "aiBrId": ai_id,
            "statement": requirement.get("statement", ""),
            "sourcePage": requirement.get("source_page"),
            "scenarioIds": sorted(scenario_ids),
            "scenarios": scenario_summaries,
            "caseIds": sorted(case_ids),
            "cases": case_summaries,
            "physicalCompleteCaseIdsFromV6": sorted(clean_case_ids),
            "physicalPayloadPaths": payload_paths,
            "chainStatus": chain_status,
            "sourceApprovalStatusUsedAsGate": False,
        })
        if scenario_ids:
            metrics["aiBrsWithScenarios"] += 1
        if case_ids:
            metrics["aiBrsWithCases"] += 1
        if clean_case_ids:
            metrics["aiBrsWithPhysicalCompleteCases"] += 1
        if exact_anchor_ids:
            metrics["aiBrsWithExistingAnchorCandidates"] += 1
        if ranked:
            metrics["aiBrsWithAnyReviewLead"] += 1
        else:
            metrics["aiBrsWithNoRetrievalLead"] += 1
        metrics["aiScenarioLinks"] += len(scenario_ids)
        metrics["aiCaseLinks"] += len(case_ids)
        metrics["aiPayloadPathLinks"] += len(payload_paths)

        register_rows.append({
            "aiBrId": ai_id,
            "aiStatement": requirement.get("statement", ""),
            "aiExpectedBehavior": requirement.get("expected_behavior", ""),
            "aiCategory": requirement.get("category", ""),
            "aiRequirementType": requirement.get("requirement_type", ""),
            "aiSourceRuleId": requirement.get("source_rule_id", ""),
            "aiSourcePage": requirement.get("source_page"),
            "aiSegments": sorted(ai_segments),
            "aiElements": sorted(ai_elements),
            "aiScenarioIds": sorted(scenario_ids),
            "aiScenarioCount": len(scenario_ids),
            "aiCaseIds": sorted(case_ids),
            "aiCaseCount": len(case_ids),
            "aiPhysicalCompleteCaseIds": sorted(clean_case_ids),
            "aiPayloadPaths": payload_paths,
            "chainStatus": chain_status,
            "v6ExactAnchorTestBrCandidates": exact_anchor_ids,
            "rankedTestBrCandidates": [test_id for test_id, _ in ranked],
            "rankedCandidateCount": len(ranked),
            "topCandidateScore": ranked[0][1]["score"] if ranked else None,
            "reviewDisposition": "PENDING_REVIEW" if ranked else "NO_RETRIEVAL_LEAD_REVIEW_ALL_TEST_BRS",
            "semanticMatchStatus": "NOT_ASSESSED",
            "aiScenarioApprovalRequired": False,
        })

    if len(register_rows) != 6880 or len(chain_rows) != 6880:
        raise AssertionError("All 6,880 AI BRs must have one review and chain row")
    if not set(prior_records).issubset({row["aiBrId"] for row in register_rows}):
        raise ValueError("Prior review IDs are outside this delivery")
    for path, digest in source_hashes.items():
        if sha256(Path(path)) != digest:
            raise ValueError("Input changed during generation: " + path)
    if output_dir.exists():
        raise ValueError("Refusing to overwrite output directory: " + str(output_dir))
    output_dir.mkdir(parents=True)

    register_fields = list(register_rows[0])
    candidate_fields = list(candidate_rows[0]) if candidate_rows else ["aiBrId", "testBrId"]
    write_csv(output_dir / "all-ai-br-review-register.csv", register_rows, register_fields)
    write_csv(output_dir / "ai-test-br-ranked-review-leads.csv", candidate_rows, candidate_fields)
    write_jsonl_gzip(output_dir / "ai-br-chain-evidence.jsonl.gz", chain_rows)
    progress_rows = [{"aiBrId": row["aiBrId"],
                      "scheduledBatchId": "001" if row["aiBrId"] in {
                          "REQ-SRC-ATL105-PDF-001:001", "REQ-SRC-ATL105-PDF-001:1937",
                          "REQ-SRC-ATL105-PDF-001:3251", "REQ-SRC-ATL105-PDF-001:6098"} else "",
                      "screeningStatus": "PRELIMINARY_OPEN" if row["aiBrId"] in prior_records else "NOT_SCREENED",
                      "fullChainReviewStatus": "NOT_COMPLETED",
                      "semanticMatchStatus": "NOT_ASSESSED",
                      "priorEvidenceFile": prior_records.get(row["aiBrId"], {}).get("sourceFile", "")}
                     for row in register_rows]
    pending_index = 0
    for row in progress_rows:
        if not row["scheduledBatchId"]:
            row["scheduledBatchId"] = str(2 + pending_index // 100).zfill(3)
            pending_index += 1
    write_csv(output_dir / "review-progress.csv", progress_rows, list(progress_rows[0]))
    write_jsonl_gzip(output_dir / "prior-preliminary-evidence.jsonl.gz", prior_records.values())
    write_jsonl_gzip(output_dir / "test-chain-evidence.jsonl.gz", (
        {"testBrId": identifier, "br": next(row for row in test_package["businessRequirements"]
                                           if row["id"].removeprefix("BR-RULE-") == identifier),
         "scenarios": [row for row in test_package["testScenarios"] if row["id"] in rule["scenarioIds"]],
         "cases": [row for row in test_package["testCases"] if row["id"] in rule["caseIds"]],
         "data": [row for row in test_package["testData"] if row["id"] in rule["dataIds"]]}
        for identifier, rule in test_rules.items()))
    (output_dir / "review-builder-implementation.py").write_bytes(Path(__file__).read_bytes())

    counts = {
        "aiBusinessRequirements": len(register_rows),
        "testBusinessRequirements": len(test_rules),
        "sourceDerivedTestBaseline": baseline.get("ruleCount"),
        "cataloguedTestAdditions": sum(row["id"].startswith("BR-RULE-") for row in test_package["businessRequirements"]) - baseline.get("ruleCount"),
        "authoredTestBrRecords": sum(not row["id"].startswith("BR-RULE-") for row in test_package["businessRequirements"]),
        "rankedCandidatePairs": len(candidate_rows),
        "topK": top_k,
        "aiScenarioApprovalRequired": False,
        "semanticMatches": 0,
        "preliminaryScreenedAiBrs": len(prior_records),
        "notYetScreenedAiBrs": len(register_rows) - len(prior_records),
        "fullChainReviewsCompleted": 0,
        **dict(sorted(metrics.items())),
    }
    (output_dir / "summary.json").write_text(json.dumps({
        "artifact": "ALL-AI-BR-CHAIN-TO-TEST-BR-REVIEW-QUEUE",
        "population": counts,
        "inputSha256": source_hashes,
        "retrievalPolicy": "Every independent Test package BR is scored for every AI BR using statement, expected behavior, AI chain language, Test chain metadata, and shared source anchors. Catalog and authored Test BRs retain separate identities; they are not assumed to represent distinct atomic obligations. Top-K nonzero retrieval leads are retained, together with every v6 source-anchor lead. Ranking is not exhaustive semantic review.",
        "reviewPolicy": "Candidates are retrieval leads only. Scores rank work and are not match confidence. Every AI BR is present even when no lead is found; those rows require direct review against the full Test BR catalog. No semantic equivalence, coverage, producer approval, SME approval, or execution is inferred.",
        "aiChainPolicy": "Scenario candidate catalog is used without an approval gate. AI scenario, test-case, and v6 physical-assessment links are retained as source evidence; source approval state is not semantic validation.",
        "files": ["all-ai-br-review-register.csv", "ai-test-br-ranked-review-leads.csv", "ai-br-chain-evidence.jsonl.gz", "test-chain-evidence.jsonl.gz", "review-progress.csv", "prior-preliminary-evidence.jsonl.gz", "review-builder-implementation.py", "00-START-HERE.md"],
    }, indent=2, ensure_ascii=True), encoding="utf-8")
    readme = (
        "# All-AI-BR Chain-to-Test-BR Review Queue\n\n"
        "This queue contains one register row and one chain-evidence record for every AI BR in the 6,880-row candidate catalog. "
        "The candidate file ranks up to the configured top-K Test BR retrieval leads for each AI BR and retains every existing v6 source-anchor lead.\n\n"
        "All Test package BR records, including authored BRs outside the catalog, are searched. `test-chain-evidence.jsonl.gz` retains their direct scenario/case/data records. `review-progress.csv` consolidates prior preliminary screenings; batches are incomplete until every assigned record is reviewed. No full-chain review is marked complete by this builder.\n\n"
        "## Review order\n\n"
        "1. Start with `all-ai-br-review-register.csv`; filter by `reviewDisposition`, category, chain status, or source page.\n"
        "2. For each AI BR, inspect its linked scenarios, cases, and physical-evidence paths in `ai-br-chain-evidence.jsonl.gz`.\n"
        "3. Compare each ranked Test BR lead in `ai-test-br-ranked-review-leads.csv` against the full BR obligation and chain context. The Test package currently marks its catalogued rules `DRAFT_REVIEW_REQUIRED`; treat them as candidate targets, not approved coverage.\n"
        "4. Record adjudication separately. `retrievalScore` is prioritization only; all semantic statuses remain `NOT_ASSESSED`. A row with no retrieval lead still requires a direct review across all Test BRs.\n\n"
        "AI scenario approval is not required for inclusion. AI physical evidence paths and frozen inventory hashes are references to inspect, not independent proof of payload semantics.\n"
    )
    (output_dir / "00-START-HERE.md").write_text(readme, encoding="utf-8")
    manifest = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file()}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": manifest}, indent=2), encoding="utf-8")
    print(json.dumps({"outputDirectory": str(output_dir), "population": counts}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--delivery-root", type=Path)
    parser.add_argument("--review-root", type=Path)
    parser.add_argument("--test-baseline", type=Path)
    parser.add_argument("--test-package", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--top-k", type=int, default=10)
    parser.add_argument("--prior-review-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        test_self_similarity()
        return
    if not all((args.delivery_root, args.review_root, args.test_baseline, args.test_package, args.output_dir)):
        parser.error("Supply delivery root, frozen v6 review root, independent Test baseline/package and new output directory")
    if args.top_k < 1:
        parser.error("--top-k must be at least 1")
    build(args.delivery_root, args.review_root, args.test_baseline, args.test_package, args.output_dir, args.top_k, args.prior_review_dir)


if __name__ == "__main__":
    main()
