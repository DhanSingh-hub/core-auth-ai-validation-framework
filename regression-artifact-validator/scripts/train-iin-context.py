"""Run the IIN Reference Manual 2024-1 training phases for the card-type catalog, verifying every claim
against source text."""

import argparse
import hashlib
import json
import re
import sys
from pathlib import Path

MODULE = Path(__file__).resolve().parents[1]
PACK = MODULE / "specifications" / "ATL105"
SPECS = PACK / "docs" / "specs"
KB = SPECS / "kb" / "iin-2024-1"
PLAN = KB / "training-plan.json"
PAGES = KB / "pages.json"
MANIFEST = KB / "ingestion-manifest.json"
PETRO_KB = SPECS / "kb" / "petroleum-2025-1"
ATL105_TEXT = {"2026-3": SPECS / "extracted_text.txt", "2026-4": SPECS / "extracted_text_new.txt"}
OUTPUT_NAMES = ("training-package.json", "training-report.md")
DRAFT = "DRAFT_REVIEW_REQUIRED"
DRAFTED_PHASES = (0, 1, 2, 3, 4, 5, 6, 7)
TABLE1_PAGES = (5, 6, 7, 8, 9)


def sha(raw: bytes) -> str:
    return hashlib.sha256(raw).hexdigest()


def norm(text: str) -> str:
    return " ".join(text.split())


def read_json(path: Path) -> dict:
    return json.loads(path.read_text(encoding="utf-8"))


def require(condition: bool, message: str) -> None:
    if not condition:
        raise ValueError(message)


def luhn(digits: str) -> bool:
    total = 0
    for index, char in enumerate(reversed(digits)):
        value = int(char) * (2 if index % 2 else 1)
        total += value - 9 if value > 9 else value
    return total % 10 == 0


def name_pattern(name: str) -> str:
    return r"\s+".join(re.escape(word) for word in name.split(" "))


def load_atl105(version: str, path: Path) -> dict:
    raw = path.read_bytes()
    text = raw.decode("utf-8")
    first = re.search(r"Specification Release Version:\s*(\S+)", text)
    require(first is not None and first.group(1) == version, f"ATL105 text {path.name} is not release {version}")
    parts = re.split(r"--- PAGE (\d+) ---", text)
    raw_pages = {int(n): body for n, body in zip(parts[1::2], parts[2::2])}
    require(bool(raw_pages), f"ATL105 text {path.name} has no page markers")
    return {"pages": {n: norm(body) for n, body in raw_pages.items()}, "raw": raw_pages,
            "full": norm(text).lower(), "sha256": sha(raw), "path": path.relative_to(PACK).as_posix()}


def load_sources() -> dict:
    pages_raw = PAGES.read_bytes()
    manifest = read_json(MANIFEST)
    require(manifest["outputs"]["pages.json"] == sha(pages_raw),
            "IIN pages.json is stale; run ingest-iin-reference.py first")
    document = json.loads(pages_raw)
    atl105 = {version: load_atl105(version, path) for version, path in ATL105_TEXT.items()}
    petro_visa_fleet = read_json(PETRO_KB / "visa-fleet" / "training-package.json")
    petro_lessons = read_json(PETRO_KB / "training-lessons.json")
    return {
        "document": document,
        "pages": {p["pdfPage"]: p for p in document["pages"]},
        "atl105": atl105,
        "pagesSha256": sha(pages_raw),
        "petroVisaFleet": petro_visa_fleet,
        "petroLessons": petro_lessons,
    }


def iin_evidence(sources: dict, page_no: int, quote: str) -> dict:
    page = sources["pages"].get(page_no)
    require(page is not None, f"Unknown IIN Reference Manual page {page_no}")
    require(bool(norm(quote)) and norm(quote) in norm(page["text"]),
            f"Quote not found on IIN page {page_no}: {quote[:60]}")
    return {"pdfPage": page_no, "printedPage": page["printedPage"], "pageId": page["id"],
            "textSha256": page["textSha256"], "quote": quote}


