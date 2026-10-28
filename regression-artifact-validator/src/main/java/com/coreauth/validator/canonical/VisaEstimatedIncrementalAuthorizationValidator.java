package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

public final class VisaEstimatedIncrementalAuthorizationValidator {
    private static final String SOURCE = "AppendixAE";

    public ValidationResult validatePayload(JsonNode flow) {
        ValidationResult result = new ValidationResult("visa-estimated-incremental-authorization");
        result.addWarning("Appendix AE logical observations only: merchant permission, host-produced incremental indicator, Transaction Identifier placement and wire/host outcomes are not certified.");
        if (flow == null || !flow.isObject() || !"2026-3".equals(flow.path("specificationVersion").asText())
                || !"Visa".equals(flow.path("network").asText())) {
            result.addWarning("AE-CONTEXT: Provide ATL105 2026-3 Visa context; unsupported networks are not assessed.");
            return result;
        }
        if (!flow.path("merchantPermissionEvidence").isTextual() || flow.path("merchantPermissionEvidence").asText().isBlank())
            result.addWarning("AE-PERMISSION: Permitted-merchant evidence is missing; MCC restrictions are not a complete eligibility list.");
        String mcc = flow.path("mcc").asText();
        if (!flow.path("mcc").isTextual() || !mcc.matches("[0-9]{4}"))
            result.addWarning("AE-MCC: Four-digit MCC context is required.");
        if (Set.of("4121", "5411").contains(mcc)) {
            if (!flow.path("cardPresent").isBoolean()) result.addWarning("AE-CNP: Card-present context is unknown.");
            else if (flow.path("cardPresent").asBoolean()) error(result, "AE-CNP", "MCC 4121/5411 estimated and incremental authorizations are card-not-present only.");
        }
        if ("5552".equals(mcc)) {
            if (!flow.path("evTransaction").isBoolean()) result.addWarning("AE-EV: EV context is unknown.");
            else if (!flow.path("evTransaction").asBoolean()) error(result, "AE-EV", "MCC 5552 estimated and incremental authorizations require EV context.");
        }
        JsonNode steps = flow.path("steps");
        if (!steps.isArray() || steps.isEmpty()) {
            result.addWarning("AE-FLOW: Ordered lifecycle steps are required.");
            return result;
        }
        int window = expirationDays(flow.path("merchantCategory").asText());
        if (window == 0) result.addWarning("AE-WINDOW: Appendix AE does not establish a 7/14-day window for the supplied merchant category.");
        JsonNode initial = null;
        LocalDate initialDate = null;
        LocalDate previousDate = null;
        boolean completed = false;
        boolean rolloverSeen = false;
        for (JsonNode step : steps) {
            LocalDate date = date(step, result);
            if (date != null && previousDate != null && date.isBefore(previousDate))
                error(result, "AE-ORDER", "Lifecycle steps must be chronological.");
            if (date != null) previousDate = date;
            String role = step.path("role").asText();
            switch (role) {
                case "INITIAL" -> {
                    if (initial != null) error(result, "AE-INITIAL", "Use SECOND_INITIAL for the documented rollover; duplicate initial roles are ambiguous.");
                    else {
                        initial = step;
                        initialDate = date;
                        if (!step.path("finalAmountKnown").isBoolean()) result.addWarning("AE-ESTIMATE: Final-amount knowledge is missing.");
                        else if (!step.path("finalAmountKnown").asBoolean())
                            requireValue(step, "estimatedAuthIndicator", "1", "AE-ESTIMATE", result);
                    }
                }
                case "INCREMENTAL" -> {
                    if (initial == null || completed) error(result, "AE-PREDECESSOR", "Incremental authorization must follow an active estimated/initial authorization.");
                    else {
                        compare(initial, step, "sequenceNumber", "AE-SEQUENCE", result);
                        compare(initial, step, "transactionIdentifier", "AE-TRANSACTION-ID", result);
                        compare(initial, step, "terminalTransactionNumberTable63", "AE-TABLE63", result);
                        if (!step.path("authorizationIdentificationResponse").isTextual()
                                || step.path("authorizationIdentificationResponse").asText().isBlank())
                            error(result, "AE-APPROVAL", "Incremental identification requires the returned approval code in Authorization Identification Response.");
                        if (step.has("estimatedAuthIndicator"))
                            error(result, "AE-NO-REESTIMATE", "Subsequent transactions must not carry the estimated authorization indicator in Sub Table 09, even as zero or null.");
                        checkWindow(initialDate, date, window, result);
                    }
                }
                case "COMPLETION" -> {
                    if (initial == null || completed) error(result, "AE-COMPLETION", "Completion requires an active initial transaction.");
                    else {
                        if (step.has("estimatedAuthIndicator"))
                            error(result, "AE-NO-REESTIMATE", "Completion is a subsequent transaction and must not carry the estimated authorization indicator.");
                        checkFinalAmount(flow, step, result);
                        checkWindow(initialDate, date, window, result);
                        completed = true;
                    }
                }
                case "SECOND_INITIAL" -> {
                    if (!completed || rolloverSeen) error(result, "AE-ROLLOVER", "Complete the first transaction before initiating one second transaction.");
                    checkWindow(initialDate, date, window, result);
                    rolloverSeen = true;
                }
                default -> result.addWarning("AE-ROLE: Unsupported lifecycle role; not assessed: " + role);
            }
        }
        if (initial == null) result.addWarning("AE-INITIAL: No initial authorization observation was supplied.");
        if (!flow.path("stayExceedsWindow").isBoolean()) result.addWarning("AE-ROLLOVER: Whether the stay/rental exceeds the approval window is unknown.");
        else if (flow.path("stayExceedsWindow").asBoolean() && (!completed || !rolloverSeen))
            error(result, "AE-ROLLOVER", "Longer stays/rentals require completion of the first transaction and initiation of the second on or before the original deadline.");
        return result;
    }

