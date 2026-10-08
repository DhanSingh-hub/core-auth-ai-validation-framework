package com.coreauth.validator;

import com.coreauth.validator.canonical.Atl105ResponseCodeFamilyOracle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105ActualExecutionEvidenceTrainingTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path EVIDENCE = Path.of(
        "specifications", "ATL105", "docs", "specs", "kb", "element-83-response-code",
        "coverage", "element-83-actual-execution-evidence.json");

    @Test
    void recordsActualEvidenceWithoutPromotingUnreviewedRules() throws Exception {
        JsonNode record = MAPPER.readTree(EVIDENCE.toFile());

        assertThat(record.path("evidenceClass").asText()).isEqualTo("USER_CONFIRMED_ACTUAL_PROCESSED_AND_TESTED_ARTIFACTS");
        assertThat(record.path("trainingStatus").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(record.path("trainerQuestionnaireStatus").asText()).isEqualTo("PENDING_TEST_TEAM_TRAINER");
        assertThat(record.path("rawProductionPayloadsCopied").asBoolean()).isFalse();
        assertThat(record.path("aiSolutionArtifactsUsedAsTrainingTruth").asBoolean()).isFalse();
        assertThat(record.path("promotedRules")).isEmpty();

        Set<String> filenames = new HashSet<>();
        for (JsonNode source : record.path("sourceArtifacts")) {
            filenames.add(source.path("file").asText());
            assertThat(source.path("sha256").asText()).matches("[0-9a-f]{64}");
            assertThat(source.path("file").asText()).doesNotContain("ai-solution");
        }
        assertThat(filenames).hasSize(20);
    }

    @Test
    void actualElement83ObservationsAreFinancialFamilyCodesOnly() throws Exception {
        JsonNode record = MAPPER.readTree(EVIDENCE.toFile());
        JsonNode observation = findObservation(record, "PDF-E83-OBSERVED-CODES");
        JsonNode codeCounts = observation.path("observedCodeCounts");
        int caseCount = 0;
        Atl105ResponseCodeFamilyOracle oracle = new Atl105ResponseCodeFamilyOracle();

        for (var fields = codeCounts.fields(); fields.hasNext();) {
            var entry = fields.next();
            caseCount += entry.getValue().asInt();
            assertThat(oracle.validate("financial", entry.getKey()).errors())
                .as("observed Element 83 code %s", entry.getKey()).isEmpty();
        }

        assertThat(caseCount).isEqualTo(11);
        assertThat(observation.path("disposition").asText()).isEqualTo("OBSERVED_RUNTIME_NOT_GENERALIZED");
    }

    @Test
    void keepsTwoByteReportFieldUnmappedFromOneByteElement83() throws Exception {
        JsonNode record = MAPPER.readTree(EVIDENCE.toFile());
        JsonNode observation = findObservation(record, "VISA-DOCX-TWO-BYTE-RESPONSE-FIELD");

        assertThat(observation.path("reportedWidthBytes").asInt()).isEqualTo(2);
        assertThat(observation.path("element83WidthBytesPerAtl105").asInt()).isEqualTo(1);
        assertThat(observation.path("element83Mapping").asText()).isEqualTo("UNRESOLVED");
        assertThat(observation.path("disposition").asText()).isEqualTo("REVIEW_REQUIRED_DO_NOT_TRAIN_AS_ELEMENT_83");
    }

    @Test
    void distinguishesEmbeddedCodeDescriptionsFromRuntimeResults() throws Exception {
        JsonNode record = MAPPER.readTree(EVIDENCE.toFile());
        JsonNode observation = findObservation(record, "EMBEDDED-CODE-DESCRIPTION-TEXT");

        assertThat(observation.path("evidenceType").asText()).isEqualTo("EMBEDDED_CODE_DESCRIPTION_NOT_ACTUAL_OUTCOME");
        assertThat(observation.path("disposition").asText()).isEqualTo("REFERENCE_TEXT_NOT_RUNTIME_OUTCOME");
        assertThat(observation.path("descriptionsSeen").path("visa").path("3").asText())
            .isEqualTo("Approved Authorization only with AVS");
    }

    @Test
    void existingCodeZeroTrainingLinksTheExecutionEvidenceBoundary() throws Exception {
        Path codeZeroTraining = Path.of(
            "specifications", "ATL105", "docs", "specs", "kb", "element-83-response-code",
            "coverage", "element-83-code-0-training.json");
        JsonNode training = MAPPER.readTree(codeZeroTraining.toFile());

        assertThat(training.path("evidenceBoundary").path("actualExecutionEvidenceRecord").asText())
            .isEqualTo("coverage/element-83-actual-execution-evidence.json");
    }

    private static JsonNode findObservation(JsonNode record, String id) {
        for (JsonNode observation : record.path("observations")) {
            if (id.equals(observation.path("id").asText())) return observation;
        }
        throw new AssertionError("Missing execution evidence observation " + id);
    }
}
