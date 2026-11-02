"""Train the BUYPASS InComm Market Basket Data Format 2023-1 chapter by chapter.

Imports every page and nested bookmark, derives chapter page spans from the outline,
verifies curated lessons, dataset field rows and usage-matrix rows against page-local
text, locates the ATL105 2026-3/2026-4 references, and exposes a draft Market Basket
Data validator. Nothing is promoted beyond DRAFT_REVIEW_REQUIRED.
"""

import argparse
import hashlib
import importlib.metadata
import json
import re
from pathlib import Path

from pypdf import PdfReader


MODULE = Path(__file__).resolve().parents[1]
PACK = MODULE / "specifications" / "ATL105"
SPECS = PACK / "docs" / "specs"
KB = SPECS / "kb" / "incomm-market-basket-2023-1"
INPUT = KB / "training-input.json"
PLAN = KB / "training-plan.json"
BR_INPUT = KB / "business-requirements-input.json"
PDF = SPECS / "BUYPASS_Platform_InComm_Market_Basket_Data_Format_2023-1.pdf"
ATL105_TEXT = {"2026-3": SPECS / "extracted_text.txt", "2026-4": SPECS / "extracted_text_new.txt"}
OUTPUT_NAMES = ("pages.json", "source-index.json", "chapters.json", "training-lessons.json",
                "dataset-catalog.json", "atl105-incomm-cross-references.json", "business-requirements.json",
                "training-report.md", "ingestion-manifest.json")
STATUS = "DRAFT_REVIEW_REQUIRED"
QUESTION_STATUSES = {"REVIEW_REQUIRED", "QE_INTERPRETATION_PENDING_SME"}
TRANSACTION_TYPES = ("310", "910", "920", "200")
PUNCTUATION = str.maketrans({"\u2018": "'", "\u2019": "'", "\u201c": '"', "\u201d": '"',
                             "\u2013": "-", "\u2022": " ", "\u2026": "...", "\u00ae": ""})
HEADER_LINE = re.compile(r"^(BUYPASS.*Format|Specification Release Version:.*|Version:.*|"
                         r"August 18, 2023|\d+-\d+|[ivx]+)$")
FORMAT = re.compile(r"^(a|an|ans|n|b) (\d+)$")
VIOLATION, REVIEW = "VIOLATION", "REVIEW_REQUIRED"


def fingerprint(raw: bytes) -> str:
    return hashlib.sha256(raw).hexdigest()


def text_fingerprint(path: Path) -> str:
    # read_text normalises CRLF checkouts so fingerprints are stable across platforms.
    return fingerprint(path.read_text(encoding="utf-8").encode("utf-8"))


def json_text(value: dict) -> str:
    return json.dumps(value, indent=2, ensure_ascii=True) + "\n"


def normalized(text: str) -> str:
    return " ".join(text.translate(PUNCTUATION).split())


def words(name: str) -> str:
    return r"\s+".join(re.escape(w) for w in normalized(name).split(" "))


def require(condition: bool, message: str) -> None:
    if not condition:
        raise ValueError(message)


# ---------------------------------------------------------------- source extraction

def flatten_outline(reader: PdfReader, entries: list, level: int = 0) -> list[dict]:
    flat = []
    for entry in entries:
        if isinstance(entry, list):
            flat.extend(flatten_outline(reader, entry, level + 1))
            continue
        target = reader.get_destination_page_number(entry)
        require(target is not None and 0 <= target < len(reader.pages),
                f"Invalid PDF outline destination: {entry.title}")
        flat.append({"title": normalized(str(entry.title)), "pdfPage": target + 1, "level": level})
    return flat


def printed_page(text: str) -> str | None:
    for line in text.splitlines()[:8]:
        if re.fullmatch(r"\s*(\d+-\d+|[ivx]+)\s*", line):
            return line.strip()
    return None


def extract_source(path: Path, config: dict) -> tuple[list[dict], list[dict]]:
    raw = path.read_bytes()
    require(fingerprint(raw) == config["sourceSha256"],
            "InComm Market Basket PDF fingerprint differs from the registered 2023-1 source")
    reader = PdfReader(path, strict=True)
    require(not reader.is_encrypted, "Encrypted InComm Market Basket source is not supported")
    require(len(reader.pages) == config["pageCount"], "InComm Market Basket page count differs from the registered source")
    pages = []
    for number, page in enumerate(reader.pages, 1):
        text = (page.extract_text() or "").replace("\r\n", "\n").replace("\r", "\n")
        pages.append({
            "id": f"INCOMM-MBD-2023-1-P{number:04}", "pdfPage": number, "printedPage": printed_page(text),
            "text": text, "textSha256": fingerprint(text.encode("utf-8")),
            "extractionStatus": "TEXT_EXTRACTED" if text.strip() else "EMPTY_REVIEW_REQUIRED",
        })
    cover = normalized(pages[0]["text"])
    require(all(normalized(m) in cover for m in config["coverMarkers"]), "InComm Market Basket cover identity is not confirmed")
    outline = flatten_outline(reader, reader.outline)
    expected = [(normalized(e["title"]), e["pdfPage"], e["level"]) for e in config["expectedOutline"]]
    require([(o["title"], o["pdfPage"], o["level"]) for o in outline] == expected,
            "InComm Market Basket outline bookmarks differ from the registered source")
    require(fingerprint(path.read_bytes()) == config["sourceSha256"], "InComm Market Basket source changed during extraction")
    return pages, outline


# ---------------------------------------------------------------- chapters and lessons

def continues_onto(page_text: str, next_heading: str) -> bool:
    """True when the page holds body text (not just running headers) before the next chapter's heading."""
    target = normalized(next_heading)
    for line in page_text.splitlines():
        text = normalized(line)
        if not text:
            continue
        if text.startswith(target):
            return False
        if not HEADER_LINE.match(text):
            return True
    return True


def build_chapters(config: dict, pages: list[dict], outline: list[dict]) -> list[dict]:
    by_title = {o["title"]: o for o in outline}
    chapters = []
    ids = set()
    for index, chapter in enumerate(config["chapters"]):
        require(chapter["id"] not in ids, f"Duplicate chapter ID: {chapter['id']}")
        ids.add(chapter["id"])
        mark = by_title.get(normalized(chapter["bookmark"]))
        require(mark is not None, f"Chapter bookmark not found in outline: {chapter['bookmark']}")
        start = mark["pdfPage"]
        require(normalized(chapter["heading"]) in normalized(pages[start - 1]["text"]),
                f"Chapter heading does not resolve on its bookmark page: {chapter['id']}")
        if index + 1 < len(config["chapters"]):
            nxt = config["chapters"][index + 1]
            nxt_start = by_title[normalized(nxt["bookmark"])]["pdfPage"]
            end = nxt_start if nxt_start == start or continues_onto(pages[nxt_start - 1]["text"], nxt["heading"]) else nxt_start - 1
        else:
            end = len(pages)
        span = pages[start - 1:end]
        chapters.append({
            "id": chapter["id"], "title": mark["title"], "level": mark["level"],
            "pdfPages": [start, end],
            "printedPages": [p["printedPage"] for p in span if p["printedPage"]],
            "pageIds": [p["id"] for p in span],
            "summary": chapter["summary"],
            "status": STATUS,
        })
    covered = {n for c in chapters for n in range(c["pdfPages"][0], c["pdfPages"][1] + 1)}
    require(covered == set(range(2, len(pages) + 1)), "Chapters do not cover every content page (2..end)")
    return chapters


def resolve_quote(by_page: dict, pdf_page: int, quote: str, owner: str, source_sha: str) -> dict:
    page = by_page.get(pdf_page)
    text = normalized(quote)
    require(bool(page) and bool(text) and text in normalized(page["text"]),
            f"Unresolved page-local InComm Market Basket quote: {owner}")
    return {"pdfPage": pdf_page, "quote": quote, "pageId": page["id"], "printedPage": page["printedPage"],
            "textSha256": page["textSha256"], "sourceSha256": source_sha,
            "scope": "QUOTE_LOCATION_AND_BYTES_NOT_SEMANTIC_APPROVAL"}


