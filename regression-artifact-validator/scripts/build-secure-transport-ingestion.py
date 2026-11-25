"""Build the local-only Secure Transport chapter/phase split and its tracked manifest.

ATL105 2026-4 Section 3.3 (Telecommunications Requirements, PDF p.49, printed p.3-2; the same text is in 2026-3)
sends integrators to "the Secure Transport section on the First Data Web site" for Internet support information.
The Secure Transport documents supplied on 2026-10-28 are First Data/Fiserv confidential and this repository is
public, so:

* the supplied files, their page-marked text, the verbatim chapter/phase split and the rendered diagram pages stay
  in specifications/ATL105/docs/specs/secure-transport/, which is git-ignored;
* only the manifest is tracked: identities, SHA-256 hashes, page ranges, character counts and chapter hashes, with
  no source text (specifications/ATL105/docs/specs/kb/secure-transport/secure-transport-source-manifest.json).

The split is complete by construction and checked fail-fast: every supplied file must match its pinned SHA-256,
every chapter must start at its pinned heading on its pinned page, and for every document the chapter texts must
concatenate to exactly the full extracted text, so no page or character can be lost between chapters.

Requirements: pypdf and pypdfium2; pywin32 and Microsoft Word for the two .doc certification scripts.
Usage, from the validator module:  python scripts/build-secure-transport-ingestion.py
"""
import bisect
import datetime
import hashlib
import json
import os
import re
import shutil
import sys
import tempfile
from collections import Counter
from dataclasses import dataclass
from pathlib import Path

HERE = Path(__file__).resolve().parent
MODULE = HERE.parent
PACK = MODULE / "specifications" / "ATL105"
SPECS = PACK / "docs" / "specs"
LOCAL = SPECS / "secure-transport"
SOURCE = LOCAL / "source"
TEXT = LOCAL / "text"
CHAPTERS = LOCAL / "chapters"
IMAGES = LOCAL / "images"
MANIFEST = SPECS / "kb" / "secure-transport" / "secure-transport-source-manifest.json"
RECORD = PACK / "docs" / "test-validation-strategy" / "SecureTransport-Training-Knowledge.md"
INGESTED_ON = "2026-10-28"
MAX_PATH = 255
PAGE_MARK = re.compile(r"^--- PAGE (\d+) ---$", re.M)
LINE = re.compile(r"^.*$", re.M)
# A heading preceded by at most this many non-empty lines (the running title and page number) opens its page.
RUNNING_HEADER_LINES = 2
BEGIN, END = "<!-- BEGIN VERBATIM -->", "<!-- END VERBATIM -->"


@dataclass(frozen=True)
class Chapter:
    seq: str
    title: str
    page: int | None
    anchor: str | None


@dataclass(frozen=True)
class Doc:
    id: str
    file: str
    supplied: str
    kind: str
    title: str
    version: str
    date: str
    publisher: str
    family: str
    sha256: str
    pages: int | None
    chapters: tuple


def ch(seq, title, page=None, anchor=None):
    return Chapter(seq, title, page, anchor)


