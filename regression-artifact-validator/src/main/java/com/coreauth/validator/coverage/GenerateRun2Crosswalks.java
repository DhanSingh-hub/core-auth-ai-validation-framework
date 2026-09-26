package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Generates denominator-complete Run2 crosswalks for the currently trained ATL105 segments. */
public final class GenerateRun2Crosswalks {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private GenerateRun2Crosswalks() { }

    public static void main(String[] args) throws Exception {
        Path outputDirectory = args.length == 0
                ? Atl105Paths.testOutput().resolve(Path.of("ai-solution-independent-review", "run2-crosswalks"))
                : Path.of(args[0]);
        Path priorDirectory = Atl105Paths.testOutput().resolve(
                Path.of("ai-artifacts", "coverage-reports"));
        Path baselineRoot = Atl105Paths.testOutput().resolve("test-json");
        ReviewedRun2CrosswalkMigrationService migrationService =
                new ReviewedRun2CrosswalkMigrationService();
        Run2CrosswalkWriter writer = new Run2CrosswalkWriter();

        for (String segment : SEGMENTS) {
            Path prior = priorDirectory.resolve(
                    "POC-AI-Segment-" + segment + "-BR-Coverage-Crosswalk.json");
            if (!Files.isRegularFile(prior)) prior = null;
            var migration = migrationService.migrate(segment, Atl105Paths.ruleCatalog(segment),
                    prior, baselineRoot, "ATL105-SME-" + segment);
            Path output = outputDirectory.resolve("segment-" + segment + "-run2-crosswalk.json");
            writer.write(segment, "2026-09-23/Run1+Run2", migration, output);
            System.out.printf("segment=%s denominator=%d confirmed=%d review=%d missing=%d unresolvedPriorConfirmed=%d%n",
                    segment, migration.denominator(), migration.confirmed(), migration.reviewRequired(),
                    migration.missing(), migration.unresolvedPriorConfirmedAiRequirementIds().size());
        }
    }
}