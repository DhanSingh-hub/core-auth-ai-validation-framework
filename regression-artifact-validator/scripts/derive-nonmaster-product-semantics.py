"""Derive the complete bounded Appendix F block; retain all other semantic work as open."""

import argparse
import contextlib
import hashlib
import importlib.util
import io
import json
import re
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_product_parent", Path(__file__).with_name("reconcile-nonmaster-petroleum.py"))
petroleum = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(petroleum)
parent, completion, base = petroleum.parent, petroleum.completion, petroleum.base
SCOPE = completion.MODEL.with_name("nonmaster-product-semantic-scope.json")
ORACLE = base.ATL.parents[1] / "src" / "main" / "resources" / "atl105" / "appendix-f-product-code-oracle.json"
OUTPUT = base.REVIEW / "nonmaster-product-semantic-review-v1"
ROW = re.compile(r"^(\d{3})(?:[-\u2013](\d{3}))?\s+(.+)$")
RESERVED = re.compile(r"^Code Nos\. (\d{3})[\u2013-](\d{3}) are (reserved for proprietary use\.)$")
CONTINUATIONS = {"PCATS Future Use", "Reserved for PCATS Use"}
REPEATED_NOTES = {
    2: "Note: A unique Product Code must be sent for each type of fuel purchase.",
    7: "Note: A unique Product Code must be sent for each type of fuel purchase.",
    10: "Note: A unique Product Code must be sent for each type of fuel purchase.",
    21: ("Notes: If the transaction involves a fuel purchase, a unique Product Code must be sent for each "
         "type of fuel involved in the transaction."),
    22: ("Note: If a transaction involves a fuel purchase, a unique Product Code must be sent for each "
         "type of fuel purchase."),
}
PARAGRAPH_HEADERS = {
    "Appendix F. Valid Payment Systems Product Codes",
    "This appendix contains the following Product Code tables:",
    "Payment Systems Product Codes", "Product Code Description",
}
OVERVIEW = {
    "\u2022 Unused codes (Code No. 000)",
    "\u2022 Motor fuel codes (Code Nos. 001\u2013099)",
    "\u2022 Automotive product/service codes (Code Nos. 100\u2013149)",
    "\u2022 Aviation fuel codes (Code Nos. 150\u2013174)",
    "\u2022 Aviation product/service codes (Code Nos. 175\u2013224)",
    "\u2022 Marine fuel codes (Code Nos. 225\u2013249)",
    "\u2022 Marine product/service codes (Code Nos. 250\u2013299)",
    "\u2022 Other fuel codes (Code Nos. 300\u2013399)",
    "\u2022 Merchandise codes (Code Nos. 400\u2013599)",
    "\u2022 Reserved (future use) codes (Code Nos. 600\u2013799)",
    "\u2022 Debit surcharge codes (Code Nos. 800\u2013809)",
    "\u2022 Reserved (proprietary use) codes (Code Nos. 810\u2013889)",
    "\u2022 FSA/HRA product codes (Code Nos. 890\u2013894)",
    "\u2022 Reserved (proprietary use) codes (Code Nos. 895\u2013899)",
    "\u2022 Negative codes (Code Nos. 900\u2013949)",
    "\u2022 Administrative codes (Code Nos. 950\u2013999)",
}
TABLE_INTRODUCTIONS = {
    "The following table contains unused codes:",
    "The following table contains motor fuel codes:",
    "The following table contains automotive product/service codes:",
    "The following table contains aviation fuel codes:",
    "The following table contains aviation product/service codes:",
    "The following table contains marine fuel codes:",
    "The following table contains marine product/service codes:",
    "The following table contains other fuel codes:",
    "The following table contains merchandise codes:",
    "The following table contains codes reserved for future use:",
    "The following table contains debit surcharge codes:",
    "The following table contains codes reserved for proprietary use:",
    "The following table contains FSA/HRA Product codes:",
    "The following table contains negative codes:",
    "The following table contains administrative codes:",
}


def digest_text(text):
    return hashlib.sha256(text.encode("utf-8")).hexdigest()