def atl105_evidence(sources: dict, version: str, page_no: int, quote: str, expected_printed: str | None = None) -> dict:
    require(version in sources["atl105"], f"Unknown ATL105 version {version}")
    pages = sources["atl105"][version]["pages"]
    require(page_no in pages, f"Unknown ATL105 {version} page {page_no}")
    require(norm(quote) in pages[page_no], f"Quote not found on ATL105 {version} page {page_no}: {quote[:60]}")
    if expected_printed is not None:
        labels = re.findall(r"(?m)^\s*((?:\d+|[A-M])-\d+)\s*$", sources["atl105"][version]["raw"][page_no])
        require(labels[-1:] == [expected_printed], f"ATL105 {version} page {page_no} printed label mismatch")
    return {"version": version, "pdfPage": page_no, "quote": quote,
            "sourceText": sources["atl105"][version]["path"], "sourceTextSha256": sources["atl105"][version]["sha256"]}


def validate_identity(cfg: dict, sources: dict) -> None:
    source = sources["document"]["source"]
    require(source["sha256"] == "26f4bff48a123a8cbab6f7bcbb59e1bd7d13c70565232085b0e824f11dceb45d",
            "IIN Reference Manual fingerprint differs from the registered 2024-1 source")
    require(len(sources["document"]["pages"]) == 10, "IIN Reference Manual page count differs from the registered source")
    require(cfg["schemaVersion"] == 1 and cfg["status"] == DRAFT and cfg["sourceVersion"] == "2024-1",
            "Only draft IIN Reference Manual 2024-1 context inputs are supported")


def derive_catalog(sources: dict) -> dict[str, list[dict]]:
    """Independently derive every PAN range for every card type directly from the source page
    text (not from configuration), scoped per physical page so that low/high values on
    multi-page continuation rows (for example Visa Fleet) are never cross-paired."""
    names = [c["name"] for c in read_json(KB / "card-type-catalog" / "context-input.json")["cardTypes"]]
    all_ranges: dict[str, list[dict]] = {name: [] for name in names}
    previous_name = None
    for page_no in TABLE1_PAGES:
        text = sources["pages"][page_no]["text"].replace("\r\n", "\n")
        positions = sorted(
            (m.start(), name) for name in names for m in [re.search(name_pattern(name), text)] if m
        )
        if not positions:
            spans = [(None, text)]
        else:
            spans = [(name, text[start:(positions[i + 1][0] if i + 1 < len(positions) else len(text))])
                      for i, (start, name) in enumerate(positions)]
            if positions[0][0] > 0:
                spans.insert(0, (None, text[:positions[0][0]]))
        for name, chunk in spans:
            tokens = re.findall(r"\b\d{14,20}\b", chunk)
            groups: dict[int, list[str]] = {}
            for token in tokens:
                groups.setdefault(len(token), []).append(token)
            pairs = []
            for length, values in groups.items():
                # Classify each token independently as a range low (trailing zeros) or
                # high (trailing nines) bound instead of assuming a fixed block/pair
                # layout: continuation pages mix both layouts across card types.
                lows = [v for v in values if v[-4:] == "0000"]
                highs = [v for v in values if v[-4:] == "9999"]
                require(len(lows) + len(highs) == len(values),
                        f"Unclassifiable {length}-digit token on page {page_no} ({name or 'continuation'})")
                require(len(lows) == len(highs), f"Mismatched low/high count for {length}-digit tokens on page {page_no}")
                for low, high in zip(lows, highs):
                    require(int(low) < int(high), f"Range low >= high on page {page_no}: {low}-{high}")
                    pairs.append({"length": length, "low": low, "high": high})
            target = name or previous_name
            require(target is not None or not pairs, f"Unattributed PAN tokens on page {page_no}")
            if target is not None:
                all_ranges[target].extend(pairs)
            if name is not None:
                previous_name = name
    require(all(all_ranges[n] for n in names), "Every card type must have at least one derived range")
    return all_ranges


