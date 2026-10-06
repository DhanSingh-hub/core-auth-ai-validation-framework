package com.coreauth.validator;

import com.coreauth.validator.canonical.Atl105ResponseCodeFamilyOracle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105ResponseCodeFamilyOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path MATRIX = Path.of("specifications", "ATL105", "test-output", "test-json", "atl105-response-code-family-matrix.json");

    @Test
    void oracleCoversEveryCodeAndFamilyInTheSourceDerivedMatrix() throws Exception {
        JsonNode matrix = MAPPER.readTree(MATRIX.toFile());
        Set<String> allCodes = new HashSet<>();
        Set<String> oracleCodes = new HashSet<>();

        for (JsonNode entry : matrix.path("codes")) {
            String code = entry.path("code").asText();
            allCodes.add(code);
            for (JsonNode family : entry.path("families")) {
                String familyName = family.asText();
                assertThat(Atl105ResponseCodeFamilyOracle.expectedCodes(familyName)).contains(code);
                assertThat(new Atl105ResponseCodeFamilyOracle().validate(familyName, code).errors()).isEmpty();
                oracleCodes.addAll(Atl105ResponseCodeFamilyOracle.expectedCodes(familyName));
            }
        }

        assertThat(allCodes).hasSize(matrix.path("uniqueCodes").asInt());
        assertThat(oracleCodes).containsExactlyInAnyOrderElementsOf(allCodes);
        assertThat(Atl105ResponseCodeFamilyOracle.expectedCodes("financial"))
            .containsExactlyInAnyOrderElementsOf(Set.of("0", "1", "2", "3", "4", "F", "S"));
    }

    @Test
    void rejectsCodesOutsideTheSelectedFamily() {
        assertThat(new Atl105ResponseCodeFamilyOracle().validate("totals", "0").errors())
            .anyMatch(error -> error.reason().contains("not documented for family totals"));
        assertThat(new Atl105ResponseCodeFamilyOracle().validate("financial", "8").errors()).isNotEmpty();
    }

    @Test
    void requiresMessageFamilyAndWarnsForOverlappingCodes() {
        var oracle = new Atl105ResponseCodeFamilyOracle();
        assertThat(oracle.validate(null, "L").errors()).isNotEmpty();
        assertThat(oracle.familiesForCode("L")).containsExactlyInAnyOrder("transarmor", "emv-key-load");
        assertThat(oracle.validate("emv-key-load", "L").warnings()).anyMatch(warning -> warning.contains("multiple source-defined meanings"));
        assertThat(oracle.familiesForCode("M")).containsExactlyInAnyOrder("totals", "emv-key-load");
        assertThat(oracle.familiesForCode("X")).containsExactlyInAnyOrder("proprietary-load", "emv-key-load");
    }

    @Test
    void codeZeroTrainingRecordSeparatesMessageFamiliesFromSegmentContext() throws Exception {
        Path coveragePath = Path.of("specifications", "ATL105", "docs", "specs", "kb", "element-83-response-code", "coverage", "element-83-code-0-training.json");
        JsonNode coverage = MAPPER.readTree(coveragePath.toFile());

        assertThat(coverage.path("responseCode").asText()).isEqualTo("0");
        assertThat(coverage.path("purchaseTransactionTypes")).hasSize(3);
        assertThat(coverage.path("responseFamilies")).hasSize(4);
        assertThat(coverage.path("responseFamilies").get(1).path("responseAliasOf").asText())
            .isEqualTo("Financial Transaction Response");
        assertThat(coverage.path("responseFamilies").get(2).path("responseAliasOf").asText())
            .isEqualTo("Financial Transaction Response");
        assertThat(coverage.path("responseFamilies").get(3).path("requiredRequestSegments").get(1).path("segment").asText())
            .isEqualTo("130");
        assertThat(coverage.path("evidenceBoundary").path("humanApproval").asBoolean()).isFalse();
    }

    @Test
    void validatesAllCodeAndFamilyPairsAgainstStructuredElement83BrMatrix() throws Exception {
        Path brMatrixPath = Path.of("specifications", "ATL105", "docs", "specs", "kb", "element-83-response-code", "coverage", "element-83-response-code-br-validation-matrix.json");
        JsonNode brMatrix = MAPPER.readTree(brMatrixPath.toFile());
        JsonNode roster = MAPPER.readTree(MATRIX.toFile());
        Set<String> codes = new HashSet<>();
        Set<String> families = new HashSet<>();
        Set<String> brIds = new HashSet<>();
        for (JsonNode entry : roster.path("codes")) codes.add(entry.path("code").asText());
        for (JsonNode family : brMatrix.path("sourceDefinedFamilies")) families.add(family.asText());
        for (JsonNode br : brMatrix.path("allowedCodeFamilyMeanings")) brIds.add(br.path("businessRequirementId").asText());

        assertThat(codes).hasSize(31);
        assertThat(families).hasSize(7);
        assertThat(brMatrix.path("allowedCodeFamilyMeanings")).hasSize(35);
        assertThat(brIds).hasSize(35);

        int validPairs = 0;
        int invalidPairs = 0;
        Atl105ResponseCodeFamilyOracle oracle = new Atl105ResponseCodeFamilyOracle();
        for (String family : families) {
            for (String code : codes) {
                boolean matrixAllows = brMatrix.path("allowedCodeFamilyMeanings").findValues("code").stream()
                    .anyMatch(node -> node.asText().equals(code))
                    && brMatrix.path("allowedCodeFamilyMeanings").findValues("family").stream()
                    .anyMatch(node -> node.asText().equals(family))
                    && hasAllowedPair(brMatrix.path("allowedCodeFamilyMeanings"), code, family);
                var result = oracle.validate(family, code);
                assertThat(result.errors().isEmpty())
                    .as("family=%s code=%s", family, code).isEqualTo(matrixAllows);
                if (matrixAllows) validPairs++; else invalidPairs++;
            }
        }
        assertThat(validPairs).isEqualTo(brMatrix.path("validationGrid").path("validPairs").asInt());
        assertThat(invalidPairs).isEqualTo(brMatrix.path("validationGrid").path("invalidForSelectedFamilyPairs").asInt());
        assertThat(validPairs + invalidPairs).isEqualTo(brMatrix.path("validationGrid").path("totalPairs").asInt());
    }

    private static boolean hasAllowedPair(JsonNode requirements, String code, String family) {
        for (JsonNode requirement : requirements) {
            if (code.equals(requirement.path("code").asText()) && family.equals(requirement.path("family").asText())) return true;
        }
        return false;
    }
}