# Mastercard / Maestro ATL105 2026-4 model overlay

[rules.json](rules.json) is a source-authored **draft**, not a copy of AI rules or
an approved replacement for the 2026-3 model. Every quote must match its page in
the hash-pinned 2026-4 extracted text. The overlay supplies explicit applicability
conditions and paired logical control/mutant fixtures for 26 obligations across
42 rule/context combinations. The fixtures are not complete physical requests,
actual device observations, valid cryptograms or host-response oracles.

The [v2 package](../../../../../test-output/test-solution-independent-review/mc-maestro-model-v2/REPORT.md)
adds [12 draft obligations](additional-rules.json) for offline-track data, partial
approval prompts, debit retry/PIN behavior, conditional companion segments and
Segment 100 serialization. Total: 38 BR, 66 TS and 132 logical TC/TD. It retains
all source pages/paragraphs for classification, reconciles every legacy BR anchor
against both releases, records overlap proposals without merging, binds candidate
matches to actual delivery TS/TC/TD identities and observes all 1,944 physical JSON
files. Fifteen bounded ASCII Segment 100 controls/mutants exercise framing, length,
sequence and card-code predicates; they are synthetic segments, not full messages.
Source-block identity and source-unit inventory completeness do not establish an
approved semantic denominator.

The [generated review package](../../../../../test-output/test-solution-independent-review/mc-maestro-model-v1/REPORT.md)
retains all 1,343 existing canonical BRs for release/applicability/overlap review.
The existing Petroleum 2025-1 Mastercard Fleet draft is indexed separately;
it is not copied, counted twice or promoted to certified evidence.

Run from the module root:

```powershell
.\framework.ps1 -Task mastercard-maestro-model-build -ReviewDirectory 'C:\delivery\31-10-out'
.\framework.ps1 -Task mastercard-maestro-model-check -ReviewDirectory 'C:\delivery\31-10-out'
.\framework.ps1 -Task mastercard-maestro-model-test
.\framework.ps1 -Task mastercard-maestro-v2-build -ReviewDirectory 'C:\delivery\31-10-out'
.\framework.ps1 -Task mastercard-maestro-v2-check -ReviewDirectory 'C:\delivery\31-10-out'
.\framework.ps1 -Task mastercard-maestro-v2-test
```

Build requires a new output directory; `--check` is read-only and fails on stale
source, delivery or generated evidence. The delivery must match the existing
31 October hash inventory. Its supporting rules are used only after independent
rule derivation and only to propose same-primary-source/page lexical candidates.
Neither shared IDs nor matching quotes automatically establish business equivalence.

## Review and promotion

1. Review source interpretation, atomicity and transaction applicability.
2. Reconcile legacy and companion overlap; do not sum these inventories.
3. Complete omitted rule/transaction/entry-method scope and classify product subtypes.
4. Resolve savings/ACH, Fleet inheritance, Signature Debit/Common AID, credit TOR
   and Mail/Phone completion questions in the generated BA/SME decision register.
5. Review each delivery-bound candidate's objective, conditions, assertions and TD.
6. Supply real runtime bindings, device/host observations and approved response oracles.

No automatic promotion mechanism is provided. Approved denominator and confirmed
coverage remain null until complete scope and independent approval evidence exist.
Passing local predicates demonstrates only those predicates on supplied facts.
