"""Voyager fleet (Petroleum chapter 9) source catalog and draft-chain rules."""

import copy
import re
from types import SimpleNamespace

from petroleum_chain import ENTRY_MODES, TRACK_MODES, require

PREFIX = "VYR"
TRACK_2 = re.compile(r";(?P<ISO>\d{4})(?P<ACCOUNT>\d{8})(?P<ACCOUNT_CHECK>\d)(?P<CARD_ID>\d{5})(?P<SUPER_CHECK>\d)=(?P<EXPIRY>\d{4})(?P<PROMPT>\d)(?P<FUEL>\d)(?P<DISCRETIONARY>\d{11})\?")
TRACK_1 = re.compile(r"%0(?P<ISO>\d{4})(?P<ACCOUNT>\d{8})(?P<ACCOUNT_CHECK>\d)(?P<CARD_ID>\d{5})(?P<SUPER_CHECK>\d)\^(?P<NAME>.{1,25})\^(?P<EXPIRY>\d{4})(?P<PROMPT>\d)(?P<FUEL>\d)(?P<DISCRETIONARY>\d{13})\?")
PROMPT_RULES = {"DRIVER_ID": "VYR-BR-05", "ODOMETER": "VYR-BR-06"}


def luhn_digit(value: str) -> str:
    total = 0
    for index, character in enumerate(reversed(value)):
        digit = int(character) * (2 if index % 2 == 0 else 1)
        total += digit - 9 if digit > 9 else digit
    return str((10 - total % 10) % 10)


def luhn_valid(value: str) -> bool:
    return value.isdigit() and luhn_digit(value[:-1]) == value[-1]


def build_matrix(chain: dict, context: dict, sources: dict, atl105_evidence) -> dict:
    matrix = chain["contextMatrix"]
    require([t["petroleum"] for t in matrix["transactions"]] == context["scope"]["supportedTransactions"]["values"],
            "Matrix transactions must match the scoped supported transactions in order")
    require(matrix["cardType"] == "060", "Voyager Table Load card type must be 060")
    card_evidence = [atl105_evidence(sources, item) for item in matrix["cardTypeEvidence"]]
    require({item["version"] for item in card_evidence} == {"2026-3", "2026-4"}, "Voyager card type needs both ATL105 releases")
    require(all(item["quote"].startswith("060 Voyager") for item in card_evidence), "Card type evidence does not quote Voyager 060")
    prompt_rule = [atl105_evidence(sources, item) for item in matrix["promptCodeRule"]]
    require({item["version"] for item in prompt_rule} == {"2026-3", "2026-4"}, "Prompt Code rule needs both ATL105 releases")

    transactions = []
    for item in matrix["transactions"]:
        evidence = [atl105_evidence(sources, quote) for quote in item["atl105"]]
        require({entry["version"] for entry in evidence} == {"2026-3", "2026-4"},
                f"Transaction {item['code']} needs both ATL105 releases")
        require(any(entry["quote"].startswith(item["code"] + " ") for entry in evidence),
                f"Transaction code {item['code']} is not quoted")
        require(item["role"] in matrix["entryModesByRole"], f"Unknown role {item['role']}")
        if item.get("reviewRequired"):
            require(bool(item.get("reviewReason")), f"Transaction {item['code']} needs a review reason")
        transactions.append({
            **item,
            "atl105": evidence,
            "promptCodes": {"fleet": item["code"] + matrix["cardType"], "fallback": item["code"] + matrix["cardType"]},
            "reviewRequired": bool(item.get("reviewRequired")),
        })

    mode_statuses = matrix["entryModesByRole"]
    cells = []
    for item in transactions:
        for mode in ENTRY_MODES:
            if item["role"] == "REVIEW" or item["reviewRequired"]:
                status = "REVIEW_REQUIRED"
            elif mode in mode_statuses[item["role"]]:
                status = "SUPPORTED"
            else:
                status = "NOT_APPLICABLE"
            cells.append({"transactionCode": item["code"], "entryMode": mode, "status": status})
    return {
        "transactions": transactions,
        "promptCodeRule": prompt_rule,
        "cardTypes": {"fleet": matrix["cardType"], "fallback": matrix["cardType"]},
        "cardTypeEvidence": card_evidence,
        "tableLoadCardType": matrix["cardType"],
        "cells": cells,
        "promptElements": matrix["promptElements"],
        "manualEntryPrompts": matrix["manualEntryPrompts"],
        "fleetDataSegment": [atl105_evidence(sources, item) for item in matrix["fleetDataSegment"]],
    }


