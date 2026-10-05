package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/** Defines when TD evidence may participate in the canonical TC -> TD chain. */
final class CanonicalTestDataLinkPolicy {
    private CanonicalTestDataLinkPolicy() {
    }

    static boolean hasCanonicalTestCaseLinks(JsonNode testData) {
        JsonNode links = testData == null ? null : testData.path("testCaseIds");
        if (links == null || !links.isArray() || links.isEmpty()) return false;
        for (JsonNode link : links) if (!link.isTextual() || link.asText().isBlank()) return false;
        return true;
    }

    static ObjectNode unlinkedCandidate(JsonNode testData, String sourceFile, ObjectMapper mapper) {
        String artifactId = testData == null ? "" : testData.path("id").asText("");
        ObjectNode candidate = mapper.createObjectNode();
        candidate.put("sourceFile", sourceFile);
        candidate.put("sourceArtifactId", artifactId);
        candidate.put("disposition", "NOT_IN_CANONICAL_CHAIN");
        candidate.put("reason", testData != null && testData.hasNonNull("coversBr")
                ? "coversBr identifies a BR but is not a canonical testCaseIds link; author and link a TC before counting this TD in a chain."
                : "No non-empty canonical testCaseIds link was supplied; keep this TD outside the canonical chain until linked.");
        candidate.put("producerClaimedReadiness", testData == null
                ? "NOT_DECLARED" : testData.path("readiness").asText("NOT_DECLARED"));
        if (testData != null) candidate.set("sourceArtifact", testData.deepCopy());
        return candidate;
    }

    static void requireExplicitReadiness(ObjectNode testData) {
        String readiness = testData.path("readiness").asText("");
        if ("EXECUTABLE".equals(readiness) || "EXTERNAL_FIXTURE_REQUIRED".equals(readiness)
                || "REVIEW_REQUIRED".equals(readiness)) {
            return;
        }
        if (!readiness.isBlank()) testData.put("producerDeclaredReadiness", readiness);
        testData.put("readiness", "REVIEW_REQUIRED");
        testData.put("readinessDispositionReason",
                "Readiness was absent or unrecognized in source evidence; aggregation cannot promote it to executable.");
    }
}
