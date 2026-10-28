"""Phases 4 to 8 for a Petroleum card context: matrix, draft BRs, chains, flows and mutation checks."""

import copy
import re
from types import SimpleNamespace


ENTRY_MODES = ("TRACK_2_DEFAULT", "TRACK_1_FALLBACK", "MANUAL_ENTRY", "FOLLOW_ON_PARTIAL_TRACK_2")
TRACK_MODES = ("TRACK_2_DEFAULT", "TRACK_1_FALLBACK")
SCENARIO_TYPES = {"POSITIVE", "NEGATIVE", "BOUNDARY"}
PROMPT_RULES = {"DRIVER_ID": "MCF-BR-05", "ID_NUMBER": "MCF-BR-05", "ODOMETER": "MCF-BR-06", "VEHICLE_NUMBER": "MCF-BR-07"}
HOST_OUTCOME = "NOT_DEFINED_BY_SOURCE_REVIEW_REQUIRED"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise ValueError(message)


def luhn_digit(body: str) -> str:
    total = 0
    for index, char in enumerate(reversed(body)):
        value = int(char) * (2 if index % 2 == 0 else 1)
        total += value - 9 if value > 9 else value
    return str((10 - total % 10) % 10)


def luhn_valid(number: str) -> bool:
    return number.isdigit() and luhn_digit(number[:-1]) == number[-1]


def layout_pattern(layout: dict, skip_start: bool) -> re.Pattern:
    parts = []
    for field in layout["fields"]:
        if field["name"] == "LRC" or (skip_start and field["name"] == "START_SENTINEL"):
            continue
        if "fixed" in field:
            parts.append(re.escape(field["fixed"]))
        elif field.get("variable"):
            parts.append(f"(?P<{field['name']}>[^^]{{0,{field['len']}}})")
        else:
            parts.append(f"(?P<{field['name']}>.{{{field['len']}}})")
    return re.compile("".join(parts))


def build_matrix(chain: dict, context: dict, sources: dict, atl105_evidence) -> dict:
    matrix = chain["contextMatrix"]
    supported = context["scope"]["supportedTransactions"]["values"]
    require([t["petroleum"] for t in matrix["transactions"]] == supported,
            "Matrix transactions must match the scoped supported transactions in order")
    codes = [t["code"] for t in matrix["transactions"]]
    require(len(set(codes)) == len(codes), "Duplicate transaction type codes")
    rule = [atl105_evidence(sources, e) for e in matrix["promptCodeRule"]]
    require({e["version"] for e in rule} == {"2026-3", "2026-4"}, "Prompt Code rule needs both ATL105 releases")
    reconciliation = {r["id"]: r for r in context["reconciliation"]}
    card_quotes = {e["quote"] for r in ("MCF-R01", "MCF-R02") for e in reconciliation[r]["atl105"]}
    for role, code in matrix["cardTypes"].items():
        require(any(q.startswith(code + " ") for q in card_quotes), f"Card type {code} ({role}) lacks reconciled evidence")
    transactions = []
    for t in matrix["transactions"]:
        evidence = [atl105_evidence(sources, e) for e in t["atl105"]]
        require({e["version"] for e in evidence} == {"2026-3", "2026-4"}, f"Transaction {t['code']} needs both releases")
        require(any(e["quote"].startswith(t["code"] + " ") for e in evidence), f"Transaction code {t['code']} is not quoted")
        require(t["role"] in matrix["entryModesByRole"], f"Unknown role {t['role']}")
        if t["role"] == "REVIEW":
            require(bool(t.get("reviewReason")), f"Transaction {t['code']} needs a review reason")
        transactions.append({**t, "atl105": evidence,
                             "promptCodes": {"fleet": t["code"] + matrix["cardTypes"]["fleet"],
                                             "fallback": t["code"] + matrix["cardTypes"]["fallback"]},
                             "reviewRequired": t["basis"] != "EXACT_NAME" or t["role"] == "REVIEW"})
    enums = context["catalog"]["enumerations"]["productTypeCode"]
    prompts = matrix["productTypePrompts"]
    require(sorted(prompts) == sorted(enums), "Product type prompts must cover every Product Type Code")
    phrases = matrix["promptPhrases"]
    for code, required in prompts.items():
        implied = sorted(p for p, phrase in phrases.items() if phrase in enums[code])
        require(sorted(required) == implied, f"Product Type {code} prompts disagree with '{enums[code]}'")
    lengths = {item["field"] for item in context["catalog"]["promptDataLengths"]}
    for name, element in matrix["promptElements"].items():
        require(element["lengthField"] in lengths and element["reconciliation"] in reconciliation,
                f"Prompt {name} lacks a verified length or reconciliation")
    segment = [atl105_evidence(sources, e) for e in matrix["fleetDataSegment"]]
    cells = []
    for t in transactions:
        for mode in ENTRY_MODES:
            if t["role"] == "REVIEW":
                status = "REVIEW_REQUIRED"
            elif mode in matrix["entryModesByRole"][t["role"]]:
                status = "SUPPORTED"
            else:
                status = "NOT_APPLICABLE"
            cells.append({"transactionCode": t["code"], "entryMode": mode, "status": status})
    return {"transactions": transactions, "promptCodeRule": rule, "cardTypes": matrix["cardTypes"],
            "productTypePrompts": prompts, "manualEntryPrompts": matrix["manualEntryPrompts"],
            "promptElements": matrix["promptElements"], "fleetDataSegment": segment, "cells": cells}


