#!/usr/bin/env python3
"""Review AI Element 34 claims and scenario-only chains against ATL105."""

from __future__ import annotations

import argparse
import csv
import gzip
import hashlib
import json
import re
import sys
from datetime import date
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
REVIEW_V3 = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-all-ai-br-chain-test-br-review-v3"
TEST_PACKAGE = ATL105 / "test-output" / "test-solution-independent-review" / "complete-all-test-solution-br-ts-tc-td-package.json"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "2026-10-19-full-corpus-review-batch-014"
DEFAULT_AI_BR_ID = "REQ-SRC-ATL105-PDF-001:041"
SOURCE_EVIDENCE = (
    ("12.6", 20267, "Number: 34 Name: End-of-Data Indicator"),
    ("12.6", 20268, "Character Type: A Maximum Length: 1 byte"),
    ("12.6", 20270, "Representation: Fixed length of one alpha character"),
    ("12.6", 20272, "Processing Rules: It is used in the following data segments:"),
    ("12.6", 20273, "Merchant Data Segment (No. DL1)"),
    ("12.6", 20274, "Dial String Data Segment (No. DL2)"),
    ("12.6", 20275, "Date and Time Data Segment (No. DL3)"),
    ("12.6", 20276, "Software Dial Load Data Segment (No. DL4)"),
    ("12.6", 20277, "Software IP Load Data Segment (No. DL5)"),
    ("12.6", 20278, "Valid Codes/Values: ~"),
    ("12.47", 17532, "12.47 Store and Forward Data Segment"),
    ("12.47", 17570, "4 34 End-of-Data"),
    ("12.47", 17571, "Indicator"),
    ("12.47", 17572, "1 R Indicates the end of the data segment."),
    ("12.47", 17573, "Fixed value: ~"),
    ("12.47", 17574, "Source: Host"),
)
DL_RULE_SEGMENTS = {
    "SEGDL3-R-001": "DL3",
    "SEGDL4-R-002": "DL4",
    "SEGDL5-R-002": "DL5",
    "SEGDL6-R-002": "DL6",
}


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def read_chain_records(path: Path, key: str, wanted: set[str]) -> dict[str, Any]:
    found = {}
    with gzip.open(path, "rt", encoding="utf-8") as stream:
        for line in stream:
            record = json.loads(line)
            identity = record.get(key)
            if identity in wanted:
                found[identity] = record
    return found


def verify_source(atl105_root: Path) -> tuple[list[dict[str, Any]], str]:
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    lines = source_path.read_text(encoding="utf-8-sig").splitlines()
    normalized_source = re.sub(r"\s+", " ", "\n".join(lines)).strip()
    evidence = []
    for section, line_number, quote in SOURCE_EVIDENCE:
        if line_number > len(lines) or re.sub(r"\s+", " ", quote).strip() not in normalized_source:
            raise ValueError(f"Element 34 source quote not found at line {line_number}")
        prefix = " ".join(re.sub(r"\s+", " ", quote).split()[:4])
        if prefix not in re.sub(r"\s+", " ", lines[line_number - 1]):
            raise ValueError(f"Element 34 source line anchor mismatch at line {line_number}")
        evidence.append({"section": section, "startLine": line_number, "quote": quote})

    element_start = normalized_source.find("Number: 34 Name: End-of-Data Indicator")
    element_end = normalized_source.find("Number: 35 Name: End-of-Load Indicator", element_start + 1)
    element_definition = normalized_source[element_start:element_end] if element_start >= 0 and element_end > element_start else ""
    dl6_definition = re.sub(r"\s+", " ", "\n".join(lines[17531:17579])).strip()
    if not element_definition or not dl6_definition:
        raise ValueError("Element 34 or DL6 source section is missing")
    required_element_terms = (
        "Character Type: A Maximum Length: 1 byte",
        "Representation: Fixed length of one alpha character",
        "Valid Codes/Values: ~",
        "Software IP Load Data Segment (No. DL5)",
    )
    if any(term not in element_definition for term in required_element_terms):
        raise ValueError("Element 34 definition, marker or segment list changed")
    if "4 34 End-of-Data Indicator 1 R Indicates the end of the data segment. Fixed value: ~ Source: Host" not in dl6_definition:
        raise ValueError("DL6 Element 34 fixed-marker table entry changed")
    return evidence, sha256(source_path)


