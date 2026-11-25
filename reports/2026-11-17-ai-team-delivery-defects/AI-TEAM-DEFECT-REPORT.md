# AI artifact delivery: defect and issue report – MasterCard Credit (17-11 run)

**Delivery reviewed:** `mastercard_credit_output_1611\mastercard_credit_new`, build
`buypass-atl105-mastercard-credit-generated-923e8c2eac`, run `20261116T152226421085Z` ("plan v12")  
**Report date:** 2026-11-17  
**Specification baseline:** ATL105 2026-4 (page numbers are PDF pages)  
**Status:** Open; submitted for AI-team investigation and correction  
**Scope:** Test case (TC) to test data (TD) consistency, field values in the delivered
TD, expected results, acceptance contexts, readiness metadata, client-input contract,
coverage and traceability, across all three packages.

## Summary for the AI team

The delivery broadens coverage to 15 acceptance contexts, but it does not yet
demonstrate semantic correctness or execution readiness. Please investigate and
correct the fourteen issues below. Where a decision belongs to the client, BA or
SME, keep it open as an input. Do not infer approved behaviour.

This report separates confirmed defects from execution blockers and coverage gaps.
It does not claim that all 3,205 TCs are invalid. 1,538 TCs have no confirmed
defect in this review, but none of them is certified.

| Package | Declared scope | Scenarios | TCs | Producer ready | TCs with ≥1 confirmed defect | Ready TCs with ≥1 confirmed defect |
|---|---|---:|---:|---:|---:|---:|
| network (`mastercard_credit_new`) | MasterCard Credit financial flows in 15 acceptance contexts | 196 | 2,129 | 619 | 1,047 | 271 |
| data_element_validation | ATL105 element-level rules | 35 | 1,029 | 790 | 610 | 390 |
| terminal_and_host_operations | Host load and terminal operation messages | 9 | 47 | 20 | 10 | 3 |
| **Total** | Three packages, one product | **240** | **3,205** | **1,429** | **1,667** | **664** |

- The defect columns are de-duplicated across issues. Issues N17-09, N17-12 (status
  part), N17-13 and N17-14 do not add TCs to these columns.
- In the network package, ready TCs exist in only four contexts: General Retail,
  CNP-0100, CNP-2100 and Petroleum attended.
  - All 13 ready Petroleum-attended TCs are defective.
  - Of the 348 ready network TCs without a confirmed defect, 77 have only a FLAG-tier
    expected result.
  - That leaves 271 ready, defect-free TCs that assert a response code: General
    Retail 226, CNP-0100 36 and CNP-2100 9.

### Priority convention

- **High:** incorrect transaction meaning, unsupported approval or decline, or a
  readiness claim that can cause an invalid execution handoff.
- **Medium:** incomplete executable data, missing inputs, coverage or traceability
  evidence needed to approve the solution.
- These are proposed remediation priorities, not security severity ratings.

| ID | Priority | Category | Issue |
|---|---|---|---|
| N17-01 | High | Confirmed composition defect | 447 positive TCs do not send the condition they claim to test |
| N17-02 | High | Confirmed composition defect | 159 negative TCs contain no trigger for the decline they assert |
| N17-03 | High | Confirmed composition defect | Original Authorization Only requests carry an Approval Number |
| N17-04 | High | Confirmed oracle defect | Preauthorized completion is asserted as an invalid transition |
| N17-05 | High | Rendered-data conformance defect | Network TD carries values outside ATL105 element formats |
| N17-06 | High | Confirmed composition defect | Other card programs' segments and random optional-segment content |
| N17-07 | High | Confirmed oracle defect | Expected responses contradict ATL105 response codes and layouts |
| N17-08 | High | Context-composition defect | Acceptance-context and CVM rules are not applied |
| N17-09 | High | Readiness defect | Ready status and README build gates are not supported by the TD |
| N17-10 | High | Rendered-data conformance defect | Data element and host operations packages keep the Round-4 field defects |
| N17-11 | Medium | Execution dependency blocker | Split-tender step 2 has literal dependent amount data |
| N17-12 | Medium | Input-contract and metadata defect | Client-input placeholders and statuses do not follow the contract |
| N17-13 | Medium | Coverage gap | Six supported ATL105 behaviours have no TC |
| N17-14 | Medium | Traceability defect | spec_basis, migration map, names and matrix do not trace correctly |

