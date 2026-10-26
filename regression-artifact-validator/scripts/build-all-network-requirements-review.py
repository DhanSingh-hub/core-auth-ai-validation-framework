"""Publish a review-only ATL105 network/program, transaction and lifecycle supplement."""

import argparse
import importlib.util
import re
from pathlib import Path


HELPER_PATH = Path(__file__).with_name("build-mastercard-reference-review.py")
SPEC = importlib.util.spec_from_file_location("mastercard_review_helpers", HELPER_PATH)
assert SPEC is not None and SPEC.loader is not None
shared = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(shared)
PACK = shared.PACK
INPUT = PACK / "docs" / "specs" / "kb" / "segment-100" / "all-network-requirements-review.json"
OUTPUT = PACK / "test-output" / "test-solution-independent-review" / "all-network-requirements-review-v1"
STATUS = "DRAFT_REVIEW_REQUIRED"
TRANSACTION_CODES = set("03456789ABCDEKLMNQSTUVZ")
STANDARD_CODES = set("0345678ABCSUZ")
GENERAL_PAIRS = {
    "AUTH_COMPLETION": [["3", "0"], ["5", "0"], ["B", "0"]],
    "AUTH_CANCELLATION": [["3", "S"], ["5", "S"], ["B", "S"]],
    "SALE_REVERSAL": [["0", "8"], ["4", "8"], ["6", "C"]],
    "REFUND_VOID": [["7", "U"]],
    "TIMEOUT_TOR_NEXT": [["0", "Z"], ["4", "Z"], ["6", "Z"]],
}
SPECIAL_FLOWS = {
    "SVC_CANCELLATION", "SVC_TIMEOUT", "EWIC_AUTH_COMPLETION",
    "EWIC_AUTH_CANCEL", "EWIC_PURCHASE_REVERSAL", "VISA_ESTIMATED_INCREMENTAL",
}
NETWORK_GROUPS = {"AMEX", "VISA", "MASTERCARD", "DISCOVER", "DINERS_CLUB", "JCB", "STAR_SIGNATURE_DEBIT"}
GENERIC_GROUPS = {"DEBIT", "EBT"}
NAVIGATION_DIMENSIONS = {
    "platform": ["BUYPASS"],
    "specificationVersion": ["ATL105/2026-3"],
    "cardProgram": ["CREDIT", "DEBIT", "PREPAID", "BENEFIT", "UNRESOLVED"],
    "commercialUsage": ["COMMERCIAL", "CONSUMER", "UNRESOLVED"],
    "transactionCategory": ["SALE", "AUTHORIZATION", "REVERSAL", "RETURN", "SETTLEMENT",
                            "PARTIAL_APPROVAL", "ALL_APPLICABLE"],
    "cardPresence": ["PRESENT", "NOT_PRESENT", "UNRESOLVED"],
    "entryMode": ["MANUAL", "MAGSTRIPE", "CONTACT_CHIP", "CONTACTLESS", "UNRESOLVED"],
    "channel": ["POS", "MOTO", "ECOMMERCE", "UNRESOLVED"],
}


def catalog_rows(source: dict, root: Path, prefix: str) -> list[dict]:
    rows = []
    path = (root / source["path"]).resolve()
    if not path.is_relative_to(root.resolve()):
        raise ValueError("Catalog source path escapes specification pack")
    for line in path.read_text(encoding="utf-8-sig").splitlines():
        columns = [value.strip().strip("`") for value in line.strip().strip("|").split("|")]
        if len(columns) == 4 and columns[0].startswith(prefix):
            rows.append({"br": columns[0], "id": columns[1], "name": columns[2],
                         "detail": columns[3], "anchor": line.strip()})
    shared.unique_index(rows, "id")
    return rows


