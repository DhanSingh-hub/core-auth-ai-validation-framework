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
    void optionalSegment101OdometerChecksOnlySuppliedNumericValues() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var valid = observation("Financial Transaction Request", "101", "64", "12345678");
        ((ObjectNode) valid.path("elements")).put("84", "061").put("85", "101");
        assertThat(validator.validate(valid).findings())
            .anySatisfy(finding -> assertThat(finding.ruleId()).isEqualTo("SEG101-R-009"));
        assertThat(validator.validate(valid).status()).isEqualTo(Status.CHECKS_PASSED);

        for (String value : new String[]{"123456789", "12A"}) {
            var invalid = observation("Financial Transaction Request", "101", "64", value);
            ((ObjectNode) invalid.path("elements")).put("84", "061").put("85", "101");
            assertThat(validator.validate(invalid).findings())
                .anySatisfy(finding -> {
                    assertThat(finding.ruleId()).isEqualTo("SEG101-R-009");
                    assertThat(finding.status()).isEqualTo(Status.INVALID);
                });
        }

        var omitted = observation("Financial Transaction Request", "101", "55", "ATL105");
        ((ObjectNode) omitted.path("elements")).put("84", "061").put("85", "101");
        assertThat(validator.validate(omitted).findings())
            .noneSatisfy(finding -> assertThat(finding.ruleId()).isEqualTo("SEG101-R-009"));
    }

    @Test
    void segmentCountMustEqualSuppliedSegmentsAndRequiredSegmentsMustBePresent() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var input = message("2", "2");
        input.putArray("segmentsPresent").add("100").add("101");
        var inputQualifiers = (ObjectNode) input.path("qualifiers");
        inputQualifiers.putObject("segmentOccurrenceCounts").put("100", 1).put("101", 1);
        inputQualifiers.put("untypedSegmentObjectCount", 0);
        assertThat(validator.validate(input).status()).isEqualTo(Status.CHECKS_PASSED);
        assertThat(validator.validate(message("3", "2")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(message("0", "0")).status()).isEqualTo(Status.INVALID);
        var noCount = message("2", "2");
        noCount.remove("qualifiers");
        assertThat(validator.validate(noCount).status()).isEqualTo(Status.REVIEW_REQUIRED);
        input.putArray("segmentsPresent").add("101").add("102");
        inputQualifiers.putObject("segmentOccurrenceCounts").put("101", 1).put("102", 1);
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        var unlisted = message("2", "2");
        unlisted.putArray("segmentsPresent").add("100").add("130");
        assertThat(validator.validate(unlisted).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void missingSegment100UsesSourceRuleIdWithoutClaimingDuplicateCoverage() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var input = message("1", "1");
        input.putArray("segmentsPresent");
        var result = validator.validate(input);
        assertThat(result.findings()).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-001");
            assertThat(finding.status()).isEqualTo(Status.INVALID);
            assertThat(finding.reason()).contains("Required Segment 100 absent");
        });
        var present = message("1", "1");
        present.putArray("segmentsPresent").add("100");
        ((ObjectNode) present.path("qualifiers")).putObject("segmentOccurrenceCounts").put("100", 1);
        assertThat(validator.validate(present).findings()).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-001");
            assertThat(finding.status()).isEqualTo(Status.CHECKS_PASSED);
            assertThat(finding.reason()).contains("occurs exactly once");
        });
    }

    @Test
    void companionSegment101DuplicateRuleUsesOccurrenceEvidence() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var input = message("2", "2");
        input.putArray("segmentsPresent").add("100").add("101");
        var qualifiers = (ObjectNode) input.path("qualifiers");
        qualifiers.putObject("segmentOccurrenceCounts").put("100", 1).put("101", 1);
        qualifiers.put("untypedSegmentObjectCount", 0);
        assertThat(validator.validate(input).findings()).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-008");
            assertThat(finding.status()).isEqualTo(Status.CHECKS_PASSED);
        });

        ((ObjectNode) qualifiers.path("segmentOccurrenceCounts")).put("101", 2);
        assertThat(validator.validate(input).findings()).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-008");
            assertThat(finding.status()).isEqualTo(Status.INVALID);
            assertThat(finding.reason()).contains("occurs 2 times");
        });

        ((ObjectNode) qualifiers.path("segmentOccurrenceCounts")).put("101", 1);
        qualifiers.put("untypedSegmentObjectCount", 1);
        assertThat(validator.validate(input).findings()).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-008");
            assertThat(finding.status()).isEqualTo(Status.REVIEW_REQUIRED);
        });
    }

    @Test
    void missingStructureEvidenceReviewsBothRequiredAndDuplicateRules() throws Exception {
        var validator = new Atl105ElementValueValidator(PACK);
        var findings = validator.validate(message("1", "1")).findings();
        assertThat(findings).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-001");
            assertThat(finding.status()).isEqualTo(Status.REVIEW_REQUIRED);
        });
        assertThat(findings).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-008");
            assertThat(finding.status()).isEqualTo(Status.REVIEW_REQUIRED);
        });
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
        input.putArray("segmentsPresent").add("100");
        ObjectNode qualifiers = (ObjectNode) input.path("qualifiers");
        qualifiers.putObject("segmentOccurrenceCounts").put("100", 1);
        qualifiers.put("untypedSegmentObjectCount", 0);
        var inputResult = validator.validate(input);
        assertThat(inputResult.status()).as("findings: %s", inputResult.findings()).isEqualTo(Status.CHECKS_PASSED);
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