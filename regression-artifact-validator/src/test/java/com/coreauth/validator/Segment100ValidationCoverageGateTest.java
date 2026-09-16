package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100AiJsonCompatibilityValidator;
import com.coreauth.validator.canonical.Segment100MultiStepCoverageValidator;
import com.coreauth.validator.canonical.Segment100SingleStepCoverageValidator;
import com.coreauth.validator.canonical.Segment100TransactionTypeOracle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100ValidationCoverageGateTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SINGLE_STEP_DIRECTORY = Path.of("test-input/ai-solution/test-data/segment-100/transaction-types");
    private static final Path LIFECYCLE_DIRECTORY = Path.of("test-input/ai-solution/test-data/segment-100/lifecycle");

    @Test
    void allThirteenTransactionTypeArtifactsPassConverterPreflight() throws Exception {
        assertThat(Files.isDirectory(SINGLE_STEP_DIRECTORY)).isTrue();
        var validator = new Segment100AiJsonCompatibilityValidator();

        try (var files = Files.list(SINGLE_STEP_DIRECTORY)) {
            var jsonFiles = files.filter(path -> path.toString().endsWith(".json")).toList();
            assertThat(jsonFiles).hasSize(13);
            for (Path file : jsonFiles) {
                assertThat(validator.validateFile(file).errors())
                        .as(file.toString())
                        .isEmpty();
            }
        }
    }

    @Test
    void singleStepCoverageGateRequiresAllThirteenCodes() {
        var result = new Segment100SingleStepCoverageValidator().validateDirectory(SINGLE_STEP_DIRECTORY);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void lifecycleCoverageGateAcceptsAllFivePositiveFlows() {
        var validator = new Segment100MultiStepCoverageValidator();
        assertThat(validator.validateFiles(
                Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION,
                file("authorization-completion-authorization.source.json"),
                file("authorization-completion-completion.valid.json")).errors()).isEmpty();
        assertThat(validator.validateFiles(
                Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_VOID,
                file("authorization-void-authorization.source.json"),
                file("authorization-void-void.valid.json")).errors()).isEmpty();
        assertThat(validator.validateFiles(
                Segment100MultiStepCoverageValidator.Flow.REFUND_VOID_OF_RETURN,
                file("refund-void-refund.source.json"),
                file("refund-void-void-of-return.valid.json")).errors()).isEmpty();
        assertThat(validator.validateFiles(
                Segment100MultiStepCoverageValidator.Flow.SALE_VOID,
                file("sale-void-sale.source.json"),
                file("sale-void-void.valid.json")).errors()).isEmpty();
        assertThat(validator.validateFiles(
                Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL,
                file("timeout-tor-original.synthetic.json"),
                file("timeout-tor-reversal.synthetic.json")).errors()).isEmpty();
    }

    @Test
    void lifecycleCoverageGateDetectsAllPreservedSourceMismatches() {
        var validator = new Segment100MultiStepCoverageValidator();
        var mismatchPairs = Set.of(
                new Pair(Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION, "authorization-completion-authorization.source.json", "authorization-completion-completion.source.json"),
                new Pair(Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_VOID, "authorization-void-authorization.source.json", "authorization-void-void.source.json"),
                new Pair(Segment100MultiStepCoverageValidator.Flow.REFUND_VOID_OF_RETURN, "refund-void-refund.source.json", "refund-void-void-of-return.source.json"),
                new Pair(Segment100MultiStepCoverageValidator.Flow.SALE_VOID, "sale-void-sale.source.json", "sale-void-void.source.json"));

        for (Pair pair : mismatchPairs) {
            assertThat(validator.validateFiles(pair.flow(), file(pair.original()), file(pair.followUp())).errors())
                    .as(pair.flow().name())
                    .anyMatch(error -> error.reason().contains("must reuse original SequenceNumber"));
        }
    }

    @Test
    void transactionTypeMatrixRemainsIndependentAndComplete() throws Exception {
        Path matrixPath = Path.of("test-output/test-json/segment-100-code-flow-br-ts-tc-testdata.json");
        JsonNode matrix = MAPPER.readTree(matrixPath.toFile());
        Set<String> matrixCodes = Set.copyOf(
                java.util.stream.StreamSupport.stream(matrix.path("transactionTypeArtifacts").spliterator(), false)
                        .map(item -> item.path("code").asText())
                        .collect(Collectors.toSet()));

        assertThat(matrixCodes).containsExactlyInAnyOrderElementsOf(
                new Segment100TransactionTypeOracle().expectedCodeMatrix().keySet());
    }

    private static Path file(String name) {
        return LIFECYCLE_DIRECTORY.resolve(name);
    }

    private record Pair(Segment100MultiStepCoverageValidator.Flow flow, String original, String followUp) {}
}