def validate(document: dict, root: Path) -> tuple[dict, list[dict], list[dict]]:
    if (document["schemaVersion"] != 1 or document["specificationVersion"] != "2026-3"
            or document["status"] != STATUS or document["baselineChanged"] is not False):
        raise ValueError("Review schema/version/status cannot promote the baseline")
    if document["registerId"] != "SEG100-SME-004":
        raise ValueError("Unexpected communication-register identity")
    sources = dict(document["sources"])
    networks = catalog_rows(sources["taxonomy"], root, "BR-SEG100-SEC5-LEAF-")
    cards = catalog_rows(sources["cards"], root, "CARD-100-001-")
    if len(networks) != 16 or len(cards) != 36:
        raise ValueError("Expected all 16 Section 5 leaves and all 36 financial card codes")
    for rows, source_id, primary in ((networks, "taxonomy", "section5"), (cards, "cards", "financialTable")):
        for row in rows:
            identifier = f"{source_id}:{row['id']}"
            sources[identifier] = {**sources[source_id], "anchor": row["anchor"]}
            row["sources"] = [identifier, primary]
    receipts = shared.unique_index([{"id": row[0]} for row in document["receiptLabels"]], "id")
    if set(receipts) != {
        "AMEX", "DEBIT", "DINERS_CLUB", "DISCOVER", "EBT", "JCB",
        "MASTERCARD", "VISA", "STAR_SIGNATURE_DEBIT",
    }:
        raise ValueError("All nine receipt labels must be preserved as a separate dimension")
    for identifier, name, printed, anchor in document["receiptLabels"]:
        if not name.strip() or not printed.strip() or not anchor.endswith(printed):
            raise ValueError(f"Receipt label lacks a consistent mapping: {identifier}")
        sources[f"receipt:{identifier}"] = {**sources["receipt"], "anchor": anchor}
    transactions = shared.unique_index(
        [{"id": row[0], "scope": row[2]} for row in document["transactions"]], "id"
    )
    if set(transactions) != TRANSACTION_CODES:
        raise ValueError("All 23 exact Appendix G codes must be present")
    for code, name, scope, anchor in document["transactions"]:
        expected_scope = "STANDARD_FINANCIAL" if code in STANDARD_CODES else "SPECIALIZED"
        if scope != expected_scope or not name.strip() or not anchor.startswith(code + " "):
            raise ValueError(f"Invalid transaction classification: {code}")
        sources[f"transaction:{code}"] = {**sources["appendixG"], "anchor": anchor}
    prompts = shared.unique_index([{"id": row[0]} for row in document["specialPrompts"]], "id")
    if set(prompts) != {"900", "901", "902", "903", "904", "905", "981", "990", "995", "996", "997"}:
        raise ValueError("All eleven Appendix E special prompts are required")
    for code, name, anchor in document["specialPrompts"]:
        if not name.strip() or not anchor.startswith(code + " "):
            raise ValueError(f"Invalid special prompt: {code}")
        sources[f"special:{code}"] = {**sources["specialTable"], "anchor": anchor}
    resolved = shared.resolve_sources(sources, root)
    primary = (root / sources["financialTable"]["path"]).read_text(encoding="utf-8-sig")
    financial_body = primary.split(sources["financialTable"]["anchor"])[1].split(sources["specialTable"]["anchor"])[0]
    primary_codes = set(re.findall(r"^([0-9]{3}) ", financial_body, re.MULTILINE))
    if {row["id"] for row in cards} != primary_codes:
        raise ValueError("Knowledge-base financial card codes disagree with the primary Appendix E financial table")
    questions = shared.unique_index(document["questions"], "id")
    if set(questions) != {f"NET-Q{number:02}" for number in range(1, 11)}:
        raise ValueError("All ten grouped question identities are required")
    for question in questions.values():
        if any(not question[key].strip() for key in ("question", "owner", "evidenceNeeded")):
            raise ValueError(f"Incomplete question: {question['id']}")
    rules = shared.unique_index(document["rules"], "id")
    flows = shared.unique_index(document["flows"], "id")
    if set(flows) != GENERAL_PAIRS.keys() | SPECIAL_FLOWS:
        raise ValueError("Missing or unexpected lifecycle profile")
    contexts = {row["id"] for row in networks} | receipts.keys() | {"ALL"}
    used_questions = set()
    for kind, rows in (("rule", rules.values()), ("flow", flows.values())):
        for row in rows:
            if not row["sources"] or set(row["sources"]) - resolved.keys():
                raise ValueError(f"Unknown/missing source for {kind}: {row['id']}")
            if not row["questions"] or set(row["questions"]) - questions.keys():
                raise ValueError(f"Unknown/missing questions for {kind}: {row['id']}")
            used_questions.update(row["questions"])
            if kind == "rule":
                if not row["scope"] or set(row["scope"]) - contexts:
                    raise ValueError(f"Invalid rule context: {row['id']}")
                if ("ALL" in row["scope"] and row["scope"] != ["ALL"]
                        or not row["statement"].strip() or not row["checks"]
                        or any(not check.strip() for check in row["checks"])
                        or not row["expected"].strip()
                        or type(row.get("teaching", True)) is not bool):
                    raise ValueError(f"Incomplete or invalid rule: {row['id']}")
                if row["id"] in {"RELEASE-OVERLAY", "DOWNSTREAM-GATE", "RBC-LOOKUP",
                                 "TRANSARMOR-REGISTRATION"} and row.get("teaching", True):
                    raise ValueError("Release overlay/external contract cannot enter teaching content")
                if row.get("teaching", True) and "afOverlay" in row["sources"]:
                    raise ValueError("2026-4 overlay cannot enter 2026-3 teaching")
            else:
                if not row["scope"].strip() or not row["name"].strip() or len(row["legs"]) < 2:
                    raise ValueError(f"Incomplete flow: {row['id']}")
                if row["pairs"] != GENERAL_PAIRS.get(row["id"], []):
                    raise ValueError(f"Invalid or cross-paired lifecycle: {row['id']}")
    if used_questions != questions.keys():
        raise ValueError("Orphan questionnaire entry")
    for required in ("COMMON-IDENTITY", "COMMON-FIELDS", "COMMON-PROMPT", "CREDIT-EXPIRY",
                     "DEBIT-EXPIRY", "PINLESS-PROFILE", "EWIC-PROFILE", "SVC-VERIFY",
                     "SVC-RECEIPT", "TOR-ORDER", "VISA-AE", "RELEASE-OVERLAY", "DOWNSTREAM-GATE"):
        if required not in rules:
            raise ValueError(f"Missing safety-critical review rule: {required}")
    validate_navigation(document, networks, cards)
    return resolved, networks, cards