GUIDE = "Secure_Transport_Guide/Secure_Transport_Guide/Secure_Transport_Guide/"
DOCS = (
    Doc("ST-UNIX-API", "SecureTransport-UnixSrc-API.pdf", "SecureTransport-UnixSrc-API.pdf", "PDF",
        "Secure Transport Unix Source Code API Guide", "API Version 4.6.3, Document Revision 1.08", "2015-08-10",
        "First Data Corporation", "SECURE_TRANSPORT_GENERIC",
        "30A3E1D109B761029A7D8985C54F1664848CFC0A77CBA7F2BBE968FDECCE0069", 64, (
            ch("00", "Cover and copyright", 1),
            ch("01", "Table of contents", 3, "TABLE OF CONTENTS"),
            ch("02", "Preface", 7, "PPREFACE"),
            ch("03", "Chapter 1 - Secure Transport Network", 9, "SECURE TRANSPORT NETWORK 1"),
            ch("04", "Chapter 6 - Compiling the Source Code", 11, "COMPILING THE SOURCE CODE 6"),
            ch("05", "Chapter 2 - Function Classification", 15, "FUNCTION CLASSIFICATION 2"),
            ch("06", "Chapter 3 - Obtaining and Saving a DID", 19, "OBTAINING AND SAVING A DID 3"),
            ch("07", "Chapter 4 - Simple Transactions", 25, "SIMPLE TRANSACTIONS 4"),
            ch("08", "Chapter 5 - Session Transactions", 31, "SESSION TRANSACTIONS 5"),
            ch("09", "Chapter 7 - Function Reference", 39, "FUNCTION REFERENCE 7"),
            ch("10", "Chapter 8 - Data Structure Reference", 49, "DATA STRUCTURE REFERENCE 8"),
            ch("11", "Appendix A - Glossary", 53, "GLOSSARY A"),
            ch("12", "Appendix B - Trusted Root Certificates", 55, "TRUSTED ROOT CERTIFICATES B"),
            ch("13", "Appendix E - Error Management", 61, "ERROR MANAGEMENT E"))),
    Doc("ST-XML-API", "SecureTransport-XML-API.pdf", "SecureTransport-XML-API.pdf", "PDF",
        "Secure Transport XML Interface Reference Guide", "API Version 3.3, Document Revision 1.14", "2022-06",
        "Fiserv, Inc.", "SECURE_TRANSPORT_GENERIC",
        "CA70142C5A65C31A6D393146B193FF8C69454E24FFC5EE97FB690CB8C387BD52", 77, (
            ch("00", "Cover and copyright", 1),
            ch("01", "Table of contents", 3, "Table of Contents"),
            ch("02", "Preface", 6, "Preface"),
            ch("03", "Secure Transport Network", 7, "Secure Transport Network"),
            ch("04", "Getting Started", 8, "Getting Started"),
            ch("05", "Sample XML Request-Response", 11, "Sample XML Request-Response"),
            ch("06", "Performing SRS", 19, "Performing SRS"),
            ch("07", "Service Discovery", 30, "Service Discovery"),
            ch("08", "Ping", 36, "Ping"),
            ch("09", "Simple Transactions", 40, "Simple Transactions"),
            ch("10", "Session Transactions", 45, "Session Transactions"),
            ch("11", "URL Management", 56, "URL Management"),
            ch("12", "Element Reference", 58, "Element Reference"),
            ch("13", "Glossary", 68, "Glossary"),
            ch("14", "Error Management", 69, "Error Management"),
            ch("15", "Advanced Topics", 74, "Advanced Topics"),
            ch("16", "Character Translation Table", 76, "Character Translation Table"),
            ch("17", "Back cover (Fiserv contact)", 77))),
    Doc("ST-RC-GUIDE", "SecureTransport-RapidConnect-Guide.pdf", GUIDE + "SecureTransport-RapidConnect-Guide.pdf",
        "PDF", "Datawire Secure Transport XML and SOAP Interfaces for Rapid Connect Reference Guide",
        "Document Version 1.26", "2022-04", "Fiserv, Inc.", "RAPID_CONNECT",
        "178D986E3B2369F74C9F6B774CF37EF6A9DE2340B61762CE03EE0305D5FCCE96", 103, (
            ch("00", "Cover and copyright", 1),
            ch("01", "Revision history", 3, "Revision History"),
            ch("02", "Table of contents", 4, "Table of Contents"),
            ch("03", "Before You Begin", 7, "Before You Begin"),
            ch("04", "The Datawire Secure Transport Network", 9, "The Datawire Secure Transport Network"),
            ch("05", "Before Sending Transactions", 11, "Before Sending Transactions"),
            ch("06-01", "The XML Transaction - overview and protocol", 12, "The XML Transaction"),
            ch("06-02", "The XML Transaction - request components", 14, "The XML Request Components"),
            ch("06-03", "The XML Transaction - sample request, XML_Escape encoding", 21,
               "Sample XML Request with XML_Escape Encoding"),
            ch("06-04", "The XML Transaction - sample request, Cdata encoding", 23,
               "Sample XML Request with Cdata Encoding"),
            ch("06-05", "The XML Transaction - response components", 25, "The XML Response Components"),
            ch("06-06", "The XML Transaction - sample response, XML_Escape encoding", 31,
               "Sample XML Response with XML_Escape Encoding"),
            ch("06-07", "The XML Transaction - sample response, Cdata encoding", 34,
               "Sample XML Response with Cdata Encoding"),
            ch("06-08", "The XML Transaction - error response components", 37, "Error Response Components"),
            ch("06-09", "The XML Transaction - sample error response with a return code", 40,
               "Sample XML Error Response with a Return Code"),
            ch("06-10", "The XML Transaction - sample error response without a return code", 46,
               "Sample XML Error Response without a Return Code"),
            ch("07-01", "The SOAP Transaction - overview and protocol", 48, "The Soap Transaction"),
            ch("07-02", "The SOAP Transaction - request components", 50, "The Soap Request Components"),
            ch("07-03", "The SOAP Transaction - sample request, XML_Escape encoding", 56,
               "Sample Soap Request with XML_Escape Encoding"),
            ch("07-04", "The SOAP Transaction - sample request, Cdata encoding", 58,
               "Sample Soap Request with Cdata Encoding"),
            ch("07-05", "The SOAP Transaction - response components", 61, "The Soap Response Components"),
            ch("07-06", "The SOAP Transaction - sample response, XML_Escape encoding", 67,
               "Sample Response with XML_Escape Encoding"),
            ch("07-07", "The SOAP Transaction - sample response, Cdata encoding", 71,
               "Sample Response with Cdata Encoding"),
            ch("07-08", "The SOAP Transaction - error response components", 74, "SOAP Error Response Components"),
            ch("07-09", "The SOAP Transaction - sample error response, XML_Escape encoding", 78,
               "Sample SOAP Error Response with XML_ Escape Encoding"),
            ch("07-10", "The SOAP Transaction - sample error response, Cdata encoding", 81,
               "Sample SOAP Error Response with Cdata Encoding"),
            ch("07-11", "The SOAP Transaction - sample error response without a return code", 84,
               "Sample SOAP Error Response without a Return Code"),
            ch("08", "Error Management", 86, "Error Management"),
            ch("09-01", "SOAP Interface Code Samples - CSharp sample", 90, "SOAP Interface Code Samples"),
            ch("09-02", "SOAP Interface Code Samples - VB.NET sample", 93, "VB Net Sample"),
            ch("09-03", "SOAP Interface Code Samples - Java sample", 96, "JAVA Sample"),
            ch("09-04", "SOAP Interface Code Samples - PHP sample", 99, "PHP Sample"),
            ch("10", "Back cover (Fiserv contact)", 103))),
    Doc("ST-RC-SRS", "SecureTransport-SRS-RapidConnect.pdf", GUIDE + "SecureTransport-SRS-RapidConnect.pdf", "PDF",
        "Datawire Secure Transport XML and SOAP SRS Interfaces for Rapid Connect Reference Guide",
        "Document Version 1.9", "2022-04-26", "Fiserv, Inc.", "RAPID_CONNECT",
        "35C1C22F23DEDF0101FFF30578080D7B813614B657F1FAE22625C0B2B9B1B648", 35, (
            ch("00", "Cover and copyright", 1),
            ch("01", "Revision history", 3, "Revision History"),
            ch("02", "Table of contents", 4, "Table of Contents"),
            ch("03", "Before You Begin", 6, "Before You Begin"),
            ch("04", "SRS Process Overview", 8, "SRS Process Overview"),
            ch("05", "SRS Service Discovery", 9, "SRS Service Discovery"),
            ch("06", "SRS Processing Using SOAP", 12, "SRS Processing Using SOAP"),
            ch("07", "SRS Processing Using XML", 22, "SRS Processing Using XML"),
            ch("08", "Error Management", 31, "Error Management"),
            ch("09", "Back cover (Fiserv contact)", 35))),
    Doc("ST-RC-SRS-STEPS", "Step By Step SRS Process for Secure Transport Rapid Connect.pdf",
        GUIDE + "Step By Step SRS Process for Secure Transport Rapid Connect.pdf", "PDF",
        "Step By Step Self-Registration (SRS) Process for Datawire Secure Transport and Rapid Connect",
        "Document Revision 1.1", "2022-02-09", "Fiserv, Inc.", "RAPID_CONNECT",
        "47C424986FB96EB4C164E3D634E05F25B6543E4DF6D5396494AC3C7FE684D317", 3, (
            ch("00", "Cover", 1),
            ch("01", "Step-by-step SRS process", 2),
            ch("02", "Back cover (Fiserv contact)", 3))),
    Doc("ST-RC-PARAMS", "Datawire Parameter Guidelines for Rapid Connect.pdf",
        GUIDE + "Datawire Parameter Guidelines for Rapid Connect.pdf", "PDF",
        "Datawire Parameter Guidelines for Rapid Connect", "Document Revision 1.1", "2022-02-09", "Fiserv, Inc.",
        "RAPID_CONNECT", "17163A3D5AE3DFCF52555C0119FFD4B80005D31A92E0B0A85A19A13B3010A1A9", 4, (
            ch("00", "Cover", 1),
            ch("01", "Datawire parameter guidelines", 2),
            ch("02", "Datawire/Secure Transport connectivity requirements", 3,
               "Datawire/Secure Transport Connectivity Requirements"),
            ch("03", "Back cover (Fiserv contact)", 4))),
    Doc("ST-RC-TLS", "Required_TLS_Ciphers_Secure_Transport_RapidConnect.pdf",
        GUIDE + "Required_TLS_Ciphers_Secure_Transport_RapidConnect.pdf", "PDF",
        "Required TLS Ciphers to Connect to Datawire Secure Transport for Rapid Connect", "Document Revision 1.1",
        "2022-02-09", "Fiserv, Inc.", "RAPID_CONNECT",
        "1E5387C225C0A7D7B3BF3A5E19A8A6DDA91C87A7183CCBCEB73EFCE9FECE6987", 3, (
            ch("00", "Cover", 1),
            ch("01", "Required TLS ciphers", 2),
            ch("02", "Back cover (Fiserv contact)", 3))),
    Doc("ST-RC-ROOTCA", "Required_Root_CA_Certificates_SecureTransport_RapidConnect.pdf",
        GUIDE + "Required_Root_CA_Certificates_SecureTransport_RapidConnect.pdf", "PDF",
        "Required Root CA Certificates to Connect to Datawire Secure Transport Network for Rapid Connect",
        "Document Revision 1.1", "2022-02-09", "Fiserv, Inc.", "RAPID_CONNECT",
        "0F069615C8AB772FC213DD018EB57ECE385125C7F2B50A6A16236C119D014E26", 3, (
            ch("00", "Cover", 1),
            ch("01", "Required root CA certificates", 2),
            ch("02", "Back cover (Fiserv contact)", 3))),
    Doc("ST-RC-CERT-FORM", "Datawire Compliance Test Form for Rapid Connect.doc",
        GUIDE + "Datawire Certification Script/Datawire Compliance Test Form for Rapid Connect.doc", "DOC",
        "Datawire (DW) / Secure Transport (ST) Compliance Test Form for Rapid Connect", "Version 3.4.4",
        "2021-12-14", "Fiserv, Inc. (Datawire)", "RAPID_CONNECT",
        "09083628C8A7E9958C6B23A98548DF2CE51A3CD4656FFDFA2EBD32728266457B", None, (
            ch("00", "Title, version and introduction"),
            ch("01", "Section 1 - Vendor, gateway and merchant information", None,
               "Section 1: Vendor / Gateway / Merchant Specific Information"),
            ch("02", "Section 2 - Application information", None, "Section 2: Application Information"),
            ch("03", "Section 3 - Credentials used for testing", None, "Section 3: Credentials Used For Testing"),
            ch("04", "Section 4 - Connectivity parameter configuration", None,
               "Section 4 - Secure Transport Configuration of Connectivity Parameters"),
            ch("05", "Section 5 - Self-Registration (SRS)", None, "Section 5 \u2013 Self-Registration (SRS)"),
            ch("06", "Section 6 - Self-tests and proofs", None, "Section 6 \u2013 Self-Tests and Proofs"),
            ch("07", "Section 7 - Error management", None, "Section 7 \u2013 Error Management"),
            ch("08", "Part 2 - Compliance test, audio portion", None,
               "Part 2 - Secure Transport Compliance Test \u2013 Audio Portion"),
            ch("09", "Compliance notice", None, "Compliance Notice"))),
    Doc("ST-RC-RECERT", "Datawire RapidConnect Re-Certification Compliance Script.doc",
        GUIDE + "Datawire Certification Script/Datawire RapidConnect Re-Certification Compliance Script.doc", "DOC",
        "Datawire Rapid Connect Re-Certification Compliance Script", "Version 1.4", "2021-12-14",
        "Fiserv, Inc. (Datawire)", "RAPID_CONNECT",
        "679FA6E62C95732B17CF181A8DCD2577619CCED42D55662B3B509713CAD31DBF", None, (
            ch("00", "Title and introduction"),
            ch("01", "Part 1 - Vendor, gateway and merchant information", None, "Part 1: Vendor/Gateway/Merchant Info"),
            ch("02", "Part 2 - Product information", None, "Part 2: Product Info"),
            ch("03", "Part 3 - Test credentials", None, "Part 3: Test Credentials"),
            ch("04", "Part 4 - Datawire session transaction support", None,
               "Part 4: Datawire Session Transaction Support"),
            ch("05", "Part 5 - Security, TLS certificates and Datawire interactions", None,
               "Part 5: Security, TLS Certificates, and Datawire Interactions"))),
    Doc("ST-RC-XSD-RCXML", "rcxml.xsd", GUIDE + "Schema/rcxml.xsd", "TEXT",
        "Rapid Connect XML transaction schema (rcxml.xsd)", "unversioned", "not stated", "Fiserv, Inc. (Datawire)",
        "RAPID_CONNECT", "04D997D6836BB2105CEFF395C8EF409635042EA8F42293B853DBD6ACFAE56F8B", None, (
            ch("01", "Schema rcxml.xsd"),)),
    Doc("ST-RC-XSD-SRS", "DW_RC_SRS_namespace-less.XSD", GUIDE + "Schema/DW_RC_SRS_namespace-less.XSD", "TEXT",
        "Datawire Rapid Connect SRS schema, namespace-less (DW_RC_SRS_namespace-less.XSD)", "unversioned",
        "not stated", "Fiserv, Inc. (Datawire)", "RAPID_CONNECT",
        "505D584D01B9F17DF97C901A8A25DB1C3F65EB8AE41F2D4FDE7244294B2E8E71", None, (
            ch("01", "Schema DW_RC_SRS_namespace-less.XSD"),)),
    Doc("ST-RC-README", "READ_ME.txt", GUIDE + "READ_ME.txt", "TEXT",
        "Datawire Secure Transport for Rapid Connect - package READ_ME", "unversioned", "not stated",
        "Fiserv, Inc. (Datawire)", "RAPID_CONNECT",
        "EF475F9D23403FF2F49855E0698F6447284E152C7BA7EE64DB9625A007B746C4", None, (
            ch("01", "Package READ_ME"),)),
)


