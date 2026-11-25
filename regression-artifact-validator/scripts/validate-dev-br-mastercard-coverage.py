"""Validate the Dev Team BR delivery for completeness and Mastercard coverage (ATL105 and the trained Test Solution).

Inputs (read-only):
  * Dev delivery at the repository root: BUSINESS_REQUIREMENTS.md and business_scenarios.csv (BR1 "BUYPASS ATL105
    MasterCard"); both are hash-pinned by the curated review, so a changed delivery must be re-reviewed.
  * ATL105 2026-4 extracted text (primary oracle, the version the Dev BR claims) and 2026-3 extracted text (the
    trained-baseline version).
  * Trained Test Solution: mc-credit-purchase-training-package.json (8 FRs, 134 business scenarios, 189 BRs) and the
    MASTERCARD group of all-network-requirements-review-v1 (17 contextual BRs).
  * Curated review: docs/specs/kb/segment-100/dev-br-mastercard-coverage-review.json.

Checks:
  1. Structural completeness of the Dev delivery (counts, 320-combination universe, IDs, codes, flows, gaps, SME).
  2. Dev source citations and BR-<page>-<n> rule references against ATL105 2026-4 and 2026-3 pages.
  3. An independent ATL105 support verdict for each Dev scenario and the basis of every exclusion.
  4. Coverage of a source-verified ATL105 Mastercard inventory (every quote must resolve on its cited page).
  5. Coverage of the trained Mastercard solution at FR, business-scenario, BR and contextual-BR level.

Curated dispositions are draft Test Solution judgements: COVERED/PARTIAL require matching Dev evidence and every
absence claim is verified by search. Nothing is approved, promoted or executed.

Usage, from the validator module:  python scripts/validate-dev-br-mastercard-coverage.py [--check]
"""
import argparse
import csv
import hashlib
import io
import json
import os
import re
import sys
from collections import Counter, OrderedDict, defaultdict
from pathlib import Path

import openpyxl
from openpyxl.styles import Alignment, Font, PatternFill
from openpyxl.utils import get_column_letter

HERE = Path(__file__).resolve().parent
MODULE = HERE.parent
REPO = MODULE.parent
PACK = MODULE / "specifications" / "ATL105"
CURATED = PACK / "docs" / "specs" / "kb" / "segment-100" / "dev-br-mastercard-coverage-review.json"
DEV_MD = REPO / "BUSINESS_REQUIREMENTS.md"
DEV_CSV = REPO / "business_scenarios.csv"
SPEC_V4 = PACK / "docs" / "specs" / "extracted_text_new.txt"
SPEC_V3 = PACK / "docs" / "specs" / "extracted_text.txt"
TRAINING = PACK / "test-output" / "test-json" / "mc-credit-purchase-training-package.json"
ALL_NETWORK = (PACK / "test-output" / "test-solution-independent-review" / "all-network-requirements-review-v1"
               / "requirements-review.csv")
OUT_DIR = PACK / "test-output" / "ai-solution-independent-review" / "dev-br-mc-coverage-v1"
XLSX_NAME = "dev-br-mc-coverage.xlsx"

PROGRAMS = OrderedDict([("CREDIT", "Credit"), ("FLEET", "Fleet")])
EMV_ENTRY = {"05", "95", "07", "86"}
COUNTED = ("COVERED", "PARTIAL", "CONTEXT_ONLY", "CONFLICT", "NOT_COVERED")
DISPOSITIONS = COUNTED + ("NOT_APPLICABLE", "EXTERNAL_SOURCE")
RANK = {name: index for index, name in enumerate(DISPOSITIONS)}
FILLS = {"COVERED": "C6EFCE", "PARTIAL": "FFEB9C", "CONTEXT_ONLY": "DDEBF7", "CONFLICT": "F8CBAD",
         "NOT_COVERED": "FFC7CE", "NOT_APPLICABLE": "EDEDED", "EXTERNAL_SOURCE": "EDEDED", "PASS": "C6EFCE",
         "WARN": "FFEB9C", "FAIL": "FFC7CE", "HIGH": "FFC7CE", "MEDIUM": "FFEB9C", "LOW": "DDEBF7", "INFO": "EDEDED",
         "SUPPORTED": "C6EFCE", "EXTERNAL_PIPS": "EDEDED", "NOT_SUPPORTED": "FFC7CE"}

# Appendix G (p.545-546): Dev transaction category -> transaction type code(s) and the code line that must resolve.
APPENDIX_G = OrderedDict([
    ("Purchase/Capture", ("0, 4", ["0 POS Purchase/Capture", "4 Customer-activated Purchase/Capture"])),
    ("Authorization Only", ("3, 5", ["3 POS Authorization Only", "5 Customer-activated Authorization Only"])),
    ("Mail/Phone Purchase", ("6", ["6 Mail/Phone Purchase"])),
    ("Merchandise Return", ("7", ["7 Merchandise return"])),
    ("Purchase Reversal", ("8", ["8 Purchase reversal"])),
    ("Account Verification", ("A", ["A Account Verification"])),
    ("Mail/Phone Authorization Only", ("B", ["B Mail/Phone Authorization Only"])),
    ("Mail/Phone Reversal", ("C", ["C Mail/Phone Reversal"])),
    ("RBC Loyalty Lookup", ("D", ["D RBC Loyalty Lookup"])),
    ("Balance Inquiry", ("E", ["E Balance Inquiry"])),
    ("Activate", ("K", ["K Activate"])),
    ("Deactivate", ("L", ["L Deactivate"])),
    ("Balance Merge", ("M", ["M Balance Merge"])),
    ("Replace Card", ("N", ["N Replace Card"])),
    ("Recharge Card", ("Q", ["Q Recharge Card"])),
    ("Cancellation", ("S", ["S Cancellation"])),
    ("Token Registration", ("T", ["T Token Registration"])),
    ("Void of a Merchandise Return", ("U", ["U Void of a Merchandise Return"])),
    ("Loyalty Advice Function", ("V", ["V Loyalty advice function"])),
    ("Time-out Reversal", ("Z", ["Z Time-out Reversal"])),
])
# Section 10.1.2 (p.83) credit list, by Dev category name; Cancellation is the Authorization Only Reversal (S).
CREDIT_LIST = {"Purchase/Capture": "Purchase/Capture", "Purchase Reversal": "Purchase Reversal",
               "Authorization Only": "Authorization Only", "Merchandise Return": "Merchandise Return",
               "Time-out Reversal": "Time-out Reversal", "Mail/Phone Purchase": "Mail/Phone Purchase",
               "Mail/Phone Authorization Only": "Mail/Phone Authorization Only",
               "Mail/Phone Reversal": "Mail/Phone Reversal", "Void of a Merchandise Return": "Void of Merchandise Return",
               "Balance Inquiry": "Balance Inquiry", "Cancellation": "Authorization Only Reversal"}
# Section 10.14.2.4 (p.161) EMV list, by Dev category name.
EMV_LIST = {"Purchase/Capture", "Authorization Only", "Merchandise Return", "Purchase Reversal", "Balance Inquiry",
            "Cancellation", "Time-out Reversal"}
MOTO = {"Mail/Phone Purchase", "Mail/Phone Authorization Only", "Mail/Phone Reversal"}
CARD_NOT_PRESENT_ENTRY = {"01", "10"}
APPENDIX_J_CODES = ["01", "02", "04", "05", "07", "10", "79", "80", "82", "86", "90", "91", "95"]
CARD_TYPES = {"Credit": "020", "Fleet": "080"}

# Version equivalence: section start/end headings compared between ATL105 2026-3 and 2026-4.
VERSION_SECTIONS = [
    ("2.3 Unsuccessful Financial Transactions", r"^2\.3 Unsuccessful Financial Transactions", r"^2\.3\.1 "),
    ("2.3.1 Time-out by customer system", r"^2\.3\.1 Time-out by Customer", r"^2\.3\.2 "),
    ("10.1.1 Card Requirements", r"^10\.1\.1 Card Requirements", r"^10\.1\.2 Supported"),
    ("10.1.2 Supported Transaction Types", r"^10\.1\.2 Supported Transaction Types", r"^10\.1\.2\.1 "),
    ("10.1.2.1 Partial Approval", r"^10\.1\.2\.1 Partial Approval", r"^10\.1\.2\.2 "),
    ("10.1.2.2 Authorization Only Reversal", r"^10\.1\.2\.2 Authorization Only Reversal", r"^10\.1\.3 "),
    ("10.1.3-10.1.5 AVS, recurring, RFID", r"^10\.1\.3 Address Verification", r"^10\.1\.6 "),
    ("10.1.7 Receipt Requirements", r"^10\.1\.7 Receipt Requirements", r"^10\.1\.8 "),
    ("10.1.8 Regulatory Requirements", r"^10\.1\.8 Regulatory Requirements", r"^10\.2 Debit"),
    ("10.14.2 EMV Card Requirements", r"^10\.14\.2 Card Requirements", r"^10\.14\.3 "),
    ("10.14.3 EMV PIN Processing", r"^10\.14\.3 EMV PIN", r"^10\.14\.4 "),
    ("Appendix G Valid Transaction Type Codes", r"^Appendix G\.\s+Valid Transaction Type Codes$",
     r"^Appendix H\.\s+Decline Codes"),
    ("Appendix J Valid POS Entry Mode Codes", r"^Appendix J\.\s+Valid Point-of-Service Entry Mode Codes$",
     r"^Appendix K\.\s+Additional Information Data Layouts$"),
    ("Appendix Z Stored Credential", r"^Appendix Z\.\s+Stored Credential", r"^Appendix AA"),
]


# ---------------------------------------------------------------- io and text

def long_path(path):
    """Windows extended-length form; several review paths exceed 260 characters."""
    resolved = os.path.abspath(str(path))
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        return "\\\\?\\" + resolved
    return resolved


def read_bytes(path):
    with open(long_path(path), "rb") as handle:
        return handle.read()


def read_text(path):
    return read_bytes(path).decode("utf-8-sig")


def sha256(data):
    return hashlib.sha256(data).hexdigest()


def rel(path):
    resolved = Path(os.path.abspath(str(path)))
    try:
        return resolved.relative_to(MODULE).as_posix()
    except ValueError:
        return "../" + resolved.relative_to(MODULE.parent).as_posix()


def norm(text):
    """Comparison form: typographic quotes/dashes and trademark signs folded, whitespace collapsed, lower case."""
    for old, new in (("\u2019", "'"), ("\u2018", "'"), ("\u201c", '"'), ("\u201d", '"'), ("\u2014", "-"),
                     ("\u2013", "-"), ("\u00ae", ""), ("\u2122", ""), ("\u2120", "")):
        text = text.replace(old, new)
    return re.sub(r"\s+", " ", text).strip().lower()


def split_codes(value, separator=","):
    return [part.strip() for part in value.split(separator) if part.strip()]


def pct(part, whole):
    return round(100.0 * part / whole, 1) if whole else 0.0


# ---------------------------------------------------------------- ATL105 text

def load_spec(path):
    """Return ({page: normalized page text}, [(page, stripped line)]) from an extracted-text file."""
    pages, lines, page = defaultdict(list), [], 0
    for raw in read_text(path).splitlines():
        marker = re.match(r"--- PAGE (\d+) ---", raw)
        if marker:
            page = int(marker.group(1))
            continue
        pages[page].append(raw)
        lines.append((page, raw.strip()))
    return {number: norm(" ".join(text)) for number, text in pages.items()}, lines


def find_pages(pages, quote):
    needle = norm(quote)
    return [number for number, text in pages.items() if needle and needle in text]


def section_body(lines, start, end):
    """Body lines of the last (non-TOC) occurrence of a section, without running headers or blank lines."""
    def body_line(text):
        return not (not text or text.startswith("BUYPASS") or text.startswith("Software Version")
                    or text.startswith("Specification Release Version") or "...." in text
                    or re.match(r"^(January|February|March|April|May|June|July|August|September|October|November|"
                                r"December) \d{2}, \d{4}$", text)
                    or re.match(r"^(\d+-\d+|Appendix [A-Z]+-\d+)$", text) or re.match(r"^\d$", text))
    starts = [i for i, (_, text) in enumerate(lines) if re.match(start, text) and "...." not in text]
    if not starts:
        return None, []
    first = starts[-1]
    stop = next((i for i in range(first + 1, len(lines)) if re.match(end, lines[i][1]) and "...." not in lines[i][1]),
                len(lines))
    body = [re.sub(r"^\d(?=[A-Z])", "", text) for _, text in lines[first:stop] if body_line(text)]
    return lines[first][0], body


