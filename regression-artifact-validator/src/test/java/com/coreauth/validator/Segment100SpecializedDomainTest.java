package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100SpecializedDomainIntakeValidator;
import com.coreauth.validator.canonical.Segment100SpecializedDomainRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100SpecializedDomainTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void registryContainsSixSeparateDomains() {
        assertThat(new Segment100SpecializedDomainRegistry().domains()).containsKeys(
                "PREMIUM_GIFT_CARD", "PAYMENT_TOKEN", "TRANSARMOR_ADMIN",
                "CA_PUBLIC_KEYS", "DIGITAL_WALLET", "MONERIS");
    }

    @Test
    void acceptsDeclaredSpecializedDomainWithSegment100() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-specialized-");
        MAPPER.writeValue(directory.resolve("wallet.json").toFile(), artifact("DIGITAL_WALLET"));

        var result = new Segment100SpecializedDomainIntakeValidator().validateDirectory(directory);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsUndeclaredSpecializedDomain() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-specialized-");
        MAPPER.writeValue(directory.resolve("unknown.json").toFile(), artifact("UNKNOWN"));

        var result = new Segment100SpecializedDomainIntakeValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("supported specializedDomain"));
    }

    @Test
    void rejectsSpecializedArtifactWithoutSegment100() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-specialized-");
        var artifact = artifact("MONERIS");
        ((com.fasterxml.jackson.databind.node.ObjectNode) artifact.path("Financial Request")).remove("Standard Segment");
        MAPPER.writeValue(directory.resolve("moneris.json").toFile(), artifact);

        var result = new Segment100SpecializedDomainIntakeValidator().validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires SegmentType 100"));
    }

    private static com.fasterxml.jackson.databind.node.ObjectNode artifact(String domain) {
        var artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("specializedDomain", domain);
        var request = artifact.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.putObject("Standard Segment").put("SegmentType", "100");
        return artifact;
    }
}
