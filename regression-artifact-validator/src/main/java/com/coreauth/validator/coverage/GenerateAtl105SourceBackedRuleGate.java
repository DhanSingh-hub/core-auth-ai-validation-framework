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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Separates a resolvable citation from an exact source-backed assertion and draft test examples. */
public final class GenerateAtl105SourceBackedRuleGate {
    private GenerateAtl105SourceBackedRuleGate() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode manifest = mapper.readTree(pack.resolve("contract/element-83-source-assertions.json").toFile());
        JsonNode coverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode approval = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json").toFile());
        String specification = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));
        String heading = manifest.path("sourceHeading").asText();
        int start = specification.indexOf(heading);
        int end = start < 0 ? -1 : specification.indexOf(manifest.path("endHeading").asText(), start + heading.length());
        if (start < 0 || end <= start) throw new IllegalStateException("Element 83 source bounds missing or ambiguous");
        String section = specification.substring(start, end);
        String fingerprint = sha256(section);
        String shapeEvidence = manifest.path("shapeEvidence").asText();
        boolean shapeVerified = !shapeEvidence.isBlank()
            && literalWithFlexibleWhitespace(shapeEvidence).matcher(section).find()
            && section.contains("Fixed length of one alphanumeric character");
        String version = manifest.path("specificationVersion").asText();
        if (version.isBlank() || !specification.contains("Specification Release Version:    " + version)) {
            throw new IllegalStateException("Source specification version does not match assertion manifest");
        }

        Map<String, JsonNode> rules = new TreeMap<>();
        int citations = 0;
        for (JsonNode rule : coverage.path("businessRequirements")) {
            if (rules.putIfAbsent(rule.path("id").asText(), rule) != null) throw new IllegalStateException("Duplicate rule ID in BR coverage");
            if (rule.path("sourceEvidenceVerified").asBoolean()) citations++;
        }
        if (rules.isEmpty() || approval.path("oracleRuleCount").asInt(-1) != rules.size()) {
            throw new IllegalStateException("BR coverage and approval denominators differ");
        }
        Map<String, JsonNode> approvalRows = new TreeMap<>();
        for (JsonNode row : approval.path("rows")) approvalRows.put(row.path("ruleId").asText(), row);
        if (!approvalRows.keySet().equals(rules.keySet())) throw new IllegalStateException("Approval matrix does not match catalog rule IDs");
        ArrayNode assertions = mapper.createArrayNode();
        ArrayNode cases = mapper.createArrayNode();
        Set<String> backedRules = new HashSet<>();
        Set<String> partialRules = new HashSet<>();
        Set<String> mappedRules = new HashSet<>();
        Set<String> keys = new HashSet<>();
        Map<String, Integer> curatedCounts = new TreeMap<>();
        Map<String, Integer> matchedCounts = new TreeMap<>();
        int mismatches = 0;
        for (JsonNode candidate : manifest.path("assertions")) {
            String ruleId = candidate.path("ruleId").asText();
            String context = candidate.path("context").asText();
            String code = candidate.path("code").asText();
            String expected = candidate.path("expected").asText();
            String phrase = candidate.path("sourcePhrase").asText();
            String contextSection = candidate.path("contextSection").asText();
            String contextPhrase = candidate.path("contextPhrase").asText();
            boolean partial = "PARTIAL_RULE_CONTEXT".equals(candidate.path("claimScope").asText());
            if (ruleId.isBlank() || context.isBlank() || phrase.isBlank() || code.length() != 1 || !keys.add(ruleId + "|" + context + "|" + code)) {
                throw new IllegalStateException("Invalid or duplicate curated assertion: " + candidate);
            }
            if (partial != !contextSection.isBlank() || partial != !contextPhrase.isBlank()) {
                throw new IllegalStateException("Partial assertions require both contextSection and contextPhrase: " + candidate);
            }
            JsonNode rule = rules.get(ruleId);
            if (rule == null) throw new IllegalStateException("Assertion cites unknown catalog rule: " + ruleId);
            mappedRules.add(ruleId);
            curatedCounts.merge(ruleId, 1, Integer::sum);
            JsonNode anchor = rule.path("sourceAnchor");
            String[] anchorSections = anchor.path("section").asText().split(",");
            boolean matchingAnchor = version.equals(anchor.path("version").asText())
                && "ATL105".equals(anchor.path("specification").asText())
                && (Set.of(anchorSections).contains("13.2") || Set.of(anchorSections).contains("Chapter13-Element83")
                    || (partial && Set.of(anchorSections).contains(contextSection)))
                && Set.of(anchor.path("element").asText().split(",")).contains("83");
            Matcher match = literalWithFlexibleWhitespace(phrase).matcher(section);
            String contextBody = partial ? contextBody(specification, contextSection) : "";
            Matcher contextMatch = literalWithFlexibleWhitespace(contextPhrase).matcher(contextBody);
            boolean contextBacked = !partial || (Set.of(anchorSections).contains(contextSection)
                && contextMatch.find());
            boolean matchingClaim = phrase.startsWith(code + " ") && phrase.toUpperCase(Locale.ROOT).contains(expected);
            boolean backed = shapeVerified && matchingAnchor && matchingClaim && contextBacked
                && rule.path("sourceEvidenceVerified").asBoolean() && match.find();
            ObjectNode row = assertions.addObject();
            row.put("ruleId", ruleId);
            row.put("context", context);
            row.put("code", code);
            row.put("expected", expected);
            row.put("specificationVersion", version);
            row.put("sourceFile", "docs/specs/extracted_text.txt");
            row.put("sourceSection", anchor.path("section").asText());
            row.put("sourceEntry", heading);
            row.put("sourceFingerprintSha256", fingerprint);
            row.put("claimScope", partial ? "PARTIAL_RULE_CONTEXT" : "CODE_VALUE_ONLY");
            if (partial) {
                row.put("contextSection", contextSection);
                row.put("contextQuote", contextBacked ? contextMatch.group().replaceAll("\\s+", " ").trim() : contextPhrase);
                row.put("contextFingerprintSha256", contextBody.isEmpty() ? "" : sha256(contextBody));
                row.put("contextLine", contextBacked ? Atl105SourceBodyLocator.find(specification, contextSection).orElseThrow().line()
                    + contextBody.substring(0, contextMatch.start()).chars().filter(character -> character == '\n').count() : -1);
            }
            row.put("sourceQuote", backed ? match.group().replaceAll("\\s+", " ").trim() : phrase);
            row.put("sourceLine", backed ? 1 + specification.substring(0, start + match.start()).chars().filter(character -> character == '\n').count() : -1);
            row.put("status", !backed ? "SOURCE_MISMATCH_REVIEW_REQUIRED"
                : partial ? "PARTIAL_SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED" : "SOURCE_BACKED_ASSERTION_REVIEW_REQUIRED");
            if (!backed) {
                mismatches++;
                continue;
            }
            backedRules.add(ruleId);
            if (partial) partialRules.add(ruleId);
            matchedCounts.merge(ruleId, 1, Integer::sum);
            addCase(cases, ruleId, context, code, expected, "POSITIVE_CONTEXT_VALUE", partial);
            addCase(cases, ruleId, context, code + code, "INVALID_LENGTH", "NEGATIVE_LENGTH", partial);
        }

        ArrayNode ruleStatuses = mapper.createArrayNode();
        int fullyLocatedRules = 0;
        int partiallyLocatedRules = 0;
        for (Map.Entry<String, JsonNode> entry : rules.entrySet()) {
            String ruleId = entry.getKey();
            JsonNode rule = entry.getValue();
            int curated = curatedCounts.getOrDefault(ruleId, 0);
            int matched = matchedCounts.getOrDefault(ruleId, 0);
            ObjectNode row = ruleStatuses.addObject();
            row.put("ruleId", ruleId);
            row.put("title", rule.path("title").asText());
            row.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
            row.put("sourceCatalog", rule.path("sourceCatalog").asText());
            row.put("citationResolved", rule.path("sourceEvidenceVerified").asBoolean());
            ArrayNode locations = row.putArray("sourceLocations");
            JsonNode sections = rule.path("sourceEvidenceSections");
            for (JsonNode token : sections) {
                ObjectNode location = locations.addObject();
                String sectionToken = token.asText();
                location.put("sectionToken", sectionToken);
                location.put("sourceFile", "docs/specs/extracted_text.txt");
                var found = Atl105SourceBodyLocator.find(specification, sectionToken);
                location.put("status", found.isPresent() ? "BODY_LOCATED_REFERENCE_ONLY" : "BODY_UNLOCATED_REVIEW_REQUIRED");
                if (found.isPresent()) {
                    var body = found.orElseThrow();
                    location.put("sourceHeading", body.heading());
                    location.put("sourceLine", body.line());
                    location.put("sourcePreview", body.preview());
                    location.put("previewSha256", sha256(body.preview()));
                }
            }
            int located = 0;
            for (JsonNode location : locations) if ("BODY_LOCATED_REFERENCE_ONLY".equals(location.path("status").asText())) located++;
            String locatorStatus = locations.isEmpty() || located == 0 ? "BODY_UNLOCATED_REVIEW_REQUIRED"
                : located == locations.size() ? "BODY_LOCATED_REFERENCE_ONLY" : "PARTIAL_BODY_LOCATION_REVIEW_REQUIRED";
            row.put("sourceLocatorStatus", locatorStatus);
            if ("BODY_LOCATED_REFERENCE_ONLY".equals(locatorStatus)) fullyLocatedRules++;
            else if ("PARTIAL_BODY_LOCATION_REVIEW_REQUIRED".equals(locatorStatus)) partiallyLocatedRules++;
            row.put("curatedAssertionCount", curated);
            row.put("sourceBackedAssertionCount", matched);
            row.put("draftCaseCount", 2 * matched);
            row.put("smeApproved", "SME_APPROVED".equals(approvalRows.get(ruleId).path("semanticApprovalStatus").asText()));
            row.put("status", !rule.path("sourceEvidenceVerified").asBoolean() ? "CITATION_UNRESOLVED_REVIEW_REQUIRED"
                : curated == 0 ? "ASSERTION_NOT_CURATED_REVIEW_REQUIRED"
                : matched != curated ? "ASSERTION_MISMATCH_REVIEW_REQUIRED"
                : partialRules.contains(ruleId) ? "PARTIAL_RULE_ASSERTIONS_REVIEW_REQUIRED" : "SOURCE_BACKED_ASSERTIONS_REVIEW_REQUIRED");
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-SOURCE-BACKED-RULE-GATE");
        output.put("specificationVersion", version);
        output.put("sourceFile", "docs/specs/extracted_text.txt");
        output.put("sourceFingerprintSha256", fingerprint);
        output.put("specificationFingerprintSha256", sha256(specification));
        output.put("shapeEvidenceVerified", shapeVerified);
        output.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        output.put("policy", "Citation resolution is not semantic approval. Draft cases are not fixture-backed or executable. Unmapped and mismatched rules require review; reused response codes are never globally prohibited.");
        ObjectNode summary = output.putObject("summary");
        summary.put("catalogRuleCount", rules.size());
        summary.put("citationResolvedRuleCount", citations);
        summary.put("bodyLocatedRuleCount", fullyLocatedRules);
        summary.put("partiallyLocatedRuleCount", partiallyLocatedRules);
        summary.put("bodyUnlocatedRuleCount", rules.size() - fullyLocatedRules - partiallyLocatedRules);
        summary.put("curatedRuleCount", mappedRules.size());
        summary.put("assertionSourceBackedRuleCount", backedRules.size());
        summary.put("partiallyBackedRuleCount", partialRules.size());
        summary.put("assertionsSourceBacked", assertions.size() - mismatches);
        summary.put("assertionsSourceMismatch", mismatches);
        summary.put("caseDerivedRuleCount", backedRules.size());
        summary.put("draftCaseCount", cases.size());
        summary.put("rulesWithoutCuratedAssertions", rules.size() - mappedRules.size());
        summary.put("smeApprovedRuleCount", approval.path("smeApprovedRuleCount").asInt());
        output.set("assertions", assertions);
        output.set("draftCases", cases);
        output.set("rules", ruleStatuses);
        Path directory = pack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(directory);
        mapper.writerWithDefaultPrettyPrinter().writeValue(directory.resolve("atl105-source-backed-rule-gate.json").toFile(), output);
        Files.writeString(directory.resolve("atl105-source-backed-rule-gate.md"), markdown(summary));
        writeReviewQueue(directory.resolve("atl105-source-backed-rule-review-queue.csv"), ruleStatuses);
        System.out.printf("rules=%d citationResolved=%d sourceBackedRules=%d draftCases=%d mismatches=%d SMEapproved=%d%n",
            rules.size(), citations, backedRules.size(), cases.size(), mismatches, summary.path("smeApprovedRuleCount").asInt());
    }

    private static Pattern literalWithFlexibleWhitespace(String phrase) {
        String[] words = phrase.trim().split("\\s+");
        StringBuilder expression = new StringBuilder();
        for (String word : words) {
            if (!expression.isEmpty()) expression.append("\\s+");
            expression.append(Pattern.quote(word));
        }
        return Pattern.compile(expression.toString());
    }

    private static String contextBody(String specification, String section) {
        var located = Atl105SourceBodyLocator.find(specification, section);
        if (located.isEmpty()) return "";
        int start = 0;
        for (int line = 1; line < located.orElseThrow().line(); line++) {
            int next = specification.indexOf('\n', start);
            if (next < 0) return "";
            start = next + 1;
        }
        Matcher nextHeading = Pattern.compile("(?m)^[ \\t]*\\d+(?:\\.\\d+)+[ \\t]+[^\\r\\n]+")
            .matcher(specification);
        if (!nextHeading.find(start + located.orElseThrow().heading().length())) return "";
        return specification.substring(start, nextHeading.start());
    }

    private static String sha256(String source) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(source.getBytes(StandardCharsets.UTF_8)));
    }

    private static void writeReviewQueue(Path output, ArrayNode rules) throws Exception {
        StringBuilder csv = new StringBuilder("ruleId,title,section,sourceLocatorStatus,sourceLines,assertionStatus,curatedAssertions,sourceBackedAssertions,draftCases,smeApproved\n");
        for (JsonNode rule : rules) {
            StringBuilder lines = new StringBuilder();
            for (JsonNode location : rule.path("sourceLocations")) {
                if (!lines.isEmpty()) lines.append(';');
                lines.append(location.path("sectionToken").asText()).append(':').append(location.path("sourceLine").asText("UNLOCATED"));
            }
            List<String> fields = List.of(rule.path("ruleId").asText(), rule.path("title").asText(),
                rule.path("sourceAnchor").path("section").asText(), rule.path("sourceLocatorStatus").asText(),
                lines.toString(), rule.path("status").asText(), rule.path("curatedAssertionCount").asText(),
                rule.path("sourceBackedAssertionCount").asText(), rule.path("draftCaseCount").asText(),
                rule.path("smeApproved").asText());
            csv.append(String.join(",", fields.stream().map(value -> "\"" + value.replace("\"", "\"\"") + "\"").toList())).append('\n');
        }
        Files.writeString(output, csv);
    }

    private static void addCase(ArrayNode cases, String ruleId, String context, String value, String outcome, String type, boolean partial) {
        ObjectNode testCase = cases.addObject();
        testCase.put("id", "DRAFT-" + ruleId + "-" + context + "-" + type);
        testCase.put("ruleId", ruleId);
        testCase.put("context", context);
        testCase.put("element", "83");
        testCase.put("value", value);
        testCase.put("expectedOutcome", outcome);
        testCase.put("caseType", type);
        testCase.put("claimScope", partial ? "PARTIAL_RULE_CONTEXT" : "CODE_VALUE_ONLY");
        testCase.put("status", partial ? "PARTIAL_SOURCE_DERIVED_REVIEW_REQUIRED" : "SOURCE_DERIVED_REVIEW_REQUIRED");
        testCase.put("executionReady", false);
    }

    private static String markdown(JsonNode summary) {
        return "# ATL105 Source-Backed Rule Gate\n\n"
            + "| Evidence level | Count |\n|---|---:|\n"
            + "| Catalogued rules | " + summary.path("catalogRuleCount").asInt() + " |\n"
            + "| Citations resolved (location only) | " + summary.path("citationResolvedRuleCount").asInt() + " |\n"
            + "| All cited body locations found (reference only) | " + summary.path("bodyLocatedRuleCount").asInt() + " |\n"
            + "| Some cited body locations found | " + summary.path("partiallyLocatedRuleCount").asInt() + " |\n"
            + "| Body location unresolved | " + summary.path("bodyUnlocatedRuleCount").asInt() + " |\n"
            + "| Curated Element 83 rules | " + summary.path("curatedRuleCount").asInt() + " |\n"
            + "| Rules with source-backed assertions (not full-rule proof) | " + summary.path("assertionSourceBackedRuleCount").asInt() + " |\n"
            + "| Rules with partial cross-section claims | " + summary.path("partiallyBackedRuleCount").asInt() + " |\n"
            + "| Source-backed assertions | " + summary.path("assertionsSourceBacked").asInt() + " |\n"
            + "| Source mismatches | " + summary.path("assertionsSourceMismatch").asInt() + " |\n"
            + "| Rules with derived cases | " + summary.path("caseDerivedRuleCount").asInt() + " |\n"
            + "| Draft cases | " + summary.path("draftCaseCount").asInt() + " |\n"
            + "| Rules without curated assertions | " + summary.path("rulesWithoutCuratedAssertions").asInt() + " |\n"
            + "| SME-approved rules | " + summary.path("smeApprovedRuleCount").asInt() + " |\n\n"
            + "Draft examples test a contextual code and the one-byte length constraint only. Cross-section claims are partial: they do not prove block lifecycle, complete host-discount behavior or transaction-wide applicability. A code valid in another context is not declared globally invalid. No draft case is an executable fixture or an approval.\n";
    }
}