def document_lines(text):
    pages, page = {}, None
    for number, line in enumerate(text.splitlines(), 1):
        match = re.fullmatch(r"--- PAGE (\d+) ---", line.strip())
        if match:
            page = int(match[1])
        elif page is not None and line.strip():
            pages.setdefault(page, []).append((number, base.norm(line)))
    return pages


def block(lines, start, end):
    starts = [index for index, (_, line) in enumerate(lines) if line == base.norm(start)]
    if len(starts) != 1:
        raise ValueError(f"Unresolved or ambiguous obligation start: {start}")
    first = starts[0]
    ends = [index for index in range(first, len(lines)) if lines[index][1] == base.norm(end)]
    if not ends:
        raise ValueError(f"Missing obligation end: {end}")
    portion = lines[first:ends[0] + 1]
    expected = base.norm(start if start == end else start + " " + end)
    if base.norm(" ".join(line for _, line in portion)) != expected:
        raise ValueError("Unreviewed text inside an authored obligation boundary")
    return portion


def parse_appendix(text, release, scope):
    pages = document_lines(text)
    if release["lastPage"] - release["firstPage"] != 21:
        raise ValueError("Appendix F must span exactly 22 declared pages")
    tables = {item["page"]: item for item in scope["tables"]}
    if len(tables) != 22 or set(tables) != set(range(1, 23)):
        raise ValueError("Every Appendix F printed page needs one explicit table scope")
    rows, dispositions, obligations = [], {}, []
    for offset in range(1, 23):
        pdf_page = release["firstPage"] + offset - 1
        lines, table = pages[pdf_page], tables[offset]
        texts = [line for _, line in lines]
        if f"Appendix F-{offset}" not in texts or table["heading"] not in texts:
            raise ValueError("Declared table heading or printed page does not match source")
        claims = {}
        for item in scope["obligations"]:
            if item["page"] == offset:
                segment = block(lines, item["start"], item["end"])
                for number, _ in segment:
                    claims[number] = item["id"]
                obligations.append({
                    "id": item["id"], "version": release["version"], "page": pdf_page,
                    "startLine": segment[0][0], "endLine": segment[-1][0],
                    "sourceTextSha256": digest_text(base.norm(" ".join(line for _, line in segment))),
                    "statement": item["statement"], "condition": item["condition"],
                    "kind": item["kind"], "legacyCounterparts": item["legacy"],
                    "status": "SOURCE_DERIVED_DRAFT_NOT_DENOMINATOR_APPROVED"})
        repeated = set()
        if offset in REPEATED_NOTES:
            phrase = REPEATED_NOTES[offset]
            for start in range(len(lines)):
                for length in (1, 2, 3):
                    portion = lines[start:start + length]
                    if base.norm(" ".join(line for _, line in portion)) == phrase:
                        repeated.update(number for number, _ in portion)
            if not repeated:
                raise ValueError("Repeated fuel uniqueness note changed")
        previous = None
        for number, line in lines:
            if number in claims:
                dispositions[number] = {"kind": "DERIVED_PROSE_OBLIGATION", "id": claims[number]}
                previous = None
                continue
            if number in repeated:
                dispositions[number] = {"kind": "REPEATED_OBLIGATION", "id": "NMP-O03"}
                previous = None
                continue
            match, reserved = ROW.fullmatch(line), RESERVED.fullmatch(line)
            if match or reserved:
                found = match or reserved
                low, high, description = found[1], found[2] or found[1], found[3]
                if not table["low"] <= int(low) <= int(high) <= table["high"]:
                    raise ValueError("Source row lies outside its declared detailed table")
                previous = {
                    "startCode": low, "endCode": high, "description": description,
                    "table": table["table"], "page": pdf_page, "printedPage": offset,
                    "startLine": number, "endLine": number,
                    "sourceRange": low if high == low else f"{low}-{high}"}
                rows.append(previous)
                dispositions[number] = {"kind": "CLASSIFICATION_ROW", "id": "BR-APPF-ROW-" + low}
                continue
            if line in CONTINUATIONS:
                if previous is None or previous["endLine"] != number - 1:
                    raise ValueError("Unattached product-row continuation")
                previous["description"] += " " + line
                previous["endLine"] = number
                dispositions[number] = {
                    "kind": "CLASSIFICATION_ROW_CONTINUATION", "id": "BR-APPF-ROW-" + previous["startCode"]}
                continue
            previous = None
            range_heading = re.fullmatch(r"\(Code Nos?\. (\d{3})(?:[\u2013-](\d{3}))?\)", line)
            if range_heading and (int(range_heading[1]) != table["low"]
                                  or int(range_heading[2] or range_heading[1]) != table["high"]):
                raise ValueError("Detailed table range heading changed")
            is_header = (
                line in PARAGRAPH_HEADERS or line == table["heading"]
                or line in {"BUYPASS\u00ae Platform ATL105 Message Format Specifications",
                            "Software Version: ATL105",
                            "Specification Release Version: " + release["version"],
                            "October 09, 2026" if release["version"] == "2026-4" else "August 07, 2026"}
                or line == f"Appendix F-{offset}"
                or range_heading
                or line in TABLE_INTRODUCTIONS or offset == 1 and line in OVERVIEW
            )
            if not is_header:
                raise ValueError(f"Unadjudicated Appendix F line {number}: {line}")
            dispositions[number] = {"kind": "SOURCE_HEADING_OR_CATALOG_INDEX", "id": ""}
    codes = [number for row in rows for number in range(int(row["startCode"]), int(row["endCode"]) + 1)]
    if len(rows) != 380 or len(codes) != 1000 or set(codes) != set(range(1000)):
        raise ValueError("Appendix F must have 380 rows, no overlaps and exactly codes 000-999")
    return rows, dispositions, obligations


