"""Verify that the Secure Transport documents were ingested, split and trained faithfully.

ATL105 2026-4 Section 3.3 (Telecommunications Requirements, PDF p.49, printed p.3-2; identical in 2026-3) refers to
"the Secure Transport section on the First Data Web site" for Internet support information. The 13 Secure Transport
files supplied on 2026-10-28 are split chapter/phase-wise by scripts/build-secure-transport-ingestion.py. Their
training record ("memory") is
specifications/ATL105/docs/test-validation-strategy/SecureTransport-Training-Knowledge.md.

Record checks (always run; they need no confidential source):
* the tracked manifest pins 13 documents, 292 PDF pages and 107 chapters/phases, and every PDF's chapters tile its
  pages in order;
* the record binds every document with the manifest SHA-256 and maps every chapter/phase with its page range;
* the rule catalog (specifications/ATL105/docs/specs/kb/secure-transport/secure-transport-rule-catalog.md) holds the
  pinned rule counts, and every rule cites an existing document location; the record states the catalog's total;
* scenario, conflict, SME query, gap and backlog counts equal the pinned expectations; every scenario cites existing
  rules; every rule that is not INFO has a scenario;
* the ATL105 Section 3.3 anchor is on the pinned lines of both ATL105 extracts;
* links and anchors in the record resolve, and the integration documents link the record;
* the confidential sources are git-ignored, and training-status.json records the reference training.

Source checks (need the local, git-ignored split in docs/specs/secure-transport/; it is never committed because the
sources are First Data/Fiserv confidential and this repository is public):
* every stored file has its pinned SHA-256, and every extracted text has its manifest SHA-256;
* every PDF re-extracts (pypdf) to the stored page-marked text;
* every chapter file's verbatim block has its manifest SHA-256, and the chapters concatenate to the full text, so no
  page, line or character is missing from the split;
* every quotation of 18 characters or more in the record and the catalog is verbatim (ignoring white space) in a
  source or in ATL105, and every quotation in a rule is on the page, phase or lines that the rule cites;
* every rendered page image listed in the manifest exists.

Exit codes: 0 every check passed; 1 a check failed; 2 the local split is unavailable, so the source checks were
NOT_ASSESSED (the record checks ran and passed).

Usage, from the validator module:  python scripts/verify-secure-transport-ingestion.py [--local DIR] [--json OUT]
"""
import argparse
import hashlib
import json
import os
import re
import sys
import unicodedata
from collections import Counter
from pathlib import Path
from urllib.parse import unquote

HERE = Path(__file__).resolve().parent
MODULE = HERE.parent
FRAMEWORK = MODULE.parent
PACK = MODULE / "specifications" / "ATL105"
STRATEGY_DIR = PACK / "docs" / "test-validation-strategy"
SPECS = PACK / "docs" / "specs"
KB = SPECS / "kb"
LOCAL = SPECS / "secure-transport"
RECORD = STRATEGY_DIR / "SecureTransport-Training-Knowledge.md"
MANIFEST = KB / "secure-transport" / "secure-transport-source-manifest.json"
KB_NOTE = KB / "secure-transport" / "README.md"
CATALOG = KB / "secure-transport" / "secure-transport-rule-catalog.md"
GITIGNORE = FRAMEWORK / ".gitignore"
TRAINING_STATUS = PACK / "training-status.json"
ATL105_TEXTS = {"2026-4": SPECS / "extracted_text_new.txt", "2026-3": SPECS / "extracted_text.txt"}
LINKING_DOCS = {
    "strategy": STRATEGY_DIR / "Core-Auth-Regression-Test-Validation-Strategy.md",
    "strategy README": STRATEGY_DIR / "README.md",
    "handbook": KB / "COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md",
    "KB index": KB / "00-index.md",
    "KB note": KB_NOTE,
    "scripts README": HERE / "README.md",
}
IGNORE_RULES = ("/regression-artifact-validator/specifications/ATL105/docs/specs/secure-transport/",
                "/SecureTransport-UnixSrc-API.pdf", "/SecureTransport-XML-API.pdf", "/Secure_Transport_Guide/")
ATL105_FRAGMENTS = ("please refer to the Secure Transport section on", "secure-transport-api")

