"""Verify that the EMV and Chip Implementation Guide was ingested and trained faithfully.

ATL105 Section 10.14 requires the "First Data EMV Implementation Guide", now published as the Fiserv EMV and Chip
Implementation Guide. Its training record ("memory") is
specifications/ATL105/docs/test-validation-strategy/ATL105-EMV-Implementation-Guide-Training-Knowledge.md.
This script checks that record in two layers.

Record checks (always run; no PDF needed):
* rule, scenario, SME query, gap disposition and backlog counts equal the pinned expectations;
* every scenario cites existing rules, and every rule that is not informational has a scenario;
* internal anchors and relative links in the record resolve;
* the strategy, strategy README, handbook, KB index and Appendix R/S/Segment 130 notes link the record;
* Section 2 acknowledges every part of the guide that exists only as an image.

Source checks (need the PDF in docs/specs/ and pypdf; the PDF is kept locally and never committed, because this
repository is public):
* SHA-256 and page count equal the values bound in the record;
* every RQ in the guide body is covered, and every RQ page citation points at a page that holds the RQ;
* Tables 7, 10, 14/15, 25 and 28, the AID lists (both directions) and the EBT field list match the guide;
* every quotation of 18 characters or more is verbatim in the guide, in ATL105 or in an allowed internal source.

Executable-training status (a referenceTraining entry and an EMV source oracle) is reported as INFO only.

Exit codes: 0 every check passed; 1 a check failed; 2 the PDF or pypdf is unavailable, so the source checks were
NOT_ASSESSED (the record checks ran and passed).

Usage, from the validator module:  python scripts/verify-emv-guide-ingestion.py [--pdf PATH] [--json OUT]
"""
import argparse
import hashlib
import io
import json
import logging
import os
import re
import sys
import unicodedata
from collections import Counter
from pathlib import Path
from urllib.parse import unquote

HERE = Path(__file__).resolve().parent
MODULE = HERE.parent
PACK = MODULE / "specifications" / "ATL105"
STRATEGY_DIR = PACK / "docs" / "test-validation-strategy"
SPECS = PACK / "docs" / "specs"
KB = SPECS / "kb"
RECORD = STRATEGY_DIR / "ATL105-EMV-Implementation-Guide-Training-Knowledge.md"
PDF = SPECS / "Fiserv_Generic_EMV_Implementation_Guide_v2025_RG20_081225.pdf"
ATL105_TEXTS = (SPECS / "extracted_text_new.txt", SPECS / "extracted_text.txt")
# Our own documents that the record may quote. The strategy and the other linking documents summarise the record,
# so they are deliberately excluded: a paraphrase copied there must not verify itself.
INTERNAL_QUOTE_SOURCES = (KB / "COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md", KB / "chapter-coverage-gap-register.md",
                          KB / "13-data-elements.md", KB / "segment-130" / "emv-entry-mode-fallback-sme-tba-note.md")
LINKING_DOCS = {
    "strategy": STRATEGY_DIR / "Core-Auth-Regression-Test-Validation-Strategy.md",
    "strategy README": STRATEGY_DIR / "README.md",
    "handbook": KB / "COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md",
    "KB index": KB / "00-index.md",
    "Appendix R note": KB / "appendix-r" / "README.md",
    "Appendix S note": KB / "appendix-s" / "README.md",
    "Segment 130 note": KB / "segment-130" / "README.md",
}
TRAINING_STATUS = PACK / "training-status.json"
ORACLE_DIR = MODULE / "src" / "main" / "resources" / "atl105"

EXPECTED_PAGES = 212
EXPECTED_BODY_RQS = 92
EXPECTED_RULES = {"TAG": 14, "XF": 11, "LC": 17, "AID": 10, "FB": 7, "RSP": 6, "CAPK": 8}
EXPECTED_SCENARIOS = 72
EXPECTED_IDS = {"Q-EMV": 11, "GAP-EMV": 17, "B": 11}
IMAGE_ONLY = ("pp.15-22", "p.30", "pp.176-177", "p.180")
READINESS = ("EXECUTABLE", "REVIEW_REQUIRED", "EXTERNAL_FIXTURE_REQUIRED")
BODY_PAGES = range(14, 201)  # PDF pp.15-201; the revision history (pp.3-14 and 202-212) is excluded
AID_PAGES = slice(40, 181)  # PDF pp.41-181: Chapter 4 to the end of Chapter 14
MIN_QUOTE = 18
MIN_FRAGMENT = 12

