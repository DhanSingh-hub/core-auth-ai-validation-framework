package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;

/** Actual binary wire supplied as hexadecimal bytes; logical chip hex is decoded, not counted as ASCII. */
final class EmvSegmentWireValidator {
    private EmvSegmentWireValidator() { }

    static List<Finding> validate(JsonNode input, List<IndicatorLengthValueSectionValidator.Triad> tables) {
        boolean request = "130".equals(input.path("segment").asText());
        String rule = request ? "SEG130-R-013" : "SEG131-R-006";
        List<Finding> findings = new ArrayList<>();
        JsonNode rawNode = input.get("serializedSegmentHex");
        if (rawNode == null) {
            findings.add(new Finding(rule, "binary-wire", Status.REVIEW_REQUIRED, "Actual serializedSegmentHex byte observation required"));
            return List.copyOf(findings);
        }
        String raw = rawNode.isTextual() ? rawNode.textValue() : "";
        if (!hex(raw)) {
            findings.add(new Finding(rule, "binary-wire", Status.INVALID, "Actual wire must be nonempty even-length hexadecimal bytes"));
            return List.copyOf(findings);
        }
        byte[] actual = HexFormat.of().parseHex(raw);
        JsonNode fields = input.path("elements");
        String chip = text(fields, "190");
        if (!hex(chip) || tables == null) {
            findings.add(new Finding(rule, "binary-wire", Status.INVALID, "Chip and table observations are needed to compare actual wire"));
            return List.copyOf(findings);
        }
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        boolean valid = true;
        for (String id : request ? List.of("85", "84", "187", "188", "189") : List.of("85", "84", "187", "189")) {
            String value = text(fields, id);
            if (value == null && request && List.of("187", "188").contains(id) && !fields.has(id)) value = "";
            if (value == null || !value.chars().allMatch(c -> c >= 32 && c <= 126)) { valid = false; break; }
            expected.writeBytes(value.getBytes(StandardCharsets.US_ASCII));
            if (request) expected.write(0x1c);
        }
        expected.writeBytes(HexFormat.of().parseHex(chip));
        if (request) expected.write(0x1c);
        for (var table : tables) {
            for (String value : new String[]{table.indicator(), table.declaredLength(), table.value()}) {
                if (value == null || !value.chars().allMatch(c -> c >= 32 && c <= 126)) { valid = false; continue; }
                expected.writeBytes(value.getBytes(StandardCharsets.US_ASCII));
            }
        }
        if (request) expected.write(0x1c);
        findings.add(new Finding(rule, "binary-wire", valid && Arrays.equals(actual, expected.toByteArray())
                ? Status.CHECKS_PASSED : Status.INVALID, "Measured wire equals source ordered ASCII headers, packed chip bytes and additional-information bytes/separators"));
        String declared = text(fields, "84");
        findings.add(new Finding(rule, "binary-length", declared != null && declared.matches("[0-9]{4}")
                && Integer.parseInt(declared) == actual.length && actual.length <= (request ? 3043 : 3834)
                ? Status.CHECKS_PASSED : Status.INVALID, "Actual byte count equals N4 Segment Length within Chapter12 cap; cap conflicts remain separate"));
        return List.copyOf(findings);
    }

    private static boolean hex(String value) { return value != null && value.matches("(?:[0-9A-Fa-f]{2})+"); }
    private static String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && value.isTextual() ? value.textValue() : null;
    }
}
