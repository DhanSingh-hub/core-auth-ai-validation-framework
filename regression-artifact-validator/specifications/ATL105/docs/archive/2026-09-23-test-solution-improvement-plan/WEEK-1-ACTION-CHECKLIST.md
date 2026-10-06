# Test Solution Immediate Action Checklist

> **ARCHIVED 2026-09-29.** Superseded by the [ATL105 Segment Training Handbook](../../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md). Kept for history only; status figures and plans are a 2026-09-23 snapshot.
## What to Improve This Week (Priority 1 — Unblock AI Review)

**Goal**: Enable Test Solution to begin independent comparison of Run2 artifacts
**Timeline**: 1 week (can parallelize 3-4 workstreams)
**Owner**: Test Solution Improvement Working Group

---

## CRITICAL PATH: Must Complete Before AI Coverage Review Can Proceed

### Task 1.1: Extend Canonical Anchor Catalog (All 49 Segments)
**Why**: Anchors are the keys linking AI claims to Test Solution independent evidence. Without them, we cannot reliably match AI BR X to Test BR X.

**What to do**:
- [ ] Review existing `segment-100-canonical-anchors.md` as template
- [ ] Create `/specifications/ATL105/docs/specs/kb/segment-NNN-canonical-anchors.md` for segments 101-157, DL1-8
- [ ] For each segment field, define sourceAnchor:
  ```
  atl105|2026-3|section2|101|field-fleet-id|rule-conditional-on-card-type
  atl105|2026-3|section2|101|field-fleet-id|rule-max-length-10
  ```
- [ ] Link each anchor to ATL105 2026-3 specification section + paragraph
- [ ] Store index in `/specifications/ATL105/canonical-anchor-registry.json`

**Output**: 49 anchor files + 1 index file
**Effort**: ~40-60 hours (parallelizable across team)
**Blocker**: YES — all downstream work depends on this

**Who can start now**: Any Test Team member with access to ATL105 spec

---

### Task 2.1: Build TC Structure Validator
**Why**: Can't trust 13,082 AI TCs are well-formed. Need automated check.

**What to do**:
- [ ] Create `TCStructureValidator.java` in `/regression-artifact-validator/src/main/java/com/coreauth/validator/`
- [ ] Check for required sections:
  ```
  ✓ Given: [context/setup]
  ✓ When: [action]
  ✓ Then: [assertion]
  ✓ Expected Result: [specific outcome]
  ✓ Test Data Reference: [payload ID link]
  ```
- [ ] Validate given→when→then logical flow
- [ ] Verify test data link exists
- [ ] Classify each TC as VALID, VALID_WITH_WARNINGS, INVALID
- [ ] Run against Run2's `test_case_candidates.json` (13,082 TCs)
- [ ] Generate `test-output/tc-structure-validation-report.json`

**Output**: Validator class + validation report
**Effort**: ~40 hours
**Blocker**: YES — must know which TCs are structurally sound before comparison

**Who can start now**: Java developer familiar with validator patterns

**Quick start**:
```java
public class TCStructureValidator {
    private static final Pattern GIVEN_PATTERN = Pattern.compile("(?i)^Given:\\s+");
    private static final Pattern WHEN_PATTERN = Pattern.compile("(?i)^When:\\s+");
    private static final Pattern THEN_PATTERN = Pattern.compile("(?i)^Then:\\s+");
    private static final Pattern EXPECTED_PATTERN = Pattern.compile("(?i)Expected Result:");

    public ValidationResult validate(String tcMarkdown) {
        // TODO: Implement checks for all required sections
        // Return VALID, VALID_WITH_WARNINGS, or INVALID
    }
}
```

---

### Task 3.1: Define Coverage Denominator for Each Segment
**Why**: Need to know what "100% coverage" means per segment. AI claims 30% but what's the denominator?

**What to do**:
- [ ] For each substantive segment (100-105, 108-109, 111, 113), create `segment-NNN-coverage-denominator.json`:
  ```json
  {
    "segment": 100,
    "specVersion": "2026-3",
    "inScope": [
      "All field-level format rules (12 fields)",
      "All cardinality rules (1..1)",
      "All conditional applicability rules",
      "All error/rejection scenarios (5)"
    ],
    "outOfScope": [
      "Performance requirements",
      "Concurrent processing assumptions"
    ],
    "expectedBRs": 45,
    "expectedScenarios": 80,
    "expectedTCs": 200,
    "expectedTCsWithData": 200
  }
  ```
- [ ] Conduct 1 SME review per segment to validate scope
- [ ] Document rationale in coverage notes
- [ ] Aggregate into `atl105-coverage-denominator-catalog.json`

**Output**: 10 segment files + 1 aggregated catalog
**Effort**: ~20-30 hours (can parallelize across segments)
**Blocker**: YES — cannot calculate coverage % without knowing denominator

**Who can start now**: Test Lead + segment SMEs

---

## PARALLEL STREAM 1: Independent BR Derivation (Start Week 1, Continue Week 2)
**Why**: Need "truth" BRs independent of AI to compare against.

**What to do** (top 3 segments first):
- [ ] **Segment 100**: Extract all BRs from ATL105 spec § 2.1.1-2.1.5 → `segment-100-independent-br-derivation.json`
  - Account Number field: 4 rules (alpha-chars, length-12, non-empty, uniqueness)
  - Card Number field: 3 rules
  - ... (repeat for 10+ fields)
