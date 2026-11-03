package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/** Updates an entirely unmapped denominator without replaying or certifying historical producer evidence. */
public final class ReconcileRun2UnmappedCatalogDenominator {
    private ReconcileRun2UnmappedCatalogDenominator() { }

    public static void main(String[] args) throws Exception {
        if (args.length != 1 || !args[0].matches("[0-9]{3}"))
            throw new IllegalArgumentException("One numbered segment identity is required");
        String segment = args[0];
        Path root = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path evidenceFile = root.resolve("run2-validation/segment-" + segment + "-validation.json");
        ObjectMapper mapper = new ObjectMapper();
        var evidence = mapper.readTree(evidenceFile.toFile());
        if (!(evidence instanceof ObjectNode object) || !segment.equals(evidence.path("segment").asText()))
            throw new IllegalArgumentException("Historical evidence must name the selected segment");
        var baseline = new RuleCatalogBaselineLoader().load(List.of(Atl105Paths.ruleCatalog(segment)));
        Path crosswalkFile = root.resolve("run2-crosswalks/segment-" + segment + "-run2-crosswalk.json");
        var prior = new Run2CrosswalkLoader().load(crosswalkFile);
        var crosswalk = extendUnmappedCrosswalk(prior, baseline);
        ObjectNode reconciled = reconcile(object, crosswalk, baseline);
        var unresolved = mapper.readTree(crosswalkFile.toFile()).path("unresolvedPriorConfirmedAiRequirementIds");
        List<String> priorUnresolved = new ArrayList<>();
        for (var id : unresolved) {
            if (!id.isTextual()) throw new IllegalArgumentException("Historical unresolved identities must be text");
            priorUnresolved.add(id.textValue());
        }
        new Run2CrosswalkWriter().write(segment, object.path("sourceRun").asText(),
                new ReviewedRun2CrosswalkMigrationService.MigrationResult(crosswalk, crosswalk.mappings().size(),
                        0, 0, crosswalk.mappings().size(), List.copyOf(priorUnresolved)), crosswalkFile);
        mapper.writerWithDefaultPrettyPrinter().writeValue(evidenceFile.toFile(), reconciled);
    }

    public static Run2Crosswalk extendUnmappedCrosswalk(Run2Crosswalk prior, IndependentRequirementBaseline baseline) {
        if (!baseline.validate().isValid())
            throw new IllegalArgumentException("Current independent baseline is structurally invalid");
        Map<String, IndependentRequirementBaseline.Requirement> rules = new HashMap<>();
        baseline.inScopeRequirements().forEach(rule -> rules.put(rule.id(), rule));
        List<Run2Crosswalk.Mapping> mappings = new ArrayList<>(prior.mappings());
        Set<String> ids = prior.mappings().stream().map(Run2Crosswalk.Mapping::testRequirementId).collect(Collectors.toSet());
        if (ids.size() != mappings.size() || !rules.keySet().containsAll(ids))
            throw new IllegalArgumentException("Historical crosswalk rules must remain unique members of the current catalog");
        for (var mapping : mappings) {
            Set<String> expected = rules.get(mapping.testRequirementId()).sourceAnchors().stream()
                    .map(anchor -> anchor.canonicalKey()).collect(Collectors.toSet());
            Set<String> actual = mapping.sourceAnchors().stream().map(anchor -> anchor.canonicalKey()).collect(Collectors.toSet());
            if (mapping.matchStatus() != RequirementMatchStatus.MISSING || !mapping.aiRequirementIds().isEmpty() || !expected.equals(actual))
                throw new IllegalArgumentException("Historical mappings must be unmapped and retain exact canonical anchors");
        }
        rules.values().stream().filter(rule -> !ids.contains(rule.id())).sorted(java.util.Comparator.comparing(
                IndependentRequirementBaseline.Requirement::id)).forEach(rule -> mappings.add(new Run2Crosswalk.Mapping(
                        rule.id(), List.of(), RequirementMatchStatus.MISSING,
                        "New independent catalog rule; no historical AI match is established by denominator reconciliation.",
                        null, rule.sourceAnchors())));
        return new Run2Crosswalk(List.copyOf(mappings));
    }

    public static ObjectNode reconcile(ObjectNode evidence, Run2Crosswalk crosswalk,
                                       IndependentRequirementBaseline baseline) {
        var assessment = evidence.path("assessment");
        int previous = assessment.path("coverageDenominator").asInt(-1);
        if (!assessment.isObject() || previous < 1
                || assessment.path("missingRequirements").asInt(-1) != previous
                || assessment.path("confirmedRequirements").asInt(-1) != 0
                || assessment.path("reviewRequired").asInt(-1) != 0
                || assessment.path("fullChainRequirements").asInt(-1) != 0
                || !"REVIEW_REQUIRED".equals(assessment.path("decision").asText())
                || !assessment.path("executionCertified").isBoolean()
                || assessment.path("executionCertified").booleanValue())
            throw new IllegalArgumentException("Only entirely unmapped, uncertified historical coverage can be reconciled");
        if (!baseline.validate().isValid())
            throw new IllegalArgumentException("Current independent baseline is structurally invalid");
        Map<String, IndependentRequirementBaseline.Requirement> rules = new HashMap<>();
        baseline.inScopeRequirements().forEach(rule -> rules.put(rule.id(), rule));
        if (rules.size() < previous || crosswalk.mappings().size() != rules.size())
            throw new IllegalArgumentException("Current denominator cannot shrink or omit crosswalk rules");
        Set<String> ids = crosswalk.mappings().stream().map(Run2Crosswalk.Mapping::testRequirementId)
                .collect(Collectors.toSet());
        if (!ids.equals(rules.keySet()))
            throw new IllegalArgumentException("Crosswalk must contain each current canonical rule exactly once");
        for (var mapping : crosswalk.mappings()) {
            var anchors = rules.get(mapping.testRequirementId()).sourceAnchors().stream()
                    .map(anchor -> anchor.canonicalKey()).collect(Collectors.toSet());
            var mappedAnchors = mapping.sourceAnchors().stream().map(anchor -> anchor.canonicalKey())
                    .collect(Collectors.toSet());
            if (mapping.matchStatus() != RequirementMatchStatus.MISSING || !mapping.aiRequirementIds().isEmpty()
                    || !anchors.equals(mappedAnchors))
                throw new IllegalArgumentException("Every current rule must be explicitly unmapped with exact canonical anchors");
        }
        ObjectNode output = evidence.deepCopy();
        ObjectNode updated = (ObjectNode) output.get("assessment");
        updated.put("coverageDenominator", rules.size());
        updated.put("missingRequirements", rules.size());
        updated.put("confirmedRequirementCoveragePercent", 0.0);
        updated.put("fullChainCoveragePercent", 0.0);
        ObjectNode audit = output.putObject("catalogDenominatorReconciliation");
        audit.put("policy", "CURRENT_ENTIRELY_UNMAPPED_CATALOG_ONLY");
        int original = evidence.path("catalogDenominatorReconciliation").path("previousDenominator").asInt(previous);
        audit.put("previousDenominator", original);
        audit.put("currentDenominator", rules.size());
        audit.put("historicalExecutionReassessed", false);
        audit.put("note", "Preserves historical traceability/payload findings and AI inventory; does not replay absent producer inputs or establish new matches.");
        var canonicalRules = audit.putArray("currentCanonicalRules");
        rules.keySet().stream().sorted().forEach(canonicalRules::add);
        return output;
    }
}