EXPECTED_DOCUMENTS = 13
EXPECTED_PDF_PAGES = 292
EXPECTED_CHAPTERS = 107
EXPECTED_RULES = {"CON": 53, "TLS": 42, "REG": 80, "DSC": 55, "ENV": 127, "SES": 49, "HTTP": 57, "TMO": 40,
                  "ERR": 127, "CRT": 57}
EXPECTED_SCENARIOS = 125
EXPECTED_IDS = {"CF-ST": 104, "Q-ST": 19, "GAP-ST": 9, "B-ST": 8}
STRENGTHS = ("MUST", "SHOULD", "MAY", "INFO")
SCOPES = ("ALL", "GENERIC", "RAPID_CONNECT")
READINESS = ("ARTIFACT_REVIEW", "CERTIFICATION_ENVIRONMENT", "SME_CONFIRMATION")
MIN_QUOTE = 18

FAMILIES = "|".join(EXPECTED_RULES)
RULE_ID = re.compile(rf"\bST-(?:{FAMILIES})-\d{{3}}\b")
LINK = re.compile(r"\]\((<[^>]+>|[^)\s]+)\)")
PAGE_MARK = re.compile(r"^--- PAGE (\d+) ---$", re.M)
BEGIN, END = "<!-- BEGIN VERBATIM -->", "<!-- END VERBATIM -->"


def long_path(path):
    """Windows extended-length form; the local split sits close to the 260-character limit."""
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
    with open(long_path(path), encoding="utf-8-sig", errors=errors, newline="") as handle:
        return handle.read().replace("\r\n", "\n")


def sha256(data):
    return hashlib.sha256(data if isinstance(data, bytes) else data.encode("utf-8")).hexdigest().upper()


def result(group, check, passed, detail=""):
    status = "INFO" if passed is None else ("PASS" if passed else "FAIL")
    return {"group": group, "check": check, "status": status, "detail": detail}


def squash(text):
    """Comparison form for quotations: NFKC, straight quotes and dashes, no white space, case kept."""
    text = unicodedata.normalize("NFKC", text)
    for old, new in (("\u201c", '"'), ("\u201d", '"'), ("\u2018", "'"), ("\u2019", "'"), ("\u2013", "-"),
                     ("\u2014", "-"), ("\u00ad", "")):
        text = text.replace(old, new)
    return re.sub(r"\s+", "", text)


def section(text, number):
    """Body of the "## <number>." section, up to the next level-2 heading."""
    head = re.search(rf"^## {number}\. .*$", text, re.M)
    if not head:
        return ""
    rest = text[head.end():]
    tail = re.search(r"^## ", rest, re.M)
    return rest[:tail.start()] if tail else rest


def table_rows(text):
    for line in text.splitlines():
        if line.startswith("|") and not re.match(r"^\|\s*:?-", line):
            yield [cell.strip() for cell in re.split(r"(?<!\\)\|", line.strip().strip("|"))]


def without_code(text):
    text = re.sub(r"^(`{3,}|~{3,}).*?^\1[ \t]*$", "", text, flags=re.M | re.S)
    return re.sub(r"`[^`\n]*`", "", text)


def quotes(text):
    """Quotations of MIN_QUOTE+ characters outside code; a table-escaped pipe (\\|) is the source's plain pipe."""
    found = []
    for line in without_code(text).replace("\\|", "|").splitlines():
        found += [q for q in re.findall(r'"([^"\n]+)"', line) if len(q) >= MIN_QUOTE]
        found += [q for q in re.findall(r"\u201c([^\u201d\n]+)\u201d", line) if len(q) >= MIN_QUOTE]
    return found


def citation_pattern(doc_ids):
    ids = "|".join(re.escape(doc) for doc in sorted(doc_ids, key=len, reverse=True))
    return re.compile(rf"\b({ids}) (?:(pp?)\.\s?(\d+)(?:-(\d+))?|\u00a7(\d{{2}}(?:-\d{{2}})?)|"
                      rf"(lines?) (\d+)(?:-(\d+))?)")


