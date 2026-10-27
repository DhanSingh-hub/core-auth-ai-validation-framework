"""Build a source-verified map of ATL105 referrals to Petroleum fleet requirements."""

import argparse
import hashlib
import json
import re
from pathlib import Path


MODULE = Path(__file__).resolve().parents[1]
PACK = MODULE / "specifications" / "ATL105"
SPECS = PACK / "docs" / "specs"
KB = SPECS / "kb" / "petroleum-2025-1"
OUTPUTS = ("atl105-petroleum-cross-references.json", "atl105-petroleum-cross-references.md")

SOURCES = {
    "2026-3": {
        "text": SPECS / "extracted_text.txt",
        "pdf": SPECS / "BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf",
    },
    "2026-4": {
        "text": SPECS / "extracted_text_new.txt",
        "pdf": SPECS / "BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf",
    },
}

BASE_FIELDS = [
    ("E31", "31", "Driver/Identification", "374", "381"),
    ("E40", "40", "Fleet Employee Number", "378", "385"),
    ("E47", "47", "Job Number", "380", "387"),
    ("E64", "64", "Odometer", "389", "396"),
    ("E108", "108", "Vehicle Number", "414", "421"),
    ("E158", "158", "License #", "453", "460"),
    ("E159", "159", "Job ID", "454", "461"),
    ("E160", "160", "Department #", "454", "461"),
    ("E161", "161", "Customer Data", "454", "461"),
    ("E162", "162", "User ID", "455", "462"),
    ("E163", "163", "Vehicle ID#", "455", "462"),
]


def sha256(raw: bytes) -> str:
    return hashlib.sha256(raw).hexdigest()


def normalized(text: str) -> str:
    return " ".join(text.split()).replace("®", "").casefold()


def pages(text: str) -> list[dict]:
    result = []
    current = None
    for line_number, line in enumerate(text.splitlines(), 1):
        marker = re.fullmatch(r"\s*--- PAGE (\d+) ---\s*", line)
        if marker:
            if current is not None:
                result.append(current)
            current = {"pdfPage": int(marker.group(1)), "startLine": line_number, "lines": []}
        elif current is not None:
            current["lines"].append(line)
    if current is not None:
        result.append(current)
    for page in result:
        page["text"] = "\n".join(page["lines"])
        labels = re.findall(r"(?m)^\s*((?:\d+|[A-M])-\d+)\s*$", page["text"])
        page["printedPage"] = labels[-1] if labels else None
    return result


def definitions() -> list[dict]:
    refs = [{
        "id": "ATL105-PETRO-REF-10-6",
        "location": "Section 10.6, Fleet Card Processing Requirements",
        "anchor": "10.6 Fleet Card Processing Requirements",
        "quote": "Please refer to the most recent version of the following documents for fleet card processing requirements:",
        "documentQuote": "BUYPASS Platform Petroleum Industry Processing Specifications",
        "expectedPrintedPage": "10-44",
        "expectedPdfPages": {"2026-3": 121, "2026-4": 123},
        "elementNumber": None,
        "condition": "FLEET_PROCESSING",
        "trainingNote": "Use the separately versioned Petroleum specification for fleet requirements; do not treat it as replacing ATL105 message-format rules.",
    }, {
        "id": "ATL105-PETRO-REF-E2",
        "location": "Element 2, Account Number",
        "anchor": "Number: 2 Name: Account Number",
        "quote": "Fleet cards: Please refer to the most recent version of BUYPASS Platform Petroleum Industry Processing Specifications.",
        "expectedPrintedPage": "13-25",
        "expectedPdfPages": {"2026-3": 358, "2026-4": 365},
        "elementNumber": "2",
        "condition": "FLEET_CARD_ACCOUNT_NUMBER_ONLY",
        "anchorPageOffset": -2,
        "trainingNote": "This referral is only for fleet cards; the same element directs credit, debit and EBT to the IIN Reference Manual.",
    }]
    field_quote = (
        "Please refer to the latest release of BUYPASS Platform Petroleum Industry Processing "
        "Specifications for issuer requirements."
    )
    for suffix, number, name, page3, page4 in BASE_FIELDS:
        refs.append({
            "id": f"ATL105-PETRO-REF-{suffix}",
            "location": f"Element {number}, {name}",
            "anchor": f"Number: {number} Name: {name}",
            "quote": field_quote,
            "expectedPrintedPage": {
                "31": "13-41", "40": "13-45", "47": "13-47", "64": "13-56",
                "108": "13-81", "158": "13-120", "159": "13-121", "160": "13-121",
                "161": "13-121", "162": "13-122", "163": "13-122",
            }[number],
            "expectedPdfPages": {"2026-3": int(page3), "2026-4": int(page4)},
            "elementNumber": number,
            "condition": "ISSUER_REQUIREMENT_WHEN_FIELD_APPLIES",
            "trainingNote": "Keep ATL105 field representation/length constraints distinct from Petroleum issuer-specific prompting and applicability. Verify the element's own conditional Segment 101 wording before requiring it.",
        })
    # Element 31 uses a specific printed page label; other element locations are
    # resolved from their source pages at build time.
    return refs


