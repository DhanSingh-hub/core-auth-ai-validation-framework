"""Run Petroleum 2025-1 training phases for one card context, verifying every claim against source text."""

import argparse
import hashlib
import json
import re
import sys
from pathlib import Path
from types import SimpleNamespace

sys.path.insert(0, str(Path(__file__).resolve().parent))
import petroleum_chain  # noqa: E402
import petroleum_visa_fleet  # noqa: E402
import petroleum_voyager  # noqa: E402

# Card programs other than the MasterCard pilot supply their own catalog checks and chain plug-in.
PROGRAMS = {"MASTERCARD_FLEET": None, "VISA_FLEET": petroleum_visa_fleet, "VOYAGER_FLEET": petroleum_voyager}


MODULE = Path(__file__).resolve().parents[1]
PACK = MODULE / "specifications" / "ATL105"
SPECS = PACK / "docs" / "specs"
KB = SPECS / "kb" / "petroleum-2025-1"
PLAN = KB / "training-plan.json"
PAGES = KB / "pages.json"
INDEX = KB / "source-index.json"
MANIFEST = KB / "ingestion-manifest.json"
STATUS_FILE = PACK / "training-status.json"
ATL105_TEXT = {"2026-3": SPECS / "extracted_text.txt", "2026-4": SPECS / "extracted_text_new.txt"}
OUTPUT_NAMES = ("training-package.json", "training-report.md")
DRAFT = "DRAFT_REVIEW_REQUIRED"
CLASSIFICATIONS = {"OVERVIEW", "RULE", "TABLE", "CARD_LAYOUT", "EXAMPLE", "CROSS_REFERENCE", "NOTE"}
EVIDENCED = {"ALIGNED", "ALIGNED_WITH_EXCEPTIONS", "PETROLEUM_NARROWS_ATL105", "DISTINCT_PROGRAM_DO_NOT_ALIAS"}
DISPOSITIONS = EVIDENCED | {"PETROLEUM_ONLY", "INTERPRETATION_REQUIRED", "NO_COUNTERPART_IDENTIFIED", "POTENTIAL_CONFLICT",
                            "CONSISTENT_WITHIN_PETROLEUM"}
PRODUCT_ROW = re.compile(r"^\s*(\d{3})(?:\s*[-–]\s*(\d{3}))?\s+(\S.*?)\s*$")
DRAFTED_PHASES = (0, 1, 2, 3)
CHAIN_PHASES = (4, 5, 6, 7)
CHAIN_MODULE = Path(petroleum_chain.__file__)


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


def load_atl105(version: str, path: Path) -> tuple[dict[int, str], dict[int, str], str, str]:
    raw = path.read_bytes()
    text = raw.decode("utf-8")
    first = re.search(r"Specification Release Version:\s*(\S+)", text)
    require(first is not None and first.group(1) == version, f"ATL105 text {path.name} is not release {version}")
    parts = re.split(r"--- PAGE (\d+) ---", text)
    raw_pages = {int(n): body for n, body in zip(parts[1::2], parts[2::2])}
    require(bool(raw_pages), f"ATL105 text {path.name} has no page markers")
    return {n: norm(body) for n, body in raw_pages.items()}, raw_pages, norm(text).lower(), sha(raw)


def load_sources() -> dict:
    pages_raw = PAGES.read_bytes()
    manifest = read_json(MANIFEST)
    require(manifest["outputs"]["pages.json"] == sha(pages_raw),
            "Petroleum pages.json is stale; run petroleum-ingest-build first")
    document = json.loads(pages_raw)
    atl105 = {}
    for version, path in ATL105_TEXT.items():
        pages, raw_pages, full, digest = load_atl105(version, path)
        atl105[version] = {"pages": pages, "raw": raw_pages, "full": full, "sha256": digest,
                           "path": path.relative_to(PACK).as_posix()}
    return {
        "document": document,
        "pages": {p["pdfPage"]: p for p in document["pages"]},
        "outline": read_json(INDEX)["outline"],
        "atl105": atl105,
        "pagesSha256": sha(pages_raw),
        "indexSha256": sha(INDEX.read_bytes()),
        "requiredGates": read_json(STATUS_FILE)["requiredGates"],
    }


def top_level_starts(outline: list[dict]) -> dict[int, str]:
    return {e["pdfPage"]: norm(e["title"]) for e in outline
            if e["depth"] == 0 and re.match(r"^(\d+\.|Appendix [A-M]\.)\s", e["title"])}


def validate_plan(plan: dict, sources: dict) -> None:
    require(plan["schemaVersion"] == 1 and plan["sourceVersion"] == "2025-1", "Unsupported Petroleum training plan")
    phases = plan["phases"]
    require([p["phase"] for p in phases] == list(range(11)), "Plan must define phases 0 to 10 in order")
    require(len({p["id"] for p in phases}) == len(phases), "Duplicate phase IDs")
    for phase in phases:
        steps = phase["steps"]
        require(bool(steps) and all(s["action"].strip() for s in steps), f"{phase['id']} needs steps")
        require([s["id"] for s in steps] == [f"P{phase['phase']}.{i}" for i in range(1, len(steps) + 1)],
                f"{phase['id']} step IDs must be sequential")
        require(bool(phase["deliverables"]) and bool(phase["acceptanceCriteria"]),
                f"{phase['id']} needs deliverables and acceptance criteria")
    gates = [p["frameworkGate"] for p in phases if p["frameworkGate"]]
    require(sorted(gates) == sorted(sources["requiredGates"]) and len(set(gates)) == len(gates),
            "Plan phases must map every framework gate exactly once")
    starts = top_level_starts(sources["outline"])
    last = max(sources["pages"])
    covered = []
    for item in plan["rolloutOrder"]:
        for low, high in item["pdfPageRanges"]:
            require(low in starts and (high + 1 in starts or high == last),
                    f"Rollout range {low}-{high} does not follow chapter boundaries")
            covered.extend(range(low, high + 1))
    require(len(covered) == len(set(covered)), "Rollout contexts overlap")
    first_chapter = min(p for p, t in starts.items() if t.startswith("2."))
    require(set(covered) == set(range(first_chapter, last + 1)), "Rollout does not cover chapters 2 to 17 and appendices")
    require(plan["pilotContext"] in {i["context"] for i in plan["rolloutOrder"]}, "Unknown pilot context")


