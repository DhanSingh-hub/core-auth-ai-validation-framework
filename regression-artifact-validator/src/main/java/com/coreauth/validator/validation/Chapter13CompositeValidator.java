package com.coreauth.validator.validation;

import com.coreauth.validator.canonical.Segment103PayloadValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Decodes supplied composite wire fields; does not infer absent bytes or hardware validity. */
public final class Chapter13CompositeValidator {
    private static final Map<String, Integer> PS_LENGTHS = Map.of("47E0", 47, "47A0", 42, "4760", 41, "4720", 36, "4620", 34);

    public Chapter13CompositeValidator(JsonNode inventory) throws IOException {
        for (var evidence : Map.of("33", "16- to 20-byte Key Serial", "153", "Account Type 97",
                "154", "EA01414200200099999", "164", "Identifies the total count of all the bytes").entrySet()) {
            boolean found = false;
            for (JsonNode definition : inventory.path("elements").path(evidence.getKey()).path("definitions")) {
                if (definition.path("sourceText").asText().replaceAll("\\s+", " ").contains(evidence.getValue())) found = true;
            }
            if (!found) throw new IOException("Composite predicate lacks element-local source: " + evidence.getKey());
        }
    }

    public List<Finding> validate(JsonNode observation) {
        List<Finding> findings = new ArrayList<>();
        JsonNode elements = observation.path("elements"), context = observation.path("qualifiers");
        for (String id : List.of("33", "153", "154", "164")) {
            JsonNode value = elements.get(id);
            if (value == null || !value.isTextual() || value.textValue().isEmpty()) continue;
            String raw = value.textValue();
            if (raw.chars().anyMatch(c -> c > 127)) {
                add(findings, id, "STRUCTURE", Status.REVIEW_REQUIRED, "Composite bytes need explicit non-ASCII encoding evidence");
                continue;
            }
            switch (id) {
                case "33" -> {
                    check(findings, id, "STRUCTURE", raw.length() >= 32 && raw.length() <= 36,
                            "DUKPT field is 16-20 byte KSN plus 16 byte encrypted PIN block");
                    add(findings, id, "HARDWARE", Status.REVIEW_REQUIRED, "Shape does not prove DUKPT encryption, key synchronization or PIN composition");
                }
                case "153" -> {
                    var result = new Segment103PayloadValidator().validateElement(id, value);
                    check(findings, id, "STRUCTURE", result.isValid(), "Source signed discount blocks: " + result.errors());
                }
                case "154" -> wic(raw, context, findings);
                case "164" -> program(raw, context, findings);
                default -> throw new IllegalStateException("Unchecked composite identity");
            }
        }
        return List.copyOf(findings);
    }

    private static void program(String raw, JsonNode context, List<Finding> findings) {
        if (raw.length() < 3 || !raw.substring(0, 3).matches("[0-9]{3}")) {
            check(findings, "164", "STRUCTURE", false, "EBT Program Data requires N3 total byte length");
            return;
        }
        ObjectNode decoded = JsonNodeFactory.instance.objectNode();
        decoded.put("totalLength", raw.substring(0, 3));
        var subelements = decoded.putArray("subelements");
        int offset = 3;
        boolean valid = Integer.parseInt(raw.substring(0, 3)) == raw.length() - 3 && raw.length() - 3 <= 264;
        boolean directionValid = true;
        String direction = text(context, "direction");
        while (offset < raw.length() && valid) {
            if (offset + 4 > raw.length() || !raw.substring(offset + 2, offset + 4).matches("[0-9]{2}")) {
                valid = false;
                break;
            }
            String tag = raw.substring(offset, offset + 2), length = raw.substring(offset + 2, offset + 4);
            int size = Integer.parseInt(length), end = offset + 4 + size;
            if (end > raw.length() || size > 40) {
                valid = false;
                break;
            }
            String data = raw.substring(offset + 4, end);
            var sub = subelements.addObject().put("tag", tag).put("len", length).put("data", data);
            if ("IT".equals(tag) && size == 37) {
                sub.put("address", data.substring(0, 28)).put("zip", data.substring(28).stripTrailing());
                valid = data.substring(28).matches("[0-9]{0,9} *");
            } else if (Set.of("50", "51", "52").contains(tag) && size == 20) {
                sub.put("accountType", data.substring(0, 2)).put("amountType", data.substring(2, 4))
                        .put("currencyCode", data.substring(4, 7)).put("amountDescriptor", data.substring(7, 8))
                        .put("detail", data.substring(8));
                valid = "98".equals(data.substring(0, 2));
            } else valid = false;
            if ("REQUEST".equals(direction)) directionValid &= Set.of("50", "IT").contains(tag);
            else if ("RESPONSE".equals(direction)) directionValid &= Set.of("51", "52").contains(tag);
            offset = end;
        }
        var result = new Segment103PayloadValidator().validateElement("164", decoded);
        check(findings, "164", "STRUCTURE", valid && offset == raw.length() && result.isValid(), "Measured program TAG/LEN/detail layout: " + result.errors());
        for (String warning : result.warnings()) add(findings, "164", "SOURCE-CONFLICT", Status.REVIEW_REQUIRED, warning);
        if (Set.of("REQUEST", "RESPONSE").contains(direction == null ? "" : direction)) check(findings, "164", "DIRECTION", directionValid,
                "Request uses 50/IT; response uses 51/52");
        else add(findings, "164", "DIRECTION", Status.REVIEW_REQUIRED, "Explicit request/response direction required for Program tags");
    }

