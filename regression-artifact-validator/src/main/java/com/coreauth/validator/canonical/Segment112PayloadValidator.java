package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/** Validates Segment 112's response envelope and triad structure; table semantics remain separate. */
public final class Segment112PayloadValidator {
    private static final String SOURCE = "Segment112Payload";
    private static final Pattern THREE_DIGITS = Pattern.compile("^[0-9]{3}$");
    private static final Set<String> RESERVED_OR_REJECTED = Set.of("002", "014", "015", "033");
    private static final int MAX_VALUE_LENGTH = 984;
    private static final int MAX_SECTION_LENGTH = 990;
    private static final int MAX_SEGMENT_LENGTH = 999;
    private static final int FINANCIAL_RESPONSE_FIELD_MAX_LENGTH = 349;

    private final ObjectMapper mapper;
    private final AppendixKTableCatalog tableCatalog;
    private final AppendixKDataLayoutValidator dataLayoutValidator;

    public Segment112PayloadValidator() {
        this(new ObjectMapper(), new AppendixKTableCatalog(), new AppendixKDataLayoutValidator());
    }

    Segment112PayloadValidator(ObjectMapper mapper, AppendixKTableCatalog tableCatalog,
                               AppendixKDataLayoutValidator dataLayoutValidator) {
        this.mapper = mapper;
        this.tableCatalog = tableCatalog;
        this.dataLayoutValidator = dataLayoutValidator;
    }

    public ValidationResult validateFile(Path file) {
        ValidationResult result = new ValidationResult(file == null ? "segment-112-payload" : file.getFileName().toString());
        if (file == null) {
            result.addError(SOURCE, "Financial Transaction Response JSON file is required");
            return result;
        }
        try {
            validatePayload(mapper.readTree(file.toFile()), result);
        } catch (IOException exception) {
            result.addError(SOURCE, "Response JSON is not readable: " + exception.getMessage());
        }
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("segment-112-payload");
        validatePayload(payload, result);
        return result;
    }

    private void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        JsonNode response = response(payload);
        if (!response.isObject()) {
            result.addError(SOURCE, "Financial Transaction Response object is required (SEG112-R-004, SEG112-R-010)");
            return;
        }

        String flag = element115(response);
        JsonNode segment = findSegment112(response);
        if (flag == null || !Set.of("0", "1").contains(flag)) {
            result.addError(SOURCE, "Element 115 must be the textual one-digit value 0 or 1 (SEG112-R-010)");
        } else if ("0".equals(flag) && segment != null && segment.isObject()) {
            result.addError(SOURCE, "Element 115 = 0 forbids Segment 112 in the response (SEG112-R-010)");
        } else if ("1".equals(flag) && (segment == null || !segment.isObject())) {
            result.addError(SOURCE, "Element 115 = 1 requires Segment 112 in the response (SEG112-R-010)");
            return;
        }

