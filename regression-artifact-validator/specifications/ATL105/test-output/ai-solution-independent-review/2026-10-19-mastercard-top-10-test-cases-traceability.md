# Mastercard - prioritized top 10 test cases with BR > TS > TC > TD traceability

Prepared: **2026-10-19** | Specification: **ATL105 2026-3** | Delivery: **October 19 Run3**

> **Review shortlist, not an executable/certified regression suite.** The ten cases below are existing AI-produced records. All have producer TC confidence 0%; eight are marked INCOMPLETE_SPEC_GAP and two DELIBERATE_OMISSION. No application/host tests were executed. Producer verification PASS is not a host result. This document validates declared artifact lineage and inspects the actual JSON fixtures, without changing them.

## 1. Selection method and scope

- Candidate population: **122 unique cases / 18 flow families**, selected by the linked scenario explicitly declaring `flow_axis_values.card_brand = MC`; the TC and TD sidecar brand declarations are also checked.
- Population readiness: 94 INCOMPLETE_SPEC_GAP, 7 INCOMPLETE_UNRESOLVED, 21 DELIBERATE_OMISSION. None of these labels establishes execution readiness.
- "Best" means a business-risk-prioritized, non-redundant review set: financial completion, cancellation, void, duplicate submission, timeout recovery, EMV paths, merchandise-return reversal, and two deliberate negative cases.
- One case per chosen flow family. Entry-mode 01 representatives are used for non-EMV flows; explicit 05 representatives are used for the two EMV flows. These are scenario axes, not independently certified brand/entry-mode combinations.
- 7 of the eight BR candidates referenced by the 122-case Mastercard-tagged population are retained by these ten cases. This is structural BR breadth, not independently confirmed Mastercard-specific obligations. The loyalty Unit of Work BR is omitted because the inspected loyalty fixture lacks the matching field.
- BR statements here are generally cross-brand lifecycle rules, exercised under an explicit Mastercard scenario axis. They are not the same population as the earlier 158 BRs selected by Mastercard wording.
- The ten independent-Test Mastercard BR records were considered as context, but draft/review placeholders do not outrank concrete multi-leg JSON routes for this shortlist. Candidate Test-solution mappings are provided separately below, with no equivalence claimed.
- Excluded: repeated entry-mode variants, generic prose-only brand associations, partial completion with unresolved lesser-amount construction, the inspected loyalty-reversal case TC-002235 with absent Unit of Work, and retry-limit flows whose retry-count obligation is not represented by their linked same-device BR alone.
- Scope: **10 TCs, 10 TSs, 7 BRs, 23 distinct TD JSON payloads and 23 metadata sidecars**. Each primary TD declaration that repeats the first leg is counted once.

## 2. Prioritized shortlist

