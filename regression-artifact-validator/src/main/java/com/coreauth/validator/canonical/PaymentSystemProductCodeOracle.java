package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Evaluates Conexxus Payment System Product Codes (PSPC) referenced by ATL105 2026-4:
 * CLASSIFICATION of a PSPC code, ELEMENT_77_CROSSWALK against ATL105 Appendix F, and
 * HOST_PURCHASE_RESTRICTION for Appendix K Table ID 034.
 */
public final class PaymentSystemProductCodeOracle {
    public static final String VISA_FLEET = "VISA_FLEET_EMV_2_0";
    public static final String MASTERCARD_ENHANCED_FLEET = "MASTERCARD_ENHANCED_FLEET_EMV";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int MAX_TABLE_DATA = 32;
    private static final Set<String> CONSISTENT = Set.of("BOTH_UNUSED", "DESCRIPTION_MATCH", "NEITHER_ASSIGNED", "APPENDIX_F_PROPRIETARY_ASSIGNMENT");
    private final Map<String, JsonNode> pspc;
    private final Map<String, JsonNode> appendixF;
    private final Set<String> appendixFConflicts = new HashSet<>();
    private final String version;

    public PaymentSystemProductCodeOracle() throws IOException {
        JsonNode source = load("/atl105/pspc-product-code-oracle.json");
        JsonNode appendix = load("/atl105/appendix-f-product-code-oracle.json");
        pspc = index(source, "PSPC");
        appendixF = index(appendix, "Appendix F");
        appendix.path("sourceConflict").path("codes").forEach(code -> appendixFConflicts.add(code.asText()));
        version = source.path("version").asText();
    }

    public ObjectNode evaluate(JsonNode payload) {
        ObjectNode result = MAPPER.createObjectNode();
        result.put("outcome", "FAIL");
        result.put("referenceVersion", version);
        result.put("transactionEligibility", "NOT_EVALUATED");
        if (payload == null || !payload.isObject()) return result.put("reason", "Payload must be an object");
        return switch (payload.path("mode").asText()) {
            case "CLASSIFICATION" -> classification(payload.path("ProductCode"), result);
            case "ELEMENT_77_CROSSWALK" -> crosswalk(payload.path("ProductCode"), result);
            case "HOST_PURCHASE_RESTRICTION" -> hostPurchaseRestriction(payload, result);
            default -> result.put("reason", "Unsupported assertion mode");
        };
    }

    private ObjectNode classification(JsonNode input, ObjectNode result) {
        if (!wellFormed(input)) return result.put("reason", "ProductCode must be a string of exactly three ASCII digits");
        JsonNode row = pspc.get(input.textValue());
        result.put("outcome", "PASS");
        for (String field : new String[]{"category", "assignment", "description", "deprecated", "sourceRange", "sourceAnchor"}) {
            result.set(field, row.path(field).deepCopy());
        }
        return result;
    }

    private ObjectNode crosswalk(JsonNode input, ObjectNode result) {
        if (!wellFormed(input)) return result.put("reason", "ProductCode must be a string of exactly three ASCII digits");
        String code = input.textValue();
        JsonNode p = pspc.get(code);
        JsonNode a = appendixF.get(code);
        String pAssignment = p.path("assignment").asText();
        String aAssignment = a.path("assignment").asText();
        String relation;
        if ("DEPRECATED".equals(pAssignment)) {
            relation = "PSPC_DEPRECATED";
        } else if ("UNUSED".equals(pAssignment) || "UNUSED".equals(aAssignment)) {
            relation = pAssignment.equals(aAssignment) ? "BOTH_UNUSED" : "DESCRIPTION_DIFFERS";
        } else {
            boolean pAssigned = "ASSIGNED".equals(pAssignment);
            boolean aAssigned = "ASSIGNED".equals(aAssignment);
            if (pAssigned && aAssigned) {
                relation = comparable(p.path("description").asText()).equals(comparable(a.path("description").asText())) ? "DESCRIPTION_MATCH" : "DESCRIPTION_DIFFERS";
            } else if (pAssigned) {
                relation = "PSPC_ONLY_ASSIGNED";
            } else if (aAssigned) {
                relation = "RESERVED_PROPRIETARY".equals(pAssignment) ? "APPENDIX_F_PROPRIETARY_ASSIGNMENT" : "APPENDIX_F_ONLY_ASSIGNED";
            } else {
                relation = "NEITHER_ASSIGNED";
            }
        }
        boolean conflict = appendixFConflicts.contains(code);
        result.put("outcome", CONSISTENT.contains(relation) && !conflict ? "PASS" : "REVIEW_REQUIRED");
        result.put("relation", relation);
        result.put("element77Authority", "ATL105_APPENDIX_F");
        result.put("appendixFSourceConflict", conflict);
        result.putObject("appendixF").put("table", a.path("table").asText()).put("assignment", aAssignment).put("description", a.path("description").asText());
        result.putObject("pspc").put("category", p.path("category").asText()).put("assignment", pAssignment).put("description", p.path("description").asText());
        return result;
    }

