"""Import the versioned Petroleum reference as retrieval evidence and draft lessons."""

import argparse
import hashlib
import importlib.metadata
import json
import re
from pathlib import Path

from pypdf import PdfReader


MODULE = Path(__file__).resolve().parents[1]
PACK = MODULE / "specifications" / "ATL105"
KB = PACK / "docs" / "specs" / "kb" / "petroleum-2025-1"
INPUT = KB / "training-input.json"
PDF = PACK / "docs" / "specs" / "BUYPASS_Platform_Petroleum_Industry_Processing_Specifications__2025-1.pdf"
OUTPUT_NAMES = ("pages.json", "source-index.json", "training-lessons.json", "ingestion-manifest.json")
STATUS = "DRAFT_REVIEW_REQUIRED"


def fingerprint(raw: bytes) -> str:
    return hashlib.sha256(raw).hexdigest()


def json_text(value: dict) -> str:
    return json.dumps(value, indent=2, ensure_ascii=True) + "\n"


def normalized(text: str) -> str:
    return " ".join(text.split())


def extract_source(path: Path, config: dict) -> tuple[list[dict], list[dict]]:
    raw = path.read_bytes()
    if fingerprint(raw) != config["sourceSha256"]:
        raise ValueError("Petroleum PDF fingerprint differs from the registered 2025-1 source")
    reader = PdfReader(path, strict=True)
    if reader.is_encrypted:
        raise ValueError("Encrypted Petroleum source is not supported")
    if len(reader.pages) != config["pageCount"]:
        raise ValueError("Petroleum source page count differs from the registered source")
    pages = []
    for number, page in enumerate(reader.pages, 1):
        text = (page.extract_text() or "").replace("\r\n", "\n").replace("\r", "\n")
        labels = re.findall(r"(?m)^\s*((?:\d+|[A-M])-\d+|Revision History-\d+)\s*$", text)
        pages.append({
            "id": f"PETRO-2025-1-P{number:04}",
            "pdfPage": number,
            "printedPage": labels[-1] if labels else None,
            "text": text,
            "textSha256": fingerprint(text.encode("utf-8")),
            "extractionStatus": "TEXT_EXTRACTED" if text.strip() else "EMPTY_REVIEW_REQUIRED",
        })
    cover = normalized(pages[0]["text"])
    if not all(normalized(s) in cover for s in config["coverMarkers"]):
        raise ValueError("Petroleum cover identity is not confirmed")
    outline = []

    def visit(entries: list, depth: int = 0) -> None:
        for entry in entries:
            if isinstance(entry, list):
                visit(entry, depth + 1)
            else:
                target = reader.get_destination_page_number(entry)
                if target is None or not 0 <= target < len(pages):
                    raise ValueError(f"Invalid PDF outline destination: {entry.title}")
                outline.append({"title": str(entry.title), "pdfPage": target + 1, "depth": depth})

    visit(reader.outline)
    chapters = [x for x in outline if re.match(r"^\d+\.\s", x["title"])]
    if len(chapters) != 17:
        raise ValueError("Expected all 17 Petroleum chapter bookmarks")
    if fingerprint(path.read_bytes()) != config["sourceSha256"]:
        raise ValueError("Petroleum source changed during extraction")
    return pages, outline


def build_lessons(config: dict, pages: list[dict]) -> list[dict]:
    if config["schemaVersion"] != 1 or config["status"] != STATUS or config["version"] != "2025-1":
        raise ValueError("Only draft Petroleum 2025-1 lessons may be imported")
    for question in config["openQuestions"]:
        if question["status"] != "REVIEW_REQUIRED" or not question["owner"] or not question["question"]:
            raise ValueError("Petroleum open questions require an owner and review disposition")
    by_page = {p["pdfPage"]: p for p in pages}
    seen = set()
    lessons = []
    for lesson in config["lessons"]:
        if not lesson["id"] or lesson["id"] in seen:
            raise ValueError("Empty or duplicate Petroleum lesson ID")
        seen.add(lesson["id"])
        if not lesson["statement"].strip() or not lesson["scope"].strip() or not lesson["evidence"]:
            raise ValueError(f"Lesson lacks a statement, scope or evidence: {lesson['id']}")
        resolved = []
        for citation in lesson["evidence"]:
            page = by_page.get(citation["pdfPage"])
            quote = normalized(citation["quote"])
            if not page or not quote or quote not in normalized(page["text"]):
                raise ValueError(f"Unresolved page-local Petroleum quote: {lesson['id']}")
            resolved.append({
                **citation,
                "pageId": page["id"],
                "printedPage": page["printedPage"],
                "textSha256": page["textSha256"],
                "sourceSha256": config["sourceSha256"],
                "scope": "QUOTE_LOCATION_AND_BYTES_NOT_SEMANTIC_APPROVAL",
            })
        lessons.append({**lesson, "status": STATUS, "evidence": resolved})
    if not lessons:
        raise ValueError("No Petroleum draft lessons were supplied")
    return lessons