# ---------------------------------------------------------------- Dev delivery

LEG = re.compile(r"^\s*- Leg (\d+) (\w+): (.+?) \(Transaction Type ([^)]+)\), entry mode ([^,]+), message "
                 r"([^;,]+?)(?:, segment\(s\) ([^;]+))?(;.*)?$")
CARRY = re.compile(r"carries ([A-Za-z ]+?) \((BR-(\d+)-\d+)(?:, p\.(\d+))?\)")


def parse_dev_csv(text):
    rows = list(csv.DictReader(io.StringIO(text)))
    for row in rows:
        row["codes"] = split_codes(row["txn_type_codes"])
        row["entries"] = split_codes(row["entry_mode_codes"])
        row["segments"] = split_codes(row["required_segments"])
    return rows


def md_section(lines, heading, next_heading=None):
    start = next(i for i, line in enumerate(lines) if line.strip() == heading)
    stop = len(lines)
    if next_heading:
        stop = next(i for i, line in enumerate(lines) if i > start and line.strip() == next_heading)
    return lines[start + 1:stop]


def table_rows(lines, prefix):
    rows = []
    for line in lines:
        if line.startswith(prefix):
            rows.append([cell.strip() for cell in line.strip().strip("|").split("|")])
    return rows


def parse_dev_md(text):
    lines = text.splitlines()
    header = {
        "selection": re.search(r"Selection: \*\*(.+?)\*\*", text).group(1),
        "mappingStatus": re.search(r"Dimension mapping status: \*\*(.+?)\*\*(.*)", text).group(1),
        "mappingNote": re.search(r"Dimension mapping status: \*\*.+?\*\*\s*\((.+?)\)", text).group(1),
        "validation": re.search(r"Validation: \*\*(.+?)\*\*", text).group(1),
        "built": re.search(r"Built: (\S+)", text).group(1),
        "brId": re.search(r"`(BR-[0-9A-F]+)` - status \*\*(\w+)\*\*", text).group(1),
        "brStatus": re.search(r"`(BR-[0-9A-F]+)` - status \*\*(\w+)\*\*", text).group(2),
    }
    summary_header = ["BR", "Name", "Spec", "Scenarios", "valid", "valid (conditional)", "requires SME", "multi-leg",
                      "with gaps", "excluded", "supporting rules"]
    summary = [dict(zip(summary_header, row)) for row in table_rows(lines, "| BR1 |")]
    scenario_lines = md_section(lines, "### Scenarios", "### Multi-leg flows (follow-on scenarios and pre-authorized variants)")
    scenario_header = ["id", "scenario", "status", "sme", "kind", "txnType", "entryMode", "cardType", "message", "rules",
                       "gaps"]
    scenarios = [dict(zip(scenario_header, row)) for row in table_rows(scenario_lines, "| BR1_TS")]
    flow_lines = md_section(lines, "### Multi-leg flows (follow-on scenarios and pre-authorized variants)",
                            "### Scenarios requiring SME confirmation")
    flows, current, variant = OrderedDict(), None, None
    for line in flow_lines:
        block = re.match(r"^\*\*(BR1_TS\d+) (.+)\*\* \((\w+)\)$", line)
        if block:
            current = OrderedDict(id=block.group(1), scenario=block.group(2), status=block.group(3), variants=[],
                                  notes=[])
            flows[current["id"]] = current
            continue
        start = re.match(r"^- \*(\w+)\* - flow `([^`]+)` - variant status (\w+)$", line)
        if start:
            variant = OrderedDict(kind=start.group(1), flow=start.group(2), status=start.group(3), legs=[], gaps=[])
            current["variants"].append(variant)
            continue
        leg = LEG.match(line)
        if leg:
            rest = leg.group(8) or ""
            variant["legs"].append(OrderedDict(
                number=int(leg.group(1)), role=leg.group(2), category=leg.group(3),
                codes=split_codes(leg.group(4), "/"), entries=split_codes(leg.group(5), "/"),
                message=leg.group(6).strip(), segments=split_codes(leg.group(7) or ""),
                carries=[OrderedDict(field=c[0].strip(), rule=c[1], rulePage=int(c[2]),
                                     citedPage=int(c[3]) if c[3] else None) for c in CARRY.findall(rest)]))
            continue
        gap = re.match(r"^\s*- GAP (.+)$", line)
        if gap:
            variant["gaps"].append(gap.group(1).strip())
            continue
        if line.startswith("- ") and current is not None:
            current["notes"].append(line[2:].strip())
    sme_lines = md_section(lines, "### Scenarios requiring SME confirmation", "### Excluded combinations (not scenarios)")
    sme = [dict(zip(["id", "scenario", "openPairs"], row)) for row in table_rows(sme_lines, "| BR1_TS")]
    exclusion_lines = md_section(lines, "### Excluded combinations (not scenarios)", "### Spec citations behind these scenarios")
    exclusions = [dict(zip(["combination", "blockingPairs", "evidence"], row))
                  for row in table_rows(exclusion_lines, "| ATL105 ")]
    citations = []
    for line in md_section(lines, "### Spec citations behind these scenarios"):
        cite = re.match(r'^-\s+(\S*)\s*p\.(\d+) \(([^)]*)\): "(.*)"$', line.strip())
        if cite:
            citations.append(OrderedDict(source=cite.group(1) or "(none)", page=int(cite.group(2)),
                                         anchor=cite.group(3), quote=cite.group(4)))
    return OrderedDict(header=header, summary=summary, scenarios=scenarios, flows=flows, sme=sme,
                       exclusions=exclusions, citations=citations)


def exclusion_dimensions(combination, entry_modes, categories):
    match = re.match(r"ATL105 MasterCard (Credit|Fleet) (.+)$", combination)
    program, rest = match.group(1), match.group(2)
    entry = next(mode for mode in sorted(entry_modes, key=len, reverse=True) if rest.startswith(mode + " "))
    category = rest[len(entry):].strip()
    if category not in categories:
        raise ValueError(f"Unknown excluded category {category!r} in {combination!r}")
    return program, entry, category


# ---------------------------------------------------------------- structural completeness

def check(rows, check_id, title, passed, expected, actual, detail="", severity_if_false="FAIL"):
    rows.append(OrderedDict(id=check_id, title=title, result="PASS" if passed else severity_if_false,
                            expected=str(expected), actual=str(actual), detail=detail))


def structural_checks(md, dev_rows, v4):
    checks = []
    summary = md["summary"][0]
    statuses = Counter(row["status"] for row in dev_rows)
    check(checks, "S01", "Scenario count reconciles (CSV rows = BR summary = scenario table)",
          len(dev_rows) == int(summary["Scenarios"]) == len(md["scenarios"]),
          summary["Scenarios"], f"CSV {len(dev_rows)}, table {len(md['scenarios'])}")
    actual = {"valid": statuses["valid"], "valid (conditional)": statuses["valid_conditional"],
              "requires SME": sum(1 for row in dev_rows if row["requires_sme"] == "yes"),
              "multi-leg": sum(1 for row in dev_rows if row["kind"] == "multi_leg"),
              "with gaps": sum(1 for row in dev_rows if row["gaps"])}
    expected = {key: int(summary[key]) for key in actual}
    check(checks, "S02", "BR summary counters reconcile with the scenario rows", actual == expected, expected, actual)
    display_ids = [row["br_display_id"] + "_TS" + str(i) for i, row in enumerate(dev_rows, 1)]
    unique = all(len({row[key] for row in dev_rows}) == len(dev_rows)
                 for key in ("scenario_display_id", "scenario_id", "source_leg_id"))
    check(checks, "S03", "Scenario IDs are unique and contiguous (BR1_TS1..BR1_TS108)",
          unique and display_ids == [row["scenario_display_id"] for row in dev_rows], "unique, contiguous",
          "unique, contiguous" if unique else "duplicates present")
    mismatches = []
    table = {row["id"]: row for row in md["scenarios"]}
    for row in dev_rows:
        line = table.get(row["scenario_display_id"])
        if (not line or line["scenario"] != row["scenario"] or line["status"] != row["status"]
                or line["rules"] != row["supporting_rules"] or line["entryMode"] != row["entry_mode_codes"]):
            mismatches.append(row["scenario_display_id"])
    check(checks, "S04", "CSV rows and the Markdown scenario table agree (name, status, entry codes, rule counts)",
          not mismatches, 0, len(mismatches), ", ".join(mismatches))
    entry_modes = sorted({row["entry_mode"] for row in dev_rows})
    categories = list(APPENDIX_G)
    included = {(row["card_program"], row["entry_mode"], row["txn_category"]) for row in dev_rows}
    excluded = [exclusion_dimensions(e["combination"], entry_modes, categories) for e in md["exclusions"]]
    universe = {(program, entry, category) for program in PROGRAMS.values() for entry in entry_modes
                for category in categories}
    overlap, missing = included & set(excluded), universe - included - set(excluded)
    check(checks, "S05", "Universe closes: 2 programs x 20 transaction categories x 8 entry modes = scenarios + exclusions",
          len(universe) == len(included) + len(set(excluded)) and not overlap and not missing
          and len(excluded) == len(set(excluded)) == int(summary["excluded"]),
          len(universe), f"{len(included)} scenarios + {len(set(excluded))} exclusions; overlap {len(overlap)}, "
          f"missing {len(missing)}")
    bad_codes = sorted({row["scenario_display_id"] for row in dev_rows
                        if row["txn_type_codes"] != APPENDIX_G[row["txn_category"]][0]})
    unresolved = [line for _, lines in APPENDIX_G.values() for line in lines
                  if not (find_pages(v4, line) and set(find_pages(v4, line)) & {545, 546})]
    check(checks, "S06", "Transaction type codes match Appendix G (pp.545-546) for every category",
          not bad_codes and not unresolved, "0 mismatches", f"{len(bad_codes)} mismatches; {len(unresolved)} unresolved",
          ", ".join(bad_codes + unresolved))
    bad_entries = sorted({code for row in dev_rows for code in row["entries"] if code not in APPENDIX_J_CODES})
    check(checks, "S07", "PAN entry mode codes are valid Appendix J (p.638) codes", not bad_entries, "0 invalid",
          len(bad_entries), ", ".join(bad_entries))
    bad_cards = sorted({row["scenario_display_id"] for row in dev_rows
                        if row["card_type_codes"] != CARD_TYPES[row["card_program"]]})
    check(checks, "S08", "Card type codes match Appendix E (020 Credit p.521, 080 MasterCard fleet p.522)", not bad_cards,
          "0 mismatches", len(bad_cards), ", ".join(bad_cards))
    bad_messages = []
    for row in dev_rows:
        emv = bool(set(row["entries"]) & EMV_ENTRY)
        if row["kind"] == "single_leg":
            expected_message = "EMV Financial Transaction Request" if emv else "Financial Transaction Request"
            if row["message"] != expected_message or (row["segments"] == ["130"]) != emv:
                bad_messages.append(row["scenario_display_id"])
        elif row["message"]:
            bad_messages.append(row["scenario_display_id"])
    check(checks, "S09", "Message family and Segment 130 follow the entry mode (EMV request only for chip reads)",
          not bad_messages, "0 mismatches", len(bad_messages), ", ".join(bad_messages))
    multi = [row["scenario_display_id"] for row in dev_rows if row["kind"] == "multi_leg"]
    preauth = [row["scenario_display_id"] for row in dev_rows if row["kind"] == "single_leg" and "preauthorized:" in row["flows"]]
    incomplete = [sid for sid in multi + preauth
                  if sid not in md["flows"] or not md["flows"][sid]["variants"]
                  or any(len(variant["legs"]) < 2 for variant in md["flows"][sid]["variants"])]
    check(checks, "S10", "Every multi-leg scenario and pre-authorized variant has an original and a follow-on leg",
          not incomplete, f"{len(multi)} multi-leg + {len(preauth)} pre-auth", f"{len(incomplete)} incomplete",
          ", ".join(incomplete))
    gaps = [(sid, gap) for sid, block in md["flows"].items() for variant in block["variants"] for gap in variant["gaps"]]
    distinct = Counter(gaps)
    check(checks, "S11", "No open flow gaps", not gaps, 0, f"{len(gaps)} gap(s) in {len({g[0] for g in gaps})} scenario(s)",
          "; ".join(f"{sid}{f' (x{n} variants)' if n > 1 else ''}: {gap}" for (sid, gap), n in distinct.items()), "WARN")
    decided = sum(1 for row in dev_rows if row["sme_decision"])
    check(checks, "S12", "SME decisions recorded for scenarios that require SME confirmation",
          decided >= actual["requires SME"], actual["requires SME"], decided,
          f"{actual['requires SME']} scenarios are 'unconfirmed'; sme_decision and sme_comment are empty for all "
          f"{len(dev_rows)}", "WARN")
    check(checks, "S13", "Dimension mapping is SME-approved", md["header"]["mappingStatus"] == "approved", "approved",
          f"{md['header']['mappingStatus']} ({md['header']['mappingNote']})", "Producer 'Validation: passed' is a claim "
          "under test, not approval.", "WARN")
    check(checks, "S14", "Supporting rules are delivered as traceable rule IDs", False, f"{summary['supporting rules']} rule IDs",
          "counts only (per-scenario 141-460)", "business_scenarios.csv and BUSINESS_REQUIREMENTS.md give rule counts "
          "but no rule IDs, anchors or text, so field-level coverage cannot be traced.", "WARN")
    return checks


