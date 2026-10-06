package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Generates Element 84/85 profiles from Chapter 13 text plus existing catalog rules; conflicts go to review. */
public final class Atl105ElementProfileGenerator {
    private static final Pattern SEGMENT_REFERENCE = Pattern.compile("\\(No\\.\\s*(\\d{3})\\)");
    private static final Pattern LENGTH_ROW = Pattern.compile("(?s)(\\d+)\\s*[\\u2013-]\\s*(\\d+)\\s+([^()]*?)\\(No\\.\\s*(\\d{3})\\)");
    private static final Pattern WIDTH = Pattern.compile("\\b([34]) (?:numeric )?digits\\b");
    private static final String LENGTH_PURPOSE = "Identifies the length of the segment in which it appears.";
    private static final String TYPE_PURPOSE = "Identifies the type of segment being formatted";
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        Path file = pack.resolve("docs/specs/kb/elements/validation-profiles.json");
        ObjectNode document = (ObjectNode) mapper.readTree(file.toFile());
        ArrayNode profiles = JsonNodeFactory.instance.arrayNode();
        for (JsonNode profile : document.path("profiles")) if (!profile.path("generated").asBoolean()) profiles.add(profile);
        ArrayNode review = JsonNodeFactory.instance.arrayNode();
        List<ObjectNode> generated = new Atl105ElementProfileGenerator().generate(pack, review);
        profiles.addAll(generated);
        document.set("profiles", profiles);
        document.set("generationReview", review);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), document);
        System.out.printf("generatedProfiles=%d review=%d total=%d%n", generated.size(), review.size(), profiles.size());
    }

    public List<ObjectNode> generate(Path pack, ArrayNode review) throws IOException {
        JsonNode elements = new Atl105ElementInventory().extract(Files.readString(pack.resolve("docs/specs/extracted_text.txt"))).path("elements");
        List<ObjectNode> result = new ArrayList<>();
        lengthProfiles(pack, elements.path("84").path("definitions").path(0).path("sourceText").asText(), result, review);
        typeProfiles(pack, elements.path("85").path("definitions").path(0).path("sourceText").asText(), result, review);
        return result;
    }

    private void lengthProfiles(Path pack, String source, List<ObjectNode> result, ArrayNode review) throws IOException {
        int noteStart = source.indexOf("in the following");
        int noteEnd = source.indexOf("Purpose:", noteStart);
        int tableStart = source.indexOf("Valid Codes/Values:");
        if (noteStart < 0 || noteEnd < 0 || tableStart < 0) throw new IOException("Element 84 source layout changed");
        Set<String> fourDigit = new TreeSet<>();
        Matcher note = SEGMENT_REFERENCE.matcher(source.substring(noteStart, noteEnd));
        while (note.find()) fourDigit.add(note.group(1));
        if (fourDigit.size() != 7) throw new IOException("Element 84 four-digit segment list changed: " + fourDigit);
        Matcher row = LENGTH_ROW.matcher(source);
        row.region(tableStart, source.length());
        while (row.find()) {
            String segment = row.group(4);
            int width = fourDigit.contains(segment) ? 4 : 3;
            if (row.group(1).length() < 3) {
                review.add(item("84", segment, "Table range " + row.group(1) + "-" + row.group(2) + " conflicts with the three/four-digit rule"));
                continue;
            }
            List<JsonNode> rules = rules(pack, segment, "84");
            if (rules.isEmpty()) {
                review.add(item("84", segment, "No existing 2026-3 catalog rule anchored to Element 84"));
                continue;
            }
            String conflict = null;
            for (JsonNode rule : rules) {
                String title = rule.path("title").asText();
                Matcher stated = WIDTH.matcher(title);
                if (title.contains(" but ")) conflict = "Existing rule " + rule.path("ruleId").asText() + " records a conflicting length";
                else if (stated.find() && Integer.parseInt(stated.group(1)) != width)
                    conflict = "Existing rule " + rule.path("ruleId").asText() + " states " + stated.group(1) + " digits";
            }
            if (conflict != null) {
                review.add(item("84", segment, conflict));
                continue;
            }
            String max = row.group(2);
            JsonNode chosen = rules.stream().filter(rule -> rule.path("title").asText().replace(",", "").contains(max)).findFirst().orElse(rules.get(0));
            ObjectNode profile = base("E84-SEG" + segment + "-LENGTH", "84", segment, chosen,
                source.substring(row.start(1), row.end(2)), LENGTH_PURPOSE);
            profile.put("pattern", "^[0-9]{" + width + "}$");
            profile.putObject("numericRange").put("min", Long.parseLong(row.group(1))).put("max", Long.parseLong(max));
            profile.put("limitations", "Digit width and declared range only; equality with the actual encoded segment length is not checked.");
            result.add(profile);
        }
    }

    private void typeProfiles(Path pack, String source, List<ObjectNode> result, ArrayNode review) throws IOException {
        List<String> segments;
        try (var folders = Files.list(pack.resolve("docs/specs/kb"))) {
            segments = folders.map(path -> path.getFileName().toString()).filter(name -> name.matches("segment-\\d{3}"))
                .map(name -> name.substring("segment-".length())).sorted().toList();
        }
        for (String segment : segments) {
            Pattern fixed = Pattern.compile("(?i)segment type\\b.*?\\b" + segment + "\\b");
            JsonNode chosen = rules(pack, segment, "85").stream().filter(rule -> fixed.matcher(rule.path("title").asText()).find()).findFirst().orElse(null);
            if (chosen == null) {
                review.add(item("85", segment, "No existing 2026-3 catalog rule states the fixed Segment Type value"));
                continue;
            }
            String listed = source.contains("Data Segment No. " + segment) ? "Data Segment No. " + segment
                : source.contains("Data Segment No " + segment) ? "Data Segment No " + segment : null;
            ObjectNode profile = base("E85-SEG" + segment + "-TYPE", "85", segment, chosen,
                listed != null ? listed : "Representation: Fixed length of three digits", TYPE_PURPOSE);
            profile.put("pattern", "^[0-9]{3}$");
            profile.put("equalsObservation", "segment");
            profile.put("limitations", listed != null ? "Value equals the declared segment; placement and family membership are checked separately."
                : "Fixed value comes from the existing Chapter 12 catalog rule; segment is not listed in the Element 85 code table.");
            result.add(profile);
        }
    }

    private ObjectNode base(String id, String element, String segment, JsonNode rule, String evidence, String familyEvidence) {
        ObjectNode profile = JsonNodeFactory.instance.objectNode();
        profile.put("id", id);
        profile.put("element", element);
        profile.put("messageFamily", "ANY");
        profile.put("familyIndependentEvidence", familyEvidence);
        profile.put("segment", segment);
        profile.put("required", true);
        profile.put("existingRuleId", rule.path("ruleId").asText());
        profile.put("existingRuleCatalog", "segment-" + segment + "/coverage/segment-" + segment + "-rule-catalog.json");
        profile.putArray("sourceSections").add("13.2").add("12");
        profile.put("sourceEvidence", evidence);
        profile.put("generated", true);
        return profile;
    }

    private List<JsonNode> rules(Path pack, String segment, String element) throws IOException {
        Path catalog = pack.resolve("docs/specs/kb/segment-" + segment + "/coverage/segment-" + segment + "-rule-catalog.json");
        List<JsonNode> matches = new ArrayList<>();
        if (!Files.isRegularFile(catalog)) return matches;
        for (JsonNode rule : mapper.readTree(catalog.toFile()).path("rules")) {
            JsonNode anchor = rule.path("sourceAnchor");
            if ("2026-3".equals(anchor.path("version").asText())
                && Arrays.stream(anchor.path("element").asText().split("[,|]")).map(String::trim).anyMatch(element::equals)) matches.add(rule);
        }
        return matches;
    }

    private static ObjectNode item(String element, String segment, String reason) {
        ObjectNode item = JsonNodeFactory.instance.objectNode();
        item.put("element", element);
        item.put("segment", segment);
        item.put("status", "REVIEW_REQUIRED");
        item.put("reason", reason);
        return item;
    }
}
