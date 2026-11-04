"""Derive the bounded Appendix G source block without promoting code classification."""

import argparse
import contextlib
import csv
import hashlib
import importlib.util
import io
import json
import re
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "appendix_g_parent", Path(__file__).with_name("reconcile-nonmaster-petroleum-legacy.py"))
parent = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(parent)
completion, base = parent.completion, parent.base
SCOPE = completion.MODEL.with_name("nonmaster-appendix-g-semantic-scope.json")
OUTPUT = base.REVIEW / "nonmaster-appendix-g-semantic-review-v1"
CATALOG = base.ROOT / "src" / "main" / "java" / "com" / "coreauth" / "validator" / "canonical" / "AppendixGTransactionTypeCatalog.java"
CODE_LINE = re.compile(r"^([0-9A-Z])\s+(.+)$")
CATALOG_ENTRY = re.compile(
    r'add\(entries,\s*"([0-9A-Z])",\s*"([^"]+)",\s*"([^"]+)",\s*Scope\.([A-Z_]+)\);')


def read_csv(path):
    with path.open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def digest_text(text):
    return hashlib.sha256(text.encode("utf-8")).hexdigest()


def norm(text):
    return re.sub(r"\s+", " ", text).strip()


def tokens(text):
    return re.findall(r"[a-z0-9]+", text.casefold())


def source_pages(text):
    pages = {}
    current = None
    for number, line in enumerate(text.splitlines(), 1):
        match = re.fullmatch(r"--- PAGE (\d+) ---", line.strip())
        if match:
            current = int(match[1])
        elif current is not None:
            pages.setdefault(current, []).append((number, line.strip()))
    return pages


def canonical_entries(text, expected_order):
    entries = {}
    for code, transaction_type, description, scope in CATALOG_ENTRY.findall(text):
        if code in entries:
            raise ValueError(f"Duplicate canonical Appendix G code: {code}")
        entries[code] = {"code": code, "type": transaction_type,
                         "description": description, "scope": scope}
    if list(entries) != expected_order or len(entries) != 23:
        raise ValueError("Canonical Appendix G catalog order/population changed")
    return entries


def body_page(lines, page):
    rows = [text for _, text in lines]
    marker = "Appendix G-1" if page == 538 or page == 545 else "Appendix G-2"
    indices = [index for index, text in enumerate(rows) if text == marker]
    if len(indices) != 1:
        raise ValueError(f"Appendix G page marker missing or ambiguous: {page}")
    return rows[indices[0]:]


def verify_release(text, release, entries, spans):
    pages = source_pages(text)
    if release["lastPage"] - release["firstPage"] != 1:
        raise ValueError("Appendix G must contain two consecutive source pages")
    first = body_page(pages[release["firstPage"]], release["firstPage"])
    second = body_page(pages[release["lastPage"]], release["lastPage"])
    combined = norm(" ".join(first + second))
    if "Appendix G. Valid Transaction Type Codes" not in combined:
        raise ValueError("Appendix G title does not match pinned source")
    actual_codes = []
    current_rows = []
    current_code = None
    for page, lines in ((release["firstPage"], first), (release["lastPage"], second)):
        table_started = False
        for line in lines:
            if line == "Code Transaction Type Transaction Description":
                table_started = True
                continue
            if not table_started:
                continue
            match = CODE_LINE.match(line)
            if match:
                code = match.group(1)
                if code not in entries:
                    raise ValueError(f"Source has unrecognized Appendix G code row {code}")
                if code in actual_codes:
                    raise ValueError(f"Duplicate Appendix G source row {code}")
                actual_codes.append(code)
                current_code = code
                current_rows.append({"code": code, "page": page, "lines": [line]})
            elif current_code is not None:
                current_rows[-1]["lines"].append(line)
    if actual_codes != list(entries):
        raise ValueError("Appendix G source code rows differ from the canonical catalog")
    for row in current_rows:
        entry = entries[row["code"]]
        actual = tokens(" ".join(row["lines"]))
        for key in ("type", "description"):
            expected = tokens(entry[key])
            if not any(actual[index:index + len(expected)] == expected
                       for index in range(len(actual) - len(expected) + 1)):
                raise ValueError(f"Source row {row['code']} differs from canonical {key}")
    observed_spans = []
    for span in spans:
        if span["page"] not in (release["firstPage"], release["lastPage"]):
            continue
        page_lines = pages[span["page"]]
        start, end = span["startLine"], span["endLine"]
        if end >= len(text.splitlines()) + 1:
            raise ValueError("Authored source span exceeds source text")
        selected = [line for number, line in page_lines if start <= number <= end]
        quote = norm(" ".join(selected))
        if not selected or len(quote) < 20 or digest_text(quote) != span["quoteSha256"]:
            raise ValueError(f"Missing authored source span {span['id']}")
        observed_spans.append({**span, "quote": quote,
                               "quoteSha256": digest_text(quote),
                               "release": release["version"]})
    return {"codes": actual_codes, "rows": current_rows, "spans": observed_spans,
            "bodySha256": digest_text(combined), "pages": release}


