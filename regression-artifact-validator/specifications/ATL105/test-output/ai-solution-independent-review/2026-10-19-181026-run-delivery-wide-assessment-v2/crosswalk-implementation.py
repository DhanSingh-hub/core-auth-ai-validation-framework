import argparse
import csv
import json
import os
import re
from collections import defaultdict
from pathlib import Path


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def normalized_element(value):
    value = str(value or "").strip()
    return str(int(value)) if re.fullmatch(r"\d+", value) else None


def source_rule_index(rules):
    index = defaultdict(list)
    for rule in rules:
        anchors = rule.get("sourceAnchors") or [rule.get("sourceAnchor") or {}]
        for anchor in anchors:
            elements = [normalized_element(value) for value in re.split(r"[,|]", str(anchor.get("element") or ""))]
            segments = [value.strip().upper() for value in str(anchor.get("segment") or "").split(",")]
            for segment in segments:
                for element in elements:
                    if segment and element:
                        index[(segment, element)].append(rule)
    return index


def ai_anchor(requirement, index):
    related = requirement.get("related_entity_ids") or []
    segments = set()
    elements = set()
    for value in related:
        segment = re.fullmatch(r"ENT-SEG-(.+)", str(value), re.IGNORECASE)
        if segment:
            segments.add(segment.group(1).strip().upper())
        element = re.fullmatch(r"ENT-ELEM-(\d+)", str(value), re.IGNORECASE)
        if element:
            elements.add(normalized_element(element.group(1)))
    source_rule_id = str(requirement.get("source_rule_id") or "").strip()
    source_element = normalized_element(source_rule_id)
    if source_element is None:
        element = re.fullmatch(r"ENT-ELEM-(\d+)", source_rule_id, re.IGNORECASE)
        if element:
            source_element = normalized_element(element.group(1))
    if source_element:
        elements.add(source_element)
    matches = {}
    for segment in sorted(segments):
        for element in sorted(elements):
            for rule in index.get((segment, element), []):
                matches[rule["ruleId"]] = rule
    if not elements:
        status = "NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR"
    elif not segments:
        status = "NOT_MATCHABLE_SEGMENT_UNRESOLVED"
    elif not matches:
        status = "NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED"
    elif len(elements) > 1 or len(segments) > 1:
        status = "REVIEW_REQUIRED_AMBIGUOUS_OR_COMPOSITE_CANDIDATE"
    elif len(matches) == 1 and next(iter(matches.values())).get("atomicity") == "ATOMIC":
        status = "REVIEW_REQUIRED_UNIQUE_EXACT_SOURCE_ANCHOR_CANDIDATE"
    else:
        status = "REVIEW_REQUIRED_AMBIGUOUS_OR_COMPOSITE_CANDIDATE"
    return sorted(segments), sorted(elements), status, [matches[key] for key in sorted(matches)]


def write_csv(path, rows, fields):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows(rows)


