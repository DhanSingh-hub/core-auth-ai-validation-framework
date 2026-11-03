import argparse
import csv
import json
from collections import Counter
from pathlib import Path


COHORTS = {
    "NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR": "NON_ELEMENT_OR_ELEMENT_ANCHOR_UNRESOLVED",
    "NOT_MATCHABLE_SEGMENT_UNRESOLVED": "SEGMENT_CONTEXT_UNRESOLVED",
}

FIELDS = [
    "aiBrId",
    "cohort",
    "disposition",
    "aiStatement",
    "producerCategory",
    "producerRequirementType",
    "proposedAnchorType",
    "proposedScope",
    "elementCandidates",
    "segmentCandidates",
    "proposedSegmentNumber",
    "proposedSegmentIds",
    "anchorReviewReason",
    "sourceId",
    "sourcePage",
    "sourceQuote",
    "quoteVerified",
    "producerFlags",
    "requestedAiTeamAction",
    "aiTeamDecision",
    "correctedAnchorType",
    "correctedSegment",
    "correctedElement",
    "reviewRationale",
]


def compact_json(value):
    return json.dumps(value, ensure_ascii=False, sort_keys=True, separators=(",", ":"))


def requested_action(disposition, proposal):
    if disposition == "NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR":
        if proposal.get("type") == "UNRESOLVED":
            return (
                "Review the cited ATL105 text and classify this BR as element-level or a non-element rule type. "
                "If element-level, provide the numeric element only when explicitly supported by the source. "
                "Otherwise provide the correct business-scope anchor. Add an exact source quote; do not infer an element."
            )
        return (
            "Verify the proposed non-element rule type and scope against an exact ATL105 source quote. "
            "Keep it non-element if the source does not define one numeric element; provide a domain anchor and rationale."
        )
    proposed_segments = proposal.get("segmentCandidates") or []
    return (
        "Resolve the segment or message-family context from the cited ATL105 text and an approved source mapping. "
        "Treat listed segment candidates as proposals only; do not promote unresolved or LLM-proposed assignments. "
        "Return an exact quote and rationale, or mark the segment unresolved for SME review."
    ) if proposed_segments else (
        "Resolve the segment or message-family context from the cited ATL105 text. "
        "Add a segment only when supported by an exact quote and approved mapping; otherwise leave it unresolved for review."
    )


def feedback_row(record):
    proposal = record.get("anchorProposal") or {}
    disposition = record.get("candidateDisposition", "NOT_ASSESSED")
    return {
        "aiBrId": record.get("id", ""),
        "cohort": COHORTS.get(disposition, "OUT_OF_SCOPE"),
        "disposition": disposition,
        "aiStatement": record.get("statement", ""),
        "producerCategory": proposal.get("category", ""),
        "producerRequirementType": proposal.get("requirementType", ""),
        "proposedAnchorType": proposal.get("type", ""),
        "proposedScope": proposal.get("scope", ""),
        "elementCandidates": compact_json(proposal.get("elementCandidates") or []),
        "segmentCandidates": compact_json(proposal.get("segmentCandidates") or []),
        "proposedSegmentNumber": proposal.get("proposedSegmentNumber", ""),
        "proposedSegmentIds": compact_json(proposal.get("proposedSegmentIds") or []),
        "anchorReviewReason": proposal.get("anchorReviewReason", ""),
        "sourceId": proposal.get("sourceId", ""),
        "sourcePage": proposal.get("sourcePage", ""),
        "sourceQuote": proposal.get("sourceQuote") or "",
        "quoteVerified": proposal.get("quoteVerified", False),
        "producerFlags": compact_json(record.get("flags") or []),
        "requestedAiTeamAction": requested_action(disposition, proposal),
        "aiTeamDecision": "",
        "correctedAnchorType": "",
        "correctedSegment": "",
        "correctedElement": "",
        "reviewRationale": "",
    }


