package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Result;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Explicit actual-field adapter: metadata supplies observed context/history, never missing segment values or wire. */
public final class Chapter12ActualPayloadAdapter {
    public static final Set<String> SEGMENTS = Set.of("115", "130", "131", "145", "146", "148");
    private static final Map<String, String> COMMON = Map.of("SegmentType", "85", "SegmentLength", "84");
    private static final Map<String, String> EMV = Map.of("CAPublicKeyFileChecksum", "187", "EMVCardSequenceNumber", "188",
            "EMVChipDataLength", "189", "EMVChipData", "190");

    public Result validate(JsonNode actual, String id, String family, JsonNode evidence) throws IOException {
        if (!SEGMENTS.contains(id)) throw new IllegalArgumentException("Unsupported actual Chapter12 segment " + id);
        List<Finding> mapping = new ArrayList<>();
        if (actual == null || !actual.isObject() || family == null || family.isBlank()) return new Result(Status.INVALID,
                List.of(new Finding("CH12-ACTUAL-INPUT", "actual-input", Status.INVALID, "Actual segment must be an object with an observed message family")), List.of());
        ObjectNode observation = JsonNodeFactory.instance.objectNode().put("specificationVersion", "2026-3")
                .put("segment", id).put("messageFamily", family).put("direction", family.endsWith("Response") ? "RESPONSE" : "REQUEST");
        ObjectNode fields = observation.putObject("elements");
        COMMON.forEach((name, number) -> copy(actual, name, fields, number));
        if (Set.of("130", "131").contains(id)) {
            EMV.forEach((name, number) -> copy(actual, name, fields, number));
            if (actual.has("EMVAdditionalInformation") && (actual.has("EMVAdditionalInformationIndicator")
                    || actual.has("EMVAdditionalInformationLength") || actual.has("EMVAdditionalInformationValue"))) {
                mapping.add(new Finding("CH12-ACTUAL-AMBIGUITY", "actual-mapping", Status.INVALID,
                        "Both additional-information array and scalar triple are ambiguous"));
            }
            if (actual.has("EMVAdditionalInformation")) observation.set("tables", actual.path("EMVAdditionalInformation").deepCopy());
            else if (actual.has("EMVAdditionalInformationIndicator") || actual.has("EMVAdditionalInformationLength")
                    || actual.has("EMVAdditionalInformationValue")) {
                ObjectNode table = observation.putArray("tables").addObject();
                copy(actual, "EMVAdditionalInformationIndicator", table, "id");
                copy(actual, "EMVAdditionalInformationLength", table, "length");
                copy(actual, "EMVAdditionalInformationValue", table, "value");
            } else observation.putArray("tables");
            if (actual.has("SerializedSegmentHex")) observation.set("serializedSegmentHex", actual.path("SerializedSegmentHex").deepCopy());
            if (actual.has("SerializedSegment")) mapping.add(new Finding("CH12-ACTUAL-WIRE", "actual-mapping",
                    actual.path("SerializedSegment").isTextual() ? Status.REVIEW_REQUIRED : Status.INVALID,
                    "Text serialization cannot certify packed chip bytes; supply actual SerializedSegmentHex"));
        } else if (Set.of("145", "146").contains(id)) {
            JsonNode encoded = actual.get("EnhancedFleetData");
            if (actual.has("Tables") && encoded != null) mapping.add(new Finding("CH12-ACTUAL-AMBIGUITY", "actual-mapping",
                    Status.INVALID, "Both table array and encoded Enhanced Fleet Data are ambiguous"));
            if (actual.has("Tables")) observation.set("tables", actual.path("Tables").deepCopy());
            else decodeTables(encoded, observation, mapping);
        } else if ("148".equals(id)) copy(actual, "AvailableProductInformation", fields, "240");
        else copy(actual, "PrintData", fields, "152");
        if (actual.has("SerializedSegment")) observation.set("serializedSegment", actual.path("SerializedSegment").deepCopy());
        if (evidence != null) {
            if (!evidence.isObject()) mapping.add(new Finding("CH12-ACTUAL-EVIDENCE", "actual-input", Status.INVALID, "Evidence must be an object"));
            else for (String key : List.of("context", "history")) if (evidence.has(key)) observation.set(key, evidence.path(key).deepCopy());
        }
        Set<String> names = switch (id) {
            case "130", "131" -> Set.of("SegmentType", "SegmentLength", "CAPublicKeyFileChecksum", "EMVCardSequenceNumber",
                    "EMVChipDataLength", "EMVChipData", "EMVAdditionalInformation", "EMVAdditionalInformationIndicator",
                    "EMVAdditionalInformationLength", "EMVAdditionalInformationValue", "SerializedSegmentHex", "SerializedSegment");
            case "145", "146" -> Set.of("SegmentType", "SegmentLength", "EnhancedFleetData", "Tables", "SerializedSegment");
            case "148" -> Set.of("SegmentType", "SegmentLength", "AvailableProductInformation", "SerializedSegment");
            default -> Set.of("SegmentType", "SegmentLength", "PrintData", "SerializedSegment");
        };
        var supplied = actual.fieldNames();
        while (supplied.hasNext()) {
            String name = supplied.next();
            if (!names.contains(name)) mapping.add(new Finding("CH12-ACTUAL-MAPPING", "actual-mapping", Status.REVIEW_REQUIRED,
                    "Actual segment field has no explicit structural mapping: " + name));
        }
        Result assessed = new Chapter12SegmentPayloadValidator().validate(observation);
        mapping.addAll(assessed.findings());
        return new Result(mapping.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED,
                List.copyOf(mapping), assessed.unassessedCatalogRules());
    }

    private static void decodeTables(JsonNode encoded, ObjectNode observation, List<Finding> findings) {
        var tables = observation.putArray("tables");
        if (encoded == null || !encoded.isTextual()) {
            findings.add(new Finding("CH12-ACTUAL-TLV", "actual-mapping", Status.INVALID, "Required EnhancedFleetData must be actual text or Tables array"));
            return;
        }
        String value = encoded.textValue();
        int index = 0;
        while (index < value.length()) {
            if (value.length() - index < 6 || !value.substring(index, index + 6).matches("[0-9]{6}")) {
                findings.add(new Finding("CH12-ACTUAL-TLV", "actual-mapping", Status.INVALID, "Fleet TLV header is truncated or nonnumeric"));
                return;
            }
            String id = value.substring(index, index + 3), length = value.substring(index + 3, index + 6);
            int end = index + 6 + Integer.parseInt(length);
            if (end > value.length()) {
                findings.add(new Finding("CH12-ACTUAL-TLV", "actual-mapping", Status.INVALID, "Fleet TLV value is truncated"));
                return;
            }
            tables.addObject().put("id", id).put("length", length).put("value", value.substring(index + 6, end));
            index = end;
        }
    }

    private static void copy(JsonNode input, String name, ObjectNode target, String number) {
        if (input.has(name)) target.set(number, input.path(name).deepCopy());
    }
}
