#!/usr/bin/env python3
"""Prepare a reproducible, isolated pilot for source-only targeted coverage generation."""

import argparse
import csv
import hashlib
import json
import random
from collections import Counter, defaultdict
from pathlib import Path


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path: Path) -> dict:
    return json.loads(path.read_text(encoding="utf-8-sig"))


def read_jsonl(path: Path) -> list[dict]:
    return [json.loads(line) for line in path.read_text(encoding="utf-8").splitlines() if line]


def read_csv(path: Path) -> list[dict]:
    with path.open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def source_only_anchors(anchors: list[dict]) -> list[dict]:
    allowed = {"specification", "version", "section", "segment", "element"}
    return [{key: value for key, value in anchor.items() if key in allowed} for anchor in anchors]


def proportional_allocation(strata: dict[str, list[dict]], sample_size: int) -> dict[str, int]:
    if sample_size < len(strata):
        raise ValueError(f"Sample size {sample_size} must cover all {len(strata)} eligible classes")
    population = sum(len(rows) for rows in strata.values())
    if sample_size > population:
        raise ValueError(f"Sample size {sample_size} exceeds eligible population {population}")
    allocation = {name: 1 for name in strata}
    while sum(allocation.values()) < sample_size:
        available = [name for name, rows in strata.items() if allocation[name] < len(rows)]
        if not available:
            break
        chosen = max(available, key=lambda name: (len(strata[name]) / (allocation[name] + 1), name))
        allocation[chosen] += 1
    return allocation


def self_test() -> None:
    strata = {"large": [{}] * 20, "small": [{}] * 3, "tiny": [{}]}
    allocation = proportional_allocation(strata, 8)
    assert sum(allocation.values()) == 8
    assert allocation["tiny"] == 1
    assert allocation["large"] >= allocation["small"]
    assert source_only_anchors([{"section": "12.4", "element": "3", "rule": "test-rule-slug"}]) == [
        {"section": "12.4", "element": "3"}
    ]
    print("PASS: stratified allocation covers every class and respects sample size")