def make_test_data(defaults: dict, overrides: dict, matrix: dict) -> dict:
    data = copy.deepcopy(defaults)
    for key, value in copy.deepcopy(overrides).items():
        if isinstance(data.get(key), dict) and isinstance(value, dict):
            data[key].update(value)
        else:
            data[key] = value
    account_check = luhn_digit(data["accountBody"])
    if data.get("breakAccountCheckDigit"):
        account_check = str((int(account_check) + 1) % 10)
    card_prefix = data["isoNumber"] + data["accountBody"] + account_check + data["cardIdentifier"]
    super_check = luhn_digit(card_prefix)
    if data.get("breakSuperCheckDigit"):
        super_check = str((int(super_check) + 1) % 10)
    account = data["isoNumber"] + data["accountBody"] + account_check + data["cardIdentifier"] + super_check
    if data.get("breakAccountLength"):
        account = account[:-1]
    mode = data["entryMode"]
    restriction = data["promptRestrictionCode"] + data["fuelRestrictionCode"]
    if mode == "TRACK_2_DEFAULT":
        card = {"track2": f";{account}={data['expiryYYMM']}{restriction}{'0' * 11}?"}
    elif mode == "TRACK_1_FALLBACK":
        track1 = (f"%0{account}^{data['cardholderName'][:25]:<25}^{data['expiryYYMM']}"
                  f"{restriction}{'0' * 13}?")
        card = {"track1": track1 if data.get("includeStartSentinel") else track1[1:]}
    elif mode == "MANUAL_ENTRY":
        card = {
            "accountNumber": account,
            "isoNumber": data["isoNumber"] if data.get("sendIsoNumber") else "",
            "expiryMMYY": data["expiryYYMM"][2:] + data["expiryYYMM"][:2] if data.get("sendExpiry") else "",
            "restrictionCode": restriction,
        }
    else:
        card = {"partialTrack2": f"{data['isoNumber']}{account}={data['expiryYYMM']}"}
        if data.get("followOnFullTrack2"):
            card["partialTrack2"] += f"{restriction}{'0' * 11}?"

    return {
        "transactionCode": data["transactionCode"],
        "promptCode": data.get("promptCodeOverride") or data["transactionCode"] + data.get("cardType", matrix["cardTypes"]["fleet"]),
        "entryMode": mode,
        "cardData": card,
        "prompts": data["prompts"],
        "products": data["products"],
        "transactionAmount": data["transactionAmount"],
        "merchantProfile": {"terminalSpecialCode073": data["terminalSpecialCode073"]},
        "tableLoadCardTypes": data["tableLoadCardTypes"],
        "fleetAllowedByAlternateMethod": data["fleetAllowedByAlternateMethod"],
        "readResults": data["readResults"],
        "swipeAttempts": data["swipeAttempts"],
        "deviceExpiryValidated": data["deviceExpiryValidated"],
        "approvedAmount": data["approvedAmount"],
        "authorizationRequestAmount": data["authorizationRequestAmount"],
        "pumpLimit": data["pumpLimit"],
        "effectiveLimit": data["effectiveLimit"],
        "isCatAuthorization": data["isCatAuthorization"],
        "isPosAuthorization": data["isPosAuthorization"],
        "posApprovalBasedOnRequest": data["posApprovalBasedOnRequest"],
        "manualEntryAsteriskOnReceiptAndJournal": data["manualEntryAsteriskOnReceiptAndJournal"],
        "emv": data["emv"],
        "accountBody": data["accountBody"],
        "cardIdentifier": data["cardIdentifier"],
        "accountCheckDigit": account_check,
        "superCheckDigit": super_check,
    }


