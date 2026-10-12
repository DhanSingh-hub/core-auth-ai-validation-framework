# Chapter 13.2: Data Elements in Data Element Number Order

Extraction of data element definitions from BUYPASS ATL105 Specification Chapter 13.2
(starts ~line 19320 of `docs/specs/extracted_text.txt`).

> **Coverage**: Elements 1-99 were individually re-verified line-by-line against
> `extracted_text.txt` lines 19367-21719 (Chapter 13.2) in a later pass; all 90 present
> entries were checked for Character Type, Maximum Length and Valid Codes/Values against
> the primary source, cross-referencing `Atl105ResponseCodeFamilyOracle.java` and
> `src/main/resources/atl105/spec-elements.json` where an independent validator exists for
> the same element. The prior header's gap list was itself wrong (it listed 32 and 59 as
> gaps; both are present in source: 32 = Employee Number at line 20225, 59 = Number of
> Card Types at line 20739) and has been corrected below. Elements 100-243 were read and
> transcribed directly from `extracted_text.txt` lines 21719-25439 in an earlier pass -
> higher confidence, but not yet individually re-verified line by line in this pass. Note
> the specification's element numbering has gaps, so "232 total" (the actual count of
> `Number: N Name:` entries found in source) is a count, not a max element number (highest
> number used is 243).
>
> **Confirmed gaps in 1-99** (no `Number: N Name:` entry exists in source for these):
> 9, 10, 19, 60, 67, 68, 69, 70, 71.

---

## Data Element Definitions (1-99)

### Executable Chapter 13 continuation

The source inventory contains **231 distinct element numbers and 232 definitions**:
Element 118 appears twice. These counts are not semantic coverage.

The current [profile catalog](elements/validation-profiles.json) contains 79
contextual profiles. This continuation added 11 profiles and extended the existing
financial Sequence Number profile with the source's nonzero range:

| Elements | Executed representation scope | Not asserted |
|---|---|---|
| 86 | Financial, Totals and Electronic Mail requests: fixed N6, 000001-999999 | Uniqueness, allocation and lifecycle/request-response identity |
| 39/43/96 | Totals Request firmware/software fixed alphanumeric width 8; hardware 4 or 8 | Device/application compatibility, authentic version values |
| 44/78 | Totals Request single/multimessage 0/1 and fixed Prompt Code 990 | Connection behavior, other transaction-specific prompt semantics |
| 32 | Populated Totals employee number N4; empty can be omitted | Assignment, authorization policy or whether the condition requires inclusion |
| 105 | Totals Request MMDDYY calendar or six source-listed special codes | Response YYMMDD, activity-window eligibility, reset and settlement history |
| 36/45 | Populated Electronic Mail Extract Date and response Initiation Date MMDDYY | Actual extraction/initiation time, host clock, merchant time zone |

Leap day with year `00` remains review-required without century evidence.
The same calendar helper is reused by the legacy Totals Request validator;
its prior representation acceptance is preserved. Request special date codes
are not imported into response or unrelated date profiles.

Evidence:
[independent probes](../../../test-output/test-json/chapter-13-element-probes.json),
[12 BR / 12 TS / 110 TC / 110 TD chains](../../../test-output/test-json/chapter-13-training-package.json),
and [actual execution and reconciliation](../../../test-output/test-json/chapter-13-training-evidence.json).
All 12 chains retain the existing catalog anchor plus a Chapter 13 anchor.
Existing Test BR exact-anchor candidates are required before generation; candidate
matches and artifacts remain review-required, not auto-approved.

The execution report reconciles all 231 identities and runs 440 baseline
max-length probes across 220 extractable single definitions. Baselines check
maximum length and numeric shape where applicable; they do not establish the
complete alphabet, every valid value or contextual semantics.
The 110 contextual probes yield 32 target representation checks passed, 63
invalid targets detected and 15 explicit review findings. Whole partial
observations are reported separately from the target-element result.

Chapter 11 now delegates supplied numbered observations for Segments 100/105/109
and Electronic Mail response elements through these profiles. A child's explicit
version/family/segment cannot override the parent's context. This is not complete
producer-alias/AI-intake integration or wire certification.

### Expanded source-local domains and dependencies

The later semantic continuation adds a complementary
[Chapter 13 validator](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13DataElementValidator.java).
It composes the existing profiles and reuses Appendix D state codes, Appendix L
currency codes, Appendix I/K table validators and the Appendix R chip-data oracle.
It does not replace their source limits or claim complete eligibility coverage.

The [value-domain catalog](elements/value-domains.json) holds **69 explicitly
authored scalar domains**, including all 36 source-listed Extended Unit of Measure
codes. Initialization verifies element-local evidence, every listed literal and
both numeric range endpoints. Domains are not inferred from AI artifacts.

Additional executed predicates cover calendar formats, request/response Totals
dates, the source's unusual 01-24/01-60 clock ranges, explicit wire width,
numeric password padding, masked Store Number, decimal-prefix quantities/prices,
transport-specific mail limits, length/data correlation, net-total arithmetic,
BIN ordering, separate print/receipt record counts, QST and true tax omission,
original sequence/reversal-prompt correlation, observed MICR truncation/extension,
received checksum reuse and Moneris key iteration counts. Private-use/reserved
values, absent context, CAPK block/file ambiguity and production availability
remain explicit review findings.

Enable these additional checks through `chapter13Semantics: true` on a Chapter 11
envelope or element-reference observation. The old default mode is preserved.
`representation: "WIRE"` opts into source fixed widths; `"LOGICAL"` does not
silently invent padding. Put context in `qualifiers`, repeated observations in
`records` (`printLines`, `receiptLines`, `cardTypes`, `products`, `monerisKeys`),
and prior observed elements in `qualifiers.originalElements`. Parent version,
family, segment, explicit representation and shared qualifiers are authoritative
in the envelope; conflicting child values are rejected.

[Independent semantic probes](../../../test-output/test-json/chapter-13-semantic-probes.json),
[251 BR / 251 TS / 711 TC / 711 TD chains](../../../test-output/test-json/chapter-13-domain-package.json)
and [711 actual executions](../../../test-output/test-json/chapter-13-domain-evidence.json)
are persisted. These comprise 466 scalar-domain/type probes plus 245 dependency,
semantic, contextual, composite and processing-history probes, with **372 target
checks passed, 317 invalid targets and 22 explicit reviews**. Additional predicates
were executed for 129 element identities; this is not 129 fully completed definitions.