def long_path(path):
    """Windows extended-length form; the supplied package nests deeply and several paths approach 260 characters."""
    resolved = os.path.abspath(str(path))
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        return "\\\\?\\" + resolved
    return resolved


def read_bytes(path):
    with open(long_path(path), "rb") as handle:
        return handle.read()


def write_text(path, text):
    if len(os.path.abspath(str(path))) > MAX_PATH:
        raise SystemExit(f"output path is longer than {MAX_PATH} characters: {path}")
    os.makedirs(long_path(Path(path).parent), exist_ok=True)
    with open(long_path(path), "w", encoding="utf-8", newline="\n") as handle:
        handle.write(text)


def sha256(data):
    return hashlib.sha256(data if isinstance(data, bytes) else data.encode("utf-8")).hexdigest().upper()


def slug(title, limit=30):
    """File-name part: the title after its last ' - ' (the sequence number already names the chapter), cut at a
    word boundary so that every local path stays below MAX_PATH."""
    words = re.sub(r"[^a-z0-9]+", "-", title.rsplit(" - ", 1)[-1].lower()).strip("-")
    while len(words) > limit and "-" in words:
        words = words.rsplit("-", 1)[0]
    return words[:limit]


def extract_pdf(path):
    from pypdf import PdfReader
    reader = PdfReader(long_path(path))
    texts, images = [], []
    for page in reader.pages:
        texts.append(page.extract_text() or "")
        try:
            images.append(len(page.images))
        except Exception:  # an unreadable image stream must not hide the page text
            images.append(-1)
    text = "".join(f"--- PAGE {number} ---\n{body}\n" for number, body in enumerate(texts, 1))
    return text, len(texts), images, texts