def assignment(row):
    if row["table"] == "UNUSED":
        return "UNUSED"
    if "reserved" in row["description"].casefold():
        return "RESERVED"
    if "undefined" in row["description"].casefold():
        return "UNDEFINED"
    return "ASSIGNED"


def source_role(row):
    if row["table"] != "DEBIT_SURCHARGE_TABLE":
        return row["table"]
    for prefix, role in (
            ("Administrative", "ADMINISTRATIVE"), ("Merchandise", "MERCHANDISE"),
            ("Motor Fuels", "MOTOR_FUEL"), ("Negative Transaction", "NEGATIVE")):
        if row["description"].startswith(prefix):
            return role
    raise ValueError("Unreviewed mixed-role surcharge row")


def complete_petroleum_inventory(numeric_rows, sources):
    rows = [dict(row) for row in numeric_rows]
    for page, low, high in ((165, 810, 889), (166, 897, 899)):
        text = sources["pages"][page]["text"]
        matches = [line.strip() for line in text.splitlines()
                   if re.fullmatch(rf"Code nos\. {low}[\u2013-]{high} are reserved for proprietary use\.",
                                   line.strip())]
        if len(matches) != 1 or "Codes Reserved for Proprietary Use" not in text:
            raise ValueError("Petroleum prose reservation source changed")
        rows.append({
            "code": f"{low}-{high}", "low": low, "high": high, "page": page,
            "category": "Reserved (proprietary use)", "fuel": False,
            "extractionKind": "PROSE_DECLARED_RESERVATION",
            "sourceTextSha256": sources["pages"][page]["textSha256"],
            "claimSha256": digest_text(base.norm(matches[0]))})
    for row in rows:
        row.setdefault("extractionKind", "NUMERIC_TABLE_ROW")
        row.setdefault("sourceTextSha256", sources["pages"][row["page"]]["textSha256"])
    return rows