Counts can overlap. For example, a TC can send a foreign segment (N17-06) and carry
a wrong oracle (N17-07). Do not add up issue counts to get an invalid-TC total. The
package table above gives the de-duplicated figures.

## N17-01 - Positive TCs do not send the condition they claim to test

**Priority:** High  
**Classification:** Confirmed composition defect  
**Scope:** 447 network TCs, 38 of them `ready`.

| Pattern | TCs | ready | Example |
|---|---:|---:|---|
| "… with <App. I table name> → approved" (objective: "sending Table NNN"). The table is in neither the TD nor the inline request. | 421 across 47 tables | 17 | TC-040080 (Table 048, MasterCard Authentication Data) |
| Decision-table condition not on the wire: "with track = 1" on chip, keyed, fallback, COF and mobile entry (6); "with Tax Type 1 = GST" without Seg 143/102 (8) | 14 | 12 | TC-040132, TC-040116 |
| "(AVS or NVS or Both Information returned) → approved" with no AVS data in the request | 12 | 9 | TC-040140, TC-040030 |

**Reproduction**

1. In `test_cases/test_cases.jsonl`, locate TC-040080 (`ready`). The name is
   "EMV Contact Chip Authorization Only with MasterCard Authentication Data →
   approved". The objective says the transaction sends Table 048.
2. Open `test_data/TD-TC-040080.json`. The Variable Information Data Segment
   carries only Tables 005, 031 and 030.
3. `rule_checks` do not test Table 048, and `awaiting_inputs` is empty. `open_items`
   still asks for the table's client data.

**Actual:** The TC executes a plain Authorization Only. A PASS would be recorded
against a table that was never sent.

**Expected:** The named table or condition is sent, either as a value or as an
`<AWAITING:…>` placeholder with status `needs_client_input`, or the TC is withdrawn.
Do not generate tables that belong to other programs for MasterCard credit.

**Specification basis**

- 10.1.3 (p.86): "The device sends a transaction request to BUYPASS containing the
  AVS information in the Variable Information Data Segment". AVS can only be
  returned if it is requested.
- p.285: GST is a Canadian Tax Type of the Tax by Product segment (Seg 143).
- App. I gives each table's purpose, e.g. Table 048 (p.586).

**Impact:** 38 ready TCs would report coverage for tables and conditions that are
never exercised.

**Acceptance criteria**

- Every positive TD carries the table, segment or parameter named in its TC.
- All 447 TCs are regenerated or withdrawn.
- A generator gate fails when the objective's element is absent from the TD.
- Note: 50 alternate-path TCs (`requires_review` / `pending_sme`) show the same
  pattern. Their target is not sent: Table 032 sub-tables 13 and 01, Seg 116,
  Table 049 sub-table 23 and Seg 130. They are a review item and are not counted.

## N17-02 - Negative TCs contain no trigger for the decline they assert

**Priority:** High  
**Classification:** Confirmed composition defect  
**Scope:** 159 network negative TCs, 86 of them `ready`.

| Pattern | TCs | ready | Example |
|---|---:|---:|---|
| "(card issuer declines) → declined": the request uses an ordinary card with a valid expiry. 58 are identical to an approved positive request. | 103 | 49 | TC-039837 = TC-039814 |
| "with <field> sent where it is not allowed → declined": identical to an approved positive request | 44 | 28 | TC-040107 = TC-040111 |
| N-C7 "<segment> omitted where requires it → declined" for segments that are optional or not applicable | 11 | 8 | TC-038607 |
| N-C1 "required field omitted", but the field is present | 1 | 1 | TC-038592 |

**Reproduction**

1. Locate TC-039837 (`ready`, expects Response Code `1`) and TC-039814 (`ready`,
   expects `0`).