def extract_doc(path):
    """Read a Word 97-2003 document through Word without saving it: Content.Text keeps tables in reading order."""
    import pythoncom
    import win32com.client
    pythoncom.CoInitialize()
    workdir = Path(tempfile.mkdtemp(prefix="st-doc-"))
    copy = workdir / "source.doc"
    shutil.copyfile(long_path(path), copy)
    word = win32com.client.DispatchEx("Word.Application")
    try:
        word.Visible = False
        word.DisplayAlerts = 0
        word.AutomationSecurity = 3  # msoAutomationSecurityForceDisable: never run document macros
        # A dummy password makes a protected document fail instead of waiting on a hidden prompt.
        document = word.Documents.Open(str(copy), False, True, False, "x", "x", False, "x", "x", 0,
                                       pythoncom.Missing, False, False, pythoncom.Missing, True)
        stats = {"wordPages": document.ComputeStatistics(2), "paragraphs": document.Paragraphs.Count,
                 "tables": document.Tables.Count, "formFields": document.FormFields.Count,
                 "comments": document.Comments.Count, "revisions": document.Revisions.Count,
                 "shapes": document.Shapes.Count, "inlineShapes": document.InlineShapes.Count}
        margins = []
        for section in document.Sections:
            for index in (1, 2, 3):
                for kind, part in (("HEADER", section.Headers.Item(index)), ("FOOTER", section.Footers.Item(index))):
                    value = part.Range.Text.strip()
                    if value and f"{kind}: {value}" not in margins:
                        margins.append(f"{kind}: {value}")
        body = document.Content.Text
        version = word.Version
        document.Close(False)
    finally:
        word.Quit()
        shutil.rmtree(workdir, ignore_errors=True)
    body = (body.replace("\r\x07\r\x07", " |\n").replace("\r\x07", " | ").replace("\r", "\n")
            .replace("\x0b", "\n").replace("\x0c", "\n"))
    margin_text = "\n".join(line.replace("\r", "\n") for line in margins)
    text = f"--- HEADERS AND FOOTERS ---\n{margin_text}\n--- BODY ---\n{body}"
    return text, stats, version


