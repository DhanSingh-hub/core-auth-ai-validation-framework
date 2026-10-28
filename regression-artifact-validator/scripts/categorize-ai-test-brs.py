#!/usr/bin/env python3
"""Group AI and independent Test BRs under a shared, reviewable taxonomy."""

import argparse
import csv
import hashlib
import json
import re
from collections import Counter, defaultdict
from pathlib import Path


CATEGORIES = {
    "FIELD": "Field",
    "SECURITY": "Security",
    "ACCOUNT_CARD": "Account/Card",
    "AMOUNT": "Amount",
    "LIFECYCLE": "Lifecycle",
    "MESSAGE": "Message",
    "DEPENDENCY": "Dependency",
    "NEEDS_REVIEW": "Needs review",
}
AI_CATEGORY_MAP = {
    "field": "FIELD",
    "security": "SECURITY",
    "account/card": "ACCOUNT_CARD",
    "amount": "AMOUNT",
    "lifecycle": "LIFECYCLE",
    "message": "MESSAGE",
    "dependency": "DEPENDENCY",
}
TEST_CATEGORY_PATTERNS = {
    "LIFECYCLE": re.compile(
        r"transaction type|transaction code|authorization|purchase/capture|merchandise return|"
        r"reversal|void|cancellation|completion|time.?out|\btor\b|sequence number|lifecycle|follow.?up",
        re.IGNORECASE,
    ),
    "SECURITY": re.compile(
        r"encrypt|decrypt|security|pin block|\bpin\b|cryptogram|\bcvv\b|\bcavv\b|\btavv\b|"
        r"tokeniz|public key|key management",
        re.IGNORECASE,
    ),
    "ACCOUNT_CARD": re.compile(
        r"account number|cardholder|card type|card label|card brand|payment card|\bpan\b|"
        r"track data|loyalty account",
        re.IGNORECASE,
    ),
    "AMOUNT": re.compile(
        r"amount|currency|tax|total|balance|price|quantity|cash back|discount|fee",
        re.IGNORECASE,
    ),
    "DEPENDENCY": re.compile(
        r"cross.?field|cross.?segment|companion segment|correlat|depends on|conditional|"
        r"only when|only if|requires? .{0,45} when|requires? (?:the )?segment|must match|must agree",
        re.IGNORECASE,
    ),
    "MESSAGE": re.compile(
        r"message|segment|field order|data layout|wire format|encoding|byte order|message format",
        re.IGNORECASE,
    ),
    "FIELD": re.compile(
        r"field|element|length|fixed value|valid value|must be present|is required|alphanumeric|numeric digits",
        re.IGNORECASE,
    ),
}
CATEGORY_PRIORITY = tuple(TEST_CATEGORY_PATTERNS)
CSV_FIELDS = [
    "recordType",
    "brId",
    "titleOrStatement",
    "producerCategory",
    "sharedCategoryId",
    "sharedCategoryLabel",
    "additionalCategoryIds",
    "categoryAssignmentMethod",
    "categoryReviewStatus",
    "candidateBrIds",
    "sourceAnchorsOrEntities",
]