All 231 identities are reconciled against the existing complete Test BR package.
Twelve exercised identities have no numeric-element candidate in that package:
6, 38, 56, 61, 100, 101, 120, 193, 194, 205, 212 and 243. The generator also
cross-checks 38 versioned ATL105 Test packages directly under `test-output/test-json`;
these do not resolve the twelve candidate gaps. Supplemental package hashes and
skip reasons are retained. All `chapter-13-*` packages are excluded from this
supplemental scan to prevent circular self-validation. Candidates remain
`ELEMENT_ID_CANDIDATE_NOT_SEMANTIC_MATCH`. This is an explicitly reported
cross-check gap, not a confirmed missing implementation or an SME decision.
Global Chapter 13 anchors intentionally have no segment identity; a synthetic
observation does not establish that an element belongs to Segment 100.

### Observed history and actual AI intake continuation

[Chapter13ProcessingHistoryValidator](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13ProcessingHistoryValidator.java)
adds independently measured action/history predicates, rather than treating a
valid scalar as proof of correct processing. Initialization verifies the relevant
element-local source statements. Supply `history` as an object with these sections:

| Section | Observation fields | Executed scope |
|---|---|---|
| `totals` | `requestDate`, `responseDate`; complete full-year `activityDates` with `activityHistoryComplete`; `requestedSettlementDate` when requesting a literal date | Request/response correlation, actual active-date rank and third-most-recent lower bound; fewer than three dates stays review-required |
| `totals` settlement | `currentSettlementDate`, `nextSettlementDate`, `settlementDateAfter`, `endedSettlementDates` | Code 222222 ends the observed current settlement date and rolls to the explicitly supplied next date; no invented calendar/weekend policy |
| `totals` accumulation | `accumulatedTotalsBefore`, `accumulatedTotalsAfter`, `lastResetEventId`, `returnedPeriodStartEventId` | 999999 zeroes observed accumulations; 111111 preserves them and starts the returned period at the last observed reset |
| `capk` | Boolean `approved`, `transferComplete`; ordered `blocks` with textual `length`/`data`; `assembledFile`, `storedFileAfter`; declined `responseData`/`displayedError` | Measured ASCII N3 block lengths, ordered assembly capped at 9999, whole-file replacement and unchanged decline display |
| `receipt` | Ordered `receivedLines`, `retainedLines`, `printedLines` | Source-170 bounded receipt lines retained and subsequently printed in the same order; source-169 length conflict remains separate |
| `cardTable` | `receivedData`, `storedDataAfter` | Received table data replaces retained device data |
| `siteConfiguration` | Boolean `businessDayComplete`; ordered `changes`, `sentData` arrays | Complete day: only final change sent, or no send if no change |
| `storeNumberActions` | Boolean `displayed`, `printed`, with masked Element 98 | Neither display nor print the masked value |

The [41 additional processing probes](../../../test-output/test-json/chapter-13-semantic-probes.json)
extend the previous 437-execution package by 17 predicate chains. Missing evidence,
partial transfer/day histories and unavailable byte encoding remain explicit
reviews; malformed controls and contradictions are invalid. Histories are supplied
observations, not proof of host authenticity or cryptographic trust.

Both Chapter 11 and the element-reference validator inherit parent history and
reject explicit contradictory child history. Validation does not modify inputs.

[Atl105AiElementIntake](../../../../../src/main/java/com/coreauth/validator/validation/Atl105AiElementIntake.java)
has opt-in actual-payload validation; the existing constructor/default CLI retain
legacy metadata mode. Use the four-argument CLI:

```text
Atl105AiElementIntake <ATL105-pack> <qe-shaped-run-folder> <report.json> --chapter13-semantics
```