def validator_model(matrix: dict, catalog: dict) -> dict:
    codes = catalog["productCodes"]
    return {
        "transactions": {item["code"]: item for item in matrix["transactions"]},
        "cardType": matrix["cardTypes"]["fleet"],
        "prompts": {
            "0": [],
            "1": ["DRIVER_ID"],
            "2": ["ODOMETER"],
            "3": ["DRIVER_ID", "ODOMETER"],
        },
        "lengths": {item["field"]: (item["min"], item["max"]) for item in catalog["promptDataLengths"]},
        "productCodes": set(codes["definedCodes"]),
        "fuelCodes": set(codes["fuelCodes"]),
        "accountPrefixes": set(catalog["accountPrefixes"]["values"]),
    }


def validate_test_data(td: dict, model: dict) -> tuple[list[str], bool]:
    violations = set()
    tx, mode, card = td["transactionCode"], td["entryMode"], td["cardData"]
    if tx not in model["transactions"]:
        violations.add("VYR-BR-01")
    if td["promptCode"] != tx + model["cardType"]:
        violations.add("VYR-BR-02")

    tsc_active = td["merchantProfile"]["terminalSpecialCode073"]
    table_loaded = model["cardType"] in td["tableLoadCardTypes"]
    if not tsc_active:
        violations.add("VYR-BR-03")
    if not table_loaded and not td["fleetAllowedByAlternateMethod"]:
        violations.add("VYR-BR-04")
    fleet = tsc_active and (table_loaded or td["fleetAllowedByAlternateMethod"])

    track = None
    if mode == "TRACK_2_DEFAULT":
        track = TRACK_2.fullmatch(card["track2"])
        if not track:
            violations.add("VYR-BR-23")
    elif mode == "TRACK_1_FALLBACK":
        track = TRACK_1.fullmatch(card["track1"])
        if not track:
            violations.add("VYR-BR-23")
    elif mode == "MANUAL_ENTRY":
        if not (card["accountNumber"].isdigit() and len(card["accountNumber"]) == 19
                and card["accountNumber"].startswith("7088") and card["isoNumber"] == "7088" and card["expiryMMYY"].isdigit()
                and len(card["expiryMMYY"]) == 4 and len(card["restrictionCode"]) == 2):
            violations.add("VYR-BR-07")
        if td["swipeAttempts"] < 3:
            violations.add("VYR-BR-07")
        if not td["manualEntryAsteriskOnReceiptAndJournal"]:
            violations.add("VYR-BR-15")
    elif mode == "FOLLOW_ON_PARTIAL_TRACK_2":
        if re.fullmatch(r"\d{4}\d{19}=\d{4}", card["partialTrack2"]) is None:
            violations.add("VYR-BR-12")

    account_number = None
    if track:
        account_number = track["ISO"] + track["ACCOUNT"] + track["ACCOUNT_CHECK"] + track["CARD_ID"] + track["SUPER_CHECK"]
        if not luhn_valid(track["ACCOUNT"] + track["ACCOUNT_CHECK"]):
            violations.add("VYR-BR-20")
        if not luhn_valid(track["ISO"] + track["ACCOUNT"] + track["ACCOUNT_CHECK"] + track["CARD_ID"] + track["SUPER_CHECK"]):
            violations.add("VYR-BR-21")
    elif mode == "MANUAL_ENTRY":
        account_number = card["accountNumber"]
        if len(card["accountNumber"]) == 19:
            if not luhn_valid(card["accountNumber"][4:13]):
                violations.add("VYR-BR-20")
            if not luhn_valid(card["accountNumber"]):
                violations.add("VYR-BR-21")
    elif mode == "FOLLOW_ON_PARTIAL_TRACK_2":
        account_number = re.match(r"\d{23}", card["partialTrack2"])
        account_number = account_number.group(0) if account_number else None
    if mode in {"TRACK_2_DEFAULT", "TRACK_1_FALLBACK"} and tx in model["transactions"]:
        reads = td["readResults"]
        if reads.get("track2") and mode != "TRACK_2_DEFAULT":
            violations.add("VYR-BR-11")
        if track and track["ACCOUNT"][:2] not in model["accountPrefixes"]:
            violations.add("VYR-BR-35")
    if mode == "MANUAL_ENTRY" and td["swipeAttempts"] < 3:
        violations.add("VYR-BR-07")
    if mode == "MANUAL_ENTRY" and len(card["accountNumber"]) == 19 and card["accountNumber"][4:6] not in model["accountPrefixes"]:
        violations.add("VYR-BR-35")
    if not td["deviceExpiryValidated"]:
        violations.add("VYR-BR-13")

    prompt_code = None
    fuel_code = None
    if track:
        prompt_code, fuel_code = track["PROMPT"], track["FUEL"]
    elif mode == "MANUAL_ENTRY" and len(card["restrictionCode"]) == 2:
        prompt_code, fuel_code = card["restrictionCode"]

    if mode in {"TRACK_2_DEFAULT", "TRACK_1_FALLBACK", "MANUAL_ENTRY"}:
        required = model["prompts"].get(prompt_code, [])
        if prompt_code not in model["prompts"]:
            violations.add("VYR-BR-08")
        for prompt in required:
            minimum, maximum = model["lengths"][prompt]
            value = td["prompts"].get(prompt, "")
            if not (value.isdigit() and minimum <= len(value) <= maximum):
                violations.add(PROMPT_RULES[prompt])
        if "VEHICLE_NUMBER" in td["prompts"]:
            violations.add("VYR-BR-16")
    elif mode == "FOLLOW_ON_PARTIAL_TRACK_2":
        required = []

    if fuel_code == "1":
        for product in td["products"]:
            if product["code"] in model["productCodes"] and product["code"] not in model["fuelCodes"]:
                violations.add("VYR-BR-09")
    if not td["products"] or sum(product["amount"] for product in td["products"]) != td["transactionAmount"]:
        violations.add("VYR-BR-10")
    if len({product["code"] for product in td["products"]}) != len(td["products"]):
        violations.add("VYR-BR-19")
    if any(product["code"] not in model["productCodes"] for product in td["products"]):
        violations.add("VYR-BR-09")

    if td["approvedAmount"] > td["authorizationRequestAmount"]:
        violations.add("VYR-BR-17")
    if td["isCatAuthorization"]:
        approved = td["approvedAmount"]
        pump = td["pumpLimit"]
        if approved > pump and td.get("effectiveLimit") != pump:
            violations.add("VYR-BR-18")
        if approved <= pump and td.get("effectiveLimit") != approved:
            violations.add("VYR-BR-18")
    if td["isPosAuthorization"] and not td["posApprovalBasedOnRequest"]:
        violations.add("VYR-BR-17")

    emv = td["emv"]
    if emv["uniqueIdentifier"] not in {"0003", "8003"}:
        violations.add("VYR-BR-22")
    if emv["productIdentifier"] != "0001":
        violations.add("VYR-BR-22")
    if emv["aid"] != "A0000000049999C00016" or emv["fleetApplicationPriority"] != 1:
        violations.add("VYR-BR-23")
    if emv["cardRemovedEarly"] and (not emv["transactionAborted"] or (emv["authorizationReceived"] and not emv["reversalSent"])):
        violations.add("VYR-BR-24")
    if emv["offlineProductsAllowed"] and not emv["offlineRestrictionChecked"]:
        violations.add("VYR-BR-25")
    if emv["approvedReceipt"] and not (emv["receiptIncludesAid"] and (emv["receiptIncludesPreferredName"] or emv["receiptIncludesLabel"])):
        violations.add("VYR-BR-26")
    if emv["serviceCodeFirstDigit"] in {"2", "6"} and not emv["chipInsertRequested"]:
        violations.add("VYR-BR-27")
    if not emv["promptScriptEnabled"] and emv["terminalPromptOrder"] != emv["requiredPromptOrder"]:
        violations.add("VYR-BR-28")
    if emv["abnormalDecline"] and not emv["troubleshootingDataStored"]:
        violations.add("VYR-BR-29")
    if not emv["terminalOnlineCapable"]:
        violations.add("VYR-BR-30")
    if not emv["terminalParametersSupported"]:
        violations.add("VYR-BR-31")
    if not (emv["noCvmSupported"] and emv["onlinePinSupported"]) or emv["offlinePinSupported"]:
        violations.add("VYR-BR-32")
    if emv["terminalAttended"] != emv["signatureSupported"]:
        violations.add("VYR-BR-32")
    if emv["offlineCapable"] and not (emv["ddaSupported"] and emv["cdaSupported"]):
        violations.add("VYR-BR-33")
    if not emv["promptingUsesThirdPartyData"]:
        violations.add("VYR-BR-34")

    return sorted(violations), fleet


