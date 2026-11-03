"""Visa fleet (Petroleum chapter 8) plug-in: catalog checks, context matrix, test data, rule validator and mutations."""

import copy
import re
from types import SimpleNamespace

from petroleum_chain import ENTRY_MODES, TRACK_MODES, luhn_digit, luhn_valid, require

PREFIX = "VFC"
PROMPT_RULES = {"DRIVER_ID": "VFC-BR-06", "ODOMETER": "VFC-BR-07", "VEHICLE_NUMBER": "VFC-BR-08", "ID_NUMBER": "VFC-BR-09"}
BIN_ITEM = re.compile(r"•\s*(\d{6})(?:\s*[–-]\s*(\d{6}))?")
RANGE = re.compile(r"(\d{6})\s*[–-]\s*(\d{6})")
TRACK_2 = re.compile(r";(?P<PAN>\d{16})=(?P<EXPIRY>\d{4})(?P<SERVICE_CODE>\d{3})(?P<DISCRETIONARY>\d{3,})\?")
TRACK_1 = re.compile(r"B(?P<PAN>\d{16})\^(?P<NAME>[^^]{2,26})\^(?P<EXPIRY>\d{4})(?P<SERVICE_CODE>\d{3})(?P<REST>[0-9A-Za-z]{19,})\?")


def expand(ranges: list[tuple[str, str]]) -> set[int]:
    return {n for low, high in ranges for n in range(int(low), int(high) + 1)}


def compress(numbers: set[int]) -> list[str]:
    runs, ordered = [], sorted(numbers)
    for n in ordered:
        if runs and n == runs[-1][1] + 1:
            runs[-1][1] = n
        else:
            runs.append([n, n])
    return [f"{a}" if a == b else f"{a}-{b}" for a, b in runs]


def enum_codes(code: str) -> list[str]:
    low, _, high = code.partition("–")
    return [str(n) for n in range(int(low), int(high or low) + 1)]


def validate_bins(cfg: dict, sources: dict, h) -> dict:
    ranges = []
    for page_no in cfg["pages"]:
        for m in BIN_ITEM.finditer(h.page_text(sources, page_no)):
            ranges.append((m[1], m[2] or m[1]))
    require(len(ranges) == cfg["expectedCount"], f"Expected {cfg['expectedCount']} Visa fleet BIN items, found {len(ranges)}")
    for (low, high), (next_low, _) in zip(ranges, ranges[1:] + [("999999", "")]):
        require(low <= high < next_low, f"Visa fleet BIN item {low}-{high} is out of order or overlaps")
    appendix = cfg["appendixA"]
    evidence = h.petroleum_evidence(sources, appendix["pdfPage"], appendix["quote"], None)
    for quote in appendix["accountQuotes"]:
        h.petroleum_evidence(sources, appendix["pdfPage"], quote, None)
    appendix_ranges = RANGE.findall(appendix["quote"])
    require(bool(appendix_ranges), "No Appendix A Visa fleet ranges parsed")
    chapter, other = expand(ranges), expand(appendix_ranges)
    differences = {"chapterOnly": compress(chapter - other), "appendixOnly": compress(other - chapter)}
    require(differences == cfg["expectedDifferences"],
            f"Chapter 8 and Appendix A Visa fleet BINs differ from the declared exceptions: {differences}")
    return {"prefixes": [list(r) for r in ranges], "consistentPages": cfg["pages"], "binCount": len(chapter),
            "appendixA": {"ranges": [list(r) for r in appendix_ranges], "binCount": len(other), "evidence": evidence,
                          **differences}}


def validate_enumerations(cfg: dict, sources: dict, h) -> dict:
    result = {}
    for name, spec in cfg.items():
        for code, text in spec["values"].items():
            marker = spec.get("footnoteMarkers", {}).get(code, "")
            for page_no in spec["pdfPages"]:
                require(f"{code} = {text}{marker}" in h.page_text(sources, page_no),
                        f"{name} value not found on page {page_no}: {code} = {text}{marker}")
        codes = [c for code in spec["values"] for c in enum_codes(code)]
        require(codes == [str(n) for n in range(10)], f"{name} must define digits 0 to 9 exactly once")
        result[name] = {"pdfPages": spec["pdfPages"], "values": spec["values"],
                        "byDigit": {c: text for code, text in spec["values"].items() for c in enum_codes(code)}}
    return result


