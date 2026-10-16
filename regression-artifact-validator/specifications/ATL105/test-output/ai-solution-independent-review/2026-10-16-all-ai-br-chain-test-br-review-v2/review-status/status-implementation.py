import argparse
import collections
import csv
import gzip
import hashlib
import json
import os
from pathlib import Path


def sha256(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def read_csv(path):
    with path.open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def write_csv(path, rows):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0]))
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (dict, list)) else value
                             for key, value in row.items()})


def run(review_dir, output_dir):
    csv.field_size_limit(100000000)
    if os.name == "nt":
        review_dir = Path("\\\\?\\" + str(review_dir.resolve()))
        output_dir = Path("\\\\?\\" + str(output_dir.resolve()))
    if output_dir.exists():
        raise ValueError("Refusing to overwrite status output")
    screen_dir = review_dir / "automated-chain-screen-v4"
    inputs = {}
    for folder in (review_dir, screen_dir):
        manifest_path = folder / "manifest.json"
        inputs[str(manifest_path)] = sha256(manifest_path)
        for name, digest in read_json(manifest_path)["artifacts"].items():
            if sha256(folder / name) != digest:
                raise ValueError("Artifact hash mismatch: " + name)
            inputs[str(folder / name)] = digest
    summary = read_json(review_dir / "summary.json")
    screening = read_json(screen_dir / "summary.json")
    progress = read_csv(review_dir / "review-progress.csv")
    pairs = read_csv(review_dir / "ai-test-br-ranked-review-leads.csv")
    br_checks = read_csv(screen_dir / "all-br-chain-screen.csv")
    by_test = collections.defaultdict(set)
    for pair in pairs:
        by_test[pair["testBrId"]].add(pair["aiBrId"])
    with gzip.open(review_dir / "test-chain-evidence.jsonl.gz", "rt", encoding="utf-8") as stream:
        tests = [json.loads(line) for line in stream]
    reverse_rows = []
    for test in tests:
        identifier = test["testBrId"]
        linked = sorted(by_test[identifier])
        reverse_rows.append({"testBrId": identifier, "title": test["br"].get("title", ""),
            "testBrStatus": test["br"].get("status") or "NOT_SET",
            "population": "AUTHORED_TEST_BR" if identifier.startswith("BR-") else "CATALOG_RULE",
            "candidateAiBrCount": len(linked), "candidateAiBrIds": linked,
            "scenarioCount": len(test["scenarios"]), "caseCount": len(test["cases"]), "dataCount": len(test["data"]),
            "dataReadiness": dict(collections.Counter(row.get("readiness") or "NOT_SET" for row in test["data"])),
            "reviewStatus": "CANDIDATE_OBLIGATIONS_PENDING" if linked else "NO_RETAINED_LEAD_NOT_PROOF_OF_NO_MATCH"})
    batch_counts = collections.defaultdict(collections.Counter)
    for row in progress:
        batch_counts[row["scheduledBatchId"]][row["screeningStatus"]] += 1
    batch_rows = [{"batchId": identifier, "assigned": sum(counts.values()),
                   "priorPreliminaryOpen": counts["PRELIMINARY_OPEN"], "notPreliminarilyScreened": counts["NOT_SCREENED"],
                   "boundedAutomatedChecks": "COMPLETED_FOR_ALL_ASSIGNED_ROWS", "fullObligationReview": "INCOMPLETE"}
                  for identifier, counts in sorted(batch_counts.items())]
    case_states = collections.Counter()
    mismatch_cases = set()
    cases = set()
    with gzip.open(screen_dir / "case-field-observations.jsonl.gz", "rt", encoding="utf-8") as stream:
        for line in stream:
            case = json.loads(line)
            if case["caseId"] in cases:
                raise ValueError("Duplicate case ID in screening output")
            cases.add(case["caseId"])
            states = {item["state"] for route in case["payloadObservations"] for item in route.get("observations", [])}
            case_states.update(states)
            if "VALUE_DISAGREEMENT_REVIEW_REQUIRED" in states:
                mismatch_cases.add(case["caseId"])
    if len(progress) != 6880 or len(br_checks) != 6880 or len(tests) != 1330:
        raise ValueError("Incomplete review population")
    if sum(row["assigned"] for row in batch_rows) != 6880:
        raise ValueError("Batch assignment population mismatch")
    output_dir.mkdir(parents=True)
    write_csv(output_dir / "all-test-br-reverse-index.csv", reverse_rows)
    write_csv(output_dir / "batch-status.csv", batch_rows)
    write_csv(output_dir / "ai-br-follow-up.csv", [row for row in br_checks if json.loads(row["reviewFlags"])])
    counts = {"aiBrsWithBoundedChecks": len(br_checks), "testBrTargetRecords": len(tests),
        "rankedCandidatePairs": len(pairs), "retainedTestBrTargets": len(by_test),
        "testBrTargetsWithoutRetainedLead": sum(not row["candidateAiBrCount"] for row in reverse_rows),
        "priorPreliminaryOpen": sum(row["screeningStatus"] == "PRELIMINARY_OPEN" for row in progress),
        "notPreliminarilyScreened": sum(row["screeningStatus"] == "NOT_SCREENED" for row in progress),
        "linkedCasesWithBoundedChecks": len(cases), "uniqueCasesWithResolvedFieldDisagreement": len(mismatch_cases),
        "uniqueCasesByObservation": dict(case_states), "fullChainReviewsCompleted": 0,
        "semanticMatchesConfirmed": 0, "hostExecuted": False}
    status = {"artifact": "ALL_BR_REVIEW_STATUS", "counts": counts, "screening": screening,
              "inputSha256": inputs, "scriptSha256": sha256(Path(__file__)),
              "policy": "Bounded checks and retrieval are completed; full BR obligation comparison is not. No SME or host certification is inferred. Missing leads are retrieval gaps, not proven absence of coverage."}
    (output_dir / "status.json").write_text(json.dumps(status, indent=2), encoding="utf-8")
    report = "# AI BR and Chain Review Status\n\n"
    report += "The automated population-wide pass is complete. The individual full-obligation and full-chain review is not complete.\n\n"
    report += "| Measure | Result |\n|---|---:|\n"
    for label, value in [("AI BRs with bounded checks", len(br_checks)), ("Test BR target records searched", len(tests)),
                         ("Catalog rules", 605), ("Additional authored Test BR records", 725),
                         ("Retained candidate pairs", len(pairs)), ("Test targets with no retained lead", counts["testBrTargetsWithoutRetainedLead"]),
                         ("Unique linked AI cases checked", len(cases)), ("Physical payload files hash-verified", screening["observations"]["physicalPayloadsHashVerified"]),
                         ("Prior preliminary screenings, still open", counts["priorPreliminaryOpen"]),
                         ("BRs not yet individually screened", counts["notPreliminarilyScreened"]),
                         ("Full chain reviews completed", 0)]:
        report += "| {} | {:,} |\n".format(label, value)
    report += "\n## Findings\n\n"
    report += "- The old 605-rule target set omitted 725 authored Test BR records. All 1,330 are now searched; overlapping catalog/authored records are not assumed to be distinct atomic obligations.\n"
    report += "- 2,806 AI BRs have no linked case. 290 have at least one linked request whose metadata does not contain the claimed element. These require context review; response-only and lifecycle rules must not be classified as broken links solely by request-field absence.\n"
    report += "- Exact single-step value checks found {} unique cases with a definite disagreement in the supported binding scope. Aliases, identity mutations and multileg steps remain unresolved, not passed. Counts of field observations include physical copies and are not distinct defects.\n".format(len(mismatch_cases))
    report += "- {} multileg payload observations require step-specific binding; case-level request metadata must not be applied to every follow-up payload.\n".format(screening["observations"].get("field_MULTILEG_STEP_BINDING_UNRESOLVED", 0))
    report += "- Top-10 plus existing anchor leads is a shortlist, not exhaustive semantic comparison. A Test target without a retained lead is not proven uncovered.\n"
    report += "\n## Review Files\n\n"
    report += "- [All Test BRs, including no-lead rows](all-test-br-reverse-index.csv)\n"
    report += "- [Every batch and its remaining work](batch-status.csv)\n"
    report += "- [AI BRs needing chain or anchor follow-up](ai-br-follow-up.csv)\n"
    report += "- [All AI BR automated checks](../automated-chain-screen-v4/all-br-chain-screen.csv)\n"
    report += "- [Candidate-pair triage](../automated-chain-screen-v4/candidate-pair-screen.csv)\n"
    report += "- [Consolidated individual review progress](../review-progress.csv)\n"
    report += "\n## Remaining Work\n\n"
    report += "Complete the partial batches in order. Compare BR obligation, applicability, scenario intent, case mutation and expected outcome with source evidence; inspect Test authored chains as well as catalog placeholders. Resolve field aliases and per-step bindings, then adjudicate candidate relations with cited evidence. Approval is not required for AI scenario inclusion or provisional comparison. Independent outcomes and host execution remain separate gates.\n"
    (output_dir / "REVIEW-STATUS.md").write_text(report, encoding="utf-8")
    (output_dir / "status-implementation.py").write_bytes(Path(__file__).read_bytes())
    manifest = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file()}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": manifest}, indent=2), encoding="utf-8")
    print(json.dumps(counts, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--review-dir", type=Path, required=True)
    parser.add_argument("--output-dir", type=Path, required=True)
    args = parser.parse_args()
    run(args.review_dir, args.output_dir)


if __name__ == "__main__":
    main()