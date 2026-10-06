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

/** Checks two existing Totals Prompt Code constants in their own source sections. */
public final class GenerateAtl105TotalsPromptCodeEvidence {
    private static final Pattern FIELD = Pattern.compile("(?m)^[ \\t]*5[ \\t]+78[ \\t]+Prompt[ \\t]+Code[ \\t]+3[ \\t]+R\\b");
    private static final Pattern NEXT_FIELD = Pattern.compile("(?m)^[ \\t]*6[ \\t]+\\d+[ \\t]+");
    private static final Pattern FIXED = Pattern.compile("(?i)\\bFixed[ \\t]+value:[ \\t]*(\\d{3})\\b");

    private GenerateAtl105TotalsPromptCodeEvidence() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode manifest = mapper.readTree(pack.resolve("contract/totals-prompt-code-assertions.json").toFile());
        Path review = pack.resolve("test-output/test-solution-independent-review");
        JsonNode coverage = mapper.readTree(review.resolve("knowledge-base-br-coverage-package.json").toFile());
        JsonNode approval = mapper.readTree(review.resolve("atl105-br-approval-matrix.json").toFile());
        String source = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));
        String version = manifest.path("specificationVersion").asText();
        if (!"2026-3".equals(version) || !version.equals(coverage.path("specificationVersion").asText())
            || !version.equals(approval.path("specificationVersion").asText())
            || !source.contains("Specification Release Version:    " + version)) {
            throw new IllegalStateException("Totals Prompt Code source version mismatch");
        }
        Map<String, JsonNode> rules = new HashMap<>();
        for (JsonNode rule : coverage.path("businessRequirements")) {
            String id = rule.path("id").asText();
            if (id.isBlank() || rules.putIfAbsent(id, rule) != null) throw new IllegalStateException("Duplicate rule " + id);
        }
        Set<String> approvedIds = new HashSet<>();
        for (JsonNode row : approval.path("rows")) if (!approvedIds.add(row.path("ruleId").asText())) {
            throw new IllegalStateException("Duplicate approval rule ID");
        }
        if (rules.isEmpty() || !rules.keySet().equals(approvedIds)
            || rules.size() != approval.path("oracleRuleCount").asInt(-1)) {
            throw new IllegalStateException("Catalog and approval rule identities differ");
        }

        Set<String> candidates = new HashSet<>();
        ArrayNode assertions = mapper.createArrayNode();
        int backed = 0;
        for (JsonNode candidate : manifest.path("assertions")) {
            String id = candidate.path("ruleId").asText();
            String section = candidate.path("section").asText();
            String segment = candidate.path("segment").asText();
            String value = candidate.path("value").asText();
            if (!candidates.add(id) || !Set.of("105", "119").contains(segment)
                || !"PARTIAL_FIXED_PROMPT_CODE".equals(candidate.path("claimScope").asText())
                || !"78".equals(candidate.path("element").asText()) || candidate.path("field").asInt() != 5
                || candidate.path("width").asInt() != 3 || !"R".equals(candidate.path("entry").asText())
                || !"990".equals(value) || !("SEG" + segment + "-R-" + ("105".equals(segment) ? "005" : "012")).equals(id)) {
                throw new IllegalStateException("Unsupported Totals Prompt Code claim " + candidate);
            }
            JsonNode rule = rules.get(id);
            if (rule == null) throw new IllegalStateException("Unknown catalog rule " + id);
            JsonNode anchor = rule.path("sourceAnchor");
            boolean matchingRule = version.equals(anchor.path("version").asText())
                && "ATL105".equals(anchor.path("specification").asText())
                && section.equals(anchor.path("section").asText())
                && segment.equals(anchor.path("segment").asText())
                && "78".equals(anchor.path("element").asText())
                && rule.path("title").asText().matches("Prompt Code is (?:fixed value )?990")
                && rule.path("sourceEvidenceVerified").asBoolean();
            var location = Atl105SourceBodyLocator.find(source, section);
            String body = location.isEmpty() ? "" : sectionBody(source, location.orElseThrow().line());
            Matcher segmentIds = Pattern.compile("(?m)^Data Segment No\\.[ \\t]+(\\d+)[ \\t]*$").matcher(body);
            boolean exactSegment = false;
            boolean conflictingSegment = false;
            while (segmentIds.find()) {
                if (segment.equals(segmentIds.group(1))) exactSegment = true;
                else conflictingSegment = true;
            }
            Matcher field = FIELD.matcher(body);
            boolean fieldFound = field.find();
            int start = fieldFound ? field.start() : -1;
            Matcher next = NEXT_FIELD.matcher(body);
            boolean nextFound = fieldFound && next.find(field.end());
            String fieldBody = nextFound ? body.substring(start, next.start()) : "";
            Matcher fixed = FIXED.matcher(fieldBody);
            boolean found = fixed.find();
            String observed = found ? fixed.group(1) : "";
            String quote = found ? fixed.group() : "";
            int offset = found ? fixed.start() : -1;
            boolean unique = found && !fixed.find() && !field.find();
            boolean supported = matchingRule && exactSegment && !conflictingSegment && nextFound && unique && value.equals(observed);
            ObjectNode row = assertions.addObject();
            row.put("ruleId", id);
            row.put("claimScope", "PARTIAL_FIXED_PROMPT_CODE");
            row.put("sourceSection", section);
            row.put("sourceFile", "docs/specs/extracted_text.txt");
            row.put("sourceFingerprintSha256", body.isEmpty() ? "" : HexFormat.of().formatHex(
                MessageDigest.getInstance("SHA-256").digest(body.getBytes(StandardCharsets.UTF_8))));
            row.put("sourceQuote", supported ? quote : "");
            row.put("sourceLine", supported ? location.orElseThrow().line()
                + body.substring(0, start + offset).chars().filter(character -> character == '\n').count() : -1);
            row.put("status", supported ? "PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED" : "SOURCE_MISMATCH_REVIEW_REQUIRED");
            if (supported) backed++;
        }
        if (!candidates.equals(Set.of("SEG105-R-005", "SEG119-R-012"))) {
            throw new IllegalStateException("Both Totals Prompt Code rule IDs are required");
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-TOTALS-PROMPT-CODE-EVIDENCE");
        output.put("specificationVersion", version);
        output.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        output.put("policy", "Field-5 fixed-value evidence is partial, not Totals workflow approval or executable test data.");
        ObjectNode summary = output.putObject("summary");
        summary.put("catalogRuleCount", rules.size());
        summary.put("candidateRuleCount", candidates.size());
        summary.put("partiallyBackedRuleCount", backed);
        summary.put("sourceMismatchCount", candidates.size() - backed);
        summary.put("draftCaseCount", 0);
        summary.put("smeApprovedRuleCount", approval.path("smeApprovedRuleCount").asInt());
        output.set("assertions", assertions);
        output.set("draftCases", mapper.createArrayNode());
        Files.createDirectories(review);
        mapper.writerWithDefaultPrettyPrinter().writeValue(review.resolve("atl105-totals-prompt-code-evidence.json").toFile(), output);
        Files.writeString(review.resolve("atl105-totals-prompt-code-evidence.md"), "# Totals Prompt Code Source Evidence\n\n"
            + "Curated: 2; partially backed: " + backed + "; mismatches: " + (2 - backed)
            + "; SME-approved: " + summary.path("smeApprovedRuleCount").asInt()
            + ".\n\nFixed Field 5 value only; no Totals workflow or execution certification.\n");
        System.out.printf("totalsPromptCandidates=2 backed=%d mismatches=%d%n", backed, 2 - backed);
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
}