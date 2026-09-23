package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.canonical.SourceAnchor;

import java.util.List;

/** SME-owned decisions that map producer-local Run2 requirements to the independent baseline. */
public record Run2Crosswalk(List<Mapping> mappings) {
    public Run2Crosswalk {
        mappings = mappings == null ? List.of() : List.copyOf(mappings);
    }

    public record Mapping(
            String testRequirementId,
            List<String> aiRequirementIds,
            RequirementMatchStatus matchStatus,
            String matchReason,
            String reviewOwner,
            List<SourceAnchor> sourceAnchors) {
        public Mapping {
            aiRequirementIds = aiRequirementIds == null ? List.of() : List.copyOf(aiRequirementIds);
            sourceAnchors = sourceAnchors == null ? List.of() : List.copyOf(sourceAnchors);
        }
    }
}