def build(seed_pack: Path, baseline_path: Path, output_dir: Path, sample_size: int, seed: int) -> dict:
    context_path = seed_pack / "targeted-ai-generation-context.jsonl"
    map_path = seed_pack / "target-evaluation-map.csv"
    summary_path = seed_pack / "targeted-cohort-summary.json"
    for path in (context_path, map_path, summary_path, baseline_path):
        if not path.is_file():
            raise FileNotFoundError(path)
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing experiment output: {output_dir}")

    seed_summary = read_json(summary_path)
    if seed_summary.get("targetedUncoveredTestBrCount") != 280:
        raise ValueError("Expected the frozen 280-target seed cohort")
    if seed_summary.get("targetedGenerationExecuted") is not False:
        raise ValueError("Seed pack must be pre-generation")
    contexts = {row["targetKey"]: row for row in read_jsonl(context_path)}
    target_map = {row["targetKey"]: row for row in read_csv(map_path)}
    if len(contexts) != 280 or contexts.keys() != target_map.keys():
        raise ValueError("Seed context and evaluation map do not reconcile to 280 targets")

    baseline = read_json(baseline_path)
    rules = {str(row["ruleId"]): row for row in baseline.get("businessRequirements", [])}
    strata: dict[str, list[dict]] = defaultdict(list)
    excluded = []
    for target_key, row in target_map.items():
        context = contexts[target_key]
        if context.get("sourceEvidenceStatus") != "SOURCE_TEXT_RETRIEVED_GENERATION_REVIEW_REQUIRED":
            excluded.append({"targetKey": target_key, "reason": context.get("sourceEvidenceStatus")})
            continue
        source_rule = rules.get(row["testSolutionRuleId"], {})
        stratum = source_rule.get("class") or "UNSPECIFIED"
        strata[stratum].append({"targetKey": target_key, "targetMap": row})

    allocation = proportional_allocation(dict(strata), sample_size)
    rng = random.Random(seed)
    selected = []
    for stratum in sorted(strata):
        rows = sorted(strata[stratum], key=lambda row: row["targetKey"])
        selected.extend((stratum, row) for row in rng.sample(rows, allocation[stratum]))
    selected.sort(key=lambda pair: pair[1]["targetKey"])

    generation_rows = []
    result_rows = []
    evaluation_rows = []
    for stratum, selected_row in selected:
        target_key = selected_row["targetKey"]
        context = contexts[target_key]
        generation_rows.append({
            "targetKey": target_key,
            "specification": context["specification"],
            "specificationVersion": context["specificationVersion"],
            "sourceAnchors": source_only_anchors(context["sourceAnchors"]),
            "sourceEvidence": context["sourceEvidence"],
            "requestedArtifactIds": context["requestedArtifactIds"],
            "generationPrompt": context["generationPrompt"],
            "chainContract": context["chainContract"],
            "generationMode": "SOURCE_ONLY_BLINDED_TREATMENT",
        })
        result_rows.append({
            "targetKey": target_key,
            "generationStatus": "NOT_RUN",
            "generatedChain": None,
            "sourceGroundingReview": "NOT_REVIEWED",
            "semanticMatchReview": "NOT_REVIEWED",
            "chainCompletenessReview": "NOT_REVIEWED",
        })
        target = selected_row["targetMap"]
        evaluation_rows.append({
            "targetKey": target_key,
            "testSolutionRuleId": target["testSolutionRuleId"],
            "testSolutionTitle": target["testSolutionTitle"],
            "sourceAnchor": target["sourceAnchor"],
            "stratum": stratum,
            "stratumPopulation": len(strata[stratum]),
            "stratumSampleSize": allocation[stratum],
            "designWeight": f"{len(strata[stratum]) / allocation[stratum]:.8f}",
            "incumbentStatus": "NO_EXACT_SOURCE_ANCHOR_CANDIDATE",
            "treatmentStatus": "NOT_RUN",
        })

    output_dir.mkdir(parents=True, exist_ok=False)
    for name, rows in (
        ("pilot-generation-input.jsonl", generation_rows),
        ("pilot-results-template.jsonl", result_rows),
    ):
        (output_dir / name).write_text(
            "".join(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n" for row in rows),
            encoding="utf-8",
        )
    with (output_dir / "pilot-evaluation-map.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(evaluation_rows[0]))
        writer.writeheader()
        writer.writerows(evaluation_rows)

    counts = Counter(row["stratum"] for row in evaluation_rows)
    protocol = {
        "experimentId": "ATL105-TARGETED-COVERAGE-PILOT-2026-10-12",
        "status": "PRE_REGISTERED_PILOT_READY_GENERATION_NOT_RUN",
        "design": "Paired pre/post pilot: incumbent has no exact anchor candidate for each sampled target; treatment receives ATL105 excerpts only.",
        "sample": {
            "eligiblePopulation": sum(len(rows) for rows in strata.values()),
            "excludedSourceGaps": excluded,
            "sampleSize": len(selected),
            "seed": seed,
            "stratification": "Requirement class; proportional allocation with at least one target per eligible class.",
            "classPopulation": {name: len(rows) for name, rows in sorted(strata.items())},
            "classSample": dict(sorted(counts.items())),
        },
        "frozenIncumbent": {
            "seedCohortExactAnchorReach": seed_summary["existingExactAnchorCandidateReach"],
            "officialBaselineCount": seed_summary["officialTestSolutionBaselineCount"],
            "blindReportsModified": False,
            "runInputsModified": False,
        },
        "primaryOutcome": "Design-weighted share of sampled targets with an independently adjudicated, source-supported semantic match after treatment.",
        "secondaryOutcomes": [
            "Share with a complete BR-to-TS-to-TC-to-TD chain.",
            "Share whose generated assertions and expected outcomes are directly supported by cited ATL105 evidence.",
            "Unsupported, ambiguous, contradicted, and source-blocked counts reported separately.",
        ],
        "decisionRule": "Report pilot estimates and uncertainty; do not promote artifacts or alter incumbent coverage. Any target without a source-supported oracle remains blocked.",
        "blinding": {
            "evaluationMapIncludedInGenerationInput": False,
            "evaluationMapFile": "pilot-evaluation-map.csv",
            "separateGenerationAndEvaluationFiles": True,
        },
        "sourceHashes": {
            "generationContextSha256": sha256(context_path),
            "evaluationMapSha256": sha256(map_path),
            "seedSummarySha256": sha256(summary_path),
            "independentBaselineSha256": sha256(baseline_path),
        },
        "outputs": {
            "generationInput": "pilot-generation-input.jsonl",
            "evaluationOnlyMap": "pilot-evaluation-map.csv",
            "resultsTemplate": "pilot-results-template.jsonl",
        },
    }
    (output_dir / "pilot-protocol.json").write_text(
        json.dumps(protocol, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return protocol


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--seed-pack", type=Path)
    parser.add_argument("--baseline", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--sample-size", type=int, default=28)
    parser.add_argument("--seed", type=int, default=20261012)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not all((args.seed_pack, args.baseline, args.output_dir)):
        parser.error("--seed-pack, --baseline, and --output-dir are required")
    protocol = build(args.seed_pack, args.baseline, args.output_dir, args.sample_size, args.seed)
    print(json.dumps({
        "experimentId": protocol["experimentId"],
        "eligiblePopulation": protocol["sample"]["eligiblePopulation"],
        "sampleSize": protocol["sample"]["sampleSize"],
        "classSample": protocol["sample"]["classSample"],
        "status": protocol["status"],
    }, indent=2))


if __name__ == "__main__":
    main()