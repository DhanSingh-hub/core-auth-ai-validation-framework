package com.coreauth.validator.coverage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/** Loads the external Test Solution resolution record for immutable Run2 artifacts. */
public final class Run2SpecificationVersionResolutionLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public Run2SpecificationVersionResolution load(Path file) throws IOException {
        JsonNode root = mapper.readTree(file.toFile());
        Map<String, String> hashes = mapper.convertValue(root.path("artifactSha256"),
                new TypeReference<>() { });
        return new Run2SpecificationVersionResolution(
                root.path("resolutionId").asText(null),
                root.path("decision").asText(null),
                root.path("sourceId").asText(null),
                root.path("sourcePdfFile").asText(null),
                root.path("declaredVersion").asText(null),
                root.path("effectiveVersion").asText(null),
                root.path("authoritativeSourcePath").asText(null),
                root.path("authoritativeSourceSha256").asText(null),
                root.path("provenanceCaveat").asText(null),
                hashes);
    }
}