def petroleum_code_crosswalk(records, petroleum_rows):
    index = {}
    for row in petroleum_rows:
        for value in range(row["low"], row["high"] + 1):
            if value in index:
                raise ValueError("Petroleum product-code intervals overlap")
            index[value] = row
    rows = []
    for record in records:
        bounds = record["sourceRange"].split("-")
        for number in range(int(bounds[0]), int(bounds[-1]) + 1):
            other = index.get(number)
            rows.append({
                "code": f"{number:03}", "legacyBrId": record["legacyBrId"],
                "atl105Table": record["table"], "atl105Assignment": record["assignment"],
                "atl105Source": record["newSource"],
                "petroleumRow": None if other is None else {
                    "range": other["code"], "page": other["page"],
                    "category": other["category"], "fuelCategory": other["fuel"],
                    "extractionKind": other["extractionKind"],
                    "sourceTextSha256": other["sourceTextSha256"]},
                "catalogPresence": "BOTH_PINNED_TABLES" if other else "ATL105_ONLY_NOT_PETROLEUM_LISTED",
                "descriptionEquivalence": "NOT_INFERRED_FROM_CODE_IDENTITY",
                "cardProductPermission": "NOT_ESTABLISHED_BY_EITHER_CATALOG",
                "scope": "VFC_BR25_MEMBERSHIP_VERSUS_EACH_ATL105_CLASSIFICATION_ROW",
                "disputedAtl105Classification": f"{number:03}" in record["disputedCodes"],
                "automaticMerge": False})
    if len(rows) != 1000 or len({row["code"] for row in rows}) != 1000:
        raise ValueError("Cross-catalogue comparison must retain all 1000 codes")
    missing = {row["code"] for row in rows if row["petroleumRow"] is None}
    if missing != {f"{value:03}" for value in range(541, 550)}:
        raise ValueError("Pinned Petroleum/ATL105 catalog differences changed; re-adjudicate")
    return rows


