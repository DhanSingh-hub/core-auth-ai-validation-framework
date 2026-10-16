# Segment 130 EMV Chip Data Length: Synthetic Fixture Review v2

**Status:** Local synthetic fixture and mutation checks pass. Authorized Test review remains open. No AI-to-Test match is approved and no coverage is credited.

This version replaces the v1 field-reference-only mismatch designs with a complete, synthetic three-segment ATL105 Financial Request and a packed-byte Segment 130 wire fixture. The fixture is at `regression-artifact-validator/src/test/resources/atl105/segment-130-synthetic-request.json`; it contains no user authorization data.

The positive request contains three segment objects and `NumSegments` value `03`. The current converter JSON preflight intentionally accepts a one- or two-digit logical count; the serialized Element 63 representation is two bytes, and this fixture does not claim to have captured the full-message wire. Segment 130 checks cover its four-digit length and measured wire length, Element 189's three-digit length-to-Element 190 byte-count relation, and Element 188 agreement with the single chip-supplied 5F34 value. Isolated mutations cover Element 189 values below and above the encoded byte count, a card-sequence disagreement, a segment-count/object-count disagreement, and Segment 130 length width/value defects. For each Element 189 mismatch, the actual Segment 130 wire remains internally consistent and the length/data relationship alone fails.

The local Java tests passed 12/12:

```powershell
mvn.cmd '-Dtest=Chapter12EmvWireTest,Segment100AiJsonCompatibilityValidatorTest' test
```

These are local validator regression tests, not certified execution of the Test Solution, a host response oracle, or an authorized independent ruling. The source's BCD-packed/hex example and the byte-count implementation are exercised by the synthetic fixture, but Test-owner confirmation of the Element 189 unit remains required. The two AI payloads and sidecars have now been reviewed from hash-verified staged copies: the sidecar Element 189/190 values agree with each payload, but Element 190 is a 999-character provisional `platform_default` value, not packed hex, and `sourceBatchSha256` is absent. These observations do not make either case a positive length/data example or establish an independent outcome. Semantic approvals: 0. Coverage credit: 0. Test Solution execution certification: false. The official baseline and canonical Test package were not changed.

The supplied authorization was inspected only for the field-width and cross-field discrepancies discussed with the user. It was not copied, stored, or used as the test oracle.