def build_lessons(config: dict, pages: list[dict], chapter_ids: set[str]) -> list[dict]:
    require(config["schemaVersion"] == 1 and config["status"] == STATUS and config["version"] == "2023-1",
            "Only draft InComm Market Basket 2023-1 lessons may be imported")
    for q in config["openQuestions"]:
        require(q["status"] in QUESTION_STATUSES and bool(q["owner"]) and bool(q["question"]),
                "InComm Market Basket open questions require an owner and a review disposition")
        require(q["chapter"] in chapter_ids, f"Open question cites unknown chapter: {q['id']}")
        require(q["status"] != "QE_INTERPRETATION_PENDING_SME" or bool(q.get("qeDecision")),
                f"QE interpretation lacks its recorded decision: {q['id']}")
    by_page = {p["pdfPage"]: p for p in pages}
    seen, lessons = set(), []
    for lesson in config["lessons"]:
        require(bool(lesson["id"]) and lesson["id"] not in seen, "Empty or duplicate InComm Market Basket lesson ID")
        seen.add(lesson["id"])
        require(lesson.get("chapter") in chapter_ids, f"Lesson cites unknown chapter: {lesson['id']}")
        require(bool(lesson["statement"].strip()) and bool(lesson["scope"].strip()) and bool(lesson["evidence"]),
                f"Lesson lacks a statement, scope or evidence: {lesson['id']}")
        evidence = [resolve_quote(by_page, c["pdfPage"], c["quote"], lesson["id"], config["sourceSha256"])
                    for c in lesson["evidence"]]
        lessons.append({**lesson, "status": STATUS, "evidence": evidence})
    require(bool(lessons), "No InComm Market Basket draft lessons were supplied")
    return lessons


# ---------------------------------------------------------------- dataset catalog

def span_pages(pages: list[dict], span: list[int]) -> list[dict]:
    return pages[span[0] - 1:span[1]]


def locate(pattern: str, pages: list[dict], owner: str) -> list[int]:
    regex = re.compile(pattern)
    for page in pages:
        if regex.search(normalized(page["text"])):
            return [page["pdfPage"]]
    joined = " ".join(normalized(p["text"]) for p in pages)
    require(bool(regex.search(joined)), f"Source row does not resolve in its chapter pages: {owner}")
    return [pages[0]["pdfPage"], pages[-1]["pdfPage"]]


def build_catalog(config: dict, pages: list[dict]) -> dict:
    datasets = {}
    for tag, spec in config["datasets"].items():
        require(re.fullmatch(r"[A-Z]{2}", tag) is not None, f"Invalid dataset tag: {tag}")
        span = span_pages(pages, spec["pdfPages"])
        fields, bits = [], set()
        for field in spec["fields"]:
            match = FORMAT.match(field["format"])
            require(match is not None, f"Unsupported field format {tag} bit {field['bit']}: {field['format']}")
            require(field["bit"] not in bits, f"Duplicate bit {tag} {field['bit']}")
            bits.add(field["bit"])
            owner = f"{tag} bit {field['bit']} {field['name']}"
            row = rf"\b{field['bit']}\s+(?:[ROC]\s+)?{words(field['name'])}\s+{words(field['format'])}\b"
            entry = {**field, "type": match.group(1), "length": int(match.group(2)),
                     "evidencePdfPages": locate(row, span, owner)}
            require(bool(field.get("descriptionQuote")), f"Field lacks a description quote: {owner}")
            entry["descriptionPdfPages"] = [p["pdfPage"] for p in span if normalized(field["descriptionQuote"]) in normalized(p["text"])]
            require(bool(entry["descriptionPdfPages"]), f"Field description quote does not resolve: {owner}")
            for value, quote in field.get("values", {}).items():
                require(any(normalized(quote) in normalized(p["text"]) for p in span), f"Enumeration value unresolved: {owner} {value}")
            for quote in field.get("positionEvidence", []):
                require(any(normalized(quote) in normalized(p["text"]) for p in span), f"Position evidence unresolved: {owner}")
            fields.append(entry)
        require(sorted(bits) == list(range(1, len(bits) + 1)), f"Dataset {tag} bits are not contiguous from 1")
        if spec["reservedBits"]:
            low, high = spec["reservedBits"]
            require(low == len(bits) + 1 and high == 32, f"Dataset {tag} reserved range does not follow the last bit")
            require(bool(re.search(rf"\b{low}-{high}\s+Reserved", " ".join(normalized(p['text']) for p in span))),
                    f"Dataset {tag} reserved range unresolved")
        datasets[tag] = {"tag": tag, "name": spec["name"], "chapter": spec["chapter"], "pdfPages": spec["pdfPages"],
                         "reservedBits": spec["reservedBits"], "fields": fields}
    matrices = {}
    for tag, matrix in config["usageMatrices"].items():
        require(tag in datasets, f"Usage matrix for unknown dataset: {tag}")
        span = span_pages(pages, matrix["pdfPages"])
        width = len(matrix["columns"])
        names = {f["bit"]: f["name"] for f in datasets[tag]["fields"]}
        rows = []
        for row in matrix["rows"]:
            require(len(row["values"]) == width, f"Usage row width mismatch {tag} bit {row['bit']}")
            require(names.get(row["bit"]) == row["name"], f"Usage row name differs from dataset field: {tag} bit {row['bit']}")
            pattern = rf"\b{row['bit']}\s+{words(row['name'])}\s+" + r"\s+".join(re.escape(v) for v in row["values"]) + r"(?=\s|$)"
            rows.append({**row, "evidencePdfPages": locate(pattern, span, f"{tag} usage bit {row['bit']}")})
        for mark, quote in matrix["footnotes"].items():
            require(any(normalized(quote) in normalized(p["text"]) for p in span), f"Usage footnote unresolved: {tag} {mark}")
        matrices[tag] = {**matrix, "rows": rows}
    return {"datasets": datasets, "usageMatrices": matrices,
            "marketBasketComposition": config["marketBasketComposition"],
            "encodingConventions": {
                "envelope": "TT (2) + LLL (3, excludes TT/LLL) + bitmap (8 hex chars) + fields in ascending bit order",
                "bitmap": "Bit 1 = most significant bit of the first hex character (INCOMM-MBD-2023-1-Q16)",
                "padding": "n right-justified zero-filled; a/an/ans left-justified space-filled (INCOMM-MBD-2023-1-Q15)",
                "echo": "E suffix = value echoed from the request (INCOMM-MBD-2023-1-Q14)",
                "continuationBit": "Bit 1 (secondary bitmap) is not currently used and is rejected",
            }}


# ---------------------------------------------------------------- ATL105 cross-references

def atl105_pages(text: str) -> dict[int, str]:
    parts = re.split(r"--- PAGE (\d+) ---", text)
    return {int(n): body for n, body in zip(parts[1::2], parts[2::2])}


def build_crossrefs(config: dict) -> dict:
    releases = {}
    for version, path in ATL105_TEXT.items():
        text = path.read_text(encoding="utf-8")
        release = re.search(r"Specification Release Version:\s*(\S+)", text)
        require(release is not None and release.group(1) == version, f"ATL105 text {path.name} is not release {version}")
        releases[version] = {"path": path, "pages": atl105_pages(text), "sha": text_fingerprint(path)}
    references = []
    for ref in config["atl105References"]:
        located = {}
        for version, release in releases.items():
            hits = [n for n, body in release["pages"].items()
                    if all(normalized(q) in normalized(body) for q in ref["quotes"])]
            require(len(hits) >= 1, f"ATL105 {version} reference not found: {ref['id']}")
            require(ref["expectedPdfPages"][version] in hits,
                    f"ATL105 {version} reference {ref['id']} resolved on {hits}, not {ref['expectedPdfPages'][version]}")
            page = ref["expectedPdfPages"][version]
            located[version] = {"pdfPage": page,
                                "pageTextSha256": fingerprint(normalized(release["pages"][page]).encode("utf-8"))}
        references.append({"id": ref["id"], "location": ref["location"], "rule": ref["rule"],
                           "quotes": ref["quotes"], "releases": located, "status": STATUS})
    return {"schemaVersion": 1, "status": STATUS,
            "atl105Sources": {v: {"extractedTextPath": r["path"].relative_to(PACK).as_posix(),
                                  "extractedTextSha256": r["sha"]} for v, r in releases.items()},
            "references": references}


