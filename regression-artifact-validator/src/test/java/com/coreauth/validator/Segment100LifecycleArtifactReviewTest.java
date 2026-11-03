package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100LifecycleArtifactReview;
import com.coreauth.validator.canonical.Segment100MultiStepCoverageValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100LifecycleArtifactReviewTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path TEST_MODEL = Path.of("specifications", "ATL105", "test-output", "test-json",
            "segment-100-item-09-sequence-lifecycle-package.json");

    @Test
    void positiveAuthorizationCompletionHasACompleteValidatedChain() throws Exception {
        var model = MAPPER.readTree(TEST_MODEL.toFile());
        var artifactPackage = new com.coreauth.validator.canonical.CanonicalPackageLoader().load(TEST_MODEL);

        assertThat(model.path("businessRequirements").get(1).path("id").asText()).isEqualTo("BR-SEG100-E86-002");
        assertThat(model.path("testScenarios").get(1).path("requirementIds").get(0).asText())
                .isEqualTo("BR-SEG100-E86-002");
        assertThat(model.path("testCases").get(2).path("scenarioIds").get(0).asText())
                .isEqualTo("SCN-SEG100-E86-002");
        assertThat(model.path("testData").get(2).path("testCaseIds").get(0).asText())
                .isEqualTo("TC-SEG100-E86-003");
        assertThat(new com.coreauth.validator.canonical.CanonicalTraceabilityValidator()
                .validate(artifactPackage).errors()).isEmpty();
        assertThat(new com.coreauth.validator.canonical.LifecycleCorrelationValidator()
                .validate(artifactPackage).errors()).isEmpty();
    }

    @Test
    void matchesExistingNegativeTestCaseWhenAuthorizationCompletionReusesWrongSequence() throws Exception {
        Path directory = Files.createTempDirectory("atl105-ai-lifecycle-review-");
        Path authorization = write(directory.resolve("authorization.json"), "3", "020", "842262");
        Path completion = write(directory.resolve("completion.json"), "0", "020", "842271");

        var report = Segment100LifecycleArtifactReview.evaluate(TEST_MODEL, "TC-SEG100-E86-004",
                Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION, authorization, completion);

        assertThat(report.path("testModelEvidence").path("businessRequirementId").asText())
                .isEqualTo("BR-SEG100-E86-002");
        assertThat(report.path("testModelEvidence").path("testDataId").asText()).isEqualTo("TD-SEG100-E86-004");
        assertThat(report.path("testCaseExpectationMatched").asBoolean()).isTrue();
        assertThat(report.path("status").asText()).isEqualTo("TEST_CASE_EXPECTATION_MATCHED_AI_PAIR_REJECTED");
        assertThat(report.path("aiArtifactConformance").asText()).isEqualTo("FAIL");
        assertThat(report.path("lifecycleValidationErrors").get(0).asText())
                .contains("follow-up must reuse original SequenceNumber 842262");
        assertThat(report.toString()).doesNotContain("TrackData");
    }

    @Test
    void doesNotCallAConformingAuthorizationCompletionTheExpectedNegative() throws Exception {
        Path directory = Files.createTempDirectory("atl105-ai-lifecycle-valid-review-");
        Path authorization = write(directory.resolve("authorization.json"), "3", "020", "842262");
        Path completion = write(directory.resolve("completion.json"), "0", "020", "842262");

        var report = Segment100LifecycleArtifactReview.evaluate(TEST_MODEL, "TC-SEG100-E86-004",
                Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION, authorization, completion);

        assertThat(report.path("aiArtifactConformance").asText()).isEqualTo("PASS");
        assertThat(report.path("targetSequenceMismatchObserved").asBoolean()).isFalse();
        assertThat(report.path("testCaseExpectationMatched").asBoolean()).isFalse();
    }

    private static Path write(Path path, String transactionType, String cardType, String sequence) throws Exception {
        var payload = MAPPER.createObjectNode();
        var request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "1");
        var segment = request.putObject("Standard Segment");
        segment.put("SegmentType", "100");
        segment.put("TerminalID", "TERM0001");
        segment.put("SequenceNumber", sequence);
        segment.putObject("PromptCode").put("TransactionType", transactionType).put("CardType", cardType);
        MAPPER.writeValue(path.toFile(), payload);
        return path;
    }
}