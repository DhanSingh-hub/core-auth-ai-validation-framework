# Test Solution Improvements Roadmap

> **ARCHIVED 2026-09-29.** Superseded by the [ATL105 Segment Training Handbook](../../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md). Kept for history only; status figures and plans are a 2026-09-23 snapshot.
## Enabling Independent Review and Coverage Calculation of AI Solution

**Status**: STRATEGIC PLAN
**Date**: 2026-09-23
**Purpose**: Define Test Solution artifacts, validators, and processes required to independently verify AI claims and calculate coverage against ATL105 specification

---

## Executive Summary

The AI Solution (Run2) contains **6,473 BRs, 11,196 scenarios, 13,082 TCs, and 26,261 test payloads**. However, **only 30.3% are fully traced** (BR→Scenario→TC→TD), **32.3% lack test data**, and **4 validation gates are not ready**.

To become the independent oracle for validating these claims, the **Test Solution must**:

1. **Complete independent knowledge models** for all 49 segments (currently 10/49 substantive)
2. **Establish canonical sourceAnchors** as the comparison baseline
3. **Build segment-specific validators** to ensure payloads comply with ATL105 rules
4. **Create the coverage denominator** (which requirements count toward completion?)
5. **Operationalize traceability matching** (how to link AI BR→TC→TD to Test BR→TC→TD)
6. **Formalize readiness gates** so we know when each segment is ready for AI comparison

---

## Current State Analysis

### Test Solution Readiness by Segment (10 Substantive / 49 Total)

| Segment | Status | Next Gate | Critical Gap |
|---------|--------|-----------|--------------|
| 100 | IN_PROGRESS | AI_INTAKE | Has rule catalog; needs anchor completion |
| 101 | IN_PROGRESS | SME_REVIEW | Fleet card applicability blockers |
| 102 | IN_PROGRESS | AI_INTAKE | Placeholder rules; needs SME refinement |
| 103-104 | IN_PROGRESS | AI_INTAKE | Minimal rule coverage |
| 105,108-109,111,113 | IN_PROGRESS | AI_INTAKE | Placeholder status; TBD evidence |
| 110,112,114-157,DL1-8 | NOT_STARTED | SOURCE_INVENTORY | Zero Test Solution artifacts |

### AI Solution Run2 Coverage Profile

- **Segments with AI output**: 49 (100-157 + DL1-8)
- **Fully traced** (BR→SC→TC→TD): 1,964 (30.3%)
- **Missing test data**: 4,226 TCs (32.3%)
- **Unresolved scenarios**: 102
- **Orphan scenarios**: 102
- **Anchor-only scenarios**: 2,766 (needs reconstruction)

### Gap: Test Solution Artifacts Missing

| Artifact Type | Purpose | Current State | Required for AI Review |
|---|---|---|---|
| **Canonical Anchor Catalog** | Define sourceAnchor URIs for all segment rules | Partial (seg-100 only) | CRITICAL |
| **Independent BR Derivation** | Derive requirements from spec without copying AI | 30% complete | CRITICAL |
| **Scenario Validation Rules** | Define which scenarios apply to which segments | ~50% documented | CRITICAL |
| **TC Structure Validator** | Ensure given/when/then/expected format | Ad-hoc markdown only | HIGH |
| **Payload Serialization Validator** | Verify JSON matches ATL105 wire format rules | None exist | HIGH |
| **Mutation Test Evidence** | Prove deliberate violations trigger correct errors | Spot-checked only | MEDIUM |
| **Coverage Baseline Matrix** | Define BR→SC→TC→TD chains independent of AI | None exist | CRITICAL |
| **Test Data Fixture Registry** | Track which TCs have manual fixtures vs generated | None exist | MEDIUM |
| **Readiness Gate Checklist** | Define what "Test Solution ready" means per segment | Template exists; unchecked | HIGH |

---

## Critical Improvements Needed (Sequenced)

### **PHASE 1: Establish Canonical Anchor Baseline (Weeks 1-2)**

**Goal**: Create the single source of truth for Test Solution requirements, independent of AI output.