def petroleum_evidence(sources: dict, page_no: int, quote: str, scope: tuple[int, int] | None) -> dict:
    page = sources["pages"].get(page_no)
    require(page is not None, f"Unknown Petroleum page {page_no}")
    require(scope is None or scope[0] <= page_no <= scope[1], f"Page {page_no} is outside the context scope")
    require(bool(norm(quote)) and norm(quote) in norm(page["text"]), f"Quote not found on Petroleum page {page_no}: {quote[:60]}")
    return {"pdfPage": page_no, "printedPage": page["printedPage"], "pageId": page["id"],
            "textSha256": page["textSha256"], "quote": quote}


def atl105_evidence(sources: dict, item: dict) -> dict:
    version = item["version"]
    require(version in sources["atl105"], f"Unknown ATL105 version {version}")
    page = sources["atl105"][version]["pages"].get(item["extractedPage"])
    require(page is not None and norm(item["quote"]) in page,
            f"Quote not found on ATL105 {version} page {item['extractedPage']}: {item['quote'][:60]}")
    return {**item, "sourceText": sources["atl105"][version]["path"], "sourceTextSha256": sources["atl105"][version]["sha256"]}


def validate_scope(cfg: dict, sources: dict) -> tuple[dict, dict]:
    scope = cfg["scope"]
    low, high = scope["pdfPageRange"]
    starts = top_level_starts(sources["outline"])
    require(starts.get(low) == norm(f"{scope['chapter']}. {scope['title']}"), "Scope must start at its chapter bookmark")
    require(high + 1 in starts, "Scope must end at the chapter boundary")
    bounds = (low, high)
    appendices = {}
    for appendix in scope.get("appendixPages", []):
        a_low, a_high = appendix["pdfPages"]
        title = norm(appendix["bookmark"])
        require(starts.get(a_low) == title and title.startswith(f"Appendix {appendix['appendix']}."),
                f"Appendix {appendix['appendix']} range must start at its document-local bookmark")
        following = min(p for p in list(starts) + [max(sources["pages"]) + 1] if p > a_low)
        require(a_low <= a_high < following, f"Appendix {appendix['appendix']} range crosses its boundary")
        appendices[title] = (a_low, a_high)
    tx = scope["supportedTransactions"]
    tx_evidence = petroleum_evidence(sources, tx["pdfPage"], tx["quote"], bounds)
    for value in tx["values"]:
        petroleum_evidence(sources, tx["pdfPage"], value, bounds)
    modes = [{"mode": m["mode"], **petroleum_evidence(sources, m["pdfPage"], m["quote"], bounds)} for m in scope["entryModes"]]
    require(bool(scope["exclusions"]), "Scope must record exclusions")
    allowed = {"chapter": bounds, "appendices": appendices}
    return allowed, {**scope, "supportedTransactions": {**tx_evidence, "values": tx["values"]}, "entryModes": modes}


def validate_inventory(cfg: dict, sources: dict, allowed: dict) -> tuple[list[dict], dict]:
    bounds = allowed["chapter"]
    expected = {norm(e["title"]) for e in sources["outline"] if bounds[0] <= e["pdfPage"] <= bounds[1]}
    items, seen = [], set()
    for item in cfg["inventory"]:
        require(item["id"] and item["id"] not in seen, f"Empty or duplicate inventory ID {item['id']}")
        seen.add(item["id"])
        require(item["classification"] in CLASSIFICATIONS, f"Unknown classification {item['classification']}")
        bookmark = norm(item["bookmark"])
        require(bookmark in expected or bookmark in allowed["appendices"], f"Inventory bookmark is outside the scope: {item['bookmark']}")
        pages = bounds if bookmark in expected else allowed["appendices"][bookmark]
        items.append({**item, "bookmark": bookmark, **petroleum_evidence(sources, item["pdfPage"], item["quote"], pages)})
    covered = {i["bookmark"] for i in items}
    missing = sorted(expected - covered)
    require(not missing, "Uncovered scoped bookmarks: " + "; ".join(missing))
    unused = sorted(set(allowed["appendices"]) - covered)
    require(not unused, "Scoped appendices without inventory items: " + "; ".join(unused))
    return items, {"expected": len(expected), "covered": len(expected & covered), "bookmarks": sorted(expected),
                   "appendices": sorted(allowed["appendices"])}


