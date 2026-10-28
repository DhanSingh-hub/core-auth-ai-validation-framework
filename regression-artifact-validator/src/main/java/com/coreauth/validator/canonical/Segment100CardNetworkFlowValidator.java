package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Fail-closed validator for card-network evidence and source-backed transaction-flow rules.
 * The PAN is never read; BIN/IIN identification must be supplied by an approved range service.
 */
public final class Segment100CardNetworkFlowValidator {
    public enum Status { PASS, FAIL, REVIEW_REQUIRED }

    public record Finding(Status status, String code, String message) { }

    public record OpenQuestion(String id, String owner, String question, String context, String reason) { }

    public record Result(List<Finding> findings) {
        public Result {
            findings = List.copyOf(findings);
        }

        public Status status() {
            if (findings.stream().anyMatch(finding -> finding.status() == Status.FAIL)) return Status.FAIL;
            if (findings.stream().anyMatch(finding -> finding.status() == Status.REVIEW_REQUIRED)) {
                return Status.REVIEW_REQUIRED;
            }
            return Status.PASS;
        }

        public List<OpenQuestion> openQuestions() {
            List<OpenQuestion> questions = new ArrayList<>();
            for (Finding finding : findings) {
                if (finding.status() != Status.REVIEW_REQUIRED) continue;
                questions.add(new OpenQuestion(
                        "Q-SEG100-NETFLOW-" + finding.code() + "-" + (questions.size() + 1),
                        "Business Analyst / ATL105 SME",
                        questionFor(finding.code()),
                        finding.message(),
                        reasonFor(finding.code())));
            }
            return List.copyOf(questions);
        }
    }

    public Result validate(JsonNode artifact, JsonNode policy) {
        List<Finding> findings = new ArrayList<>();
        if (artifact == null || !artifact.isObject()) {
            return result(Status.FAIL, "INVALID_ARTIFACT", "Artifact must be a JSON object");
        }
        if (!validPolicy(policy, findings)) return new Result(findings);

        JsonNode evidence = artifact.path("networkEvidence");
        JsonNode binResolution = evidence.path("binIinResolution");
        JsonNode processor = evidence.path("processorNetwork");
        String binNetwork = text(binResolution, "network");
        String processorNetwork = text(processor, "network");
        String network = validateNetworkEvidence(binResolution, processor, binNetwork, processorNetwork,
                policy.path("networks"), findings);
        if (network == null) return new Result(findings);

        String flowId = text(artifact, "flowId");
        JsonNode flow = findById(policy.path("flows"), flowId);
        if (flow == null) {
            findings.add(new Finding(Status.REVIEW_REQUIRED, "FLOW_NOT_CONFIGURED",
                    "No source-backed flow definition is configured for " + flowId));
            return new Result(findings);
        }
        if (!hasSourceAnchor(flow)) {
            findings.add(new Finding(Status.REVIEW_REQUIRED, "FLOW_WITHOUT_SOURCE",
                    "Flow definition " + flowId + " has no complete source anchor"));
            return new Result(findings);
        }

        JsonNode steps = artifact.path("steps");
        JsonNode expectedRoles = flow.path("stepRoles");
        if (!steps.isArray() || !expectedRoles.isArray() || steps.size() != expectedRoles.size()
                || steps.isEmpty()) {
            findings.add(new Finding(Status.FAIL, "FLOW_SHAPE_MISMATCH",
                    "Flow steps must match the configured ordered step-role list for " + flowId));
            return new Result(findings);
        }

        boolean needsReview = false;
        for (int index = 0; index < steps.size(); index++) {
            JsonNode step = steps.get(index);
            String transactionType = text(step, "transactionType");
            String cardType = text(step, "cardType");
            String role = text(step, "role");
            String expectedRole = expectedRoles.get(index).asText(null);
            if (role == null || !role.equals(expectedRole)) {
                findings.add(new Finding(Status.FAIL, "STEP_ROLE_MISMATCH",
                        "Step " + index + " role does not match the configured flow"));
                continue;
            }
            if (transactionType == null || !contains(policy.path("transactionTypes"), transactionType)) {
                findings.add(new Finding(Status.FAIL, "UNKNOWN_TRANSACTION_TYPE",
                        "Step " + index + " has an unsupported or missing transaction type"));
                continue;
            }
            if (cardType == null || !contains(policy.path("cardTypes"), cardType)) {
                findings.add(new Finding(Status.FAIL, "UNKNOWN_CARD_TYPE",
                        "Step " + index + " has an unsupported or missing card type"));
                continue;
            }

            JsonNode rule = findRule(policy.path("rules"), network, flowId, index, transactionType, cardType);
            if (rule == null) {
                findings.add(new Finding(Status.REVIEW_REQUIRED, "RULE_NOT_CONFIGURED",
                        "No source-backed eligibility rule exists for network " + network + ", flow "
                                + flowId + ", step " + index + ", transaction " + transactionType
                                + ", card type " + cardType));
                needsReview = true;
                continue;
            }
            String disposition = text(rule, "disposition");
            if (!hasSourceAnchor(rule)) {
                findings.add(new Finding(Status.REVIEW_REQUIRED, "RULE_WITHOUT_SOURCE",
                        "Eligibility rule for " + network + "/" + flowId + " step " + index
                                + " has no source anchor"));
                needsReview = true;
            } else if ("NOT_SUPPORTED".equals(disposition)) {
                findings.add(new Finding(Status.FAIL, "COMBINATION_NOT_SUPPORTED",
                        "Source-backed policy marks " + network + "/" + flowId
                                + " step " + index + " as not supported"));
            } else if (!"SUPPORTED".equals(disposition)) {
                findings.add(new Finding(Status.REVIEW_REQUIRED, "RULE_REQUIRES_REVIEW",
                        "Eligibility rule for " + network + "/" + flowId + " step " + index
                                + " is not approved"));
                needsReview = true;
            }
        }
        if (findings.isEmpty()) findings.add(new Finding(Status.PASS, "VALIDATED",
                "Network evidence and every flow step match source-backed policy"));
        else if (needsReview && findings.stream().noneMatch(finding -> finding.status() == Status.REVIEW_REQUIRED)) {
            findings.add(new Finding(Status.REVIEW_REQUIRED, "POLICY_REVIEW_REQUIRED",
                    "One or more eligibility rules are not fully approved"));
        }
        return new Result(findings);
    }

