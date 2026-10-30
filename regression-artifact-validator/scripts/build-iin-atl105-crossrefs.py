"""Build a source-verified map of the ATL105 referral to the IIN Reference Manual."""

import argparse
import hashlib
import json
import re
from pathlib import Path


MODULE = Path(__file__).resolve().parents[1]
PACK = MODULE / "specifications" / "ATL105"
SPECS = PACK / "docs" / "specs"
KB = SPECS / "kb" / "iin-2024-1"
OUTPUTS = ("atl105-iin-cross-references.json", "atl105-iin-cross-references.md")

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
    return [{
        "id": "ATL105-IIN-REF-E2",
        "location": "Element 2, Account Number",
        "anchor": "Number: 2 Name: Account Number",
        "quote": "Credit, debit, and EBT cards: Please refer to the most recent version of BUYPASS Platform Issuer Identification Number (IIN) Reference Manual.",
        "expectedPrintedPage": "13-25",
        "expectedPdfPages": {"2026-3": 358, "2026-4": 365},
        "elementNumber": "2",
        "condition": "CREDIT_DEBIT_EBT_ACCOUNT_NUMBER_IIN_LOOKUP",
        "anchorPageOffset": -2,
        "trainingNote": "This referral covers credit, debit and EBT cards only; the same element directs fleet cards to the Petroleum Industry Processing Specification instead.",
    }]


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
            "id": "BUYPASS-IIN-2024-1",
            "title": "BUYPASS Platform Issuer Identification Number (IIN) Reference Manual",
            "availableVersion": "2024-1",
            "localPath": "docs/specs/BUYPASS_Platform_IIN_Reference_Manual_06072024_2024-1.pdf",
        },
        "trainingInterpretation": {
            "rule": "ATL105 retains message-format and field representation rules; the IIN Reference Manual supplies the separately versioned Card Type ID, IIN Length, PAN Length and PAN range catalog for credit, debit and EBT cards where referenced.",
            "constraints": [
                "Apply the IIN catalog only to credit, debit and EBT cards; fleet cards are referred to the Petroleum specification instead by the same element.",
                "Do not replace ATL105 field lengths, encoding, segment placement, transaction format or other message rules.",
                "The word 'most recent' does not itself establish that the local 2024-1 IIN manual is the currently governing release.",
                "All entries remain draft and review-required; no source conflict, SME approval, execution readiness or model-weight training is implied.",
            ],
        },
        "sourceFingerprints": source_fingerprints,
        "referenceCountPerRelease": len(refs),
        "references": trained_refs,
    }
    json_output = json.dumps(record, indent=2, ensure_ascii=True) + "\n"
    report = [
        "# ATL105 reference to the IIN Reference Manual",
        "",
        "Generated by `scripts/build-iin-atl105-crossrefs.py`; do not edit by hand.",
        "",
        "Status: `DRAFT_REVIEW_REQUIRED`. ATL105 2026-3 and 2026-4 both point to the separately versioned "
        "BUYPASS IIN Reference Manual for credit, debit and EBT card account-number validation. This is a "
        "locator and training cue, not SME approval, execution evidence, or model-weight training.",
        "",
        "The single anchor is Element 2, Account Number, printed page 13-25. The same element separately "
        "directs fleet cards to the Petroleum Industry Processing Specification (see "
        "`kb/petroleum-2025-1/atl105-petroleum-cross-references.md`); the two referrals do not overlap in "
        "card-type scope.",
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
        raise ValueError("Missing or stale ATL105 IIN cross-reference outputs: " + ", ".join(stale))


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
    print(f"ATL105 IIN cross-references: {len(definitions())} per release across 2026-3 and 2026-4; {action}.")


if __name__ == "__main__":
    main()