def validate_navigation(document: dict, categories: list[dict], cards: list[dict]) -> None:
    navigation = document["businessNavigation"]
    if (navigation["schemaVersion"] != 1 or navigation["platform"] != "BUYPASS"
            or navigation["specification"] != "ATL105" or navigation["version"] != "2026-3"
            or navigation["authority"] != "BA_DIAGRAM_STRUCTURE_ONLY_NOT_ELIGIBILITY"):
        raise ValueError("Unsupported business navigation baseline or authority")
    groups = shared.unique_index(navigation["groups"], "id")
    if (set(groups) != NETWORK_GROUPS | GENERIC_GROUPS
            or set(navigation["networkGroupIds"]) != NETWORK_GROUPS
            or len(navigation["networkGroupIds"]) != 7
            or set(navigation["genericGroupIds"]) != GENERIC_GROUPS
            or len(navigation["genericGroupIds"]) != 2):
        raise ValueError("Primary navigation must contain seven networks and two generic groups")
    profiles = shared.unique_index(navigation["profiles"], "id")
    rules = shared.unique_index(document["rules"], "id")
    flows = shared.unique_index(document["flows"], "id")
    card_ids = {row["id"] for row in cards}
    category_ids = {row["id"] for row in categories}
    used_profiles, used_topics, used_flows = set(), set(), set()
    for group in groups.values():
        kind = "NETWORK" if group["id"] in NETWORK_GROUPS else "GENERIC_CATEGORY"
        if group["kind"] != kind or not group["name"].strip() or not group["profiles"]:
            raise ValueError(f"Invalid network/generic group: {group['id']}")
        if len(set(group["profiles"])) != len(group["profiles"]):
            raise ValueError(f"Duplicate profile link: {group['id']}")
        for identifier in group["profiles"]:
            if identifier not in profiles or profiles[identifier]["groupId"] != group["id"]:
                raise ValueError(f"Unknown or cross-group profile: {identifier}")
            used_profiles.add(identifier)
    if used_profiles != profiles.keys():
        raise ValueError("Orphan business profile")
    for profile in profiles.values():
        if (profile["cardProgram"] not in NAVIGATION_DIMENSIONS["cardProgram"]
                or not profile["processingProfile"].strip()
                or profile["topicSet"] not in navigation["topicSets"]
                or set(profile["cardCodes"]) - card_ids
                or set(profile["taxonomyIds"]) - category_ids):
            raise ValueError(f"Invalid program/card/category profile: {profile['id']}")
        if (len(set(profile["cardCodes"])) != len(profile["cardCodes"])
                or len(set(profile["taxonomyIds"])) != len(profile["taxonomyIds"])):
            raise ValueError(f"Duplicate card/category link: {profile['id']}")
        topics = navigation["commonTopics"] + navigation["topicSets"][profile["topicSet"]] + profile["additionalTopics"]
        if len(set(topics)) != len(topics) or len(set(profile["flowIds"])) != len(profile["flowIds"]):
            raise ValueError(f"Duplicate topic/flow link: {profile['id']}")
        for identifier in topics:
            if identifier not in rules or identifier not in navigation["topicNavigation"]:
                raise ValueError(f"Unresolved business topic: {identifier}")
            scope = set(rules[identifier]["scope"])
            if "ALL" not in scope and not scope.intersection({profile["groupId"], *profile["taxonomyIds"]}):
                raise ValueError(f"Rule scope incompatible with profile: {identifier}/{profile['id']}")
            used_topics.add(identifier)
        for identifier in profile["flowIds"]:
            if identifier not in flows or identifier not in navigation["flowNavigation"]:
                raise ValueError(f"Unresolved business flow: {identifier}")
            if identifier.startswith("EWIC_") and profile["processingProfile"] != "EWIC":
                raise ValueError("eWIC flow cannot be assigned to a generic financial profile")
            if identifier == "VISA_ESTIMATED_INCREMENTAL" and profile["groupId"] != "VISA":
                raise ValueError("Visa Appendix AE cannot be copied to another network")
            if identifier in GENERAL_PAIRS and profile["cardProgram"] == "BENEFIT":
                raise ValueError("Benefit profiles need dedicated lifecycle source mappings")
            used_flows.add(identifier)
    if used_topics != navigation["topicNavigation"].keys() or used_flows != navigation["flowNavigation"].keys():
        raise ValueError("Orphan navigation topic/flow definition")
    for row in list(navigation["topicNavigation"].values()) + list(navigation["flowNavigation"].values()):
        if (row["category"] not in NAVIGATION_DIMENSIONS["transactionCategory"]
                or not row["role"].strip() or len(set(row["codes"])) != len(row["codes"])
                or set(row["codes"]) - TRANSACTION_CODES):
            raise ValueError("Invalid transaction-category/code/role navigation")