def validate_reconciliation(cfg: dict, sources: dict, inventory: list[dict]) -> list[dict]:
    by_id = {i["id"]: i for i in inventory}
    referenced, seen, results = set(), set(), []
    for item in cfg["reconciliation"]:
        require(item["id"] and item["id"] not in seen, f"Empty or duplicate reconciliation ID {item['id']}")
        seen.add(item["id"])
        require(item["disposition"] in DISPOSITIONS, f"Unknown disposition {item['disposition']}")
        refs = item["inventoryRefs"]
        require(bool(refs) and all(r in by_id for r in refs), f"{item['id']} has unknown inventory references")
        referenced.update(refs)
        if item["disposition"] != "ALIGNED" or item["reviewRequired"]:
            require(item["reviewRequired"] is True and bool(item.get("question", "").strip()),
                    f"{item['id']} needs reviewRequired and an owned question")
        evidence = [atl105_evidence(sources, e) for e in item.get("atl105", [])]
        if item["disposition"] in EVIDENCED:
            require({e["version"] for e in evidence} == set(ATL105_TEXT),
                    f"{item['id']} needs evidence from both ATL105 releases")
        if item["disposition"] in {"POTENTIAL_CONFLICT", "CONSISTENT_WITHIN_PETROLEUM"}:
            require(len(refs) >= 2 or bool(evidence), f"{item['id']} must cite both sides of the comparison")
        absent = []
        if item["disposition"] == "PETROLEUM_ONLY":
            require(bool(item.get("atl105Absent")), f"{item['id']} must list verified absence terms")
            for term in item["atl105Absent"]:
                for version, source in sources["atl105"].items():
                    require(norm(term).lower() not in source["full"], f"{item['id']}: '{term}' exists in ATL105 {version}")
                absent.append({"term": term, "versions": sorted(ATL105_TEXT), "result": "NOT_FOUND"})
        results.append({
            "id": item["id"], "topic": item["topic"], "disposition": item["disposition"],
            "reviewRequired": item["reviewRequired"], "note": item.get("note"), "question": item.get("question"),
            "petroleum": [{"inventoryId": r, **{k: by_id[r][k] for k in ("pdfPage", "printedPage", "pageId", "textSha256", "quote")}} for r in refs],
            "atl105": evidence, "atl105Absent": absent,
        })
    rules = {i["id"] for i in inventory if i["classification"] == "RULE"}
    require(not rules - referenced, "Unreconciled rule items: " + ", ".join(sorted(rules - referenced)))
    return results


def page_text(sources: dict, page_no: int) -> str:
    return norm(sources["pages"][page_no]["text"])


def validate_bins(catalog: dict, sources: dict) -> dict:
    bins = catalog["binRanges"]
    prefixes = [tuple(p) for p in bins["prefixes"]]
    accounts = re.findall(r"(\d{16})\s*[–-]\s*(\d{16})", page_text(sources, bins["accountRangesPage"]))
    require(bool(accounts), "No 16-digit BIN account ranges found")
    for low, high in accounts:
        require(low.endswith("0" * 10) and high.endswith("9" * 10), f"Account range {low}-{high} is not a BIN prefix range")
    require([(l[:6], h[:6]) for l, h in accounts] == prefixes, "Account ranges disagree with BIN prefixes")
    for page_no in bins["prefixPages"]:
        found = re.findall(r"\b(\d{6})\s*[–-]\s*(\d{6})\b", page_text(sources, page_no))
        require(found == prefixes, f"BIN prefixes on page {page_no} disagree")
    return {"prefixes": [list(p) for p in prefixes], "accountRanges": [list(a) for a in accounts],
            "consistentPages": [bins["accountRangesPage"], *bins["prefixPages"]]}


def validate_layout(name: str, layout: dict, sources: dict) -> dict:
    fields = layout["fields"]
    require([f["no"] for f in fields] == list(range(1, len(fields) + 1)), f"{name} field numbers must be sequential")
    position = 0
    for field in fields:
        require(field["pos"] == position and field["len"] > 0, f"{name} field {field['no']} is not contiguous")
        position += field["len"]
        if layout["rowFormat"] == "NO_LABEL_POS_LEN":
            row = f"{field['no']} {field['label']} {field['pos']} {field['len']}"
        else:
            require(layout["rowFormat"] == "NO_POS_LEN_LABEL", f"Unknown row format for {name}")
            row = f"{field['no']} {field['pos']} {field['len']} {field['label']}"
        require(row in page_text(sources, field["pdfPage"]), f"{name} row not found on page {field['pdfPage']}: {row}")
        field["rowEvidence"] = row
    return {"rowFormat": layout["rowFormat"], "totalLength": position, "fields": fields}


