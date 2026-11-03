#!/usr/bin/env python3
"""Prepare source-only generation briefs for Test BRs lacking AI anchor candidates."""

import argparse
import csv
import hashlib
import json
import re
from pathlib import Path


PRIMARY_SCOPE = "PRIMARY_AI_CATALOG"
SEGMENT_TO_CHAPTER_12 = {
    "100": "12.1", "101": "12.2", "102": "12.3", "103": "12.4", "104": "12.5",
    "105": "12.6", "108": "12.9", "109": "12.10", "110": "12.11", "111": "12.12",
    "112": "12.13", "113": "12.14", "114": "12.15", "115": "12.16", "116": "12.17",
    "118": "12.19", "119": "12.20", "120": "12.21", "123": "12.24", "130": "12.31",
    "131": "12.32", "132": "12.33", "134": "12.35", "135": "12.36", "136": "12.37",
    "139": "12.40", "140": "12.41", "141": "12.42", "142": "12.43", "143": "12.44",
    "145": "12.46", "146": "12.47", "148": "12.49", "149": "12.50", "150": "12.51",
    "151": "12.52", "152": "12.53", "153": "12.54", "155": "12.56", "156": "12.57",
    "157": "12.58",
}
MAP_FIELDS = ["targetKey", "testSolutionRuleId", "testSolutionTitle", "sourceAnchor", "evidenceStatus"]


