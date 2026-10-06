package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Indexes authored Test Solution BRs by the catalog rules they cite; AI artifacts are never read. */
public final class BusinessRequirementIndex {
    public record Link(String requirementId, String source, String evidence) { }

    private static final Pattern RULE_ID = Pattern.compile("SEG[0-9A-Z]+-R-\\d{3}");
    private static final Pattern REQUIREMENT_ID = Pattern.compile("[A-Z][A-Za-z0-9]*(?:-[A-Za-z0-9]+)+");
    private static final Pattern NOT_REQUIREMENT = Pattern.compile("^(?:AI|MUT|P)-|-(?:R|SME)-\\d");
    private static final Pattern ADOPTED_ID = Pattern.compile("\\bBR-[A-Z0-9]+(?:-[A-Z0-9]+)+\\b");
    private final Set<String> knownRules;
    private final Map<String, Set<String>> rulesByAnchor = new HashMap<>();
    private final Map<String, Set<String>> rulesByAnchorWithoutSection = new HashMap<>();
    private final Set<String> definedIds = new HashSet<>();
    private final Map<String, Map<String, Link>> requirements = new TreeMap<>();
    private final Map<String, Map<String, Link>> negatives = new TreeMap<>();
    private final Set<String> unknownCitations = new TreeSet<>();

    public BusinessRequirementIndex(Map<String, JsonNode> rules) {
        this.knownRules = Set.copyOf(rules.keySet());
        rules.forEach((id, rule) -> {
            JsonNode anchor = rule.path("sourceAnchor");
            if (!anchor.isObject()) return;
            rulesByAnchor.computeIfAbsent(anchorKey(anchor, true), ignored -> new TreeSet<>()).add(id);
            rulesByAnchorWithoutSection.computeIfAbsent(anchorKey(anchor, false), ignored -> new TreeSet<>()).add(id);
        });
    }

    public static BusinessRequirementIndex load(Path pack, Map<String, JsonNode> rules) throws IOException {
        BusinessRequirementIndex index = new BusinessRequirementIndex(rules);
        try (var files = Files.walk(pack.resolve("docs/specs/kb"))) {
            for (Path file : files.filter(path -> path.getFileName().toString().endsWith("business-requirements.md")).sorted().toList()) {
                index.indexMarkdown(Files.readString(file), relative(pack, file));
            }
        }
        Path packages = pack.resolve("test-output/test-json");
        if (Files.isDirectory(packages)) {
            ObjectMapper mapper = new ObjectMapper();
            try (var files = Files.walk(packages)) {
                for (Path file : files.filter(path -> path.toString().endsWith(".json")).sorted().toList()) {
                    String name = file.getFileName().toString();
                    if (name.startsWith("ai-") || name.contains("-ai-")) continue;
                    index.indexJson(mapper.readTree(file.toFile()), relative(pack, file));
                }
            }
        }
        index.indexAdoptions(rules);
        return index;
    }

    public void indexMarkdown(String text, String source) {
        for (String line : text.split("\\R")) {
            if (!line.startsWith("|")) continue;
            String[] cells = line.split("\\|", -1);
            if (cells.length < 3) continue;
            String id = cells[1].replaceAll("[`*]", "").trim();
            if (!REQUIREMENT_ID.matcher(id).matches() || NOT_REQUIREMENT.matcher(id).find()) continue;
            definedIds.add(id);
            List<String> cited = new ArrayList<>();
            List<String> anywhere = new ArrayList<>();
            for (int index = 2; index < cells.length; index++) {
                List<String> found = rules(cells[index]);
                anywhere.addAll(found);
                String rest = cells[index].replaceAll("\u00a7.*", "").replaceAll("`?" + RULE_ID.pattern() + "`?", "").replaceAll("[,\\s]", "");
                if (!found.isEmpty() && rest.isEmpty()) cited.addAll(found);
            }
            for (String rule : cited.isEmpty() ? anywhere : cited) link(rule, id, source, "DOCUMENT_ROW");
        }
    }

    public void indexJson(JsonNode document, String source) {
        for (JsonNode requirement : document.path("businessRequirements")) {
            String id = requirement.path("id").asText(requirement.path("brId").asText(""));
            if (id.isBlank()) continue;
            definedIds.add(id);
            Set<String> explicit = new TreeSet<>(rules(requirement.toString()));
            for (String rule : explicit) link(rule, id, source, "JSON_PACKAGE_RULE_ID");
            for (JsonNode anchor : anchors(requirement)) {
                Set<String> exact = rulesByAnchor.getOrDefault(anchorKey(anchor, true), Set.of());
                if (exact.size() == 1) {
                    link(exact.iterator().next(), id, source, "JSON_PACKAGE_EXACT_SOURCE_ANCHOR");
                    continue;
                }
                if (!exact.isEmpty()) continue;
                Set<String> sameWithoutSection = rulesByAnchorWithoutSection.getOrDefault(anchorKey(anchor, false), Set.of());
                if (sameWithoutSection.size() == 1)
                    link(sameWithoutSection.iterator().next(), id, source, "JSON_PACKAGE_UNIQUE_ANCHOR_SECTION_VARIANT");
            }
        }
    }

    private static List<JsonNode> anchors(JsonNode requirement) {
        List<JsonNode> result = new ArrayList<>();
        JsonNode sourceAnchors = requirement.path("sourceAnchors");
        if (sourceAnchors.isArray()) sourceAnchors.forEach(anchor -> { if (anchor.isObject()) result.add(anchor); });
        else if (sourceAnchors.isObject()) result.add(sourceAnchors);
        JsonNode sourceAnchor = requirement.path("sourceAnchor");
        if (sourceAnchor.isObject()) result.add(sourceAnchor);
        return result;
    }

    private static String anchorKey(JsonNode anchor, boolean includeSection) {
        return String.join("|", anchor.path("specification").asText(""), anchor.path("version").asText(""),
            includeSection ? anchor.path("section").asText("") : "", anchor.path("segment").asText(""),
            anchor.path("element").asText(""), anchor.path("rule").asText(""));
    }

    public void indexAdoptions(Map<String, JsonNode> rules) {
        rules.forEach((ruleId, rule) -> {
            Matcher adopted = ADOPTED_ID.matcher(rule.path("note").asText(""));
            while (adopted.find()) {
                if (definedIds.contains(adopted.group())) link(ruleId, adopted.group(), "rule catalog note", "ADOPTED_BY_RULE_NOTE");
            }
        });
    }

    public List<Link> requirements(String ruleId) {
        return List.copyOf(requirements.getOrDefault(ruleId, Map.of()).values());
    }

    public List<Link> negatives(String ruleId) {
        return List.copyOf(negatives.getOrDefault(ruleId, Map.of()).values());
    }

    public Set<String> unknownRuleCitations() {
        return Set.copyOf(unknownCitations);
    }

    private void link(String rule, String id, String source, String evidence) {
        if (!knownRules.contains(rule)) {
            unknownCitations.add(rule + " cited by " + id + " in " + source);
            return;
        }
        (id.contains("-NEG-") ? negatives : requirements).computeIfAbsent(rule, key -> new LinkedHashMap<>())
            .putIfAbsent(id, new Link(id, source, evidence));
    }

    private static List<String> rules(String text) {
        List<String> found = new ArrayList<>();
        Matcher matcher = RULE_ID.matcher(text);
        while (matcher.find()) found.add(matcher.group());
        return found;
    }

    private static String relative(Path pack, Path file) {
        return pack.relativize(file).toString().replace('\\', '/');
    }
}