def citations(cell, pattern):
    """(document, kind, first, last) for every citation in a cell; kind is PAGE, PHASE or LINE."""
    out = []
    for match in pattern.finditer(cell):
        doc = match.group(1)
        if match.group(2):
            first = int(match.group(3))
            out.append((doc, "PAGE", first, int(match.group(4) or first)))
        elif match.group(5):
            out.append((doc, "PHASE", match.group(5), match.group(5)))
        else:
            first = int(match.group(7))
            out.append((doc, "LINE", first, int(match.group(8) or first)))
    return out


def rule_rows(catalog):
    """Every rule row of the rule catalog: Rule | Statement | Strength | Scope | Source."""
    return [cells for cells in table_rows(catalog) if RULE_ID.fullmatch(cells[0]) and len(cells) >= 5]


def scenario_rows(record):
    return [cells for cells in table_rows(section(record, 9))
            if re.fullmatch(r"ST-TS-\d{3}", cells[0]) and len(cells) >= 5]


def slug(heading):
    return re.sub(r"[^\w\- ]", "", heading.strip().lower()).replace(" ", "-")


def anchors(text):
    found, seen = set(), Counter()
    for match in re.finditer(r"^#{1,6}[ \t]+(.+?)[ \t]*#*[ \t]*$", text, re.M):
        base = slug(match.group(1))
        found.add(base if not seen[base] else f"{base}-{seen[base]}")
        seen[base] += 1
    found.update(re.findall(r'<a\s+(?:id|name)="([^"]+)"', text))
    return found


def broken_links(text, base):
    own, others, broken = anchors(text), {}, []
    for raw in LINK.findall(without_code(text)):
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
            others.setdefault(full, anchors(read_text(full, errors="replace")))
            if anchor not in others[full]:
                broken.append(f"{path}#{anchor}")
    return sorted(set(broken))


def ids_in(record, prefix):
    numbers = [int(n) for n in re.findall(rf"^\|\s*{re.escape(prefix)}-(\d{{2,3}})\s*\|", record, re.M)]
    return numbers


def read_linking_docs():
    return {name: read_text(path) if exists(path) else "" for name, path in LINKING_DOCS.items()}


def read_atl105():
    return {version: read_text(path, errors="replace") if exists(path) else "" for version, path in ATL105_TEXTS.items()}


def load_manifest(path=MANIFEST):
    return json.loads(read_text(path))


def check_manifest(manifest):
    out = []
    documents = manifest.get("documents", [])
    totals = manifest.get("totals", {})
    out.append(result("Manifest", f"{EXPECTED_DOCUMENTS} documents, {EXPECTED_PDF_PAGES} PDF pages and "
                      f"{EXPECTED_CHAPTERS} chapters/phases",
                      (len(documents), sum(d.get("pages", 0) for d in documents),
                       sum(len(d["chapters"]) for d in documents)) ==
                      (EXPECTED_DOCUMENTS, EXPECTED_PDF_PAGES, EXPECTED_CHAPTERS) and
                      (totals.get("documents"), totals.get("pdfPages"), totals.get("chapters")) ==
                      (EXPECTED_DOCUMENTS, EXPECTED_PDF_PAGES, EXPECTED_CHAPTERS),
                      f"{len(documents)} documents, {totals.get('pdfPages')} pages, {totals.get('chapters')} chapters"))
    gaps = []
    for doc in documents:
        if doc["format"] != "PDF":
            continue
        chapters = doc["chapters"]
        if chapters[0].get("startPage") != 1 or chapters[-1].get("endPage") != doc["pages"]:
            gaps.append(f"{doc['id']} does not run from page 1 to {doc['pages']}")
        for left, right in zip(chapters, chapters[1:]):
            if right["startPage"] not in (left["endPage"], left["endPage"] + 1):
                gaps.append(f"{doc['id']} {left['seq']}->{right['seq']}")
    out.append(result("Manifest", "Every PDF's chapters tile its pages in order, with no gap", not gaps,
                      "; ".join(gaps)))
    bad_hash = [doc["id"] for doc in documents if not re.fullmatch(r"[0-9A-F]{64}", doc.get("sha256", ""))]
    out.append(result("Manifest", "Every document has a SHA-256", not bad_hash, ", ".join(bad_hash)))
    out.append(result("Manifest", "The sources are declared local-only (never committed)",
                      manifest.get("storagePolicy", {}).get("committed") is False))
    return out