def business_view(document: dict, requirements: list[dict], sources: dict) -> dict:
    navigation = document["businessNavigation"]
    library = shared.unique_index(requirements, "id")
    profiles, contextual, scenarios = [], [], []
    flows = shared.unique_index(document["flows"], "id")
    assigned = set()
    for profile in navigation["profiles"]:
        classification_ids = [f"NET-REQ-CARD-{code}" for code in profile["cardCodes"]]
        classification_ids += [f"NET-REQ-TAX-{key}" for key in profile["taxonomyIds"]]
        classification_ids += [f"NET-REQ-RECEIPT-{profile['groupId']}"]
        topics = navigation["commonTopics"] + navigation["topicSets"][profile["topicSet"]] + profile["additionalTopics"]
        selections = [(key, navigation["topicNavigation"][key], f"NET-REQ-{key}") for key in topics]
        selections += [(f"FLOW-{key}", navigation["flowNavigation"][key], f"NET-REQ-FLOW-{key}")
                       for key in profile["flowIds"]]
        context_ids = []
        for key, selection, requirement_id in selections:
            source_rule = library[requirement_id]
            identifier = f"CTX-BR-{profile['id']}-{key}"
            scenario_id = identifier.replace("CTX-BR-", "CTX-TS-", 1)
            context = {
                "platform": "BUYPASS", "specification": "ATL105", "version": "2026-3",
                "cardProgram": profile["cardProgram"], "commercialUsage": "UNRESOLVED",
                "groupId": profile["groupId"],
                "network": profile["groupId"] if profile["groupId"] in NETWORK_GROUPS else None,
                "genericCategory": profile["groupId"] if profile["groupId"] in GENERIC_GROUPS else None,
                "processingProfile": profile["processingProfile"],
                "transactionCategory": selection["category"], "transactionCodes": selection["codes"],
                "messageFamily": "SOURCE_PROFILE_REQUIRED",
                "flowId": key.removeprefix("FLOW-") if key.startswith("FLOW-") else None,
                "lifecycleRole": selection["role"],
                "cardPresence": "UNRESOLVED", "entryMode": "UNRESOLVED", "channel": "UNRESOLVED",
                "candidateCardCodes": profile["cardCodes"],
                "eligibility": "REVIEW_REQUIRED", "mappingBasis": "CONDITIONAL_REVIEW_SELECTION_NOT_APPROVED_COMPATIBILITY",
            }
            validate_business_context(context)
            references = classification_ids + [f"NET-REQ-TX-{code}" for code in selection["codes"]]
            references += [requirement_id]
            evidence_ids = sorted({source for ref in references for source in library[ref]["sources"]})
            conditions = [
                f"Establish {profile['groupId']} identity independently of receipt text; for generic groups resolve the actual network/provider.",
                f"Confirm {profile['cardProgram']}/{profile['processingProfile']} processing and per-leg card/message eligibility.",
                "Resolve presence, entry mode, channel, environment and version; unresolved values are not wildcards.",
                source_rule["scope"],
            ]
            statement = (
                f"For the {profile['groupId']} / {profile['cardProgram']} / {profile['processingProfile']} "
                f"review context, only when independently evidenced applicability conditions hold: {source_rule['statement']}"
            )
            question_ids = sorted(set(source_rule["questions"] + ["NET-Q01", "NET-Q02"]))
            contextual.append({
                "id": identifier, "profileId": profile["id"], "context": context,
                "statement": statement, "conditions": conditions, "sharedRequirementId": requirement_id,
                "classificationReferenceIds": classification_ids,
                "transactionRequirementIds": [f"NET-REQ-TX-{code}" for code in selection["codes"]],
                "scenarioIds": [scenario_id], "questionIds": question_ids,
                "sourceEvidence": [{"sourceId": key, **sources[key]} for key in evidence_ids],
                "status": STATUS, "teaching": False,
                "teachingExclusion": "Contextual applicability is unapproved; only original curated shared lessons are teaching evidence.",
            })
            scenarios.append({
                "id": scenario_id, "requirementIds": [identifier], "sharedScenarioId": source_rule["scenarioId"],
                "intent": f"Review {source_rule['name']} in {profile['id']} with explicit applicability and exception evidence.",
                "preconditions": conditions, "validations": source_rule["checks"],
                "expectedResult": source_rule["expected"],
                "orderedLegs": flows[context["flowId"]]["legs"] if context["flowId"] else [],
                "sourceEvidence": [{"sourceId": key, **sources[key]} for key in source_rule["sources"]],
                "testCaseIds": [], "status": STATUS, "actualResult": "Not Executed",
                "chainGaps": ["ELIGIBILITY_AND_CONTEXT_EVIDENCE_REQUIRED", "CONCRETE_TC_NOT_AUTHORED",
                              "SYNTHETIC_TD_PAYLOAD_NOT_AUTHORED", "INDEPENDENT_OUTCOME_ORACLE_REQUIRED"],
            })
            context_ids.append(identifier)
            assigned.update(references)
        profiles.append({
            **profile, "mappingStatus": "REVIEW_REQUIRED", "classificationReferenceIds": classification_ids,
            "contextRequirementIds": context_ids,
            "unresolvedDimensions": ["commercialUsage", "cardPresence", "entryMode", "channel",
                                     "perLegEligibility", "hostOutcome", "PREPAID_and_COMMERCIAL_applicability"],
        })
    groups = []
    for group in navigation["groups"]:
        members = [row for row in contextual if row["context"]["groupId"] == group["id"]]
        groups.append({
            **group, "sourceEvidence": [{"sourceId": f"receipt:{group['id']}", **sources[f"receipt:{group['id']}"]}],
            "identityEvidenceScope": "SOURCE_NAMES_LABEL_NOT_RUNTIME_IDENTITY",
            "contextRequirementIds": [row["id"] for row in members],
            "sharedRequirementIds": sorted({ref for row in members
                                            for ref in [row["sharedRequirementId"], *row["classificationReferenceIds"],
                                                        *row["transactionRequirementIds"]]}),
            "draftContextRequirementCount": len(members),
            "draftScenarioCount": len(members), "authoredTestCaseCount": 0, "authoredTestDataCount": 0,
            "completeChainCount": 0, "status": STATUS,
            "gaps": ["NETWORK_OR_PROVIDER_ELIGIBILITY_POLICY_MISSING",
                     "PREPAID_COMMERCIAL_AND_CHANNEL_CONTEXTS_UNRESOLVED",
                     "NETWORK_SPECIFIC_SOURCE_COMPLETENESS_NOT_CERTIFIED",
                     "CONCRETE_TC_TD_AND_HOST_ORACLES_NOT_AUTHORED"],
        })
    tree = {"BUYPASS": {"ATL105/2026-3": {}}}
    for row in contextual:
        context = row["context"]
        node = tree["BUYPASS"]["ATL105/2026-3"]
        for value in (context["cardProgram"], context["groupId"], context["transactionCategory"],
                      context["cardPresence"], context["entryMode"], context["channel"],
                      context["flowId"] or context["lifecycleRole"]):
            node = node.setdefault(value, {})
        node.setdefault("requirementIds", []).append(row["id"])
        node.setdefault("scenarioIds", []).extend(row["scenarioIds"])
    return {
        "schemaVersion": 1, "authority": navigation["authority"], "status": STATUS,
        "navigationOrder": ["platform", "specificationVersion", "cardProgram", "networkOrGenericGroup",
                            "transactionCategory", "cardPresence", "entryMode", "channel", "flowAndLeg",
                            "BR", "TS", "TC", "TD"],
        "dimensionValues": NAVIGATION_DIMENSIONS,
        "dimensionSemantics": {
            "commercialUsage": "Orthogonal to CREDIT/DEBIT/PREPAID funding, not an exclusive fourth program.",
            "presentation": "Presence, entry mode and channel are separate; contactless is not a channel or a synonym for e-commerce.",
            "unresolved": "A review gap, never all-values eligibility.",
            "specifications": "ATL104, ISO and Gen2 in the BA diagram are not enabled by this ATL105 baseline.",
        },
        "businessTree": tree, "groups": groups, "profiles": profiles, "businessRequirements": contextual,
        "testScenarios": scenarios, "testCases": [], "testData": [],
        "sharedLibraryRequirementIds": sorted(library),
        "linkedSharedRequirementIds": sorted(assigned),
        "unmappedSharedRequirementIds": sorted(library.keys() - assigned),
        "unmappedDisposition": "SHARED_LIBRARY_PENDING_SOURCE_BACKED_GROUP_MAPPING_NOT_DROPPED",
        "limitations": navigation["limitations"],
        "completePackage": False, "completeChainCount": 0,
    }