def markdown_report(rows, ledger_path):
    disposition_counts = Counter(row["disposition"] for row in rows)
    type_counts = Counter(
        (row["cohort"], row["proposedAnchorType"] or "NOT_DECLARED") for row in rows
    )
    unverified = sum(row["quoteVerified"] is False for row in rows)
    page_present = sum(bool(row["sourcePage"]) for row in rows)
    non_element_count = disposition_counts["NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR"]
    segment_count = disposition_counts["NOT_MATCHABLE_SEGMENT_UNRESOLVED"]
    lines = [
        "# AI Team Feedback: ATL105 BR Anchor Gaps",
        "",
        "**Status:** Draft remediation request; source quotes are not yet verified.",
        "**AI catalog:** 6,880 BRs; source catalog SHA-256 `6b40963dfec26160013f87d47fe1defa3652b414adc4b29b60619cd6d71b6b4a`.",
        f"**Evidence ledger:** `{ledger_path}`.",
        "",
        "## Request",
        "",
        "Please review the attached row-level CSV and return a versioned anchor-correction sidecar. Preserve the producer delivery unchanged. For each row, provide the supported rule type, segment/message family when applicable, source page/section, exact ATL105 quote, and rationale. Do not fabricate element numbers or infer a segment solely from linked test-case payloads.",
        "",
        "## Scope and Counts",
        "",
        "| Cohort | BRs | Proposed anchor types |",
        "|---|---:|---|",
    ]
    for cohort in ("NON_ELEMENT_OR_ELEMENT_ANCHOR_UNRESOLVED", "SEGMENT_CONTEXT_UNRESOLVED"):
        count = sum(row["cohort"] == cohort for row in rows)
        types = sorted({kind for (group, kind), value in type_counts.items() if group == cohort and value})
        lines.append(f"| {cohort} | {count:,} | {', '.join(types)} |")
    lines.extend([
        "",
        f"Total feedback rows: **{len(rows):,}** ({non_element_count:,} without a numeric element anchor; {segment_count:,} with segment context unresolved).",
        f"Source page is populated on **{page_present:,}/{len(rows):,}** rows. Exact source quote verification is false on **{unverified:,}/{len(rows):,}** rows in the ledger. A page number alone is not verified source evidence.",
        "",
        "### Non-element or element-anchor unresolved",
        "",
        f"The ledger classifies {non_element_count:,} BRs without a usable numeric element anchor. The current proposed types split as follows:",
        "",
        "| Proposed type | BRs |",
        "|---|---:|",
    ])
    for (cohort, kind), count in sorted(type_counts.items()):
        if cohort == "NON_ELEMENT_OR_ELEMENT_ANCHOR_UNRESOLVED":
            lines.append(f"| {kind} | {count:,} |")
    lines.extend([
        "",
        "For `UNRESOLVED`, determine from normative source text whether the BR is element-level or belongs to a process, response, lifecycle, cross-field, dependency, security, or other rule type. For a confirmed non-element BR, supply the domain/scope anchor rather than forcing an element ID. For an element-level BR, add an element only when the source explicitly supports it.",
        "",
        "### Segment context unresolved",
        "",
        f"The ledger has {segment_count:,} BRs with an element or cross-field proposal but no lookup-eligible segment. Current anchor-type proposals are {dict(sorted((kind, count) for (cohort, kind), count in type_counts.items() if cohort == 'SEGMENT_CONTEXT_UNRESOLVED'))}.",
        "",
        "Resolve segment/message-family context from the cited source section, exact quote, and a validated entity-to-segment mapping. Preserve multiple possible segments as ambiguous until reviewed. The ledger's segment candidates and assignment methods are proposals; unresolved and LLM-proposed assignments are not approved mappings.",
        "",
        "## Required Response Per BR",
        "",
        "1. Confirm `ELEMENT` or the correct non-element rule type; provide business scope for non-element BRs.",
        "2. Provide source ID, document version, page/section, and an exact quote that can be checked against the source.",
        "3. Provide a segment/message-family anchor only when the quote and approved mapping support it. State `UNRESOLVED` when evidence is insufficient.",
        "4. For element-level BRs, provide the numeric element only when explicitly supported; otherwise explain the ambiguity.",
        "5. Keep confidence, ambiguity, and any needed SME decision visible. Do not mark semantic equivalence as approved through anchor repair alone.",
        "",
        "## Acceptance Checks",
        "",
        "- Exact quote matches the named ATL105 source version and page/section.",
        "- Every added segment/element has explicit provenance and a stated assignment basis.",
        "- Non-element BRs use typed business-scope anchors rather than invented element numbers.",
        "- Ambiguous rows remain review-required; no broad test-case segment list is copied onto a BR without source evidence.",
        "- Corrections are returned as a versioned sidecar or regenerated catalog; the original delivery remains unchanged.",
        "- Semantic match/partial/no-match decisions remain a separate authorized Test/SME review gate.",
        "",
        "## Limitations",
        "",
        "This feedback was assembled from the v5 BR assessment ledger. At preparation time, that directory did not contain a finalized `summary.json`, `REPORT.md`, or verification manifest. All 6,880 quote checks in the ledger were false. Treat its producer anchor proposals as triage only; do not use the proposed changes as certified coverage.",
        "",
        "The CSV includes blank AI-team response fields (`aiTeamDecision`, `correctedAnchorType`, `correctedSegment`, `correctedElement`, `reviewRationale`) for return annotations.",
    ])
    return "\n".join(lines) + "\n"