2. Compare `TD-TC-039837.json` with `TD-TC-039814.json`. They are identical except
   for Sequence Number, segment lengths and chip cryptogram data. The PAN, expiry,
   amounts and tables are the same.
3. Repeat with TC-040107 ("Encrypted PIN Block Data sent where it is not allowed",
   expects `1`) and TC-040111 ("Encrypted PIN Block Data = PIN → approved", expects
   `2`).

**Actual:** 102 negatives send the same request as an approved positive, so each
pair of ready TCs contradicts itself. The other issuer-decline TCs also use an
ordinary card. 27 of them list "Expired EMV test card" as an open item but send a
valid expiry.

**Expected:** Each negative differs from a valid request by exactly one wire-level
violation. A decline that depends on the card needs a client-supplied decline card
(an `<AWAITING:AccountNumber>` placeholder and `needs_client_input`). A
"not allowed" claim needs a rule that forbids the field. For example, the
Information Byte is a field of every Seg 100 request (12.1, p.223, field 3).

**Impact:** 86 ready negatives would pass or fail for reasons unrelated to their
objective.

**Acceptance criteria**

- No negative TD equals an approved positive TD, ignoring Sequence Number.
- Each negative's single violation is visible in its TD.
- Issuer-decline TCs stay `needs_client_input` until a decline card is supplied.
- A regression check fails when two TDs that expect opposite results are identical.

## N17-03 - Original Authorization Only requests carry an Approval Number

**Priority:** High  
**Classification:** Confirmed composition defect  
**Scope:** 8 TCs, 7 of them `ready`: TC-040126, TC-040240, TC-040356, TC-040557,
TC-040668, TC-040786, TC-040903 and TC-041019.

**Reproduction:** Open `TD-TC-040126.json`. Seg 100 has Transaction Type `3` (an
original Authorization Only) at a general-retail merchant, and sends:

- Approval Number `J38FQA`
- Local Date and Time `0615251430`
- Encrypted PIN Block Data `16`
- Pump/Lane `1`, Fuel Purchase Amount `1`, Tax Amount `1` and Cash Amount `1`

**Actual:** An original authorization quotes an approval that BUYPASS never issued.

**Expected:** An original Authorization Only carries no Approval Number and no Local
Date and Time. Its amounts suit the merchant type, and it carries no cash on
MasterCard credit.

**Specification basis**

- Element 5 (p.368) states that the Approval Number is "Required on reversals for
  matching information to the original purchase. If it is included in a Purchase
  Request, then the Purchase Request is considered preauthorized, and BUYPASS does
  not go out for authorization."
- p.160: "First Data does not support cashback for Visa or MasterCard credit".
- E33 (p.382): the PIN block is a 16-byte block, not the literal `16`.

**Impact:** The request turns a new authorization into a preauthorized-style message.
The TC therefore cannot show that a fresh authorization was obtained.

**Acceptance criteria:** No original request quotes an Approval Number, and a
regression fails if one is reintroduced.

## N17-04 - Preauthorized completion is asserted as an invalid transition

**Priority:** High  
**Classification:** Confirmed oracle defect  
**Scope:** 12 TCs (10 `needs_client_input`, 2 `pending_sme`). The package has
**no positive** Authorization Only → completion flow.

- Petroleum attended (TT 3 → 0): TC-041343, TC-041374, TC-041405, TC-041447,
  TC-041476, TC-041504, TC-041532.
- Fuel machine (TT 5 → 4): TC-041634, TC-041666, TC-041698, TC-041742, TC-041771.

**Reproduction**

1. Open TC-041634: "… Authorization Only, then Purchase/Capture (not allowed after
   this original) → declined (to confirm)". The expected Response Code is `1`.
2. Step 1 is TT `5`. Step 2 is TT `4` with
   `<ApprovalNumber from step 1 response>` and the same Sequence Number.

**Actual:** The standard pay-at-the-pump completion is expected to be declined.

**Expected:** The completion is valid and should be approved. Add positive
completion flows for POS (3 → 0) and CAT (5 → 4).

**Specification basis**