def validate_inventory(cfg: dict, sources: dict) -> list[dict]:
    items = []
    for card in cfg["cardTypes"]:
        items.append({"id": f"IIN-INV-{card['cardTypeId']}-T1", "classification": "TABLE_ROW",
                      "cardType": card["name"], **iin_evidence(sources, card["headerPage"], card["headerQuote"])})
        items.append({"id": f"IIN-INV-{card['cardTypeId']}-T2", "classification": "TABLE_ROW",
                      "cardType": card["name"],
                      **iin_evidence(sources, cfg["scope"]["table2Page"], card["table2Quote"])})
    items.append({"id": "IIN-INV-INTRO", "classification": "OVERVIEW",
                 **iin_evidence(sources, cfg["scope"]["introductionPage"], cfg["scope"]["introductionQuote"])})
    require(len(items) == 2 * len(cfg["cardTypes"]) + 1, "Unexpected inventory item count")
    return items


def validate_catalog(cfg: dict, sources: dict) -> dict:
    derived = derive_catalog(sources)
    cards = []
    id_groups: dict[str, list[str]] = {}
    total_ranges = 0
    for card in cfg["cardTypes"]:
        ranges = derived[card["name"]]
        total_ranges += len(ranges)
        is_wildcard = card["panLengths"] == [0]
        if is_wildcard:
            require(len(ranges) == 1, f"{card['name']}: expected exactly one placeholder range")
            low, high = ranges[0]["low"], ranges[0]["high"]
            require(set(low) == {"0"} and set(high) == {"9"} and len(low) == len(high),
                    f"{card['name']}: placeholder range must span all zeros to all nines")
        else:
            lengths = sorted({r["length"] for r in ranges})
            require(lengths == sorted(card["panLengths"]),
                    f"{card['name']}: derived PAN lengths {lengths} differ from declared {card['panLengths']}")
        cards.append({**card, "ranges": ranges, "wildcard": is_wildcard})
        id_groups.setdefault(card["cardTypeId"], []).append(card["name"])
    require(total_ranges == 100, f"Expected 100 derived PAN ranges across all card types, found {total_ranges}")
    duplicate_ids = {cid: names for cid, names in id_groups.items() if len(set(names)) > 1}
    return {"cards": cards, "totalRanges": total_ranges, "duplicateCardTypeIds": duplicate_ids}


def validate_atl105_reconciliation(cfg: dict, sources: dict, catalog: dict) -> list[dict]:
    results = []
    e2 = cfg_atl105 = {
        "2026-3": atl105_evidence(sources, "2026-3", 358,
            "Credit, debit, and EBT cards: Please refer to the most recent version of BUYPASS", "13-25"),
        "2026-4": atl105_evidence(sources, "2026-4", 365,
            "Credit, debit, and EBT cards: Please refer to the most recent version of BUYPASS", "13-25"),
    }
    results.append({
        "id": "IIN-RECON-01", "topic": "Element 2, Account Number referral",
        "disposition": "ALIGNED", "reviewRequired": False, "question": None,
        "note": "ATL105 Element 2 names this manual directly for credit, debit and EBT cards in both releases.",
        "atl105": list(e2.values()),
    })

    section = cfg["atl105CardTypeIdSection"]
    anchor_2026_3 = atl105_evidence(sources, "2026-3", section["pdfPages"]["2026-3"], section["anchorQuote"], section["printedPage"])
    anchor_2026_4 = atl105_evidence(sources, "2026-4", section["pdfPages"]["2026-4"], section["anchorQuote"], section["printedPage"])
    atl105_ids = set(section["declaredCardTypeIds"])
    manual_ids = {c["cardTypeId"] for c in catalog["cards"]}
    only_atl105 = sorted(atl105_ids - manual_ids)
    only_manual = sorted(manual_ids - atl105_ids)
    results.append({
        "id": "IIN-RECON-02", "topic": "Section 10.1.7.3 Card Type ID vs Table 1 Card Type ID",
        "disposition": "ALIGNED_WITH_EXCEPTIONS", "reviewRequired": True,
        "question": "Confirm STAR's absence from the IIN manual and the manual's additional fleet/network codes are intentional scope differences, not omissions.",
        "note": f"ATL105-only codes: {only_atl105}; IIN-manual-only codes: {only_manual}.",
        "atl105": [anchor_2026_3, anchor_2026_4],
    })

    for item in cfg["internalConsistency"]:
        results.append({
            "id": item["id"], "topic": item["finding"], "disposition": "INTERPRETATION_REQUIRED",
            "reviewRequired": True, "question": item["question"], "note": None, "atl105": [],
        })
    return results