def derive(scope, old_text, new_text, oracle, legacy, units, current, companion_doc):
    if (scope["schemaVersion"] != 1 or scope["status"] != "DRAFT_REVIEW_REQUIRED"
            or scope["scope"] != "APPENDIX_F_ROW_CLASSIFICATION_NOT_TRANSACTION_ACCEPTANCE"):
        raise ValueError("Only draft classification scope is supported")
    versions = {row["version"]: row for row in scope["releases"]}
    if set(versions) != {"2026-3", "2026-4"} or len(scope["releases"]) != 2:
        raise ValueError("Exactly two explicit release authorities required")
    if scope["conflict"] != {
            "codes": ["895", "896"], "overviewPage": 1, "detailedPage": 19,
            "followingReservedPage": 20, "status": "SOURCE_PRECEDENCE_REVIEW_REQUIRED",
            "meaning": ("The overview ends FSA/HRA at 894 and begins proprietary reservation at 895; "
                        "the detailed tables assign 895/896 and begin reservation at 897. "
                        "Preserve both claims; do not silently reject or accept disputed codes.")}:
        raise ValueError("Source conflict must remain explicit and unresolved")
    old, _, _ = parse_appendix(old_text, versions["2026-3"], scope)
    new, line_ledger, obligations = parse_appendix(new_text, versions["2026-4"], scope)
    original_rows = {row["startCode"]: row for row in oracle["rows"]}
    if (oracle["version"] != "2026-3" or len(original_rows) != len(oracle["rows"])
            or set(original_rows) != {row["startCode"] for row in old}):
        raise ValueError("Original product classification oracle population changed")
    current_ids = set(base.unique(current["businessRequirements"]))
    classifications = base.gates.load(parent.DECISIONS)["classificationGroups"]
    group_ids = {"BR-" + identity for group in classifications
                 if group["kind"] == "PROGRAM_ELIGIBILITY" for identity in group["rules"]}
    if set(scope["currentComparison"]["programEligibility"]) != group_ids:
        raise ValueError("Authored program-eligibility counterpart population changed")
    other_current = current_ids - group_ids
    if len(current_ids) != 71 or len(other_current) != 61:
        raise ValueError("Current comparison universe changed; re-adjudicate")
    companion_ids = set(base.unique(companion_doc["rules"]))
    companion_members = [identity for group in scope["companionComparison"] for identity in group["ids"]]
    if (len(companion_members) != len(set(companion_members))
            or set(companion_members) != companion_ids or len(companion_ids) != 61):
        raise ValueError("Every companion BR needs exactly one explicit product comparison disposition")
    comparisons = [
        {"ids": sorted(group_ids), "relation": "ELIGIBILITY_NOT_CLASSIFICATION",
         "reason": scope["currentComparison"]["programEligibilityReason"]},
        {"ids": sorted(other_current), "relation": "DIFFERENT_ASSERTION_NOT_ROW_CLASSIFICATION",
         "reason": scope["currentComparison"]["otherReason"]},
        *scope["companionComparison"],
    ]
    historical = {row["legacyBrId"]: row for row in legacy}
    expected_legacy = {"BR-APPF-ROW-" + row["startCode"] for row in new}
    if (len(historical) != len(legacy) or
            {identity for identity in historical if identity.startswith("BR-APPF-ROW-")} != expected_legacy):
        raise ValueError("All 380 legacy row-classification assertions must be retained")
    records = []
    for before, after in zip(old, new):
        identity = "BR-APPF-ROW-" + after["startCode"]
        row = original_rows[before["startCode"]]
        anchor = row["sourceAnchor"]
        if (any(before[key] != after[key] for key in ("startCode", "endCode", "description", "table"))
                or any(row[key] != before[key] for key in ("startCode", "endCode", "description", "table"))
                or anchor["startLine"] != before["startLine"] or anchor["endLine"] != before["endLine"]
                or row["assignment"] != assignment(after) or row["role"] != source_role(after)):
            raise ValueError(f"Release row, source anchor or oracle classification differs: {identity}")
        legacy_row = historical[identity]
        if (anchor not in json.loads(legacy_row["sourceAnchors"])
                or legacy_row["title"] != f"Preserve source row {row['sourceRange']} and its detailed-table classification."):
            raise ValueError("Legacy assertion is not the reviewed classification-only statement")
        code_set = {f"{number:03}" for number in range(int(after["startCode"]), int(after["endCode"]) + 1)}
        conflict = sorted(code_set & set(scope["conflict"]["codes"]))
        records.append({
            "legacyBrId": identity, "sourceRange": after["sourceRange"],
            "descriptionSha256": digest_text(after["description"]),
            "table": after["table"], "assignment": row["assignment"], "role": row["role"],
            "oldSource": {"page": before["page"], "startLine": before["startLine"],
                          "endLine": before["endLine"], "sha256": base.OLD_HASH},
            "newSource": {"page": after["page"], "startLine": after["startLine"],
                          "endLine": after["endLine"], "sha256": base.gates.SOURCE_SHA256},
            "releaseDecision": "SAME_ROW_DESCRIPTION_AND_DETAILED_CLASSIFICATION",
            "applicability": "SOURCE_CLASSIFICATION_ONLY_WHEN_RESOLVING_AN_ELEMENT77_PRODUCT_CODE",
            "transactionAcceptance": "NOT_ESTABLISHED_BY_CLASSIFICATION",
            "networkInheritance": "NONE_ASSERTED_GENERIC_METADATA_NOT_ROUTING_PERMISSION",
            "overlapAgainst132RetainedCurrentAndCompanionRules": "NO_EQUIVALENT_CLASSIFICATION_ASSERTION",
            "comparisonEvidence": "counterpart-partition.json",
            "otherRowOverlap": "DISJOINT_NUMERIC_DOMAIN_PROVED_WITHIN_380_ROW_ASSERTIONS",
            "otherLegacyOverlap": "NOT_ADJUDICATED_OUTSIDE_THIS_BATCH",
            "disputedCodes": conflict,
            "sourcePrecedence": "REVIEW_REQUIRED" if conflict else "DETAILED_ROW_CLASSIFICATION_ONLY",
            "atomicBusinessDenominator": False, "approvedDenominatorDelta": None,
            "status": "DRAFT_ASSERTION_REVIEW_COMPLETE_SOURCE_CONFLICTS_RETAINED"})
    # A unit is closed only if each retained line is accounted for, including tables and notes.
    new_lines = new_text.splitlines()
    source_ledger = []
    for unit in units:
        page = int(unit["page"])
        if not versions["2026-4"]["firstPage"] <= page <= versions["2026-4"]["lastPage"]:
            continue
        start, end = int(unit["startLine"]), int(unit["endLine"])
        text = base.norm(" ".join(new_lines[start - 1:end]))
        if text != unit["quote"] or digest_text(text) != unit["sha256"]:
            raise ValueError("Retained source-unit boundaries or digest changed")
        retained = [number for number in range(start, end + 1) if new_lines[number - 1].strip()]
        if set(retained) - line_ledger.keys():
            raise ValueError("Source unit has unclassified lines")
        source_ledger.append({
            "sourceUnitId": unit["sourceUnitId"], "page": page, "sha256": unit["sha256"],
            "startLine": start, "endLine": end, "classifiedLineCount": len(retained),
            "classifications": [{"line": number, **line_ledger[number]} for number in retained],
            "derivationStatus": "ALL_RETAINED_LINES_DERIVED_OR_NON_OBLIGATION_CLASSIFIED",
            "approvalStatus": "DRAFT_NOT_APPROVED",
            "sourceConflicts": ["NMP-CONFLICT-895-896"] if page in {523, 541, 542} else [],
            "scope": "THIS_APPENDIX_UNIT_ONLY_NOT_OTHER_REFERENCED_MESSAGE_RULES"})
    if len(source_ledger) != 23:
        raise ValueError("Exactly 23 retained Appendix F source units required")
    return records, source_ledger, obligations, comparisons