def build_requirements(chain: dict, context: dict, prefix: str = "MCF") -> list[dict]:
    inventory = {i["id"]: i for i in context["inventory"]}
    reconciliation = {r["id"]: r for r in context["reconciliation"]}
    seen, covered, requirements = set(), set(), []
    for br in chain["businessRequirements"]:
        require(re.fullmatch(rf"{prefix}-BR-\d{{2}}", br["id"]) is not None and br["id"] not in seen, f"Invalid or duplicate BR {br['id']}")
        seen.add(br["id"])
        require(bool(br["statement"].strip()) and bool(br["inventoryRefs"]), f"{br['id']} needs a statement and source anchors")
        require(all(r in inventory for r in br["inventoryRefs"]), f"{br['id']} has unknown inventory references")
        require(all(r in reconciliation for r in br["reconciliationRefs"]), f"{br['id']} has unknown reconciliation references")
        covered.update(br["inventoryRefs"])
        review = bool(br.get("interpretation")) or any(reconciliation[r]["reviewRequired"] for r in br["reconciliationRefs"])
        requirements.append({
            "id": br["id"], "title": br["title"], "statement": br["statement"],
            "status": "DRAFT_REVIEW_REQUIRED", "reviewRequired": review,
            "interpretation": br.get("interpretation"),
            "reconciliationRefs": br["reconciliationRefs"],
            "sourceAnchors": [{"inventoryId": r, **{k: inventory[r][k] for k in ("pdfPage", "printedPage", "pageId", "textSha256", "quote")}}
                              for r in br["inventoryRefs"]],
        })
    statements = [r["statement"] for r in requirements]
    require(len(set(statements)) == len(statements), "Duplicate BR statements")
    rules = {i for i, item in inventory.items() if item["classification"] == "RULE"}
    require(not rules - covered, "Rule items without a BR: " + ", ".join(sorted(rules - covered)))
    return requirements


def make_test_data(defaults: dict, overrides: dict, context: dict) -> dict:
    d = {**copy.deepcopy(defaults), **copy.deepcopy(overrides)}
    tx = d["transactionCode"]
    body = d["bin"] + d["accountBody"]
    account = body + luhn_digit(body)
    if d.get("breakCheckDigit"):
        account = account[:-1] + str((int(account[-1]) + 1) % 10)
    expiry = d["expiryYYMM"]
    discretionary = "0" * (10 if d.get("shortenDiscretionary") else 11)
    track2 = f";{account}={expiry}{d['trackCardType']}{discretionary}{d['productRestrictionCode']}{d['productTypeCode']}?"
    mode = d["entryMode"]
    if mode == "TRACK_2_DEFAULT":
        card = {"track2": track2}
    elif mode == "TRACK_1_FALLBACK":
        track1 = (f"%B{account}^{d['cardholderName']}^{expiry}{d['trackCardType']}{'0' * 22}"
                  f"{d['productRestrictionCode']}{d['productTypeCode']}?")
        card = {"track1": track1 if d.get("includeStartSentinel") else track1[1:]}
    elif mode == "MANUAL_ENTRY":
        card = {"account": account[:d.get("manualAccountLength", 16)],
                "expiryMMYY": expiry[2:] + expiry[:2] if d["sendExpiry"] else None}
    else:
        card = {"partialTrack2": track2 if d.get("followOnFullTrack2") else f"{account}={expiry}"}
    return {
        "transactionCode": tx,
        "promptCode": d.get("promptCodeOverride") or tx + d.get("cardType", context["cardTypes"]["fleet"]),
        "entryMode": mode, "cardData": card, "prompts": d["prompts"],
        "products": d["products"], "transactionAmount": d["transactionAmount"],
        "merchantProfile": {"terminalSpecialCode034": d["terminalSpecialCode034"]},
        "tableLoadCardTypes": d["tableLoadCardTypes"],
        "fleetAllowedByAlternateMethod": d["fleetAllowedByAlternateMethod"],
        "readResults": d["readResults"], "swipeAttempts": d["swipeAttempts"],
        "deviceExpiryValidated": d["deviceExpiryValidated"],
        "cardholderReceiptShowsExpiry": d["cardholderReceiptShowsExpiry"],
    }