        if (segment != null && segment.isObject()) {
            validateSegment(segment, text(response, "paymentNetwork", "PaymentNetwork", "network", "Network"), result);
        }
    }

    private void validateSegment(JsonNode segment, String network, ValidationResult result) {
        String type = text(segment, "segmentType", "SegmentType", "Segment Type");
        if (!"112".equals(type)) {
            result.addError(SOURCE, "Additional Information Data Segment type must be 112 (SEG112-R-001)");
        }

        String declaredLength = text(segment, "segmentLength", "SegmentLength", "Segment Length");
        if (!matches(declaredLength, THREE_DIGITS)) {
            result.addError(SOURCE, "Segment 112 SegmentLength must be exactly three digits (SEG112-R-002)");
        }

        List<JsonNode> repetitions = repetitions(segment);
        if (repetitions.isEmpty()) {
            result.addError(SOURCE, "Segment 112 requires at least one Additional Information triad (SEG112-R-007)");
            return;
        }

        List<IndicatorLengthValueSectionValidator.Triad> triads = new ArrayList<>();
        List<String> tableIds = new ArrayList<>();
        for (JsonNode repetition : repetitions) {
            String indicator = text(repetition, "additionalInformationIndicator", "AdditionalInformationIndicator", "indicator");
            String length = text(repetition, "additionalInformationLength", "AdditionalInformationLength", "length");
            String value = text(repetition, "additionalInformation", "AdditionalInformation", "value");
            triads.add(new IndicatorLengthValueSectionValidator.Triad(indicator, length, value));
            tableIds.add(indicator);
            if (matches(length, THREE_DIGITS) && (Integer.parseInt(length) < 1 || Integer.parseInt(length) > 985)) {
                result.addError(SOURCE, "Element 117 must be in the source-defined range 001-985 (SEG112-R-008)");
            }
        }

        var section = IndicatorLengthValueSectionValidator.validate(
                triads, 1, MAX_VALUE_LENGTH, MAX_SECTION_LENGTH);
        for (IndicatorLengthValueSectionValidator.Violation violation : section.violations()) {
            int index = violation.repetitionIndex();
            switch (violation.problem()) {
                case INVALID_INDICATOR -> result.addError(SOURCE,
                        "Element 116 repetition " + index + " must be a three-digit selector (SEG112-R-007)");
                case INVALID_LENGTH -> result.addError(SOURCE,
                        "Element 117 repetition " + index + " must be three digits (SEG112-R-008)");
                case MISSING_VALUE -> result.addError(SOURCE,
                        "Element 118 repetition " + index + " is required (SEG112-R-009)");
                case VALUE_TOO_SHORT -> result.addError(SOURCE,
                        "Element 118 repetition " + index + " must contain at least one character (SEG112-R-009)");
                case VALUE_TOO_LONG -> result.addError(SOURCE,
                        "Element 118 repetition " + index + " exceeds 984 characters (SEG112-R-009)");
                case LENGTH_MISMATCH -> result.addError(SOURCE,
                        "Element 117 repetition " + index + " must equal the Element 118 character length (SEG112-R-008)");
                case SECTION_TOO_LONG -> result.addError(SOURCE,
                        "Additional Information Section exceeds 990 characters (SEG112-R-006)");
            }
        }

        for (int index = 0; index < tableIds.size(); index++) {
            String tableId = tableIds.get(index);
            classifyTable(tableId, result);
            if (tableCatalog.classify(tableId).disposition() == AppendixKTableCatalog.Disposition.ASSIGNED) {
                assessTableData(tableId, triads.get(index).value(), network, result);
            }
        }
        result.addWarning("AppendixKLayoutReview: table IDs are classified, but assigned Element 118 sub-layout semantics are not validated by this envelope validator.");

        int computedLength = 9 + section.sectionLength();
        if (computedLength > MAX_SEGMENT_LENGTH) {
            result.addError(SOURCE, "Segment 112 total length exceeds 999 characters (SEG112-R-003)");
        }
        if (computedLength > FINANCIAL_RESPONSE_FIELD_MAX_LENGTH && computedLength <= MAX_SEGMENT_LENGTH) {
            result.addWarning("SOURCE_CONFLICT_REVIEW_REQUIRED SEG112-SME-008: Section 11.1.2 lists a 349-character Segment 112 field maximum, while Section 12.11 gives a 999-character segment maximum; response-context certification is blocked until precedence is confirmed.");
        }
        if (matches(declaredLength, THREE_DIGITS) && Integer.parseInt(declaredLength) != computedLength) {
            result.addError(SOURCE, "Segment 112 SegmentLength must equal computed field/separator length " + computedLength + " (SEG112-R-002)");
        }
    }

    private void classifyTable(String tableId, ValidationResult result) {
        var classification = tableCatalog.classify(tableId);
        if (classification.disposition() == AppendixKTableCatalog.Disposition.INVALID_FORMAT) return;
        if (RESERVED_OR_REJECTED.contains(tableId)) {
            result.addError(SOURCE, "Element 116 selector " + tableId + " is reserved/invalid under SEG112-SME-002 (SEG112-R-007)");
        } else if (classification.disposition() == AppendixKTableCatalog.Disposition.UNLISTED) {
            result.addWarning("REVIEW_REQUIRED: Appendix K selector " + tableId + " is not listed; future-use status is not inferred.");
        }
    }

    private void assessTableData(String tableId, String data, String network, ValidationResult result) {
        AppendixKDataLayoutValidator.Assessment assessment = dataLayoutValidator.validate(tableId, data, network);
        switch (assessment.status()) {
            case PASS -> {
                if (!"NETWORK_CODE".equals(assessment.assertionScope())) {
                    result.addWarning("AppendixKLayoutScope " + tableId + " PASS(" + assessment.assertionScope()
                            + "): " + assessment.reason());
                }
            }
            case FAIL -> result.addError(SOURCE, "Appendix K " + tableId + ": " + assessment.reason());
            case REVIEW_REQUIRED -> result.addWarning("AppendixKLayoutReview " + tableId + ": " + assessment.reason());
            case NOT_ASSERTABLE -> result.addWarning("AppendixKLayoutNotAssertable " + tableId + ": " + assessment.reason());
        }
    }

    private static JsonNode response(JsonNode payload) {
        JsonNode response = firstObject(payload, "response", "Financial Transaction Response");
        if (response != null && response.has("Financial Transaction Response")) {
            return response.path("Financial Transaction Response");
        }
        if (response != null) return response;
        return payload;
    }

    private static String element115(JsonNode response) {
        String direct = text(response, "additionalInformationDataSegmentFlag", "AdditionalInformationDataSegmentFlag",
                "Additional Information Data Segment Flag", "element115", "Element115");
        if (direct != null) return direct;
        return findElementValue(response, "115");
    }

    private static String findElementValue(JsonNode node, String elementNo) {
        if (node == null) return null;
        if (node.isObject()) {
            if (elementNo.equals(text(node, "elementNo", "elementNumber", "ElementNo", "Element Number"))) {
                String value = text(node, "value", "data", "text");
                if (value != null) return value;
            }
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                String found = findElementValue(fields.next().getValue(), elementNo);
                if (found != null) return found;
            }
        } else if (node.isArray()) {
            for (JsonNode child : node) {
                String found = findElementValue(child, elementNo);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static JsonNode findSegment112(JsonNode node) {
        if (node == null) return null;
        JsonNode direct = firstObject(node, "additionalInformationDataSegment", "additionalInformationSegment",
                "Additional Information Data Segment", "AdditionalInformationDataSegment");
        if (direct != null && direct.isObject()) return direct;
        if (node.isObject()) {
            if ("112".equals(text(node, "segmentType", "SegmentType", "Segment Type"))) return node;
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                JsonNode found = findSegment112(fields.next().getValue());
                if (found != null) return found;
            }
        } else if (node.isArray()) {
            for (JsonNode child : node) {
                JsonNode found = findSegment112(child);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static List<JsonNode> repetitions(JsonNode segment) {
        for (String key : new String[]{"additionalInformationSections", "additionalInformationSection",
                "Additional Information Section", "repetitions", "sections"}) {
            JsonNode value = segment.path(key);
            if (value.isArray()) {
                List<JsonNode> records = new ArrayList<>();
                value.forEach(records::add);
                return records;
            }
            if (value.isObject()) return List.of(value);
        }
        if (first(segment, "additionalInformationIndicator", "AdditionalInformationIndicator", "indicator") != null) {
            return List.of(segment);
        }
        return List.of();
    }

    private static JsonNode firstObject(JsonNode node, String... keys) {
        for (String key : keys) {
            JsonNode value = node.path(key);
            if (value.isObject()) return value;
        }
        return null;
    }

    private static JsonNode first(JsonNode node, String... keys) {
        for (String key : keys) if (node.has(key)) return node.path(key);
        return null;
    }

    private static String text(JsonNode node, String... keys) {
        JsonNode value = first(node, keys);
        return value != null && value.isTextual() ? value.asText() : null;
    }

    private static boolean matches(String value, Pattern pattern) {
        return value != null && pattern.matcher(value).matches();
    }
}