def validate_layouts(cfg: dict, sources: dict, h) -> dict:
    layouts = {}
    for name, layout in cfg.items():
        fields = layout["fields"]
        require([f["no"] for f in fields] == list(range(1, len(fields) + 1)), f"{name} field numbers must be sequential")
        for f in fields:
            require(f["row"] in h.page_text(sources, f["pdfPage"]), f"{name} row not found on page {f['pdfPage']}: {f['row']}")
        notes = [h.petroleum_evidence(sources, q["pdfPage"], q["quote"], None) for q in layout["fleetPositionQuotes"]]
        layouts[name] = {"fields": fields, "fleetPositionEvidence": notes, "reviewNotes": layout.get("reviewNotes", []),
                         "totalLength": "variable length; the fleet positions are always the last three before the End Sentinel"}
    return layouts


def fleet_positions(layout: str, compact: str) -> tuple[re.Match | None, str]:
    if layout == "TRACK_2":
        m = TRACK_2.fullmatch(compact)
        return m, m["DISCRETIONARY"][-3:] if m else ""
    m = TRACK_1.fullmatch(compact[1:] if compact.startswith("%") else compact)
    return m, m["REST"][-3:] if m else ""


def check_example(example: dict, bins: set[int], enums: dict, sources: dict, h) -> dict:
    evidence = h.petroleum_evidence(sources, example["pdfPage"], example["quote"], None)
    compact = "".join(example["quote"].split()).removesuffix("<LRC>")
    checks = [{"check": "LRC", "status": "NOT_PRINTED", "detail": "The printed example shows <LRC> instead of the LRC byte."}]

    def add(check: str, passed: bool, detail: str) -> None:
        checks.append({"check": check, "status": "PASS" if passed else "REVIEW_REQUIRED", "detail": detail})

    m, fleet = fleet_positions(example["layout"], compact)
    add("layoutParse", m is not None, "separators, 16-digit PAN, YYMM expiry and service code parse")
    if m:
        account = m["PAN"]
        add("binRange", int(account[:6]) in bins, f"prefix {account[:6]} against the chapter 8 Visa fleet BIN list")
        add("mod10", luhn_valid(account), f"Mod 10 check digit on {account}")
        if example["layout"] == "TRACK_1":
            rest = m["REST"]
            add("discretionaryLength", len(rest) - 11 in {8, 9, 10, 13, 14, 15},
                f"{len(rest) - 11} characters before the 11-character Visa-Reserved field (8-10, plus 5 if PIN Verification Data is present)")
            add("visaReservedPosition9", rest[-3] == "0", f"Visa-Reserved position 9 is {rest[-3]!r}; source fixes it to 0")
        else:
            add("fleetServiceThirdToLast", fleet[0] == "0", f"third-to-last position is {fleet[0]!r}; source fixes it to 0")
        sei = enums["serviceEnhancementIndicator"]["byDigit"][fleet[1]]
        add("serviceEnhancementIndicator", fleet[1] in "012", f"{fleet[1]} = {sei}")
        add("servicePrompt", fleet[2] in enums["servicePrompt"]["byDigit"],
            f"{fleet[2]} = {enums['servicePrompt']['byDigit'][fleet[2]]}")
    status = "PASS" if all(c["status"] in {"PASS", "NOT_PRINTED"} for c in checks) else "REVIEW_REQUIRED"
    return {"layout": example["layout"], "status": status, "evidence": evidence, "compact": compact, "checks": checks}