def classify_source_units(text, units, scope, entries):
    lines = text.splitlines()
    selected_units = sorted(
        (unit for unit in units if int(unit["page"]) in scope["sourceUnitPages"]),
        key=lambda unit: (int(unit["page"]), int(unit["startLine"])))
    unit_counts = {page: sum(int(unit["page"]) == page for unit in selected_units)
                   for page in scope["sourceUnitPages"]}
    if unit_counts != {545: 2, 546: 1} or len(selected_units) != 3:
        raise ValueError("Expected the three retained Appendix G source units")
    derived_pages = {}
    for page in scope["sourceUnitPages"]:
        current_code = None
        parsed_codes = []
        page_results = []
        page_units = [unit for unit in selected_units if int(unit["page"]) == page]
        for unit in page_units:
            start, end = int(unit["startLine"]), int(unit["endLine"])
            quote = norm(" ".join(lines[start - 1:end]))
            if quote != unit["quote"] or digest_text(quote) != unit["sha256"]:
                raise ValueError("Appendix G source unit boundary or digest changed")
            classified = []
            for number in range(start, end + 1):
                value = lines[number - 1].strip()
                if not value:
                    continue
                if value.startswith("Software Version:") or value.startswith("Specification Release Version:") \
                        or re.match(r"^(?:August|October) \d{2}, \d{4}$", value) \
                        or re.fullmatch(r"Appendix G-[12]", value):
                    kind = "SOURCE_PAGE_METADATA"
                elif value.startswith("Appendix G. ") or value == "Valid Transaction Type Codes":
                    kind = "SOURCE_HEADING"
                elif value == "Code Transaction Type Transaction Description":
                    kind = "SOURCE_TABLE_HEADER"
                else:
                    match = CODE_LINE.match(value)
                    if match and match.group(1) in entries:
                        current_code = match.group(1)
                        parsed_codes.append(current_code)
                        kind = "CODE_ROW"
                    elif current_code is not None:
                        kind = ("CODE_SPECIFIC_NOTE" if value.startswith("Note:") else
                                "CODE_SPECIFIC_SUBFUNCTION" if current_code == "V" and value.startswith("•")
                                else "CODE_ROW_CONTINUATION")
                    elif value == "This appendix contains a list of valid Transaction Type codes.":
                        kind = "STATEMENT_OF_CODE_LIST_SCOPE"
                    elif 29379 <= number <= 29402:
                        kind = "PROSE_OBLIGATION"
                    elif 29403 <= number <= 29404:
                        kind = "CODE_WIDTH_AND_LIST_SCOPE"
                    else:
                        raise ValueError(f"Unclassified Appendix G source line {number}: {value}")
                classified.append({"line": number, "kind": kind, "code": current_code or ""})
            page_results.append({
                "sourceUnitId": unit["sourceUnitId"], "page": page, "sha256": unit["sha256"],
                "startLine": start, "endLine": end,
                "classifiedLineCount": len(classified), "classifications": classified,
                "derivationStatus": "ALL_RETAINED_LINES_DERIVED_OR_SOURCE_METADATA_CLASSIFIED",
                "approvalStatus": "DRAFT_NOT_APPROVED",
            })
        if page == 545 and parsed_codes != ["0", "3", "4", "5", "6"]:
            raise ValueError("Appendix G-1 table rows changed")
        if page == 546 and parsed_codes != ["7", "8", "9", "A", "B", "C", "D", "E",
                                              "K", "L", "M", "N", "Q", "S", "T", "U", "V", "Z"]:
            raise ValueError("Appendix G-2 table rows changed")
        derived_pages[page] = page_results
    result = [unit for page in scope["sourceUnitPages"] for unit in derived_pages[page]]
    if sum(row["classifiedLineCount"] for row in result) != 88:
        raise ValueError("Expected 88 retained nonblank lines across Appendix G units")
    return result