FAMILIES = "|".join(EXPECTED_RULES)
RQ = re.compile(r"\bRQ\s*(\d{4})\b")
RECORD_RQ = re.compile(r"RQ\s?(\d{4})")
RQ_RANGE = re.compile(r"RQ\s?(\d{4})\s*(?:-|to|\u2013)\s*RQ\s?(\d{4})")
INLINE_CITATION = re.compile(
    r"((?:RQ\s?\d{4}(?:\s*(?:,|/|and)\s*)?)+)\s*\((?:p|pp)\.\s*(\d{1,3}(?:\s*[-,]\s*\d{1,3})*)\)")
AID = re.compile(r"\bA0[0-9A-F]{10,22}\b")
TABLE_25_ROW = re.compile(r"(?:^|\n)\s*([0-9A-F]{2}(?:[0-9A-F]{2})?)\*?1?\s+(.+?)\s+(B|CT|CTLS)\s+([YN])\s+([YN])\s+"
                          r"([YN])\s+([YN])\s+(Chip|Issuer|Terminal)\b", re.S)
RUNNING_LINE = re.compile(r"^[ \t]*(?:EMV and Chip Implementation Guide\b.*|v2025\.RG20\b.*|Fiserv Public)[ \t]*$", re.M)
LINK = re.compile(r"\]\((<[^>]+>|[^)\s]+)\)")

TABLE_7 = (("approved 00/000", "0x3030"), ("partial approval 10/002", "0x3130"), ("declined 05/51/51x", "0x3035"),
           ("offline approved y1", "0x5931"), ("offline declined z1", "0x5a31"), ("y3", "0x5933"), ("z3", "0x5a33"))
TABLE_28 = (("goods and services", "00"), ("withdrawal/cash advance", "01"), ("purchase with cash back", "09"),
            ("return/refund", "20"), ("balance inquiry", "31"), ("force post", "45"))
TABLE_15 = ("ca_keys", "0100", "mmddyyyy", "010001", "max 496", "hex 40", "sha-1")
TABLE_10 = (("pin pad unavailable", "1st generate ac"), ("transaction cancelled", "1st generate ac"),
            ("card removed", "1st generate ac"), ("chip error", "1st generate ac"), ("card decline", "2nd generate ac"))
EBT_TAGS = r"9f26|9f27|9f10|9f34|9f33|9f37|9f36|95|9a|9c|9f02|5f2a|82|84|9f1a|9f03|5f34|9f6e|9f1e"


def long_path(path):
    """Windows extended-length form; several repository paths exceed 260 characters (handbook L25)."""
    resolved = os.path.abspath(str(path))
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        return "\\\\?\\" + resolved
    return resolved


def exists(path):
    return os.path.exists(long_path(path))


def read_bytes(path):
    with open(long_path(path), "rb") as handle:
        return handle.read()


def read_text(path, errors="strict"):
    with open(long_path(path), encoding="utf-8-sig", errors=errors) as handle:
        return handle.read()


def read_atl105():
    return "\n".join(read_text(path, errors="replace") for path in ATL105_TEXTS if exists(path))


def read_internal_sources():
    return "\n".join(read_text(path) for path in INTERNAL_QUOTE_SOURCES if exists(path))


def read_linking_docs():
    return {name: read_text(path) if exists(path) else "" for name, path in LINKING_DOCS.items()}


def load_pdf(path=PDF):
    """Return ((page texts, SHA-256), None), or (None, reason) when the PDF or pypdf is unavailable."""
    if not exists(path):
        return None, f"PDF not found at {path}; it is kept locally and never committed"
    try:
        from pypdf import PdfReader
    except ImportError:
        return None, "pypdf is not installed (python -m pip install pypdf)"
    data = read_bytes(path)
    logging.getLogger("pypdf").setLevel(logging.ERROR)
    pages = [page.extract_text() or "" for page in PdfReader(io.BytesIO(data)).pages]
    return (pages, hashlib.sha256(data).hexdigest().upper()), None


def result(group, check, passed, detail=""):
    status = "INFO" if passed is None else ("PASS" if passed else "FAIL")
    return {"group": group, "check": check, "status": status, "detail": detail}


