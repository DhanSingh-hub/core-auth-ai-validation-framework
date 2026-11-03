package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;

/** Coordinates request-side Segment 111 and response-side Segment 112 without merging their meanings. */
public final class AdditionalInformationTransactionFlowValidator {
    private final ObjectMapper mapper;
    private final Segment111PayloadValidator segment111Validator;
    private final Segment112PayloadValidator segment112Validator;
    private final AppendixILogicalDataValidator appendixILogicalValidator;
    private final AppendixITableCatalog appendixITableCatalog;

    public AdditionalInformationTransactionFlowValidator() {
        this(new ObjectMapper(), new Segment111PayloadValidator(), new Segment112PayloadValidator(),
            new AppendixILogicalDataValidator(), new AppendixITableCatalog());
    }

    AdditionalInformationTransactionFlowValidator(ObjectMapper mapper,
                                                   Segment111PayloadValidator segment111Validator,
                                                   Segment112PayloadValidator segment112Validator,
                                                   AppendixILogicalDataValidator appendixILogicalValidator,
                                                   AppendixITableCatalog appendixITableCatalog) {
        this.mapper = mapper;
        this.segment111Validator = segment111Validator;
        this.segment112Validator = segment112Validator;
        this.appendixILogicalValidator = appendixILogicalValidator;
        this.appendixITableCatalog = appendixITableCatalog;
    }

    public ValidationResult validatePayload(JsonNode transaction) {
        ValidationResult result = new ValidationResult("additional-information-transaction-flow");
        if (transaction == null || !transaction.isObject()) {
            result.addError("AdditionalInformationFlow", "Transaction request/response pair must be an object");
            return result;
        }

        JsonNode request = firstObject(transaction, "request", "Financial Request", "Financial Transaction Request");
        JsonNode response = firstObject(transaction, "response", "Financial Transaction Response");
        if (response != null && response.has("Financial Transaction Response")) {
            response = response.path("Financial Transaction Response");
        }
        result.merge(request == null
                ? missing("Financial Transaction Request object is required")
                : validateRequestPayload(request));
        result.merge(response == null
                ? missing("Financial Transaction Response object is required")
                : validateResponsePayload(response));
        return result;
    }