def adjudicate_legacy(scope, entries, legacy):
    historical = {row["legacyBrId"]: row for row in legacy}
    expected_ids = {"BR-APPG-CODE-" + code for code in entries} | {
        scope["negativeAssertion"]["legacyBrId"]}
    actual_ids = {identity for identity in historical if identity.startswith("BR-APPG-")}
    if actual_ids != expected_ids:
        raise ValueError("Appendix G legacy classification/negative assertion population changed")
    rows = []
    for code, entry in entries.items():
        identity = "BR-APPG-CODE-" + code
        row = historical[identity]
        expected = f"Appendix G transaction type {code} is recognized as {entry['scope']}."
        anchors = json.loads(row["sourceAnchors"])
        if row["title"] != expected or not any(
                anchor.get("section") == "Appendix G" and anchor.get("version") == "2026-3"
                for anchor in anchors):
            raise ValueError(f"Legacy Appendix G assertion differs from source catalog: {identity}")
        rows.append({
            "legacyBrId": identity, "sourceCode": code, "sourceType": entry["type"],
            "sourceDescriptionSha256": digest_text(entry["description"]),
            "classification": entry["scope"],
            "sourcePages": {
                "2026-3": {"pages": [538, 539], "sha256": base.OLD_HASH},
                "2026-4": {"pages": [545, 546], "sha256": base.gates.SOURCE_SHA256},
            },
            "releaseDecision": "CODE_AND_CLASSIFICATION_MATCH_BOTH_PINNED_RELEASES",
            "semanticBoundary": scope["rowDisposition"],
            "atomicBusinessDenominator": False, "approvedDenominatorDelta": None,
            "status": "DRAFT_SOURCE_CLASSIFICATION_REVIEWED",
        })
    negative = historical[scope["negativeAssertion"]["legacyBrId"]]
    negative_anchors = json.loads(negative["sourceAnchors"])
    if (negative["title"] != "Unknown or malformed values are not classified as valid Appendix G transaction types."
            or not any(anchor.get("section") == "Appendix G" and anchor.get("version") == "2026-3"
                       for anchor in negative_anchors)):
        raise ValueError("Appendix G negative-test boundary changed")
    rows.append({
        "legacyBrId": negative["legacyBrId"], **scope["negativeAssertion"],
        "sourcePages": {"2026-3": {"pages": [538, 539], "sha256": base.OLD_HASH},
                        "2026-4": {"pages": [545, 546], "sha256": base.gates.SOURCE_SHA256},
                        },
        "automaticMerge": False, "atomicBusinessDenominator": False,
        "approvedDenominatorDelta": None, "status": "DRAFT_NEGATIVE_BOUNDARY_REVIEWED",
    })
    return rows


def derive(scope, old_text, new_text, catalog_text, legacy, units):
    if scope.get("schemaVersion") != 1 or scope.get("status") != "DRAFT_REVIEW_REQUIRED":
        raise ValueError("Appendix G scope must remain draft schema 1")
    expected_releases = {item["version"]: item for item in scope["releases"]}
    if set(expected_releases) != {"2026-3", "2026-4"}:
        raise ValueError("Both pinned ATL105 releases are required")
    entries = canonical_entries(catalog_text, scope["appendixGCodeOrder"])
    release_data = {
        "2026-3": verify_release(old_text, expected_releases["2026-3"], entries, scope["sourceSpans"]),
        "2026-4": verify_release(new_text, expected_releases["2026-4"], entries, scope["sourceSpans"]),
    }
    if release_data["2026-3"]["codes"] != release_data["2026-4"]["codes"]:
        raise ValueError("Appendix G code populations differ across releases")
    if release_data["2026-3"]["bodySha256"] != release_data["2026-4"]["bodySha256"]:
        raise ValueError("Appendix G full normalized body differs across the pinned releases")
    old_pages = source_pages(old_text)
    for span in release_data["2026-4"]["spans"]:
        old_page = span["page"] - 7
        old_body = norm(" ".join(body_page(old_pages[old_page], old_page)))
        if span["quote"] not in old_body:
            raise ValueError(f"Authored Appendix G span differs in ATL105 2026-3: {span['id']}")
    source_units = classify_source_units(new_text, units, scope, entries)
    source_obligations = [{
        **span, "status": "DRAFT_SOURCE_OBLIGATION_DERIVED",
        "sourcePrecedence": "PINNED_RELEASE_TEXT_ONLY",
        "automaticDenominatorInclusion": False,
    } for span in release_data["2026-4"]["spans"]]
    assertions = adjudicate_legacy(scope, entries, legacy)
    return entries, source_units, source_obligations, assertions, release_data


