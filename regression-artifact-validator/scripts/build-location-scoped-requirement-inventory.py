"""Derive segment-free ATL105 Test BRs from the pinned source text.

The 601-rule segment-catalog baseline only anchors rules by segment + element.
ATL105 also defines message-level fields outside any data segment (Chapter 11
Data Section 1 tables and positional response/load layouts) and a segment-
agnostic data element dictionary (Chapter 13). This builder derives an
independent supplemental inventory for those locations without modifying the
frozen baseline.
"""

import argparse
import hashlib
import json
import re
import sys
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
SOURCE_TEXT = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
TEST_SOLUTION = ROOT / "specifications/ATL105/test-output/test-solution-independent-review"
BASELINE = TEST_SOLUTION / "source-derived-requirement-inventory.json"
OUTPUT_JSON = TEST_SOLUTION / "location-scoped-requirement-inventory.json"
OUTPUT_MD = TEST_SOLUTION / "location-scoped-requirement-inventory.md"
PINNED_SOURCE_SHA256 = {"2026-3": "6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa"}
REAL_SEGMENT = re.compile(r"\d{3}|DL\d")
HEADING = re.compile(r"^(\d{1,2}(?:\.\d+)+)\s+[A-Z]")
ROW = re.compile(r"^(\d{1,2}) ([0-9]{1,3}|DL\d) (.+)$")
REPEAT_CONTINUATION = re.compile(r"^(or|\d{1,2}\S*)$")
ATTRIBUTES = re.compile(r"^(?P<name>.+?)\s+(?:(?P<pos>\d+)\s+)?(?P<len>\d{1,3}(?:,\d{3})+|\d+|Var\.)\s+(?P<entry>[ROC])\d?\b(?P<rest>.*)$")
CHARACTER = re.compile(r"Character Type:\s*(?P<type>[A-Za-z0-9/]+)(?:\s+(?:(?P<kind>Maximum|Max\.|Fixed|Variable)\s+)?Length:\s*(?P<length>\d[\d,/]*)\s*bytes?)?")
ENTRY = {"R": "REQUIRED", "O": "OPTIONAL", "C": "CONDITIONAL"}
# Sections whose message layout is defined by reference or in an external document.
LAYOUT_REFERENCES = {
    "11.2.2": {"message": "Loyalty Card Transaction Response", "sameLayoutAs": "11.1.2"},
    "11.3.2": {"message": "ECA/TeleCheck Service Financial Transaction Response", "sameLayoutAs": "11.1.2"},
}
OUT_OF_SCOPE_SECTIONS = {
    "11.7.5": "TransArmor Key and Key ID Load layout is defined in the separate TransArmor specification-updates document, not in the pinned ATL105 text.",
}


def sha256(path):
    return hashlib.sha256(Path(path).read_bytes()).hexdigest()


def clean(value):
    return re.sub(r"\s+", " ", value).strip()


def element_key(value):
    return str(int(value)) if str(value).isdigit() else str(value).upper()