def check_record(record, catalog, linked, manifest, gitignore, atl105, status_text):
    """Checks that need only tracked files: the record, the rule catalog, the manifest and the linking documents."""
    out = check_manifest(manifest)
    documents = {doc["id"]: doc for doc in manifest["documents"]}
    pattern = citation_pattern(documents)

    binding = section(record, 2)
    unbound = [doc_id for doc_id, doc in documents.items()
               if not re.search(rf"^\|\s*{re.escape(doc_id)}\s*\|.*{doc['sha256']}", binding, re.M)]
    out.append(result("Record", "Section 2 binds every document with its manifest SHA-256", not unbound,
                      ", ".join(unbound)))

    mapped = Counter()
    wrong_pages = []
    for cells in table_rows(section(record, 4)):
        match = re.fullmatch(r"(ST-[A-Z0-9-]+?) (\d{2}(?:-\d{2})?)", cells[0])
        if not match or match.group(1) not in documents:
            continue
        mapped[(match.group(1), match.group(2))] += 1
        chapter = next((c for c in documents[match.group(1)]["chapters"] if c["seq"] == match.group(2)), None)
        if chapter and "startPage" in chapter and len(cells) > 2:
            expected = f"{chapter['startPage']}-{chapter['endPage']}"
            if cells[2] != expected:
                wrong_pages.append(f"{cells[0]} says {cells[2]}, manifest {expected}")
    expected_keys = {(doc_id, c["seq"]) for doc_id, doc in documents.items() for c in doc["chapters"]}
    missing = sorted(f"{d} {s}" for d, s in expected_keys - set(mapped))
    extra = sorted(f"{d} {s}" for d, s in set(mapped) - expected_keys)
    repeated = sorted(f"{d} {s}" for (d, s), n in mapped.items() if n > 1)
    out.append(result("Record", "Section 4 maps every chapter/phase exactly once",
                      not (missing or extra or repeated),
                      "; ".join(filter(None, [f"missing {', '.join(missing)}" if missing else "",
                                               f"unknown {', '.join(extra)}" if extra else "",
                                               f"repeated {', '.join(repeated)}" if repeated else ""]))))
    out.append(result("Record", "Section 4 page ranges equal the manifest", not wrong_pages, "; ".join(wrong_pages)))

    rules = rule_rows(catalog)
    rule_ids = [cells[0] for cells in rules]
    counts = Counter(rule.split("-")[1] for rule in rule_ids)
    out.append(result("Catalog", "Rule counts per family equal the pinned expectations",
                      dict(counts) == {k: v for k, v in EXPECTED_RULES.items() if v} and
                      len(rule_ids) == len(set(rule_ids)),
                      f"found {dict(counts)}, expected {EXPECTED_RULES}"))
    out.append(result("Record", "Section 6 states the catalog's rule total",
                      re.search(rf"\b{len(rule_ids):,} rules\b", section(record, 6)) is not None,
                      f"{len(rule_ids):,} rules in the catalog"))
    for family in EXPECTED_RULES:
        numbers = sorted(int(rule.split("-")[2]) for rule in rule_ids if rule.split("-")[1] == family)
        if numbers != list(range(1, len(numbers) + 1)):
            out.append(result("Record", f"ST-{family} rules are numbered without gaps", False, str(numbers)))
    bad_strength = [cells[0] for cells in rules if cells[2] not in STRENGTHS]
    bad_scope = [cells[0] for cells in rules if cells[3] not in SCOPES]
    out.append(result("Record", "Every rule has a strength and a scope", not (bad_strength or bad_scope),
                      f"strength {bad_strength} scope {bad_scope}" if bad_strength or bad_scope else ""))
    bad_citation = []
    for cells in rules:
        found = citations(cells[4], pattern)
        if not found:
            bad_citation.append(f"{cells[0]} (no citation)")
        for doc_id, kind, first, last in found:
            doc = documents[doc_id]
            if kind == "PAGE" and not (doc["format"] == "PDF" and 1 <= first <= last <= doc["pages"]):
                bad_citation.append(f"{cells[0]} {doc_id} p.{first}-{last}")
            elif kind == "PHASE" and first not in {c["seq"] for c in doc["chapters"]}:
                bad_citation.append(f"{cells[0]} {doc_id} \u00a7{first}")
            elif kind == "LINE" and not 1 <= first <= last <= doc.get("textLines", 0):
                bad_citation.append(f"{cells[0]} {doc_id} line {first}-{last}")
    out.append(result("Record", "Every rule cites an existing page, phase or line", not bad_citation,
                      "; ".join(bad_citation)))

    scenarios = scenario_rows(record)
    scenario_ids = [cells[0] for cells in scenarios]
    out.append(result("Record", f"Scenarios ST-TS-001 to ST-TS-{EXPECTED_SCENARIOS:03d}",
                      scenario_ids == [f"ST-TS-{n:03d}" for n in range(1, EXPECTED_SCENARIOS + 1)],
                      f"{len(scenario_ids)} found"))
    known = set(rule_ids)
    cited = set()
    unknown = []
    for cells in scenarios:
        refs = set(RULE_ID.findall(cells[2]))
        if not refs:
            unknown.append(f"{cells[0]} cites no rule")
        unknown += [f"{cells[0]} -> {ref}" for ref in sorted(refs - known)]
        cited |= refs
    out.append(result("Record", "Every rule cited by a scenario exists", not unknown, "; ".join(unknown)))
    bad_ready = [cells[0] for cells in scenarios if cells[4] not in READINESS]
    out.append(result("Record", "Every scenario has a readiness value", not bad_ready, ", ".join(bad_ready)))
    untested = [cells[0] for cells in rules if cells[2] != "INFO" and cells[0] not in cited]
    out.append(result("Record", "Every testable rule (not INFO) has a scenario", not untested, ", ".join(untested)))

    for prefix, expected in EXPECTED_IDS.items():
        numbers = ids_in(record, prefix)
        out.append(result("Record", f"{prefix}-01 to {prefix}-{expected:02d} are listed once each",
                          sorted(numbers) == list(range(1, expected + 1)), f"found {sorted(numbers)}"))

    broken = broken_links(record, RECORD.parent)
    out.append(result("Record", "Record links and anchors resolve", not broken, ", ".join(broken)))
    catalog_broken = broken_links(catalog, CATALOG.parent)
    out.append(result("Catalog", "Catalog links and anchors resolve", not catalog_broken, ", ".join(catalog_broken)))
    note_broken = broken_links(linked.get("KB note", ""), KB_NOTE.parent) if linked.get("KB note") else ["KB note missing"]
    out.append(result("Record", "KB note links and anchors resolve", not note_broken, ", ".join(note_broken)))
    unlinked = [name for name, text in linked.items() if RECORD.name not in text]
    out.append(result("Integration", "The record is linked from the strategy, strategy README, handbook, KB index, "
                      "KB note and scripts README", not unlinked, ", ".join(unlinked)))
    catalog_unlinked = [name for name, text in (("record", record), ("KB note", linked.get("KB note", "")))
                        if CATALOG.name not in text]
    out.append(result("Integration", "The rule catalog is linked from the record and the KB note", not catalog_unlinked,
                      ", ".join(catalog_unlinked)))

    ignored = [rule for rule in IGNORE_RULES if not re.search(rf"^{re.escape(rule)}\s*$", gitignore, re.M)]
    out.append(result("Integration", "The confidential sources and the local split are git-ignored", not ignored,
                      ", ".join(ignored)))

    anchor = manifest.get("atl105Reference", {}).get("versions", {})
    misplaced = []
    for version, text in atl105.items():
        lines = text.splitlines()
        first, last = anchor.get(version, {}).get("lines", [0, 0])
        window = " ".join(lines[first - 1:last]) if lines and first else ""
        if not all(fragment in window for fragment in ATL105_FRAGMENTS):
            misplaced.append(f"{version} lines {first}-{last}")
    out.append(result("Integration", "ATL105 Section 3.3 refers to Secure Transport on the pinned lines (2026-4 and "
                      "2026-3)", not misplaced, ", ".join(misplaced)))

    try:
        entry = json.loads(status_text).get("referenceTraining", {}).get("secureTransport", {})
    except json.JSONDecodeError:
        entry = {}
    out.append(result("Integration", "training-status.json records referenceTraining.secureTransport for this record",
                      entry.get("trainingRecord", "").endswith(RECORD.name) and
                      entry.get("evidenceLevel") == "SOURCE_BOUND_DOCUMENTATION_LEVEL",
                      "" if entry else "entry missing"))
    return out