    private ObjectNode hostPurchaseRestriction(JsonNode payload, ObjectNode result) {
        ArrayNode checks = result.putArray("checks");
        check(checks, "TABLE_ID", "034".equals(payload.path("tableId").asText()) && payload.path("tableId").isTextual() ? "PASS" : "FAIL",
                "Appendix K-26: Table ID n3 fixed value 034");
        JsonNode data = payload.path("tableData");
        boolean dataValid = data.isTextual() && !data.textValue().isEmpty() && data.textValue().length() <= MAX_TABLE_DATA
                && data.textValue().chars().allMatch(ch -> ch >= 0x20 && ch <= 0x7E);
        check(checks, "TABLE_DATA_FORMAT", dataValid ? "PASS" : "FAIL", "Appendix K-26: Table Data ans32, variable length up to 32 printable bytes");
        String method = payload.path("purchaseRestrictionMethod").isTextual() ? payload.path("purchaseRestrictionMethod").textValue() : null;
        check(checks, "REQUEST_OPT_IN", "2".equals(method) || "3".equals(method) ? "PASS" : "FAIL",
                method == null ? "Purchase Restriction Method absent: BUYPASS defaults to no restrictions supported, so Table 034 must not be received"
                        : "Table 034 is received only when Table 049 Sub Table 18 was 2 (Host-Based Only) or 3 (Both Chip and Host Based)");
        String program = payload.path("fleetProgram").asText();
        String programOutcome = MASTERCARD_ENHANCED_FLEET.equals(program) ? "PASS" : VISA_FLEET.equals(program) ? "REVIEW_REQUIRED" : "FAIL";
        ObjectNode programCheck = check(checks, "FLEET_PROGRAM", programOutcome, VISA_FLEET.equals(program)
                ? "Visa Fleet EMV 2.0 is named by Appendix K-26 but Appendix I-46 limits the request indicator to MasterCard Enhanced Fleet; kept REVIEW_REQUIRED"
                : "Applicable only to Visa Fleet EMV 2.0 and MasterCard Enhanced Fleet EMV transactions");
        if (VISA_FLEET.equals(program)) programCheck.put("registerId", "SEG111-SME-003");
        if (MASTERCARD_ENHANCED_FLEET.equals(program)) {
            result.put("availabilityNotice", "Appendix I-47: MasterCard Enhanced functionality expected in First Data production no earlier than June 2026");
        }
        JsonNode codes = payload.path("productCodes");
        ArrayNode codeResults = result.putArray("productCodeResults");
        if (!codes.isArray() || codes.isEmpty()) {
            check(checks, "VALUE_SEMANTICS", "REVIEW_REQUIRED", "ATL105 does not define how PSPC codes are packed into Table Data; supply explicit productCodes from the network contract");
        } else {
            String worst = "PASS";
            for (JsonNode code : codes) {
                ObjectNode entry = codeResults.addObject();
                entry.set("ProductCode", code.deepCopy());
                String outcome;
                if (!wellFormed(code)) {
                    outcome = "FAIL";
                    entry.put("reason", "PSPC code must be three ASCII digits");
                } else {
                    JsonNode row = pspc.get(code.textValue());
                    String assignment = row.path("assignment").asText();
                    entry.put("assignment", assignment).put("description", row.path("description").asText());
                    outcome = switch (assignment) {
                        case "ASSIGNED" -> "PASS";
                        case "UNUSED" -> "FAIL";
                        default -> "REVIEW_REQUIRED";
                    };
                }
                entry.put("outcome", outcome);
                worst = worse(worst, outcome);
            }
            check(checks, "VALUE_SEMANTICS", worst, "Each code is resolved against Conexxus PSPC v" + version
                    + "; deprecated and reserved codes are not defined products in this version");
        }
        String overall = "PASS";
        for (JsonNode item : checks) overall = worse(overall, item.path("outcome").asText());
        return result.put("outcome", overall);
    }

    private static ObjectNode check(ArrayNode checks, String name, String outcome, String reason) {
        return checks.addObject().put("check", name).put("outcome", outcome).put("reason", reason);
    }

    private static String worse(String left, String right) {
        if ("FAIL".equals(left) || "FAIL".equals(right)) return "FAIL";
        if ("REVIEW_REQUIRED".equals(left) || "REVIEW_REQUIRED".equals(right)) return "REVIEW_REQUIRED";
        return "PASS";
    }

    private static boolean wellFormed(JsonNode input) {
        return input != null && input.isTextual() && input.textValue().matches("[0-9]{3}");
    }

    private static String comparable(String text) {
        StringBuilder out = new StringBuilder();
        for (char ch : text.toLowerCase(Locale.ROOT).toCharArray()) {
            if ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) out.append(ch);
        }
        return out.toString();
    }

    private static JsonNode load(String resource) throws IOException {
        try (var stream = PaymentSystemProductCodeOracle.class.getResourceAsStream(resource)) {
            if (stream == null) throw new IOException("Missing resource " + resource);
            return MAPPER.readTree(stream);
        }
    }

    private static Map<String, JsonNode> index(JsonNode source, String label) throws IOException {
        Map<String, JsonNode> indexed = new HashMap<>();
        for (JsonNode row : source.path("rows")) {
            int start = Integer.parseInt(row.path("startCode").asText());
            int end = Integer.parseInt(row.path("endCode").asText());
            if (start < 0 || end > 999 || end < start) throw new IOException("Invalid " + label + " range");
            for (int value = start; value <= end; value++) {
                if (indexed.put(String.format(Locale.ROOT, "%03d", value), row) != null) throw new IOException("Overlapping " + label + " code " + value);
            }
        }
        if (indexed.size() != 1000) throw new IOException("Incomplete " + label + " oracle");
        return Map.copyOf(indexed);
    }
}