def build() -> dict[str, str]:
    raw_config = INPUT.read_bytes()
    config = json.loads(raw_config)
    pages, outline = extract_source(PDF, config)
    lessons = build_lessons(config, pages)
    source = {
        "id": "BUYPASS-PETROLEUM-2025-1",
        "title": config["title"],
        "version": config["version"],
        "releaseDate": config["releaseDate"],
        "path": PDF.relative_to(PACK).as_posix(),
        "sha256": config["sourceSha256"],
    }
    gates = {
        "status": STATUS, "certified": False, "executionReady": False,
        "baselineChanged": False, "modelWeightsChanged": False,
        "independentBrCount": 0, "testScenarioCount": 0,
        "testCaseCount": 0, "testDataCount": 0, "executedCaseCount": 0,
    }
    outputs = {
        "pages.json": json_text({
            "schemaVersion": 1, "source": source,
            "usage": "FULL_DOCUMENT_RETRIEVAL_EVIDENCE_NOT_TRAINING_TRUTH",
            "pages": pages,
        }),
        "source-index.json": json_text({
            "schemaVersion": 1, "source": source,
            "usage": "PDF_BOOKMARKS_NOT_VALIDATED_RULES",
            "outline": outline,
        }),
        "training-lessons.json": json_text({
            "schemaVersion": 1, "source": source, **gates,
            "authority": "SUPPLEMENTAL_SOURCE_DRAFT_NOT_EXECUTABLE_ORACLE",
            "atl105Compatibility": {
                "trainedBaseline": "2026-3", "availableRelease": "2026-4",
                "crossVersionReconciliation": "REVIEW_REQUIRED",
                "appendixIdentity": "DOCUMENT_SCOPED_NEVER_ALIAS_PETROLEUM_APPENDICES_TO_ATL105",
            },
            "lessons": lessons,
            "openQuestions": config["openQuestions"],
        }),
    }
    outputs["ingestion-manifest.json"] = json_text({
        "schemaVersion": 1, "source": source, **gates,
        "pageCount": len(pages),
        "textPageCount": sum(p["extractionStatus"] == "TEXT_EXTRACTED" for p in pages),
        "emptyPagesRequiringReview": [p["pdfPage"] for p in pages if not p["text"].strip()],
        "bookmarkCount": len(outline), "draftLessonCount": len(lessons),
        "chapterCount": sum(bool(re.match(r"^\d+\.\s", x["title"])) for x in outline),
        "appendixCount": sum(bool(re.match(r"^Appendix [A-M]\.", x["title"])) for x in outline),
        "extractor": {"name": "pypdf", "version": importlib.metadata.version("pypdf")},
        "inputSha256": fingerprint(raw_config),
        "generatorSha256": fingerprint(Path(__file__).read_bytes()),
        "outputs": {name: fingerprint(text.encode("utf-8")) for name, text in outputs.items()},
        "limitations": [
            "Text extraction and bookmark coverage do not prove preservation of diagram or table semantics.",
            "Only explicitly curated, quoted statements enter the draft lesson list.",
            "No source conflict, fleet eligibility or monetary outcome is resolved by ingestion.",
            "Petroleum 2025-1 does not replace ATL105 2026-3 or the unreconciled 2026-4 release.",
            "No canonical BR chains, rule counts, execution gates or SME approvals are promoted.",
        ],
    })
    return outputs


def check_outputs(outputs: dict[str, str], folder: Path) -> None:
    stale = [name for name, expected in outputs.items()
             if not (folder / name).is_file()
             or (folder / name).read_text(encoding="utf-8") != expected]
    if stale:
        raise ValueError("Missing or stale Petroleum ingestion outputs: " + ", ".join(stale))


def search_pages(query: str, pages: list[dict], limit: int = 5) -> list[dict]:
    terms = set(re.findall(r"[a-z0-9]+", query.lower()))
    if not terms:
        raise ValueError("Petroleum query must include a word or number")
    ranked = []
    for page in pages:
        text = page["text"]
        words = re.findall(r"[a-z0-9]+", text.lower())
        matched = terms & set(words)
        if matched == terms:
            score = sum(words.count(t) for t in terms)
            position = min(text.lower().find(t) for t in terms)
            ranked.append((score, page["pdfPage"], {
                "pdfPage": page["pdfPage"], "printedPage": page["printedPage"],
                "pageId": page["id"], "textSha256": page["textSha256"],
                "excerpt": text[max(0, position - 120):position + 1000],
                "status": "RETRIEVAL_CANDIDATE_NOT_VALIDATED_RULE",
            }))
    return [item for _, _, item in sorted(ranked, key=lambda x: (-x[0], x[1]))[:limit]]


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    action = parser.add_mutually_exclusive_group()
    action.add_argument("--check", action="store_true", help="Re-extract and compare all evidence; writes nothing")
    action.add_argument("--query", help="Read-only lexical search over verified page evidence")
    args = parser.parse_args()
    if args.query is not None and not re.search(r"[a-z0-9]", args.query.lower()):
        parser.error("Petroleum query must include a word or number")
    outputs = build()
    if args.check or args.query is not None:
        check_outputs(outputs, KB)
    else:
        for name, text in outputs.items():
            (KB / name).write_text(text, encoding="utf-8", newline="\n")
        check_outputs(outputs, KB)
    if args.query is not None:
        pages = json.loads(outputs["pages.json"])["pages"]
        print(json_text({"query": args.query, "results": search_pages(args.query, pages)}))
    else:
        manifest = json.loads(outputs["ingestion-manifest.json"])
        print(f"Petroleum 2025-1: {manifest['pageCount']} pages; "
              f"{manifest['draftLessonCount']} draft lessons; "
              f"{'verified (read-only)' if args.check else 'built and verified'}. "
              "No BR/TC/TD or execution gate promoted.")


if __name__ == "__main__":
    main()
