package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
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
}
