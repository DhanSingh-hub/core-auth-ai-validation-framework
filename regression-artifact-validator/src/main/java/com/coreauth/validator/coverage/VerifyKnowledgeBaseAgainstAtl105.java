package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/** Verifies independent KB catalog structure and anchor section/version evidence against the ATL105 extract. */
public final class VerifyKnowledgeBaseAgainstAtl105 {
    private VerifyKnowledgeBaseAgainstAtl105() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path kbRoot = Atl105Paths.docs().resolve(Path.of("specs", "kb"));
        Path extractedText = Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt"));
        String source = Files.readString(extractedText);
        Set<String> sourceSections = sourceSections(source);
        Set<String> catalogFiles = new TreeSet<>();
        Set<String> duplicateAnchors = new HashSet<>();
        Set<String> incompleteAnchors = new LinkedHashSet<>();
        Set<String> unsupportedVersion = new LinkedHashSet<>();
        Set<String> missingSections = new LinkedHashSet<>();
        int ruleCount = 0;

        try (Stream<Path> files = Files.walk(kbRoot)) {
            for (Path file : files.filter(value -> value.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).sorted().toList()) {
                catalogFiles.add(kbRoot.relativize(file).toString().replace('\\', '/'));
                JsonNode catalog = mapper.readTree(file.toFile());
                if (!"2026-3".equals(catalog.path("specificationVersion").asText())) {
                    unsupportedVersion.add(file.toString());
                }
                Set<String> anchorsInCatalog = new HashSet<>();
                for (JsonNode rule : catalog.path("rules")) {
                    ruleCount++;
                    JsonNode anchor = rule.path("sourceAnchor");
                    String key = anchor.path("specification").asText() + "|"
                            + anchor.path("version").asText() + "|"
                            + anchor.path("section").asText() + "|"
                            + anchor.path("segment").asText() + "|"
                            + anchor.path("element").asText() + "|"
                            + anchor.path("rule").asText();
                    if (!anchorsInCatalog.add(key)) duplicateAnchors.add(key);
                    if (anchor.path("specification").asText().isBlank()
                            || !"2026-3".equals(anchor.path("version").asText())
                            || anchor.path("section").asText().isBlank()
                            || anchor.path("segment").asText().isBlank()
                            || anchor.path("rule").asText().isBlank()) {
                        incompleteAnchors.add(file + " :: " + rule.path("ruleId").asText());
                    }
                    String section = anchor.path("section").asText();
                    if (!section.isBlank() && !"Appendix A".equalsIgnoreCase(section)
                            && !"Appendix D".equalsIgnoreCase(section)
                            && !"Appendix E".equalsIgnoreCase(section)
                            && !"Appendix F".equalsIgnoreCase(section)
                            && !"Appendix G".equalsIgnoreCase(section)
                            && !"Appendix H".equalsIgnoreCase(section)
                            && !"Appendix I".equalsIgnoreCase(section)
                            && !"Appendix J".equalsIgnoreCase(section)
                            && !"Appendix K".equalsIgnoreCase(section)
                            && !"Appendix L".equalsIgnoreCase(section)
                            && !"Appendix M".equalsIgnoreCase(section)
                            && !"Appendix O".equalsIgnoreCase(section)
                            && !"Appendix Q".equalsIgnoreCase(section)
                            && !"Appendix R".equalsIgnoreCase(section)
                            && !"Appendix T".equalsIgnoreCase(section)
                            && !"Appendix V".equalsIgnoreCase(section)
                            && !sourceSections.contains(section)) {
                        missingSections.add(file + " :: " + rule.path("ruleId").asText() + " :: " + section);
                    }
                }
            }
        }

        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "atl105-knowledge-base-source-verification");
        result.put("specification", "ATL105");
        result.put("specificationVersion", "2026-3");
        result.put("sourceExtract", extractedText.toString());
        result.put("catalogCount", catalogFiles.size());
        result.put("ruleCount", ruleCount);
        result.put("catalogsValidStructurally", incompleteAnchors.isEmpty() && unsupportedVersion.isEmpty() && duplicateAnchors.isEmpty());
        result.put("sourceEvidenceRequiresManualSemanticReview", true);
        result.put("sourceEvidencePolicy", "Automated checks verify version, anchor completeness, duplicate anchors, and section presence. Business meaning remains subject to SME/TBA review.");
        result.set("catalogFiles", mapper.valueToTree(catalogFiles));
        result.set("duplicateAnchors", mapper.valueToTree(duplicateAnchors));
        result.set("incompleteAnchors", mapper.valueToTree(incompleteAnchors));
        result.set("unsupportedVersionFiles", mapper.valueToTree(unsupportedVersion));
        result.set("missingSectionEvidence", mapper.valueToTree(missingSections));

        Path output = Atl105Paths.testOutput().resolve(Path.of(
                "ai-solution-independent-review", "knowledge-base-atl105-verification.json"));
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        System.out.printf("catalogs=%d rules=%d incompleteAnchors=%d duplicateAnchors=%d missingSections=%d structural=%s%n",
                catalogFiles.size(), ruleCount, incompleteAnchors.size(), duplicateAnchors.size(), missingSections.size(),
                result.path("catalogsValidStructurally").asBoolean());
    }

    private static Set<String> sourceSections(String source) {
        Set<String> sections = new HashSet<>();
        for (String line : source.split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.matches("(?:1[0-9]|2[0-9]|3[0-9]|4[0-9])(?:\\.[0-9]+)*.*")) {
                String number = trimmed.split("\\s+", 2)[0];
                sections.add(number);
            }
        }
        return sections;
    }
}