def parse_layouts(lines):
    """Return Chapter 11 message layouts with element/segment rows in source order."""
    layouts, current, pending = [], None, None
    page = section = mode = None
    data_section = "NONE"
    active = False
    index = 0

    def flush():
        nonlocal pending
        if pending is not None and current is not None:
            current["rows"].append(pending)
        pending = None

    def last_field():
        if pending is not None:
            return pending["field"]
        return current["rows"][-1]["field"] if current and current["rows"] else 0

    while index < len(lines):
        text = lines[index].strip()
        marker = re.match(r"--- PAGE (\d+) ---", text)
        if marker:
            page = int(marker.group(1))
        heading = HEADING.match(text)
        if heading:
            section = heading.group(1)
        if text == "Message Format" and index + 1 < len(lines) and section and section.startswith("11."):
            flush()
            name = clean(lines[index + 1])
            if not (current and current["message"] == name and current["section"] == section):
                current = {"message": name, "section": section, "startPage": page, "rows": [], "dataSections": []}
                layouts.append(current)
                mode, data_section = None, "NONE"
            active = True
            index += 2
            continue
        if active:
            if text.startswith("--- PAGE") or text.startswith("1Required/Optional") or heading:
                flush()
                active = False
            else:
                section_marker = re.match(r"^Data Section No\. (\d)", text)
                if section_marker:
                    flush()
                    data_section = section_marker.group(1)
                    if data_section not in current["dataSections"]:
                        current["dataSections"].append(data_section)
                if text in ("Ele.", "Element"):
                    flush()
                    mode = "ELEMENT"
                elif text in ("Seg.", "Segment"):
                    flush()
                    mode = "SEGMENT"
                row = ROW.match(text)
                expected = last_field() + 1
                if mode and text == str(expected):
                    # Repeating field such as "15 / or / 15-16 / or / 15-17 / 101 Terminal ...".
                    probe = index + 1
                    while probe < len(lines) and REPEAT_CONTINUATION.match(lines[probe].strip()):
                        probe += 1
                    element_row = re.match(r"^([0-9]{1,3}|DL\d) ([A-Za-z].*)$", lines[probe].strip()) if probe < len(lines) else None
                    if element_row and probe > index + 1:
                        flush()
                        pending = {"field": expected, "kind": mode, "number": element_row.group(1), "dataSection": data_section,
                                   "text": element_row.group(2), "page": page, "repeating": True}
                        index = probe + 1
                        continue
                if row and mode and int(row.group(1)) == expected and not re.match(r"^[ROC]\d?\b", row.group(3)):
                    flush()
                    pending = {"field": expected, "kind": mode, "number": row.group(2), "dataSection": data_section,
                               "text": row.group(3), "page": page, "repeating": False}
                elif pending is not None:
                    pending["text"] += " " + text
        index += 1
    flush()
    for layout in layouts:
        for row in layout["rows"]:
            text = clean(row.pop("text"))
            match = ATTRIBUTES.match(text)
            if match:
                variable = match.group("len") == "Var."
                row.update({"name": clean(match.group("name")), "position": int(match.group("pos")) if match.group("pos") else None,
                            "length": "VARIABLE" if variable else int(match.group("len").replace(",", "")),
                            "entry": ENTRY[match.group("entry")], "attributeParseStatus": "PARSED"})
            else:
                row.update({"name": text[:120], "position": None, "length": None, "entry": None, "attributeParseStatus": "REVIEW_REQUIRED"})
    return layouts


def parse_element_dictionary(lines):
    """Return Chapter 13 data element definitions in source order."""
    definitions, page, section = [], None, None
    index = 0
    while index < len(lines):
        text = lines[index].strip()
        marker = re.match(r"--- PAGE (\d+) ---", text)
        if marker:
            page = int(marker.group(1))
        heading = HEADING.match(text)
        if heading:
            section = heading.group(1)
        number = re.match(r"^Number:\s*(\d+)\s+Name:\s*(.*)$", text)
        if number:
            name, probe, attributes = [number.group(2)], index + 1, None
            while probe < len(lines) and probe <= index + 6:
                candidate = lines[probe].strip()
                if candidate.startswith("Character Type:"):
                    attributes = CHARACTER.search(candidate)
                    break
                if candidate:
                    name.append(candidate)
                probe += 1
            definitions.append({
                "element": element_key(number.group(1)), "name": clean(" ".join(name)), "page": page, "section": section,
                "characterType": attributes.group("type").upper() if attributes else None,
                "lengthKind": {"Max.": "MAXIMUM", "Maximum": "MAXIMUM", "Fixed": "FIXED", "Variable": "VARIABLE"}.get(attributes.group("kind"), "STATED") if attributes and attributes.group("length") else None,
                "length": attributes.group("length") if attributes and attributes.group("length") else None,
            })
            index = probe
        index += 1
    return definitions


