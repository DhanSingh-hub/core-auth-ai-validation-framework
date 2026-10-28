# WIC UPC/PLU Execution Evidence - MCH-44658

**Evidence class:** External translator/adapter QA execution evidence; not canonical specification text and not execution evidence for this repository's Java validator.

**Source:** Jira story MCH-44658, "ATL105 Segment 103 - parse WIC UPC/PLU data correctly," resolved 2026-08-26. Supplied PDF export SHA-256: `C6423B5535B094AF57AC6EFDC8595F086EE72A8BFD7D28748F2963814D6819D4`.

**Handling:** The PDF includes credential-shaped and transaction-identifying values in screenshots. They are intentionally omitted here. Do not copy API keys, trace/request IDs, card-track/PIN material, merchant identifiers, or raw production-like logs into training fixtures. Rotate any credential if its value was live rather than a placeholder.

## Executive Learning

The issue documents a PLU-specific failure in an eWIC purchase path while an UPC control path worked, followed by translator/adapter changes and successful QA comments. The strongest training value is the differential: hold the purchase context and benefit-balance conditions constant, vary only the UPC/PLU interpretation, and verify both the parsed product value and the resulting host request/response.

This evidence supports a converter-level regression target. It does **not** close the internal PS bitmap question in the repository's structural Segment 103 validator, prove the exact root cause of the host decline, or certify production behavior.

## Evidence Timeline

| Date / environment | Transaction evidence | Observed result | Limit |
|---|---|---|---|
| 2026-08-06, development | eWIC capture using Segment 103 WIC Product Data; a later cancel/void request and response are shown in the exported thread | Capture is shown as approved; the cancel is shown as voided/reversal | Test/development evidence only; the export contains multiple sample records and does not provide a single immutable test-case package |
| 2026-08-11, QA-CHD | PLU `30047` purchase with balance; UPC `28000008215` is the comparison/control path | PLU request is declined with host text `ENTR LESS AMT`; the ticket reports UPC purchase working | The message does not isolate the parser as the sole cause; balance/host behavior may contribute. Preserve the decline as an observed result, not a parser oracle |
| 2026-08-20, implementation update | Translator and BUYPASS adapter merge requests are referenced; a direct API capture snapshot is attached | Comment says PLU transactions are working after the change | The snapshot contains secret-shaped fields and is not suitable for direct fixture reuse |
| 2026-08-21, QA-CHD | Follow-up eWIC/Segment 103 tests | Tester reports the result good; ticket notes translator 2.146.0 and adapter 1.250.0 deployed to QA-CHD | Narrative test report; no test-case IDs or machine-readable assertion results are included |
| 2026-08-24 to 2026-08-26, QA-OMA | Follow-up deployment and QA test | AWS-dev is called out as an exception/retrigger; final QA-OMA comment reports good and the story is resolved | Does not establish AWS-dev behavior, production certification, or exhaustive UPC/PLU coverage |

## Parser Interpretation and Ambiguity

The issue acceptance criteria describe this interpretation for the WIC UPC Purchase Information `PS` record in Element 154:

1. Bit 2 is a 17-digit numeric field, left-padded with zeroes.
2. Bit 11 is a two-digit data length. Count from the right edge of Bit 2 and take that many digits as the meaningful payload.
3. The first digit of that extracted payload is the UPC/PLU indicator: `0` means UPC and `1` means PLU.
4. The remaining extracted digits are the product value. Zeroes outside the declared meaningful suffix are padding, not part of the indicator or value.

For the acceptance-criteria example, the Bit 2 value is `00000123456789012` and Bit 11 is `12`. The 12-digit suffix is `123456789012`: indicator `1`, PLU value `23456789012`.

The same export contains conflicting explanatory wording and examples:

- One email calls `23456789012` (11 digits) "Bit 11," although the acceptance criteria define Bit 11 as a two-digit length.
- A screenshot says the "first byte" of the 17-digit field indicates UPC/PLU. That conflicts with left-padding and the acceptance rule that the indicator is the first digit of the length-selected meaningful suffix.
- A PLU `30047` sample is shown adjacent to a `05` length value. If Bit 11 counts the indicator plus product value, the meaningful length would be six digits (`1` + `30047`); the displayed `05` suggests a different counting convention or a field-boundary misunderstanding.

