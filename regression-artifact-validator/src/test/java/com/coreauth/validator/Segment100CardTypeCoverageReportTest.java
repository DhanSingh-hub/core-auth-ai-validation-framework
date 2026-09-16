package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100CardTypeCoverageReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100CardTypeCoverageReportTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String[] CODES = {
            "001", "011", "012", "013", "020", "040", "041", "045", "046", "050",
            "051", "052", "053", "055", "056", "057", "058", "059", "060", "061",
            "064", "070", "071", "072", "073", "074", "075", "077", "078", "079",
            "080", "081", "083", "084", "085", "090"
    };

    @Test
    void reportsCompleteAiCoverageForAllThirtySixCardTypes() throws Exception {
        Path directory = Files.createTempDirectory("ai-card-coverage-");
        for (String code : CODES) {
            MAPPER.writeValue(directory.resolve("card-" + code + ".json").toFile(), payload(code));
        }

        var report = new Segment100CardTypeCoverageReport().generate(directory, "LOCAL|test-fixture|working-tree");

        assertThat(report.status()).isEqualTo("PASS");
        assertThat(report.expectedCount()).isEqualTo(36);
        assertThat(report.present()).hasSize(36);
        assertThat(report.missing()).isEmpty();
        assertThat(report.provenance()).startsWith("LOCAL|");
    }

    @Test
    void reportsCurrentBaselineAsIncompleteInsteadOfClaimingAiCoverage() throws Exception {
        Path directory = Path.of("test-input/ai-solution/test-data/segment-100/transaction-types");

        var report = new Segment100CardTypeCoverageReport().generate(directory, "LOCAL|current-baseline|working-tree");

        assertThat(report.status()).isEqualTo("INCOMPLETE");
        assertThat(report.expectedCount()).isEqualTo(36);
        assertThat(report.present()).containsExactly("020");
        assertThat(report.missing()).hasSize(35);
    }

    @Test
    void reportsDuplicateAndInvalidArtifacts() throws Exception {
        Path directory = Files.createTempDirectory("ai-card-coverage-");
        MAPPER.writeValue(directory.resolve("a.json").toFile(), payload("020"));
        MAPPER.writeValue(directory.resolve("b.json").toFile(), payload("020"));
        MAPPER.writeValue(directory.resolve("invalid.json").toFile(), payload("XXX"));

        var report = new Segment100CardTypeCoverageReport().generate(directory, "LOCAL|negative-fixture|working-tree");

        assertThat(report.duplicates()).containsExactly("020");
        assertThat(report.invalid()).containsExactly("invalid.json");
    }

    private static ObjectNode payload(String cardType) {
        ObjectNode root = MAPPER.createObjectNode();
        ObjectNode request = root.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        ObjectNode segment = request.putObject("Standard Segment");
        segment.put("SegmentType", "100");
        segment.putObject("PromptCode").put("TransactionType", "0").put("CardType", cardType);
        return root;
    }
}
