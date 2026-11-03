"""Build a review-only Mastercard teaching pack from independent, linked evidence."""

import argparse
import csv
import hashlib
import io
import json
from collections import Counter
from pathlib import Path


MODULE = Path(__file__).resolve().parents[1]
PACK = MODULE / "specifications" / "ATL105"
INPUT = PACK / "docs" / "specs" / "kb" / "segment-100" / "mastercard-ba-reference-review.json"
OUTPUT = PACK / "test-output" / "test-solution-independent-review" / "mastercard-ba-reference-review-v1"
DISPOSITIONS = {
    "SOURCE_SUPPORTED_CORRECTION", "CORRECTED_WITH_QUESTIONS",
    "REVIEW_REQUIRED", "EXTERNAL_CONTRACT_REQUIRED",
}


def fingerprint(content: bytes) -> str:
    return hashlib.sha256(content).hexdigest()


def read_json(path: Path) -> dict:
    return json.loads(path.read_text(encoding="utf-8-sig"))


def unique_index(rows: list[dict], key: str) -> dict[str, dict]:
    result = {}
    for row in rows:
        identifier = row[key]
        if not identifier or identifier in result:
            raise ValueError(f"Empty or duplicate {key}: {identifier}")
        result[identifier] = row
    return result


def resolve_sources(sources: dict, root: Path) -> dict[str, dict]:
    resolved = {}
    for identifier, source in sources.items():
        path = (root / source["path"]).resolve()
        if not path.is_relative_to(root.resolve()):
            raise ValueError(f"Source path escapes specification pack: {identifier}")
        raw = path.read_bytes()
        text = raw.decode("utf-8-sig").replace("\r\n", "\n")
        anchor = source["anchor"]
        positions = [index for index, line in enumerate(text.splitlines(), 1) if anchor.rstrip("\n") in line]
        if len(positions) != 1:
            raise ValueError(f"Source anchor must resolve uniquely: {identifier}: {positions}")
        resolved[identifier] = {
            **source, "line": positions[0], "sha256": fingerprint(raw),
            "evidenceScope": "LOCATION_AND_HASH_ONLY_NOT_SEMANTIC_CERTIFICATION",
        }
    return resolved


def validate(document: dict, root: Path) -> dict[str, dict]:
    if document["schemaVersion"] != 1 or document["specificationVersion"] != "2026-3":
        raise ValueError("Unsupported review schema or specification version")
    if document["status"] != "DRAFT_REVIEW_REQUIRED":
        raise ValueError("Automated review cannot promote this pack")
    if document["reference"]["authority"] != "REFERENCE_ONLY_NOT_AN_ORACLE":
        raise ValueError("BA screenshot must remain reference-only")
    if document["businessRequirement"]["independentBaselineChanged"] is not False:
        raise ValueError("This supplement cannot change the independent BR baseline")
    sources = resolve_sources(document["sources"], root)
    lessons = unique_index(document["lessons"], "id")
    questions = unique_index(document["questions"], "id")
    scenarios = unique_index(document["scenarios"], "id")
    expected = {f"TS-{number:03}" for number in range(1, 38)}
    if set(scenarios) != expected:
        raise ValueError("All 37 reference scenarios must be represented exactly once")
    expected_frs = ["FR-001"] * 3 + ["FR-002"] * 7 + ["FR-003"] * 5 + ["FR-004"] * 4
    expected_frs += ["FR-005"] * 6 + ["FR-006"] * 2 + ["FR-007"] * 5 + ["FR-008"] * 5
    for number, fr in enumerate(expected_frs, 1):
        if scenarios[f"TS-{number:03}"]["fr"] != fr:
            raise ValueError(f"Reference FR identity changed for TS-{number:03}")
    for lesson in lessons.values():
        if not lesson["statement"].strip() or not lesson["sources"]:
            raise ValueError(f"Training lesson lacks statement/evidence: {lesson['id']}")
        if set(lesson["sources"]) - sources.keys():
            raise ValueError(f"Unknown lesson source: {lesson['id']}")
    for row in scenarios.values():
        for field in ("name", "leg", "level", "referenceClaim", "correctedRequirement", "expected"):
            if not row[field].strip():
                raise ValueError(f"{row['id']} lacks {field}")
        if row["disposition"] not in DISPOSITIONS or not row["validations"] or not row["sources"]:
            raise ValueError(f"Invalid review disposition or missing evidence/checks: {row['id']}")
        for field, index in (("sources", sources), ("lessons", lessons), ("questions", questions)):
            if set(row[field]) - index.keys():
                raise ValueError(f"Unresolved {field} link: {row['id']}")
        if row["disposition"] != "SOURCE_SUPPORTED_CORRECTION" and not row["questions"]:
            raise ValueError(f"Unresolved scenario lacks BA/SME question: {row['id']}")
        if row["disposition"] == "SOURCE_SUPPORTED_CORRECTION" and not row["lessons"]:
            raise ValueError(f"Supported scenario lacks a curated training lesson: {row['id']}")
    used_lessons = {item for row in scenarios.values() for item in row["lessons"]}
    used_questions = {item for row in scenarios.values() for item in row["questions"]}
    if used_lessons != lessons.keys() or used_questions != questions.keys():
        raise ValueError("Orphan training lessons or questions")
    for question in questions.values():
        if not question["owner"].strip() or not question["question"].strip() or not question["evidenceNeeded"].strip():
            raise ValueError(f"Question lacks owner or required evidence: {question['id']}")
    return sources