def validate_catalog(cfg: dict, sources: dict, h) -> dict:
    catalog = cfg["catalog"]
    bins = validate_bins(catalog["binRanges"], sources, h)
    enums = validate_enumerations(catalog["enumerations"], sources, h)
    lengths = []
    for item in catalog["promptDataLengths"]:
        expected = item.get("rangeText", f"{item['min']}–{item['max']}")
        require("".join(expected.split()) in "".join(item["quote"].split()), f"{item['field']} quote disagrees with its range")
        lengths.append({**item, **h.petroleum_evidence(sources, item["pdfPage"], item["quote"], None)})
    layouts = validate_layouts(catalog["trackLayouts"], sources, h)
    chapter_bins = expand([tuple(p) for p in bins["prefixes"]])
    examples = [check_example(e, chapter_bins, enums, sources, h) for e in catalog["examples"]]
    emboss = catalog["embossing"]
    emboss_evidence = [h.petroleum_evidence(sources, emboss["pdfPage"], q, None) for q in emboss["quotes"]]
    codes = h.validate_product_codes(catalog["productCodes"], sources)
    maintenance = set(catalog["productCodes"]["maintenanceCategories"])
    require(maintenance <= {c["name"] for c in codes["categories"]}, "Unknown maintenance category names")
    codes["maintenanceCodes"] = sorted(f"{n:03}" for r in codes["rows"] if r["category"] in maintenance
                                       for n in range(r["low"], r["high"] + 1))
    codes["maintenanceInterpretation"] = catalog["productCodes"]["maintenanceInterpretation"]
    return {"binRanges": bins, "enumerations": enums, "promptDataLengths": lengths, "trackLayouts": layouts,
            "exampleChecks": examples, "embossing": {"note": emboss["note"], "evidence": emboss_evidence},
            "productCodes": codes}


def render_catalog(catalog: dict, cell) -> list[str]:
    bins, codes = catalog["binRanges"], catalog["productCodes"]
    appendix = bins["appendixA"]
    lines = ["", "## Visa fleet BINs: chapter 8 list versus Appendix A", "",
             f"Chapter 8 lists {len(bins['prefixes'])} BIN items ({bins['binCount']} six-digit BINs); the Appendix A Visa Fleet row "
             f"lists {len(appendix['ranges'])} ranges ({appendix['binCount']} BINs).", "",
             "| Difference | BINs |", "|---|---|",
             f"| Chapter 8 only | {cell(', '.join(appendix['chapterOnly']) or '-')} |",
             f"| Appendix A only | {cell(', '.join(appendix['appendixOnly']) or '-')} |", "",
             "## Service Enhancement Indicator and Service Prompt", "", "| Field | Digit | Meaning |", "|---|---|---|"]
    for name, enum in catalog["enumerations"].items():
        for code, text in enum["values"].items():
            lines.append(f"| {name} | {code} | {cell(text)} |")
    lines += ["", f"Maintenance codes for Service Enhancement Indicator 1: {len(codes['maintenanceCodes'])} codes. "
              f"{codes['maintenanceInterpretation']}"]
    for name, layout in catalog["trackLayouts"].items():
        for note in layout["reviewNotes"]:
            lines.append(f"- {name} review note: {note}")
    return lines


def derive_prompts(text: str, phrases: dict) -> list[str]:
    text = re.sub(r"\d+$", "", text.strip()).lower()
    if "reserved" in text or text == "no prompt":
        return []
    return sorted(phrases[part.strip()] for part in text.split(" and "))