def build(source_text=SOURCE_TEXT, baseline_path=BASELINE):
    source_text, baseline_path = Path(source_text), Path(baseline_path)
    baseline = json.loads(baseline_path.read_text(encoding="utf-8"))
    version = baseline.get("specificationVersion")
    source_hash = sha256(source_text)
    if PINNED_SOURCE_SHA256.get(version) != source_hash:
        raise ValueError(f"Source text hash {source_hash} is not the pinned ATL105 {version} text")
    lines = source_text.read_text(encoding="utf-8-sig").splitlines()
    layouts = parse_layouts(lines)
    definitions = parse_element_dictionary(lines)

    pseudo, segments = {}, {}
    for rule in baseline["businessRequirements"]:
        anchor = rule.get("sourceAnchor") or {}
        anchor_segments = [value.strip().upper() for value in str(anchor.get("segment") or "").split(",") if value.strip()]
        for element in re.split(r"[,|]", str(anchor.get("element") or "")):
            element = element.strip()
            if not element.isdigit():
                continue
            for segment in anchor_segments:
                if REAL_SEGMENT.fullmatch(segment):
                    segments.setdefault(element_key(element), set()).add(segment)
                elif segment != "UNKNOWN":
                    pseudo.setdefault(element_key(element), set()).add(rule["ruleId"])

    errors, rules, layout_summaries = [], [], []
    for layout in layouts:
        fields = [row["field"] for row in layout["rows"]]
        if fields != list(range(1, len(fields) + 1)):
            errors.append(f"Non-contiguous field sequence in {layout['section']} {layout['message']}: {fields}")
        layout_summaries.append({
            "section": layout["section"], "message": layout["message"], "startPage": layout["startPage"],
            "dataSections": layout["dataSections"] or ["NONE"],
            "elementFields": [{"field": row["field"], "element": element_key(row["number"]), "dataSection": row["dataSection"]} for row in layout["rows"] if row["kind"] == "ELEMENT"],
            "segmentFields": [{"field": row["field"], "segment": row["number"], "dataSection": row["dataSection"]} for row in layout["rows"] if row["kind"] == "SEGMENT"],
        })
        for row in layout["rows"]:
            if row["kind"] != "ELEMENT":
                continue
            element = element_key(row["number"])
            where = f"Data Section {row['dataSection']} Field {row['field']}" if row["dataSection"] != "NONE" else f"positional Field {row['field']} (no data segment)"
            details = ", ".join(part for part in (
                f"position {row['position']}" if row["position"] else None,
                ("variable length" if row["length"] == "VARIABLE" else f"length {row['length']}") if row["length"] else None,
                row["entry"].lower() if row["entry"] else None,
                "repeating" if row["repeating"] else None) if part)
            applies = [reference["message"] for section, reference in LAYOUT_REFERENCES.items() if reference["sameLayoutAs"] == layout["section"]]
            rules.append({
                "ruleId": f"LOC-{layout['section']}-F{row['field']:02d}-E{int(element):03d}",
                "title": f"{layout['message']} carries Element {element} ({row['name']}) at {where}" + (f"; {details}" if details else ""),
                "class": "location", "requirementType": "MESSAGE_LOCATION", "severity": "error",
                "sourceAnchor": {"specification": "ATL105", "version": version, "section": layout["section"], "page": row["page"],
                                 "message": layout["message"], "dataSection": row["dataSection"], "field": row["field"],
                                 "segment": None, "element": element, "rule": f"message-location-field-{row['field']}"},
                "attributes": {"elementName": row["name"], "position": row["position"], "length": row["length"], "entry": row["entry"],
                               "repeating": row["repeating"], "attributeParseStatus": row["attributeParseStatus"]},
                "appliesAlsoToMessages": applies,
                "relatedBaselineRuleIds": sorted(pseudo.get(element, ())),
                "elementAlsoSegmentScopedIn": sorted(segments.get(element, ())),
                "atomicity": "ATOMIC" if row["attributeParseStatus"] == "PARSED" else "REVIEW_REQUIRED",
                "verificationDimensions": ["message_location", "presence_and_structure"],
                "derivation": "CHAPTER_11_MESSAGE_LAYOUT_TABLE",
            })

    duplicates = {}
    for definition in definitions:
        duplicates[definition["element"]] = duplicates.get(definition["element"], 0) + 1
    suffix_state = {}
    for definition in definitions:
        element = definition["element"]
        rule_id = f"ELEM-{int(element):03d}-DEF"
        if duplicates[element] > 1:
            suffix_state[element] = suffix_state.get(element, 0) + 1
            rule_id += "-" + chr(64 + suffix_state[element])
        parsed = bool(definition["characterType"] and definition["length"])
        length = (f"length {definition['length']} bytes" if definition["lengthKind"] == "STATED" else f"{definition['lengthKind'].lower()} length {definition['length']} bytes") if parsed else None
        rules.append({
            "ruleId": rule_id,
            "title": f"Element {element} ({definition['name']}) conforms to its Chapter 13 definition" + (f": character type {definition['characterType']}, {length}" if parsed else ""),
            "class": "field", "requirementType": "ELEMENT_DEFINITION", "severity": "error",
            "sourceAnchor": {"specification": "ATL105", "version": version, "section": definition["section"], "page": definition["page"],
                             "segment": None, "element": element, "rule": f"element-{element}-definition"},
            "attributes": {"elementName": definition["name"], "characterType": definition["characterType"],
                           "lengthKind": definition["lengthKind"], "length": definition["length"],
                           "attributeParseStatus": "PARSED" if parsed else "REVIEW_REQUIRED"},
            "duplicateElementNumberReviewRequired": duplicates[element] > 1,
            "relatedBaselineRuleIds": sorted(pseudo.get(element, ())),
            "elementAlsoSegmentScopedIn": sorted(segments.get(element, ())),
            # Type and length are two independently testable behaviors.
            "atomicity": "COMPOSITE_CANDIDATE" if parsed else "REVIEW_REQUIRED",
            "verificationDimensions": ["character_type", "length"] if parsed else ["definition_review"],
            "derivation": "CHAPTER_13_DATA_ELEMENT_DICTIONARY",
        })

    identifiers = [rule["ruleId"] for rule in rules]
    baseline_ids = {rule["ruleId"] for rule in baseline["businessRequirements"]}
    if len(set(identifiers)) != len(identifiers):
        errors.append("Duplicate supplemental rule IDs")
    if set(identifiers) & baseline_ids:
        errors.append("Supplemental rule IDs collide with the frozen baseline")
    found_sections = {layout["section"] for layout in layouts}
    if set(LAYOUT_REFERENCES) & found_sections or set(OUT_OF_SCOPE_SECTIONS) & found_sections:
        errors.append("A referenced/out-of-scope section unexpectedly has its own layout table")
    if errors:
        raise ValueError("; ".join(errors))

    element_index = {}
    for rule in rules:
        entry = element_index.setdefault(rule["sourceAnchor"]["element"], {"messageLocationRuleIds": [], "definitionRuleIds": []})
        entry["messageLocationRuleIds" if rule["requirementType"] == "MESSAGE_LOCATION" else "definitionRuleIds"].append(rule["ruleId"])
    for element, entry in element_index.items():
        entry["baselinePseudoLocationRuleIds"] = sorted(pseudo.get(element, ()))
        entry["baselineSegments"] = sorted(segments.get(element, ()))
    by_type = {}
    for rule in rules:
        by_type[rule["requirementType"]] = by_type.get(rule["requirementType"], 0) + 1
    location_elements = sorted({rule["sourceAnchor"]["element"] for rule in rules if rule["requirementType"] == "MESSAGE_LOCATION"}, key=int)
    return {
        "artifact": "atl105-location-scoped-requirement-inventory",
        "specification": "ATL105",
        "specificationVersion": version,
        "authority": "INDEPENDENT_TEST_SOLUTION",
        "aiArtifactsIncluded": False,
        "sourceText": {"path": source_text.resolve().relative_to(ROOT).as_posix() if source_text.resolve().is_relative_to(ROOT) else source_text.name, "sha256": source_hash},
        "baselineInventory": {"path": baseline_path.resolve().relative_to(ROOT).as_posix() if baseline_path.resolve().is_relative_to(ROOT) else baseline_path.name,
                              "sha256": sha256(baseline_path), "ruleCount": len(baseline_ids)},
        "policy": "Supplements, never replaces, the frozen segment-catalog baseline. Rules are derived deterministically from Chapter 11 message layouts and the Chapter 13 element dictionary; AI artifacts are excluded.",
        "anchorPolicy": "Segment-free rules anchor to specification, version, section, page, message/data section/field or dictionary entry, and element. segment is null by design.",
        "layoutReferences": LAYOUT_REFERENCES,
        "outOfScopeSections": OUT_OF_SCOPE_SECTIONS,
        "messageLayouts": layout_summaries,
        "ruleCount": len(rules),
        "countsByRequirementType": by_type,
        "countsByAtomicity": {value: sum(rule["atomicity"] == value for rule in rules) for value in sorted({rule["atomicity"] for rule in rules})},
        "segmentFreeLocationElements": location_elements,
        "elementLocationIndex": dict(sorted(element_index.items(), key=lambda item: int(item[0]))),
        "businessRequirements": rules,
        "validation": {
            "valid": True, "layoutCount": len(layouts), "elementDefinitionCount": len(definitions),
            "duplicateElementDefinitions": sorted((element for element, count in duplicates.items() if count > 1), key=int),
            "attributeReviewRequired": sorted(rule["ruleId"] for rule in rules if rule["attributes"]["attributeParseStatus"] != "PARSED"),
            "requiresSemanticReview": True,
            "denominatorStatus": "SUPPLEMENT_TO_FROZEN_BASELINE_NOT_WHOLE_SPEC",
        },
    }