    private static String validateNetworkEvidence(JsonNode binResolution, JsonNode processor, String binNetwork,
                                                  String processorNetwork, JsonNode supportedNetworks,
                                                  List<Finding> findings) {
        if (!binResolution.isObject() || !processor.isObject()
                || isBlank(binNetwork) || isBlank(processorNetwork)
                || isBlank(text(binResolution, "rangeId")) || isBlank(text(binResolution, "rangeTableVersion"))
                || isBlank(text(processor, "source"))) {
            findings.add(new Finding(Status.REVIEW_REQUIRED, "NETWORK_EVIDENCE_INCOMPLETE",
                    "Approved BIN/IIN resolution and processor network evidence are both required"));
            return null;
        }
        if (!contains(supportedNetworks, binNetwork) || !contains(supportedNetworks, processorNetwork)) {
            findings.add(new Finding(Status.FAIL, "UNKNOWN_CARD_NETWORK",
                    "Network evidence contains a value outside the configured card-network set"));
            return null;
        }
        if (!binNetwork.equals(processorNetwork)) {
            findings.add(new Finding(Status.FAIL, "NETWORK_EVIDENCE_CONFLICT",
                    "BIN/IIN resolution and processor-reported network disagree"));
            return null;
        }
        return binNetwork;
    }

    private static boolean validPolicy(JsonNode policy, List<Finding> findings) {
        if (policy == null || !policy.isObject() || isBlank(text(policy, "policyVersion"))
                || !policy.path("networks").isArray() || !policy.path("transactionTypes").isArray()
                || !policy.path("cardTypes").isArray() || !policy.path("flows").isArray()
                || !policy.path("rules").isArray()) {
            findings.add(new Finding(Status.REVIEW_REQUIRED, "POLICY_MISSING_OR_INVALID",
                    "A versioned policy with networks, transaction types, card types, flows, and rules is required"));
            return false;
        }
        if (hasDuplicates(policy.path("networks")) || hasDuplicates(policy.path("transactionTypes"))
                || hasDuplicates(policy.path("cardTypes"))) {
            findings.add(new Finding(Status.FAIL, "POLICY_DUPLICATE_DOMAIN_VALUE",
                    "Policy domain lists must not contain duplicates"));
            return false;
        }
        return true;
    }