# ---------------------------------------------------------------- citations and versions

def quote_fragments(quote):
    quote = re.sub(r"\s*\(original: [^)]*\)\s*$", "", quote)
    return [part.strip(" .:") for part in re.split(r"\s*\.\.\.\s*", quote) if len(part.strip(" .:")) >= 3]


def tokens_on_page(quote, page_text):
    words = [w for w in re.findall(r"[a-z0-9]+", norm(quote)) if len(w) >= 3 and w not in {"the", "and", "for"}]
    return bool(words) and all(w in page_text for w in words)


def citation_checks(md, v4, v3):
    rows = []
    for cite in md["citations"]:
        row = OrderedDict(source=cite["source"], page=cite["page"], anchor=cite["anchor"], quote=cite["quote"])
        if cite["source"].startswith("SRC-BUYPASS-PIPS"):
            row.update(status="EXTERNAL_SOURCE_UNAVAILABLE", v4Pages="", v3Pages="",
                       note="Petroleum Industry Processing Specifications are not in this repository.")
            rows.append(row)
            continue
        fragments = quote_fragments(cite["quote"])
        v4_pages = sorted(set.intersection(*[set(find_pages(v4, f)) for f in fragments])) if fragments else []
        v3_pages = sorted(set.intersection(*[set(find_pages(v3, f)) for f in fragments])) if fragments else []
        if cite["page"] in v4_pages:
            status, note = "VERIFIED_2026_4", ""
        elif fragments and all({cite["page"] - 1, cite["page"]} & set(find_pages(v4, f)) for f in fragments) \
                and cite["page"] in set(find_pages(v4, fragments[-1])):
            status, note = "VERIFIED_2026_4_SPANS_PAGE_BREAK", "Quoted list starts on the previous page."
        elif (" - " in cite["quote"] and ":" in cite["quote"]
              and tokens_on_page(cite["quote"].split(" - ")[0] + " " + cite["quote"].split(":")[-1],
                                 v4.get(cite["page"], ""))):
            status, note = "VERIFIED_PARAPHRASE_2026_4", "Table row paraphrased; all key tokens are on the cited page."
        elif v4_pages:
            status, note = "PAGE_MISMATCH", f"Quote is on 2026-4 page(s) {v4_pages}."
        elif v3_pages:
            status, note = "FOUND_ONLY_IN_2026_3", ""
        else:
            status, note = "NOT_FOUND", ""
        row.update(status=status, v4Pages=", ".join(map(str, v4_pages)), v3Pages=", ".join(map(str, v3_pages)), note=note)
        rows.append(row)
    return rows


FIELD_EVIDENCE = {"Sequence Number": ["sequence number"], "Terminal Identifier": ["same device"]}


def rule_reference_checks(md, v4, v3):
    refs = OrderedDict()
    for block in md["flows"].values():
        for variant in block["variants"]:
            for leg in variant["legs"]:
                for carry in leg["carries"]:
                    key = (carry["field"], carry["rule"], carry["citedPage"])
                    entry = refs.setdefault(key, OrderedDict(field=carry["field"], rule=carry["rule"],
                                                             rulePage=carry["rulePage"], citedPage=carry["citedPage"],
                                                             uses=0, scenarios=[]))
                    entry["uses"] += 1
                    if block["id"] not in entry["scenarios"]:
                        entry["scenarios"].append(block["id"])
    rows = []
    for entry in refs.values():
        words = FIELD_EVIDENCE.get(entry["field"], [entry["field"].lower()])
        pages = [entry["rulePage"]] + ([entry["citedPage"]] if entry["citedPage"] else [])
        on_v4 = {page: any(w in v4.get(page, "") for w in words) for page in pages}
        on_v3 = {page: any(w in v3.get(page, "") for w in words) for page in pages}
        if all(on_v4.values()):
            verdict = "2026-4 PAGINATION"
        elif all(on_v3.values()):
            verdict = "2026-3 PAGINATION (spec version mismatch)"
        else:
            verdict = "UNRESOLVED"
        rows.append(OrderedDict(field=entry["field"], rule=entry["rule"], rulePage=entry["rulePage"],
                                citedPage=entry["citedPage"] or "", uses=entry["uses"],
                                scenarios=len(entry["scenarios"]),
                                fieldOnPage2026_4=", ".join(f"p.{p}={'yes' if v else 'no'}" for p, v in on_v4.items()),
                                fieldOnPage2026_3=", ".join(f"p.{p}={'yes' if v else 'no'}" for p, v in on_v3.items()),
                                verdict=verdict))
    return rows


def version_checks(lines_v3, lines_v4):
    rows = []
    for name, start, end in VERSION_SECTIONS:
        page3, body3 = section_body(lines_v3, start, end)
        page4, body4 = section_body(lines_v4, start, end)
        rows.append(OrderedDict(section=name, page2026_3=page3, page2026_4=page4, lines=len(body4),
                                identical=bool(body3) and body3 == body4))
    return rows


# ---------------------------------------------------------------- exclusions and scenario verdicts

def exclusion_rows(md, dev_rows):
    entry_modes = sorted({row["entry_mode"] for row in dev_rows})
    rows = []
    for item in md["exclusions"]:
        program, entry, category = exclusion_dimensions(item["combination"], entry_modes, list(APPENDIX_G))
        bases, consistent = [], []
        evidence = item["evidence"]
        if "p.51 " in evidence or "fleet card" in evidence:
            bases.append("EXTERNAL_PIPS")
        if "p.83 " in evidence:
            bases.append("ATL105_10.1.2")
            consistent.append(category not in CREDIT_LIST)
        if "p.161 " in evidence:
            bases.append("ATL105_10.14.2.4")
            consistent.append(category not in EMV_LIST)
        if "p.695 " in evidence:
            bases.append("ATL105_APPENDIX_Q_DERIVED")
            consistent.append(category in MOTO)
        if not bases:
            bases.append("OTHER")
        if any(b.startswith("ATL105") for b in bases):
            verdict = "CONSISTENT_WITH_ATL105" if consistent and all(consistent) else "INCONSISTENT_WITH_ATL105"
        else:
            verdict = "EXTERNAL_UNVERIFIED" if "EXTERNAL_PIPS" in bases else "UNCLASSIFIED"
        rows.append(OrderedDict(combination=item["combination"], program=program, entryMode=entry, category=category,
                                blockingPairs=item["blockingPairs"], basis=", ".join(bases), verdict=verdict))
    return rows


def scenario_verdicts(dev_rows, md, old_pagination_rules):
    """Independent ATL105 verdict per Dev scenario; old_pagination_rules are BR-<page>-<n> IDs keyed to 2026-3 pages."""
    rows = []
    for row in dev_rows:
        sid, program, category, entries = row["scenario_display_id"], row["card_program"], row["txn_category"], row["entries"]
        emv = bool(set(entries) & EMV_ENTRY)
        issues, basis, conditions = [], [], []
        if program == "Credit":
            if category not in CREDIT_LIST:
                verdict = "NOT_SUPPORTED"
            elif emv and category not in EMV_LIST:
                verdict = "NOT_SUPPORTED"
            elif category in MOTO and not set(entries) <= CARD_NOT_PRESENT_ENTRY:
                verdict = "NOT_SUPPORTED"
            else:
                verdict = "SUPPORTED"
                basis.append(f"10.1.2 p.83 ({CREDIT_LIST[category]})")
                if emv:
                    basis.append("10.14.2.4 p.161")
                basis.append("Appendix J p.638 (" + "/".join(entries) + ")")
            if "10" in entries:
                conditions.append("Entry Mode 10 is not valid on the initial credential-storing transaction (App. J note 2)")
            if set(entries) & {"79", "80"}:
                conditions.append("Fallback only after a failed chip read (p.162)")
            if category in MOTO:
                conditions.append("Card-not-present (Appendix Q p.695)")
            if category in {"Purchase/Capture", "Authorization Only"}:
                issues.append("Partial Approval variant (Element 121 / Response F) not modelled (10.1.2.1 p.84)")
        else:
            verdict = "EXTERNAL_PIPS"
            basis.append("SRC-BUYPASS-PIPS-PDF-001 pp.51-53 (not in repository)")
            if "10" in entries:
                verdict = "CONFLICT"
                issues.append("Entry Mode 10 is listed for MasterCard Credit, not MasterCard Fleet (App. J note 2 p.638)")
            if emv:
                issues.append("MasterCard Enhanced Fleet EMV: Segment 145 Prompt Table and Purchase Restriction Method "
                              "not required (pp.289, 595)")
        block = md["flows"].get(sid)
        if block:
            for variant in block["variants"]:
                issues.extend(f"Flow gap: {gap}" for gap in variant["gaps"])
                follow = variant["legs"][-1]
                carried = {carry["field"] for carry in follow["carries"]}
                if follow["category"] == "Cancellation" and "Approval Number" not in carried:
                    issues.append("Approval Number (Element 5) not carried to the cancellation leg (10.1.2.2 p.85)")
                if "90" in variant["legs"][0]["entries"]:
                    issues.append("Follow-up of a 90 original must use entry mode 02 (App. J note 1 p.638); not stated")
                if any(c["rule"] in old_pagination_rules for c in follow["carries"]):
                    issues.append("Carried-field rule references use 2026-3 page numbers")
        if row["status"] == "valid" and verdict == "SUPPORTED":
            agreement = "AGREE"
        elif row["status"] == "unconfirmed" and verdict == "SUPPORTED":
            agreement = "ATL105_PERMITS_PENDING_SME"
        elif verdict == "CONFLICT":
            agreement = "CONFLICT"
        elif verdict == "EXTERNAL_PIPS":
            agreement = "UNVERIFIABLE_EXTERNAL_SOURCE"
        else:
            agreement = "DISAGREE"
        rows.append(OrderedDict(id=sid, scenario=row["scenario"], program=program, category=category,
                                entryMode=row["entry_mode"], txnCodes=row["txn_type_codes"],
                                entryCodes=row["entry_mode_codes"], cardType=row["card_type_codes"], kind=row["kind"],
                                devStatus=row["status"], requiresSme=row["requires_sme"] or "no",
                                openPairs=row["open_pairs"], supportingRules=int(row["supporting_rules"]),
                                independentVerdict=verdict, agreement=agreement, atl105Basis="; ".join(basis),
                                conditions="; ".join(conditions), issues="; ".join(dict.fromkeys(issues)),
                                trainedLinks=[]))
    return rows


# ---------------------------------------------------------------- curated evidence

class Delivery:
    def __init__(self, md_text, csv_text, md, rows):
        self.md_text, self.csv_text, self.md, self.rows = md_text, csv_text, md, rows
        self.searchable = md_text + "\n" + csv_text
        self.by_id = {row["scenario_display_id"]: row for row in rows}


