package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100ContextBoundaryRegistry;
import com.coreauth.validator.canonical.Segment100ContextBoundaryValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100ContextBoundaryTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void registryContainsAllFivePendingContextAreas() {
        assertThat(new Segment100ContextBoundaryRegistry().contexts()).containsKeys(
                "MASTERCARD_AUTHORIZATION", "POS_CONDITION_ENTRY_MODE", "TOKENIZATION",
                "APPENDIX_RESPONSE_LAYOUT", "PRODUCT_CARD_LIFECYCLE");
    }

    @Test
    void acceptsDeclaredContextAndReturnsReviewWarning() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-context-");
        MAPPER.writeValue(directory.resolve("mastercard.json").toFile(), artifact("MASTERCARD_AUTHORIZATION"));

        var result = new Segment100ContextBoundaryValidator().validateDirectory(directory);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("MASTERCARD_AUTHORIZATION"));
    }

    @Test
    void rejectsUnknownContext() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-context-");
        MAPPER.writeValue(directory.resolve("unknown.json").toFile(), artifact("UNKNOWN"));

        var result = new Segment100ContextBoundaryValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("supported segment100Context"));
    }

    @Test
    void rejectsContextArtifactWithoutSegment100() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-context-");
        ObjectNode artifact = artifact("TOKENIZATION");
        ((ObjectNode) artifact.path("Financial Request")).remove("Standard Segment");
        MAPPER.writeValue(directory.resolve("token.json").toFile(), artifact);

        var result = new Segment100ContextBoundaryValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires a Segment 100 Financial Request"));
    }

    private static ObjectNode artifact(String context) {
        ObjectNode artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("segment100Context", context);
        ObjectNode request = artifact.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.putObject("Standard Segment").put("SegmentType", "100");
        return artifact;
    }
}