def extract_text_file(path):
    raw = read_bytes(path)
    text = raw.decode("utf-8-sig")
    return text.replace("\r\n", "\n").replace("\r", "\n")


def page_starts(text):
    return {int(match.group(1)): match.start() for match in PAGE_MARK.finditer(text)}


def squash(text):
    """Whitespace-free form for heading matching: pypdf sometimes splits words ("Res ponse", "XML _Escape")."""
    return re.sub(r"\s+", "", text)


def locate(doc, text, starts, chapter):
    """Return (offset, how) where the chapter begins; fail unless the pinned anchor is found exactly once."""
    if chapter.page is None:
        if chapter.anchor is None:
            return 0, "DOCUMENT_START"
        hits = [m for m in LINE.finditer(text) if squash(m.group(0)).lstrip("|").startswith(squash(chapter.anchor))]
        if len(hits) != 1:
            raise SystemExit(f"{doc.id} {chapter.seq}: anchor {chapter.anchor!r} found {len(hits)} times")
        return hits[0].start(), "HEADING_LINE"
    begin = starts[chapter.page]
    finish = starts.get(chapter.page + 1, len(text))
    if chapter.anchor is None:
        return begin, "PAGE_START"
    page = text[begin:finish]
    lines = list(LINE.finditer(page))[1:]
    hits = [m for m in lines if squash(m.group(0)) == squash(chapter.anchor)]
    if len(hits) != 1:
        raise SystemExit(f"{doc.id} {chapter.seq}: anchor {chapter.anchor!r} found {len(hits)} times on page "
                         f"{chapter.page}")
    before = sum(1 for m in lines if m.start() < hits[0].start() and m.group(0).strip())
    if before <= RUNNING_HEADER_LINES:
        return begin, "PAGE_START"
    return begin + hits[0].start(), "HEADING_LINE"