def evidence_found(dev, item):
    pattern = re.compile(item["pattern"], re.M)
    if item["file"] == "md":
        return bool(pattern.search(dev.md_text))
    rows = [row for row in dev.rows if all(row.get(key) == value for key, value in item.get("where", {}).items())]
    hits = [bool(pattern.search(row[item["column"]])) for row in rows]
    return bool(rows) and (all(hits) if item.get("match") == "all" else any(hits))


def describe_evidence(item):
    scope = "" if item["file"] == "md" else f"{item['column']}" + (f" where {item['where']}" if item.get("where") else "")
    return f"{item['file'].upper()} {scope} /{item['pattern']}/".replace("  ", " ")


def apply_curated(curated, dev, entry, context):
    """Resolve a curated disposition; positive claims need Dev evidence and absence claims must survive a search."""
    disposition = entry["disposition"]
    if disposition not in DISPOSITIONS:
        raise ValueError(f"{context}: unknown disposition {disposition}")
    reason = curated["reasons"].get(entry["reason"])
    text = reason["text"] if reason else entry["reason"]
    absent = (reason.get("absent", []) if reason else []) + entry.get("absent", [])
    evidence = entry.get("evidence", [])
    if disposition in ("COVERED", "PARTIAL", "CONFLICT", "EXTERNAL_SOURCE") and not evidence:
        raise ValueError(f"{context}: {disposition} requires Dev evidence")
    missing = [item["pattern"] for item in evidence if not evidence_found(dev, item)]
    if missing:
        raise ValueError(f"{context}: Dev evidence not found {missing}; re-review the curated disposition")
    contradicted = [pattern for pattern in absent if re.search(pattern, dev.searchable)]
    if contradicted:
        raise ValueError(f"{context}: absence claim contradicted by the Dev delivery {contradicted}")
    return OrderedDict(disposition=disposition, reason=text,
                       evidence="; ".join(describe_evidence(item) for item in evidence),
                       absenceVerified="; ".join(absent))


def metrics(dispositions):
    counts = Counter(dispositions)
    applicable = sum(counts[name] for name in COUNTED)
    result = OrderedDict(total=len(dispositions), applicable=applicable)
    result.update((name, counts[name]) for name in DISPOSITIONS)
    result["coveredPct"] = pct(counts["COVERED"], applicable)
    result["coveredOrPartialPct"] = pct(counts["COVERED"] + counts["PARTIAL"], applicable)
    result["contextReachPct"] = pct(counts["COVERED"] + counts["PARTIAL"] + counts["CONTEXT_ONLY"], applicable)
    return result


def best(current, candidate):
    return candidate if current is None or RANK[candidate] < RANK[current] else current


# ---------------------------------------------------------------- ATL105 inventory coverage

def program_rows(dev, program):
    return [row for row in dev.rows if row["card_program"] == PROGRAMS[program]]


def program_variants(dev, program):
    return [(sid, variant) for sid, block in dev.md["flows"].items()
            if dev.by_id[sid]["card_program"] == PROGRAMS[program] for variant in block["variants"]]


def auto_disposition(check_spec, dev, program):
    rows, kind = program_rows(dev, program), check_spec["type"]

    def share(found, wanted, label):
        if not found:
            return "NOT_COVERED", f"no Dev {program.lower()} scenario with {label} {', '.join(wanted)}"
        if set(found) == set(wanted):
            return "COVERED", f"{label} {', '.join(wanted)} modelled"
        return "PARTIAL", f"{label} {', '.join(sorted(set(wanted) - set(found)))} not modelled"

    if kind == "card":
        hits = [row["scenario_display_id"] for row in rows if row["card_type_codes"] == check_spec["code"]]
        return ("COVERED" if hits else "NOT_COVERED"), f"card type {check_spec['code']} on {len(hits)} scenario(s)", hits
    if kind in ("entries", "codes"):
        field = "entries" if kind == "entries" else "codes"
        found = [code for code in check_spec["codes"] if any(code in row[field] for row in rows)]
        hits = [row["scenario_display_id"] for row in rows if set(row[field]) & set(check_spec["codes"])]
        return (*share(found, check_spec["codes"], "entry mode" if kind == "entries" else "transaction type"), hits)
    if kind in ("txn", "txn_entry"):
        category_rows = [row for row in rows if row["txn_category"] == check_spec["category"]]
        if kind == "txn":
            found = [code for code in check_spec["codes"] if any(code in row["codes"] for row in category_rows)]
            disposition, detail = share(found, check_spec["codes"], f"'{check_spec['category']}' code")
        else:
            found = [code for code in check_spec["entryCodes"] if any(code in row["entries"] for row in category_rows)]
            disposition, detail = share(found, check_spec["entryCodes"], f"'{check_spec['category']}' with entry mode")
        return disposition, detail, [row["scenario_display_id"] for row in category_rows]
    if kind == "emv_segment":
        singles = [row for row in rows if row["kind"] == "single_leg"]
        emv = [row for row in singles if set(row["entries"]) & EMV_ENTRY]
        legs = [leg for _, variant in program_variants(dev, program) for leg in variant["legs"]
                if leg["role"] == "original" and set(leg["entries"]) & EMV_ENTRY]
        ok = (emv and all(row["segments"] == ["130"] and row["message"] == "EMV Financial Transaction Request" for row in emv)
              and all("130" not in row["segments"] for row in singles if row not in emv)
              and all(leg["segments"] == ["130"] for leg in legs))
        detail = f"{len(emv)} EMV single-leg scenarios and {len(legs)} EMV original legs carry Segment 130"
        return ("COVERED" if ok else "PARTIAL"), detail, [row["scenario_display_id"] for row in emv]
    if kind == "flow":
        matches = [(sid, variant) for sid, variant in program_variants(dev, program)
                   if set(variant["legs"][0]["codes"]) & set(check_spec["from"])
                   and set(variant["legs"][-1]["codes"]) & set(check_spec["to"])]
        if not matches:
            return "NOT_COVERED", f"no {' / '.join(check_spec['from'])} -> {' / '.join(check_spec['to'])} flow", []
        problems = []
        modelled = set().union(*[set(variant["legs"][0]["codes"]) for _, variant in matches]) & set(check_spec["from"])
        if modelled != set(check_spec["from"]):
            problems.append(f"original code(s) {', '.join(sorted(set(check_spec['from']) - modelled))} not modelled")
        gaps = sorted({gap for _, variant in matches for gap in variant["gaps"]})
        problems.extend(f"gap: {gap}" for gap in gaps)
        for carry in check_spec.get("carries", []):
            lacking = sorted({sid for sid, variant in matches
                              if carry not in {c["field"] for c in variant["legs"][-1]["carries"]}})
            if lacking:
                problems.append(f"{carry} not carried in {len(lacking)} scenario(s)")
        sids = sorted({sid for sid, _ in matches}, key=lambda s: int(s.split("TS")[1]))
        detail = f"{len(matches)} variant(s) in {len(sids)} scenario(s)" + (("; " + "; ".join(problems)) if problems else "")
        return ("PARTIAL" if problems else "COVERED"), detail, sids
    raise ValueError(f"Unknown check type {kind}")


def verify_sources(item, v4, v3):
    refs, pages3 = [], set()
    for source in item["sources"]:
        for quote in source["quotes"]:
            if source["page"] not in find_pages(v4, quote):
                raise ValueError(f"{item['id']}: quote not on ATL105 2026-4 p.{source['page']}: {quote!r}")
            pages3.update(find_pages(v3, quote))
        refs.append(f"{source['section']} p.{source['page']}")
    return "; ".join(refs), ", ".join(map(str, sorted(pages3)))


def spec_coverage(curated, dev, v4, v3):
    rows = []
    for item in curated["specInventory"]:
        refs, pages3 = verify_sources(item, v4, v3)
        for program in item["programs"]:
            check_spec = item["check"]
            if check_spec["type"] == "curated":
                entry = check_spec.get("byProgram", {}).get(program, check_spec)
                resolved = apply_curated(curated, dev, entry, f"{item['id']}/{program}")
                disposition, detail, sids = resolved["disposition"], resolved["reason"], []
                evidence, absence = resolved["evidence"], resolved["absenceVerified"]
            else:
                disposition, detail, sids = auto_disposition(check_spec, dev, program)
                evidence, absence = f"computed from the Dev delivery ({check_spec['type']})", ""
            rows.append(OrderedDict(id=item["id"], group=item["group"], program=program, title=item["title"],
                                    atl105Reference=refs, sameQuote2026_3Pages=pages3, disposition=disposition,
                                    detail=detail, devScenarios=", ".join(sids), evidence=evidence,
                                    absenceVerifiedBySearch=absence, note=item.get("note", "")))
    return rows


def spec_summary(rows):
    summary = OrderedDict()
    for program in list(PROGRAMS) + ["COMBINED"]:
        selected = [row for row in rows if program == "COMBINED" or row["program"] == program]
        groups = OrderedDict()
        for row in selected:
            groups.setdefault(row["group"], []).append(row["disposition"])
        summary[program] = OrderedDict(overall=metrics([row["disposition"] for row in selected]),
                                       groups=OrderedDict((g, metrics(d)) for g, d in groups.items()))
    return summary


# ---------------------------------------------------------------- trained solution coverage

PROMPT_BRS = ["BR-SEG100-E78-001", "BR-SEG100-E78-002", "BR-RULE-SEG100-R-018", "BR-RULE-SEG100-R-033",
              "BR-SEG100-APPE-PROMPT-COMPOSITION", "BR-SEG100-ANN-G", "BR-SEG100-ANN-E", "BR-SEG100-APPE-FINANCIAL-CARD-TYPES"]
EMV_BRS = ["BR-RULE-SEG100-R-002", "BR-SEG100-EMV-NEG-001", "BR-RULE-SEG130-R-001", "BR-SEG100-APPR-EMV-COMPANION",
           "BR-SEG100-EMV-001", "BR-SEG100-ANN-R"]
# Explicit Dev scenario -> trained BR crosswalk: a covered/partial trained BR links to every Dev scenario whose codes,
# entry modes or modelled flow legs express it. Rules are structural, never text similarity.
LINK_RULES = ([(["BR-APPG-CODE-" + code], (lambda code: lambda p: code in p["codes"])(code)) for code in "0345678BCSUZ"]
              + [(PROMPT_BRS, lambda p: True),
                 (EMV_BRS, lambda p: p["emv"]),
                 (["BR-RULE-SEG130-R-019"], lambda p: bool(p["entries"] & {"79", "80", "02", "90"})),
                 (["BR-RULE-SEG130-R-017"], lambda p: p["emv"] and bool(p["follow"] & {"8", "Z", "S"})),
                 (["BR-SEG100-E86-002"], lambda p: "Sequence Number" in p["carries"]),
                 (["BR-SEG100-LIFE-AUTH-COMPLETE"], lambda p: "preauthorized" in p["kinds"]),
                 (["BR-SEG100-LIFE-SALE-VOID"], lambda p: "8" in p["follow"]),
                 (["BR-SEG100-LIFE-AUTH-VOID"], lambda p: "S" in p["follow"]),
                 (["BR-SEG100-LIFE-REFUND-VOID"], lambda p: "U" in p["follow"]),
                 (["BR-SEG100-LIFE-TIMEOUT-TOR"], lambda p: "Z" in p["follow"]),
                 (["BR-RULE-SEG100-R-037", "BR-SEG100-LIFE-001", "BR-RULE-SEG100-R-023", "BR-SEG100-ANN-J"],
                  lambda p: bool(p["kinds"])),
                 (["BR-RULE-SEG145-R-001", "BR-RULE-SEG145-R-003"], lambda p: p["program"] == "Fleet" and p["emv"])])


def scenario_profile(row, md):
    variants = md["flows"].get(row["scenario_display_id"], {}).get("variants", [])
    codes = set(row["codes"]).union(*[set(leg["codes"]) for variant in variants for leg in variant["legs"]])
    return {"codes": codes, "entries": set(row["entries"]), "emv": bool(set(row["entries"]) & EMV_ENTRY),
            "program": row["card_program"], "kinds": {variant["kind"] for variant in variants},
            "follow": {code for variant in variants for code in variant["legs"][-1]["codes"]
                       if variant["kind"] != "preauthorized"},
            "carries": {carry["field"] for variant in variants for carry in variant["legs"][-1]["carries"]}}