#### 1.1 Complete Canonical Anchor Catalog for All 49 Segments
- **What**: Extend `segment-100-canonical-anchors.md` to cover segments 101-157 + DL1-8
- **Format**: sourceAnchor URI pattern: `atl105|2026-3|section|segment|element|rule`
- **Examples**:
  ```
  atl105|2026-3|section2|100|field-account-number|rule-alpha-chars-only
  atl105|2026-3|section2|101|field-fleet-id|rule-conditional-on-card-type
  atl105|2026-3|section3|102|field-merchant-category-code|rule-4-digit-numeric
  ```
- **Deliverables**:
  - `/specifications/ATL105/docs/specs/kb/segment-*/segment-NNN-canonical-anchors.md` (49 files)
  - `/specifications/ATL105/docs/specs/kb/canonical-anchor-registry.json` (machine-readable index)
- **Validation**: Each anchor must be traceable to ATL105 2026-3 specification section + paragraph
- **Effort**: ~2-4 hours per segment (40-60 hours total)
- **Blocks**: All downstream comparison work; must complete before AI review

#### 1.2 Create Independent BR Derivation Index
- **What**: For each segment, catalog all business requirements extracted directly from spec (no copying from AI)
- **Format**: `segment-NNN-independent-br-derivation.json`
  ```json
  {
    "segment": 100,
    "specVersion": "2026-3",
    "requirements": [
      {
        "brId": "100-BR-001",
        "sourceAnchor": "atl105|2026-3|section2|100|field-account-number|rule-alpha-chars-only",
        "derivedFrom": "ATL105 2026-3 Section 2.1.1, paragraph 3",
        "requirement": "Account Number must contain only uppercase A-Z characters",
        "applicableCardinality": "exactly 1",
        "conditionalApplicability": "all transaction types"
      },
      {
        "brId": "100-BR-002",
        "sourceAnchor": "atl105|2026-3|section2|100|field-account-number|rule-length-12",
        "derivedFrom": "ATL105 2026-3 Section 2.1.1, paragraph 4",
        "requirement": "Account Number length must be exactly 12 characters",
        "applicableCardinality": "exactly 1",
        "conditionalApplicability": "all transaction types"
      }
    ],
    "derivationDate": "2026-09-23",
    "reviewer": "Test Team",
    "reviewStatus": "REVIEW_REQUIRED"
  }
  ```
- **Deliverables**:
  - `/specifications/ATL105/docs/specs/kb/segment-*/segment-NNN-independent-br-derivation.json` (49 files)
- **Coverage**: Must derive ≥90% of requirements the AI extracted for each segment
- **Effort**: 6-8 hours per segment (100-150 hours for all)

---

### **PHASE 2: Build Segment-Specific Validators (Weeks 2-4)**

**Goal**: Create automated validators that ensure AI payloads comply with ATL105 rules.

#### 2.1 Test Case Structure Validator
- **What**: Automated validator ensuring all TCs follow expected format
- **Check**:
  ```
  ✓ Given: [prerequisite state or context]
  ✓ When: [action taken]
  ✓ Then: [expected outcome]
  ✓ Expected Result: [specific assertion]
  ✓ Test Data Reference: [link to payload ID]
  ```
- **Implementation**: Python validator class
  ```python
  class TCStructureValidator:
      def validate(self, tc_markdown: str) -> ValidationResult:
          # Check for required sections
          # Verify logic flow (given→when→then)
          # Ensure test data link exists
          # Classify as VALID, VALID_WITH_WARNINGS, or INVALID
  ```
- **Deliverables**:
  - `/regression-artifact-validator/src/main/java/com/coreauth/validator/TCStructureValidator.java`
  - Validation report: `test-output/tc-structure-validation-report.json`
- **Apply to**: Run2's 13,082 TCs
- **Effort**: 40 hours (validator design + testing)

#### 2.2 Payload Serialization Validator (Per-Segment)
- **What**: Validate each test data JSON payload against segment rules
- **Examples**:
  - **Segment 100**: Account Number field → must be 12 A-Z chars
  - **Segment 101**: Fleet ID field → conditional on card type
  - **Segment 102**: Merchant Category Code → must be 4 numeric digits
