import argparse
import collections
import csv
import gzip
import hashlib
import json
import re
from pathlib import Path


def sha256(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_csv(path):
    with path.open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def write_csv(path, rows, fields):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value
                             for key, value in row.items()})


def clean(text):
    return re.sub(r"\s+", " ", text).strip()


def obligation_types(text):
    text = clean(text).lower()
    kinds = []
    if re.search(r"persist|lifecycle|subsequent|follow.up|initial authorization|echo|reuse", text):
        kinds.append("LIFECYCLE")
    if re.search(r"one of|allowed value|valid value|fixed value|must be ['\"]", text):
        kinds.append("VALUES")
    if re.search(r"numeric|alphanumeric|alphabetic|length|\bdigits?\b|\bbytes?\b|\bcharacters?\b|format", text):
        kinds.append("FORMAT")
    if re.search(r"required|must be present|mandatory|must contain|optional|omitted", text):
        kinds.append("PRESENCE")
    if re.search(r"\bif\b|\bwhen\b|conditional|depends|requires|only for|only when", text):
        kinds.append("CONDITION")
    if re.search(r"response code|decline code|approved|declined", text):
        kinds.append("OUTCOME")
    return kinds or ["OTHER"]


def grouping_key(row, contexts):
    segments = sorted(json.loads(row["aiSegments"]))
    elements = sorted(json.loads(row["aiElements"]))
    scope = elements if elements else [clean(row["aiStatement"])]
    return json.dumps([segments, scope, obligation_types(row["aiStatement"]), sorted(contexts)], ensure_ascii=True)


def self_test():
    row = {"aiSegments": '["100"]', "aiElements": '["86"]', "aiStatement": "Sequence Number must be present"}
    other = dict(row, aiStatement="Sequence Number must persist through subsequent transactions")
    assert grouping_key(row, {"Authorization"}) != grouping_key(other, {"Authorization"})
    assert grouping_key(row, {"Authorization"}) != grouping_key(row, {"Completion"})
    assert grouping_key(row, set()) != grouping_key(dict(row, aiSegments='["109"]'), set())
    assert clean("six digits") != clean("five digits")
    assert "VALUES" in obligation_types("Load Type must be one of F or P")
    print("PASS: presence/lifecycle, segment and transaction contexts remain separate; constraint wording is preserved")