# ---------------------------------------------------------------- draft validator

VALIDATOR_RULES = {
    "TXN_CODE": "Transaction type is one of 310, 910, 920, 200",
    "MAX_LENGTH": "Market Basket Data does not exceed the ATL105 field maximum",
    "ENVELOPE": "Each dataset has a known Tag, a 3-digit Length and enough data for its Length and fields",
    "BITMAP_HEX": "Dataset bitmap is 8 hexadecimal characters",
    "CONTINUATION_UNUSED": "Bit 1 (secondary bitmap) is not used",
    "RESERVED_BIT": "No reserved or undefined bit is present",
    "LENGTH_MATCH": "Dataset Length equals the bitmap plus the fixed widths of the present fields",
    "FIELD_FORMAT": "Each field matches its n / a / an / ans character type",
    "FIELD_EMBEDDED_SPACE": "An 'an' field contains an embedded space (padding interpretation)",
    "FIELD_VALUE": "Enumerated, MM.mm and Y/N position values are documented values",
    "DV_FIRST": "Market Basket Data starts with a DV dataset",
    "COMPOSITION_LIMIT": "Dataset counts stay within the ATL105 Segment 151/152 limits",
    "COMPOSITION_OUTSIDE_ATL105": "A dataset type not listed in the ATL105 composition is present",
    "DV_VERSION_REQUIRED": "DV carries Dataset Version",
    "NON_REAL_TIME_FIELD": "A field defined only for non-real-time implementations is present",
    "DISCOUNT_TYPE_REQUIRED": "POS discount Type accompanies its POS discount Amount",
    "MBAS_PAIR": "MBAS discount Quantity and Amount are sent together",
    "PI_CONSOLIDATION": "PIs sharing Bits 2, 5, 10, 11, 17 and 18 should be combined",
    "PURSE_ZERO_ON_910": "Item Qualification responses carry Purse ID 000",
    "PURSE_LINK": "A non-zero PI Purse ID matches a PU Purse ID in the same response",
    "USAGE_REQUIRED": "Usage-matrix R/RE fields are present for the direction and transaction type",
    "USAGE_NOT_APPLICABLE": "Usage-matrix NA fields are absent for the direction and transaction type",
    "USAGE_AMBIGUOUS": "Duplicated usage columns disagree; the cell is not enforced",
    "NOT_ON_310": "Adjustment to Price is not provided on a 310",
    "ADJUSTMENT_CONDITION_CONFLICT": "A response PI lacks Adjustment to Price, which the usage table requires but the field text makes conditional",
    "PURSE_ID_CONDITION_CONFLICT": "A response PI without any PU lacks Purse ID, which the usage table requires but the field text ties to responses containing a purse",
    "ITEM_REF_UNIQUE": "Item Reference Numbers are unique per PI on the request",
    "ITEM_REF_KNOWN": "Every response Item Reference Number was sent in the request",
    "ECHO_MATCH": "Echoed (E) PI fields equal the request values",
    "SPLIT_QUANTITY": "Split response quantities add up to the requested quantity",
    "OC_TYPE_DIRECTION": "OC Type belongs to the set listed for the message direction",
}
OC_TYPES = {"response": {"tallyEarn", "tallyRedeem", "redeemedCount"},
            "request": {"incrementedEarn", "incrementedRedeem", "timesRedeemed", "totalAmountAwarded"}}


def finding(rule: str, severity: str, dataset: str | None, bit: int | None, message: str, ref: str) -> dict:
    require(rule in VALIDATOR_RULES, f"Unregistered validator rule: {rule}")
    return {"rule": rule, "severity": severity, "dataset": dataset, "bit": bit, "message": message, "reference": ref}


def check_value(field: dict, value: str, tag: str) -> list[dict]:
    issues, kind, bit = [], field["type"], field["bit"]
    content = value if kind == "n" else value.rstrip(" ")
    if kind == "n" and not value.isdigit():
        issues.append(finding("FIELD_FORMAT", VIOLATION, tag, bit, f"{field['name']} must be numeric", "format"))
    elif kind == "a" and content and not re.fullmatch(r"[A-Za-z]+", content.replace(" ", "")):
        issues.append(finding("FIELD_FORMAT", VIOLATION, tag, bit, f"{field['name']} must be alphabetic", "format"))
    elif kind == "an" and "valuePattern" not in field:
        if not re.fullmatch(r"[A-Za-z0-9 ]*", content):
            issues.append(finding("FIELD_FORMAT", VIOLATION, tag, bit, f"{field['name']} must be alphanumeric", "format"))
        elif " " in content:
            issues.append(finding("FIELD_EMBEDDED_SPACE", REVIEW, tag, bit, f"{field['name']} contains an embedded space in an 'an' field", "INCOMM-MBD-2023-1-Q15"))
    elif kind == "ans" and not re.fullmatch(r"[\x20-\x7e]*", value):
        issues.append(finding("FIELD_FORMAT", VIOLATION, tag, bit, f"{field['name']} contains non-printable characters", "format"))
    if "valuePattern" in field and not re.fullmatch(field["valuePattern"], value):
        issues.append(finding("FIELD_VALUE", VIOLATION, tag, bit, f"{field['name']} must match MM.mm", "INCOMM-MBD-2023-1-Q07"))
    if "values" in field and content not in field["values"]:
        issues.append(finding("FIELD_VALUE", VIOLATION, tag, bit, f"{field['name']} value '{content}' is not a documented value", "enumeration"))
    if "positionValues" in field:
        for position in (0, 1):
            if value[position] not in field["positionValues"]:
                issues.append(finding("FIELD_VALUE", VIOLATION, tag, bit, f"{field['name']} position {position + 1} must be Y or N", "enumeration"))
    return issues


def parse_datasets(text: str, catalog: dict) -> tuple[list[dict], list[dict]]:
    datasets, issues, pos = [], [], 0
    while pos < len(text):
        tag, size = text[pos:pos + 2], text[pos + 2:pos + 5]
        if tag not in catalog["datasets"]:
            issues.append(finding("ENVELOPE", VIOLATION, tag, None, f"Unknown dataset tag '{tag}' at offset {pos}", "INCOMM-MBD-2023-1-L008"))
            break
        if not re.fullmatch(r"\d{3}", size):
            issues.append(finding("ENVELOPE", VIOLATION, tag, None, "Length is not three digits", "INCOMM-MBD-2023-1-L004"))
            break
        body = text[pos + 5:pos + 5 + int(size)]
        if len(body) < int(size):
            issues.append(finding("ENVELOPE", VIOLATION, tag, None, "Dataset is shorter than its Length", "INCOMM-MBD-2023-1-L005"))
            break
        pos += 5 + int(size)
        bitmap = body[:8]
        if not re.fullmatch(r"[0-9A-Fa-f]{8}", bitmap):
            issues.append(finding("BITMAP_HEX", VIOLATION, tag, None, "Bitmap is not 8 hexadecimal characters", "INCOMM-MBD-2023-1-L006"))
            continue
        present = [i + 1 for i in range(32) if int(bitmap, 16) & (1 << (31 - i))]
        fields = {f["bit"]: f for f in catalog["datasets"][tag]["fields"]}
        values, offset, broken = {}, 8, False
        for bit in present:
            if bit == 1:
                issues.append(finding("CONTINUATION_UNUSED", VIOLATION, tag, 1, "Continuation (secondary) bitmap is not currently used", "INCOMM-MBD-2023-1-L006"))
                broken = True
                break
            if bit not in fields:
                issues.append(finding("RESERVED_BIT", VIOLATION, tag, bit, "Bit is reserved or undefined for this dataset", "dataset-catalog"))
                broken = True
                break
            width = fields[bit]["length"]
            value = body[offset:offset + width]
            if len(value) < width:
                issues.append(finding("ENVELOPE", VIOLATION, tag, bit, "Field is truncated", "INCOMM-MBD-2023-1-L005"))
                broken = True
                break
            issues.extend(check_value(fields[bit], value, tag))
            values[bit] = value
            offset += width
        if not broken and offset != len(body):
            issues.append(finding("LENGTH_MATCH", VIOLATION, tag, None, f"Length {size} does not match bitmap fields ({offset})", "INCOMM-MBD-2023-1-L005"))
        datasets.append({"tag": tag, "length": int(size), "bitmap": bitmap.upper(), "fields": values})
    return datasets, issues


