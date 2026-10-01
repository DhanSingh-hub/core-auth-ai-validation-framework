package com.coreauth.validator;

import com.coreauth.validator.validation.Atl105ElementValueValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Files;
import com.coreauth.validator.coverage.Atl105ElementInventory;
import com.coreauth.validator.coverage.Atl105ElementProfileGenerator;
import static org.assertj.core.api.Assertions.assertThat;

class Atl105ElementValueValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACK = Path.of("specifications", "ATL105");

    @Test
    void everySourceElementRequiresReviewOutsideItsVerifiedContext() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var inventory = new Atl105ElementInventory().extract(Files.readString(PACK.resolve("docs/specs/extracted_text.txt")));
        for (var element : inventory.path("elements")) {
            String id = element.path("element").asText();
            assertThat(validator.validate(observation("Unverified Request", "UNVERIFIED", id, "0")).status())
                .as("element " + id).isEqualTo(Status.REVIEW_REQUIRED);
        }
    }

    @Test
    void baselineMutationsAreDetectedForEveryExtractedElement() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var inventory = new Atl105ElementInventory().extract(Files.readString(PACK.resolve("docs/specs/extracted_text.txt")));
        int checked = 0;
        for (var element : inventory.path("elements")) {
            var constraint = element.path("definitions").path(0).path("constraints");
            if (element.path("definitions").size() != 1 || !"EXTRACTED".equals(constraint.path("extractionStatus").asText())) continue;
            String id = element.path("element").asText();
            boolean numeric = "N".equals(constraint.path("characterType").asText());
            String valid = (numeric ? "1" : "A").repeat(constraint.path("maxLength").asInt());
            assertThat(validator.validate(observation("Unverified Request", "UNVERIFIED", id, valid)).status()).as("valid " + id).isEqualTo(Status.REVIEW_REQUIRED);
            assertThat(validator.validate(observation("Unverified Request", "UNVERIFIED", id, valid + (numeric ? "1" : "A"))).status()).as("over-length " + id).isEqualTo(Status.INVALID);
            if (numeric) assertThat(validator.validate(observation("Unverified Request", "UNVERIFIED", id, "A")).status()).as("non-digit " + id).isEqualTo(Status.INVALID);
            checked++;
        }
        assertThat(checked).isGreaterThanOrEqualTo(220);
    }

    @Test
    void segmentLengthAndTypeUseSourceWidthRangeAndDeclaredSegment() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        assertThat(validator.validate(segment("101", "061", "101")).status()).isEqualTo(Status.CHECKS_PASSED);
        assertThat(validator.validate(segment("101", "062", "101")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(segment("101", "0061", "101")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(segment("101", "061", "100")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(segment("103", "3334", "103")).status()).isEqualTo(Status.CHECKS_PASSED);
        assertThat(validator.validate(segment("103", "3335", "103")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(segment("103", "334", "103")).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void segmentCountMustEqualSuppliedSegmentsAndRequiredSegmentsMustBePresent() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var input = message("2", "2");
        assertThat(validator.validate(input).status()).isEqualTo(Status.CHECKS_PASSED);
        assertThat(validator.validate(message("3", "2")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(message("0", "0")).status()).isEqualTo(Status.INVALID);
        var noCount = message("2", "2");
        noCount.remove("qualifiers");
        assertThat(validator.validate(noCount).status()).isEqualTo(Status.REVIEW_REQUIRED);
        input.putArray("segmentsPresent").add("101").add("102");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        var unlisted = message("2", "2");
        unlisted.putArray("segmentsPresent").add("100").add("130");
        assertThat(validator.validate(unlisted).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void partialObservationsNeverTurnMissingRequiredElementsIntoPasses() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var partial = observation("Financial Transaction Request", "100", "86", "123456");
        partial.put("completeness", "PARTIAL");
        assertThat(validator.validate(partial).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(validator.validate(observation("Financial Transaction Request", "100", "86", "123456")).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void generatedProfilesMatchCurrentSourceAndCatalogs() throws Exception {
        var review = MAPPER.createArrayNode();
        var generated = new Atl105ElementProfileGenerator().generate(PACK, review);
        var stored = MAPPER.readTree(PACK.resolve("docs/specs/kb/elements/validation-profiles.json").toFile());
        var storedGenerated = new java.util.ArrayList<String>();
        stored.path("profiles").forEach(profile -> { if (profile.path("generated").asBoolean()) storedGenerated.add(profile.toString()); });
        assertThat(storedGenerated).containsExactlyElementsOf(generated.stream().map(Object::toString).toList());
        assertThat(stored.path("generationReview").toString()).isEqualTo(review.toString());
        assertThat(review.toString()).contains("\"segment\":\"116\"", "\"segment\":\"132\"", "\"segment\":\"134\"");
    }

    @Test
    void verifiesKnownValueAndRejectsInvalidAndMissingRequiredValue() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var input = message("1", "1");
        assertThat(validator.validate(input).status()).isEqualTo(Status.CHECKS_PASSED);
        ((ObjectNode) input.path("elements")).put("55", "ATL999");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        ((ObjectNode) input.path("elements")).remove("55");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void multithreadedRangeIsContextualAndIncludesBothBoundaries() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        for (String value : new String[]{"100000", "199999", "099999", "200000"}) {
            var input = observation("CA Public Key File Load Request", "132", "86", value);
            ((ObjectNode) input.path("elements")).put("85", "132");
            input.putObject("qualifiers").put("protocol", "MULTITHREADED_DIAL");
            assertThat(validator.validate(input).status()).as(value)
                .isEqualTo(value.startsWith("1") ? Status.CHECKS_PASSED : Status.INVALID);
        }
        var missingContext = observation("CA Public Key File Load Request", "132", "86", "100000");
        ((ObjectNode) missingContext.path("elements")).put("85", "132");
        assertThat(validator.validate(missingContext).status()).isEqualTo(Status.REVIEW_REQUIRED);
        var financial = segment("100", "218", "100");
        ((ObjectNode) financial.path("elements")).put("86", "200000");
        assertThat(validator.validate(financial).status()).isEqualTo(Status.CHECKS_PASSED);
    }

    @Test
    void unsupportedElementsAndContextsCannotBeReportedAsPassed() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        for (String owner : new String[]{"110", "145"}) {
            var input = observation("Financial Transaction Request", owner, "239", "Y");
            input.put("completeness", "PARTIAL");
            assertThat(validator.validate(input).status()).as(owner).isEqualTo(Status.REVIEW_REQUIRED);
        }
        var unknownFamily = observation("Unknown Request", "100", "55", "ATL105");
        unknownFamily.put("completeness", "PARTIAL");
        assertThat(validator.validate(unknownFamily).status())
            .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(validator.validate(null).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void preservesStringRepresentationAndDoesNotLeakValues() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var input = observation("Financial Transaction Request", "100", "86", "PRIVATEVALUE");
        var result = validator.validate(input);
        assertThat(result.status()).isEqualTo(Status.INVALID);
        assertThat(result.toString()).doesNotContain("PRIVATEVALUE");
        ((ObjectNode) input.path("elements")).put("86", 100001);
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    private static ObjectNode segment(String segment, String length, String type) {
        ObjectNode input = observation("Financial Transaction Request", segment, "84", length);
        ((ObjectNode) input.path("elements")).put("85", type);
        return input;
    }

    private static ObjectNode message(String count, String supplied) {
        ObjectNode input = observation("Financial Transaction Request", "MESSAGE", "55", "ATL105");
        ((ObjectNode) input.path("elements")).put("63", count);
        input.putObject("qualifiers").put("dataSegmentCount", supplied);
        return input;
    }

    private static ObjectNode observation(String family, String segment, String element, String value) {
        ObjectNode input = MAPPER.createObjectNode();
        input.put("specificationVersion", "2026-3");
        input.put("messageFamily", family);
        input.put("segment", segment);
        input.putObject("elements").put(element, value);
        return input;
    }
}