Actual root names must be exact extracted Section 11 family names or existing
reviewed crosswalk aliases. Segment identities come from actual textual
`SegmentType`, or a unique source Data Type Indicator, not metadata. Indicators
`#`, `!`, `:`, `@`, `$`, `\`, `^` and `%` identify DL1-DL8 respectively;
overloaded `&`/`K` are not guessed. Reviewed aliases also cover Software Load
Phone Response, Date & Time Load Response and ECA/TeleCheck Service Transaction
Request. Aliases establish candidate intake mappings, never confirmed matches.
Every mapped repeated occurrence is evaluated
separately. Repeated metadata needs an unambiguous matching occurrence name; its
value is never compared against an arbitrary first occurrence. Only source-known
envelope identities 55/63 and source/catalog-grounded segment fields are mapped.
Non-text values are not coerced, and metadata-only values are not executed.
Mapping uncertainty remains review-required, not invented source prohibitions.
The CLI rejects report paths inside the producer input folder to prevent
accidental input overwrite; report provenance includes the pack-relative run path.

The [actual AI batch report](../../../test-output/test-solution-independent-review/chapter-13-ai-semantic-intake-2026-09-29-phase1.json)
assesses the 2026-09-29 phase-1 single-leg batch: **37 cases**, with source-mapped
observations for **32** and unsupported root labels for **5**. Case verdicts are
29 invalid and 8 review-required; the 253 executed observations contain 64
invalid and 189 review-required results. These are artifact-defect candidates,
not full-message certification or a negative-test detection ratio: the batch
labels its scenarios `field_constraint`, not positive/negative. The report
persists per-case input SHA-256 and does not copy AI values. All 74 input files
were verified unchanged after execution.

### Contextual, operational and composite continuation

[Chapter13ContextValidator](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13ContextValidator.java)
adds source-local predicates for address/terminal layouts, card and response-code
families, load indicators, versions/passwords, account/track splitting and
retention, manual check/loyalty/fuel/EBT conditions, partial approval, retry and
reversal echoes, print output, remaining-balance display, cash-back fees, sequence
reuse, loaded Key ID, following Segment 112, card sequence and last-interface TLVs,
tokenized network gates, SafeKey, Moneris/table layouts, dial ordering, connection,
key-update scheduling, merchant currency and observed automatic cut scheduling.
Requiredness is scoped to the correct family and segment, not leaked across
sibling observations. SafeKey Secure ID 25/26 requires Element 203 in Segment
123: complete omission is invalid, partial omission requires review, and the
condition does not leak into unrelated siblings.
A recognized parent response family overrides contradictory
qualifiers. Source hour 24/minute 60 is not normalized into an invented ISO time;
that scheduling correlation remains review-required. Transformed EDATA is never
rejected by applying the unencrypted stored-value PAN width.

[Chapter13CompositeValidator](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13CompositeValidator.java)
measures the 33 PIN/KSN shape, 153 discount blocks, 154 WIC N4 totals/EA/PS
layouts and 164 Program TAG/LEN/details. It reuses the isolated
`Segment103PayloadValidator.validateElement` entry point instead of fabricating
sibling payload fields. All five published PS bitmaps, item indicator/padding,
declared data length, reserved actions and supplied CVB quantity/price context
are exercised. Program record count, measured lengths, IT address/ZIP and
request/response tags are checked.

The published WIC purchase worked example declares `PS034` but displays a
37-character body. Fixtures use the positional tables; the validator is not
weakened to fit that contradictory example. EF's descriptor establishes an
eight-byte date, not unambiguous N3 framing. EF decoding requires the explicit
selected `qualifiers.wicEncodingProfile: "TAG_N3"`; its framing remains a separate
review even when the selected layout/date checks pass. Unselected EF framing
is review-required, not automatically invalid. Cryptographic validity is not
inferred from PIN/KSN, TLV or key-table structure.

The changes were necessary because width/enum checks alone cannot validate
conditional usage, processing actions or nested byte lengths. Independent
[245 semantic probes](../../../test-output/test-json/chapter-13-semantic-probes.json)
and [boundary/control tests](../../../../../src/test/java/com/coreauth/validator/Chapter13ContextAndCompositeTest.java)
verify the new behavior and retain whole-observation non-certification.

Verification: the expanded 819-test integration gate passed. The subsequent full
regression ran 2,483 tests, with 2,481 passing and the two known appendix failures.
After the final EF-framing and missing-SafeKey guards, the smallest covering
273-test suite passed with no failures/errors/skips, and both persistent evidence
and the real AI report were regenerated. The full suite was not rerun after
those final two guards.

**Chapter 13 remains IN_PROGRESS; full non-SME completion has not been achieved.**
The new evidence labels uncompleted processing reconciliation as an
implementation gap, not an SME blocker. All successful whole observations
remain review-required, separate from passed target predicates.
The scalar baseline is still ambiguous or
unextracted for 2/33/43/51/94/118/183/184/202/203/243. Some of these already have
bounded segment/appendix checks (including the new hardware profile); they are
not automatically missing code. Remaining source-to-predicate, applicability,
encoding and lifecycle reconciliation must use existing oracles before adding
new logic.

| Elem # | Name | Format/Length | Allowed Values or Reference | Notes |
|--------|------|---------------|---------------------------|-------|
| 1 | Access Code | AN, 12 bytes max | Variable up to 12 alphanumeric; "B" for pause | Used for dial strings; PBX access codes |
| 2 | Account Number | ANS, context-specific length, up to 425 bytes in TransArmor mode | Per element definition; varies by TransArmor mode | Identifies card or account; Track 1/2 data rules apply. **Compressed detail** (`extracted_text.txt` lines 19397-19578): non-TransArmor requests are up to 24 bytes but responses are a fixed 25 bytes; TransArmor is 25-byte header + up to 400-byte EDATA (see Appendix AA); Stored Value is 16 or 19 bytes; Check Services up to 23 bytes; Express Code exactly 25 digits; ComCheck exactly 10 digits; Money Code 10-21 digits. There is no universal minimum of 24 bytes. |
| 3 | Address Line 1 | AN, 24 bytes | Valid street address (24 alphanum chars) | Device location street address |
| 4 | Address Line 2 | AN, 21 bytes | City (12) + Space (1) + State Code alphabetical (2) + Space (1) + ZIP (5); per Appendix D | Device location city, state, ZIP |
| 5 | Approval Number | AN, 6 bytes | Any alphanumeric; space-filled | Required on reversals; indicates preauthorization if in purchase request |
| 6 | Approved Amount | N, 9 bytes | 000000001-999999999; fixed 9 digits, 2 assumed decimals | Amount approved by issuer; may differ from requested amount |
| 7 | Authorizer Code | N, 2 bytes | 00-98; see Appendix C | Identifies authorizer (BUYPASS-defined) |
| 8 | Authorizer Response Code | AN, 2 bytes | Any valid code from BUYPASS/authorizer | Identifies response type; from BUYPASS |
| 11 | Block Number | N, 3 bytes | 000, 001, or specific block number | Tracks data blocks in Electronic Mail & CA Public Key File responses |
| 12 | Card Discretionary Block Data | AN, 51 bytes max | Variable up to 51 alphanum; expiration date + discretionary data | Format varies by application; Track 1/2 parsing rules |
| 13 | Card Label | AN, 4 bytes | "CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR", "CK", "EF", "EC", "SV1-4", "ECA", "EWIC", "EK" | Identifies card type in Totals Response |
| 14 | Card Type | AN, 3 bytes | Valid codes per Appendix E | Identifies card in transaction or feature in Table Load |
| 15 | Card Type Total Amount | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Total amount for particular card type |
| 16 | Card Type Total Count | N, 5 bytes | 00001-99999; fixed 5 digits | Transaction count for card type in Totals Response |
| 17 | Cash Amount | N, 8 bytes max | 1-99999999; variable, 2 assumed decimals | Cash back amount; includes fee if supported |
| 18 | Clerk ID | N, 10 bytes max | 1-9999999999; variable digits | Clerk identification number |
| 20 | Currency Code | N, 3 bytes | 001-999 per Appendix L; default 840 (USD) | Identifies currency by country |
| 21 | Current Date | N, 6 bytes | MMDDYY | From BUYPASS; includes timezone/DST adjustments |
| 22 | Current Time | N, 4 bytes | HHMM (01-24 hours, 01-60 minutes) | From BUYPASS; includes timezone/DST adjustments |
| 23 | Cut Time | N, 4 bytes | HHMM (01-24 hours, 01-60 minutes) | Merchant end-of-day settlement time |
| 24 | Data Type Indicator | AN, 1 byte | "#", "!", ":", "@", "$", "\", "&", "K", "^", "%" | Identifies segment type in host response |
| 25 | Day of the Week | N, 1 byte | 0-6 (0=Sunday, 6=Saturday) | From BUYPASS |
| 26 | Decline Code | AN, 2 bytes | Any valid BUYPASS decline code | Identifies decline reason; see Appendix H for codes allowing intervention |
| 27 | Dial String Terminator | AN, 1 byte | "A" (first dial string), "F" (second dial string) | Marks end of dial string |
| 28 | Dial String Type | N, 1 byte | Fixed value: 1 | Indicates transaction dial strings |
| 29 | Direct Marketing Invoice Number | AN, 10 bytes max | Variable up to 10 alphanum | Invoice number for Direct Marketing/AVS requests |
| 30 | Download Indicator | N, 1 byte | 0 (no download), 1 (partial load), 8 (update key), 9 (update public key) | Indicates device load type |
| 31 | Driver/Identification Number | N, 10 bytes max | Variable up to 10 digits | Fleet card driver identification (unencrypted) |
| 32 | Employee Number | N, 4 bytes | Fixed 4 digits; use "1111" if none assigned | Utility transaction employee (reporting only) |
| 33 | Encrypted PIN Block Data | AN, 36 bytes | 16-20 byte KSN + 16-byte PIN block | Required for debit/EBT; DUKPT encryption |
| 34 | End-of-Data Indicator | A, 1 byte | Fixed value: "~" | Marks end of data segment |
| 35 | End-of-Load Indicator | A, 1 byte | Fixed value: "*" | Marks end of Table Load |
| 36 | Extract Date | N, 6 bytes | MMDDYY | Date info extracted for Electronic Mail |
| 37 | Extract Time | N, 4 bytes | HHMM | Time info extracted for Electronic Mail |
| 38 | Fee Amount | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Fee calculated by BUYPASS |
| 39 | Firmware Version | AN, 8 bytes | Fixed 8 alphanum characters | Device firmware version |
| 40 | Fleet Employee Number | N, 10 bytes max | Variable up to 10 digits | Fleet card employee identifier |
| 41 | Fuel Purchase Amount | N, 8 bytes max | 1-99999999; variable, 2 assumed decimals (7 bytes max for non-Amex) | Total fuel dollar amount |
| 42 | Grand Total | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Final total in Totals Response |
| 43 | Hardware Version | AN, 4 or 8 bytes | Fixed 4 or 8 alphanum characters | Device hardware version |
| 44 | Information Byte | AN, 1 byte | "?" (download), "0" (single-message), "1" (multimessage) | Identifies request type |
| 45 | Initiation Date | N, 6 bytes | MMDDYY | Local date transaction performed (BUYPASS-calculated) |
| 46 | Initiation Time | N, 4 bytes | HHMM | Local time transaction performed (BUYPASS-calculated) |
| 47 | Job Number | N, 10 bytes max | Variable up to 10 digits | Fleet card job identifier |
| 48 | Load Type | A, 1 byte | "P" (partial), "D" (date/time), "K" (TransArmor key or CA Public Key File load), "S" (signing key ID) | Identifies load type for TransArmor/EMV. **Dual meaning**: code "K" means TransArmor PKI Key Load in one context and EMV CA Public Key File Load in another (`extracted_text.txt` lines 20499-20521); the latter requires BUYPASS Device Type "+*" per source note. Context, not the code alone, disambiguates - same pattern as the Element 83 context-dependent codes above. |
| 49 | Local Date and Local Time | N, 10 bytes | MMDDYYHHMM | Local date/time of preauthorized transaction |
| 50 | Local Time | N, 4 bytes | HHMM | Time of Electronic Mail data segment |
| 51 | Mail Text Data | AN, up to 750 bytes | Variable alphanum (IP: 750 max, dial: 150 max) | Data in Electronic Mail Response |
| 52 | Mail Text Data Length | N, 3 bytes | 001-750 (IP) or 001-150 (dial) | Length of Mail Text Data |
| 53 | Merchant Name | AN, 24 bytes | Fixed 24 alphanum chars | Merchant name at device location |
| 54 | Merchant Phone Number | AN, 13 bytes | Fixed 13 alphanum chars, format (nnn)nnn-nnnn | Telephone of device location |
| 55 | Message Format Version Identifier | AN, 6 bytes | Fixed value: "ATL105" | Message format identifier |
| 56 | Net Amount | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Grand Total minus Fee Amount |
| 57 | New Software Version | AN, 8 bytes | Fixed 8 alphanum characters | New software application version number |
| 58 | Nonfuel Amount | N, 8 bytes max | 1-99999999; variable, 2 assumed decimals (7 bytes max for non-Amex) | Non-fuel transaction dollar amount |
| 59 | Number of Card Types | N, 2 bytes | 01-99; fixed 2 digits | Count of card types in Merchant Data Segment |
| 61 | Number of Print Lines | N, 1 byte | 1, 2, or 3 | Count of Terminal Display/Printer Messages in response |
| 62 | Number of Products | N, 2 bytes | 01-10; fixed 2 digits | Count of products in transaction |
| 63 | Number of Segments | N, 2 bytes | Variable up to 2 digits | Count of BUYPASS data segments in request |
| 64 | Odometer | N, 8 bytes max | 1-99999999; variable digits | Odometer reading from fleet card transaction |
| 65 | Password | N, 6 bytes max | 1-999999; default "123456" | Password for end-of-day function |
| 66 | Pause Indicator | AN, 1 byte | Fixed value: "B" | 1-second pause for slow phone systems |
| 72 | PC Duty Amount | N, 7 bytes max | Variable up to 7 digits, 2 assumed decimals | Duty amount in Purchase Card transaction |
| 73 | PC Freight Amount | N, 7 bytes max | Variable up to 7 digits, 2 assumed decimals | Freight charges in Direct Marketing/AVS request |
| 74 | PC Tax Amount | N, 7 bytes max | Variable up to 7 digits, 2 assumed decimals | Tax in Purchase Card Data Segment (distinct from Element 99) |
| 75 | Phone Number | N, up to 18 bytes | Variable up to 18 digits | Primary/secondary/tertiary phone number for dialing |
| 76 | Product Amount | N, 12 bytes max | 1-999999999999; variable, 2 assumed decimals | Monetary value of product (1-10 products repeating) |
| 77 | Product Code | N, 3 bytes | Fixed 3 digits per Appendix F | Product type identifier; see Appendix F |
| 78 | Prompt Code | AN, 3 or 4 bytes | 3-char or 4-char; position 1 = Transaction Type (Appendix G), positions 2-4 = Card Type (Appendix E) | Identifies transaction/card type and special data |
| 79 | Pump/Lane Number | N, 2 bytes max | 1-99; variable digits | Pump or lane identifier at POS |
| 80 | Purchase Code | AN, 16 bytes max | Variable up to 16 alphanum | Purchase code associated with transaction |
| 81 | Quantity | N, 9 bytes max | 00000000.01-399999999; variable, up to 3 assumed decimals | Number of product units (1-10 products repeating) |
| 82 | Redial Count | N, 1 byte | 1-3; fixed 1 digit | Number of times subsequent phone can be redialed |
| 83 | Response Code | AN, 1 byte | ~30 distinct values 0-9,B-Y; see table below | Indicates approval, decline, mail, test, or load response; several codes are context-dependent (same character means different things in different transaction families) |
| 84 | Segment Length | N, 3 or 4 bytes | Per-segment numeric range; see table below | Global list names 4-digit Segments 103, 114, 115, 118, 120, 130, 131. Own segment tables also state N4 for 134, 151 and 152; do not use the global list to override those tables silently. |
| 85 | Segment Type | N, 3 bytes | 100, 101, 102, 103, 104, 105, 108, 109, 111, 112, 116, 118, 119, 120, 123, 130, 131, 132, 134, 157, DL1-DL6 | Identifies segment type in request. **Source gap**: Segments 110 (Check) and 113 (ECA/TeleCheck) have defined lengths under Element 84 but are not listed in Element 85's own valid-codes enumeration (`extracted_text.txt` lines 21399-21457); not silently added here. |
| 86 | Sequence Number | N, 6 bytes | 000001-999999 (normal) or 100000-199999 (multithreaded) | Unique transaction identifier; must persist through lifecycle |
| 87 | Service Level | A, 1 byte | "F" (full-serve), "S" (self-serve), "N" (mini-serve), "X" (maxi-serve), "H" (high-speed), "O" (other), 0-9 (private) | Sale type in Product Data Segment |
| 88 | Ship-from Postal Code | AN, 10 bytes | Fixed format: nnnnn-nnnn | Postal code of shipping location |
| 89 | Ship-to Country Code | N, 3 bytes | Fixed 3 digits | Country code of shipment destination |
| 90 | Ship-to Postal Code | AN, 10 bytes | Fixed format: nnnnn-nnnn | Postal code of shipment destination |
| 91 | Software Load Phone Number | AN, 18 bytes max | Variable up to 18 alphanum | Phone number for device management system |
| 92 | Software Load Request Date | N, 6 bytes | MMDDYY | Date device requests full application load |
| 93 | Software Load Request Time | N, 4 bytes | HHMM | Time device requests full application load |
| 94 | Software Load Type | A, 1 byte | "F" (full application), "P" (partial application) | Type of software load |
| 95 | Software Terminal Record ID | AN, 13 bytes | Fixed 13 alphanum | ID from device management system |
| 96 | Software Version | AN, 8 bytes | Fixed 8 alphanum | Device software version at customer location |
| 97 | Start-of-Data Block Indicator | A, 1 byte | Fixed value: ")" | Marks start of data block in Table Load Response |
| 98 | Store Number | N, 16 bytes | 0000000000000001-9999999999999999; fixed 16 digits | Store identifier (asterisks if masked) |
| 99 | Tax Amount | N, 8 bytes max | 1-9999999; variable, 2 assumed decimals (7 bytes max for non-Amex) | Transaction tax amount in Standard Message Data Segment (distinct from Element 74) |

### Element 83 (Response Code) - full enumeration

Source: `extracted_text.txt` lines 21186-21312. Cross-checked against
`Atl105ResponseCodeFamilyOracle.java`, which already implements this full
enumeration correctly, including the four context-dependent codes below -
this is a documentation-table gap, not a code defect.

| Code | Meaning | Family |
|---|---|---|
| 0 | Approved - Purchase/Capture | financial |
| 1 | Approved - Communications Test **or** Declined - all other transactions | communications-test / financial (context-dependent) |
| 2 | Approved - Authorization only | financial |
| 3 | Approved - Authorization only with AVS | financial |
| 4 | Approved - Purchase/Capture with AVS | financial |
| 5 | Approved - Totals | totals |
| 6 | Approved - Totals with electronic mail retrieval pending | totals |
| 7 | Declined - Totals with electronic mail retrieval pending | totals |
| 8 | Approved - Electronic mail | electronic-mail |
| 9 | Approved - Electronic mail and more mail pending | electronic-mail |
| B | Approved - Totals with electronic mail retrieval pending and proprietary data pending | totals |
| C | Declined - Totals with electronic mail retrieval pending and proprietary data pending | totals |
| D | Approved - Totals with proprietary data pending | totals |
| E | Declined - Totals with proprietary data pending | totals |
| F | Approved - Partial approval | financial |
| G | Approved - Electronic mail and end of proprietary data block | electronic-mail |
| H | Approved - Proprietary data retrieval, more pending | proprietary-load |
| J | Approved - Electronic Mail, reset | electronic-mail |
| K | Approved - TransArmor Load | transarmor |
| L | Rejected (merchant not TransArmor-enabled, or signing key invalid/outdated) **or** Approved - EMV Key Load, last block | transarmor / emv-key-load (context-dependent) |
| M | Approved - EMV Key Load, more pending **or** Declined - Totals with Proprietary Host Discount data pending | emv-key-load / totals (context-dependent) |
| N | Approved - Totals with Proprietary Host Discount data pending | totals |
| O | Approved - Proprietary data load, more pending | proprietary-load |
| P | Successful Signing/Signed Key Load Request | transarmor |
| S | Declined - Retry the transaction | financial |
| T | Approved - Proprietary data load, no more data pending | proprietary-load |
| U | Declined - Proprietary data load, no more data pending | proprietary-load |
| V | Declined - Totals with Proprietary Custom Receipt Text data pending | totals |
| W | Approved - Totals with Proprietary Custom Receipt Text pending | totals |
| X | Declined - Proprietary data load, proceed to next pending Prompt Code **or** Not Required/Rejected - EMV Key Load (checksum matches host) | proprietary-load / emv-key-load (context-dependent) |
| Y | Approved - Proprietary data load, proceed to next pending Prompt Code | proprietary-load |

A single character means different things depending on the transaction
family in progress; `Atl105ResponseCodeFamilyOracle.CONTEXT_DEPENDENT_CODES`
already marks codes `1`, `L`, `M`, `X` as requiring family context to interpret,
matching this source enumeration exactly.

### Element 84 (Segment Length) - per-segment numeric ranges

Source: `extracted_text.txt` lines 21396-21398 and the preceding table at
lines 21351-21378. **No current validator enforces these per-segment bounds**
(`spec-elements.json`'s `SegmentLength` entry only checks a generic 3-4 digit
numeric shape); enforcing them is Chapter 12 segment-training work, not done here.

| Segment | Range | 4-digit? |
|---|---|---|
| 100 (Standard Message) | 001-218 | No |
| 101 (Fleet) | 001-061 | No |
| 102 (Product Code) | 001-381 | No |
| 103 (EBT) | 001-3334 | Yes |
| 104 (Purchase Card) | 001-086 | No |
| 105 (Totals) | 001-409 | No |
| 108 (Loyalty Card) | 001-142 | No |
| 109 (Electronic Mail) | 001-232 | No |
| 110 (Check) | 001-168 | No |
| 111 (Variable Information) | 001-999 | No |
| 112 (Additional Information) | 001-999 | No |
| 113 (ECA/TeleCheck) | 001-156 | No |
| 114 (SKU) | 0001-1010 | Yes |
| 115 (Print Data) | 001-1009 | Yes (per text's own 4-digit exception list) |
| 116 (TransArmor Load) | 01-50 | No |
| 118 (Proprietary Data Load) | 0001-3800 | Yes |
| 119 (Totals w/ Proprietary Data Load) | 001-493 | No |
| 120 (Print Data 2) | 001-1009 | Yes |
| 123 (NFC Payment Tokenization) | 001-186 | No |
| 130 (EMV Request) | 001-3043 | Yes |
| 131 (EMV Response) | 001-3834 | Yes |
| 132 (CA Public Key File) | 01-77 | No |
| 134 (Transaction Attributes) | 01-19 | No |
| 157 (Adjusted Product Code) | 001-381 | No |

**This independently corroborates three already-known SME-flagged length
conflicts** recorded in
[section1-section2-business-requirement-refinement.md](atl105-knowledge-notes/section1-section2-business-requirement-refinement.md)
and `section-11-message-layout-index.json`: Segment 119 (493 here vs. 389 in
Section 11.4.1.2), Segment 130 (3043 here vs. 9999 in Section 11.8.1), and
Segment 131 (3834 here vs. 3850 in Section 11.8.2). This table is Chapter 13's
side of each conflict; it does not resolve which side is correct.

---

## Data Element Definitions (100-243)

| Elem # | Name | Format/Length | Allowed Values or Reference | Notes |
|--------|------|---------------|---------------------------|-------|
| 100 | Terminal Display Communications Message | A, 16 bytes | Fixed: "COMM_TEST_PASSED" | 14 upper-case alpha chars + 2 spaces |
| 101 | Terminal Display/Printer Message | AN, 31 bytes | Free text, space-filled to 31 chars | Repeatable up to 3x per Number of Print Lines (Elem 61) |
| 102 | Terminal Identifier | AN, 22 bytes max | Device Type (2) + State Code/Appendix D (2) + Merchant Number (6-15) + Device Number (3) | 13 bytes for Table/Phone/Date-Time/Software Load requests |
| 103 | Text Data | AN, 150 bytes max | Free-form text | Electronic Mail Data Segment |
| 104 | Text Data Length | N, 3 bytes | 001-750 | Length of Text Data (Elem 103) |
| 105 | Totals Date | N, 6 bytes | MMDDYY / 111111 / 222222 / 333333 / 444444 / 555555 / 999999 | Special codes for relative-date totals requests |
| 106 | Unit of Measure | A, 1 byte | C,G,H,I,K,L,M,P,Q,U,W,Z,O,0-9 | M=EV charging minutes, W=kWh |
| 107 | Unit Price | N, 9 bytes | 000000.000-999999.999 | Up to 3 assumed decimals |
| 108 | Vehicle Number | N, 10 bytes max | Any numeric | Fleet Data Segment (101) |
| 109 | Voucher ID | N, 10 bytes max | (not stated) | Preprinted voucher for local-approval EBT |
| 111 | Variable Information Indicator | AN, 3 bytes | 001-081 (see Appendix I) | Identifies Table ID for Element 113 |
| 112 | Variable Information Length | N, 3 bytes | 001-999 | Length of Element 113 |
| 113 | Variable Information | AN, 982 bytes max | Per Table ID in Element 111; see Appendix I | a-z, A-Z, numeric |
| 114 | Software Load IP/URL Address | AN, 30 bytes | 01-999, a-z, A-Z | Software IP Load Data Segment (DL5) |
| 115 | Additional Information Data Segment Flag | N, 1 byte | 0 (none follows), 1 (follows) | Financial Transaction Response |
| 116 | Additional Information Indicator | N, 3 bytes | 001-047 (see Appendix K) | Identifies Table ID for Element 118 |
| 117 | Additional Information Length | N, 3 bytes | 001-985 | Length of Element 118 |
| 118 | Additional Information | AN, 984 bytes max | Per Table ID in Element 116; see Appendix K | a-z, A-Z, numeric |
| 118 | EMV Additional Information | ANSB, 984 bytes max | 001-984, a-z, A-Z | Distinct element also numbered 118; see Appendix T |
| 119 | Settlement Date | N, 4 bytes | MMDD (01-12, 01-31) | Actual financial settlement date |
| 120 | Network Management Message | A, 8 bytes | Fixed: "COMMTEST" | Communications test message |
| 121 | Partial Approval Indicator | N, 1 byte | 0 (not supported/default), 1 (supported), 5 (balance receipt only, Amex prepaid) | Mandatory population for all card-present transactions |
| 122 | MICR Data | AN, 50 bytes max | (not stated) | Required on all check transactions; also mirrored in Element 2 |
| 123 | Driver's License | AN, 40 bytes max | (not stated) | Check transaction ID |
| 124 | State Code | AN, 2 bytes | Per Appendix D (alphabetical) | Used with Driver's License (Elem 123) |
| 125 | Date of Birth | N, 8 bytes | MMDDYYYY | Check transaction |
| 126 | Check Type | AN, 1 byte | P (Personal), C (Company) | |
| 127 | Check Number | AN, 8 bytes max | (not stated) | |
| 128 | Customer Phone Number | N, 10 bytes max | (not stated) | Check presenter's phone |
| 129 | Customer Last Name | AN, 24 bytes max | (not stated) | Check presenter |
| 130 | Check Issue Date | N, 8 bytes | MMDDYYYY | |
| 131 | ECA/TeleCheck Clerk ID | AN, 6 bytes max | 1-999999 | |
| 132 | ECA/TeleCheck Product Code | AN, 6 bytes max | (not stated) | |
| 133 | ECA/TeleCheck Phone Number | N, 10 bytes max | (not stated) | |
| 134 | ECA/TeleCheck Trace ID | AN, 22 bytes max | (not stated) | |
| 135 | Merchant Trace ID | AN, 25 bytes max | (not stated) | Optional merchant-assigned ID |
| 136 | Denial Record Number | AN, 7 bytes max | (not stated) | Declined ECA/TeleCheck transaction |
| 137 | Extended MICR Data | AN, 65 bytes max | (not stated) | Used with Element 122 when raw MICR exceeds length |
| 138 | Loyalty Program ID | N, 6 bytes max | "ERN" (per source) | Loyalty Card Data Segment (108) |
| 139 | Loyalty Account Number | N, 24 bytes max | (not stated) | |
| 140 | Points to Redeem | N, 6 bytes max | (not stated) | |
| 141 | Coupon ID | N, 19 bytes max | (not stated) | |
| 142 | Coupon Amount | N, 8 bytes max | (not stated) | |
| 143 | Update Code | AN, 1 byte | A,C,E,I,P,S,T,U | Add/Coupon/Expiration/Inquiry/Points/Sale/Totals/Update |
| 144 | Street Address | N, 5 bytes max | (not stated) | Consumer street number, loyalty |
| 145 | Phone Number, Loyalty | N, 10 bytes max | (not stated) | |
| 146 | Expiration Date | N, 4 bytes | MMYY | Loyalty transaction |
| 147 | Loyalty Track 2 Data | AN, 38 bytes max | (not stated) | |
| 148 | Payment Tender Type | AN, 2 bytes | AX,CK,CS,DB,DN,DS,EB,EC,FL,GC,JC,MC,PC,PR,VS | CS = loyalty-only |
| 149 | SKU Data | AN, 1000 bytes max | (not stated) | Bar code SKU, Segment 114 |
| 150 | Loyalty Information Version | N, 1 byte | 1 (Table ID 008), 2 (Table ID 010) | Defaults to 1 if omitted |
| 151 | Unit of Work | N, 19 bytes | (not stated) | Required on loyalty reversals |
| 152 | Print Data | ANS, 900 bytes max | (not stated) | Print Data Segment (115) |
| 153 | WIC Discount Amount | N, 40 bytes max | Account Type=97, Amount Type=52, Currency per Appendix L, Amount sign 0/C/D | Positional subfields |
| 154 | WIC Product Data | AN, 3001 bytes max | Composed of Total Length + up to 6 subelements (EF/EA/PS tags) | EBT Data Segment (103); see Appendix M |
| 155 | Key ID | AN, 11 bytes | Any valid Key ID (incl. CA Key ID) | TransArmor PKI Load Response |
| 156 | Key Data Length | N, 3 bytes | 000-999 | Length of Element 157 |
| 157 | Key Data | AN, 999 bytes max | Any valid key | TransArmor Load Response |
| 158 | License # | AN, 10 bytes max | (not stated) | Fleet card, Segment 101 |
| 159 | Job ID | AN, 12 bytes max | (not stated) | Fleet card, Segment 101 |
| 160 | Department # | AN, 12 bytes max | (not stated) | Fleet card, Segment 101 |
| 161 | Customer Data | AN, 12 bytes max | (not stated) | Fleet card, Segment 101 |
| 162 | User ID | AN, 12 bytes max | Any except zero | Fleet card, Segment 101 |
| 163 | Vehicle ID# | AN, 8 bytes max | (not stated) | Fleet card, Segment 101 |
| 164 | EBT Program Data | AN, 267 bytes max | Total Length (3) + 1-6 Program Data subelements (max 44 bytes each) | TAG 50/51/52/IT; see Appendix M |
| 165 | Start Date or End Date | N, 8 bytes | CCYYMMDD; 00000000=immediate, 99999999=no end | Proprietary Data Load Segment (118) |
| 166 | Start Time or End Time | N, 4 bytes | 0000-2359 | Used with Element 165 |
| 168 | Number of Receipt Text Lines | N, 2 bytes | 1-10 | Prompt Code 901 |
| 169 | Receipt Text Data Length | N, 2 bytes | 1-40 | Prompt Code 901 |
| 170 | Receipt Text Data | AN, 20 bytes max | (not stated) | Prompt Code 901 |
| 171 | Number of Discounts | N, 2 bytes | (not stated) | Prompt Code 904 |
| 172 | Product Discount Amount | N, 5 bytes | Format 99v999 | Prompt Code 904 |
| 173 | BUYPASS Card Type | N, 4 bytes | Format 9999 | Dynamic BIN table match |
| 174 | Card Table Type | N, 4 bytes | 0001 BIN, 0002 RULES, 0003 RESTRICTIONS, 0004 SAF, 0005 PROMPT, 0006 PRODUCT | Prompt Code 902 |
| 175 | Card Table Data | AN, 3600 bytes max | (not stated) | Prompt Code 902 |
| 176 | Device Card Table Version | N, 35 bytes | Leading "99999" = no card table used | |
| 177 | Card Table Load Version | N, 35 bytes | (not stated) | |
| 178 | Load Control Key | AN, 60 bytes | (not stated) | |
| 179 | Host Discount Timestamp | N, 12 bytes | CCYYMMDDHHMM | |
| 180 | Site Configuration Data | AN, 3600 bytes max | (not stated) | Prompt Code 903 |
| 182 | Prompt Code, Pending | AN, 4 bytes | 0901,0902,0904,0981 | Custom Receipt/Proprietary Load/Host Discount/Electronic Mail |
| 183 | Card BIN Range, Beginning | AN, 12 bytes | 12 digits, left-justified space-filled | Prompt Code 904 |
| 184 | Card BIN Range, Ending | AN, 12 bytes | 12 digits, left-justified space-filled | Prompt Code 904 |
| 185 | Discount Quantity Limit | N, 3 bytes | (not stated) | Prompt Code 904 |
| 186 | Discount Program Description | AN, 15 bytes | (not stated) | Prompt Code 904 |
| 187 | CA Public Key File Checksum | N, 25 bytes | (not stated) | EMV Financial Transaction Request |
| 188 | EMV Card Sequence Number | AN, 3 bytes | 000-099, or 3 spaces if Tag 5F34 absent | EMV Tag 5F34 |
| 189 | EMV Chip Data Length | N, 3 bytes | 000-999 | Length of Element 190 |
| 190 | EMV Chip Data | ANSB, 999 bytes max | TLV combinations; must include Tag 9F06/84; must exclude Tag 5A/57 | See Appendix R |
| 191 | EMV Additional Information Indicator | N, 3 bytes | 001 (EMV table data) | |
| 192 | EMV Additional Information Length | N, 3 bytes | 001-985 | |
| 193 | CA Public Key File Block Length | N, 3 bytes | 000-999 | Length of Element 194 |
| 194 | CA Public Key File Block | AN, 9999 bytes max | Any valid key; error codes RQST/CURRENT HASH MATCH, BLOCK NBR NOT NUMERIC, BLOCK NBR NOT 000-xxx | See Appendix S |
| 195 | CAVV Revised Format | AN, 20 bytes | Cardholder auth verification value data | Visa/Interlink tokenized only; see Appendix Y |
| 196 | Token Requestor ID | AN, 11 bytes | Left-justified, space-filled | Visa/MasterCard tokenized transactions |
| 197 | Token PAN Suffix | AN, 4 bytes | Last 4 digits of PAN | Visa/MasterCard/Interlink/Maestro tokenized |
| 198 | Settlement Type | AN, 1 byte | D (Dual/credit), S (Single/debit), X (Dual, non-traditional Signature Debit) | Common AID EMV / Signature Debit |
| 199 | Signature Required | AN, 1 byte | T (required), F (not required), space (device determines) | Common AID EMV / Signature Debit |
| 200 | Receipt Card Description | AN, 10 bytes | Left-justified, space-filled card type text (e.g. "STAR") | Common AID EMV / Signature Debit |
| 201 | Fuel Volume Data | AN, 3600 bytes max | (not stated) | Prompt Code 905 |
| 202 | Cryptogram Token Data | B64, 28 or 56 bytes | Base64: a-z,A-Z,0-9,+,/,= | In-app payment tokenization |
| 203 | Safekey Data | B64, 58 bytes | Pos 1-2 fixed "SK"; 3-30 AEVV; 31-58 AESK (Base64) | NFC Payment Tokenization Data Segment (123) |
| 204 | SafeKey Response | AN, 1 byte | 0,1,2,3,4,5,6,7,8,9,A,B,C,D,U | AEVV validation result codes |
| 205 | Load Subtype | AN, 1 byte | M (Moneris Key Load) | |
| 206 | SPDH Header | AN, 48 bytes | See Appendix V | Moneris Key Load request/response |
| 207 | Moneris Terminal Identifier | AN, 8 bytes | (not stated) | From Moneris terminal initialization |
| 208 | Moneris Merchant ID | AN, 13 bytes | (not stated) | From Moneris terminal initialization |
| 209 | Moneris Response Code | AN, 2 bytes | (not stated) | For MAC Encryption |
| 210 | MAC | AN, 16 bytes | Any alphanumeric | Moneris debit transactions |
| 211 | Moneris Key Indicators | AN, 3 bytes | Each char Y/N: MAC key, Data Encryption key, PIN Encryption key present | |
| 212 | Moneris Key Data | AN, 16 bytes | Any alphanumeric | One iteration per "Y" in Element 211 |
| 213 | Moneris Data | AN, 100 bytes max | \<tag\>\<len\>\<data\> format | |
| 214 | Batch Number | AN, 3 bytes | 000-999 | Moneris batch |
| 215 | Language Indicator | AN, 1 byte | See Appendix V Moneris Language Indicator Matrix | |
| 216 | Response Display | AN, 16 bytes | Any alphanumeric | Moneris batch close response |
| 217 | Number of Debits | N, 4 bytes | 0001-9999 | Moneris batch |
| 218 | Debit Dollar Value | AN, 19 bytes | (+/-)000000000000000000-(+/-)999999999999999999 | Moneris batch |
| 219 | Number of Credits | N, 4 bytes | 0001-9999 | Moneris batch |
| 220 | Credit Dollar Value | AN, 19 bytes | (+/-)000000000000000000-(+/-)999999999999999999 | Moneris batch |
| 221 | Number of Corrections | N, 4 bytes | 0001-9999 | Moneris batch |
| 222 | Corrections Dollar Value | AN, 19 bytes | (+/-)000000000000000000-(+/-)999999999999999999 | Moneris batch |
| 223 | Inclusive/Exclusive for Tax 1 | AN, 1 byte | I,E,N | If N, tax type/amount omitted |
| 224 | Tax Type 1 | AN, 3 bytes | GST,HST,PST,QST (Canada) | Country-dependent |
| 225 | Tax Amount 1 | N, 9 bytes | 000000000-999999999 | Decimals per currency |
| 226 | Inclusive/Exclusive for Tax 2 | AN, 3 bytes | I,E,N | If N, tax type/amount omitted |
| 227 | Tax Type 2 | AN, 3 bytes | GST,HST,PST,QST (Canada) | Country-dependent |
| 228 | Tax Amount 2 | N, 9 bytes | 000000000-999999999 | Decimals per currency |
| 229 | Inclusive/Exclusive for Tax 3 | AN, 1 byte | I,E,N | If N, tax type/amount omitted |
| 230 | Tax Type 3 | AN, 3 bytes | GST,HST,PST,QST (Canada) | Country-dependent |
| 231 | Tax Amount 3 | N, 9 bytes | 000000000-999999999 | Decimals per currency |
| 232 | Download Data | AN, 100 bytes max | Any alphanumeric | |
| 233 | RID | AN, 10 bytes | Valid EMV Registered Application Provider identifier | |
| 234 | Stand-in Indicator | N, 1 byte | 1 (No), 2 (Domestic only), 3 (Domestic & Foreign) | |
| 235 | Floor Limit | N, 12 bytes | 000000000000-999999999999 | Max allowable stand-in value |
| 236 | BUYPASS RID Card Type | AN, 3 bytes | (not stated) | EMV Terminal Floor Limits Data Segment (DL8) |
| 237 | TAVV Cryptogram | AN, 28 bytes | (not stated) | NFC Payment Tokenization Data Segment (123); see Appendix Y |
| 238 | TAVV Result Code | AN, 1 byte | 1,2,3,4 | Cryptogram/DTVV validation results |
| 239 | Enhanced Fleet Data | AN, 999 bytes max | See Data Segment 145 | WEX OTR, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV, Voyager EMV |
| 240 | Available Product Information | AN, 17 bytes max | Any alphanumeric/special chars | WEX Available Product Fleet Information, Segment 148 |
| 241 | Price Data | AN, 999 bytes max | See Data Segment 149 | Fuel Price Update Request Segment |
| 242 | EV Charging Data | AN, 200 bytes max | See Data Segment 156 | EV Charging Data Segment |
| 243 | Extended Unit of Measure | A, 4 bytes | ACRE,ARES,CELI,CMET,EACH,FOOT,GBGA,GBOU,GBPI,GBQA,GRAM,HECT,INCH,KILO,KMET,LITR,METR,MILE,MILI,MMET,PIEC,PUND,SCMT,SMET,SMIL,SQFO,SQIN,SQKI,SQMI,SQYA,TONS,USGA,USOU,USPI,USQA,YARD | MasterCard Enhanced Fleet EMV non-fuel products |

---

## Extraction status

- **Extracted**: elements 1-243 (all element numbers found in the source; the document's "228 total" is a count with numbering gaps, not a max element number).
- Directly transcribed from `extracted_text.txt` lines 19367-25439 (chapter 13.2) in this pass; elements 1-99 were an earlier automated pass, spot-checked but not fully re-verified.

## Key cross-references

- Element 4 (Address Line 2) -> Appendix D (Valid State Codes)
- Element 7 (Authorizer Code) -> Appendix C (Valid Authorizer Codes)
- Element 14 (Card Type) -> Appendix E (Valid Card Type Codes)
- Element 20 (Currency Code) -> Appendix L (Valid Currency Codes)
- Element 77 (Product Code) -> Appendix F (Valid Payment Systems Product Codes)
- Element 78 (Prompt Code) -> Appendices E & G (Card Type + Transaction Type)
- Element 111/113 (Variable Information) -> Appendix I (Table IDs 001-081)
- Element 116/118 (Additional Information) -> Appendix K (Table IDs 001-047)
