package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Checks only Element 84 width evidence, never computed segment lengths or approval. */
public final class GenerateAtl105Element84WidthGate {
    private GenerateAtl105Element84WidthGate() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode manifest = mapper.readTree(pack.resolve("contract/element-84-width-assertions.json").toFile());
        JsonNode coverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode approval = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json").toFile());
        String source = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));
        String version = manifest.path("specificationVersion").asText();
        if (version.isBlank() || !version.equals(coverage.path("specificationVersion").asText())
            || !version.equals(approval.path("specificationVersion").asText())
            || !source.contains("Specification Release Version:    " + version)) {
            throw new IllegalStateException("Element 84 source version mismatch");
        }
        String elementBody = between(source, manifest.path("sourceHeading").asText(), manifest.path("endHeading").asText());
        String shape = manifest.path("shapeEvidence").asText();
        boolean shapeVerified = !shape.isBlank() && contains(elementBody, shape);
        int listStart = elementBody.indexOf("seven instances only:");
        int listEnd = listStart < 0 ? -1 : elementBody.indexOf("Purpose:", listStart);
        Set<String> observed = new HashSet<>();
        if (listEnd > listStart) {
            Matcher list = Pattern.compile("(?m)^\\s*•[^\\r\\n]*\\(No\\.\\s*(\\d+)\\)")
                .matcher(elementBody.substring(listStart, listEnd));
            while (list.find()) observed.add(list.group(1));
        }
        Set<String> declared = new HashSet<>();
        for (JsonNode segment : manifest.path("fourDigitSegments")) {
            if (!declared.add(segment.asText())) throw new IllegalStateException("Duplicate four-digit segment");
        }
        Matcher restriction = literal("It cannot have a length of 4 digits when sending any other segment.").matcher(elementBody);
        int secondStart = restriction.find() ? restriction.end() : -1;
        int secondEnd = secondStart < 0 ? -1 : elementBody.indexOf("Valid Codes/Values:", secondStart);
        Set<String> processingRulesSegments = new HashSet<>();
        if (secondEnd > secondStart) {
            Matcher list = Pattern.compile("(?m)^\\s*•[^\\r\\n]*\\(No\\.\\s*(\\d+)\\)")
                .matcher(elementBody.substring(secondStart, secondEnd));
            while (list.find()) processingRulesSegments.add(list.group(1));
        }
        boolean exceptionListVerified = observed.size() == 7 && observed.equals(declared)
            && processingRulesSegments.size() == 7 && processingRulesSegments.equals(declared);
        Map<String, JsonNode> rules = new HashMap<>();
        for (JsonNode rule : coverage.path("businessRequirements")) rules.put(rule.path("id").asText(), rule);
        Map<String, JsonNode> approvals = new HashMap<>();
        for (JsonNode row : approval.path("rows")) approvals.put(row.path("ruleId").asText(), row);
        if (rules.size() != approval.path("oracleRuleCount").asInt(-1) || !rules.keySet().equals(approvals.keySet())) {
            throw new IllegalStateException("Coverage and approval rule identities differ");
        }
        ArrayNode assertions = mapper.createArrayNode();
        ArrayNode drafts = mapper.createArrayNode();
        Set<String> keys = new HashSet<>();
        int matched = 0;
        for (JsonNode candidate : manifest.path("assertions")) {
            String ruleId = candidate.path("ruleId").asText();
            String segment = candidate.path("segment").asText();
            String section = candidate.path("contextSection").asText();
            String phrase = candidate.path("contextPhrase").asText();
            String sample = candidate.path("sample").asText();
            int width = candidate.path("width").asInt();
            if (!keys.add(ruleId) || !"PARTIAL_RULE_WIDTH".equals(candidate.path("claimScope").asText())
                || !segment.matches("\\d+") || (width != 3 && width != 4)
                || !sample.matches("[0-9]+") || sample.length() != width || phrase.isBlank()) {
                throw new IllegalStateException("Invalid width assertion: " + candidate);
            }
            JsonNode rule = rules.get(ruleId);
            if (rule == null) throw new IllegalStateException("Unknown catalog rule: " + ruleId);
            var location = Atl105SourceBodyLocator.find(source, section);
            String sectionBody = location.isEmpty() ? "" : sectionBody(source, location.orElseThrow().line(), section);
            Matcher rowMatch = literal(phrase).matcher(sectionBody);
            JsonNode anchor = rule.path("sourceAnchor");
            boolean backed = shapeVerified && exceptionListVerified && width == (observed.contains(segment) ? 4 : 3)
                && version.equals(anchor.path("version").asText()) && "ATL105".equals(anchor.path("specification").asText())
                && segment.equals(anchor.path("segment").asText()) && "84".equals(anchor.path("element").asText())
                && section.equals(anchor.path("section").asText()) && rule.path("sourceEvidenceVerified").asBoolean()
                && rowMatch.find();
            ObjectNode row = assertions.addObject();
            row.put("ruleId", ruleId);
            row.put("segment", segment);
            row.put("width", width);
            row.put("claimScope", "PARTIAL_RULE_WIDTH");
            row.put("sourceFile", "docs/specs/extracted_text.txt");
            row.put("sourceSection", section);
            row.put("sourceFingerprintSha256", hash(elementBody));
            row.put("contextFingerprintSha256", sectionBody.isEmpty() ? "" : hash(sectionBody));
            row.put("sourceQuote", backed ? rowMatch.group().replaceAll("\\s+", " ").trim() : phrase);
            row.put("sourceLine", backed ? location.orElseThrow().line()
                + sectionBody.substring(0, rowMatch.start()).chars().filter(character -> character == '\n').count() : -1);
            row.put("status", backed ? "PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED" : "SOURCE_MISMATCH_REVIEW_REQUIRED");
            if (backed) {
                matched++;
                addDraft(drafts, ruleId, segment, sample, "VALID_WIDTH_ONLY", "POSITIVE_WIDTH");
                addDraft(drafts, ruleId, segment, width == 4 ? sample.substring(1) : "0" + sample, "INVALID_WIDTH", "NEGATIVE_WIDTH");
            }
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-ELEMENT-84-WIDTH-GATE");
        output.put("specificationVersion", version);
        output.put("sourceFile", "docs/specs/extracted_text.txt");
        output.put("shapeVerified", shapeVerified);
        output.put("exceptionListVerified", exceptionListVerified);
        output.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        output.put("policy", "Width-only examples do not validate calculated segment length, serialization, approval or execution readiness.");
        ObjectNode summary = output.putObject("summary");
        summary.put("catalogRuleCount", rules.size());
        summary.put("curatedRuleCount", keys.size());
        summary.put("partiallyBackedRuleCount", matched);
        summary.put("sourceMismatchCount", assertions.size() - matched);
        summary.put("draftCaseCount", drafts.size());
        summary.put("smeApprovedRuleCount", approval.path("smeApprovedRuleCount").asInt());
        output.set("assertions", assertions);
        output.set("draftCases", drafts);
        Path directory = pack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(directory);
        mapper.writerWithDefaultPrettyPrinter().writeValue(directory.resolve("atl105-element-84-width-gate.json").toFile(), output);
        Files.writeString(directory.resolve("atl105-element-84-width-gate.md"), "# Element 84 Width Evidence\n\n"
            + "Curated rules: " + keys.size() + "; partially source-backed: " + matched
            + "; review-only draft cases: " + drafts.size() + "; SME-approved: "
            + summary.path("smeApprovedRuleCount").asInt() + ".\n\nWidth only; no calculated length or execution certification.\n");
        System.out.printf("widthRules=%d backed=%d drafts=%d mismatches=%d%n", keys.size(), matched, drafts.size(), assertions.size() - matched);
    }

    private static String between(String source, String start, String end) {
        int first = source.indexOf(start);
        int last = first < 0 ? -1 : source.indexOf(end, first + start.length());
        if (first < 0 || last <= first) throw new IllegalStateException("Element 84 source bounds missing");
        return source.substring(first, last);
    }

    private static String sectionBody(String source, int line, String token) {
        int start = 0;
        for (int index = 1; index < line; index++) {
            int next = source.indexOf('\n', start);
            if (next < 0) return "";
            start = next + 1;
        }
        Matcher next = Pattern.compile("(?m)^[ \\t]*\\d+(?:\\.\\d+)+[ \\t]+[^\\r\\n]+")
            .matcher(source);
        if (!next.find(start + token.length())) return "";
        return source.substring(start, next.start());
    }

    private static boolean contains(String source, String text) {
        return literal(text).matcher(source).find();
    }

    private static Pattern literal(String text) {
        String[] words = text.trim().split("\\s+");
        StringBuilder regex = new StringBuilder();
        for (String word : words) {
            if (!regex.isEmpty()) regex.append("\\s+");
            regex.append(Pattern.quote(word));
        }
        return Pattern.compile(regex.toString());
    }

    private static String hash(String body) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(body.getBytes(StandardCharsets.UTF_8)));
    }

    private static void addDraft(ArrayNode drafts, String ruleId, String segment, String value, String expected, String type) {
        ObjectNode row = drafts.addObject();
        row.put("id", "DRAFT-" + ruleId + "-" + type);
        row.put("ruleId", ruleId);
        row.put("segment", segment);
        row.put("element", "84");
        row.put("value", value);
        row.put("expectedOutcome", expected);
        row.put("caseType", type);
        row.put("status", "PARTIAL_SOURCE_DERIVED_REVIEW_REQUIRED");
        row.put("executionReady", false);
    }
}