    private static void wic(String raw, JsonNode context, List<Finding> findings) {
        if (raw.length() < 4 || !raw.substring(0, 4).matches("[0-9]{4}")) {
            check(findings, "154", "STRUCTURE", false, "WIC Product Data begins with N4 total byte length");
            return;
        }
        boolean valid = Integer.parseInt(raw.substring(0, 4)) == raw.length() - 4 && raw.length() - 4 <= 2997;
        int offset = 4, count = 0;
        boolean unknownBitmap = false, reservedAction = false;
        while (offset < raw.length() && valid) {
            if (raw.startsWith("EF", offset) && !"TAG_N3".equals(text(context, "wicEncodingProfile"))) {
                add(findings, "154", "STRUCTURE", Status.REVIEW_REQUIRED, "Source specifies EF and an eight-byte date, but does not establish its N3 framing; explicit selected encoding profile required");
                return;
            }
            if (offset + 5 > raw.length() || !raw.substring(offset + 2, offset + 5).matches("[0-9]{3}")) {
                valid = false;
                break;
            }
            String tag = raw.substring(offset, offset + 2);
            int size = Integer.parseInt(raw.substring(offset + 2, offset + 5)), end = offset + 5 + size;
            if (end > raw.length()) {
                valid = false;
                break;
            }
            String data = raw.substring(offset + 5, end);
            if ("EF".equals(tag)) {
                valid = size == 8 && date(data);
                add(findings, "154", "EF-FRAMING", Status.REVIEW_REQUIRED, "EF checked under explicitly selected TAG_N3 profile; this framing is not source-certified");
            }
            else if ("EA".equals(tag)) valid = size == 14 && data.matches("1420[0-9]{10}");
            else if ("PS".equals(tag) && size >= 4 && size <= 47) {
                String bitmap = data.substring(0, 4);
                Integer expected = PS_LENGTHS.get(bitmap);
                if (expected == null && bitmap.matches("[0-9A-F]{4}")) unknownBitmap = true;
                else if (expected == null || size != expected || !data.substring(4).matches("[0-9]+")) valid = false;
                else {
                    valid = data.charAt(4) == '0' || data.charAt(4) == '1';
                    int digits = Integer.parseInt(data.substring(data.length() - 2));
                    valid &= digits >= 1 && digits <= 16;
                    if (digits >= 1 && digits <= 16) valid &= data.substring(5, 21 - digits).matches("0*");
                    if (Set.of("47E0", "47A0", "4760", "4720").contains(bitmap)) {
                        int action = Integer.parseInt(data.substring(32, 34));
                        valid &= action <= 6 || action >= 26;
                        reservedAction |= action >= 28;
                    }
                    if ("4620".equals(bitmap) && context.path("cashValueBenefit").isBoolean() && context.path("cashValueBenefit").booleanValue()) {
                        check(findings, "154", "CVB-QUANTITY", new java.math.BigInteger(data.substring(21, 27))
                                .equals(new java.math.BigInteger(data.substring(27, 32))), "CVB purchase quantity equals source item price");
                    }
                }
            } else valid = false;
            offset = end;
            count++;
        }
        add(findings, "154", "STRUCTURE", !valid || count == 0 || offset != raw.length() ? Status.INVALID
                : unknownBitmap ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED, "WIC measured length, source bitmap and positional numeric fields; unknown bitmap is not silently certified");
        if (reservedAction) add(findings, "154", "RESERVED-ACTION", Status.REVIEW_REQUIRED, "WIC Item Action 28-99 is reserved, not approved processing");
    }

    private static boolean date(String value) {
        if (!value.matches("[0-9]{8}")) return false;
        try {
            return Integer.parseInt(value.substring(0, 4)) > 0 && LocalDate.of(Integer.parseInt(value.substring(0, 4)),
                    Integer.parseInt(value.substring(4, 6)), Integer.parseInt(value.substring(6))) != null;
        } catch (DateTimeException exception) { return false; }
    }
    private static String text(JsonNode node, String field) { JsonNode value = node.get(field); return value != null && value.isTextual() ? value.textValue() : null; }
    private static void check(List<Finding> findings, String id, String rule, boolean valid, String reason) { add(findings, id, rule, valid ? Status.CHECKS_PASSED : Status.INVALID, reason); }
    private static void add(List<Finding> findings, String id, String rule, Status status, String reason) { findings.add(new Finding(id, status, "CH13-E" + id + "-COMPOSITE-" + rule, reason)); }
}