def report(summary):
    counts = summary["counts"]
    return "\n".join([
        "# Non-Mastercard Appendix G semantic derivation", "",
        "**This is a bounded source-block review; overall semantic work remains incomplete.**", "",
        f"- {counts['legacyAssertionsReviewed']} Appendix G legacy assertions reviewed,",
        "  including 23 source-listed codes and one separately identified negative-test boundary.",
        f"- {counts['sourceUnitsDerived']} retained Appendix G units derived with all",
        f"  {counts['retainedLinesClassified']} nonblank lines classified.",
        "- Appendix G prose, MasterCard-specific scope, code-9 Card Type restriction,",
        "  and specialized table descriptions remain source-scoped; no operation contracts are inferred.",
        "- Code recognition is not Prompt Code serialization, network eligibility, or lifecycle certification.", "",
        "## Still open", "",
        f"- {counts['legacyRulesWithoutScopedDecisions']} legacy BRs lack new or prior scoped decisions.",
        f"- {counts['sourceUnitsStillUnclassified']} source units still require derivation.",
        "- Exhaustive cross-legacy and Petroleum-to-legacy review remains incomplete.",
        "- Approved denominator and confirmed coverage remain null.", "",
        "## Evidence", "",
        "- [Appendix G legacy source classifications](legacy-adjudications.json)",
        "- [Line-complete source derivations](source-derivation.json)",
        "- [Derived prose obligations](obligations.json)",
        "- [Pinned release source comparison](release-comparison.json)",
        "- [Summary and input identities](summary.json)",
        "- [Input manifest](input-manifest.json)", "",
    ])