def build_matrix(chain: dict, context: dict, sources: dict, atl105_evidence) -> dict:
    matrix = chain["contextMatrix"]
    supported = context["scope"]["supportedTransactions"]["values"]
    require([t["petroleum"] for t in matrix["transactions"]] == supported,
            "Matrix transactions must match the scoped supported transactions in order")
    rule = [atl105_evidence(sources, e) for e in matrix["promptCodeRule"]]
    require({e["version"] for e in rule} == {"2026-3", "2026-4"}, "Prompt Code rule needs both ATL105 releases")
    card_type = [atl105_evidence(sources, e) for e in matrix["cardTypeEvidence"]]
    require({e["version"] for e in card_type} == {"2026-3", "2026-4"}, "Card type evidence needs both ATL105 releases")
    require(all(e["quote"].startswith(matrix["cardType"] + " ") for e in card_type), "Card type evidence does not quote the code")
    transactions = []
    for t in matrix["transactions"]:
        evidence = [atl105_evidence(sources, e) for e in t["atl105"]]
        require({e["version"] for e in evidence} == {"2026-3", "2026-4"}, f"Transaction {t['code']} needs both releases")
        require(any(e["quote"].startswith(t["code"] + " ") for e in evidence), f"Transaction code {t['code']} is not quoted")
        require(t["role"] in matrix["entryModesByRole"], f"Unknown role {t['role']}")
        require(t["role"] != "REVIEW" or bool(t.get("reviewReason")), f"Transaction {t['code']} needs a review reason")
        code = t["code"] + matrix["cardType"]
        transactions.append({**t, "atl105": evidence, "promptCodes": {"fleet": code, "fallback": code},
                             "reviewRequired": t["basis"] != "EXACT_NAME" or t["role"] == "REVIEW"})
    enums = context["catalog"]["enumerations"]["servicePrompt"]["byDigit"]
    prompts = matrix["servicePromptPrompts"]
    require(sorted(prompts) == sorted(enums), "Service Prompt prompts must cover digits 0 to 9")
    for digit, required in prompts.items():
        require(sorted(required) == derive_prompts(enums[digit], matrix["promptPhrases"]),
                f"Service Prompt {digit} prompts disagree with '{enums[digit]}'")
    reconciliation = {r["id"]: r for r in context["reconciliation"]}
    appendix = {}
    for name, item in matrix["appendixAPrompts"].items():
        evidence = next((e for r in reconciliation.values() for e in r["petroleum"] if e["quote"] == item["quote"]), None)
        require(evidence is not None, f"Appendix A prompt quote for {name} is not a reconciled inventory item")
        digits = sorted(re.findall(r"\b(\d)\b", item["quote"].split("Value")[0]))
        require(digits == sorted(d for d, p in prompts.items() if name in p),
                f"Appendix A prompts {name} for Service Prompt {digits}, chapter 8 implies otherwise")
        appendix[name] = {"servicePrompts": digits, **evidence}
    lengths = {item["field"] for item in context["catalog"]["promptDataLengths"]}
    for name, element in matrix["promptElements"].items():
        require(element["lengthField"] in lengths and element["reconciliation"] in reconciliation,
                f"Prompt {name} lacks a verified length or reconciliation")
    segment = [atl105_evidence(sources, e) for e in matrix["fleetDataSegment"]]
    cells = []
    for t in transactions:
        for mode in ENTRY_MODES:
            status = ("REVIEW_REQUIRED" if t["role"] == "REVIEW" else
                      "SUPPORTED" if mode in matrix["entryModesByRole"][t["role"]] else "NOT_APPLICABLE")
            cells.append({"transactionCode": t["code"], "entryMode": mode, "status": status})
    return {"transactions": transactions, "promptCodeRule": rule, "cardTypes": {"fleet": matrix["cardType"],
            "fallback": matrix["cardType"]}, "cardTypeEvidence": card_type, "tableLoadCardType": matrix["cardType"],
            "promptSelector": {"label": "Service Prompt", "prompts": prompts}, "servicePromptPrompts": prompts,
            "appendixAPrompts": appendix, "manualEntryPrompts": matrix["manualEntryPrompts"],
            "promptElements": matrix["promptElements"], "fleetDataSegment": segment, "cells": cells}