**Training rule:** follow the written acceptance criteria as the current candidate interpretation, but keep Bit 11's inclusion/exclusion of the indicator and the meaning of the screenshot's "first byte" unresolved. Do not promote the contradictory screenshot wording or hard-code the `05` example as canonical without a source-owner clarification and a captured, independently decoded wire example.

## Transaction Analysis

### PLU-with-balance decline

The most discriminating reported failure is the QA-CHD PLU `30047` purchase with a balance. The ticket records an `ENTR LESS AMT` host decline after the initial mapping change, while an UPC control was reported to work. This points to the PS purchase-record decode or its downstream product/amount mapping as a plausible fault area, but the response alone cannot prove parser causality. A future regression should capture the raw PS subrecord, extracted length, indicator, decoded value, submitted order item, balance context, and host result in one correlated case.

### UPC control and capture/cancel path

The ticket includes successful UPC capture evidence, including UPC control examples, and a capture followed by cancel/void with an approved capture and reversal/void response. Treat this as evidence for the tested QA/development paths only. The exported comments do not provide a stable test-case identifier, a reproducible fixture set, or complete assertions for every displayed transaction.

### Post-fix QA outcomes

Later QA-CHD and QA-OMA comments report successful testing after translator/adapter changes. These are useful implementation-validation evidence and support adding PLU cases to the converter's regression suite. They do not replace a controlled before/after test, prove the host decline's root cause, cover every PS bitmap/layout, or close the independent Segment 103 test-data evidence gap.

## Recommended Regression Learning

| Candidate | Input variation | Expected observation | Evidence status |
|---|---|---|---|
| UPC control | Indicator `0`, UPC value, zero-padded 17-digit Bit 2, explicit Bit 11 | Correct UPC value reaches the order item and the eWIC request; verify host outcome separately | Candidate; use a stable, masked test fixture |
| PLU control | Indicator `1`, PLU `30047`, same purchase/balance conditions as UPC control | Correct PLU value reaches the order item; compare against the historical `ENTR LESS AMT` failure | Candidate; ticket reports post-fix QA success but supplies no automated test assertion |
| Padding boundary | Same meaningful suffix with different leading zero padding | Decode is invariant to the number of permitted left-pad zeroes | Candidate derived from acceptance wording |
| Length boundary | Bit 11 zero, non-numeric, greater than 17, or inconsistent with available digits | Reject or mark review-required according to a separately approved decoder contract | Requires converter owner to define error behavior |
| Indicator boundary | Selected suffix begins with neither `0` nor `1` | Reject or mark review-required; never guess UPC/PLU from the raw field's first byte | Candidate derived from acceptance wording |
| PS variant boundary | Same `PS` identifier with purchase versus exception/denial bitmap | Select the correct layout and downstream interpretation | Existing structural validator does not decode this bitmap |
| Lifecycle | Approved eWIC capture followed by cancel/void | Correlate original transaction and observe reversal/void disposition without changing product interpretation | Ticket evidence is partial; build a deterministic test pair |

Before using a PLU `30047` fixture as a hard expected-value oracle, resolve whether Bit 11 counts the indicator. The issue's displayed `05` sample and its written acceptance criteria do not agree on this point.

## Repository Boundary and Training Status

`Segment103PayloadValidator` validates Element 154 total length and the `EF`/`EA`/`PS` tag/size boundary. It intentionally does not interpret the internal `PS` bitmap or UPC/PLU indicator. The Jira evidence concerns CommerceHub translator/BUYPASS-adapter behavior outside this validator; its QA outcomes must not be presented as tests executed by this repository.

This addendum does not change `SEG103-R-023`, does not add a canonical parser rule, and does not close `SEG103-SME-007`/P-07 or `SEG103-SME-008`/P-08. It records external implementation evidence and a focused regression design. The official rule catalog and independent baseline remain unchanged.
