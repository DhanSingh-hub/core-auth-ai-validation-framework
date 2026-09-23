package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.canonical.SourceAnchor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Loads the separate, Test-Solution-owned Run2 crosswalk without modifying AI artifacts. */
public final class Run2CrosswalkLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public Run2Crosswalk load(Path file) throws IOException {
        JsonNode root = mapper.readTree(file.toFile());
        List<Run2Crosswalk.Mapping> mappings = new ArrayList<>();
        for (JsonNode node : root.path("mappings")) {
            List<String> aiIds = new ArrayList<>();
            node.path("aiRequirementIds").forEach(value -> aiIds.add(value.asText()));
            List<SourceAnchor> anchors = new ArrayList<>();
            for (JsonNode anchor : node.path("sourceAnchors")) {
                anchors.add(mapper.treeToValue(anchor, SourceAnchor.class));
            }
            mappings.add(new Run2Crosswalk.Mapping(
                    node.path("testRequirementId").asText(null),
                    aiIds,
                    parseStatus(node.path("matchStatus").asText(null)),
                    node.path("matchReason").asText(null),
                    node.path("reviewOwner").asText(null),
                    anchors));
        }
        return new Run2Crosswalk(mappings);
    }

    private static RequirementMatchStatus parseStatus(String value) throws IOException {
        try {
            return value == null ? null : RequirementMatchStatus.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new IOException("Unknown Run2 matchStatus: " + value, exception);
        }
    }
}