def page_texts(text):
    parts = PAGE_MARK.split(text)
    return {int(parts[i]): parts[i + 1] for i in range(1, len(parts), 2)}


def parse_chapter_block(markdown):
    start = markdown.index(BEGIN) + len(BEGIN) + 1
    fence_end = markdown.index("\n", start)
    fence = markdown[start:fence_end].removesuffix("text")
    end = markdown.rindex(f"\n{fence}\n{END}")
    return markdown[fence_end + 1:end]


def load_local(manifest, local=LOCAL):
    """Stored text, chapter texts and source hashes per document, or None when the local split is absent."""
    if not exists(Path(local) / "00-INDEX.md"):
        return None
    loaded = {}
    for doc in manifest["documents"]:
        source = Path(local) / doc["storedFile"]
        text_file = Path(local) / doc["textFile"]
        loaded[doc["id"]] = {
            "sourceSha": sha256(read_bytes(source)) if exists(source) else None,
            "text": read_text(text_file) if exists(text_file) else None,
            "chapters": {c["seq"]: (parse_chapter_block(read_text(Path(local) / c["file"]))
                                    if exists(Path(local) / c["file"]) else None) for c in doc["chapters"]},
            "images": [path for path in doc.get("renderedImages", []) if not exists(Path(local) / path)],
        }
    return loaded


