package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Path;

/** Read-only command that verifies Run2 can be streamed and reports canonical artifact counts. */
public final class Run2IntakeSummary {
    private Run2IntakeSummary() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: Run2IntakeSummary <traceability-file> <run-root>");
        }
        Run2TraceabilityAdapter.AdaptedRun adapted = new Run2TraceabilityAdapter().adapt(
                Path.of(args[0]), Path.of(args[1]), new Run2Crosswalk(java.util.List.of()));
        ObjectNode summary = new ObjectMapper().createObjectNode();
        summary.put("packageId", adapted.artifactPackage().getManifest().getPackageId());
        summary.put("specificationVersion", adapted.artifactPackage().getManifest().getSpecificationVersion());
        summary.put("requirements", adapted.requirements());
        summary.put("scenarios", adapted.scenarios());
        summary.put("testCases", adapted.testCases());
        summary.put("resolvedTestData", adapted.testData());
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(summary));
    }
}