def roster(register_path: Path, ai_br_id: str) -> dict[str, Any]:
    csv.field_size_limit(100000000)
    with register_path.open(encoding="utf-8-sig", newline="") as stream:
        matches = [row for row in csv.DictReader(stream) if row.get("aiBrId") == ai_br_id]
    if len(matches) != 1:
        raise ValueError(f"Expected one roster row for {ai_br_id}, found {len(matches)}")
    row = matches[0]
    parse = lambda key: json.loads(row.get(key) or "[]")
    return {
        "aiBrId": ai_br_id,
        "statement": row.get("aiStatement"),
        "segments": parse("aiSegments"),
        "elements": parse("aiElements"),
        "scenarioIds": parse("aiScenarioIds"),
        "caseIds": parse("aiCaseIds"),
        "physicalCompleteCaseIds": parse("aiPhysicalCompleteCaseIds"),
        "payloadPaths": parse("aiPayloadPaths"),
        "chainStatus": row.get("chainStatus"),
        "exactTestCandidates": parse("v6ExactAnchorTestBrCandidates"),
        "rankedTestCandidates": parse("rankedTestBrCandidates"),
        "reviewDisposition": row.get("reviewDisposition"),
        "semanticMatchStatus": row.get("semanticMatchStatus"),
    }


def test_chain_summary(record: dict[str, Any] | None) -> dict[str, Any] | None:
    if record is None:
        return None
    return {
        "businessRequirementStatus": record.get("br", {}).get("status"),
        "scenarioIds": [item.get("id") for item in record.get("scenarios", [])],
        "scenarioStatuses": [item.get("status") for item in record.get("scenarios", [])],
        "caseIds": [item.get("id") for item in record.get("cases", [])],
        "caseStatuses": [item.get("status") for item in record.get("cases", [])],
        "testDataIds": [item.get("id") for item in record.get("data", [])],
        "testDataReadiness": [item.get("readiness") for item in record.get("data", [])],
        "testDataExpectedValidation": [item.get("expectedValidation") for item in record.get("data", [])],
    }


def test_candidates(package: dict[str, Any], ids: list[str], chains: dict[str, Any]) -> list[dict[str, Any]]:
    requirements = {item.get("id"): item for item in package.get("businessRequirements", [])}
    output = []
    for candidate in ids:
        package_id = candidate if candidate.startswith("BR-") else "BR-RULE-" + candidate
        record = requirements.get(package_id)
        output.append({
            "candidateId": candidate,
            "packageId": package_id if record else None,
            "title": record.get("title") if record else None,
            "status": record.get("status") if record else None,
            "sourceAnchors": record.get("sourceAnchors", []) if record else [],
            "claimedSegment": DL_RULE_SEGMENTS.get(candidate),
            "chain": test_chain_summary(chains.get(candidate)),
        })
    return output


def build_report(atl105_root: Path, review_v3: Path, test_package_path: Path, ai_br_id: str) -> dict[str, Any]:
    register_path = review_v3 / "all-ai-br-review-register.csv"
    ai_chain_path = review_v3 / "ai-br-chain-evidence.jsonl.gz"
    test_chain_path = review_v3 / "test-chain-evidence.jsonl.gz"
    ai = roster(register_path, ai_br_id)
    if ai["caseIds"] or ai["payloadPaths"]:
        raise ValueError("This source-only reviewer expects the rostered BR to have no linked cases or payload paths")
    source_evidence, source_hash = verify_source(atl105_root)
    ai_chains = read_chain_records(ai_chain_path, "aiBrId", {ai_br_id})
    if ai_br_id not in ai_chains:
        raise ValueError(f"AI chain evidence missing {ai_br_id}")
    candidates = list(dict.fromkeys(ai["rankedTestCandidates"]))
    test_chains = read_chain_records(test_chain_path, "testBrId", set(candidates))
    test_package = json.loads(test_package_path.read_text(encoding="utf-8-sig"))
    ranked_candidates = test_candidates(test_package, candidates, test_chains)
    ai_chain = ai_chains[ai_br_id]
    dl6_candidate = next((item for item in ranked_candidates if item["candidateId"] == "SEGDL6-R-002"), None)
    return {
        "reviewId": "ATL105-FULL-CORPUS-BATCH-014-END-OF-DATA-INDICATOR",
        "reviewDate": date.today().isoformat(),
        "authority": "PRELIMINARY_ANALYST_REVIEW_NOT_AUTHORIZED_APPROVAL",
        "status": "REVIEW_REQUIRED",
        "aiBusinessRequirement": {
            "id": ai_br_id,
            "statement": ai["statement"],
            "segments": ai["segments"],
            "elements": ai["elements"],
            "scenarioIds": ai["scenarioIds"],
            "scenarioCount": len(ai["scenarioIds"]),
            "caseIds": ai["caseIds"],
            "caseCount": len(ai["caseIds"]),
            "payloadPaths": ai["payloadPaths"],
            "chainStatus": ai_chain.get("chainStatus"),
            "physicalPayloadPathCount": len(ai_chain.get("physicalPayloadPaths", [])),
        },
        "sourceRule": {
            "id": "ENT-ELEM-34",
            "element": "34",
            "name": "End-of-Data Indicator",
            "characterType": "A",
            "maximumLengthBytes": 1,
            "representation": "Fixed length of one alpha character",
            "validCode": "~",
            "validCodeAsciiAlphabetic": False,
            "processingRuleSegments": ["DL1", "DL2", "DL3", "DL4", "DL5"],
            "dl6Table": {"section": "12.47", "field": 4, "element": "34", "fixedValue": "~", "source": "Host"},
            "sourceInternalTensions": [
                "The generic Element 34 definition says Character Type A and fixed one alpha character, but its sole valid code '~' is ASCII punctuation, not alphabetic.",
                "The generic processing-rule list names DL1-DL5, while Section 12.47 explicitly includes Element 34 as DL6 field 4 with fixed value '~'.",
            ],
            "sourceDisposition": "SOURCE_INTERNAL_INCONSISTENCY_REQUIRES_AUTHORIZED_INTERPRETATION",
            "sourceEvidence": source_evidence,
            "sourceTextSha256": source_hash,
        },
        "testCrosswalk": {
            "exactAnchorCandidates": ai["exactTestCandidates"],
            "rankedCandidatesAreLeadsOnly": True,
            "rankedCandidates": ranked_candidates,
            "dl3ToDl5Candidates": "Their titles use the '~' terminator and align with the Element 34 processing list; all remain draft/review-required and their chains are placeholders.",
            "dl6Candidate": {
                "id": "SEGDL6-R-002",
                "sourceReconciliation": "The direct Test lead is consistent with the DL6 table layout; the generic Element 34 processing list omits DL6. This source inconsistency remains unresolved.",
                "chain": test_chain_summary(test_chains.get("SEGDL6-R-002")),
            } if dl6_candidate else None,
        },
        "caseReview": {
            "aiCaseCount": 0,
            "scenarioCount": len(ai["scenarioIds"]),
            "evidence": "SCENARIO_ONLY_NO_LINKED_CASES_OR_PAYLOAD_PATHS",
            "outcomeOracle": "NOT_PROVIDED",
            "executionPerformed": False,
        },
        "analystDisposition": {
            "formatClaim": "One-byte/fixed-alpha representation aligns with the generic type text, but the explicit valid code '~' contradicts an alphabetic-only reading.",
            "segmentScope": "DL3-DL5 appear in the Element 34 processing list; DL6 appears in the dedicated DL6 layout but is omitted from that list.",
            "overall": "PRELIMINARY_SOURCE_REVIEW; NO_MATCH_GRANTED",
            "authorizedReviewerDecision": "PENDING",
            "semanticMatchStatus": "NOT_ASSESSED",
            "coverageCredit": 0,
        },
        "guardrails": {
            "officialTestBaselineModified": False,
            "currentTestPackageModified": False,
            "sourceAiArtifactsModified": False,
            "casePayloadsInspected": False,
            "executionPerformed": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
        },
    }