def location_text(doc, local_doc, kind, first, last):
    text = local_doc["text"] or ""
    if kind == "PAGE":
        pages = page_texts(text)
        return "\n".join(pages.get(n, "") for n in range(first, last + 1))
    if kind == "PHASE":
        return local_doc["chapters"].get(first) or ""
    return "\n".join(text.split("\n")[first - 1:last])


def check_source(manifest, local, record, catalog, atl105, reextract=True):
    out = []
    documents = {doc["id"]: doc for doc in manifest["documents"]}
    wrong_source = [d for d, doc in documents.items() if local[d]["sourceSha"] != doc["sha256"]]
    out.append(result("Source", "Every stored source file has its pinned SHA-256", not wrong_source,
                      ", ".join(wrong_source)))
    wrong_text = [d for d, doc in documents.items()
                  if local[d]["text"] is None or sha256(local[d]["text"]) != doc["textSha256"]]
    out.append(result("Source", "Every extracted text has its manifest SHA-256", not wrong_text, ", ".join(wrong_text)))

    if reextract:
        try:
            import pypdf
            from pypdf import PdfReader
        except ImportError:
            out.append({"group": "Source", "check": "PDF re-extraction", "status": "NOT_ASSESSED",
                        "detail": "pypdf is not installed"})
        else:
            drift, mismatch = [], []
            root = Path(local.get("__root__", LOCAL))
            for doc_id, doc in documents.items():
                if doc["format"] != "PDF":
                    continue
                reader = PdfReader(long_path(root / doc["storedFile"]))
                text = "".join(f"--- PAGE {n} ---\n{page.extract_text() or ''}\n"
                               for n, page in enumerate(reader.pages, 1))
                if text != local[doc_id]["text"]:
                    (drift if pypdf.__version__ != manifest["extractors"]["pypdf"] else mismatch).append(doc_id)
            out.append(result("Source", "Every PDF re-extracts to the stored page-marked text", not mismatch,
                              ", ".join(mismatch)))
            if drift:
                out.append({"group": "Source", "check": "PDF re-extraction with a different pypdf version",
                            "status": "NOT_ASSESSED",
                            "detail": f"pypdf {pypdf.__version__} differs from {manifest['extractors']['pypdf']}: "
                                      + ", ".join(drift)})

    bad_chapter, bad_join = [], []
    for doc_id, doc in documents.items():
        pieces = []
        for chapter in doc["chapters"]:
            piece = local[doc_id]["chapters"].get(chapter["seq"])
            if piece is None or sha256(piece) != chapter["sha256"]:
                bad_chapter.append(f"{doc_id} {chapter['seq']}")
            pieces.append(piece or "")
        if "".join(pieces) != (local[doc_id]["text"] or ""):
            bad_join.append(doc_id)
    out.append(result("Source", "Every chapter/phase file holds its manifest text (SHA-256)", not bad_chapter,
                      ", ".join(bad_chapter)))
    out.append(result("Source", "The chapters/phases of every document concatenate to its full text", not bad_join,
                      ", ".join(bad_join)))

    corpus = [squash(entry["text"] or "") for key, entry in local.items() if key != "__root__"]
    corpus += [squash(text) for text in atl105.values()]
    not_verbatim = sorted({q for q in quotes(record) + quotes(catalog)
                           if not any(squash(q) in source for source in corpus)})
    out.append(result("Source", f"Every quotation of {MIN_QUOTE}+ characters is verbatim in a source or in ATL105",
                      not not_verbatim, " | ".join(q[:70] for q in not_verbatim)))

    pattern = citation_pattern(documents)
    misplaced = []
    for cells in rule_rows(catalog):
        found = citations(cells[4], pattern)
        for quoted in quotes(cells[1]):
            places = [location_text(documents[d], local[d], kind, a, b) for d, kind, a, b in found]
            if not any(squash(quoted) in squash(place) for place in places):
                misplaced.append(f"{cells[0]}: {quoted[:50]}")
    out.append(result("Source", "Every rule quotation is on the page, phase or lines the rule cites", not misplaced,
                      "; ".join(misplaced)))

    missing_images = [f"{d}: {', '.join(local[d]['images'])}" for d in documents if local[d]["images"]]
    out.append(result("Source", "Every rendered page image exists", not missing_images, "; ".join(missing_images)))
    return out