def as_csv(rows: list[dict]) -> str:
    stream = io.StringIO(newline="")
    writer = csv.DictWriter(stream, fieldnames=list(rows[0]))
    writer.writeheader()
    for row in rows:
        safe = {}
        for key, value in row.items():
            text = str(value)
            safe[key] = "'" + text if text.startswith(("=", "+", "-", "@")) else text
        writer.writerow(safe)
    return stream.getvalue()


def json_text(value: dict) -> str:
    return json.dumps(value, indent=2, ensure_ascii=True) + "\n"


def render(document: dict, sources: dict) -> dict[str, str]:
    review_rows, csv_rows, questionnaire = [], [], []
    for row in document["scenarios"]:
        enriched = {
            **row, "referenceBrId": "BR-01", "reviewStatus": "DRAFT_REVIEW_REQUIRED",
            "actualResult": "Not Executed", "testDataReference": "NOT_AUTHORED",
            "preconditions": f"Explicit Mastercard credit context; {row['leg']}; synthetic or masked evidence only.",
            "sourceEvidence": [{"sourceId": identifier, **sources[identifier]} for identifier in row["sources"]],
            "reviewer": document["reviewer"], "reviewedOn": document["reviewedOn"],
        }
        review_rows.append(enriched)
        csv_rows.append({
            "BR ID": "BR-01", "Business Requirement": document["businessRequirement"]["statement"],
            "FR ID": row["fr"], "TS ID": row["id"], "Test Scenario": row["name"],
            "Reference Claim (not authoritative)": row["referenceClaim"],
            "Corrected Requirement / Acceptance Criteria": row["correctedRequirement"],
            "Preconditions": enriched["preconditions"], "Flow / Leg": row["leg"],
            "Validations": " | ".join(row["validations"]), "Expected Result": row["expected"],
            "Actual Result": "Not Executed", "TD Reference": "NOT_AUTHORED",
            "Validation Level": row["level"],
            "Source References": " | ".join(
                f"{sources[key]['path']}:{sources[key]['line']} ({sources[key]['section']})" for key in row["sources"]
            ),
            "Source Locator Excerpts": " | ".join(sources[key]["anchor"].strip() for key in row["sources"]),
            "Review Disposition": row["disposition"], "Status": "DRAFT_REVIEW_REQUIRED",
            "Training Lesson IDs": ";".join(row["lessons"]), "Question IDs": ";".join(row["questions"]),
            "BA Decision": "", "BA Rationale / Evidence": "", "BA Reviewer / Date": "",
        })
    for question in document["questions"]:
        questionnaire.append({
            "Question ID": question["id"], "Register ID": "SEG100-SME-003",
            "Affected TS IDs": ";".join(row["id"] for row in review_rows if question["id"] in row["questions"]),
            "Owner": question["owner"], "Question": question["question"], "Evidence Needed": question["evidenceNeeded"],
            "Status": "OPEN", "Answer": "", "Source / Configuration / Version": "",
            "Environment / Exceptions": "", "Reviewer / Date": "",
        })
    counts = dict(sorted(Counter(row["disposition"] for row in review_rows).items()))
    training = {
        "artifact": "ATL105-MASTERCARD-SOURCE-LINKED-TRAINING-SUPPLEMENT",
        "authority": "INDEPENDENT_TEST_SOLUTION", "status": "DRAFT_REVIEW_REQUIRED",
        "specificationVersion": document["specificationVersion"],
        "certified": False, "executionReady": False, "baselineChanged": False,
        "policy": "Only curated independent source-derived statements are teaching content. Reference claims and open answers are excluded. Source location/hash checks do not certify semantic correctness.",
        "lessons": [
            {**lesson, "status": "DRAFT_REVIEW_REQUIRED",
             "sourceEvidence": [{"sourceId": key, **sources[key]} for key in lesson["sources"]],
             "scenarioIds": [row["id"] for row in review_rows if lesson["id"] in row["lessons"]]}
            for lesson in document["lessons"]
        ],
    }
    report = {
        "artifact": "ATL105-MASTERCARD-BA-REFERENCE-REVIEW", "reference": document["reference"],
        "businessRequirement": document["businessRequirement"],
        "status": "DRAFT_REVIEW_REQUIRED", "executionReady": False,
        "summary": {"referenceBrCount": 1, "functionalRequirementCount": 8, "scenarioCount": len(review_rows),
                    "curatedTrainingLessonCount": len(training["lessons"]), "questionCount": len(questionnaire),
                    "dispositions": counts, "authoredTestDataCount": 0, "executedCaseCount": 0},
        "scenarios": review_rows, "questions": document["questions"],
    }
    readme = (
        "# Mastercard BA reference review\n\n"
        "Reference scope: one BR, eight FRs and all 37 scenarios transcribed from the user-shared screenshot. "
        "The screenshot is reference-only and is not stored or hash-bound in this repository. "
        "This is not a Mastercard certification or a complete BR/TS/TC/TD package.\n\n"
        "## Deliverables\n\n"
        "- [Requirements review](requirements-review.csv): corrected statements, original disputed claims, "
        "validations, expected results, source locations, flow legs, review level and BA response columns.\n"
        "- [BA/SME questionnaire](ba-sme-questionnaire.csv): nine grouped questions with affected scenario IDs.\n"
        "- [Source-linked training lessons](training-lessons.json): 13 curated statements only; no "
        "unresolved answer or screenshot claim is promoted to an oracle.\n"
        "- [Full review](review.json) and [provenance](manifest.json).\n"
        "- [Email draft](BA-SME-EMAIL.md): prepared for internal distribution, not sent.\n\n"
        "## Review result\n\n"
        + "".join(f"- {key}: {value}\n" for key, value in counts.items())
        + "\nAll content remains DRAFT_REVIEW_REQUIRED pending authorized review. "
        "Actual results are Not Executed; concrete TDs are NOT_AUTHORED. "
        "Source lookup checks establish location and fingerprint only, not semantic approval. "
        "The 601-rule independent baseline and existing canonical chains are unchanged. "
        "This updates repository teaching evidence, not model weights or hosted-model training.\n\n"
        "Expiry validation is required by Section 10.1.1.1 while Element 12 still specifies "
        "transmission for expired-card authorizations. Queued TOR ordering is supported by Section 2.3.1 "
        "for the same device, not as a global single-thread rule. Clearing, settlement, issuer "
        "status, routing and audit contracts remain separately gated.\n"
    )
    email = (
        "Subject: Review requested - corrected Mastercard credit-purchase requirements and open questions\n\n"
        "Hi BA/SME team,\n\n"
        "We used your table as reference and reviewed all 37 scenarios against the independent "
        "ATL105 2026-3 Test knowledge base. Please review the attached requirements-review.csv "
        "and complete ba-sme-questionnaire.csv (MC-Q01 through MC-Q09; communication register SEG100-SME-003).\n\n"
        "Corrections include expiry Element 12 (not 51), account Element 2 (not 24), "
        "sequence Element 86 (not 80), approval Element 5 (not 6), and credit-purchase Prompt Code "
        "0020 (020 is the credit card-type component). Mastercard identity is a separate policy.\n\n"
        "Please confirm or amend interpretations and provide versioned source/configuration evidence, "
        "environment, exceptions, reviewer and date. Expiry handling and same-device TOR queueing "
        "retain their source-specific conditions. External card/merchant status, routing, amount "
        "policy, host outcomes, clearing/settlement and audit requirements remain open.\n\n"
        "These are review-only requirements, not executed tests or certified coverage. "
        "No source baseline, approval status or coverage claim has been promoted. "
        "Please use synthetic/redacted examples only and return feedback by [date].\n\nThanks,\n[Sender]\n"
    )
    return {
        "review.json": json_text(report), "requirements-review.csv": as_csv(csv_rows),
        "ba-sme-questionnaire.csv": as_csv(questionnaire), "training-lessons.json": json_text(training),
        "BA-SME-EMAIL.md": email, "README.md": readme,
    }