    public static int expirationDays(String category) {
        return switch (category) {
            case "CRUISE", "LODGING", "CAR_VEHICLE_RENTAL" -> 14;
            case "OTHER_RENTAL" -> 7;
            default -> 0;
        };
    }

    private static void checkWindow(LocalDate initial, LocalDate step, int days, ValidationResult result) {
        if (initial != null && step != null && days != 0 && step.isAfter(initial.plusDays(days)))
            error(result, "AE-WINDOW", "Approval window is anchored to the original initial authorization; incremental requests do not restart it.");
    }

    private static LocalDate date(JsonNode step, ValidationResult result) {
        try {
            if (step.path("date").isTextual()) return LocalDate.parse(step.path("date").asText());
        } catch (DateTimeParseException exception) {
            result.addWarning("AE-DATE: Invalid ISO date; expiration cannot be assessed.");
            return null;
        }
        result.addWarning("AE-DATE: Missing ISO date; expiration cannot be assessed.");
        return null;
    }

    private static void compare(JsonNode initial, JsonNode step, String field, String rule, ValidationResult result) {
        if (!initial.path(field).isTextual() || initial.path(field).asText().isBlank()
                || !step.path(field).isTextual() || step.path(field).asText().isBlank())
            result.addWarning(rule + ": Missing textual continuity value; equality is not assessed.");
        else if (!initial.path(field).asText().equals(step.path(field).asText()))
            error(result, rule, field + " must match the original initial request.");
    }

    private static void requireValue(JsonNode step, String field, String value, String rule, ValidationResult result) {
        if (!step.path(field).isTextual() || !value.equals(step.path(field).asText()))
            error(result, rule, "Initial estimated authorization when final amount is unknown requires indicator 1 in Acquirer Trace Data Sub Table 09.");
    }

    private static void checkFinalAmount(JsonNode flow, JsonNode completion, ValidationResult result) {
        try {
            if (!flow.path("finalAmount").isTextual() || !completion.path("amount").isTextual()) {
                result.addWarning("AE-FINAL-AMOUNT: Textual final amount and completion amount are required for comparison.");
                return;
            }
            BigDecimal expected = new BigDecimal(flow.path("finalAmount").asText());
            BigDecimal actual = new BigDecimal(completion.path("amount").asText());
            if (expected.signum() < 0 || actual.signum() < 0 || expected.compareTo(actual) != 0)
                error(result, "AE-FINAL-AMOUNT", "Completion must send the final amount; estimated/incremental sum rules are not inferred.");
        } catch (NumberFormatException exception) {
            result.addWarning("AE-FINAL-AMOUNT: Amount representation is unsupported; no equality verdict.");
        }
    }

    private static void error(ValidationResult result, String rule, String reason) {
        result.addError(SOURCE, rule + ": " + reason);
    }
}