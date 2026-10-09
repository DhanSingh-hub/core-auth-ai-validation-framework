package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12SegmentPayloadValidatorTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Chapter12SegmentPayloadValidator validator = new Chapter12SegmentPayloadValidator();

    private ObjectNode payload(String segment) throws IOException {
        for (JsonNode observation : mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile())
                .path("observations")) {
            if (segment.equals(observation.path("payload").path("segment").asText())) return observation.path("payload").deepCopy();
        }
        throw new IllegalArgumentException("Missing fixture " + segment);
    }

    private static ObjectNode fields(ObjectNode payload) {
        return (ObjectNode) payload.path("elements");
    }

    @Test
    void independentFixturesMeetTheirExactOutcomesWithoutPromotingReviewGates() throws IOException {
        var observations = mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()).path("observations");
        assertThat(observations).hasSize(Chapter12SegmentPayloadValidator.SEGMENTS.size());
        for (JsonNode fixture : observations) {
            var result = validator.validate(fixture.path("payload"));
            assertThat(result.status().name()).as(fixture.path("id").asText())
                    .isEqualTo(fixture.path("expectedStatus").asText());
            assertThat(result.findings()).noneMatch(f -> f.status() == Status.INVALID);
        }
    }

    @Test
    void everySupportedSegmentRejectsWrongTypeNontextAndWrongLengthWidth() throws IOException {
        for (String id : Chapter12SegmentPayloadValidator.SEGMENTS) {
            ObjectNode payload = payload(id);
            fields(payload).put("85", "999");
            assertThat(validator.validate(payload).status()).as(id + " type").isEqualTo(Status.INVALID);
            payload = payload(id);
            fields(payload).put("84", "00");
            assertThat(validator.validate(payload).status()).as(id + " length width").isEqualTo(Status.INVALID);
            payload = payload(id);
            fields(payload).put("85", Integer.parseInt(id));
            assertThat(validator.validate(payload).status()).as(id + " numeric coercion").isEqualTo(Status.INVALID);
        }
        assertThat(validator.validate(mapper.createObjectNode()).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void printDataMeasuresTwoSeparatorsRequiredContentAndSourceConflictInterval() throws IOException {
        ObjectNode input = payload("115");
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(validator.validate(input).findings()).anyMatch(f -> f.scope().equals("wire-declared-length")
                && f.status() == Status.CHECKS_PASSED);
        input.put("serializedSegment", input.path("serializedSegment").asText() + "\u001c");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        for (int size : new int[]{900, 901, 999, 1000}) {
            input = payload("115");
            fields(input).put("152", "T".repeat(size)).put("84", String.format("%04d", size + 9));
            input.put("serializedSegment", "115\u001c" + fields(input).path("84").asText() + "\u001c" + "T".repeat(size));
            assertThat(validator.validate(input).status()).as("print bytes " + size)
                    .isEqualTo(size <= 999 ? Status.REVIEW_REQUIRED : Status.INVALID);
            if (size > 900 && size <= 999) assertThat(validator.validate(input).findings())
                    .anyMatch(f -> f.ruleId().equals("SEG115-R-008") && f.scope().equals("source-length-conflict"));
        }
        input = payload("115");
        fields(input).remove("152");
        input.remove("serializedSegment");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        fields(input).put("152", "");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        fields(input).put("152", 123);
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void printDataChecksObservedResponseAndRequestContextWithoutCoercion() throws IOException {
        ObjectNode input = payload("115");
        input.put("direction", "REQUEST");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        input = payload("115");
        input.put("messageFamily", "Electronic Mail Response");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        input = payload("115");
        ((ObjectNode) input.path("context")).put("additionalInformationFlag", "0");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        input = payload("115");
        ((ObjectNode) input.path("context")).put("requestLoyaltyVersion", 2);
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        input = payload("115");
        input.remove("context");
        input.remove("serializedSegment");
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
        input.putArray("context");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        input = payload("115");
        input.put("messageFamily", 115);
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void tokenizationChecksBlockWidthsSafeKeyPaddingTavvAlphabetAndDirection() throws IOException {
        ObjectNode payload = payload("123");
        payload.remove("serializedSegment");
        fields(payload).put("202", "A".repeat(28)).put("203", "SK" + " ".repeat(56));
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        fields(payload).put("202", "A".repeat(29));
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        fields(payload).put("202", "").put("237", "A".repeat(28));
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        fields(payload).put("237", "!".repeat(28));
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        fields(payload).put("237", "A".repeat(28));
        payload.put("direction", "RESPONSE");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        fields(payload).put("237", "").put("197", "1234");
        payload.put("direction", "REQUEST");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void attributesCheckBlankSignaturePaddingAvailabilityAndExactEncodedLength() throws IOException {
        ObjectNode payload = payload("134");
        fields(payload).put("198", "X");
        payload.remove("serializedSegment");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        payload.putObject("context").put("signatureDebitAvailable", false);
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("134");
        fields(payload).put("200", "STAR");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("134");
        payload.put("serializedSegment", payload.path("serializedSegment").asText() + "\u001c");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void capkEnforcesProtocolScopedSequenceAndDeviceIdentityWithoutInventingPlacement() throws IOException {
        ObjectNode payload = payload("132");
        fields(payload).put("86", "200000");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload.putObject("context").put("protocol", "TCP_IP");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        fields(payload).put("102", "1D13500101001");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("132");
        fields(payload).put("48", "P");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void emvReusesBinaryLengthAndSensitiveTagChecksAndDifferentSectionCaps() throws IOException {
        for (String id : Set.of("130", "131")) {
            ObjectNode payload = payload(id);
            fields(payload).put("189", "008");
            assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
            payload = payload(id);
            fields(payload).put("189", "004").put("190", "5A021234");
            assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        }
        ObjectNode request = payload("130");
        ArrayNode tables = request.putArray("tables");
        tables.addObject().put("id", "001").put("length", "994").put("value", "A".repeat(994));
        assertThat(validator.validate(request).status()).isEqualTo(Status.INVALID);
        tables.removeAll();
        tables.addObject().put("id", "001").put("length", "985").put("value", "A".repeat(985));
        tables.addObject().put("id", "002").put("length", "985").put("value", "A".repeat(985));
        tables.addObject().put("id", "001").put("length", "017").put("value", "A".repeat(17));
        assertThat(validator.validate(request).status()).isEqualTo(Status.INVALID); // 2005, including final FS
        ObjectNode response = payload("131");
        response.set("tables", tables);
        assertThat(validator.validate(response).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void monerisUsesAppendixVRatherThanAcceptingArbitraryTlvContent() throws IOException {
        for (String id : Set.of("135", "136")) {
            ObjectNode payload = payload(id);
            fields(payload).put("213", "999001X");
            assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        }
    }

    @Test
    void networkTablesCheckEveryPublishedLengthWithoutInventingValueEnums() throws IOException {
        ObjectNode payload = payload("153");
        ArrayNode tables = (ArrayNode) payload.path("tables");
        for (int index = 0; index < tables.size(); index++) {
            ObjectNode mutated = payload.deepCopy();
            ((ObjectNode) mutated.path("tables").get(index)).put("length", "000");
            assertThat(validator.validate(mutated).status()).as("network table " + index).isEqualTo(Status.INVALID);
        }
        ((ObjectNode) tables.get(0)).put("id", "007");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        ((ObjectNode) tables.get(0)).remove("id");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void monerisBatchFieldsRejectMissingIdentityMalformedAmountsAndWrongMacWidth() throws IOException {
        for (String id : Set.of("139", "140", "141", "142")) {
            ObjectNode payload = payload(id);
            fields(payload).remove("207");
            assertThat(validator.validate(payload).status()).as(id).isEqualTo(Status.INVALID);
        }
        ObjectNode payload = payload("140");
        fields(payload).put("218", "100.00");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("142");
        fields(payload).put("210", "123");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void taxProductsRequireExactCompanionOrderAndTrueOmissionForNoTax() throws IOException {
        ObjectNode payload = payload("143");
        ObjectNode noTax = (ObjectNode) payload.path("taxProducts").get(0).path("taxes").get(0);
        noTax.put("type", "").put("amount", "");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("143");
        ((ObjectNode) payload.path("context")).putArray("segment102ProductCodes").add("500");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("143");
        payload.remove("serializedSegment");
        ObjectNode tax = (ObjectNode) payload.path("taxProducts").get(0).path("taxes").get(0);
        tax.put("flag", "E").put("type", "GST").put("amount", "97");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        tax.put("type", "QST");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        tax.put("type", "VAT");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void fleetChecksAuthorizerRestrictionsCompanionExclusionAndTlvFraming() throws IOException {
        ObjectNode payload = payload("145");
        ((ObjectNode) payload.path("context")).putArray("segmentsPresent").add("100").add("101").add("145");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("145");
        ((ObjectNode) payload.path("tables").get(0)).put("id", "006");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("146");
        ((ObjectNode) payload.path("tables").get(0)).put("id", "002");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("146");
        ((ObjectNode) payload.path("tables").get(0)).put("length", "001");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void wexChecksFillerGrammarAndFuelGroupsWithoutResolvingMaxLengthConflict() throws IOException {
        ObjectNode payload = payload("148");
        fields(payload).put("240", "99=100,50 G");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        fields(payload).put("240", "00:100,50 G");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        fields(payload).put("240", "00=100,50 G");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void fuelPriceRequestMatchesSourceExampleWireAndRejectsMissingFinalSeparator() throws IOException {
        ObjectNode payload = payload("149");
        payload.put("serializedSegment", "149\u001c045\u001c1D13500101001\u001c|CASS:03.00|CRSS:3.05|\u001c");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        payload.put("serializedSegment", "149\u001c045\u001c1D13500101001\u001c|CASS:03.00|CRSS:3.05|");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void marketBasketRemainsExternallyGatedButRejectsWrongDatasetCountsAndOversizedRawData() throws IOException {
        ObjectNode payload = payload("151");
        ((ArrayNode) payload.path("datasets")).addObject().put("type", "PU");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("152");
        payload.put("marketBasketData", "A".repeat(2301));
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void updaterReusesAppendixAdAndDoesNotInventTheMissingVau015Code() throws IOException {
        ObjectNode payload = payload("155");
        payload.remove("serializedSegment");
        ObjectNode table = (ObjectNode) payload.path("tables").get(0);
        table.put("id", "006").put("length", "006").put("value", "VAU015");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        table.put("value", "VAU016");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        table.put("id", "003").put("length", "004").put("value", "1326");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void evChecksAllTimeRangesCodesCapsFramingAndContext() throws IOException {
        ObjectNode payload = payload("156");
        payload.remove("serializedSegment");
        ArrayNode tables = (ArrayNode) payload.path("tables");
        for (String code : new String[]{"010", "023", "100", "200"}) {
            ((ObjectNode) tables.get(6)).put("value", code);
            assertThat(validator.validate(payload).status()).as(code).isEqualTo(Status.REVIEW_REQUIRED);
        }
        ((ObjectNode) tables.get(6)).put("value", "024");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("156");
        payload.remove("serializedSegment");
        ((ObjectNode) payload.path("tables").get(1)).put("value", "996000");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("156");
        payload.remove("serializedSegment");
        ((ObjectNode) payload.path("tables").get(0)).put("value", "N");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("156");
        ((ObjectNode) payload.path("context")).put("brand", "MASTERCARD");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("156");
        ((ObjectNode) payload.path("context")).putArray("segment102ProductCodes").add("001");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("156");
        ((ObjectNode) payload.path("tables").get(0)).put("id", "001");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        ((ObjectNode) payload.path("tables").get(0)).remove("id");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
    }
}
