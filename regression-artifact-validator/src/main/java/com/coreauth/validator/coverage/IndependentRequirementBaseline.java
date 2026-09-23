package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.validation.ValidationResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Test-Solution-owned requirements and anchors that define the coverage denominator. */
public final class IndependentRequirementBaseline {
    private final List<Requirement> requirements;

    public IndependentRequirementBaseline(List<Requirement> requirements) {
        this.requirements = requirements == null ? List.of() : List.copyOf(requirements);
    }

    public List<Requirement> requirements() {
        return requirements;
    }

    public List<Requirement> inScopeRequirements() {
        return requirements.stream().filter(Requirement::includedInCoverage).toList();
    }

    public List<SourceAnchor> anchors() {
        return requirements.stream().flatMap(requirement -> requirement.sourceAnchors().stream()).toList();
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("independent-requirement-baseline");
        Set<String> ids = new HashSet<>();
        Set<String> anchors = new HashSet<>();
        for (Requirement requirement : requirements) {
            if (requirement.id() == null || requirement.id().isBlank()) {
                result.addError("IndependentBaseline", "requirement id is required");
            } else if (!ids.add(requirement.id())) {
                result.addError("IndependentBaseline", "duplicate requirement id: " + requirement.id());
            }
            if (requirement.sourceAnchors().isEmpty()) {
                result.addError("CanonicalAnchor", requirement.id() + " must have at least one sourceAnchor");
            }
            for (SourceAnchor anchor : requirement.sourceAnchors()) {
                if (!isComplete(anchor)) {
                    result.addError("CanonicalAnchor", requirement.id() + " contains an incomplete sourceAnchor");
                } else if (!anchors.add(anchor.canonicalKey())) {
                    result.addError("CanonicalAnchor", "duplicate sourceAnchor: " + anchor.canonicalKey());
                }
            }
        }
        if (inScopeRequirements().isEmpty()) {
            result.addError("CoverageDenominator", "at least one requirement must be included in coverage");
        }
        return result;
    }

    private static boolean isComplete(SourceAnchor anchor) {
        return anchor != null
                && !blank(anchor.getSpecification())
                && !blank(anchor.getVersion())
                && !blank(anchor.getSegment())
                && !blank(anchor.getRule());
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }

    public record Requirement(String id, List<SourceAnchor> sourceAnchors, boolean includedInCoverage) {
        public Requirement {
            sourceAnchors = sourceAnchors == null ? List.of() : List.copyOf(sourceAnchors);
        }
    }
}