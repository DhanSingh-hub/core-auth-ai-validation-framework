#!/usr/bin/env python3
"""Export all unique AI BRs with source-anchor disposition against Test BRs."""

import argparse
import csv
import hashlib
import json
from pathlib import Path


PRIMARY_SCOPE = "PRIMARY_AI_CATALOG"
CSV_FIELDS = [
    "aiRequirementId",
    "aiRecordCohort",
    "sourceRunIds",
    "aiSourceRuleId",
    "sourceAnchorCandidateStatus",
    "aiSourceAnchorPairs",
    "testBrDisposition",
    "officialTestBrCandidateCount",
    "officialTestBrCandidateIds",
    "aiChainStatus",
    "testScenarioIds",
    "testCaseIds",
    "testDataPathsOrIds",
    "aiStatement",
    "semanticMatchStatus",
]


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_jsonl(path: Path) -> list[dict]:
    return [json.loads(line) for line in path.read_text(encoding="utf-8").splitlines() if line]


def joined(values) -> str:
    return ";".join(str(value) for value in values or [] if value is not None)


def to_csv_row(record: dict) -> dict:
    candidates = record.get("officialIndependentTestBrCandidates") or []
    candidate_ids = sorted({str(item["ruleId"]) for item in candidates if item.get("ruleId")})
    chain = record.get("aiChain") or {}
    data_ids = chain.get("testDataPaths") or chain.get("testDataIds") or []
    return {
        "aiRequirementId": record.get("aiRequirementId"),
        "aiRecordCohort": record.get("aiRecordCohort"),
        "sourceRunIds": joined(record.get("sourceRunIds")),
        "aiSourceRuleId": record.get("aiSourceRuleId"),
        "sourceAnchorCandidateStatus": record.get("aiSourceAnchorCandidateStatus"),
        "aiSourceAnchorPairs": joined("/".join(pair) for pair in record.get("aiSourceAnchorPairs", [])),
        "testBrDisposition": (
            "TEST_BR_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED" if candidate_ids
            else "NO_EXACT_TEST_BR_SOURCE_ANCHOR_CANDIDATE"
        ),
        "officialTestBrCandidateCount": len(candidate_ids),
        "officialTestBrCandidateIds": joined(candidate_ids),
        "aiChainStatus": chain.get("chainStatus"),
        "testScenarioIds": joined(chain.get("testScenarioIds")),
        "testCaseIds": joined(chain.get("testCaseIds")),
        "testDataPathsOrIds": joined(data_ids),
        "aiStatement": record.get("aiStatement"),
        "semanticMatchStatus": record.get("semanticMatchStatus", "NOT_ASSESSED"),
    }


def write_csv(path: Path, rows: list[dict]) -> None:
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=CSV_FIELDS, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def self_test() -> None:
    candidate = to_csv_row({
        "aiRequirementId": "AI-1",
        "officialIndependentTestBrCandidates": [{"ruleId": "SEG100-R-001"}],
        "aiChain": {"chainStatus": "COMPLETE_DIRECT_BR_TS_TC_TD_CHAIN"},
        "aiStatement": "statement",
    })
    unmatched = to_csv_row({"aiRequirementId": "AI-2", "officialIndependentTestBrCandidates": []})
    assert candidate["testBrDisposition"] == "TEST_BR_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED"
    assert candidate["officialTestBrCandidateIds"] == "SEG100-R-001"
    assert unmatched["testBrDisposition"] == "NO_EXACT_TEST_BR_SOURCE_ANCHOR_CANDIDATE"
    assert candidate["semanticMatchStatus"] == "NOT_ASSESSED"
    print("PASS: candidate and no-candidate rows remain distinct and unconfirmed")


