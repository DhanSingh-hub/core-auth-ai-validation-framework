# Test Team Action 5: Product, Network, and Program Applicability Review

**Status:** Applicability review; workbook matrix is a proposal, not an approved scope  
**Source baseline inspected:** ATL105 2026-3 knowledge artifacts; workbook release not identified  
**Input:** BA workbook `BR & TS Scope updates.xlsx` (SHA-256 recorded in [the workbook review](./REPORT.md))  
**Related reviews:** [Action 1 scenario/source review](./TEST-TEAM-ACTION-1-SCENARIO-SOURCE-REVIEW.md), [Action 2 traceability/applicability review](./TEST-TEAM-ACTION-2-TRACEABILITY-APPLICABILITY.md)  
**Boundary:** No workbook or BRs were changed. Nothing below establishes a supported/unsupported transaction policy, approved product scope, denominator, or coverage percentage.

## 1. Executive outcome

The product applicability matrix is useful as a draft scope proposal, but it
combines several independent dimensions and does not provide a source-backed
per-case applicability rule. It should not be read as proof that every listed
transaction scenario applies to every product in a BR range.

The key risk is dimensional conflation:

- **Network/brand** (for example Visa or Mastercard);
- **Funding/card type** (for example credit, debit, fleet, EBT);
- **Card program/product** (for example Healthcare, Purchasing, Corporate,
  prepaid, or a fleet program);
- **Transaction flow** (purchase, authorization, completion, return, inquiry,
  stored credential);
- **Channel/entry mode/device capability** (card-present, keyed, contactless,
  EMV, fallback, e-commerce);
- **Merchant/processor configuration and enabled features**.

ATL105 materials explicitly treat these as separate or conditionally related
dimensions. An applicability relation should be made only when the governing
source or an approved product/network rule joins them.

## 2. What the workbook asserts

The `Product Applicability matrix` contains 12 product rows:

| Workbook product label | Listed BRs |
|---|---|
| Mastercard Credit | BR1–BR10, BR13 |
| Visa Credit | BR1–BR10, BR13 |
| American Express Credit | BR1–BR11, BR13 |
| Discover Credit | BR1–BR10, BR13 |
| Mastercard Debit | BR1–BR10 |
| Visa Debit | BR1–BR10 |
| Interlink Debit | BR1–BR10 |
| Mastercard Fleet | BR1–BR10, BR12 |
| Visa Fleet | BR1–BR10, BR12 |
| Purchasing Card | BR1–BR10, BR13 |
| Corporate Card | BR1–BR10, BR13 |
| Healthcare Card | BR1–BR11 |

Most rows assign the same broad BR ranges, with variations for Amex/Healthcare,
Fleet, and industry. The sheet does not cite a source or explain the range
logic. Its BR labels are not mapped to canonical ATL105 BRs. The case sheets
for BR1–BR4 do not carry network/product/program fields. BR5 does contain
`Network` and `Card_Programs` values on its cases, but its TS91–TS116 identifiers
do not appear in the scope tab's TS1–TS86 set.

The matrix therefore describes **proposed broad applicability**, not
case-level, source-confirmed applicability.

## 3. Source-backed dimension boundaries

### Section 5 “supported payment network types” is a mixed taxonomy

The source-backed [Payment-Network Type Training](../../../docs/specs/kb/segment-100/payment-network-type-training.md)
records 16 Section 5 categories. These include actual payment methods and card
categories, but also check services, benefit programs, fleet/petroleum,
stored-value, and loyalty. Its normalized labels are Test Solution metadata,
not an ATL105 wire field. The catalog explicitly says those labels do not by
themselves establish transaction eligibility, card-code or authorizer mapping,
or required companion segments.

Consequences for the workbook:

- Do not treat Credit, Debit, EBT, Fleet, Healthcare, Purchasing, or Corporate
  as interchangeable network brands.
- “Interlink Debit” may be a meaningful network/program label in the proposed
  client scope, but it is not shown as a distinct Section 5 leaf in the
  inspected source catalog. Preserve it as a separate proposed network/program
  dimension pending authoritative mapping.