def validate_test_data(td: dict, model: dict) -> tuple[list[str], bool]:
    """Return violated BR IDs and whether MasterCard fleet rules applied."""
    v = set()
    tx, pc, mode, card = td["transactionCode"], td["promptCode"], td["entryMode"], td["cardData"]
    transactions = {t["code"]: t for t in model["transactions"]}
    if tx not in transactions:
        v.add("MCF-BR-01")
    if len(pc) != 4 or pc[0] != tx or not pc[1:].isdigit():
        v.add("MCF-BR-22")
    card_type = pc[-3:]
    account, pr, pt = None, None, None
    if mode == "TRACK_2_DEFAULT":
        m = model["track2"].fullmatch(card["track2"])
        if m:
            account, pr, pt = m["BIN"] + m["PAN"], m["PRODUCT_RESTRICTION_CODE"], m["PRODUCT_TYPE_CODE"]
        else:
            v.add("MCF-BR-21")
    elif mode == "TRACK_1_FALLBACK":
        data = card["track1"]
        if data.startswith("%"):
            v.add("MCF-BR-20")
            data = data[1:]
        m = model["track1"].fullmatch(data)
        if m:
            account, pr, pt = m["BIN"] + m["PAN"], m["PRODUCT_RESTRICTION_CODE"], m["PRODUCT_TYPE_CODE"]
        else:
            v.add("MCF-BR-21")
    elif mode == "MANUAL_ENTRY":
        account = card["account"]
        if len(account) != 16 or not account.isdigit():
            v.add("MCF-BR-09")
    else:
        if re.fullmatch(r"\d{16}=\d{4}", card["partialTrack2"]) is None:
            v.add("MCF-BR-19")
    if account is None:
        found = re.match(r"[;%]?B?(\d{16})", next(iter(card.values())) or "")
        account = found.group(1) if found else ""
    fleet_bin = len(account) >= 6 and any(low <= account[:6] <= high for low, high in model["bins"])
    tsc = td["merchantProfile"]["terminalSpecialCode034"]
    allowed = tsc and ("080" in td["tableLoadCardTypes"] or td["fleetAllowedByAlternateMethod"])
    if card_type == model["cardTypes"]["fleet"]:
        if not fleet_bin:
            v.add("MCF-BR-02")
        elif not tsc:
            v.add("MCF-BR-03")
        elif not allowed:
            v.add("MCF-BR-04")
    elif fleet_bin and allowed:
        v.add("MCF-BR-02")
    fleet = card_type == model["cardTypes"]["fleet"] and fleet_bin
    if not fleet:
        return sorted(v), False
    if len(account) == 16 and account.isdigit() and not luhn_valid(account):
        v.add("MCF-BR-13")
    role = transactions.get(tx, {}).get("role")
    if role == "ORIGINAL":
        reads = td["readResults"]
        expected = "TRACK_2_DEFAULT" if reads["track2"] else "TRACK_1_FALLBACK" if reads["track1"] else "MANUAL_ENTRY"
        if mode != expected:
            v.add("MCF-BR-18")
        if mode == "MANUAL_ENTRY" and td["swipeAttempts"] < 3:
            v.add("MCF-BR-08")
    if role == "FOLLOW_ON" and mode != "FOLLOW_ON_PARTIAL_TRACK_2":
        v.add("MCF-BR-19")
    required = []
    if mode in TRACK_MODES and pt is not None:
        if pt in model["productTypePrompts"]:
            required = model["productTypePrompts"][pt]
        else:
            v.add("MCF-BR-21")
    elif mode == "MANUAL_ENTRY":
        required = model["manualEntryPrompts"]
        if set(td["prompts"]) - set(required):
            v.add("MCF-BR-24")
    for prompt in required:
        low, high = model["lengths"][model["promptElements"][prompt]["lengthField"]]
        value = td["prompts"].get(prompt, "")
        if not (value.isdigit() and low <= len(value) <= high):
            v.add(PROMPT_RULES[prompt])
    if pr is not None and pr not in model["restrictionCodes"]:
        v.add("MCF-BR-14")
    if not td["deviceExpiryValidated"]:
        v.add("MCF-BR-10")
    if td["cardholderReceiptShowsExpiry"]:
        v.add("MCF-BR-11")
    if transactions.get(tx, {}).get("authorization") and mode == "MANUAL_ENTRY" and not card["expiryMMYY"]:
        v.add("MCF-BR-12")
    products = td["products"]
    if not products:
        v.add("MCF-BR-16")
    else:
        codes = [p["code"] for p in products]
        if len(set(codes)) != len(codes):
            v.add("MCF-BR-15")
        if sum(p["amount"] for p in products) != td["transactionAmount"]:
            v.add("MCF-BR-17")
        if any(code not in model["productCodes"] for code in codes):
            v.add("MCF-BR-23")
        if pr == "2" and any(code in model["productCodes"] and code not in model["fuelCodes"] for code in codes):
            v.add("MCF-BR-14")
    return sorted(v), True


