package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/** Generates independent BR coverage entries from KB rule catalogs with resolved composite/title/appendix evidence. */
public final class GenerateKnowledgeBaseBrCoveragePackage {
    private GenerateKnowledgeBaseBrCoveragePackage() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path kbRoot = Atl105Paths.docs().resolve(Path.of("specs", "kb"));
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-independent-knowledge-base-br-coverage");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("status", "REVIEW_REQUIRED_UNTIL_SME_SEMANTIC_SIGNOFF");
        output.put("sectionPolicy", "Composite sections remain multiple evidence references; title aliases are resolved only when the ATL105 source title is unambiguous.");
        ArrayNode requirements = output.putArray("businessRequirements");
        int total = 0;
        int composite = 0;
        int titleAlias = 0;
        int appendix = 0;

        try (Stream<Path> files = Files.walk(kbRoot)) {
            for (Path file : files.filter(value -> value.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).sorted().toList()) {
                JsonNode catalog = mapper.readTree(file.toFile());
                for (JsonNode rule : catalog.path("rules")) {
                    ObjectNode br = requirements.addObject();
                    br.put("id", rule.path("ruleId").asText());
                    br.put("title", rule.path("title").asText());
                    br.put("class", rule.path("class").asText());
                    br.put("severity", rule.path("severity").asText());
                    br.put("status", "DRAFT_REVIEW_REQUIRED");
                    br.set("sourceAnchor", rule.path("sourceAnchor"));
                    String rawSection = rule.path("sourceAnchor").path("section").asText("");
                    ArrayNode evidenceSections = br.putArray("sourceEvidenceSections");
                    String resolution = "DIRECT_SECTION";
                    for (String token : rawSection.split(",")) {
                        String section = token.trim();
                        if (section.isBlank()) continue;
                        if ("Totals Request".equalsIgnoreCase(section)) {
                            evidenceSections.add("11.4.1.1");
                            resolution = "TITLE_ALIAS_RESOLVED";
                            titleAlias++;
                        } else {
                            evidenceSections.add(section);
                            if (section.startsWith("Appendix")) {
                                resolution = "APPENDIX_SECTION";
                                appendix++;
                            }
                        }
                    }
                    if (evidenceSections.size() > 1) {
                        resolution = "COMPOSITE_SECTIONS";
                        composite++;
                    }
                    br.put("sourceEvidenceResolution", resolution);
                    br.put("sourceCatalog", kbRoot.relativize(file).toString().replace('\\', '/'));
                    total++;
                }
            }
        }

        ObjectNode summary = output.putObject("summary");
        summary.put("businessRequirements", total);
        summary.put("compositeSectionRequirements", composite);
        summary.put("titleAliasRequirements", titleAlias);
        summary.put("appendixEvidenceRequirements", appendix);
        summary.put("testScenarios", 0);
        summary.put("testCases", 0);
        summary.put("testData", 0);
        summary.put("nextGate", "SME_SEMANTIC_REVIEW_AND_BR_TS_TC_TD_DERIVATION");

        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        Files.createDirectories(outputRoot);
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("knowledge-base-br-coverage-package.json").toFile(), output);
        Files.writeString(outputRoot.resolve("knowledge-base-br-coverage-summary.md"), markdown(summary));
        System.out.printf("BRs=%d composite=%d titleAliases=%d appendices=%d%n", total, composite, titleAlias, appendix);
    }

    private static String markdown(ObjectNode summary) {
        return "# Independent Knowledge-Base BR Coverage Package\n\n"
                + "This package derives Test Solution BR entries from independent rule catalogs only. AI artifacts are excluded.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| BRs | " + summary.path("businessRequirements").asInt() + " |\n"
                + "| Composite-section BRs | " + summary.path("compositeSectionRequirements").asInt() + " |\n"
                + "| Title-alias BRs | " + summary.path("titleAliasRequirements").asInt() + " |\n"
                + "| Appendix-evidence BRs | " + summary.path("appendixEvidenceRequirements").asInt() + " |\n\n"
                + "TS/TC/TD are intentionally zero here: this is the BR source-evidence package, and downstream chain derivation remains the next Test Solution gate.\n";
    }
}