def check_example(name: str, layout: dict, example: dict, bins: list[tuple], enums: dict, sources: dict) -> dict:
    evidence = petroleum_evidence(sources, example["pdfPage"], example["quote"], None)
    compact = "".join(example["quote"].split())
    fields = layout["fields"]
    checks = []

    def add(check: str, passed: bool, detail: str) -> None:
        checks.append({"check": check, "status": "PASS" if passed else "REVIEW_REQUIRED", "detail": detail})

    printed = fields[:-1] if fields[-1]["name"] == "LRC" else fields
    if printed is not fields:
        checks.append({"check": "LRC", "status": "NOT_PRINTED", "detail": "The printed example omits the LRC byte."})
    variable = next((i for i, f in enumerate(printed) if f.get("variable")), None)
    by_no = {}

    def slice_fixed(segment: list[dict], text: str) -> None:
        cursor = 0
        for field in segment:
            by_no[field["no"]] = text[cursor:cursor + field["len"]]
            cursor += field["len"]

    if variable is None:
        expected = sum(f["len"] for f in printed)
        aligned = len(compact) == expected
        add("fixedWidthLength", aligned, f"expected {expected}, found {len(compact)}")
        slice_fixed(printed, compact)
    else:
        head = sum(f["len"] for f in printed[:variable])
        slice_fixed(printed[:variable], compact)
        end = compact.find(printed[variable + 1]["fixed"], head)
        require(end >= 0, f"{name} example lacks the separator after the variable field")
        by_no[printed[variable]["no"]] = compact[head:end]
        add("variableFieldLength", end - head <= printed[variable]["len"],
            f"{printed[variable]['name']} has {end - head} of at most {printed[variable]['len']} characters; "
            "fixed positions after it assume the maximum width")
        suffix, tail = printed[variable + 1:], compact[end:]
        expected = sum(f["len"] for f in suffix)
        aligned = len(tail) == expected
        add("suffixLength", aligned, f"after the variable field expected {expected} characters, found {len(tail)}")
        slice_fixed(suffix, tail)
    values = {}
    for field in printed:
        values.setdefault(field["name"], by_no[field["no"]])
        if "fixed" in field and (aligned or field["no"] <= (variable + 2 if variable is not None else 1)):
            add(f"fixed:{field['no']}:{field['name']}", by_no[field["no"]] == field["fixed"],
                f"expected {field['fixed']!r}, found {by_no[field['no']]!r}")
    account = values.get("BIN", "") + values.get("PAN", "")
    add("accountDigits", account.isdigit() and len(account) == 16, f"account {account}")
    add("binRange", any(low <= account[:6] <= high for low, high in bins), f"prefix {account[:6]}")
    add("mod10", account.isdigit() and luhn(account), "Mod 10 check digit on the full account number")
    if aligned:
        add("productRestrictionCode", values.get("PRODUCT_RESTRICTION_CODE") in enums["productRestrictionCode"],
            f"value {values.get('PRODUCT_RESTRICTION_CODE')!r}")
        add("productTypeCode", values.get("PRODUCT_TYPE_CODE") in enums["productTypeCode"],
            f"value {values.get('PRODUCT_TYPE_CODE')!r}")
    else:
        checks.append({"check": "productCodes", "status": "REVIEW_REQUIRED",
                       "detail": "Positions after the variable field are unreliable until the example length is reviewed."})
    status = "PASS" if all(c["status"] in {"PASS", "NOT_PRINTED"} for c in checks) else "REVIEW_REQUIRED"
    return {"layout": name, "status": status, "evidence": evidence, "compact": compact, "checks": checks}


def validate_catalog(cfg: dict, sources: dict) -> dict:
    catalog = cfg["catalog"]
    bins = validate_bins(catalog, sources)
    enums = catalog["enumerations"]
    for key in ("productRestrictionCode", "productTypeCode"):
        for code, description in enums[key].items():
            require(f"{code} = {description}" in page_text(sources, enums["pdfPage"]), f"Enumeration not found: {code} = {description}")
    lengths = []
    for item in catalog["promptDataLengths"]:
        require(f"{item['min']}–{item['max']}" in item["quote"], f"{item['field']} quote disagrees with its range")
        lengths.append({**item, **petroleum_evidence(sources, item["pdfPage"], item["quote"], None)})
    layouts, examples = {}, []
    prefixes = [tuple(p) for p in bins["prefixes"]]
    for name, layout in catalog["trackLayouts"].items():
        layouts[name] = validate_layout(name, json.loads(json.dumps(layout)), sources)
        examples.append(check_example(name, layouts[name], layout["example"], prefixes, enums, sources))
    emboss = catalog["embossing"]
    emboss_evidence = [petroleum_evidence(sources, emboss["pdfPage"], q, None) for q in emboss["quotes"]]
    return {"binRanges": bins, "enumerations": enums, "promptDataLengths": lengths, "trackLayouts": layouts,
            "exampleChecks": examples, "embossing": {"note": emboss["note"], "evidence": emboss_evidence},
            "productCodes": validate_product_codes(catalog["productCodes"], sources) if "productCodes" in catalog else None}


def parse_product_rows(pages: list[tuple[int, str]]) -> dict[str, dict]:
    rows = {}
    for page_no, text in pages:
        for line in text.splitlines():
            m = PRODUCT_ROW.match(line)
            if m:
                key = m[1] if m[2] is None else f"{m[1]}-{m[2]}"
                rows.setdefault(key, {"code": key, "low": int(m[1]), "high": int(m[2] or m[1]),
                                      "description": norm(m[3]), "page": page_no})
    return rows


