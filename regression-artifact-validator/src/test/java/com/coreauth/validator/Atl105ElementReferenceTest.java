package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105ElementReference;
import com.coreauth.validator.validation.Atl105ElementReferenceValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105ElementReferenceTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final Path MATRIX = PACK.resolve("test-output/test-solution-independent-review/atl105-all-elements-reference.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void generates231ElementsAndPreservesEvidenceBoundaries() throws Exception {
        GenerateAtl105ElementReference.main(new String[]{PACK.toString()});
        JsonNode matrix = MAPPER.readTree(MATRIX.toFile());
        assertThat(matrix.path("elements")).hasSize(231);
        assertThat(matrix.path("definitionCount").asInt()).isEqualTo(232);
        assertThat(matrix.path("summary").path("sourceDefinedTransactionTypesListed").asInt()).isEqualTo(23);
        assertThat(matrix.path("summary").path("approvedBusinessRules").asInt()).isZero();
        assertThat(matrix.path("summary").path("absentFromTemplateMeansProhibited").asBoolean()).isFalse();
        assertThat(matrix.path("summary").path("omittedFamilySegmentReviewRows").asInt()).isPositive();
        assertThat(matrix.path("summary").path("transactionCodeApplicabilityExhaustivelyMappedForAllElements").asBoolean()).isFalse();
        JsonNode element118 = find(matrix, "118");
        assertThat(element118.path("definitions")).hasSize(2);
        assertThat(find(matrix, "78").path("transactionTypeCodes")).hasSize(23);
        assertThat(find(matrix, "84").path("transactionTypeCodes")).isEmpty();
        assertThat(find(matrix, "84").path("transactionTypeApplicabilityStatus").asText())
            .isEqualTo("NOT_EXHAUSTIVELY_MAPPED_REVIEW_REQUIRED");
        assertThat(FilesExist.contextCsv()).isTrue();
        assertThat(java.nio.file.Files.readString(PACK.resolve("test-output/test-solution-independent-review/atl105-element-contexts.csv")))
            .contains("REVIEW_REQUIRED_NOT_PROOF_OF_ABSENCE", "SEGMENT_LISTED_FIELD_NOT_LISTED");
    }

    @Test
    void validatorUsesExactShapesButLeavesUnlistedApplicabilityForReview() throws Exception {
        GenerateAtl105ElementReference.main(new String[]{PACK.toString()});
        var validator = new Atl105ElementReferenceValidator(PACK);
        var matrixResult = validator.validateMatrix();
        assertThat(matrixResult.structurallyValid()).isTrue();
        assertThat(matrixResult.elementCount()).isEqualTo(231);

        ObjectNode valid = observation("Financial Transaction Request", "100", "84", "001");
        ((ObjectNode) valid.path("segments").path(0).path("elements")).put("85", "100").put("86", "123456");
        valid.put("transactionTypeCode", "0");
        var validResult = validator.validate(valid);
        assertThat(validResult.status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(validResult.findings().toString()).contains("not proof of prohibition", "not exhaustively established");

        ObjectNode invalid = observation("Financial Transaction Request", "100", "84", "001");
        ((ObjectNode) invalid.path("segments").path(0).path("elements")).put("85", "101").put("86", "123456");
        assertThat(validator.validate(invalid).status()).isEqualTo(Status.INVALID);

        assertThat(validator.validate(observation("Unknown family", "100", "85", "100")).status())
            .isEqualTo(Status.REVIEW_REQUIRED);
    }

    private static ObjectNode observation(String family, String segment, String element, String value) {
        ObjectNode observation = MAPPER.createObjectNode();
        observation.put("specificationVersion", "2026-3");
        observation.put("messageFamily", family);
        observation.put("completeness", "PARTIAL");
        observation.putArray("segments").addObject().put("segment", segment).putObject("elements").put(element, value);
        return observation;
    }

    private static JsonNode find(JsonNode matrix, String id) {
        for (JsonNode element : matrix.path("elements")) if (id.equals(element.path("element").asText())) return element;
        throw new AssertionError("Element not found: " + id);
    }

    private static final class FilesExist {
        private static boolean contextCsv() {
            return PACK.resolve("test-output/test-solution-independent-review/atl105-element-contexts.csv").toFile().isFile();
        }
    }
}
