package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.Chapter13ProcessingHistoryValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Actual named fields only: metadata never supplies missing payload values. */
public final class Segment119ActualPayloadAdapter {
    private static final Map<String, String> FIELDS = fields();
    private final Chapter13ProcessingHistoryValidator historyValidator;

    public Segment119ActualPayloadAdapter(JsonNode inventory) throws IOException {
        historyValidator = new Chapter13ProcessingHistoryValidator(inventory);
    }

    public Chapter12SegmentPayloadValidator.Result validate(JsonNode segment, String family, JsonNode evidence) {
        ObjectNode observation = JsonNodeFactory.instance.objectNode().put("specificationVersion", "2026-3")
                .put("segment", "119").put("messageFamily", family).put("direction", "REQUEST");
        ObjectNode elements = observation.putObject("elements");
        if (segment == null || !segment.isObject()) {
            return new Chapter12SegmentPayloadValidator.Result(Chapter12SegmentPayloadValidator.Status.INVALID,
                    List.of(new Chapter12SegmentPayloadValidator.Finding("SEG119-R-008", "actual-input",
                            Chapter12SegmentPayloadValidator.Status.INVALID, "Actual Segment 119 must be an object")), List.of());
        }
        FIELDS.forEach((name, id) -> { if (segment.has(name)) elements.set(id, segment.path(name).deepCopy()); });
        var buckets = observation.putArray("cardBuckets");
        JsonNode actualBuckets = segment.get("CategoryTotals");
        if (actualBuckets != null && !actualBuckets.isArray()) observation.set("cardBuckets", actualBuckets.deepCopy());
        else if (actualBuckets != null) {
            for (JsonNode bucket : actualBuckets) {
                if (!bucket.isObject()) { buckets.add(bucket.deepCopy()); continue; }
                mapBucket(bucket, buckets.addObject());
            }
        } else if (segment.has("CardLabel") || segment.has("CardTypeTotalCount") || segment.has("CardTypeTotalAmount")) {
            mapBucket(segment, buckets.addObject());
        }
        if (segment.has("SerializedSegment")) observation.set("serializedSegment", segment.path("SerializedSegment").deepCopy());
        if (evidence != null) {
            for (String field : List.of("context", "history")) {
                if (evidence.has(field)) observation.set(field, evidence.path(field).deepCopy());
            }
        }
        var findings = new java.util.ArrayList<>(Segment119ObservationValidator.validate(observation));
        if (actualBuckets != null && (segment.has("CardLabel") || segment.has("CardTypeTotalCount") || segment.has("CardTypeTotalAmount"))) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG119-R-027", "actual-mapping",
                    Chapter12SegmentPayloadValidator.Status.INVALID, "Both flat and array bucket representations supplied; occurrence identity is ambiguous"));
        }
        observation.putObject("qualifiers").put("direction", "REQUEST");
        if (evidence != null && !evidence.isObject()) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG119-R-032", "history-controls",
                    Chapter12SegmentPayloadValidator.Status.INVALID, "Observed evidence must be an object"));
        }
        var actualNames = segment.fieldNames();
        while (actualNames.hasNext()) {
            String name = actualNames.next();
            if (!FIELDS.containsKey(name) && !List.of("CategoryTotals", "SerializedSegment", "CardLabel", "CardTypeTotalCount", "CardTypeTotalAmount").contains(name)) {
                findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG119-R-036", "actual-mapping",
                        Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED, "Unmapped actual Segment 119 field: " + name));
            }
        }
        if (evidence != null && evidence.has("qualifiers") && !evidence.path("qualifiers").isObject()) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG119-R-032", "history-controls",
                    Chapter12SegmentPayloadValidator.Status.INVALID, "History qualifiers must be an object"));
        }
        if (evidence != null && evidence.path("qualifiers").has("direction")
                && !"REQUEST".equals(evidence.path("qualifiers").path("direction").asText())) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding("SEG119-R-032", "history-controls",
                    Chapter12SegmentPayloadValidator.Status.INVALID, "Evidence direction contradicts actual request family"));
        }
        for (var finding : historyValidator.validate(observation)) {
            findings.add(new Chapter12SegmentPayloadValidator.Finding(finding.ruleId(), "observed-history",
                    switch (finding.status()) {
                        case INVALID -> Chapter12SegmentPayloadValidator.Status.INVALID;
                        case CHECKS_PASSED -> Chapter12SegmentPayloadValidator.Status.CHECKS_PASSED;
                        case REVIEW_REQUIRED -> Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
                    }, finding.reason()));
        }
        var status = findings.stream().anyMatch(f -> f.status() == Chapter12SegmentPayloadValidator.Status.INVALID)
                ? Chapter12SegmentPayloadValidator.Status.INVALID : Chapter12SegmentPayloadValidator.Status.REVIEW_REQUIRED;
        return new Chapter12SegmentPayloadValidator.Result(status, List.copyOf(findings),
                List.of("SEG119-R-031", "SEG119-R-034", "SEG119-R-035", "SEG119-R-036"));
    }

    private static void mapBucket(JsonNode bucket, ObjectNode numbered) {
        for (var entry : Map.of("CardLabel", "13", "CardTypeTotalCount", "16", "CardTypeTotalAmount", "15").entrySet()) {
            if (!bucket.has(entry.getKey())) continue;
            JsonNode value = bucket.path(entry.getKey());
            if ("13".equals(entry.getValue()) && value.isTextual() && value.textValue().matches("[A-Z0-9]{2,4}")) {
                numbered.put("13", value.textValue() + " ".repeat(4 - value.textValue().length()));
            } else numbered.set(entry.getValue(), value.deepCopy());
        }
    }

    private static Map<String, String> fields() {
        Map<String, String> fields = new LinkedHashMap<>();
        String[] names = {"SegmentType", "SegmentLength", "InformationByte", "TerminalID", "PromptCode", "EmployeeNumber",
                "Password", "TotalsDate", "HardwareVersion", "SoftwareVersion", "FirmwareVersion", "SequenceNumber",
                "DeviceCardTableVersion", "HostDiscountTimestamp", "CurrencyCode", "GrandTotal"};
        String[] ids = {"85", "84", "44", "102", "78", "32", "65", "105", "43", "96", "39", "86", "176", "179", "20", "42"};
        for (int i = 0; i < names.length; i++) fields.put(names[i], ids[i]);
        return Map.copyOf(fields);
    }
}