def self_test() -> None:
    marker = "~"
    if marker.isalpha() or len(marker.encode("ascii")) != 1:
        raise AssertionError("The published Element 34 marker check changed")
    if "*" == marker:
        raise AssertionError("End-of-Data and End-of-Load markers were conflated")
    print("PASS: '~' is one ASCII byte but not alphabetic; '*' remains distinct for Element 35.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--test-package", type=Path, default=TEST_PACKAGE)
    parser.add_argument("--ai-br-id", default=DEFAULT_AI_BR_ID)
    parser.add_argument("--output", type=Path, default=OUTPUT / "end-of-data-indicator-review.json")
    parser.add_argument("--manifest", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return 0
    report = build_report(args.atl105_root, args.review_v3, args.test_package, args.ai_br_id)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    manifest_path = args.manifest or args.output.with_name("manifest.json")
    readme = args.output.with_name("README.md")
    artifacts = [{"path": str(path), "sha256": sha256(path), "role": role}
                 for path, role in ((args.output, "category-review-report"),
                                    (Path(__file__).resolve(), "category-review-runner"))]
    if readme.is_file():
        artifacts.append({"path": str(readme), "sha256": sha256(readme), "role": "review-summary"})
    register_path = args.review_v3 / "all-ai-br-review-register.csv"
    inputs = [{"path": str(path), "sha256": sha256(path), "role": role}
              for path, role in ((args.atl105_root / "docs" / "specs" / "extracted_text.txt", "ATL105-source-text"),
                                 (register_path, "AI-BR-roster"),
                                 (args.review_v3 / "ai-br-chain-evidence.jsonl.gz", "AI-chain-evidence"),
                                 (args.review_v3 / "test-chain-evidence.jsonl.gz", "Test-chain-evidence"),
                                 (args.test_package, "Test-package"))]
    manifest = {"manifestId": "ATL105-FULL-CORPUS-BATCH-014-END-OF-DATA-INDICATOR",
                "reviewDate": date.today().isoformat(), "status": "REVIEW_REQUIRED",
                "artifacts": artifacts, "inputs": inputs, "guardrails": report["guardrails"]}
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path),
                      "scenarioCount": report["aiBusinessRequirement"]["scenarioCount"],
                      "caseCount": report["caseReview"]["aiCaseCount"],
                      "exactTestCandidates": report["testCrosswalk"]["exactAnchorCandidates"],
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)