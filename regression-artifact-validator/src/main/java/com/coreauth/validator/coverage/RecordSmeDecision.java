package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/** Appends one reviewed SME decision to the Test Solution-owned decision register. */
public final class RecordSmeDecision {
    private static final Set<String> DECISIONS = Set.of(
            "NEW_RULE", "DUPLICATE", "REJECT", "REVIEW_REQUIRED", "CONFIRMED_MATCH");

    private RecordSmeDecision() { }

    public static void main(String[] args) throws Exception {
        if ((args.length == 2 || args.length == 4) && "--decision-json".equals(args[0])) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode input = mapper.readTree(Path.of(args[1]).toFile());
            String subjectType = input.path("subjectType").asText("");
            Path requestedRegister = args.length == 4 && "--decision-register".equals(args[2])
                    ? Path.of(args[3]) : null;
            if (args.length == 4 && requestedRegister == null) {
                throw new IllegalArgumentException("Expected --decision-register <path>");
            }
            Path registerFile = registerPath(subjectType, requestedRegister);
            ObjectNode register = (ObjectNode) mapper.readTree(registerFile.toFile());
            ObjectNode appended = record(register, input);
            mapper.writerWithDefaultPrettyPrinter().writeValue(registerFile.toFile(), register);
            System.out.printf("recorded subjectType=%s subjectId=%s decision=%s revision=%d totalEvents=%d%n",
                    subjectType, input.path("subjectId").asText(), input.path("decision").asText(),
                    appended.path("revision").asInt(), register.path("decisions").size());
            return;
        }
        if (args.length < 8 || args.length > 13) {
            throw new IllegalArgumentException("Usage: <subjectId> <segment> <decision> <reviewer> <rationale> <specSection> <specElement> <specRule> [subjectType] [approvedRuleTitle] [approvedRuleText] [relatedTestRuleId] [NEW_RULE_INDEPENDENCE_ATTESTATION]");
        }
        String subjectType = args.length > 8 ? args[8] : "AI_ONLY_BR";
        Path registerFile = registerPath(subjectType);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode register = (ObjectNode) mapper.readTree(registerFile.toFile());
        ObjectNode appended = record(register, args[0], args[1], args[2], args[3], args[4],
                args[5], args[6], args[7], subjectType,
                args.length > 9 ? args[9] : "", args.length > 10 ? args[10] : "",
                args.length > 11 ? args[11] : "", args.length > 12 ? args[12] : "");
        mapper.writerWithDefaultPrettyPrinter().writeValue(registerFile.toFile(), register);
        System.out.printf("recorded subjectType=%s subjectId=%s decision=%s revision=%d totalEvents=%d%n",
                subjectType, args[0], args[2], appended.path("revision").asInt(), register.path("decisions").size());
    }

    static ObjectNode record(ObjectNode register, JsonNode input) {
        String subjectType = required(input, "subjectType");
        String segment = required(input, "segment");
        JsonNode anchor = input.path("sourceAnchor");
        if (!"ATL105".equals(anchor.path("specification").asText())
                || !"2026-3".equals(anchor.path("version").asText())
                || anchor.path("section").asText().isBlank()
                || anchor.path("element").asText().isBlank()
                || anchor.path("rule").asText().isBlank()
                || !anchorContainsSegment(anchor.path("segment").asText(), segment)) {
            throw new IllegalArgumentException("decision input requires a complete ATL105 2026-3 sourceAnchor for its segment");
        }
        JsonNode approvedRule = input.path("approvedRule");
        String targetRule = "CONFIRMED_MATCH".equals(input.path("decision").asText())
                ? input.path("matchedTestRuleId").asText("") : input.path("duplicateOfRuleId").asText("");
        return record(register, required(input, "subjectId"), segment, required(input, "decision"),
                required(input, "reviewer"), required(input, "rationale"), anchor.path("section").asText(),
                anchor.path("element").asText(), anchor.path("rule").asText(), subjectType,
                approvedRule.path("title").asText(""), approvedRule.path("requirement").asText(), targetRule,
                input.path("independenceAttestation").asText(""));
    }

    static ObjectNode record(ObjectNode register, String subjectId, String segment, String decision,
                             String reviewer, String rationale, String section, String element,
                             String rule, String subjectType, String approvedRuleTitle,
                             String approvedRuleText, String relatedTestRuleId,
                             String independenceAttestation) {
        if (!DECISIONS.contains(decision)) throw new IllegalArgumentException("Unsupported decision: " + decision);
        if (!Set.of("AI_ONLY_BR", "AI_TO_TEST_CROSSWALK").contains(subjectType)) {
            throw new IllegalArgumentException("Unsupported subject type: " + subjectType);
        }
        if (reviewer == null || reviewer.isBlank() || "UNREVIEWED".equals(reviewer)) {
            throw new IllegalArgumentException("A reviewed decision requires a named reviewer");
        }
        if (rationale == null || rationale.isBlank()) throw new IllegalArgumentException("A reviewed decision requires rationale");
        if (section == null || section.isBlank() || element == null || element.isBlank() || rule == null || rule.isBlank()) {
            throw new IllegalArgumentException("A reviewed decision requires a complete ATL105 source anchor");
        }
        if ("NEW_RULE".equals(decision) && (approvedRuleTitle.isBlank() || approvedRuleText.isBlank()
                || !"I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI".equals(independenceAttestation))) {
            throw new IllegalArgumentException("NEW_RULE requires reviewer-authored title/text and I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI attestation");
        }
        if (("CONFIRMED_MATCH".equals(decision) || "DUPLICATE".equals(decision))
                && (relatedTestRuleId == null || relatedTestRuleId.isBlank())) {
            throw new IllegalArgumentException(decision + " requires relatedTestRuleId");
        }

        boolean declaredType = false;
        for (JsonNode allowed : register.path("subjectTypes")) {
            if (subjectType.equals(allowed.asText())) declaredType = true;
        }
        if (!declaredType) throw new IllegalArgumentException("Register does not declare subject type " + subjectType);
        String subjectKey = subjectType + "|" + subjectId;
        Map<String, JsonNode> latest = SmeDecisionHistory.latestBySubject(register);
        JsonNode prior = latest.get(subjectKey);
        if (prior == null) throw new IllegalArgumentException("No pending/reviewable subject in register: " + subjectId);
        if (!segment.equals(prior.path("segment").asText())) {
            throw new IllegalArgumentException("Segment does not match registered subject " + subjectId);
        }

        ObjectNode entry = new ObjectMapper().createObjectNode();
        entry.put("decisionId", "SME-" + segment + "-" + subjectId.replaceAll("[^A-Za-z0-9]+", "-")
                + "-" + UUID.randomUUID());
        entry.put("subjectType", subjectType);
        entry.put("subjectId", subjectId);
        entry.put("segment", segment);
        entry.put("decision", decision);
        entry.put("reviewer", reviewer);
        entry.put("reviewedAt", Instant.now().toString());
        entry.put("rationale", rationale);
        ArrayNode evidence = entry.putArray("evidence");
        ObjectNode source = evidence.addObject();
        source.put("type", "ATL105_SPECIFICATION");
        ObjectNode anchor = source.putObject("sourceAnchor");
        anchor.put("specification", "ATL105");
        anchor.put("version", "2026-3");
        anchor.put("section", section);
        anchor.put("segment", segment);
        anchor.put("element", element);
        anchor.put("rule", rule);
        source.put("reviewerProvided", true);
        if ("NEW_RULE".equals(decision)) {
            ObjectNode approvedRule = entry.putObject("approvedRule");
            approvedRule.put("title", approvedRuleTitle);
            approvedRule.put("requirement", approvedRuleText);
            approvedRule.put("source", "ATL105");
            approvedRule.put("independentOfAiWording", "I_AUTHORED_THIS_INDEPENDENTLY_FROM_AI".equals(independenceAttestation));
        } else if ("CONFIRMED_MATCH".equals(decision)) {
            entry.put("matchedTestRuleId", relatedTestRuleId);
        } else if ("DUPLICATE".equals(decision)) {
            entry.put("duplicateOfRuleId", relatedTestRuleId);
        }
        ObjectNode candidateRegister = register.deepCopy();
        SmeDecisionHistory.append(candidateRegister, entry);
        ValidateSmeDecisionRegister.validate(candidateRegister);
        return SmeDecisionHistory.append(register, entry);
    }

    private static Path registerPath(String subjectType) {
        String fileName = switch (subjectType) {
            case "AI_ONLY_BR" -> "ai-only-sme-decision-register.json";
            case "AI_TO_TEST_CROSSWALK" -> "semantic-br-decision-register.json";
            default -> throw new IllegalArgumentException("Unsupported subject type: " + subjectType);
        };
        return Atl105Paths.testOutput().resolve(Path.of("ai-solution-independent-review", fileName));
    }

    static Path registerPath(String subjectType, Path requestedRegister) {
        if (requestedRegister == null) return registerPath(subjectType);
        if (!Set.of("AI_ONLY_BR", "AI_TO_TEST_CROSSWALK").contains(subjectType)) {
            throw new IllegalArgumentException("Unsupported subject type: " + subjectType);
        }
        return explicitRegisterPath(Atl105Paths.testOutput(), requestedRegister);
    }

    static Path explicitRegisterPath(Path testOutputRoot, Path requestedRegister) {
        Path testOutput = testOutputRoot.toAbsolutePath().normalize();
        Path requested = requestedRegister.toAbsolutePath().normalize();
        if (!requested.startsWith(testOutput)) {
            throw new IllegalArgumentException("Explicit decision register must be beneath ATL105 test-output");
        }
        return requested;
    }

    private static boolean anchorContainsSegment(String anchorSegments, String expectedSegment) {
        for (String segment : anchorSegments.split("[,|]")) {
            if (expectedSegment.equals(segment.trim())) return true;
        }
        return false;
    }

    private static String required(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) throw new IllegalArgumentException("decision input requires " + field);
        return value;
    }
}