- App. G (p.545): TT `0` is "Point-of-sale purchase/preauthorized completion" and
  TT `4` is "Customer-activated purchase completion".
- E86 (p.409): the Sequence Number in the Purchase/Capture "is the same Sequence
  Number as in the transaction's Authorization Only request".
- E5 (p.368): a Purchase Request carrying an Approval Number is preauthorized.

**Impact:** The core fuel flow is tested only with the opposite oracle.

**Acceptance criteria:** Positive completion flows are asserted as approved for POS
and CAT. The 12 TCs are reworked, or withdrawn with a source-backed reason.

## N17-05 - Network TD carries values outside ATL105 element formats

**Priority:** High  
**Classification:** Confirmed rendered-data conformance defect  
**Scope:** 283 network TCs, 55 of them `ready`.

| Defect | TCs | ready | Example | ATL105 rule |
|---|---:|---:|---|---|
| Nonfuel Amount `"0"` on every fuel-machine leg (PETROLEUM-UNATTENDED, MCC_5542), 231 legs | 208 | 0 (146 nci, 34 sme, 28 rr) | TD-TC-041569: Fuel 1500, Nonfuel `0`, Pump `01` | E58 (p.393): valid values `1–99999999`. 12.1 (p.223): "When a field is not populated, still send the Field Separator." |
| Encrypted PIN Block Data is the literal `PIN` or `16` (28 positive, 20 negative) | 48 | 21 | TC-040111, TC-040126 | E33 (p.382): a 16-byte PIN block (plus a KSN for DUKPT) |
| Chip or track read entry mode, but E12 carries only an MMYY expiry | 37 | 34 | TC-038593: entry `05`, E12 `0529` | E12 (p.371): for a track read, E12 is "all data read from the card" after the `=` |
| A PIN block is sent while 9F34 (CVM Results) says signature (`5E0302`) | 8 | 4 | TC-040111 | The request claims two different cardholder verification methods |
| N-C/BD1 carriers with a second, unintended violation, or an invalid value on a positive | 6 | 6 | TC-038603 (Seg 130 length `0054`, computed 201); TC-038635 – TC-038637 (Seg 130 on a non-chip entry mode); TC-038634 (Information Byte `A`) | The tested element is not the only error |

**Reproduction:** Open `TD-TC-041569.json` (CAT Purchase → approved) and read Seg 100
Nonfuel Amount. Then open `TD-TC-038593.json` and compare Table 005 (`051`) with
E12. The value does not depend on client input. It will still be wrong when these
TCs become executable.

**Expected:** Leave Nonfuel Amount empty for fuel-only sales. Send a real PIN block
or `<AWAITING:EncryptedPINBlockData>`. Send track data in E12 for read entry modes.
Align 9F34 with the PIN. Each N-C/BD1 carrier carries only its named violation.

**Acceptance criteria:** An independent replay finds no E58, E33, E12 or E44 format
violation and no conflict between the PIN and 9F34. Segment lengths are recomputed.

## N17-06 - Other card programs' segments and random optional-segment content

**Priority:** High  
**Classification:** Confirmed composition defect  
**Scope:** 69 TCs (67 network, 2 data element), 52 of them `ready`.

| Pattern | TCs | ready | Example |
|---|---:|---:|---|
| Segments of other card programs in MasterCard credit requests: Loyalty Seg 108 (27), Fleet Seg 101 (14), Enhanced Fleet Seg 145 (9), ECA/TeleCheck Seg 113 (1) | 51 | 35 | TC-040114 (Seg 108); TC-041338 (Seg 101 with odometer and vehicle number); TC-038601 (Seg 113 in a Balance Inquiry) |
| Random values in optional segments | 54 | 49 | TC-040127: Seg 104 with random tax, freight and duty and Ship-to Country Code `407`.<br>TC-040114: Payment Tender Type `AX` and Coupon Amount `91577796` on a MasterCard sale. |

**Specification basis**

- 12.2 (p.226): "The Fleet Data Segment is included in all fleet card transaction
  requests."
- 11.1.1 (p.166): Loyalty Card and ECA/TeleCheck transactions have their own message
  formats (sections 11.2 and 11.3).