- “Mastercard Credit” / “Visa Credit” are not merely the Section 5 `CREDIT`
  leaf; the latter does not name a brand. A product/network relationship needs
  a separate source or approved configuration.

### Appendix E card types are a different dimension

The source-backed [Appendix E Card Type Training](../../../docs/specs/kb/segment-100/appendix-e-card-type-training.md)
distinguishes Table Load codes, 36 financial Prompt Code card types, and special
Prompt Codes. The financial allowlist validates card-code syntax; it does not
prove that every transaction type is supported for that card. The
[Financial Card-Type BR catalog](../../../docs/specs/kb/segment-100/financial-card-type-business-requirements.md)
likewise requires transaction flow and conditional data rules to be assessed
separately.

Consequences:

- Do not infer Appendix E card code from a workbook product label (or vice
  versa). For example, the Appendix E financial Prompt Code catalog calls
  `020` Credit and separately identifies `080` MasterCard Fleet; that does not
  create a complete Mastercard Credit/Fleet product matrix.
- A named fleet card does not automatically mean Segment 101 is required for
  every flow, nor does a product code establish merchant enablement. The source
  requires relevant companion data only under its applicable conditions.
- A recognized code or product label does not prove network/merchant
  permission.

### Product code classification is not product permission

The source-backed [Appendix F Product Code Training](../../../docs/specs/kb/segment-100/appendix-f-product-code-training.md)
states that Appendix F code classification does not itself prove merchant
Dynamic Card Table configuration, transaction eligibility, or product
permission. Segment 102 composition and ordering rules are separately
conditional. The workbook matrix cannot serve as a substitute for those
conditions.

### Brand-specific and program-specific restrictions must stay separate

Existing source training includes examples where a rule is context-specific:

- The partial-approval indicator value `5` requires an **Amex prepaid
  balance-receipt** context in the source-backed Segment 100 model. The workbook
  row says American Express Credit generally and does not separately identify
  prepaid; do not claim this context is included or excluded by that row.
- [Mastercard/Maestro 2026-4 modeling](../../../docs/specs/kb/segment-100/mastercard-maestro-2026-4-model/README.md)
  treats its 2026-4 overlay and Mastercard/Maestro distinctions as explicit,
  draft, and release-gated. “Mastercard Credit” or “Mastercard Debit” in the
  workbook should not be assumed to include Maestro or all Mastercard
  subprograms.
- eWIC has separate source-derived prompt and Segment 103 rules, including a
  no-return condition in the independent Test Solution catalog. EBT/eWIC is
  not explicitly named in the workbook product matrix. Its omission is a scope
  question, not proof it must be included or proof it is unsupported.
- Healthcare, fleet, petroleum, purchasing, and corporate labels may represent
  distinct card programs or business products. They do not automatically
  describe the network, transaction type, channel, or required data segments.

## 4. Applicability concerns by workbook group