- **Implementation**: Rule-based validator per segment
  ```python
  class Segment100PayloadValidator:
      RULES = {
          'account_number': {
              'type': 'string',
              'pattern': '^[A-Z]{12}$',
              'required': True,
              'sourceAnchor': 'atl105|2026-3|section2|100|field-account-number|rule-alpha-chars-only'
          },
          # ... 15+ fields per segment
      }

      def validate(self, payload: dict) -> ValidationResult:
          # Check each field against rules
          # Track which rules are triggered
          # Report violations
  ```
- **Deliverables**:
  - `Segment{NNN}PayloadValidator.java` (49 validators)
  - Per-segment validation rules file: `segment-NNN-payload-validation-rules.json`
  - Batch validation report: `test-output/payload-validation-report.json`
- **Apply to**: Run2's 26,261 payloads
- **Effort**: 4-6 hours per segment (150-200 hours total)

#### 2.3 Mutation Test Validator
- **What**: Verify that TCs with deliberately-violated rules catch the violations
- **Example**: If test data has Account Number = "123456789ABC" (numeric violation), the TC must fail
- **Implementation**:
  ```python
  class MutationTestValidator:
      def validate_mutation_evidence(self, tc_id: str, payload: dict, mutation: dict) -> MutationResult:
          # Apply mutation to payload
          # Run validation rules
          # Verify expected rule violation occurs
          # Classify as CONFIRMED_CATCH, MISSED_CATCH, or FALSE_POSITIVE
  ```
- **Deliverables**:
  - `MutationTestValidator.java`
  - Mutation report: `test-output/mutation-test-evidence-report.json`
- **Apply to**: TCs marked as "negative test" or "error scenario"
- **Effort**: 30 hours

---

### **PHASE 3: Build Coverage Comparison Framework (Weeks 4-6)**

**Goal**: Enable Test Solution to independently compute coverage and compare to AI claims.

#### 3.1 Create Coverage Denominator Definition
- **What**: Formally define which BRs count toward "complete" coverage per segment
- **Format**:
  ```json
  {
    "segment": 100,
    "coverageDefinition": {
      "inScope": [
        "All field-level format rules",
        "All cardinality rules",
        "All conditional applicability rules",
        "All error/rejection scenarios"
      ],
      "outOfScope": [
        "Performance requirements (response time)",
        "Scalability requirements (concurrent transactions)",
        "External system integration specifics"
      ],
      "exemptedRules": [
        "deprecated field handling (legacy compatibility)"
      ],
      "brRequiredForComplete": 45,
      "scenariosRequiredForComplete": 80,
      "tcRequiredForComplete": 200,
      "tcWithDataRequiredForComplete": 200
    }
  }
  ```
- **Deliverables**:
  - `segment-NNN-coverage-denominator.json` (49 files)
  - Aggregated: `atl105-coverage-denominator-catalog.json`
- **Validation**: Review by Test Lead + Spec Owner
- **Effort**: 6-8 hours per segment (60-80 hours total)

#### 3.2 Build Independent Traceability Matrix
- **What**: Construct BR→Scenario→TC→TD chains independently of AI, then compare
- **Schema**:
  ```json
  {
    "source": "Test Solution",
    "computedAt": "2026-09-23T14:30:00Z",
    "traceability": [
      {
        "brId": "100-BR-001",
        "sourceAnchor": "atl105|2026-3|section2|100|field-account-number|rule-alpha-chars-only",
        "scenarios": [
          {
            "scId": "100-SC-001",
            "description": "Valid account number: 12 uppercase letters",
            "testCases": [
              {
                "tcId": "100-TC-001",
                "hasData": true,
                "dataPayloadId": "segment-100-payload-0001.json",
                "status": "CONFIRMED_INDEPENDENT"
              }
            ]
          }
        ]
      }
    ],
    "coverage": {
      "brsCovered": 45,
      "brsTotal": 45,
      "brsPercentage": 100,
      "scenariosCovered": 80,
      "scenariosTotal": 85,
      "scenariosPercentage": 94,
      "tcsCovered": 200,
      "tcsTotal": 215,
      "tcsPercentage": 93,
      "tcsWithDataCovered": 200,
      "tcsWithDataTotal": 200,
      "tcsWithDataPercentage": 100
    }
  }
  ```