def build(input_path: Path, root: Path) -> dict[str, str]:
    document = read_json(input_path)
    sources = validate(document, root)
    files = render(document, sources)
    files["manifest.json"] = json_text({
        "artifact": "ATL105-MASTERCARD-REFERENCE-REVIEW-PROVENANCE", "schemaVersion": 1,
        "inputSha256": fingerprint(input_path.read_bytes()),
        "generatorSha256": fingerprint(Path(__file__).read_bytes()),
        "sourceHashes": {source["path"]: source["sha256"] for source in sources.values()},
        "outputHashes": {name: fingerprint(text.encode("utf-8")) for name, text in files.items()},
        "semanticCertification": False, "executionCertified": False,
    })
    return files


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true", help="Verify published outputs without modifying them")
    args = parser.parse_args()
    files = build(INPUT, PACK)
    if args.check:
        mismatches = [
            name for name, content in files.items()
            if not (OUTPUT / name).is_file() or (OUTPUT / name).read_bytes() != content.encode("utf-8")
        ]
        if mismatches:
            raise ValueError(f"Missing/stale review outputs; rerun the builder: {mismatches}")
        print("PASS: 37 scenarios, 13 draft training lessons, 9 questions; outputs and source hashes reconcile.")
    else:
        OUTPUT.mkdir(parents=True, exist_ok=True)
        for name, content in files.items():
            (OUTPUT / name).write_bytes(content.encode("utf-8"))
        print(f"Wrote {len(files)} review/training deliverables to {OUTPUT}")


if __name__ == "__main__":
    main()
