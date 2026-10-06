package com.coreauth.validator;

import com.coreauth.validator.coverage.Run2SegmentReportWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Run2SegmentReportWriterTest {
    @TempDir Path directory;

    @Test
    void rendersIndependentRuleAndAiBrInventoryDenominatorsSeparately() throws Exception {
        Path crosswalk = directory.resolve("crosswalk.json");
        Files.writeString(crosswalk, "{\"mappings\":[]}");
        Path evidence = directory.resolve("evidence.json");
        Files.writeString(evidence, """
                {
                  "segment":"100",
                  "sourceRun":"synthetic-run",
                  "versionResolutionId":"",
                  "artifacts":{"requirements":2,"scenarios":1,"testCases":0,"testData":0},
                  "assessment":{
                    "decision":"REVIEW_REQUIRED",
                    "coverageDenominatorType":"INDEPENDENT_TEST_SOLUTION_RULES",
                    "coverageDenominatorStructurallyValid":true,
                    "coverageDenominatorCertification":"NOT_ESTABLISHED_BY_STRUCTURAL_ASSESSMENT",
                    "coverageDenominator":1,
                    "confirmedRequirements":1,
                    "reviewRequired":0,
                    "missingRequirements":0,
                    "fullChainRequirements":0,
                    "confirmedRequirementCoveragePercent":100.0,
                    "fullChainCoveragePercent":0.0,
                    "aiBusinessRequirementDenominator":2,
                    "aiBusinessRequirementsWithCrosswalkDisposition":1,
                    "confirmedAiBusinessRequirements":1,
                    "reviewRequiredAiBusinessRequirements":0,
                    "missingMappedAiBusinessRequirements":0,
                    "unmappedAiBusinessRequirements":1,
                    "confirmedAiBusinessRequirementInventoryPercent":50.0
                  },
                  "payloadBatch":{}
                }
                """);

        new Run2SegmentReportWriter().write(crosswalk, evidence, directory.resolve("reports"));

        String report = Files.readString(directory.resolve("reports/segment-100-run2-review.md"));
        assertThat(report).contains("Independent Test Solution Rule Coverage")
                .contains("independent Test Solution in-scope rules")
                .contains("AI Business-Requirement Mapping")
                .contains("|1|1|0|0|0|")
                .contains("|2|1|1|0|0|1|50.0%|");
    }

    @Test
    void missingHistoricalAiMetricsAreNotRenderedAsZero() throws Exception {
        Path crosswalk = directory.resolve("legacy-crosswalk.json");
        Files.writeString(crosswalk, "{\"mappings\":[]}");
        Path evidence = directory.resolve("legacy-evidence.json");
        Files.writeString(evidence, """
                {
                  "segment":"101",
                  "artifacts":{},
                  "assessment":{"decision":"REVIEW_REQUIRED","coverageDenominator":1,
                    "confirmedRequirements":0,"reviewRequired":0,"missingRequirements":1,
                    "fullChainRequirements":0,"validation":{"baseline":{"valid":true}}},
                  "payloadBatch":{}
                }
                """);

        new Run2SegmentReportWriter().write(crosswalk, evidence, directory.resolve("legacy-reports"));

        String report = Files.readString(directory.resolve("legacy-reports/segment-101-run2-review.md"));
        assertThat(report).contains("NOT_RECORDED").doesNotContain("|0|0|0|0|0|0|0.0%|");
    }
}
