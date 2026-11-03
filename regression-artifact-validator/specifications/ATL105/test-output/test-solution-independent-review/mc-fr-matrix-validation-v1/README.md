# Mastercard Credit Purchase FR Matrix: Workbook Validation

This review validates `../Mastercard_Credit_Purchase_FR_Matrix (1) 1.xlsx` (sheet `MC Purchase BR Matrix`, SHA-256 `b984445301d8f83acadc96e3a31b7b771a81486d807a4bb010985dec45698b92`) line by line against the current Test Solution for ATL105 2026-3: the ATL105 source text, the canonical Test Solution BRs and their Mastercard training status, the Section 10.1 credit-card draft BRs, and the Mastercard BA reference review. Status is DRAFT_REVIEW_REQUIRED; no SME approval or execution certification is implied.

## Outcome: REJECTED_NOT_INTAKE_READY

As written, the workbook cannot be used as a test oracle: 5 validation lines point at the wrong ATL105 field and 3 name no testable field or rule, so tests derived from them would check the wrong thing. The BR-01 / FR / scenario structure is sound and is already the business layer of the Test Solution FR matrix. Every line's corrected requirement is in `line-validation.csv` and `validation.xlsx`.

## Summary

| Measure | Result |
|---|---|
| Business requirement / FRs / scenarios / validation lines | 1 / 8 / 37 / 65 |
| Lines supported as written | 14 |
| Lines supported once corrected | 29 |
| Lines contradicted by ATL105 | 5 |
| Lines that depend on contracts outside ATL105 | 14 |
| Lines not testable as written | 3 |
| Lines backed by a trained Test Solution chain | 40 of 65 |
| Element citations naming the wrong element | 4 of 6 |
| Expected results confirmable from the message alone | 0 of 37 |


## Scenario Outcomes

| Outcome | Scenarios |
|---|---|
| REJECTED_NOT_INTAKE_READY | TS-002, TS-004, TS-010, TS-016, TS-022 |
| REVIEW_REQUIRED | TS-001, TS-003, TS-005, TS-006, TS-011, TS-015, TS-017, TS-018, TS-019, TS-021, TS-023, TS-026, TS-030, TS-034, TS-035, TS-036, TS-037 |
| ACCEPTED_WITH_GAPS | TS-007, TS-008, TS-009, TS-012, TS-013, TS-014, TS-020, TS-024, TS-025, TS-027, TS-028, TS-029, TS-031, TS-032, TS-033 |
| ACCEPTED | - |


## Wrong Field Mappings

| Line | As written | Correct requirement | ATL105 evidence |
|---|---|---|---|
| TS-002.V1 | Element 51 present for manual entry | Element 12 carries the MMYY expiry for a manually entered account. | 13.2 Element 51 (extracted_text.txt:20557); 13.2 Element 12 (extracted_text.txt:19751) |
| TS-004.V1 | Processing Code = Purchase/Capture (020) | Element 78 Prompt Code = 0020 (transaction type 0, card type 020); 0020 alone does not prove Mastercard. | 13.2 Element 78 (extracted_text.txt:21051); Prompt Code decision examples (prompt-code-sme-tba-note.md:46) |
| TS-010.V1 | Element 24 present | Element 2 Account Number present. | 13.2 Element 24 (extracted_text.txt:20052); 13.2 Element 2 (extracted_text.txt:19397) |
| TS-016.V1 | Element 80 present | Element 86 Sequence Number present. | 13.2 Element 80 (extracted_text.txt:21127); 13.2 Element 86 (extracted_text.txt:21458) |
| TS-022.V1 | Element 6 received for approval | Element 5 Approval Number received in the approved response. | 13.2 Element 6 (extracted_text.txt:19636); 13.2 Element 5 (extracted_text.txt:19617) |


## Element Citation Check (Chapter 13)

| Line | Cited | Cited element is | Intended | Correct element | Result |
|---|---|---|---|---|---|
| TS-002.V1 | Element 51 | Mail Text Data | card expiration date for manual entry | Element 12 Card Discretionary | MISMATCH |
| TS-007.V1 | Element 55 | Message Format | message format version identifier | Element 55 Message Format | MATCH |
| TS-008.V1 | Element 63 | Number of Segments | number of segments | Element 63 Number of Segments | MATCH |
| TS-010.V1 | Element 24 | Data Type Indicator | account number | Element 2 Account Number | MISMATCH |
| TS-016.V1 | Element 80 | Purchase Code | sequence number | Element 86 Sequence Number | MISMATCH |
| TS-022.V1 | Element 6 | Approved Amount | approval number | Element 5 Approval Number | MISMATCH |


## Line Verdicts by FR