- E148 (p.450): the tender type for MasterCard is `MC`.

**Impact:** The requests are not valid MasterCard credit messages, so the asserted
approvals are not supported. 35 of the 51 TCs are ready.

**Acceptance criteria:** No MasterCard credit request carries a fleet, loyalty or
ECA/TeleCheck segment. Optional segments carry valid, coherent values or are omitted.

## N17-07 - Expected responses contradict ATL105 response codes and layouts

**Priority:** High  
**Classification:** Confirmed oracle defect  
**Scope:** 126 network TCs, 60 of them `ready`.

| Defect | TCs | ready | Example | ATL105 rule |
|---|---:|---:|---|---|
| AVS requested, but the approval is asserted as `2` / `0` instead of `3` / `4` | 20 | 9 | TC-040118 (sends Table 003 as a placeholder, expects `2`) | E83 (p.404): `3` "Approved—Authorization only with AVS", `4` "Approved—Purchase/Capture with AVS" |
| First-card partial approval asserted as `2` (FLAG, "partially approved") | 11 | 0 | TC-040134 step 1 | E83 (p.404): `F` "Approved—Partial approval" |
| Download-only segments expected in an Authorization Only or Purchase response: DL1 (34), DL6 (20), DL2 (8), DL3 (8), DL4 (8) | 78 | 46 | TC-039833 (Merchant Data Segment); TC-040157 (Software Dial Load) | 12.42–12.47 (p.328–335). p.335: "The Store and Forward Data Segment is only sent in a Table Load Response". p.333: DL4 "is used to download an application". |
| BD1 TCs named "→ declined" whose objective says "is accepted". TC-038618 expects `2`; TC-038622 and TC-038632 expect `0`; TC-038601 and TC-038634 assert nothing. | 5 | 5 | TC-038618 | Name, objective and oracle conflict |
| "Cancellation (not allowed here) → declined": a standalone Cancellation with no original transaction | 12 | 0 | TC-041333 | p.83 lists Cancellation as supported. The same package has 31 positive "… then Cancellation" flows. |

**Impact:** A correct host response would be recorded as a failure, or an incorrect
one as a pass.

**Acceptance criteria:** Every asserted code and response segment can be traced to E83
and the financial response layout. A regression rejects DL segments in financial
responses.

## N17-08 - Acceptance-context and CVM rules are not applied

**Priority:** High  
**Classification:** Confirmed context-composition defect  
**Scope:** 83 network TCs, 33 of them `ready`.

| Defect | TCs | ready | Example | Basis |
|---|---:|---:|---|---|
| MCC 6051 / 6211 TCs send Table 007 but no Table 032 sub-table 13 (28 legs) | 20 | 0 (18 nci, 2 sme) | TC-041789 (6051), TC-041793 (6211) | App. I (p.578): "Visa and MasterCard are mandating this attribute in all the related transactions. Only MCC '6051'…" and "'H' High risk security indicator … MasterCard is mandating this attribute … Only MCC '6211'". |
| Petroleum-attended N-C/BD1 carriers use the general-retail terminal (`JX…`) instead of `KC…`; 11 of the 13 omit Seg 102 | 13 | 13 | TC-038616 | All 358 other Petroleum-attended legs use `KC…` with Seg 102 (12.3, p.229) |
| "… offline PIN → approved" on magnetic stripe, contactless magstripe, keyed, fallback and COF (34 named, 5 "cvm = offline_pin") | 39 | 20 | TC-039881, TC-040350 | p.162: offline PIN "is exclusive to EMV card transactions" |
| "… on a CAT terminal with online/offline pin" filed under GENERAL or CNP-0100 ("attended POS"), but the request sends an unattended Table 030 `1000000001` | 16 | 0 | TC-039831 | The context and the request disagree |

**Impact:** The asserted approvals are not supported for the context tested. These
findings contradict the README statement "context alignment mismatches 0".

**Acceptance criteria:** Context and CVM checks pass for every TC. MCC 6051 / 6211
requests carry `13001Y` / `13001H`.

