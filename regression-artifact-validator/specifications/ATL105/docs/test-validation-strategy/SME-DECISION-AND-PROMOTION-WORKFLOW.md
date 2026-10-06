# SME Decision and Promotion Workflow

## Scope and Authority

This workflow records Test Solution-owned SME/TBA decisions about AI-to-Test crosswalk candidates. It does not modify AI artifacts, treat similarity as approval, or make a decision executable. The reviewer must compare each candidate to ATL105 2026-3 and provide the exact source anchor and rationale.

Two append-only registers have separate subject scopes:

| Register | Subject type | Purpose |
|---|---|---|
| `test-output/test-solution-independent-review/semantic-br-decision-register.json` | `AI_TO_TEST_CROSSWALK` | Decide whether an AI requirement is a new independent Test Solution rule, a confirmed match, a duplicate, rejected, or still under review. `NEW_RULE` may produce an independently authored BR candidate. |
| `test-output/ai-solution-independent-review/ai-only-sme-decision-register.json` | `AI_ONLY_BR` | Triage AI-only requirements that have no direct existing Test Solution rule. This register does not feed the crosswalk BR promotion package. |

Do not merge these registers or count their records together as coverage.

## Decision Lifecycle

1. Select one subject from the matching register and inspect its AI evidence, candidate Test Solution rules, and ATL105 source text.
2. Prepare one structured JSON decision input. Keep the existing pending event; recording creates a new revision with `supersedesDecisionId` and an incremented revision number.
3. Run the recorder. It appends the event only after checking the subject exists, the register supports its subject type, and the anchor matches ATL105 2026-3 and the subject segment.
4. Validate the register. Its event history must be linear per subject: unique decision IDs, consecutive revisions, and each revision supersedes the current event for the same subject and segment.
5. Generate the approved BR package and coverage summary. These use only each subject's effective latest decision.
6. Derive TS/TC/TD separately. `NEW_RULE` creates a BR candidate for chain derivation only; no decision outcome is execution certification.

## Decision Requirements

| Decision | Required evidence | Promotion effect |
|---|---|---|
| `NEW_RULE` | Reviewer, rationale, ATL105 source anchor, independently authored title and requirement text, explicit `I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI` attestation | Creates one `APPROVED_FOR_CHAIN_DERIVATION` BR candidate. It still needs TS/TC/TD and their validation. |
| `CONFIRMED_MATCH` | Reviewer, rationale, canonical source anchor, existing `matchedTestRuleId` | Resolves the crosswalk to an existing rule; it creates no new BR. |
| `DUPLICATE` | Reviewer, rationale, `duplicateOfRuleId` | Records duplicate disposition; it gets no coverage credit. |
| `REJECT` | Reviewer and rationale | Rejects the candidate; it creates no BR and gets no coverage credit. |
| `REVIEW_REQUIRED` | Reviewer, rationale, evidence where available | Keeps the subject blocked from promotion. |
| `PENDING` | Seed state only; reviewer remains `UNREVIEWED` | Never counts as coverage or promotion. Resolve it by appending a revision, not by editing the seed event. |

The attestation is a reviewer declaration, not an automated proof that prose is independent. The source-anchor validator checks specification, version, section, rule, and segment shape; semantic source support remains a human review responsibility.

## Structured Input

Save one decision object in a JSON file and run the recorder from the `regression-artifact-validator` module directory:

```json
{
  "subjectType": "AI_TO_TEST_CROSSWALK",
  "subjectId": "REQ-SRC-ATL105-PDF-001:002",
  "segment": "100",
  "decision": "NEW_RULE",
  "reviewer": "reviewer-id",
  "rationale": "Explain the source comparison and why this is a distinct Test Solution rule.",
  "sourceAnchor": {
    "specification": "ATL105",
    "version": "2026-3",
    "section": "12.1",
    "segment": "100",
    "element": "2",
    "rule": "account-number-entry-method"
  },
  "approvedRule": {
    "title": "Account number representation follows the entry method",
    "requirement": "Write the independently derived Test Solution rule here."
  },
  "independenceAttestation": "I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI"
}
```

For `CONFIRMED_MATCH`, supply `matchedTestRuleId` instead of `approvedRule`. For `DUPLICATE`, supply `duplicateOfRuleId`. Other outcomes do not create BRs.

```powershell
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.RecordSmeDecision' '-Dexec.args=--decision-json path/to/decision.json'
```

## Validation and Outputs

Run both register validations; the default validates the AI-only register, and the explicit path validates the crosswalk register:

```powershell
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.ValidateSmeDecisionRegister'
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.ValidateSmeDecisionRegister' '-Dexec.args=specifications/ATL105/test-output/test-solution-independent-review/semantic-br-decision-register.json'
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateApprovedAtomicBusinessRequirements'
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GeneratePromotedDecisionCoverage'
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateSmeDecisionSummary'
```

The outputs are `approved-atomic-test-solution-business-requirements.json`, `promoted-sme-decision-coverage.json`, and `live-sme-decision-summary.json` under the relevant independent-review directory. Approved BR candidates remain `APPROVED_FOR_CHAIN_DERIVATION`, not `EXECUTION_READY`.

## Reviewer-Unavailable Contingency

If no SME/TBA reviewer is available:

1. Keep subjects `PENDING` or `REVIEW_REQUIRED`; do not record a reviewed decision with a proxy identity, fabricated timestamp, or generated rationale.
2. Continue deterministic evidence preparation: source quotes/anchors, catalog links, schema and payload checks, duplicate detection, and mutation results. Keep these as technical/source evidence, not semantic approval.
3. Prioritize ambiguous, high-impact, and release-blocking subjects for an authorized alternate (formally delegated business approver, specification owner, or Test leadership with documented authority). The actual reviewer must record their own decision and rationale.
4. If no authorized reviewer can be assigned, record the release risk and accountable business risk acceptance outside the SME decision register. Risk acceptance does not change the subject to `CONFIRMED_MATCH`, `NEW_RULE`, or `EXECUTION_READY`.
5. Keep affected coverage blocked or `NOT_CALCULABLE` when the eligible independent baseline is incomplete. Do not report unresolved AI BRs as `AI_ONLY` merely because the Test Solution baseline has not been reviewed or completed.

Automated extraction can produce `DRAFT_REVIEW_REQUIRED` evidence from unambiguous source text, but it cannot issue an SME/TBA decision. No reviewer availability is a schedule/risk condition, not an implicit approval.