def find_reference_page(source_pages: list[dict], ref: dict, version: str) -> dict:
    expected_page = ref["expectedPdfPages"][version]
    page_by_number = {page["pdfPage"]: page for page in source_pages}
    page = page_by_number.get(expected_page)
    anchor_page = page_by_number.get(expected_page + ref.get("anchorPageOffset", 0))
    matches = [page] if (
        page
        and anchor_page
        and normalized(ref["anchor"]) in normalized(anchor_page["text"])
        and normalized(ref["quote"]) in normalized(page["text"])
        and (not ref.get("documentQuote")
             or normalized(ref["documentQuote"]) in normalized(page["text"]))
    ) else []
    if len(matches) != 1:
        raise ValueError(f"{ref['id']} did not resolve exactly once in ATL105 {version} page {expected_page}")
    page = matches[0]
    if ref["expectedPrintedPage"] and page["printedPage"] != ref["expectedPrintedPage"]:
        raise ValueError(
            f"{ref['id']} expected printed page {ref['expectedPrintedPage']}, got {page['printedPage']}"
        )
    return page


def build() -> dict[str, str]:
    refs = definitions()
    release_evidence = {}
    source_fingerprints = {}
    for version, files in SOURCES.items():
        raw_text = files["text"].read_bytes()
        raw_pdf = files["pdf"].read_bytes()
        extracted = raw_text.decode("utf-8")
        source_pages = pages(extracted)
        if not source_pages:
            raise ValueError(f"ATL105 {version} extracted text has no physical page markers")
        source_fingerprints[version] = {
            "pdfPath": files["pdf"].relative_to(PACK).as_posix(),
            "pdfSha256": sha256(raw_pdf),
            "extractedTextPath": files["text"].relative_to(PACK).as_posix(),
            "extractedTextSha256": sha256(raw_text),
        }
        release_evidence[version] = []
        for ref in refs:
            page = find_reference_page(source_pages, ref, version)
            release_evidence[version].append({
                "referenceId": ref["id"],
                "physicalPdfPage": page["pdfPage"],
                "printedPage": page["printedPage"],
                "pageTextSha256": sha256(page["text"].encode("utf-8")),
                "sourceQuote": ref["quote"],
                **({"documentQuote": ref["documentQuote"]} if ref.get("documentQuote") else {}),
            })

    trained_refs = [
        {
            **ref,
            "status": "DRAFT_REVIEW_REQUIRED",
            "atl105Evidence": {
                version: next(e for e in release_evidence[version] if e["referenceId"] == ref["id"])
                for version in SOURCES
            },
        }
        for ref in refs
    ]
    record = {
        "schemaVersion": 1,
        "status": "DRAFT_REVIEW_REQUIRED",
        "authority": "ATL105_REFERENCE_LOCATOR_AND_TRAINING_CUE_NOT_AN_APPROVED_REQUIREMENT_ORACLE",
        "referenceDocument": {
            "id": "BUYPASS-PETROLEUM-INDUSTRY-PROCESSING-SPECIFICATIONS",
            "title": "BUYPASS Platform Petroleum Industry Processing Specifications",
            "availableVersion": "2025-1",
            "localPath": "docs/specs/BUYPASS_Platform_Petroleum_Industry_Processing_Specifications__2025-1.pdf",
        },
        "trainingInterpretation": {
            "rule": "ATL105 retains message-format and field representation rules; the Petroleum document supplies separately versioned fleet-processing and issuer-specific requirements where referenced.",
            "constraints": [
                "Apply Petroleum requirements only to the applicable fleet program and issuer context.",
                "Do not replace ATL105 field lengths, encoding, segment placement, transaction format or other message rules.",
                "Element 2's Petroleum referral applies to fleet cards, not the credit/debit/EBT IIN referral.",
                "Issuer-required Segment 101 fields remain conditional unless their source trigger is satisfied.",
                "The word 'most recent' does not itself establish that local 2025-1 is the currently governing release.",
                "All entries remain draft and review-required; no source conflict, SME approval, execution readiness or model-weight training is implied.",
            ],
        },
        "sourceFingerprints": source_fingerprints,
        "referenceCountPerRelease": len(refs),
        "references": trained_refs,
    }
    json_output = json.dumps(record, indent=2, ensure_ascii=True) + "\n"
    report = [
        "# ATL105 references to the Petroleum fleet specification",
        "",
        "Generated by `scripts/build-petroleum-atl105-crossrefs.py`; do not edit by hand.",
        "",
        "Status: `DRAFT_REVIEW_REQUIRED`. ATL105 2026-3 and 2026-4 both point to the separately versioned "
        "BUYPASS Petroleum Industry Processing Specifications. These are locators and training cues, not "
        "SME approval, execution evidence, or model-weight training.",
        "",
        "The direct global reference is section 10.6, printed page 10-44. Additional element-level references "
        "locate fleet-specific account-number and issuer-rule details. Element 2 distinguishes fleet cards "
        "from credit/debit/EBT IIN rules. Preserve ATL105's format and field constraints; apply Petroleum "
        "requirements only within fleet and issuer applicability.",
        "",
        "| ATL105 location | 2026-3 physical / printed page | 2026-4 physical / printed page | Rule cue |",
        "|---|---:|---:|---|",
    ]
    for ref in trained_refs:
        locations = [
            f"{ref['atl105Evidence'][version]['physicalPdfPage']} / {ref['atl105Evidence'][version]['printedPage']}"
            for version in SOURCES
        ]
        report.append(f"| {ref['location']} | {locations[0]} | {locations[1]} | {ref['condition']} |")
    report += [
        "",
        "## Application notes",
        "",
    ]
    report.extend(f"- {item}" for item in record["trainingInterpretation"]["constraints"])
    report += [
        "",
        "Both source releases are fingerprinted in the JSON record. Physical PDF page numbers and printed ATL105 "
        "page labels are reported separately.",
        "",
    ]
    return {
        OUTPUTS[0]: json_output,
        OUTPUTS[1]: "\n".join(report),
    }


def check_outputs(outputs: dict[str, str], folder: Path = KB) -> None:
    stale = [
        name for name, expected in outputs.items()
        if not (folder / name).is_file()
        or (folder / name).read_text(encoding="utf-8") != expected
    ]
    if stale:
        raise ValueError("Missing or stale ATL105 Petroleum cross-reference outputs: " + ", ".join(stale))


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true", help="Rebuild in memory and compare without writing")
    args = parser.parse_args()
    outputs = build()
    if args.check:
        check_outputs(outputs)
        action = "verified (read-only)"
    else:
        for name, text in outputs.items():
            (KB / name).write_text(text, encoding="utf-8", newline="\n")
        check_outputs(outputs)
        action = "built and verified"
    print(f"ATL105 Petroleum cross-references: {len(definitions())} per release across 2026-3 and 2026-4; {action}.")


if __name__ == "__main__":
    main()
