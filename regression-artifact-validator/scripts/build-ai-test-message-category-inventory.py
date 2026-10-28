import argparse
import csv
import hashlib
import json
import re
from collections import Counter, defaultdict
from pathlib import Path

import ijson


FIELDS = [
    "artifactSet", "artifactKind", "artifactId", "sourcePath", "parentIds",
    "messageFamily", "messageDirection", "posTransactionCode", "posTransactionCategory",
    "cardTypeCode", "cardCategory", "networkType", "businessCategory", "requirementType",
    "scenarioType", "outcomeType", "segmentScope", "segmentEvidence", "categoryStatus",
    "categoryBasis",
]

POS_TRANSACTION_TYPES = {"3": "POS_AUTHORIZATION_ONLY"}
CARD_TYPES = {"020": "CREDIT_CARD"}


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def stream_array(path, key):
    with path.open("rb") as stream:
        yield from ijson.items(stream, key + ".item", use_float=True)


def json_cell(value):
    return json.dumps(value, ensure_ascii=False, sort_keys=True, separators=(",", ":"))


def add_values(target, value):
    if value is None or value == "":
        return
    if isinstance(value, str):
        target.add(value.strip())
    elif isinstance(value, (int, float)):
        target.add(str(value))
    elif isinstance(value, (list, tuple, set)):
        for item in value:
            add_values(target, item)


def refs(row, plural, singular):
    result = set()
    add_values(result, row.get(plural))
    add_values(result, row.get(singular))
    return result


def message_direction(families):
    directions = set()
    for family in families:
        normalized = family.lower()
        has_request = "request" in normalized
        has_response = "response" in normalized
        if has_request and has_response:
            directions.add("MIXED_OR_COMBINED")
        elif has_request:
            directions.add("REQUEST")
        elif has_response:
            directions.add("RESPONSE")
    if not directions:
        return "UNSPECIFIED"
    return next(iter(directions)) if len(directions) == 1 else "MIXED"


def family_group(family):
    value = family.lower()
    for phrase, label in (
        ("eca/telecheck", "ECA_TELECHECK"), ("fsa/hra", "FSA_HRA"),
        ("loyalty", "LOYALTY"), ("electronic mail", "ELECTRONIC_MAIL"),
        ("proprietary data load", "PROPRIETARY_DATA_LOAD"), ("totals", "TOTALS"),
        ("emv", "EMV"), ("financial transaction", "FINANCIAL_TRANSACTION"),
        ("key load", "KEY_LOAD"), ("table load", "TABLE_LOAD"),
    ):
        if phrase in value:
            return label
    return "OTHER_EXPLICIT_FAMILY"


def parse_prompt_code(value):
    if not isinstance(value, str):
        return None, None
    value = value.strip()
    if len(value) not in (3, 4) or not value.isalnum():
        return None, None
    if len(value) == 4:
        return value[0], value[1:]
    return value[0], value[1:]


def prompt_codes(node):
    found = set()
    def walk(value):
        if isinstance(value, dict):
            for key, child in value.items():
                normalized = re.sub(r"[^a-z]", "", key.lower())
                if normalized == "promptcode":
                    raw = child.get("value") if isinstance(child, dict) else child
                    if raw is not None:
                        found.add(str(raw))
                walk(child)
        elif isinstance(value, list):
            for child in value:
                walk(child)
    walk(node)
    return found


def prompt_columns(codes):
    transaction_codes, card_codes = set(), set()
    for code in codes:
        transaction, card = parse_prompt_code(code)
        if transaction:
            transaction_codes.add(transaction)
            card_codes.add(card)
    transaction_categories = {
        POS_TRANSACTION_TYPES.get(code, "POS_TRANSACTION_CODE_" + code + "_UNMAPPED")
        for code in transaction_codes
    }
    card_categories = {CARD_TYPES.get(code, "CARD_TYPE_CODE_" + code + "_UNMAPPED") for code in card_codes}
    return transaction_codes, transaction_categories, card_codes, card_categories