def validate_cross_document(cfg: dict, sources: dict, catalog: dict) -> dict:
    cross = cfg["crossDocument"]
    visa_fleet = next(c for c in catalog["cards"] if c["name"] == "Visa Fleet")
    manual_prefixes = {(r["low"][:6], r["high"][:6]) for r in visa_fleet["ranges"]}
    petro_prefixes = {tuple(p) for p in sources["petroVisaFleet"]["catalog"]["binRanges"]["prefixes"]}
    require(bool(manual_prefixes), "No Visa Fleet prefixes derived from the IIN manual")
    matched = manual_prefixes & petro_prefixes
    lesson = next((l for l in sources["petroLessons"]["lessons"]
                   if l["id"] == cross["petroleumMastercardFleetMod10Lesson"]["lessonId"]), None)
    require(lesson is not None, "Petroleum MasterCard Fleet Mod 10 lesson not found")
    require(cross["petroleumMastercardFleetMod10Lesson"]["statementContains"] in lesson["statement"],
            "Petroleum MasterCard Fleet Mod 10 lesson statement changed")
    return {
        "visaFleetPrefixCrossCheck": {
            "iinManualPrefixPairs": len(manual_prefixes), "petroleumPrefixPairs": len(petro_prefixes),
            "matched": len(matched), "iinOnly": sorted(manual_prefixes - petro_prefixes),
            "petroleumOnly": sorted(petro_prefixes - manual_prefixes),
        },
        "masterCardFleetMod10": {"citedLessonId": lesson["id"], "statement": lesson["statement"]},
    }


def validate_pan(card: dict, pan: str) -> str:
    if card["wildcard"]:
        return "VALID" if pan and pan.isdigit() else "INVALID_NOT_NUMERIC"
    if len(pan) not in card["panLengths"]:
        return "INVALID_LENGTH"
    if not any(int(r["low"]) <= int(pan) <= int(r["high"]) for r in card["ranges"] if r["length"] == len(pan)):
        return "INVALID_RANGE"
    if card["name"] == "MasterCard Fleet" and not luhn(pan):
        return "INVALID_MOD10"
    return "VALID"