def build_dataset(tag: str, values: dict[int, str], catalog: dict) -> str:
    fields = {f["bit"]: f for f in catalog["datasets"][tag]["fields"]}
    bitmap, body = 0, ""
    for bit in sorted(values):
        require(bit in fields and bit != 1, f"Cannot encode {tag} bit {bit}")
        field, value = fields[bit], str(values[bit])
        require(len(value) <= field["length"], f"{tag} bit {bit} exceeds {field['length']}")
        body += value.rjust(field["length"], "0") if field["type"] == "n" else value.ljust(field["length"], " ")
        bitmap |= 1 << (32 - bit)
    payload = f"{bitmap:08X}" + body
    return f"{tag}{len(payload):03d}{payload}"


def requirement(matrix: dict, row: dict, direction: str, txn: str) -> str | None:
    cells = [row["values"][i] for i, c in enumerate(matrix["columns"])
             if c["direction"] == direction and txn in c["transactionTypes"]]
    if not cells:
        return None
    if txn == "310" and any(cell.endswith("**") for cell in cells):
        return "NOT_ON_310"
    codes = {cell.rstrip("*") for cell in cells}
    return codes.pop() if len(codes) == 1 else "AMBIGUOUS"


def check_dataset_rules(d: dict, direction: str, txn: str, catalog: dict, flagged: set, has_purse: bool) -> list[dict]:
    issues, tag, values = [], d["tag"], d["fields"]
    if tag == "DV":
        if 2 not in values:
            issues.append(finding("DV_VERSION_REQUIRED", VIOLATION, "DV", 2, "Dataset Version is required", "INCOMM-MBD-2023-1-L010"))
        for bit in (3, 4):
            if bit in values:
                issues.append(finding("NON_REAL_TIME_FIELD", REVIEW, "DV", bit, "Non-real-time file version present", "INCOMM-MBD-2023-1-Q01"))
    if tag == "PI":
        for amount, kind in ((10, 11), (17, 18)):
            if amount in values and kind not in values:
                issues.append(finding("DISCOUNT_TYPE_REQUIRED", VIOLATION, "PI", kind, "Discount Type is required when the Discount Amount is present", "INCOMM-MBD-2023-1-L018"))
        for qty, amt in ((6, 7), (15, 16)):
            if (qty in values) != (amt in values):
                issues.append(finding("MBAS_PAIR", VIOLATION, "PI", qty, "MBAS discount quantity and amount must be sent together", "INCOMM-MBD-2023-1-L016"))
        if direction == "response" and txn == "910" and values.get(14, "000") != "000":
            issues.append(finding("PURSE_ZERO_ON_910", VIOLATION, "PI", 14, "Item Qualification responses carry Purse ID 000", "INCOMM-MBD-2023-1-L021"))
    if tag == "PU" and direction == "response":
        for bit in (8, 9):
            if bit in values:
                issues.append(finding("NON_REAL_TIME_FIELD", REVIEW, "PU", bit, "Non-real-time purse field present", "INCOMM-MBD-2023-1-Q01"))
    if tag == "OC" and 5 in values and values[5].rstrip(" ") not in OC_TYPES[direction]:
        issues.append(finding("OC_TYPE_DIRECTION", REVIEW, "OC", 5, f"OC Type '{values[5].rstrip()}' is not listed for a {direction}", "INCOMM-MBD-2023-1-Q05"))
    matrix = catalog["usageMatrices"].get(tag)
    for row in (matrix["rows"] if matrix else []):
        code = requirement(matrix, row, direction, txn)
        if code == "AMBIGUOUS":
            if (tag, row["bit"]) not in flagged:
                flagged.add((tag, row["bit"]))
                issues.append(finding("USAGE_AMBIGUOUS", REVIEW, tag, row["bit"], f"{row['name']} requirement differs between duplicated columns; not enforced", "INCOMM-MBD-2023-1-Q03"))
        elif code in ("R", "RE") and row["bit"] not in values:
            if tag == "PI" and row["bit"] == 12:
                issues.append(finding("ADJUSTMENT_CONDITION_CONFLICT", REVIEW, tag, 12, f"{row['name']} is absent; required by the usage table but conditional in the field text", "INCOMM-MBD-2023-1-Q18"))
            elif tag == "PI" and row["bit"] == 14 and not has_purse:
                issues.append(finding("PURSE_ID_CONDITION_CONFLICT", REVIEW, tag, 14, f"{row['name']} is absent in a response without a PU; the field text requires it only when a purse is present", "INCOMM-MBD-2023-1-Q19"))
            else:
                issues.append(finding("USAGE_REQUIRED", VIOLATION, tag, row["bit"], f"{row['name']} is required for {direction} {txn}", f"{matrix['chapter']} usage matrix"))
        elif code == "NA" and row["bit"] in values:
            issues.append(finding("USAGE_NOT_APPLICABLE", VIOLATION, tag, row["bit"], f"{row['name']} is not applicable for {direction} {txn}", f"{matrix['chapter']} usage matrix"))
        elif code == "NOT_ON_310" and row["bit"] in values:
            issues.append(finding("NOT_ON_310", REVIEW, tag, row["bit"], f"{row['name']} is not provided on a 310", "INCOMM-MBD-2023-1-Q11"))
    return issues


def check_cross_dataset(datasets: list[dict], direction: str, catalog: dict, request_text: str | None) -> list[dict]:
    issues = []
    pis = [d["fields"] for d in datasets if d["tag"] == "PI"]
    if direction == "request":
        refs = [p.get(13) for p in pis if 13 in p]
        if len(refs) != len(set(refs)):
            issues.append(finding("ITEM_REF_UNIQUE", VIOLATION, "PI", 13, "Item Reference Number must be unique per PI on the request", "INCOMM-MBD-2023-1-L020"))
        keys = [tuple(p.get(b) for b in (2, 5, 10, 11, 17, 18)) for p in pis]
        if len(keys) != len(set(keys)):
            issues.append(finding("PI_CONSOLIDATION", REVIEW, "PI", 3, "PIs with identical Bits 2, 5, 10, 11, 17 and 18 should be combined into one PI", "INCOMM-MBD-2023-1-L014"))
        return issues
    purse_ids = {d["fields"][3] for d in datasets if d["tag"] == "PU" and 3 in d["fields"]}
    for p in pis:
        if p.get(14, "000") != "000" and purse_ids and p[14] not in purse_ids:
            issues.append(finding("PURSE_LINK", VIOLATION, "PI", 14, f"PI Purse ID {p[14]} has no matching PU Purse ID", "INCOMM-MBD-2023-1-L021"))
    if request_text is None:
        return issues
    sent = {p[13]: p for p in (d["fields"] for d in parse_datasets(request_text, catalog)[0] if d["tag"] == "PI") if 13 in p}
    quantities, redemptions, parts = {}, {}, {}
    for p in pis:
        ref = p.get(13)
        if ref not in sent:
            issues.append(finding("ITEM_REF_KNOWN", VIOLATION, "PI", 13, f"Response Item Reference Number {ref} was not sent in the request", "INCOMM-MBD-2023-1-L020"))
            continue
        for bit in (2, 4, 5):
            if bit in p and bit in sent[ref] and p[bit] != sent[ref][bit]:
                issues.append(finding("ECHO_MATCH", VIOLATION, "PI", bit, f"Echoed bit {bit} differs from the request for reference {ref}", "INCOMM-MBD-2023-1-Q14"))
        parts[ref] = parts.get(ref, 0) + 1
        if 3 in p:
            quantities[ref] = quantities.get(ref, 0) + int(p[3])
        if 9 in p:
            redemptions[ref] = redemptions.get(ref, 0) + int(p[9])
    for ref, total in quantities.items():
        if 3 in sent[ref] and total != int(sent[ref][3]):
            issues.append(finding("SPLIT_QUANTITY", REVIEW, "PI", 3, f"Split quantities for reference {ref} total {total}, request sent {int(sent[ref][3])}", "INCOMM-MBD-2023-1-L022"))
    for ref, total in redemptions.items():
        if 9 in sent[ref] and total != int(sent[ref][9]):
            split = parts[ref] > 1
            issues.append(finding("ECHO_MATCH", REVIEW if split else VIOLATION, "PI", 9,
                                  f"Redemption Request Amount for reference {ref} {'totals' if split else 'is'} {total}, request sent {int(sent[ref][9])}",
                                  "INCOMM-MBD-2023-1-Q14"))
    return issues