- [ ] **Segment 101**: Extract all BRs from spec § 2.2.1-2.2.4
- [ ] **Segment 102**: Extract all BRs from spec § 2.3.1-2.3.3
- [ ] Each BR must have:
  - Unique ID: `NNN-BR-001`
  - Source Anchor: `atl105|2026-3|section2|100|field-X|rule-Y`
  - Spec citation: "ATL105 2026-3 Section 2.1.1, para 3"
  - Requirement text
  - Cardinality/applicability

**Output**: 3 detailed BR files
**Effort**: ~18-24 hours (6-8 hrs per segment)
**Blocker**: NO (but high priority) — enables coverage calculation

**Who can start now**: Test Architect + domain SMEs

---

## PARALLEL STREAM 2: Payload Format Validation (Start Week 1, Overlap Week 2-3)
**Why**: 26,261 test payloads must comply with ATL105 serialization rules.

**What to do**:
- [ ] **Segment 100 Rule Validator**: Create `Segment100PayloadValidator.java`
  - Define field rules: account_number (12-char A-Z), card_number (format), etc.
  - Check JSON structure against expected fields
  - Validate field values against rules
  - Report violations
- [ ] **Apply to Run2 payloads**: Test against all segment-100 payloads in Run2
- [ ] Generate `test-output/segment-100-payload-validation-report.json`
- [ ] Repeat for segments 101, 102 (top 3 to start)

**Output**: 3 segment validators + validation reports
**Effort**: ~15-20 hours (5-7 hrs per segment)
**Blocker**: NO (but high priority) — enables data quality assessment

**Who can start now**: QA Engineer familiar with JSON schema validation

**Quick start**:
```java
public class Segment100PayloadValidator {
    static final Map<String, Rule> RULES = Map.ofEntries(
        Map.entry("account_number", new Rule("string", "^[A-Z]{12}$", true)),
        Map.entry("card_number", new Rule("string", "^[0-9]{16}$", true)),
        Map.entry("amount", new Rule("decimal", null, true))
    );

    public PayloadValidationResult validate(JSONObject payload) {
        // TODO: Check each field against rules; report violations
    }
}
```

---

## OPTIONAL (Week 2+): Documentation & Communication
- [ ] Update README.md with Test Solution roadmap status
- [ ] Create weekly status dashboard for stakeholders
- [ ] Document findings as we discover AI coverage gaps

---

## Success Metrics (By End of Week 1)

| Metric | Target | How to Measure |
|---|---|---|
| Canonical Anchors Complete | 49/49 segments | Count files in `/specifications/ATL105/docs/specs/kb/segment-*/segment-NNN-canonical-anchors.md` |
| TC Structure Validator Built | 1/1 validator | Run validation on Run2 → `test-output/tc-structure-validation-report.json` exists |
| Coverage Denominator Defined | 10/10 substantive segments | Count files `segment-NNN-coverage-denominator.json` |
| Independent BRs Derived | 3/10 priority segments | `segment-100/101/102-independent-br-derivation.json` files exist with ≥50 requirements each |
| Payload Validation Started | 3/10 segments | `segment-100/101/102PayloadValidator.java` exists + runs without error |

---

## Key Deliverables This Week

1. **Canonical Anchor Registry** → `/specifications/ATL105/canonical-anchor-registry.json`
2. **TC Structure Validation Report** → `test-output/tc-structure-validation-report.json`
3. **Coverage Denominator Catalog** → `/specifications/ATL105/coverage-denominator-catalog.json`
4. **Independent BR Index (Phase 1)** → `segment-100/101/102-independent-br-derivation.json`
5. **Payload Validator (Phase 1)** → `regression-artifact-validator/src/main/java/com/coreauth/validator/Segment100PayloadValidator.java`

---

## How This Unblocks AI Review

Once Week 1 checklist is complete:
- ✅ We have **canonical anchors** to match AI claims
- ✅ We know which **TCs are structurally sound**
- ✅ We know **what "complete" means** per segment
- ✅ We have **independent BRs** to compare against AI BRs
- ✅ We can **validate payload quality** independent of AI assertions

**Result**: Can run `CoverageReconciler` to compare AI Run2 coverage to Test Solution coverage and generate the independent verdict.

---

## Estimated Resource Allocation (One Week)

| Role | Hours | Tasks |
|---|---|---|
| Senior Test Architect | 40 | Lead canonical anchors; review coverage denominator |
| Test Lead | 30 | Define coverage scope; oversee BR derivation; manage checklist |
| QA Engineer #1 | 35 | Build TC structure validator; segment-100 payload validator |
| QA Engineer #2 | 35 | Segment-101/102 payload validators; validation reporting |
| Test SME #1 | 25 | Segment-100 BR derivation |
| Test SME #2 | 25 | Segment-101 BR derivation |
| Compliance/Doc | 10 | Anchor documentation; README updates |
| **Total** | **200** | (5 FTE × 40 hrs) |

---

**Prepared by**: Test Solution Improvement Initiative
**Status**: Ready for Kickoff
**Target Start**: Tomorrow (2026-09-24)