def read_json(path: Path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def test_br_anchors(row: dict) -> list[dict]:
    anchors = row.get("sourceAnchors") or row.get("sourceAnchor") or []
    return [anchors] if isinstance(anchors, dict) else [a for a in anchors if isinstance(a, dict)]


def classify_test_br(row: dict) -> dict:
    anchors = test_br_anchors(row)
    evidence = " ".join(
        [str(row.get("title") or "")]
        + [
            str(value or "")
            for anchor in anchors
            for value in (anchor.get("rule"), anchor.get("section"), anchor.get("segment"))
        ]
    )
    matched = [category for category, pattern in TEST_CATEGORY_PATTERNS.items() if pattern.search(evidence)]
    if not matched:
        return {
            "primaryCategoryId": "NEEDS_REVIEW",
            "additionalCategoryIds": [],
            "categoryAssignmentMethod": "NO_SUPPORTED_TITLE_OR_SOURCE_ANCHOR_CUE",
            "categoryReviewStatus": "NEEDS_REVIEW",
        }
    return {
        "primaryCategoryId": matched[0],
        "additionalCategoryIds": matched[1:],
        "categoryAssignmentMethod": "TITLE_AND_SOURCE_ANCHOR_KEYWORD_PROPOSAL",
        "categoryReviewStatus": "PROPOSED_REVIEW_REQUIRED" if len(matched) == 1 else "MULTI_CATEGORY_REVIEW_REQUIRED",
    }


def ai_category(row: dict) -> dict:
    producer_category = str(row.get("category") or "").strip()
    shared_category = AI_CATEGORY_MAP.get(producer_category.casefold(), "NEEDS_REVIEW")
    return {
        "producerCategory": producer_category,
        "primaryCategoryId": shared_category,
        "additionalCategoryIds": [],
        "categoryAssignmentMethod": "PRODUCER_DECLARED_CATEGORY",
        "categoryReviewStatus": "PRODUCER_LABEL_RETAINED" if shared_category != "NEEDS_REVIEW" else "NEEDS_REVIEW_UNKNOWN_PRODUCER_CATEGORY",
    }


def self_test() -> None:
    assert ai_category({"category": "Account/Card"})["primaryCategoryId"] == "ACCOUNT_CARD"
    assert classify_test_br({"title": "Lifecycle follow-ups reuse original Sequence Number"})["primaryCategoryId"] == "LIFECYCLE"
    assert classify_test_br({"title": "EMV data requires Segment 130"})["primaryCategoryId"] == "DEPENDENCY"
    assert classify_test_br({"title": "Unclear new rule"})["primaryCategoryId"] == "NEEDS_REVIEW"
    print("PASS: shared categories preserve producer labels and keep unsupported Test BR assignments reviewable")


def build(ai_catalog_path: Path, test_package_path: Path, crosswalk_dir: Path, output_dir: Path) -> dict:
    ai_catalog_path = ai_catalog_path.resolve()
    test_package_path = test_package_path.resolve()
    crosswalk_dir = crosswalk_dir.resolve()
    output_dir = output_dir.resolve()
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")

    ai_catalog = read_json(ai_catalog_path)
    package = read_json(test_package_path)
    test_manifest = package.get("manifest") or {}
    if test_manifest.get("authority") != "INDEPENDENT_TEST_SOLUTION" or test_manifest.get("aiArtifactsIncluded") is not False:
        raise ValueError("Test BR package must be independent of AI artifacts")
    test_rules = [
        row for row in package.get("businessRequirements") or []
        if str(row.get("id") or "").startswith("BR-RULE-")
    ]
    test_by_id = {str(row["id"])[len("BR-RULE-"):]: row for row in test_rules}
    expected_test_count = (package.get("summary") or {}).get("independentRuleDenominator")
    if len(test_by_id) != len(test_rules) or expected_test_count != len(test_rules):
        raise ValueError("Independent Test BR rows do not reconcile to the package denominator")

    pair_path = crosswalk_dir / "ai-test-br-candidate-pairs.csv"
    pairs = list(csv.DictReader(pair_path.open(encoding="utf-8-sig", newline="")))
    ai_candidates = defaultdict(set)
    test_candidates = defaultdict(set)
    for pair in pairs:
        ai_id, test_id = pair["aiBrId"], pair["testBrId"]
        if ai_id and test_id:
            ai_candidates[ai_id].add(test_id)
            test_candidates[test_id].add(ai_id)

    ai_rows = []
    ai_category_counts = Counter()
    for row in ai_catalog.get("requirements") or []:
        br_id = str(row.get("id") or "")
        assignment = ai_category(row)
        category_id = assignment["primaryCategoryId"]
        ai_category_counts[category_id] += 1
        ai_rows.append({
            "recordType": "AI_BR",
            "brId": br_id,
            "titleOrStatement": row.get("statement") or "",
            **assignment,
            "candidateBrIds": sorted(ai_candidates.get(br_id, set())),
            "sourceAnchorsOrEntities": {
                "sourceRuleId": row.get("source_rule_id"),
                "sourcePage": row.get("source_page"),
                "relatedEntityIds": row.get("related_entity_ids") or [],
            },
        })

    test_rows = []
    test_category_counts = Counter()
    for br_id, row in sorted(test_by_id.items()):
        assignment = classify_test_br(row)
        category_id = assignment["primaryCategoryId"]
        test_category_counts[category_id] += 1
        test_rows.append({
            "recordType": "TEST_BR",
            "brId": br_id,
            "titleOrStatement": row.get("title") or "",
            "producerCategory": "",
            **assignment,
            "candidateBrIds": sorted(test_candidates.get(br_id, set())),
            "sourceAnchorsOrEntities": row.get("sourceAnchors") or row.get("sourceAnchor") or [],
        })

    category_summary = {}
    same_category_pair_count = 0
    cross_category_pair_count = 0
    pairs_by_category = Counter()
    ai_ids_by_category = defaultdict(set)
    test_ids_by_category = defaultdict(set)
    for row in ai_rows:
        ai_ids_by_category[row["primaryCategoryId"]].add(row["brId"])
    test_category_by_id = {row["brId"]: row["primaryCategoryId"] for row in test_rows}
    for pair in pairs:
        ai_row = next((row for row in ai_rows if row["brId"] == pair["aiBrId"]), None)
        test_category = test_category_by_id.get(pair["testBrId"])
        if not ai_row or not test_category:
            continue
        ai_category_id = ai_row["primaryCategoryId"]
        if ai_category_id == test_category:
            same_category_pair_count += 1
            pairs_by_category[ai_category_id] += 1
            test_ids_by_category[ai_category_id].add(pair["testBrId"])
        else:
            cross_category_pair_count += 1

    for category_id, label in CATEGORIES.items():
        category_summary[category_id] = {
            "label": label,
            "aiBrCount": ai_category_counts[category_id],
            "testBrCount": test_category_counts[category_id],
            "testBrsWithSameCategoryCandidate": len(test_ids_by_category[category_id]),
            "sameCategoryCandidatePairRows": pairs_by_category[category_id],
        }

    summary = {
        "artifact": "ATL105-AI-TEST-BR-SHARED-CATEGORY-GROUPING",
        "classificationStatus": "PROPOSED_CATEGORIES_REVIEW_REQUIRED",
        "policy": "AI BRs retain producer categories mapped to shared IDs. Test BR categories are proposed from titles and source anchors; multi-cue or unsupported assignments are review-required. Category alignment is not semantic equivalence.",
        "aiRequirementCount": len(ai_rows),
        "independentTestBrCount": len(test_rows),
        "sourceCandidatePairRows": len(pairs),
        "sameCategoryCandidatePairRows": same_category_pair_count,
        "crossCategoryCandidatePairRows": cross_category_pair_count,
        "categoryDefinitions": CATEGORIES,
        "categories": category_summary,
        "testBrCategoryReviewCounts": dict(sorted(Counter(row["categoryReviewStatus"] for row in test_rows).items())),
        "inputHashes": {
            "aiRequirementCatalogSha256": sha256(ai_catalog_path),
            "independentTestPackageSha256": sha256(test_package_path),
            "candidateCrosswalkSha256": sha256(pair_path),
        },
        "files": ["categorized-business-requirements.csv"],
    }

    output_dir.mkdir(parents=True)
    with (output_dir / "categorized-business-requirements.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=CSV_FIELDS)
        writer.writeheader()
        for row in ai_rows + test_rows:
            writer.writerow({
                "recordType": row["recordType"],
                "brId": row["brId"],
                "titleOrStatement": row["titleOrStatement"],
                "producerCategory": row["producerCategory"],
                "sharedCategoryId": row["primaryCategoryId"],
                "sharedCategoryLabel": CATEGORIES[row["primaryCategoryId"]],
                "additionalCategoryIds": ";".join(row["additionalCategoryIds"]),
                "categoryAssignmentMethod": row["categoryAssignmentMethod"],
                "categoryReviewStatus": row["categoryReviewStatus"],
                "candidateBrIds": ";".join(row["candidateBrIds"]),
                "sourceAnchorsOrEntities": json.dumps(row["sourceAnchorsOrEntities"], ensure_ascii=True, sort_keys=True),
            })
    (output_dir / "category-coverage-summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    write_summary_markdown(output_dir / "category-coverage-summary.md", summary)
    print(json.dumps({"outputDirectory": str(output_dir), **summary}, indent=2, ensure_ascii=True))
    return summary


def write_summary_markdown(path: Path, summary: dict) -> None:
    lines = [
        "# AI and Test BR Shared Categories",
        "",
        f"- AI BRs categorized: {summary['aiRequirementCount']:,}",
        f"- Independent Test BRs categorized: {summary['independentTestBrCount']:,}",
        f"- Candidate pairs: {summary['sourceCandidatePairRows']:,}",
        f"- Same-category candidate pairs: {summary['sameCategoryCandidatePairRows']:,}",
        f"- Cross-category candidate pairs: {summary['crossCategoryCandidatePairRows']:,}",
        "",
        "Test BR assignments are proposed from titles/source anchors and remain review-required; category alignment is not semantic approval.",
        "",
        "| Shared category | AI BRs | Test BRs | Test BRs with same-category candidates | Same-category candidate pairs |",
        "|---|---:|---:|---:|---:|",
    ]
    for item in summary["categories"].values():
        lines.append(
            f"| {item['label']} | {item['aiBrCount']:,} | {item['testBrCount']:,} | "
            f"{item['testBrsWithSameCategoryCandidate']:,} | {item['sameCategoryCandidatePairRows']:,} |"
        )
    lines.extend([
        "",
        "## Test BR Assignment Review Status",
        "",
    ])
    for status, count in summary["testBrCategoryReviewCounts"].items():
        lines.append(f"- `{status}`: {count:,}")
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--ai-requirements", type=Path)
    parser.add_argument("--test-br-package", type=Path)
    parser.add_argument("--candidate-crosswalk-directory", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not all((args.ai_requirements, args.test_br_package, args.candidate_crosswalk_directory, args.output_directory)):
        parser.error("--ai-requirements, --test-br-package, --candidate-crosswalk-directory, and --output-directory are required")
    build(args.ai_requirements, args.test_br_package, args.candidate_crosswalk_directory, args.output_directory)


if __name__ == "__main__":
    main()