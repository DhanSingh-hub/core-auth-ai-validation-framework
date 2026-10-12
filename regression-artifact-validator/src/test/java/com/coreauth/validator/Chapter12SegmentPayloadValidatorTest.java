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
            // DL8 uses Element 24 (Data Type Indicator) rather than the universal Element 85/84
            // Segment Type/Segment Length convention used by every other Chapter 12 segment
            // (Section 12.49 field table); its own type/length/boundary checks are asserted in
            // dl8FloorLimitsValidatesFramingPerRidGroupsAndTheSpecialFlagGateWithoutInventingPlacement().
            if ("DL8".equals(id)) continue;
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
    void printDataDocumentsOriginHostAndCompanionSourceConflictAsNonAssertedMetadata() throws IOException {
        ObjectNode input = payload("115");
        assertThat(validator.validate(input).findings())
                .anyMatch(f -> f.ruleId().equals("SEG115-R-009") && f.scope().equals("origin-metadata")
                        && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.ruleId().equals("SEG115-R-010") && f.scope().equals("companion-source-conflict")
                        && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.ruleId().equals("SEG115-R-005") && f.scope().equals("source-length-conflict")
                        && f.status() == Status.REVIEW_REQUIRED);
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
    void tokenizationRejectsTotalEncodedLengthOverTheOneHundredEightySixCharacterCap() throws IOException {
        ObjectNode payload = payload("123");
        payload.remove("serializedSegment");
        var findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG123-R-003"))
                .singleElement().satisfies(f -> {
                    assertThat(f.scope()).isEqualTo("max-length");
                    assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED);
                });
        fields(payload).put("203", "X".repeat(200));
        findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG123-R-003"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
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
    void ebtDataValidatesHeaderWireDirectionAndConditionalFacetsWithoutInventingApplicability() throws IOException {
        ObjectNode payload = payload("103");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(validator.validate(payload).findings()).noneMatch(f -> f.status() == Status.INVALID)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-006") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-007") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-009") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-010") && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.ruleId().equals("SEG100-R-061") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-001") && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-002") && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-019") && f.status() == Status.REVIEW_REQUIRED);

        // Clerk ID must be numeric, not merely nonempty.
        payload = payload("103");
        fields(payload).put("18", "12A4567890");
        payload.remove("serializedSegment");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);

        // Voucher ID over 10 digits is rejected when populated.
        payload = payload("103");
        fields(payload).put("109", "12345678901");
        payload.remove("serializedSegment");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);

        // Origin context, when supplied, must be the device.
        payload = payload("103");
        payload.putObject("context").put("origin", "HOST");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("103");
        payload.putObject("context").put("origin", "device");
        assertThat(validator.validate(payload).findings()).anyMatch(f -> f.ruleId().equals("SEG103-R-019")
                && f.status() == Status.CHECKS_PASSED);

        // eWIC data present requires the N4 Segment Length width (SEG103-R-004 / SEG100-R-061).
        payload = payload("103");
        fields(payload).put("154", "EF00000000");
        payload.remove("serializedSegment");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG103-R-004") && f.status() == Status.INVALID)
                .anyMatch(f -> f.ruleId().equals("SEG100-R-061") && f.status() == Status.INVALID);
        fields(payload).put("84", "0024");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG103-R-004") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG100-R-061") && f.status() == Status.CHECKS_PASSED);

        // Segment Length over the 3,334 maximum is rejected even when well-formed.
        payload = payload("103");
        fields(payload).put("84", "3335");
        payload.remove("serializedSegment");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG103-R-005") && f.status() == Status.INVALID);

        // A RESPONSE direction requires wire agreement with no Field Separator between fields.
        payload = payload("103");
        ((ObjectNode) payload).put("direction", "RESPONSE");
        payload.put("serializedSegment", "1030241234567890");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG103-R-008") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-006") && f.status() == Status.CHECKS_PASSED);
        payload.put("serializedSegment", "103\u001c0241234567890");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG103-R-008") && f.status() == Status.INVALID);

        // Absent message direction leaves the wire facets explicitly review-required, never assumed.
        payload = payload("103");
        ((ObjectNode) payload).remove("direction");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG103-R-006") && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-007") && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.ruleId().equals("SEG103-R-008") && f.status() == Status.REVIEW_REQUIRED);
    }

    @Test
    void transArmorLoadValidatesTypeAndLengthWithoutInventingTheSourceExternalFieldLayout() throws IOException {
        ObjectNode payload = payload("116");
        var result = validator.validate(payload);
        assertThat(result.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(result.findings()).noneMatch(f -> f.status() == Status.INVALID)
                .anyMatch(f -> f.ruleId().equals("SEG116-R-003") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG116-R-004") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEG116-R-006") && f.status() == Status.REVIEW_REQUIRED);
        // The field layout beyond Segment Type/Segment Length is an external-document gap, so this
        // segment can never reach CHECKS_PASSED overall (SEG116-R-001/002/005/007/008/009 are
        // never referenced); that is the documented, not fabricated, outcome.
        assertThat(result.unassessedCatalogRules()).contains("SEG116-R-001", "SEG116-R-002",
                "SEG116-R-005", "SEG116-R-007", "SEG116-R-008", "SEG116-R-009");

        // Segment Type must be the fixed value 116.
        payload = payload("116");
        fields(payload).put("85", "119");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);

        // Segment Length is N2, range 01-50; zero and over-50 values are rejected.
        payload = payload("116");
        fields(payload).put("84", "00");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);
        payload = payload("116");
        fields(payload).put("84", "51");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG116-R-004") && f.status() == Status.INVALID);
        payload = payload("116");
        fields(payload).put("84", "50");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEG116-R-004") && f.status() == Status.CHECKS_PASSED);
    }

    @Test
    void emvFloorLimitsValidatesFramingAndPerRidGroupsWithoutInventingTheSpecialFlagPlacement() throws IOException {
        ObjectNode payload = payload("DL8");
        var result = validator.validate(payload);
        assertThat(result.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(result.findings()).noneMatch(f -> f.status() == Status.INVALID)
                .anyMatch(f -> f.ruleId().equals("SEGDL8-R-002") && f.scope().equals("data-type-indicator") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEGDL8-R-002") && f.scope().equals("segment-length-exclusive") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEGDL8-R-003") && f.status() == Status.CHECKS_PASSED)
                .anyMatch(f -> f.ruleId().equals("SEGDL8-R-001") && f.status() == Status.REVIEW_REQUIRED);
        // R-004 (element formats) and R-005 (segment-length-whole-groups restatement) are anchored to
        // Section 13.2, not 12.49, and are never referenced here, so DL8 can never reach CHECKS_PASSED
        // overall - that is the documented, not fabricated, outcome (matches the Segment 103/116 pattern).
        assertThat(result.unassessedCatalogRules()).contains("SEGDL8-R-004", "SEGDL8-R-005");

        // Data Type Indicator (Element 24) is the fixed value '%'.
        payload = payload("DL8");
        fields(payload).put("24", "&");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);

        // Segment Length (Element 84, N3) must exclusively equal 26 bytes per RID group.
        payload = payload("DL8");
        fields(payload).put("84", "999");
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);

        // The repeating Floor Limit Data group array is required, even when empty.
        payload = payload("DL8");
        ((ObjectNode) payload).set("floorLimitGroups", mapper.createArrayNode());
        assertThat(validator.validate(payload).status()).isEqualTo(Status.INVALID);

        // Each group is RID (AN10) + Stand-in Indicator (N1) + Floor Limit (N12) + BUYPASS RID Card Type (AN3).
        payload = payload("DL8");
        ArrayNode groups = mapper.createArrayNode();
        groups.addObject().put("rid", "BAD").put("standInIndicator", "9").put("floorLimit", "abc").put("cardType", "XY");
        ((ObjectNode) payload).set("floorLimitGroups", groups);
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEGDL8-R-003") && f.status() == Status.INVALID);

        // Up to 24 groups (624 bytes) is the stated maximum; 25 groups overflows it.
        payload = payload("DL8");
        ArrayNode overflow = mapper.createArrayNode();
        for (int i = 0; i < 25; i++) {
            overflow.addObject().put("rid", "A000000003").put("standInIndicator", "1").put("floorLimit", "000000050000").put("cardType", "001");
        }
        ((ObjectNode) payload).set("floorLimitGroups", overflow);
        fields(payload).put("84", "650");
        assertThat(validator.validate(payload).findings())
                .anyMatch(f -> f.ruleId().equals("SEGDL8-R-003") && f.status() == Status.INVALID);
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
    void capkEnforcesDistinctMaxLengthCapSeparateFromThreeDigitWidthCheck() throws IOException {
        ObjectNode payload = payload("132");
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG132-R-004") && f.scope().equals("length-cap-77"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
        fields(payload).put("84", "100");
        Chapter12SegmentPayloadValidator.Result result = validator.validate(payload);
        assertThat(result.status()).isEqualTo(Status.INVALID);
        assertThat(result.findings())
                .filteredOn(f -> f.ruleId().equals("SEG132-R-004") && f.scope().equals("length-cap-77"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
        // Header's own length-width-range check embeds the same 77 cap, so both R-003 and the
        // newly distinct R-004 fail together on an over-cap length; they are intentionally not
        // independent of each other for this boundary, only separately and correctly tagged.
        assertThat(result.findings())
                .filteredOn(f -> f.ruleId().equals("SEG132-R-003") && f.scope().equals("length-width-range"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
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
    void emvCardSequenceNumberAcceptsZeroPaddedRangeAndThreeSpacesAndRejectsOutOfRangeOrMalformed() throws IOException {
        for (String value : java.util.List.of("000", "012", "099")) {
            ObjectNode payload = payload("130");
            fields(payload).put("188", value);
            assertThat(validator.validate(payload).findings()).filteredOn(f -> f.scope().equals("element-188"))
                    .singleElement().satisfies(f -> assertThat(f.status()).as(value).isEqualTo(Status.CHECKS_PASSED));
        }
        ObjectNode spaceFilled = payload("130");
        fields(spaceFilled).put("188", "   ");
        assertThat(validator.validate(spaceFilled).findings()).filteredOn(f -> f.scope().equals("element-188"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
        for (String value : java.util.List.of("100", "999", "AB1", "1", "0000", "  ")) {
            ObjectNode payload = payload("130");
            fields(payload).put("188", value);
            assertThat(validator.validate(payload).status()).as(value).isEqualTo(Status.INVALID);
            assertThat(validator.validate(payload).findings()).filteredOn(f -> f.scope().equals("element-188"))
                    .singleElement().satisfies(f -> assertThat(f.status()).as(value).isEqualTo(Status.INVALID));
        }
        ObjectNode omitted = payload("130");
        assertThat(validator.validate(omitted).findings()).noneMatch(f -> f.scope().equals("element-188"));
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
    void taxProductsEarlyTerminationDelimiterAndEnumerationScopeAreIndependentlyReconciled() throws IOException {
        ObjectNode payload = payload("143");
        // Base fixture has 1 of up to 3 taxes: early termination applies and the wire already
        // closes the single tax with a Field Separator, not the continuation '\' delimiter.
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG143-R-009") && f.scope().equals("early-termination-separator"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
        payload.put("serializedSegment", payload.path("serializedSegment").asText().substring(0,
                payload.path("serializedSegment").asText().length() - 1) + "9");
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG143-R-009") && f.scope().equals("early-termination-separator"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
        payload = payload("143");
        payload.remove("serializedSegment");
        ArrayNode taxes = (ArrayNode) payload.path("taxProducts").get(0).path("taxes");
        taxes.removeAll();
        taxes.addObject().put("flag", "N");
        taxes.addObject().put("flag", "N");
        taxes.addObject().put("flag", "N");
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG143-R-009") && f.scope().equals("applicability"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.REVIEW_REQUIRED));
        payload = payload("143");
        payload.remove("serializedSegment");
        ObjectNode canadaTax = (ObjectNode) payload.path("taxProducts").get(0).path("taxes").get(0);
        canadaTax.put("flag", "I").put("type", "GST").put("amount", "000000100");
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG143-R-007") && f.scope().equals("tax-type-enumeration"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG143-R-007") && f.scope().equals("element-type"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
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
        payload.put("messageFamily", "Financial Transaction Response").putObject("context").put("authorizer", "WEX_OTR");
        assertThat(validator.validate(payload).findings()).filteredOn(f -> f.scope().equals("routing"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
    }

    @Test
    void fleetMasksCheckEveryPublishedGrammarFamilyAndRejectMalformedEntries() throws IOException {
        for (String mask : java.util.List.of("?", "A", "B", "N", "I", "O", "Z", "TN", "TS", "M123", "X456", "V1100", "PUN####", "P@#*", "N;TS;M1;X25")) {
            var input = payload("146");
            String value = "|DRID:" + mask + "|";
            var tables = input.putArray("tables");
            tables.addObject().put("id", "004").put("length", String.format(java.util.Locale.ROOT, "%03d", value.length())).put("value", value);
            var original = input.deepCopy();
            assertThat(validator.validate(input).findings()).filteredOn(f -> f.scope().equals("edit-mask-grammar"))
                    .singleElement().satisfies(f -> assertThat(f.status()).as(mask).isEqualTo(Status.CHECKS_PASSED));
            assertThat(input).isEqualTo(original);
        }
        for (String value : java.util.List.of("|DRID:Q|", "|DRID:N;|", "|DRID:TX|", "|DRID:P!|", "|DRID:M|", "|DRID:V|",
                "|DRID:P|", "|TOOLONG:N|", "|DRID:|", "|:N|", "|DRID:N||", "DRID:N|")) {
            var input = payload("146");
            input.putArray("tables").addObject().put("id", "004").put("length", String.format(java.util.Locale.ROOT, "%03d", value.length())).put("value", value);
            assertThat(validator.validate(input).status()).as(value).isEqualTo(Status.INVALID);
        }
    }

    @Test
    void emvChecksumRequiresActualMatchedRequestAndWellTypedEvidence() throws IOException {
        var response = payload("131");
        var request = payload("130");
        fields(request).put("187", fields(response).path("187").asText());
        response.putObject("history").putObject("emv").set("request", request);
        assertThat(validator.validate(response).findings()).filteredOn(f -> f.scope().equals("checksum-echo"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
        request.put("segment", "132");
        assertThat(validator.validate(response).status()).isEqualTo(Status.INVALID);
        response.putArray("history");
        assertThat(validator.validate(response).status()).isEqualTo(Status.INVALID);
        response.putObject("history").putArray("emv");
        assertThat(validator.validate(response).status()).isEqualTo(Status.INVALID);
        request = payload("130");
        fields(request).put("187", fields(payload("131")).path("187").asText());
        request.putObject("context").put("loadedCapkChecksum", 1);
        assertThat(validator.validate(request).status()).isEqualTo(Status.INVALID);
        request.putArray("context");
        assertThat(validator.validate(request).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void routedChildrenCannotReplaceAuthoritativeParentContextOrHistory() throws IOException {
        for (String id : java.util.List.of("148", "131")) {
            var child = payload(id);
            var parent = mapper.createObjectNode().put("specificationVersion", "2026-3")
                    .put("messageFamily", "Financial Transaction Response").put("completeness", "PARTIAL");
            parent.putObject("elements").put("55", "ATL105").put("63", "01");
            parent.putArray("segments").addObject().put("segment", id).put("dataSection", 3).put("fieldNumber", 4).set("observation", child);
            String target;
            if ("148".equals(id)) {
                child.putObject("context").put("authorizer", "WEX");
                parent.putObject("context").put("authorizer", "COMDATA");
                target = "SEG148-R-001";
            } else {
                var request = payload("130");
                fields(request).put("187", "0000000000000000000000002");
                parent.putObject("history").putObject("emv").set("request", request);
                child.putObject("history").putObject("emv").set("request", payload("130"));
                target = "SEG131-R-007";
            }
            var original = parent.deepCopy();
            var result = new com.coreauth.validator.canonical.Chapter11MessageLayoutValidator().validate(parent);
            assertThat(result.findings()).anyMatch(f -> f.scope().equals("segment-context") && f.status() == Status.INVALID)
                    .anyMatch(f -> f.ruleId().equals(target) && f.status() == Status.INVALID);
            assertThat(parent).isEqualTo(original);
        }
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
    void updaterCardStatusEnumerationRejectsUnpublishedCodesAndAcceptsAllFivePublishedOnes() throws IOException {
        ObjectNode payload = payload("155");
        payload.remove("serializedSegment");
        ObjectNode table = (ObjectNode) payload.path("tables").get(0);
        table.put("id", "004").put("length", "001");
        for (String status : new String[]{"A", "E", "Q", "C", "U"}) {
            table.put("value", status);
            assertThat(validator.validate(payload).findings()).as(status)
                    .filteredOn(f -> f.ruleId().equals("SEG155-R-004"))
                    .singleElement().satisfies(f -> {
                        assertThat(f.scope()).isEqualTo("card-status-enumeration");
                        assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED);
                    });
        }
        table.put("value", "Z");
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG155-R-004"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
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

    @Test
    void evSectionCapRejectsAnyTableValueLongerThanTwelveCharacters() throws IOException {
        ObjectNode payload = payload("156");
        payload.remove("serializedSegment");
        ((ArrayNode) payload.path("tables")).addObject().put("id", "13").put("length", "020").put("value", "A".repeat(20));
        assertThat(validator.validate(payload).findings())
                .filteredOn(f -> f.ruleId().equals("SEG156-R-009") && f.scope().equals("section-cap"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
    }

    @Test
    void moneris139WireReviewUsesItsOwnDistinctRuleIdNotTheHeaderRule() throws IOException {
        // Regression test for a conflation bug: the wire/serialization review for Segment 139
        // previously reused ruleId SEG139-R-002 (header type/length) instead of its own
        // distinct catalog rule SEG139-R-004 (separator-behavior-undocumented).
        ObjectNode payload = payload("139");
        var findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.scope().equals("wire"))
                .singleElement().satisfies(f -> assertThat(f.ruleId()).isEqualTo("SEG139-R-004"));
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG139-R-002")).isNotEmpty()
                .allSatisfy(f -> assertThat(f.scope()).isNotEqualTo("wire"));
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG139-R-004")).hasSize(1);
    }

    @Test
    void marketBasket152EmitsTheTrailingFieldSeparatorWireReview() throws IOException {
        // Regression test for a missing-Finding gap: catalog rule SEG152-R-001 (trailing
        // Field Separator) previously had no corresponding Finding anywhere in the code.
        ObjectNode payload = payload("152");
        var findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG152-R-001"))
                .singleElement().satisfies(f -> {
                    assertThat(f.scope()).isEqualTo("wire");
                    assertThat(f.status()).isEqualTo(Status.REVIEW_REQUIRED);
                });
        // Segment 151 (request side) must not emit this response-only rule.
        ObjectNode requestPayload = payload("151");
        assertThat(validator.validate(requestPayload).findings())
                .noneMatch(f -> f.ruleId().equals("SEG152-R-001"));
    }

    @Test
    void marketBasket151EmitsFieldSlotPlacementAndFieldSeparatorReviewsAndNotTheResponseOnlyRule() throws IOException {
        // Regression test for two previously entirely-missing Findings on the request side
        // (Segment 151): SEG151-R-001 (any Data Section No. 3 field slot / device origination,
        // an envelope-level placement fact not independently verifiable from this segment-level
        // observation) and SEG151-R-003 (Field Separator between each of the 3 fields).
        ObjectNode payload = payload("151");
        var findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG151-R-001"))
                .singleElement().satisfies(f -> {
                    assertThat(f.scope()).isEqualTo("placement");
                    assertThat(f.status()).isEqualTo(Status.REVIEW_REQUIRED);
                });
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG151-R-003"))
                .singleElement().satisfies(f -> {
                    assertThat(f.scope()).isEqualTo("wire");
                    assertThat(f.status()).isEqualTo(Status.REVIEW_REQUIRED);
                });
        // Segment 151's own trailing-separator rule is SEG152-R-001 on the response side only.
        assertThat(findings).noneMatch(f -> f.ruleId().equals("SEG152-R-001"));
    }

    @Test
    void marketBasket151ConfirmsTheFieldSeparatorWireFormatWhenSerializedSegmentIsSupplied() throws IOException {
        // The source text (serialization-wire-format-flow.md) specifies an exact emittable
        // request format: SegmentType FS SegmentLengthIndicator FS MarketBasketData FS. When
        // serializedSegment evidence is present this is now a concrete pass/fail check (not
        // merely a review-only observation), mirroring the Segment 130/131 EMV wire checks.
        ObjectNode payload = payload("151");
        fields(payload).put("84", "0012");
        payload.put("serializedSegment", "151\u001c0012\u001cDV\u001c");
        var findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG151-R-003") && f.scope().equals("wire-order-delimiters"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG151-R-003") && f.scope().equals("wire-declared-length"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));

        // Missing a Field Separator breaks the prescribed order/delimiters.
        ObjectNode broken = payload("151");
        fields(broken).put("84", "0012");
        broken.put("serializedSegment", "1510012\u001cDV\u001c");
        assertThat(validator.validate(broken).findings())
                .filteredOn(f -> f.ruleId().equals("SEG151-R-003") && f.scope().equals("wire-order-delimiters"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
    }

    @Test
    void marketBasket152ConfirmsTheNoSeparatorExceptTrailingWireFormatWhenSerializedSegmentIsSupplied() throws IOException {
        // The source text specifies response format: SegmentType SegmentLengthIndicator
        // MarketBasketData FS - i.e. no separators except a single trailing one, distinct
        // from Segment 151's request-side format.
        ObjectNode payload = payload("152");
        fields(payload).put("84", "0010");
        payload.put("serializedSegment", "1520010DV\u001c");
        var findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG152-R-001") && f.scope().equals("wire-order-delimiters"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.CHECKS_PASSED));

        // An extraneous Field Separator (not matching the no-separator-until-trailing format) is invalid.
        ObjectNode broken = payload("152");
        fields(broken).put("84", "0010");
        broken.put("serializedSegment", "152\u001c0010DV\u001c");
        assertThat(validator.validate(broken).findings())
                .filteredOn(f -> f.ruleId().equals("SEG152-R-001") && f.scope().equals("wire-order-delimiters"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.INVALID));
    }

    @Test
    void attributes134EmitsAllSevenCatalogRulesIncludingTheConditionalSignatureDebitAvailabilityCheck() throws IOException {
        // Segment 134 (Section 12.23) had only SEG134-R-004 linked to executed evidence; the
        // other 6 catalog rules (direction, wire x2 scopes, encoded-size, availability,
        // element-199, element-200) were already implemented in attributes() but never asserted.
        ObjectNode payload = payload("134");
        var findings = validator.validate(payload).findings();
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG134-R-001"))
                .singleElement().satisfies(f -> assertThat(f.scope()).isEqualTo("direction"));
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG134-R-002"))
                .extracting(Chapter12SegmentPayloadValidator.Finding::scope)
                .containsExactlyInAnyOrder("wire-order-delimiters", "wire-declared-length");
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG134-R-003"))
                .singleElement().satisfies(f -> assertThat(f.scope()).isEqualTo("encoded-size"));
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG134-R-004"))
                .extracting(Chapter12SegmentPayloadValidator.Finding::scope)
                .containsExactlyInAnyOrder("element-85", "length-width-range", "source-conflict");
        // SEG134-R-005's conditional "availability" scope only fires when Element 198 is 'X'.
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG134-R-005"))
                .singleElement().satisfies(f -> assertThat(f.scope()).isEqualTo("element-198"));
        fields(payload).put("198", "X");
        var withSignatureDebit = validator.validate(payload).findings();
        assertThat(withSignatureDebit).filteredOn(f -> f.ruleId().equals("SEG134-R-005") && f.scope().equals("availability"))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(Status.REVIEW_REQUIRED));
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG134-R-006"))
                .singleElement().satisfies(f -> assertThat(f.scope()).isEqualTo("element-199"));
        assertThat(findings).filteredOn(f -> f.ruleId().equals("SEG134-R-007"))
                .singleElement().satisfies(f -> assertThat(f.scope()).isEqualTo("element-200"));
    }
}