| FR | Name | Scenarios | Lines | Supported | With correction | Contradicted | External | Not testable |
|---|---|---|---|---|---|---|---|---|
| FR-001 | Card Validation | 3 | 13 | 3 | 3 | 1 | 6 | 0 |
| FR-002 | Request Construction | 7 | 15 | 5 | 6 | 2 | 2 | 0 |
| FR-003 | Amount Processing | 5 | 10 | 3 | 4 | 0 | 0 | 3 |
| FR-004 | Tracking | 4 | 8 | 1 | 4 | 1 | 2 | 0 |
| FR-005 | Authorization | 6 | 6 | 0 | 5 | 1 | 0 | 0 |
| FR-006 | Partial Approval | 2 | 3 | 0 | 2 | 0 | 1 | 0 |
| FR-007 | Exception Handling | 5 | 5 | 2 | 3 | 0 | 0 | 0 |
| FR-008 | Post Authorization | 5 | 5 | 0 | 2 | 0 | 3 | 0 |


## Findings

| ID | Class | Severity | Status | Title | Owner |
|---|---|---|---|---|---|
| MCV-A01 | PROVENANCE_OR_VERSION_GAP | MEDIUM | OPEN | Workbook does not identify its ATL105 version, producer, date or document ID | BA (SEG100-SME-003 questionnaire) |
| MCV-A02 | AI_ARTIFACT_DEFECT | HIGH | OPEN | Field mappings that contradict ATL105 | BA (SEG100-SME-003 questionnaire) |
| MCV-A03 | AI_ARTIFACT_DEFECT | HIGH | OPEN | Lines whose intent is supported but whose wording, scope or field must be corrected | BA (SEG100-SME-003 questionnaire) |
| MCV-A04 | REVIEW_REQUIRED | MEDIUM | OPEN | Checks that depend on contracts outside ATL105 or are not testable as written | BA (SEG100-SME-003 questionnaire) |
| MCV-A05 | REVIEW_REQUIRED | MEDIUM | OPEN | Expected results need execution or external evidence | BA (SEG100-SME-003 questionnaire) |
| MCV-A06 | AI_ARTIFACT_DEFECT | LOW | OPEN | Structure: no line IDs, stray whitespace, several checks in one cell | BA (SEG100-SME-003 questionnaire) |
| MCV-A07 | AI_ARTIFACT_DEFECT | LOW | OPEN | Duplicated or overlapping checks across scenarios | BA (SEG100-SME-003 questionnaire) |
| MCV-T01 | TEST_SOLUTION_GAP | MEDIUM | OPEN | Source-supported checks without a trained Test Solution chain | Test Solution team |
| MCV-T02 | CHAIN_GAP | MEDIUM | OPEN | Cited Test Solution BRs that are still in the Mastercard review queue | Test Solution team |
| MCV-T03 | TEST_SOLUTION_GAP | MEDIUM | OPEN | Multi-segment anchors are excluded from the Mastercard context scope | Test Solution team |
| MCV-T04 | TEST_SOLUTION_GAP | MEDIUM | OPEN | Template TS-020 is mapped to the Authorization Only transaction type | Test Solution team |
| MCV-T05 | PROVENANCE_OR_VERSION_GAP | LOW | OPEN | The BA reference review is not bound to this workbook | Test Solution team |


Full detail, affected IDs and dispositions are in `findings.csv`.


## BA Reference Review Transcription Differences

The existing [BA reference review](../mastercard-ba-reference-review-v1/README.md) was transcribed from a screenshot. Checked against the actual workbook:


| TS | Workbook | BA review transcription | Impact |
|---|---|---|---|
| TS-001 | PAN present | (not transcribed) | No disposition change; Element 2 presence is covered by trained Test Solution chains. |
| TS-034 | Approved transaction captured | Approval number captured | No disposition change; clearing remains an external contract. |
| TS-035 | Approval data and amounts retained | Transaction data and amounts retained | No disposition change; settlement remains an external contract. |
| TS-036 | Approval number, sequence number, amounts present | (not transcribed) | No disposition change; settlement reporting remains an external contract. |


## Deliverables

- `validation.xlsx`: line validation, scenario outcomes, findings, element check and transcription sheets.
- `line-validation.csv`: one row per validation line with verdict, correction, evidence and Test Solution status.
- `findings.csv`: findings with class, severity, owner and disposition.
- `validation.json`: the complete machine-readable review, including input hashes.


## Re-run

From the validator module:

```powershell
python scripts/validate-mastercard-fr-matrix.py           # regenerate this folder
python scripts/validate-mastercard-fr-matrix.py --check   # fail if these outputs are stale
python -m pytest scripts/test_validate_mastercard_fr_matrix.py
```

The script refuses to run if the workbook's SHA-256, any validation line or expected result, an element definition, a source anchor or a cited BR no longer matches, so a changed workbook must be re-reviewed.


## Limitations

- Line assessments are curated Test Solution judgements; they remain DRAFT_REVIEW_REQUIRED until SME/TBA review.
- Section 10.1 draft BRs are cited as source-derived evidence; they are not trained chains.
- External contracts (BIN/brand, issuer status, merchant configuration, routing, clearing, settlement, audit) are not available, so those checks stay REVIEW_REQUIRED (questions MC-Q01 to MC-Q09, register SEG100-SME-003).
- Nothing was executed; expected results that need host, issuer or device evidence are not confirmed.