- **Deliverables**:
  - `segment-NNN-independent-traceability-matrix.json` (49 files)
  - Aggregated: `atl105-test-solution-coverage-matrix.json`
  - Diff report: `ai-vs-test-solution-coverage-delta.json`
- **Validation**: Each link must cite sourceAnchor evidence
- **Effort**: 10-15 hours per segment (150-250 hours, can parallelize)

#### 3.3 Create Comparison & Reconciliation Tool
- **What**: Side-by-side comparison of AI claims vs Test Solution facts
- **Outputs**:
  - ✅ **AGREEMENT**: AI BR X matches Test BR X via sourceAnchor
  - ⚠️ **HEURISTIC_MATCH**: AI and Test derived similar requirements but sourced differently
  - ❌ **DIVERGENCE**: AI and Test disagree on requirement scope
  - 🔍 **REVIEW_REQUIRED**: Match uncertain; needs SME decision
- **Implementation**:
  ```python
  class CoverageReconciler:
      def compare(self, ai_traceability: dict, test_traceability: dict) -> ReconciliationReport:
          # Match BRs by sourceAnchor
          # Match scenarios by semantics
          # Match TCs by structure
          # Report agreement %, divergences, gaps
  ```
- **Deliverables**:
  - `CoverageReconciler.java`
  - Report: `ai-test-solution-reconciliation-report.json`
- **Effort**: 40 hours

---

### **PHASE 4: Operationalize Readiness Gates (Weeks 6-8)**

**Goal**: Define and enforce when each segment is ready for AI comparison.

#### 4.1 Segment Readiness Checklist
- **What**: Formalize the 9-gate process for Test Solution certification
- **Gates**:
  1. ✅ **SOURCE_INVENTORY**: Specification sections mapped to segment fields
  2. ✅ **SEGMENT_KNOWLEDGE_MODEL**: All fields, types, cardinality documented
  3. ✅ **CONTEXT_MATRIX**: Segment dependencies, conditional applicability defined
  4. ✅ **INDEPENDENT_BR_DERIVATION**: ≥90% BRs extracted from spec, anchored
  5. ✅ **TC_TEST_DATA_CHAIN**: Representative TCs created, data fixtures built
  6. ✅ **LIFECYCLE_FLOWS**: All business flows documented with decision points
  7. ✅ **SERIALIZATION_MUTATION**: Wire format verified, mutation tests designed
  8. ✅ **AI_ARTIFACT_INTAKE**: Readiness for AI comparison confirmed
  9. ✅ **CONVERTER_EXECUTION_READINESS**: Ready for downstream test execution
- **Deliverables**:
  - `segment-NNN-readiness-gate-checklist.json` (49 files)
  - Template checklist with evidence requirements
  - Batch status dashboard: `test-solution-readiness-status.json`
- **Validation**: Each gate requires documented evidence + approver signature
- **Effort**: 20 hours

#### 4.2 Test Solution Readiness Dashboard
- **What**: Real-time visibility into which segments are ready for AI review
- **Display**:
  ```
  Segment 100 ████████████████ 100% ready (all 9 gates PASS)
  Segment 101 ██████████░░░░░░░  65% ready (gate 3 REVIEW_REQUIRED)
  Segment 102 ████░░░░░░░░░░░░░  20% ready (gates 4-9 not started)
  Segment 103 ░░░░░░░░░░░░░░░░░   0% ready (placeholder only)
  ...
  Overall    ███████░░░░░░░░░░  35% ready (17/49 segments)
  ```
- **Deliverables**:
  - Dashboard JSON: `test-solution-readiness-dashboard.json`
  - HTML report: `test-solution-readiness-report.html`
