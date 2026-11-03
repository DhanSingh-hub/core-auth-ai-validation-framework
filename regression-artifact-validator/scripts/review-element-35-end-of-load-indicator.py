#!/usr/bin/env python3
"""Review AI Element 35 claims and scenario-only chains against ATL105."""

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
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "2026-10-19-full-corpus-review-batch-015"
DEFAULT_AI_BR_ID = "REQ-SRC-ATL105-PDF-001:042"
SOURCE_EVIDENCE = (
    (20292, "Number: 35 Name: End-of-Load Indicator"),
    (20293, "Character Type: A Maximum Length: 1 byte"),
    (20295, "Representation: Fixed length of one alpha character"),
    (20296, "Purpose: Indicates the end of a Table Load."),
    (20297, "Processing Rules: It is used in the Table Load Response."),
    (20298, "Valid Codes/Values: *"),
    (9190, "If Segment No. DL1 (Merchant Data Segment) includes a Card Type value of"),
)
TEST_CANDIDATE_NOTES = {
    "SEGDL6-R-006": "Table Load Response DL6 block-order lead; draft/review-required, not an approved Element 35 oracle.",
    "SEGDL1-R-012": "Table Load Response block-order lead; draft/review-required, not an approved Element 35 oracle.",
}


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def read_records(path: Path, key: str, wanted: set[str]) -> dict[str, Any]:
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
    for line_number, quote in SOURCE_EVIDENCE:
        if line_number > len(lines) or re.sub(r"\s+", " ", quote).strip() not in normalized_source:
            raise ValueError(f"Element 35 source quote not found at line {line_number}")
        prefix = " ".join(re.sub(r"\s+", " ", quote).split()[:4])
        if prefix not in re.sub(r"\s+", " ", lines[line_number - 1]):
            raise ValueError(f"Element 35 source line anchor mismatch at line {line_number}")
        evidence.append({"startLine": line_number, "quote": quote})

    start = normalized_source.find("Number: 35 Name: End-of-Load Indicator")
    end = normalized_source.find("Number: 36 Name: Extract Date", start + 1)
    definition = normalized_source[start:end] if start >= 0 and end > start else ""
    table_load_excerpt = re.sub(r"\s+", " ", "\n".join(lines[9177:9195])).strip()
    required = ("Character Type: A Maximum Length: 1 byte",
                "Representation: Fixed length of one alpha character",
                "Processing Rules: It is used in the Table Load Response.",
                "Valid Codes/Values: *")
    if not definition or any(term not in definition for term in required):
        raise ValueError("Element 35 source definition, context or valid value changed")
    if "7 35 End-of-Load Indicator 1 R Indicates the end of a table load. Fixed value: * Source: Host" not in table_load_excerpt:
        raise ValueError("Table Load Response End-of-Load marker table row changed")
    if "Data Block No. 4 If Segment No. DL1 (Merchant Data Segment) includes a Card Type value of '173'" not in table_load_excerpt.replace("‘", "'").replace("’", "'"):
        raise ValueError("Conditional DL6 Table Load Response context changed")
    evidence.append({"section": "Table Load Response", "startLine": 9184,
                     "quote": "7 35 End-of-Load"})
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


def chain_summary(record: dict[str, Any] | None) -> dict[str, Any] | None:
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


def summarize_candidates(package: dict[str, Any], candidates: list[str], chains: dict[str, Any]) -> list[dict[str, Any]]:
    requirements = {item.get("id"): item for item in package.get("businessRequirements", [])}
    result = []
    for candidate in candidates:
        package_id = candidate if candidate.startswith("BR-") else "BR-RULE-" + candidate
        requirement = requirements.get(package_id)
        result.append({
            "candidateId": candidate,
            "packageId": package_id if requirement else None,
            "title": requirement.get("title") if requirement else None,
            "status": requirement.get("status") if requirement else None,
            "sourceAnchors": requirement.get("sourceAnchors", []) if requirement else [],
            "disposition": TEST_CANDIDATE_NOTES.get(candidate, "Ranked lead only; scope requires independent review."),
            "chain": chain_summary(chains.get(candidate)),
        })
    return result