def validate_market_basket(text: str, direction: str, txn: str, catalog: dict, request_text: str | None = None) -> dict:
    require(direction in ("request", "response"), "direction must be request or response")
    issues = []
    if txn not in TRANSACTION_TYPES:
        issues.append(finding("TXN_CODE", VIOLATION, None, None, f"Transaction type {txn} is not 310/910/920/200", "INCOMM-MBD-2023-1-L003"))
    composition = catalog["marketBasketComposition"]
    rules = composition[direction]
    if len(text) > composition["maxLength"]:
        issues.append(finding("MAX_LENGTH", VIOLATION, None, None, f"Market Basket Data length {len(text)} exceeds {composition['maxLength']}", rules["source"]))
    datasets, parse_issues = parse_datasets(text, catalog)
    issues.extend(parse_issues)
    if not datasets or datasets[0]["tag"] != rules["first"]:
        issues.append(finding("DV_FIRST", VIOLATION, None, None, "Market Basket Data must start with one DV dataset", rules["source"]))
    counts = {}
    for d in datasets:
        counts[d["tag"]] = counts.get(d["tag"], 0) + 1
    for tag, count in counts.items():
        if tag not in rules["allowed"]:
            issues.append(finding("COMPOSITION_OUTSIDE_ATL105", REVIEW, tag, None, f"{tag} is not listed in the ATL105 {direction} composition", "INCOMM-MBD-2023-1-Q01"))
        elif count > rules["allowed"][tag]:
            issues.append(finding("COMPOSITION_LIMIT", VIOLATION, tag, None, f"{count} {tag} datasets exceed the ATL105 limit of {rules['allowed'][tag]}", rules["source"]))
    flagged = set()
    has_purse = "PU" in counts
    for d in datasets:
        issues.extend(check_dataset_rules(d, direction, txn, catalog, flagged, has_purse))
    issues.extend(check_cross_dataset(datasets, direction, catalog, request_text))
    verdict = "FAIL" if any(i["severity"] == VIOLATION for i in issues) else ("REVIEW_REQUIRED" if issues else "PASS")
    return {"verdict": verdict, "status": "DRAFT_VALIDATOR_NOT_EXECUTION_ORACLE", "datasets": datasets, "findings": issues}

# ---------------------------------------------------------------- training plan and business requirements

BR_STATUS_BLOCKED = "DRAFT_BLOCKED_PENDING_SME"
ENFORCEMENT_LEVELS = {"ENFORCED", "PARTIAL", "NOT_DATA_ENFORCEABLE"}
PLAN_GATES = [None, "SOURCE_INVENTORY", None, "SEGMENT_KNOWLEDGE_MODEL", "CONTEXT_MATRIX", "INDEPENDENT_BR_DERIVATION",
              "TS_TC_TEST_DATA_CHAIN", "LIFECYCLE_AND_SPECIALIZED_FLOWS", "SERIALIZATION_AND_MUTATION",
              "INDEPENDENT_AI_ARTIFACT_INTAKE", "CONVERTER_AND_EXECUTION_READINESS"]


def check_plan(plan: dict) -> None:
    require(plan["schemaVersion"] == 1 and plan["sourceVersion"] == "2023-1", "Training plan is not for InComm Market Basket 2023-1")
    require([p["phase"] for p in plan["phases"]] == list(range(11)), "Training plan must define phases 0 to 10 in order")
    require([p["frameworkGate"] for p in plan["phases"]] == PLAN_GATES, "Training plan phases map to the wrong framework gates")
    require(set(plan["scopeDecision"]["excludedArtifacts"]) == {"testScenarios", "testCases", "testData"},
            "Training plan scope must exclude test scenarios, test cases and test data")