def read_json(path: Path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def read_jsonl(path: Path) -> list[dict]:
    return [json.loads(line) for line in path.read_text(encoding="utf-8").splitlines() if line]


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def normalized_anchor_parts(anchor: dict) -> tuple[list[str], list[str], bool]:
    section = str(anchor.get("section") or "")
    sections = []
    elements = [part.strip() for part in str(anchor.get("element") or "").split(",") if part.strip()]
    is_stub = "stub" in section.lower()
    for part in re.split(r"[,;+]", section):
        token = part.strip()
        element_match = re.match(r"Chapter13-Element(\d+)(?:-.*)?$", token, re.IGNORECASE)
        if element_match:
            sections.append("13.2")
            elements.append(element_match.group(1))
            continue
        if token.lower().startswith("chapter12") or not token:
            continue
        token = re.sub(r"\(.*?\)", "", token).strip()
        token = re.sub(r"-EMV$", "", token, flags=re.IGNORECASE)
        token = re.sub(r"^Appendix([A-Z])$", r"Appendix \1", token, flags=re.IGNORECASE)
        if token == "12":
            token = SEGMENT_TO_CHAPTER_12.get(str(anchor.get("segment") or ""), token)
        sections.append(token)
    return list(dict.fromkeys(sections)), list(dict.fromkeys(elements)), is_stub


def heading_index(lines: list[str]) -> list[tuple[int, str, int]]:
    headings = []
    for index, line in enumerate(lines):
        value = line.strip()
        numeric = re.match(r"^(\d+(?:\.\d+){0,4})\s+\S", value)
        appendix = re.match(r"^(Appendix\s+[A-Z])\.\s+", value, re.IGNORECASE)
        if numeric:
            section = numeric.group(1)
            headings.append((index, section, len(section.split("."))))
        elif appendix:
            headings.append((index, appendix.group(1), 1))
    return headings


def extract_section(lines: list[str], headings: list[tuple[int, str, int]], section: str) -> dict | None:
    candidates = [(index, depth) for index, value, depth in headings
                  if value.lower() == section.lower() and index > 3000]
    if not candidates:
        return None
    start, depth = candidates[0]
    end = next((index for index, _, next_depth in headings
                if index > start and next_depth <= depth), min(len(lines), start + 90))
    end = min(end, start + 120)
    quote = "\n".join(lines[start:end]).strip()
    if not quote:
        return None
    return {"section": section, "lineStart": start + 1, "lineEnd": end, "quote": quote}


def extract_element(lines: list[str], section_excerpt: dict, element: str) -> dict | None:
    element_head = re.compile(r"^\s*Number:\s*" + re.escape(element) + r"\s+Name:", re.IGNORECASE)
    candidates = [index for index, line in enumerate(lines) if element_head.search(line)]
    if not candidates:
        token = re.compile(r"(?:Element No\.?\s*|No\.?\s*)" + re.escape(element) + r"\b", re.IGNORECASE)
        candidates = [index for index, line in enumerate(lines) if token.search(line)]
    if not candidates:
        return None
    index = candidates[0]
    next_element = re.compile(r"^\s*Number:\s*\d+\s+Name:", re.IGNORECASE)
    stop = next((i for i in range(index + 1, len(lines)) if next_element.search(lines[i])), min(len(lines), index + 36))
    stop = min(stop, index + 60)
    quote = "\n".join(lines[index:stop]).strip()
    if not quote:
        return None
    return {"section": "13.2", "element": element, "lineStart": index + 1, "lineEnd": stop, "quote": quote}


def source_evidence(lines: list[str], headings: list[tuple[int, str, int]], anchor: dict) -> tuple[list[dict], str]:
    sections, elements, is_stub = normalized_anchor_parts(anchor)
    excerpts = []
    for section in sections:
        if section == "13.2" and elements:
            for element in elements:
                item = extract_element(lines, {}, element)
                if item:
                    excerpts.append(item)
            continue
        section_quote = extract_section(lines, headings, section)
        if not section_quote:
            continue
        excerpts.append(section_quote)
    unique = []
    seen = set()
    for item in excerpts:
        key = (item["lineStart"], item["lineEnd"])
        if key not in seen:
            unique.append(item)
            seen.add(key)
    if is_stub:
        status = "SOURCE_STUB_REVIEW_REQUIRED"
    elif unique:
        status = "SOURCE_TEXT_RETRIEVED_GENERATION_REVIEW_REQUIRED"
    else:
        status = "SOURCE_EVIDENCE_NOT_LOCATED"
    return unique, status


def self_test() -> None:
    sections, elements, is_stub = normalized_anchor_parts({
        "section": "Chapter13-Element63,12.15(stub)+Chapter12-convention",
        "segment": "116",
        "element": "84",
    })
    assert sections == ["13.2", "12.15"]
    assert elements == ["84", "63"]
    assert is_stub
    assert "testSolutionTitle" not in {
        "targetKey", "sourceAnchors", "sourceEvidence", "generationPrompt"
    }
    print("PASS: custom anchors normalize; stub evidence remains review-required")


def build(test_solution_dir: Path, chain_audit: Path, extracted_text: Path, output_dir: Path) -> dict:
    baseline_path = test_solution_dir / "source-derived-requirement-inventory.json"
    for path in (baseline_path, chain_audit, extracted_text):
        if not path.is_file():
            raise FileNotFoundError(path)
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")

    baseline = read_json(baseline_path)
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Target baseline is not an independent Test Solution inventory")
    crosswalk_rows = read_jsonl(chain_audit)
    reached_ids = {
        str(candidate.get("ruleId"))
        for row in crosswalk_rows if row.get("recordScope") == PRIMARY_SCOPE
        for candidate in row.get("officialIndependentTestBrCandidates") or []
        if candidate.get("ruleId")
    }
    targets = [row for row in baseline.get("businessRequirements") or []
               if str(row.get("ruleId")) not in reached_ids]
    if len(targets) != 280:
        raise ValueError(f"Expected 280 no-candidate Test BRs; found {len(targets)}")

    lines = extracted_text.read_text(encoding="utf-8-sig").splitlines()
    headings = heading_index(lines)
    context_rows = []
    mapping_rows = []
    chain_templates = []
    source_status_counts = {}
    for index, test_rule in enumerate(sorted(targets, key=lambda row: str(row.get("ruleId"))), start=1):
        target_key = f"TARGET-{index:04d}"
        anchor = test_rule.get("sourceAnchor") or {}
        excerpts, evidence_status = source_evidence(lines, headings, anchor)
        for excerpt in excerpts:
            excerpt["sourceFile"] = "docs/specs/extracted_text.txt"
            excerpt["quoteSha256"] = hashlib.sha256(excerpt["quote"].encode("utf-8")).hexdigest()
        source_status_counts[evidence_status] = source_status_counts.get(evidence_status, 0) + 1
        artifact_ids = {
            "businessRequirementId": f"{target_key}-BR",
            "scenarioId": f"{target_key}-TS",
            "testCaseId": f"{target_key}-TC",
            "testDataDesignId": f"{target_key}-TD",
        }
        context_rows.append({
            "recordType": "TARGETED_AI_GENERATION_CONTEXT",
            "targetKey": target_key,
            "specification": "ATL105",
            "specificationVersion": "2026-3",
            "sourceAnchors": [anchor],
            "sourceEvidenceStatus": evidence_status,
            "sourceEvidence": excerpts,
            "requestedArtifactIds": artifact_ids,
            "generationPrompt": (
                "Using only the quoted ATL105 source excerpts and source anchors, draft the smallest atomic AI business requirement "
                "that is directly supported by the text. Do not use the Test BR title or statement, infer unstated behavior, or "
                "invent a response oracle. Then propose one BR-linked scenario, test case, and synthetic Test Data design; mark any "
                "step blocked when the excerpt does not define an expected result. Preserve source citations on each artifact."
            ),
            "generationStatus": "READY_FOR_TARGETED_AI_GENERATION" if excerpts and evidence_status != "SOURCE_STUB_REVIEW_REQUIRED" else "SOURCE_REVIEW_REQUIRED_BEFORE_GENERATION",
            "chainContract": ["AI_BR", "AI_SCENARIO", "AI_TEST_CASE", "AI_TEST_DATA_DESIGN"],
            "semanticCoverageCredit": False,
        })
        mapping_rows.append({
            "targetKey": target_key,
            "testSolutionRuleId": test_rule.get("ruleId"),
            "testSolutionTitle": test_rule.get("title"),
            "sourceAnchor": json.dumps(anchor, ensure_ascii=True, sort_keys=True),
            "evidenceStatus": evidence_status,
        })
        chain_templates.append({
            "recordType": "TARGETED_AI_GENERATION_CHAIN_TEMPLATE",
            "targetKey": target_key,
            "cohortId": "TARGETED-ATL105-UNCOVERED-TEST-BR-2026-10-12",
            "blindBaselineEligible": False,
            "generationStatus": "NOT_GENERATED",
            "artifactIds": artifact_ids,
            "links": [
                {"from": artifact_ids["businessRequirementId"], "to": artifact_ids["scenarioId"]},
                {"from": artifact_ids["scenarioId"], "to": artifact_ids["testCaseId"]},
                {"from": artifact_ids["testCaseId"], "to": artifact_ids["testDataDesignId"]},
            ],
            "aiBusinessRequirement": None,
            "scenario": None,
            "testCase": None,
            "testDataDesign": None,
            "sourceEvidenceStatus": evidence_status,
            "approvalStatus": "NOT_REVIEWED",
        })

    output_dir.mkdir(parents=True, exist_ok=False)
    with (output_dir / "targeted-ai-generation-context.jsonl").open("w", encoding="utf-8", newline="\n") as stream:
        for row in context_rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")
    with (output_dir / "targeted-chain-templates.jsonl").open("w", encoding="utf-8", newline="\n") as stream:
        for row in chain_templates:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")
    with (output_dir / "target-evaluation-map.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=MAP_FIELDS)
        writer.writeheader()
        writer.writerows(mapping_rows)

    summary = {
        "artifact": "ATL105-TARGETED-AI-BR-GENERATION-SEED-COHORT",
        "cohortId": "TARGETED-ATL105-UNCOVERED-TEST-BR-2026-10-12",
        "status": "SOURCE_CONTEXT_PACKAGED_GENERATION_NOT_RUN",
        "officialTestSolutionBaselineCount": int(baseline.get("ruleCount", 0)),
        "existingExactAnchorCandidateReach": 321,
        "targetedUncoveredTestBrCount": len(targets),
        "sourceEvidenceStatusCounts": dict(sorted(source_status_counts.items())),
        "targetedGenerationExecuted": False,
        "newAiBrChainsGenerated": 0,
        "blindCoverageCredit": 0,
        "semanticMatchStatus": "NOT_ASSESSED",
        "testSolutionTextInGenerationContext": False,
        "evaluationMapSeparatedFromGenerationContext": True,
        "sourceHashes": {
            "independentTestBrBaselineSha256": sha256(baseline_path),
            "existingChainAuditSha256": sha256(chain_audit),
            "atl105ExtractedTextSha256": sha256(extracted_text),
        },
        "outputs": {
            "generationContext": "targeted-ai-generation-context.jsonl",
            "evaluationOnlyTargetMap": "target-evaluation-map.csv",
            "unfilledChainTemplates": "targeted-chain-templates.jsonl",
        },
        "limitations": [
            "This is a source-evidence seed pack, not newly generated or validated AI requirements/test data.",
            "Generation context omits Test BR IDs, titles, and statements; those appear only in the separate evaluation map to reduce direct text leakage.",
            "Stub or unresolved source anchors are retained with SOURCE_REVIEW_REQUIRED_BEFORE_GENERATION, not used to synthesize unsupported requirements.",
            "All targeted cohort rows are excluded from blind AI coverage calculations and receive no semantic/test approval credit until separately generated and evaluated.",
        ],
    }
    (output_dir / "targeted-cohort-summary.json").write_text(
        json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return summary


def main() -> None:
    parser = argparse.ArgumentParser(description="Prepare source-only generation briefs for uncovered ATL105 Test BRs.")
    parser.add_argument("--test-solution-dir", type=Path)
    parser.add_argument("--chain-audit", type=Path)
    parser.add_argument("--extracted-text", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    required = (args.test_solution_dir, args.chain_audit, args.extracted_text, args.output_dir)
    if not all(required):
        parser.error("all input paths and --output-dir are required unless --self-test is used")
    summary = build(*required)
    print(json.dumps({
        "outputDirectory": str(args.output_dir),
        "targets": summary["targetedUncoveredTestBrCount"],
        "sourceEvidenceStatuses": summary["sourceEvidenceStatusCounts"],
        "targetedGenerationExecuted": summary["targetedGenerationExecuted"],
        "blindCoverageCredit": summary["blindCoverageCredit"],
    }, indent=2))


if __name__ == "__main__":
    main()