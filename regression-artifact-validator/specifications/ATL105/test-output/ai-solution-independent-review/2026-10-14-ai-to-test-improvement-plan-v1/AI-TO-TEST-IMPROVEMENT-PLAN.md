# AI-to-Test Improvement Plan

**Assessment basis:** Independent V6 delivery-wide assessment, finalized 2026-10-14  
**AI BR population:** 6,880 unique BRs  
**Continuity denominator:** 601 source-derived Test BRs  
**Status:** Action plan; no semantic approvals are implied

## Current Baseline

V6 verified 347/601 Test BRs with at least one exact source-anchor candidate (57.74%) and 320/601 with a candidate route that also has a clean physical chain (53.24%). These are structural candidate measures only. Semantic-confirmed and executed coverage remain `NOT_ASSESSED`; execution certification is false.

The V6 anchor disposition partitions all 6,880 AI BRs:

| Disposition | AI BRs | Meaning for work |
|---|---:|---|
| No numeric element anchor | 2,585 | 1,337 are still untyped; remaining proposals require source-backed type/scope review. Do not force element IDs onto non-element rules. |
| Segment unresolved | 569 | 536 element proposals and 33 cross-field proposals have no lookup-eligible segment. |
| No exact source-anchor candidate | 140 | Review whether the anchor is wrong, the rule is new scope, or the independent baseline lacks a rule. |
| Ambiguous/composite candidates | 2,769 | Candidate sets need pair-level review; split BRs only when source obligations are independently testable. |
| Unique exact-anchor candidate | 817 | These already have one candidate; they are pending review, not missing-link defects. |

All 6,880 anchor proposals remain unassessed for source interpretation and none has a supplied literal source quote. Source-page references are present, but a page reference without a verified quotation is not validated provenance.

Physical evidence also needs a separate workstream: 18,868 TCs have clean complete declared physical routes; 2,359 TC IDs have conflicting catalog versions and are not credited as clean; 27 additional unconflicted TCs lack complete physical routes. V6 found zero broken BR/TS/TC references and zero unreferenced payloads. These checks do not validate payload semantics or expected outcomes.

The separate 605-rule catalog view consists of the 601 source-derived baseline plus four `DRAFT_REVIEW_REQUIRED` additions. Keep that view separate until all four additions pass independent approval.

## Execution Backlog

### 1. Verify source citations and extraction lineage

**Owner:** AI team with ATL105 document custodian  
**Scope:** All 6,880 AI BRs; prioritize the 3,154 anchor-gap BRs in the feedback CSV.  
**Deliverable:** Versioned source-evidence sidecar with document/version identity, page/section, exact quote, quote-verification result, and source hash. Preserve original artifacts.

**Acceptance:** Every quote is an exact span in the identified source version; missing, paraphrased, or conflicting evidence remains `REVIEW_REQUIRED`. A verified quote establishes citation integrity only, not interpretation or semantic approval.

### 2. Classify non-element rules and add domain scope

**Owner:** AI team; business/specification SME for unresolved interpretations  
**Scope:** 2,585 BRs with no numeric element anchor, including 1,337 whose proposed type is `UNRESOLVED`.  
**Deliverable:** Source-backed rule type and business-scope anchor for process/transaction, response, cross-field, lifecycle, dependency, security, message, account/card, amount, cross-segment, or another justified class.

**Acceptance:** Element-level BRs receive an element only when the source identifies one. Non-element rules keep a non-element type and domain anchor. Unsupported classification remains unresolved; no fabricated element IDs.

### 3. Resolve segment and message-family context

**Owner:** AI team; ATL105 SME for ambiguity  
**Scope:** 569 BRs with no lookup-eligible segment (536 element proposals; 33 cross-field proposals).  
**Deliverable:** Source-backed segment/message-family mapping with assignment basis and applicability conditions, or an explicit unresolved disposition.

**Acceptance:** A segment is added only when the verified source and validated mapping support it. Do not infer a BR's segment from the broad segment list in linked TC payloads. Multiple plausible segments remain ambiguous pending review.

### 4. Adjudicate AI-to-Test BR candidates

**Owners:** Test Solution owner and authorized business/SME reviewers; AI team supplies source corrections  
**Scope:** 140 no-candidate BRs, 2,769 ambiguous/composite BRs, and 817 unique exact-anchor candidates (12,210 candidate pair rows).  
**Deliverable:** Append-only pair decisions with `MATCH`, `PARTIAL`, `NOT_MATCH`, or `NOT_APPLICABLE`, reviewer identity/authority, source evidence, and rationale. Keep draft additions distinct.

**Acceptance:** Every accepted pair has pair-specific rationale and verified source anchors. Ambiguous one-to-many candidates remain pending until each relationship is adjudicated. The four added Test BRs remain drafts until independently approved.

### 5. Reconcile physical routes and catalog conflicts

**Owners:** AI test-generation/data owners and Test Solution owners  
**Scope:** 2,359 conflicting TC IDs and 27 incomplete unconflicted TC physical routes.  
**Deliverable:** Identify the authoritative candidate/approved variant per TC, then restore or explicitly disposition missing payload/metadata declarations.

**Acceptance:** One selected TC version has all declared payloads and matching TC/scenario metadata, or carries an explicit unresolved disposition. Preserve alternate variants and source hashes. Structural integrity does not certify field values, assertions, wire behavior, or runtime execution.

### 6. Improve testability and outcome evidence

**Owners:** AI team and Test team, with business review for expected outcomes  
**Scope:** BRs and accepted BR/Test BR pairs after source anchors are reviewed.  
**Deliverable:** Atomic, observable BRs; linked scenario conditions; TC assertions and expected outcomes tied to each BR; explicit separation of inferred expectations from authoritative oracles.

**Acceptance:** Each accepted requirement can be evaluated with explicit preconditions, behavior, exceptions, and outcome. Composite BRs are split only where the source defines independent obligations. Execution evidence is separately recorded.

### 7. Publish stable, denominator-aware measures

**Owner:** Independent validation/reporting owner  
**Scope:** 601-rule continuity view and 605-rule catalogued view.  
**Deliverable:** Reproducible report from hash-frozen inputs, showing candidate reach, complete physical-chain candidate reach, authorized semantic decisions, and executed validation separately.

**Acceptance:** Report 347/601 and 320/601 only as V6 structural candidate measures until a new verified run supersedes them. Present the four draft Test BRs separately in the 605-rule view. Never convert candidate reach into semantic or execution coverage.

## Recommended Order

Start with Tasks 1-3 to establish source-backed anchor proposals. Run Task 4 only on those proposals after source evidence is verified. Run Task 5 in parallel because physical route integrity is an independent prerequisite for chain credit. Use reviewed pairs to scope Task 6, then publish updated measures through Task 7.

Task IDs, owners, inputs, outputs, dependencies, and acceptance checks are also provided in `AI-TO-TEST-IMPROVEMENT-PLAN.csv`.