def make_test_data(defaults: dict, overrides: dict, matrix: dict) -> dict:
    d = {**copy.deepcopy(defaults), **copy.deepcopy(overrides)}
    tx = d["transactionCode"]
    body = d["bin"] + d["accountBody"]
    account = body + luhn_digit(body)
    if d.get("breakCheckDigit"):
        account = account[:-1] + str((int(account[-1]) + 1) % 10)
    expiry, fleet = d["expiryYYMM"], d.get("fleetServiceDigit", "0") + d["serviceEnhancementIndicator"] + d["servicePrompt"]
    mode = d["entryMode"]
    if mode == "TRACK_2_DEFAULT":
        card = {"track2": f";{account}={expiry}{d['serviceCode']}{d['cvv']}{d['issuerInformation']}{fleet}?"}
    elif mode == "TRACK_1_FALLBACK":
        track1 = (f"%B{account}^{d['cardholderName']}^{expiry}{d['serviceCode']}{d['track1Discretionary']}"
                  f"00{d['cvv']}000{fleet}?")
        card = {"track1": track1 if d.get("includeStartSentinel") else track1[1:]}
    elif mode == "MANUAL_ENTRY":
        card = {"account": account[:d.get("manualAccountLength", 16)],
                "expiryMMYY": expiry[2:] + expiry[:2] if d["sendExpiry"] else None}
    else:
        card = {"partialTrack2": f";{account}={expiry}{d['serviceCode']}?" if d.get("followOnFullTrack2") else f"{account}={expiry}"}
    return {
        "transactionCode": tx, "promptCode": d.get("promptCodeOverride") or tx + d.get("cardType", matrix["cardTypes"]["fleet"]),
        "entryMode": mode, "cardData": card, "prompts": d["prompts"], "products": d["products"],
        "transactionAmount": d["transactionAmount"],
        "merchantProfile": {"creditOptionActive": d["creditOptionActive"]},
        "tableLoadCardTypes": d["tableLoadCardTypes"], "fleetAllowedByAlternateMethod": d["fleetAllowedByAlternateMethod"],
        "readResults": d["readResults"], "swipeAttempts": d["swipeAttempts"],
        "deviceExpiryValidated": d["deviceExpiryValidated"], "cardholderReceiptShowsExpiry": d["cardholderReceiptShowsExpiry"],
        "manualEntryAsteriskOnReceiptAndJournal": d["manualEntryAsteriskOnReceiptAndJournal"],
    }


def validator_model(matrix: dict, catalog: dict) -> dict:
    codes = catalog["productCodes"]
    return {
        "transactions": {t["code"]: t for t in matrix["transactions"]}, "cardType": matrix["cardTypes"]["fleet"],
        "servicePromptPrompts": matrix["servicePromptPrompts"], "manualEntryPrompts": matrix["manualEntryPrompts"],
        "promptElements": matrix["promptElements"],
        "lengths": {i["field"]: (i["min"], i["max"]) for i in catalog["promptDataLengths"]},
        "bins": expand([tuple(p) for p in catalog["binRanges"]["prefixes"]]),
        "productCodes": set(codes["definedCodes"]), "fuelCodes": set(codes["fuelCodes"]),
        "maintenanceCodes": set(codes["maintenanceCodes"]),
    }