def validate_product_codes(cfg: dict, sources: dict) -> dict:
    low, high = cfg["pdfPages"]
    categories = []
    for m in re.finditer(r"• ([^•]+?) \(Code Nos?\. (\d{3})(?:–(\d{3}))?\)", page_text(sources, cfg["categoryPage"])):
        categories.append({"name": m[1].strip(), "low": int(m[2]), "high": int(m[3] or m[2]),
                           "fuel": m[1].strip() in cfg["fuelCategories"]})
    require(categories and categories[0]["low"] == 0 and categories[-1]["high"] == 999, "Product categories must span 000-999")
    for before, after in zip(categories, categories[1:]):
        require(after["low"] == before["high"] + 1, f"Product categories are not contiguous at {after['name']}")
    require({c["name"] for c in categories if c["fuel"]} == set(cfg["fuelCategories"]), "Unknown fuel category names")
    rows = parse_product_rows([(n, sources["pages"][n]["text"]) for n in range(low, high + 1)])
    require(len(rows) >= cfg["minimumRows"], "Too few Appendix B product rows were extracted")
    covered = []
    for row in rows.values():
        category = next((c for c in categories if c["low"] <= row["low"] and row["high"] <= c["high"]), None)
        require(category is not None, f"Product row {row['code']} lies outside one category")
        row["category"], row["fuel"] = category["name"], category["fuel"]
        covered.extend(range(row["low"], row["high"] + 1))
    require(len(covered) == len(set(covered)), "Product rows overlap")
    comparison = {}
    for version, spec in cfg["atl105"].items():
        a_low, a_high = spec["pages"]
        raw = sources["atl105"][version]["raw"]
        require(f"Appendix {spec['appendix']}. Valid Payment Systems Product Codes" in norm(raw[a_low]),
                f"ATL105 {version} page {a_low} is not Appendix {spec['appendix']}")
        other = parse_product_rows([(n, raw[n]) for n in range(a_low, a_high + 1)])
        only_petroleum, only_atl105 = sorted(set(rows) - set(other)), sorted(set(other) - set(rows))
        differences = [{"code": k, "petroleum": rows[k]["description"], "atl105": other[k]["description"]}
                       for k in sorted(set(rows) & set(other)) if rows[k]["description"].lower() != other[k]["description"].lower()]
        require(only_petroleum == cfg["expectedDifferences"]["onlyPetroleum"] and only_atl105 == cfg["expectedDifferences"]["onlyAtl105"],
                f"ATL105 {version} product table differs from the declared exceptions: petroleum-only {only_petroleum}, atl105-only {only_atl105}")
        comparison[version] = {"appendix": spec["appendix"], "pages": spec["pages"], "rows": len(other),
                               "shared": len(set(rows) & set(other)), "onlyPetroleum": only_petroleum,
                               "onlyAtl105": only_atl105, "descriptionDifferences": differences}
    codes = sorted(f"{n:03}" for n in covered)
    fuel = sorted(f"{n:03}" for r in rows.values() if r["fuel"] for n in range(r["low"], r["high"] + 1))
    return {"appendix": "B", "pdfPages": cfg["pdfPages"], "categories": categories, "rows": sorted(rows.values(), key=lambda r: r["low"]),
            "rowCount": len(rows), "definedCodes": codes, "fuelCodes": fuel,
            "fuelInterpretation": cfg["fuelInterpretation"], "atl105Comparison": comparison}


def catalog_helpers() -> SimpleNamespace:
    return SimpleNamespace(require=require, norm=norm, luhn=luhn, page_text=page_text,
                           petroleum_evidence=petroleum_evidence, validate_product_codes=validate_product_codes)


def validate_approvals(cfg: dict) -> dict[int, dict]:
    approved = {}
    for approval in cfg["approvals"]:
        for key in ("phase", "approver", "decision", "date", "evidence"):
            require(bool(str(approval.get(key, "")).strip()), f"Approval lacks {key}")
        require(approval["decision"] in {"APPROVED", "REJECTED"}, "Approval decision must be APPROVED or REJECTED")
        approved[approval["phase"]] = approval
    return approved