def segment_values(row, *, tc_observed=False, anchors=None):
    segments = set()
    bases = set()
    for key in ("segment_number", "segmentNumber"):
        if row.get(key) not in (None, ""):
            segments.add(str(row[key]).strip().upper())
            bases.add(key)
    for key in ("segment_ids", "segmentIds", "scope_segments", "scopeSegments"):
        values = row.get(key) or []
        values = [values] if isinstance(values, str) else values
        for value in values:
            text = str(value).strip()
            match = re.fullmatch(r"ENT-SEG-(.+)", text, re.IGNORECASE)
            if match:
                segments.add(match.group(1).strip().upper())
                bases.add(key)
            elif text:
                segments.add(text.upper())
                bases.add(key)
    for value in row.get("related_entity_ids") or []:
        match = re.fullmatch(r"ENT-SEG-(.+)", str(value), re.IGNORECASE)
        if match:
            segments.add(match.group(1).strip().upper())
            bases.add("related_entity_ids")
    for anchor in anchors or row.get("sourceAnchors") or row.get("sourceAnchor") or []:
        if isinstance(anchor, dict) and anchor.get("segment") not in (None, ""):
            segments.add(str(anchor["segment"]).strip().upper())
            bases.add("sourceAnchors")
    if tc_observed:
        presence = row.get("segment_presence") or {}
        if isinstance(presence, dict):
            segments.update(str(key).strip().upper() for key in presence if str(key).strip())
            if presence:
                bases.add("segment_presence_observed_on_TC_not_BR_applicability")
        forced = row.get("forced_segments") or []
        for value in forced:
            segments.add(str(value).strip().upper())
        if forced:
            bases.add("forced_segments_observed_on_TC")
    return sorted(segments), sorted(bases)


def make_row(artifact_set, kind, identifier, source, *, parent_ids=None, families=None,
             prompt_values=None, network=None, business_category=None, requirement_type=None,
             scenario_type=None, outcome_type=None, segments=None, segment_basis=None,
             category_basis=None, explicit_status=None):
    families = sorted({str(value).strip() for value in (families or []) if str(value).strip()})
    codes = prompt_values or set()
    tx_codes, tx_categories, card_codes, card_categories = prompt_columns(codes)
    basis = set(category_basis or [])
    if families:
        basis.add("explicit_or_linked_message_family")
    if codes:
        basis.add("raw_prompt_code_from_artifact")
    if business_category or requirement_type or scenario_type or outcome_type:
        basis.add("artifact_domain_or_test_type")
    if segments:
        basis.add("segment_scope_evidence")
    if explicit_status:
        status = explicit_status
    elif families or codes:
        status = "EXPLICIT_OR_CHAIN_DERIVED"
    elif segments or business_category or requirement_type or scenario_type or outcome_type:
        status = "SCOPE_OR_DOMAIN_ONLY_NO_MESSAGE_FAMILY"
    else:
        status = "MESSAGE_CATEGORY_NOT_DECLARED"
    return {
        "artifactSet": artifact_set,
        "artifactKind": kind,
        "artifactId": str(identifier),
        "sourcePath": str(source),
        "parentIds": json_cell(sorted(set(parent_ids or []))),
        "messageFamily": json_cell(families),
        "messageDirection": message_direction(families),
        "posTransactionCode": json_cell(sorted(tx_codes)),
        "posTransactionCategory": json_cell(sorted(tx_categories)),
        "cardTypeCode": json_cell(sorted(card_codes)),
        "cardCategory": json_cell(sorted(card_categories)),
        "networkType": network or "",
        "businessCategory": business_category or "",
        "requirementType": requirement_type or "",
        "scenarioType": scenario_type or "",
        "outcomeType": outcome_type or "",
        "segmentScope": json_cell(sorted(set(segments or []))),
        "segmentEvidence": json_cell(sorted(set(segment_basis or []))),
        "categoryStatus": status,
        "categoryBasis": json_cell(sorted(basis)),
    }


