package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/** Validates Test Solution JSON only; AI input folders are intentionally excluded. */
class TestSolutionJsonFormatTest {
    private static final Path TEST_SOLUTION_JSON = Path.of(
            "specifications", "ATL105", "test-output", "test-json");
    private static final Path KNOWLEDGE_BASE = Path.of(
            "specifications", "ATL105", "docs", "specs", "kb");

    private final ObjectMapper mapper = new ObjectMapper();
        private final ObjectMapper canonicalMapper = mapper.copy()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final CanonicalTraceabilityValidator traceabilityValidator = new CanonicalTraceabilityValidator();

    @Test
    void allTestSolutionJsonIsWellFormedAndCanonicalPackagesAreTraceable() throws IOException {
        List<String> failures = new ArrayList<>();
        try (Stream<Path> files = Files.walk(TEST_SOLUTION_JSON)) {
            files.filter(path -> path.toString().endsWith(".json"))
                    .sorted(Comparator.comparing(Path::toString))
                    .forEach(path -> validateTestSolutionFile(path, failures));
        }

        assertThat(failures)
                .as("Test Solution JSON validation failures; AI Solution input is excluded")
                .isEmpty();
    }

    @Test
    void allAtl105KnowledgeBaseRuleCatalogsHaveRulesAndAnchors() throws IOException {
        List<String> failures = new ArrayList<>();
        try (Stream<Path> files = Files.walk(KNOWLEDGE_BASE)) {
            files.filter(path -> path.getFileName().toString().endsWith("-rule-catalog.json"))
                    .sorted(Comparator.comparing(Path::toString))
                    .forEach(path -> validateRuleCatalog(path, failures));
        }

        assertThat(failures)
                .as("ATL105 knowledge-base rule catalog failures")
                .isEmpty();
    }

    private void validateTestSolutionFile(Path path, List<String> failures) {
        try {
            JsonNode root = mapper.readTree(path.toFile());
            if (root == null || !(root.isObject() || root.isArray())) {
                failures.add(path + ": root must be a JSON object or array");
                return;
            }

            if (root.isObject() && root.has("manifest") && root.has("businessRequirements")
                    && root.has("testScenarios") && root.has("testCases") && root.has("testData")) {
                CanonicalArtifactPackage artifactPackage = canonicalMapper.readValue(path.toFile(), CanonicalArtifactPackage.class);
                var result = traceabilityValidator.validate(artifactPackage);
                result.errors().forEach(error -> failures.add(path + ": " + error));
            }
        } catch (Exception exception) {
            failures.add(path + ": " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
        }
    }

    private void validateRuleCatalog(Path path, List<String> failures) {
        try {
            JsonNode root = mapper.readTree(path.toFile());
            JsonNode rules = root.path("rules");
            if (!rules.isArray() || rules.isEmpty()) {
                failures.add(path + ": rules must be a non-empty array");
                return;
            }
            for (JsonNode rule : rules) {
                if (rule.path("ruleId").asText().isBlank()
                        || rule.path("title").asText().isBlank()
                        || !rule.path("sourceAnchor").isObject()) {
                    failures.add(path + ": every rule requires ruleId, title, and sourceAnchor");
                }
            }
        } catch (Exception exception) {
            failures.add(path + ": " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
        }
    }
}