def page_of(offset, ordered_pages, ordered_starts):
    return ordered_pages[bisect.bisect_right(ordered_starts, offset) - 1]


def fence_for(text):
    longest = max((len(run) for run in re.findall(r"`+", text)), default=0)
    return "`" * max(4, longest + 1)


def chapter_block(text):
    fence = fence_for(text)
    return f"{BEGIN}\n{fence}text\n{text}\n{fence}\n{END}\n"


def parse_chapter_block(markdown):
    """Inverse of chapter_block; the verifier uses the same rule."""
    start = markdown.index(BEGIN) + len(BEGIN) + 1
    fence_line_end = markdown.index("\n", start)
    fence = markdown[start:fence_line_end].removesuffix("text")
    end = markdown.rindex(f"\n{fence}\n{END}")
    return markdown[fence_line_end + 1:end]


def image_pages(doc, images, texts, chapters):
    baseline = Counter(images).most_common(1)[0][0]
    covers = {item["startPage"] for item in chapters if item["title"].lower().startswith(("cover", "back cover"))}
    return [number for number, (count, body) in enumerate(zip(images, texts), 1)
            if number not in covers and (count > baseline or (count > 0 and len(body.strip()) < 120))]


def render(doc, pages):
    if not pages:
        return []
    import pypdfium2
    pdf = pypdfium2.PdfDocument(long_path(SOURCE / doc.file))
    written = []
    try:
        for number in pages:
            target = IMAGES / f"{doc.id}-p{number:03d}.png"
            os.makedirs(long_path(IMAGES), exist_ok=True)
            pdf[number - 1].render(scale=1.4).to_pil().save(long_path(target))
            written.append(target.relative_to(LOCAL).as_posix())
    finally:
        pdf.close()
    return written


def relative(target, start_dir):
    return Path(os.path.relpath(target, start_dir)).as_posix()


def build_document(doc, word_version_holder):
    source = SOURCE / doc.file
    raw = read_bytes(source)
    if sha256(raw) != doc.sha256:
        raise SystemExit(f"{doc.id}: SHA-256 of {source.name} is {sha256(raw)}, expected {doc.sha256}")
    entry = {"id": doc.id, "title": doc.title, "version": doc.version, "date": doc.date, "publisher": doc.publisher,
             "family": doc.family, "format": doc.kind, "suppliedPath": doc.supplied,
             "storedFile": f"source/{doc.file}", "bytes": len(raw), "sha256": doc.sha256}
    images, texts, stats = [], [], None
    if doc.kind == "PDF":
        text, pages, images, texts = extract_pdf(source)
        if pages != doc.pages:
            raise SystemExit(f"{doc.id}: {pages} pages, expected {doc.pages}")
        entry["pages"] = pages
        entry["extraction"] = "pypdf page text with '--- PAGE N ---' markers"
    elif doc.kind == "DOC":
        text, stats, version = extract_doc(source)
        word_version_holder.append(version)
        entry.update(stats)
        entry["extraction"] = "Microsoft Word Content.Text (cells ' | ', rows end ' |'), headers and footers first"
    else:
        text = extract_text_file(source)
        entry["extraction"] = "UTF-8 text, line endings normalised to LF"
    starts = page_starts(text)
    ordered_pages = sorted(starts)
    ordered_starts = [starts[number] for number in ordered_pages]
    offsets = [locate(doc, text, starts, chapter) for chapter in doc.chapters]
    if offsets[0][0] != 0:
        raise SystemExit(f"{doc.id}: the first chapter must start at the beginning of the text")
    if any(b[0] <= a[0] for a, b in zip(offsets, offsets[1:])):
        raise SystemExit(f"{doc.id}: chapter anchors are not in document order: {offsets}")
    bounds = [offset for offset, _ in offsets] + [len(text)]
    folder = CHAPTERS / doc.id
    chapters, pieces = [], []
    for index, chapter in enumerate(doc.chapters):
        piece = text[bounds[index]:bounds[index + 1]]
        if not piece.strip():
            raise SystemExit(f"{doc.id} {chapter.seq}: empty chapter")
        pieces.append(piece)
        item = {"seq": chapter.seq, "title": chapter.title, "anchor": chapter.anchor, "startsAt": offsets[index][1],
                "file": f"chapters/{doc.id}/{chapter.seq}-{slug(chapter.title)}.md", "characters": len(piece),
                "sha256": sha256(piece)}
        if ordered_pages:
            item["startPage"] = page_of(bounds[index], ordered_pages, ordered_starts)
            item["endPage"] = page_of(bounds[index + 1] - 1, ordered_pages, ordered_starts)
        chapters.append(item)
    if "".join(pieces) != text:
        raise SystemExit(f"{doc.id}: the chapters do not concatenate to the extracted text")
    if ordered_pages:
        covered = set()
        for item in chapters:
            covered.update(range(item["startPage"], item["endPage"] + 1))
        if covered != set(range(1, doc.pages + 1)):
            raise SystemExit(f"{doc.id}: pages not covered by any chapter: {sorted(set(range(1, doc.pages + 1)) - covered)}")
    entry["textFile"] = f"text/{doc.id}.txt"
    entry["textCharacters"] = len(text)
    entry["textLines"] = text.count("\n") + (0 if text.endswith("\n") else 1)
    entry["textSha256"] = sha256(text)
    write_text(TEXT / f"{doc.id}.txt", text)
    if doc.kind == "PDF":
        pages_with_images = image_pages(doc, images, texts, chapters)
        entry["imagesPerPage"] = images
        entry["renderedPages"] = pages_with_images
        entry["renderedImages"] = render(doc, pages_with_images)
    entry["chapters"] = chapters
    for index, (chapter, item, piece) in enumerate(zip(doc.chapters, chapters, pieces)):
        write_text(LOCAL / item["file"], chapter_markdown(doc, entry, chapters, index, piece))
    write_text(folder / "README.md", document_index(doc, entry))
    return entry


