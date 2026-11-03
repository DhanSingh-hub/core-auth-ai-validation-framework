"""Release and overlap evidence; exact text observations never approve semantic equivalence."""

import difflib
import hashlib
import re
from collections import defaultdict

HEADER = re.compile(r"^(BUYPASS|Software Version:|Specification Release Version:|October 09, 2026|August 07, 2026)")
SECTION = re.compile(r"^(\d{1,2}(?:\.\d+)+)\s+([A-Z].*)$")
CHAPTER = re.compile(r"^(\d{1,2})\.\s+([A-Z].*)$")
APPENDIX = re.compile(r"^Appendix ([A-Z]+)\.\s+(.+)$")
ELEMENT = re.compile(r"^Number:\s*(\d+)\s+Name:\s*(.*)$")
MODAL = re.compile(r"\b(must|shall|should|required|not allowed|not supported|only|always|never)\b", re.I)


def norm(text):
    return " ".join(text.split())


def sha(text):
    return hashlib.sha256(text.encode("utf-8")).hexdigest()


def source_index(text):
    """Keep ordered lines and explicit body locators; reject TOC-only headings."""
    lines = text.splitlines()
    markers = []
    page, in_body = None, False
    page_rows = []
    for line_no, line in enumerate(lines, 1):
        found = re.fullmatch(r"--- PAGE (\d+) ---", line.strip())
        if found:
            page = int(found[1])
        if page is None:
            continue
        page_rows.append((line_no, page, line))
        if line.strip() == "1. Introduction":
            in_body = True
        if not in_body:
            continue
        if "...." in line or "................................" in line:
            continue
        heading = SECTION.match(line) or CHAPTER.match(line)
        appendix = APPENDIX.match(line)
        element = ELEMENT.match(line)
        if appendix and "." in appendix[2]:
            appendix = None
        if heading and "." not in heading[1] and "." in heading[2]:
            heading = None
        if appendix:
            markers.append((line_no, page, "APPENDIX:" + appendix[1], "APPENDIX"))
        elif element:
            markers.append((line_no, page, "ELEMENT:" + element[1], "ELEMENT"))
        elif heading:
            markers.append((line_no, page, "SECTION:" + heading[1], "SECTION"))
    result = defaultdict(list)
    for offset, (start, page, key, kind) in enumerate(markers):
        if kind == "ELEMENT":
            end = next((row[0] - 1 for row in markers[offset + 1:]
                        if row[3] in {"ELEMENT", "APPENDIX"}), len(lines))
        elif kind == "APPENDIX":
            end = next((row[0] - 1 for row in markers[offset + 1:]
                        if row[3] == "APPENDIX"), len(lines))
        else:
            section = key.split(":", 1)[1]
            end = next((row[0] - 1 for row in markers[offset + 1:]
                        if row[3] == "APPENDIX" or row[3] == "SECTION" and
                        not row[2].split(":", 1)[1].startswith(section + ".")), len(lines))
        cleaned = []
        for line in lines[start - 1:end]:
            value = line.strip()
            if (not value or HEADER.match(value) or re.fullmatch(r"--- PAGE \d+ ---", value)
                    or value in {"ATL105", "2026-3", "2026-4"}
                    or re.fullmatch(r"(?:\d+-\d+|Appendix [A-Z]+-\d+)", value)):
                continue
            cleaned.append(value)
        block = norm(" ".join(cleaned))
        result[key].append({"key": key, "page": page, "startLine": start, "endLine": end,
                            "text": block, "sha256": sha(block)})
    return dict(result), page_rows


def anchor_keys(anchor):
    if str(anchor.get("specification", "ATL105")).upper() != "ATL105":
        return []
    element = str(anchor.get("element", ""))
    if element.isdigit():
        return ["ELEMENT:" + str(int(element))]
    section = str(anchor.get("section", "")).strip()
    appendix = re.match(r"Appendix ([A-Z]+)(?:-\d+)?$", section)
    if appendix:
        return ["APPENDIX:" + appendix[1]]
    if re.fullmatch(r"\d+(?:\.\d+)*", section):
        return ["SECTION:" + section]
    return []


def compare_anchor(anchor, old, new):
    keys = anchor_keys(anchor)
    if not keys:
        return {"status": "UNRESOLVED_LOCATOR", "key": "", "reason": "Composite or unsupported legacy locator"}
    key = keys[0]
    before, after = old.get(key, []), new.get(key, [])
    if len(before) != 1 or len(after) != 1:
        return {"status": "MISSING_OR_AMBIGUOUS_LOCATOR", "key": key,
                "oldCount": len(before), "newCount": len(after)}
    left, right = before[0], after[0]
    same = left["text"] == right["text"]
    removed = sorted(set(left["text"].split(". ")) - set(right["text"].split(". ")))[:4]
    added = sorted(set(right["text"].split(". ")) - set(left["text"].split(". ")))[:4]
    return {"status": "IDENTICAL_NORMALIZED_BLOCK" if same else "CHANGED_BLOCK_REVIEW_REQUIRED",
            "key": key, "oldPage": left["page"], "newPage": right["page"],
            "oldStartLine": left["startLine"], "newStartLine": right["startLine"],
            "oldSha256": left["sha256"], "newSha256": right["sha256"],
            "similarityForTriageOnly": (1.0 if same else round(difflib.SequenceMatcher(
                None, left["text"], right["text"], autojunk=False).ratio(), 4))
            if len(left["text"]) + len(right["text"]) < 20000 else None,
            "removedTextSample": [] if same else [text[:400] for text in removed],
            "addedTextSample": [] if same else [text[:400] for text in added],
            "meaning": "Text-block identity/difference only, not atomic-rule equivalence or applicability"}