def validate_test_data(td: dict, model: dict) -> tuple[list[str], bool]:
    """Return violated BR IDs and whether Visa fleet rules applied (the account is in the chapter 8 BIN list)."""
    v = set()
    tx, pc, mode, card = td["transactionCode"], td["promptCode"], td["entryMode"], td["cardData"]
    transactions = model["transactions"]
    if tx not in transactions:
        v.add("VFC-BR-01")
    account, fleet = None, None
    if mode in TRACK_MODES:
        data = card.get("track2") or card.get("track1")
        if mode == "TRACK_1_FALLBACK" and data.startswith("%"):
            v.add("VFC-BR-19")
        m, fleet = fleet_positions("TRACK_2" if mode == "TRACK_2_DEFAULT" else "TRACK_1", data)
        if m:
            account = m["PAN"]
        else:
            v.add("VFC-BR-20")
            fleet = None
    elif mode == "MANUAL_ENTRY":
        account = card["account"]
        if len(account) != 16 or not account.isdigit():
            v.add("VFC-BR-11")
    elif re.fullmatch(r"\d{16}=\d{4}", card["partialTrack2"]) is None:
        v.add("VFC-BR-18")
    if account is None:
        found = re.search(r"(\d{16})", next(iter(card.values())) or "")
        account = found.group(1) if found else ""
    if not (len(account) >= 6 and account[:6].isdigit() and int(account[:6]) in model["bins"]):
        return sorted(v), False
    if len(pc) != 4 or pc[0] != tx or pc[1:] != model["cardType"]:
        v.add("VFC-BR-05")
    if not td["merchantProfile"]["creditOptionActive"]:
        v.add("VFC-BR-03")
    elif model["cardType"] not in td["tableLoadCardTypes"] and not td["fleetAllowedByAlternateMethod"]:
        v.add("VFC-BR-04")
    if len(account) == 16 and account.isdigit() and not luhn_valid(account):
        v.add("VFC-BR-16")
    role = transactions.get(tx, {}).get("role")
    if role == "ORIGINAL":
        reads = td["readResults"]
        expected = "TRACK_2_DEFAULT" if reads["track2"] else "TRACK_1_FALLBACK" if reads["track1"] else "MANUAL_ENTRY"
        if mode != expected:
            v.add("VFC-BR-17")
        if mode == "MANUAL_ENTRY" and td["swipeAttempts"] < 3:
            v.add("VFC-BR-11")
    if role == "FOLLOW_ON" and mode != "FOLLOW_ON_PARTIAL_TRACK_2":
        v.add("VFC-BR-18")
    sei = None
    required = []
    if fleet:
        if fleet[0] != "0":
            v.add("VFC-BR-20")
        sei, required = fleet[1], model["servicePromptPrompts"][fleet[2]]
        if set(td["prompts"]) - set(required):
            v.add("VFC-BR-10")
    elif mode == "MANUAL_ENTRY":
        required = model["manualEntryPrompts"]
        if set(td["prompts"]) - set(required):
            v.add("VFC-BR-26")
        if not td["manualEntryAsteriskOnReceiptAndJournal"]:
            v.add("VFC-BR-12")
    for prompt in required:
        low, high = model["lengths"][model["promptElements"][prompt]["lengthField"]]
        value = td["prompts"].get(prompt, "")
        if not (value.isdigit() and low <= len(value) <= high):
            v.add(PROMPT_RULES[prompt])
    if not td["deviceExpiryValidated"]:
        v.add("VFC-BR-13")
    if td["cardholderReceiptShowsExpiry"]:
        v.add("VFC-BR-14")
    if transactions.get(tx, {}).get("authorization") and mode == "MANUAL_ENTRY" and not card["expiryMMYY"]:
        v.add("VFC-BR-15")
    products = td["products"]
    codes = [p["code"] for p in products]
    if not products or sum(p["amount"] for p in products) != td["transactionAmount"]:
        v.add("VFC-BR-24")
    if len(set(codes)) != len(codes):
        v.add("VFC-BR-23")
    if any(code not in model["productCodes"] for code in codes):
        v.add("VFC-BR-25")
    known = [code for code in codes if code in model["productCodes"]]
    if sei == "2" and any(code not in model["fuelCodes"] for code in known):
        v.add("VFC-BR-21")
    if sei == "1" and any(code not in model["fuelCodes"] | model["maintenanceCodes"] for code in known):
        v.add("VFC-BR-22")
    return sorted(v), True


def fleet_track(td: dict) -> bool:
    return td["entryMode"] in TRACK_MODES


