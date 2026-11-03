package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.canonical.Segment100AiJsonCompatibilityValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12EmvWireTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Chapter12SegmentPayloadValidator validator = new Chapter12SegmentPayloadValidator();

    private ObjectNode input(String id) {
        var input = mapper.createObjectNode().put("specificationVersion", "2026-3").put("segment", id)
                .put("direction", "130".equals(id) ? "REQUEST" : "RESPONSE");
        input.putObject("elements").put("85", id).put("84", "130".equals(id) ? "0038" : "0056")
                .put("187", "130".equals(id) ? "" : "0000000000000000000000001").put("188", "")
                .put("189", "009").put("190", "8407A0000000041010");
        // Table value must be the source-documented 'EMVYES'/'EMVNOT' (length 6) so this test's
        // chip-data/binary-wire fixture does not also trip the (separately tested) Appendix T
        // Table 001 content check (SEG130-R-020); the +3 bytes versus the prior "ABC" placeholder
        // are folded into the declared Segment Length above.
        input.putArray("tables").addObject().put("id", "001").put("length", "006").put("value", "EMVYES");
        String prefix = "130".equals(id) ? "130\u001c0038\u001c\u001c\u001c009\u001c"
                : "13100560000000000000000000000001009";
        String raw = HexFormat.of().formatHex(prefix.getBytes(StandardCharsets.US_ASCII)) + "8407A0000000041010"
                + HexFormat.of().formatHex(("130".equals(id) ? "\u001c001006EMVYES\u001c" : "001006EMVYES").getBytes(StandardCharsets.US_ASCII));
        assertThat(raw.length() / 2).isEqualTo("130".equals(id) ? 38 : 56);
        input.put("serializedSegmentHex", raw);
        return input;
    }


    private void target(ObjectNode input, String scope, Status expected) throws Exception {
        assertThat(validator.validate(input).findings()).filteredOn(f -> f.scope().equals(scope))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(expected));
    }

    @Test void measuresPackedChipBytesRatherThanHexCharactersAndRetainsEmptyRequestSeparators() throws Exception {
        for (String id : List.of("130", "131")) {
            var input = input(id);
            var original = input.deepCopy();
            target(input, "binary-wire", Status.CHECKS_PASSED);
            target(input, "binary-length", Status.CHECKS_PASSED);
            assertThat(validator.validate(input).findings()).noneMatch(f -> f.status() == Status.INVALID);
            assertThat(input).isEqualTo(original);
            ((ObjectNode) input.path("elements")).put("190", "84071c000000041010");
            input.put("serializedSegmentHex", input.path("serializedSegmentHex").asText().replace("8407A0000000041010", "84071c000000041010"));
            target(input, "binary-wire", Status.CHECKS_PASSED);
        }
    }

    @Test void rejectsMissingEmptyFieldSeparatorsAsciiChipSubstitutionAndMismatchedByteLength() throws Exception {
        var input = input("130");
        String raw = input.path("serializedSegmentHex").asText();
        for (String mutated : List.of(raw.replace("1c1c1c", "1c1c"), raw.substring(0, raw.length() - 2),
                raw + "1c", raw.replace("8407A0000000041010", HexFormat.of().formatHex("8407A0000000041010".getBytes(StandardCharsets.US_ASCII))))) {
            input.put("serializedSegmentHex", mutated);
            target(input, "binary-wire", Status.INVALID);
            target(input, "binary-length", Status.INVALID);
        }
        input = input("131");
        input.put("serializedSegmentHex", input.path("serializedSegmentHex").asText() + "1c");
        target(input, "binary-wire", Status.INVALID);
        ((ObjectNode) input.path("elements")).put("84", "0057");
        target(input, "binary-length", Status.CHECKS_PASSED);
        target(input, "binary-wire", Status.INVALID);
    }

    @Test void malformedActualHexIsRejectedAndMetadataDoesNotSupplyWire() throws Exception {
        var input = input("130");
        for (String raw : List.of("", "0", "XX", " 00", "00 11")) {
            input.put("serializedSegmentHex", raw);
            target(input, "binary-wire", Status.INVALID);
        }
        input.put("serializedSegmentHex", 130);
        target(input, "binary-wire", Status.INVALID);
        input.remove("serializedSegmentHex");
        input.putObject("context").put("serializedSegmentHex", input("130").path("serializedSegmentHex").asText());
        target(input, "binary-wire", Status.REVIEW_REQUIRED);
    }

    @Test void syntheticFullRequestPassesBoundedEmvChecksAndKeepsReviewGatesOpen() throws Exception {
        ObjectNode request = syntheticRequest();
        assertThat(new Segment100AiJsonCompatibilityValidator().validatePayload(request).errors()).isEmpty();

        var result = validateSyntheticEmv(request);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-wire") && f.status() == Status.CHECKS_PASSED);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-length") && f.status() == Status.CHECKS_PASSED);
        assertThat(result.findings()).anyMatch(f -> f.ruleId().equals("BR-SEG100-APPR-CHIP-LENGTH")
                && f.scope().equals("LENGTH_FORMAT") && f.status() == Status.CHECKS_PASSED);
        assertThat(result.findings()).anyMatch(f -> f.ruleId().equals("SEG130-R-006")
                && f.scope().equals("chip-card-sequence") && f.status() == Status.CHECKS_PASSED);
        assertThat(result.findings()).noneMatch(f -> f.status() == Status.INVALID);
        assertThat(result.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test void isolatedLengthAndSequenceMutationsFailWithoutBreakingTheMeasuredWire() throws Exception {
        for (String length : List.of("000", "015")) {
            ObjectNode request = syntheticRequest();
            ObjectNode emv = emvSegment(request);
            emv.put("EMVChipDataLength", length);
            replaceAsciiField(emv, "014", length);

            var result = validateSyntheticEmv(request);
            assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-wire") && f.status() == Status.CHECKS_PASSED);
            assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-length") && f.status() == Status.CHECKS_PASSED);
            assertThat(result.findings()).anyMatch(f -> f.ruleId().equals("BR-SEG100-APPR-CHIP-LENGTH")
                    && f.scope().equals("LENGTH_FORMAT") && f.status() == Status.INVALID);
        }

        ObjectNode request = syntheticRequest();
        ObjectNode emv = emvSegment(request);
        emv.put("EMVCardSequenceNumber", "025");
        replaceAsciiField(emv, "001", "025");
        var sequenceResult = validateSyntheticEmv(request);
        assertThat(sequenceResult.findings()).anyMatch(f -> f.scope().equals("binary-wire") && f.status() == Status.CHECKS_PASSED);
        assertThat(sequenceResult.findings()).anyMatch(f -> f.ruleId().equals("SEG130-R-006")
                && f.scope().equals("chip-card-sequence") && f.status() == Status.INVALID);
    }

    @Test void fullRequestCountAndSegmentLengthAreCheckedAtTheirOwnBoundaries() throws Exception {
        ObjectNode request = syntheticRequest();
        ObjectNode financialRequest = (ObjectNode) request.path("Financial Request");
        financialRequest.put("NumSegments", "3");
        assertThat(new Segment100AiJsonCompatibilityValidator().validatePayload(request).errors()).isEmpty();
        financialRequest.put("NumSegments", "02");
        assertThat(new Segment100AiJsonCompatibilityValidator().validatePayload(request).errors())
                .anyMatch(error -> error.reason().contains("does not match segment objects found: 3"));

        request = syntheticRequest();
        ObjectNode emv = emvSegment(request);
        emv.put("SegmentLength", "071");
        replaceAsciiField(emv, "0071", "071");
        var result = validateSyntheticEmv(request);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-wire") && f.status() == Status.CHECKS_PASSED);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-length") && f.status() == Status.INVALID);

        request = syntheticRequest();
        emv = emvSegment(request);
        emv.put("SegmentLength", "0070");
        replaceAsciiField(emv, "0071", "0070");
        result = validateSyntheticEmv(request);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-wire") && f.status() == Status.CHECKS_PASSED);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("binary-length") && f.status() == Status.INVALID);
    }

    private ObjectNode syntheticRequest() throws Exception {
        try (var stream = getClass().getResourceAsStream("/atl105/segment-130-synthetic-request.json")) {
            assertThat(stream).isNotNull();
            return (ObjectNode) mapper.readTree(stream);
        }
    }

    private static ObjectNode emvSegment(ObjectNode request) {
        return (ObjectNode) request.path("Financial Request").path("EMV Data Segment");
    }

    private static Chapter12SegmentPayloadValidator.Result validateSyntheticEmv(ObjectNode request) throws Exception {
        return new Chapter12ActualPayloadAdapter().validate(emvSegment(request), "130",
                "EMV Financial Transaction Request", null);
    }

    private static void replaceAsciiField(ObjectNode emv, String before, String after) {
        String beforeHex = HexFormat.of().formatHex(before.getBytes(StandardCharsets.US_ASCII));
        String afterHex = HexFormat.of().formatHex(after.getBytes(StandardCharsets.US_ASCII));
        String wire = emv.path("SerializedSegmentHex").asText();
        emv.put("SerializedSegmentHex", wire.replaceFirst(beforeHex, afterHex));
    }
}