def render_markdown(inventory):
    lines = [
        "# ATL105 Location-Scoped Requirement Inventory",
        "",
        f"Source: `{inventory['sourceText']['path']}` (`{inventory['sourceText']['sha256']}`), ATL105 {inventory['specificationVersion']}.",
        f"Supplements the frozen {inventory['baselineInventory']['ruleCount']}-rule segment-catalog baseline; it does not replace it.",
        "",
        "| Requirement type | Rules |",
        "|---|---:|",
    ]
    lines += [f"| {name} | {count} |" for name, count in inventory["countsByRequirementType"].items()]
    lines += ["", f"Total supplemental rules: {inventory['ruleCount']}.", "", "## Message Layouts", "",
              "| Section | Message | Data sections | Segment-free element fields | Segment fields |", "|---|---|---|---|---|"]
    for layout in inventory["messageLayouts"]:
        elements = ", ".join(f"F{item['field']}=E{item['element']}" for item in layout["elementFields"]) or "-"
        segments = ", ".join(f"F{item['field']}={item['segment']}" for item in layout["segmentFields"]) or "-"
        lines.append(f"| {layout['section']} | {layout['message']} | {', '.join(layout['dataSections'])} | {elements} | {segments} |")
    lines += ["", "## Referenced And Out-Of-Scope Sections", ""]
    lines += [f"- {section}: {item['message']} uses the {item['sameLayoutAs']} layout." for section, item in inventory["layoutReferences"].items()]
    lines += [f"- {section}: {reason}" for section, reason in inventory["outOfScopeSections"].items()]
    review = inventory["validation"]
    lines += ["", "## Review Required", "",
              f"- Duplicate element numbers in the dictionary: {', '.join(review['duplicateElementDefinitions']) or 'none'}.",
              f"- Rules whose attributes need manual confirmation: {len(review['attributeReviewRequired'])}.",
              "- All rules are source-derived candidates; semantic approval and execution remain separate gates.", ""]
    return "\n".join(lines)