def validator_model(matrix: dict, catalog: dict) -> dict:
    layouts = catalog["trackLayouts"]
    return {
        "transactions": matrix["transactions"], "cardTypes": matrix["cardTypes"],
        "productTypePrompts": matrix["productTypePrompts"], "manualEntryPrompts": matrix["manualEntryPrompts"],
        "promptElements": matrix["promptElements"],
        "lengths": {i["field"]: (i["min"], i["max"]) for i in catalog["promptDataLengths"]},
        "bins": [tuple(p) for p in catalog["binRanges"]["prefixes"]],
        "restrictionCodes": set(catalog["enumerations"]["productRestrictionCode"]),
        "productCodes": set(catalog["productCodes"]["definedCodes"]),
        "fuelCodes": set(catalog["productCodes"]["fuelCodes"]),
        "track2": layout_pattern(layouts["TRACK_2"], skip_start=False),
        "track1": layout_pattern(layouts["TRACK_1"], skip_start=True),
    }


def odometer_required(td: dict) -> bool:
    if td["entryMode"] == "MANUAL_ENTRY":
        return True
    track = td["cardData"].get("track2") or td["cardData"].get("track1") or ""
    return td["entryMode"] in TRACK_MODES and len(track) > 2 and track[-2] in "1234"


MUTATIONS = (
    ("BREAK_CHECK_DIGIT", lambda d, td: {"breakCheckDigit": True}, lambda td: True, ["MCF-BR-13"]),
    ("BIN_OUTSIDE_RANGE", lambda d, td: {"bin": "553230"}, lambda td: True, ["MCF-BR-02"]),
    ("FLEET_SENT_AS_CREDIT_020", lambda d, td: {"cardType": "020"}, lambda td: True, ["MCF-BR-02"]),
    ("PROMPT_CODE_WRONG_TRANSACTION", lambda d, td: {"promptCodeOverride": ("3" if td["transactionCode"] != "3" else "0") + "080"},
     lambda td: True, ["MCF-BR-22"]),
    ("SKIP_EXPIRY_VALIDATION", lambda d, td: {"deviceExpiryValidated": False}, lambda td: True, ["MCF-BR-10"]),
    ("RECEIPT_SHOWS_EXPIRY", lambda d, td: {"cardholderReceiptShowsExpiry": True}, lambda td: True, ["MCF-BR-11"]),
    ("NO_PRODUCTS", lambda d, td: {"products": []}, lambda td: True, ["MCF-BR-16"]),
    ("PRODUCT_SUM_OFF_BY_ONE_CENT", lambda d, td: {"transactionAmount": td["transactionAmount"] + 1}, lambda td: True, ["MCF-BR-17"]),
    ("DUPLICATE_PRODUCT_CODE", lambda d, td: {"products": [{"code": td["products"][0]["code"], "amount": 1}, {**td["products"][0], "amount": td["products"][0]["amount"] - 1}] + td["products"][1:]},
     lambda td: td["products"][0]["amount"] > 1, ["MCF-BR-15"]),
    ("INVALID_RESTRICTION_CODE", lambda d, td: {"productRestrictionCode": "9"}, lambda td: td["entryMode"] in TRACK_MODES, ["MCF-BR-14"]),
    ("INVALID_PRODUCT_TYPE", lambda d, td: {"productTypeCode": "9"}, lambda td: td["entryMode"] in TRACK_MODES, ["MCF-BR-21"]),
    ("ODOMETER_NINE_DIGITS", lambda d, td: {"prompts": {**td["prompts"], "ODOMETER": "123456789"}},
     odometer_required, ["MCF-BR-06"]),
    ("TRACK_1_WITH_START_SENTINEL", lambda d, td: {"includeStartSentinel": True}, lambda td: td["entryMode"] == "TRACK_1_FALLBACK", ["MCF-BR-20"]),
    ("FOLLOW_ON_FULL_TRACK_2", lambda d, td: {"followOnFullTrack2": True}, lambda td: td["entryMode"] == "FOLLOW_ON_PARTIAL_TRACK_2", ["MCF-BR-19"]),
    ("MANUAL_AFTER_TWO_ATTEMPTS", lambda d, td: {"swipeAttempts": 2}, lambda td: td["entryMode"] == "MANUAL_ENTRY", ["MCF-BR-08"]),
    ("FUEL_ONLY_WITH_CAR_WASH", lambda d, td: {"productRestrictionCode": "2", "products": [
        {**p, "code": "102"} if i == 0 and all(q["code"] != "102" for q in td["products"]) else p
        for i, p in enumerate(td["products"])]},
     lambda td: td["entryMode"] in TRACK_MODES and bool(td["products"]), ["MCF-BR-14"]),
    ("UNKNOWN_PRODUCT_CODE", lambda d, td: {"products": [{**td["products"][0], "code": "P01"}] + td["products"][1:]},
     lambda td: bool(td["products"]), ["MCF-BR-23"]),
    ("KEYED_PROMPTS_FOR_VEHICLE", lambda d, td: {"prompts": {**td["prompts"], "VEHICLE_NUMBER": "1234"}},
     lambda td: td["entryMode"] == "MANUAL_ENTRY", ["MCF-BR-24"]),
)