def validate_business_context(context: dict) -> None:
    if context["specification"] != "ATL105" or context["version"] != "2026-3":
        raise ValueError("Business context cannot enable an unsupported specification/version")
    for key in ("platform", "cardProgram", "commercialUsage", "transactionCategory",
                "cardPresence", "entryMode", "channel"):
        if context[key] not in NAVIGATION_DIMENSIONS[key]:
            raise ValueError(f"Invalid business dimension: {key}")
    group_id = context["groupId"]
    if group_id in NETWORK_GROUPS:
        if context["network"] != group_id or context["genericCategory"] is not None:
            raise ValueError("Network context must preserve named network identity dimension")
    elif group_id in GENERIC_GROUPS:
        if context["network"] is not None or context["genericCategory"] != group_id:
            raise ValueError("Generic Debit/EBT must not become a named network")
    else:
        raise ValueError("Unknown primary network/generic group")
    if (context["eligibility"] != "REVIEW_REQUIRED"
            or context["mappingBasis"] != "CONDITIONAL_REVIEW_SELECTION_NOT_APPROVED_COMPATIBILITY"):
        raise ValueError("Review navigation cannot approve eligibility")
    if context["entryMode"] in {"CONTACTLESS", "CONTACT_CHIP", "MAGSTRIPE"} and context["cardPresence"] == "NOT_PRESENT":
        raise ValueError("Physical card entry contradicts card-not-present")
    if context["channel"] in {"ECOMMERCE", "MOTO"} and context["cardPresence"] == "PRESENT":
        raise ValueError("Remote channel contradicts card-present")
    if context["entryMode"] == "CONTACTLESS" and context["channel"] in {"ECOMMERCE", "MOTO"}:
        raise ValueError("Contactless entry is not a remote channel")