def fleet_mode(td: dict) -> bool:
    return td["entryMode"] in TRACK_MODES


MUTATIONS = (
    ("PROMPT_CODE_WRONG_CARD_TYPE", lambda d, td: {"cardType": "020"}, lambda td: True, ["VYR-BR-02"]),
    ("TERMINAL_SPECIAL_DISABLED", lambda d, td: {"terminalSpecialCode073": False}, lambda td: True, ["VYR-BR-03"]),
    ("UNREQUESTED_TABLE_LOAD", lambda d, td: {"tableLoadCardTypes": []}, lambda td: not td["fleetAllowedByAlternateMethod"], ["VYR-BR-04"]),
    ("EXPIRY_VALIDATION_SKIPPED", lambda d, td: {"deviceExpiryValidated": False}, lambda td: True, ["VYR-BR-13"]),
    ("PRODUCT_TOTAL_OFF_BY_CENT", lambda d, td: {"transactionAmount": td["transactionAmount"] + 1}, lambda td: True, ["VYR-BR-10"]),
    ("ACCOUNT_CHECK_DIGIT_INVALID", lambda d, td: {"breakAccountCheckDigit": True}, lambda td: td["entryMode"] in TRACK_MODES, ["VYR-BR-20"]),
    ("SUPER_CHECK_DIGIT_INVALID", lambda d, td: {"breakSuperCheckDigit": True}, lambda td: td["entryMode"] in TRACK_MODES, ["VYR-BR-21"]),
    ("FUEL_ONLY_WITH_MERCHANDISE", lambda d, td: {"fuelRestrictionCode": "1", "products": [{"code": "400", "amount": td["transactionAmount"]}]},
     lambda td: td["entryMode"] in TRACK_MODES, ["VYR-BR-09"]),
    ("ACCOUNT_PREFIX_OUTSIDE_VOYAGER", lambda d, td: {"accountBody": "90123456"}, lambda td: td["entryMode"] in TRACK_MODES, ["VYR-BR-35"]),
    ("FOLLOW_ON_FULL_TRACK2", lambda d, td: {"followOnFullTrack2": True}, lambda td: td["entryMode"] == "FOLLOW_ON_PARTIAL_TRACK_2", ["VYR-BR-12"]),
)