def chapter_markdown(doc, entry, chapters, index, piece):
    item = chapters[index]
    here = (LOCAL / item["file"]).parent
    if "startPage" in item:
        where = f"PDF pages {item['startPage']}-{item['endPage']}"
        where += (" (starts at the top of the page)" if item["startsAt"] == "PAGE_START"
                  else f" (starts at the heading \"{item['anchor']}\" part-way down page {item['startPage']})")
        rendered = [path for path in entry.get("renderedImages", [])
                    if item["startPage"] <= int(path.rsplit("-p", 1)[1][:3]) <= item["endPage"]]
    else:
        where = "whole file" if entry["format"] == "TEXT" else (
            "from the start of the document" if item["anchor"] is None else f"from the heading \"{item['anchor']}\"")
        rendered = []
    links = []
    if index > 0:
        links.append(f"previous: [{chapters[index - 1]['seq']}]({Path(chapters[index - 1]['file']).name})")
    if index + 1 < len(chapters):
        links.append(f"next: [{chapters[index + 1]['seq']}]({Path(chapters[index + 1]['file']).name})")
    rows = [("Document", f"{doc.title} ({doc.version}, {doc.date})"),
            ("Source", f"`source/{doc.file}` (SHA-256 `{doc.sha256}`)"),
            ("Location", where),
            ("Verbatim text", f"{item['characters']:,} characters, SHA-256 `{item['sha256']}`")]
    if rendered:
        rows.append(("Rendered pages", ", ".join(f"[{Path(p).name}]({relative(LOCAL / p, here)})" for p in rendered)))
    rows.append(("Navigation", " | ".join([f"[document index](README.md)"] + links)))
    table = "\n".join(f"| {key} | {value} |" for key, value in rows)
    return (f"# {doc.id} {item['seq']}: {item['title']}\n\n"
            "> Local-only verbatim extract of a First Data/Fiserv confidential document. Do not commit or share it.\n"
            "> Regenerate with `python scripts/build-secure-transport-ingestion.py`. The trained knowledge is in\n"
            f"> [SecureTransport-Training-Knowledge.md]({relative(RECORD, here)}).\n\n"
            f"| Field | Value |\n| --- | --- |\n{table}\n\n{chapter_block(piece)}")


def document_index(doc, entry):
    here = CHAPTERS / doc.id
    rows = []
    for item in entry["chapters"]:
        pages = f"{item['startPage']}-{item['endPage']}" if "startPage" in item else "-"
        rows.append(f"| {item['seq']} | [{item['title']}]({Path(item['file']).name}) | {pages} | "
                    f"{item['characters']:,} |")
    extent = f"{entry['pages']} PDF pages" if "pages" in entry else (
        f"{entry['wordPages']} Word pages, {entry['tables']} tables" if "wordPages" in entry else "text file")
    return (f"# {doc.id}: {doc.title}\n\n"
            "> Local-only. First Data/Fiserv confidential. Do not commit or share.\n\n"
            f"| Field | Value |\n| --- | --- |\n"
            f"| Version | {doc.version} |\n| Date | {doc.date} |\n| Publisher | {doc.publisher} |\n"
            f"| Family | {doc.family} |\n| Supplied as | `{doc.supplied}` |\n"
            f"| Stored as | [{doc.file}]({relative(SOURCE / doc.file, here)}) |\n| SHA-256 | `{doc.sha256}` |\n"
            f"| Extent | {extent}; {entry['textCharacters']:,} characters of extracted text |\n"
            f"| Full text | [{doc.id}.txt]({relative(TEXT / (doc.id + '.txt'), here)}) |\n"
            f"| Trained knowledge | [SecureTransport-Training-Knowledge.md]({relative(RECORD, here)}) |\n\n"
            f"## Chapters and phases\n\n| Seq | Title | Pages | Characters |\n| --- | --- | --- | ---: |\n"
            + "\n".join(rows) + "\n\nThe chapters concatenate to exactly the full text; "
            "`scripts/verify-secure-transport-ingestion.py` checks this.\n")