def norm(text):
    text = unicodedata.normalize("NFKC", text)
    for old, new in (("\u201c", '"'), ("\u201d", '"'), ("\u2018", "'"), ("\u2019", "'"), ("\u2013", "-"),
                     ("\u2014", "-"), ("\u2022", " "), ("\u25aa", " "), ("\u27a2", " ")):
        text = text.replace(old, new)
    return re.sub(r"\s+", " ", text).strip().lower()


def section(text, start, end):
    """Text after the line that starts with `start`, up to the next line that starts with `end`."""
    head = re.search(rf"^{re.escape(start)}", text, re.M)
    if not head:
        return ""
    rest = text[head.end():]
    tail = re.search(rf"^{re.escape(end)}", rest, re.M)
    return rest[:tail.start()] if tail else rest


def table_rows(text):
    for line in text.splitlines():
        if line.startswith("|") and not re.match(r"^\|\s*:?-", line):
            yield [cell.strip() for cell in line.strip().strip("|").split("|")]


def rule_rows(record):
    """(rule id without the EMVIG- prefix, informational) for each rule row, in document order."""
    rows = []
    for cells in table_rows(record):
        match = re.fullmatch(rf"EMVIG-((?:{FAMILIES})-\d{{3}})", cells[0])
        if match:
            rows.append((match.group(1), cells[-1].upper().startswith("INFO")))
    return rows


def scenario_rows(record):
    return [cells for cells in table_rows(section(record, "## 14.", "## 15."))
            if re.fullmatch(r"EMV-TS-\d{3}", cells[0]) and len(cells) >= 5]


def rule_refs(cell):
    """Rule ids in a scenario's Rules cell; shorthand such as "TAG-001, -006" expands to TAG-001 and TAG-006."""
    refs, family = set(), None
    for token in re.split(r"[;,]", cell):
        full = re.findall(rf"\b({FAMILIES})-(\d{{3}})\b", token)
        if full:
            refs.update(f"{fam}-{num}" for fam, num in full)
            family = full[-1][0]
            continue
        short = re.match(r"^\s*-(\d{3})\b", token)
        if short and family:
            refs.add(f"{family}-{short.group(1)}")
    return refs


def slug(heading):
    return re.sub(r"[^\w\- ]", "", heading.strip().lower()).replace(" ", "-")


def anchors(text):
    """GitHub heading anchors (with -1, -2 suffixes for repeats) plus explicit HTML anchors."""
    found, seen = set(), Counter()
    for match in re.finditer(r"^#{1,6}[ \t]+(.+?)[ \t]*#*[ \t]*$", text, re.M):
        base = slug(match.group(1))
        found.add(base if not seen[base] else f"{base}-{seen[base]}")
        seen[base] += 1
    found.update(re.findall(r'<a\s+(?:id|name)="([^"]+)"', text))
    return found


def broken_links(record, base):
    own, others, broken = anchors(record), {}, []
    for raw in LINK.findall(record):
        target = unquote(raw.strip("<>"))
        if re.match(r"^[a-z][a-z0-9+.-]*:", target, re.I):
            continue
        path, _, anchor = target.partition("#")
        if not path:
            if anchor not in own:
                broken.append(f"#{anchor}")
            continue
        full = os.path.normpath(os.path.join(str(base), path))
        if not exists(full):
            broken.append(f"{path} (file missing)")
            continue
        if anchor and full.lower().endswith(".md"):
            if full not in others:
                others[full] = anchors(read_text(full, errors="replace"))
            if anchor not in others[full]:
                broken.append(f"{path}#{anchor}")
    return sorted(set(broken))