MASTERCARD = SimpleNamespace(prefix="MCF", build_matrix=build_matrix, make_test_data=make_test_data,
                             validate_test_data=validate_test_data, validator_model=validator_model, mutations=MUTATIONS)


def build_chain(chain: dict, context: dict, sources: dict, atl105_evidence, program: SimpleNamespace = MASTERCARD) -> dict:
    """Draft phases 4 to 8; card-specific matrix, test data, validator and mutations come from `program`."""
    require(chain["schemaVersion"] == 1 and chain["status"] == "DRAFT_REVIEW_REQUIRED", "Unsupported chain input")
    require(chain["contextId"] == context["contextId"], "Chain input belongs to another context")
    prefix, operators = program.prefix, program.mutations
    make_test_data, validate_test_data = program.make_test_data, program.validate_test_data
    matrix = program.build_matrix(chain, context, sources, atl105_evidence)
    requirements = build_requirements(chain, context, prefix)
    model = program.validator_model(matrix, context["catalog"])
    br_ids = {r["id"] for r in requirements}
    transitions = {t["id"]: t for t in chain["flows"]["transitions"]}
    inventory = {i["id"] for i in context["inventory"]}
    for t in transitions.values():
        require(all(r in inventory for r in t["inventoryRefs"]), f"Transition {t['id']} has unknown anchors")
    supported = {(c["transactionCode"], c["entryMode"]) for c in matrix["cells"] if c["status"] == "SUPPORTED"}
    conflicts = {r["id"]: r["disposition"] for r in context["reconciliation"]}
    anchors = {r["id"]: r["sourceAnchors"] for r in requirements}
    scenarios, cases, data, seen = [], [], [], set()
    covered_cells, covered_transitions, covered_brs, positives = set(), set(), set(), []
    for s in chain["scenarios"]:
        require(re.fullmatch(rf"{prefix}-TS-\d{{3}}", s["id"]) is not None and s["id"] not in seen, f"Invalid or duplicate scenario {s['id']}")
        seen.add(s["id"])
        require(s["type"] in SCENARIO_TYPES, f"{s['id']} has unknown type")
        require(bool(s["brs"]) and set(s["brs"]) <= br_ids, f"{s['id']} references unknown BRs")
        require(set(s["expect"]) <= set(s["brs"]), f"{s['id']} expects a violation outside its BRs")
        require(s["type"] != "POSITIVE" or not s["expect"], f"{s['id']} is positive but expects violations")
        require(all(t in transitions for t in s.get("transitions", [])), f"{s['id']} references unknown transitions")
        conflict = s.get("oracleConflict")
        if conflict:
            require(conflicts.get(conflict) in {"POTENTIAL_CONFLICT", "ALIGNED_WITH_EXCEPTIONS"},
                    f"{s['id']} cites {conflict}, which is not a recorded source conflict or exception")
        if "matrix" in s:
            require(tuple(s["matrix"]) in supported, f"{s['id']} targets an unsupported matrix cell")
            covered_cells.add(tuple(s["matrix"]))
        td = make_test_data(chain["testDataDefaults"], s["data"], matrix)
        actual, fleet = validate_test_data(td, model)
        require(actual == sorted(s["expect"]), f"{s['id']} oracle mismatch: expected {sorted(s['expect'])}, validator found {actual}")
        if not s["expect"]:
            positives.append((s, td, fleet))
        covered_transitions.update(s.get("transitions", []))
        covered_brs.update(s["brs"])
        n = s["id"][-3:]
        source = [a for br in s["brs"] for a in anchors[br]]
        scenarios.append({"id": s["id"], "type": s["type"], "title": s["title"], "requirementIds": s["brs"],
                          "matrixCell": s.get("matrix"), "transitions": s.get("transitions", []), "sourceAnchors": source})
        cases.append({"id": f"{prefix}-TC-{n}", "scenarioIds": [s["id"]], "sourceAnchors": source,
                      "expectedOutcome": {"validator": "REJECT" if s["expect"] else "ACCEPT",
                                          "violatedRequirements": sorted(s["expect"]), "host": HOST_OUTCOME,
                                          "oracleStatus": "CONFLICTING_SOURCES_REVIEW_REQUIRED" if conflict else "SOURCE_DERIVED_DRAFT",
                                          "oracleConflict": conflict},
                      "fleetRulesApplied": fleet})
        data.append({"id": f"{prefix}-TD-{n}", "testCaseIds": [f"{prefix}-TC-{n}"], "payload": td,
                     "readiness": "DRAFT_SYNTHETIC_NOT_EXECUTED", "sourceAnchors": source})
    require(not br_ids - covered_brs, "BRs without scenarios: " + ", ".join(sorted(br_ids - covered_brs)))
    require(covered_cells == supported, "Supported matrix cells without positive scenarios: "
            + ", ".join(f"{a}/{b}" for a, b in sorted(supported - covered_cells)))
    require(set(transitions) == covered_transitions, "Flow transitions without scenarios: "
            + ", ".join(sorted(set(transitions) - covered_transitions)))
    mutations = []
    for s, td, fleet in positives:
        if not fleet:
            continue
        for name, change, applies, expected in operators:
            if not applies(td):
                continue
            mutated = make_test_data(chain["testDataDefaults"], {**s["data"], **change(s["data"], td)}, matrix)
            actual, _ = validate_test_data(mutated, model)
            require(actual == expected, f"Mutation {name} on {s['id']} expected {expected}, validator found {actual}")
            mutations.append({"id": f"{prefix}-MUT-{len(mutations) + 1:03}", "baseScenario": s["id"], "operator": name,
                              "expectedViolations": expected, "actualViolations": actual, "status": "CAUGHT"})
    require(len({m["operator"] for m in mutations}) == len(operators), "Every mutation operator must be exercised")
    flows = {"interpretations": chain["flows"]["interpretations"],
             "transitions": [{**t, "coveredBy": sorted(s["id"] for s in scenarios if t["id"] in s["transitions"])}
                             for t in chain["flows"]["transitions"]]}
    return {
        "manifest": {
            "packageId": f"PETRO-2025-1-{context['contextId']}-DRAFT-CHAINS",
            "authority": chain["authority"], "producer": "GitHub Copilot assistant, drafted from source text",
            "independence": "NOT_INDEPENDENT_TEST_SOLUTION_EVIDENCE_UNTIL_SME_APPROVAL",
            "referenceDate": chain["referenceDate"], "testDataNotes": chain["testDataNotes"],
        },
        "contextMatrix": matrix, "businessRequirements": requirements, "flows": flows,
        "testScenarios": scenarios, "testCases": cases, "testData": data,
        "mutationSuite": {"operators": [m[0] for m in operators], "results": mutations,
                          "caught": sum(m["status"] == "CAUGHT" for m in mutations), "total": len(mutations)},
        "atl105Serialization": chain["atl105Serialization"],
    }
