package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Section 12.31/12.32 prompt framing, source token caps and observed mask execution, not host authorization. */
final class EnhancedFleetPromptValidator {
    private EnhancedFleetPromptValidator() { }

    private static final Map<String, Integer> VOYAGER = Map.ofEntries(
            Map.entry("AUID", 6), Map.entry("VHID", 6), Map.entry("DRID", 6), Map.entry("ODRD", 6),
            Map.entry("TRLR", 10), Map.entry("HRRD", 7), Map.entry("TRIP", 10), Map.entry("EXID", 20),
            Map.entry("UNIT", 10), Map.entry("PONB", 10), Map.entry("DLIC", 20), Map.entry("DLST", 2),
            Map.entry("LCSN", 12), Map.entry("LCST", 2), Map.entry("TRLN", 12), Map.entry("TRLS", 2));
    private static final Map<String, Integer> VISA = Map.of("AD1", 20, "AD2", 20, "DRI", 17, "EMP", 12,
            "GEN", 17, "ODM", 7, "TRL", 16, "VHI", 17, "WON", 25);
    private static final Map<String, Integer> COMDATA = Map.ofEntries(Map.entry("UNIT", 10), Map.entry("DRID", 16),
            Map.entry("TRLR", 10), Map.entry("HBRD", 6), Map.entry("TRHB", 6), Map.entry("HRRD", 6),
            Map.entry("TRIP", 10), Map.entry("DLIC", 20), Map.entry("DLST", 2), Map.entry("PONB", 10),
            Map.entry("INVC", 8), Map.entry("XPRS", 25), Map.entry("PAYE", 20), Map.entry("XNID", 10));
    private static final Set<String> WEX = Set.of("BDAY", "BLID", "CARR", "CLCD", "CNTN", "CUNB", "CVNM",
            "DLIC", "DLST", "DMLC", "DRID", "DSCD", "DTKT", "FSTI", "HBRD", "HRRD", "ISNB", "LCCD",
            "LCST", "LICN", "LSNB", "LSTN", "NAME", "ODRD", "PDLN", "PLDS", "PONB", "PPIN", "RTMP",
            "SLDS", "SPLN", "SSEC", "SSUB", "TRIP", "TRLR", "TRNB", "UNIT", "VEHN", "VHNB", "VHTP");
    private static final Set<String> CONEXXUS = Set.of("001", "002", "003", "004", "005", "009", "010",
            "011", "012", "013", "015", "016", "017", "018", "019", "021", "022", "023", "024", "025",
            "026", "033", "035", "036", "038", "040", "041", "042", "043", "044", "045", "046", "047",
            "048", "049", "050", "051", "052", "053", "054", "055", "056", "058", "065", "067", "068", "069", "999");

    static List<Finding> validate(String value, boolean request) {
        return validate(value, request, "", null);
    }

    static List<Finding> validate(String value, boolean request, String authorizer, JsonNode history) {
        String rule = request ? "SEG145-R-008" : "SEG146-R-004";
        List<Finding> findings = new ArrayList<>();
        boolean framed = value != null && value.length() > 2 && value.startsWith("|") && value.endsWith("|");
        findings.add(new Finding(rule, "prompt-framing", framed ? Status.CHECKS_PASSED : Status.INVALID,
                "Prompt entries require leading/ending pipes and colon-separated tokens and values"));
        if (!framed) return List.copyOf(findings);
        Map<String, String> entries = new LinkedHashMap<>();
        for (String entry : value.substring(1, value.length() - 1).split("\\|", -1)) {
            int colon = entry.indexOf(':');
            String token = colon < 0 ? "" : entry.substring(0, colon);
            int cap = tokenCap(authorizer, token);
            boolean shape = colon >= 1 && colon <= 4 && entry.substring(0, colon).matches("[A-Za-z0-9]+")
                    && entry.length() - colon - 1 <= (request ? Math.max(cap, 20) : 20) && (request || entry.length() > colon + 1);
            findings.add(new Finding(rule, "prompt-entry", shape ? Status.CHECKS_PASSED : Status.INVALID,
                    "Prompt token has 1-4 characters and a value/format of at most 20; a format is nonempty"));
            if (!shape) continue;
            String response = entry.substring(colon + 1);
            check(findings, rule, "prompt-identity", !entries.containsKey(token), "Prompt tokens occur once in this table");
            entries.put(token, response);
            if (!authorizer.isBlank()) {
                if (Set.of("VOYAGER_EMV", "VISA_FLEET_2", "COMDATA", "WEX_OTR", "MASTERCARD_ENHANCED_FLEET_EMV").contains(authorizer)) {
                    check(findings, rule, "authorizer-prompt", cap > 0, "Token belongs to the source-listed authorizer prompt table");
                    if (request && cap > 0) check(findings, rule, "prompt-value-cap", response.length() <= cap, "Source-specific token value cap");
                    if (request && cap > 20 && response.length() > 20) review(findings, rule, "prompt-cap-conflict",
                            "Authorizer token cap 25 conflicts with generic Prompt Response cap 20; this interval is not certified");
                } else review(findings, rule, "authorizer-prompt", "No source-local prompt enumeration for supplied authorizer");
            } else review(findings, rule, "authorizer-prompt", "Authorizer observation required for token enumeration");
            if ("SSEC".equals(token)) review(findings, rule, "sensitive-prompt", "Source says SSEC should not be used; policy/provenance review required");
            if (request) continue;
            boolean valid = true;
            for (String mask : entry.substring(colon + 1).split(";", -1)) {
                valid &= mask.matches("[?ABNIOZ]|T[NS]|[MX][0-9]+|V[^;|]+|P[@#*A-Za-z0-9]+");
            }
            findings.add(new Finding(rule, "edit-mask-grammar", valid ? Status.CHECKS_PASSED : Status.INVALID,
                    "Each semicolon-separated edit mask uses the published grammar"));
        }
        if ("VISA_FLEET_2".equals(authorizer)) check(findings, rule, "visa-shared-prompt",
                entries.keySet().stream().filter(Set.of("GEN", "VHI", "DRI")::contains).count() <= 1,
                "Issuer prompts at most one Generic ID, Vehicle ID or Driver ID");
        execute(entries, request, authorizer, history, findings);
        return List.copyOf(findings);
    }