def report(summary):
    c = summary["counts"]
    return "\n".join([
        "# Non-Mastercard Appendix F semantic derivation", "",
        "**Overall request remains incomplete. This is a fully reviewed Appendix F batch.**", "",
        f"- {c['legacyClassificationAssertions']} legacy row-classification assertions adjudicated.",
        "- All 380 detailed source rows match across ATL105 2026-3 and 2026-4,",
        "  including description, code interval, table and original oracle/source anchor.",
        "- Their intervals partition 000-999 without gaps or overlaps.",
        f"- {c['sourceUnitsDerived']} retained source units derived line by line: rows,",
        "  headings, overview metadata, five prose obligations and repeated notes.",
        "- Every one of the retained 71 current BRs and 61 Petroleum companion BRs",
        "  has an explicit comparison partition against the classification assertion.",
        "- Classification is not merchant permission, product eligibility, amount",
        "  reconciliation, fuel uniqueness, message validity or host approval.",
        "- Codes 895/896 retain the overview/detailed-table source conflict.",
        "- Reserved/undefined values are not automatically accepted or rejected.",
        "- Code 800 preserves its stated credit/debit scope; it is not debit-only.", "",
        "## Petroleum counterparts", "",
        "- Appendix B membership and Appendix F descriptive classification remain",
        "  different authorities, with ATL105-only 541-549 retained.",
        "- Fleet product restrictions, list uniqueness, sum reconciliation and",
        "  chip limits remain additional assertions, not duplicate classifications.",
        "- All 1,000 numeric codes are cross-checked against the pinned Petroleum",
        "  Appendix B inventory; 541-549 are retained as ATL105-only, not silently allowed.",
        "- Petroleum's 810-889 and 897-899 reservations are explicitly declared in",
        "  prose. The historical numeric-row extractor omits these 83 values;",
        "  that omission is not proof of source absence or transaction permission.",
        "- The comparison covers all 61 retained companion BRs, but not every",
        "  remaining legacy BR or unmodeled Petroleum obligation.", "",
        "## Remaining work", "",
        f"- {c['remainingLegacyWithoutNewOrPriorDecisions']} of the previously unreviewed",
        "  legacy BRs still need exhaustive semantic overlap/applicability review.",
        f"- {c['remainingSourceUnits']} retained source units still need full derivation.",
        "- Cross-legacy relationships outside these 380 disjoint row assertions remain open.",
        "- Complete Petroleum-to-legacy review beyond this classification block remains open.",
        "- Denominator approval, conditional-message oracles and real execution remain blocked.", "",
        "## Evidence", "",
        "- [Row-level adjudications](legacy-adjudications.json)",
        "- [Line-complete source-unit derivations](source-derivation.json)",
        "- [Derived prose obligations](obligations.json)",
        "- [Exhaustive finite counterpart partition](counterpart-partition.json)",
        "- [All-code Petroleum catalogue crosswalk](petroleum-code-crosswalk.json)",
        "- [Retained source conflict](source-conflict.json)",
        "- [Summary](summary.json)",
        "- [Frozen input identities](input-manifest.json)", "",
    ])


