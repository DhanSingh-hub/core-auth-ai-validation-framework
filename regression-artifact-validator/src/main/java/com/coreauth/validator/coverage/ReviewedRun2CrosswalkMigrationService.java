package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.canonical.SourceAnchor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Migrates prior Test Solution decisions without promoting heuristic-only matches. */
public final class ReviewedRun2CrosswalkMigrationService {
    private static final Pattern NUMERIC_SUFFIX = Pattern.compile("(\\d{3})$");
    private final ObjectMapper mapper = new ObjectMapper();

    public MigrationResult migrate(String segment, Path ruleCatalogFile, Path priorCrosswalkFile,
                                   Path baselineRoot, String reviewOwner) throws IOException {
        List<CatalogRule> rules = loadRules(ruleCatalogFile);
        Map<String, List<SourceAnchor>> baselineAnchors = loadBaselineAnchors(baselineRoot);
        List<PriorDecision> prior = loadPriorDecisions(priorCrosswalkFile);
        Map<String, List<PriorDecision>> byRule = new HashMap<>();
        List<String> unresolvedConfirmed = new ArrayList<>();

        for (PriorDecision decision : prior) {
            CatalogRule rule = resolveRule(segment, rules, decision, baselineAnchors);
            if (rule == null) {
                if (decision.confirmed()) unresolvedConfirmed.add(decision.aiRequirementId());
                continue;
            }
            byRule.computeIfAbsent(rule.ruleId(), ignored -> new ArrayList<>()).add(decision);
        }

        List<Run2Crosswalk.Mapping> mappings = new ArrayList<>();
        int confirmed = 0;
        int reviewRequired = 0;
        int missing = 0;
        for (CatalogRule rule : rules) {
            List<PriorDecision> decisions = byRule.getOrDefault(rule.ruleId(), List.of());
            List<String> aiIds = decisions.stream().map(PriorDecision::aiRequirementId)
                    .filter(value -> value != null && !value.isBlank()).distinct().toList();
            List<PriorDecision> confirmedDecisions = decisions.stream().filter(PriorDecision::confirmed).toList();
            RequirementMatchStatus status;
            String reason;
            String owner = null;
            if (!confirmedDecisions.isEmpty()) {
                status = RequirementMatchStatus.CONFIRMED;
                reason = "Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor "
                        + "or unambiguous canonical rule-id mapping.";
                confirmed++;
            } else if (!decisions.isEmpty()) {
                status = RequirementMatchStatus.REVIEW_REQUIRED;
                reason = "Prior comparison produced candidates but no evidence-supported confirmation for this rule.";
                owner = reviewOwner;
                reviewRequired++;
            } else {
                status = RequirementMatchStatus.MISSING;
                reason = "No prior AI requirement was mapped to this independent Test Solution rule.";
                missing++;
            }
            mappings.add(new Run2Crosswalk.Mapping(rule.ruleId(), aiIds, status, reason, owner,
                    List.of(rule.sourceAnchor())));
        }
        return new MigrationResult(new Run2Crosswalk(mappings), rules.size(), confirmed,
                reviewRequired, missing, List.copyOf(unresolvedConfirmed));
    }

    private List<CatalogRule> loadRules(Path file) throws IOException {
        JsonNode root = mapper.readTree(file.toFile());
        List<CatalogRule> result = new ArrayList<>();
        for (JsonNode node : root.path("rules")) {
            result.add(new CatalogRule(node.path("ruleId").asText(), node.path("title").asText(),
                    mapper.treeToValue(node.path("sourceAnchor"), SourceAnchor.class)));
        }
        return result;
    }

    private List<PriorDecision> loadPriorDecisions(Path file) throws IOException {
        if (file == null || !Files.isRegularFile(file)) return List.of();
        JsonNode root = mapper.readTree(file.toFile());
        List<PriorDecision> result = new ArrayList<>();
        for (JsonNode node : root.path("aiToTestSolution")) {
            result.add(new PriorDecision(node.path("aiRequirementId").asText(null),
                    node.path("testRequirementId").asText(null),
                    node.path("testMatchStatus").asText(null)));
        }
        return result;
    }

    private Map<String, List<SourceAnchor>> loadBaselineAnchors(Path root) throws IOException {
        Map<String, List<SourceAnchor>> result = new LinkedHashMap<>();
        if (!Files.isDirectory(root)) return result;
        try (var files = Files.walk(root)) {
            for (Path file : files.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json")).toList()) {
                try {
                    collectAnchors(mapper.readTree(file.toFile()), result);
                } catch (IOException ignored) {
                    // Non-contract JSON is irrelevant to baseline identity discovery.
                }
            }
        }
        return result;
    }

    private void collectAnchors(JsonNode node, Map<String, List<SourceAnchor>> destination) {
        if (node == null) return;
        if (node.isObject()) {
            String id = node.path("id").asText(null);
            if (id != null) {
                List<SourceAnchor> anchors = new ArrayList<>();
                if (node.path("sourceAnchor").isObject()) {
                    anchors.add(mapper.convertValue(node.path("sourceAnchor"), SourceAnchor.class));
                }
                for (JsonNode anchor : node.path("sourceAnchors")) {
                    anchors.add(mapper.convertValue(anchor, SourceAnchor.class));
                }
                if (!anchors.isEmpty()) {
                    destination.merge(id, anchors, ReviewedRun2CrosswalkMigrationService::mergeAnchors);
                }
            }
            node.elements().forEachRemaining(child -> collectAnchors(child, destination));
        } else if (node.isArray()) {
            node.elements().forEachRemaining(child -> collectAnchors(child, destination));
        }
    }

    private static CatalogRule resolveRule(String segment, List<CatalogRule> rules,
                                           PriorDecision decision,
                                           Map<String, List<SourceAnchor>> baselineAnchors) {
        List<SourceAnchor> anchors = baselineAnchors.getOrDefault(decision.testRequirementId(), List.of());
        List<CatalogRule> anchorMatches = rules.stream()
                .filter(rule -> anchors.stream().anyMatch(anchor ->
                        anchor.canonicalKey().equals(rule.sourceAnchor().canonicalKey())))
                .toList();
        if (anchorMatches.size() == 1) return anchorMatches.getFirst();

        Matcher suffix = NUMERIC_SUFFIX.matcher(decision.testRequirementId() == null
                ? "" : decision.testRequirementId());
        if (!suffix.find()) return null;
        String expected = "SEG" + segment + "-R-" + suffix.group(1);
        List<CatalogRule> idMatches = rules.stream().filter(rule -> expected.equals(rule.ruleId())).toList();
        return idMatches.size() == 1 ? idMatches.getFirst() : null;
    }

    private static List<SourceAnchor> mergeAnchors(List<SourceAnchor> left, List<SourceAnchor> right) {
        Map<String, SourceAnchor> result = new LinkedHashMap<>();
        left.forEach(anchor -> result.put(anchor.canonicalKey(), anchor));
        right.forEach(anchor -> result.put(anchor.canonicalKey(), anchor));
        return List.copyOf(result.values());
    }

    private record CatalogRule(String ruleId, String title, SourceAnchor sourceAnchor) { }

    private record PriorDecision(String aiRequirementId, String testRequirementId, String matchStatus) {
        boolean confirmed() { return "MATCHED_ELEMENT_AND_SEMANTICS".equals(matchStatus); }
    }

    public record MigrationResult(Run2Crosswalk crosswalk, int denominator, int confirmed,
                                  int reviewRequired, int missing,
                                  List<String> unresolvedPriorConfirmedAiRequirementIds) { }
}