def build_business_requirements(config: dict, lessons: list[dict], chapters: list[dict], catalog: dict,
                                crossrefs: dict, questions: list[dict]) -> dict:
    require(config["schemaVersion"] == 1 and config["status"] == STATUS, "Only draft InComm Market Basket BRs may be derived")
    lesson_by_id = {l["id"]: l for l in lessons}
    chapter_ids = {c["id"] for c in chapters}
    question_by_id = {q["id"]: q for q in questions}
    ref_by_id = {r["id"]: r for r in crossrefs["references"]}
    informational = config["informationalLessons"]
    require(set(informational) <= set(lesson_by_id), "Informational lesson list cites an unknown lesson")
    brs, seen = [], []
    for index, br in enumerate(config["businessRequirements"], 1):
        owner = br["id"]
        require(owner == f"{config['idPrefix']}{index:03d}" and owner not in seen, f"BR IDs must be sequential and unique: {owner}")
        seen.append(owner)
        require(br["chapter"] in chapter_ids, f"{owner} cites unknown chapter {br['chapter']}")
        require(bool(br["title"].strip()) and bool(br["statement"].strip()), f"{owner} lacks a title or statement")
        require(set(br["appliesTo"]["direction"]) <= {"request", "response"} and bool(br["appliesTo"]["direction"]), f"{owner} has an invalid direction")
        require(set(br["appliesTo"]["transactionTypes"]) <= set(TRANSACTION_TYPES) and bool(br["appliesTo"]["transactionTypes"]),
                f"{owner} has an invalid transaction type")
        require(bool(br["lessonIds"] or br["catalogRefs"] or br["atl105RefIds"]), f"{owner} has no source anchor")
        anchors = {"lessons": [], "catalog": [], "atl105": []}
        for lid in br["lessonIds"]:
            require(lid in lesson_by_id and lid not in informational, f"{owner} cites unknown or informational lesson {lid}")
            lesson = lesson_by_id[lid]
            anchors["lessons"].append({"id": lid, "chapter": lesson["chapter"], "statement": lesson["statement"],
                                       "pdfPages": sorted({e["pdfPage"] for e in lesson["evidence"]})})
        for ref in br["catalogRefs"]:
            if "usageMatrix" in ref:
                matrix = catalog["usageMatrices"].get(ref["usageMatrix"])
                require(matrix is not None, f"{owner} cites unknown usage matrix {ref['usageMatrix']}")
                anchors["catalog"].append({"usageMatrix": ref["usageMatrix"], "chapter": matrix["chapter"], "pdfPages": matrix["pdfPages"]})
                continue
            dataset = catalog["datasets"].get(ref["dataset"])
            require(dataset is not None, f"{owner} cites unknown dataset {ref['dataset']}")
            fields = {f["bit"]: f for f in dataset["fields"]}
            bits = sorted(fields) if ref["bits"] == "all" else ref["bits"]
            require(bool(bits) and all(b in fields for b in bits), f"{owner} cites an unknown {ref['dataset']} bit")
            anchors["catalog"].append({"dataset": ref["dataset"], "chapter": dataset["chapter"], "fields": [
                {"bit": b, "name": fields[b]["name"], "format": fields[b]["format"], "pdfPages": fields[b]["evidencePdfPages"]} for b in bits]})
        for rid in br["atl105RefIds"]:
            require(rid in ref_by_id, f"{owner} cites unknown ATL105 reference {rid}")
            anchors["atl105"].append({"id": rid, "location": ref_by_id[rid]["location"],
                                      "pdfPages": {v: p["pdfPage"] for v, p in ref_by_id[rid]["releases"].items()}})
        require(all(q in question_by_id for q in br["questionIds"]), f"{owner} cites an unknown question")
        require(set(br["blockingQuestionIds"]) <= set(br["questionIds"]), f"{owner} blocking questions must also be listed as questions")
        require(all(question_by_id[q]["status"] == "REVIEW_REQUIRED" for q in br["blockingQuestionIds"]),
                f"{owner} may only be blocked by an unresolved REVIEW_REQUIRED question")
        enforcement = br["enforcement"]
        require(enforcement["level"] in ENFORCEMENT_LEVELS, f"{owner} has an unknown enforcement level")
        require(all(r in VALIDATOR_RULES for r in enforcement["rules"]), f"{owner} cites an unregistered validator rule")
        if enforcement["level"] == "NOT_DATA_ENFORCEABLE":
            require(not enforcement["rules"] and bool(enforcement["gap"]), f"{owner} not-enforceable BR needs a gap and no rules")
        else:
            require(bool(enforcement["rules"]), f"{owner} enforced BR needs at least one validator rule")
            require(enforcement["level"] == "ENFORCED" or bool(enforcement["gap"]), f"{owner} partial BR must state its gap")
        brs.append({
            "id": owner, "chapter": br["chapter"], "category": br["category"], "title": br["title"], "statement": br["statement"],
            "appliesTo": br["appliesTo"], "sourceAnchors": anchors,
            "openQuestionIds": [q for q in br["questionIds"] if question_by_id[q]["status"] == "REVIEW_REQUIRED"],
            "qeInterpretationIds": [q for q in br["questionIds"] if question_by_id[q]["status"] == "QE_INTERPRETATION_PENDING_SME"],
            "blockingQuestionIds": br["blockingQuestionIds"],
            "enforcement": enforcement,
            "status": BR_STATUS_BLOCKED if br["blockingQuestionIds"] else STATUS,
        })
    cited_lessons = {a["id"] for b in brs for a in b["sourceAnchors"]["lessons"]}
    missing = sorted(set(lesson_by_id) - cited_lessons - set(informational))
    require(not missing, "Lessons not covered by any BR: " + ", ".join(missing))
    cited_datasets = {a["dataset"] for b in brs for a in b["sourceAnchors"]["catalog"] if "dataset" in a}
    require(cited_datasets == set(catalog["datasets"]), "Every dataset must be covered by a BR")
    cited_matrices = {a["usageMatrix"] for b in brs for a in b["sourceAnchors"]["catalog"] if "usageMatrix" in a}
    require(cited_matrices == set(catalog["usageMatrices"]), "Every usage matrix must be covered by a BR")
    cited_refs = {a["id"] for b in brs for a in b["sourceAnchors"]["atl105"]}
    require(cited_refs == set(ref_by_id), "Every ATL105 reference must be covered by a BR")
    open_questions = {q["id"] for q in questions if q["status"] == "REVIEW_REQUIRED"}
    require(open_questions <= {q for b in brs for q in b["openQuestionIds"]}, "Every open question must be linked to a BR")
    content = {c["id"] for c in chapters if c["id"] not in ("CH-REV", "CH-TOC")}
    covered = {b["chapter"] for b in brs} | {a["chapter"] for b in brs for a in b["sourceAnchors"]["lessons"]}
    require(content <= covered, "Content chapters without a BR: " + ", ".join(sorted(content - covered)))
    used_rules = {r for b in brs for r in b["enforcement"]["rules"]}
    require(used_rules == set(VALIDATOR_RULES), "Validator rules not tied to a BR: " + ", ".join(sorted(set(VALIDATOR_RULES) - used_rules)))
    return {"businessRequirements": brs, "informationalLessons": informational,
            "coverage": {"lessons": f"{len(cited_lessons)}/{len(lesson_by_id) - len(informational)} non-informational",
                         "datasets": sorted(cited_datasets), "usageMatrices": sorted(cited_matrices),
                         "atl105References": sorted(cited_refs), "openQuestions": len(open_questions),
                         "chapters": sorted(content), "validatorRules": len(used_rules)}}


def phase_progress(plan: dict, manifest_counts: dict, brs: list[dict]) -> list[dict]:
    blocked = sum(b["status"] == BR_STATUS_BLOCKED for b in brs)
    lifecycle = sum(b["category"] == "LIFECYCLE" for b in brs)
    statuses = {
        0: ("DRAFTED_PENDING_SME_SIGNOFF", f"{manifest_counts['pages']} pages, {manifest_counts['bookmarks']} bookmarks; scope question Q01 open"),
        1: ("DRAFTED_PENDING_SME_REVIEW", f"{manifest_counts['chapters']} chapters, {manifest_counts['lessons']} page-quoted lessons"),
        2: ("DRAFTED_PENDING_SME_REVIEW", f"{manifest_counts['atl105']} ATL105 references located in 2026-3 and 2026-4; {manifest_counts['questions']} open questions"),
        3: ("DRAFTED_PENDING_SME_REVIEW", f"{manifest_counts['fields']} dataset fields and {manifest_counts['usageRows']} usage rows re-matched to page text"),
        4: ("DRAFTED_PENDING_SME_REVIEW", "Usage columns mapped to direction x 310/910/920/200; duplicated PU columns flagged (Q03)"),
        5: ("DRAFTED_PENDING_SME_REVIEW", f"{len(brs)} draft BRs ({blocked} blocked pending SME)"),
        6: ("OUT_OF_SCOPE_BY_USER_DECISION", "No TS, TC or TD are created for this document (user decision 2026-10-30)"),
        7: ("DRAFTED_PENDING_SME_REVIEW", f"{lifecycle} lifecycle BRs drawn only from documented 310/910/920/200 relationships"),
        8: ("PARTIAL_ATL105_SERIALIZATION_BLOCKED", f"{len(VALIDATOR_RULES)} validator rules, each reachable by a regression mutation; Segment 151/152 wire serialization not demonstrated"),
        9: ("BLOCKED_NO_AI_PACKAGE", "No AI-produced Segment 151/152 package has been supplied"),
        10: ("BLOCKED_PENDING_EXECUTION_AND_SME_APPROVAL", "No execution environment or SME approval"),
    }
    return [{"phase": p["phase"], "id": p["id"], "name": p["name"], "frameworkGate": p["frameworkGate"],
             "status": statuses[p["phase"]][0], "evidence": statuses[p["phase"]][1]} for p in plan["phases"]]


# ---------------------------------------------------------------- report and build