| Workbook group | Matrix assertion / issue | Review disposition |
|---|---|---|
| BR1 Purchase Processing | Most listed products inherit BR1; the case tab has no product/network field. | Candidate intent only. Confirm per purchase flow, channel, and product/network configuration. |
| BR2 Authorization / completion | Broad product ranges; completion and cancellation are separate lifecycle flows with original transaction dependencies. | Do not inherit purchase applicability automatically. Verify the initiating authorization type, completion/cancellation type, and network/program rule for each case. |
| BR3 Return / refund | Matrix assigns BR3 broadly to credit, debit, and fleet; detailed tab has no product/network field. | Return/void behavior may be restricted by product/program. Check source and processor policy per operation; do not infer universal return support from the matrix. |
| BR4 Balance inquiry / account verification | Scope sheet names debit, prepaid, healthcare, and “supported credit products”; matrix has no explicit prepaid row and broad credit assignment. | “Supported” is undefined. Require a product/network allowlist or approved capability source per inquiry/verification operation. |
| BR5 Credential-on-file | Product matrix includes many products, but case rows use TS91–TS116 absent from the scope sheet; only BR5 has case-level network/program fields, which are not normalized source crosswalks. | Reconcile TS IDs and establish network-specific stored-credential eligibility/indicators and initial-storage vs subsequent-use behavior. |
| BR6 EMV / entry mode | Matrix broadly assigns all products; no detailed case tab. | EMV, contactless, fallback, and mobile wallet are device/card/channel conditions, not universal product features. Confirm capability and required Segment 130/context data per case. |
| BR7 Settlement / batch | Broad product applicability; no detailed cases. | Verify settlement participation, batch operations, and reversal/void effects per host/processor/product; the matrix alone cannot establish them. |
| BR8 Timeout / recovery | Broad “All” in scope tab; matrix generally includes BR8 through BR1–BR10 ranges. | Recovery type is transaction-specific (e.g., purchase TOR versus authorization cancellation); product/network timeout and retry behavior needs an oracle. |
| BR9 Device/terminal controls | Broad “All”; cases have no detailed tab. | Device settings/features and product applicability are independent; identify supported terminal/card profile before asserting behavior. |
| BR10 Negative validation | Broad “All” in scope tab, including varied invalid fields and contexts. | Negative input applicability depends on field presence, entry mode, program, and transaction flow. Do not apply every invalid-field case to every product. |
| BR11 Healthcare / program | Listed for Amex Credit, Healthcare Card, and Amex/Healthcare ranges in matrix. | Healthcare Card is not itself a network. Confirm the network/program pair and healthcare operation (FSA/HRA etc.) before mapping. |
| BR12 Fleet | Listed for Mastercard Fleet and Visa Fleet plus scope-sheet Fleet. | Confirm each network’s fleet product/program and the source conditions for Segment 101/102. Product label alone does not establish fleet-data presence or fuel-product rules. |
| BR13 Industry-specific processing | Assigned to several credit, purchasing, and corporate rows. | Utilities, government, transit, MOTO, CNP, hospitality, rental, and petroleum are merchant/transaction contexts, not card brands. Confirm supported combinations individually. |

This table identifies review gates; it does not determine that any listed
combination is valid or invalid.

## 5. Matrix omissions and ambiguities to disposition

The following are not automatic defects; each needs an explicit scope decision:

1. **Prepaid:** Mentioned in BR4 scope scenarios but absent as a product-matrix
   row. Determine whether prepaid is a product, program, or funding-type
   attribute and which networks/products it applies to.
2. **EBT/eWIC:** No explicit matrix row, although the repository models distinct
   EBT food, cash, and eWIC categories and specific transaction restrictions.
   Confirm whether these programs are in the client request and define their
   operation-specific applicability.
3. **STAR Signature Debit:** The receipt Card Type ID catalog includes a STAR
   Signature Debit receipt label, but that does not establish that STAR is
   included in the BA product list. Decide whether STAR is in scope and map it
   independently; do not infer it from generic Debit.
4. **Maestro:** The workbook's Mastercard labels do not say whether Maestro is
   included, excluded, or treated as a separate network/card product. Resolve
   against the governing release and the client’s actual product scope.
5. **Other debit networks/programs:** Only Mastercard Debit, Visa Debit, and
   Interlink Debit are named. Establish whether the list is exhaustive, and
   distinguish network from generic debit funding and card type.
6. **Fleet/petroleum:** Mastercard Fleet and Visa Fleet are listed, while
   Voyager and other proprietary/petroleum contexts are not. Confirm if the
   requested fleet scope is network-specific, processor-specific, or
   product-code-specific.
7. **Stored value, gift, check, loyalty, and proprietary cards:** These do not
   appear as matrix rows even though ATL105 source catalogs include relevant
   independent categories. Confirm client applicability rather than silently
   including every ATL105 category.
8. **Channel and device capability:** Contactless, EMV, fallback, manual entry,
   mobile wallet, mail/phone, and CNP are not product rows. State them as
   separate case dimensions where relevant.
9. **Industry rows:** The product matrix maps BR13 to selected card products,
   but the workbook's industry examples include multiple contexts. Confirm
   exactly which product/network/industry combinations are intended.

