package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Envelope-only validator for ATL105 Segment 120 (Print Data 2 Segment).
 *
 * <p>Segment 120 is a RESPONSE-side companion segment: it only appears in Data Section 3 of a
 * Financial Transaction Response or EMV Financial Transaction Response, unlike Segment 100/101/111
 * which are request-side. Rule anchors correspond to {@code SEG120-R-###} in
 * {@code docs/specs/kb/segment-120/coverage/segment-120-rule-catalog.json}.
 *
 * <p>Item 1 baseline validator (Coverage Closure) per SEGMENT-100-TRAINING-METHODOLOGY.md.
 *
 * <p>PROVISIONAL item disposition (see {@code docs/specs/kb/segment-120/coverage/README.md}):
 * <ul>
 *   <li><b>P-01</b> (impacts SEG120-R-007, final-segment ordering) — RESOLVED 2026-09-23: literal
 *       spec text (BR-263-2, Section 12.18, "always appears at the end") is authoritative;
 *       {@code atl105_complete_templates.json}'s ordering is a numeric-sort extraction artifact,
 *       not true wire order — see
 *       {@code docs/specs/kb/segment-120/coverage/segment-120-coverage-sme-tba-note.md}.
 *       SEG120-R-007 is therefore hard-enforced below whenever Data Section 3 carries an explicit
 *       segment order (either a {@code dataSection3.segmentOrder} array or JSON key order).</li>
 *   <li><b>P-02-WIDTH</b> (impacts SEG120-R-002) — RESOLVED 2026-09-23. Element 84 spec text
 *       confirms Segment 120 is one of only seven segments whose Segment Length is always exactly
 *       4 digits, zero-padded. {@code SEGMENT_LENGTH_PATTERN} is therefore fixed-width, not the
 *       variable 3-4 digit width originally assumed.</li>
 *   <li><b>P-02-RESIDUAL</b> (impacts SEG120-R-004) — OPEN. There is a 1-character arithmetic gap
 *       between the 999-character Print Data field cap and the 1,009-character total-segment cap
 *       once Segment Length's own (now confirmed) 4-digit width is subtracted. Both caps are
 *       enforced independently below rather than one being derived from the other.</li>
 *   <li><b>P-03</b> (impacts SEG120-R-008) — OPEN. Out of scope for this envelope validator:
 *       Print Data may legitimately contain literal {@code '\'} Blackhawk line delimiters, and
 *       this content is treated as opaque text, never parsed or rejected here.</li>
 *   <li><b>P-04</b> (impacts SEG120-R-006) — OPEN. Segment 120 cardinality per message is not yet
 *       confirmed, so no "at most one" constraint is enforced here.</li>
 * </ul>
 */
public final class Segment120PayloadValidator {
    private static final String SOURCE = "Segment120Payload";

    // P-02-WIDTH resolved: Segment Length is always exactly 4 digits, zero-padded (never 3).
    private static final Pattern SEGMENT_LENGTH_PATTERN = Pattern.compile("^[0-9]{4}$");

    private static final int MAX_PRINT_DATA_FIELD = 999;   // SEG120-R-004 per-field cap
    private static final int MAX_TOTAL_SEGMENT = 1009;      // SEG120-R-004 total-segment cap (P-02-RESIDUAL open)

    /**
     * SEG120-R-002: the declared Segment Length value must equal
     * {@code 3 (Segment Type) + 2 (field separators) + len(Print Data)}. This 5-character
     * constant intentionally EXCLUDES Segment Length's own 4-digit width: the spec wording
     * ("including Segment Type's length and Field Separators") describes Segment Length as
     * informational about the rest of the segment, not self-referential. Cross-checked against
     * both positive AI-generated fixtures under
     * {@code test-input/ai-solution/test-data/segment-120/lifecycle/} (36-char and 57-char Print
     * Data values serialize to declared lengths of 0041 and 0062 respectively, both of which only
     * hold under the {@code 5 + len(printData)} formula, not {@code 9 + len(printData)} which
     * would also count Segment Length's own width).
     */
    private static final int SEGMENT_LENGTH_FIELD_OVERHEAD = 5;

    private static final Set<String> VALID_MESSAGE_FAMILIES = Set.of(
        "FINANCIAL_TRANSACTION_RESPONSE", "EMV_FINANCIAL_TRANSACTION_RESPONSE"
    );

    private static final Set<String> PRINT_DATA_2_KEYS = Set.of(
        "printData2Segment", "Print Data 2 Segment", "segment120"
    );

    private final ObjectMapper mapper;

    public Segment120PayloadValidator() {
        this(new ObjectMapper());
    }

    Segment120PayloadValidator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ValidationResult validateFile(Path file) {
        ValidationResult r = new ValidationResult(file == null ? "segment-120-payload" : file.getFileName().toString());
        if (file == null) {
            r.addError(SOURCE, "AI JSON file is required");
            return r;
        }
        try {
            validatePayload(mapper.readTree(file.toFile()), r);
        } catch (IOException e) {
            r.addError(SOURCE, "AI JSON is not readable: " + e.getMessage());
        }
        return r;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult r = new ValidationResult("segment-120-payload");
        validatePayload(payload, r);
        return r;
    }

    private void validatePayload(JsonNode payload, ValidationResult r) {
        if (payload == null || !payload.isObject()) {
            r.addError(SOURCE, "AI JSON root must be an object");
            return;
        }

        Container container = container(payload);
        JsonNode section3 = container.node().path("dataSection3");
        JsonNode segment = firstObject(section3, PRINT_DATA_2_KEYS.toArray(new String[0]));
        if (!segment.isObject()) {
            // Segment 120 is an optional response-side companion segment; its absence is not an error.
            return;
        }

        validateApplicability(payload, container.requestContext(), r);   // SEG120-R-006 (+ P-04 open, cardinality unconstrained)
        validateOrdering(section3, r);                                    // SEG120-R-007 (P-01 resolved: hard-enforced)

        String type = textual(segment, "segmentType", "SegmentType");
        if (!"120".equals(type)) {
            r.addError(SOURCE, "SEG120-R-001 Print Data 2 Segment.segmentType must be 120; found " + type);
        }

        JsonNode declaredNode = first(segment, "segmentLength", "SegmentLength");
        String declared = declaredNode != null && declaredNode.isTextual() ? declaredNode.asText() : null;
        if (declared == null || !SEGMENT_LENGTH_PATTERN.matcher(declared).matches()) {
            r.addError(SOURCE, "SEG120-R-002 segmentLength must be a textual value with exactly 4 digits (P-02-WIDTH resolved)");
        }

        String printData = textual(segment, "printData", "PrintData");
        if (printData == null) {
            r.addError(SOURCE, "SEG120-R-003 printData is required whenever Segment 120 is included");
        }

        int printDataLength = printData == null ? 0 : printData.length();
        if (printData != null && printDataLength > MAX_PRINT_DATA_FIELD) {
            r.addError(SOURCE, "SEG120-R-004 printData must not exceed 999 characters; found " + printDataLength);
        }

        int computedLength = SEGMENT_LENGTH_FIELD_OVERHEAD + printDataLength;
        int totalSerialized = 3 + 4 + 2 + printDataLength; // segmentType(3) + segmentLength(4) + 2 separators + printData
        if (totalSerialized > MAX_TOTAL_SEGMENT) {
            r.addError(SOURCE, "SEG120-R-004 total serialized Segment 120 length must not exceed 1009 characters; computed " + totalSerialized);
        }
        if (declared != null && SEGMENT_LENGTH_PATTERN.matcher(declared).matches() && Integer.parseInt(declared) != computedLength) {
            r.addError(SOURCE, "SEG120-R-002 segmentLength must equal computed structural length " + computedLength + "; found " + declared);
        }

        validateSeparators(segment, r);
        // SEG120-R-008 (P-03 open): printData may legitimately contain literal '\' Blackhawk line
        // delimiters (BR-263-5). Content is intentionally treated as opaque text and is NOT parsed
        // or rejected here; that is out of Item 1 envelope-validator scope pending SME resolution.
    }

    /** SEG120-R-005: exactly one separator between fields 1/2 and 2/3; no trailing separator (unlike Segment 111). */
    private static void validateSeparators(JsonNode segment, ValidationResult r) {
        JsonNode wire = segment.path("wireFormat");
        if (!wire.isTextual() || wire.asText().isEmpty()) {
            return; // no wire-format representation supplied; indeterminate, not an error
        }
        String raw = wire.asText();
        char separator = separator(segment.path("fieldSeparator").asText("\u001c"));
        long count = raw.chars().filter(ch -> ch == separator).count();
        if (count != 2) {
            r.addError(SOURCE, "SEG120-R-005 wireFormat must contain exactly two field separators; found " + count);
        }
        if (!raw.isEmpty() && raw.charAt(raw.length() - 1) == separator) {
            r.addError(SOURCE, "SEG120-R-005 wireFormat must NOT end with a trailing field separator after Print Data (unlike Segment 111)");
        }
    }

    private static char separator(String configured) {
        return configured == null || configured.isEmpty() ? '\u001c' : configured.charAt(0);
    }

    /** SEG120-R-006: response-only applicability. See P-04 (cardinality) for what is intentionally NOT enforced. */
    private static void validateApplicability(JsonNode payload, boolean requestContext, ValidationResult r) {
        String messageType = textual(payload, "messageType");
        String messageFamily = textual(payload.path("testControls"), "messageFamily");
        String candidate = messageFamily != null ? messageFamily : messageType;

        if (candidate != null) {
            String upper = candidate.toUpperCase(Locale.ROOT);
            if (!VALID_MESSAGE_FAMILIES.contains(upper)) {
                r.addError(SOURCE, "SEG120-R-006 messageType/testControls.messageFamily must be FINANCIAL_TRANSACTION_RESPONSE "
                    + "or EMV_FINANCIAL_TRANSACTION_RESPONSE when Segment 120 is present; found " + candidate);
                return;
            }
            if (requestContext) {
                r.addError(SOURCE, "SEG120-R-006 Segment 120 must not appear in a request message; "
                    + "messageType/messageFamily=" + candidate + " conflicts with the request container it was found in");
            }
            return;
        }
        if (requestContext) {
            r.addError(SOURCE, "SEG120-R-006 Segment 120 must not appear on a request message (found under request/Financial Request)");
        }
        // messageType/messageFamily absent and no request container detected: indeterminate, not an error.
    }

    /**
     * SEG120-R-007. P-01 resolved 2026-09-23: the literal spec text (BR-263-2, Section 12.18,
     * "The Print Data 2 Segment always appears at the end of a Financial Transaction response")
     * is authoritative. {@code docs/atl105_complete_templates.json}'s segment ordering (which
     * places Segment 120 mid-sequence) lists segment numbers in strict ascending numeric order in
     * both the Financial Transaction Response and EMV Financial Transaction Response templates --
     * assessed as a numeric-sort extraction artifact (the same pattern as the spec's own
     * "Alphabetical List of Data Element Names" appendix, which groups by number, not transmission
     * order), not genuine wire-order evidence. See
     * {@code docs/specs/kb/segment-120/coverage/segment-120-coverage-sme-tba-note.md} for the full
     * analysis. SEG120-R-007 is therefore hard-enforced whenever Data Section 3 exposes an
     * explicit segment order, via either signal below (checked in this precedence):
     * <ol>
     *   <li>an explicit {@code dataSection3.segmentOrder} array of segment-type strings (e.g.
     *       {@code ["112", "115", "120"]}) -- no existing Segment 100/101/111 convention represents
     *       "which companions are present and in what order" in the canonical model, so this field
     *       is introduced specifically for Segment 120; when present, "120" must be its last
     *       element;</li>
     *   <li>otherwise, the declaration order of Data Section 3's own JSON object keys (mirroring
     *       how Segment 111's structural field-order rule, SEG111-R-010, is checked).</li>
     * </ol>
     * When neither signal is present (a single-key Data Section 3), ordering is indeterminate and
     * trivially satisfied, not an error.
     */
    private static void validateOrdering(JsonNode section3, ValidationResult r) {
        if (!section3.isObject()) {
            return;
        }

        JsonNode explicitOrder = section3.path("segmentOrder");
        if (explicitOrder.isArray() && !explicitOrder.isEmpty()) {
            List<String> order = new ArrayList<>();
            explicitOrder.forEach(n -> order.add(n.asText()));
            if (order.contains("120") && !"120".equals(order.get(order.size() - 1))) {
                r.addError(SOURCE, "SEG120-R-007 Segment 120 (Print Data 2 Segment) must be the final entry of "
                    + "dataSection3.segmentOrder (P-01 resolved: literal spec text BR-263-2 is authoritative over "
                    + "the AI template's numeric-sort ordering; found order " + order + ")");
            }
            return;
        }

        List<String> keys = new ArrayList<>();
        section3.fieldNames().forEachRemaining(keys::add);
        int printData2Index = -1;
        for (int i = 0; i < keys.size(); i++) {
            if (PRINT_DATA_2_KEYS.contains(keys.get(i))) {
                printData2Index = i;
                break;
            }
        }
        if (printData2Index < 0) {
            return;
        }
        if (printData2Index != keys.size() - 1) {
            r.addError(SOURCE, "SEG120-R-007 Segment 120 (Print Data 2 Segment) must be the final segment of Data Section 3 "
                + "(P-01 resolved: literal spec text BR-263-2 is authoritative over the AI template's numeric-sort ordering)");
        }
    }

    private record Container(JsonNode node, boolean requestContext) {}

    private static Container container(JsonNode payload) {
        JsonNode response = firstObject(payload, "response", "Financial Transaction Response", "EMV Financial Transaction Response");
        if (response.isObject()) {
            return new Container(response, false);
        }
        JsonNode request = firstObject(payload, "request", "Financial Request");
        if (request.isObject()) {
            return new Container(request, true);
        }
        return new Container(payload, false);
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

    private static JsonNode first(JsonNode n, String... names) {
        for (String s : names) {
            if (n.has(s)) {
                return n.path(s);
            }
        }
        return null;
    }

    private static String textual(JsonNode n, String... names) {
        JsonNode value = first(n, names);
        return value != null && value.isTextual() ? value.asText() : null;
    }
}