## N17-09 - Ready status and README build gates are not supported by the TD

**Priority:** High  
**Classification:** Readiness defect  
**Scope:** All three packages.

| Package | Ready | Ready with ≥1 confirmed defect | Ready without a confirmed defect |
|---|---:|---:|---:|
| network | 619 | 271 | 348 (77 of them FLAG-only) |
| data_element_validation | 790 | 390 | 400 |
| terminal_and_host_operations | 20 | 3 | 17 |

**Evidence**

- 14 ready TCs send `PIN` or `16` as the PIN block (N17-05).
- 115 ready network TCs (and 47 data element TCs) carry a FLAG-tier expected
  response. This means the producer has not confirmed the expected code.
- The README states: "validation findings 0 (all BRs); test case audit 0 high (all
  BRs); name readability findings 0; context alignment mismatches 0; package
  contract holds (0 unresolved references); full test suite green." N17-01 to
  N17-08, N17-12 and N17-14 contradict these statements.

**Expected:** `ready` means executable with a deterministic oracle. Readiness is gated
on independent TC/TD consistency, element-format and oracle checks.

**Acceptance criteria**

- No ready TC carries a confirmed defect.
- Every ready TC asserts its response code.
- The README gate figures can be reproduced by an independent replay.
- If `ready` means "design-complete", separate it from "execution-ready" and
  document both meanings.

## N17-10 - Data element and host operations packages keep the Round-4 field defects

**Priority:** High  
**Classification:** Confirmed rendered-data conformance defect  
**Scope:** 620 TCs, 393 of them `ready`. Apart from Table 030, these TDs are unchanged
since v10.

| Defect | Data element TCs (all ready unless noted) | Operations TCs | ATL105 rule |
|---|---:|---:|---|
| Chip/track read with an MMYY expiry in E12 | 435 (218 ready) | – | E12 (p.371) |
| Table, Phone, Date and Time and Software Load Request: TID is not 13 bytes | 54 | 6 (3 ready), e.g. TC-039807 | E102 (p.417) |
| CA Public Key File Load Request: TID / Device Type is not 13 bytes with `+*`; Hardware Version is longer than 4; Seg 132 is longer than 77 | 49 / 49 / 46 | TC-039804, TC-039805 | Seg 132 (p.274–275) |
| TransArmor Key Load Request: Device Type is not `++` | 40 | TC-039802, TC-039803 | E102 (p.417) |
| Other field defects in positive boundary/profile and negative carriers: Seg 130 length 19, Seg 130 on a non-chip entry mode 15, amounts 13 (e.g. TC-038767 Fuel `00000000`, TC-038770 8-byte amount) | 81 (25 of them have no defect in the rows above) | – | Element formats |
| `REJECTED_FINAL` TCs that still ship an executable TD | 21 (`blocked_verification`) | – | Verification status |

**Acceptance criteria:** Both packages are regenerated. An independent replay finds
no field-format violation, positive boundary TCs carry valid values, and blocked TCs
ship no executable TD.

## N17-11 - Split-tender step 2 has literal dependent amount data

**Priority:** Medium  
**Classification:** Execution dependency blocker  
**Scope:** 11 split-tender TCs (10 `needs_client_input`, 1 `pending_sme`).

- 5 EMV step-2 legs send `9F02 = 000000000000`: TC-040134, TC-040248, TC-041234,
  TC-041341 and TC-041632.
- 11 step-2 legs declare a Seg 100 length computed for a one-digit amount (`086`,
  `075` or `089`), but the amount is a runtime token. Seg 102 length `022` is also
  precomputed on TC-041341 and TC-041632.
- TC-041632 (fuel machine) builds the remainder and the Seg 102 product amount from
  step 1's Nonfuel Amount (`0`) instead of the Fuel Purchase Amount (12.3, p.229).

**Qualification:** The second card is correctly distinct. All 11 step-2 legs use a
different PAN from step 1 and carry no Approval Number, so the 30-Oct
inherited-approval defect (AI-001 / AI-002) does not recur. The open problem is the
amount binding.

