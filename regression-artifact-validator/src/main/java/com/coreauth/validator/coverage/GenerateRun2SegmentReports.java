package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;

import java.nio.file.Path;
import java.util.List;

/** Renders Step 4 reports for the ten trained ATL105 segments. */
public final class GenerateRun2SegmentReports {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private GenerateRun2SegmentReports() { }

    public static void main(String[] args) throws Exception {
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path crosswalkRoot = reviewRoot.resolve("run2-crosswalks");
        Path validationRoot = reviewRoot.resolve("run2-validation");
        Path reportRoot = reviewRoot.resolve("run2-segment-reports");
        Run2SegmentReportWriter writer = new Run2SegmentReportWriter();
        for (String segment : SEGMENTS) {
            writer.write(crosswalkRoot.resolve("segment-" + segment + "-run2-crosswalk.json"),
                    validationRoot.resolve("segment-" + segment + "-validation.json"), reportRoot);
            System.out.println("generated segment=" + segment);
        }
    }
}