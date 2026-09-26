package com.coreauth.validator.validation;

import com.coreauth.validator.rules.FieldRule;
import com.coreauth.validator.rules.RuleSource;
import com.coreauth.validator.source.JsonDocument;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;

/**
 * Validates a BUYPASS ATL105 JSON test-data document against the reference business rules.
 *
 * <p>Document shape, confirmed against real Fiserv-supplied sample files
 * (core_auth-imp/Valid transactions/...):
 * <pre>{@code
 * {
 *   "Financial Request": {
 *     "MessageType": "ATL105",
 *     "NumSegments": "3",
 *     "Standard Segment": { "SegmentType": "100", "TerminalID": "...",
 *                           "PromptCode": { "TransactionType": "3", "CardType": "020" }, ... },
 *     "Variable Info Segment": { "SegmentType": "111",
 *                                "VarInfoTable": [ { "TableID": "047", "Value": "..." }, ... ] }
 *   }
 * }
 * }</pre>
 * The single top-level key ("Financial Request" / "Financial Response") is the message wrapper;
 * every other key directly under it whose value is an object is a named segment. Nested objects
 * within a segment (e.g. PromptCode) are flattened to "Parent > Child" field names, and table
 * arrays (e.g. VarInfoTable) are flattened to "ArrayName_TableID" field names - both match the
 * field-name vocabulary already used in the ATL105 reference CSV and spec-elements.json.
 */
public final class Atl105JsonValidator {

    private static final String[] META_FIELDS = {"MessageType", "NumSegments"};

    private final RuleSource rules;

    public Atl105JsonValidator(RuleSource rules) {
        this.rules = rules;
    }

    public ValidationResult validate(JsonDocument document) {
        ValidationResult result = new ValidationResult(document.id());
        JsonNode root = document.content();

        if (root.size() != 1) {
            result.addError("Document", "expected exactly one top-level message wrapper key (e.g. 'Financial Request'), found "
                    + root.size());
            return result;
        }
        Map.Entry<String, JsonNode> wrapperEntry = root.fields().next();
        String wrapperKey = wrapperEntry.getKey();
        JsonNode message = wrapperEntry.getValue();
        String context = toContext(wrapperKey);

        String messageType = message.path("MessageType").asText(null);
        if (!"ATL105".equals(messageType)) {
            result.addError(context, "MessageType must be 'ATL105', got: " + messageType);
        }

        int declaredSegments = message.path("NumSegments").asInt(-1);
        int actualSegments = 0;

        Iterator<Map.Entry<String, JsonNode>> it = message.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            if (isMetaField(key) || !value.isObject()) {
                continue;
            }
            actualSegments++;
            validateSegment(result, context, key, value);
        }

        if (declaredSegments >= 0 && declaredSegments != actualSegments) {
            result.addWarning("NumSegments declared as " + declaredSegments
                    + " but " + actualSegments + " segment object(s) were found");
        }
        return result;
    }

    private void validateSegment(ValidationResult result, String context, String segment, JsonNode segmentNode) {
        Iterator<Map.Entry<String, JsonNode>> it = segmentNode.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> field = it.next();
            String fieldName = field.getKey();
            JsonNode fieldValue = field.getValue();

            if (fieldValue.isObject()) {
                // e.g. PromptCode: { TransactionType, CardType } -> "PromptCode > TransactionType"
                Iterator<Map.Entry<String, JsonNode>> nested = fieldValue.fields();
                while (nested.hasNext()) {
                    Map.Entry<String, JsonNode> child = nested.next();
                    checkField(result, context, segment, fieldName + " > " + child.getKey(), child.getValue());
                }
            } else if (fieldValue.isArray()) {
                // e.g. VarInfoTable: [ { TableID, Value } ] -> "VarInfoTable_047"
                for (JsonNode tableEntry : fieldValue) {
                    String tableId = tableEntry.path("TableID").asText(null);
                    JsonNode tableValue = tableEntry.get("Value");
                    if (tableId == null || tableValue == null) {
                        result.addWarning("segment '" + segment + "' field '" + fieldName
                                + "' has a table entry missing TableID/Value, skipped");
                        continue;
                    }
                    checkField(result, context, segment, fieldName + "_" + tableId, tableValue);
                }
            } else {
                checkField(result, context, segment, fieldName, fieldValue);
            }
        }
    }

    private void checkField(ValidationResult result, String context, String segment, String fieldName, JsonNode valueNode) {
        String value = valueNode.isNull() ? "" : valueNode.asText();
        rules.find(context, segment, fieldName).ifPresentOrElse(
                rule -> checkRule(result, context, segment, fieldName, value, rule),
                () -> result.addWarning("no reference rule for [" + context + " > " + segment
                        + " > " + fieldName + "] - value not verified"));
    }

    private void checkRule(ValidationResult result, String context, String segment,
                            String element, String value, FieldRule rule) {
        String problem = rule.check(value);
        if (problem != null) {
            result.addError(new ValidationError(context, segment, element, problem));
        }
    }

    private static boolean isMetaField(String key) {
        for (String meta : META_FIELDS) {
            if (meta.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /** Maps the message wrapper key ("Financial Request"/"Financial Response") to the CSV's Context vocabulary. */
    private static String toContext(String wrapperKey) {
        if (wrapperKey.endsWith("Request")) {
            return "Merchant Request";
        }
        if (wrapperKey.endsWith("Response")) {
            return "Merchant Response";
        }
        return wrapperKey;
    }
}