**Acceptance criteria:** 9F02, the Seg 100 / 102 lengths and the petroleum remainder
are bound to the runtime remainder, or the contract states that the harness
recomputes them. Step 2 is shown to serialize consistently for several
partial-approval amounts.

## N17-12 - Client-input placeholders and statuses do not follow the contract

**Priority:** Medium  
**Classification:** Execution-input contract and metadata defect.

- **16 online-PIN TCs send an empty PIN block.** DELIVERY_CONTRACT requires
  `<AWAITING:…>` and "never an empty field". These are:
  - 8 "on a CAT terminal with online pin" TCs: TC-039831, TC-039855, TC-039880,
    TC-039905, TC-039929, TC-039953, TC-039978 and TC-040002
  - TC-040121 and TC-040235 (9F34 = `420300`, online PIN)
  - 6 "cvm = online_pin" decision tables, e.g. TC-040351
- **310 TCs are `needs_client_input`, but nothing is awaited.** Neither the TD nor
  `awaiting_inputs` contains anything to supply: 284 network TCs (e.g. TC-039779)
  and 26 operations TCs.
- **79 TD files (63 network, 16 data element) contain `<AWAITING:…>` but no
  `meta.unavailableFields` block**, e.g. TC-038631. The contract requires awaited
  fields to be listed there with dataSource `TODO_CLIENT_VALUE`.
- **9 `pending_sme` TDs carry client placeholders**, e.g. TC-039991.

**Acceptance criteria**

- Every awaited value is a placeholder listed in the metadata.
- No `needs_client_input` TC lacks an awaited field.
- No required field is sent empty.

## N17-13 - Six supported ATL105 behaviours have no TC

**Priority:** Medium  
**Classification:** Coverage gap.

| Behaviour | Delivered | ATL105 basis |
|---|---|---|
| Authorization Only Reversal (TT 3 → 8, 5 → 8) | 0 TCs | p.83 lists it as supported. E5 (p.368) and E86 (p.409) define its matching data. |
| Chip fallback to swipe (entry mode `80`) | 0 legs. All 238 fallback legs use `79` (fallback to manual). | §10.14.4 (p.162). App. J (p.638): `80` is "Chip card capable – unaltered track data read". |
| Positive preauthorized completion (3 → 0, 5 → 4) | 0 (see N17-04) | App. G (p.545); E86 (p.409) |
| Expired card | 0 | E12 (p.372) requires a value "even for expired cards" |
| Contactless-magnetic-stripe-only terminal | 0 legs with Table 031 `07` | App. I (p.569); App. J (p.639) |
| Entry modes `95` and `86` | 0 legs | App. J (p.638) |

**Acceptance criteria:** Each behaviour has a positive TC, plus negatives where
ATL105 defines them, or a documented BA/SME exclusion.

## N17-14 - spec_basis, migration map, names and matrix do not trace correctly

**Priority:** Medium  
**Classification:** Traceability defect.

- **25 N-C/BD1 TCs (21 ready) cite pages unrelated to the element under test.**
  Examples:
  - TC-038601, Merchant Trace ID ← p.503 "TPDU Protocol ID …"
  - TC-038607, Loyalty Card Data Segment ← TransArmor p.15 / p.19 (expiry) and PIPS p.73
- **287 non-petroleum TCs (139 ready) cite PIPS**, the petroleum specification, in
  `spec_basis`.
- **12 `id_migration.csv` rows marked "kept" map to a different test.** Results
  carried over would attach to the wrong test. Examples:
  - TC-024316 (EMV Contactless Merchandise Return) → TC-038593 (EMV Contact
    Authorization Only)
  - TC-024327 (Manual/Keyed Authorization Only) → TC-038606 (Chip Fallback
    Merchandise Return)
- **763 names are broken by the mechanical removal of numbers.** 13 network names now
  read "… omitted where requires it → declined". 750 data element names have a
  dangling "– ", e.g. TC-038738.
- **`reports/traceability_matrix.md` lists 1,990 of the 2,422 network leg files.**
  The follow-on legs are missing. The full JSON matrix lists all of them.