MUTATIONS = (
    ("BREAK_CHECK_DIGIT", lambda d, td: {"breakCheckDigit": True}, lambda td: True, ["VFC-BR-16"]),
    ("PROMPT_CODE_WRONG_CARD_TYPE_080", lambda d, td: {"cardType": "080"}, lambda td: True, ["VFC-BR-05"]),
    ("PROMPT_CODE_WRONG_TRANSACTION", lambda d, td: {"promptCodeOverride": ("3" if td["transactionCode"] != "3" else "0") + "020"},
     lambda td: True, ["VFC-BR-05"]),
    ("CREDIT_OPTION_INACTIVE", lambda d, td: {"creditOptionActive": False}, lambda td: True, ["VFC-BR-03"]),
    ("FLEET_NOT_ALLOWED", lambda d, td: {"tableLoadCardTypes": [], "fleetAllowedByAlternateMethod": False}, lambda td: True, ["VFC-BR-04"]),
    ("SKIP_EXPIRY_VALIDATION", lambda d, td: {"deviceExpiryValidated": False}, lambda td: True, ["VFC-BR-13"]),
    ("RECEIPT_SHOWS_EXPIRY", lambda d, td: {"cardholderReceiptShowsExpiry": True}, lambda td: True, ["VFC-BR-14"]),
    ("NO_PRODUCTS", lambda d, td: {"products": []}, lambda td: True, ["VFC-BR-24"]),
    ("PRODUCT_SUM_OFF_BY_ONE_CENT", lambda d, td: {"transactionAmount": td["transactionAmount"] + 1}, lambda td: True, ["VFC-BR-24"]),
    ("DUPLICATE_PRODUCT_CODE", lambda d, td: {"products": [{"code": td["products"][0]["code"], "amount": 1},
                                                           {**td["products"][0], "amount": td["products"][0]["amount"] - 1}] + td["products"][1:]},
     lambda td: td["products"][0]["amount"] > 1, ["VFC-BR-23"]),
    ("UNKNOWN_PRODUCT_CODE", lambda d, td: {"products": [{**td["products"][0], "code": "P01"}] + td["products"][1:]},
     lambda td: bool(td["products"]), ["VFC-BR-25"]),
    ("FUEL_ONLY_WITH_CAR_WASH", lambda d, td: {"serviceEnhancementIndicator": "2", "products": [
        {**p, "code": "102"} if i == 0 and all(q["code"] != "102" for q in td["products"]) else p
        for i, p in enumerate(td["products"])]}, fleet_track, ["VFC-BR-21"]),
    ("FUEL_AND_MAINTENANCE_WITH_MERCHANDISE", lambda d, td: {"serviceEnhancementIndicator": "1", "products": [
        {**p, "code": "400"} if i == 0 else p for i, p in enumerate(td["products"])]}, fleet_track, ["VFC-BR-22"]),
    ("FLEET_SERVICE_POSITION_NOT_ZERO", lambda d, td: {"fleetServiceDigit": "8"}, fleet_track, ["VFC-BR-20"]),
    ("ODOMETER_NINE_DIGITS", lambda d, td: {"prompts": {**td["prompts"], "ODOMETER": "123456789"}},
     lambda td: "ODOMETER" in td["prompts"] and td["entryMode"] != "FOLLOW_ON_PARTIAL_TRACK_2", ["VFC-BR-07"]),
    ("UNREQUESTED_VEHICLE_PROMPT", lambda d, td: {"prompts": {**td["prompts"], "VEHICLE_NUMBER": "1234"}},
     lambda td: fleet_track(td) and td["cardData"].get("track2", td["cardData"].get("track1", ""))[-2] != "2",
     ["VFC-BR-10"]),
    ("KEYED_PROMPTS_FOR_DRIVER_ID", lambda d, td: {"prompts": {**td["prompts"], "DRIVER_ID": "1234"}},
     lambda td: td["entryMode"] == "MANUAL_ENTRY", ["VFC-BR-26"]),
    ("KEYED_WITHOUT_ASTERISK", lambda d, td: {"manualEntryAsteriskOnReceiptAndJournal": False},
     lambda td: td["entryMode"] == "MANUAL_ENTRY", ["VFC-BR-12"]),
    ("TRACK_1_WITH_START_SENTINEL", lambda d, td: {"includeStartSentinel": True}, lambda td: td["entryMode"] == "TRACK_1_FALLBACK", ["VFC-BR-19"]),
    ("FOLLOW_ON_FULL_TRACK_2", lambda d, td: {"followOnFullTrack2": True}, lambda td: td["entryMode"] == "FOLLOW_ON_PARTIAL_TRACK_2", ["VFC-BR-18"]),
    ("MANUAL_AFTER_TWO_ATTEMPTS", lambda d, td: {"swipeAttempts": 2}, lambda td: td["entryMode"] == "MANUAL_ENTRY", ["VFC-BR-11"]),
)

PROGRAM = SimpleNamespace(prefix=PREFIX, build_matrix=build_matrix, make_test_data=make_test_data,
                          validate_test_data=validate_test_data, validator_model=validator_model, mutations=MUTATIONS)
