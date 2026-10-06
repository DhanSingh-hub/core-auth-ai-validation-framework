package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionTypeTrainingLedgerTest {
    private static final Path LEDGER = Path.of(
            "specifications/ATL105/docs/specs/kb/segment-100/transaction-type-training");

    @Test
    void createsOneSourceAnchoredHandoffTaskPerAppendixGCode() throws Exception {
        JsonNode index = new ObjectMapper().readTree(LEDGER.resolve("training-ledger-index.json").toFile());

        assertThat(index.path("taskCount").asInt()).isEqualTo(23);
        assertThat(index.path("tasks")).hasSize(23);
        Set<String> codes = new HashSet<>();
        for (JsonNode summary : index.path("tasks")) {
            String code = summary.path("code").asText();
            assertThat(codes.add(code)).as("unique task for code %s", code).isTrue();
            JsonNode task = new ObjectMapper().readTree(LEDGER.resolve(summary.path("taskFile").asText()).toFile());
            if ("0".equals(code)) {
                assertThat(task.path("status").asText()).isEqualTo("IN_PROGRESS");
                assertThat(task.path("owner").path("machine").asText()).isEqualTo("CPC-shiva-XBSH1");
                assertThat(task.path("gates").get(0).path("status").asText()).isEqualTo("COMPLETE");
                assertThat(task.path("workLog")).hasSize(2);
            } else {
                assertThat(task.path("status").asText()).isEqualTo("UNCLAIMED");
                assertThat(task.path("workLog")).isEmpty();
            }
            assertThat(task.path("transactionType").path("sourceAnchor").asText())
                    .contains("Appendix G|100|78|transaction-type-" + code);
            assertThat(task.path("gates")).hasSize(8);
            assertThat(task.has("owner")).isTrue();
        }
        assertThat(Files.isRegularFile(LEDGER.resolve("README.md"))).isTrue();
        assertThat(Files.isRegularFile(LEDGER.resolve("training-ledger.md"))).isTrue();
        try (var events = Files.list(LEDGER.resolve("events"))) {
            List<Path> eventFiles = events.filter(Files::isRegularFile).toList();
            assertThat(eventFiles).isNotEmpty();
            for (Path eventFile : eventFiles) {
                JsonNode event = new ObjectMapper().readTree(eventFile.toFile());
                assertThat(event.path("eventId").asText()).isNotBlank();
                assertThat(event.path("occurredAt").asText()).isNotBlank();
                assertThat(event.path("user").asText()).isNotBlank();
                assertThat(event.path("machine").asText()).isNotBlank();
                assertThat(event.path("branch").asText()).isNotBlank();
            }
        }
    }
}