- **Effort**: 20 hours

---

## Implementation Roadmap

### Priority 1: Unblock AI Review (Weeks 1-2) — 8 people-weeks
- Complete canonical anchors for all 49 segments (1.1)
- Build TC structure validator (2.1)
- Define coverage denominator (3.1)
- **Outcome**: Can compare AI artifacts to Test baseline

### Priority 2: Enable Coverage Calculation (Weeks 2-4) — 12 people-weeks
- Create independent BR derivation index (1.2)
- Build payload serialization validators (2.2)
- Build independent traceability matrix (3.2)
- **Outcome**: Can compute independent coverage vs AI claims

### Priority 3: Formalize Validation (Weeks 4-6) — 8 people-weeks
- Build mutation test validator (2.3)
- Build reconciliation tool (3.3)
- Create readiness checklist (4.1)
- **Outcome**: Can classify matches as CONFIRMED vs REVIEW_REQUIRED

### Priority 4: Operationalize Process (Weeks 6-8) — 4 people-weeks
- Build readiness dashboard (4.2)
- Execute batch validation on Run2 (apply all validators)
- Document lessons learned
- **Outcome**: Repeatable process for future AI runs

---

## Resource Requirements

| Artifact Type | Effort | Owner | Prerequisite |
|---|---|---|---|
| Canonical Anchors (all 49) | 40-60 hrs | Senior Test Architect | ATL105 spec v2026-3 |
| Independent BR Index | 100-150 hrs | Test Team (3-5 people) | Canonical Anchors |
| TC Structure Validator | 40 hrs | QA Engineer | Test TC format standard |
| Payload Validators (49×) | 150-200 hrs | QA Engineers (3 people) | ATL105 field rules |
| Mutation Test Validator | 30 hrs | Test Lead | Mutation design strategy |
| Coverage Denominator (49×) | 60-80 hrs | Test Lead + SMEs | Business Requirements approval |
| Independent Matrix | 150-250 hrs | QA Automation | All validators complete |
| Reconciliation Tool | 40 hrs | QA Engineer | Matrix computation |
| Readiness Checklist | 20 hrs | Test Lead | Gate definitions |
| Dashboard | 20 hrs | DevOps / QA Tools | All status data |

**Total Effort**: 610-850 person-hours (~4-6 FTE for 8 weeks)

---

## Success Criteria

Once this roadmap is executed:

✅ **Test Solution can independently compute coverage** without relying on AI statistics
✅ **All AI claims are inputs under test**, not acceptance criteria
✅ **Every sourceAnchor is traceable** to ATL105 specification
✅ **Every match is classified** as CONFIRMED (evidence-based) or REVIEW_REQUIRED (unresolved)
✅ **Heuristic matches require SME decision**, never auto-promoted
✅ **Process improvements are gated**, requiring evidence + validation + regression testing

---

## Risk Mitigations

| Risk | Mitigation |
|---|---|
| Anchors incomplete or wrong | Each anchor must cite spec section + paragraph; Spec Owner review |
| BR derivation misses requirements | Compare derived BRs to AI BRs; gaps trigger spec re-review |
| Validators have bugs | Unit test validators against known payloads; Run2 spot-checks |
| Coverage denominator too strict | Document rationale for in-scope vs out-of-scope; Test Lead approval |
| Readiness gates never close | Gate escalation process; time-box SME reviews; approve with caveats |

---

## Next Steps

1. **Immediate** (This week): Form Test Solution Improvement Working Group
2. **Week 1**: Kickoff Phase 1 (canonical anchors + BR derivation)
3. **Week 2**: Kickoff Phase 2 (TC + payload validators in parallel)
4. **Week 4**: Kickoff Phase 3 (coverage matrix + reconciliation)
5. **Week 6**: Kickoff Phase 4 (readiness gates + dashboard)
6. **Week 8**: Execute batch validation on Run2; generate final coverage comparison report

---

**Prepared by**: Test Solution Improvement Initiative
**Status**: Ready for Working Group Review
**Next Review**: 2026-09-30
