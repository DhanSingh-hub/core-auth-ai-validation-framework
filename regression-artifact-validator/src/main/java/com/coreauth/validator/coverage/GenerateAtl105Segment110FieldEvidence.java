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

/** Checks Section 12.9 field-row evidence; never certifies field behavior or approval. */
public final class GenerateAtl105Segment110FieldEvidence {
    private GenerateAtl105Segment110FieldEvidence() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode manifest = mapper.readTree(pack.resolve("contract/segment-110-field-row-assertions.json").toFile());
        JsonNode coverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode approval = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json").toFile());
        String source = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));
        String version = manifest.path("specificationVersion").asText();
        if (version.isBlank() || !version.equals(coverage.path("specificationVersion").asText())
            || !version.equals(approval.path("specificationVersion").asText())
            || !source.contains("Specification Release Version:    " + version)) {
            throw new IllegalStateException("Segment 110 source version mismatch");
        }
        var location = Atl105SourceBodyLocator.find(source, manifest.path("section").asText())
            .orElseThrow(() -> new IllegalStateException("Section 12.9 body heading not unique"));
        if (!location.heading().equals(manifest.path("heading").asText())) {
            throw new IllegalStateException("Unexpected Section 12.9 heading");
        }
        int start = lineOffset(source, location.line());
        Pattern nextHeading = Pattern.compile("(?m)^[ \\t]*" + Pattern.quote(manifest.path("nextHeading").asText()) + "[ \\t]*$");
        Matcher end = nextHeading.matcher(source);
        if (!end.find(start + location.heading().length())) {
            throw new IllegalStateException("Section 12.9 boundary missing or ambiguous");
        }
        int endOffset = end.start();
        if (end.find()) throw new IllegalStateException("Section 12.9 boundary missing or ambiguous");
        String body = source.substring(start, endOffset);
        Map<String, JsonNode> rules = new HashMap<>();
        for (JsonNode rule : coverage.path("businessRequirements")) {
            String id = rule.path("id").asText();
            if (id.isBlank() || rules.putIfAbsent(id, rule) != null) throw new IllegalStateException("Duplicate catalog rule " + id);
        }
        Set<String> approvalIds = new HashSet<>();
        for (JsonNode row : approval.path("rows")) if (!approvalIds.add(row.path("ruleId").asText())) {
            throw new IllegalStateException("Duplicate approval rule");
        }
        if (rules.isEmpty() || !rules.keySet().equals(approvalIds)
            || rules.size() != approval.path("oracleRuleCount").asInt(-1)) {
            throw new IllegalStateException("Catalog and approval rule identities differ");
        }

        ArrayNode assertions = mapper.createArrayNode();
        Set<String> seen = new HashSet<>();
        int backed = 0;
        for (JsonNode candidate : manifest.path("assertions")) {
            String id = candidate.path("ruleId").asText();
            JsonNode rule = rules.get(id);
            if (rule == null || !seen.add(id)) throw new IllegalStateException("Unknown or duplicate curated rule " + id);
            String scope = candidate.path("claimScope").asText();
            boolean fieldRow = "PARTIAL_FIELD_ROW".equals(scope);
            if (!fieldRow && !"PARTIAL_SEGMENT_MAXIMUM".equals(scope)) {
                throw new IllegalStateException("Unsupported claim scope " + scope);
            }
            String phrase = fieldRow ? candidate.path("field").asText() + " " + candidate.path("element").asText()
                + " " + candidate.path("name").asText() + " " + candidate.path("length").asText()
                + " " + candidate.path("entry").asText() : candidate.path("sourcePhrase").asText();
            if (phrase.isBlank() || (fieldRow && (!candidate.path("field").canConvertToInt()
                || !candidate.path("length").canConvertToInt() || !candidate.path("element").asText().matches("\\d+")))) {
                throw new IllegalStateException("Malformed curated claim " + id);
            }
            String[] tokens = phrase.trim().split("\\s+");
            StringBuilder expression = new StringBuilder();
            for (String token : tokens) {
                if (!expression.isEmpty()) expression.append("\\s+");
                expression.append(Pattern.quote(token));
            }
            Matcher match = Pattern.compile(expression.toString()).matcher(body);
            boolean found = match.find();
            int matchStart = found ? match.start() : -1;
            String quote = found ? match.group().replaceAll("\\s+", " ").trim() : "";
            boolean unique = found && !match.find();
            JsonNode anchor = rule.path("sourceAnchor");
            boolean backedClaim = unique && version.equals(anchor.path("version").asText())
                && "ATL105".equals(anchor.path("specification").asText())
                && manifest.path("section").asText().equals(anchor.path("section").asText())
                && manifest.path("segment").asText().equals(anchor.path("segment").asText())
                && (!fieldRow || candidate.path("element").asText().equals(anchor.path("element").asText()))
                && rule.path("sourceEvidenceVerified").asBoolean();
            ObjectNode row = assertions.addObject();
            row.put("ruleId", id);
            row.put("claimScope", scope);
            row.put("sourceFile", "docs/specs/extracted_text.txt");
            row.put("sourceSection", manifest.path("section").asText());
            row.put("sourceFingerprintSha256", HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(body.getBytes(StandardCharsets.UTF_8))));
            row.put("sourceQuote", backedClaim ? quote : "");
            row.put("sourceLine", backedClaim ? location.line()
                + body.substring(0, matchStart).chars().filter(character -> character == '\n').count() : -1);
            row.put("status", backedClaim ? "PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED" : "SOURCE_MISMATCH_REVIEW_REQUIRED");
            if (backedClaim) backed++;
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-SEGMENT-110-FIELD-EVIDENCE");
        output.put("specificationVersion", version);
        output.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        output.put("policy", "Table-row evidence proves neither conditional triggers nor valid values nor segment serialization; no cases or approval are inferred.");
        ObjectNode summary = output.putObject("summary");
        summary.put("catalogRuleCount", rules.size());
        summary.put("candidateRuleCount", seen.size());
        summary.put("partiallyBackedRuleCount", backed);
        summary.put("sourceMismatchCount", seen.size() - backed);
        summary.put("draftCaseCount", 0);
        summary.put("smeApprovedRuleCount", approval.path("smeApprovedRuleCount").asInt());
        output.set("assertions", assertions);
        output.set("draftCases", mapper.createArrayNode());
        Path directory = pack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(directory);
        mapper.writerWithDefaultPrettyPrinter().writeValue(directory.resolve("atl105-segment-110-field-evidence.json").toFile(), output);
        Files.writeString(directory.resolve("atl105-segment-110-field-evidence.md"), "# Segment 110 Field Evidence\n\n"
            + "Curated: " + seen.size() + "; partially backed: " + backed + "; source mismatches: " + (seen.size() - backed)
            + "; draft cases: 0; SME-approved: " + summary.path("smeApprovedRuleCount").asInt() + ".\n\n"
            + "Rows and maximum only; conditional triggers, values and serialization remain review-required.\n");
        System.out.printf("segment110Candidates=%d backed=%d mismatches=%d%n", seen.size(), backed, seen.size() - backed);
    }

    private static int lineOffset(String source, int line) {
        int start = 0;
        for (int index = 1; index < line; index++) {
            int next = source.indexOf('\n', start);
            if (next < 0) throw new IllegalStateException("Section 12.9 line is outside source");
            start = next + 1;
        }
        return start;
    }
}