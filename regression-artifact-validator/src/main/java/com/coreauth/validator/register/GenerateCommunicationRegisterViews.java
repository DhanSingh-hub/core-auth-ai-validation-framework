package com.coreauth.validator.register;

import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.coreauth.validator.register.CommunicationRegisterValidator.text;

/**
 * Generates every human-readable view of the ATL105 communication register: one SME/TBA input
 * register per segment (at its existing KB path), one view per channel, and an index.
 */
public final class GenerateCommunicationRegisterViews {
    public static final Path REGISTER = Atl105Paths.root().resolve(Path.of("registers", "atl105-communication-register.json"));
    public static final Path VIEWS = Atl105Paths.root().resolve(Path.of("registers", "views"));
    public static final Path KNOWLEDGE_BASE = Atl105Paths.docs().resolve(Path.of("specs", "kb"));

    private static final List<String> STATUS_ORDER = List.of(
            "OPEN", "REOPENED", "IN_DISCUSSION", "ANSWERED", "RESOLVED", "DEFERRED", "WITHDRAWN", "SUPERSEDED");
    private static final String DEFAULT_RESPONSE_FORMAT = "For each answer provide: `ID`, answer, source reference or "
            + "configuration owner, effective environment, approved date, and any exception. Use synthetic or masked values only.";

    public static void main(String[] args) throws IOException {
        JsonNode register = new ObjectMapper().readTree(REGISTER.toFile());
        ValidationResult result = new CommunicationRegisterValidator().validate(register, KNOWLEDGE_BASE);
        if (!result.isValid()) {
            result.errors().forEach(error -> System.err.println(error.reason()));
            System.exit(1);
        }
        Map<Path, String> views = new GenerateCommunicationRegisterViews().render(register);
        for (Map.Entry<Path, String> view : views.entrySet()) {
            Files.createDirectories(view.getKey().getParent());
            Files.writeString(view.getKey(), view.getValue(), StandardCharsets.UTF_8);
        }
        System.out.println("Wrote " + views.size() + " register views");
    }

    public Map<Path, String> render(JsonNode register) {
        Map<String, List<JsonNode>> byChannel = new LinkedHashMap<>();
        Map<String, List<JsonNode>> smeBySegment = new LinkedHashMap<>();
        register.path("segments").fieldNames().forEachRemaining(segment -> smeBySegment.put(segment, new ArrayList<>()));
        for (JsonNode item : register.path("items")) {
            byChannel.computeIfAbsent(text(item, "channel", ""), c -> new ArrayList<>()).add(item);
            if ("SME_QUERY".equals(text(item, "channel", ""))) {
                smeBySegment.computeIfAbsent(item.path("segments").path(0).asText(), s -> new ArrayList<>()).add(item);
            }
        }

        Map<Path, String> views = new LinkedHashMap<>();
        smeBySegment.forEach((segment, items) ->
                views.put(segmentView(segment), segmentRegister(segment, register.path("segments").path(segment), items)));
        views.put(VIEWS.resolve("index.md"), index(register, byChannel, smeBySegment));
        views.put(VIEWS.resolve("sme-queries.md"), smeQueries(smeBySegment));
        views.put(VIEWS.resolve("test-team-discussion.md"), channelView("Test Team Discussion Items",
                "Items the Test Team can decide internally: process, tooling, fixtures and validator policy.",
                byChannel.getOrDefault("TEST_TEAM", List.of()), VIEWS.resolve("test-team-discussion.md")));
        views.put(VIEWS.resolve("ai-dev-discussion.md"), channelView("Open Discussion Topics with the AI Developers",
                "Topics that need agreement between the Test Team and the AI Solution Team before either side changes its artifacts.",
                byChannel.getOrDefault("AI_DEV_DISCUSSION", List.of()), VIEWS.resolve("ai-dev-discussion.md")));
        views.put(VIEWS.resolve("ai-feedback.md"), channelView("Feedback to the AI Solution Team",
                "Confirmed defects, with evidence, that the AI Solution Team must correct. Each item stays open until a delivery shows it fixed.",
                byChannel.getOrDefault("AI_FEEDBACK", List.of()), VIEWS.resolve("ai-feedback.md")));
        return views;
    }

    static Path segmentView(String segment) {
        return KNOWLEDGE_BASE.resolve(Path.of("segment-" + segment, "segment-" + segment + "-sme-tba-input-register.md"));
    }