def run(local=LOCAL):
    manifest = load_manifest()
    record = read_text(RECORD)
    atl105 = read_atl105()
    catalog = read_text(CATALOG) if exists(CATALOG) else ""
    gitignore = read_text(GITIGNORE) if exists(GITIGNORE) else ""
    status_text = read_text(TRAINING_STATUS) if exists(TRAINING_STATUS) else "{}"
    results = check_record(record, catalog, read_linking_docs(), manifest, gitignore, atl105, status_text)
    loaded = load_local(manifest, local)
    if loaded is None:
        results.append({"group": "Source", "check": "Source checks against the local split", "status": "NOT_ASSESSED",
                        "detail": f"{Path(local)} is not available (local-only, never committed)"})
    else:
        loaded["__root__"] = Path(local)
        results += check_source(manifest, loaded, record, catalog, atl105)
    counts = Counter(item["status"] for item in results)
    code = 1 if counts["FAIL"] else (2 if counts["NOT_ASSESSED"] else 0)
    return results, counts, code


def main(argv=None):
    parser = argparse.ArgumentParser(description="Verify the Secure Transport ingestion and training record.")
    parser.add_argument("--local", type=Path, default=LOCAL, help="local split (default: docs/specs/secure-transport)")
    parser.add_argument("--json", type=Path, help="also write the results to this JSON file")
    args = parser.parse_args(argv)
    if hasattr(sys.stdout, "reconfigure"):
        sys.stdout.reconfigure(encoding="utf-8")
    results, counts, code = run(args.local)
    print("Secure Transport ingestion verification")
    print(f"Record:   {RECORD.relative_to(MODULE).as_posix()}")
    print(f"Catalog:  {CATALOG.relative_to(MODULE).as_posix()}")
    print(f"Manifest: {MANIFEST.relative_to(MODULE).as_posix()}")
    print(f"Local:    {args.local}")
    for item in results:
        detail = f" -- {item['detail']}" if item["detail"] else ""
        print(f"[{item['status']}] {item['group']}: {item['check']}{detail}")
    summary = ", ".join(f"{counts[s]} {s}" for s in ("PASS", "FAIL", "INFO", "NOT_ASSESSED") if counts[s])
    print(f"Summary: {summary}; exit code {code}")
    if args.json:
        payload = {"record": RECORD.relative_to(MODULE).as_posix(), "local": str(args.local), "results": results,
                   "summary": dict(counts), "exitCode": code}
        args.json.write_text(json.dumps(payload, indent=2) + "\n", encoding="utf-8")
    return code


if __name__ == "__main__":
    sys.exit(main())
