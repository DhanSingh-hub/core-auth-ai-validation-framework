package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.AdditionalInformationTransactionFlowValidator;
import com.coreauth.validator.canonical.Segment100AiJsonCompatibilityValidator;
import com.coreauth.validator.canonical.Segment101PayloadValidator;
import com.coreauth.validator.canonical.Segment103PayloadValidator;
import com.coreauth.validator.canonical.Segment104PayloadValidator;
import com.coreauth.validator.canonical.Segment108PayloadValidator;
import com.coreauth.validator.canonical.Segment109PayloadValidator;
import com.coreauth.validator.canonical.Segment111PayloadValidator;
import com.coreauth.validator.canonical.Segment112PayloadValidator;
import com.coreauth.validator.canonical.Segment113PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/** Opens only linked Run2 payloads and applies the segment-owned validator with bounded evidence. */
public final class Run2PayloadBatchValidator {
    private static final int MAX_SAMPLES = 25;
    private final ObjectMapper mapper = new ObjectMapper();

    public BatchResult validate(String segment, Path runRoot, CanonicalArtifactPackage artifactPackage) {
        Function<JsonNode, ValidationResult> validator = validator(segment);
        Set<String> files = new LinkedHashSet<>();
        for (CanonicalTestData data : safe(artifactPackage.getTestData())) {
            String sourceFile = data.getPayload() == null ? null : data.getPayload().path("sourceFile").asText(null);
            if (sourceFile != null) files.add(sourceFile);
        }
        int applicable = 0;
        int valid = 0;
        int invalid = 0;
        int reviewRequired = 0;
        int unreadable = 0;
        List<String> samples = new ArrayList<>();
        for (String relative : files) {
            Path file = runRoot.resolve(relative).normalize();
            try {
                if (!file.startsWith(runRoot.normalize()) || !Files.isRegularFile(file)) {
                    unreadable++;
                    sample(samples, relative + ": missing or outside run root");
                    continue;
                }
                JsonNode payload = mapper.readTree(file.toFile());
                if (!containsSegment(payload, segment)) continue;
                applicable++;
                if (validator == null) continue;
                ValidationResult result = validator.apply(normalizeForValidator(payload, segment));
                boolean review = hasReviewWarnings(result);
                if (result.isValid() && !review) {
                    valid++;
                } else if (result.isValid()) {
                    reviewRequired++;
                    result.warnings().stream().limit(3)
                            .forEach(warning -> sample(samples, relative + ": " + warning));
                } else {
                    invalid++;
                    result.errors().stream().limit(3)
                            .forEach(error -> sample(samples, relative + ": " + error.reason()));
                }
            } catch (Exception exception) {
                unreadable++;
                sample(samples, relative + ": " + exception.getMessage());
            }
        }
        return new BatchResult(segment, validator != null, files.size(), applicable, valid, invalid, reviewRequired,
                unreadable, List.copyOf(samples));
    }

        private static boolean hasReviewWarnings(ValidationResult result) {
        return result.warnings().stream().map(String::toLowerCase)
            .anyMatch(warning -> warning.contains("review_required") || warning.contains("not_assertable")
                || warning.contains("appendixklayoutscope"));
        }

    private static JsonNode normalizeForValidator(JsonNode payload, String segment) {
        if (!(payload.deepCopy() instanceof ObjectNode normalized)) return payload;
        JsonNode requestNode = normalized.path("Financial Request");
        if (!(requestNode instanceof ObjectNode request)) return normalized;
        if ("101".equals(segment) && request.has("Fleet Segment") && !request.has("Fleet Data Segment")) {
            request.set("Fleet Data Segment", request.get("Fleet Segment"));
        }
        if ("111".equals(segment) && request.has("Variable Info Segment")
                && !request.has("Variable Information Data Segment")) {
            request.set("Variable Information Data Segment", request.get("Variable Info Segment"));
        }
        return normalized;
    }

    private static Function<JsonNode, ValidationResult> validator(String segment) {
        return switch (segment) {
            case "100" -> new Segment100AiJsonCompatibilityValidator()::validatePayload;
            case "101" -> new Segment101PayloadValidator()::validatePayload;
            case "103" -> new Segment103PayloadValidator()::validatePayload;
            case "104" -> new Segment104PayloadValidator()::validatePayload;
            case "108" -> new Segment108PayloadValidator()::validatePayload;
            case "109" -> new Segment109PayloadValidator()::validatePayload;
            case "111" -> new AdditionalInformationTransactionFlowValidator()::validateRequestPayload;
            case "112" -> new AdditionalInformationTransactionFlowValidator()::validateResponsePayload;
            case "113" -> new Segment113PayloadValidator()::validatePayload;
            default -> null;
        };
    }

    private static boolean containsSegment(JsonNode node, String segment) {
        if (node == null) return false;
        if (node.isObject()) {
            String compact = node.path("SegmentType").asText(node.path("segmentType").asText(null));
            String spaced = node.path("Segment Type").asText(null);
            if (segment.equals(compact) || segment.equals(spaced)) return true;
            var fields = node.elements();
            while (fields.hasNext()) if (containsSegment(fields.next(), segment)) return true;
        } else if (node.isArray()) {
            for (JsonNode child : node) if (containsSegment(child, segment)) return true;
        }
        return false;
    }

    private static void sample(List<String> samples, String value) {
        if (samples.size() < MAX_SAMPLES) samples.add(value);
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? List.of() : values;
    }

    public record BatchResult(String segment, boolean validatorImplemented, int linkedPayloadFiles,
                              int applicablePayloadFiles, int validPayloadFiles, int invalidPayloadFiles,
                              int reviewRequiredPayloadFiles,
                              int unreadablePayloadFiles, List<String> errorSamples) {
        public BatchResult(String segment, boolean validatorImplemented, int linkedPayloadFiles,
                           int applicablePayloadFiles, int validPayloadFiles, int invalidPayloadFiles,
                           int unreadablePayloadFiles, List<String> errorSamples) {
            this(segment, validatorImplemented, linkedPayloadFiles, applicablePayloadFiles,
                    validPayloadFiles, invalidPayloadFiles, 0, unreadablePayloadFiles, errorSamples);
        }

        public ValidationResult asValidationResult() {
            ValidationResult result = new ValidationResult("segment-" + segment + "-payload-batch");
            if (!validatorImplemented) {
                result.addError("PayloadCompliance", "Segment " + segment + " validator is not implemented");
            }
            if (applicablePayloadFiles == 0) {
                result.addError("PayloadCompliance", "Segment " + segment + " has no applicable linked payload files to validate");
                } else if (validatorImplemented && validPayloadFiles + invalidPayloadFiles
                    + reviewRequiredPayloadFiles != applicablePayloadFiles) {
                result.addError("PayloadCompliance", "Segment " + segment + " has no valid payload or incomplete validation outcomes");
            }
                if (reviewRequiredPayloadFiles > 0) {
                result.addError("PayloadCompliance", reviewRequiredPayloadFiles
                    + " applicable linked payload files are REVIEW_REQUIRED and cannot certify execution");
                }
            if (unreadablePayloadFiles > 0) {
                result.addError("PayloadCompliance", unreadablePayloadFiles + " linked payload files are unreadable");
            }
            if (invalidPayloadFiles > 0) {
                result.addError("PayloadCompliance", invalidPayloadFiles + " applicable payload files failed validation");
            }
            errorSamples.forEach(result::addWarning);
            return result;
        }
    }
}