def export(ai_requirements_path, test_solution_dir, output_dir, test_solution_package_path=None):
    ai_requirements_path = Path(os.path.normpath(str(ai_requirements_path)))
    test_solution_dir = Path(os.path.normpath(str(test_solution_dir)))
    output_dir = Path(os.path.normpath(str(output_dir)))
    baseline = read_json(test_solution_dir / "source-derived-requirement-inventory.json")
    classification = read_json(test_solution_dir / "atomic-composite-rule-classification.json")
    ai_catalog = read_json(ai_requirements_path)
    requirements = ai_catalog.get("requirements") or []
    test_rules = baseline.get("businessRequirements") or []
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Test BR baseline must be independent of AI artifacts")
    if baseline.get("ruleCount") != len(test_rules):
        raise ValueError("Official Test BR count disagrees with its baseline rows")
    source_derived_rule_ids = {str(rule["ruleId"]) for rule in test_rules}
    added_test_rules = []
    added_test_rule_ids = []
    test_baseline_status = "SOURCE_DERIVED_INVENTORY"
    if test_solution_package_path:
        package = read_json(Path(os.path.normpath(str(test_solution_package_path))))
        manifest = package.get("manifest") or {}
        catalogued_rules = [
            row for row in package.get("businessRequirements") or []
            if str(row.get("id") or "").startswith("BR-RULE-")
        ]
        catalogued_by_id = {
            str(row["id"])[len("BR-RULE-"):]: row for row in catalogued_rules
        }
        declared_count = (package.get("summary") or {}).get("independentRuleDenominator")
        if manifest.get("authority") != "INDEPENDENT_TEST_SOLUTION" or manifest.get("aiArtifactsIncluded") is not False:
            raise ValueError("Expanded Test BR package must be independent of AI artifacts")
        if declared_count != len(catalogued_rules) or len(catalogued_by_id) != len(catalogued_rules):
            raise ValueError("Expanded package denominator disagrees with unique BR-RULE rows")
        if not source_derived_rule_ids.issubset(catalogued_by_id):
            raise ValueError("Expanded package omits rules from the source-derived baseline")
        added_test_rule_ids = sorted(set(catalogued_by_id) - source_derived_rule_ids)
        for rule_id in added_test_rule_ids:
            row = catalogued_by_id[rule_id]
            anchors = row.get("sourceAnchors") or []
            if not anchors:
                raise ValueError(f"New catalogued Test BR lacks source anchors: {rule_id}")
            added_test_rules.append({
                "ruleId": rule_id,
                "title": row.get("title", ""),
                "class": "catalogued-addition",
                "atomicity": row.get("status") or "REVIEW_REQUIRED",
                "sourceAnchors": anchors,
                "catalogStatus": row.get("status"),
            })
        if len(test_rules) + len(added_test_rules) != declared_count:
            raise ValueError("Combined Test BR baseline disagrees with expanded package denominator")
        test_rules.extend(added_test_rules)
        test_baseline_status = manifest.get("status") or "UNDECLARED"
    if output_dir.exists():
        raise ValueError(f"Output directory already exists: {output_dir}")
    index = source_rule_index((classification.get("rules") or []) + added_test_rules)
    test_by_id = {rule["ruleId"]: rule for rule in test_rules}
    if len(test_by_id) != len(test_rules):
        raise ValueError("Test BR baseline contains duplicate IDs")

    pairs = []
    unmatched = []
    by_test_br = defaultdict(list)
    disposition_counts = defaultdict(int)
    matched_ai = set()
    for requirement in requirements:
        ai_id = str(requirement.get("id") or "")
        if not ai_id:
            raise ValueError("AI requirement is missing an ID")
        segments, elements, status, matches = ai_anchor(requirement, index)
        disposition_counts[status] += 1
        if not matches:
            unmatched.append({
                "aiBrId": ai_id,
                "aiBrStatement": requirement.get("statement", ""),
                "aiSourceRuleId": requirement.get("source_rule_id", ""),
                "aiCandidateSegments": ";".join(segments),
                "aiCandidateElements": ";".join(elements),
                "aiBrFlags": json.dumps(requirement.get("flags") or [], ensure_ascii=True),
                "noCandidateReason": status,
                "semanticMatchStatus": "NOT_ASSESSED",
            })
            continue
        matched_ai.add(ai_id)
        for rule in matches:
            rule_id = rule["ruleId"]
            pair = {
                "testBrId": rule_id,
                "testBrTitle": rule.get("title", ""),
                "testBrClass": rule.get("class", ""),
                "testBrAtomicity": rule.get("atomicity", ""),
                "testBrSourceAnchor": json.dumps(rule.get("sourceAnchor") or rule.get("sourceAnchors") or {}, ensure_ascii=True, sort_keys=True),
                "aiBrId": ai_id,
                "aiBrStatement": requirement.get("statement", ""),
                "aiSourceRuleId": requirement.get("source_rule_id", ""),
                "aiCandidateSegments": ";".join(segments),
                "aiCandidateElements": ";".join(elements),
                "aiBrFlags": json.dumps(requirement.get("flags") or [], ensure_ascii=True),
                "candidateDisposition": status,
                "semanticMatchStatus": "NOT_ASSESSED",
            }
            pairs.append(pair)
            by_test_br[rule_id].append(pair)

    test_index = []
    for rule_id in sorted(by_test_br):
        rule = test_by_id[rule_id]
        linked = sorted(by_test_br[rule_id], key=lambda row: row["aiBrId"])
        test_index.append({
            "testBrId": rule_id,
            "testBrTitle": rule.get("title", ""),
            "testBrClass": rule.get("class", ""),
            "testBrAtomicity": rule.get("atomicity", ""),
            "testBrSourceAnchor": json.dumps(rule.get("sourceAnchor") or rule.get("sourceAnchors") or {}, ensure_ascii=True, sort_keys=True),
            "matchedAiBrCount": len(linked),
            "matchedAiBrIds": ";".join(row["aiBrId"] for row in linked),
            "semanticMatchStatus": "NOT_ASSESSED",
        })

    output_dir.mkdir(parents=True)
    pair_fields = list(pairs[0]) if pairs else [
        "testBrId", "testBrTitle", "testBrClass", "testBrAtomicity", "testBrSourceAnchor",
        "aiBrId", "aiBrStatement", "aiSourceRuleId", "aiCandidateSegments", "aiCandidateElements",
        "aiBrFlags", "candidateDisposition", "semanticMatchStatus",
    ]
    index_fields = [
        "testBrId", "testBrTitle", "testBrClass", "testBrAtomicity", "testBrSourceAnchor",
        "matchedAiBrCount", "matchedAiBrIds", "semanticMatchStatus",
    ]
    write_csv(output_dir / "ai-test-br-candidate-pairs.csv", pairs, pair_fields)
    unmatched_fields = [
        "aiBrId", "aiBrStatement", "aiSourceRuleId", "aiCandidateSegments", "aiCandidateElements",
        "aiBrFlags", "noCandidateReason", "semanticMatchStatus",
    ]
    write_csv(output_dir / "ai-brs-without-test-br-candidates.csv", unmatched, unmatched_fields)
    write_csv(output_dir / "test-br-ai-br-index.csv", test_index, index_fields)
    summary = {
        "artifact": "ATL105-AI-TO-TEST-BR-SOURCE-ANCHOR-CROSSWALK",
        "aiRequirementCount": len(requirements),
        "officialIndependentTestBrCount": len(test_rules),
        "sourceDerivedTestBrCount": baseline.get("ruleCount"),
        "cataloguedDraftTestBrCount": len(added_test_rules),
        "cataloguedDraftTestBrIds": added_test_rule_ids,
        "testBaselineStatus": test_baseline_status,
        "aiBrsWithCandidates": len(matched_ai),
        "aiBrsWithoutCandidates": len(unmatched),
        "testBrsWithCandidates": len(by_test_br),
        "candidatePairCount": len(pairs),
        "testBrCandidateReachPercent": round(100 * len(by_test_br) / len(test_rules), 2) if test_rules else None,
        "aiBrCandidateDispositionCounts": dict(sorted(disposition_counts.items())),
        "semanticEquivalence": "NOT_ASSESSED",
        "policy": "Exact normalized source segment plus numeric element anchor only. All pairs are review-required candidates; no semantic confirmation or SME approval is inferred.",
        "files": ["ai-test-br-candidate-pairs.csv", "test-br-ai-br-index.csv", "ai-brs-without-test-br-candidates.csv"],
    }
    (output_dir / "crosswalk-summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
    print(json.dumps({"outputDirectory": str(output_dir), **summary}, indent=2))


def self_test():
    test_rule = {
        "ruleId": "SEG100-R-001", "title": "Sample", "class": "field", "atomicity": "ATOMIC",
        "sourceAnchor": {"segment": "100", "element": "0055"},
    }
    index = source_rule_index([test_rule])
    segments, elements, status, matches = ai_anchor({
        "id": "AI-1", "source_rule_id": "55", "related_entity_ids": ["ENT-SEG-100", "ENT-ELEM-55"]
    }, index)
    assert segments == ["100"] and elements == ["55"] and matches == [test_rule]
    assert status == "REVIEW_REQUIRED_UNIQUE_EXACT_SOURCE_ANCHOR_CANDIDATE"
    segments, elements, status, matches = ai_anchor({
        "id": "AI-2", "source_rule_id": "ENT-ELEM-55", "related_entity_ids": ["ENT-SEG-100"]
    }, index)
    assert segments == ["100"] and elements == ["55"] and matches == [test_rule]
    assert status == "REVIEW_REQUIRED_UNIQUE_EXACT_SOURCE_ANCHOR_CANDIDATE"
    segments, elements, status, matches = ai_anchor({"id": "AI-3", "source_rule_id": "ENT-ELEM-55"}, index)
    assert not segments and elements == ["55"] and not matches
    assert status == "NOT_MATCHABLE_SEGMENT_UNRESOLVED"
    print("PASS: normalized source-anchor candidates remain review-required")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--ai-requirements", type=Path)
    parser.add_argument("--test-solution-directory", type=Path)
    parser.add_argument("--test-solution-package", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not all((args.ai_requirements, args.test_solution_directory, args.output_directory)):
        parser.error("--ai-requirements, --test-solution-directory, and --output-directory are required")
    export(args.ai_requirements, args.test_solution_directory, args.output_directory, args.test_solution_package)


if __name__ == "__main__":
    main()