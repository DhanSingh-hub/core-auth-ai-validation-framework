# Needs Review: 16-Rule Draft Repair Proposal

This source-only Test Solution supplement repairs the draft wording and test design for the 16-rule batch. It does not modify the canonical Test package, execute tests, or record authorized decisions.

## Repair Scope

| Treatment | Rules |
|---|---:|
| DATED_SOURCE_NOTICE_CONTEXT_BLOCKED | 1 |
| POLICY_ONLY_BLOCKED | 1 |
| SOURCE_DERIVED_COMPOSITE | 2 |
| SOURCE_DERIVED_ENUMERATION_COMPLETION | 4 |
| SOURCE_DERIVED_PROHIBITION | 1 |
| SOURCE_DERIVED_RULE | 4 |
| SOURCE_DERIVED_RULE_WITH_EXTERNAL_TIME_FIXTURE | 1 |
| SOURCE_DERIVED_SEQUENCE_WITH_EXTERNAL_DEVICE_FIXTURE | 1 |
| SOURCE_DERIVED_TITLE_CORRECTION | 1 |

## Required Follow-up

- Review all 16 corrected rule statements and anchors against source excerpts.
- Move `SEG119-R-035` to a policy gate or retain it blocked pending `SEG119-SME-005`; it is not sourced as an ATL105 runtime obligation.
- Keep `SEG145-R-004` historical and blocked pending `SEG145-SME-002` current product availability confirmation.
- Add approved independent expected outcomes and concrete request/response envelopes before promoting any draft cases to the canonical package.
- Wire/header and stateful software-load cases require a harness/device-management fixture; no host or production operation was attempted.

## Rule-by-Rule Corrections

### BR-RULE-SEG100-R-012

Current: TPDU Protocol ID is 0x60

Proposed: TCP/IP TPDU Protocol ID is fixed at 0x60

For the standard BUYPASS TCP/IP header, the one-byte TPDU Protocol ID shall be 0x60.

Repair class: `SOURCE_DERIVED_RULE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Logical-header probes only; full-message transport/wire validation remains separate.
Draft cases: TPDU-012-POS, TPDU-012-NEG

### BR-RULE-SEG100-R-024

Current: Reserved TPDU destination and source addresses are zero

Proposed: Reserved TPDU destination and source addresses are zero

In messages sent to BUYPASS, the two-byte TPDU Destination Address and two-byte TPDU Source Address shall each be 0x0000.

Repair class: `SOURCE_DERIVED_RULE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: The specification states this for messages to BUYPASS; do not generalize to other transports or peers.
Draft cases: TPDU-024-POS, TPDU-024-NEG

### BR-RULE-SEG100-R-066

Current: Declined financial responses carry a decline code

Proposed: Financial Response Code (Element 83) identifies decline

Element 83 is the one-byte Financial Response Code and identifies a financial response as an approval with purchase/capture, approval without purchase/capture, or decline. Keep the separate Element 26 Decline Code distinct.

Repair class: `SOURCE_DERIVED_TITLE_CORRECTION`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Response-family value validation must use the separate Element 83 family matrix; do not treat response code as Element 26.
Draft cases: RESP-066-CLASSIFY, RESP-066-DISTINCT

### BR-RULE-SEG101-R-022

Current: Fleet Tag format is 3-byte code plus up to 31-byte data payload

Proposed: Fleet Tag uses a fixed three-byte tag and up to 31 data bytes

Fleet Tag fields 1-5, when applicable, use a three-byte fixed tag followed by zero to 31 data bytes; the source limits these fields to Auth Completion messages when Host Prompts are supported.

