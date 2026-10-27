# ATL105 EMV Companion Reference: Training Knowledge

**Training subject:** Fiserv *EMV and Chip Implementation Guide*, Generic Edition, Version 2025.RG20 (12 August 2025)<br>
**Required by:** BUYPASS Platform ATL105 Message Format Specifications 2026-4, Section 10.14 (EMV Processing Requirements)<br>
**Ingested:** 2026-10-27 by the Core Auth Test Validation Team<br>
**Applies to:** ATL105 §10.14, Segments 130/131/132, Appendices R/S/T, and Elements 30, 83, 187-194 in EMV contexts<br>
**Parent strategy:** [Core Auth Regression Test Validation Strategy](Core-Auth-Regression-Test-Validation-Strategy.md#emv-companion-reference-intake-october-2026)<br>
**Training handbook:** [ATL105 Segment Training Handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md#emv-companion-reference-overlay-october-2026)

> **This file is the persistent training record ("memory") for the EMV companion reference.**
>
> Read it before training, generating, or validating any EMV-related ATL105 artifact. It records:
>
> - what the previously missing document contains
> - where ATL105 needed it
> - which gaps it closes, and which it does not
> - how its content may and may not be used as a Test Solution oracle
>
> It does not amend ATL105, approve any business requirement, or replace SME/TBA decisions.

## Contents

1. [Why This Document Was Ingested](#1-why-this-document-was-ingested)
2. [Source Binding](#2-source-binding)
3. [Precedence and Evidence Rules](#3-precedence-and-evidence-rules)
4. [Gap Closure Register](#4-gap-closure-register)
5. [Guide Content Map](#5-guide-content-map)
6. [EMV Tag Catalog for Element 190](#6-emv-tag-catalog-for-element-190)
7. [Chip-to-ATL105 Cross-Field Rules](#7-chip-to-atl105-cross-field-rules)
8. [Lifecycle Rules](#8-lifecycle-rules)
9. [AID, Brand, Routing and CVM Rules](#9-aid-brand-routing-and-cvm-rules)
10. [Entry Mode and Fallback Rules](#10-entry-mode-and-fallback-rules)
11. [Response Chip Data and Tag 8A Rules](#11-response-chip-data-and-tag-8a-rules)
12. [CA Public Key (CAPK) Rules](#12-ca-public-key-capk-rules)
13. [Requirement (RQ) Traceability Index](#13-requirement-rq-traceability-index)
14. [EMV Test Scenario Catalog](#14-emv-test-scenario-catalog)
15. [Synthetic Test-Data Guidance](#15-synthetic-test-data-guidance)
16. [Source Conflicts and Defects](#16-source-conflicts-and-defects)
17. [Still-Open Items and Missing External Documents](#17-still-open-items-and-missing-external-documents)
18. [Implementation Follow-Up Backlog](#18-implementation-follow-up-backlog)
19. [How to Reuse This Memory](#19-how-to-reuse-this-memory)
20. [Revision History](#20-revision-history)

## 1. Why This Document Was Ingested

When ATL105 was first trained, three places in the specification or knowledge base pointed to an EMV document that was not available. Those items were held as `REVIEW_REQUIRED` or "no formula stated".

| ATL105 / KB location | What it said | Previous effect |
| --- | --- | --- |
| ATL105 2026-4 §10.14 (p.10-80, PDF p.159; also 2026-3 p.10-79) | "Refer to First Data EMV Implementation Guide, available on the Direct Platform Specifications Portal. The EMV Implementation Guide must be used in conjunction with the ATL105 Message Format Specifications." | The EMV processing rules in §10.14 could be trained only from ATL105's own summary text. |
| ATL105 Appendix R (p.R-1, PDF p.697) | "The below table does not contain complete list of tags. For complete list of EMV tags, please contact your BUYPASS representative." | No tag catalog existed for Element 190 ([Appendix R module](../specs/kb/appendix-r/README.md), Deferred Work 3). |
| KB [chapter-coverage-gap-register](../specs/kb/chapter-coverage-gap-register.md), Appendix R `BR-SEG100-APPR-CROSS-FIELD`, `SEG130-SME-003` | "General EMV amount/currency-to-Segment 100 formulas are not stated by the extracted Segment 130 / Appendix R / 189 / 190 source." | The chip-to-Segment 100 cross-field check stayed `REVIEW_REQUIRED` by design. |

The "First Data EMV Implementation Guide" named by ATL105 is now published as the Fiserv **EMV and Chip Implementation Guide**. The guide's revision history records the name change in v2024.RG17 (7 November 2024, PDF p.5).

## 2. Source Binding

| Item | Value |
| --- | --- |
| Companion document | `Fiserv_Generic_EMV_Implementation_Guide_v2025_RG20_081225.pdf`. This is a local copy in the trainer's workspace (framework root). It is **not committed**, because this GitHub repository is public. Get the current version from the Fiserv Direct Platform Specifications portal (Developer Studio) and check it against the SHA-256 below. |
| Title / edition / version | EMV and Chip Implementation Guide, Generic Edition, Revised Format, v2025.RG20, 12 August 2025 |
| Size / pages | 6,704,094 bytes / 212 pages |
| SHA-256 | `A79B133C950CA2A0734E8D38D07AAE140A254E0069A4FC3F61D653CCC1489EF4` |
| Classification | Sensitivity label "Fiserv Public"; page footer "Fiserv - Proprietary"; the copyright notice restricts disclosure. Do not commit the PDF or its full-text extraction to this public repository. This record cites section and page anchors and keeps quotations short. |
| Front ends covered | Nashville, Cardnet, North, **BuyPass** and Omaha, called generically the "front end host" (§2.1, PDF p.24) |
| Extraction | `pypdf` text with `--- PAGE N ---` markers (10,773 lines), used for this training only and not committed |
| Bound ATL105 release | [BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf](../specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf), SHA-256 `568C04D3081CAC6C5CC545ADEE9C6F8F3A34334E788FDF5EE0260FFEF57C77AF`, text in [extracted_text_new.txt](../specs/extracted_text_new.txt) |

**Extraction limits:**

- Chapter 1 ("Requirements", "Best Practices" and "Tables" index pages, PDF pp.15-22) is rendered as image fragments and has no extractable text. Every RQ requirement used here was taken from its full statement in the body chapters. The Chapter 1 index itself was not verified.
- §14.9 embeds an attachment, "State of California and Oklahoma Chip Card.pdf", which could not be extracted.
- The guide marks requirements with `RQ nnnn` and best practices with `BP nnnn`. In the body text, the BP labels are mostly images. BP numbers quoted here come from the revision history or from adjacent text.

## 3. Precedence and Evidence Rules

### 3.1 Precedence (fixed)

1. **The bound ATL105 release governs.** The guide states: "Should contents within this Guide contradict any specifications listed, the specification overrides this Guide" (§2.3, PDF p.25). It also says: "This guide is not a specification. It is to provide guidance only and does not override any specification or EMVCo documentation" (§2.3, PDF p.25). The petroleum section repeats this: "the message specification takes precedence" (§5.15, PDF p.76).
2. **The guide fills gaps that ATL105 delegates to it.** ATL105 §10.14 requires the guide to be used "in conjunction with" ATL105. Guide statements therefore count as companion-source evidence where ATL105 is silent.
3. **The guide is Fiserv-generic.** It covers five front ends, and "each FE is unique, and functionality may vary depending on spec and platform used" (§2.1). Any guide rule that depends on a front-end message field needs BuyPass applicability before it can gate certification.
4. **Tag meanings are brand-dependent** (§2.3, Tag 9F6E example). A validator must not assume a single meaning for 9F5B, 9F66, 9F67, 9F69, 9F6C, 9F6D, 9F6E or the proprietary `DFxx` tags. See [§16.2](#162-guide-internal-inconsistencies).

### 3.2 Provenance anchor

Ingest guide content as a separately hash-pinned **companion overlay**. This follows the handbook rule for later releases: "do not silently relabel 2026-3 rules". Every guide-derived rule carries its own anchor:

```json
{ "specification": "FISERV-EMV-IG", "version": "2025.RG20", "section": "5.9", "requirement": "RQ 4600", "pdfPage": 72,
  "relatedAtl105": { "specification": "ATL105", "version": "2026-4", "section": "10.14.2.4" } }
```

Never write a guide anchor into an ATL105 `sourceAnchor` field. AI-to-Test matching stays keyed on ATL105 anchors. The guide anchor is supporting evidence only.

### 3.3 Status and enforcement mapping

The strategy statuses do not change: `SOURCE_VERIFIED`, `TEST_VALIDATED` and `PROVISIONAL`. TD readiness also keeps its existing values: `EXECUTABLE`, `REVIEW_REQUIRED` and `EXTERNAL_FIXTURE_REQUIRED`. The guide adds only a provenance value, `sourceDocument = FISERV-EMV-IG-2025.RG20`.

| Evidence situation | Status | Enforcement in validators |
| --- | --- | --- |
| ATL105 states the rule; the guide only corroborates | `SOURCE_VERIFIED` (ATL105) | Existing ATL105 enforcement (FAIL where deterministic) |
| Guide `RQ` ("must") rule, observable in an ATL105 message, not contradicted by ATL105 | `PROVISIONAL` | **FAIL-candidate**: reported as a finding with expected outcome; not counted as certified coverage until SME/TBA confirms BuyPass applicability |
| Guide `BP`, "should", illustrative table, brand-dependent or context-dependent statement | `PROVISIONAL` | **REVIEW**: `REVIEW_REQUIRED` finding only |
| Device-, kernel-, receipt-, report- or certification-process rule (not visible in an ATL105 message) | Not a message rule | **INFO**: certification evidence; out of message-validator scope |
| Guide contradicts ATL105 | ATL105 rule unchanged | **SPEC_PRECEDENCE**: ATL105 is enforced and the difference is logged in [§16.1](#161-guide-versus-atl105-atl105-governs) |
| Arithmetic or representation fact that resolves an ATL105 ambiguity (for example, 2^16+1 = hex `010001`) | `SOURCE_VERIFIED` (ATL105, with guide corroboration) | FAIL where deterministic |

A guide-derived check that has not been reviewed must never move a rule to `TEST_VALIDATED` or count toward certified Rule Coverage. Under the proposed hard gates, a guide-derived `REVIEW_REQUIRED` finding is held for manual review. It is not treated as a pass.

## 4. Gap Closure Register

These dispositions are documentation dispositions. The SME/TBA communication register keeps its own statuses. Updating it is listed in [§18](#18-implementation-follow-up-backlog).

| Gap | Where it was open | Guide evidence | Disposition | Remaining action |
| --- | --- | --- | --- | --- |
| GAP-EMV-01 | ATL105 §10.14 "Refer to First Data EMV Implementation Guide" | Whole guide, v2025.RG20 | **INGESTED** (this file) | Keep the overlay hash-pinned. Re-ingest when a new guide version is supplied. |
| GAP-EMV-02 | Appendix R "complete list of EMV tags: contact your BUYPASS representative"; Appendix R Deferred Work 3 | Table 25 (Auth / Reverse / Settle / Response tag matrix, PDF pp.118-119) and Table 26 (formats, PDF pp.121-138) | **EVIDENCE_SUPPLIED**: Fiserv-generic tag catalog in [§6](#6-emv-tag-catalog-for-element-190) | The guide says the table is illustrative and "not hard-coded". BuyPass confirmation is still required (Q-EMV-01). |
| GAP-EMV-03 | `BR-SEG100-APPR-CROSS-FIELD`, `SEG130-SME-003` / `P-03`, gap-register "no general amount/currency formula" | RQ3400, RQ3500, RQ3700, RQ4200, RQ1400; Table 6; Table 28; §10.1.3; §4.8 (9A) | **EVIDENCE_SUPPLIED**: candidate formulas XF-001 to XF-011 in [§7](#7-chip-to-atl105-cross-field-rules) | Run them as `REVIEW_REQUIRED` checks until the SME confirms (Q-EMV-02). |
| GAP-EMV-04 | `SEG130-SME-008` / `SEG130-SME-012`: no Appendix E code exists for the §10.14.2.3 brands | Table 4 and Table 63 AID lists, Table 12 (US CAIDs), Table 13 (message type) | **PROPOSED_RESOLUTION**: decide EMV eligibility and brand from the AID in Element 190 (84/9F06). Appendix E applies only to fleet, proprietary and debit card types ([§9.1](#91-atl105-product-to-aid-bridge-closes-the-sme-008012-path)). | The SME accepts the AID-based method. |
| GAP-EMV-05 | `SEG130-SME-009` / `SEG130-SME-013`: "Generic Proprietary (Some issuers participating)" | Chapter 14 non-brand AIDs: ExxonMobil/Citi, Sunoco/Citi, Wawa/Citi, Fleetcor (Fuelman, Gascard/FleetWide, Comdata) | **PARTIAL**: candidate participant list | Confirm the BuyPass deployment for each issuer. |
| GAP-EMV-06 | `SEG130-SME-004` / `SEG130-R-010`: cryptogram authenticity | §3.2 Table 2 and §13: brand L3 certification on ICCSimTMat with card logs (RQ7200) | **CONFIRMED EXTERNAL**: out of message-validator scope permanently; certification evidence comes from the Fiserv/brand process | Keep `EXTERNAL_FIXTURE_REQUIRED`. |
| GAP-EMV-07 | `BR-SEG100-APPS-CA-EMV-LINK`, `SEG130-SME-002`: no AID-to-key formula | Table 15 ("Index identifies the key in conjunction with the RID"); §4.2 (AID = RID + PIX); tag 8F; Voyager uses Mastercard CA keys; PLCC AIDs have no CAPK index | **EVIDENCE_SUPPLIED**: key = (RID of selected AID, CA PK Index 8F). See [§12](#12-ca-public-key-capk-rules). | A real CAPK file is still external. Tag 8F is not sent in Element 190, so only RID coverage is observable. |
| GAP-EMV-08 | Appendix S Exponent "03 or 216+1" (superscript lost); the KB uses `065537` | Table 15: Exponent "03" or **"010001"** ("3" or "2^16+1") | **CORRECTION_REQUIRED**: the 6-character form is hex `010001` | Fix `BR-SEG100-APPS-EXPONENT-VALUE` and its checksum fixtures (backlog B-01). |
| GAP-EMV-09 | Appendix S RID length: KB accepts 1-10 characters | Table 15: RID A/N 10, for example `A000000003`. ATL105 also gives AN 10. | **TIGHTEN**: exactly 10 hex characters | Backlog B-08. |
| GAP-EMV-10 | Appendix S Modulus "Hex Var.": no maximum | Table 15: Modulus Hex, variable, maximum 496 | **EVIDENCE_SUPPLIED**: maximum 496 hex characters (248 bytes) | Backlog B-08. |
| GAP-EMV-11 | `SEG130-R-017` / `EMV-130-TX-005/008`: EMV data on reversals | Table 10 (reversal reason to 1st/2nd Generate AC data); RQ4600 | **EXTENDED**: ATL105 "not required" still governs absence; the guide governs content when present ([§8](#8-lifecycle-rules)) | Backlog B-06. |
| GAP-EMV-12 | [Fallback note](../specs/kb/segment-130/emv-entry-mode-fallback-sme-tba-note.md) open item: "80/90 or spec equivalent" | RQ4400 (fallback entry mode, "ex. '80'"); Discover empty-candidate-list rule | **RESOLVED_FROM_SOURCE**: ATL105 Appendix J defines 80 (chip fallback to swipe), 79 (fallback to manual) and 90 (MSR). The guide corroborates 80. | Update the note (backlog B-10). |
| GAP-EMV-13 | `SEG131-SME-003`: "Source: Device" on Segment 131 chip data | Table 25: response tags 71, 72, 8A and 91 have source "Issuer"; 9F36 and DF3F come from the chip | **EVIDENCE_SUPPLIED**: supports host/issuer-originated response chip data | The SME confirms a spec-defect log. |
| GAP-EMV-14 | `SEG130-SME-010`: what Segment 131 echoes when Element 187 is omitted | Not addressed. The guide covers only CAPK status and download (§6.1.1). | **OPEN** | Unchanged |
| GAP-EMV-15 | `SEG130-SME-007` / `SEG130-SME-011`: Appendix T Table 002 (CARC) in requests | Not addressed. The guide has no Appendix T content. | **OPEN** | Unchanged |
| GAP-EMV-16 | `SEG131-SME-001/002`, `SEG132-SME-001` to `006` (BuyPass wire layout) | Not addressed. §6.3 adds only "download from one data center" and "replace the whole file". | **OPEN** | Unchanged |
| GAP-EMV-17 | §10.14.2.5 receipts; §10.14.3 EMV PIN | Chapter 7 receipt mandates (RQ5800 to RQ6700); RQ1900 (online and offline PIN; UCAT offline plaintext PIN sunset dates) | **CORROBORATED (device scope)** | Certification evidence only |

## 5. Guide Content Map

| Guide chapter (PDF pages) | Content | Test Solution use |
| --- | --- | --- |
| 2 Introduction (23-31) | Precedence, Table 1 terminal types (9F35), feature checklist, announcements | Precedence rules; 9F35 domain |
| 3 Development Process (32-40) | Table 2 certification steps (t3:Cert, ICCSimTMat, TOR testing, settlement/back-office testing, pilot, go-live with production CAPK), recertification, UAT | External certification evidence; environment rules |
| 4 EMV Overview (41-59) | Initiation, AID selection and **Table 4 AIDs**, RQ1400 (5F24), offline authentication, CVM, PIN bypass, terminal risk management, **Table 6** cryptogram input tags, CID, online processing, **Table 7** Tag 8A, offline approvals, scripts, reversal processing | Tag, cross-field, AID and response rules |
| 5 Market and Transaction Types (60-91) | Account verification, balance inquiry, **Table 9** cashback by AID, contactless/CDCVM, DCC, floor limit, **fallback**, **Table 10** reversals, incremental/partial reversal, returns, partial authorization, **petroleum (BuyPass)**, pre-authorization/completion, Quick Chip, referrals, stand-in, SAF, tip, US debit (**Tables 12, 13**), PAVD, Fleet 2.0 | Lifecycle, AID and fallback rules |
| 6 CAPK Management (92-96) | Update policy, status request (N/U), **Tables 14 and 15** file format, download rules | CAPK rules |
| 7 Receipts (97-108) | Table 16 receipt data; Table 17 offline-decline tags | Device evidence only |
| 8 Reports (109-116) | Chip transaction report sample (Table 18), fallback and entry-mode reports, mandatory EMV configuration report, **Tables 23 and 24** parameters | Synthetic-data values; certification evidence |
| 9 EMV Tags (117-138) | **Table 25** tag matrix by message role; **Table 26** tag dictionary (format and length) | Tag catalog and format rules |
| 10 EMV 101 (139-144) | DOLs (RQ6900 to RQ7100), random selection, IAC/TAC, **Table 28** Tag 9C values | Cross-field rule XF-005 |
| 11 Tag Layouts (145-159) | Bit maps for 82, 87, 9F40, 9F07, 9F34, 9F27, 9F33, 9F66, 9F6C, 95, 9B | Value-domain checks |
| 13 Test Tools (164-166) | ICCSimTMat for Fiserv (mandated, RQ7200) and supported brand suites | External tool |
| 14 Non-Brand AIDs (167-180) | Voyager, WEX, ExxonMobil, Fleetcor/Fuelman/Comdata, Sunoco, Wawa, **EBT chip field set** | Fleet, proprietary and EBT rules |
| 15-16 Glossary, prior revisions (181-212) | Definitions; "removed mention of mandatory tags" (v2019.RG1) | Precedence of Table 25 |

## 6. EMV Tag Catalog for Element 190

### 6.1 Message-role mapping

| Table 25 column | ATL105 message role |
| --- | --- |
| Auth | Element 190 in Segment 130 of an authorization-class EMV Financial Transaction Request: Purchase, Authorization Only, Balance Inquiry, Merchandise Return, and SAF/deferred authorization |
| Reverse | Element 190 in a Purchase Reversal or Time-out Reversal **when EMV data is present**. ATL105 §10.14.2.4 says "EMV data is not required on Reversal transactions". |
| Settle*/Completion | Element 190 in a completion (Purchase/Capture after Authorization Only), an offline-approved upload or advice, or a settlement record |
| Response* | Element 190 in Segment 131 of the EMV Financial Transaction Response |

### 6.2 Table 25: EMV tags used for request and response (guide §9.1, PDF pp.118-119)

B = contact and contactless; CT = contact only; CTLS = contactless only. Y = expected; N = not expected.

| Tag | Name | Interface | Auth | Reverse | Settle/Completion | Response | Source | Format and length (Table 26) |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 4F | Application DF Name (AID) | B | Y | Y | N | N | Chip | b, 5-16 |
| 71 | Issuer Script Template 1 | CT | N | N | Y | Y | Issuer | b, var |
| 72 | Issuer Script Template 2 | CT | N | N | Y | Y | Issuer | b, var |
| 8A | Authorization Response Code | B | N | N | Y | Y | Issuer | an, 2 |
| 82 | Application Interchange Profile | B | Y | Y | Y | N | Chip | b, 2 |
| 84 | Dedicated File Name | B | Y | Y | Y | N | Chip | b, 5-16 |
| 91 | Issuer Authentication Data | CT | N | N | Y | Y | Issuer | b, 8-16 |
| 95 | Terminal Verification Results | B | Y | Y | Y | N | Terminal | b, 5 |
| 9A | Transaction Date | B | Y | Y | Y | N | Terminal | n6 YYMMDD, 3 |
| 9C | Transaction Type | B | Y | Y | Y | N | Terminal | n2, 1 |
| 5F24 | Application Expiration Date | B | Y | Y | Y | N | Chip | n6 YYMMDD, 3 |
| 5F2A | Transaction Currency Code | B | Y | Y | Y | N | Chip (Table 26: Terminal) | n3, 2 |
| 5F30 | Service Code | B | Y | Y | N | N | Chip | n3, 2 |
| 5F34 | PAN Sequence Number | B | Y | Y | Y | N | Chip | n2, 1 |
| 9F02 | Amount, Authorized | B | Y | Y | Y | N | Terminal | n12, 6 |
| 9F03 | Amount, Other | B | Y | Y | Y | N | Terminal | n12, 6 |
| 9F06 | AID (terminal) | B | Y | Y | N | N | Terminal | b, 5-16 |
| 9F07 | Application Usage Control | B | Y | Y | N | N | Chip | b, 2 |
| 9F09 | Terminal Application Version | B | Y | Y | Y | N | Terminal | b, 2 |
| 9F0A | Application Selection Registered Proprietary Data (ASRPD) | B | Y | Y | N | N | Chip | (Mastercard optional; mandatory for Visa Fleet 2.0 full-chip solution, §5.23) |
| 9F10 | Issuer Application Data | B | Y | Y | Y | N | Chip | b, up to 32 |
| 9F1A | Terminal Country Code | B | Y | Y | Y | N | Terminal | n3, 2 |
| 9F1E | IFD Serial Number | B | Y | Y | Y | N | Terminal | an, 8 |
| 9F24 | Payment Account Reference (PAR) | B | N | Y | N | Y | Issuer | issuer option |
| 9F26 | Application Cryptogram | B | Y | Y | Y | N | Chip | b, 8 |
| 9F27 | Cryptogram Information Data | B | Y | Y | Y | N | Chip | b, 1 |
| 9F33 | Terminal Capabilities | B | Y | Y | Y | N | Terminal | b, 3 |
| 9F34 | CVM Results | B | Y | Y | Y | N | Terminal | b, 3 |
| 9F35 | Terminal Type | B | Y | Y | Y | N | Terminal | n2, 1 |
| 9F36 | Application Transaction Counter | B | Y | Y | Y | Y | Chip | b, 2 |
| 9F37 | Unpredictable Number | B | Y | Y | Y | N | Terminal | b, 4 |
| 9F39 | POS Entry Mode | B | Y | Y | Y | N | Terminal | n2, 1 |
| 9F41 | Transaction Sequence Counter | B | Y | Y | Y | N | Terminal | n4-8, 2-4 |
| 9F53 | Transaction Category Code | B | Y | Y | Y | N | Terminal | an, 1 (required for Mastercard credit; optional for Mastercard debit) |
| 9F66 | Terminal Transaction Qualifiers | CTLS | Y | Y | Y | N | Terminal | b, 4 |
| 9F5B | Issuer Script Results | B | N | N | Y | N | Chip | brand-dependent (see [§16.2](#162-guide-internal-inconsistencies)) |
| 9F6E | Form Factor (Visa/Discover) / Device Type (Mastercard) / Third Party Data | B | Y | N | Y | N | Chip | b, 5-32 |
| 9F7C | Merchant Custom Data | CT | Y | Y | N | N | Terminal | ans, 20 |
| DF3F | Storage Data (D-PAS Connect) | B | Y | Y | Y | Y | Chip | brand-defined |

Table notes from the guide (§9.1, PDF pp.118-120):

- The table "is a guide reflecting current tag use expectations". Tag handling must not be hard-coded.
- "If it does not exist, do not create one with false or empty values." For all brands, "if the tag is there, send it and Fiserv will determine the applicable tags to each Brand". Issuers may return tags not listed.
- **Revision v2019.RG1 removed any mention of mandatory tags** (PDF p.211). Table 25 is therefore an expectation, not a mandatory list. Missing tags are `REVIEW_REQUIRED`, not FAIL.
- 9F53 values are hex-encoded ASCII: A (vehicle rental), C (cash disbursement), F (restaurant), H (hotel/cruise), O (college/hospital), R (retail/all other, `52`), T (mail/telephone order), U (unique), X (transportation), Z (ATM).
- 9F6E for Mastercard and Visa contactless: alphabetic values must be upper case, and an invalid form factor must not be sent. Discover contact and contactless: forward it if it is on the card. Table 26 says: "If this tag is present on the card, it must be present in the authorization message."
- Host draft-capture settlement sends all tags used in the authorization. Settlement files send the tags marked Y.
- 9F33 for JCB contactless sent through the Discover network is all zeros.

### 6.3 Tag rules

| Rule | Statement | Guide anchor | ATL105 anchor | Enforcement / status |
| --- | --- | --- | --- | --- |
| EMVIG-TAG-001 | Authorization-class Element 190 should contain the Table 25 "Auth" tags available on the card-terminal interface. If any Table 6 cryptogram-input tag (9F02, 9F03, 9F1A, 95, 5F2A, 9A, 9C, 9F37, 82, 9F36) or 9F26, 9F27 or 9F10 is missing, the chip data is likely incomplete. | Table 25 (p.118); Table 6 (p.52); BP3000 (p.120) | Element 190: "Required in all EMV transactions"; "exact values that were present on the card to terminal interface" (13-138) | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-002 | When a reversal carries EMV data, the expected tags are the "Reverse" column (same as Auth, except 9F6E is N and 9F24 may appear). Absence of EMV data is valid. | Table 25; RQ4600 (p.72) | §10.14.2.4 | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-003 | Completion, settlement and upload chip data follows the "Settle" column, including 8A, 91 and 71/72 when returned, and 9F5B. 4F, 5F30, 9F06, 9F07, 9F0A and 9F7C are not expected. Element 190 still needs 84 or 9F06 (ATL105), and 84 is "Settle = Y". | Table 25 and notes (pp.118-120) | Element 190 AID note | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-004 | Segment 131 chip data contains issuer or host tags only: 71, 72, 8A, 91, 9F24, 9F36, DF3F. Other issuer tags are INFO. Response chip data may be entirely absent ("all ... optional in the response message", §4.9, p.55). | Table 25; §4.9 | §12.21 (Segment 131) | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-005 | An authorization request must not carry issuer-sourced 71, 72 or 91, or 8A (Auth = N). | Table 25 | none | FAIL-candidate / `PROVISIONAL` |
| EMVIG-TAG-006 | 5A (PAN) and 57 (Track 2 equivalent) are never in Element 190. | Table 25 (neither listed) | Element 190 note (13-138); `BR-SEG100-APPR-PROHIBITED-SENSITIVE-TAG` | FAIL / `SOURCE_VERIFIED` (ATL105) |
| EMVIG-TAG-007 | Each present tag's value length must match Table 26. Key lengths: 82=2, 95=5, 9A=3, 9C=1, 9F02=6, 9F03=6, 5F2A=2, 9F1A=2, 5F24=3, 5F34=1, 9F26=8, 9F27=1, 9F36=2, 9F37=4, 9F33=3, 9F34=3, 9F35=1, 9F10≤32, 84/4F/9F06=5-16, 9F1E=8, 9F53=1, 9F7C=20, 91=8-16. For brand-dependent tags (see §3.1), the result is REVIEW only. | Table 26 (pp.121-138) | Element 190 "exact values" and TLV rules (13-138) | FAIL-candidate / `PROVISIONAL` |
| EMVIG-TAG-008 | The 9F27 cryptogram type must suit the message role: online authorization and SAF → ARQC (bits 8-7 = `10`, for example `80`); offline-approved upload → TC (`40`); online refund → AAC (`00`) or ARQC; reversal → as in Table 10; Voyager settlement → TC `40` or AAC `00`. | §4.8.2 (p.53); Table 42 (p.150); §5.11, §5.20, §14.3.5 | none | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-009 | Value domains: 9F35 ∈ {11-16, 21-26} (Table 1); 9F34 byte 1 method per Table 39 and byte 3 ∈ {00, 01, 02}; 9F53 ∈ hex-ASCII {A, C, F, H, O, R, T, U, X, Z}; 9A and 5F24 are valid YYMMDD; 5F2A and 9F1A are ISO numeric codes (Appendix L for currency). | Table 1 (p.27); Tables 39-41 (pp.149-150); §9.1 | Appendix L | FAIL-candidate (format) / REVIEW (domain) |
| EMVIG-TAG-010 | 84 or 9F06 is required (ATL105); 4F alone does not satisfy ATL105. When two or more of 84, 9F06 and 4F are present, their RIDs (first 5 bytes) must agree. Fiserv routing uses 84 first, then 4F, then 9F06; if none is present, the transaction is declined (BP2801). | BP2801 (p.120); Table 26 9F06 note ("only the RID portion of the DF name needs to be examined") | Element 190 AID note; `BR-SEG100-APPR-MANDATORY-IDENTIFIER` | FAIL for the ATL105 part / REVIEW for RID agreement |
| EMVIG-TAG-011 | TVR byte 4 bit 4 ("Merchant forced transaction online") set → suspicious. | RQ2500 (p.53); Table 57 (p.157) | none | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-012 | Tags must not be invented with false values. A zero-length TLV is tolerated: BP3000 allows it, while §9.1 says "do not create". | §9.1 (p.118); BP3000 (p.120) | Element 190 "exact values" | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-013 | If 9F6E is present, check length 5-32 (EBT list: 2) and upper-case values for Mastercard and Visa contactless. For Mastercard non-fleet, byte 3 bit 8 must be 0. For Mastercard fleet and Voyager, 9F6E carries Product Control / Third Party Data. | RQ4000 (p.64); §9.1 notes; Table 26 (pp.133-134); §14.3.3 | none | REVIEW / `PROVISIONAL` |
| EMVIG-TAG-014 | EBT (AID A0000000044542) chip data. Mandatory: 9F26, 9F27, 9F10, 9F33, 9F37, 9F36, 95, 9A, 9C, 9F02, 5F2A, 82, 84, 9F1A. Conditional: 9F34, 9F03, 5F34, 9F6E, 9F1E. The set must "meet the 255 field length". | §14.9 (pp.179-180) | Segment 103 with 130 permitted ([Segment 130 module](../specs/kb/segment-130/README.md), Do-Not-Assume 1) | FAIL-candidate (mandatory set) / `PROVISIONAL` |

## 7. Chip-to-ATL105 Cross-Field Rules

These rules supply the candidate formulas that `BR-SEG100-APPR-CROSS-FIELD` and `SEG130-SME-003` were missing. ATL105 has no single "transaction amount" element. Element 58 Nonfuel Amount is defined as "the transaction's dollar amount that is not a fuel, tax, or cash amount" ([extracted_text_new.txt](../specs/extracted_text_new.txt) line 22687). The candidate total is therefore built from Elements 41, 58, 99 and 17.

| Rule | Candidate formula / check | Guide anchor | ATL105 anchor | Enforcement / status |
| --- | --- | --- | --- | --- |
| EMVIG-XF-001 | **9F02 = E41 Fuel Purchase Amount + E58 Nonfuel Amount + E99 Tax Amount + E17 Cash Amount** (absent = 0), in the authorization-class request whose cryptogram was generated for that amount. Exceptions: completions reuse the original chip data (LC-006); contactless DCC uses the pre-DCC cryptogram amount (BP1200); Quick Chip / pre-tap placeholder amount (§5.17A); AFD pre-authorization (pump maximum amount, §5.15); discount applied before the 1st Generate AC (§5.5). | RQ3400 (p.62); §10.1.3 (9F02 = total before GPO, p.141) | Elements 17, 41, 58, 99 (lines 21899, 22342, 22687, 23699) | REVIEW / `PROVISIONAL` (derived formula) |
| EMVIG-XF-002 | **9F03 = E17 Cash Amount** when cash back is present; otherwise `000000000000`. Interac AID: 9F03 must be zero, with the total in 9F02 (RQ3700). E17 may include an Interlink cash-back fee. | RQ3500, RQ3700 (p.62) | Element 17 | REVIEW / `PROVISIONAL` |
| EMVIG-XF-003 | **5F2A = E20 Currency Code** (840 when E20 is absent). Under DCC it is the selected currency (RQ4200). Contactless DCC is REVIEW because the cryptogram precedes DCC (BP1200). | RQ4200, BP1200 (p.68) | Element 20; Appendix L | REVIEW / `PROVISIONAL` |
| EMVIG-XF-004 | **9F1A agrees with the terminal location**: a US state code in E102 (Appendix D) → `0840`; a Canadian province → `0124`. Territories and military codes need SME confirmation; the guide notes US territories are not the "US Region" (§2.1). | Table 26 9F1A; §2.1 (p.24) | Element 102; Appendices D, AC | REVIEW / `PROVISIONAL` |
| EMVIG-XF-005 | **9C agrees with Element 78 position 1 (Appendix G)**, per the table below. | Table 28 (p.144); Table 26 9C ("first two digits of the ISO 8583:1987 Processing Code") | Appendix G; §10.14.2.4 | REVIEW / `PROVISIONAL` |
| EMVIG-XF-006 | **E188 = 5F34** right-aligned and zero-padded to 3 digits when 5F34 is present (for example 5F34 `01` → `001`). E188 is three spaces when 5F34 is absent. | Table 25 (5F34 Auth/Reverse/Settle = Y); Table 26 (n2) | Element 188 (13-137): "supplied by the EMV chip card"; "If EMV Tag 5F34 is not provided by the card, send three spaces" | FAIL / `SOURCE_VERIFIED` (ATL105) |
| EMVIG-XF-007 | **9A = the local date the card was used at the POS.** For real-time requests it matches the device local date. For SAF/deferred it may precede the submission date; this is not a failure. | §4.8 (p.52); §5.20 (p.83) | Element 49 Local Date and Local Time, where present | REVIEW / `PROVISIONAL` |
| EMVIG-XF-008 | **Request expiration date (YYMM) = 5F24 YYMM.** The device does not validate expiry, so an expired 5F24 is not a request-format failure. | RQ1400 (p.47) | §10.14.2.1; Element 12 (expiration within the discretionary block), subject to the producer mapping boundary recorded in handbook history (2026-10-16) | REVIEW / `PROVISIONAL` |
| EMVIG-XF-009 | **9F39 agrees with the Appendix J PAN entry mode**: `05` for contact chip and `07` for contactless chip. | Table 25 9F39; Table 26 (p.128) | Appendix J (Element 111 Table 005 / Element 113) | REVIEW / `PROVISIONAL` |
| EMVIG-XF-010 | **9F33, 9F35 and 9F40 agree with the terminal context.** For example, 9F33 byte 2 bit 7 (enciphered PIN online) is consistent with Appendix J position 3 = `1`; CAT/AFD contexts use unattended types 24-26. The kernel configuration in 9F33 is fixed per certified device (RQ0800). | Table 1 (p.27); Table 44 (p.151); RQ0800, RQ4900 | Appendix J position 3 | REVIEW / `PROVISIONAL` |
| EMVIG-XF-011 | **Partial approval** (E83 = `F`, E6 Approved Amount below the requested amount): the completion or settlement amount equals E6. Chip 9F02 and 9F03 keep the cryptogram amounts. | §5.14 (p.75); RQ5100 (p.76) | Elements 6, 83 (`F`), 121 | REVIEW / `PROVISIONAL` |

### 7.1 Appendix G to Tag 9C mapping (EMVIG-XF-005)

| ATL105 §10.14.2.4 EMV transaction | Appendix G (Element 78 position 1) | Expected 9C (guide Table 28) | Notes |
| --- | --- | --- | --- |
| Purchase | `0` POS Purchase/Capture; `4` Customer-activated Purchase/Capture | `00`; **`09`** when E17 cash back > 0. `00` with cash back may be accepted subject to certification review. | Table 28 footnote |
| Purchase/Capture (completion of an Authorization Only) | `0` / `4` | Same 9C as the original pre-authorization | Chip data is reused (§5.16) |
| Authorization Only | `3` / `5` | `00` | Goods and services authorization |
| Merchandise Return | `7` | `20` | An AAC at the 1st Generate AC is normal (§5.11) |
| Purchase Reversal | `8` | Same as the original (`00` for goods and services: "Authorizations and Full Reversals") | EMV data is optional (§10.14.2.4) |
| Balance Inquiry | `E` | `31` | Must not be reversed (§5.2) |
| Cancellation | `S` | Same as the original authorization | Table 10 "Transaction Cancelled" uses 1st Generate AC data |
| Time-out Reversal | `Z` | Same as the original, when EMV data is present | 1st Generate AC data (no 2nd Generate AC occurred) |
| *Not listed in §10.14.2.4:* Account Verification | `A` | Guide §5.1 permits $0 EMV account verification | SPEC_PRECEDENCE: held as REVIEW (Q-EMV-05) |
| *Not ATL105 EMV types:* cash advance, force post | none | `01`, `45` | No §10.14.2.4 equivalent |

## 8. Lifecycle Rules

### 8.1 Table 10: EMV reversal types (guide §5.9, PDF p.72)

Reversals are required only when host authorization completed and the host approved the transaction (§5.9).

| Reversal reason | EMV tags in the reversal | Description |
| --- | --- | --- |
| PIN pad unavailable | 1st Generate AC | PIN pad disconnected or malfunctioning when the 2nd Generate AC is attempted |
| Transaction cancelled | 1st Generate AC | Cancelled by the merchant or cardholder, during or after host authorization |
| Card removed | 1st Generate AC | Card removed before the 2nd Generate AC |
| Chip error | 1st Generate AC | Card error during the 2nd Generate AC or External Authenticate. For Mastercard, the disposition follows the host decision. |
| Card decline | 2nd Generate AC | Card declined the host-approved transaction at the 2nd Generate AC |

**RQ4600:** EMV tags on EMV reversals are **conditional**. A reversal "may include tag data from either the 1st or 2nd Generate AC". The tag list "must match the original authorization message but must be updated to include 2nd Generate AC values if a 2nd Generate AC is performed". For Quick Chip, EMV tags on reversals are optional.

### 8.2 Lifecycle rules

| Rule | Statement | Guide anchor | ATL105 anchor | Enforcement / status |
| --- | --- | --- | --- | --- |
| EMVIG-LC-001 | A Purchase Reversal (`8`) or Time-out Reversal (`Z`) **without** Segment 130 is valid. | §5.9 (conditional) | §10.14.2.4: "EMV data is not required on Reversal transactions"; `SEG130-R-017`; `EMV-130-NEG-003` | PASS rule / `SOURCE_VERIFIED` (ATL105) |
| EMVIG-LC-002 | A reversal **with** EMV data uses the same tag list as the original authorization. Values are unchanged except those produced by a 2nd Generate AC (for example 9F26, 9F27, 9F10, 95) when one occurred. | RQ4600 (p.72) | §10.14.2.4 | REVIEW / `PROVISIONAL` |
| EMVIG-LC-003 | Reversal reason agrees with the cryptogram source (Table 10). A card decline after host approval → 2nd Generate AC data (9F27 = `00`). Every other reason, and every TOR → 1st Generate AC data (9F27 = ARQC). | Table 10 (p.72); RQ2900 (p.56) | Appendix G `8` / `Z` | REVIEW / `PROVISIONAL` |
| EMVIG-LC-004 | Reversals follow a host approval; a TOR is the timeout exception. | §5.9 (p.71) | Element 5 Approval Number ("Required on reversals") | REVIEW / `PROVISIONAL` |
| EMVIG-LC-005 | A Balance Inquiry (`E`) is an EMV transaction (all steps through script processing) and **must not be reversed**. | §5.2 (p.61) | §10.14.2.4 lists Balance Inquiry | REVIEW / `PROVISIONAL` |
| EMVIG-LC-006 | A completion (`0`/`4` after `3`/`5`) carries **the same chip data and the same POS entry mode** as the original pre-authorization, plus the settlement indicator returned in the pre-authorization response. Under Quick Chip, completion chip data comes from the 1st Generate AC. | §5.15 (p.76); §5.16 (pp.77-78); §4.12.1 (p.57) | Appendix G; P2 Authorization-to-Completion training (handbook history 2026-10-16) | FAIL-candidate (entry-mode change) / REVIEW (chip-data equality; settlement-indicator mapping is Q-EMV-07) |
| EMVIG-LC-007 | Incremental authorizations and partial reversals are **keyed**, with no EMV tags and no other card data from the original (RQ4700). Completion uses the initial authorization's EMV tags. The guide notes this is platform-dependent. | §5.10 (pp.72-73); RQ4700 (p.73) | Appendix AE / AF overlays (incremental authorization) | REVIEW / `PROVISIONAL`: reconcile with AE/AF first (Q-EMV-06) |
| EMVIG-LC-008 | SAF / deferred authorization uses the same format as real time, with **1st Generate AC chip data (ARQC)**; the 2nd Generate AC (AAC) is discarded. 9A is the original card-use date. **9F36 may be out of sequence.** | §5.20 (p.83); BP1801 (p.84) | Chapter 8 Store-and-Forward | REVIEW / `PROVISIONAL` |
| EMVIG-LC-009 | Offline chip approvals: Fiserv currently supports only Mastercard offline processing. The upload carries TC (9F27 = `40`) and 8A = `Y1` (`Z1`, `Y3`, `Z3` for the other outcomes). A non-Mastercard offline-approved upload → REVIEW. | §4.10 (pp.56-57); Table 8 | Chapter 8 | REVIEW / `PROVISIONAL` (Q-EMV-09) |
| EMVIG-LC-010 | A chip Merchandise Return (`7`): 9C = `20`. A 1st Generate AC AAC (9F27 = `00`) is normal and no 2nd Generate AC follows. An ARQC is followed by a 2nd Generate AC after the response. Entry mode is chip. A Mastercard contactless refund requires AAC (RQ4701). Visa requires a refund authorization before settlement. | §5.11 (pp.73-74); RQ4701 (p.74) | §10.14.2.4 Merchandise Return | REVIEW / `PROVISIONAL` |
| EMVIG-LC-011 | Partial approval accepted: 8A = `0x3130` ("10") before the 2nd Generate AC. If the cardholder declines it: AAC at the 2nd Generate AC, **a reversal is sent**, and 8A = `0x3035` (RQ5000). Settlement uses the partially approved amount (RQ5100). | §5.14 (pp.75-76) | Element 83 `F`; Elements 121, 6 | REVIEW / `PROVISIONAL` |
| EMVIG-LC-012 | Host approved, but the card was removed after the 1st Generate AC and before the 2nd: send a reversal with 1st Generate AC data, then cancel. Not applicable to Quick Chip or contactless. | RQ2900 (p.56) | none | REVIEW / `PROVISIONAL` |
| EMVIG-LC-013 | Referral response: the terminal requests AAC at the 2nd Generate AC (RQ5200), then the voice-authorization approval code is attached to the original. | §5.18 (p.81) | ATL105 referral representation not mapped | INFO |
| EMVIG-LC-014 | $0 account verification: "No reason to decline" plus Tag 91 may lead to an AAC at the 2nd Generate AC. This is not a failure, and the reversal is optional. | §5.1 (p.61) | Not listed in §10.14.2.4 | SPEC_PRECEDENCE: REVIEW (Q-EMV-05) |
| EMVIG-LC-015 | EMV debit has no merchant stand-in: it goes online or is declined (RQ5300). | §5.19 (p.82) | none | INFO / REVIEW |
| EMVIG-LC-016 | 1st and 2nd Generate AC values must not be mixed in one message. This is detectable only when the Generate AC capture context is supplied. | §4.12.1 (p.58) | none | REVIEW / `PROVISIONAL` |
| EMVIG-LC-017 | Tip: the tip is added before the 1st Generate AC and settled as the total authorized. A CAID with an unknown tip needs the Final Amount Indicator. | §5.21 (p.84) | none | INFO |

## 9. AID, Brand, Routing and CVM Rules

### 9.1 ATL105 product-to-AID bridge (closes the SME-008/012 path)

ATL105 §10.14.2.3 names the EMV products by brand only. The Appendix E table-load list carries no codes for the bank-card brands. EMV product identity is therefore taken from the AID in Element 190 (84 or 9F06). Appendix C authorizer codes (Element 7) identify the network that authorized the transaction. They are the ATL105 counterpart of the guide's "Network Label/ID" (RQ5500) and of the §10.14.2.5 single/dual disposition.

| §10.14.2.3 product | Guide AID(s) (RID + PIX) | AID type / message | ATL105 code evidence (KB-transcribed) |
| --- | --- | --- | --- |
| American Express | A00000002501 (Global Credit); A00000002504 (US Common Debit) | Dual | Appendix C `01` |
| Discover | A0000001523010 D-PAS (Global Credit); A0000003241010 ZIP (Global Credit); A0000001524010 (Common Debit). Discover terminals must also accept JCB A0000000651010, UnionPay and Diners (RQ1102). | Dual (UnionPay Debit single) | Appendix C `04` |
| MasterCard | A0000000041010 | Dual | Appendix C `03` |
| Visa | A0000000031010; A0000000032010 (Electron) | Dual | Appendix C `02` |
| STAR Signature Debit | No own AID; reached through US Common Debit AIDs (§10.14.1: Common AIDs are "licensed to other debit networks (like Star, NYCE, Pulse, etc.)") | Dual (CAID) | Appendix C `84` / `85` (STAR single/dual message signature) |
| Interlink | A0000000033010 | Single; no PIN bypass | Appendix C `48` |
| Maestro | A0000000043060 (Global Debit); A0000000042203 US Maestro (Common Debit) | Single / Dual | Appendix C `40` (Maestro Direct) |
| STAR West / Southeast / Northeast | Through Common Debit AIDs | Dual (CAID) or single (DNA) | Appendix C `23` / `07` / `17` |
| NYCE | Through Common Debit AIDs. Interac cash back routes to NYCE (Table 9). | Dual (CAID) or single | Appendix C `28` |
| PULSE | Through Common Debit AIDs. DNA cash back excludes PULSE (Table 9). | Dual (CAID) or single | Appendix C `06` |
| Accel | Through Common Debit AIDs | Dual (CAID) or single | Appendix C `19` / `69` |
| Shazam | Through Common Debit AIDs | Dual (CAID) or single | Appendix C `58` |
| Generic Proprietary ("Some issuers participating") | Candidates (Chapter 14): ExxonMobil/Citi A0000000049999D8400320 / …21; Sunoco/Citi …24 / …25 / …26; Wawa/Citi …23; Fleetcor family: Fuelman/FleetWide A0000000049999C000170001, 2in1 …0002, 2in1 6 PID …0003, Comdata …0301 | Dual/Credit | Appendix E `056` Generic proprietary, `052` ExxonMobil, `085` Sunoco, `081` Fuelman/Gascard (partial table) |
| Voyager | A0000000049999C00016 (extension `YYYY`) | Dual/Credit | Appendix E `060`; Appendix C `94` |
| Wright Express | A0000007681010 (WEX) | Dual/Credit | Appendix E `059`; Appendix C `72` (Wright Express), `57` (WEX ISO) |

**US Common Debit AIDs:** ATL105 §10.14.1 lists four: Visa Common AID, US Maestro, DNA Shared Debit and Discover Common AID. Guide Table 12 adds Amex A00000002504 and UnionPay A000000333010108. Treat the two additions as SPEC_PRECEDENCE / REVIEW (Q-EMV-08).

### 9.2 Supported AID profile (guide Tables 4, 9 and 24)

Contactless (CL) PIN bypass and contact PIN bypass come from Table 4. CL CVM limit is the **test** value from Table 24, which the brand sets and may change.

| AID | Application | AID type / message | PIN bypass (CL / contact) | Cash back (routing) | 9F09 | Test CL CVM limit |
| --- | --- | --- | --- | --- | --- | --- |
| A00000002501 | Amex | Global Credit / Dual | Y / Y | N | 0001 | $10.00 |
| A00000002504 | Amex US Common Debit | Common Debit / Dual | Y / Y | N/A in Table 9 | 0001 | $200.01 |
| A0000001524010 | Discover Common AID | Common Debit / Dual | Y / Y | Y (Discover or any debit) | 0001 / 0002 | $50.00 |
| A0000003241010 | Discover ZIP | Global Credit / Dual | N / N | N | 0001 | $50.00 |
| A0000001523010 | Discover D-PAS | Global Credit / Dual | Y / Y | Y (Discover) | 0001 / 0002 | $50.00 |
| A0000006200620 | DNA Shared Debit | Shared Debit / **Single** | N / Y (RQ2300: no bypass) | Y (any debit except Maestro, Interlink, PULSE) | 0001 | N/A |
| A0000002771010 | Interac | Global Debit / Single | N / Y (RQ2300: no bypass) | Y (NYCE); 9F03 must be 0 | 0001 | N/A |
| A0000000651010 | JCB | Global Credit / Dual | N / Y | N | 0200 / 0120 | $50.00 |
| A0000000041010 | Mastercard | Global Credit / Dual | N / Y | N | 0002 | $100.00 |
| A0000000043060 | Maestro | Global Debit / Single | N / Y (RQ2300: no bypass) | Y (Maestro) | 0002 | $0 or $100 (PINless) |
| A0000000042203 | US Maestro | Common Debit / Dual | Y / Y | Y (any debit) | 0002 | $100.00 |
| A000000333010102 | UnionPay Credit | Global Credit / Dual | Y / Y | N | 0020 / 0030 | $50.00 |
| A000000333010103 | UnionPay Quasi Credit | Global Credit / Dual | Y / Y | N | 0020 | $50.00 |
| A000000333010101 | UnionPay Debit | Global Debit / Single | N / Y (RQ2300: no bypass) | N | 0020 | $50.00 |
| A000000333010108 | UnionPay US Common Debit | Common Debit / Dual | Y / Y (RQ2300: no bypass) | N | 0020 | $0.00 |
| A0000000031010 | Visa Credit/Debit | Global Credit / Dual | Y / Y | N | 00A0 / 0096 | $25.00 |
| A0000000032010 | Visa Electron | Global Credit / Dual | Y / Y | N | 00A0 / 0096 | $25.00 |
| A0000000033010 | Visa Interlink | Global Debit / Single | N / N | Y (Interlink) | 00A0 / 0096 | $0.00 |
| A0000000980840 | Visa US Common Debit | Common Debit / Dual | Y / Y | Y (any debit) | 00A0 / 0096 | $25.00 |
| A0000000044542 | EBT | EBT / Single | none | none | none | none |

**Partial selection:** Partial selection (matching RID + PIX and allowing an issuer suffix) is mandatory (RQ1100). The exception is UnionPay, whose AIDs are 16 digits and must match exactly. **Contactless CVM limit for AFD is $0** for all brands (§5.4, §5.15).

### 9.3 AID rules

| Rule | Statement | Guide anchor | ATL105 anchor | Enforcement / status |
| --- | --- | --- | --- | --- |
| EMVIG-AID-001 | The AID in 84 or 9F06 is on the supported list (Tables 4 and 63) under partial selection. An unknown AID → REVIEW: "Any AID that has not been approved ... must be ignored ... without being sent to the host" (BP0300). | §4.2 (pp.43-45); RQ1100; Table 63 (p.168) | §10.14.2.3 | REVIEW / `PROVISIONAL` |
| EMVIG-AID-002 | Message type follows the AID type. Global Credit and Common Debit (except DNA) → credit/dual. Global Debit (Interac, Maestro A0000000043060, Interlink, UnionPay Debit) and DNA → debit/single. 17-19 digit PANs → debit/single only (RQ2600). CDCVM → dual. | Table 13 (p.87); RQ2600 (p.54); §5.4.2 (p.64) | §10.14.1: Visa and US Maestro CAID as "credit / 0100 / Dual message"; DNA as "debit / 0200 / single message"; Prompt Code card type `011` = debit | REVIEW / `PROVISIONAL` |
| EMVIG-AID-003 | **No PIN bypass** on Maestro A0000000043060, Interlink A0000000033010, Interac A0000002771010, UnionPay Debit A000000333010101, DNA A0000006200620 or UnionPay US Common Debit A000000333010108: the host declines. No PIN bypass on Mastercard CAT or contactless (allowed on US AFD). UnionPay global debit and CAID must be PIN-based. | RQ2300, RQ2301 (p.51); §4.6.2 (p.50) | §10.14.3 (PIN debit: a PIN or all F's is mandatory) | Negative scenario: expected decline / `PROVISIONAL` |
| EMVIG-AID-004 | Cash back only on Table 9 "Y" AIDs (RQ3600), included in 9F02 and 9F03 (XF-001 and XF-002). No cash back on UnionPay CAID or on PINless debit. | Table 9 (pp.61-62); RQ3400-RQ3700 | §10.14.1: First Data does not support cash back for Visa or MasterCard credit / signature debit; CAID credit with cash back is routed to single-message debit, except Discover | REVIEW / `PROVISIONAL` |
| EMVIG-AID-005 | **PAVD:** Visa US Common Debit (A0000000980840) with an online PIN must not be routed to the Visa dual network (RQ5600). When final amount is unknown or debit is not entitled → No CVM. **POS PINless:** CAID only; No CVM; no PIN block; credit/dual; not MCC 5542; no cash back; not UnionPay CAID. CAID credit on MCC 5542: no Final Amount Indicator unless AVS (RQ5101 / RQ5102). | §5.22.3-5.22.4 (pp.88-89); RQ5600 (p.89); RQ5101 (p.77) | §10.14.1 | REVIEW / `PROVISIONAL` |
| EMVIG-AID-006 | CAID auto-selection: when a US Common Debit AID is on the card it must be selected (RQ1300). This is device behavior; the message shows the CAID. | RQ1300 (pp.46, 85); Table 12 | §10.14.1 | INFO |
| EMVIG-AID-007 | Discover acceptance includes JCB, UnionPay and Diners AIDs (RQ1102). ATL105 §10.14.2.3 lists only Discover. Classify those chips under Discover acceptance. | RQ1102 (p.45) | §10.14.2.3 | REVIEW / `PROVISIONAL` |
| EMVIG-AID-008 | **Voyager:** Mastercard RID and **Mastercard CA keys**. No cash back, no Quick Chip, **no contactless**. CVM: online enciphered PIN and No CVM are mandatory; no offline PIN; signature is mandatory only when attended. A blocked card terminates the transaction (no fallback). Settlement uses the 2nd Generate AC 9F26 with 9F27 `40` or `00`. Enhanced fleet prompting: 9F6E unique identifier `0003`/`8003` and product identifier `0001`. | §14.3 (pp.169-173); RQ7201-RQ7205 | §10.14.2.3 Voyager; Appendix E `060` | REVIEW / `PROVISIONAL` |
| EMVIG-AID-009 | **WEX:** credit only, dual/credit, WEX-supplied TACs, brand-approved test tool. Fleet prompt tags DF30/DF31; their values are in a "Petroleum Implementation Guide" that was not supplied. | §14.4 (pp.173-174); Table 66 | §10.14.2.3 Wright Express; Appendix E `059` | REVIEW / `PROVISIONAL` |
| EMVIG-AID-010 | **PLCC** (ExxonMobil/Citi, Sunoco/Citi, Wawa/Citi): Mastercard RID, credit only, no PIN, not offline, **no CAPK index**. Sunoco supports contactless from June 2023; the others have no contactless. **Fleetcor / Fuelman / Comdata:** dual only; CVM list `1F00` (No CVM); no ARPC; always online; floor limit 0; no issuer scripts. | §14.5-14.8 (pp.175-179) | §10.14.2.3 Generic Proprietary | REVIEW / `PROVISIONAL` |

## 10. Entry Mode and Fallback Rules

| Guide term | ATL105 Appendix J PAN entry mode | Segment 130 |
| --- | --- | --- |
| Contact chip read | `05` (ICC read, CVV data reliable) | Present |
| Contactless chip read | `07` | Present |
| Chip fallback to swipe (RQ4400, "ex. '80'") | `80` | **Absent** (`SEG130-R-019`) |
| Chip fallback to manual | `79` (the guide is silent; §10.14.2.2 allows manual entry) | Absent |
| Non-chip swipe, or a chip card processed as ordinary MSR after fallback is cancelled (RQ4500) | `90` | Absent |
| Contactless magnetic stripe (MSD) | `91`. Visa no longer supports MSR contactless (§2.4). | Absent |

| Rule | Statement | Guide anchor | ATL105 anchor | Enforcement / status |
| --- | --- | --- | --- | --- |
| EMVIG-FB-001 | A chip card swiped at an EMV-enabled device after a failed chip read uses entry mode `80`, with no Segment 130. | RQ4300, RQ4400 (p.69) | §10.14.4; Appendix J `80` | FAIL / `SOURCE_VERIFIED` (ATL105) |
| EMVIG-FB-002 | Segment 130 present with entry mode `80` → contradiction. | §5.8 | `EMV-130-NEG-002` | FAIL / `SOURCE_VERIFIED` (ATL105) |
| EMVIG-FB-003 | A track service code starting `2` or `6` with entry mode `90` at a chip-capable terminal → probable bypassed chip or mis-coded fallback (liability shift). For Voyager, the service code is at Track 2 position 31 / Track 1 position 58. | RQ0900, RQ4800, RQ4900 (pp.42, 75); §14.3.3 (p.172) | §10.14.4 status table | REVIEW / `PROVISIONAL` |
| EMVIG-FB-004 | Discover: an empty candidate list or unknown AID must be coded as fallback, not as magnetic stripe (02/90). | §5.8 Discover note (p.70) | Appendix J | REVIEW / `PROVISIONAL` |
| EMVIG-FB-005 | **Fallback is not allowed** when: the chip is blocked; the application is blocked with no other AID; the card declined offline (AAC at the 1st Generate AC); the card was removed prematurely; the network forbids fallback; the transaction was cancelled; the terminal is unattended for some networks (Amex). Also not on offline-only CATs (terminal type 26; RQ4401), Amex on CATs (RQ4402), or a blocked Voyager card (RQ7201). | §5.8.1 (pp.70-71) | §10.14.4 | REVIEW (session context needed) / `PROVISIONAL` |
| EMVIG-FB-006 | Fallback cancellation: if an EMV card is then inserted or tapped, process as EMV. If the swipe shows a service code other than 2xx/6xx, process as ordinary MSR (`90`). | RQ4500 (p.71) | Appendix J | REVIEW / `PROVISIONAL` |
| EMVIG-FB-007 | EBT fallback is supported. | §14.9 (p.179) | Segment 103 | INFO |

## 11. Response Chip Data and Tag 8A Rules

### 11.1 Table 7: Tag 8A values for the 2nd Generate AC (guide §4.9, PDF p.55)

The ATL105 Element 83 column is a **candidate** mapping (Q-EMV-10). Table 7 uses front-end-generic host codes; BuyPass Element 83 uses one-character codes ([Element 83 enumeration](../specs/kb/13-data-elements.md)).

| Host decision (Table 7) | 8A (ASCII / hex) | Candidate ATL105 Element 83 code(s) |
| --- | --- | --- |
| Approved `00`/`000`; approved with ID `001`; honor with ID `08`/`708`; no reason to decline `85`/`785` | `00` / `0x3030` | `0` Purchase/Capture, `2` Authorization only, `3` Authorization only with AVS, `4` Purchase/Capture with AVS |
| Partial approval `10`/`002` | `10` / `0x3130` | `F` Partial approval |
| Declined `05`/`51`/`51x`; any other issuer decision | `05` / `0x3035` | `1` Declined (financial context), `S` Declined, retry |
| Offline approved | `Y1` / `0x5931` | Offline upload context (Chapter 8) |
| Offline declined | `Z1` / `0x5A31` | Not sent online |
| Unable to go online, offline approved | `Y3` / `0x5933` | Offline upload context |
| Unable to go online, offline declined | `Z3` / `0x5A33` | Not sent online; pre-tap AAC request (§5.17) |

### 11.2 Response rules

| Rule | Statement | Guide anchor | ATL105 anchor | Enforcement / status |
| --- | --- | --- | --- | --- |
| EMVIG-RSP-001 | Response chip data is optional. An issuer may return no chip data; its absence is not a failure. | §4.9 (p.55) | §12.21 Segment 131 | PASS rule / `PROVISIONAL` |
| EMVIG-RSP-002 | When the host does not return 8A, the device sets it from the host response code before the 2nd Generate AC (RQ2700). The 8A in a later completion or advice must agree with the Element 83 outcome (§11.1). | RQ2700 (p.56); Table 7 | Element 83 | REVIEW / `PROVISIONAL` |
| EMVIG-RSP-003 | A host response never includes **both** 71 and 72, and carries at most one issuer script. Devices must support scripts up to 128 bytes (RQ3100). 71 runs before the 2nd Generate AC and 72 after. Script results (9F5B) are carried forward in settlement. | §4.12.2 (p.58); RQ2800, RQ3000-RQ3200 | §12.21 | FAIL-candidate (both present) / REVIEW (length > 128) |
| EMVIG-RSP-004 | 91 Issuer Authentication Data is 8-16 bytes. External Authenticate applies when AIP byte 1 bit 3 is set. Under Quick Chip, 91 and scripts never reach the card. | Table 26; §4.11 (p.57) | §12.21 | FAIL-candidate (length) / INFO |
| EMVIG-RSP-005 | Routing disclosure: the response must tell the device which network authorized a CAID dual-message transaction (Network Label/ID, alternate routing indicators) so it settles correctly. On BuyPass this corresponds to the Appendix C authorizer code (Element 7) and the §10.14.2.5 single/dual disposition. | RQ5500 (p.88); §5.22.2 | §10.14.2.5; Appendix C | REVIEW / `PROVISIONAL` |
| EMVIG-RSP-006 | $0 account verification with "No reason to decline" plus a 91 whose last two bytes are `3835`: the chip may decline. This is correct behavior, not a defect. | §5.1 (p.61) | none | INFO |

## 12. CA Public Key (CAPK) Rules

### 12.1 ATL105 Appendix S compared with guide Tables 14 and 15

| Field | ATL105 Appendix S (2026-4, PDF pp.698-699) | Guide Tables 14/15 (PDF pp.94-95) | Test Solution decision |
| --- | --- | --- | --- |
| Header File Name | AN, max 8, fixed `CA_KEYS` | A/N 7, `CA_KEYS` | `CA_KEYS` exactly (unchanged) |
| Header File Version | N 4 (for example `0100`) | Numeric 4, `0100` | Unchanged |
| Expiry Date | N 8, MMDDYYYY | Numeric 8, MMDDYYYY. The guide's own example, `06312014`, is not a valid calendar date. | Keep the structural calendar check. A past date is **not** a failure (BP2301 / BP2304). |
| Hash Algorithm Indicator | N 2, `01` (SHA-1) | `01` only | Unchanged |
| Public Key Algorithm Indicator | N 2, `01` (RSA) | `01` only | Unchanged |
| RID | AN 10 (for example `A000000003`) | A/N 10 | **Exactly 10 hex characters.** The KB currently accepts 1-10 (B-08). |
| Index | Hex 2 | Hex 2; "identifies the CA Public Key in conjunction with the RID" | Unchanged |
| Modulus | Hex, variable | Hex, variable, **maximum 496** | Add the 496 hex-character maximum (248 bytes) (B-08) |
| Exponent | N, 2 or 6: "03 or 216+1" (the superscript was lost in extraction) | **`03` or `010001`** ("3" or "2^16+1") | **Use `03` or `010001`.** The KB value `065537` is incorrect (B-01). |
| Check Sum | Hex 40, SHA-1 over RID, Index, Modulus and Exponent | Same | Recompute over hex-decoded bytes. Exponent 65537 is the bytes `01 00 01`. |
| Records | Comma-separated, CRLF-terminated | Same | Unchanged |

**Why the exponent correction matters:** the Appendix S oracle hex-decodes every component before computing SHA-1. Decoding `065537` gives the bytes `06 55 37`, which is not the RSA exponent 65537. A genuine brand or Fiserv CAPK record would therefore never verify against the current value domain. Meanwhile, a synthetic fixture built on `065537` passes. This is a false-pass risk (R007).

### 12.2 CAPK rules

| Rule | Statement | Guide anchor | ATL105 anchor | Enforcement / status |
| --- | --- | --- | --- | --- |
| EMVIG-CAPK-001 | Exponent is `03` or `010001`; any other value fails. The checksum is SHA-1 over hex-decoded RID, Index, Modulus and Exponent. | Table 15 (pp.94-95) | Appendix S | FAIL / `SOURCE_VERIFIED` (ATL105 + guide + arithmetic) |
| EMVIG-CAPK-002 | RID is exactly 10 hex characters. Index is exactly 2 hex characters. Modulus is non-empty, even-length hex of at most 496 characters. | Table 15 | Appendix S (RID AN 10) | FAIL-candidate / `PROVISIONAL` |
| EMVIG-CAPK-003 | At most **6 active CA public keys per card scheme (RID)**. | §6.1 (p.93) | Element 194: the file "contains all active CA Public Keys" | REVIEW / `PROVISIONAL` |
| EMVIG-CAPK-004 | A downloaded file **completely replaces** the previous file: delete the old file and activate the new one, with no merge. | §6.1, §6.3 (pp.93-95) | Element 194: "completely replaces the key file previously stored at the device" | Lifecycle REVIEW: subsequent Element 187 equals the new file checksum (existing `12.22-CAPK-008`) |
| EMVIG-CAPK-005 | Update signaling. Guide "U" (update available) = ATL105 **Element 30 = `9`**, sent when the request checksum (Element 187) does not match the host. Guide "N" (no update) = Element 30 = `0`, or a load answered "RQST/CURRENT HASH MATCH" (Element 83 `X`, "checksum matches host"). | §6.1.1 (p.93) | Element 30 (13-40/41); Element 194 error codes; Element 83 `L`/`M`/`X` (EMV key load) | REVIEW / `PROVISIONAL` |
| EMVIG-CAPK-006 | Key selection: key = (RID of the selected AID, i.e. the first 10 hex characters of 84/9F06, and CA PK Index tag 8F from the card). Voyager uses Mastercard keys. ExxonMobil, Sunoco and Wawa PLCC AIDs have no CAPK index, so no offline data authentication. Tag 8F is not in Element 190, so only RID coverage is observable: the loaded file should hold at least one key per RID that is accepted for offline data authentication. | Table 15; Table 26 8F (p.125); §14.3, §14.5 | Appendix S; `BR-SEG100-APPS-CA-EMV-LINK` | REVIEW / `PROVISIONAL` |
| EMVIG-CAPK-007 | **Test and production CAPK sets are not interchangeable.** The repository holds only synthetic or test keys. Go-live loads production CAPK (§3.2 step 23). | §6.1.1 (p.93); Table 2 | R003 synthetic-data gate | FAIL (process) |
| EMVIG-CAPK-008 | Download from a single data center (no round robin). New keys must be implemented within 6 months of notification. Expiry dates should not be held in the terminal. Expired keys are not deleted until a new file arrives. | §6.3, BP2301, BP2304 (pp.95-96) | none | INFO |

## 13. Requirement (RQ) Traceability Index

Classes:

- **MSG**: observable in an ATL105 request or response
- **LC**: multi-message lifecycle
- **CAPK**: CA public key file or load
- **DEV**: terminal or kernel behavior, not observable in the message
- **CERT**: certification or process
- **RCPT/RPT**: receipt or report output

| RQ | Requirement (summary) | § / PDF page | Class | Test Solution rule |
| --- | --- | --- | --- | --- |
| RQ0100 / RQ3800 | MSR contactless support requires EMV contactless | 2.4 / 27; 5.4 / 63 | DEV | Context (entry mode `91`) |
| RQ0200 | A non-zero floor limit must be certified with a non-zero floor limit | 2.6 / 28 | CERT | none |
| RQ0300 | Features must be MSR-certified before EMV | 2.6 / 29 | CERT | none |
| RQ0400 / RQ0500 / RQ0600 | EMVCo Bulletin #11; per-kernel-configuration certification; notify all kernel configurations | 3.1-3.2 / 33-34 | CERT | none |
| RQ0700 | Documentation (L1/L2, PCI PTS, Mastercard TQM); L3 grace period of 2 years for Visa, 1 year for others | 3.2 / 34 | CERT | none |
| RQ0800 | Brand certification per PIN pad kernel configuration (fixed in 9F33) | 3.2 / 34 | CERT | XF-010 context |
| RQ0801 | Settlement and back-office validation is mandatory | 3.2 / 34 | CERT / LC | Completion scenarios |
| RQ0900 / RQ4800 | Service code 2xx/6xx must force chip use | 4.1 / 42; 5.13 / 75 | DEV | FB-003 |
| RQ1000 | Only one card interface active once a read begins | 4.1 / 42 | DEV | none |
| RQ1100 | Partial AID selection is mandatory (except UnionPay) | 4.2 / 43 | DEV | AID-001 matching |
| RQ1101 | No Canadian ASF on US terminals (Interac co-badge) | 4.2 / 43 | DEV | none |
| RQ1102 | Discover terminals accept JCB, UnionPay and Diners AIDs | 4.2 / 45 | DEV | AID-007 |
| RQ1200 | Application list is shown to the cardholder only | 4.2 / 46 | DEV | none |
| RQ1300 | Auto-select the CAID when present | 4.2 / 46; 5.22 / 85 | DEV | AID-006 |
| RQ1400 | Expiration date taken from 5F24 for all authorizations; 5F24 forwarded in chip data | 4.3 / 47 | MSG | XF-008, TAG-001 |
| RQ1500-RQ1800 | Offline authentication failure or not performed → online; all of SDA/DDA/CDA; CDA mode 1 | 4.5 / 48 | DEV | none |
| RQ1900 | PIN support means both online and offline PIN; UCAT offline plaintext PIN sunset (Visa 2026-01-01 new / 2027-01-01 all; Mastercard unattended 2025-01-01, attended 2027-06-01) | 4.6 / 49 | DEV | Corroborates §10.14.3 |
| RQ2000 / RQ2001 | Signature CVM retained (attended); Visa unattended AFD signature CVM in TTQ | 4.6 / 49 | DEV | none |
| RQ2100 / RQ2200 | Offline PIN tries ≤ 9F17; last-try notice | 4.6.1 / 50 | DEV | none |
| RQ2300 / RQ2301 / RQ2302 | No PIN bypass on the listed debit AIDs; none on Mastercard CAT/contactless; PIN bypass at AFD | 4.6.2 / 51 | MSG | AID-003 |
| RQ2400 | Terminal risk management supported | 4.7 / 52 | DEV | none |
| RQ2500 | Do not force transactions online (TVR byte 4 bit 4) | 4.8.1 / 53 | MSG | TAG-011 |
| RQ2600 | Global Debit AIDs and 17-19 digit PANs not sent as dual message | 4.9 / 54 | MSG | AID-002 |
| RQ2601 | AID/CVM negotiation before BIN-range checks | 4.9 / 54 | DEV | none |
| RQ2700 | Set 8A from the host response code before the 2nd Generate AC | 4.9 / 56 | LC | RSP-002 |
| RQ2800 / RQ3000 / RQ3100 / RQ3200 | Issuer scripts supported; executed on approval and decline; up to 128 bytes; errors tracked in 9F5B | 4.9 / 56; 4.12.2 / 58 | MSG / DEV | RSP-003, TAG-003 |
| RQ2900 | Host approved but card removed before the 2nd Generate AC → reversal and cancel | 4.9 / 56 | LC | LC-012 |
| RQ3400 / RQ3500 | 9F02 includes cash back; 9F03 equals cash back | 5.3 / 62 | MSG | XF-001, XF-002 |
| RQ3600 / RQ3700 | Cash back controlled by AID; Interac 9F03 = 0 | 5.3 / 62 | MSG | AID-004, XF-002 |
| RQ3900 | Contactless devices support the latest network specifications | 5.4 / 63 | CERT | none |
| RQ4000 | Form factor / device type (9F6E) supplied (EMV contactless; Discover contact and contactless; Mastercard fleet) | 5.4 / 64 | MSG | TAG-013 |
| RQ4100 | Contactless devices support CDCVM | 5.4.2 / 65 | DEV | none |
| RQ4200 / RQ4201 | 5F2A is the DCC currency; no DCC at or below the contactless CVM limit | 5.6 / 68 | MSG | XF-003 |
| RQ4300 / RQ4400 | Fallback accepts service code 2xx/6xx; fallback identified (entry mode `80`) | 5.8 / 69 | MSG | FB-001 |
| RQ4401 / RQ4402 | No MSR/fallback on offline-only CATs (type 26); no Amex fallback on CATs | 5.8.1 / 71 | MSG (context) | FB-005 |
| RQ4500 | Cancel fallback on chip insert/tap or a non-2xx/6xx service code | 5.8.2 / 71 | DEV / MSG | FB-006 |
| RQ4600 | Reversal EMV tags are conditional; same list as the original, updated for the 2nd Generate AC | 5.9 / 72 | LC | LC-002, LC-003 |
| RQ4700 | Incremental authorization and partial reversal are keyed, with no EMV data | 5.10 / 73 | LC | LC-007 |
| RQ4701 | Mastercard contactless refund requires AAC | 5.11 / 74 | MSG | LC-010 |
| RQ4900 | Each device identifies chip capability correctly | 5.13 / 75 | MSG (context) | XF-010, FB-003 |
| RQ5000 / RQ5100 | Declined partial approval → AAC, reversal, 8A `05`; settle at the partial amount | 5.14 / 76 | LC | LC-011, XF-011 |
| RQ5101 / RQ5102 | CAID credit on MCC 5542: no Final Amount Indicator unless AVS (duplicate RQs) | 5.15-5.16 / 77-78 | MSG (context) | AID-005 |
| RQ5200 | Referral → AAC at the 2nd Generate AC | 5.18 / 81 | DEV | LC-013 |
| RQ5300 / RQ5400 | EMV debit always online; offline approvals are client liability | 5.19 / 82-83 | LC | LC-015, LC-009 |
| RQ5401 | Support all debit AIDs when debit is entitled | 5.22 / 85 | CERT | none |
| RQ5500 | Network label / alternate routing in the response (CAID dual) | 5.22.2 / 88 | MSG | RSP-005 |
| RQ5600 | Visa CAID with online PIN must not route to Visa (PAVD) | 5.22.4 / 89 | MSG | AID-005 |
| RQ5700 / RQ5701 | CAPK download supported; per-device installation process | 6.1, 6.3 / 94, 96 | CAPK | CAPK-005, CAPK-008 |
| RQ5800-RQ6700 | Receipt content, EMV receipt tags, offline-decline receipt data | 7 / 101-103 | RCPT | Out of message scope |
| RQ6800 | EMV configuration report is mandatory (no LOA without it) | 8.4 / 114 | RPT / CERT | none |
| RQ6900 / RQ7000 / RQ7100 | Default DDOL = 9F37; Visa default TDOL = 9F02; Mastercard default TDOL = 9F02, 5F2A, 9A, 9C, 95, 9F37 | 10.1 / 140-141 | DEV | none |
| RQ7200 | Fiserv Test Suite (ICCSimTMat for Fiserv) required | 13.1 / 165 | CERT | External tool |
| RQ7201-RQ7205 | Voyager: blocked card terminates; partial selection; AID priority; online PIN + No CVM; maximum floor limit | 14.3 / 169-173 | DEV / MSG | AID-008 |
| *RQ6801 (removed)* | Tag 9F06 is no longer mandatory (v2019.RG1) | 16 / 211 | none | ATL105 still requires 9F06 **or** 84 |

## 14. EMV Test Scenario Catalog

**Readiness values:**

- **EXECUTABLE**: deterministic from the message alone, under an ATL105-sourced rule
- **REVIEW_REQUIRED**: guide-derived, or needs SME or session context
- **EXTERNAL_FIXTURE_REQUIRED**: needs a real CAPK, kernel or cryptogram

Expected outcomes for guide-derived rules are candidate oracles. They do not count as certified coverage until reviewed.

### 14.1 Request structure and cross-field

| ID | Scenario | Expected outcome | Rules | Readiness |
| --- | --- | --- | --- | --- |
| EMV-TS-001 | Contact chip Purchase, Visa credit: 84 = 9F06 = A0000000031010; full Table 25 Auth set; 9F27 `80`; 9C `00`; 9F02 = E41+E58+E99; 9F03 zero; 5F2A = E20 = 840; E188 = 5F34 | Pass | TAG-001, -006, -007, -010; XF-001, -003, -005, -006 | EXECUTABLE (structure) / REVIEW_REQUIRED (XF) |
| EMV-TS-002 | Contactless Purchase (entry mode `07`), Mastercard, with 9F66 and a valid upper-case 9F6E | Pass | TAG-001, TAG-013, XF-009 | REVIEW_REQUIRED |
| EMV-TS-003 | Purchase with cash back on US Maestro CAID: 9C `09`, 9F03 = E17, 9F02 includes E17 | Pass. Variant with 9C `00` → review (accepted subject to certification). | XF-001, -002, -005; AID-004 | REVIEW_REQUIRED |
| EMV-TS-004 | Cash back on the Visa Global AID A0000000031010 | Review; expected decline / not supported (Table 9 N; §10.14.1) | AID-004 | REVIEW_REQUIRED |
| EMV-TS-005 | Interac AID with a non-zero 9F03 | Review (RQ3700) | XF-002 | REVIEW_REQUIRED |
| EMV-TS-006 | Element 190 has 4F only (no 84 or 9F06) | **Fail** | TAG-010 (ATL105 part) | EXECUTABLE |
| EMV-TS-007 | 84 = A0000000041010 (Mastercard) with 9F06 = A0000000980840 (Visa CAID), the Appendix R pattern | Review (RID disagreement) | TAG-010 | REVIEW_REQUIRED |
| EMV-TS-008 | Element 190 contains 5A or 57 | **Fail** | TAG-006 | EXECUTABLE |
| EMV-TS-009 | Wrong tag lengths: 9F26 = 7 bytes, 95 = 4 bytes, 9F02 = 5 bytes | Fail-candidate | TAG-007 | REVIEW_REQUIRED |
| EMV-TS-010 | Authorization request contains 8A, 91, 71 or 72 | Fail-candidate | TAG-005 | REVIEW_REQUIRED |
| EMV-TS-011 | TVR byte 4 bit 4 set (merchant forced online) | Review | TAG-011 | REVIEW_REQUIRED |
| EMV-TS-012 | E188 does not match 5F34: 5F34 `01` with E188 `002`; E188 spaces while 5F34 is present; E188 `001` while 5F34 is absent | **Fail** | XF-006 | EXECUTABLE |
| EMV-TS-013 | 9C `20` on a Purchase Prompt Code (Appendix G `0`) | Review | XF-005 | REVIEW_REQUIRED |
| EMV-TS-014 | 5F2A `0978` with E20 `840` and no DCC context (Appendix R pattern) | Review | XF-003 | REVIEW_REQUIRED |
| EMV-TS-015 | EBT AID chip data missing 9F37, or longer than 255 bytes | Fail-candidate / review | TAG-014 | REVIEW_REQUIRED |
| EMV-TS-016 | Unknown or unsupported AID in 84 | Review (BP0300) | AID-001 | REVIEW_REQUIRED |

### 14.2 Entry mode and fallback

| ID | Scenario | Expected outcome | Rules | Readiness |
| --- | --- | --- | --- | --- |
| EMV-TS-017 | Fallback: entry mode `80`, track data, no Segment 130 | Pass | FB-001 | EXECUTABLE |
| EMV-TS-018 | Entry mode `80` with Segment 130 present | **Fail** | FB-002 | EXECUTABLE |
| EMV-TS-019 | Service code 2xx swiped with entry mode `90` at a chip-capable terminal | Review (liability shift) | FB-003 | REVIEW_REQUIRED |
| EMV-TS-020 | Voyager card swiped; service code read at Track 2 position 31 | Same as TS-019, at the Voyager position | FB-003, AID-008 | REVIEW_REQUIRED |
| EMV-TS-021 | Amex fallback at an unattended CAT, or any fallback at an offline-only CAT (type 26) | Review; expected not allowed | FB-005 | REVIEW_REQUIRED |
| EMV-TS-022 | Fallback after an offline decline (AAC at the 1st Generate AC) in the same session | Review; expected not allowed | FB-005 | REVIEW_REQUIRED |
| EMV-TS-023 | Discover empty candidate list coded as MSR (`02`/`90`) | Review (should be fallback) | FB-004 | REVIEW_REQUIRED |

### 14.3 Lifecycle

| ID | Scenario | Expected outcome | Rules | Readiness |
| --- | --- | --- | --- | --- |
| EMV-TS-024 | Purchase Reversal or TOR **without** Segment 130 | Pass | LC-001 | EXECUTABLE |
| EMV-TS-025 | Reversal with Segment 130 after a card decline at the 2nd Generate AC: 9F27 `00`; tag list = original plus 2nd Generate AC updates | Pass. With 9F27 `80` → review. | LC-002, LC-003 | REVIEW_REQUIRED |
| EMV-TS-026 | TOR with Segment 130 holding 1st Generate AC data (9F27 `80`) | Pass. With a TC (`40`) → review. | LC-003 | REVIEW_REQUIRED |
| EMV-TS-027 | Reversal whose tag set differs from the original (tag missing or added) | Review (RQ4600) | LC-002 | REVIEW_REQUIRED |
| EMV-TS-028 | Balance Inquiry (`E`) with 9C `31`; then a reversal of that inquiry | Inquiry passes; reversal → review | XF-005, LC-005 | REVIEW_REQUIRED |
| EMV-TS-029 | Authorization Only (`3`) followed by completion (`0`) | Same chip data and entry mode → pass. Entry mode changed (`05` → `90`) → fail-candidate. New cryptogram → review. | LC-006 | REVIEW_REQUIRED |
| EMV-TS-030 | Partial approval (E83 `F`, E6 below requested) then completion | Completion amount = E6 with chip 9F02 unchanged → pass. Completion at the original amount → review. | XF-011, LC-011 | REVIEW_REQUIRED |
| EMV-TS-031 | Partial approval declined by the cardholder | Reversal with 2nd Generate AC AAC → pass. No reversal → review. | LC-011 | REVIEW_REQUIRED |
| EMV-TS-032 | Incremental authorization carrying Segment 130 or a chip entry mode | Review (RQ4700, pending the Appendix AE/AF reconciliation) | LC-007 | REVIEW_REQUIRED |
| EMV-TS-033 | SAF / deferred authorization: 9F27 `80`; 9A earlier than submission; 9F36 out of sequence | Pass (no date or ATC failure) | LC-008, XF-007 | REVIEW_REQUIRED |
| EMV-TS-034 | Offline-approved upload with TC `40` and 8A `Y1` on a Visa AID | Review (Fiserv supports only Mastercard offline processing) | LC-009 | REVIEW_REQUIRED |
| EMV-TS-035 | Chip Merchandise Return: 9C `20`, 9F27 `00`. Variant: Mastercard contactless refund with ARQC. | Pass; variant → review (RQ4701) | LC-010 | REVIEW_REQUIRED |
| EMV-TS-036 | $0 Account Verification (`A`) with Segment 130 | Review (not in §10.14.2.4; the guide permits it) | LC-014 | REVIEW_REQUIRED |

### 14.4 Response, AID and brand

| ID | Scenario | Expected outcome | Rules | Readiness |
| --- | --- | --- | --- | --- |
| EMV-TS-037 | Segment 131 chip data with both 71 and 72 | Fail-candidate | RSP-003 | REVIEW_REQUIRED |
| EMV-TS-038 | Approved response with no chip data | Pass (issuer may return none), subject to Segment 131 structure rules | RSP-001 | REVIEW_REQUIRED |
| EMV-TS-039 | Response 91 of 7 or 17 bytes | Fail-candidate | RSP-004 | REVIEW_REQUIRED |
| EMV-TS-040 | 8A in a later completion or advice against E83: `0`/`2`/`3`/`4` → `00`; `F` → `10`; `1`/`S` → `05` | Agreement → pass; mismatch → review | RSP-002 | REVIEW_REQUIRED |
| EMV-TS-041 | No-CVM / PIN-bypass result on the Interlink AID with no PIN data | Expected decline (negative) | AID-003 | REVIEW_REQUIRED |
| EMV-TS-042 | Visa CAID A0000000980840 with an online PIN in a credit/dual request | Review (PAVD, RQ5600) | AID-005 | REVIEW_REQUIRED |
| EMV-TS-043 | Maestro A0000000043060, or a 19-digit PAN, sent as credit/dual | Review (Table 13, RQ2600) | AID-002 | REVIEW_REQUIRED |
| EMV-TS-044 | Voyager AID with contactless entry (`07`), or with cash back | Review; expected not supported | AID-008 | REVIEW_REQUIRED |

### 14.5 CAPK

| ID | Scenario | Expected outcome | Rules | Readiness |
| --- | --- | --- | --- | --- |
| EMV-TS-045 | Exponent `010001`; checksum = SHA-1(RID, Index, Modulus, Exponent) | Pass (corrected oracle) | CAPK-001 | EXECUTABLE (after B-01) |
| EMV-TS-046 | Exponent `065537` | **Fail**. The current KB oracle passes it, which is a false pass (B-01). | CAPK-001 | EXECUTABLE (after B-01) |
| EMV-TS-047 | RID of 9 or 11 characters; Modulus of 498 hex characters | Fail-candidate | CAPK-002 | REVIEW_REQUIRED |
| EMV-TS-048 | Seven records with the same RID | Review | CAPK-003 | REVIEW_REQUIRED |
| EMV-TS-049 | Request E187 differs from the host's current CAPK checksum | Response E30 = `9` → pass; E30 = `0` → review | CAPK-005 | REVIEW_REQUIRED |
| EMV-TS-050 | Load answered "RQST/CURRENT HASH MATCH" (E83 `X`); the device keeps its current file | Pass | CAPK-005 | REVIEW_REQUIRED |
| EMV-TS-051 | Record with a past expiry date in the loaded file | Not a structural failure (INFO) | CAPK-008 | EXECUTABLE |
| EMV-TS-052 | Production CAPK material found in the repository or test fixtures | **Fail** (process / R003) | CAPK-007 | EXECUTABLE |
| EMV-TS-053 | Genuine cryptogram (ARQC/TC) and CAPK authenticity | Not assessable by the message validator | GAP-EMV-06 | EXTERNAL_FIXTURE_REQUIRED |

## 15. Synthetic Test-Data Guidance

- Use synthetic or approved masked data only. Never use real PANs, track data, PIN blocks, production CAPK, or cryptograms from live cards (R003; CAPK-007).
- Never put 5A or 57 in Element 190. Card account data stays in the ATL105 account fields.
- Encode Element 190 as binary TLV: one- or two-byte tags; short or long-form lengths; values up to 255 bytes. Element 189 holds the 3-digit length.
- Reuse values only from the guide's sample chip transaction report (Table 18, p.110) or from ATL105 Appendix R, and keep AID tags consistent. Appendix R itself pairs 9F06 Visa CAID with 84 Mastercard, so do not use it as a positive RID-agreement oracle.

**Synthetic contact-chip authorization (Visa credit, illustrative only):**

| Tag | Length | Value | Origin |
| --- | --- | --- | --- |
| 9F06 | 07 | A0000000031010 | Table 4 |
| 84 | 07 | A0000000031010 | Table 18 |
| 82 | 02 | 5C00 | Table 18 / Appendix R |
| 95 | 05 | 0000008000 | Table 18 |
| 9A | 03 | 261027 (YYMMDD) | synthetic |
| 9C | 01 | 00 | Table 28 |
| 5F2A | 02 | 0840 | Table 18 |
| 9F1A | 02 | 0840 | Table 18 |
| 9F02 | 06 | 000000001000 ($10.00 = E41+E58+E99) | Table 18 |
| 9F03 | 06 | 000000000000 | Table 18 |
| 9F10 | 07 | 06010A03A40002 | Table 18 |
| 9F26 | 08 | 1234567890ABCDEF | Table 18 (synthetic) |
| 9F27 | 01 | 80 (ARQC) | Table 18 |
| 9F33 | 03 | E0B8C8 | Table 18 / Appendix R |
| 9F34 | 03 | 040302 | Table 18 |
| 9F35 | 01 | 22 | Table 18 / Table 1 |
| 9F36 | 02 | 0197 | Table 18 |
| 9F37 | 04 | 12345678 | Table 26 length (the Table 18 sample shows 5 bytes, which conflicts with b4) |
| 9F09 | 02 | 008C | Table 18 |
| 5F34 | 01 | 01 → Element 188 = `001` | XF-006 |

**Negative mutations to derive from this fixture:**

- Drop 84 and 9F06 (TS-006).
- Change 9F06 to A0000000980840 (TS-007).
- Add 5A (TS-008).
- Truncate 9F26 to 7 bytes (TS-009).
- Set E188 to `002` (TS-012).
- Set 9C to `20` (TS-013).
- Set 5F2A to `0978` (TS-014).
- Set TVR byte 4 bit 4 (TS-011).

## 16. Source Conflicts and Defects

### 16.1 Guide versus ATL105 (ATL105 governs)

| ID | Topic | ATL105 | Guide | Decision |
| --- | --- | --- | --- | --- |
| CF-EMV-01 | EMV data on reversals | "EMV data is not required on Reversal transactions" (§10.14.2.4) | "EMV tags must be included in the host reversal message" (§5.9); RQ4600 "conditional" | Absence is valid (LC-001). When present, content follows LC-002 and LC-003. |
| CF-EMV-02 | AID tag needed for processing | 9F06 **or** 84 must be in Element 190 | Routing uses 84, else 4F, else 9F06 (BP2801); 9F06 no longer mandatory (RQ6801 removed) | 4F alone fails ATL105 (TAG-010). |
| CF-EMV-03 | US Common Debit AIDs | Four categories (§10.14.1) | Six, adding Amex and UnionPay (Table 12) | Additions are REVIEW (Q-EMV-08). |
| CF-EMV-04 | EMV transaction types | Eight types (§10.14.2.4) | Also $0 account verification, cash advance, force post, DCC, Quick Chip, incremental | Only the ATL105 list is EMV-eligible. Others are REVIEW (Q-EMV-05, Q-EMV-06). |
| CF-EMV-05 | Incremental authorization | Appendix AE/AF overlays define incremental flows | Keyed, no EMV tags (RQ4700), "depending on specification/platform" | No enforcement until reconciled (Q-EMV-06). |
| CF-EMV-06 | Expiry handling | "The device does not perform expiration date validation" (§10.14.2.1) | 5F24 populates the expiration date; processing restrictions log expiry in TVR | Compatible: expiry is logged, not declined by the device. |
| CF-EMV-07 | Supported products | 17 products named by brand, including STAR / NYCE / PULSE / Accel / Shazam | AID-based; regional networks have no own AID | Bridge through CAIDs and authorizer codes ([§9.1](#91-atl105-product-to-aid-bridge-closes-the-sme-008012-path)). |

### 16.2 Guide-internal inconsistencies

- **9F5B:** "Issuer Script Results" in Table 25 and RQ3200, but "Data Storage Data Object List (DSDOL)" in Table 26. Interpret it by brand and message role.
- **Duplicate tag meanings in Table 26:** 9F66 is both PUNATC (Track 2) and TTQ. 9F67, 9F69, 9F6C and 9F6D each carry two brand meanings. DF30 is a Visa Fleet prompt, a Voyager trailer licence and a WEX fuel-card bitmap.
- **9F6E length:** b 5-32 in Table 26, but 2 in the EBT list (§14.9).
- **5F2A source:** "Chip" in Table 25, "Terminal" in Tables 6 and 26.
- **Zero-length tags:** BP3000 allows a zero-length TLV; §9.1 says not to create tags with empty values.
- **TVR byte 5:** Table 58 repeats the byte 4 text; the real byte 5 bits follow it (layout defect, p.157).
- **Wawa RID:** Table 63 prints `A00000000A`; the AID prefix is `A000000004`.
- **Sample values:** Table 18 shows 9F37 as 5 bytes against b4. The CAPK expiry example `06312014` is not a valid date.
- **Contactless LOA2s:** the text says "five" brands but lists six.
- **Duplicate RQ texts:** RQ0900 = RQ4800; RQ5101 = RQ5102; RQ1300 appears twice.
- **Table 20:** titled "EMV Reversal Types Sample" but shows the PIN Pad Fallback Report.
- **§4.2 typo:** "fuel AID (CAID)" should read Common Debit AID.

### 16.3 ATL105-side issues found during ingestion

- **Appendix R example (p.R-1):**
  - 9F06 = A0000000980840 (Visa US Common Debit) but 84 = A0000000041010 (Mastercard).
  - 5F2A = 0978 (EUR) with 9F1A = 0840 (US).
  - The example is illustrative and must not be the positive oracle for RID agreement or currency/country rules (Q-EMV-04). The existing Appendix R golden fixture remains valid only for TLV decoding.
- **Appendix S:** the exponent prints as "216+1" because the superscript was lost; the meaning is 2^16+1 = hex `010001`. File Name is declared max 8 but the value is 7 characters (harmless).
- **§10.14:** cites the "First Data EMV Implementation Guide", now titled "EMV and Chip Implementation Guide". Request a specification wording update.

## 17. Still-Open Items and Missing External Documents

### 17.1 SME queries raised by this ingestion

These must be added to the communication register (B-10). Answers must cite a source, configuration or named owner.

| ID | Question | Blocks |
| --- | --- | --- |
| Q-EMV-01 | Does guide Table 25 apply to BuyPass as the Element 190 tag expectation, given the Appendix R note "contact your BUYPASS representative"? | TAG-001 to TAG-004 |
| Q-EMV-02 | Confirm the cross-field formulas: 9F02 = E41+E58+E99+E17; 9F03 = E17; 5F2A = E20; 9C from Appendix G; 9F1A from the E102 state code; 9A against the local date; and the treatment of fees. | XF-001 to XF-011; `SEG130-SME-003`; `BR-SEG100-APPR-CROSS-FIELD` |
| Q-EMV-03 | Confirm the Appendix S exponent is `010001`, and log the "216+1" rendering as a specification defect. | CAPK-001 (B-01) |
| Q-EMV-04 | Confirm that the Appendix R AID and currency mismatches are example errors. | TAG-010, XF-003 |
| Q-EMV-05 | Is EMV $0 Account Verification (Appendix G `A`) supported on BuyPass? | LC-014 |
| Q-EMV-06 | For incremental authorization on BuyPass, what are the entry mode and chip-data rules (guide RQ4700 compared with Appendix AE/AF)? | LC-007 |
| Q-EMV-07 | Which ATL105 element is the "settlement indicator" that the authorization response returns for completions? | LC-006 |
| Q-EMV-08 | Are the Amex A00000002504 and UnionPay A000000333010108 Common Debit AIDs supported on BuyPass? | AID-006 |
| Q-EMV-09 | Are non-Mastercard offline (Y1) uploads accepted on BuyPass? | LC-009 |
| Q-EMV-10 | Confirm the Element 83 to Tag 8A mapping in [§11.1](#111-table-7-tag-8a-values-for-the-2nd-generate-ac-guide-49-pdf-p55). | RSP-002 |
| Q-EMV-11 | Accept the AID-based method for EMV product eligibility (`SEG130-SME-008` / `SEG130-SME-012`), and confirm the Generic Proprietary participants (`SEG130-SME-009` / `SEG130-SME-013`). | AID-001; EMV-130-CR/DB/PF |

### 17.2 Items the guide does not close

- `SEG130-SME-010` (Element 187 echo when it is omitted)
- `SEG130-SME-007` / `SEG130-SME-011` (Appendix T CARC in requests)
- `SEG131-SME-001` / `002` / `004`
- `SEG132-SME-001` to `006`
- `SEG130-SME-005` (canonical AI package)
- Cryptogram and CAPK authenticity (external by design)

### 17.3 External documents named by the guide and not supplied (new "missing references")

| Document | Named in | Needed for |
| --- | --- | --- |
| BuyPass Petroleum Industry (Processing) Specifications | §14.1 (p.168), §14.3 (p.169) | Voyager and fleet prompting, petroleum flows |
| Petroleum Implementation Guide | §14.4.2 (p.174) | WEX DF30/DF31 values |
| USPF AFD Chip Fallback Transaction Processing Best Practices | §14.6 (p.175) | Fleetcor empty-candidate-list fallback |
| Mastercard M/Chip Requirements; Mastercard Fleet Card specification | §4.6.2 (RQ2301), §14.6 | PIN bypass on CAT; 9F6E fleet data |
| Expresspay EMV Implementation Guide Special Bulletin (Nov 2019) | §2.7 | Amex CAID with Expresspay 3.1 |
| EMVCo Books and Type Approval Bulletin #11; PIN pad vendor kernel interface documents | §2.3, §3.1 | Kernel behavior, recertification |
| Embedded "State of California and Oklahoma Chip Card.pdf" | §14.9 (p.180) | EBT chip specifics |

## 18. Implementation Follow-Up Backlog

Nothing in this list has been implemented. Under the handbook's [Automatic Update Rule](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md#automatic-update-rule), any code change must update, in the same change set:

- the handbook
- [AI-Solution-Output-Validation-Plan.md](AI-Solution-Output-Validation-Plan.md)
- the affected `AIV-*` controls in [AI-ARTIFACT-VALIDATION-CONTROLS.md](AI-ARTIFACT-VALIDATION-CONTROLS.md)
- the regression tests

| ID | Priority | Action |
| --- | --- | --- |
| B-01 | P1 | Correct `BR-SEG100-APPS-EXPONENT-VALUE` to `03` / `010001`. Regenerate the Appendix S checksum fixtures over bytes `01 00 01`. Add negative test `065537`. Update the [Appendix S module](../specs/kb/appendix-s/README.md). |
| B-02 | P1 | Add the RID-agreement check across 84, 9F06 and 4F (REVIEW). Keep the Appendix R golden fixture for TLV decoding only, and record CF/Q-EMV-04. |
| B-03 | P1 | Add the E188 ↔ 5F34 cross-field check (ATL105-sourced, deterministic). |
| B-04 | P2 | Make the Table 25 tag-role catalog and Table 26 length/format checks a versioned resource (for example `emv-tag-catalog.json`) with companion anchors. |
| B-05 | P2 | Execute XF-001 to XF-011 as `REVIEW_REQUIRED` findings under `BR-SEG100-APPR-CROSS-FIELD` (formula available; not certified). |
| B-06 | P2 | Add reversal/TOR content rules (LC-002, LC-003), completion equality (LC-006), and partial-approval rules (LC-011, XF-011). |
| B-07 | P2 | Make the AID catalog (Tables 4, 9, 12, 13, 24, 63) machine-readable for eligibility, message-type, PIN-bypass and cash-back checks (AID-001 to AID-005). |
| B-08 | P3 | Tighten CAPK checks: RID exactly 10 characters, Modulus at most 496, at most 6 keys per RID (CAPK-002, CAPK-003). |
| B-09 | P3 | Add fallback signals FB-003 to FB-006 and session-context fallback eligibility. |
| B-10 | P1 | Update `registers/atl105-communication-register.json` and regenerate its views: attach companion evidence to `SEG130-SME-002/003/004/008/009/012/013` and `SEG131-SME-003`; add Q-EMV-01 to Q-EMV-11; close the fallback-note "80/90" item from Appendix J. |
| B-11 | P2 | Add a hash-pinned companion-overlay entry to `specifications/ATL105/training-status.json` (source, SHA-256, `IN_PROGRESS`). |

## 19. How to Reuse This Memory

1. **Check the source.** If a newer guide version is supplied, compare the SHA-256 with [§2](#2-source-binding), re-ingest, and add a revision row. Never silently relabel rules.
2. **Apply precedence ([§3](#3-precedence-and-evidence-rules)) to every rule.** ATL105 governs. Guide-derived rules are `PROVISIONAL` and cannot raise certified coverage.
3. **Training a segment:**
   - For Segments 130, 131 or 132 or Appendices R, S or T, start from [§4](#4-gap-closure-register) and the matching rule section ([§6](#6-emv-tag-catalog-for-element-190) to [§12](#12-ca-public-key-capk-rules)).
   - For lifecycle or transaction-context training (for example Mastercard Credit Purchase), use [§7](#7-chip-to-atl105-cross-field-rules), [§8](#8-lifecycle-rules) and [§14](#14-emv-test-scenario-catalog).
4. **Reviewing AI artifacts.** Compare AI EMV requirements with the EMVIG rule IDs. An AI requirement citing the guide gets companion provenance; it does not get an ATL105 `sourceAnchor`.
5. **Generating test data.** Follow [§15](#15-synthetic-test-data-guidance); never use real card, key or cryptogram material.
6. **When an SME answers a Q-EMV item.** Record the answer in the communication register, then update the affected rule status here and in the strategy, in the same change.
7. **When a new external reference is found.** Add it to [§17.3](#173-external-documents-named-by-the-guide-and-not-supplied-new-missing-references) instead of inventing its content.

## 20. Revision History

| Version | Date | Change |
| --- | --- | --- |
| 1.0 | 2026-10-27 | First ingestion of the EMV and Chip Implementation Guide v2025.RG20 against ATL105 2026-4 §10.14. Includes: source binding; precedence and evidence rules; 17 gap dispositions; Table 25/26 tag catalog; 14 tag, 11 cross-field, 17 lifecycle, 10 AID, 7 fallback, 6 response and 8 CAPK rules; RQ traceability index; 53 scenarios; conflict register; 11 SME queries; 11 backlog items. |