**Acceptance criteria**

- Spec references are verified per TC.
- "kept" is used only when transaction, entry mode and element are unchanged.
- Names are regenerated.
- Both matrices list all 2,422 legs.

## Review items (not counted above)

- **Resubmission limit (24 TCs, e.g. TC-041346):** ATL105 p.61 says a transaction
  "may be resubmitted up to three more times", but the TCs use the PIPS p.17 limit
  of three sends. No source states that the host declines a further send.
- **mPOS alternate path at the unattended fuel machine (12 TCs, e.g. TC-041620):**
  App. I p.570 says "Mobile POS are always attended devices".
- **Offline PIN on EMV contactless (8 TCs) and contactless mobile (8 TCs):** SME to
  confirm.
- **The same not-sent pattern on 50 alternate-path TCs:** see N17-01.
- **Other items:**
  - TVR `8000048000` (online PIN entered) on 600 EMV legs that carry no PIN
  - Seg 105 declared length one byte shorter than computed
  - Random version and key strings in host messages and Totals legs (430 legs)
  - Totals after non-capture transactions

## Regression context

Prior items still open in this delivery, mapped to the new IDs:

| N17 | Round 4 IDs | Round 5 defect report | Round 5 validation report |
|---|---|---|---|
| N17-01 | CD-18, R4-15 | R5-02, R5-09 | D-10, D-11, D-12 |
| N17-02 | R4-12 | R5-05 | D-14 |
| N17-03 | R4-04 | – | D-04 |
| N17-04 | – | R5-04 | – |
| N17-05 | R4-03, R4-07 (network) | R5-01 | D-01, D-02, D-05 |
| N17-06 | R4-01, R4-02 | – | D-08, D-09 |
| N17-07 | R4-14 | R5-08, R5-10 | D-16 – D-19 |
| N17-08 | R4-11 | R5-03, R5-07, R5-16 | D-13, D-15 |
| N17-10 | R4-05 – R4-09 | – | D-24 – D-27 |
| N17-11 | CD-07, CD-08 | R5-06 | D-06, D-07 |
| N17-12 | CD-13, R4-10 | R5-14 | D-03, D-29 |
| N17-13 | CD-12, CD-17 | – | D-30 – D-34 |
| N17-14 | – | R5-11 – R5-13 | D-20, D-37 |

The counts in this register were re-derived from the delivered files. Where they
differ from either Round-5 report, this register takes precedence:

- N17-02 counts all 103 issuer-decline negatives.
- N17-08 includes the 5 "cvm = offline_pin" decision tables.
- N17-12 includes the "cvm = online_pin" cases.
- N17-07 includes the 8 DL4 checks.

## Review boundaries and acceptance handoff

**Method**

- This was an offline structural and semantic audit of all 3,205 TCs and 3,479 TD
  files, checked against ATL105 2026-4.
- The Java `TestDataSpecFieldValidationRunner` was run on a 42-TC sample: 33 FAIL and
  9 PASS_WITH_REVIEW. 30 of the FAILs include the Appendix T framing rule (review).
- Each rule cited here was checked against the specification text.
- No live host test or cryptographic validation was performed.
- No card numbers are reproduced in this report.

**Independent TC disposition:** 1,667 TCs FAIL (at least one confirmed defect).
1,538 TCs are REVIEW_REQUIRED (no confirmed defect, not certified).

For each issue, please return:

1. An accepted or disputed disposition, with a source-backed rationale.
2. The generator or root-cause correction, and the list of affected artefacts.
3. The regenerated outputs, plus reproducible regression evidence.
4. The remaining client, BA and SME inputs, and their execution impact.

Do not close issues only by renaming a TC, editing a label, suppressing a flag, or
making the same producer-authored checks pass again.

Companion files:

- Tracker: [AI-TEAM-ISSUE-REGISTER.csv](AI-TEAM-ISSUE-REGISTER.csv)
- Affected TCs per issue and finding: [AI-TEAM-AFFECTED-TEST-CASES.csv](AI-TEAM-AFFECTED-TEST-CASES.csv)
