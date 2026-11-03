package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Set;

/** Validates source-backed eWIC Segment 100 and Segment 103 request combinations. */
public final class Segment100EwicValidator {
    private static final Map<String, Set<String>> PROMPT_CODES = Map.of(
            "AUTHORIZATION", Set.of("3086"),
            "CANCELLATION", Set.of("S086"),
            "BALANCE_INQUIRY", Set.of("E086"),
            "COMPLETION", Set.of("0086"),
            "VOUCHER_CLEAR", Set.of("0086"),
            "REVERSAL_VOID", Set.of("8086"));
    private static final Map<String, String> APPLICABILITY_ROLES = Map.of(
            "AUTHORIZATION", "EWIC_AUTHORIZATION",
            "CANCELLATION", "EWIC_AUTHORIZATION_CANCELLATION",
            "BALANCE_INQUIRY", "EWIC_BALANCE_INQUIRY",
            "COMPLETION", "EWIC_PURCHASE_COMPLETION",
            "VOUCHER_CLEAR", "EWIC_VOUCHER_CLEAR",
            "REVERSAL_VOID", "EWIC_PURCHASE_REVERSAL_VOID");

    private final Segment103ApplicabilityValidator applicabilityValidator = new Segment103ApplicabilityValidator();

    public ValidationResult validateRequest(String id, JsonNode payload, String operation) {
        ValidationResult result = new ValidationResult(id == null ? "ewic" : id);
        JsonNode request = payload == null ? null : payload.path("Financial Request");
        if (request == null || !request.isObject()) {
            result.addError("Segment100Ewic", "Financial Request is required");
            return result;
        }
        String applicabilityRole = APPLICABILITY_ROLES.get(operation);
        if (applicabilityRole == null) {
            result.addError("Segment100Ewic", "Unsupported eWIC operation " + operation);
            return result;
        }
        JsonNode standard = request.path("Standard Segment");
        JsonNode ebt = request.path("EBT Data Segment");
        String promptCode = standard.path("PromptCode").path("TransactionType").asText("")
                + standard.path("PromptCode").path("CardType").asText("");
        if (!PROMPT_CODES.getOrDefault(operation, Set.of()).contains(promptCode)) {
            result.addError("Segment100Ewic", operation + " requires eWIC Prompt Code, got " + promptCode);
        }
        if (!"100".equals(standard.path("SegmentType").asText(null))) {
            result.addError("Segment100Ewic", "Standard Segment type must be 100");
        }
        if (!"086".equals(standard.path("PromptCode").path("CardType").asText(null))) {
            result.addError("Segment100Ewic", "eWIC Card Type must be 086");
        }
        boolean ebtObjectPresent = ebt.isObject();
        boolean segment103Present = ebtObjectPresent && "103".equals(ebt.path("SegmentType").asText(null));
        if (ebtObjectPresent && !segment103Present) {
            result.addError("Segment100Ewic", "EBT Data Segment type must be 103");
        }
        boolean wicDataPresent = hasValue(ebt.path("WICDiscountAmount")) || hasValue(ebt.path("WICProductData"));
        result.merge(applicabilityValidator.validate(applicabilityRole, segment103Present, false, wicDataPresent));
        if (segment103Present) {
            String segmentLength = ebt.path("SegmentLength").asText(null);
            if (segmentLength == null || !segmentLength.matches("^[0-9]{3,4}$")
                    || Integer.parseInt(segmentLength) < 1 || Integer.parseInt(segmentLength) > 3334) {
                result.addError("Segment100Ewic", "Segment 103 length must be 001-3334");
            }
        }
        String sequence = standard.path("SequenceNumber").asText(null);
        if (sequence == null || !sequence.matches("^[0-9]{6}$")) {
            result.addError("Segment100Ewic", "eWIC SequenceNumber must be six digits");
        }
        String count = request.path("NumSegments").asText(null);
        int actualSegmentCount = countSegmentObjects(request);
        String paddedCount = String.format(java.util.Locale.ROOT, "%02d", actualSegmentCount);
        if (!paddedCount.equals(count) && !Integer.toString(actualSegmentCount).equals(count)) {
            result.addError("Segment100Ewic", "NumSegments must equal the BUYPASS segment-object count " + paddedCount);
        }
        return result;
    }

    public ValidationResult validateLifecycle(String id, JsonNode authorization, JsonNode completion) {
        ValidationResult result = new ValidationResult(id == null ? "ewic-lifecycle" : id);
        result.merge(validateRequest("ewic-authorization", authorization, "AUTHORIZATION"));
        result.merge(validateRequest("ewic-completion", completion, "COMPLETION"));
        if (!result.isValid()) return result;
        JsonNode original = authorization.path("Financial Request").path("Standard Segment");
        JsonNode followUp = completion.path("Financial Request").path("Standard Segment");
        if (!original.path("SequenceNumber").asText().equals(followUp.path("SequenceNumber").asText())) {
            result.addError("Segment100Ewic", "eWIC completion must reuse authorization SequenceNumber");
        }
        if (!original.path("TerminalID").asText().equals(followUp.path("TerminalID").asText())) {
            result.addError("Segment100Ewic", "eWIC completion must use the authorization TerminalID");
        }
        return result;
    }

    public static ObjectMapper objectMapper() { return new ObjectMapper(); }

    private static int countSegmentObjects(JsonNode request) {
        int count = 0;
        var fields = request.fields();
        while (fields.hasNext()) {
            JsonNode value = fields.next().getValue();
            if (value.isObject() && (value.has("SegmentType") || value.has("segmentType"))) count++;
        }
        return count;
    }

    private static boolean hasValue(JsonNode value) {
        if (value == null || value.isMissingNode() || value.isNull()) return false;
        if (value.isTextual()) return !value.asText().isBlank();
        if (value.isContainerNode()) return !value.isEmpty();
        return true;
    }
}