def generate(ai_root, test_package_path, output_dir):
    ai_root = Path(ai_root).resolve()
    test_package_path = Path(test_package_path).resolve()
    output_dir = Path(output_dir).resolve()
    if output_dir.exists():
        raise ValueError(f"Output directory must be new: {output_dir}")
    br_path = ai_root / "step5_requirements/approved/requirement_catalog.json"
    ts_path = ai_root / "scenarios/approved/approved_scenarios.json"
    tc_path = ai_root / "test_generation/test_generation/candidates/test_case_candidates.json"
    td_root = ai_root / "test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data"
    for path in (br_path, ts_path, tc_path, td_root, test_package_path):
        if not path.exists():
            raise FileNotFoundError(path)
    test_package = read_json(test_package_path)
    test_collection_paths = {
        "TEST_BR": ("businessRequirements", "businessRequirements"),
        "TEST_TS": ("testScenarios", "testScenarios"),
        "TEST_TC": ("testCases", "testCases"),
        "TEST_TD": ("testData", "testData"),
    }

    ai_scenarios = list(stream_array(ts_path, "scenarios"))
    ai_cases = list(stream_array(tc_path, "test_cases"))
    scenario_by_id = {str(row.get("id")): row for row in ai_scenarios}
    case_by_id = {str(row.get("id")): row for row in ai_cases}
    cases_by_scenario = defaultdict(list)
    for case in ai_cases:
        cases_by_scenario[str(case.get("scenario_id") or "")].append(case)
    scenarios_by_br = defaultdict(list)
    for scenario in ai_scenarios:
        for br_id in refs(scenario, "requirement_ids", "requirement_id"):
            scenarios_by_br[br_id].append(scenario)

    rows = []
    ai_br_ids = set()
    for br in stream_array(br_path, "requirements"):
        identifier = str(br.get("id") or "")
        if not identifier or identifier in ai_br_ids:
            raise ValueError("AI BR missing or duplicate ID: " + identifier)
        ai_br_ids.add(identifier)
        scenarios = scenarios_by_br.get(identifier, [])
        linked_cases = [case for scenario in scenarios for case in cases_by_scenario.get(str(scenario.get("id")), [])]
        families = {str(br.get("target_transaction"))} if br.get("target_transaction") else set()
        bases = {"AI_BR.target_transaction"} if families else set()
        for scenario in scenarios:
            if scenario.get("target_transaction"):
                families.add(str(scenario["target_transaction"]))
                bases.add("linked_AI_TS.target_transaction")
        prompts = set()
        for case in linked_cases:
            if case.get("transaction_type"):
                families.add(str(case["transaction_type"]))
                bases.add("linked_AI_TC.transaction_type")
            prompts.update(prompt_codes(case.get("request") or {}))
        segments, segment_basis = segment_values(br)
        rows.append(make_row("AI", "AI_BR", identifier, "step5_requirements/approved/requirement_catalog.json",
                             parent_ids=[x.get("id") for x in scenarios], families=families,
                             prompt_values=prompts, business_category=br.get("category"),
                             requirement_type=br.get("requirement_type"), segments=segments,
                             segment_basis=segment_basis, category_basis=bases))

    ai_scenario_ids = set()
    for scenario in ai_scenarios:
        identifier = str(scenario.get("id") or "")
        if not identifier or identifier in ai_scenario_ids:
            raise ValueError("AI TS missing or duplicate ID: " + identifier)
        ai_scenario_ids.add(identifier)
        linked_cases = cases_by_scenario.get(identifier, [])
        families = {str(scenario.get("target_transaction"))} if scenario.get("target_transaction") else set()
        bases = {"AI_TS.target_transaction"} if families else set()
        prompts = set()
        for case in linked_cases:
            if case.get("transaction_type"):
                families.add(str(case["transaction_type"]))
                bases.add("linked_AI_TC.transaction_type")
            prompts.update(prompt_codes(case.get("request") or {}))
        segments, segment_basis = segment_values(scenario)
        rows.append(make_row("AI", "AI_TS", identifier, "scenarios/approved/approved_scenarios.json",
                             parent_ids=refs(scenario, "requirement_ids", "requirement_id"),
                             families=families, prompt_values=prompts,
                             scenario_type=scenario.get("type"), segments=segments,
                             segment_basis=segment_basis, category_basis=bases))

    ai_case_ids = set()
    for case in ai_cases:
        identifier = str(case.get("id") or "")
        if not identifier or identifier in ai_case_ids:
            raise ValueError("AI TC missing or duplicate ID: " + identifier)
        ai_case_ids.add(identifier)
        segments, segment_basis = segment_values(case, tc_observed=True)
        rows.append(make_row("AI", "AI_TC", identifier,
                             "test_generation/test_generation/candidates/test_case_candidates.json",
                             parent_ids=[case.get("scenario_id"), *refs(case, "requirement_ids", "requirement_id")],
                             families=[case.get("transaction_type")], prompt_values=prompt_codes(case.get("request") or {}),
                             scenario_type=case.get("scenario_type"), outcome_type=(case.get("verification") or {}).get("status"),
                             segments=segments, segment_basis=segment_basis,
                             category_basis={"AI_TC.transaction_type"} if case.get("transaction_type") else set()))

    metadata_by_filename = {}
    for path in sorted(td_root.glob("*.meta.json")):
        try:
            metadata_by_filename[path.name] = read_json(path)
        except (ValueError, UnicodeError):
            metadata_by_filename[path.name] = {}
    for path in sorted(td_root.glob("*.json")):
        if path.name == "traceability.json" or path.name.endswith(".meta.json"):
            continue
        meta = metadata_by_filename.get(path.stem + ".meta.json", {})
        case_id = str(meta.get("testCaseId") or "")
        case = case_by_id.get(case_id, {})
        root_keys = []
        try:
            payload = read_json(path)
            if isinstance(payload, dict):
                root_keys = [str(key) for key in payload]
        except (ValueError, UnicodeError):
            pass
        family = meta.get("messageFamily") or case.get("transaction_type")
        source = path.relative_to(ai_root).as_posix()
        rows.append(make_row("AI", "AI_TD_PAYLOAD", path.name, source,
                             parent_ids=[case_id, meta.get("scenarioId")],
                             families=[family] if family else [],
                             scenario_type=case.get("scenario_type"),
                             segments=root_keys,
                             segment_basis=["payload_root_keys_not_BR_applicability"] if root_keys else [],
                             category_basis={"metadata.messageFamily"} if meta.get("messageFamily") else
                             ({"linked_AI_TC.transaction_type"} if case.get("transaction_type") else set())))
    for name, meta in sorted(metadata_by_filename.items()):
        case_id = str(meta.get("testCaseId") or "")
        case = case_by_id.get(case_id, {})
        source_path = td_root / name
        rows.append(make_row("AI", "AI_TD_METADATA", name, source_path.relative_to(ai_root).as_posix(),
                             parent_ids=[case_id, meta.get("scenarioId")],
                             families=[meta.get("messageFamily") or case.get("transaction_type")],
                             scenario_type=case.get("scenario_type"),
                             segments=meta.get("dataSections", {}).get("segments", []) if isinstance(meta.get("dataSections"), dict) else [],
                             segment_basis=["metadata.dataSections"] if meta.get("dataSections") else [],
                             category_basis={"metadata.messageFamily"} if meta.get("messageFamily") else
                             ({"linked_AI_TC.transaction_type"} if case.get("transaction_type") else set())))

    test_package = read_json(test_package_path)
    test_br_by_id = {str(x.get("id")): x for x in test_package.get("businessRequirements") or [] if x.get("id")}
    test_scenarios = test_package.get("testScenarios") or []
    test_cases = test_package.get("testCases") or []
    test_data = test_package.get("testData") or []
    for br in test_package.get("businessRequirements") or []:
        identifier = str(br.get("id") or "")
        anchors = br.get("sourceAnchors") or br.get("sourceAnchor") or []
        anchors = [anchors] if isinstance(anchors, dict) else anchors
        segments, segment_basis = segment_values({}, anchors=anchors)
        code = str(br.get("transactionTypeCode") or "")
        rows.append(make_row("TEST_SOLUTION", "TEST_BR", identifier, "complete-all-test-solution-br-ts-tc-td-package.json",
                             families=[], prompt_values=set(), network=br.get("networkType"),
                             business_category=br.get("codeKind") or br.get("scope"),
                             outcome_type=br.get("status"), segments=segments, segment_basis=segment_basis,
                             category_basis={"testBR.sourceAnchors"} if anchors else set(),
                             explicit_status="SEGMENT_OR_RULE_SCOPE_ONLY_MESSAGE_FAMILY_NOT_DECLARED" if segments else "MESSAGE_CATEGORY_NOT_DECLARED"))
        if code:
            rows[-1]["posTransactionCode"] = json_cell([code])
            rows[-1]["posTransactionCategory"] = json_cell([POS_TRANSACTION_TYPES.get(code, "POS_TRANSACTION_TYPE_CODE_" + code + "_UNMAPPED")])
            rows[-1]["categoryBasis"] = json_cell(sorted(set(json.loads(rows[-1]["categoryBasis"])) | {"testBR.transactionTypeCode"}))

    test_scenario_by_id = {str(x.get("id")): x for x in test_scenarios if x.get("id")}
    for scenario in test_scenarios:
        identifier = str(scenario.get("id") or "")
        anchors = scenario.get("sourceAnchors") or []
        segments, segment_basis = segment_values({}, anchors=anchors)
        rows.append(make_row("TEST_SOLUTION", "TEST_TS", identifier, "complete-all-test-solution-br-ts-tc-td-package.json",
                             parent_ids=scenario.get("requirementIds") or [], families=[],
                             scenario_type=scenario.get("scenarioType") or scenario.get("scenarioDefinitionStatus"),
                             outcome_type=scenario.get("status"), segments=segments, segment_basis=segment_basis,
                             category_basis={"testTS.sourceAnchors"} if anchors else set(),
                             explicit_status="SEGMENT_OR_RULE_SCOPE_ONLY_MESSAGE_FAMILY_NOT_DECLARED" if segments else "MESSAGE_CATEGORY_NOT_DECLARED"))
    for case in test_cases:
        identifier = str(case.get("id") or "")
        anchors = case.get("sourceAnchors") or []
        segments, segment_basis = segment_values({}, anchors=anchors)
        rows.append(make_row("TEST_SOLUTION", "TEST_TC", identifier, "complete-all-test-solution-br-ts-tc-td-package.json",
                             parent_ids=case.get("scenarioIds") or [], families=[],
                             business_category=case.get("category"), scenario_type=case.get("candidatePolarity"),
                             outcome_type=case.get("expectedOutcome") or case.get("status"),
                             segments=segments, segment_basis=segment_basis,
                             category_basis={"testTC.category"} if case.get("category") else set(),
                             explicit_status="SEGMENT_OR_RULE_SCOPE_ONLY_MESSAGE_FAMILY_NOT_DECLARED" if segments else "MESSAGE_CATEGORY_NOT_DECLARED"))
    for data in test_data:
        identifier = str(data.get("id") or "")
        anchors = data.get("sourceAnchors") or []
        segments, segment_basis = segment_values({}, anchors=anchors)
        if data.get("segment"):
            segments = sorted(set(segments) | {str(data["segment"]).upper()})
            segment_basis = sorted(set(segment_basis) | {"testTD.segment"})
        rows.append(make_row("TEST_SOLUTION", "TEST_TD", identifier, "complete-all-test-solution-br-ts-tc-td-package.json",
                             parent_ids=data.get("testCaseIds") or [], families=[],
                             business_category=data.get("fixtureKind") or data.get("producer"),
                             outcome_type=data.get("expectedValidation") or data.get("readiness"),
                             segments=segments, segment_basis=segment_basis,
                             category_basis={"testTD.fixtureKind"} if data.get("fixtureKind") else set(),
                             explicit_status="SEGMENT_OR_RULE_SCOPE_ONLY_MESSAGE_FAMILY_NOT_DECLARED" if segments else "MESSAGE_CATEGORY_NOT_DECLARED"))

    output_dir.mkdir(parents=True)
    csv_path = output_dir / "artifact-message-category-register.csv"
    with csv_path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=FIELDS)
        writer.writeheader()
        writer.writerows(sorted(rows, key=lambda row: (row["artifactSet"], row["artifactKind"], row["artifactId"])))

    kind_counts = Counter(row["artifactSet"] + ":" + row["artifactKind"] for row in rows)
    status_counts = Counter(row["categoryStatus"] for row in rows)
    family_counts = Counter(family for row in rows for family in json.loads(row["messageFamily"]))
    summary = {
        "artifact": "ATL105-AI-TEST-MESSAGE-CATEGORY-INVENTORY",
        "policy": "Raw message-family labels and codes are preserved; linked categories are derived only through explicit chain IDs. Segment scope, BR domain type, and message family are separate dimensions. Missing evidence is NOT_DECLARED, never guessed.",
        "aiSourceRoot": str(ai_root),
        "testPackage": str(test_package_path),
        "artifactRowCount": len(rows),
        "countsByArtifactSetAndKind": dict(sorted(kind_counts.items())),
        "countsByCategoryStatus": dict(sorted(status_counts.items())),
        "messageFamilies": [
            {"family": family, "count": count}
            for family, count in family_counts.most_common()
        ],
        "posTransactionCodeMappings": {"3": "POS_AUTHORIZATION_ONLY (user-confirmed mapping)", "all other codes": "retained raw; category not mapped by this inventory"},
        "cardTypeMappings": {"020": "CREDIT_CARD (user-confirmed mapping)", "all other codes": "retained raw; category not mapped by this inventory"},
        "limitations": [
            "AI message families can be inherited from explicitly linked scenarios/test cases; that does not mean every linked family semantically applies to the BR.",
            "AI TC segment_presence and payload roots are message/test-data observations, not BR applicability anchors.",
            "The independent Test package mostly records rule/source-anchor scope and does not declare transaction families; these rows are intentionally marked as not declared rather than guessed.",
            "No semantic match, test adequacy, or host execution is inferred.",
            "Only one canonical AI payload/metadata directory is inventoried; duplicate package copies are not double-counted.",
        ],
        "files": [csv_path.name],
    }
    summary_path = output_dir / "message-category-summary.json"
    summary_path.write_text(json.dumps(summary, indent=2, ensure_ascii=False), encoding="utf-8")
    md = [
        "# ATL105 AI and Test Message-Category Inventory", "",
        "This inventory adds message family/direction and POS/card-code fields while keeping them separate from requirement type and segment scope.",
        "Missing Test-side transaction-family metadata is explicitly `NOT_DECLARED`; it is not inferred from a BR title or segment alone.", "",
        f"Rows: **{len(rows):,}**.", "", "## Rows by artifact type", "",
        "| Artifact set and kind | Rows |", "|---|---:|",
    ]
    for kind, count in sorted(kind_counts.items()):
        md.append(f"| {kind} | {count:,} |")
    md.extend(["", "## Category status", "", "| Status | Rows |", "|---|---:|"])
    for status, count in sorted(status_counts.items()):
        md.append(f"| {status} | {count:,} |")
    md.extend([
        "", "## Classification rules", "",
        "- `messageFamily` retains the exact source transaction-family string; linked family values are identified by `categoryBasis`.",
        "- `messageDirection` is parsed from explicit request/response wording only.",
        "- `posTransactionCategory` maps only transaction code `3` to `POS_AUTHORIZATION_ONLY`; other codes remain unmapped raw codes.",
        "- `cardCategory` maps only card type `020` to `CREDIT_CARD`; other codes remain unmapped.",
        "- `segmentScope` records explicit anchors, metadata/payload observations, or test-package scopes. `segmentEvidence` distinguishes them.",
        "- `businessCategory`, `requirementType`, and `scenarioType` are not substituted for message family.",
        "- This is categorization metadata, not semantic adjudication or approval.",
        "", "See `artifact-message-category-register.csv` for the complete AI BR/TS/TC/TD and Test BR/TS/TC/TD inventory.",
    ])
    (output_dir / "README.md").write_text("\n".join(md) + "\n", encoding="utf-8")
    print(json.dumps({"outputDirectory": str(output_dir), "artifactRows": len(rows),
                      "countsByArtifactSetAndKind": dict(kind_counts),
                      "countsByCategoryStatus": dict(status_counts),
                      "csv": str(csv_path), "summary": str(summary_path)}, indent=2, ensure_ascii=False))


def self_test():
    assert message_direction(["Financial Transaction Request"]) == "REQUEST"
    assert message_direction(["Financial Transaction Response"]) == "RESPONSE"
    assert parse_prompt_code("3020") == ("3", "020")
    row = make_row("AI", "AI_TC", "TC1", "test_case.json", families=["Financial Transaction Request"],
                   prompt_values={"3020"}, segments=["100"])
    assert row["posTransactionCategory"] == '["POS_AUTHORIZATION_ONLY"]'
    assert row["cardCategory"] == '["CREDIT_CARD"]'
    assert row["categoryStatus"] == "EXPLICIT_OR_CHAIN_DERIVED"
    print("PASS: message family, direction, POS intent, card type, and segment scope stay separate")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--ai-root", type=Path)
    parser.add_argument("--test-package", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.ai_root, args.test_package, args.output_directory)):
        generate(args.ai_root, args.test_package, args.output_directory)
    else:
        parser.error("--ai-root, --test-package, and --output-directory are required")


if __name__ == "__main__":
    main()