## 6. Recommended per-case applicability schema

Replace broad ranges and free-text lists with explicit, reviewable dimensions
for each case. Suggested fields:

| Field | Example content / allowed disposition |
|---|---|
| `canonicalBrId` | Existing canonical ATL105 BR ID(s); candidate/open if unadjudicated. |
| `tsId` / `tcId` | Approved, reconciled trace IDs; TC globally unique. |
| `specificationRelease` / `sourceAnchor` | Exact governing version and section/rule; no silent cross-release inheritance. |
| `networkOrRoute` | Named network, or `GENERIC_DEBIT`, `GENERIC_EBT`, processor route, or `NOT_APPLICABLE`; do not use `Credit` as a network. |
| `fundingOrCardType` | Credit/debit/EBT/stored-value/etc.; keep distinct from network brand. |
| `programOrProduct` | Mastercard Fleet, Healthcare FSA/HRA, Purchasing, corporate, prepaid, eWIC, etc., when supported by the source. |
| `transactionTypeAndFlow` | Purchase, auth-only, completion, return, reversal, inquiry, stored-credential usage, etc.; include original/follow-up link for lifecycle. |
| `channelAndEntryMode` | Card-present, CNP/MOTO, EMV/contactless/fallback/keyed, with terminal capabilities as required. |
| `merchantConfiguration` | Required network/product/service enablement or `NOT_ESTABLISHED`; do not infer production permission. |
| `applicabilityDecision` | `IN_SCOPE`, `OUT_OF_SCOPE`, `CONDITIONAL`, or `REVIEW_REQUIRED`, with source/decision authority. |
| `conditionsAndExceptions` | Precise trigger, exclusions, required companion segments and special flow rules. |

An `IN_SCOPE` decision should identify its authority: ATL105 source rule,
approved network/processor rule, or approved client/product-scope decision. If
the source describes a format/code but not product eligibility, record the
eligibility dimension as `REVIEW_REQUIRED`.

## 7. Recommended review procedure

1. Start from the scenario's canonical BR and source release, not the
   product-matrix row.
2. Identify the transaction type/flow and required message family.
3. Resolve funding/card type separately from network and program.
4. Resolve channel, entry mode, device capability, and merchant configuration.
5. Apply only source-backed cross-dimension rules; do not build a Cartesian
   product/network/transaction matrix from independent allowlists.
6. Record negative or excluded combinations only where a source or approved
   product decision explicitly says they are prohibited. Otherwise use
   `REVIEW_REQUIRED`.
7. Link applicability to the individual TC and TD so data fixtures match the
   stated product, network, and flow.

## 8. BA / SME decisions needed

1. Are the 12 product rows exhaustive for the client request, or are they only
   an initial subset?
2. What do `All`, `Credit`, `Debit`, `Fleet`, `Supported Products`, and
   “supported credit products” mean operationally, and who owns those
   definitions?
3. Does “Mastercard” include Maestro? If so, which source/release and processing
   profiles establish that; if not, should Maestro be explicitly excluded?
4. Are STAR, Interlink, other debit networks, EBT food/cash, eWIC, prepaid,
   stored value/gift, check, loyalty, Voyager/proprietary fleet, and purchasing
   card programs in or out of scope?
5. For Healthcare, Purchasing, Corporate, and Fleet, which networks and
   subprograms are intended, and which transaction types are supported?
6. Which channel/device dimensions are required for each scenario (EMV,
   contactless, fallback, keyed, MOTO, CNP, mobile wallet)?
7. Is the BR-to-product range intended to mean every scenario under each BR
   applies to that product, or only that the product is in a broad feature
   family? Provide a per-case exception mechanism.
8. Which authority approves merchant/processor enablement where ATL105 lists a
   format or code but does not state transaction eligibility?

## Action 5 disposition

This action is **complete as an applicability review**. The workbook matrix
should remain marked proposed pending answers and per-case mapping. Do not
declare omitted contexts in or out of scope, and do not claim that a product
label authorizes every listed operation. No spreadsheet, BR, or test artifact
was altered by this review.
