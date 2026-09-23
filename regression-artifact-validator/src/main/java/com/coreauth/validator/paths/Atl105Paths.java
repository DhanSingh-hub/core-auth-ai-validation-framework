package com.coreauth.validator.paths;

import java.nio.file.Path;

/** Canonical module-relative locations for the ATL105 specification pack. */
public final class Atl105Paths {
    private static final Path ROOT = Path.of("specifications", "ATL105");

    private Atl105Paths() {
    }

    public static Path root() { return ROOT; }
    public static Path docs() { return ROOT.resolve("docs"); }
    public static Path schemas() { return ROOT.resolve("schemas"); }
    public static Path testInput() { return ROOT.resolve("test-input"); }
    public static Path testOutput() { return ROOT.resolve("test-output"); }
    public static Path testJson() { return testOutput().resolve("test-json"); }
    public static Path testJson(String fileName) { return testJson().resolve(fileName); }
    public static Path consolidatedReports() { return testOutput().resolve("consolidated-reports"); }
    public static Path consolidatedReport(String segment) {
        return consolidatedReports().resolve("SEGMENT-" + normalizeSegment(segment) + "-CONSOLIDATED-REPORT.txt");
    }
    public static Path ruleCatalog(String segment) {
        String normalized = normalizeSegment(segment).toLowerCase(java.util.Locale.ROOT);
        return docs().resolve(Path.of("specs", "kb", "segment-" + normalized, "coverage",
                "segment-" + normalized + "-rule-catalog.json"));
    }
    public static Path aiTestData(String segment) {
        return testInput().resolve(Path.of("ai-solution", "test-data",
                "segment-" + normalizeSegment(segment).toLowerCase(java.util.Locale.ROOT)));
    }

    private static String normalizeSegment(String segment) {
        if (segment == null || segment.isBlank()) {
            throw new IllegalArgumentException("segment is required");
        }
        return segment.trim().toUpperCase(java.util.Locale.ROOT);
    }
}