def build_report(atl105_root: Path, review_v3: Path, test_package_path: Path, ai_br_id: str) -> dict[str, Any]:
    ai = roster(review_v3 / "all-ai-br-review-register.csv", ai_br_id)
    if ai["caseIds"] or ai["payloadPaths"]:
        raise ValueError("This source-only reviewer expects zero rostered cases and payload paths")
    evidence, source_hash = verify_source(atl105_root)
    ai_chains = read_records(review_v3 / "ai-br-chain-evidence.jsonl.gz", "aiBrId", {ai_br_id})
    if ai_br_id not in ai_chains:
        raise ValueError(f"AI chain evidence missing {ai_br_id}")
    candidates = list(dict.fromkeys(ai["rankedTestCandidates"]))
    test_chains = read_records(review_v3 / "test-chain-evidence.jsonl.gz", "testBrId", set(candidates))
    package = json.loads(test_package_path.read_text(encoding="utf-8-sig"))
    ai_chain = ai_chains[ai_br_id]
    return {
        "reviewId": "ATL105-FULL-CORPUS-BATCH-015-END-OF-LOAD-INDICATOR",
        "reviewDate": date.today().isoformat(),
        "authority": "PRELIMINARY_ANALYST_REVIEW_NOT_AUTHORIZED_APPROVAL",
        "status": "REVIEW_REQUIRED",
        "aiBusinessRequirement": {
            "id": ai_br_id, "statement": ai["statement"], "segments": ai["segments"], "elements": ai["elements"],
            "scenarioIds": ai["scenarioIds"], "scenarioCount": len(ai["scenarioIds"]),
            "caseIds": ai["caseIds"], "caseCount": len(ai["caseIds"]), "payloadPaths": ai["payloadPaths"],
            "chainStatus": ai_chain.get("chainStatus"),
            "physicalPayloadPathCount": len(ai_chain.get("physicalPayloadPaths", [])),
        },
        "sourceRule": {
            "id": "ENT-ELEM-35", "element": "35", "name": "End-of-Load Indicator",
            "characterType": "A", "maximumLengthBytes": 1,
            "representation": "Fixed length of one alpha character",
            "validCode": "*", "validCodeAsciiAlphabetic": False,
            "purpose": "Indicates the end of a Table Load.",
            "processingContext": "Used in the Table Load Response.",
            "tableLoadResponseMarker": {"fixedValue": "*", "source": "Host", "tableRow": 7},
            "dl6Condition": "DL6 follows in the Table Load Response when DL1 carries Card Type 173; DL6 is Data Block No. 4.",
            "distinctFromEndOfData": {"element": "34", "name": "End-of-Data Indicator", "marker": "~"},
            "sourceTension": "The source describes Character Type A/fixed alpha but lists '*' (ASCII punctuation) as the valid code; do not erase this explicit source conflict.",
            "sourceDisposition": "SOURCE_WORDING_REQUIRES_AUTHORIZED_INTERPRETATION",
            "sourceEvidence": evidence, "sourceTextSha256": source_hash,
        },
        "testCrosswalk": {
            "exactAnchorCandidates": ai["exactTestCandidates"],
            "rankedCandidatesAreLeadsOnly": True,
            "rankedCandidates": summarize_candidates(package, candidates, test_chains),
            "closestLeads": ["SEGDL6-R-006", "SEGDL1-R-012"],
            "disposition": "The closest leads cover Table Load Response block ordering and DL6 context, not an approved standalone Element 35 assertion; both chains remain review-required.",
        },
        "caseReview": {
            "scenarioCount": len(ai["scenarioIds"]), "caseCount": 0, "payloadPathCount": 0,
            "evidence": "SCENARIO_ONLY_NO_LINKED_CASES_OR_PAYLOAD_PATHS",
            "outcomeOracle": "NOT_PROVIDED", "executionPerformed": False,
        },
        "analystDisposition": {
            "formatClaim": "One-byte/fixed-alpha text aligns with the generic representation, but the listed '*' value is not alphabetic.",
            "segmentScope": "Table Load Response context; DL6 is conditional on DL1 Card Type 173.",
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
    eol, eod = "*", "~"
    if eol.isalpha() or len(eol.encode("ascii")) != 1:
        raise AssertionError("Element 35 marker classification changed")
    if eol == eod or eod.isalpha():
        raise AssertionError("Element 35 and Element 34 markers were conflated")
    print("PASS: '*' is one ASCII byte but not alphabetic; remains distinct from Element 34 '~'.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--test-package", type=Path, default=TEST_PACKAGE)
    parser.add_argument("--ai-br-id", default=DEFAULT_AI_BR_ID)
    parser.add_argument("--output", type=Path, default=OUTPUT / "end-of-load-indicator-review.json")
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
    manifest = {"manifestId": "ATL105-FULL-CORPUS-BATCH-015-END-OF-LOAD-INDICATOR",
                "reviewDate": date.today().isoformat(), "status": "REVIEW_REQUIRED",
                "artifacts": artifacts, "inputs": inputs, "guardrails": report["guardrails"]}
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path),
                      "scenarioCount": report["aiBusinessRequirement"]["scenarioCount"],
                      "caseCount": report["caseReview"]["caseCount"],
                      "exactTestCandidates": report["testCrosswalk"]["exactAnchorCandidates"],
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)