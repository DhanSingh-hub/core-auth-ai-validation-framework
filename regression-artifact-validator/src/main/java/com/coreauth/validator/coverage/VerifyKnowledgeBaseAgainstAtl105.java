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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        Set<String> sourcePageLocators = sourcePageLocators(source);
        Set<String> sourceAppendices = sourceAppendices(source);
        Set<String> sourceAppendixPages = sourceAppendixPages(source);
        Set<Integer> sourceElements = sourceElements(source);
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
                        if (!section.isBlank() && !sourceSectionEvidencePresent(
                            section, sourceSections, sourcePageLocators, sourceAppendices, sourceAppendixPages, sourceElements)) {
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
        result.put("missingSectionEvidenceCount", missingSections.size());
        result.put("sourceSectionEvidenceVerified", missingSections.isEmpty());
        result.put("catalogsValidStructurally", incompleteAnchors.isEmpty() && unsupportedVersion.isEmpty()
            && duplicateAnchors.isEmpty() && missingSections.isEmpty());
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
            Matcher matcher = Pattern.compile("^(\\d+(?:\\.\\d+)*)\\s+.*$").matcher(trimmed);
            if (matcher.matches()) sections.add(matcher.group(1));
        }
        return sections;
    }

    private static Set<String> sourceAppendices(String source) {
        Set<String> appendices = new HashSet<>();
        Matcher matcher = Pattern.compile("(?m)^Appendix\\s+([A-Z]{1,2})\\.").matcher(source);
        while (matcher.find()) appendices.add(matcher.group(1).toUpperCase());
        return appendices;
    }

    private static Set<String> sourcePageLocators(String source) {
        Set<String> pageLocators = new HashSet<>();
        for (String line : source.split("\\R")) {
            String value = line.trim();
            if (value.matches("13-\\d+")) pageLocators.add(value);
        }
        return pageLocators;
    }

    private static Set<String> sourceAppendixPages(String source) {
        Set<String> pages = new HashSet<>();
        Matcher matcher = Pattern.compile("(?m)^Appendix\\s*([A-Z]{1,2})-(\\d+)\\s*$").matcher(source);
        while (matcher.find()) pages.add(matcher.group(1).toUpperCase() + "-" + matcher.group(2));
        return pages;
    }

    private static Set<Integer> sourceElements(String source) {
        Set<Integer> elements = new HashSet<>();
        Matcher matcher = Pattern.compile("(?m)^Number:\\s*(\\d+)\\s+Name:").matcher(source);
        while (matcher.find()) elements.add(Integer.parseInt(matcher.group(1)));
        return elements;
    }

    private static boolean sourceSectionEvidencePresent(String section, Set<String> sourceSections,
                                                        Set<String> sourcePageLocators,
                                                        Set<String> sourceAppendices, Set<String> sourceAppendixPages,
                                                        Set<Integer> sourceElements) {
        for (String rawPart : section.split("[,+]")) {
            String part = rawPart.trim().replaceAll("(?i)\\(stub\\)", "").trim();
            if (part.isEmpty()) continue;

            if (part.matches("13-\\d+")) {
                if (!sourcePageLocators.contains(part)) return false;
                continue;
            }

            Matcher appendix = Pattern.compile("(?i)^Appendix\\s*([A-Z]{1,2})(?:-(\\d+))?$").matcher(part);
            if (appendix.matches()) {
                String letter = appendix.group(1).toUpperCase();
                String page = appendix.group(2);
                if (page == null ? !sourceAppendices.contains(letter)
                        : !sourceAppendixPages.contains(letter + "-" + page)) return false;
                continue;
            }

            Matcher chapterElement = Pattern.compile("(?i)^Chapter\\s*13-Elements?(\\d+)(?:-(\\d+))?(?:-[A-Za-z][A-Za-z0-9-]*)?$").matcher(part);
            if (chapterElement.matches()) {
                int first = Integer.parseInt(chapterElement.group(1));
                int last = chapterElement.group(2) == null ? first : Integer.parseInt(chapterElement.group(2));
                for (int element = first; element <= last; element++) {
                    if (!sourceElements.contains(element)) return false;
                }
                continue;
            }

            Matcher chapter = Pattern.compile("(?i)^Chapter\\s*(\\d+)(?:-convention)?$").matcher(part);
            if (chapter.matches()) {
                if (!hasSectionPrefix(sourceSections, chapter.group(1))) return false;
                continue;
            }

            String numeric = part.replaceFirst("(?i)-EMV$", "");
            if (numeric.matches("\\d+(?:\\.\\d+)*")) {
                if (!sourceSections.contains(numeric)
                        && !(numeric.matches("\\d+") && hasSectionPrefix(sourceSections, numeric))) return false;
                continue;
            }
            return false;
        }
        return true;
    }

    private static boolean hasSectionPrefix(Set<String> sourceSections, String prefix) {
        return sourceSections.stream().anyMatch(section -> section.equals(prefix) || section.startsWith(prefix + "."));
    }
}
