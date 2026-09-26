# Print Data Content Dependency Flow

```mermaid
flowchart TD
    A[Host determines response requires print data] --> B{Blackhawk phone<br/>activation/recharge flow?}
    B -->|No| C[Print Data = plain receipt/print text]
    B -->|Yes| D[Print Data = receipt text with<br/>'\' delimiter after each line]

    C --> E{Print Data present and non-empty?}
    D --> E
    E -->|No| X1[REJECT SEG120-R-003: Print Data required]
    E -->|Yes| F{Print Data length <= 999 characters?}
    F -->|No| X2[REJECT: exceeds per-field cap]
    F -->|Yes| G{Envelope-only validator scope?}
    G -->|Yes| H[Validate presence and length only;<br/>treat '\' as opaque text — SEG120-R-008 informational]
    G -->|No, content-validation scope| I["Validate '\' delimiter well-formedness<br/>REVIEW_REQUIRED until P-03 resolved"]
    H --> J[Pass envelope validation]
```

## Key Insight

Print Data is unstructured free text, unlike Segment 111's Table-ID-keyed
repetitions. The only documented internal structure is the `\` line
delimiter used for Blackhawk phone activation/recharge receipts (BR-263-5):

```text
Line of text\line of text\line of text
```

The current validator treats this as **content**, not **envelope**: it does
not parse or require well-formed `\` delimiters, because whether that belongs
in this module's scope is an open question (`P-03`, see the
[coverage SME note](coverage/segment-120-coverage-sme-tba-note.md)).

## A Mis-Bucketed Requirement

`REQ-SRC-ATL105-PDF-001:1261` (BR-263-5) is the AI Solution's own requirement
statement for this delimiter behavior — but the pipeline's segment-bucketing
step tagged it `UNASSIGNED` instead of `SEG-120`, even though its scenario
(`SC-1842`) and test cases (`TC-4311`, `TC-4312`) are exclusively about
Segment 120 Print Data. This knowledge base attributes it correctly as
`SEG120-R-008`; see the
[AI vs Test Solution analysis](coverage/segment-120-ai-vs-test-solution-analysis.md)
for the full cross-reference.
