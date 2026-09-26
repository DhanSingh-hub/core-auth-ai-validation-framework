package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Writes an auditable, SME-owned Run2 crosswalk and migration summary. */
public final class Run2CrosswalkWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public void write(String segment, String sourceRun,
                      ReviewedRun2CrosswalkMigrationService.MigrationResult migration,
                      Path output) throws IOException {
        ObjectNode root = mapper.createObjectNode();
        root.put("artifact", "run2-ai-to-test-solution-crosswalk");
        root.put("segment", segment);
        root.put("sourceRun", sourceRun);
        root.put("decisionAuthority", "TEST_SOLUTION_REVIEW");
        root.put("generationMethod", "PRIOR_REVIEW_EXACT_CANONICAL_MIGRATION");
        ObjectNode summary = root.putObject("summary");
        summary.put("denominator", migration.denominator());
        summary.put("confirmed", migration.confirmed());
        summary.put("reviewRequired", migration.reviewRequired());
        summary.put("missing", migration.missing());
        summary.put("unresolvedPriorConfirmed", migration.unresolvedPriorConfirmedAiRequirementIds().size());
        root.set("mappings", mapper.valueToTree(migration.crosswalk().mappings()));
        root.set("unresolvedPriorConfirmedAiRequirementIds",
                mapper.valueToTree(migration.unresolvedPriorConfirmedAiRequirementIds()));
        Files.createDirectories(output.toAbsolutePath().normalize().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), root);
    }
}