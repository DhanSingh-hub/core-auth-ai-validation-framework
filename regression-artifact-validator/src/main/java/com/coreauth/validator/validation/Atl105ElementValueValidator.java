package com.coreauth.validator.validation;

import com.coreauth.validator.coverage.Atl105ElementInventory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class Atl105ElementValueValidator {
    public enum Status { CHECKS_PASSED, INVALID, REVIEW_REQUIRED }
    public record Finding(String element, Status status, String ruleId, String reason) { }
    public record Result(Status status, List<Finding> findings) { }
    public static final String ANY_FAMILY = "ANY";
    private static final Pattern DIGITS = Pattern.compile("[0-9]+");
    private final JsonNode inventory;
    private final JsonNode profiles;
    private final JsonNode templates;
    private final Map<String, Pattern> patterns = new HashMap<>();

    public Atl105ElementValueValidator(Path pack) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        inventory = new Atl105ElementInventory().extract(Files.readString(pack.resolve("docs/specs/extracted_text.txt")));
        profiles = mapper.readTree(pack.resolve("docs/specs/kb/elements/validation-profiles.json").toFile());
        templates = mapper.readTree(pack.resolve("docs/atl105_complete_templates.json").toFile()).path("message_templates");
        if (!"ATL105".equals(profiles.path("specification").asText())
            || !"2026-3".equals(profiles.path("specificationVersion").asText())
            || !profiles.path("profiles").isArray() || profiles.path("profiles").isEmpty()) {
            throw new IOException("Missing or unsupported element validation profile catalog");
        }
        Set<String> ids = new HashSet<>();
        for (JsonNode profile : profiles.path("profiles")) {
            String profileId = profile.path("id").asText();
            if (profileId.isBlank() || !ids.add(profileId)) throw new IOException("Profile id missing or duplicated: " + profileId);
            JsonNode definitions = inventory.path("elements").path(profile.path("element").asText()).path("definitions");
            if (!inSource(definitions, profile.path("sourceEvidence").asText("")) || profile.path("existingRuleId").asText("").isBlank()) {
                throw new IOException("Profile lacks element-local source evidence or existing rule identity: " + profileId);
            }
            if (ANY_FAMILY.equals(profile.path("messageFamily").asText())
                && !inSource(definitions, profile.path("familyIndependentEvidence").asText(""))) {
                throw new IOException("Family-independent profile lacks element-local evidence: " + profileId);
            }
            JsonNode range = profile.path("numericRange");
            if (!range.isMissingNode() && (!range.path("min").canConvertToLong() || !range.path("max").canConvertToLong()
                || range.path("min").asLong() > range.path("max").asLong())) throw new IOException("Invalid numeric range: " + profileId);
            String equals = profile.path("equalsObservation").asText("segment");
            if (!equals.equals("segment") && !equals.startsWith("qualifiers.")) throw new IOException("Unsupported observation reference: " + profileId);
            Path kb = pack.resolve("docs/specs/kb").toAbsolutePath().normalize();
            Path ruleFile = kb.resolve(profile.path("existingRuleCatalog").asText("")).normalize();
            if (!ruleFile.startsWith(kb) || !Files.isRegularFile(ruleFile)) throw new IOException("Profile catalog path is invalid");
            JsonNode catalog = mapper.readTree(ruleFile.toFile());
            boolean ruleExists = false;
            for (JsonNode existing : catalog.path("rules")) {
                if (profile.path("existingRuleId").asText().equals(existing.path("ruleId").asText())
                    && "2026-3".equals(existing.path("sourceAnchor").path("version").asText())
                    && Arrays.stream(existing.path("sourceAnchor").path("element").asText().split("[,|]"))
                        .map(String::trim).anyMatch(profile.path("element").asText()::equals)) ruleExists = true;
            }
            if (!ruleExists) throw new IOException("Profile does not reuse a matching existing element rule: " + profileId);
            patterns.put(profileId, Pattern.compile(profile.path("pattern").asText()));
        }
    }

    private static boolean inSource(JsonNode definitions, String evidence) {
        if (evidence.isBlank()) return false;
        for (JsonNode definition : definitions) if (definition.path("sourceText").asText().contains(evidence)) return true;
        return false;
    }

    public Result validate(JsonNode observation) {
        List<Finding> findings = new ArrayList<>();
        if (observation == null || !observation.isObject() || !observation.path("elements").isObject()
            || !"2026-3".equals(observation.path("specificationVersion").asText())
            || observation.path("messageFamily").asText("").isBlank()
            || observation.path("segment").asText("").isBlank()) {
            return new Result(Status.REVIEW_REQUIRED, List.of(new Finding("", Status.REVIEW_REQUIRED, "",
                "Explicit version, message family, segment and element-number-keyed values are required")));
        }
        membership(observation, findings);
        Set<String> checked = new HashSet<>();
        for (JsonNode profile : profiles.path("profiles")) {
            if (!matches(profile, observation)) continue;
            String id = profile.path("element").asText();
            JsonNode value = observation.path("elements").get(id);
            if (value == null && !profile.path("required").asBoolean()) continue;
            checked.add(id);
            findings.add(check(profile, value, observation));
        }
        var fields = observation.path("elements").fields();
        while (fields.hasNext()) {
            var field = fields.next();
            if (!checked.contains(field.getKey())) findings.add(baseline(field.getKey(), field.getValue()));
        }
        Status status = findings.stream().anyMatch(item -> item.status() == Status.INVALID) ? Status.INVALID
            : findings.isEmpty() || findings.stream().anyMatch(item -> item.status() == Status.REVIEW_REQUIRED)
                ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED;
        return new Result(status, List.copyOf(findings));
    }

    private Finding check(JsonNode profile, JsonNode value, JsonNode observation) {
        String id = profile.path("element").asText();
        String profileId = profile.path("id").asText();
        String ruleId = profile.path("existingRuleId").asText();
        if (value == null && "PARTIAL".equals(observation.path("completeness").asText()))
            return new Finding(id, Status.REVIEW_REQUIRED, ruleId, "Required element not supplied in partial observation for profile " + profileId);
        if (value == null || !value.isTextual()) return new Finding(id, Status.INVALID, ruleId, "Required element missing or not text under profile " + profileId);
        String text = value.asText();
        if (!patterns.get(profileId).matcher(text).matches()) return new Finding(id, Status.INVALID, ruleId, "Value shape violates profile " + profileId);
        JsonNode range = profile.path("numericRange");
        if (!range.isMissingNode()) {
            if (!DIGITS.matcher(text).matches() || text.length() > 18)
                return new Finding(id, Status.INVALID, ruleId, "Non-numeric value for range profile " + profileId);
            long number = Long.parseLong(text);
            if (number < range.path("min").asLong() || number > range.path("max").asLong())
                return new Finding(id, Status.INVALID, ruleId, "Value outside source range of profile " + profileId);
        }
        if (profile.has("equalsObservation")) {
            String reference = profile.path("equalsObservation").asText();
            JsonNode expected = reference.equals("segment") ? observation.path("segment")
                : observation.path("qualifiers").path(reference.substring("qualifiers.".length()));
            if (!expected.isValueNode() || expected.asText().isBlank())
                return new Finding(id, Status.REVIEW_REQUIRED, ruleId, "Combination context " + reference + " required by profile " + profileId);
            boolean same = DIGITS.matcher(text).matches() && DIGITS.matcher(expected.asText()).matches()
                && text.length() <= 18 && expected.asText().length() <= 18
                ? Long.parseLong(text) == Long.parseLong(expected.asText()) : text.equals(expected.asText());
            if (!same) return new Finding(id, Status.INVALID, ruleId, "Value does not equal " + reference + " under profile " + profileId);
        }
        return new Finding(id, Status.CHECKS_PASSED, ruleId, "Verified profile checks passed; not business approval");
    }

    private Finding baseline(String id, JsonNode value) {
        JsonNode element = inventory.path("elements").get(id);
        if (element == null) return new Finding(id, Status.REVIEW_REQUIRED, "", "Element identity not found in Chapter 13; segment-scoped source review required");
        if (element.path("definitions").size() != 1) return new Finding(id, Status.REVIEW_REQUIRED, "", "Multiple Chapter 13 definitions; context-specific review required");
        JsonNode constraint = element.path("definitions").path(0).path("constraints");
        String source = "Chapter 13 Element " + id + " (line " + element.path("definitions").path(0).path("sourceLine").asText() + ")";
        if (!"EXTRACTED".equals(constraint.path("extractionStatus").asText())) return new Finding(id, Status.REVIEW_REQUIRED, "", source + " type/length not machine-extracted");
        if (value == null || !value.isTextual()) return new Finding(id, Status.REVIEW_REQUIRED, "", "Non-text value; representation review required");
        String text = value.asText();
        if (text.isEmpty()) return new Finding(id, Status.REVIEW_REQUIRED, "", "Empty value; omission semantics require context");
        if (text.length() > constraint.path("maxLength").asInt()) return new Finding(id, Status.INVALID, "", "Exceeds maximum length of " + source);
        if ("N".equals(constraint.path("characterType").asText()) && !DIGITS.matcher(text).matches())
            return new Finding(id, Status.INVALID, "", "Non-digit value for numeric " + source);
        if ("FIXED".equals(constraint.path("lengthMode").asText()) && text.length() < constraint.path("maxLength").asInt())
            return new Finding(id, Status.REVIEW_REQUIRED, "", "Shorter than fixed length of " + source + "; padding convention requires review");
        return new Finding(id, Status.REVIEW_REQUIRED, "", "Baseline type/length of " + source + " passed; no verified contextual profile");
    }

    private void membership(JsonNode observation, List<Finding> findings) {
        String family = observation.path("messageFamily").asText();
        String segment = observation.path("segment").asText();
        JsonNode template = templates.get(family);
        if (template == null) {
            findings.add(new Finding("", Status.REVIEW_REQUIRED, "", "No Section 11 template for message family; segment membership not verified"));
            return;
        }
        Set<String> allowed = new HashSet<>();
        Set<String> required = new HashSet<>();
        for (JsonNode listed : template.path("segments")) {
            allowed.add(listed.path("segment_number").asText());
            if (Set.of("R", "PRESENT").contains(listed.path("presence_in_message").asText())) required.add(listed.path("segment_number").asText());
        }
        JsonNode extensions = template.path("approved_extensions");
        if (extensions.isObject()) allowed.add(extensions.path("segment_number").asText());
        for (JsonNode extension : extensions) if (extension.isObject()) allowed.add(extension.path("segment_number").asText());
        String section = "Section " + template.path("source_section").asText() + " layout (source-derived template)";
        if (!segment.equals("MESSAGE")) {
            if (!allowed.contains(segment)) findings.add(new Finding("", Status.REVIEW_REQUIRED, "", "Segment " + segment + " not listed in " + section));
            return;
        }
        if (!observation.path("segmentsPresent").isArray()) return;
        Set<String> present = new HashSet<>();
        observation.path("segmentsPresent").forEach(item -> present.add(item.asText()));
        for (String missing : required) if (!present.contains(missing))
            findings.add(new Finding("", Status.INVALID, "", "Required Segment " + missing + " absent per " + section));
        for (String extra : present) if (!allowed.contains(extra))
            findings.add(new Finding("", Status.REVIEW_REQUIRED, "", "Segment " + extra + " not listed in " + section));
    }

    private static boolean matches(JsonNode profile, JsonNode observation) {
        String family = profile.path("messageFamily").asText();
        if (!(family.equals(ANY_FAMILY) || family.equals(observation.path("messageFamily").asText()))
            || !profile.path("segment").asText().equals(observation.path("segment").asText())) return false;
        var conditions = profile.path("qualifiers").fields();
        while (conditions.hasNext()) {
            var condition = conditions.next();
            if (!condition.getValue().equals(observation.path("qualifiers").get(condition.getKey()))) return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("Usage: Atl105ElementValueValidator <ATL105-pack> <observation.json> <report.json>");
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Atl105ElementValueValidator(Path.of(args[0])).validate(mapper.readTree(Path.of(args[1]).toFile()));
        Path output = Path.of(args[2]);
        Files.createDirectories(output.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), result);
        System.out.println("Element validation: " + result.status());
        if (result.status() != Status.CHECKS_PASSED) System.exit(1);
    }
}