    private static JsonNode findRule(JsonNode rules, String network, String flowId, int stepIndex,
                                     String transactionType, String cardType) {
        for (JsonNode rule : rules) {
            if (network.equals(text(rule, "network")) && flowId.equals(text(rule, "flowId"))
                    && stepIndex == rule.path("stepIndex").asInt(-1)
                    && transactionType.equals(text(rule, "transactionType"))
                    && cardType.equals(text(rule, "cardType"))) return rule;
        }
        return null;
    }

    private static JsonNode findById(JsonNode values, String id) {
        if (id == null || !values.isArray()) return null;
        for (JsonNode value : values) if (id.equals(text(value, "id"))) return value;
        return null;
    }

    private static boolean hasSourceAnchor(JsonNode rule) {
        JsonNode anchors = rule.path("sourceAnchors");
        if (!anchors.isArray() || anchors.isEmpty()) return false;
        for (JsonNode anchor : anchors) {
            if (isBlank(text(anchor, "specification")) || isBlank(text(anchor, "version"))
                    || isBlank(text(anchor, "section")) || isBlank(text(anchor, "rule"))) return false;
        }
        return true;
    }

    private static boolean contains(JsonNode values, String expected) {
        if (expected == null || !values.isArray()) return false;
        for (JsonNode value : values) if (expected.equals(value.asText(null))) return true;
        return false;
    }

    private static boolean hasDuplicates(JsonNode values) {
        Set<String> seen = new HashSet<>();
        for (JsonNode value : values) {
            String text = value.asText(null);
            if (text == null || !seen.add(text)) return true;
        }
        return false;
    }

    private static String text(JsonNode node, String field) {
        return node == null ? null : node.path(field).asText(null);
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static String questionFor(String code) {
        return switch (code) {
            case "POLICY_MISSING_OR_INVALID" ->
                    "Which approved, versioned source should define the network, transaction, card-type, and flow domains?";
            case "NETWORK_EVIDENCE_INCOMPLETE" ->
                    "Which approved BIN/IIN range source and processor field should identify the card network, and how should their versions be governed?";
            case "FLOW_NOT_CONFIGURED" ->
                    "What are the valid ordered transaction steps, lifecycle roles, and correlation requirements for this flow?";
            case "FLOW_WITHOUT_SOURCE" ->
                    "Which exact ATL105 clause or approved processor rule defines this flow's ordered steps and lifecycle correlation?";
            case "RULE_NOT_CONFIGURED" ->
                    "Is this network, transaction type, card type, and flow-step combination supported, prohibited, or conditional? Please cite the controlling ATL105 clause or approved processor rule.";
            case "RULE_WITHOUT_SOURCE" ->
                    "Which exact ATL105 clause or approved processor rule supports this proposed eligibility decision?";
            case "RULE_REQUIRES_REVIEW" ->
                    "Should this combination be supported, prohibited, or conditional, and what source-backed conditions must the validator enforce?";
            case "POLICY_REVIEW_REQUIRED" ->
                    "Which policy rules remain unapproved, and who owns approval of each rule?";
            default -> "What authoritative business rule determines the behavior reported in this finding?";
        };
    }

    private static String reasonFor(String code) {
        return switch (code) {
            case "NETWORK_EVIDENCE_INCOMPLETE" ->
                    "The artifact does not contain both required, versioned network-identification results.";
            case "FLOW_NOT_CONFIGURED", "FLOW_WITHOUT_SOURCE", "RULE_NOT_CONFIGURED",
                    "RULE_WITHOUT_SOURCE", "RULE_REQUIRES_REVIEW", "POLICY_REVIEW_REQUIRED" ->
                    "ATL105 does not establish this behavior in the currently approved evidence.";
            default -> "The approved validation policy or supporting evidence is incomplete.";
        };
    }

    private static Result result(Status status, String code, String message) {
        return new Result(List.of(new Finding(status, code, message)));
    }
}