    private static int tokenCap(String authorizer, String token) {
        return switch (authorizer) {
            case "VOYAGER_EMV" -> token.matches("DF[3-6][0-9A-F]") && Integer.parseInt(token.substring(2), 16) >= 0x32
                    ? 20 : VOYAGER.getOrDefault(token, 0);
            case "VISA_FLEET_2" -> VISA.getOrDefault(token, 0);
            case "COMDATA" -> COMDATA.getOrDefault(token, 0);
            case "WEX_OTR" -> WEX.contains(token) ? 20 : 0;
            case "MASTERCARD_ENHANCED_FLEET_EMV" -> CONEXXUS.contains(token) ? 20 : 0;
            default -> 20;
        };
    }

    private static void execute(Map<String, String> entries, boolean request, String authorizer, JsonNode history, List<Finding> findings) {
        String rule = "SEG146-R-004";
        if (history == null || history.isMissingNode()) {
            review(findings, rule, "prompt-execution", "Observed fleet prompt history required for value/mask and follow-up execution");
            return;
        }
        if (!history.isObject()) {
            check(findings, rule, "prompt-history", false, "Fleet prompt history must be an object");
            return;
        }
        if (history.has("priorPromptFormats") && textMap(history.get("priorPromptFormats")) == null) {
            check(findings, rule, "prompt-history", false, "Prior prompt formats must be a text-valued object");
            return;
        }
        JsonNode formatsNode = history.get("promptFormats");
        if (request && formatsNode == null) {
            review(findings, rule, "prompt-execution", "Matched prior response prompt formats required");
            return;
        }
        Map<String, String> formats = request ? textMap(formatsNode) : entries;
        Map<String, String> captured = request ? entries : textMap(history.get("capturedResponses"));
        if (formats == null || captured == null) {
            if (!request && !history.has("capturedResponses")) review(findings, rule, "prompt-execution", "Observed captured responses required");
            else check(findings, rule, "prompt-history", false, "Formats and captured responses must be text-valued objects");
            return;
        }
        for (var entry : formats.entrySet()) {
            String token = entry.getKey(), format = entry.getValue();
            if (!token.matches("[A-Za-z0-9]{1,4}") || format.isEmpty() || format.length() > 20) {
                check(findings, rule, "execution-mask-grammar", false, "Matched prompt identity/format uses source caps");
                continue;
            }
            Set<String> masks = new java.util.LinkedHashSet<>(List.of(format.split(";", -1)));
            if (masks.contains("?")) {
                JsonNode previous = history.path("priorPromptFormats").get(token);
                if (previous == null) {
                    review(findings, rule, "reprompt-format", "Previously received format required for re-prompt");
                    continue;
                }
                if (!previous.isTextual() || previous.textValue().isEmpty() || previous.textValue().length() > 20 || previous.textValue().contains("?")) {
                    check(findings, rule, "reprompt-format", false, "Prior re-prompt format must be concrete textual masks");
                    continue;
                }
                masks.remove("?");
                masks.addAll(List.of(previous.textValue().split(";", -1)));
            }
            boolean grammar = masks.stream().allMatch(m -> m.length() <= 20
                    && m.matches("[ABNIOZ]|T[NS]|[MX][0-9]+|V[^;|]+|P[@#*A-Za-z0-9]+"));
            check(findings, rule, "execution-mask-grammar", grammar, "Observed effective masks use source grammar");
            if (!grammar) continue;
            if (masks.contains("Z")) {
                JsonNode subsequent = history.get("subsequentResponses");
                if (subsequent == null) review(findings, rule, "capture-without-resend", "Observed subsequent prompt responses required for Z");
                else check(findings, rule, "capture-without-resend", textMap(subsequent) != null && !subsequent.has(token),
                        "Z prompt is captured but absent from subsequent sent responses");
            }
            String actual = captured.get(token);
            if (actual == null || actual.isEmpty()) {
                check(findings, rule, "prompt-requiredness", masks.contains("O"), "Only an optional response may be omitted");
                continue;
            }
            check(findings, rule, "captured-value", actual.length() <= 25 && actual.chars().allMatch(c -> c >= 32 && c <= 126),
                    "Captured prompt value is bounded printable text");
            if (!authorizer.isBlank()) {
                int cap = tokenCap(authorizer, token);
                check(findings, rule, "captured-token-cap", cap > 0 && actual.length() <= cap,
                        "Captured value fits source authorizer-specific token cap");
                if (cap > 20 && actual.length() > 20) review(findings, rule, "prompt-cap-conflict",
                        "Authorizer cap25 and generic cap20 disagree; captured interval requires source-owner resolution");
            }
            boolean valid = true;
            for (String mask : masks) {
                if ("A".equals(mask) || "B".equals(mask)) valid &= actual.matches("[A-Za-z0-9]+");
                else if ("N".equals(mask)) valid &= actual.matches("[0-9]+");
                else if ("TN".equals(mask)) valid &= actual.matches("[+-]?[0-9]+(?:\\.[0-9]+)?");
                else if (mask.startsWith("V")) valid &= actual.equals(mask.substring(1));
                else if (mask.startsWith("P")) valid &= patternMatches(actual, mask.substring(1));
                else if (mask.startsWith("M") || mask.startsWith("X")) {
                    if (masks.contains("TN") == masks.contains("TS")) {
                        review(findings, rule, "bound-type", "M/X requires one observed TN or TS type; no default type is inferred");
                        continue;
                    }
                    if (masks.contains("TN") && !actual.matches("[+-]?[0-9]+(?:\\.[0-9]+)?")) { valid = false; continue; }
                    BigDecimal measured = masks.contains("TN") ? new BigDecimal(actual) : BigDecimal.valueOf(actual.length());
                    int comparison = measured.compareTo(new BigDecimal(mask.substring(1)));
                    valid &= mask.startsWith("M") ? comparison >= 0 : comparison <= 0;
                }
            }
            boolean unknownBoundType = masks.stream().anyMatch(m -> m.startsWith("M") || m.startsWith("X"))
                    && !masks.contains("TN") && !masks.contains("TS");
            if (valid && unknownBoundType) review(findings, rule, "mask-value", "Bound cannot be assessed without observed TN or TS");
            else check(findings, rule, "mask-value", valid && !(masks.contains("TN") && masks.contains("TS")),
                    "Captured value meets type, minimum/maximum, exact and pattern masks; min/max boundaries are inclusive");
        }
        for (String token : captured.keySet()) {
            if (!formats.containsKey(token)) review(findings, rule, "unmatched-capture", "Captured token has no matched format; cannot establish mask compliance");
        }
    }

    private static Map<String, String> textMap(JsonNode node) {
        if (node == null || !node.isObject()) return null;
        Map<String, String> values = new LinkedHashMap<>();
        var fields = node.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            if (!field.getValue().isTextual()) return null;
            values.put(field.getKey(), field.getValue().textValue());
        }
        return values;
    }

    private static boolean patternMatches(String actual, String pattern) {
        if (actual.length() != pattern.length()) return false;
        for (int i = 0; i < pattern.length(); i++) {
            char value = actual.charAt(i), mask = pattern.charAt(i);
            boolean alpha = value >= 'A' && value <= 'Z' || value >= 'a' && value <= 'z';
            boolean digit = value >= '0' && value <= '9';
            if (!(mask == '@' ? alpha : mask == '#' ? digit : mask == '*' ? alpha || digit : mask == value)) return false;
        }
        return true;
    }

    private static void check(List<Finding> findings, String rule, String scope, boolean valid, String reason) {
        findings.add(new Finding(rule, scope, valid ? Status.CHECKS_PASSED : Status.INVALID, reason));
    }

    private static void review(List<Finding> findings, String rule, String scope, String reason) {
        findings.add(new Finding(rule, scope, Status.REVIEW_REQUIRED, reason));
    }
}