    private String segmentRegister(String segment, JsonNode meta, List<JsonNode> items) {
        Path self = segmentView(segment);
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(text(meta, "title", "Segment " + segment + " SME/TBA Input Register")).append("\n\n");
        sb.append(generatedNotice(self)).append("\n\n");
        if (!text(meta, "intro", "").isBlank()) {
            sb.append(text(meta, "intro", "")).append("\n\n");
        }
        sb.append("**Status:** ").append(statusSummary(items)).append("\n\n");
        if (!items.isEmpty()) {
            sb.append("| ID | Question | Why it is needed | Test impact | Status | Resolution | Catalog |\n");
            sb.append("| --- | --- | --- | --- | --- | --- | --- |\n");
            for (JsonNode item : items) {
                sb.append("| ").append(anchor(text(item, "id", ""))).append(text(item, "id", ""))
                        .append(" | ").append(cell(text(item, "subject", "")))
                        .append(" | ").append(cell(text(item, "context", "")))
                        .append(" | ").append(cell(text(item, "impact", "")))
                        .append(" | ").append(statusCell(item))
                        .append(" | ").append(cell(resolutionText(item)))
                        .append(" | ").append(cell(catalogLinks(item)))
                        .append(" |\n");
            }
            sb.append('\n');
        }
        String history = history(items);
        if (!history.isEmpty()) {
            sb.append("## History\n\n").append(history).append('\n');
        }
        for (JsonNode note : meta.path("notes")) {
            sb.append("## ").append(text(note, "heading", "Note")).append("\n\n").append(text(note, "text", "")).append("\n\n");
        }
        sb.append("## Response Format\n\n").append(text(meta, "responseFormat", DEFAULT_RESPONSE_FORMAT)).append('\n');
        return sb.toString();
    }

    private String index(JsonNode register, Map<String, List<JsonNode>> byChannel, Map<String, List<JsonNode>> smeBySegment) {
        Path self = VIEWS.resolve("index.md");
        StringBuilder sb = new StringBuilder("# ATL105 Communication Register Index\n\n");
        sb.append(generatedNotice(self)).append("\n\n");
        sb.append(text(register, "purpose", "")).append("\n\n");
        sb.append("## Channels\n\n| Channel | View | Use it when | ").append(String.join(" | ", STATUS_ORDER)).append(" | Total |\n");
        sb.append("| --- | --- | --- |").append(" --- |".repeat(STATUS_ORDER.size() + 1)).append('\n');
        Map<String, String> viewFor = Map.of("SME_QUERY", "sme-queries.md", "TEST_TEAM", "test-team-discussion.md",
                "AI_DEV_DISCUSSION", "ai-dev-discussion.md", "AI_FEEDBACK", "ai-feedback.md");
        register.path("channels").fields().forEachRemaining(channel -> {
            List<JsonNode> items = byChannel.getOrDefault(channel.getKey(), List.of());
            sb.append("| `").append(channel.getKey()).append("` | [").append(viewFor.get(channel.getKey())).append("](")
                    .append(viewFor.get(channel.getKey())).append(") | ").append(cell(text(channel.getValue(), "use", ""))).append(" |");
            for (String status : STATUS_ORDER) {
                sb.append(' ').append(count(items, status)).append(" |");
            }
            sb.append(' ').append(items.size()).append(" |\n");
        });
        sb.append("\n## SME/TBA Input Registers by Segment\n\n| Segment | Open | Reopened | Resolved | Deferred | Total | Register |\n");
        sb.append("| --- | --- | --- | --- | --- | --- | --- |\n");
        smeBySegment.forEach((segment, items) -> sb.append("| ").append(segment)
                .append(" | ").append(count(items, "OPEN")).append(" | ").append(count(items, "REOPENED"))
                .append(" | ").append(count(items, "RESOLVED")).append(" | ").append(count(items, "DEFERRED"))
                .append(" | ").append(items.size()).append(" | [segment-").append(segment).append("](")
                .append(link(self, segmentView(segment))).append(") |\n"));
        sb.append("\n## Related Stores\n\nThese hold decisions and generated work queues, not questions, and are not duplicated here:\n\n");
        register.path("relatedStores").fields().forEachRemaining(store -> sb.append("- `").append(store.getKey()).append("`: [")
                .append(store.getValue().asText()).append("](").append(link(self, Path.of(store.getValue().asText()))).append(")\n"));
        sb.append("\n## Updating the Register\n\n");
        sb.append("1. Edit `").append(REGISTER.toString().replace('\\', '/')).append("`: add or update an item, never delete one (use `WITHDRAWN` or `SUPERSEDED`).\n");
        sb.append("2. Keep catalog `provisionalItems` statuses equal to the linked `SME_QUERY` status.\n");
        sb.append("3. Run `GenerateCommunicationRegisterViews` to regenerate this index, the channel views and every per-segment SME/TBA register.\n");
        sb.append("4. `CommunicationRegisterTest` fails if the register is inconsistent or any view is out of date.\n");
        return sb.toString();
    }

