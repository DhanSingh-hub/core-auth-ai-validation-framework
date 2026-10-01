package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/** Generates independent BR coverage entries from KB rule catalogs, linked to authored BRs and verified source evidence. */
public final class GenerateKnowledgeBaseBrCoveragePackage {
    private static final Pattern NUMERIC = Pattern.compile("(\\d+(?:\\.\\d+)*)(?:-[A-Za-z]+)?");
    private static final Pattern APPENDIX = Pattern.compile("(?i)Appendix ?([A-Z])(?:-\\d+)?");
    private static final Pattern ELEMENT = Pattern.compile("Chapter ?13-Elements?(\\d+)(?:-(\\d+))?(?:-[a-z]+)?");
    private static final Pattern PAGE = Pattern.compile("\\d+-\\d+");

    private GenerateKnowledgeBaseBrCoveragePackage() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path kbRoot = Atl105Paths.docs().resolve(Path.of("specs", "kb"));
        String spec = Files.readString(Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt")));
        JsonNode elements = new Atl105ElementInventory().extract(spec).path("elements");
        Map<String, JsonNode> rules = new LinkedHashMap<>();
        Map<String, String> catalogs = new LinkedHashMap<>();
        try (Stream<Path> files = Files.walk(kbRoot)) {
            for (Path file : files.filter(value -> value.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).sorted().toList()) {
                for (JsonNode rule : mapper.readTree(file.toFile()).path("rules")) {
                    rules.put(rule.path("ruleId").asText(), rule);
                    catalogs.put(rule.path("ruleId").asText(), kbRoot.relativize(file).toString().replace('\\', '/'));
                }
            }
        }
        BusinessRequirementIndex index = BusinessRequirementIndex.load(Atl105Paths.root(), rules);
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-independent-knowledge-base-br-coverage");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("status", "REVIEW_REQUIRED_UNTIL_SME_SEMANTIC_SIGNOFF");
        output.put("sectionPolicy", "Composite sections remain multiple evidence references; title aliases are resolved only when the ATL105 source title is unambiguous.");
        output.put("brPolicy", "A rule is covered only by an authored Test Solution BR that cites it (segment BR document row or crosswalk, Test Solution JSON package, or a BR adopted in the rule's catalog note). AI artifacts are never read.");
        ArrayNode requirements = output.putArray("businessRequirements");
        int composite = 0;
        int titleAlias = 0;
        int appendix = 0;
        int covered = 0;
        int withNegative = 0;
        int unverified = 0;
        Map<String, Integer> linksBySource = new TreeMap<>();

        for (Map.Entry<String, JsonNode> entry : rules.entrySet()) {
            JsonNode rule = entry.getValue();
            ObjectNode br = requirements.addObject();
            br.put("id", entry.getKey());
            br.put("title", rule.path("title").asText());
            br.put("class", rule.path("class").asText());
            br.put("severity", rule.path("severity").asText());
            br.put("status", "DRAFT_REVIEW_REQUIRED");
            br.set("sourceAnchor", rule.path("sourceAnchor"));
            ArrayNode evidenceSections = br.putArray("sourceEvidenceSections");
            ArrayNode unresolved = br.putArray("unverifiedSourceEvidence");
            String resolution = "DIRECT_SECTION";
            for (String token : rule.path("sourceAnchor").path("section").asText("").split(",")) {
                String section = token.trim();
                if (section.isBlank()) continue;
                String kind = resolve(section, spec, elements);
                if (kind == null) {
                    unresolved.add(section);
                    evidenceSections.add(section);
                    resolution = "UNRESOLVED_REVIEW_REQUIRED";
                    continue;
                }
                evidenceSections.add("TITLE_ALIAS".equals(kind) ? "11.4.1.1" : section);
                if ("TITLE_ALIAS".equals(kind)) titleAlias++;
                if ("APPENDIX".equals(kind)) appendix++;
                if (!"UNRESOLVED_REVIEW_REQUIRED".equals(resolution)) resolution = label(kind);
            }
            if (evidenceSections.size() > 1 && unresolved.isEmpty()) {
                resolution = "COMPOSITE_SECTIONS";
                composite++;
            }
            br.put("sourceEvidenceResolution", resolution);
            br.put("sourceEvidenceVerified", unresolved.isEmpty() && !evidenceSections.isEmpty());
            if (!br.path("sourceEvidenceVerified").asBoolean()) unverified++;
            br.put("sourceCatalog", catalogs.get(entry.getKey()));
            ArrayNode existing = br.putArray("existingBusinessRequirements");
            for (BusinessRequirementIndex.Link link : index.requirements(entry.getKey())) {
                existing.addObject().put("id", link.requirementId()).put("source", link.source()).put("evidence", link.evidence());
                linksBySource.merge(link.evidence(), 1, Integer::sum);
            }
            ArrayNode negative = br.putArray("negativeCoverage");
            index.negatives(entry.getKey()).forEach(link -> negative.add(link.requirementId()));
            br.put("brCoverageStatus", existing.isEmpty() ? "NO_AUTHORED_BR" : "AUTHORED_BR_LINKED");
            if (!existing.isEmpty()) covered++;
            if (!negative.isEmpty()) withNegative++;
        }

        ObjectNode summary = output.putObject("summary");
        summary.put("businessRequirements", rules.size());
        summary.put("oracleRules", rules.size());
        summary.put("rulesWithAuthoredBr", covered);
        summary.put("rulesWithoutAuthoredBr", rules.size() - covered);
        summary.put("brToRuleCoveragePercent", rules.isEmpty() ? 0 : Math.round(1000.0 * covered / rules.size()) / 10.0);
        summary.put("rulesWithNegativeCoverage", withNegative);
        summary.set("brLinksByEvidence", mapper.valueToTree(linksBySource));
        summary.set("unknownRuleCitations", mapper.valueToTree(index.unknownRuleCitations()));
        summary.put("sourceEvidenceUnverified", unverified);
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
        System.out.printf("rules=%d withBr=%d withoutBr=%d coverage=%.1f%% negatives=%d unverifiedEvidence=%d unknownCitations=%d%n",
                rules.size(), covered, rules.size() - covered, summary.path("brToRuleCoveragePercent").asDouble(),
                withNegative, unverified, index.unknownRuleCitations().size());
    }