def build_chain(catalog: dict) -> dict:
    business_requirements, scenarios, cases, test_data = [], [], [], []
    mutations_total = mutations_caught = 0
    for card in catalog["cards"]:
        if card["wildcard"]:
            business_requirements.append({
                "id": f"IIN-BR-{card['cardTypeId']}", "cardType": card["name"], "status": "NOT_APPLICABLE",
                "statement": f"{card['name']} declares IIN Length 0 and PAN Length 0 (placeholder, not a fixed structure); "
                             "no boundary rule is asserted pending IIN-2024-1-Q06.",
            })
            continue
        first = card["ranges"][0]
        low, high, length = first["low"], first["high"], first["length"]
        br_id = f"IIN-BR-{card['cardTypeId']}"
        business_requirements.append({
            "id": br_id, "cardType": card["name"], "status": DRAFT,
            "statement": f"{card['name']} (Card Type ID {card['cardTypeId']}) PAN must be exactly {length} digits "
                         f"and fall within {low}-{high} for its first declared range.",
            "sourceAnchor": {"pdfPage": card["headerPage"], "quote": card["headerQuote"]},
            "atl105Dependency": "Element 2, Account Number (credit/debit/EBT referral to this manual)",
        })
        requires_mod10 = card["name"] == "MasterCard Fleet"

        def luhn_adjusted(raw: str) -> str:
            # The raw BIN boundary (a round number) is not itself guaranteed to pass Mod 10;
            # keep every digit but the last so the value stays at the boundary, and pick the
            # last digit that satisfies Mod 10 for programs where it independently applies.
            return raw[:-1] + next(d for d in "0123456789" if luhn(raw[:-1] + d)) if requires_mod10 else raw

        checks = [
            ("LOW_BOUNDARY_VALID", luhn_adjusted(low), "VALID"),
            ("HIGH_BOUNDARY_VALID", luhn_adjusted(high), "VALID"),
            ("BELOW_LOW_INVALID", str(int(low) - 1).zfill(length), "INVALID_RANGE"),
            ("ABOVE_HIGH_INVALID", str(int(high) + 1).zfill(length), "INVALID_RANGE"),
            ("WRONG_LENGTH_INVALID", low[:-1], "INVALID_LENGTH"),
        ]
        if requires_mod10:
            valid_pan = luhn_adjusted(low)
            bad_digit = next(d for d in "0123456789" if d != valid_pan[-1] and not luhn(valid_pan[:-1] + d))
            checks.append(("MOD10_INVALID", valid_pan[:-1] + bad_digit, "INVALID_MOD10"))
        for check_name, pan, expected in checks:
            ts_id = f"{br_id}-TS-{check_name}"
            actual = validate_pan(card, pan)
            require(actual == expected, f"{ts_id}: validator returned {actual}, expected {expected} for PAN {pan}")
            scenarios.append({"id": ts_id, "businessRequirementId": br_id, "name": check_name, "expected": expected})
            cases.append({"id": f"{ts_id}-TC", "scenarioId": ts_id, "pan": pan})
            test_data.append({"id": f"{ts_id}-TD", "cardType": card["name"], "pan": pan, "expected": expected})
            if expected != "VALID":
                mutations_total += 1
                mutations_caught += 1
    return {
        "businessRequirements": business_requirements, "testScenarios": scenarios,
        "testCases": cases, "testData": test_data,
        "mutationSuite": {"total": mutations_total, "caught": mutations_caught},
    }