def local_index(entries):
    rows = []
    for entry in entries:
        extent = f"{entry['pages']} pp." if "pages" in entry else entry["format"]
        rows.append(f"| {entry['id']} | [{entry['title']}](chapters/{entry['id']}/README.md) | {entry['version']} | "
                    f"{entry['date']} | {entry['family']} | {extent} | {len(entry['chapters'])} |")
    return ("# Secure Transport sources (local only)\n\n"
            "> First Data/Fiserv confidential. This folder is git-ignored because the repository is public.\n"
            "> Regenerate it with `python scripts/build-secure-transport-ingestion.py`; verify it with\n"
            "> `python scripts/verify-secure-transport-ingestion.py`.\n\n"
            "ATL105 2026-4 Section 3.3 (PDF p.49, printed p.3-2) refers to \"the Secure Transport section on the "
            "First Data Web site\" for Internet support information. The trained knowledge is in "
            f"[SecureTransport-Training-Knowledge.md]({relative(RECORD, LOCAL)}).\n\n"
            "| ID | Document | Version | Date | Family | Extent | Chapters |\n"
            "| --- | --- | --- | --- | --- | --- | ---: |\n" + "\n".join(rows) + "\n")


def main():
    import pypdf
    word_version = []
    entries = [build_document(doc, word_version) for doc in DOCS]
    write_text(LOCAL / "00-INDEX.md", local_index(entries))
    manifest = {
        "artifact": "secure-transport-source-manifest",
        "schemaVersion": 1,
        "generatedBy": "scripts/build-secure-transport-ingestion.py",
        "ingestedOn": INGESTED_ON,
        "extractors": {"pypdf": pypdf.__version__,
                       "word": f"Microsoft Word {word_version[0]} via pywin32" if word_version else None},
        "storagePolicy": {
            "localOnlyRoot": "specifications/ATL105/docs/specs/secure-transport/",
            "committed": False,
            "reason": "First Data/Fiserv confidential sources; the repository is public. Only this manifest and "
                      "the training record are tracked."},
        "atl105Reference": {
            "specification": "BUYPASS Platform ATL105 Message Format Specifications",
            "versions": {"2026-4": {"extract": "docs/specs/extracted_text_new.txt", "lines": [2813, 2815],
                                    "pdfPage": 49, "printedPage": "3-2"},
                         "2026-3": {"extract": "docs/specs/extracted_text.txt", "lines": [2621, 2623],
                                    "printedPage": "3-2"}},
            "section": "3.3 Telecommunications Requirements",
            "statement": "For additional Internet support information, please refer to the Secure Transport "
                         "section on the First Data\u00ae Web site (https://www.firstdata.com/en_us/customer-"
                         "center/merchants/support/secure-transport-api)"},
        "trainingRecord": "specifications/ATL105/docs/test-validation-strategy/SecureTransport-Training-Knowledge.md",
        "documents": entries,
        "totals": {"documents": len(entries),
                   "pdfPages": sum(entry.get("pages", 0) for entry in entries),
                   "chapters": sum(len(entry["chapters"]) for entry in entries),
                   "characters": sum(entry["textCharacters"] for entry in entries),
                   "renderedPages": sum(len(entry.get("renderedPages", [])) for entry in entries)},
    }
    write_text(MANIFEST, json.dumps(manifest, indent=2, ensure_ascii=False) + "\n")
    totals = manifest["totals"]
    print(f"{totals['documents']} documents, {totals['pdfPages']} PDF pages, {totals['chapters']} chapters, "
          f"{totals['characters']:,} characters, {totals['renderedPages']} rendered pages")
    for entry in entries:
        print(f"  {entry['id']}: {len(entry['chapters'])} chapters, {entry['textCharacters']:,} characters"
              + (f", rendered pages {entry['renderedPages']}" if entry.get("renderedPages") else ""))
    print(f"Manifest: {MANIFEST.relative_to(MODULE).as_posix()}")
    print(f"Local split: {LOCAL.relative_to(MODULE).as_posix()}/ (git-ignored)")
    return 0


if __name__ == "__main__":
    sys.exit(main())