def report(source: dict, chapters: list[dict], lessons: list[dict], catalog: dict, crossrefs: dict, questions: list[dict],
           br_package: dict) -> str:
    by_chapter = {}
    for lesson in lessons:
        by_chapter.setdefault(lesson["chapter"], []).append(lesson)
    brs = br_package["businessRequirements"]
    out = [f"# {source['title']} {source['version']} — Chapter Training Report", "",
           "Status: `DRAFT_REVIEW_REQUIRED`. Generated by `scripts/ingest-incomm-market-basket.py`; do not edit.",
           "Every lesson quote, dataset field row and usage-matrix row below is verified against page-local text. "
           f"{len(brs)} draft BRs are derived; by user decision no TS/TC/TD are created, and no execution is certified.", "",
           "## Training phases", "", "| Phase | Name | Status | Evidence |", "|---|---|---|---|"]
    for p in br_package["phaseProgress"]:
        out.append(f"| {p['phase']} | {p['name']} | `{p['status']}` | {p['evidence']} |")
    out += ["", "## Chapters", "", "| Chapter | Title | PDF pages | Printed | Lessons | BRs |", "|---|---|---|---|---|---|"]
    for c in chapters:
        pages = f"{c['pdfPages'][0]}-{c['pdfPages'][1]}" if c["pdfPages"][0] != c["pdfPages"][1] else str(c["pdfPages"][0])
        out.append(f"| {c['id']} | {c['title']} | {pages} | {', '.join(c['printedPages']) or '-'} | "
                   f"{len(by_chapter.get(c['id'], []))} | {len(c['brIds'])} |")
    datasets_by_chapter = {d["chapter"]: d for d in catalog["datasets"].values()}
    matrices_by_chapter = {m["chapter"]: (t, m) for t, m in catalog["usageMatrices"].items()}
    questions_by_chapter = {}
    for q in questions:
        questions_by_chapter.setdefault(q["chapter"], []).append(q)
    for c in chapters:
        out += ["", f"## {c['title']}", "", c["summary"], ""]
        for lesson in by_chapter.get(c["id"], []):
            pages = sorted({e["pdfPage"] for e in lesson["evidence"]})
            out.append(f"- **{lesson['id']}** (p.{', '.join(map(str, pages))}): {lesson['statement']}")
        dataset = datasets_by_chapter.get(c["id"])
        if dataset:
            out += ["", f"**{dataset['tag']} dataset fields**", "", "| Bit | Field | Format | Description (source quote) | Source page |", "|---|---|---|---|---|"]
            for f in dataset["fields"]:
                out.append(f"| {f['bit']} | {f['name']} | {f['format']} | {f['descriptionQuote']} | {'-'.join(map(str, f['evidencePdfPages']))} |")
            if dataset["reservedBits"]:
                out.append(f"| {dataset['reservedBits'][0]}-{dataset['reservedBits'][1]} | Reserved | - | Reserved for future use | - |")
        if c["id"] in matrices_by_chapter:
            tag, matrix = matrices_by_chapter[c["id"]]
            out += ["", f"**{tag} usage matrix**", "", "| Bit | Field | " + " | ".join(col["label"] for col in matrix["columns"]) + " |",
                    "|---|---|" + "---|" * len(matrix["columns"])]
            for row in matrix["rows"]:
                out.append(f"| {row['bit']} | {row['name']} | " + " | ".join(row["values"]) + " |")
        for q in questions_by_chapter.get(c["id"], []):
            out.append(f"- ❓ **{q['id']}** ({q['status']}): {q['question']}" + (f" _QE decision: {q['qeDecision']}_" if q.get("qeDecision") else ""))
    out += ["", "## ATL105 cross-references", "", "| ID | ATL105 location | 2026-3 page | 2026-4 page | Rule |", "|---|---|---|---|---|"]
    for ref in crossrefs["references"]:
        out.append(f"| {ref['id']} | {ref['location']} | {ref['releases']['2026-3']['pdfPage']} | {ref['releases']['2026-4']['pdfPage']} | {ref['rule']} |")
    out += ["", "## Business requirements (draft)", "",
            "Each BR cites lessons, catalog rows or ATL105 references verified above. `DRAFT_BLOCKED_PENDING_SME` means the BR "
            "depends on an unresolved question. Enforcement states how the draft validator checks it.", "",
            "| BR | Chapter | Category | Requirement | Applies to | Sources | Enforcement | Status |", "|---|---|---|---|---|---|---|---|"]
    for b in brs:
        sources = [a["id"].replace("INCOMM-MBD-2023-1-", "") for a in b["sourceAnchors"]["lessons"]]
        sources += [a["usageMatrix"] + " usage" if "usageMatrix" in a else
                    f"{a['dataset']} bit {','.join(str(f['bit']) for f in a['fields'])}" if len(a["fields"]) < 10 else f"{a['dataset']} all"
                    for a in b["sourceAnchors"]["catalog"]]
        sources += [a["id"].replace("INCOMM-MBD-ATL105-", "ATL105-") for a in b["sourceAnchors"]["atl105"]]
        applies = f"{'/'.join(b['appliesTo']['direction'])} {'/'.join(b['appliesTo']['transactionTypes'])}"
        enforcement = b["enforcement"]["level"] + (f" ({', '.join(b['enforcement']['rules'])})" if b["enforcement"]["rules"] else "")
        blocked = f" — blocked by {', '.join(q.replace('INCOMM-MBD-2023-1-', '') for q in b['blockingQuestionIds'])}" if b["blockingQuestionIds"] else ""
        out.append(f"| {b['id']} | {b['chapter']} | {b['category']} | **{b['title']}**: {b['statement']} | {applies} | "
                   f"{'; '.join(sources)} | {enforcement} | `{b['status']}`{blocked} |")
    gaps = [b for b in brs if b["enforcement"]["gap"]]
    out += ["", "**Enforcement gaps**", ""] + [f"- {b['id']}: {b['enforcement']['gap']}" for b in gaps]
    out += ["", "## Draft validator", "",
            "`python scripts/ingest-incomm-market-basket.py --validate <MarketBasketData> --direction request|response --txn 310|910|920|200 [--request <requestData>]`",
            "returns `PASS`, `REVIEW_REQUIRED` or `FAIL`; each finding carries the validator rule ID that its BR declares. "
            "It is a draft checker for Segment 151/152 data, not an execution oracle.", ""]
    return "\n".join(out)


