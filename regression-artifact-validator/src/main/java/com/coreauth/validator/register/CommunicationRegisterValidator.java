package com.coreauth.validator.register;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Validates the ATL105 communication register (SME queries, Test Team and AI developer
 * discussion items, and AI feedback) and its links to the rule catalogs' provisionalItems.
 */
public final class CommunicationRegisterValidator {
    static final Set<String> TERMINAL_STATUSES = Set.of("RESOLVED", "DEFERRED", "WITHDRAWN", "SUPERSEDED");
    private static final Pattern DATE = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final String SOURCE = "CommunicationRegister";

    private final ObjectMapper mapper = new ObjectMapper();

    public ValidationResult validate(JsonNode register, Path knowledgeBase) {
        ValidationResult result = new ValidationResult(text(register, "registerId", "communication-register"));
        JsonNode channels = register.path("channels");
        Set<String> statuses = new HashSet<>();
        register.path("statuses").forEach(s -> statuses.add(s.asText()));
        if (!channels.isObject() || statuses.isEmpty() || !register.path("items").isArray()) {
            result.addError(SOURCE, "register must declare channels, statuses and an items array");
            return result;
        }

        Map<String, JsonNode> byId = new HashMap<>();
        for (JsonNode item : register.path("items")) {
            String id = text(item, "id", null);
            if (id == null) {
                result.addError(SOURCE, "item without id");
                continue;
            }
            if (byId.put(id, item) != null) {
                result.addError(SOURCE, id + " is duplicated");
            }
            validateItem(id, item, channels, statuses, register.path("segments"), result);
        }
        for (JsonNode item : register.path("items")) {
            for (JsonNode related : item.path("relatedItems")) {
                if (!byId.containsKey(related.asText())) {
                    result.addError(SOURCE, text(item, "id", "?") + " relates to unknown item " + related.asText());
                }
            }
        }
        if (knowledgeBase != null) {
            validateCatalogLinks(byId, knowledgeBase, result);
        }
        return result;
    }

    private static void validateItem(String id, JsonNode item, JsonNode channels, Set<String> statuses,
                                     JsonNode segments, ValidationResult result) {
        String channel = text(item, "channel", "");
        JsonNode definition = channels.path(channel);
        if (!definition.isObject()) {
            result.addError(SOURCE, id + " has unknown channel " + channel);
            return;
        }
        if (!Pattern.matches(definition.path("idPattern").asText(), id)) {
            result.addError(SOURCE, id + " does not match the " + channel + " id pattern");
        }
        for (String field : List.of("subject", "owner", "raisedBy")) {
            if (text(item, field, "").isBlank()) {
                result.addError(SOURCE, id + " is missing " + field);
            }
        }
        if (!DATE.matcher(text(item, "raisedOn", "")).matches()) {
            result.addError(SOURCE, id + " raisedOn must be yyyy-MM-dd");
        }
        String status = text(item, "status", "");
        if (!statuses.contains(status)) {
            result.addError(SOURCE, id + " has unknown status " + status);
        }
        if (TERMINAL_STATUSES.contains(status)) {
            if (text(item, "resolution", "").isBlank() || text(item, "resolvedBy", "").isBlank()
                    || !DATE.matcher(text(item, "resolvedOn", "")).matches()) {
                result.addError(SOURCE, id + " is " + status + " but lacks resolution, resolvedBy or a yyyy-MM-dd resolvedOn");
            }
        }
        if ("REOPENED".equals(status) && item.path("history").isEmpty()) {
            result.addError(SOURCE, id + " is REOPENED but has no history");
        }
        if (!item.path("segments").isArray()) {
            result.addError(SOURCE, id + " segments must be an array");
        }
        if ("SME_QUERY".equals(channel)) {
            JsonNode segs = item.path("segments");
            String segment = segs.size() == 1 ? segs.get(0).asText() : null;
            if (segment == null || !id.startsWith("SEG" + segment + "-SME-")) {
                result.addError(SOURCE, id + " must name exactly one segment matching its id");
            } else if (!segments.has(segment)) {
                result.addError(SOURCE, id + " refers to segment " + segment + " with no segment entry");
            }
        }
    }

    private void validateCatalogLinks(Map<String, JsonNode> byId, Path knowledgeBase, ValidationResult result) {
        Set<String> linkedFromCatalogs = new HashSet<>();
        List<Path> catalogs;
        try (Stream<Path> files = Files.walk(knowledgeBase)) {
            catalogs = files.filter(p -> p.getFileName().toString().endsWith("-rule-catalog.json")).sorted().toList();
        } catch (IOException exception) {
            result.addError(SOURCE, "cannot read knowledge base: " + exception.getMessage());
            return;
        }
        for (Path catalogPath : catalogs) {
            JsonNode catalog;
            try {
                catalog = mapper.readTree(catalogPath.toFile());
            } catch (IOException exception) {
                result.addError(SOURCE, catalogPath.getFileName() + " is not readable: " + exception.getMessage());
                continue;
            }
            // Not every catalog declares "segment"; the kb/segment-<NNN>/coverage/ folder always does.
            String segment = catalogPath.getParent().getParent().getFileName().toString().replaceFirst("^segment-", "");
            for (JsonNode provisional : catalog.path("provisionalItems")) {
                String where = catalogPath.getFileName() + " " + text(provisional, "id", "?");
                String registerId = text(provisional, "registerId", null);
                JsonNode item = registerId == null ? null : byId.get(registerId);
                if (item == null) {
                    result.addError(SOURCE, where + " has no registerId in the communication register");
                    continue;
                }
                linkedFromCatalogs.add(registerId);
                if (!"SME_QUERY".equals(text(item, "channel", ""))
                        || !segment.equals(item.path("segments").path(0).asText())
                        || !text(provisional, "id", "").equals(item.path("links").path("catalogItem").asText())) {
                    result.addError(SOURCE, where + " links to " + registerId + ", which is not the SME query for that segment and catalog item");
                }
                if (!text(provisional, "status", "").equals(text(item, "status", ""))) {
                    result.addError(SOURCE, where + " status " + text(provisional, "status", "") + " differs from "
                            + registerId + " status " + text(item, "status", ""));
                }
                if (provisional.has("question") || provisional.has("resolution")) {
                    result.addError(SOURCE, where + " must not repeat the question or resolution; they live in " + registerId);
                }
            }
        }
        byId.forEach((id, item) -> {
            if (item.path("links").has("catalogItem") && !linkedFromCatalogs.contains(id)) {
                result.addError(SOURCE, id + " names catalog item " + item.path("links").path("catalogItem").asText()
                        + " but no catalog links back to it");
            }
        });
    }

    static String text(JsonNode node, String field, String fallback) {
        JsonNode value = node.path(field);
        return value.isMissingNode() || value.isNull() ? fallback : value.asText();
    }
}