def build(context: str = "mastercard-fleet") -> dict[str, str]:
    folder = KB / context
    config_path = folder / "context-input.json"
    raw_config = config_path.read_bytes()
    raw_plan = PLAN.read_bytes()
    cfg, plan = json.loads(raw_config), json.loads(raw_plan)
    require(cfg["schemaVersion"] == 1 and cfg["status"] == DRAFT and cfg["sourceVersion"] == "2025-1",
            "Only draft Petroleum 2025-1 context inputs are supported")
    require(cfg["planId"] == plan["id"], "Context input belongs to another plan")
    sources = load_sources()
    validate_plan(plan, sources)
    program_name = cfg.get("program", "MASTERCARD_FLEET")
    require(program_name in PROGRAMS, f"Unknown card program {program_name}")
    plugin = PROGRAMS[program_name]
    allowed, scope = validate_scope(cfg, sources)
    bounds = allowed["chapter"]
    inventory, coverage = validate_inventory(cfg, sources, allowed)
    reconciliation = validate_reconciliation(cfg, sources, inventory)
    catalog = plugin.validate_catalog(cfg, sources, catalog_helpers()) if plugin else validate_catalog(cfg, sources)
    chain_program = plugin.PROGRAM if plugin else petroleum_chain.MASTERCARD
    approvals = validate_approvals(cfg)
    for question in cfg["openQuestions"]:
        require(question["status"] == "REVIEW_REQUIRED" and question["owner"] and question["question"],
                "Context questions need an owner and review status")

    summary = {}
    for item in reconciliation:
        summary[item["disposition"]] = summary.get(item["disposition"], 0) + 1
    chain_path = folder / "chain-input.json"
    raw_chain = chain_path.read_bytes() if chain_path.is_file() else None
    chain = None
    if raw_chain is not None:
        chain = petroleum_chain.build_chain(
            json.loads(raw_chain),
            {"contextId": cfg["contextId"], "scope": scope, "inventory": inventory,
             "reconciliation": reconciliation, "catalog": catalog},
            sources, atl105_evidence, chain_program)
    example_review = sum(e["status"] != "PASS" for e in catalog["exampleChecks"])
    evidence = {
        0: f"{bounds[0]}-{bounds[1]} pages, {len(scope['supportedTransactions']['values'])} transactions, "
           f"{len(scope['entryModes'])} entry modes, {len(scope['exclusions'])} exclusions",
        1: f"{len(inventory)} items; bookmark coverage {coverage['covered']}/{coverage['expected']}",
        2: f"{len(reconciliation)} items; " + ", ".join(f"{k} {v}" for k, v in sorted(summary.items())),
        3: f"{len(catalog['trackLayouts'])} track layouts, {len(catalog['binRanges']['prefixes'])} BIN ranges, "
           f"{len(catalog['exampleChecks'])} examples ({example_review} review-required)",
    }
    if chain:
        cells = chain["contextMatrix"]["cells"]
        count = lambda status: sum(c["status"] == status for c in cells)  # noqa: E731
        evidence.update({
            4: f"{len(chain['contextMatrix']['transactions'])} transactions x 4 entry modes: {count('SUPPORTED')} supported, "
               f"{count('REVIEW_REQUIRED')} review-required, {count('NOT_APPLICABLE')} not applicable",
            5: f"{len(chain['businessRequirements'])} draft BRs; "
               f"{sum(r['reviewRequired'] for r in chain['businessRequirements'])} carry review items",
            6: f"{len(chain['testScenarios'])} TS / {len(chain['testCases'])} TC / {len(chain['testData'])} TD; "
               "every validator outcome equals its expectation",
            7: f"{len(chain['flows']['transitions'])} transitions, all covered by scenarios",
            8: f"{chain['mutationSuite']['caught']}/{chain['mutationSuite']['total']} mutations caught; "
               f"ATL105 serialization {chain['atl105Serialization']['status']}",
            9: "No AI-produced Petroleum package has been supplied",
            10: "No execution environment or SME approval",
        })
    progress = []
    for phase in plan["phases"]:
        n = phase["phase"]
        if n in approvals:
            status = "SME_" + approvals[n]["decision"]
        elif n == 0:
            status = "DRAFTED_PENDING_SME_SIGNOFF"
        elif n in DRAFTED_PHASES or (chain and n in CHAIN_PHASES):
            status = "DRAFTED_PENDING_SME_REVIEW"
        elif chain and n == 8:
            status = "PARTIAL_ATL105_SERIALIZATION_BLOCKED"
        elif chain and n == 9:
            status = "BLOCKED_NO_AI_PACKAGE"
        elif chain and n == 10:
            status = "BLOCKED_PENDING_EXECUTION_AND_SME_APPROVAL"
        else:
            status = "NOT_STARTED"
        progress.append({"phase": n, "id": phase["id"], "name": phase["name"], "frameworkGate": phase["frameworkGate"],
                         "steps": len(phase["steps"]), "status": status, "evidence": evidence.get(n)})
    gate_names = {"DRAFTED": "DRAFTED_REVIEW_REQUIRED", "PARTIAL": "PARTIAL_REVIEW_REQUIRED"}
    gates = {p["frameworkGate"]: gate_names.get(p["status"].split("_")[0], p["status"])
             for p in progress if p["frameworkGate"]}
    questions = cfg["openQuestions"] + [
        {"id": f"{r['id']}-Q", "owner": "BUYPASS SME", "status": "REVIEW_REQUIRED", "question": r["question"]}
        for r in reconciliation if r["reviewRequired"]
    ]
    if chain:
        questions += [{"id": f"{b['id']}-Q", "owner": "BUYPASS SME", "status": "REVIEW_REQUIRED",
                       "question": f"Confirm interpretation: {b['interpretation']}"}
                      for b in chain["businessRequirements"] if b["interpretation"]]
        questions += [{"id": f"{chain_program.prefix}-TX-{t['code']}-Q", "owner": "BUYPASS SME", "status": "REVIEW_REQUIRED",
                       "question": f"Confirm {t['petroleum']} maps to ATL105 transaction type {t['code']} ({t['basis']})."
                       + (f" {t['reviewReason']}" if t.get("reviewReason") else "")}
                      for t in chain["contextMatrix"]["transactions"] if t["reviewRequired"]]
    draft_counts = {
        "draftBusinessRequirements": len(chain["businessRequirements"]) if chain else 0,
        "draftTestScenarios": len(chain["testScenarios"]) if chain else 0,
        "draftTestCases": len(chain["testCases"]) if chain else 0,
        "draftTestData": len(chain["testData"]) if chain else 0,
        "mutationsCaught": chain["mutationSuite"]["caught"] if chain else 0,
    }
    package = {
        "schemaVersion": 1, "contextId": cfg["contextId"], "planId": plan["id"],
        "status": DRAFT, "certified": False, "executionReady": False, "baselineChanged": False,
        "modelWeightsChanged": False, "independentBrCount": 0, "testScenarioCount": 0,
        "testCaseCount": 0, "testDataCount": 0, "executedCaseCount": 0,
        "draftCounts": draft_counts,
        "countPolicy": "Draft chains are authored from source text and stay out of independent Test Solution counts until SME approval.",
        "source": {**sources["document"]["source"], "pagesSha256": sources["pagesSha256"]},
        "atl105Sources": [{"version": v, "path": s["path"], "sha256": s["sha256"]} for v, s in sources["atl105"].items()],
        "phaseProgress": progress, "frameworkGates": gates,
        "scope": scope, "inventory": inventory, "bookmarkCoverage": coverage,
        "reconciliation": reconciliation, "reconciliationSummary": dict(sorted(summary.items())),
        "catalog": catalog, "openQuestions": questions,
        "inputs": {
            "trainingPlanSha256": sha(raw_plan), "contextInputSha256": sha(raw_config),
            "chainInputSha256": sha(raw_chain) if raw_chain else None,
            "sourceIndexSha256": sources["indexSha256"], "generatorSha256": sha(Path(__file__).read_bytes()),
            "chainGeneratorSha256": sha(CHAIN_MODULE.read_bytes()),
            **({"programGeneratorSha256": sha(Path(plugin.__file__).read_bytes())} if plugin else {}),
        },
    }
    outputs = {"training-package.json": json.dumps(package, indent=2, ensure_ascii=True) + "\n"}
    if chain:
        outputs["chain-package.json"] = json.dumps(chain, indent=2, ensure_ascii=True) + "\n"
    outputs["training-report.md"] = render_report(package, plan, chain, plugin)
    return outputs


