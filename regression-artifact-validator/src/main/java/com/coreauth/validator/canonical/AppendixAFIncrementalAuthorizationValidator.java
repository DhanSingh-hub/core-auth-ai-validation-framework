package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Locale;
import java.util.Set;

public final class AppendixAFIncrementalAuthorizationValidator {
    private static final String SOURCE = "AppendixAF";
    private static final Set<String> NETWORKS = Set.of("DISCOVER", "VISA", "MASTERCARD", "AMERICAN EXPRESS");
    private static final Set<String> ROLES = Set.of("ORIGINAL_AUTH", "INCREMENTAL_AUTH", "PARTIAL_REVERSAL", "INCREMENTAL_AUTH_COMPLETION");
    private static final Set<String> SUBSEQUENT_ROLES = Set.of("INCREMENTAL_AUTH", "PARTIAL_REVERSAL", "INCREMENTAL_AUTH_COMPLETION");

    public ValidationResult validate(JsonNode flow) {
        ValidationResult result = new ValidationResult("atl105-appendix-af-incremental-authorization");
        result.addWarning("Appendix AF logical observations only; source approval, physical wire, processor behavior and host outcomes are not certified.");
        if (!flow.isObject() || !"2026-4".equals(flow.path("specificationVersion").asText())) {
            result.addWarning("AF-CONTEXT: ATL105 2026-4 context is required; other releases are not assessed by this overlay.");
            return result;
        }
        String network = flow.path("network").asText("").trim().toUpperCase(Locale.ROOT);
        if (!NETWORKS.contains(network)) {
            result.addWarning("AF-NETWORK: Supported AF applicability is Discover, Visa, Mastercard or American Express.");
            return result;
        }
        JsonNode steps = flow.path("steps");
        if (!steps.isArray() || steps.isEmpty()) {
            result.addWarning("AF-FLOW: Ordered authorization lifecycle steps are required.");
            return result;
        }

        String terminalId = flow.path("terminalId").asText("");
        JsonNode original = null;
        boolean completed = false;
        for (JsonNode step : steps) {
            String role = step.path("role").asText("");
            if (!ROLES.contains(role)) {
                result.addWarning("AF-ROLE: Unsupported transaction type is not assessed: " + role);
                continue;
            }
            checkTable82(step, network, result);
            if ("ORIGINAL_AUTH".equals(role)) {
                if (original != null) {
                    error(result, "AF-ORIGINAL-PREDECESSOR", "This logical flow represents one open Incremental Auth purchase; a second Original Auth needs a separate flow.");
                    continue;
                }
                original = step;
                checkTransactionIdUniqueness(flow, step, terminalId, network, result);
                continue;
            }

            if (original == null) {
                error(result, "AF-ORIGINAL-PREDECESSOR", "Incremental Auth, Partial Reversal and Incremental Auth Completion must follow an Original Auth.");
            } else {
                if (completed) error(result, "AF-COMPLETION-CLOSED", "No subsequent transaction is permitted for this Terminal Transaction ID after completion.");
                checkApprovalCode(original, step, result);
            }
            if ("INCREMENTAL_AUTH_COMPLETION".equals(role)) completed = true;
        }
        if (original == null) result.addWarning("AF-ORIGINAL-PREDECESSOR: No Original Auth observation was supplied.");
        return result;
    }

    private static void checkTable82(JsonNode step, String network, ValidationResult result) {
        boolean discover = "DISCOVER".equals(network);
        String role = step.path("role").asText();
        JsonNode present = step.path("table82Present");
        if (!present.isBoolean()) {
            result.addWarning("AF-TABLE82-PRESENCE: Table 82 presence is unknown for " + role + ".");
            return;
        }
        if (!present.asBoolean()) {
            if (discover) error(result, "AF-TABLE82-PRESENCE", "Discover IA transactions must contain Incremental Auth Data in Segment 111 Table 82.");
            else result.addWarning("AF-TABLE82-RECOMMENDATION: Table 82 is recommended for this network; the source does not state the Discover mandatory rule for it.");
            return;
        }
        JsonNode data = step.path("incrementalAuthData");
        if (!data.isObject() || !data.path("transactionId").isTextual() || data.path("transactionId").asText().isBlank()) {
            if (discover) error(result, "AF-TABLE82-PRESENCE", "Discover Table 82 must contain a nonblank Transaction ID.");
            else result.addWarning("AF-TABLE82-RECOMMENDATION: Table 82 is present but its Transaction ID is unavailable.");
        }
    }

    private static void checkTransactionIdUniqueness(JsonNode flow, JsonNode original, String terminalId,
            String network, ValidationResult result) {
        JsonNode transactionId = original.path("incrementalAuthData").path("transactionId");
        if (!transactionId.isTextual() || transactionId.asText().isBlank()) return;
        JsonNode open = flow.path("openIncrementalAuthTransactions");
        if (terminalId.isBlank() || !open.isArray() || !flow.path("openIncrementalAuthorizationHistoryComplete").asBoolean(false)) {
            result.addWarning("AF-TRANSACTION-ID-UNIQUENESS: Complete open-purchase history and terminal identity are required to assess uniqueness.");
            return;
        }
        for (JsonNode entry : open) {
            if (terminalId.equals(entry.path("terminalId").asText())
                    && transactionId.asText().equals(entry.path("transactionId").asText())) {
                error(result, "AF-TRANSACTION-ID-UNIQUENESS", "Transaction ID must be unique per terminal among open Incremental Auth purchases.");
                return;
            }
        }
    }

    private static void checkApprovalCode(JsonNode original, JsonNode subsequent, ValidationResult result) {
        if (original.path("approved").isBoolean() && !original.path("approved").asBoolean()) {
            error(result, "AF-APPROVAL-CODE", "A subsequent transaction must be preceded by an approved Original Auth.");
            return;
        }
        JsonNode originalCode = original.path("approvalCodeDE39");
        if (!original.path("approved").isBoolean() || !originalCode.isTextual() || originalCode.asText().isBlank()) {
            result.addWarning("AF-APPROVAL-CODE: Approved Original Auth DE 39 evidence is unavailable for comparison.");
        }
        JsonNode subsequentCode = subsequent.path("approvalCodeDE39");
        if (!subsequentCode.isTextual() || subsequentCode.asText().isBlank()) {
            error(result, "AF-APPROVAL-CODE", "Subsequent transactions must contain the Approval Code received on the approved Original Auth.");
        } else if (originalCode.isTextual() && !originalCode.asText().isBlank()
                && !subsequentCode.asText().equals(originalCode.asText())) {
            error(result, "AF-APPROVAL-CODE", "Subsequent Approval Code must match the approved Original Auth DE 39 value.");
        }
    }

    private static void error(ValidationResult result, String rule, String reason) {
        result.addError(SOURCE, rule + ": " + reason);
    }
}