Repair class: `SOURCE_DERIVED_RULE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: The generic maximum does not replace the per-code maximum in SEG101-R-024.
Draft cases: FLEET-022-POS, FLEET-022-NEG

### BR-RULE-SEG101-R-023

Current: Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table

Proposed: Fleet Tag code is one of the 17 source-listed Auth Completion codes

When Host Prompts are supported on an Auth Completion (0220), each three-byte Fleet Tag code shall be one of DLS, DLN, PON, INV, TRP, UNT, TLH, DOB, ZIP, END, MID, VIN, TRA, HUB, TLR, CBA, or VHT.

Repair class: `SOURCE_DERIVED_ENUMERATION_COMPLETION`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: This repairs the missing eleven codes in the current partial AI-derived comparison; the source table is the authority.
Draft cases: FLEET-023-ENUM, FLEET-023-CONTEXT

### BR-RULE-SEG101-R-024

Current: Fleet Tag data payload conforms to the per-code format

Proposed: Fleet Tag data obeys the source type and maximum for its tag code

For each Auth Completion Fleet Tag, data shall obey that code's source type and maximum length: DLS an3; DLN an22; PON an31; INV an31; TRP an15; UNT an31; TLH n6; DOB n8; ZIP an9; END an31; MID an31; VIN an17; TRA an15; HUB n9; TLR an15; CBA n6; VHT an10.

Repair class: `SOURCE_DERIVED_ENUMERATION_COMPLETION`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Semantic validation of values such as DOB/state/ZIP remains separate from declared type and length.
Draft cases: FLEET-024-BOUNDARIES

### BR-RULE-SEG102-R-017

Current: Fuel merchants send both fuel and nonfuel product data

Proposed: Fuel merchants send fuel and nonfuel product data

A fuel merchant shall send both fuel and nonfuel product data in the Product Code Data Segment.

Repair class: `SOURCE_DERIVED_RULE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Comdata exclusion and product/amount reconciliation remain separate rules, not silently included here.
Draft cases: PROD-017-POS, PROD-017-NEG-FUEL, PROD-017-NEG-NONFUEL

### BR-RULE-SEG103-R-020

Current: eWIC does not support Return transactions (absolute prohibition, not a code lookup)

Proposed: eWIC does not support Return transactions

The eWIC transaction flow shall not issue a Return transaction; this is an operation prohibition, not a response-code lookup.

Repair class: `SOURCE_DERIVED_PROHIBITION`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: ATL105 does not define a host decline response for an unsupported Return; do not invent one.
Draft cases: EWIC-020-PROHIBIT

### BR-RULE-SEG109-R-019

Current: Retrieval requests use Prompt Code 981, or 996 when retrieving proprietary card data, and allow up to 750 bytes of print data per request

Proposed: Electronic Mail Retrieval uses Prompt Code 981, or 996 for proprietary card data; print data is at most 750 bytes per request

A retrieval request shall use Prompt Code 981; retrieval of proprietary card data shall use 996. Print data shall not exceed 750 bytes per request.

Repair class: `SOURCE_DERIVED_COMPOSITE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Byte length is measured on encoded data; complete electronic-mail envelope/response behavior requires its own source-backed rules.
Draft cases: MAIL-019-981, MAIL-019-996, MAIL-019-BOUNDARY

### BR-RULE-SEG109-R-020

Current: Submission requests use Prompt Code 995, contain up to 217 bytes of data, and receive a confirmation response

Proposed: Electronic Mail Submission uses Prompt Code 995, at most 217 data bytes, and a receipt confirmation response

A submission request shall use Prompt Code 995, contain no more than 217 data bytes, and receive an Electronic Mail Response confirming receipt.

Repair class: `SOURCE_DERIVED_COMPOSITE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: The response must be bound to the same request before integration execution; no host behavior is claimed here.
Draft cases: MAIL-020-BOUNDARY, MAIL-020-CONFIRM

### BR-RULE-SEG119-R-035

Current: Merchant settlement cutoff and timezone behavior is approved before certification

Proposed: Settlement cutoff/time-zone approval prerequisite (policy register; not an ATL105 BR)

No source-derived runtime requirement is proposed. Preserve the certification policy prerequisite only in the policy/SME register until the policy owner defines the cutoff, timezone, and approval authority.

Repair class: `POLICY_ONLY_BLOCKED`; status remains `DRAFT_REVIEW_REQUIRED`.
Blocker: {"registerId": "SEG119-SME-005", "owner": "SME/TBA or settlement policy owner", "status": "OPEN", "requiredDecision": "Approve cutoff/time-zone policy or withdraw the certification gate.", "communicationRegisterId": "SEG119-SME-005"}
Note: Do not count this as an ATL105 source-derived BR or AI semantic gap; do not create an execution case without the policy definition.
Draft cases: none; blocked or context-gated

### BR-RULE-SEG145-R-004

Current: MasterCard Enhanced Fleet EMV functionality was, as of the specification's authoring, expected to be enabled in First Data production no earlier than June 2026; until then it is for development/certification preparation only and must not be used in live production transactions

Proposed: MasterCard Enhanced Fleet availability notice (ATL105 2026-3, historical date condition)

The 2026-3 specification states that MasterCard Enhanced functionality was expected in First Data production no earlier than June 2026 and, until enabled, was for development/certification preparation rather than live transactions.