    public ValidationResult validateRequestPayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("additional-information-request-flow");
        if (payload == null || !payload.isObject()) {
            result.addError("AdditionalInformationFlow", "Financial Transaction Request object is required");
            return result;
        }
        JsonNode request = firstObject(payload, "request", "Financial Request", "Financial Transaction Request");
        if (request == null && (findSegment(payload, "111") != null || findSegment(payload, "112") != null)) {
            request = payload;
        }
        if (request == null) {
            result.addError("AdditionalInformationFlow", "Financial Transaction Request object is required");
            return result;
        }
        if (findSegment(request, "112") != null) {
            result.addError("AdditionalInformationFlow", "Segment 112 is response-only and must not appear in the request");
        }
        if (findSegment(request, "111") != null) {
            ObjectNode wrappedRequest = mapper.createObjectNode();
            wrappedRequest.set("request", request.deepCopy());
            result.merge(segment111Validator.validatePayload(wrappedRequest));
            validateAppendixITables(request, result);
        }
        return result;
    }

    public ValidationResult validateResponsePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("additional-information-response-flow");
        if (payload == null || !payload.isObject()) {
            result.addError("AdditionalInformationFlow", "Financial Transaction Response object is required");
            return result;
        }
        JsonNode response = firstObject(payload, "response", "Financial Transaction Response");
        if (response != null && response.has("Financial Transaction Response")) {
            response = response.path("Financial Transaction Response");
        }
        if (response == null && (findSegment(payload, "111") != null || findSegment(payload, "112") != null
                || findElement115(payload) != null)) response = payload;
        if (response == null) {
            result.addError("AdditionalInformationFlow", "Financial Transaction Response object is required");
            return result;
        }
        if (findSegment(response, "111") != null) {
            result.addError("AdditionalInformationFlow", "Segment 111 is request-side and must not appear in the response");
        }
        ObjectNode wrappedResponse = mapper.createObjectNode();
        wrappedResponse.set("Financial Transaction Response", response.deepCopy());
        result.merge(segment112Validator.validatePayload(wrappedResponse));
        return result;
    }

    private static ValidationResult missing(String reason) {
        ValidationResult result = new ValidationResult("additional-information-transaction-flow");
        result.addError("AdditionalInformationFlow", reason);
        return result;
    }

    private static String findElement115(JsonNode node) {
        return text(node, "additionalInformationDataSegmentFlag", "AdditionalInformationDataSegmentFlag",
                "Additional Information Data Segment Flag", "element115", "Element115");
    }

    private void validateAppendixITables(JsonNode request, ValidationResult result) {
        JsonNode segment = findSegment(request, "111");
        if (segment == null) return;
        String network = text(request, "paymentNetwork", "PaymentNetwork", "network", "Network");
        for (JsonNode repetition : variableInformationRepetitions(segment)) {
            String tableId = text(repetition, "variableInformationIndicator", "VariableInformationIndicator", "indicator");
            String data = text(repetition, "variableInformation", "VariableInformation", "value");
            AppendixITableCatalog.Status selectorStatus = appendixITableCatalog.classify(tableId);
            if (selectorStatus == AppendixITableCatalog.Status.UNLISTED) {
                result.addError("AppendixISelector", "Table ID " + tableId + " is not listed in Appendix I.");
                continue;
            }
            if (selectorStatus == AppendixITableCatalog.Status.SOURCE_UNLOCATED) {
                result.addWarning("AppendixISelector " + tableId
                        + " REVIEW_REQUIRED: allocated-range ID is not source-located; validity is not inferred.");
                continue;
            }
            if (selectorStatus == AppendixITableCatalog.Status.INVALID_FORMAT) continue;
            var assessment = appendixILogicalValidator.validate(tableId, data, network);
            for (var finding : assessment.findings()) {
                switch (finding.status()) {
                    case PASS -> { }
                    case FAIL -> result.addError("AppendixILogicalData", finding.ruleId() + ": " + finding.reason());
                    case REVIEW_REQUIRED -> result.addWarning("AppendixILogicalData " + finding.ruleId()
                            + " REVIEW_REQUIRED: " + finding.reason());
                    case NOT_ASSERTABLE -> result.addWarning("AppendixILogicalData " + finding.ruleId()
                            + " NOT_ASSERTABLE: " + finding.reason());
                }
            }
        }
    }

    private static java.util.List<JsonNode> variableInformationRepetitions(JsonNode segment) {
        for (String key : new String[]{"variableInformationSections", "variableInformationSection",
                "VariableInformationSections", "repetitions", "sections"}) {
            JsonNode value = segment.path(key);
            if (value.isArray()) {
                java.util.List<JsonNode> records = new java.util.ArrayList<>();
                value.forEach(records::add);
                return records;
            }
            if (value.isObject()) return java.util.List.of(value);
        }
        if (text(segment, "variableInformationIndicator", "VariableInformationIndicator", "indicator") != null) {
            return java.util.List.of(segment);
        }
        return java.util.List.of();
    }

    private static JsonNode firstObject(JsonNode node, String... keys) {
        for (String key : keys) {
            JsonNode child = node.path(key);
            if (child.isObject()) return child;
        }
        return null;
    }

    private static JsonNode findSegment(JsonNode node, String segmentType) {
        if (node == null) return null;
        if (node.isObject()) {
            String type = text(node, "segmentType", "SegmentType", "Segment Type");
            if (segmentType.equals(type)) return node;
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                JsonNode found = findSegment(fields.next().getValue(), segmentType);
                if (found != null) return found;
            }
        } else if (node.isArray()) {
            for (JsonNode child : node) {
                JsonNode found = findSegment(child, segmentType);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static String text(JsonNode node, String... keys) {
        for (String key : keys) {
            JsonNode value = node.path(key);
            if (value.isTextual()) return value.asText();
        }
        return null;
    }
}