def trained_coverage(curated, dev, training, network_rows):
    scenarios = training["businessContext"]["scenarios"]
    if [s["id"] for s in scenarios if s["id"] not in curated["trainedScenarios"]] or \
            set(curated["trainedScenarios"]) - {s["id"] for s in scenarios}:
        raise ValueError("Curated trained-scenario dispositions do not match the training package scenarios")
    titles = {br["id"]: br.get("title", "") for br in training["businessRequirements"]}
    titles.update({br["id"]: br.get("title", "") for br in training["reviewQueue"]})
    status = {br["id"]: "TRAINED" for br in training["businessRequirements"]}
    status.update({br["id"]: "REVIEW_QUEUE" for br in training["reviewQueue"]})
    unknown = set(curated["trainedBrOverrides"]) - set(titles)
    if unknown:
        raise ValueError(f"Curated BR overrides reference unknown BRs: {sorted(unknown)}")
    scenario_rows, br_scenarios, br_fr, inherited = [], defaultdict(list), {}, {}
    for scenario in scenarios:
        resolved = apply_curated(curated, dev, curated["trainedScenarios"][scenario["id"]], scenario["id"])
        scenario_rows.append(OrderedDict(
            id=scenario["id"], title=scenario["title"], fr=scenario["functionalRequirementId"], origin=scenario["origin"],
            trainedCoverageStatus=scenario["coverageStatus"], trainingState=scenario["trainingState"],
            trainedBrs=len(scenario["testSolutionRequirementIds"]), devDisposition=resolved["disposition"],
            reason=resolved["reason"], evidence=resolved["evidence"], absenceVerifiedBySearch=resolved["absenceVerified"]))
        for br in scenario["testSolutionRequirementIds"]:
            br_scenarios[br].append(scenario["id"])
            br_fr.setdefault(br, scenario["functionalRequirementId"])
            inherited[br] = best(inherited.get(br), resolved["disposition"])
    if set(br_scenarios) != set(titles):
        raise ValueError("Training package BRs and scenario links disagree")
    br_rows = []
    for br in sorted(titles):
        if br in curated["trainedBrOverrides"]:
            resolved = apply_curated(curated, dev, curated["trainedBrOverrides"][br], br)
            basis = "BR-level curated disposition"
        else:
            resolved = OrderedDict(disposition=inherited[br], reason="Inherited from business scenario(s) "
                                   + ", ".join(br_scenarios[br]), evidence="", absenceVerified="")
            basis = "inherited"
        br_rows.append(OrderedDict(id=br, title=titles[br], fr=br_fr[br], trainedStatus=status[br],
                                   scenarios=", ".join(br_scenarios[br]), devDisposition=resolved["disposition"],
                                   basis=basis, reason=resolved["reason"], evidence=resolved["evidence"],
                                   devScenarios=[]))
    mastercard = [row for row in network_rows if row["Network / Generic Group"] == "MASTERCARD"]
    if {row["BR ID (contextual draft)"] for row in mastercard} != set(curated["contextualBrs"]):
        raise ValueError("Curated contextual BR dispositions do not match the MASTERCARD all-network group")
    contextual_rows = []
    for row in mastercard:
        resolved = apply_curated(curated, dev, curated["contextualBrs"][row["BR ID (contextual draft)"]],
                                 row["BR ID (contextual draft)"])
        contextual_rows.append(OrderedDict(
            id=row["BR ID (contextual draft)"], program=row["Card Program"], category=row["Transaction Category"],
            codes=row["Transaction Codes (conditional)"], role=row["Lifecycle Role"],
            requirement=row["Business Requirement"].split("hold: ", 1)[-1], devDisposition=resolved["disposition"],
            reason=resolved["reason"], evidence=resolved["evidence"], absenceVerifiedBySearch=resolved["absenceVerified"]))
    return scenario_rows, br_rows, contextual_rows


def link_trained(dev, dev_scenario_rows, br_rows):
    """Attach explicitly expressed trained BRs to Dev scenarios; report covered BRs without a structural link."""
    by_br = {row["id"]: row for row in br_rows}
    for rule_brs, _ in LINK_RULES:
        for br in rule_brs:
            if br not in by_br or by_br[br]["devDisposition"] not in ("COVERED", "PARTIAL"):
                raise ValueError(f"Crosswalk rule references {br}, which is not a COVERED/PARTIAL trained BR")
    for row in dev_scenario_rows:
        profile = scenario_profile(dev.by_id[row["id"]], dev.md)
        links = [br for rule_brs, predicate in LINK_RULES if predicate(profile) for br in rule_brs]
        row["trainedLinks"] = sorted(dict.fromkeys(links))
        for br in row["trainedLinks"]:
            by_br[br]["devScenarios"].append(row["id"])
    return sorted(br for br, row in by_br.items()
                  if row["devDisposition"] in ("COVERED", "PARTIAL") and not row["devScenarios"])


def trained_summary(scenario_rows, br_rows, contextual_rows):
    frs = OrderedDict()
    for row in scenario_rows:
        frs.setdefault(row["fr"], {"scenarios": [], "brs": []})["scenarios"].append(row["devDisposition"])
    for row in br_rows:
        frs.setdefault(row["fr"], {"scenarios": [], "brs": []})["brs"].append(row["devDisposition"])
    return OrderedDict(
        businessScenarios=metrics([row["devDisposition"] for row in scenario_rows]),
        trainedEvidencedScenarios=metrics([row["devDisposition"] for row in scenario_rows
                                           if row["trainedCoverageStatus"] != "NOT_IN_TEST_SOLUTION"]),
        templateScenarios=metrics([row["devDisposition"] for row in scenario_rows if row["origin"] == "TEMPLATE"]),
        testSolutionScenarios=metrics([row["devDisposition"] for row in scenario_rows if row["origin"] != "TEMPLATE"]),
        businessRequirements=metrics([row["devDisposition"] for row in br_rows]),
        trainedBusinessRequirements=metrics([row["devDisposition"] for row in br_rows if row["trainedStatus"] == "TRAINED"]),
        contextualBusinessRequirements=metrics([row["devDisposition"] for row in contextual_rows]),
        functionalRequirements=OrderedDict((fr, OrderedDict(scenarios=metrics(v["scenarios"]), brs=metrics(v["brs"])))
                                           for fr, v in sorted(frs.items())))


# ---------------------------------------------------------------- findings

def finding(rows, severity, klass, title, detail, references, affected, recommendation, owner):
    rows.append(OrderedDict(id=f"F-{len(rows) + 1:02d}", severity=severity, findingClass=klass, title=title,
                            detail=detail, references=references, affected=affected, recommendation=recommendation,
                            owner=owner))


def ids(rows, predicate, key="id"):
    return [row[key] for row in rows if predicate(row)]


def compact(values, limit=12):
    values = list(dict.fromkeys(values))
    return ", ".join(values[:limit]) + (f" (+{len(values) - limit} more)" if len(values) > limit else "")