def serialize(inventory):
    return json.dumps(inventory, indent=2, ensure_ascii=False) + "\n"


def main(argv=None):
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source-text", type=Path, default=SOURCE_TEXT)
    parser.add_argument("--baseline-inventory", type=Path, default=BASELINE)
    parser.add_argument("--output-json", type=Path, default=OUTPUT_JSON)
    parser.add_argument("--output-markdown", type=Path, default=OUTPUT_MD)
    parser.add_argument("--check", action="store_true", help="Fail if the committed outputs differ from a fresh derivation.")
    args = parser.parse_args(argv)
    inventory = build(args.source_text, args.baseline_inventory)
    outputs = {args.output_json: serialize(inventory), args.output_markdown: render_markdown(inventory)}
    if args.check:
        stale = [str(path) for path, text in outputs.items() if not path.is_file() or path.read_text(encoding="utf-8") != text]
        if stale:
            print("STALE: " + ", ".join(stale), file=sys.stderr)
            return 1
        print(f"PASS: {inventory['ruleCount']} location-scoped rules match a fresh derivation")
        return 0
    for path, text in outputs.items():
        path.write_text(text, encoding="utf-8", newline="\n")
    print(json.dumps({"ruleCount": inventory["ruleCount"], "countsByRequirementType": inventory["countsByRequirementType"],
                      "validation": {key: inventory["validation"][key] for key in ("layoutCount", "elementDefinitionCount", "duplicateElementDefinitions")},
                      "attributeReviewRequired": len(inventory["validation"]["attributeReviewRequired"])}, indent=2))
    return 0


if __name__ == "__main__":
    sys.exit(main())