def generate(ledger_path, output_dir):
    ledger_path = Path(ledger_path)
    output_dir = Path(output_dir)
    if output_dir.exists():
        raise ValueError(f"Output directory already exists: {output_dir}")
    records = {}
    with ledger_path.open(encoding="utf-8") as stream:
        for line_number, line in enumerate(stream, 1):
            if not line.strip():
                continue
            record = json.loads(line)
            identifier = record.get("id")
            if not identifier or identifier in records:
                raise ValueError(f"Missing or duplicate BR ID at ledger line {line_number}")
            if record.get("candidateDisposition") in COHORTS:
                records[identifier] = feedback_row(record)
    rows = [records[key] for key in sorted(records)]
    expected = {"NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR": 2585, "NOT_MATCHABLE_SEGMENT_UNRESOLVED": 569}
    actual = Counter(row["disposition"] for row in rows)
    if any(actual[key] != value for key, value in expected.items()):
        raise ValueError(f"Unexpected v5 feedback cohort counts: {dict(actual)}")
    if any(row["quoteVerified"] is not False for row in rows):
        raise ValueError("Unexpected verified quote found; review the report assumptions")
    output_dir.mkdir(parents=True)
    csv_path = output_dir / "AI-TEAM-ATL105-ANCHOR-FEEDBACK.csv"
    with csv_path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=FIELDS)
        writer.writeheader()
        writer.writerows(rows)
    md_path = output_dir / "AI-TEAM-ATL105-ANCHOR-FEEDBACK.md"
    md_path.write_text(markdown_report(rows, ledger_path.as_posix()), encoding="utf-8")
    print(json.dumps({"outputDirectory": str(output_dir), "feedbackRows": len(rows),
                      "dispositions": dict(actual), "csv": str(csv_path), "markdown": str(md_path)}, indent=2))


def self_test():
    proposal = {"type": "UNRESOLVED", "category": "Field"}
    action = requested_action("NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR", proposal)
    assert "do not infer an element" in action
    proposal = {"type": "ELEMENT", "segmentCandidates": [{"segment": "100", "lookupEligible": False}]}
    action = requested_action("NOT_MATCHABLE_SEGMENT_UNRESOLVED", proposal)
    assert "Treat listed segment candidates as proposals only" in action
    print("PASS: feedback actions preserve non-element and unresolved-segment evidence boundaries")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--ledger", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not args.ledger or not args.output_directory:
        parser.error("--ledger and --output-directory are required")
    generate(args.ledger, args.output_directory)


if __name__ == "__main__":
    main()