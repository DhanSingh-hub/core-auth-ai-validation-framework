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
import java.util.HexFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Checks only the positional shape of Element 4, not external city, state or ZIP validity. */
public final class GenerateAtl105Element4LayoutGate {
    private GenerateAtl105Element4LayoutGate() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode manifest = mapper.readTree(pack.resolve("contract/element-4-layout-assertions.json").toFile());
        JsonNode coverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode approval = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json").toFile());
        String source = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));
        String version = manifest.path("specificationVersion").asText();
        String ruleId = manifest.path("ruleId").asText();
        if (version.isBlank() || !version.equals(coverage.path("specificationVersion").asText())
            || !version.equals(approval.path("specificationVersion").asText())
            || !source.contains("Specification Release Version:    " + version)) {
            throw new IllegalStateException("Element 4 source version mismatch");
        }
        int start = source.indexOf(manifest.path("sourceHeading").asText());
        int end = start < 0 ? -1 : source.indexOf(manifest.path("endHeading").asText(), start + 1);
        if (start < 0 || end <= start) throw new IllegalStateException("Element 4 source bounds missing");
        String definition = source.substring(start, end);
        JsonNode catalogRule = null;
        int count = 0;
        for (JsonNode rule : coverage.path("businessRequirements")) {
            count++;
            if (ruleId.equals(rule.path("id").asText())) catalogRule = rule;
        }
        if (count == 0 || count != approval.path("oracleRuleCount").asInt(-1) || catalogRule == null) {
            throw new IllegalStateException("Element 4 catalog denominator or rule mismatch");
        }
        JsonNode anchor = catalogRule.path("sourceAnchor");
        if (!"SEGDL1-R-010".equals(ruleId) || !"PARTIAL_POSITIONAL_LAYOUT".equals(manifest.path("claimScope").asText())
            || !"ATL105".equals(anchor.path("specification").asText()) || !version.equals(anchor.path("version").asText())
            || !"13.2".equals(anchor.path("section").asText()) || !"DL1".equals(anchor.path("segment").asText())
            || !"4".equals(anchor.path("element").asText()) || !catalogRule.path("sourceEvidenceVerified").asBoolean()) {
            throw new IllegalStateException("Element 4 assertion is not anchored to the catalog");
        }
        String[] positions = {"1–12", "13", "14–15", "16", "17–21"};
        JsonNode evidence = manifest.path("layoutEvidence");
        if (!evidence.isArray() || evidence.size() != positions.length) {
            throw new IllegalStateException("Element 4 layout evidence must contain five positional rows");
        }
        boolean backed = contains(definition, manifest.path("shapeEvidence").asText())
            && contains(definition, "Representation: Fixed length of 21 alphanumeric characters")
            && contains(definition, "Used in Data Segment No. DL1, Merchant Data Segment.");
        ArrayNode assertions = mapper.createArrayNode();
        for (int index = 0; index < positions.length; index++) {
            String phrase = evidence.get(index).asText();
            if (!phrase.startsWith(positions[index] + " ") || phrase.isBlank()) {
                throw new IllegalStateException("Invalid Element 4 positional row " + index);
            }
            Matcher match = literal(phrase).matcher(definition);
            boolean found = match.find();
            backed &= found;
            ObjectNode row = assertions.addObject();
            row.put("ruleId", ruleId);
            row.put("claimScope", "PARTIAL_POSITIONAL_LAYOUT");
            row.put("position", positions[index]);
            row.put("sourceFile", "docs/specs/extracted_text.txt");
            row.put("sourceSection", "13.2");
            row.put("sourceQuote", found ? match.group().replaceAll("\\s+", " ").trim() : phrase);
            row.put("sourceLine", found ? 1 + source.substring(0, start + match.start()).chars().filter(character -> character == '\n').count() : -1);
            row.put("sourceFingerprintSha256", HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(definition.getBytes(StandardCharsets.UTF_8))));
        }
        ArrayNode drafts = mapper.createArrayNode();
        for (JsonNode assertion : assertions) {
            ((ObjectNode) assertion).put("status", backed ? "PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED" : "SOURCE_MISMATCH_REVIEW_REQUIRED");
        }
        if (backed) {
            addDraft(drafts, ruleId, "CITY         NY 12345", "VALID_POSITIONAL_SHAPE", "POSITIVE_LAYOUT");
            addDraft(drafts, ruleId, "CITY         NYX12345", "INVALID_POSITION_16", "NEGATIVE_SEPARATOR");
        }
        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "ATL105-ELEMENT-4-LAYOUT-GATE");
        result.put("specificationVersion", version);
        result.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        result.put("policy", "Structural examples do not verify assigned city, Appendix D state or ZIP validity, DL1 parsing, approval or execution.");
        result.putObject("summary").put("catalogRuleCount", count).put("partiallyBackedRuleCount", backed ? 1 : 0)
            .put("sourceMismatchCount", backed ? 0 : 1).put("draftCaseCount", drafts.size())
            .put("smeApprovedRuleCount", approval.path("smeApprovedRuleCount").asInt());
        result.set("assertions", assertions);
        result.set("draftCases", drafts);
        Path directory = pack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(directory);
        mapper.writerWithDefaultPrettyPrinter().writeValue(directory.resolve("atl105-element-4-layout-gate.json").toFile(), result);
        System.out.printf("element4 backed=%d drafts=%d%n", backed ? 1 : 0, drafts.size());
    }

    private static boolean contains(String source, String text) {
        return !text.isBlank() && literal(text).matcher(source).find();
    }

    private static Pattern literal(String text) {
        StringBuilder expression = new StringBuilder();
        for (String word : text.trim().split("\\s+")) {
            if (!expression.isEmpty()) expression.append("\\s+");
            expression.append(Pattern.quote(word));
        }
        return Pattern.compile(expression.toString());
    }

    private static void addDraft(ArrayNode drafts, String ruleId, String value, String outcome, String type) {
        drafts.addObject().put("id", "DRAFT-" + ruleId + "-" + type).put("ruleId", ruleId)
            .put("element", "4").put("value", value).put("expectedOutcome", outcome)
            .put("status", "PARTIAL_SOURCE_DERIVED_REVIEW_REQUIRED").put("executionReady", false);
    }
}