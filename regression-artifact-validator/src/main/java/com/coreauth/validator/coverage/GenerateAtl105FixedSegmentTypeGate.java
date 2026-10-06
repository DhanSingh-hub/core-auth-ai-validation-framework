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
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Finds only fixed Segment Type claims corroborated by their own Section 12 field-1 row. */
public final class GenerateAtl105FixedSegmentTypeGate {
    private static final Pattern TITLE = Pattern.compile("^Segment Type(?: \\(Element 85\\))? (?:is (?:fixed value )?|fixed (?:value )?)(\\d{3})(?:\\b|\\.)", Pattern.CASE_INSENSITIVE);
    private static final Pattern FIELD = Pattern.compile("(?m)^\\s*1\\s+85\\s+Segment\\s+Type\\s+3\\s+R\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALUE = Pattern.compile("(?i)\\bFixed\\s+[Vv]alue\\s*:?\\s*(\\d{3})\\b");
    private static final Pattern NEXT_FIELD = Pattern.compile("(?m)^\\s*2\\s+\\d+\\s+");

    private GenerateAtl105FixedSegmentTypeGate() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode coverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode approvals = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json").toFile());
        String source = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));
        String version = coverage.path("specificationVersion").asText();
        if (!"2026-3".equals(version) || !version.equals(approvals.path("specificationVersion").asText())
            || !source.contains("Specification Release Version:    " + version)
            || coverage.path("businessRequirements").size() != approvals.path("oracleRuleCount").asInt(-1)) {
            throw new IllegalStateException("Fixed-type gate source and catalog versions/counts disagree");
        }
        Set<String> approvalIds = new HashSet<>();
        for (JsonNode row : approvals.path("rows")) approvalIds.add(row.path("ruleId").asText());
        ArrayNode assertions = mapper.createArrayNode();
        ArrayNode draftCases = mapper.createArrayNode();
        Set<String> seen = new HashSet<>();
        int backed = 0;
        for (JsonNode rule : coverage.path("businessRequirements")) {
            String ruleId = rule.path("id").asText();
            if (!seen.add(ruleId) || !approvalIds.contains(ruleId)) throw new IllegalStateException("Unreconciled rule " + ruleId);
            Matcher claim = TITLE.matcher(rule.path("title").asText());
            if (!claim.find()) continue;
            String value = claim.group(1);
            JsonNode anchor = rule.path("sourceAnchor");
            String section = anchor.path("section").asText();
            String segment = anchor.path("segment").asText();
            if (!section.matches("12\\.\\d+") || !segment.matches("\\d{3}") || !"85".equals(anchor.path("element").asText())
                || rule.path("status").asText().contains("PROVISIONAL")) continue;
            var location = Atl105SourceBodyLocator.find(source, section);
            String body = location.isPresent() ? sectionBody(source, location.orElseThrow().line()) : "";
            Matcher field = FIELD.matcher(body);
            Matcher next = NEXT_FIELD.matcher(body);
            boolean hasField = field.find();
            boolean hasNext = hasField && next.find(field.end());
            String firstField = hasNext ? body.substring(field.start(), next.start()) : "";
            Matcher fixed = VALUE.matcher(firstField);
            boolean hasValue = fixed.find();
            String observedValue = hasValue ? fixed.group(1) : "";
            String sourceQuote = hasValue ? fixed.group() : "";
            int valueOffset = hasValue ? fixed.start() : -1;
            boolean uniqueValue = hasValue && !fixed.find();
            boolean supported = value.equals(segment) && version.equals(anchor.path("version").asText())
                && "ATL105".equals(anchor.path("specification").asText())
                && rule.path("sourceEvidenceVerified").asBoolean() && uniqueValue && value.equals(observedValue);
            ObjectNode assertion = assertions.addObject();
            assertion.put("ruleId", ruleId);
            assertion.put("section", section);
            assertion.put("segment", segment);
            assertion.put("element", "85");
            assertion.put("value", value);
            assertion.put("sourceFile", "docs/specs/extracted_text.txt");
            assertion.put("sourceQuote", sourceQuote);
            assertion.put("sourceLine", supported ? location.orElseThrow().line()
                + body.substring(0, field.start() + valueOffset).chars().filter(character -> character == '\n').count() : -1);
            assertion.put("sourceFingerprintSha256", body.isEmpty() ? "" : hash(body));
            assertion.put("status", supported ? "PARTIAL_SOURCE_BACKED_REVIEW_REQUIRED" : "SOURCE_MISMATCH_REVIEW_REQUIRED");
            if (!supported) continue;
            backed++;
            addDraft(draftCases, ruleId, segment, value, "VALID_TYPE_ONLY");
            addDraft(draftCases, ruleId, segment, "999".equals(value) ? "998" : "999", "INVALID_TYPE_FOR_SEGMENT");
        }
        if (seen.size() != approvalIds.size()) throw new IllegalStateException("Missing catalog rule in approval matrix");
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-FIXED-SEGMENT-TYPE-EVIDENCE");
        output.put("specificationVersion", version);
        output.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        output.put("policy", "A fixed field-1 value is a partial source claim, not message applicability, BR approval, or an executable fixture.");
        ObjectNode summary = output.putObject("summary");
        summary.put("catalogRuleCount", seen.size());
        summary.put("candidateRuleCount", assertions.size());
        summary.put("partiallyBackedRuleCount", backed);
        summary.put("sourceMismatchCount", assertions.size() - backed);
        summary.put("draftCaseCount", draftCases.size());
        summary.put("smeApprovedRuleCount", approvals.path("smeApprovedRuleCount").asInt());
        output.set("assertions", assertions);
        output.set("draftCases", draftCases);
        Path directory = pack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(directory);
        mapper.writerWithDefaultPrettyPrinter().writeValue(directory.resolve("atl105-fixed-segment-type-gate.json").toFile(), output);
        Files.writeString(directory.resolve("atl105-fixed-segment-type-gate.md"), "# Fixed Segment Type Evidence\n\n"
            + "Catalog rules: " + seen.size() + "; candidates: " + assertions.size() + "; partially backed: " + backed
            + "; source mismatches: " + (assertions.size() - backed) + "; review-only draft cases: " + draftCases.size()
            + "; SME-approved: " + summary.path("smeApprovedRuleCount").asInt() + ".\n\n"
            + "Only field-1 type values are checked. No message applicability, serialization, or execution certification is implied.\n");
        System.out.printf("typeCandidates=%d backed=%d mismatches=%d drafts=%d%n",
            assertions.size(), backed, assertions.size() - backed, draftCases.size());
    }

    private static String sectionBody(String source, int line) {
        int start = 0;
        for (int index = 1; index < line; index++) {
            int next = source.indexOf('\n', start);
            if (next < 0) return "";
            start = next + 1;
        }
        Matcher next = Pattern.compile("(?m)^[ \\t]*\\d+(?:\\.\\d+)+[ \\t]+[^\\r\\n]+")
            .matcher(source);
        return next.find(start + 1) ? source.substring(start, next.start()) : "";
    }

    private static String hash(String body) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(body.getBytes(StandardCharsets.UTF_8)));
    }

    private static void addDraft(ArrayNode cases, String ruleId, String segment, String value, String expected) {
        ObjectNode row = cases.addObject();
        row.put("id", "DRAFT-" + ruleId + "-" + expected);
        row.put("ruleId", ruleId);
        row.put("segment", segment);
        row.put("element", "85");
        row.put("value", value);
        row.put("expectedOutcome", expected);
        row.put("status", "PARTIAL_SOURCE_DERIVED_REVIEW_REQUIRED");
        row.put("executionReady", false);
    }
}