def build_findings(report):
    rows, spec, verdicts = [], report["specCoverage"], report["devScenarios"]
    trained, contextual = report["trainedScenarios"], report["contextualBrs"]
    disposition = {(row["id"], row["program"]): row["disposition"] for row in spec}
    fr = report["trainedSummary"]["functionalRequirements"]
    partial_scenarios = ids(verdicts, lambda r: "Partial Approval variant" in r["issues"])
    finding(rows, "HIGH", "MISSING_REQUIREMENT", "Partial Approval is not modelled",
            "ATL105 10.1.2 lists Partial Approval as a supported credit transaction (POS Purchase, POS and CAT "
            "Authorization Only; Element 121 = 1 -> Response Code F with Approved Amount, reversal = approved amount, "
            f"TOR = requested amount). Dev has no scenario, flow or rule for it. Trained FR-006: "
            f"{fr['FR-006']['brs']['COVERED']}/{fr['FR-006']['brs']['applicable']} BRs covered.",
            "ATL105 10.1.2 p.83, 10.1.2.1 p.84; SPEC-TXN-11, SPEC-FLOW-09; trained TS-026, TS-027, "
            "CTX-BR-MASTERCARD-CREDIT-CREDIT-PARTIAL", compact(partial_scenarios),
            "Add Partial Approval scenarios for POS Purchase (0), POS Authorization Only (3) and CAT Authorization Only "
            "(5), including the partially approved reversal and TOR amount rules.", "Dev Team")
    no_approval = ids(verdicts, lambda r: "Approval Number (Element 5) not carried" in r["issues"])
    finding(rows, "HIGH", "DEFECT", "Authorization Only Reversal (Cancellation S) does not carry the Approval Number",
            "Section 10.1.2.2 requires Element 5 (Approval Number) and Element 86 (Sequence Number) from the original "
            "authorization to be present and identical; Dev carries only the Sequence Number. The trained Approval "
            "Number capture/retention scenarios (TS-022, TS-023) are therefore NOT_COVERED.",
            "ATL105 10.1.2.2 p.85; SPEC-FLOW-02; trained TS-022, TS-023, CTX-BR-MASTERCARD-CREDIT-FLOW-AUTH_CANCELLATION",
            compact(no_approval), "Add the Approval Number carry to every Cancellation leg and name the category "
            "'Authorization Only Reversal (Cancellation, S)'.", "Dev Team")
    old = [row for row in report["ruleReferences"] if row["verdict"].startswith("2026-3")]
    identical = sum(1 for row in report["versionChecks"] if row["identical"])
    old_scenarios = sorted({s for r in old for s in r["scenarioIds"]}, key=lambda s: int(s.split("TS")[1]))
    finding(rows, "HIGH", "PROVENANCE_OR_VERSION_GAP", "BR claims ATL105 2026-4 but its carried-field rule IDs use 2026-3 pages",
            f"{len(old)} rule IDs ({', '.join(r['rule'] for r in old)}; {sum(r['uses'] for r in old)} uses) resolve only "
            f"on 2026-3 pages (e.g. BR-402-* = 2026-3 p.402 Element 86; 2026-4 p.402 is Element 80). BR-97-7 is taken "
            f"from the debit section (2026-3 p.97). The {identical}/{len(report['versionChecks'])} business sections "
            f"compared are textually identical in both versions, so content is unaffected, but the storage policy "
            f"blocks CONFIRMED coverage until citations are corrected.",
            "ARTIFACT-STORAGE-POLICY.md (AI run intake rule 6); version check table", compact(old_scenarios),
            "Rebuild the Dev knowledge base on 2026-4 or cite the 2026-3 pages explicitly; re-issue rule IDs.", "Dev Team")
    context_only = report["trainedSummary"]["businessScenarios"]["CONTEXT_ONLY"]
    finding(rows, "HIGH", "TRACEABILITY_GAP", "The 785 supporting rules are delivered as counts only",
            f"Each scenario lists a rule count (141-460) but no rule IDs, anchors or text. {context_only} trained business "
            f"scenarios and {report['trainedSummary']['businessRequirements']['CONTEXT_ONLY']} trained BRs fall in a Dev "
            "context without any stated requirement, so field-level coverage (Segment 100/111/130/131, amounts, "
            "responses) cannot be verified.", "S14; trained scenarios with CONTEXT_ONLY", "all 108 scenarios",
            "Deliver the per-scenario rule list (rule ID, ATL105 section/page/element, statement) so it can be "
            "cross-walked to the 601-rule Test Solution catalogue.", "Dev Team")
    mc_gaps = [row for row in spec if row["group"] == "MASTERCARD_SPECIFIC" and row["disposition"] in ("NOT_COVERED", "CONTEXT_ONLY")]
    mc_gaps += [row for row in spec if row["id"] in ("SPEC-EMV-11", "SPEC-ENTRY-16")]
    mc_gaps = list(OrderedDict((row["id"], row) for row in mc_gaps).values())
    finding(rows, "HIGH", "MISSING_REQUIREMENT", "Mastercard-specific requirements are absent",
            "No Dev scenario or rule expresses: " + "; ".join(f"{r['title']} ({r['atl105Reference']})" for r in mc_gaps) + ".",
            ", ".join(sorted({r["id"] for r in mc_gaps})), "MasterCard Credit and Fleet BR",
            "Add Mastercard-specific BRs/scenarios (stored credential MIT/CIT, token/DSRP, UCAF/3DS, incremental "
            "authorization, preauthorization vs final presentment, Transaction Link ID, EMV PIN, mPOS, Fleet EMV data).",
            "Dev Team")
    cr_gaps = [row for row in spec if row["group"] == "CREDIT_PROCESSING_REQUIREMENTS" and row["disposition"] != "COVERED"]
    cr_gaps = list(OrderedDict((row["id"], row) for row in cr_gaps).values())
    finding(rows, "MEDIUM", "MISSING_REQUIREMENT", "Section 10.1 credit processing requirements are not modelled",
            "; ".join(f"{r['title']} [{r['disposition']}]" for r in cr_gaps) + ".",
            ", ".join(sorted({r["id"] for r in cr_gaps})), "MasterCard Credit scenarios",
            "Add AVS, bill-payment/recurring/installment, healthcare (FSA/HRA), receipt and data-storage requirements "
            "where in scope, or record an explicit scope exclusion.", "Dev Team / BA")
    moto = [row for row in spec if row["id"] in ("SPEC-FLOW-04", "SPEC-FLOW-07")]
    finding(rows, "MEDIUM", "DEFECT", "Mail/Phone lifecycle flows are incomplete",
            "; ".join(f"{r['title']}: {r['detail']}" for r in moto),
            "ATL105 2.3 p.39, Appendix G p.546; SPEC-FLOW-04, SPEC-FLOW-07",
            compact(ids(verdicts, lambda r: "Flow gap" in r["issues"])),
            "Add the MOTO_REVERSAL flow template (leg-2 message and carried fields) and a TOR template whose original "
            "leg accepts Transaction Type 6.", "Dev Team")
    finding(rows, "MEDIUM", "MISSING_REQUIREMENT", "Time-out handling and queued TOR ordering are not modelled",
            "Dev models original -> TOR only. The 30-second response interval, late-response handling and the rule that "
            "a queued TOR precedes the next financial request from the same device are absent.",
            "ATL105 2.3.1 p.40; SPEC-FLOW-08, SPEC-FLOW-10; trained TS-028, TS-029, TS-032, CTX TOR-ORDER, TIMEOUT_TOR_NEXT",
            compact(ids(verdicts, lambda r: r["category"] == "Time-out Reversal")),
            "Add a timeout -> TOR -> next-financial-request flow and the timer/late-response requirements.", "Dev Team")
    entry_9002 = ids(verdicts, lambda r: "Follow-up of a 90 original" in r["issues"])
    finding(rows, "MEDIUM", "DEFECT", "Follow-up entry mode rule (90 original -> 02 follow-up) is not stated",
            "Appendix J note 1 (naming MasterCard and MasterCard Fleet) requires voids/completions of a PAN Entry Mode 90 "
            "original to send 02; Dev lists 02/90 on both legs.", "ATL105 Appendix J p.638; SPEC-ENTRY-14; trained TS-114",
            compact(entry_9002), "State the entry-mode persistence rule and its 90 -> 02 exception on every follow-up leg.",
            "Dev Team")
    fleet_cof = ids(verdicts, lambda r: r["independentVerdict"] == "CONFLICT")
    finding(rows, "HIGH", "CONFLICT", "MasterCard Fleet Credential on File scenarios conflict with Appendix J note 2",
            "Entry Mode 10 is applicable to Visa Credit, MasterCard Credit, Discover Credit and Amex; MasterCard Fleet is "
            "not listed, yet Dev keeps Fleet Credential on File scenarios (unconfirmed).",
            "ATL105 Appendix J p.638; SPEC-ENTRY-15 (FLEET)", compact(fleet_cof),
            "Exclude Fleet x Credential on File or obtain an SME/PIPS decision.", "Dev Team / SME")
    fleet_emv = ids(verdicts, lambda r: "Enhanced Fleet EMV" in r["issues"])
    finding(rows, "MEDIUM", "MISSING_REQUIREMENT", "MasterCard Enhanced Fleet EMV requirements are incomplete",
            "Dev cites the Prompt-Table-only rule but its Fleet EMV scenarios require Segment 130 only; the mandatory "
            "Purchase Restriction Method (auth/pre-auth/completion) and the production availability notice are absent.",
            "ATL105 12.31 p.289, Appendix I p.595; SPEC-MC-07, SPEC-MC-08; trained TS-086, TS-087, TS-088",
            compact(fleet_emv), "Add Segment 145 (Prompt Table) and Purchase Restriction Method to Fleet EMV scenarios.",
            "Dev Team")
    external = ids(verdicts, lambda r: r["program"] == "Fleet")
    fleet_ex = sum(1 for row in report["exclusions"] if row["verdict"] == "EXTERNAL_UNVERIFIED")
    finding(rows, "MEDIUM", "EXTERNAL_SOURCE", "MasterCard Fleet scope rests on a source that is not in the repository",
            f"ATL105 10.6 delegates fleet processing to the Petroleum Industry Processing Specifications. All "
            f"{len(external)} Fleet scenarios, {fleet_ex} Fleet exclusions and "
            f"{sum(1 for c in report['citations'] if c['status'] == 'EXTERNAL_SOURCE_UNAVAILABLE')} citations rely on "
            "SRC-BUYPASS-PIPS-PDF-001 pp.31-53, which cannot be verified here.", "ATL105 10.6 p.123; SPEC-FLEET-01",
            f"{len(external)} Fleet scenarios", "Add the PIPS source to docs/specs or have the BA confirm the Fleet scope.",
            "BA / Test Solution team")
    permits = ids(verdicts, lambda r: r["agreement"] == "ATL105_PERMITS_PENDING_SME")
    finding(rows, "MEDIUM", "SME_REQUIRED", "85 scenarios await SME confirmation and the dimension mapping is unapproved",
            f"sme_decision is empty for all 108 scenarios and the mapping was built with --allow-unapproved. Independent "
            f"reading: {len(permits)} unconfirmed Credit scenarios are permitted by ATL105 (transaction listed in 10.1.2, "
            "valid Appendix J entry mode, no restriction found) and can be confirmed; the Fleet ones need PIPS.",
            "S12, S13; dev-scenario-validation.csv (agreement)", compact(permits),
            "Run the SME session with the ATL105-permitted list first; record decisions in sme_decision/sme_comment.",
            "BA / SME")
    finding(rows, "LOW", "MISSING_REQUIREMENT", "PAN Entry Mode 04 (bar code / QR) is not modelled",
            "Appendix J lists 04 (Bar Code Scan / OCR) and Appendix I defines Mastercard QR (Pay by Link, X QR); Dev's "
            "eight entry modes omit it without an exclusion.", "ATL105 Appendix J p.638, Appendix I p.624; SPEC-ENTRY-03, "
            "SPEC-MC-12", "-", "Add Entry Mode 04 scenarios or record a scope exclusion.", "Dev Team / BA")
    finding(rows, "LOW", "SME_REQUIRED", "Mail/Phone Authorization Only (B) follow-ups disagree with the Test Solution draft",
            "Dev reverses B with Mail/Phone Reversal C and has no B completion; the Test Solution draft lists B -> "
            "Cancellation S and B -> Completion 0. ATL105 10.1.2.2 names only codes 3 and 5 for Authorization Only "
            "Reversal.", "Appendix G p.546, 10.1.2.2 p.85; CTX FLOW-AUTH_CANCELLATION, FLOW-AUTH_COMPLETION",
            compact(ids(verdicts, lambda r: r["category"] == "Mail/Phone Reversal")),
            "BA/SME to decide the B lifecycle; align Dev flows and the Test Solution draft.", "BA / SME")
    finding(rows, "LOW", "NAMING", "Authorization Only Reversal is named 'Cancellation'",
            "The S scenarios implement ATL105 10.1.2.2 Authorization Only Reversal; the 10.1.2 name is not used, which "
            "hides the requirement from readers of the BR.", "ATL105 10.1.2 p.83, Appendix G p.546; SPEC-TXN-12",
            compact(ids(verdicts, lambda r: r["category"] == "Cancellation")),
            "Label the category 'Authorization Only Reversal (Cancellation, S)'.", "Dev Team")
    bad_cites = [c for c in report["citations"] if c["status"] in ("PAGE_MISMATCH", "NOT_FOUND", "FOUND_ONLY_IN_2026_3")]
    if bad_cites:
        finding(rows, "LOW", "PROVENANCE_OR_VERSION_GAP", "Some Dev citations do not resolve on the cited 2026-4 page",
                "; ".join(f"p.{c['page']} {c['anchor']}: {c['status']} {c['note']}" for c in bad_cites),
                "citation-verification.csv", "-", "Correct the cited pages.", "Dev Team")
    na_scenarios = ids(trained, lambda r: r["devDisposition"] == "NOT_APPLICABLE")
    na_brs = ids(report["trainedBrs"], lambda r: r["devDisposition"] == "NOT_APPLICABLE")
    finding(rows, "INFO", "TEST_SOLUTION_SCOPE", "Trained Mastercard Credit Purchase package contains non-Mastercard items",
            f"Excluded from the denominator as NOT_APPLICABLE: scenarios {', '.join(na_scenarios)}; BRs {', '.join(na_brs)} "
            "(load-flow, Account Verification A, Accel/STAR, Money Code/Comdata).", "mc-credit-purchase-training-package.json",
            f"{len(na_scenarios)} scenarios, {len(na_brs)} BRs", "Re-scope the training context.", "Test Solution team")
    finding(rows, "INFO", "TEST_SOLUTION_SCOPE", "The trained solution has no MasterCard Fleet context",
            "Only the Segment 145 rules (TS-086 to TS-089) touch MasterCard Enhanced Fleet; the 40 Dev Fleet scenarios "
            "cannot be measured against a trained Fleet baseline.", "training-status.json; all-network review",
            f"{len(external)} Fleet scenarios", "Train a MasterCard Fleet context once PIPS is available.",
            "Test Solution team")
    order = ("HIGH", "MEDIUM", "LOW", "INFO")
    rows.sort(key=lambda f: order.index(f["severity"]))
    for number, row in enumerate(rows, 1):
        row["id"] = f"F-{number:02d}"
    return rows


def outcome(findings):
    blocking = [f for f in findings if f["severity"] == "HIGH" and f["findingClass"] in
                ("DEFECT", "CONFLICT", "PROVENANCE_OR_VERSION_GAP", "TRACEABILITY_GAP")]
    if blocking:
        return "REJECTED_NOT_INTAKE_READY"
    if any(f["severity"] == "HIGH" for f in findings):
        return "REVIEW_REQUIRED"
    return "ACCEPTED_WITH_GAPS" if findings else "ACCEPTED"


# ---------------------------------------------------------------- report