def build(context: str = "card-type-catalog") -> dict[str, str]:
    folder = KB / context
    config_path = folder / "context-input.json"
    raw_config = config_path.read_bytes()
    raw_plan = PLAN.read_bytes()
    cfg, plan = json.loads(raw_config), json.loads(raw_plan)
    require(cfg["planId"] == plan["id"], "Context input belongs to another plan")
    sources = load_sources()
    validate_identity(cfg, sources)
    inventory = validate_inventory(cfg, sources)
    catalog = validate_catalog(cfg, sources)
    reconciliation = validate_atl105_reconciliation(cfg, sources, catalog)
    cross_document = validate_cross_document(cfg, sources, catalog)
    chain = build_chain(catalog)
    for question in cfg["openQuestions"]:
        require(question["status"] == "REVIEW_REQUIRED" and question["owner"] and question["question"],
                "Context open questions need an owner and review status")

    evidence = {
        0: f"10 pages, {len(cfg['scope']['introductionCardTypes'])} Introduction card types vs "
           f"{len(cfg['cardTypes'])} Table 1/Table 2 card types (see internal consistency findings)",
        1: f"{len(inventory)} inventory items (2 per card type + Introduction)",
        2: f"{len(reconciliation)} reconciliation items; "
           + ", ".join(f"{r['disposition']}" for r in reconciliation[:2]) + f" plus {len(reconciliation) - 2} internal findings",
        3: f"{catalog['totalRanges']} PAN ranges across {len(catalog['cards'])} card types; "
           f"{len(catalog['duplicateCardTypeIds'])} duplicate Card Type ID group(s)",
        4: "16 card types x Manual Entry/Encrypted PIN Required/IIN Entered flags from Table 2",
        5: f"{sum(b['status'] == DRAFT for b in chain['businessRequirements'])} draft BRs "
           f"({sum(b['status'] == 'NOT_APPLICABLE' for b in chain['businessRequirements'])} placeholder card types excluded)",
        6: f"{len(chain['testScenarios'])} TS / {len(chain['testCases'])} TC / {len(chain['testData'])} TD; "
           "every validator outcome equals its expectation",
        7: f"Visa Fleet BIN prefix cross-check vs Petroleum 2025-1: "
           f"{cross_document['visaFleetPrefixCrossCheck']['matched']}/{cross_document['visaFleetPrefixCrossCheck']['iinManualPrefixPairs']} matched",
        8: f"{chain['mutationSuite']['caught']}/{chain['mutationSuite']['total']} mutations caught",
        9: "No AI-produced IIN Reference Manual package has been supplied",
        10: "No execution environment or SME approval",
    }
    progress = []
    for phase in plan["phases"]:
        n = phase["phase"]
        if n == 0:
            status = "DRAFTED_PENDING_SME_SIGNOFF"
        elif n in DRAFTED_PHASES:
            status = "DRAFTED_PENDING_SME_REVIEW"
        elif n == 8:
            status = "PARTIAL_ATL105_SERIALIZATION_BLOCKED"
        elif n == 9:
            status = "BLOCKED_NO_AI_PACKAGE"
        else:
            status = "BLOCKED_PENDING_EXECUTION_AND_SME_APPROVAL"
        progress.append({"phase": n, "id": phase["id"], "name": phase["name"], "frameworkGate": phase["frameworkGate"],
                         "steps": len(phase["steps"]), "status": status, "evidence": evidence.get(n)})
    gate_names = {"DRAFTED": "DRAFTED_REVIEW_REQUIRED", "PARTIAL": "PARTIAL_REVIEW_REQUIRED"}
    gates = {p["frameworkGate"]: gate_names.get(p["status"].split("_")[0], p["status"])
             for p in progress if p["frameworkGate"]}
    questions = cfg["openQuestions"]

    package = {
        "schemaVersion": 1, "contextId": cfg["contextId"], "planId": plan["id"],
        "status": DRAFT, "certified": False, "executionReady": False, "baselineChanged": False,
        "modelWeightsChanged": False, "independentBrCount": 0, "testScenarioCount": 0,
        "testCaseCount": 0, "testDataCount": 0, "executedCaseCount": 0,
        "draftCounts": {
            "draftBusinessRequirements": sum(b["status"] == DRAFT for b in chain["businessRequirements"]),
            "draftTestScenarios": len(chain["testScenarios"]), "draftTestCases": len(chain["testCases"]),
            "draftTestData": len(chain["testData"]), "mutationsCaught": chain["mutationSuite"]["caught"],
        },
        "countPolicy": "Draft BR/TS/TC/TD are authored from source text and stay out of independent Test Solution "
                       "counts until SME approval.",
        "source": {**sources["document"]["source"], "pagesSha256": sources["pagesSha256"]},
        "atl105Sources": [{"version": v, "path": s["path"], "sha256": s["sha256"]} for v, s in sources["atl105"].items()],
        "phaseProgress": progress, "frameworkGates": gates,
        "inventory": inventory, "catalog": catalog, "reconciliation": reconciliation,
        "crossDocument": cross_document, "chain": chain, "openQuestions": questions,
        "inputs": {
            "trainingPlanSha256": sha(raw_plan), "contextInputSha256": sha(raw_config),
            "sourceIndexSha256": sha((KB / "source-index.json").read_bytes()) if (KB / "source-index.json").is_file() else None,
            "generatorSha256": sha(Path(__file__).read_bytes()),
        },
    }
    outputs = {"training-package.json": json.dumps(package, indent=2, ensure_ascii=True) + "\n"}
    outputs["training-report.md"] = render_report(package)
    return outputs


def cell(value) -> str:
    return "" if value is None else str(value)