def check_record(record, linked, base=STRATEGY_DIR):
    """Checks that need only the training record and the documents that should link it."""
    out = []
    rules = rule_rows(record)
    ids = [rule for rule, _ in rules]
    counts = {family: sum(1 for rule in set(ids) if rule.split("-")[0] == family) for family in EXPECTED_RULES}
    duplicates = sorted(rule for rule, count in Counter(ids).items() if count > 1)
    out.append(result("Record", f"Rule counts per family equal {json.dumps(EXPECTED_RULES)}, with no duplicates",
                      counts == EXPECTED_RULES and not duplicates,
                      json.dumps(counts) + (f"; duplicates: {', '.join(duplicates)}" if duplicates else "")))

    scenarios = scenario_rows(record)
    found = [cells[0] for cells in scenarios]
    expected = [f"EMV-TS-{n:03d}" for n in range(1, EXPECTED_SCENARIOS + 1)]
    problems = [f"missing {', '.join(sorted(set(expected) - set(found)))}" if set(expected) - set(found) else "",
                f"unexpected {', '.join(sorted(set(found) - set(expected)))}" if set(found) - set(expected) else "",
                f"repeated {', '.join(sorted(s for s, c in Counter(found).items() if c > 1))}"
                if len(found) != len(set(found)) else ""]
    problems = [item for item in problems if item] or (["out of order"] if found != expected else [])
    out.append(result("Record", f"Scenarios EMV-TS-001 to EMV-TS-{EXPECTED_SCENARIOS:03d} each appear once, in order",
                      not problems, "; ".join(problems) or f"{len(found)} scenarios"))
    unready = [cells[0] for cells in scenarios if not cells[4].startswith(READINESS)]
    out.append(result("Record", "Every scenario has a known readiness (" + ", ".join(READINESS) + ")", not unready,
                      ", ".join(unready) or "all known"))

    defined = set(ids)
    informational = {rule for rule, info in rules if info}
    cited = {}
    for cells in scenarios:
        for ref in rule_refs(cells[3]):
            cited.setdefault(ref, []).append(cells[0])
    unknown = sorted(set(cited) - defined)
    out.append(result("Record", "Every rule cited by a scenario exists", not unknown,
                      "; ".join(f"{rule} in {', '.join(cited[rule])}" for rule in unknown) or "all exist"))
    testable = defined - informational
    uncovered = sorted(testable - set(cited))
    out.append(result("Record", f"Every testable rule has a scenario ({len(testable)} testable)", not uncovered,
                      ", ".join(uncovered) or "all covered"))
    out.append(result("Record", "Informational rules (last column starts with INFO; no scenario required)", None,
                      ", ".join(sorted(informational)) or "none"))

    got = {prefix: len({cells[0] for cells in table_rows(record) if re.fullmatch(rf"{prefix}-\d{{2}}", cells[0])})
           for prefix in EXPECTED_IDS}
    out.append(result("Record", f"SME queries, gap dispositions and backlog items equal {json.dumps(EXPECTED_IDS)}",
                      got == EXPECTED_IDS, json.dumps(got)))

    broken = broken_links(record, base)
    out.append(result("Record", "Internal anchors and relative links in the record resolve", not broken,
                      "; ".join(broken) or "all resolve"))
    unlinked = [name for name in LINKING_DOCS if RECORD.name not in linked.get(name, "")]
    out.append(result("Record", f"The record is linked from the {len(LINKING_DOCS)} integration documents",
                      not unlinked, ", ".join(unlinked) or "all link it"))
    binding = section(record, "## 2.", "## 3.")
    missing = [pages for pages in IMAGE_ONLY if pages not in binding]
    out.append(result("Record", "Section 2 acknowledges every image-only part of the guide (" + ", ".join(IMAGE_ONLY)
                      + ")", not missing, f"missing: {', '.join(missing)}" if missing else "all acknowledged"))
    return out


def cited_pages(cell):
    """Pages from an RQ index cell such as "4.3 / 47" or "4.6.2 / 50-51; 5.4 / 64"."""
    pages = set()
    for segment in cell.split(";"):
        if "/" not in segment:
            continue
        for part in segment.split("/", 1)[1].split(","):
            numbers = [int(n) for n in re.findall(r"\d+", part)]
            if len(numbers) == 2:
                pages.update(range(numbers[0], numbers[1] + 1))
            elif numbers:
                pages.add(numbers[0])
    return pages


def page_list(text):
    pages = set()
    for part in text.split(","):
        numbers = [int(n) for n in re.findall(r"\d+", part)]
        if len(numbers) == 2:
            pages.update(range(numbers[0], numbers[1] + 1))
        elif numbers:
            pages.add(numbers[0])
    return pages


def on_page(pages, rq, number):
    return 1 <= number <= len(pages) and re.search(rf"\bRQ\s*{rq}\b", pages[number - 1]) is not None


def paired_quotes(text):
    quotes = []
    for line in text.splitlines():
        marks = [i for i, ch in enumerate(line) if ch == '"']
        quotes.extend(line[a + 1:b] for a, b in zip(marks[0::2], marks[1::2]) if b - a - 1 >= MIN_QUOTE)
    return quotes


