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

/** Checks curated Chapter 13 phrases within their individual element definitions only. */
public final class GenerateAtl105Chapter13RuleEvidence {
    private GenerateAtl105Chapter13RuleEvidence() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode manifest = mapper.readTree(pack.resolve("contract/chapter-13-rule-evidence.json").toFile());
        JsonNode coverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode approval = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json").toFile());
        String source = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));
        String version = manifest.path("specificationVersion").asText();
        if (version.isBlank() || !version.equals(coverage.path("specificationVersion").asText())
            || !version.equals(approval.path("specificationVersion").asText())
            || !source.contains("Specification Release Version:    " + version)) {
            throw new IllegalStateException("Chapter 13 source version mismatch");
        }
        Map<String, JsonNode> rules = new HashMap<>();
        for (JsonNode rule : coverage.path("businessRequirements")) {
            String id = rule.path("id").asText();
            if (id.isBlank() || rules.putIfAbsent(id, rule) != null) throw new IllegalStateException("Duplicate catalog rule " + id);
        }
        Set<String> approvals = new HashSet<>();
        for (JsonNode row : approval.path("rows")) {
            if (!approvals.add(row.path("ruleId").asText())) throw new IllegalStateException("Duplicate approval rule");
        }
        if (rules.isEmpty() || rules.size() != approval.path("oracleRuleCount").asInt(-1)
            || !rules.keySet().equals(approvals)) throw new IllegalStateException("Chapter 13 catalog and approval mismatch");

        ArrayNode assertions = mapper.createArrayNode();
        Set<String> curated = new HashSet<>();
        int matched = 0;
        for (JsonNode candidate : manifest.path("assertions")) {
            String ruleId = candidate.path("ruleId").asText();
            JsonNode rule = rules.get(ruleId);
            if (rule == null || !curated.add(ruleId)) throw new IllegalStateException("Unknown or duplicate Chapter 13 rule " + ruleId);
            JsonNode anchor = rule.path("sourceAnchor");
            String segment = candidate.path("segment").asText();
            JsonNode elements = candidate.path("elements");
            Set<String> anchoredElements = Set.of(anchor.path("element").asText().split(","));
            if (!"PARTIAL_CHAPTER_13_FIELD_DEFINITIONS".equals(candidate.path("claimScope").asText())
                || !"ATL105".equals(anchor.path("specification").asText()) || !version.equals(anchor.path("version").asText())
                || !"13.2".equals(anchor.path("section").asText()) || !segment.equals(anchor.path("segment").asText())
                || !rule.path("sourceEvidenceVerified").asBoolean() || !elements.isArray() || elements.isEmpty()) {
                throw new IllegalStateException("Invalid Chapter 13 source anchor for " + ruleId);
            }
            Set<String> declaredElements = new HashSet<>();
            ArrayNode quotes = mapper.createArrayNode();
            boolean backed = true;
            for (JsonNode element : elements) {
                String number = element.path("element").asText();
                String heading = element.path("sourceHeading").asText();
                String endHeading = element.path("endHeading").asText();
                JsonNode phrases = element.path("phrases");
                if (!anchoredElements.contains(number) || !declaredElements.add(number)
                    || !heading.matches("Number:\\s*" + Pattern.quote(number) + "\\s+Name:.*")
                    || endHeading.isBlank() || !phrases.isArray() || phrases.isEmpty()) {
                    throw new IllegalStateException("Invalid Chapter 13 element assertion " + number);
                }
                int start = source.indexOf(heading);
                int end = start < 0 ? -1 : source.indexOf(endHeading, start + heading.length());
                if (start < 0 || end <= start) throw new IllegalStateException("Chapter 13 element bounds missing: " + number);
                String body = source.substring(start, end);
                for (JsonNode phraseNode : phrases) {
                    String phrase = phraseNode.asText();
                    if (phrase.isBlank()) throw new IllegalStateException("Blank Chapter 13 phrase for " + number);
                    Matcher match = literal(phrase).matcher(body);
                    boolean found = match.find();
                    backed &= found;
                    ObjectNode quote = quotes.addObject();
                    quote.put("element", number);
                    quote.put("sourceQuote", found ? match.group().replaceAll("\\s+", " ").trim() : phrase);
                    quote.put("sourceLine", found ? 1 + source.substring(0, start + match.start()).chars().filter(character -> character == '\n').count() : -1);
                    quote.put("definitionFingerprintSha256", HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                        .digest(body.getBytes(StandardCharsets.UTF_8))));
                }
            }
            if (!declaredElements.equals(anchoredElements)) throw new IllegalStateException("Unaccounted Chapter 13 element for " + ruleId);
            ObjectNode assertion = assertions.addObject();
            assertion.put("ruleId", ruleId);
            assertion.put("claimScope", "PARTIAL_CHAPTER_13_FIELD_DEFINITIONS");
            assertion.put("sourceFile", "docs/specs/extracted_text.txt");
            assertion.put("sourceSection", "13.2");
            assertion.put("status", backed ? "PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED" : "SOURCE_MISMATCH_REVIEW_REQUIRED");
            assertion.set("quotes", quotes);
            if (backed) matched++;
        }
        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "ATL105-CHAPTER-13-RULE-EVIDENCE");
        result.put("specificationVersion", version);
        result.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        result.put("policy", "Matched definition phrases are partial source evidence, not complete BR semantics, executable cases, or SME approval.");
        result.putObject("summary").put("catalogRuleCount", rules.size()).put("curatedRuleCount", curated.size())
            .put("partiallyBackedRuleCount", matched).put("sourceMismatchCount", curated.size() - matched)
            .put("draftCaseCount", 0).put("smeApprovedRuleCount", approval.path("smeApprovedRuleCount").asInt());
        result.set("assertions", assertions);
        result.set("draftCases", mapper.createArrayNode());
        Path directory = pack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(directory);
        mapper.writerWithDefaultPrettyPrinter().writeValue(directory.resolve("atl105-chapter-13-rule-evidence.json").toFile(), result);
        System.out.printf("chapter13 curated=%d backed=%d mismatches=%d%n", curated.size(), matched, curated.size() - matched);
    }

    private static Pattern literal(String phrase) {
        StringBuilder expression = new StringBuilder();
        for (String word : phrase.trim().split("\\s+")) {
            if (!expression.isEmpty()) expression.append("\\s+");
            expression.append(Pattern.quote(word));
        }
        return Pattern.compile(expression.toString());
    }
}