def render(document: dict, sources: dict, networks: list[dict], cards: list[dict]) -> dict[str, str]:
    requirements = []

    def add(identifier, name, statement, scope, evidence, questions, checks, expected, tier, lesson=True):
        requirements.append({
            "id": f"NET-REQ-{identifier}", "fr": f"NET-FR-{identifier}",
            "scenarioId": f"NET-TS-{identifier}", "name": name, "statement": statement,
            "scope": scope, "sources": evidence, "questions": questions,
            "checks": checks, "expected": expected, "validationLevel": tier,
            "status": STATUS, "actualResult": "Not Executed", "testDataReference": "NOT_AUTHORED",
            "teaching": lesson,
            "sourceEvidence": [{"sourceId": key, **sources[key]} for key in evidence],
        })

    for row in networks:
        add(f"TAX-{row['id']}", row["name"],
            f"Recognize {row['id']} as the Section 5 category '{row['name']}'. {row['detail']}",
            row["id"], row["sources"], ["NET-Q01", "NET-Q02"],
            ["Check the normalized category independently of card brand, card type and flow."],
            "Category recognition only; no brand or transaction mapping is inferred.", "CLASSIFICATION_ONLY")
    for row in cards:
        add(f"CARD-{row['id']}", row["name"],
            f"Recognize financial card code {row['id']} as '{row['name']}', preserving all three digits.",
            f"Financial card code {row['id']}", row["sources"], ["NET-Q01", "NET-Q02"],
            ["Select the financial Appendix E namespace; do not use Table Load descriptions."],
            "Financial card-code recognition only; business eligibility remains separately gated.",
            "CLASSIFICATION_ONLY")
    for identifier, name, printed, anchor in document["receiptLabels"]:
        add(f"RECEIPT-{identifier}", name, f"In the applicable receipt profile, {name} prints as {printed}.",
            identifier, [f"receipt:{identifier}"], ["NET-Q01", "NET-Q08"],
            ["Check the exact source-listed receipt label; do not use it as network identity evidence."],
            "Receipt label mapping only, not rendered receipt validation.", "CLASSIFICATION_ONLY")
    for code, name, scope, anchor in document["transactions"]:
        add(f"TX-{code}", name, f"Recognize exact Appendix G code {code} as {name} ({scope}).",
            "Transaction-code dimension only", [f"transaction:{code}"], ["NET-Q02", "NET-Q06"],
            ["Reject malformed/unknown code; keep specialized operations outside the legacy financial oracle."],
            "Code recognition only; no standalone, network or lifecycle eligibility is inferred.",
            "CLASSIFICATION_ONLY")
    for code, name, anchor in document["specialPrompts"]:
        add(f"SPECIAL-{code}", name,
            f"Recognize special three-character Prompt Code {code} as {name}; this is not a financial card code.",
            "Special/nonfinancial message namespace", [f"special:{code}"], ["NET-Q06"],
            ["Select the exact operation/message-family contract.",
             "Codes 900 and 997 remain blocked in existing harness coverage; no readiness is implied."],
            "Source-list classification only; no payload, totals, download or mail-operation outcome is inferred.",
            "CLASSIFICATION_ONLY")
    for flow in document["flows"]:
        add(f"FLOW-{flow['id']}", flow["name"],
            f"{flow['name']}: {' -> '.join(flow['legs'])}. {flow['scope']}.",
            flow["scope"], flow["sources"], flow["questions"],
            ["Check ordered roles, predecessor state, original references and context-specific eligibility.",
             f"Exact code pairs where established: {flow['pairs']}; an empty list is not a wildcard."],
            "Validate only the applicable source profile; unresolved policy/host evidence remains REVIEW_REQUIRED.",
            "CONDITIONAL_LIFECYCLE")
    for rule in document["rules"]:
        add(rule["id"], rule["id"].replace("-", " ").title(), rule["statement"], ";".join(rule["scope"]),
            rule["sources"], rule["questions"], rule["checks"], rule["expected"],
            "CONDITIONAL_SOURCE_RULE" if rule.get("teaching", True) else "EXTERNAL_REVIEW_GATE",
            rule.get("teaching", True))

    navigation = business_view(document, requirements, sources)
    contexts = navigation["groups"]

    def locators(keys):
        return " | ".join(f"{sources[key]['path']}:{sources[key]['line']} ({sources[key]['section']})" for key in keys)

    business_rows, matrix, gaps = [], [], []
    scenario_index = shared.unique_index(navigation["testScenarios"], "id")
    for row in navigation["businessRequirements"]:
        context = row["context"]
        scenario = scenario_index[row["scenarioIds"][0]]
        business_rows.append({
            "Group Kind": "NETWORK" if context["network"] else "GENERIC_CATEGORY",
            "Network / Generic Group": context["groupId"], "Platform": context["platform"],
            "Specification": context["specification"], "Version": context["version"],
            "Card Program": context["cardProgram"], "Commercial Usage": context["commercialUsage"],
            "Processing Profile": context["processingProfile"], "Profile ID": row["profileId"],
            "Transaction Category": context["transactionCategory"],
            "Transaction Codes (conditional)": ";".join(context["transactionCodes"]),
            "Candidate Card Codes (not eligibility)": ";".join(context["candidateCardCodes"]),
            "Card Presence": context["cardPresence"], "Entry Mode": context["entryMode"],
            "Channel": context["channel"], "Message Family": context["messageFamily"],
            "Flow ID": context["flowId"] or "", "Lifecycle Role": context["lifecycleRole"],
            "BR ID (contextual draft)": row["id"], "Business Requirement": row["statement"],
            "Shared Requirement ID": row["sharedRequirementId"],
            "Classification Reference IDs": ";".join(row["classificationReferenceIds"]),
            "Transaction Requirement IDs": ";".join(row["transactionRequirementIds"]),
            "TS ID": scenario["id"], "Test Scenario": scenario["intent"],
            "Preconditions": " | ".join(scenario["preconditions"]),
            "Acceptance Criteria / Validations": " | ".join(scenario["validations"]),
            "Expected Result": scenario["expectedResult"], "Ordered Flow Legs": " -> ".join(scenario["orderedLegs"]),
            "TC IDs": "", "TC Status": "NOT_AUTHORED", "TD IDs": "", "TD Status": "NOT_AUTHORED",
            "Actual Result": "Not Executed", "Status": STATUS, "Eligibility": "REVIEW_REQUIRED",
            "Source References": locators([item["sourceId"] for item in row["sourceEvidence"]]),
            "Question IDs": ";".join(row["questionIds"]), "BA Decision": "",
            "BA Rationale / Evidence": "", "BA Reviewer / Date": "",
        })
        if context["flowId"]:
            matrix.append({
                "Context ID": context["groupId"], "Profile ID": row["profileId"],
                "Card Program": context["cardProgram"], "Flow ID": context["flowId"],
                "Transaction Category": context["transactionCategory"],
                "Context Requirement ID": row["id"], "Shared Requirement ID": row["sharedRequirementId"],
                "TS ID": scenario["id"], "Eligibility": "REVIEW_REQUIRED",
                "Meaning": "Explicit conditional review selection; NOT a support/denial policy.",
                "Ordered Legs": " -> ".join(scenario["orderedLegs"]),
                "Source References (not eligibility proof)": locators(
                    [item["sourceId"] for item in row["sourceEvidence"]]),
                "Required Evidence": "Identity + selected program + exact ordered message/card eligibility + environment/version.",
                "Question IDs": ";".join(row["questionIds"]),
                "BA Decision": "", "Source / Configuration / Version": "", "Reviewer / Date": "",
            })
        gaps.append({
            "Group ID": context["groupId"], "Profile ID": row["profileId"],
            "BR ID": row["id"], "TS ID": scenario["id"],
            "TC IDs": "", "TD IDs": "", "Complete Chain": "False",
            "Gaps": ";".join(scenario["chainGaps"]),
            "Question IDs": ";".join(row["questionIds"]),
            "Required Next Action": "Authorize applicability; author concrete cases and synthetic payloads with independent oracles before execution.",
        })

    csv_rows = [{
        "BR ID (supplement only)": row["id"], "Business Requirement": row["statement"],
        "FR ID": row["fr"], "TS ID": row["scenarioId"], "Test Scenario": row["name"],
        "Preconditions / Applicability": row["scope"],
        "Acceptance Criteria / Validations": " | ".join(row["checks"]), "Expected Result": row["expected"],
        "Actual Result": row["actualResult"], "TD Reference": row["testDataReference"],
        "Validation Level": row["validationLevel"], "Status": row["status"],
        "Source References": locators(row["sources"]),
        "Source Locator Excerpts": " | ".join(sources[key]["anchor"] for key in row["sources"]),
        "Question IDs": ";".join(row["questions"]), "BA Decision": "",
        "BA Rationale / Evidence": "", "BA Reviewer / Date": "",
    } for row in requirements]
    questionnaire = [{
        "Question ID": q["id"], "Register ID": document["registerId"], "Owner": q["owner"],
        "Question": q["question"], "Evidence Needed": q["evidenceNeeded"],
        "Affected Requirement IDs": ";".join(row["id"] for row in requirements if q["id"] in row["questions"]),
        "Affected Context BR IDs": ";".join(row["id"] for row in navigation["businessRequirements"]
                                           if q["id"] in row["questionIds"]),
        "Affected Groups": ";".join(group["id"] for group in contexts if any(
            row["context"]["groupId"] == group["id"] and q["id"] in row["questionIds"]
            for row in navigation["businessRequirements"])),
        "Status": "OPEN", "Answer": "", "Source / Configuration / Version": "",
        "Environment / Exceptions": "", "Reviewer / Date": "",
    } for q in document["questions"]]
    summary = {
        "section5CategoryCount": len(networks), "financialCardTypeCount": len(cards),
        "receiptLabelCount": len(document["receiptLabels"]),
        "transactionCodeCount": len(document["transactions"]), "generalLifecycleFamilyCount": 5,
        "specialPromptCodeCount": len(document["specialPrompts"]),
        "specializedLifecycleProfileCount": len(SPECIAL_FLOWS), "lifecycleProfileCount": len(document["flows"]),
        "draftRequirementCount": len(requirements),
        "networkGroupCount": len(NETWORK_GROUPS), "genericGroupCount": len(GENERIC_GROUPS),
        "primaryGroupCount": len(contexts), "reviewProfileCount": len(navigation["profiles"]),
        "contextualDraftBrCount": len(navigation["businessRequirements"]),
        "contextualDraftScenarioCount": len(navigation["testScenarios"]),
        "linkedSharedRequirementCount": len(navigation["linkedSharedRequirementIds"]),
        "unmappedSharedRequirementCount": len(navigation["unmappedSharedRequirementIds"]),
        "completeChainCount": 0, "authoredTestCaseCount": 0,
        "draftLessonCount": sum(row["teaching"] for row in requirements),
        "openQuestionCount": len(questionnaire), "eligibilityReviewCellCount": len(matrix),
        "approvedEligibilityRuleCount": 0, "authoredTestDataCount": 0, "executedCaseCount": 0,
    }
    safety = {"status": STATUS, "specificationVersion": "2026-3", "baselineChanged": False,
              "certified": False, "executionReady": False, "modelWeightsUpdated": False,
              "sourceCompletenessCertified": False}
    report = {**safety, "reviewFormatVersion": 2,
              "artifact": "ATL105-ALL-NETWORK-REQUIREMENTS-REVIEW", "summary": summary,
              "reviewedOn": document["reviewedOn"], "contexts": contexts, "financialCardTypes": cards,
              "flows": document["flows"], "requirements": requirements, "questions": document["questions"],
              "businessNavigation": navigation}
    training = {**safety, "artifact": "ATL105-ALL-NETWORK-DRAFT-TEACHING-SUPPLEMENT",
                "policy": "Only curated source-linked statements/classifications. No eligibility cross-product, unanswered question, 2026-4 overlay or downstream contract is teaching truth. Location/hash checks do not certify semantics.",
                "lessons": [{"id": row["id"].replace("NET-REQ-", "NET-LESSON-", 1),
                             "statement": row["statement"], "applicability": row["scope"],
                             "validationLevel": row["validationLevel"], "status": STATUS,
                             "requirementId": row["id"], "scenarioId": row["scenarioId"],
                             "sourceEvidence": row["sourceEvidence"]}
                            for row in requirements if row["teaching"]]}
    readme = (
        "# Network-first business requirements review\n\n"
        "Primary groups: **seven named networks plus two generic categories (Debit and EBT)**. "
        "Credit is a card program, NOT a network. Section 5 leaves are secondary classification only.\n\n"
        "BA navigation: BUYPASS -> ATL105/2026-3 -> card program -> network/generic group -> transaction category "
        "-> presence / entry mode / channel -> flow/leg -> contextual BR -> TS -> TC -> TD. "
        "The network-first index below is an alternate review view of the same contexts.\n\n"
        "## Scope and counts\n\n"
        + "".join(f"- {key}: {value}\n" for key, value in summary.items())
        + "\nThe original **145 shared requirements are retained once**, with stable IDs. "
        "Contextual BRs/TSs reference shared rules rather than pretending they are newly approved atomic BRs. "
        "Every shared row is linked to a group or explicitly retained in the unmapped library. "
        "The 16 Section 5 leaves, 36 financial card codes and nine receipt labels "
        "are secondary, overlapping dimensions, NOT 61 networks. "
        "All 23 Appendix G codes and 11 Appendix E special prompts are inventoried, including specialized operations. Five general "
        "lifecycle families and six program-specific profiles are modeled. This is not a claim of "
        "exhaustive source semantics, all optional subfunctions or arbitrary composed flows.\n\n"
        "## Deliverables\n\n"
        "- [Network review index](NETWORK-REVIEW.md): all seven network and two generic groups, with BR/TS summaries and gaps.\n"
        "- [Contextual requirements](requirements-review.csv): primary BA hierarchy columns, source-linked conditional BRs/TSs and response columns.\n"
        "- [Shared requirements library](shared-requirements-library.csv): all original 145 rows, not network-specific BRs.\n"
        "- [Shared library mapping](shared-library-mapping.csv): every shared ID, linked groups/profiles or explicit unmapped disposition.\n"
        "- [Network/flow review](network-flow-review.csv): explicitly selected conditional profiles, not a Cartesian product "
        "or executable policy. Unsupported program combinations are not fabricated.\n"
        "- [BR -> TS -> TC -> TD gaps](chain-gaps.csv): real BR/TS links, empty TC/TD IDs and explicit missing evidence.\n"
        "- [Questionnaire](ba-sme-questionnaire.csv): ten grouped questions, SEG100-SME-004.\n"
        "- [Draft teaching lessons](training-lessons.json): classifications and curated conditional source rules only.\n"
        "- [Structured review](review.json), [provenance](manifest.json), [email draft](BA-SME-EMAIL.md).\n\n"
        "## Format migration\n\n"
        "Review format version 2 replaces the previous 25 mixed primary contexts and 850 Cartesian "
        "candidates with nine primary groups and explicit conditional flow selections. "
        "requirements-review.csv is now the contextual BA table; the original 145-row CSV shape is "
        "preserved in shared-requirements-library.csv. JSON requirements remains the shared library; "
        "businessNavigation contains contextual businessRequirements/testScenarios and empty testCases/testData. "
        "The directory and existing shared IDs stay stable. Rebuild/check commands are unchanged.\n\n"
        "## Boundaries\n\n"
        "Concrete TCs and TDs are NOT_AUTHORED and every chain is incomplete. No placeholder ID is counted as an artefact. "
        "Actual results are Not Executed. These supplement IDs do not "
        "change canonical BR counts or coverage. No runtime policy, model weights, source approval, "
        "TC/TD fixtures, converter or host certification is created. All statements remain draft. "
        "Source matching establishes location and byte hashes, NOT semantic correctness. "
        "Issuer status, merchant eligibility, routing, fleet external sources, security, settlement and "
        "source ambiguities remain separately review-gated. Visa Appendix AE stays 2026-3; Appendix AF "
        "2026-4 is an excluded, question-gated overlay, not silently adopted.\n\n"
        "Commercial usage is orthogonal to funding program. Presence, entry mode and channel are separate, "
        "with unresolved values explicitly retained; a contactless entry is not an e-commerce channel. "
        "Prepaid/commercial eligibility and unresolved library mappings are BA gaps, not implicit support. "
        "ATL104, ISO and Gen2 are diagram examples only, not enabled specifications.\n\n"
        "Return BA decisions in a separate copy; rebuilding overwrites generated files. Authorized "
        "answers must be persisted through the existing SME workflow before curated inputs change.\n"
    )
    email = (
        "Subject: Review requested - ATL105 all-network requirements and lifecycle applicability\n\n"
        "Hi BA/SME team,\n\n"
        "Please review requirements-review.csv, network-flow-review.csv and ba-sme-questionnaire.csv "
        "(NET-Q01 through NET-Q10, SEG100-SME-004), plus NETWORK-REVIEW.md and chain-gaps.csv. "
        "The primary review groups are AMEX, Visa, Mastercard, Discover, Diners Club, JCB and STAR Signature Debit, "
        "plus generic Debit and EBT. Credit is a program, not a network. "
        "The original 145 mixed-dimension requirements are a shared library linked beneath conditional "
        "business contexts; they are not 145 network-specific BRs. Concrete TCs/TDs remain missing.\n\n"
        "Please return decisions with per-leg source/configuration evidence, version, environment, "
        "exceptions, reviewer and date. Candidate combinations are not supported/denied policies. "
        "Fleet, host, routing, settlement, receipt/security and release-overlay gaps remain open. "
        "Only synthetic/redacted evidence should be supplied.\n\n"
        "This is draft repository teaching evidence, not executed tests, certified coverage or "
        "completed model training. Please return feedback by [date].\n\nThanks,\n[Sender]\n"
    )
    mapping_rows = []
    for row in requirements:
        linked_groups = [group["id"] for group in contexts if row["id"] in group["sharedRequirementIds"]]
        linked_profiles = [profile["id"] for profile in navigation["profiles"] if any(
            member["profileId"] == profile["id"] and row["id"] in [
                member["sharedRequirementId"], *member["classificationReferenceIds"], *member["transactionRequirementIds"]]
            for member in navigation["businessRequirements"])]
        mapping_rows.append({
            "Shared Requirement ID": row["id"], "Statement": row["statement"],
            "Group IDs": ";".join(linked_groups), "Profile IDs": ";".join(linked_profiles),
            "Mapping Disposition": "CONDITIONAL_REVIEW_LINK" if linked_groups else "UNMAPPED_REVIEW_REQUIRED",
            "Meaning": "Navigation only; not eligibility or added canonical coverage.",
            "Question IDs": ";".join(sorted(set(row["questions"] + ["NET-Q02"]))),
        })
    index = "# Network-first BA review index\n\n"
    index += "Seven NETWORK groups and two GENERIC_CATEGORY groups. All conditional drafts; zero complete TC/TD chains.\n\n"
    index += "Use [requirements-review.csv](requirements-review.csv) for the BA hierarchy and source references, "
    index += "[chain gaps](chain-gaps.csv) for missing artefacts, and [library mapping](shared-library-mapping.csv) "
    index += "for all 145 shared rows. Receipt IDs name labels, not runtime network evidence.\n\n"
    for group in contexts:
        members = [row for row in business_rows if row["Network / Generic Group"] == group["id"]]
        index += f"## {group['name']} ({group['kind']})\n\n"
        index += (f"{len(members)} contextual draft BRs / {len(members)} draft scenarios; "
                  "0 authored concrete TCs, 0 TDs, 0 complete chains.\n\n")
        index += "| Profile / program | Category / flow | BR | Shared source rule | TS |\n|---|---|---|---|---|\n"
        for row in members:
            index += (f"| {row['Profile ID']} / {row['Card Program']} | "
                      f"{row['Transaction Category']} / {row['Flow ID'] or row['Lifecycle Role']} | "
                      f"{row['BR ID (contextual draft)']} | {row['Shared Requirement ID']} | {row['TS ID']} |\n")
        index += "\nIdentity, eligibility, channel/entry, concrete cases, synthetic payloads and outcome oracles remain review-gated.\n\n"
    index += "## Unmapped shared library\n\n"
    index += ("These rules remain available without inventing a network mapping, including closed-loop stored value, "
              "fleet, check, loyalty, specialized prompts and release overlays. "
              "Open-loop prepaid is not automatically governed by closed-loop SVC rules.\n\n")
    index += "".join(f"- {identifier}\n" for identifier in navigation["unmappedSharedRequirementIds"])
    return {"requirements-review.csv": shared.as_csv(business_rows),
            "shared-requirements-library.csv": shared.as_csv(csv_rows),
            "shared-library-mapping.csv": shared.as_csv(mapping_rows),
            "chain-gaps.csv": shared.as_csv(gaps), "NETWORK-REVIEW.md": index,
            "network-flow-review.csv": shared.as_csv(matrix),
            "ba-sme-questionnaire.csv": shared.as_csv(questionnaire),
            "training-lessons.json": shared.json_text(training),
            "review.json": shared.json_text(report), "README.md": readme, "BA-SME-EMAIL.md": email}