    private String smeQueries(Map<String, List<JsonNode>> smeBySegment) {
        Path self = VIEWS.resolve("sme-queries.md");
        StringBuilder sb = new StringBuilder("# SME/TBA Queries: Open Worklist\n\n");
        sb.append(generatedNotice(self)).append("\n\n");
        sb.append("Every open or reopened SME query across all segments. Resolved and deferred questions, and each segment's ")
                .append("response instructions, are in the per-segment registers linked from the [index](index.md).\n\n");
        List<JsonNode> all = smeBySegment.values().stream().flatMap(List::stream).toList();
        sb.append("**Status:** ").append(statusSummary(all)).append("\n");
        smeBySegment.forEach((segment, items) -> {
            List<JsonNode> open = items.stream().filter(i -> !CommunicationRegisterValidator.TERMINAL_STATUSES
                    .contains(text(i, "status", ""))).toList();
            if (open.isEmpty()) {
                return;
            }
            sb.append("\n## Segment ").append(segment).append(" ([register](").append(link(self, segmentView(segment)))
                    .append("))\n\n| ID | Question | Why it is needed | Status | Blocks | Raised |\n| --- | --- | --- | --- | --- | --- |\n");
            for (JsonNode item : open) {
                sb.append("| [").append(text(item, "id", "")).append("](").append(link(self, segmentView(segment)))
                        .append('#').append(text(item, "id", "").toLowerCase(Locale.ROOT)).append(") | ")
                        .append(cell(text(item, "subject", ""))).append(" | ").append(cell(text(item, "context", "")))
                        .append(" | ").append(text(item, "status", "")).append(" | ").append(cell(catalogLinks(item)))
                        .append(" | ").append(text(item, "raisedOn", "")).append(" |\n");
            }
        });
        return sb.toString();
    }

    private String channelView(String title, String purpose, List<JsonNode> items, Path self) {
        StringBuilder sb = new StringBuilder("# ").append(title).append("\n\n");
        sb.append(generatedNotice(self)).append("\n\n").append(purpose).append("\n\n");
        sb.append("**Status:** ").append(statusSummary(items)).append("\n\n");
        sb.append("| ID | Subject | Status | Owner | Raised |\n| --- | --- | --- | --- | --- |\n");
        for (JsonNode item : items) {
            sb.append("| [").append(text(item, "id", "")).append("](#").append(text(item, "id", "").toLowerCase(Locale.ROOT))
                    .append(") | ").append(cell(text(item, "subject", ""))).append(" | ").append(text(item, "status", ""))
                    .append(" | ").append(text(item, "owner", "")).append(" | ").append(text(item, "raisedOn", "")).append(" |\n");
        }
        for (JsonNode item : items) {
            String id = text(item, "id", "");
            sb.append("\n").append(anchor(id)).append("\n## ").append(id).append(": ").append(text(item, "subject", "")).append("\n\n");
            sb.append("- **Status:** ").append(text(item, "status", "")).append('\n');
            sb.append("- **Owner:** ").append(text(item, "owner", "")).append('\n');
            sb.append("- **Raised:** ").append(text(item, "raisedOn", "")).append(" by ").append(text(item, "raisedBy", "")).append('\n');
            if (item.path("segments").size() > 0) {
                sb.append("- **Segments:** ").append(join(item.path("segments"))).append('\n');
            }
            if (!text(item, "targetDelivery", "").isBlank()) {
                sb.append("- **Target delivery:** ").append(text(item, "targetDelivery", "")).append('\n');
            }
            sb.append('\n').append(text(item, "detail", "")).append('\n');
            if (item.path("evidence").size() > 0) {
                sb.append("\n**Evidence:**\n\n");
                item.path("evidence").forEach(e -> sb.append("- `").append(e.asText()).append("`\n"));
            }
            if (item.path("relatedItems").size() > 0) {
                sb.append("\n**Related:** ");
                List<String> links = new ArrayList<>();
                item.path("relatedItems").forEach(r -> links.add(relatedLink(self, r.asText())));
                sb.append(String.join(", ", links)).append('\n');
            }
            if (!text(item, "resolution", "").isBlank()) {
                sb.append("\n**Resolution (").append(text(item, "resolvedOn", "")).append(", ").append(text(item, "resolvedBy", ""))
                        .append("):** ").append(text(item, "resolution", "")).append('\n');
            }
            String history = history(List.of(item));
            if (!history.isEmpty()) {
                sb.append("\n**History:**\n\n").append(history);
            }
        }
        return sb.toString();
    }