def build_report():
    curated = json.loads(read_text(CURATED))
    md_bytes, csv_bytes = read_bytes(DEV_MD), read_bytes(DEV_CSV)
    pinned = {"BUSINESS_REQUIREMENTS.md": sha256(md_bytes), "business_scenarios.csv": sha256(csv_bytes)}
    if pinned != curated["reviewedInputs"]:
        raise SystemExit("The Dev delivery differs from the reviewed bytes "
                         f"(expected {curated['reviewedInputs']}, found {pinned}); re-review the curated dispositions.")
    md_text, csv_text = md_bytes.decode("utf-8-sig"), csv_bytes.decode("utf-8-sig")
    md, dev_rows = parse_dev_md(md_text), parse_dev_csv(csv_text)
    dev = Delivery(md_text, csv_text, md, dev_rows)
    v4, lines_v4 = load_spec(SPEC_V4)
    v3, lines_v3 = load_spec(SPEC_V3)
    training = json.loads(read_text(TRAINING))
    network_rows = list(csv.DictReader(io.StringIO(read_text(ALL_NETWORK))))

    checks = structural_checks(md, dev_rows, v4)
    citations = citation_checks(md, v4, v3)
    rule_refs = rule_reference_checks(md, v4, v3)
    for row in rule_refs:
        row["scenarioIds"] = sorted({block["id"] for block in md["flows"].values() for variant in block["variants"]
                                     for carry in variant["legs"][-1]["carries"] if carry["rule"] == row["rule"]},
                                    key=lambda s: int(s.split("TS")[1]))
    versions = version_checks(lines_v3, lines_v4)
    exclusions = exclusion_rows(md, dev_rows)
    verdicts = scenario_verdicts(dev_rows, md, {r["rule"] for r in rule_refs if r["verdict"].startswith("2026-3")})
    spec_rows = spec_coverage(curated, dev, v4, v3)
    trained_rows, br_rows, contextual_rows = trained_coverage(curated, dev, training, network_rows)
    unlinked = link_trained(dev, verdicts, br_rows)
    if unlinked:
        raise ValueError(f"COVERED/PARTIAL trained BRs without a Dev scenario link: {unlinked}")
    summary = md["summary"][0]
    report = OrderedDict(
        reviewId=curated["reviewId"], title=curated["title"], status=curated["status"], reviewer=curated["reviewer"],
        reviewedOn=curated["reviewedOn"], specification=curated["specification"],
        specificationVersion=curated["specificationVersion"], trainedBaselineVersion=curated["trainedBaselineVersion"],
        generator="scripts/validate-dev-br-mastercard-coverage.py",
        inputs=[OrderedDict(role=role, path=rel(path), sha256=sha256(read_bytes(path))) for role, path in (
            ("Dev BR document", DEV_MD), ("Dev business scenarios", DEV_CSV), ("Curated review", CURATED),
            ("ATL105 2026-4 text", SPEC_V4), ("ATL105 2026-3 text", SPEC_V3),
            ("Trained MC Credit Purchase package", TRAINING), ("Trained all-network review", ALL_NETWORK))],
        devDelivery=OrderedDict(header=md["header"], summary=summary,
                                programs=dict(Counter(r["card_program"] for r in dev_rows)),
                                categories=len({r["txn_category"] for r in dev_rows}),
                                entryModes=len({r["entry_mode"] for r in dev_rows}),
                                flowVariants=sum(len(b["variants"]) for b in md["flows"].values())),
        structuralChecks=checks, citations=citations, ruleReferences=rule_refs, versionChecks=versions,
        exclusions=exclusions, devScenarios=verdicts, specCoverage=spec_rows, specSummary=spec_summary(spec_rows),
        trainedScenarios=trained_rows, trainedBrs=br_rows, contextualBrs=contextual_rows,
        trainedSummary=trained_summary(trained_rows, br_rows, contextual_rows),
        frTitles={fr["id"]: fr["title"] for fr in training["businessContext"]["functionalRequirements"]},
        dispositions=curated["dispositions"])
    report["findings"] = build_findings(report)
    report["outcome"] = outcome(report["findings"])
    report["headline"] = headline(report)
    return report


def headline(report):
    spec, trained = report["specSummary"], report["trainedSummary"]
    verdicts = Counter(row["agreement"] for row in report["devScenarios"])
    return OrderedDict(
        devBusinessRequirements=1, devScenarios=len(report["devScenarios"]),
        structuralChecks=dict(Counter(row["result"] for row in report["structuralChecks"])),
        scenarioAgreement=dict(verdicts),
        atl105Credit=spec["CREDIT"]["overall"], atl105Fleet=spec["FLEET"]["overall"], atl105Combined=spec["COMBINED"]["overall"],
        trainedBusinessScenarios=trained["businessScenarios"], trainedBrs=trained["businessRequirements"],
        trainedContextualBrs=trained["contextualBusinessRequirements"])


# ---------------------------------------------------------------- rendering

def cell(value):
    if isinstance(value, (list, tuple)):
        value = "; ".join(map(str, value))
    value = "" if value is None else str(value)
    return "'" + value if value.startswith(("=", "+", "-", "@")) else value


def csv_text(rows):
    stream = io.StringIO(newline="")
    writer = csv.DictWriter(stream, fieldnames=list(rows[0]))
    writer.writeheader()
    for row in rows:
        writer.writerow({key: cell(value) for key, value in row.items()})
    return stream.getvalue()


def xcell(value):
    return value if isinstance(value, (int, float)) and not isinstance(value, bool) else cell(value)


def md_table(header, rows):
    return ("| " + " | ".join(header) + " |\n|" + "|".join("---" for _ in header) + "|\n"
            + "".join("| " + " | ".join(str(c).replace("|", "\\|").replace("\n", " ") for c in row) + " |\n"
                      for row in rows))


def ratio(m, key="COVERED"):
    value = m[key] if key in m else 0
    return f"{value}/{m['applicable']} ({pct(value, m['applicable'])}%)"


def cover_cells(m):
    return [m["applicable"], f"{m['COVERED']} ({m['coveredPct']}%)",
            f"{m['COVERED'] + m['PARTIAL']} ({m['coveredOrPartialPct']}%)",
            f"{m['COVERED'] + m['PARTIAL'] + m['CONTEXT_ONLY']} ({m['contextReachPct']}%)"]


def dev_rows_out(report):
    return [OrderedDict((k, v) for k, v in row.items() if k != "trainedLinks") |
            OrderedDict(trainedBrsExpressed=len(row["trainedLinks"]), trainedBrIds="; ".join(row["trainedLinks"]))
            for row in report["devScenarios"]]


def readme(report):
    head, spec, trained = report["headline"], report["specSummary"], report["trainedSummary"]
    dev = report["devDelivery"]
    agreement = Counter((row["program"], row["agreement"]) for row in report["devScenarios"])
    exclusion = Counter((row["program"], row["verdict"]) for row in report["exclusions"])
    checks = Counter(row["result"] for row in report["structuralChecks"])
    group_names = list(OrderedDict.fromkeys(row["group"] for row in report["specCoverage"]))
    md = [
        "# Dev Team BR Validation: BUYPASS ATL105 MasterCard\n",
        f"Independent review of the Dev Team BR delivery (`BUSINESS_REQUIREMENTS.md`, `business_scenarios.csv`: "
        f"{dev['summary']['BR']} `{dev['header']['brId']}` \"{dev['summary']['Name']}\", built {dev['header']['built']}) "
        f"against **ATL105 {report['specificationVersion']}** and the **trained Mastercard Test Solution** (baseline "
        f"{report['trainedBaselineVersion']}). Generated by `{report['generator']}` for review date {report['reviewedOn']}. "
        f"Status **{report['status']}**: curated Test Solution judgements, not SME approval or execution evidence.\n",
        f"**Outcome: {report['outcome']}.** The delivery is structurally complete for its own transaction-category x "
        f"entry-mode matrix ({checks['PASS']} structural checks pass, {checks['WARN']} warnings), and every ATL105 "
        f"credit transaction type except Partial Approval is present. It is not intake-ready: Partial Approval and most "
        f"Mastercard-specific requirements are missing, the Authorization Only Reversal omits the Approval Number, rule "
        f"references use 2026-3 pages while the BR claims 2026-4, and the 785 supporting rules are counts only.\n",
        "## Headline coverage\n",
        md_table(["Measure", "Applicable", "Covered", "Covered + partial", "Context reach"], [
            ["ATL105 2026-4 Mastercard inventory - Credit (items)"] + cover_cells(spec["CREDIT"]["overall"]),
            ["ATL105 2026-4 Mastercard inventory - Fleet (items)"] + cover_cells(spec["FLEET"]["overall"]),
            ["ATL105 2026-4 Mastercard inventory - combined (item x program)"] + cover_cells(spec["COMBINED"]["overall"]),
            ["Trained solution - business scenarios (37 FR-matrix + 97 Test Solution)"] + cover_cells(trained["businessScenarios"]),
            ["Trained solution - business requirements (168 trained + 21 review queue)"] + cover_cells(trained["businessRequirements"]),
            ["Trained solution - Mastercard contextual BRs (all-network review)"] + cover_cells(trained["contextualBusinessRequirements"]),
        ]),
        "\n*Covered* = explicitly stated or modelled by the Dev BR with evidence; *partial* = modelled with a defect or "
        "missing part; *context reach* adds requirements whose transaction context exists in the Dev BR but which the "
        "Dev BR does not state (not counted as covered). Not-applicable items are excluded from the denominator. The "
        "Fleet inventory is small because ATL105 10.6 delegates fleet card processing to the Petroleum Industry "
        "Processing Specifications; it holds only the ATL105-level Fleet rules (card type, entry modes, EMV, lifecycle, "
        "MasterCard Enhanced Fleet EMV).\n",
        "## 1. Have all business requirements been created?\n",
        f"The delivery holds **1 BR** ({dev['summary']['Name']}) with **{dev['summary']['Scenarios']} scenarios** "
        f"(Credit {dev['programs'].get('Credit', 0)}, Fleet {dev['programs'].get('Fleet', 0)}; {dev['categories']} "
        f"transaction categories x {dev['entryModes']} entry modes; {dev['flowVariants']} multi-leg/pre-authorized flow "
        f"variants) and **{dev['summary']['excluded']} excluded combinations**. Dev status: {dev['summary']['valid']} valid, "
        f"{dev['summary']['requires SME']} unconfirmed (SME), {dev['summary']['with gaps']} with gaps.\n",
        md_table(["Check", "Result", "Expected", "Actual", "Detail"],
                 [[c["id"] + " " + c["title"], c["result"], c["expected"], c["actual"], c["detail"][:220]]
                  for c in report["structuralChecks"]]),
        "\nIndependent ATL105 verdict per Dev scenario (`dev-scenario-validation.csv`):\n",
        md_table(["Program", "Verdict vs Dev status", "Scenarios"],
                 [[p, a, n] for (p, a), n in sorted(agreement.items())]),
        "\n`ATL105_PERMITS_PENDING_SME`: the transaction is in the 10.1.2 credit list, the entry mode is a valid "
        "Appendix J code and no ATL105 restriction was found, so the SME can confirm it. Exclusions "
        "(`exclusion-validation.csv`):\n",
        md_table(["Program", "Exclusion verdict", "Combinations"],
                 [[p, v, n] for (p, v), n in sorted(exclusion.items())]),
        "\nBusiness requirements that should exist but were not created (see findings):\n",
        "".join(f"- **{f['title']}** ({f['severity']}): {f['recommendation']}\n" for f in report["findings"]
                if f["findingClass"] == "MISSING_REQUIREMENT"),
        "\n## 2. Coverage against ATL105 2026-4 (Mastercard)\n",
        f"A source-verified inventory of {len({r['id'] for r in report['specCoverage']})} Mastercard requirements; "
        "every quote resolves on its cited 2026-4 page (`spec-coverage.csv`).\n",
        md_table(["Group", "Credit covered", "Credit covered + partial", "Fleet covered", "Fleet covered + partial"],
                 [[g] + sum(([ratio(spec[p]["groups"][g]), f"{spec[p]['groups'][g]['coveredOrPartialPct']}%"]
                             if g in spec[p]["groups"] else ["-", "-"] for p in ("CREDIT", "FLEET")), [])
                  for g in group_names]),
        "\nItems not fully covered:\n",
        md_table(["Item", "Program", "Disposition", "Requirement", "ATL105", "Detail"],
                 [[r["id"], r["program"], r["disposition"], r["title"], r["atl105Reference"], r["detail"][:200]]
                  for r in report["specCoverage"] if r["disposition"] != "COVERED"]),
        "\n## 3. Coverage against the trained Mastercard solution\n",
        "Trained baseline: `mc-credit-purchase-training-package.json` (BR-01 Mastercard credit financial purchase; 8 FRs, "
        "134 business scenarios, 189 BRs) and the 17 MASTERCARD contextual BRs of the all-network review. The trained "
        "solution covers MasterCard Credit only.\n",
        md_table(["Scope", "Applicable", "Covered", "Covered + partial", "Context reach"], [
            ["All business scenarios"] + cover_cells(trained["businessScenarios"]),
            ["Scenarios with Test Solution evidence"] + cover_cells(trained["trainedEvidencedScenarios"]),
            ["FR-matrix template scenarios (37)"] + cover_cells(trained["templateScenarios"]),
            ["Test Solution scenarios (97)"] + cover_cells(trained["testSolutionScenarios"]),
            ["All BRs (189)"] + cover_cells(trained["businessRequirements"]),
            ["Trained BRs (168)"] + cover_cells(trained["trainedBusinessRequirements"]),
            ["Contextual MASTERCARD BRs (17)"] + cover_cells(trained["contextualBusinessRequirements"])]),
        "\nBy functional requirement:\n",
        md_table(["FR", "Title", "Scenarios", "Scenarios covered", "BRs", "BRs covered", "BRs covered + partial"],
                 [[fr, report["frTitles"].get(fr, ""), m["scenarios"]["applicable"], ratio(m["scenarios"]),
                   m["brs"]["applicable"], ratio(m["brs"]), f"{m['brs']['coveredOrPartialPct']}%"]
                  for fr, m in trained["functionalRequirements"].items()]),
        "\nFR-matrix template scenarios:\n",
        md_table(["TS", "Scenario", "FR", "Trained status", "Dev disposition", "Reason"],
                 [[r["id"], r["title"], r["fr"], r["trainedCoverageStatus"], r["devDisposition"], r["reason"][:160]]
                  for r in report["trainedScenarios"] if r["origin"] == "TEMPLATE"]),
        "\nTrained business scenarios the Dev BR covers or partly covers:\n",
        md_table(["TS", "Scenario", "FR", "Dev disposition", "Reason"],
                 [[r["id"], r["title"], r["fr"], r["devDisposition"], r["reason"][:160]] for r in report["trainedScenarios"]
                  if r["origin"] != "TEMPLATE" and r["devDisposition"] in ("COVERED", "PARTIAL")]),
        "\nMASTERCARD contextual BRs:\n",
        md_table(["Contextual BR", "Category", "Dev disposition", "Reason"],
                 [[r["id"], r["category"], r["devDisposition"], r["reason"][:200]] for r in report["contextualBrs"]]),
        "\n## 4. Findings\n",
        md_table(["ID", "Severity", "Class", "Finding", "Affected", "Recommendation", "Owner"],
                 [[f["id"], f["severity"], f["findingClass"], f["title"], f["affected"], f["recommendation"], f["owner"]]
                  for f in report["findings"]]),
        "\nFull detail and source references are in `findings.csv`.\n",
        "## 5. Version and citation checks\n",
        md_table(["Section", "2026-3 page", "2026-4 page", "Identical text"],
                 [[r["section"], r["page2026_3"], r["page2026_4"], "yes" if r["identical"] else "NO"]
                  for r in report["versionChecks"]]),
        "\nDev citations (`citation-verification.csv`): "
        + ", ".join(f"{s} {n}" for s, n in sorted(Counter(c["status"] for c in report["citations"]).items())) + ".\n\n",
        md_table(["Carried field", "Rule", "Rule page", "Cited page", "Uses", "On 2026-4 page", "On 2026-3 page", "Verdict"],
                 [[r["field"], r["rule"], r["rulePage"], r["citedPage"], r["uses"], r["fieldOnPage2026_4"],
                   r["fieldOnPage2026_3"], r["verdict"]] for r in report["ruleReferences"]]),
        "\n## 6. Coverage definitions\n",
        "".join(f"- **{name}**: {text}\n" for name, text in report["dispositions"].items()),
        "\nCOVERED and PARTIAL require a Dev evidence pattern that the script finds in the delivery; NOT_COVERED and "
        "CONTEXT_ONLY claims carry search terms that must be absent from it. Spec items are computed from the Dev "
        "scenarios where possible (codes, entry modes, flows, carried fields) and curated otherwise.\n",
        "## 7. Deliverables and re-run\n",
        "- `dev-br-mc-coverage.xlsx`: all tables below, one sheet each.\n"
        "- `dev-scenario-validation.csv`: 108 Dev scenarios with the independent ATL105 verdict, issues and the trained "
        "BRs each scenario explicitly expresses.\n"
        "- `exclusion-validation.csv`: 212 exclusions with their ATL105/external basis.\n"
        "- `spec-coverage.csv`: ATL105 inventory x program dispositions.\n"
        "- `trained-scenario-coverage.csv`, `trained-br-coverage.csv`, `contextual-br-coverage.csv`: trained-solution coverage.\n"
        "- `findings.csv`, `citation-verification.csv`, `coverage.json` (complete report with input hashes).\n\n"
        "From the validator module:\n\n```powershell\n"
        "python scripts/validate-dev-br-mastercard-coverage.py           # regenerate this folder\n"
        "python scripts/validate-dev-br-mastercard-coverage.py --check   # fail if these outputs are stale\n"
        "python -m pytest scripts/test_validate_dev_br_mastercard_coverage.py\n```\n\n"
        "The script refuses to run when the Dev delivery bytes differ from the reviewed SHA-256 values, when an "
        "ATL105 quote does not resolve on its cited page, when curated evidence is missing or when an absence claim is "
        "contradicted.\n",
        "## 8. Limitations\n",
        "- Dispositions are curated Test Solution judgements (DRAFT_REVIEW_REQUIRED) pending SME/BA review.\n"
        "- The Dev supporting rules are not delivered, so rule-level coverage cannot be measured; CONTEXT_ONLY marks "
        "that gap rather than assuming coverage.\n"
        "- MasterCard Fleet scope depends on the Petroleum Industry Processing Specifications, which are not in the "
        "repository, and the trained solution has no Fleet context.\n"
        "- The trained baseline is ATL105 2026-3; the compared sections are identical in 2026-4, but page numbers differ.\n"
        "- Nothing was executed; no host, issuer or device outcome is confirmed.\n",
        "\n## Inputs\n",
        md_table(["Role", "Path", "SHA-256"], [[i["role"], i["path"], i["sha256"]] for i in report["inputs"]]),
    ]
    return "\n".join(part.rstrip("\n") + "\n" if part.endswith("\n") else part for part in md)