def build(chain_audit: Path, output_dir: Path) -> dict:
    if not chain_audit.is_file():
        raise FileNotFoundError(chain_audit)
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")
    records = read_jsonl(chain_audit)
    primary = [record for record in records if record.get("recordScope") == PRIMARY_SCOPE]
    if len({record.get("aiRequirementId") for record in primary}) != len(primary):
        raise ValueError("Primary AI BR IDs are missing or duplicated")
    if not all(record.get("aiStatement") for record in primary):
        raise ValueError("At least one primary AI BR has no source statement")

    converted = [to_csv_row(record) for record in primary]
    unmatched = [row for row in converted if row["testBrDisposition"] == "NO_EXACT_TEST_BR_SOURCE_ANCHOR_CANDIDATE"]
    candidate = [row for row in converted if row["testBrDisposition"] == "TEST_BR_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED"]
    if len(unmatched) + len(candidate) != len(primary):
        raise ValueError("AI BR disposition groups do not partition the primary catalog")

    all_candidate_ids = {
        value for row in candidate for value in row["officialTestBrCandidateIds"].split(";") if value
    }
    summary = {
        "artifact": "ATL105-AI-BR-TEST-MODEL-INVENTORY",
        "status": "SOURCE_ANCHOR_DISPOSITIONS_EXPORTED_SEMANTIC_MATCHES_NOT_ASSESSED",
        "primaryUniqueAiBrCount": len(primary),
        "aiBrsWithNoExactTestBrSourceAnchorCandidate": len(unmatched),
        "aiBrsWithTestBrAnchorCandidatesReviewRequired": len(candidate),
        "distinctOfficialTestBrCandidateIds": len(all_candidate_ids),
        "supplementalRun1SuiteBrRowsExcluded": sum(
            record.get("recordScope") != PRIMARY_SCOPE for record in records
        ),
        "semanticConfirmedMatchCount": 0,
        "semanticMatchStatus": "NOT_ASSESSED",
        "matchingPolicy": "Exact normalized source segment plus numeric element anchors from the independent Test Solution baseline. Candidate links are not semantic matches.",
        "limitations": [
            "NO_EXACT_TEST_BR_SOURCE_ANCHOR_CANDIDATE means no candidate was found by this anchor method; it does not prove semantic absence from the Test Solution.",
            "TEST_BR_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED means one or more anchor candidates exist; equivalence is not confirmed.",
            "Run1 legacy BRs have no direct links in the supplied prioritized-scenario catalog; their source-anchor candidates do not establish scenario/test coverage.",
            "No SME approval is needed to produce this source-anchor inventory, but semantic decisions and final test approval remain separate.",
        ],
        "sourceHashes": {"chainAuditSha256": sha256(chain_audit)},
        "outputs": {
            "allAiBusinessRequirements": "ai-br-test-model-disposition-all.csv",
            "noExactTestBrSourceAnchorCandidate": "ai-br-no-exact-test-br-anchor-candidate.csv",
            "testBrCandidateReviewRequired": "ai-br-test-br-candidate-review-required.csv",
        },
    }
    output_dir.mkdir(parents=True, exist_ok=False)
    write_csv(output_dir / summary["outputs"]["allAiBusinessRequirements"], converted)
    write_csv(output_dir / summary["outputs"]["noExactTestBrSourceAnchorCandidate"], unmatched)
    write_csv(output_dir / summary["outputs"]["testBrCandidateReviewRequired"], candidate)
    (output_dir / "ai-br-test-model-inventory-summary.json").write_text(
        json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return summary


def main() -> None:
    parser = argparse.ArgumentParser(description="Export AI BRs by Test BR source-anchor candidate disposition.")
    parser.add_argument("--chain-audit", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not args.chain_audit or not args.output_dir:
        parser.error("--chain-audit and --output-dir are required unless --self-test is used")
    summary = build(args.chain_audit, args.output_dir)
    print(json.dumps({
        "outputDirectory": str(args.output_dir),
        "primaryUniqueAiBrCount": summary["primaryUniqueAiBrCount"],
        "noExactAnchorCandidate": summary["aiBrsWithNoExactTestBrSourceAnchorCandidate"],
        "candidateReviewRequired": summary["aiBrsWithTestBrAnchorCandidatesReviewRequired"],
        "semanticMatchStatus": summary["semanticMatchStatus"],
    }, indent=2))


if __name__ == "__main__":
    main()