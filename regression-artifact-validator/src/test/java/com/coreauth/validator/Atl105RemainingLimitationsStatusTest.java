package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105RemainingLimitationsStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105RemainingLimitationsStatusTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @TempDir Path temporaryPack;

    @Test
    void reportsAllSixLimitationsWithVerifiedBoundaries() throws Exception {
        Path inputs = PACK.resolve("test-output/test-solution-independent-review");
        Path outputs = temporaryPack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(outputs);
        for (String name : new String[]{"atl105-all-elements-reference.json", "atl105-element-transaction-applicability.json",
            "atl105-element-omission-matrix.json", "atl105-br-approval-matrix.json", "atl105-dependency-absence-review-matrix.json",
            "atl105-element-chain-coverage.json"}) Files.copy(inputs.resolve(name), outputs.resolve(name));
        GenerateAtl105RemainingLimitationsStatus.main(new String[]{temporaryPack.toString()});
        Path output = outputs.resolve("atl105-remaining-limitations-status.json");
        JsonNode status = MAPPER.readTree(output.toFile());
        assertThat(status.path("limitations")).hasSize(6);
        assertThat(status.path("limitations").get(0).path("denominator").asInt()).isEqualTo(5313);
        assertThat(status.path("limitations").get(1).path("denominator").asInt()).isEqualTo(12893);
        assertThat(status.path("limitations").get(2).path("denominator").asInt()).isEqualTo(3);
        assertThat(status.path("limitations").get(3).path("status").asText()).isEqualTo("AUTHORED_LINKAGE_COMPLETE_SME_APPROVAL_PENDING");
        assertThat(status.path("limitations").get(4).path("remainingOrReview").asInt()).isEqualTo(235);
        assertThat(status.path("limitations").get(5).path("completedOrMeasured").asInt()).isZero();
        assertThat(status.path("limitations").get(5).path("status").asText()).isEqualTo("STRUCTURAL_PLACEHOLDER_CHAINS_COMPLETE_EXECUTABLE_COVERAGE_MISSING");
        assertThat(status.path("humanReviewRequired").asBoolean()).isTrue();
    }
}