SHEETS = [
    ("Structural Checks", "structuralChecks", ("result",)),
    ("Dev Scenarios", None, ("independentVerdict",)),
    ("Exclusions", "exclusions", ()),
    ("ATL105 Coverage", "specCoverage", ("disposition",)),
    ("Trained Scenarios", "trainedScenarios", ("devDisposition",)),
    ("Trained BRs", None, ("devDisposition",)),
    ("Contextual BRs", "contextualBrs", ("devDisposition",)),
    ("Findings", "findings", ("severity",)),
    ("Citations", "citations", ()),
    ("Rule References", None, ()),
    ("Version Check", "versionChecks", ()),
]


def sheet_rows(report, name, key):
    if name == "Dev Scenarios":
        return dev_rows_out(report)
    if name == "Trained BRs":
        return report["trainedBrs"]
    if name == "Rule References":
        return report["ruleReferences"]
    return report[key]


def write_sheet(ws, rows, color_columns):
    header = list(rows[0])
    ws.append(header)
    for c in ws[1]:
        c.font = Font(bold=True, color="FFFFFF")
        c.fill = PatternFill("solid", fgColor="1F4E78")
        c.alignment = Alignment(wrap_text=True, vertical="top")
    for row in rows:
        ws.append([xcell(row[name]) for name in header])
    for r in range(2, ws.max_row + 1):
        for name in color_columns:
            if name in header:
                target = ws.cell(r, header.index(name) + 1)
                if target.value in FILLS:
                    target.fill = PatternFill("solid", fgColor=FILLS[target.value])
    for index, name in enumerate(header, 1):
        longest = max([len(str(name))] + [len(str(ws.cell(r, index).value or "")) for r in range(2, min(ws.max_row, 60) + 1)])
        ws.column_dimensions[get_column_letter(index)].width = max(10, min(70, longest + 2))
        for r in range(2, ws.max_row + 1):
            ws.cell(r, index).alignment = Alignment(wrap_text=True, vertical="top")
    ws.freeze_panes = "A2"
    ws.auto_filter.ref = ws.dimensions


def workbook_bytes(report):
    wb = openpyxl.Workbook()
    summary = wb.active
    summary.title = "Summary"
    head, spec, trained = report["headline"], report["specSummary"], report["trainedSummary"]
    lines = [["Dev Team BR Validation: BUYPASS ATL105 MasterCard", ""], ["Outcome", report["outcome"]],
             ["Status", report["status"]], ["Review date", report["reviewedOn"]],
             ["Specification", f"ATL105 {report['specificationVersion']} (trained baseline {report['trainedBaselineVersion']})"],
             ["Dev BR", f"{report['devDelivery']['summary']['Name']} ({report['devDelivery']['header']['brId']}), "
                        f"{report['devDelivery']['summary']['Scenarios']} scenarios, "
                        f"{report['devDelivery']['summary']['excluded']} exclusions"], ["", ""],
             ["Measure", "Applicable | Covered | Covered + partial | Context reach"]]
    for label, m in (("ATL105 Credit inventory", spec["CREDIT"]["overall"]), ("ATL105 Fleet inventory", spec["FLEET"]["overall"]),
                     ("ATL105 combined", spec["COMBINED"]["overall"]),
                     ("Trained business scenarios", trained["businessScenarios"]),
                     ("Trained BRs", trained["businessRequirements"]),
                     ("Trained contextual BRs", trained["contextualBusinessRequirements"])):
        lines.append([label, " | ".join(map(str, cover_cells(m)))])
    lines += [["", ""], ["Structural checks", json.dumps(head["structuralChecks"])],
              ["Scenario verdicts", json.dumps(head["scenarioAgreement"])]]
    for row in lines:
        summary.append(row)
    summary["A1"].font = Font(bold=True, size=14)
    summary["B2"].fill = PatternFill("solid", fgColor=FILLS["FAIL"] if report["outcome"].startswith("REJECTED") else FILLS["WARN"])
    summary.column_dimensions["A"].width = 32
    summary.column_dimensions["B"].width = 110
    for name, key, colors in SHEETS:
        rows = sheet_rows(report, name, key)
        if rows:
            write_sheet(wb.create_sheet(name), rows, colors)
    stream = io.BytesIO()
    wb.save(stream)
    return stream.getvalue()


def build():
    report = build_report()
    outputs = OrderedDict([
        ("README.md", readme(report)),
        ("coverage.json", json.dumps(report, indent=2, ensure_ascii=True) + "\n"),
        ("dev-scenario-validation.csv", csv_text(dev_rows_out(report))),
        ("exclusion-validation.csv", csv_text(report["exclusions"])),
        ("spec-coverage.csv", csv_text(report["specCoverage"])),
        ("trained-scenario-coverage.csv", csv_text(report["trainedScenarios"])),
        ("trained-br-coverage.csv", csv_text(report["trainedBrs"])),
        ("contextual-br-coverage.csv", csv_text(report["contextualBrs"])),
        ("findings.csv", csv_text(report["findings"])),
        ("citation-verification.csv", csv_text(report["citations"])),
    ])
    return report, outputs, workbook_bytes(report)


def main(argv=None):
    parser = argparse.ArgumentParser(description="Validate the Dev Team Mastercard BR delivery against ATL105 and the "
                                                 "trained Test Solution.")
    parser.add_argument("--check", action="store_true", help="fail if the written outputs are stale; write nothing")
    args = parser.parse_args(argv)
    report, outputs, xlsx = build()
    if args.check:
        stale = [name for name, text in outputs.items()
                 if not os.path.exists(long_path(OUT_DIR / name)) or read_bytes(OUT_DIR / name).decode("utf-8") != text]
        print("Stale outputs: " + ", ".join(stale) if stale else "Outputs are current.")
        return 1 if stale else 0
    os.makedirs(long_path(OUT_DIR), exist_ok=True)
    for name, text in outputs.items():
        with open(long_path(OUT_DIR / name), "w", encoding="utf-8", newline="") as handle:
            handle.write(text)
    with open(long_path(OUT_DIR / XLSX_NAME), "wb") as handle:
        handle.write(xlsx)
    head = report["headline"]
    print(f"Outcome {report['outcome']} | structural {head['structuralChecks']} | "
          f"ATL105 credit {head['atl105Credit']['coveredPct']}% ({head['atl105Credit']['coveredOrPartialPct']}% incl. partial), "
          f"fleet {head['atl105Fleet']['coveredPct']}% | trained scenarios {head['trainedBusinessScenarios']['coveredPct']}% "
          f"({head['trainedBusinessScenarios']['coveredOrPartialPct']}%), trained BRs {head['trainedBrs']['coveredPct']}% "
          f"({head['trainedBrs']['coveredOrPartialPct']}%) | findings {len(report['findings'])}")
    print(f"Wrote {rel(OUT_DIR)}/ ({', '.join(list(outputs) + [XLSX_NAME])})")
    return 0


if __name__ == "__main__":
    sys.exit(main())