def run(delivery, output=OUTPUT, check=False):
    delivery, output = delivery.resolve(), output.resolve()
    if output.is_relative_to(delivery) or delivery.is_relative_to(output):
        raise ValueError("Output must be disjoint from original delivery")
    if not check and output.exists():
        raise ValueError("New output directory required")
    frozen = completion.v1.freeze_delivery(delivery)
    with contextlib.redirect_stdout(io.StringIO()):
        petroleum.run(delivery, petroleum.OUTPUT, check=True)
    paths = [SCOPE, ORACLE, Path(__file__), Path(petroleum.__file__), Path(parent.__file__),
             parent.DECISIONS, petroleum.DECISIONS, base.OLD_SOURCE, base.gates.SOURCE]
    paths.extend(path for folder in (petroleum.OUTPUT, parent.OUTPUT, parent.v1.OUTPUT, completion.OUTPUT)
                 for path in sorted(folder.iterdir()) if path.is_file())
    paths.extend(Path(row["path"]) for row in base.gates.load(petroleum.OUTPUT / "input-manifest.json"))
    manifest = [{"path": str(path.resolve()), "sha256": base.digest(path)}
                for path in dict.fromkeys(paths)]
    scope = base.gates.load(SCOPE)
    legacy = parent.read_csv(parent.OUTPUT / "legacy-semantic-register.csv")
    units = parent.read_csv(parent.v1.OUTPUT / "source-unit-register.csv")
    records, source, obligations, comparisons = derive(
        scope, base.OLD_SOURCE.read_text(encoding="utf-8-sig"),
        base.gates.SOURCE.read_text(encoding="utf-8-sig"), base.gates.load(ORACLE), legacy, units,
        base.gates.load(completion.OUTPUT / "model-package.json"), base.gates.load(petroleum.DECISIONS))
    sources = completion.fleet.load_sources()
    cfg = base.gates.load(completion.fleet.KB / "visa-fleet" / "context-input.json")
    product_catalog = completion.fleet.validate_product_codes(cfg["catalog"]["productCodes"], sources)
    petroleum_rows = complete_petroleum_inventory(product_catalog["rows"], sources)
    crosswalk = petroleum_code_crosswalk(records, petroleum_rows)
    reviewed = {row["legacyBrId"] for row in legacy if json.loads(row["semanticRelationIds"])}
    closed = {row["legacyBrId"] for row in records}
    summary = {
        "status": "APPENDIX_F_BATCH_COMPLETE_OVERALL_SEMANTIC_REQUEST_INCOMPLETE",
        "counts": {"legacyClassificationAssertions": len(records), "sourceUnitsDerived": len(source),
                   "proseObligations": len(obligations), "numericCodesPartitioned": 1000,
                   "retainedCounterpartRulesCompared": sum(len(row["ids"]) for row in comparisons),
                   "petroleumCodesCompared": len(crosswalk),
                   "atl105OnlyCodes": sum(row["petroleumRow"] is None for row in crosswalk),
                   "petroleumProseReservationCodes": sum(
                       row["petroleumRow"] is not None
                       and row["petroleumRow"]["extractionKind"] == "PROSE_DECLARED_RESERVATION"
                       for row in crosswalk),
                   "remainingLegacyWithoutNewOrPriorDecisions": len(legacy) - len(reviewed | closed),
                   "remainingSourceUnits": len(units) - len(source)},
        "wholeSpecSemanticsComplete": False, "allLegacyOverlapComplete": False,
        "petroleumToAllLegacyReviewComplete": False, "approvedDenominator": None,
        "confirmedRequirementCoveragePercent": None, "executionReady": False, "baselineChanged": False}
    outputs = {"summary.json": summary, "legacy-adjudications.json": records,
               "source-derivation.json": source, "obligations.json": obligations,
               "counterpart-partition.json": comparisons, "source-conflict.json": scope["conflict"],
               "petroleum-code-crosswalk.json": crosswalk,
               "input-manifest.json": manifest}
    expected = {name: (json.dumps(value, ensure_ascii=True, indent=2) + "\n").encode("utf-8")
                for name, value in outputs.items()}
    expected["REPORT.md"] = report(summary).encode("utf-8")
    if (completion.v1.freeze_delivery(delivery) != frozen
            or any(base.digest(Path(row["path"])) != row["sha256"] for row in manifest)):
        raise ValueError("Product semantic input population or hashes changed")
    if check:
        parent.v1.verify_outputs(output, expected)
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