    /** Returns the evidence kind when the token resolves in the extracted specification, otherwise null. */
    public static String resolve(String token, String spec, JsonNode elements) {
        if ("Totals Request".equalsIgnoreCase(token)) return "TITLE_ALIAS";
        Matcher numeric = NUMERIC.matcher(token);
        if (numeric.matches()) {
            return Pattern.compile("(?m)^[ \\t]*" + Pattern.quote(numeric.group(1)) + "\\.?[ \\t]+\\S").matcher(spec).find() ? "SECTION" : null;
        }
        Matcher appendix = APPENDIX.matcher(token);
        if (appendix.matches()) {
            return Pattern.compile("(?m)^[ \\t]*Appendix " + appendix.group(1) + "[.: \\t]").matcher(spec).find() ? "APPENDIX" : null;
        }
        Matcher element = ELEMENT.matcher(token);
        if (element.matches()) {
            int first = Integer.parseInt(element.group(1));
            int last = element.group(2) == null ? first : Integer.parseInt(element.group(2));
            for (int id = first; id <= last; id++) if (!elements.has(String.valueOf(id))) return null;
            return "CHAPTER_13_ELEMENT";
        }
        if (token.matches("Chapter ?13")) return elements.isEmpty() ? null : "CHAPTER_13";
        if (PAGE.matcher(token).matches()) {
            return Pattern.compile("(?m)^[ \\t]*" + Pattern.quote(token) + "[ \\t]*$").matcher(spec).find() ? "PAGE" : null;
        }
        return null;
    }

    private static String label(String kind) {
        return switch (kind) {
            case "TITLE_ALIAS" -> "TITLE_ALIAS_RESOLVED";
            case "APPENDIX" -> "APPENDIX_SECTION";
            case "CHAPTER_13_ELEMENT", "CHAPTER_13" -> "CHAPTER_13_ELEMENT";
            case "PAGE" -> "PAGE_REFERENCE";
            default -> "DIRECT_SECTION";
        };
    }

    private static String markdown(ObjectNode summary) {
        return "# Independent Knowledge-Base BR Coverage Package\n\n"
                + "This package links every Test Solution rule-catalog entry to the authored Test Solution BRs that cite it. AI artifacts are excluded.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Test Solution rules (oracle) | " + summary.path("oracleRules").asInt() + " |\n"
                + "| Rules with an authored BR | " + summary.path("rulesWithAuthoredBr").asInt() + " |\n"
                + "| Rules without a BR | " + summary.path("rulesWithoutAuthoredBr").asInt() + " |\n"
                + "| BR-to-rule coverage | " + summary.path("brToRuleCoveragePercent").asDouble() + "% |\n"
                + "| Rules with negative BR coverage | " + summary.path("rulesWithNegativeCoverage").asInt() + " |\n"
                + "| Rules with unverified source evidence | " + summary.path("sourceEvidenceUnverified").asInt() + " |\n"
                + "| Composite-section BRs | " + summary.path("compositeSectionRequirements").asInt() + " |\n"
                + "| Title-alias BRs | " + summary.path("titleAliasRequirements").asInt() + " |\n"
                + "| Appendix-evidence BRs | " + summary.path("appendixEvidenceRequirements").asInt() + " |\n\n"
                + "Coverage means an authored BR exists, not SME approval. TS/TC/TD are intentionally zero here; downstream chain derivation remains the next Test Solution gate.\n";
    }
}