def cell(value) -> str:
    return str(value).replace("|", "\\|").replace("\n", " ")


def render_report(package: dict, plan: dict, chain: dict | None = None, plugin=None) -> str:
    counts = package["draftCounts"]
    lines = [
        f"# Petroleum 2025-1 training: {package['contextId']}",
        "",
        "Generated by `scripts/train-petroleum-context.py`; do not edit by hand.",
        "",
        f"Status: `{package['status']}`. Draft source-grounded training: {counts['draftBusinessRequirements']} BRs, "
        f"{counts['draftTestScenarios']} TS, {counts['draftTestCases']} TC and {counts['draftTestData']} TD are drafted "
        "from source text and are not independent Test Solution evidence until SME approval. Nothing has been executed "
        "on a host, no model weights changed and no SME approval is recorded.",
        "",
        "## Phase steps and pilot progress",
        "",
        "| Phase | Name | Framework gate | Steps | Pilot status | Evidence |",
        "|---|---|---|---|---|---|",
    ]
    steps = {p["phase"]: p["steps"] for p in plan["phases"]}
    for p in package["phaseProgress"]:
        actions = "<br>".join(f"{s['id']} {s['action']}" for s in steps[p["phase"]])
        lines.append(f"| {p['phase']} | {cell(p['name'])} | {p['frameworkGate'] or '-'} | {cell(actions)} | "
                     f"`{p['status']}` | {cell(p['evidence'] or '-')} |")
    lines += ["", "## Reconciliation with ATL105 2026-3 and 2026-4", "",
              "| ID | Topic | Disposition | Petroleum pages | ATL105 pages (2026-3 / 2026-4) | Review |",
              "|---|---|---|---|---|---|"]
    for r in package["reconciliation"]:
        pet = ", ".join(sorted({str(e["pdfPage"]) for e in r["petroleum"]}, key=int))
        pages = {v: sorted({e["extractedPage"] for e in r["atl105"] if e["version"] == v}) for v in ("2026-3", "2026-4")}
        atl = " / ".join(",".join(map(str, pages[v])) or "-" for v in pages)
        if r["atl105Absent"]:
            atl = "absent: " + ", ".join(a["term"] for a in r["atl105Absent"])
        lines.append(f"| {r['id']} | {cell(r['topic'])} | `{r['disposition']}` | {pet} | {cell(atl)} | "
                     f"{'yes' if r['reviewRequired'] else 'no'} |")
    lines += ["", "## Card layout and example checks", ""]
    for name, layout in package["catalog"]["trackLayouts"].items():
        length = f"total length {layout['totalLength']}" if isinstance(layout["totalLength"], int) else layout["totalLength"]
        lines.append(f"- {name}: {len(layout['fields'])} source-verified fields, {length}.")
    prefixes = package["catalog"]["binRanges"]["prefixes"]
    shown = "; ".join(f"{a}-{b}" for a, b in prefixes) if len(prefixes) <= 8 else \
        f"{len(prefixes)} ranges from {prefixes[0][0]} to {prefixes[-1][1]} (full list in training-package.json)"
    lines.append("- BIN prefixes: " + shown
                 + " (consistent on PDF pages " + ", ".join(map(str, package["catalog"]["binRanges"]["consistentPages"])) + ").")
    lines += ["", "| Example | Check | Status | Detail |", "|---|---|---|---|"]
    for example in package["catalog"]["exampleChecks"]:
        for c in example["checks"]:
            lines.append(f"| {example['layout']} | {c['check']} | `{c['status']}` | {cell(c['detail'])} |")
    if plugin:
        lines += plugin.render_catalog(package["catalog"], cell)
    codes = package["catalog"].get("productCodes")
    if codes:
        fuel = [c["name"] for c in codes["categories"] if c["fuel"]]
        lines += ["", "## Petroleum Appendix B product codes", "",
                  f"{codes['rowCount']} rows extracted from PDF pages {codes['pdfPages'][0]}-{codes['pdfPages'][1]} across "
                  f"{len(codes['categories'])} contiguous categories; {len(codes['definedCodes'])} defined codes, "
                  f"{len(codes['fuelCodes'])} in fuel categories ({', '.join(fuel)}). {codes['fuelInterpretation']}", "",
                  "| ATL105 release | Appendix | Rows | Shared | Petroleum-only | ATL105-only | Wording differences |",
                  "|---|---|---|---|---|---|---|"]
        for version, c in codes["atl105Comparison"].items():
            lines.append(f"| {version} | {c['appendix']} (p{c['pages'][0]}-{c['pages'][1]}) | {c['rows']} | {c['shared']} | "
                         f"{', '.join(c['onlyPetroleum']) or '-'} | {', '.join(c['onlyAtl105']) or '-'} | {len(c['descriptionDifferences'])} |")
    if chain:
        lines += render_chain(chain)
    lines += ["", "## Open questions", "", "| ID | Owner | Question |", "|---|---|---|"]
    for q in package["openQuestions"]:
        lines.append(f"| {q['id']} | {cell(q['owner'])} | {cell(q['question'])} |")
    return "\n".join(lines) + "\n"