def run(delivery, output=OUTPUT, check=False):
    delivery, output = delivery.resolve(), output.resolve()
    if output.is_relative_to(delivery) or delivery.is_relative_to(output):
        raise ValueError("Output must be disjoint from original delivery")
    if not check and output.exists():
        raise ValueError("New output directory required")
    frozen = completion.v1.freeze_delivery(delivery)
    with contextlib.redirect_stdout(io.StringIO()):
        parent.parent.run(delivery, parent.parent.OUTPUT, check=True)
    with contextlib.redirect_stdout(io.StringIO()):
        parent.completion.run(delivery, parent.completion.OUTPUT, check=True)
    with contextlib.redirect_stdout(io.StringIO()):
        parent.run(delivery, parent.OUTPUT, check=True)
    scope = base.gates.load(SCOPE)
    legacy = read_csv(parent.parent.parent.OUTPUT / "legacy-semantic-register.csv")
    units = read_csv(parent.parent.completion.v1.OUTPUT / "source-unit-register.csv")
    old_text = base.OLD_SOURCE.read_text(encoding="utf-8-sig")
    new_text = base.gates.SOURCE.read_text(encoding="utf-8-sig")
    entries, source_units, obligations, assertions, releases = derive(
        scope, old_text, new_text, CATALOG.read_text(encoding="utf-8"),
        legacy, units)
    # Compute prior scoped populations from the persisted decision ledgers.
    scoped_ids = {row["legacyBrId"] for row in legacy
                  if json.loads(row["semanticRelationIds"])}
    scoped_ids.update(row["legacyBrId"] for row in json.loads(
        (base.REVIEW / "nonmaster-product-semantic-review-v1" / "legacy-adjudications.json")
        .read_text(encoding="utf-8")))
    scoped_ids.update(row["legacyBrId"] for row in json.loads(
        (base.REVIEW / "nonmaster-petroleum-legacy-review-v1" / "legacy-counterpart-decisions.json")
        .read_text(encoding="utf-8")))
    scoped_ids.update(row["legacyBrId"] for row in assertions)
    source_ids = {row["sourceUnitId"] for row in source_units}
    prior_source_ids = {
        row["sourceUnitId"] for row in json.loads(
            (base.REVIEW / "nonmaster-product-semantic-review-v1" / "source-derivation.json")
            .read_text(encoding="utf-8"))
    }
    current_source_ids = {row["sourceUnitId"] for row in units}
    summary = {
        "status": "APPENDIX_G_BATCH_COMPLETE_OVERALL_SEMANTIC_REQUEST_INCOMPLETE",
        "counts": {
            "legacyAssertionsReviewed": len(assertions),
            "sourceUnitsDerived": len(source_units),
            "retainedLinesClassified": sum(row["classifiedLineCount"] for row in source_units),
            "legacyRulesWithoutScopedDecisions": len(set(row["legacyBrId"] for row in legacy) - scoped_ids),
            "sourceUnitsStillUnclassified": len(current_source_ids - prior_source_ids - source_ids),
        },
        "wholeSpecSemanticsComplete": False, "allLegacyOverlapComplete": False,
        "petroleumToAllLegacyReviewComplete": False, "approvedDenominator": None,
        "confirmedRequirementCoveragePercent": None, "executionReady": False, "baselineChanged": False,
    }
    paths = [
        SCOPE, CATALOG, Path(__file__), Path(parent.__file__), Path(parent.parent.__file__),
        Path(parent.parent.parent.__file__), Path(parent.completion.__file__),
        Path(parent.completion.v1.__file__), Path(parent.completion.fleet.__file__),
        base.OLD_SOURCE, base.gates.SOURCE, base.LEGACY,
        parent.parent.parent.DECISIONS, parent.parent.DECISIONS, parent.DECISIONS,
        parent.parent.OUTPUT / "summary.json",
        base.REVIEW / "nonmaster-product-semantic-review-v1" / "summary.json",
        base.REVIEW / "nonmaster-petroleum-legacy-review-v1" / "summary.json",
        base.REVIEW / "nonmaster-product-semantic-review-v1" / "legacy-adjudications.json",
        base.REVIEW / "nonmaster-product-semantic-review-v1" / "source-derivation.json",
        base.REVIEW / "nonmaster-petroleum-legacy-review-v1" / "legacy-counterpart-decisions.json",
        parent.completion.OUTPUT / "model-package.json",
        parent.completion.v1.OUTPUT / "source-unit-register.csv",
        parent.parent.parent.OUTPUT / "legacy-semantic-register.csv",
    ]
    paths.extend(path for folder in (parent.parent.parent.OUTPUT,
                                     parent.parent.completion.OUTPUT,
                                     parent.parent.completion.v1.OUTPUT,
                                     parent.parent.OUTPUT)
                 for path in sorted(folder.iterdir()) if path.is_file())
    manifest = [{"path": str(path.resolve()), "sha256": base.digest(path)}
                for path in dict.fromkeys(paths)]
    output_values = {
        "summary.json": summary, "legacy-adjudications.json": assertions,
        "source-derivation.json": source_units, "obligations.json": obligations,
        "release-comparison.json": {
            version: {"pages": row["pages"], "codes": row["codes"],
                      "bodySha256": row["bodySha256"]}
            for version, row in releases.items()},
        "input-manifest.json": manifest,
    }
    expected = {name: (json.dumps(value, ensure_ascii=True, indent=2) + "\n").encode("utf-8")
                for name, value in output_values.items()}
    expected["REPORT.md"] = report(summary).encode("utf-8")
    if (completion.v1.freeze_delivery(delivery) != frozen
            or any(base.digest(Path(row["path"])) != row["sha256"] for row in manifest)):
        raise ValueError("Frozen delivery or Appendix G review inputs changed")
    if check:
        parent.parent.parent.v1.verify_outputs(output, expected)
    else:
        output.mkdir(parents=True)
        for name, value in expected.items():
            (output / name).write_bytes(value)
    print(json.dumps(summary, indent=2))
    return summary


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--delivery", type=Path, required=True)
    parser.add_argument("--output", type=Path, default=OUTPUT)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args()
    run(args.delivery, args.output, args.check)


if __name__ == "__main__":
    main()