def reconcile_legacy(legacy_rows, overlay_rules, old_index, new_index):
    rows, overlaps, comparisons = [], [], {}
    for legacy in legacy_rows:
        observations = []
        for anchor in legacy["sourceAnchors"]:
            key = tuple(anchor_keys(anchor))
            if key not in comparisons:
                comparisons[key] = compare_anchor(anchor, old_index, new_index)
            observations.append(comparisons[key])
        for rule in overlay_rules:
            explicit = f"BR-{rule['id']}"
            legacy_words = set(re.findall(r"[a-z0-9]+", legacy["title"].lower()))
            rule_words = set(re.findall(r"[a-z0-9]+", rule["statement"].lower()))
            common = legacy_words & rule_words
            element = re.search(r"Element (\d+)", rule["section"])
            same_element = element and any(str(anchor.get("element")) == element[1]
                                           for anchor in legacy["sourceAnchors"])
            exact = norm(legacy["title"]).casefold().rstrip(".") == norm(rule["statement"]).casefold().rstrip(".")
            if exact or len(common) >= 5 and (same_element or len(common) / max(len(rule_words), 1) >= .65):
                overlaps.append({"legacyBrId": legacy["legacyBrId"], "overlayBrId": explicit,
                                 "evidence": "EXACT_TITLE" if exact else "ELEMENT_OR_LEXICAL_OVERLAP_CANDIDATE",
                                 "disposition": "REVIEW_REQUIRED_NO_AUTOMATIC_MERGE"})
        all_same = bool(observations) and all(row["status"] == "IDENTICAL_NORMALIZED_BLOCK" for row in observations)
        rows.append({**legacy, "releaseObservations": observations,
                     "releaseEquivalence": "ALL_ANCHORED_BLOCKS_IDENTICAL_TEXT_ONLY" if all_same
                     else "SOURCE_DIFFERENCE_OR_LOCATOR_REVIEW_REQUIRED",
                     "overlapCandidateIds": [row["overlayBrId"] for row in overlaps
                                             if row["legacyBrId"] == legacy["legacyBrId"]],
                     "denominatorEligibility": "BLOCKED_PENDING_SEMANTIC_SCOPE_AND_APPROVAL"})
    return rows, overlaps


def obligation_inventory(page_rows, overlay_rules):
    """Retain every nonempty body paragraph, including table-like and nonnormative text."""
    pages = defaultdict(list)
    for number, page, line in page_rows:
        pages[page].append((number, line))
    rows, scan = [], []
    for page, lines in pages.items():
        paragraph = []
        before = len(rows)

        def flush():
            if not paragraph:
                return
            quote = norm(" ".join(line for _, line in paragraph))
            if quote and not all(HEADER.match(line.strip()) or not line.strip()
                                 for _, line in paragraph):
                mentions = bool(re.search(r"master\s*card|maestro", quote, re.I))
                rows.append({"sourceUnitId": f"MM4-SRC-P{page:04}-L{paragraph[0][0]:05}",
                             "page": page, "startLine": paragraph[0][0], "endLine": paragraph[-1][0],
                             "quote": quote, "sha256": sha(quote),
                             "unitKind": "NORMATIVE_CANDIDATE" if MODAL.search(quote)
                             else "TABLE_OR_INFORMATION_REVIEW",
                             "networkMention": mentions,
                             "overlayQuoteIds": [rule["id"] for rule in overlay_rules
                                                 if rule["page"] == page and norm(rule["quote"]) in quote],
                             "scopeDisposition": "UNCLASSIFIED_SOURCE_UNIT_RETAINED",
                             "atomicity": "NOT_ASSERTED", "coverage": "NOT_ASSESSED"})
            paragraph.clear()

        for number, line in lines:
            if not line.strip() or re.fullmatch(r"--- PAGE \d+ ---", line.strip()):
                flush()
            else:
                paragraph.append((number, line))
        flush()
        scan.append({"page": page, "firstLine": lines[0][0], "lastLine": lines[-1][0],
                     "retainedUnits": len(rows) - before,
                     "pageTextSha256": sha("\n".join(line for _, line in lines)),
                     "disposition": "SCANNED_NOT_SEMANTIC_COMPLETENESS"})
    return rows, scan