Repair class: `DATED_SOURCE_NOTICE_CONTEXT_BLOCKED`; status remains `DRAFT_REVIEW_REQUIRED`.
Blocker: {"registerId": "SEG145-SME-002", "owner": "SME/TBA or product owner", "status": "OPEN", "requiredDecision": "Confirm current production enablement, effective date, and environment before deriving a current live-use test.", "communicationRegisterId": "SEG145-SME-002"}
Note: Keep the dated source statement; do not infer that the June 2026 milestone occurred.
Draft cases: none; blocked or context-gated

### BR-RULE-SEG155-R-005

Current: Account Updater Result Code (Table 006) is a fixed 6-character code from a documented enumeration (VAU001 through VAU016, non-contiguous — VAU015 absent), applicable only to Visa

Proposed: Visa Account Updater Result Code is a six-character Table 006 value from the source enumeration

When the response field is applicable (Visa and an Account Updater Request Indicator of Y or I), Table 006 has ID 006, length 006, and one of VAU001-VAU014 or VAU016; VAU015 is not defined in the source list.

Repair class: `SOURCE_DERIVED_ENUMERATION_COMPLETION`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Segment 155 has no separators; layout and parent transaction/Segment 111 context need independent fixture coverage.
Draft cases: UPDATER-005-ENUM, UPDATER-005-GATE

### BR-RULE-SEG156-R-006

Current: Connector Type (Table 12) uses a documented Visa-defined enumeration (001-003, 100-103, 200)

Proposed: Visa EV Connector Type is one of the eight source-defined codes

Connector Type Table 12, when present for the applicable Visa EV charging context, shall use 001, 002, 003, 100, 101, 102, 103, or 200.

Repair class: `SOURCE_DERIVED_ENUMERATION_COMPLETION`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Do not synthesize EV transaction tokens or infer network support beyond the source-defined Visa list.
Draft cases: EV-006-ENUM

### BR-RULE-SEGDL3-R-008

Current: A Date and Time Load is initiated by the device with Load Type 'D' (Date and Time Load Request: '?' + Terminal Identifier + 'D'); the merchant load flag does not have to be set, and the response includes adjustments for the initiating device's time zone and daylight saving time

Proposed: Date and Time Load request is device-initiated with Load Type D; response uses initiating-device timezone/DST

The request is the positional string '?' + 13-character Terminal Identifier + 'D', with no Field Separators; no merchant load flag is required. The response time is adjusted for the initiating device's timezone and daylight-saving setting.

Repair class: `SOURCE_DERIVED_RULE_WITH_EXTERNAL_TIME_FIXTURE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: Request serialization is locally source-testable; response clock conversion is not executable without a source-bound timezone fixture.
Draft cases: DL3-008-REQUEST, DL3-008-NEG, DL3-008-TIMEZONE

### BR-RULE-SEGDL4-R-007

Current: Software update processing: a bit on the merchant profile makes BUYPASS return Download Indicator 1 on the next transaction response; the device requests a load; BUYPASS returns DL4 and DL5; the device stores New Software Version, Software Terminal Record ID, Software Load Phone Number or IP/URL Address, Request Date and Request Time; at that date and time the device dials the Software Load Phone Number and requests a full application load; afterwards it requests a Table Load, which BUYPASS sends without the profile bit

Proposed: Software update follows the eight-step profile/download/load/schedule/completion sequence

Follow the source sequence: profile bit; Download Indicator 1 on the next transaction response; Table Load request; DL4/DL5 response; store version/record ID/address/date/time; scheduled full application load; on success request Table Load; BUYPASS sends that Table Load without the profile bit. Automated full-load attempts are limited to three; unsuccessful load prints the decline message.

Repair class: `SOURCE_DERIVED_SEQUENCE_WITH_EXTERNAL_DEVICE_FIXTURE`; status remains `DRAFT_REVIEW_REQUIRED`.
Note: No actual application image, network endpoint, device-management response, or production software data is included.
Draft cases: DL4-007-PATH, DL4-007-RETRY, DL4-007-DEVICE

## Governance

The 2026-3 ATL105 source is the authority for its enumerations and protocol statements. The independent Test Solution remains separate from AI artifacts. All draft vectors retain `REVIEW_REQUIRED`; they are not canonical Test Data, SME decisions, coverage credit, or execution evidence.

The attached Authorization.json was not ingested; no PAN, Track data, or production values are copied into this supplement.