def check_source(pages, sha, record, atl105_text, internal_text):
    """Checks that compare the record with the guide PDF text and the ATL105 extraction."""
    out = []
    bound = re.search(r"SHA-256 \| `([0-9A-F]{64})`", record)
    out.append(result("Integrity", "PDF SHA-256 equals the value bound in the record",
                      bool(bound) and bound.group(1) == sha.upper(),
                      f"PDF {sha.upper()}; record {bound.group(1) if bound else 'none'}"))
    out.append(result("Integrity", f"Page count is {EXPECTED_PAGES} and the record states it",
                      len(pages) == EXPECTED_PAGES and f"{EXPECTED_PAGES} pages" in record, f"{len(pages)} pages"))
    title = pages[0] if pages else ""
    out.append(result("Integrity", "Title page states Version 2025.RG20, August 12, 2025",
                      "2025.RG20" in title and "August 12, 2025" in title, " ".join(title.split())[:90]))

    body = {}
    for index in BODY_PAGES:
        if index < len(pages):
            for match in RQ.finditer(pages[index]):
                body.setdefault(match.group(1), set()).add(index + 1)
    everywhere = {match.group(1) for page in pages for match in RQ.finditer(page)}
    cited = set(RECORD_RQ.findall(record))
    covered = set(cited)
    for low, high in RQ_RANGE.findall(record):
        covered |= {rq for rq in body if int(low) <= int(rq) <= int(high)}
    missing = sorted(set(body) - covered)
    out.append(result("RQ coverage", f"Every RQ in the guide body (pp.15-201) is covered ({len(body)} found, "
                      f"{EXPECTED_BODY_RQS} expected)", not missing and len(body) == EXPECTED_BODY_RQS,
                      "missing: " + ", ".join(f"RQ{rq} (p.{min(body[rq])})" for rq in missing) if missing
                      else "all covered"))
    unknown = sorted(cited - everywhere)
    out.append(result("RQ coverage", "Every RQ cited in the record exists in the guide", not unknown,
                      ", ".join(f"RQ{rq}" for rq in unknown) or "all exist"))

    wrong, checked = [], 0
    for cells in table_rows(section(record, "## 13.", "## 14.")):
        if len(cells) < 3 or not re.match(r"^\*?RQ", cells[0]) or "removed" in cells[0].lower():
            continue
        rqs = set(RECORD_RQ.findall(cells[0]))
        for low, high in re.findall(r"RQ\s?(\d{4})\s*-\s*RQ\s?(\d{4})", cells[0]):
            rqs |= {rq for rq in body if int(low) <= int(rq) <= int(high)}
        pages_cited = cited_pages(cells[2])
        for rq in sorted(rqs):
            checked += 1
            if not any(on_page(pages, rq, number) for number in pages_cited):
                wrong.append(f"RQ{rq} cites p.{sorted(pages_cited)}, found on p.{sorted(body.get(rq, []))}")
    out.append(result("RQ coverage", f"RQ index page citations point at a page holding the RQ ({checked} checked)",
                      not wrong and checked > 0, "; ".join(wrong) or "all correct"))

    wrong, checked = [], 0
    for group, cited_text in INLINE_CITATION.findall(record):
        numbers = page_list(cited_text)
        for rq in re.findall(r"\d{4}", group):
            checked += 1
            if not any(on_page(pages, rq, number) for number in numbers):
                wrong.append(f"RQ{rq} (p.{cited_text}) found on p.{sorted(body.get(rq, []))}")
    out.append(result("RQ coverage", f"Inline RQ page citations are correct ({checked} checked)", not wrong,
                      "; ".join(wrong) or "all correct"))

    source_25 = {m.group(1): m.groups()[2:8] for m in TABLE_25_ROW.finditer(" ".join(pages[117:119]))}
    record_25 = {}
    for cells in table_rows(section(record, "### 6.2", "### 6.3")):
        if len(cells) >= 8 and re.fullmatch(r"[0-9A-F]{2,4}", cells[0]):
            record_25[cells[0]] = (cells[2], cells[3], cells[4], cells[5], cells[6], cells[7].split(" ")[0])
    diff = [f"{tag}: guide {source_25.get(tag)}, record {record_25.get(tag)}"
            for tag in sorted(set(source_25) | set(record_25)) if source_25.get(tag) != record_25.get(tag)]
    out.append(result("Tables", f"Table 25 tag matrix matches (guide rows {len(source_25)}, record rows "
                      f"{len(record_25)})", not diff and len(source_25) > 0, "; ".join(diff) or "identical"))

    guide_aids = set(AID.findall(" ".join(pages[AID_PAGES])))
    record_aids = set(AID.findall(record))
    unsourced = sorted(aid for aid in record_aids if aid not in guide_aids and aid not in set(AID.findall(atl105_text)))
    out.append(result("Tables", f"Every AID in the record exists in the guide or ATL105 ({len(record_aids)} AIDs)",
                      not unsourced, ", ".join(unsourced) or "all sourced"))
    absent = sorted(guide_aids - record_aids)
    out.append(result("Tables", f"Every AID in the guide body is written in full in the record ({len(guide_aids)} AIDs)",
                      not absent and len(guide_aids) > 0, ", ".join(absent) or "all present"))

    pages_7 = norm(pages[54]) + " " + norm(pages[56]) if len(pages) > 56 else ""
    bad_7 = [f"{decision} -> {code}" for decision, code in TABLE_7
             if not re.search(re.escape(decision) + r".{0,40}?" + re.escape(code), pages_7)]
    missing_7 = [code for _, code in TABLE_7 if code not in record.lower()]
    out.append(result("Tables", "Table 7 Tag 8A values match the guide and appear in the record",
                      not bad_7 and not missing_7,
                      "; ".join(filter(None, ["guide: " + ", ".join(bad_7) if bad_7 else "",
                                              "record: " + ", ".join(missing_7) if missing_7 else ""])) or "all 7"))
    page_28 = norm(pages[143]) if len(pages) > 143 else ""
    bad_28 = [f"{meaning}={code}" for meaning, code in TABLE_28
              if not re.search(re.escape(meaning) + r"[^0-9]{0,60}?" + code, page_28)]
    out.append(result("Tables", "Table 28 Tag 9C values used by the record exist in the guide", not bad_28,
                      ", ".join(bad_28) or "all 6"))
    pages_15 = norm(pages[93] + " " + pages[94]) if len(pages) > 94 else ""
    bad_15 = [fact for fact in TABLE_15 if fact not in pages_15]
    out.append(result("Tables", "Table 14/15 CAPK facts used by the record exist on pp.94-95", not bad_15,
                      ", ".join(bad_15) or "all present"))
    page_10 = norm(pages[71]) if len(pages) > 71 else ""
    lifecycle = norm(section(record, "### 8.1", "### 8.2"))
    bad_10 = [f"guide {reason} -> {source}" for reason, source in TABLE_10
              if not re.search(re.escape(reason) + r"\s+" + re.escape(source), page_10)]
    bad_10 += [f"record {reason} -> {source}" for reason, source in TABLE_10
               if not re.search(re.escape(reason) + r" \| " + re.escape(source), lifecycle)]
    out.append(result("Tables", "Table 10 reversal reasons and Generate AC source match the guide and the record",
                      not bad_10, "; ".join(bad_10) or "all 5"))

    ebt_source = norm(pages[178] + " " + pages[179]) if len(pages) > 179 else ""
    ebt = re.findall(rf"({EBT_TAGS})\s+(\d+(?:\s*-\s*\d+)?)\s+(m|c)\b", ebt_source)
    guide_m = {tag.upper() for tag, _, kind in ebt if kind == "m"}
    guide_c = {tag.upper() for tag, _, kind in ebt if kind == "c"}
    row = next((line for line in record.splitlines() if line.startswith("| EMVIG-TAG-014 |")), "")
    record_m = set(re.findall(r"[0-9A-F]{2,4}", row.split("Mandatory:")[1].split("Conditional:")[0])) \
        if "Mandatory:" in row and "Conditional:" in row else set()
    record_c = set(re.findall(r"[0-9A-F]{2,4}", row.split("Conditional:")[1].split("The set")[0])) \
        if "Conditional:" in row else set()
    out.append(result("Tables", f"EBT chip field list matches EMVIG-TAG-014 (guide M {len(guide_m)}, C {len(guide_c)})",
                      bool(guide_m) and guide_m == record_m and guide_c == record_c,
                      f"mandatory differences {sorted(guide_m ^ record_m)}; conditional differences "
                      f"{sorted(guide_c ^ record_c)}"))

    # Running headers and footers are removed so quotations that cross a page break still match; the raw text is
    # kept as well because the record legitimately quotes the footer ("Fiserv - Proprietary").
    sources = (norm(" ".join(RUNNING_LINE.sub("", page) for page in pages)), norm(" ".join(pages)), norm(atl105_text))
    internal_norm = norm(internal_text)
    verbatim, internal, unmatched = 0, [], []
    for quote in paired_quotes(record):
        fragments = [norm(part).strip(" .;:,") for part in re.split(r"\.\.\.|\u2026", quote)]
        fragments = [part for part in fragments if len(part) >= MIN_FRAGMENT]
        if not fragments:
            continue
        if all(any(part in text for text in sources) for part in fragments):
            verbatim += 1
        elif all(part in internal_norm for part in fragments):
            internal.append(quote[:80])
        else:
            unmatched.append(quote[:100])
    out.append(result("Quotations", f"Quotations verbatim in the guide or ATL105: {verbatim}", None, ""))
    out.append(result("Quotations", f"Quotations taken from allowed internal sources: {len(internal)}", None,
                      " | ".join(internal) or "none"))
    out.append(result("Quotations", "Quotations not verbatim in any allowed source (paraphrase or misquote)",
                      not unmatched, " | ".join(unmatched) or "none"))

    anchor = "Refer to First Data EMV Implementation Guide"
    out.append(result("ATL105 anchor", f"ATL105 still states \"{anchor}\" (2026-4 and 2026-3 extractions)",
                      atl105_text.count(anchor) >= len(ATL105_TEXTS), f"{atl105_text.count(anchor)} occurrences"))
    return out