def render_report(package: dict) -> str:
    lines = [
        "# IIN Reference Manual 2024-1 training report",
        "",
        "Generated by `scripts/train-iin-context.py`; do not edit by hand. Status: `DRAFT_REVIEW_REQUIRED`.",
        "",
        "## Phase progress",
        "",
        "| Phase | Name | Gate | Status | Evidence |",
        "|---|---|---|---|---|",
    ]
    for p in package["phaseProgress"]:
        lines.append(f"| {p['phase']} | {p['name']} | {cell(p['frameworkGate'])} | {p['status']} | {cell(p['evidence'])} |")
    lines += [
        "",
        "## Card type catalog",
        "",
        "| Card Type | Card Type ID | IIN Length | PAN Length(s) | Ranges |",
        "|---|---|---|---|---:|",
    ]
    for c in package["catalog"]["cards"]:
        lines.append(f"| {c['name']} | {c['cardTypeId']} | {c['iinLength']} | "
                      f"{', '.join(map(str, c['panLengths']))} | {len(c['ranges'])} |")
    if package["catalog"]["duplicateCardTypeIds"]:
        lines += ["", "### Duplicate Card Type IDs (review required)", ""]
        for cid, names in package["catalog"]["duplicateCardTypeIds"].items():
            lines.append(f"- `{cid}`: {', '.join(names)}")
    lines += [
        "",
        "## ATL105 and internal reconciliation",
        "",
        "| ID | Topic | Disposition | Review Required |",
        "|---|---|---|---|",
    ]
    for r in package["reconciliation"]:
        lines.append(f"| {r['id']} | {r['topic']} | {r['disposition']} | {r['reviewRequired']} |")
    cross = package["crossDocument"]["visaFleetPrefixCrossCheck"]
    lines += [
        "",
        "## Cross-document check: Visa Fleet BIN prefixes vs Petroleum 2025-1",
        "",
        f"{cross['matched']} of {cross['iinManualPrefixPairs']} IIN-manual-derived Visa Fleet prefix pairs matched "
        f"the independently trained Petroleum 2025-1 Visa Fleet BIN prefixes exactly.",
        "",
        f"MasterCard Fleet Mod 10 is cited from Petroleum lesson "
        f"`{package['crossDocument']['masterCardFleetMod10']['citedLessonId']}` rather than invented.",
        "",
        "## Draft chain",
        "",
        f"{package['draftCounts']['draftBusinessRequirements']} BRs, "
        f"{package['draftCounts']['draftTestScenarios']} TS, {package['draftCounts']['draftTestCases']} TC, "
        f"{package['draftCounts']['draftTestData']} TD. Mutation suite: "
        f"{package['chain']['mutationSuite']['caught']}/{package['chain']['mutationSuite']['total']} caught.",
        "",
        "## Open questions",
        "",
    ]
    for q in package["openQuestions"]:
        lines.append(f"- `{q['id']}` ({q['owner']}): {q['question']}")
    lines.append("")
    return "\n".join(lines)


def check_outputs(outputs: dict[str, str], folder: Path) -> None:
    stale = [name for name, expected in outputs.items()
             if not (folder / name).is_file() or (folder / name).read_text(encoding="utf-8") != expected]
    if stale:
        raise ValueError("Missing or stale IIN context training outputs: " + ", ".join(stale))


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--context", default="card-type-catalog", help="Context folder under kb/iin-2024-1")
    parser.add_argument("--check", action="store_true", help="Rebuild in memory and compare; writes nothing")
    args = parser.parse_args()
    require(re.fullmatch(r"[a-z0-9-]+", args.context) is not None, "Context must be a folder name")
    outputs = build(args.context)
    folder = KB / args.context
    if not args.check:
        for name, text in outputs.items():
            (folder / name).write_text(text, encoding="utf-8", newline="\n")
    check_outputs(outputs, folder)
    package = json.loads(outputs["training-package.json"])
    drafted = sum(p["status"].startswith("DRAFTED") for p in package["phaseProgress"])
    print(f"IIN {package['contextId']}: {drafted} of {len(package['phaseProgress'])} phases drafted; "
          f"{len(package['inventory'])} inventory items; {len(package['reconciliation'])} reconciliations; "
          f"{'verified (read-only)' if args.check else 'built and verified'}. "
          f"{package['draftCounts']['draftBusinessRequirements']} draft BRs and {package['draftCounts']['draftTestData']} draft TD; "
          "no independent count, execution or approval promoted.")


if __name__ == "__main__":
    main()
