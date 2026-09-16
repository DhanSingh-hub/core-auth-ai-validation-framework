package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100EwicArtifactIntakeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100EwicArtifactIntakeValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String[] OPERATIONS = {"AUTHORIZATION", "CANCELLATION", "BALANCE_INQUIRY", "COMPLETION", "VOUCHER_CLEAR", "REVERSAL_VOID"};
    private static final String[] CODES = {"3086", "S086", "E086", "0086", "0086", "8086"};

    @Test
    void acceptsCompleteAiEwicOperationSet() throws Exception {
        Path directory = Files.createTempDirectory("ai-ewic-intake-");
        for (int index = 0; index < OPERATIONS.length; index++) {
            MAPPER.writeValue(directory.resolve(index + "-" + OPERATIONS[index] + ".json").toFile(), payload(OPERATIONS[index], CODES[index]));
        }

        var result = new Segment100EwicArtifactIntakeValidator().validateDirectory(directory);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void reportsMissingEwicOperation() throws Exception {
        Path directory = Files.createTempDirectory("ai-ewic-intake-");
        for (int index = 0; index < OPERATIONS.length - 1; index++) {
            MAPPER.writeValue(directory.resolve(index + ".json").toFile(), payload(OPERATIONS[index], CODES[index]));
        }

        var result = new Segment100EwicArtifactIntakeValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Missing AI-generated eWIC artifact for operation REVERSAL_VOID"));
    }

    @Test
    void rejectsWrongPromptCodeAndDuplicateOperation() throws Exception {
        Path directory = Files.createTempDirectory("ai-ewic-intake-");
        MAPPER.writeValue(directory.resolve("authorization-1.json").toFile(), payload("AUTHORIZATION", "3086"));
        MAPPER.writeValue(directory.resolve("authorization-2.json").toFile(), payload("AUTHORIZATION", "S086"));

        var result = new Segment100EwicArtifactIntakeValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires Prompt Code 3086"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Duplicate AI-generated eWIC artifact"));
    }

    private static ObjectNode payload(String operation, String promptCode) {
        ObjectNode payload = MAPPER.createObjectNode();
        payload.putObject("metadata").put("ewicOperation", operation);
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105").put("NumSegments", "02");
        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100").put("TerminalID", "TERM001").put("SequenceNumber", "100001");
        standard.putObject("PromptCode").put("TransactionType", promptCode.substring(0, 1)).put("CardType", "086");
        request.putObject("EBT Data Segment").put("SegmentType", "103").put("SegmentLength", "010").put("WICProductData", "WIC-DATA");
        return payload;
    }
}