def check_executable(status_text, oracle_names):
    """Executable-training status, reported as INFO: ingestion is verified, execution is still pending."""
    try:
        reference = json.loads(status_text).get("referenceTraining", {})
    except ValueError:
        reference = {}
    entries = sorted(name for name in reference if "emv" in name.lower())
    oracles = sorted(name for name in oracle_names if "emv" in name.lower())
    return [result("Executable training", "training-status.json has an EMV referenceTraining entry", None,
                   ", ".join(entries) or "absent; pending backlog B-11"),
            result("Executable training", "An EMV guide source oracle exists in src/main/resources/atl105", None,
                   ", ".join(oracles) or "absent; pending strategy procedure steps 5-9")]


def run(pdf=PDF):
    results = check_record(read_text(RECORD), read_linking_docs())
    loaded, reason = load_pdf(pdf)
    if loaded:
        pages, digest = loaded
        results += check_source(pages, digest, read_text(RECORD), read_atl105(), read_internal_sources())
    else:
        results.append({"group": "Source", "check": "Source checks against the PDF", "status": "NOT_ASSESSED",
                        "detail": reason})
    status_text = read_text(TRAINING_STATUS) if exists(TRAINING_STATUS) else "{}"
    oracles = os.listdir(long_path(ORACLE_DIR)) if exists(ORACLE_DIR) else []
    results += check_executable(status_text, oracles)
    counts = Counter(item["status"] for item in results)
    code = 1 if counts["FAIL"] else (2 if counts["NOT_ASSESSED"] else 0)
    return results, counts, code