| Rank | Test case | Scenario | Business flow | Linked BR suffixes | TD legs | Nature |
|---:|---|---|---|---|---:|---|
| 1 | [TC-001779](#case-1) | SC-2087 | Authorization to completion | 1921 | 2 | Lifecycle / recovery |
| 2 | [TC-002005](#case-2) | SC-2313 | Authorization cancellation | 592 | 2 | Lifecycle / recovery |
| 3 | [TC-002104](#case-3) | SC-2412 | Authorization, completion and void | 1661, 1921, 1923 | 3 | Lifecycle / recovery |
| 4 | [TC-001889](#case-4) | SC-2197 | Duplicate completion | 1921 | 3 | Lifecycle / recovery |
| 5 | [TC-002204](#case-5) | SC-2512 | Timeout reversal followed by next transaction | 288, 293 | 3 | Lifecycle / recovery |
| 6 | [TC-002384](#case-6) | SC-2692 | EMV authorization to completion | 1921 | 2 | Lifecycle / recovery |
| 7 | [TC-002469](#case-7) | SC-2777 | EMV timeout reversal | 288, 293 | 2 | Lifecycle / recovery |
| 8 | [TC-001940](#case-8) | SC-2248 | Merchandise return followed by void | 1923 | 2 | Lifecycle / recovery |
| 9 | [TC-002054](#case-9) | SC-2362 | Wrong reversal transaction code - negative | 1923 | 2 | Deliberate negative |
| 10 | [TC-002329](#case-10) | SC-2637 | Duplicate independent Sequence Number - negative | 294 | 2 | Deliberate negative |

BR suffixes in the shortlist use the prefix `REQ-SRC-ATL105-PDF-001:`. The detailed sections retain full IDs.

## 3. BR register and independent-Test context

BR catalog: [requirement_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/Requirement candidates/requirement_candidates.json>); record lookup: `requirements[].id`. AI candidate mapping source: [br-assessment.jsonl](<2026-10-19-181026-run-delivery-wide-assessment-v4/br-assessment.jsonl>). Independent-Test records: [complete-all-test-solution-br-ts-tc-td-package.json](<../test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json>).

| AI BR ID | Source rule / PDF page | Complete business requirement | Selected TCs | Candidate independent-Test rules |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:1661 | BR-361-1 / 361 | Approval Number is required on reversals for matching information to the original purchase. | TC-002104 | SEG100-R-049 |
| REQ-SRC-ATL105-PDF-001:1921 | BR-402-1 / 402 | Sequence Number must persist in all subsequent transactions associated with the initial authorization request. | TC-001779, TC-002104, TC-001889, TC-002384 | SEG100-R-019, SEG100-R-037 |
| REQ-SRC-ATL105-PDF-001:1923 | BR-402-3 / 402 | In purchase reversal/void or time-out reversal, Sequence Number must match original transaction request. | TC-002104, TC-001940, TC-002054 | SEG100-R-019, SEG100-R-037 |
| REQ-SRC-ATL105-PDF-001:288 | BR-38-4 / 38 | A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR. | TC-002204, TC-002469 | No generated candidate |
| REQ-SRC-ATL105-PDF-001:293 | BR-38-9 / 38 | The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. | TC-002204, TC-002469 | SEG100-R-019, SEG100-R-037 |
| REQ-SRC-ATL105-PDF-001:294 | BR-38-10 / 38 | Each financial transaction request forwarded to BUYPASS should be assigned a new Sequence Number. | TC-002329 | SEG100-R-019, SEG100-R-037 |
| REQ-SRC-ATL105-PDF-001:592 | BR-97-7 / 97 | Element No. 86 (Sequence Number) present in original transaction must be present and identical in the Authorization Only Reversal transaction. | TC-002005 | SEG100-R-019, SEG100-R-037 |

Candidate mappings are source-anchor search results only, including ambiguous/composite and unique-anchor proposals. They do not establish semantic equivalence, independent Test coverage, or execution.
- **SEG100-R-049** (BR-RULE-SEG100-R-049): Approval Number is present when the lifecycle requires approval reference Status: DRAFT_REVIEW_REQUIRED.
- **SEG100-R-019** (BR-RULE-SEG100-R-019): Sequence Number is six digits and persists through lifecycle Status: DRAFT_REVIEW_REQUIRED.
- **SEG100-R-037** (BR-RULE-SEG100-R-037): Lifecycle follow-ups reuse the original Sequence Number and required approval context Status: DRAFT_REVIEW_REQUIRED.

<details><summary>Full source BR records (all 7)</summary>

```json
[
  {
    "id": "REQ-SRC-ATL105-PDF-001:1661",
    "statement": "Approval Number is required on reversals for matching information to the original purchase.",
    "source_rule_id": "BR-361-1",
    "source_page": 361,
    "source_document_id": "SRC-ATL105-PDF-001",
    "derivation_method": "business_rule_derived",
    "confidence_score": 0.9,
    "below_confidence_gate": false,
    "requires_review": false,
    "flags": [
      "GROUNDED_FROM_TEXT:approval number->ENT-ELEM-5"
    ],
    "related_entity_ids": [
      "ENT-ELEM-5"
    ],
    "segment_number": "100",
    "segment_inferred_from_text": false,
    "category": "Lifecycle",
    "requirement_type": "Business Rule",
    "condition_element_id": null,
    "condition_value": null,
    "condition_value_parsed": null,
    "condition_trigger": "Transaction is a reversal",
    "expected_behavior": "Approval Number must be present",
    "constraint_text": "Approval Number must be present",
    "evidence_ref": {
      "source_id": "SRC-ATL105-PDF-001",
      "entity_id": "BR-361-1",
      "page": 361
    },
    "scope_name": null,
    "segment_ids": [
      "ENT-SEG-100"
    ],
    "segment_assignment_method": "declared",
    "segment_attribution": "unique",
    "confidence_pct": 90,
    "confidence_band": "HIGH",
    "confidence_factors": [
      "prior_confidence_score=0.9"
    ]
  },
  {
    "id": "REQ-SRC-ATL105-PDF-001:1921",
    "statement": "Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
    "source_rule_id": "BR-402-1",
    "source_page": 402,
    "source_document_id": "SRC-ATL105-PDF-001",
    "derivation_method": "business_rule_derived",
    "confidence_score": 0.85,
    "below_confidence_gate": false,
    "requires_review": false,
    "flags": [],
    "related_entity_ids": [
      "ENT-ELEM-86"
    ],
    "segment_number": "100",
    "segment_inferred_from_text": false,
    "category": "Lifecycle",
    "requirement_type": "Business Rule",
    "condition_element_id": null,
    "condition_value": null,
    "condition_value_parsed": null,
    "condition_trigger": null,
    "expected_behavior": "Sequence Number must remain same across associated transactions",
    "constraint_text": "Sequence Number must remain same across associated transactions",
    "evidence_ref": {
      "source_id": "SRC-ATL105-PDF-001",
      "entity_id": "BR-402-1",
      "page": 402
    },
    "scope_name": null,
    "segment_ids": [
      "ENT-SEG-100"
    ],
    "segment_assignment_method": "declared",
    "segment_attribution": "unique",
    "confidence_pct": 85,
    "confidence_band": "HIGH",
    "confidence_factors": [
      "prior_confidence_score=0.85"
    ]
  },
  {
    "id": "REQ-SRC-ATL105-PDF-001:1923",
    "statement": "In purchase reversal/void or time-out reversal, Sequence Number must match original transaction request.",
    "source_rule_id": "BR-402-3",
    "source_page": 402,
    "source_document_id": "SRC-ATL105-PDF-001",
    "derivation_method": "business_rule_derived",
    "confidence_score": 0.85,
    "below_confidence_gate": false,
    "requires_review": false,
    "flags": [],
    "related_entity_ids": [
      "ENT-ELEM-86"
    ],
    "segment_number": "100",
    "segment_inferred_from_text": false,
    "category": "Lifecycle",
    "requirement_type": "Business Rule",
    "condition_element_id": null,
    "condition_value": null,
    "condition_value_parsed": null,
    "condition_trigger": "Purchase reversal/void or time-out reversal transaction",
    "expected_behavior": "Sequence Number must equal original transaction's value",
    "constraint_text": "Sequence Number must equal original transaction's value",
    "evidence_ref": {
      "source_id": "SRC-ATL105-PDF-001",
      "entity_id": "BR-402-3",
      "page": 402
    },
    "scope_name": null,
    "segment_ids": [
      "ENT-SEG-100"
    ],
    "segment_assignment_method": "declared",
    "segment_attribution": "unique",
    "confidence_pct": 85,
    "confidence_band": "HIGH",
    "confidence_factors": [
      "prior_confidence_score=0.85"
    ]
  },
  {
    "id": "REQ-SRC-ATL105-PDF-001:288",
    "statement": "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.",
    "source_rule_id": "BR-38-4",
    "source_page": 38,
    "source_document_id": "SRC-ATL105-PDF-001",
    "derivation_method": "business_rule_derived",
    "confidence_score": 0.9,
    "below_confidence_gate": false,
    "requires_review": true,
    "flags": [
      "LLM_PROPOSED_SEGMENT_ATTRIBUTION: segment named by LLM Segment Attribution pass, validated as a real KB segment genuinely containing this requirement's own referenced element before acceptance"
    ],
    "related_entity_ids": [],
    "segment_number": null,
    "segment_inferred_from_text": false,
    "category": "Lifecycle",
    "requirement_type": "Business Rule",
    "condition_element_id": null,
    "condition_value": null,
    "condition_value_parsed": null,
    "condition_trigger": "Time-out occurs for a device",
    "expected_behavior": "TOR must originate from same device as original request",
    "constraint_text": "TOR must originate from same device as original request",
    "evidence_ref": {
      "source_id": "SRC-ATL105-PDF-001",
      "entity_id": "BR-38-4",
      "page": 38
    },
    "scope_name": null,
    "segment_ids": [
      "ENT-SEG-DL1"
    ],
    "segment_assignment_method": "llm_proposed",
    "segment_attribution": "unique",
    "confidence_pct": 87,
    "confidence_band": "HIGH",
    "confidence_factors": [
      "prior_confidence_score=0.9",
      "minor_flags=-3"
    ]
  },
  {
    "id": "REQ-SRC-ATL105-PDF-001:293",
    "statement": "The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed.",
    "source_rule_id": "BR-38-9",
    "source_page": 38,
    "source_document_id": "SRC-ATL105-PDF-001",
    "derivation_method": "business_rule_derived",
    "confidence_score": 0.9,
    "below_confidence_gate": false,
    "requires_review": false,
    "flags": [],
    "related_entity_ids": [
      "ENT-ELEM-86"
    ],
    "segment_number": "100",
    "segment_inferred_from_text": false,
    "category": "Lifecycle",
    "requirement_type": "Business Rule",
    "condition_element_id": null,
    "condition_value": null,
    "condition_value_parsed": null,
    "condition_trigger": "TOR is sent",
    "expected_behavior": "Sequence Number must match original transaction's Sequence Number",
    "constraint_text": "Sequence Number must match original transaction's Sequence Number",
    "evidence_ref": {
      "source_id": "SRC-ATL105-PDF-001",
      "entity_id": "BR-38-9",
      "page": 38
    },
    "scope_name": null,
    "segment_ids": [
      "ENT-SEG-100"
    ],
    "segment_assignment_method": "declared",
    "segment_attribution": "unique",
    "confidence_pct": 90,
    "confidence_band": "HIGH",
    "confidence_factors": [
      "prior_confidence_score=0.9"
    ]
  },
  {
    "id": "REQ-SRC-ATL105-PDF-001:294",
    "statement": "Each financial transaction request forwarded to BUYPASS should be assigned a new Sequence Number.",
    "source_rule_id": "BR-38-10",
    "source_page": 38,
    "source_document_id": "SRC-ATL105-PDF-001",
    "derivation_method": "business_rule_derived",
    "confidence_score": 0.9,
    "below_confidence_gate": false,
    "requires_review": false,
    "flags": [],
    "related_entity_ids": [
      "ENT-ELEM-86"
    ],
    "segment_number": "100",
    "segment_inferred_from_text": false,
    "category": "Lifecycle",
    "requirement_type": "Business Rule",
    "condition_element_id": null,
    "condition_value": null,
    "condition_value_parsed": null,
    "condition_trigger": null,
    "expected_behavior": "New Sequence Number assigned per transaction request",
    "constraint_text": "New Sequence Number assigned per transaction request",
    "evidence_ref": {
      "source_id": "SRC-ATL105-PDF-001",
      "entity_id": "BR-38-10",
      "page": 38
    },
    "scope_name": null,
    "segment_ids": [
      "ENT-SEG-100"
    ],
    "segment_assignment_method": "declared",
    "segment_attribution": "unique",
    "confidence_pct": 90,
    "confidence_band": "HIGH",
    "confidence_factors": [
      "prior_confidence_score=0.9"
    ]
  },
  {
    "id": "REQ-SRC-ATL105-PDF-001:592",
    "statement": "Element No. 86 (Sequence Number) present in original transaction must be present and identical in the Authorization Only Reversal transaction.",
    "source_rule_id": "BR-97-7",
    "source_page": 97,
    "source_document_id": "SRC-ATL105-PDF-001",
    "derivation_method": "business_rule_derived",
    "confidence_score": 0.9,
    "below_confidence_gate": false,
    "requires_review": false,
    "flags": [],
    "related_entity_ids": [
      "ENT-ELEM-86"
    ],
    "segment_number": "100",
    "segment_inferred_from_text": false,
    "category": "Lifecycle",
    "requirement_type": "Business Rule",
    "condition_element_id": null,
    "condition_value": null,
    "condition_value_parsed": null,
    "condition_trigger": "Sequence Number present in original transaction",
    "expected_behavior": "Must be present and identical in reversal transaction",
    "constraint_text": "Must be present and identical in reversal transaction",
    "evidence_ref": {
      "source_id": "SRC-ATL105-PDF-001",
      "entity_id": "BR-97-7",
      "page": 97
    },
    "scope_name": null,
    "segment_ids": [
      "ENT-SEG-100"
    ],
    "segment_assignment_method": "declared",
    "segment_attribution": "unique",
    "confidence_pct": 90,
    "confidence_band": "HIGH",
    "confidence_factors": [
      "prior_confidence_score=0.9"
    ]
  }
]
```

</details>

## 4. Detailed BR > TS > TC > TD chains

<a id="case-1"></a>
### 1. Authorization to completion - TC-001779

**Why selected:** Core financial lifecycle baseline: preserve the authorization Sequence Number in capture.

**Trace:** `REQ-SRC-ATL105-PDF-001:1921` **>** `SC-2087` **>** `TC-001779` **>** [TD-TC-001779.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step1.authorization.json>) + [TD-TC-001779.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step2.completion.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:1921**: Sequence Number must persist in all subsequent transactions associated with the initial authorization request. Source rule **BR-402-1**, PDF page **402**; category **Lifecycle**; producer BR confidence **85%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2087"`.
- Original name: Flow — Authorization followed by Completion (Financial Transaction Request → Purchase/Capture) [maximal-multi-brand]
- Flow ID: `FLOW-AUTH-COMPLETION`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Authorization followed by Completion (Financial Transaction Request → Purchase/Capture) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-AUTH-COMPLETION",
  "flow_steps": [
    {
      "step": 1,
      "role": "authorization",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "3"
      }
    },
    {
      "step": 2,
      "role": "completion",
      "transaction": "Purchase/Capture",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-1",
          "source_page": null,
          "rule": "Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
          "spec_anchor": {
            "keywords": [
              "sequence number must persist",
              "subsequent transactions"
            ],
            "element_no": "86"
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 0.9,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-402-1",
  "source_page": null,
  "business_rule_assertion": "Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "87ae70ca34f0265895d45fdac2c141c2929a1847c83ddca7c111e0a73d96d857",
  "dedup_key": "ee632711e92421eff0c8888eca759e31a0285ea3b7d6da61bde2b6cbe6cb5da7",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1921",
      "rule_id": "BR-402-1",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:1921"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2087",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-001779"`; direct scenario reference: `SC-2087`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | authorization / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"3"} | [TD-TC-001779.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step1.authorization.json>) | []; No per-step host outcome declared |
| 2 | completion / logical: Purchase/Capture; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-001779.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step2.completion.json>) | [{"element":"Approval Number","from_step":1,"rule":"Required on reversals for matching information to the original\npurchase. If it is included in a Purchase Request, then the\nPurchase Request is considered preauthorized, and BUYPASS\ndoes not go out for authorization. In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:","source_rule_id":"ENT-ELEM-5"}]; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-1 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "authorization",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '3' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "completion",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '3'",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 1's RESPONSE, which is processor-generated and cannot be derived from the spec",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: Approval Number from step 1 response",
    "FLOW_REQUIRED_FIELD_INCLUDED: this element is the target of a declared flow link — its inclusion is asserted by the flow, not by the generic inclusion profile",
    "FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 1's live response value at execution time",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "RESPONSE_ORIGIN_FIELD_SUPPRESSED: a later flow step carries this element from THIS step's own RESPONSE (processor-generated); the active inclusion profile would otherwise have included it here, which would misrepresent it as terminal-supplied — set it via scenario parameters to test a variant where it IS request-supplied",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: authorization

- TD JSON: [TD-TC-001779.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step1.authorization.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-001779.step1.authorization.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-001779.step1.authorization.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step1.authorization.meta.json>).
- Identity: `testCaseId=TC-001779`, `scenarioId=SC-2087`, `step=1`, `role=authorization`, `card_brand=MC` - checked.
- Payload SHA-256: `9578caf4216547ed48b1d87c4b9129e957b4f4c4bc7584edc6f5fcd57611aba1`.
- Metadata SHA-256: `c94f78df7528714e5d0f979ff4cf0c0e72d7eb3d149cb6c7ba7035d9ca84226d`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "KOOU302594317266705244",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5147905507406026",
      "CardDiscretionaryBlockData": "0230",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: completion

- TD JSON: [TD-TC-001779.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step2.completion.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-001779.step2.completion.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-001779.step2.completion.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001779.step2.completion.meta.json>).
- Identity: `testCaseId=TC-001779`, `scenarioId=SC-2087`, `step=2`, `role=completion`, `card_brand=MC` - checked.
- Payload SHA-256: `d942a1fc7bfee4ed23dd0455e7fa2fa569990eec0ede33f9e96a904fdf68a902`.
- Metadata SHA-256: `77f1d7c6c0ed9fa3dedcdb8af94912b0670a09cffafcf59e02b7a965d7ff6a9c`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "CKLC353241855813905927",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5147905507406026",
      "CardDiscretionaryBlockData": "0230",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "DQUCUM",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-2"></a>
### 2. Authorization cancellation - TC-002005

**Why selected:** Release an unused authorization using the correct cancellation transaction code and original Sequence Number.

**Trace:** `REQ-SRC-ATL105-PDF-001:592` **>** `SC-2313` **>** `TC-002005` **>** [TD-TC-002005.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step1.authorization.json>) + [TD-TC-002005.step2.cancellation.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step2.cancellation.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:592**: Element No. 86 (Sequence Number) present in original transaction must be present and identical in the Authorization Only Reversal transaction. Source rule **BR-97-7**, PDF page **97**; category **Lifecycle**; producer BR confidence **90%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2313"`.
- Original name: Flow — Authorization followed by Cancellation (Financial Transaction Request → Financial Transaction Request) [maximal-multi-brand]
- Flow ID: `FLOW-AUTH-CANCELLATION`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Authorization followed by Cancellation (Financial Transaction Request → Financial Transaction Request) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-AUTH-CANCELLATION",
  "flow_steps": [
    {
      "step": 1,
      "role": "authorization",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "3"
      }
    },
    {
      "step": 2,
      "role": "cancellation",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "S"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-97-7",
          "source_page": 83,
          "rule": "§10.1.2.2/§10.2.2.2 (p.83/97): 'Element No.86 (Sequence Number)-present in the original transaction-must be present and identical in the Authorization Only Reversal transaction.' Element No. 78 (Prompt Code) must contain Transaction Type code 'S' — verified verbatim against raw/pdf_pages/page_0083.txt and page_0097.txt, 2026-10-01.",
          "spec_anchor": {
            "keywords": [
              "element no.86",
              "sequence number",
              "authorization only reversal transaction"
            ],
            "page_hint": 97
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 0.9,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-97-7",
  "source_page": 83,
  "business_rule_assertion": "§10.1.2.2/§10.2.2.2 (p.83/97): 'Element No.86 (Sequence Number)-present in the original transaction-must be present and identical in the Authorization Only Reversal transaction.' Element No. 78 (Prompt Code) must contain Transaction Type code 'S' — verified verbatim against raw/pdf_pages/page_0083.txt and page_0097.txt, 2026-10-01.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "3147287bfb614a6153d6ba0880157334edcef11cb1c3b0f36d86c4f95005db37",
  "dedup_key": "d51e954e5dd87b40c5261c0409c469b9831b7ec1abc7eaec3f46f569bd6111f8",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:592",
      "rule_id": "BR-97-7",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:592"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2313",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-002005"`; direct scenario reference: `SC-2313`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | authorization / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"3"} | [TD-TC-002005.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step1.authorization.json>) | []; No per-step host outcome declared |
| 2 | cancellation / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"S"} | [TD-TC-002005.step2.cancellation.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step2.cancellation.json>) | [{"element":"Approval Number","from_step":1,"rule":"Required on reversals for matching information to the original\npurchase. If it is included in a Purchase Request, then the\nPurchase Request is considered preauthorized, and BUYPASS\ndoes not go out for authorization. In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:","source_rule_id":"ENT-ELEM-5"}]; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-97-7 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Financial Transaction Request' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "authorization",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '3' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "cancellation",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type 'S' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = 'S'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = 'S'",
    "FLOW_LINK: carried 'Prompt Code' from step 1 — In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — §10.1.2.2/§10.2.2.2 (p.83/97): 'Element No.86 (Sequence Number)-present in the original transaction-must be present and identical in the Authorization Only Reversal transaction.' Element No. 78 (Prompt Code) must contain Transaction Type code 'S' — verified verbatim against raw/pdf_pages/page_0083.txt and page_0097.txt, 2026-10-01.",
    "FLOW_LINK_APPLIED: Prompt Code carried from step 1",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 1's RESPONSE, which is processor-generated and cannot be derived from the spec",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: Approval Number from step 1 response",
    "FLOW_REQUIRED_FIELD_INCLUDED: this element is the target of a declared flow link — its inclusion is asserted by the flow, not by the generic inclusion profile",
    "FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 1's live response value at execution time",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "RESPONSE_ORIGIN_FIELD_SUPPRESSED: a later flow step carries this element from THIS step's own RESPONSE (processor-generated); the active inclusion profile would otherwise have included it here, which would misrepresent it as terminal-supplied — set it via scenario parameters to test a variant where it IS request-supplied",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: authorization

- TD JSON: [TD-TC-002005.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step1.authorization.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002005.step1.authorization.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002005.step1.authorization.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step1.authorization.meta.json>).
- Identity: `testCaseId=TC-002005`, `scenarioId=SC-2313`, `step=1`, `role=authorization`, `card_brand=MC` - checked.
- Payload SHA-256: `446200d299dacd0a8bd6ab6395174565fe8137fa40a5dc2dbb6e42879f342e20`.
- Metadata SHA-256: `91180e26cd1d4eeffdc25a9488b22c8e5f0f8afc138443ed0124c344ad2ba191`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "DYTZ570566042118147497",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5168023653523455",
      "CardDiscretionaryBlockData": "0232",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: cancellation

- TD JSON: [TD-TC-002005.step2.cancellation.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step2.cancellation.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002005.step2.cancellation.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002005.step2.cancellation.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002005.step2.cancellation.meta.json>).
- Identity: `testCaseId=TC-002005`, `scenarioId=SC-2313`, `step=2`, `role=cancellation`, `card_brand=MC` - checked.
- Payload SHA-256: `4f9b395c792e39e56714c28bba42badb122e9a1cfa721d46ecfc530ba8dd7beb`.
- Metadata SHA-256: `f0893307865766726f4054c66eec7885c6e0f2ba742292528b197d176dc5bd50`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "HIIM365091381149070305",
      "PromptCode": {
        "TransactionType": "S",
        "CardType": "020"
      },
      "AccountNumber": "5168023653523455",
      "CardDiscretionaryBlockData": "0232",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "FI6LX4",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-3"></a>
### 3. Authorization, completion and void - TC-002104

**Why selected:** Three-step lifecycle with response-derived Approval Number substitution and reversal matching.

**Trace:** `REQ-SRC-ATL105-PDF-001:1661` + `REQ-SRC-ATL105-PDF-001:1921` + `REQ-SRC-ATL105-PDF-001:1923` **>** `SC-2412` **>** `TC-002104` **>** [TD-TC-002104.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step1.authorization.json>) + [TD-TC-002104.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step2.completion.json>) + [TD-TC-002104.step3.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step3.void.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:1661**: Approval Number is required on reversals for matching information to the original purchase. Source rule **BR-361-1**, PDF page **361**; category **Lifecycle**; producer BR confidence **90%**.
- **REQ-SRC-ATL105-PDF-001:1921**: Sequence Number must persist in all subsequent transactions associated with the initial authorization request. Source rule **BR-402-1**, PDF page **402**; category **Lifecycle**; producer BR confidence **85%**.
- **REQ-SRC-ATL105-PDF-001:1923**: In purchase reversal/void or time-out reversal, Sequence Number must match original transaction request. Source rule **BR-402-3**, PDF page **402**; category **Lifecycle**; producer BR confidence **85%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2412"`.
- Original name: Flow — Authorization, Completion, then Void (Financial Transaction Request → Purchase/Capture → Purchase Reversal) [maximal-multi-brand]
- Flow ID: `FLOW-AUTH-COMPLETION-VOID`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Authorization, Completion, then Void (Financial Transaction Request → Purchase/Capture → Purchase Reversal) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-AUTH-COMPLETION-VOID",
  "flow_steps": [
    {
      "step": 1,
      "role": "authorization",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "3"
      }
    },
    {
      "step": 2,
      "role": "completion",
      "transaction": "Purchase/Capture",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-1",
          "source_page": null,
          "rule": "Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
          "spec_anchor": {
            "keywords": [
              "sequence number must persist",
              "subsequent transactions"
            ],
            "element_no": "86"
          }
        }
      ]
    },
    {
      "step": 3,
      "role": "void",
      "transaction": "Purchase Reversal",
      "fixed_elements": {
        "Prompt Code.TransactionType": "8"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 2,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-3",
          "source_page": null,
          "rule": "CORRECTED 2026-10-01 (was from_step 1): a code '8' Void reverses a purchase (Appendix G p.539) — the Completion (step 2) is the purchase being voided, not the Authorization. REVIEW_REQUIRED, see flow description.",
          "spec_anchor": {
            "keywords": [
              "purchase reversal void",
              "time-out reversal",
              "must match original transaction request"
            ],
            "element_no": "86"
          }
        },
        {
          "type": "carry_response_field",
          "from_step": 2,
          "element_name": "Approval Number",
          "source_rule_id": "BR-361-1",
          "source_page": 361,
          "rule": "Declared explicitly so auto-derivation (which resolves origin by the Authorization's quoted Transaction Type code '3') cannot re-attach this to step 1 instead of step 2. Same correction and REVIEW_REQUIRED status as the Sequence Number link above.",
          "spec_anchor": {
            "keywords": [
              "approval number is required on reversals",
              "matching information to the original purchase"
            ]
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 1,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-402-1",
  "source_page": 361,
  "business_rule_assertion": "Sequence Number must persist in all subsequent transactions associated with the initial authorization request.; CORRECTED 2026-10-01 (was from_step 1): a code '8' Void reverses a purchase (Appendix G p.539) — the Completion (step 2) is the purchase being voided, not the Authorization. REVIEW_REQUIRED, see flow description.; Declared explicitly so auto-derivation (which resolves origin by the Authorization's quoted Transaction Type code '3') cannot re-attach this to step 1 instead of step 2. Same correction and REVIEW_REQUIRED status as the Sequence Number link above.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "e3e0666039a882f9d85176fe05d7bc496f144b7e95b02c3dc7e0eac424e4523d",
  "dedup_key": "ea69819343d0d278de334919b9b9846174a895b362eedfa563f8d4f8edc368c4",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1921",
      "rule_id": "BR-402-1",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    },
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1923",
      "rule_id": "BR-402-3",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 3,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    },
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1661",
      "rule_id": "BR-361-1",
      "basis": "flow_link",
      "link_type": "carry_response_field",
      "step": 3,
      "element_name": "Approval Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:1661",
    "REQ-SRC-ATL105-PDF-001:1921",
    "REQ-SRC-ATL105-PDF-001:1923"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2412",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-002104"`; direct scenario reference: `SC-2412`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | authorization / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"3"} | [TD-TC-002104.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step1.authorization.json>) | []; No per-step host outcome declared |
| 2 | completion / logical: Purchase/Capture; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-002104.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step2.completion.json>) | [{"element":"Approval Number","from_step":1,"rule":"Required on reversals for matching information to the original\npurchase. If it is included in a Purchase Request, then the\nPurchase Request is considered preauthorized, and BUYPASS\ndoes not go out for authorization. In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:","source_rule_id":"ENT-ELEM-5"}]; No per-step host outcome declared |
| 3 | void / logical: Purchase Reversal; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"8"} | [TD-TC-002104.step3.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step3.void.json>) | [{"element":"Approval Number","from_step":2,"rule":"Declared explicitly so auto-derivation (which resolves origin by the Authorization's quoted Transaction Type code '3') cannot re-attach this to step 1 instead of step 2. Same correction and REVIEW_REQUIRED status as the Sequence Number link above.","source_rule_id":"BR-361-1"}]; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-1 |
| 2 > 3 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-3 |
| 2 > 3 | Approval Number / carry_response_field | `["GY417B"]` | `["8UWUTS"]` | RUNTIME_RESPONSE_REQUIRED | BR-361-1 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Purchase/Capture → Purchase Reversal' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "authorization",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '3' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "completion",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 3,
      "role": "void",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '8' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase Reversal' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '8'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '8'",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
    "FLOW_LINK: carried 'Sequence Number' from step 2 — CORRECTED 2026-10-01 (was from_step 1): a code '8' Void reverses a purchase (Appendix G p.539) — the Completion (step 2) is the purchase being voided, not the Authorization. REVIEW_REQUIRED, see flow description.",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 2",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 1's RESPONSE, which is processor-generated and cannot be derived from the spec",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 2's RESPONSE, which is processor-generated and cannot be derived from the spec",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: Approval Number from step 1 response",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: Approval Number from step 2 response",
    "FLOW_REQUIRED_FIELD_INCLUDED: this element is the target of a declared flow link — its inclusion is asserted by the flow, not by the generic inclusion profile",
    "FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 1's live response value at execution time",
    "FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 2's live response value at execution time",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "RESPONSE_ORIGIN_FIELD_SUPPRESSED: a later flow step carries this element from THIS step's own RESPONSE (processor-generated); the active inclusion profile would otherwise have included it here, which would misrepresent it as terminal-supplied — set it via scenario parameters to test a variant where it IS request-supplied",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step3.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: authorization

- TD JSON: [TD-TC-002104.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step1.authorization.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002104.step1.authorization.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002104.step1.authorization.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step1.authorization.meta.json>).
- Identity: `testCaseId=TC-002104`, `scenarioId=SC-2412`, `step=1`, `role=authorization`, `card_brand=MC` - checked.
- Payload SHA-256: `2ed2c2641f08c0c3f5a1edd6940ccf2633ac86af460ab7748ae52b89b7752a7c`.
- Metadata SHA-256: `58694e2a82d1615d0b29627242c92248f6db7e75da11c2db6209031437a7850e`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "AGOG278996374675346549",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5102942932834400",
      "CardDiscretionaryBlockData": "0829",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: completion

- TD JSON: [TD-TC-002104.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step2.completion.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002104.step2.completion.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002104.step2.completion.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step2.completion.meta.json>).
- Identity: `testCaseId=TC-002104`, `scenarioId=SC-2412`, `step=2`, `role=completion`, `card_brand=MC` - checked.
- Payload SHA-256: `d15f2484d147fb5c6e2df2fcec6d2375e2376e6a7985bd2c8ebd3837fa940430`.
- Metadata SHA-256: `fd6619827dae3987b6ad4d3ea7b368a5a26676e12b94bf130eb6fc6fa03127a2`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "FOMJ569891120470588774",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5102942932834400",
      "CardDiscretionaryBlockData": "0829",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "GY417B",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 3: void

- TD JSON: [TD-TC-002104.step3.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step3.void.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002104.step3.void.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002104.step3.void.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002104.step3.void.meta.json>).
- Identity: `testCaseId=TC-002104`, `scenarioId=SC-2412`, `step=3`, `role=void`, `card_brand=MC` - checked.
- Payload SHA-256: `2200d3d11bdbf003593652d5138591b7ee261f2a0a687ea86493c251bb06c697`.
- Metadata SHA-256: `99ed0463c4325c799c421e890baa141606f5e06006e0f3fa9fef858e9af35f6d`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "PIYZ324708521749973887",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5102942932834400",
      "CardDiscretionaryBlockData": "0829",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "8UWUTS",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-4"></a>
### 4. Duplicate completion - TC-001889

**Why selected:** Duplicate financial submission review: preserve prior sequence and amounts without inventing a host idempotency outcome.

**Trace:** `REQ-SRC-ATL105-PDF-001:1921` **>** `SC-2197` **>** `TC-001889` **>** [TD-TC-001889.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step1.authorization.json>) + [TD-TC-001889.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step2.completion.json>) + [TD-TC-001889.step3.duplicate-completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step3.duplicate-completion.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:1921**: Sequence Number must persist in all subsequent transactions associated with the initial authorization request. Source rule **BR-402-1**, PDF page **402**; category **Lifecycle**; producer BR confidence **85%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2197"`.
- Original name: Flow — Authorization, Completion, then duplicate Completion (Financial Transaction Request → Purchase/Capture → Purchase/Capture) [maximal-multi-brand]
- Flow ID: `FLOW-AUTH-COMPLETION-DUPLICATE`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Authorization, Completion, then duplicate Completion (Financial Transaction Request → Purchase/Capture → Purchase/Capture) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-AUTH-COMPLETION-DUPLICATE",
  "flow_steps": [
    {
      "step": 1,
      "role": "authorization",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "3"
      }
    },
    {
      "step": 2,
      "role": "completion",
      "transaction": "Purchase/Capture",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-1",
          "source_page": null,
          "rule": "Sequence Number must match the original transaction request.",
          "spec_anchor": {
            "keywords": [
              "sequence number must persist",
              "subsequent transactions"
            ],
            "element_no": "86"
          }
        }
      ]
    },
    {
      "step": 3,
      "role": "duplicate_completion",
      "transaction": "Purchase/Capture",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      },
      "expected_outcome_note": "Duplicate of step 2. Whether this is declined, ignored or accepted is BUYPASS business logic and is NOT documented in ATL105 — outcome deliberately not asserted.",
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 2,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-1",
          "source_page": null,
          "rule": "Duplicate repeats the prior completion identically.",
          "spec_anchor": {
            "keywords": [
              "sequence number must persist",
              "subsequent transactions"
            ],
            "element_no": "86"
          }
        },
        {
          "type": "amount_relation",
          "from_step": 2,
          "relation": "equal",
          "element_name": "Fuel Purchase Amount",
          "rule": "Duplicate carries the same amount as the completion it repeats. Fix 2026-09-28: 'Transaction Amount' is not a real ATL105 element (no field of that name exists in any extracted template) — ATL105's Standard Message Data Segment has no single combined amount field, only Fuel Purchase Amount/Nonfuel Amount/Tax Amount/Cash Amount; split into one amount_relation link per real field below instead of guessing which one field was meant.",
          "citation_kind": "unresolved_kb_gap"
        },
        {
          "type": "amount_relation",
          "from_step": 2,
          "relation": "equal",
          "element_name": "Nonfuel Amount",
          "rule": "Duplicate carries the same amount as the completion it repeats.",
          "citation_kind": "unresolved_kb_gap"
        },
        {
          "type": "amount_relation",
          "from_step": 2,
          "relation": "equal",
          "element_name": "Tax Amount",
          "rule": "Duplicate carries the same amount as the completion it repeats.",
          "citation_kind": "unresolved_kb_gap"
        },
        {
          "type": "amount_relation",
          "from_step": 2,
          "relation": "equal",
          "element_name": "Cash Amount",
          "rule": "Duplicate carries the same amount as the completion it repeats.",
          "citation_kind": "unresolved_kb_gap"
        }
      ]
    }
  ],
  "flow_risk_weight": 1,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-402-1",
  "source_page": null,
  "business_rule_assertion": "Sequence Number must match the original transaction request.; Duplicate repeats the prior completion identically.; Duplicate carries the same amount as the completion it repeats. Fix 2026-09-28: 'Transaction Amount' is not a real ATL105 element (no field of that name exists in any extracted template) — ATL105's Standard Message Data Segment has no single combined amount field, only Fuel Purchase Amount/Nonfuel Amount/Tax Amount/Cash Amount; split into one amount_relation link per real field below instead of guessing which one field was meant.; Duplicate carries the same amount as the completion it repeats.; Duplicate carries the same amount as the completion it repeats.; Duplicate carries the same amount as the completion it repeats.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "6e7563caa4db8502efb90c3e345be6224cfc924514f826685a4c99034b33d39c",
  "dedup_key": "705e25c89cd0fdec7eeff1b5e9dc103720657a7b7e0e089f962f330b81be239e",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1921",
      "rule_id": "BR-402-1",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    },
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1921",
      "rule_id": "BR-402-1",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 3,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:1921"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2197",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-001889"`; direct scenario reference: `SC-2197`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | authorization / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"3"} | [TD-TC-001889.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step1.authorization.json>) | []; No per-step host outcome declared |
| 2 | completion / logical: Purchase/Capture; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-001889.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step2.completion.json>) | [{"element":"Approval Number","from_step":1,"rule":"Required on reversals for matching information to the original\npurchase. If it is included in a Purchase Request, then the\nPurchase Request is considered preauthorized, and BUYPASS\ndoes not go out for authorization. In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:","source_rule_id":"ENT-ELEM-5"}]; No per-step host outcome declared |
| 3 | duplicate_completion / logical: Purchase/Capture; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-001889.step3.duplicate-completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step3.duplicate-completion.json>) | [{"element":"Approval Number","from_step":1,"rule":"Required on reversals for matching information to the original\npurchase. If it is included in a Purchase Request, then the\nPurchase Request is considered preauthorized, and BUYPASS\ndoes not go out for authorization. In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:","source_rule_id":"ENT-ELEM-5"}]; Duplicate of step 2. Whether this is declined, ignored or accepted is BUYPASS business logic and is NOT documented in ATL105 — outcome deliberately not asserted. |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-1 |
| 2 > 3 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-1 |
| 2 > 3 | Fuel Purchase Amount / amount_relation | `["1"]` | `["1"]` | EQUALITY_PRESENT | unresolved_kb_gap |
| 2 > 3 | Nonfuel Amount / amount_relation | `["1"]` | `["1"]` | EQUALITY_PRESENT | unresolved_kb_gap |
| 2 > 3 | Tax Amount / amount_relation | `["1"]` | `["1"]` | EQUALITY_PRESENT | unresolved_kb_gap |
| 2 > 3 | Cash Amount / amount_relation | `["1"]` | `["1"]` | EQUALITY_PRESENT | unresolved_kb_gap |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Purchase/Capture → Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "authorization",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '3' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "completion",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 3,
      "role": "duplicate_completion",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": "Duplicate of step 2. Whether this is declined, ignored or accepted is BUYPASS business logic and is NOT documented in ATL105 — outcome deliberately not asserted."
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '3'",
    "FLOW_LINK: 'Cash Amount' is equal relative to step 2",
    "FLOW_LINK: 'Fuel Purchase Amount' is equal relative to step 2",
    "FLOW_LINK: 'Nonfuel Amount' is equal relative to step 2",
    "FLOW_LINK: 'Tax Amount' is equal relative to step 2",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — Sequence Number must match the original transaction request.",
    "FLOW_LINK: carried 'Sequence Number' from step 2 — Duplicate repeats the prior completion identically.",
    "FLOW_LINK_APPLIED: Cash Amount equal vs step 2",
    "FLOW_LINK_APPLIED: Fuel Purchase Amount equal vs step 2",
    "FLOW_LINK_APPLIED: Nonfuel Amount equal vs step 2",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 2",
    "FLOW_LINK_APPLIED: Tax Amount equal vs step 2",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 1's RESPONSE, which is processor-generated and cannot be derived from the spec",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: Approval Number from step 1 response",
    "FLOW_REQUIRED_FIELD_INCLUDED: this element is the target of a declared flow link — its inclusion is asserted by the flow, not by the generic inclusion profile",
    "FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 1's live response value at execution time",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "RESPONSE_ORIGIN_FIELD_SUPPRESSED: a later flow step carries this element from THIS step's own RESPONSE (processor-generated); the active inclusion profile would otherwise have included it here, which would misrepresent it as terminal-supplied — set it via scenario parameters to test a variant where it IS request-supplied",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step3.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: authorization

- TD JSON: [TD-TC-001889.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step1.authorization.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-001889.step1.authorization.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-001889.step1.authorization.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step1.authorization.meta.json>).
- Identity: `testCaseId=TC-001889`, `scenarioId=SC-2197`, `step=1`, `role=authorization`, `card_brand=MC` - checked.
- Payload SHA-256: `b884a76b5c5575e6480858a28a0bef81ea178aec2e07f69221ee7cd577d05cd3`.
- Metadata SHA-256: `83bb287d098a09cb084b87bc83b60af9febd29ecfca665ad410839786fee96ba`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "RLOA047668193947306482",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5191411325860523",
      "CardDiscretionaryBlockData": "0130",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: completion

- TD JSON: [TD-TC-001889.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step2.completion.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-001889.step2.completion.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-001889.step2.completion.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step2.completion.meta.json>).
- Identity: `testCaseId=TC-001889`, `scenarioId=SC-2197`, `step=2`, `role=completion`, `card_brand=MC` - checked.
- Payload SHA-256: `7e463d89f4ae7b3e050409dceaa6823de32ee0ecc9f54ce00d98c3c17e7ea004`.
- Metadata SHA-256: `5e25296827391022794c53facdb2a6e31a8545ffc7a491deff83cfa555fa3516`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "MTKZ110279989845493215",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5191411325860523",
      "CardDiscretionaryBlockData": "0130",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "CT45I4",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 3: duplicate_completion

- TD JSON: [TD-TC-001889.step3.duplicate-completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step3.duplicate-completion.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-001889.step3.duplicate-completion.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-001889.step3.duplicate-completion.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001889.step3.duplicate-completion.meta.json>).
- Identity: `testCaseId=TC-001889`, `scenarioId=SC-2197`, `step=3`, `role=duplicate_completion`, `card_brand=MC` - checked.
- Payload SHA-256: `74fa7e4075345b389459e12a634e9403804b1bf538c0786f08d8b60ddc7fdd17`.
- Metadata SHA-256: `6698a35f1e309151a7270e9816068d64db79d9a0782a0116624d929ee48f8d75`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "MEFX000077796288801143",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5191411325860523",
      "CardDiscretionaryBlockData": "0130",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "SJR6YC",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-5"></a>
### 5. Timeout reversal followed by next transaction - TC-002204

**Why selected:** Recovery boundary: the TOR retains the original terminal and sequence; the next transaction changes sequence.

**Trace:** `REQ-SRC-ATL105-PDF-001:288` + `REQ-SRC-ATL105-PDF-001:293` **>** `SC-2512` **>** `TC-002204` **>** [TD-TC-002204.step1.original-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step1.original-transaction.json>) + [TD-TC-002204.step2.timeout-reversal.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step2.timeout-reversal.json>) + [TD-TC-002204.step3.next-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step3.next-transaction.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:288**: A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR. Source rule **BR-38-4**, PDF page **38**; category **Lifecycle**; producer BR confidence **87%**.
- **REQ-SRC-ATL105-PDF-001:293**: The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. Source rule **BR-38-9**, PDF page **38**; category **Lifecycle**; producer BR confidence **90%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2512"`.
- Original name: Flow — Original request, Time-out Reversal, then next financial transaction (Financial Transaction Request → Time-out Reversal → Financial Transaction Request) [maximal-multi-brand]
- Flow ID: `FLOW-TIMEOUT-NEXT-TRANSACTION`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Original request, Time-out Reversal, then next financial transaction (Financial Transaction Request → Time-out Reversal → Financial Transaction Request) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-TIMEOUT-NEXT-TRANSACTION",
  "flow_steps": [
    {
      "step": 1,
      "role": "original_transaction",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      }
    },
    {
      "step": 2,
      "role": "timeout_reversal",
      "transaction": "Time-out Reversal",
      "fixed_elements": {
        "Prompt Code.TransactionType": "Z"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-38-9",
          "source_page": null,
          "rule": "The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed.",
          "spec_anchor": {
            "keywords": [
              "sequence number of a tor",
              "must be the same"
            ],
            "element_no": "86"
          }
        },
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Terminal Identifier",
          "source_rule_id": "BR-38-4",
          "source_page": 38,
          "rule": "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.",
          "spec_anchor": {
            "keywords": [
              "sent from the same device",
              "financial transaction request that resulted"
            ]
          }
        }
      ]
    },
    {
      "step": 3,
      "role": "next_transaction",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      },
      "expected_outcome_note": "TOR must be sent before the next financial transaction from the same device (QE feedback) — sequencing is not asserted here, only the Sequence Number relationship.",
      "links": [
        {
          "type": "must_differ",
          "from_step": 2,
          "element_name": "Sequence Number",
          "source_rule_id": "QE-2026-09-16",
          "source_page": null,
          "rule": "QE feedback: the next financial transaction uses a new Sequence Number.",
          "citation_kind": "qe_source"
        }
      ]
    }
  ],
  "flow_risk_weight": 1,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-38-9",
  "source_page": 38,
  "business_rule_assertion": "The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed.; A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.; QE feedback: the next financial transaction uses a new Sequence Number.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "94ba63653fd3871ef68c24114a8191040e3789e5c3563df64bc044d545ff7e9b",
  "dedup_key": "21d76576472d90ebf9fcc0d7acf11b1ec856fba7fcebf1d8a312519ac1732026",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:293",
      "rule_id": "BR-38-9",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    },
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:288",
      "rule_id": "BR-38-4",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Terminal Identifier",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:288",
    "REQ-SRC-ATL105-PDF-001:293"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2512",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-002204"`; direct scenario reference: `SC-2512`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | original_transaction / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-002204.step1.original-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step1.original-transaction.json>) | []; No per-step host outcome declared |
| 2 | timeout_reversal / logical: Time-out Reversal; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"Z"} | [TD-TC-002204.step2.timeout-reversal.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step2.timeout-reversal.json>) | []; No per-step host outcome declared |
| 3 | next_transaction / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-002204.step3.next-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step3.next-transaction.json>) | []; TOR must be sent before the next financial transaction from the same device (QE feedback) — sequencing is not asserted here, only the Sequence Number relationship. |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-38-9 |
| 1 > 2 | Terminal Identifier / carry_request_field | `["OLQP396559734877836807"]` | `["OLQP396559734877836807"]` | EQUALITY_PRESENT | BR-38-4 |
| 2 > 3 | Sequence Number / must_differ | `["000001"]` | `["000002"]` | DIFFERENCE_PRESENT | QE-2026-09-16 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Time-out Reversal → Financial Transaction Request' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "original_transaction",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "timeout_reversal",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type 'Z' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Time-out Reversal' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 3,
      "role": "next_transaction",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": "TOR must be sent before the next financial transaction from the same device (QE feedback) — sequencing is not asserted here, only the Sequence Number relationship."
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = 'Z'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = 'Z'",
    "FLOW_LINK: 'Sequence Number' must differ from step 2's value — QE feedback: the next financial transaction uses a new Sequence Number.",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed.",
    "FLOW_LINK: carried 'Terminal Identifier' from step 1 — A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_APPLIED: Sequence Number must differ from step 2",
    "FLOW_LINK_APPLIED: Terminal Identifier carried from step 1",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step3.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: original_transaction

- TD JSON: [TD-TC-002204.step1.original-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step1.original-transaction.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002204.step1.original-transaction.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002204.step1.original-transaction.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step1.original-transaction.meta.json>).
- Identity: `testCaseId=TC-002204`, `scenarioId=SC-2512`, `step=1`, `role=original_transaction`, `card_brand=MC` - checked.
- Payload SHA-256: `16cb1bc3877f55cdcbf2d1a97414c256e756a59b5cc1f87a5c54842b41416b92`.
- Metadata SHA-256: `459ca7e126b8360834fbdcd9cacba18872741362fb69af293d35b6fc9bae5233`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "OLQP396559734877836807",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5124058760656940",
      "CardDiscretionaryBlockData": "1027",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: timeout_reversal

- TD JSON: [TD-TC-002204.step2.timeout-reversal.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step2.timeout-reversal.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002204.step2.timeout-reversal.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002204.step2.timeout-reversal.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step2.timeout-reversal.meta.json>).
- Identity: `testCaseId=TC-002204`, `scenarioId=SC-2512`, `step=2`, `role=timeout_reversal`, `card_brand=MC` - checked.
- Payload SHA-256: `afe51eafad7367ccca6cc8e6ec22f7ff4257a0002887aaabe878f6b6938bebf7`.
- Metadata SHA-256: `1f4e5ed151e58a197469de589f1f813057d5b8fc1c069f72ec9c69383549bee3`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "OLQP396559734877836807",
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5124058760656940",
      "CardDiscretionaryBlockData": "1027",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 3: next_transaction

- TD JSON: [TD-TC-002204.step3.next-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step3.next-transaction.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002204.step3.next-transaction.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002204.step3.next-transaction.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002204.step3.next-transaction.meta.json>).
- Identity: `testCaseId=TC-002204`, `scenarioId=SC-2512`, `step=3`, `role=next_transaction`, `card_brand=MC` - checked.
- Payload SHA-256: `0647b8fb453c6cd6c65bdfd995bc03ba2405067e700f1c4b016e3860f7e5ca8c`.
- Metadata SHA-256: `0a1e4b02cc088cbe4779c006cc0849feca7866b96959c4458be0220a9707cd31`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "XNFI253742570981079800",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5124058760656940",
      "CardDiscretionaryBlockData": "1027",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000002",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-6"></a>
### 6. EMV authorization to completion - TC-002384

**Why selected:** Separate chip-path coverage with explicit entry mode 05 and Segment 130 in the authorization fixture.

**Trace:** `REQ-SRC-ATL105-PDF-001:1921` **>** `SC-2692` **>** `TC-002384` **>** [TD-TC-002384.step1.emv-authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step1.emv-authorization.json>) + [TD-TC-002384.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step2.completion.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:1921**: Sequence Number must persist in all subsequent transactions associated with the initial authorization request. Source rule **BR-402-1**, PDF page **402**; category **Lifecycle**; producer BR confidence **85%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2692"`.
- Original name: Flow — EMV Authorization followed by Completion (EMV Financial Transaction Request → Purchase/Capture) [maximal-multi-brand]
- Flow ID: `FLOW-EMV-AUTH-COMPLETION`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"05","field_profile":"maximal","pin_entry_capability":"1"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — EMV Authorization followed by Completion (EMV Financial Transaction Request → Purchase/Capture) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "05",
    "Terminal PIN Entry Capability": "1"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "05",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "1",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-EMV-AUTH-COMPLETION",
  "flow_steps": [
    {
      "step": 1,
      "role": "emv_authorization",
      "transaction": "EMV Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "3"
      }
    },
    {
      "step": 2,
      "role": "completion",
      "transaction": "Purchase/Capture",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-1",
          "source_page": null,
          "rule": "Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
          "spec_anchor": {
            "keywords": [
              "sequence number must persist",
              "subsequent transactions"
            ],
            "element_no": "86"
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 0.8,
  "target_transaction": "EMV Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-402-1",
  "source_page": null,
  "business_rule_assertion": "Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "12495ae1877049c234be07fb59416e3a1633547b8436c27cb0010e7678ca102b",
  "dedup_key": "754f349927b4f60f12f7b08645057063406a1ce563db293c3226c467bd140528",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands, EMV chip entry modes",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "05",
    "field_profile": "maximal",
    "pin_entry_capability": "1"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1921",
      "rule_id": "BR-402-1",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:1921"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2692",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-002384"`; direct scenario reference: `SC-2692`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | emv_authorization / logical: EMV Financial Transaction Request; physical: EMV Financial Transaction Request | {"Prompt Code.TransactionType":"3"} | [TD-TC-002384.step1.emv-authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step1.emv-authorization.json>) | []; No per-step host outcome declared |
| 2 | completion / logical: Purchase/Capture; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-002384.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step2.completion.json>) | [{"element":"Approval Number","from_step":1,"rule":"Required on reversals for matching information to the original\npurchase. If it is included in a Purchase Request, then the\nPurchase Request is considered preauthorized, and BUYPASS\ndoes not go out for authorization. In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:","source_rule_id":"ENT-ELEM-5"}]; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-1 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'EMV Financial Transaction Request → Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "emv_authorization",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '3' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "EMV Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "completion",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase/Capture' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='05' (entry_mode), positions [3, 3]='1' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '3'",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — Sequence Number must persist in all subsequent transactions associated with the initial authorization request.",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 1's RESPONSE, which is processor-generated and cannot be derived from the spec",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: Approval Number from step 1 response",
    "FLOW_REQUIRED_FIELD_INCLUDED: this element is the target of a declared flow link — its inclusion is asserted by the flow, not by the generic inclusion profile",
    "FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 1's live response value at execution time",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "LINKED_FIELD_DERIVED: 'EMV Chip Data Length' (hex_byte_length of 'EMV Chip Data', group 'EMV')",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'EMV Card Sequence Number' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "RESPONSE_ORIGIN_FIELD_SUPPRESSED: a later flow step carries this element from THIS step's own RESPONSE (processor-generated); the active inclusion profile would otherwise have included it here, which would misrepresent it as terminal-supplied — set it via scenario parameters to test a variant where it IS request-supplied",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: emv_authorization

- TD JSON: [TD-TC-002384.step1.emv-authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step1.emv-authorization.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002384.step1.emv-authorization.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002384.step1.emv-authorization.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step1.emv-authorization.meta.json>).
- Identity: `testCaseId=TC-002384`, `scenarioId=SC-2692`, `step=1`, `role=emv_authorization`, `card_brand=MC` - checked.
- Payload SHA-256: `ac522c07cd6b1498c4d393f93b4ac4e0d89fcafaf5f61421d650692bf67ef5bc`.
- Metadata SHA-256: `bd5d0721a524ec850c163d56e235fbaf13081d7d3bc24280f7bf74b9cc9e7974`.

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "WXFJ745690653692542148",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5198135906818633",
      "CardDiscretionaryBlockData": "0327",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0190",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "25",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000042203950580000480009A031803019C01009F02060000000015009F03060000000000009F0607A00000000422039F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37048042484C9F41030128509F530152"
    }
  }
}
```

##### Step 2: completion

- TD JSON: [TD-TC-002384.step2.completion.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step2.completion.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002384.step2.completion.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002384.step2.completion.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002384.step2.completion.meta.json>).
- Identity: `testCaseId=TC-002384`, `scenarioId=SC-2692`, `step=2`, `role=completion`, `card_brand=MC` - checked.
- Payload SHA-256: `316c9b36e5162e1a8a6bef443edacdbeb55dfb58a1ce812a496902c625b7f2b9`.
- Metadata SHA-256: `aa9f406275c2b9f79a5568f4114c1ff51cf3f390e09266d5112079f4fdb190d5`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "QQMB868960109866456734",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5198135906818633",
      "CardDiscretionaryBlockData": "0327",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "TU2ZT4",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-7"></a>
### 7. EMV timeout reversal - TC-002469

**Why selected:** Recovery for the chip path: preserve original-device and original-sequence relationships.

**Trace:** `REQ-SRC-ATL105-PDF-001:288` + `REQ-SRC-ATL105-PDF-001:293` **>** `SC-2777` **>** `TC-002469` **>** [TD-TC-002469.step1.emv-financial-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step1.emv-financial-transaction.json>) + [TD-TC-002469.step2.timeout-reversal.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step2.timeout-reversal.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:288**: A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR. Source rule **BR-38-4**, PDF page **38**; category **Lifecycle**; producer BR confidence **87%**.
- **REQ-SRC-ATL105-PDF-001:293**: The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. Source rule **BR-38-9**, PDF page **38**; category **Lifecycle**; producer BR confidence **90%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2777"`.
- Original name: Flow — EMV transaction followed by Time-out Reversal (EMV Financial Transaction Request → Time-out Reversal) [maximal-multi-brand]
- Flow ID: `FLOW-EMV-TIMEOUT-REVERSAL`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"05","field_profile":"maximal","pin_entry_capability":"1"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — EMV transaction followed by Time-out Reversal (EMV Financial Transaction Request → Time-out Reversal) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "05",
    "Terminal PIN Entry Capability": "1"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "05",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "1",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-EMV-TIMEOUT-REVERSAL",
  "flow_steps": [
    {
      "step": 1,
      "role": "emv_financial_transaction",
      "transaction": "EMV Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      }
    },
    {
      "step": 2,
      "role": "timeout_reversal",
      "transaction": "Time-out Reversal",
      "fixed_elements": {
        "Prompt Code.TransactionType": "Z"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-38-9",
          "source_page": null,
          "rule": "The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed.",
          "spec_anchor": {
            "keywords": [
              "sequence number of a tor",
              "must be the same"
            ],
            "element_no": "86"
          }
        },
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Terminal Identifier",
          "source_rule_id": "BR-38-4",
          "source_page": 38,
          "rule": "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.",
          "spec_anchor": {
            "keywords": [
              "sent from the same device",
              "financial transaction request that resulted"
            ]
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 0.8,
  "target_transaction": "EMV Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-38-9",
  "source_page": 38,
  "business_rule_assertion": "The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed.; A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "a70d8ca5601ed05ea35f1dfe88278f603a1135292bac115a4a7fb959e0c18c21",
  "dedup_key": "5d7e74d0432cb79888b5b02b71949e7f54b029dfa5d06905e9968174177ea3c2",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands, EMV chip entry modes",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "05",
    "field_profile": "maximal",
    "pin_entry_capability": "1"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:293",
      "rule_id": "BR-38-9",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    },
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:288",
      "rule_id": "BR-38-4",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Terminal Identifier",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:288",
    "REQ-SRC-ATL105-PDF-001:293"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2777",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-002469"`; direct scenario reference: `SC-2777`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | emv_financial_transaction / logical: EMV Financial Transaction Request; physical: EMV Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-002469.step1.emv-financial-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step1.emv-financial-transaction.json>) | []; No per-step host outcome declared |
| 2 | timeout_reversal / logical: Time-out Reversal; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"Z"} | [TD-TC-002469.step2.timeout-reversal.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step2.timeout-reversal.json>) | []; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-38-9 |
| 1 > 2 | Terminal Identifier / carry_request_field | `["CJML145721604105762465"]` | `["CJML145721604105762465"]` | EQUALITY_PRESENT | BR-38-4 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'EMV Financial Transaction Request → Time-out Reversal' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "emv_financial_transaction",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '0' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "EMV Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "timeout_reversal",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type 'Z' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Time-out Reversal' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='05' (entry_mode), positions [3, 3]='1' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = 'Z'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = 'Z'",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed.",
    "FLOW_LINK: carried 'Terminal Identifier' from step 1 — A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_APPLIED: Terminal Identifier carried from step 1",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "LINKED_FIELD_DERIVED: 'EMV Chip Data Length' (hex_byte_length of 'EMV Chip Data', group 'EMV')",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'EMV Card Sequence Number' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: emv_financial_transaction

- TD JSON: [TD-TC-002469.step1.emv-financial-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step1.emv-financial-transaction.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002469.step1.emv-financial-transaction.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002469.step1.emv-financial-transaction.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step1.emv-financial-transaction.meta.json>).
- Identity: `testCaseId=TC-002469`, `scenarioId=SC-2777`, `step=1`, `role=emv_financial_transaction`, `card_brand=MC` - checked.
- Payload SHA-256: `9ae88714e3e7fa19ef9f091ff1e2c7bff03e8c46ae90e9182c327c05fea3d1c2`.
- Metadata SHA-256: `9e9b148b9832ca3cd6367581a5fadf86b67f0310db1421eeac3aad8ed0afdf86`.

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "CJML145721604105762465",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5193410756526072",
      "CardDiscretionaryBlockData": "0328",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0190",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "25",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000042203950580000480009A031803019C01009F02060000000015009F03060000000000009F0607A00000000422039F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37048042484C9F41030128509F530152"
    }
  }
}
```

##### Step 2: timeout_reversal

- TD JSON: [TD-TC-002469.step2.timeout-reversal.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step2.timeout-reversal.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002469.step2.timeout-reversal.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002469.step2.timeout-reversal.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002469.step2.timeout-reversal.meta.json>).
- Identity: `testCaseId=TC-002469`, `scenarioId=SC-2777`, `step=2`, `role=timeout_reversal`, `card_brand=MC` - checked.
- Payload SHA-256: `cffbc3cfd8c573a99e80fb05f0264ff976029d3ecf0540f0965e45bd251fecf4`.
- Metadata SHA-256: `f9c08c32c6c24a9a7c67a3c2a6e01e7bba309ad133fa50cbbdc377c42e747bbc`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "CJML145721604105762465",
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5193410756526072",
      "CardDiscretionaryBlockData": "0328",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-8"></a>
### 8. Merchandise return followed by void - TC-001940

**Why selected:** Refund lifecycle coverage: preserve the original return Sequence Number when voiding with the declared U transaction code.

**Trace:** `REQ-SRC-ATL105-PDF-001:1923` **>** `SC-2248` **>** `TC-001940` **>** [TD-TC-001940.step1.return.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step1.return.json>) + [TD-TC-001940.step2.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step2.void.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:1923**: In purchase reversal/void or time-out reversal, Sequence Number must match original transaction request. Source rule **BR-402-3**, PDF page **402**; category **Lifecycle**; producer BR confidence **85%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2248"`.
- Original name: Flow — Return followed by Void of return (Financial Transaction Request → Purchase Reversal) [maximal-multi-brand]
- Flow ID: `FLOW-RETURN-VOID`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Return followed by Void of return (Financial Transaction Request → Purchase Reversal) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": false,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-RETURN-VOID",
  "flow_steps": [
    {
      "step": 1,
      "role": "return",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "7"
      }
    },
    {
      "step": 2,
      "role": "void",
      "transaction": "Purchase Reversal",
      "fixed_elements": {
        "Prompt Code.TransactionType": "U"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-3",
          "source_page": null,
          "rule": "The Sequence Number must be the Sequence Number contained in the original transaction.",
          "spec_anchor": {
            "keywords": [
              "purchase reversal void",
              "time-out reversal",
              "must match original transaction request"
            ],
            "element_no": "86"
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 0.8,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-402-3",
  "source_page": null,
  "business_rule_assertion": "The Sequence Number must be the Sequence Number contained in the original transaction.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "4916f69df72b20ed1f06e95661280cfa301ed1a50155c713f30f30b87b89fed2",
  "dedup_key": "c47873bd869210ad9926a57cacbb88aa7cc0f01b68cf9da51c8fae0228b8d3f2",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1923",
      "rule_id": "BR-402-3",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:1923"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2248",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-001940"`; direct scenario reference: `SC-2248`.
- Producer priority: **P2**; producer confidence: **0% / LOW**; completeness: **INCOMPLETE_SPEC_GAP**; negative flag: **false**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | return / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"7"} | [TD-TC-001940.step1.return.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step1.return.json>) | []; No per-step host outcome declared |
| 2 | void / logical: Purchase Reversal; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"U"} | [TD-TC-001940.step2.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step2.void.json>) | [{"element":"Approval Number","from_step":1,"rule":"Required on reversals for matching information to the original\npurchase. If it is included in a Purchase Request, then the\nPurchase Request is considered preauthorized, and BUYPASS\ndoes not go out for authorization. In the case of an Authorization Only Reversal, this element-\npresent in the original transaction-must be present and identical\nin the Authorization Only Reversal transaction. (Note:","source_rule_id":"ENT-ELEM-5"}]; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-3 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Purchase Reversal' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "return",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type '7' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "void",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC",
            "SPEC_RESPONSE_CODE_UNRESOLVED: transaction type 'U' has no Appendix G meaning"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase Reversal' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "INCOMPLETE_SPEC_GAP",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '7'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = 'U'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '7'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = 'U'",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — The Sequence Number must be the Sequence Number contained in the original transaction.",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: 'Approval Number' comes from step 1's RESPONSE, which is processor-generated and cannot be derived from the spec",
    "FLOW_LINK_NEEDS_RUNTIME_VALUE: Approval Number from step 1 response",
    "FLOW_REQUIRED_FIELD_INCLUDED: this element is the target of a declared flow link — its inclusion is asserted by the flow, not by the generic inclusion profile",
    "FLOW_RESPONSE_SHAPE_FILLED_VIA: format_synthesis — value is provisional, substitute step 1's live response value at execution time",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "RESPONSE_ORIGIN_FIELD_SUPPRESSED: a later flow step carries this element from THIS step's own RESPONSE (processor-generated); the active inclusion profile would otherwise have included it here, which would misrepresent it as terminal-supplied — set it via scenario parameters to test a variant where it IS request-supplied",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: return

- TD JSON: [TD-TC-001940.step1.return.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step1.return.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-001940.step1.return.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-001940.step1.return.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step1.return.meta.json>).
- Identity: `testCaseId=TC-001940`, `scenarioId=SC-2248`, `step=1`, `role=return`, `card_brand=MC` - checked.
- Payload SHA-256: `c3c6769a520512ea3d72369e521f7dbdbb33178c996b71f0d3d8127f40c8517c`.
- Metadata SHA-256: `0f2d6639385fb84c1fa3e00e7421abbc5ab561877c25a8a73c66be2ffb7de769`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "DESY593766543367973726",
      "PromptCode": {
        "TransactionType": "7",
        "CardType": "020"
      },
      "AccountNumber": "5160587644093560",
      "CardDiscretionaryBlockData": "0632",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: void

- TD JSON: [TD-TC-001940.step2.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step2.void.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-001940.step2.void.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-001940.step2.void.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-001940.step2.void.meta.json>).
- Identity: `testCaseId=TC-001940`, `scenarioId=SC-2248`, `step=2`, `role=void`, `card_brand=MC` - checked.
- Payload SHA-256: `3025b2afb20b1412eef2de0243ada7d606a62aa1b40c82cab6f0cfdb5c88264d`.
- Metadata SHA-256: `e258c776cdbc63165b4310118ce6ca1d28164965f61787bea9efddc46c5a1285`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "CFQA412784598982082869",
      "PromptCode": {
        "TransactionType": "U",
        "CardType": "020"
      },
      "AccountNumber": "5160587644093560",
      "CardDiscretionaryBlockData": "0632",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "ITROD9",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-9"></a>
### 9. Wrong reversal transaction code - negative - TC-002054

**Why selected:** Deliberately reverse Authorization Only with code 8 instead of S; retain the mutation and require an approved host oracle.

**Trace:** `REQ-SRC-ATL105-PDF-001:1923` **>** `SC-2362` **>** `TC-002054` **>** [TD-TC-002054.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step1.authorization.json>) + [TD-TC-002054.step2.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step2.void.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:1923**: In purchase reversal/void or time-out reversal, Sequence Number must match original transaction request. Source rule **BR-402-3**, PDF page **402**; category **Lifecycle**; producer BR confidence **85%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2362"`.
- Original name: Flow — Authorization reversed with the wrong Transaction Type code (negative) (Financial Transaction Request → Purchase Reversal) [maximal-multi-brand]
- Flow ID: `FLOW-AUTH-VOID`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Authorization reversed with the wrong Transaction Type code (negative) (Financial Transaction Request → Purchase Reversal) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": true,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-AUTH-VOID",
  "flow_steps": [
    {
      "step": 1,
      "role": "authorization",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "3"
      }
    },
    {
      "step": 2,
      "role": "void",
      "transaction": "Purchase Reversal",
      "fixed_elements": {
        "Prompt Code.TransactionType": "8"
      },
      "links": [
        {
          "type": "carry_request_field",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-402-3",
          "source_page": null,
          "rule": "The Sequence Number must be the Sequence Number contained in the original transaction.",
          "spec_anchor": {
            "keywords": [
              "purchase reversal void",
              "time-out reversal",
              "must match original transaction request"
            ],
            "element_no": "86"
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 0.5,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-402-3",
  "source_page": null,
  "business_rule_assertion": "The Sequence Number must be the Sequence Number contained in the original transaction.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "VARIANT_NOTE: Reverses an Authorization Only (code 3) with Transaction Type code '8' instead of the spec-required code 'S' (§10.1.2.2 p.83) — a correct system should reject or flag this combination; the specific decline behavior is BUYPASS processor logic, not documented in ATL105, so the outcome is not asserted.",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "666ff8a8dedd8d08cf8b74ad65c87746d50d0939c5fbb4f392a6716028fa2d25",
  "dedup_key": "d38318799581f2c437d286a7bf7ceac419d1bfb47a0e0406760ea70fbc2e707d",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands (spec-contradicting code combination)",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:1923",
      "rule_id": "BR-402-3",
      "basis": "flow_link",
      "link_type": "carry_request_field",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:1923"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2362",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-002054"`; direct scenario reference: `SC-2362`.
- Producer priority: **P1**; producer confidence: **0% / LOW**; completeness: **DELIBERATE_OMISSION**; negative flag: **true**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | authorization / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"3"} | [TD-TC-002054.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step1.authorization.json>) | []; No per-step host outcome declared |
| 2 | void / logical: Purchase Reversal; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"8"} | [TD-TC-002054.step2.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step2.void.json>) | []; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / carry_request_field | `["000001"]` | `["000001"]` | EQUALITY_PRESENT | BR-402-3 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Purchase Reversal' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "authorization",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC: per-step flow outcome is processor business logic, not documented in ATL105"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "void",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC: per-step flow outcome is processor business logic, not documented in ATL105"
          ]
        },
        "response_message": {
          "available": false,
          "response_transaction_type": null,
          "flags": [
            "NO_RESPONSE_TEMPLATE: no paired response template for 'Purchase Reversal' in the assembled catalog; expected response message cannot be constructed"
          ],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "DELIBERATE_OMISSION",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "VARIANT_NOTE: Reverses an Authorization Only (code 3) with Transaction Type code '8' instead of the spec-required code 'S' (§10.1.2.2 p.83) — a correct system should reject or flag this combination; the specific decline behavior is BUYPASS processor logic, not documented in ATL105, so the outcome is not asserted.",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '8'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '3'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '8'",
    "FLOW_LINK: carried 'Sequence Number' from step 1 — The Sequence Number must be the Sequence Number contained in the original transaction.",
    "FLOW_LINK_APPLIED: Sequence Number carried from step 1",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "REJECTED_FINAL",
  "verification_reasons": [
    "VACUOUS_NEGATIVE_TEST: scenario is negative but the composed request carries no deliberate_violation_* field — the violation this test claims to exercise never reached the message"
  ],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: authorization

- TD JSON: [TD-TC-002054.step1.authorization.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step1.authorization.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002054.step1.authorization.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002054.step1.authorization.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step1.authorization.meta.json>).
- Identity: `testCaseId=TC-002054`, `scenarioId=SC-2362`, `step=1`, `role=authorization`, `card_brand=MC` - checked.
- Payload SHA-256: `b352ba5887a31dcf3b8f9eb653370176671cd3947e9b58850a1025a500760c01`.
- Metadata SHA-256: `5bc47a8a1dd1c0598e94445fe6655865d6fa39eb6b79e9ec020996116ce528a2`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "ZLLD748392487080582330",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5130332534652689",
      "CardDiscretionaryBlockData": "0229",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: void

- TD JSON: [TD-TC-002054.step2.void.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step2.void.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002054.step2.void.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002054.step2.void.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002054.step2.void.meta.json>).
- Identity: `testCaseId=TC-002054`, `scenarioId=SC-2362`, `step=2`, `role=void`, `card_brand=MC` - checked.
- Payload SHA-256: `7af0b86ba5e5289ed49107105fbfc91d88cec3c766ce11934989f178be84aa45`.
- Metadata SHA-256: `a7911ef08ddca88690797047ca2fb88bd6fa7ca2ed373fd8352e4dbb17b78bdf`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "WKVI968827646795613332",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5130332534652689",
      "CardDiscretionaryBlockData": "0229",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

<a id="case-10"></a>
### 10. Duplicate independent Sequence Number - negative - TC-002329

**Why selected:** Deliberately reuse a Sequence Number across independent transactions to exercise the new-sequence rule.

**Trace:** `REQ-SRC-ATL105-PDF-001:294` **>** `SC-2637` **>** `TC-002329` **>** [TD-TC-002329.step1.first-independent-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step1.first-independent-transaction.json>) + [TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.json>)

#### BR - requirement and source
- **REQ-SRC-ATL105-PDF-001:294**: Each financial transaction request forwarded to BUYPASS should be assigned a new Sequence Number. Source rule **BR-38-10**, PDF page **38**; category **Lifecycle**; producer BR confidence **90%**.

#### TS - scenario
- Catalog: [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>); exact selector: `scenarios[].id = "SC-2637"`.
- Original name: Flow — Two independent transactions forced to share a Sequence Number (negative) (Financial Transaction Request → Financial Transaction Request) [maximal-multi-brand]
- Flow ID: `FLOW-DUPLICATE-SEQUENCE-NUMBER-VIOLATION`; variant: `maximal-multi-brand`; axes: `{"card_brand":"MC","entry_mode":"01","field_profile":"maximal","pin_entry_capability":"2"}`.
- Producer scenario confidence: **50% / MEDIUM**; oracle authority: config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec

<details><summary>Full original scenario JSON, including links, parameters and caveats</summary>

```json
{
  "name": "Flow — Two independent transactions forced to share a Sequence Number (negative) (Financial Transaction Request → Financial Transaction Request) [maximal-multi-brand]",
  "requirement_id": null,
  "type": "flow",
  "is_negative": true,
  "parameters": {
    "Point-of-Service Entry Mode": "01",
    "Terminal PIN Entry Capability": "2"
  },
  "table_placements": [
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        1,
        2
      ],
      "table_data_length": 3,
      "axis": "entry_mode",
      "value": "01",
      "parameter": "Point-of-Service Entry Mode"
    },
    {
      "segment": "111",
      "table_id": "005",
      "table_entity": "ENT-TABLE-I-005",
      "source_pages": [
        548
      ],
      "positions": [
        3,
        3
      ],
      "table_data_length": 3,
      "axis": "pin_entry_capability",
      "value": "2",
      "parameter": "Terminal PIN Entry Capability"
    }
  ],
  "flow_id": "FLOW-DUPLICATE-SEQUENCE-NUMBER-VIOLATION",
  "flow_steps": [
    {
      "step": 1,
      "role": "first_independent_transaction",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      }
    },
    {
      "step": 2,
      "role": "second_independent_transaction_duplicate_sequence",
      "transaction": "Financial Transaction Request",
      "fixed_elements": {
        "Prompt Code.TransactionType": "0"
      },
      "links": [
        {
          "type": "duplicate_sequence_violation",
          "from_step": 1,
          "element_name": "Sequence Number",
          "source_rule_id": "BR-38-10",
          "source_page": null,
          "rule": "Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number by the customer's host/controller/POS system.",
          "spec_anchor": {
            "keywords": [
              "new sequence number",
              "forwarded to buypass"
            ],
            "element_no": "86",
            "page_hint": 38
          }
        }
      ]
    }
  ],
  "flow_risk_weight": 0.7,
  "target_transaction": "Financial Transaction Request",
  "related_entity_ids": [],
  "source_rule_id": "BR-38-10",
  "source_page": null,
  "business_rule_assertion": "Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number by the customer's host/controller/POS system.",
  "expected_response_code": null,
  "expected_decline_code": null,
  "oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec",
  "flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "source": "auto_generated_flow",
  "fingerprint": "2960d6c3179002177dd4d37163f750639cbf3479ea28ed895e413738d176c6f4",
  "dedup_key": "1013b842ab884f3a997f5b84a8b8a7d602d8def403610707c66adb08916702e3",
  "inclusion_profile": "maximal",
  "flow_variant_id": "maximal-multi-brand",
  "flow_variant_name": "Maximal profile across card brands",
  "flow_axis_values": {
    "card_brand": "MC",
    "entry_mode": "01",
    "field_profile": "maximal",
    "pin_entry_capability": "2"
  },
  "flow_axis_provenance": {
    "card_brand": {
      "source": "test_axes",
      "applied_as": "card_profile"
    },
    "entry_mode": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Point-of-Service Entry Mode"
    },
    "field_profile": {
      "source": "field_inclusion_profiles",
      "applied_as": "inclusion_profile"
    },
    "pin_entry_capability": {
      "source": "test_axes",
      "applied_as": "table_placement",
      "element_name": "Terminal PIN Entry Capability"
    }
  },
  "requirement_links": [
    {
      "requirement_id": "REQ-SRC-ATL105-PDF-001:294",
      "rule_id": "BR-38-10",
      "basis": "flow_link",
      "link_type": "duplicate_sequence_violation",
      "step": 2,
      "element_name": "Sequence Number",
      "confidence_band": "HIGH"
    }
  ],
  "requirement_ids": [
    "REQ-SRC-ATL105-PDF-001:294"
  ],
  "axis_requirement_ids": [],
  "id": "SC-2637",
  "spec_id": "SRC-ATL105-PDF-001",
  "spec_version": "2026-3",
  "scope_name": null,
  "scope_segments": null,
  "scope_mode": null,
  "confidence_pct": 50,
  "confidence_band": "MEDIUM",
  "confidence_factors": [
    "unscored_default=50"
  ]
}
```

</details>

#### TC - procedure, expectations and readiness
- Catalog: [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>); exact selector: `test_cases[].id = "TC-002329"`; direct scenario reference: `SC-2637`.
- Producer priority: **P1**; producer confidence: **0% / LOW**; completeness: **DELIBERATE_OMISSION**; negative flag: **true**.
- Preconditions for eventual execution: isolated processor-approved test environment; approved Mastercard test account/profile; approved per-step oracle; valid runtime terminal/sequence provisioning; capture response fields needed by later steps. Existing JSON is a generated fixture, not an approved production message.
- Procedure below describes the existing flow. It was not executed as part of this document; apply the exact scenario inter-step rules, not an assumed approval/decline code.

| Step | Role / message | Declared fixed elements | JSON data | Runtime substitutions / outcome note |
|---:|---|---|---|---|
| 1 | first_independent_transaction / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-002329.step1.first-independent-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step1.first-independent-transaction.json>) | []; No per-step host outcome declared |
| 2 | second_independent_transaction_duplicate_sequence / logical: Financial Transaction Request; physical: Financial Transaction Request | {"Prompt Code.TransactionType":"0"} | [TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.json>) | []; No per-step host outcome declared |

**Fixture-level relationship inspection (not host execution):**

| From > to | Field / relation | Source fixture value(s) | Target fixture value(s) | Observation | Provenance |
|---|---|---|---|---|---|
| 1 > 2 | Sequence Number / duplicate_sequence_violation | `["000001"]` | `["000001"]` | INTENTIONAL_DUPLICATE_PRESENT | BR-38-10 |

`RUNTIME_RESPONSE_REQUIRED` compares no host response: the shown source is only the source-step request fixture. `EQUAL_BUT_EMPTY` is not a valid populated matching key. `FIELD_ABSENT_OR_AMBIGUOUS` blocks the corresponding fixture check. Links marked `unresolved_kb_gap` or QE feedback are not promoted to source-approved BRs.

**Expected result / oracle: producer structural assertions, response/decline codes and response-template status (original response template segment trees are not duplicated):**
```json
{
  "scenario_oracle_hint": {
    "response_code": null,
    "decline_code": null
  },
  "expected_response": {
    "structural": {
      "tier": "INFER",
      "assertions": [
        "response message is well-formed per spec segment structure",
        "every field satisfies its documented character-type and length"
      ],
      "grounding": "steps 1-2 extracted constraints"
    },
    "response_code": {
      "tier": "FLAG",
      "field": "ResponseCode",
      "value": null,
      "flags": [
        "NEEDS_AUTHORIZER_DOC",
        "SPEC_RESPONSE_CODE_UNRESOLVED: no response code rule for scenario type 'flow'"
      ]
    },
    "decline_code": {
      "tier": "INFER",
      "field": "DeclineCode",
      "value": null,
      "note": "positive scenario — no decline expected"
    },
    "response_message": {
      "available": false,
      "response_transaction_type": null,
      "flags": [
        "NO_RESPONSE_TEMPLATE: no paired response template for 'Financial Transaction Request → Financial Transaction Request' in the assembled catalog; expected response message cannot be constructed"
      ],
      "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
    }
  },
  "step_expected_responses": [
    {
      "step": 1,
      "role": "first_independent_transaction",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC: per-step flow outcome is processor business logic, not documented in ATL105"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    },
    {
      "step": 2,
      "role": "second_independent_transaction_duplicate_sequence",
      "expected_response": {
        "structural": {
          "tier": "INFER",
          "assertions": [
            "response message is well-formed per spec segment structure",
            "every field satisfies its documented character-type and length"
          ],
          "grounding": "steps 1-2 extracted constraints"
        },
        "response_code": {
          "tier": "FLAG",
          "field": "ResponseCode",
          "value": null,
          "flags": [
            "NEEDS_AUTHORIZER_DOC: per-step flow outcome is processor business logic, not documented in ATL105"
          ]
        },
        "response_message": {
          "available": true,
          "response_transaction_type": "Financial Transaction Response",
          "flags": [],
          "template_detail": "Original response template segment trees, if any, remain in the linked TC catalog; not duplicated here."
        }
      },
      "expected_outcome_note": null
    }
  ]
}
```

**Readiness findings and full producer flags:**
```json
{
  "completeness_status": "DELIBERATE_OMISSION",
  "completeness_findings": [
    {
      "tier": "T2",
      "kind": "UNVERIFIED_CONDITIONAL_INCLUSION",
      "detail": "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated"
    }
  ],
  "scenario_flags": [
    "NEEDS_AUTHORIZER_DOC: per-step flow outcome requires processor business rules",
    "LLM_JUDGE_NOT_APPLIED: built from config/transaction_flows.json declared chain data, not spec prose; no free-text claim to verify"
  ],
  "test_case_flags": [
    "AMBIGUOUS_LENGTH: character_type and representation state different lengths [28, 56] — left unresolved",
    "AMBIGUOUS_LENGTH: max_length_raw has 2 candidate numbers [4, 14], using max_length_bytes=4",
    "AMBIGUOUS_LENGTH: max_length_raw has 5 candidate numbers [24, 25, 50, 105, 425], using max_length_bytes=24",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 2' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 3' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 4' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Fleet Tag 5' has no client-supplied value",
    "AWAITING_CLIENT_VALUE: library entry for 'Market Basket Data' has no client-supplied value",
    "AXIS_TABLE_PLACEMENT: Table 005 positions [1, 2]='01' (entry_mode), positions [3, 3]='2' (pin_entry_capability)",
    "CARD_EXPIRY_PER_ELEMENT_12: the test case's own card expiry (MMYY) — element 12 (p.371-372): 'For all authorizations, merchants must send a value even for expired cards'",
    "CONDITIONAL_FIELD_INCLUDED_UNVERIFIED: populated by the active inclusion profile; its governing condition was not evaluated",
    "CONDITIONAL_FIELD_OMITTED",
    "CONSTRAINT_RULE_UNDEFINED: element '2' ('Account Number') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=24 is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '202' ('Cryptogr am Token Data') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=None is one arbitrary catalog pick, not length-verifiable",
    "CONSTRAINT_RULE_UNDEFINED: element '78' ('Prompt Code') has no DEFINED length rule (UNRESOLVED_KB_GAP) — see docs/client-inputs/CLIENT_INPUT_REGISTER.md; max_length=4 is one arbitrary catalog pick, not length-verifiable",
    "DELIBERATE_CONSTRAINT_VIOLATION: 'Sequence Number' duplicates step 1's value; Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number by the customer's host/controller/POS system.",
    "DERIVED_AT_WIRE_ENCODING: 'EBT Program Data''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segme nt Length Indicat or''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length Indicator''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "DERIVED_AT_WIRE_ENCODING: 'Segment Length''s own spec text defines its value as computed from other rendered content (e.g. segment/element lengths), never synthesized",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Alternate MICR IND' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "ELEMENT_NO_COLLISION: attribute catalog's element_no 239 record is 'Enhanced Fleet Data', not this template field's own name 'Networ k Token Data' (ATL105 reuses element numbers per-segment) — attribute not trusted for this field",
    "FLOW_FIXED_ELEMENT: Prompt Code.TransactionType = '0'",
    "FLOW_FIXED_ELEMENT_APPLIED: Prompt Code.TransactionType = '0'",
    "FLOW_LINK_APPLIED: Sequence Number deliberately duplicated from step 1",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 11 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 20 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 200 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 4 taken from this element's own character_type Length cell text",
    "LENGTH_RECOVERED_FROM_SPEC_TEXT: max_length_raw/max_length_bytes empty; length 9 taken from this element's own character_type Length cell text",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562)",
    "MANDATORY_VARIABLE_INFORMATION_TABLE: Table 031 'must be populated for all transactions' (p562); spec 'unspecified' code from ENT-TABLE-I-031 p562 — replace with the client's real value via VarInfoTable[031]",
    "NOT_FILLED_BY_INCLUSION_PROFILE: field_inclusion_profiles.json never_filled_by_profile — only a scenario parameter or a flow link may supply this element (see that list's page-cited grounding, e.g. element 5 p.368, element 146 p.239)",
    "PROMPT_CODE_CARD_TYPE_PER_APPENDIX_E: positions 2-4 = 020 (card brand 'mc'; element 78 p.400-401, Appendix E p.521-522); position 1 = the composed Transaction Type",
    "QE_EXAMPLE_CONDITIONALLY_SCOPED_SKIPPED: 'Account Number'/'TOTALS REQUEST' is only documented for a specific precondition (\"Loyalty card Totals Request (Update Code = 'T'): value\") this test case's context cannot verify — skipped rather than misapplied as a universal value",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Information Byte' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_SUPERSEDED_BY_USER_VALUE: 'Partial Approval Indicator' has QE example value(s); the client-entered value is used instead",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Account Number''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Loyalty Program ID''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-from Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Ship-to Postal Code''s documented QE example values passed character_type/length validation",
    "QE_EXAMPLE_VALUES_REJECTED: none of 'Terminal Identifier''s documented QE example values passed character_type/length validation",
    "UNKNOWN: no character_type/valid_values available for this field",
    "UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped (heuristically/unstructured-parsed from narrative text)"
  ],
  "verification_status": "PASS",
  "verification_reasons": [],
  "verification_advisories": [
    "CONSTRAINT_NOT_VERIFIABLE: step1.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified",
    "CONSTRAINT_NOT_VERIFIABLE: step2.Standard Message Data Segment.Account Number (element 2) has no DEFINED length rule (UNRESOLVED_KB_GAP) — conformance was not verified"
  ],
  "grounding": {
    "structure_source": "assembled/output/atl105_complete_templates.json message_templates",
    "value_source": "test_generation/value_fill.py priority order + flow link rules",
    "scenario_oracle_authority": "config/transaction_flows.json declared flow; inter-step linking rules are spec-cited, but per-step approval/decline outcome is processor logic absent from the ATL105 spec"
  }
}
```

**Acceptance boundary:** the linked BR relationships and declared negative mutation can be reviewed against the fixture observations. Approval, decline, reversal effect, settlement effect, idempotency and cryptographic validity remain unverified until an approved processor oracle and actual execution evidence exist. Producer verification PASS does not override completeness findings.

#### TD - original per-step JSON payloads and sidecars

Payloads below are the complete parsed original fixture objects, not redesigned examples. Metadata identity and hashes are recorded for each leg. Preserve runtime substitutions before any eventual test execution; do not run these fixtures against production.

##### Step 1: first_independent_transaction

- TD JSON: [TD-TC-002329.step1.first-independent-transaction.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step1.first-independent-transaction.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002329.step1.first-independent-transaction.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002329.step1.first-independent-transaction.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step1.first-independent-transaction.meta.json>).
- Identity: `testCaseId=TC-002329`, `scenarioId=SC-2637`, `step=1`, `role=first_independent_transaction`, `card_brand=MC` - checked.
- Payload SHA-256: `be659970c2f21eef420fb92c0c62123f6c87e1126716115b265536777cfeddcb`.
- Metadata SHA-256: `161fe083886be2dd83ed07077299e261127ce8a28f15b30f9b650d5873949995`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "MELF101316801734586710",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5179107442536428",
      "CardDiscretionaryBlockData": "0731",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

##### Step 2: second_independent_transaction_duplicate_sequence

- TD JSON: [TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.json>).
- Original catalog declaration: `qe_shaped_test_data/TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.json`.
- Resolution: the immutable delivery nests the declared `qe_shaped_test_data` path under an additional `qe_shaped_test_data` directory; the link above points to the existing resolved file.
- Metadata: [TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.meta.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/qe_shaped_test_data/qe_shaped_test_data/TD-TC-002329.step2.second-independent-transaction-duplicate-sequence.meta.json>).
- Identity: `testCaseId=TC-002329`, `scenarioId=SC-2637`, `step=2`, `role=second_independent_transaction_duplicate_sequence`, `card_brand=MC` - checked.
- Payload SHA-256: `7c5eccfa522a67cda94220bbd0f8816b43299c0b67f634a0dcc2102057bdcc0b`.
- Metadata SHA-256: `b5ca1c0651614305f250825b3fae8dcec77dd5acc04c6b4b67a9f6690c75677e`.

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "094",
      "InformationByte": "0",
      "TerminalIdentifier": "LJRB084744180109316901",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5179107442536428",
      "CardDiscretionaryBlockData": "0731",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "000001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "012"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

## 5. Known limitations and review actions

1. None of these ten cases is represented as ready to execute. Review conditional-field applicability, placeholder/malformed values, length ambiguities, unresolved response templates and all flags above.
2. The producer uses a maximal multi-brand profile. An MC axis or MC sidecar is classification evidence, not independent validation of every card-brand-specific field, account number, cryptogram or PIN value.
3. Obtain the BUYPASS/processor oracle for success, decline, duplicate-completion handling, wrong reversal code and duplicate-sequence behavior. No response code was invented.
4. Bind Approval Number and any other response-derived field from the actual prior-step response. Empty static values and missing fields must not be treated as completed response relationships.
5. EMV data requires its own payload semantic review. Presence of Segment 130 is not cryptographic validation of EMV tags, lengths, MACs or issuer behavior. Loyalty reversal TC-002235 was not selected: its Unit of Work relationship cannot be checked from the inspected fixture.
6. The wrong-reversal-code case traces Sequence Number to its explicit BR. Its additional negative transaction-code claim is retained in the scenario flags with the original source reference, not silently assigned to that sequence BR.
7. Duplicate completion is producer-labelled is_negative=false even though it explores duplicate handling; this label is preserved. Its host behavior remains intentionally unspecified.
8. Runtime flow rules marked QE feedback / unresolved_kb_gap remain supplemental unapproved claims, separate from the 7 catalog BRs.
9. Generic Mastercard authentication/UCAF/DSRP and fleet requirements from the earlier wording-based BR extract are not covered by this lifecycle shortlist. This is a top-ten selection, not complete Mastercard functional coverage.

## 6. Validation evidence

- Catalog scan: all 11,356 TS records and all 15,625 TC records were parsed record-by-record; 122 explicit MC-tagged TCs retained. This avoids reading the 1.17 GB TC catalog into one string.
- Shortlist: exactly ten unique TC IDs, ten unique TS IDs and ten distinct flow IDs.
- All 7 BR IDs resolve uniquely in the source BR catalog; TC and TS BR-ID sets reconcile for each selected case.
- All 23 TD payloads and 23 sidecars parse as JSON; each sidecar identifies the exact TC, TS, step, role and MC brand. Every TC step has exactly one distinct TD leg.
- Per-step JSON root agrees with the TC physical_transaction_type; the sidecar messageFamily agrees with the logical transaction_type. Purchase/Capture, Purchase Reversal and Time-out Reversal use the declared Financial Transaction Request physical envelope, rather than a JSON root named after the logical operation.
- Inter-step field observations are recorded above, including unresolved/empty/runtime cases rather than hidden failures.
- Every relative file link in this document was checked for existence; all embedded JSON blocks were parsed and all TD blocks were compared to their original payload objects.
- Independent report read-back: all 54 JSON blocks parse; all 23 embedded TD payload objects exactly match their original JSON files; all 23 per-step PromptCode.TransactionType values match the scenario fixed elements. The shortlist contains exactly two deliberately negative cases.
- Application startup, wire serialization, host/processor execution, issuer response, and certification: **NOT RUN / NOT ESTABLISHED**.

## 7. Source files

- [requirement_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/Requirement candidates/requirement_candidates.json>).
- [approved_scenarios.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/scenarios/scenarios/approved/approved_scenarios.json>).
- [test_case_candidates.json](<../../test-input/ai-solution/runs/2026-10-19/Run3/test_case_candidates.json>).
- [br-assessment.jsonl](<2026-10-19-181026-run-delivery-wide-assessment-v4/br-assessment.jsonl>).
- [complete-all-test-solution-br-ts-tc-td-package.json](<../test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json>).
- [mastercard-transaction-links.csv](<2026-10-19-mastercard-transaction-links-v1/mastercard-transaction-links.csv>).