def render_chain(chain: dict) -> list[str]:
    matrix = chain["contextMatrix"]
    modes = petroleum_chain.ENTRY_MODES
    lines = ["", "## Phase 4: context matrix", "",
             "| Petroleum transaction | ATL105 code | Fleet / fallback Prompt Code | Basis | " + " | ".join(modes) + " |",
             "|---|---|---|---|" + "---|" * len(modes)]
    for t in matrix["transactions"]:
        status = {c["entryMode"]: c["status"] for c in matrix["cells"] if c["transactionCode"] == t["code"]}
        lines.append(f"| {cell(t['petroleum'])} | {t['code']} | {t['promptCodes']['fleet']} / {t['promptCodes']['fallback']} | "
                     f"{t['basis']} | " + " | ".join(f"`{status[m]}`" for m in modes) + " |")
    lines.append("")
    selector = matrix.get("promptSelector", {"label": "Product Type Code", "prompts": matrix.get("productTypePrompts", {})})
    lines.append(f"Prompts by {selector['label']}: " + "; ".join(
        f"{code} = {', '.join(p) or 'none'}" for code, p in sorted(selector["prompts"].items()))
        + f". Manual entry prompts: {', '.join(matrix['manualEntryPrompts'])}.")
    by_br = {}
    for s in chain["testScenarios"]:
        for br in s["requirementIds"]:
            by_br.setdefault(br, []).append(s)
    lines += ["", "## Phase 5 and 6: draft BRs and chains", "",
              "| BR | Title | Source pages | Scenarios (positive / negative / boundary) | Review |", "|---|---|---|---|---|"]
    for b in chain["businessRequirements"]:
        scenarios = by_br.get(b["id"], [])
        mix = "/".join(str(sum(s["type"] == k for s in scenarios)) for k in ("POSITIVE", "NEGATIVE", "BOUNDARY"))
        pages = ", ".join(sorted({str(a["pdfPage"]) for a in b["sourceAnchors"]}, key=int))
        lines.append(f"| {b['id']} | {cell(b['title'])} | {pages} | {len(scenarios)} ({mix}) | {'yes' if b['reviewRequired'] else 'no'} |")
    lines += ["", "| Scenario | Type | Title | Expected validator outcome | Oracle |", "|---|---|---|---|---|"]
    expected = {c["scenarioIds"][0]: c["expectedOutcome"] for c in chain["testCases"]}
    for s in chain["testScenarios"]:
        outcome = expected[s["id"]]
        result = "ACCEPT" if outcome["validator"] == "ACCEPT" else "REJECT " + ", ".join(outcome["violatedRequirements"])
        oracle = f"conflict {outcome['oracleConflict']}" if outcome["oracleConflict"] else "draft"
        lines.append(f"| {s['id']} | {s['type']} | {cell(s['title'])} | {result} | {oracle} |")
    lines += ["", "Host outcomes are not defined by the source; every test case records `" + petroleum_chain.HOST_OUTCOME + "`.",
              "", "## Phase 7: card-entry flow", "", "| Transition | From | To | Condition | Covered by |", "|---|---|---|---|---|"]
    for t in chain["flows"]["transitions"]:
        lines.append(f"| {t['id']} | {t['from']} | {t['to']} | {cell(t['condition'])} | {', '.join(t['coveredBy'])} |")
    suite = chain["mutationSuite"]
    lines += ["", "## Phase 8: rule validator and mutations", "",
              f"{suite['caught']} of {suite['total']} mutations of positive fleet test data were caught with exactly the expected BR.",
              "", "| Operator | Mutations | Expected violation |", "|---|---|---|"]
    for name in suite["operators"]:
        hits = [m for m in suite["results"] if m["operator"] == name]
        lines.append(f"| {name} | {len(hits)} | {', '.join(hits[0]['expectedViolations'])} |")
    blocker = chain["atl105Serialization"]
    lines += ["", f"ATL105 message serialization: `{blocker['status']}` ({blocker['observedOn']}). {blocker['reason']}"]
    if blocker.get("referenceCandidate"):
        lines += ["", f"Reference-only candidate: [{blocker['referenceCandidate']}]({blocker['referenceCandidate']}). "
                  "It is synthetic structure evidence, not producer output, host execution or approval."]
    return lines


def check_outputs(outputs: dict[str, str], folder: Path) -> None:
    stale = [name for name, expected in outputs.items()
             if not (folder / name).is_file() or (folder / name).read_text(encoding="utf-8") != expected]
    if stale:
        raise ValueError("Missing or stale Petroleum context training outputs: " + ", ".join(stale))


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--context", default="mastercard-fleet", help="Context folder under kb/petroleum-2025-1")
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
    print(f"Petroleum {package['contextId']}: {drafted} of {len(package['phaseProgress'])} phases drafted; "
          f"{len(package['inventory'])} inventory items; {len(package['reconciliation'])} reconciliations; "
          f"{'verified (read-only)' if args.check else 'built and verified'}. "
          f"{package['draftCounts']['draftBusinessRequirements']} draft BRs and {package['draftCounts']['draftTestData']} draft TD; "
          "no independent count, execution or approval promoted.")


if __name__ == "__main__":
    main()
