# Core Auth Regression Test Validation Strategy Package

## Documents

- [AI Generation Output Validation Presentation](presentations/AI-Generation-Output-Validation-Presentation.md)
- [Browser Presentation](presentations/AI-Generation-Output-Validation-Presentation.html)
- [PDF Presentation](presentations/AI-Generation-Output-Validation-Presentation.pdf)
- [RAID Log](RAID-Log.md)
- [Expected Questions and Answers](Expected-Questions-and-Answers.md)
- [Browser Presentation](presentations/AI-Generation-Output-Validation-Presentation.html)
- [PDF Presentation](presentations/AI-Generation-Output-Validation-Presentation.pdf)

## Intended Review Order

The [Common LLM Segment Training Strategy](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) is the mandatory standard for all Test Team training and AI-output validation. Every contributor follows it; segment addenda may add controls but may not bypass its phases or gates.

1. AI Solution Team confirms the metadata and input-format information requested by the strategy.
2. Test/SME Team approves the Segment 100 boundary and descriptive requirements.
3. Client/Business Team reviews business wording and acceptance criteria.
4. Test Validation Team maps approved rules into the canonical rule catalog.
5. AI artifacts are preserved, normalized, independently validated, matched, covered, and reported.
6. Proposed process improvements are evidence-based, reviewed, documented, and regression-tested before adoption.

## Current Implementation Evidence

AI-generated candidate artifacts should be placed in:

```text
test-input/ai-solution/
```

See the [AI Solution input README](../../test-input/ai-solution/README.md) for the required folder structure and intake rules.

The current Java implementation is the ATL105 validation foundation, with Segment 100 as the first completed module. It produces coverage reports under:

```text
test-output/traceability-matrix/segment-100/
```

The strategy now targets complete ATL105 coverage across all segments and message categories, followed by reuse of the same platform for future specifications. Implementation will expand module by module from the Segment 100 foundation.