def build() -> dict[str, str]:
    config = json.loads(INPUT.read_text(encoding="utf-8"))
    pages, outline = extract_source(PDF, config)
    chapters = build_chapters(config, pages, outline)
    chapter_ids = {c["id"] for c in chapters}
    lessons = build_lessons(config, pages, chapter_ids)
    for lesson in lessons:
        chapter = next(c for c in chapters if c["id"] == lesson["chapter"])
        for e in lesson["evidence"]:
            require(chapter["pdfPages"][0] <= e["pdfPage"] <= chapter["pdfPages"][1],
                    f"Lesson {lesson['id']} cites page {e['pdfPage']} outside chapter {chapter['id']}")
    catalog = build_catalog(config, pages)
    crossrefs = build_crossrefs(config)
    plan = json.loads(PLAN.read_text(encoding="utf-8"))
    check_plan(plan)
    br_package = build_business_requirements(json.loads(BR_INPUT.read_text(encoding="utf-8")), lessons, chapters,
                                             catalog, crossrefs, config["openQuestions"])
    brs = br_package["businessRequirements"]
    for chapter in chapters:
        chapter["brIds"] = [b["id"] for b in brs if b["chapter"] == chapter["id"]]
        chapter["lessonIds"] = [l["id"] for l in lessons if l["chapter"] == chapter["id"]]
        chapter["datasets"] = [t for t, d in catalog["datasets"].items() if d["chapter"] == chapter["id"]]
        chapter["usageMatrices"] = [t for t, m in catalog["usageMatrices"].items() if m["chapter"] == chapter["id"]]
        chapter["openQuestionIds"] = [q["id"] for q in config["openQuestions"] if q["chapter"] == chapter["id"]]
    source = {"id": "BUYPASS-INCOMM-MBD-2023-1", "title": config["title"], "version": config["version"],
              "releaseDate": config["releaseDate"], "path": PDF.relative_to(PACK).as_posix(), "sha256": config["sourceSha256"]}
    gates = {"status": STATUS, "certified": False, "executionReady": False, "baselineChanged": False,
             "modelWeightsChanged": False, "independentBrCount": 0, "testScenarioCount": 0,
             "testCaseCount": 0, "testDataCount": 0, "executedCaseCount": 0}
    blocked = sum(b["status"] == BR_STATUS_BLOCKED for b in brs)
    br_package["phaseProgress"] = phase_progress(plan, {
        "pages": len(pages), "bookmarks": len(outline), "chapters": len(chapters), "lessons": len(lessons),
        "atl105": len(crossrefs["references"]),
        "questions": sum(q["status"] == "REVIEW_REQUIRED" for q in config["openQuestions"]),
        "fields": sum(len(d["fields"]) for d in catalog["datasets"].values()),
        "usageRows": sum(len(m["rows"]) for m in catalog["usageMatrices"].values())}, brs)
    outputs = {
        "pages.json": json_text({"schemaVersion": 1, "source": source,
                                 "usage": "FULL_DOCUMENT_RETRIEVAL_EVIDENCE_NOT_TRAINING_TRUTH", "pages": pages}),
        "source-index.json": json_text({"schemaVersion": 1, "source": source,
                                        "usage": "PDF_BOOKMARKS_NOT_VALIDATED_RULES", "outline": outline}),
        "chapters.json": json_text({"schemaVersion": 1, "source": source, "status": STATUS,
                                    "usage": "CHAPTER_SPANS_FROM_BOOKMARKS_HEADINGS_VERIFIED", "chapters": chapters}),
        "training-lessons.json": json_text({"schemaVersion": 1, "source": source, **gates,
                                            "authority": "SUPPLEMENTAL_SOURCE_DRAFT_NOT_EXECUTABLE_ORACLE",
                                            "atl105Compatibility": {"trainedBaseline": "2026-3", "availableRelease": "2026-4",
                                                                    "crossVersionReconciliation": "REFERENCES_LOCATED_IN_BOTH"},
                                            "lessons": lessons, "openQuestions": config["openQuestions"]}),
        "dataset-catalog.json": json_text({"schemaVersion": 1, "source": source, **gates, **catalog}),
        "atl105-incomm-cross-references.json": json_text({**crossrefs, "source": source}),
        "business-requirements.json": json_text({
            "schemaVersion": 1, "contextId": "MARKET_BASKET_DATA", "planId": plan["id"], "source": source, **gates,
            "draftCounts": {"businessRequirements": len(brs), "blockedPendingSme": blocked,
                            "enforced": sum(b["enforcement"]["level"] == "ENFORCED" for b in brs),
                            "partiallyEnforced": sum(b["enforcement"]["level"] == "PARTIAL" for b in brs),
                            "notDataEnforceable": sum(b["enforcement"]["level"] == "NOT_DATA_ENFORCEABLE" for b in brs)},
            "countPolicy": "Draft BRs are authored from source text and stay out of independent Test Solution counts until SME approval.",
            "scopeDecision": plan["scopeDecision"],
            "phaseProgress": br_package["phaseProgress"],
            "coverage": br_package["coverage"],
            "validatorRules": VALIDATOR_RULES,
            "informationalLessons": br_package["informationalLessons"],
            "businessRequirements": brs}),
        "training-report.md": report(source, chapters, lessons, catalog, crossrefs, config["openQuestions"], br_package),
    }
    outputs["ingestion-manifest.json"] = json_text({
        "schemaVersion": 1, "source": source, **gates,
        "pageCount": len(pages),
        "textPageCount": sum(p["extractionStatus"] == "TEXT_EXTRACTED" for p in pages),
        "emptyPagesRequiringReview": [p["pdfPage"] for p in pages if not p["text"].strip()],
        "bookmarkCount": len(outline), "chapterCount": len(chapters), "draftLessonCount": len(lessons),
        "datasetCount": len(catalog["datasets"]), "datasetFieldCount": sum(len(d["fields"]) for d in catalog["datasets"].values()),
        "usageMatrixRowCount": sum(len(m["rows"]) for m in catalog["usageMatrices"].values()),
        "atl105ReferenceCount": len(crossrefs["references"]),
        "openQuestionCount": sum(q["status"] == "REVIEW_REQUIRED" for q in config["openQuestions"]),
        "qeInterpretationCount": sum(q["status"] == "QE_INTERPRETATION_PENDING_SME" for q in config["openQuestions"]),
        "draftBusinessRequirementCount": len(brs),
        "validatorRuleCount": len(VALIDATOR_RULES),
        "extractor": {"name": "pypdf", "version": importlib.metadata.version("pypdf")},
        "inputSha256": text_fingerprint(INPUT),
        "planSha256": text_fingerprint(PLAN),
        "businessRequirementsInputSha256": text_fingerprint(BR_INPUT),
        "generatorSha256": text_fingerprint(Path(__file__)),
        "outputs": {name: fingerprint(text.encode("utf-8")) for name, text in outputs.items()},
        "limitations": [
            "Text extraction does not prove table column alignment; every dataset and usage row is re-matched against page text instead.",
            "Only curated, quoted statements enter the draft lesson list; QE interpretations remain pending SME confirmation.",
            "The InComm ISO 8583 Generic Interface Specification v6.2.3 is not ingested.",
            "Real-time versus non-real-time scope (INCOMM-MBD-2023-1-Q01) is unresolved; OF, OC and non-real-time fields are reported, not rejected.",
            "Only draft BRs are derived; by user decision no test scenarios, test cases or test data are created.",
            "Draft BRs are excluded from independent Test Solution counts until SME approval; no execution gate is promoted.",
        ],
    })
    return outputs


def check_outputs(outputs: dict[str, str], folder: Path) -> None:
    stale = [name for name, expected in outputs.items()
             if not (folder / name).is_file() or (folder / name).read_text(encoding="utf-8") != expected]
    if stale:
        raise ValueError("Missing or stale InComm Market Basket training outputs: " + ", ".join(stale))


def search_pages(query: str, pages: list[dict], limit: int = 5) -> list[dict]:
    terms = set(re.findall(r"[a-z0-9]+", query.lower()))
    require(bool(terms), "InComm Market Basket query must include a word or number")
    ranked = []
    for page in pages:
        text = page["text"]
        found = re.findall(r"[a-z0-9]+", text.lower())
        if terms <= set(found):
            score = sum(found.count(t) for t in terms)
            position = min(text.lower().find(t) for t in terms)
            ranked.append((score, page["pdfPage"], {
                "pdfPage": page["pdfPage"], "printedPage": page["printedPage"], "pageId": page["id"],
                "textSha256": page["textSha256"], "excerpt": text[max(0, position - 120):position + 1000],
                "status": "RETRIEVAL_CANDIDATE_NOT_VALIDATED_RULE"}))
    return [item for _, _, item in sorted(ranked, key=lambda x: (-x[0], x[1]))[:limit]]


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    action = parser.add_mutually_exclusive_group()
    action.add_argument("--check", action="store_true", help="Re-extract and compare all evidence; writes nothing")
    action.add_argument("--query", help="Read-only lexical search over verified page evidence")
    action.add_argument("--validate", help="Validate a Market Basket Data string with the draft validator")
    parser.add_argument("--direction", choices=("request", "response"), default="request")
    parser.add_argument("--txn", choices=TRANSACTION_TYPES)
    parser.add_argument("--request", help="Original request Market Basket Data for response echo checks")
    args = parser.parse_args()
    if args.validate is not None and args.txn is None:
        parser.error("--validate requires --txn")
    outputs = build()
    if args.check or args.query is not None or args.validate is not None:
        check_outputs(outputs, KB)
    else:
        for name, text in outputs.items():
            (KB / name).write_text(text, encoding="utf-8", newline="\n")
        check_outputs(outputs, KB)
    if args.query is not None:
        print(json_text({"query": args.query, "results": search_pages(args.query, json.loads(outputs["pages.json"])["pages"])}))
    elif args.validate is not None:
        catalog = json.loads(outputs["dataset-catalog.json"])
        print(json_text(validate_market_basket(args.validate, args.direction, args.txn, catalog, args.request)))
    else:
        m = json.loads(outputs["ingestion-manifest.json"])
        print(f"InComm Market Basket 2023-1: {m['pageCount']} pages; {m['chapterCount']} chapters; "
              f"{m['draftLessonCount']} draft lessons; {m['datasetFieldCount']} dataset fields; "
              f"{m['usageMatrixRowCount']} usage rows; {m['atl105ReferenceCount']} ATL105 references; "
              f"{m['draftBusinessRequirementCount']} draft BRs; {m['validatorRuleCount']} validator rules; "
              f"{m['openQuestionCount']} open questions; {'verified (read-only)' if args.check else 'built and verified'}. "
              "No TS/TC/TD created (user decision); no BR promoted or execution gate certified.")


if __name__ == "__main__":
    main()
