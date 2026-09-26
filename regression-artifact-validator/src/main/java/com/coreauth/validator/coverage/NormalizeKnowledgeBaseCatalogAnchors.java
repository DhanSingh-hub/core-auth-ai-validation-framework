package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

/** Repairs known rule-catalog anchor contract gaps without changing rule meaning or source evidence. */
public final class NormalizeKnowledgeBaseCatalogAnchors {
    private NormalizeKnowledgeBaseCatalogAnchors() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        fixSegment119(mapper);
        fixSegment111Metadata(mapper);
        fixSegment100Sections(mapper);
        System.out.println("normalizedKnownCatalogAnchorContractGaps=true");
    }

    private static void fixSegment119(ObjectMapper mapper) throws Exception {
        Path file = Atl105Paths.ruleCatalog("119");
        ObjectNode root = (ObjectNode) mapper.readTree(file.toFile());
        for (JsonNode value : root.path("rules")) {
            ObjectNode anchor = (ObjectNode) value.path("sourceAnchor");
            anchor.put("specification", "ATL105");
            anchor.put("version", "2026-3");
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), root);
    }

    private static void fixSegment111Metadata(ObjectMapper mapper) throws Exception {
        Path file = Atl105Paths.ruleCatalog("111");
        ObjectNode root = (ObjectNode) mapper.readTree(file.toFile());
        root.put("specification", "ATL105");
        root.put("specificationVersion", "2026-3");
        root.put("catalogId", "ATL105-SEG111-RULE-CATALOG-001");
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), root);
    }

    private static void fixSegment100Sections(ObjectMapper mapper) throws Exception {
        Path file = Atl105Paths.ruleCatalog("100");
        ObjectNode root = (ObjectNode) mapper.readTree(file.toFile());
        for (JsonNode value : root.path("rules")) {
            String ruleId = value.path("ruleId").asText();
            if (ruleId.equals("SEG100-R-053") || ruleId.equals("SEG100-R-054") || ruleId.equals("SEG100-R-055")) {
                ((ObjectNode) value.path("sourceAnchor")).put("section", "11.1.1");
            }
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), root);
    }
}