def main(argv=None):
    parser = argparse.ArgumentParser(description="Verify the EMV guide ingestion and training record.")
    parser.add_argument("--pdf", type=Path, default=PDF, help="companion guide PDF (default: the docs/specs copy)")
    parser.add_argument("--json", type=Path, help="also write the results to this JSON file")
    args = parser.parse_args(argv)
    if hasattr(sys.stdout, "reconfigure"):
        sys.stdout.reconfigure(encoding="utf-8")
    results, counts, code = run(args.pdf)
    print("EMV guide ingestion verification")
    print(f"Record: {RECORD.relative_to(MODULE)}")
    print(f"PDF:    {args.pdf}")
    for item in results:
        detail = f" -- {item['detail']}" if item["detail"] else ""
        print(f"[{item['status']}] {item['group']}: {item['check']}{detail}")
    summary = ", ".join(f"{counts[s]} {s}" for s in ("PASS", "FAIL", "INFO", "NOT_ASSESSED") if counts[s])
    print(f"Summary: {summary}; exit code {code}")
    if args.json:
        payload = {"record": str(RECORD.relative_to(MODULE)), "pdf": str(args.pdf), "results": results,
                   "summary": dict(counts), "exitCode": code}
        args.json.write_text(json.dumps(payload, indent=2) + "\n", encoding="utf-8")
    return code


if __name__ == "__main__":
    sys.exit(main())
