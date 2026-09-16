package com.coreauth.validator.canonical;

import java.nio.file.Path;

/** Command-line entry point for refreshing Segment 100 validation evidence. */
public final class GenerateSegment100ValidationEvidence {
    private GenerateSegment100ValidationEvidence() { }

    public static void main(String[] args) throws Exception {
        Path root = Path.of(".").toAbsolutePath().normalize();
        Segment100ValidationEvidenceReport.ReportFiles files = new Segment100ValidationEvidenceReport().generate(
                root.resolve("test-input/ai-solution/test-data/segment-100/transaction-types"),
                root.resolve("test-input/ai-solution/test-data/segment-100/lifecycle"),
                root.resolve("test-output/test-json/segment-100-code-flow-br-ts-tc-testdata.json"),
                root.resolve("test-output/traceability-matrix/segment-100"));
        System.out.println("Generated: " + files.json());
        System.out.println("Generated: " + files.markdown());
    }
}