def run(review_dir, output_dir):
    csv.field_size_limit(100000000)
    if output_dir.exists():
        raise ValueError("Refusing to overwrite output directory")
    manifest = json.loads((review_dir / "manifest.json").read_text())
    names = ["all-ai-br-review-register.csv", "ai-test-br-ranked-review-leads.csv", "ai-br-chain-evidence.jsonl.gz"]
    inputs = {str(review_dir / name): sha256(review_dir / name) for name in names}
    for name in names:
        if inputs[str(review_dir / name)] != manifest["artifacts"][name]:
            raise ValueError("Review input hash mismatch: " + name)
    inputs[str(Path(__file__).resolve())] = sha256(Path(__file__))
    register = read_csv(review_dir / names[0])
    by_id = {row["aiBrId"]: row for row in register}
    if len(register) != len(by_id) or len(register) != 6880:
        raise ValueError("Expected all 6,880 unique AI BRs")
    leads = collections.defaultdict(list)
    for pair in read_csv(review_dir / names[1]):
        leads[pair["aiBrId"]].append(pair["testBrId"])
    groups = {}
    members = []
    representatives = {}
    chain_ids = set()
    with gzip.open(review_dir / names[2], "rt", encoding="utf-8") as stream:
        for line in stream:
            chain = json.loads(line)
            identifier = chain["aiBrId"]
            if identifier in chain_ids:
                raise ValueError("Duplicate chain BR")
            chain_ids.add(identifier)
            row = by_id[identifier]
            contexts = {case.get("transactionType") for case in chain["cases"] if case.get("transactionType")}
            if not contexts:
                contexts = {scenario.get("targetTransaction") for scenario in chain["scenarios"] if scenario.get("targetTransaction")}
            key = grouping_key(row, contexts)
            group_id = "OBL-" + hashlib.sha256(key.encode()).hexdigest()[:12].upper()
            group = groups.setdefault(group_id, {"groupId": group_id, "obligation": "+".join(obligation_types(row["aiStatement"])),
                "segments": json.loads(row["aiSegments"]), "elements": json.loads(row["aiElements"]),
                "transactionContexts": sorted(contexts), "members": [], "variants": set(), "testLeads": set(),
                "exampleStatement": clean(row["aiStatement"]), "exceptionFlags": set()})
            group["members"].append(identifier)
            statement = clean(row["aiStatement"])
            group["variants"].add(statement)
            group["testLeads"].update(leads[identifier])
            flags = []
            if not chain["cases"]:
                flags.append("NO_LINKED_CASE")
            if not group["segments"] or not group["elements"]:
                flags.append("UNRESOLVED_ANCHOR")
            if any(case.get("scenarioType") == "flow" for case in chain["cases"]):
                flags.append("MULTILEG_REVIEW")
            if not leads[identifier]:
                flags.append("NO_RETAINED_TEST_LEAD")
            group["exceptionFlags"].update(flags)
            members.append({"groupId": group_id, "aiBrId": identifier, "statement": statement,
                "sourcePage": row["aiSourcePage"], "sourceRuleId": row["aiSourceRuleId"],
                "scenarioCount": len(chain["scenarios"]), "caseCount": len(chain["cases"]),
                "testBrCandidateIds": leads[identifier], "exceptions": flags,
                "brAlignmentDecision": "NOT_REVIEWED", "chainAdequacyDecision": "NOT_REVIEWED"})
            for case in chain["cases"]:
                sample_key = (group_id, statement, case.get("transactionType"), case.get("scenarioType"),
                              case.get("negativeClass"), case.get("violatedElementName"), bool(case.get("flags")))
                representatives.setdefault(sample_key, {"groupId": group_id, "aiBrId": identifier,
                    "obligationStatement": statement, "caseId": case["id"], "scenarioId": case.get("scenarioId"),
                    "transaction": case.get("transactionType"), "intent": case.get("scenarioType"),
                    "negativeClass": case.get("negativeClass"), "violatedElement": case.get("violatedElementName"),
                    "payloadReferences": [entry["path"] for entry in case.get("physicalEvidence", [])],
                    "sampleUse": "REVIEW_STARTING_POINT_NOT_COVERAGE_PROOF"})
    if chain_ids != set(by_id):
        raise ValueError("BR and chain populations differ")
    queue = []
    for group in groups.values():
        queue.append({"groupId": group["groupId"], "obligation": group["obligation"], "segments": group["segments"],
            "elements": group["elements"], "transactionContexts": group["transactionContexts"],
            "aiBrCount": len(group["members"]), "distinctClaimCount": len(group["variants"]),
            "exampleStatement": group["exampleStatement"], "candidateTestBrCount": len(group["testLeads"]),
            "exceptionFlags": sorted(group["exceptionFlags"]),
            "priority": "EXCEPTION" if group["exceptionFlags"] else "STANDARD",
            "reviewStatus": "REVIEW_REQUIRED"})
    queue.sort(key=lambda row: (row["priority"] != "STANDARD", -row["aiBrCount"], row["groupId"]))
    for path, digest in inputs.items():
        if sha256(Path(path)) != digest:
            raise ValueError("Input changed: " + path)
    output_dir.mkdir(parents=True)
    write_csv(output_dir / "review-groups.csv", queue, list(queue[0]))
    write_csv(output_dir / "br-members.csv", members, list(members[0]))
    samples = list(representatives.values())
    write_csv(output_dir / "representative-cases.csv", samples, list(samples[0]))
    exceptions = [row for row in members if row["exceptions"]]
    write_csv(output_dir / "exceptions.csv", exceptions, list(members[0]))
    counts = {"aiBrs": len(members), "proposedGroups": len(queue),
              "distinctClaimVariantsAcrossGroups": sum(row["distinctClaimCount"] for row in queue),
              "representativeCaseRows": len(samples), "brsWithExceptions": len(exceptions),
              "semanticApprovals": 0}
    summary = {"counts": counts, "inputSha256": inputs,
        "policy": "Proposed work groups only: same producer segment/element scope, heuristic obligation labels and exact observed transaction-context set. Distinct statements remain separate claim variants. Conditions, values, source interpretation and expected outcomes must be checked before sharing any decision. Samples do not establish equivalence of uninspected cases.",
        "firstReview": [{"aiBrId": identifier, "groupId": next(row["groupId"] for row in members if row["aiBrId"] == identifier)}
                        for identifier in ["REQ-SRC-ATL105-PDF-001:3251", "REQ-SRC-ATL105-PDF-001:6098", "REQ-SRC-ATL105-PDF-001:1937"]]}
    (output_dir / "summary.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
    guide = "# Review by Business Obligation\n\n"
    guide += "Start with one group. Record two separate decisions: **BR alignment** and **chain adequacy**.\n\n"
    guide += "1. Open [review-groups.csv](review-groups.csv); choose a STANDARD group.\n"
    guide += "2. Filter [br-members.csv](br-members.csv) by groupId. Read every distinct claim; compare Test candidates and their source scope.\n"
    guide += "3. Inspect [representative-cases.csv](representative-cases.csv) for each transaction and test intent. Escalate differences through [exceptions.csv](exceptions.csv).\n\n"
    guide += "Decisions: **Aligned / Partial / Conflict / Insufficient Evidence**. Keep chain adequacy separate: **Demonstrated / Partial / Missing / Unresolved**. No decisions have been granted by grouping.\n\n"
    guide += "## First Review: Sequence Number\n\n| AI BR | Obligation | Evidence Needed |\n|---|---|---|\n"
    guide += "| :3251 | Presence | Present and omitted field, with justified expected outcomes |\n"
    guide += "| :6098 | Six-digit numeric format | Valid, short, long and nonnumeric values; lifetime claim separate |\n"
    guide += "| :1937 | Lifecycle persistence | Linked authorization and follow-up steps, same sequence number |\n\n"
    guide += "Do not combine these three into a single match. The grouping is proposed, not an approval. Different claim variants, conditions and outcomes need separate review; passing a sample never approves every member. AI scenario approval is not a prerequisite.\n"
    (output_dir / "START.md").write_text(guide, encoding="utf-8")
    artifacts = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file()}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2), encoding="utf-8")
    print(json.dumps(counts, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--review-dir", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.review_dir and args.output_dir:
        run(args.review_dir.resolve(), args.output_dir.resolve())
    else:
        parser.error("Supply review directory and new output directory")


if __name__ == "__main__":
    main()