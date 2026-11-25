# Secure Transport (Datawire) Transport-Layer Reference

> ATL105 2026-4 Section 3.3 Telecommunications Requirements (PDF p.49, printed p.3-2; the same text is in 2026-3)
> says: "For additional Internet support information, please refer to the Secure Transport section on the First
> Data® Web site (https://www.firstdata.com/en_us/customer-center/merchants/support/secure-transport-api)".
> ATL105 names no document, version or section beyond that.
>
> - **Trained knowledge (memory):** [SecureTransport-Training-Knowledge.md](../../../test-validation-strategy/SecureTransport-Training-Knowledge.md)
> - **Rule catalog:** [secure-transport-rule-catalog.md](secure-transport-rule-catalog.md). It holds 687 rules by family; each rule has a scope, a strength and a cited page, phase or line.
> - **Manifest:** [secure-transport-source-manifest.json](secure-transport-source-manifest.json). It is tracked and holds identities, hashes, page ranges and chapter hashes, but no source text.

## Where the sources are

The supplied documents are First Data/Fiserv confidential, and this repository is public, so everything derived verbatim
from them stays local. The folder `specifications/ATL105/docs/specs/secure-transport/` is git-ignored:

| Local path | Content |
| --- | --- |
| `source/` | The 13 supplied files, byte-for-byte. Their SHA-256 values are pinned in the manifest. |
| `text/<DOC-ID>.txt` | Full extracted text. PDFs carry `--- PAGE N ---` markers. |
| `chapters/<DOC-ID>/` | Verbatim chapter/phase split: one file per chapter with a per-document `README.md`. The chapters of a document concatenate to exactly its full text. |
| `images/` | Rendered pages that carry diagrams or screenshots (19 pages) |
| `digests/` | Chapter-by-chapter reading digests and the record rows derived from them |
| `00-INDEX.md` | Local index of all of the above |

Commands, from the validator module:

| Task | Command |
| --- | --- |
| Rebuild the split and the manifest (needs the local sources, `pypdf`, `pypdfium2` and Microsoft Word) | `.\framework.ps1 -Task secure-transport-ingestion-build` |
| Verify the training record and, when the local split exists, the sources | `.\framework.ps1 -Task secure-transport-ingestion-check` |
| Run the verification regressions | `.\framework.ps1 -Task secure-transport-ingestion-test` |

## Documents

13 files, 292 PDF pages and 107 chapters/phases, ingested 2026-10-28:

| ID | Document | Version and date | Family | Extent | Chapters/phases |
| --- | --- | --- | --- | --- | ---: |
| ST-UNIX-API | Secure Transport Unix Source Code API Guide | API 4.6.3, revision 1.08, 2015-08-10 | Generic | 64 pp. | 14 |
| ST-XML-API | Secure Transport XML Interface Reference Guide | API 3.3, revision 1.14, June 2022 | Generic | 77 pp. | 18 |
| ST-RC-GUIDE | Datawire Secure Transport XML and SOAP Interfaces for Rapid Connect Reference Guide | 1.26, April 2022 | Rapid Connect | 103 pp. | 33 |
| ST-RC-SRS | Datawire Secure Transport XML and SOAP SRS Interfaces for Rapid Connect Reference Guide | 1.9, 2022-04-26 | Rapid Connect | 35 pp. | 10 |
| ST-RC-SRS-STEPS | Step By Step Self-Registration (SRS) Process for Datawire Secure Transport and Rapid Connect | 1.1, 2022-02-09 | Rapid Connect | 3 pp. | 3 |
| ST-RC-PARAMS | Datawire Parameter Guidelines for Rapid Connect | 1.1, 2022-02-09 | Rapid Connect | 4 pp. | 4 |
| ST-RC-TLS | Required TLS Ciphers to Connect to Datawire Secure Transport for Rapid Connect | 1.1, 2022-02-09 | Rapid Connect | 3 pp. | 3 |
| ST-RC-ROOTCA | Required Root CA Certificates to Connect to Datawire Secure Transport Network for Rapid Connect | 1.1, 2022-02-09 | Rapid Connect | 3 pp. | 3 |
| ST-RC-CERT-FORM | Datawire (DW) / Secure Transport (ST) Compliance Test Form for Rapid Connect | 3.4.4, 2021-12-14 | Rapid Connect | Word, 12 pp. | 10 |
| ST-RC-RECERT | Datawire Rapid Connect Re-Certification Compliance Script | 1.4, 2021-12-14 | Rapid Connect | Word, 4 pp. | 6 |
| ST-RC-XSD-RCXML | `rcxml.xsd` (Rapid Connect XML transaction schema) | unversioned | Rapid Connect | 204 lines | 1 |
| ST-RC-XSD-SRS | `DW_RC_SRS_namespace-less.XSD` (SRS schema) | unversioned | Rapid Connect | 141 lines | 1 |
| ST-RC-README | `READ_ME.txt` (package guide) | unversioned | Rapid Connect | 22 lines | 1 |

## How to use this reference

- **Applicability.** ATL105 governs the message. Secure Transport only carries it, as an opaque payload. The generic
  guides apply to an ATL105 payload. The Rapid Connect package applies only where it states transport behaviour that
  the generic guides share. Its identity values (`App`, `ServiceID` `160`, AuthKeys, ClientRef with a TPP ID) and its
  XML payload rules never apply to BUYPASS without SME confirmation. See record
  [section 3](../../../test-validation-strategy/SecureTransport-Training-Knowledge.md#3-applicability-precedence-and-evidence-rules).
- **Finding a topic.** Use the record's [chapter and phase map](../../../test-validation-strategy/SecureTransport-Training-Knowledge.md#4-chapter-and-phase-map),
  then open the matching local chapter file.
- **ATL105 touchpoints.** Section 3.3 (Internet), Section 2.3 (time-out reversals), Sections 7.1 and 7.2
  (Communications Test), Section 11.8.1 (4,000-byte request limit) and Appendix A (TCP/IP header). See the
  [integration map](../../../test-validation-strategy/SecureTransport-Training-Knowledge.md#8-atl105-integration-map).
- **Status.** Every rule derived from these documents is `PROVISIONAL` and outside certified ATL105 coverage until an
  SME confirms it for BUYPASS.
- **Keep it local.** Never copy source text into tracked files beyond short, cited quotations. Never commit
  `docs/specs/secure-transport/`.
