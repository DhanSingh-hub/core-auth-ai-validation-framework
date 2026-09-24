package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

/** Verifies and records the 2026-09-23 Run1+Run2 composite AI delivery boundary. */
public final class VerifyRun1Run2CompositeDelivery {
    private VerifyRun1Run2CompositeDelivery() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path deliveryRoot = Atl105Paths.testInput().resolve(
                Path.of("ai-solution", "runs", "2026-09-23"));
        Path requirementsFile = deliveryRoot.resolve(Path.of("Run1", "step5_requirements", "approved",
                "requirement_catalog.json"));
        Path candidatesFile = deliveryRoot.resolve(Path.of("Run1", "step5_requirements", "candidates",
                "requirement_candidates.json"));
        Path traceabilityFile = deliveryRoot.resolve(Path.of("Run2", "traceability_matrix_full.json"));
        JsonNode run1 = mapper.readTree(requirementsFile.toFile());
        JsonNode run2 = mapper.readTree(traceabilityFile.toFile());
        Map<String, String> run1Requirements = new HashMap<>();
        for (JsonNode requirement : run1.path("requirements")) {
            run1Requirements.put(requirement.path("id").asText(), requirement.path("statement").asText());
        }
        Map<String, String> run2Requirements = new HashMap<>();
        for (JsonNode requirement : run2.path("requirements")) {
            run2Requirements.put(requirement.path("requirement_id").asText(), requirement.path("statement").asText());
        }
        int matchingStatements = 0;
        for (Map.Entry<String, String> requirement : run1Requirements.entrySet()) {
            if (requirement.getValue().equals(run2Requirements.get(requirement.getKey()))) matchingStatements++;
        }

        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "ai-composite-delivery-provenance");
        result.put("deliveryId", "ATL105-AI-2026-09-23-RUN1-RUN2");
        result.put("deliveryLabel", "2026-09-23/Run1+Run2");
        result.put("sourceId", run1.path("source_id").asText());
        result.put("relationship", "Run1 requirements and scenario-generation stage feed Run2 materialized scenario output, test cases, test data, and traceability");
        ObjectNode run1Node = result.putObject("run1");
        run1Node.put("role", "requirements-and-scenario-generation");
        run1Node.put("approvedRequirementCatalog", deliveryRoot.relativize(requirementsFile).toString().replace('\\', '/'));
        run1Node.put("approvedRequirementSha256", sha256(requirementsFile));
        run1Node.put("candidateRequirementSha256", sha256(candidatesFile));
        run1Node.put("requirements", run1Requirements.size());
        run1Node.put("state", run1.path("state").asText());
        run1Node.put("approvalNote", run1.path("approval_note").asText());
        run1Node.put("flaggedForReview", run1.path("flagged_for_review").asInt());
        run1Node.put("lowConfidence", run1.path("confidence_summary").path("low_confidence_count").asInt());
        run1Node.put("standaloneScenarioArtifactPresentInReceivedRun1Folder", false);
        ObjectNode run2Node = result.putObject("run2");
        run2Node.put("role", "materialized-scenario-markdown-and-traceability-test-cases-test-data");
        run2Node.put("requirementsEmbedded", run2Requirements.size());
        run2Node.put("scenarios", run2.path("summary").path("total_scenarios").asInt());
        run2Node.put("testCases", run2.path("summary").path("total_test_cases").asInt());
        run2Node.put("reportedTestDataFiles", run2.path("summary").path("test_cases_with_data_file").asInt());
        run2Node.put("traceabilitySha256", sha256(traceabilityFile));
        ObjectNode linkage = result.putObject("linkageVerification");
        linkage.put("run1RequirementIds", run1Requirements.size());
        linkage.put("run2RequirementIds", run2Requirements.size());
        linkage.put("matchingIds", run1Requirements.keySet().stream().filter(run2Requirements::containsKey).count());
        linkage.put("matchingIdsAndStatements", matchingStatements);
        linkage.put("run1Only", run1Requirements.keySet().stream().filter(id -> !run2Requirements.containsKey(id)).count());
        linkage.put("run2Only", run2Requirements.keySet().stream().filter(id -> !run1Requirements.containsKey(id)).count());
        linkage.put("verified", matchingStatements == run1Requirements.size()
                && run1Requirements.size() == run2Requirements.size());
        result.put("interpretation", "Run1 and Run2 are one composite delivery; Run1 owns requirements and scenario generation, while Run2 carries the received scenario outputs and downstream artifacts. Run2 is not an independent requirement source.");

        Path output = Atl105Paths.testOutput().resolve(Path.of("ai-solution-independent-review",
                "run1-run2-composite-delivery.json"));
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        System.out.printf("requirements=%d matchingIdsAndStatements=%d verified=%s%n",
                run1Requirements.size(), matchingStatements, linkage.path("verified").asBoolean());
    }

    private static String sha256(Path file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream input = Files.newInputStream(file)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer)) >= 0) digest.update(buffer, 0, read);
        }
        return HexFormat.of().withUpperCase().formatHex(digest.digest());
    }
}