def build(input_path: Path, root: Path) -> dict[str, str]:
    document = shared.read_json(input_path)
    sources, networks, cards = validate(document, root)
    files = render(document, sources, networks, cards)
    files["manifest.json"] = shared.json_text({
        "artifact": "ATL105-ALL-NETWORK-REVIEW-PROVENANCE", "schemaVersion": 1,
        "inputSha256": shared.fingerprint(input_path.read_bytes()),
        "generatorSha256": shared.fingerprint(Path(__file__).read_bytes()),
        "helperSha256": shared.fingerprint(HELPER_PATH.read_bytes()),
        "sourceHashes": {source["path"]: source["sha256"] for source in sources.values()},
        "outputHashes": {name: shared.fingerprint(text.encode("utf-8")) for name, text in files.items()},
        "semanticCertification": False, "executionCertified": False,
    })
    return files


def publish(files: dict[str, str], output: Path, check: bool) -> None:
    if check:
        mismatches = [name for name, content in files.items()
                      if not (output / name).is_file()
                      or (output / name).read_bytes() != content.encode("utf-8")]
        if mismatches:
            raise ValueError(f"Missing/stale all-network outputs; rerun builder: {mismatches}")
        print("PASS: all-network source links, dimension counts, lifecycle pairs and output hashes reconcile.")
    else:
        output.mkdir(parents=True, exist_ok=True)
        for name, content in files.items():
            (output / name).write_bytes(content.encode("utf-8"))
        print(f"Wrote {len(files)} all-network review deliverables to {output}")


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true", help="Read-only check of generated review outputs")
    args = parser.parse_args()
    publish(build(INPUT, PACK), OUTPUT, args.check)


if __name__ == "__main__":
    main()
