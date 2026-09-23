package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.SourceAnchor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Loads Test-Solution-owned rule catalogs into one independent coverage baseline. */
public final class RuleCatalogBaselineLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public IndependentRequirementBaseline load(List<Path> catalogFiles) throws IOException {
        List<IndependentRequirementBaseline.Requirement> requirements = new ArrayList<>();
        for (Path catalogFile : catalogFiles == null ? List.<Path>of() : catalogFiles) {
            JsonNode root = mapper.readTree(catalogFile.toFile());
            for (JsonNode rule : root.path("rules")) {
                String ruleId = rule.path("ruleId").asText(null);
                SourceAnchor anchor = mapper.treeToValue(rule.path("sourceAnchor"), SourceAnchor.class);
                boolean included = !rule.path("coverageExcluded").asBoolean(false);
                requirements.add(new IndependentRequirementBaseline.Requirement(
                        ruleId, List.of(anchor), included));
            }
        }
        return new IndependentRequirementBaseline(requirements);
    }
}