    private static String relatedLink(Path self, String id) {
        String target;
        if (id.startsWith("SEG")) {
            String segment = id.substring(3, id.indexOf("-SME-"));
            target = link(self, segmentView(segment));
        } else if (id.startsWith("TT-")) {
            target = link(self, VIEWS.resolve("test-team-discussion.md"));
        } else if (id.startsWith("AID-")) {
            target = link(self, VIEWS.resolve("ai-dev-discussion.md"));
        } else {
            target = link(self, VIEWS.resolve("ai-feedback.md"));
        }
        return "[" + id + "](" + target + "#" + id.toLowerCase(Locale.ROOT) + ")";
    }

    private static String history(List<JsonNode> items) {
        StringBuilder sb = new StringBuilder();
        for (JsonNode item : items) {
            for (JsonNode entry : item.path("history")) {
                sb.append("- **").append(text(item, "id", "")).append("** ").append(text(entry, "date", "")).append(' ')
                        .append(text(entry, "status", "")).append(": ").append(text(entry, "note", "")).append('\n');
            }
        }
        return sb.toString();
    }

    private static String statusCell(JsonNode item) {
        String status = text(item, "status", "");
        String resolvedOn = text(item, "resolvedOn", "");
        String note = text(item, "statusNote", "");
        String shown = resolvedOn.isBlank() ? status : status + " (" + resolvedOn + ")";
        return note.isBlank() ? shown : shown + ": " + cell(note);
    }

    private static String resolutionText(JsonNode item) {
        String resolution = text(item, "resolution", "");
        return resolution.isBlank() ? "" : resolution + " (" + text(item, "resolvedBy", "") + ")";
    }

    private static String catalogLinks(JsonNode item) {
        JsonNode links = item.path("links");
        Set<String> parts = new LinkedHashSet<>();
        if (links.has("catalogItem")) {
            parts.add("`" + links.path("catalogItem").asText() + "`");
        }
        links.path("rules").forEach(r -> parts.add(r.asText()));
        return String.join(", ", parts);
    }

    private static String statusSummary(List<JsonNode> items) {
        String summary = STATUS_ORDER.stream()
                .filter(status -> count(items, status) > 0)
                .map(status -> count(items, status) + " " + status.toLowerCase(Locale.ROOT).replace('_', ' '))
                .collect(Collectors.joining(", "));
        return summary.isEmpty() ? "no items." : summary + " (" + items.size() + " total).";
    }

    private static long count(List<JsonNode> items, String status) {
        return items.stream().filter(i -> status.equals(text(i, "status", ""))).count();
    }

    private static String generatedNotice(Path self) {
        return "> Generated from [atl105-communication-register.json](" + link(self, REGISTER)
                + ") by `GenerateCommunicationRegisterViews`. Do not edit this file: update the register and regenerate. "
                + "All registers: [index](" + link(self, VIEWS.resolve("index.md")) + ").";
    }

    private static String anchor(String id) {
        return "<a id=\"" + id.toLowerCase(Locale.ROOT) + "\"></a>";
    }

    private static String cell(String value) {
        return value.replace("\r\n", "<br>").replace("\n", "<br>").replace("|", "\\|");
    }

    private static String join(JsonNode array) {
        List<String> values = new ArrayList<>();
        array.forEach(v -> values.add(v.asText()));
        return String.join(", ", values);
    }

    static String link(Path from, Path to) {
        return from.toAbsolutePath().normalize().getParent().relativize(to.toAbsolutePath().normalize()).toString().replace('\\', '/');
    }
}
