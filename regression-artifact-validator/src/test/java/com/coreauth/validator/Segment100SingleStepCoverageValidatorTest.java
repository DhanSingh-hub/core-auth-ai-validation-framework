package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100SingleStepCoverageValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100SingleStepCoverageValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String[] EXPECTED_CODES = {"0", "3", "4", "5", "6", "7", "8", "A", "B", "C", "S", "U", "Z"};

    @Test
    void acceptsCompleteThirteenCodeDirectory() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-single-step-");
        writeArtifacts(directory, EXPECTED_CODES);

        var result = new Segment100SingleStepCoverageValidator().validateDirectory(directory);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void reportsMissingTransactionCode() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-single-step-");
        writeArtifacts(directory, "0", "3", "4", "5", "6", "7", "8", "A", "B", "C", "S", "U");

        var result = new Segment100SingleStepCoverageValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Missing single-step AI artifact for transaction type Z"));
    }

    @Test
    void reportsDuplicateTransactionCode() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-single-step-");
        writeArtifacts(directory, EXPECTED_CODES);
        writeArtifact(directory.resolve("duplicate.json"), "0");

        var result = new Segment100SingleStepCoverageValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Duplicate single-step AI artifacts for transaction type 0"));
    }

    @Test
    void reportsInvalidTransactionCode() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-single-step-");
        writeArtifact(directory.resolve("invalid.json"), "X");

        var result = new Segment100SingleStepCoverageValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Unsupported standard financial transaction type: X"));
    }

    private static void writeArtifacts(Path directory, String... codes) throws Exception {
        for (String code : codes) {
            writeArtifact(directory.resolve("transaction-" + code + ".json"), code);
        }
    }

    private static void writeArtifact(Path path, String code) throws Exception {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "01");
        ObjectNode segment = request.putObject("Standard Segment");
        segment.put("SegmentType", "100");
        segment.putObject("PromptCode").put("TransactionType", code);
        MAPPER.writeValue(path.toFile(), payload);
    }
}
