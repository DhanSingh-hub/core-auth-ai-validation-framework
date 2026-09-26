package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/** Validates Segment 111 wire-envelope serialization when explicitly enabled by testControls. */
public final class Segment111SerializationValidator {
    public ValidationResult validate(CanonicalArtifactPackage pkg) {
        String id = pkg == null || pkg.getManifest() == null ? "segment-111-serialization" : pkg.getManifest().getPackageId();
        ValidationResult r = new ValidationResult(id == null ? "segment-111-serialization" : id);
        if (pkg == null || pkg.getTestData() == null) {
            r.addError("Segment111Serialization", "test data is required");
            return r;
        }
        for (CanonicalTestData d : pkg.getTestData()) {
            validate(d, r);
        }
        return r;
    }

    private void validate(CanonicalTestData d, ValidationResult r) {
        if (d == null || d.getPayload() == null) {
            return;
        }
        JsonNode p = d.getPayload();
        JsonNode c = p.path("testControls");
        if (!c.path("validateSerialization").asBoolean(false)) {
            return;
        }

        String id = d.getId() == null ? "unknown-test-data" : d.getId();
        JsonNode request = p.path("request").isObject() ? p.path("request") : p.path("Financial Request");
        JsonNode s = segment111(request);
        if (!s.isObject()) {
            r.addError("Segment111Serialization", id + " Variable Information Segment 111 is required for serialization validation");
            return;
        }

        if (!"111".equals(text(s, "segmentType", "SegmentType"))) {
            r.addError("Segment111Serialization", id + " SegmentType must be 111");
        }

        List<JsonNode> reps = repetitions(s);
        int body = 0;
        for (JsonNode x : reps) {
            String v = text(x, "variableInformation", "VariableInformation", "value");
            body += 6 + (v == null ? 0 : v.length());
        }
        int computed = 3 + 1 + 3 + 1 + body + 1;
        String declared = text(s, "segmentLength", "SegmentLength");
        if (declared != null && declared.matches("[0-9]{3}") && Integer.parseInt(declared) != computed) {
            r.addError("Segment111Serialization", id + " SegmentLength must equal computed serialized length " + computed);
        }
        if (body > 991) {
            r.addError("Segment111Serialization", id + " repeated section exceeds 991");
        }
        if (computed > 999) {
            r.addError("Segment111Serialization", id + " total length exceeds 999");
        }

        JsonNode wire = s.path("wireFormat");
        if (!wire.isTextual() || wire.asText().isEmpty()) {
            r.addError("Segment111Serialization", id + " wireFormat is required when serialization validation is enabled");
            return;
        }

        String raw = wire.asText();
        char separator = separator(c.path("serialization").path("fieldSeparator").asText("\\u001c"));
        long count = raw.chars().filter(ch -> ch == separator).count();
        if (count != 3) {
            r.addError("Segment111Serialization", id + " wireFormat must contain exactly three field separators");
        }
        if (raw.charAt(raw.length() - 1) != separator) {
            r.addError("Segment111Serialization", id + " wireFormat must end with one field separator");
        }
    }

    private static JsonNode segment111(JsonNode request) {
        JsonNode section3 = request.path("dataSection3");
        if (section3.isObject()) {
            JsonNode canonical = firstObject(section3, "variableInfoSegment", "Variable Information Data Segment", "variableInformationSegment");
            if (canonical.isObject()) {
                return canonical;
            }
        }
        return firstObject(request, "Variable Information Data Segment", "variableInformationSegment", "variableInfoSegment");
    }

    private static JsonNode firstObject(JsonNode node, String... names) {
        for (String name : names) {
            JsonNode child = node.path(name);
            if (child.isObject()) {
                return child;
            }
        }
        return node.path("__missing");
    }

    private static String text(JsonNode node, String... names) {
        for (String name : names) {
            JsonNode value = node.path(name);
            if (value.isTextual()) {
                return value.asText();
            }
        }
        return null;
    }

    private static List<JsonNode> repetitions(JsonNode segment) {
        for (String key : new String[]{"variableInformationSection", "variableInformationSections", "VariableInformationSections", "repetitions", "sections"}) {
            if (segment.path(key).isArray()) {
                List<JsonNode> out = new ArrayList<>();
                segment.path(key).forEach(out::add);
                return out;
            }
        }
        if (segment.has("VariableInformationIndicator") || segment.has("variableInformationIndicator") || segment.has("indicator")) {
            return List.of(segment);
        }
        return List.of();
    }

    private static char separator(String configured) {
        if ("\\u001c".equals(configured)) {
            return '\u001c';
        }
        return configured == null || configured.isEmpty() ? '\u001c' : configured.charAt(0);
    }
}