PROGRAM = SimpleNamespace(prefix=PREFIX, build_matrix=build_matrix, make_test_data=make_test_data,
                          validate_test_data=validate_test_data, validator_model=validator_model, mutations=MUTATIONS)

def validate_catalog(cfg: dict, sources: dict, h) -> dict:
    catalog = cfg["catalog"]
    prefixes = catalog["accountPrefixes"]
    require = h.require
    require(prefixes == ["85", "86", "87", "88", "89"], "Voyager account prefixes must match the embossed-card statement")
    prefix_evidence = h.petroleum_evidence(
        sources, 92, "Voyager’s Account Numbers start with 85, 86, 87, 88, or 89.", None)

    enumerations = catalog["enumerations"]
    for spec in enumerations.values():
        for value, item in spec["values"].items():
            h.petroleum_evidence(sources, item["pdfPage"], item["quote"], None)

    lengths = []
    for item in catalog["promptDataLengths"]:
        evidence = h.petroleum_evidence(sources, item["pdfPage"], item["quote"], None)
        lengths.append({**item, **evidence})

    layouts = {}
    for name, spec in catalog["trackLayouts"].items():
        require([field["no"] for field in spec["fields"]] == list(range(1, len(spec["fields"]) + 1)),
                f"{name} field numbers must be sequential")
        for field in spec["fields"]:
            h.petroleum_evidence(sources, field["pdfPage"], field["quote"], None)
        layouts[name] = {
            "fields": spec["fields"],
            "totalLength": spec["totalLength"],
            "reviewNotes": spec.get("reviewNotes", []),
        }

    examples = []
    for example in catalog["examples"]:
        evidence = h.petroleum_evidence(sources, example["pdfPage"], example["quote"], None)
        examples.append({
            "layout": example["layout"],
            "status": "REVIEW_REQUIRED",
            "evidence": evidence,
            "compact": "".join(example["quote"].split()),
            "checks": [{
                "check": "source-example",
                "status": "REVIEW_REQUIRED",
                "detail": "Example values and check digits require source-image and Voyager-specific algorithm review.",
            }],
        })

    codes = h.validate_product_codes(catalog["productCodes"], sources)
    emv = {
        key: h.petroleum_evidence(sources, item["pdfPage"], item["quote"], None)
        for key, item in catalog["emvEvidence"].items()
    }
    return {
        "binRanges": {
            "prefixes": [[value, value] for value in prefixes],
            "accountPrefixes": prefixes,
            "binCount": len(prefixes),
            "consistentPages": [92],
            "evidence": prefix_evidence,
        },
        "enumerations": enumerations,
        "promptDataLengths": lengths,
        "trackLayouts": layouts,
        "exampleChecks": examples,
        "embossing": {
            "note": catalog["embossing"]["note"],
            "evidence": [h.petroleum_evidence(sources, item["pdfPage"], item["quote"], None)
                         for item in catalog["embossing"]["evidence"]],
        },
        "productCodes": codes,
        "emvEvidence": emv,
        "accountPrefixes": {"values": prefixes, "evidence": prefix_evidence},
    }


def render_catalog(catalog: dict, cell) -> list[str]:
    lines = [
        "",
        "## Voyager catalog checks",
        "",
        "Account prefixes recorded from the embossing section: " + ", ".join(catalog["accountPrefixes"]["values"]) + ".",
        "Track layout rows are retained with page-local quotes; printed examples remain review-required.",
        "",
        "| Restriction field | Value | Meaning |",
        "|---|---|---|",
    ]
    for name, spec in catalog["enumerations"].items():
        for value, description in spec["values"].items():
            lines.append(f"| {cell(name)} | {cell(value)} | {cell(description['meaning'])} |")
    lines += [
        "",
        "Voyager EMV rules and source references (Appendices I–L) are catalogued as chapter evidence; "
        "appendix-wide validation remains in the later Petroleum appendix rollout.",
    ]
    for name, layout in catalog["trackLayouts"].items():
        lines.append(f"- {name}: {len(layout['fields'])} fields, source-declared length {layout['totalLength']}.")
    return lines
