package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Requires AI-originated BR -> TS -> TC -> TD evidence for every source-defined Element 83 family/code meaning. */
public final class Element83AiCoverageValidator {
    private static final String SOURCE = "Element83AiCoverage";
    private static final Path DEFAULT_MATRIX = Path.of("specifications", "ATL105", "docs", "specs", "kb",
        "element-83-response-code", "coverage", "element-83-response-code-br-validation-matrix.json");
    private static final Set<String> RESPONSE_CODE_FIELD_NAMES = Set.of("responsecode", "response_code");

    private final ObjectMapper mapper;
    private final Path matrixPath;

    public Element83AiCoverageValidator() {
        this(new ObjectMapper(), DEFAULT_MATRIX);
    }

    Element83AiCoverageValidator(ObjectMapper mapper, Path matrixPath) {
        this.mapper = mapper;
        this.matrixPath = matrixPath;
    }

    public ValidationResult validate(CanonicalArtifactPackage aiPackage) {
        ValidationResult result = new ValidationResult(packageId(aiPackage));
        if (aiPackage == null) {
            result.addError(SOURCE, "AI canonical artifact package is required");
            return result;
        }

        JsonNode matrix;
        try {
            matrix = mapper.readTree(matrixPath.toFile());
        } catch (IOException exception) {
            result.addError(SOURCE, "Element 83 source-derived BR matrix cannot be read: " + exception.getMessage());
            return result;
        }

        JsonNode requiredPairs = matrix.path("allowedCodeFamilyMeanings");
        if (!requiredPairs.isArray() || requiredPairs.isEmpty()) {
            result.addError(SOURCE, "Element 83 source-derived BR matrix has no allowed code/family meanings");
            return result;
        }

        Atl105ResponseCodeFamilyOracle responseOracle = new Atl105ResponseCodeFamilyOracle();
        for (CanonicalTestData testData : safe(aiPackage.getTestData())) {
            validateObservedResponses(testData, testData == null ? null : testData.getPayload(), responseOracle, result);
            validateObservedResponses(testData, testData == null ? null : testData.getResponse(), responseOracle, result);
        }

        int covered = 0;
        for (JsonNode pair : requiredPairs) {
            String family = pair.path("family").asText("");
            String code = pair.path("code").asText("");
            String brId = pair.path("businessRequirementId").asText("");
            if (hasFullAiEvidence(aiPackage, family, code, brId)) {
                covered++;
            } else {
                result.addError(SOURCE, "Missing AI BR -> TS -> TC -> TD response evidence for Element 83 family="
                    + family + ", code=" + code + ", requiredBR=" + brId);
            }
        }
        if (covered == requiredPairs.size()) {
            result.addWarning("Element 83 AI coverage found complete structural chains for " + covered
                + " family/code meanings; this is not business approval or independent payload-semantic approval.");
        }
        return result;
    }

    /** Resolves Run2 TD file references under the immutable run root and validates the actual JSON bodies. */
    public ValidationResult validateFiles(CanonicalArtifactPackage aiPackage, Path runRoot) {
        ValidationResult result = new ValidationResult(packageId(aiPackage));
        if (aiPackage == null || runRoot == null) {
            result.addError(SOURCE, "AI canonical package and immutable run root are required");
            return result;
        }

        Path normalizedRoot = runRoot.toAbsolutePath().normalize();
        List<CanonicalTestData> hydratedData = new ArrayList<>();
        for (CanonicalTestData original : safe(aiPackage.getTestData())) {
            CanonicalTestData copy = new CanonicalTestData();
            copy.setId(original.getId());
            copy.setTestCaseIds(original.getTestCaseIds());
            copy.setSourceAnchors(original.getSourceAnchors());
            copy.setExpectedValidation(original.getExpectedValidation());
            copy.setFileName(original.getFileName());
            copy.setReadiness(original.getReadiness());
            copy.setResponse(original.getResponse());

            String relative = original.getFileName();
            if (relative == null || relative.isBlank()) {
                JsonNode payload = original.getPayload();
                relative = payload != null && payload.path("sourceFile").isTextual()
                    ? payload.path("sourceFile").asText() : null;
            }
            if (relative == null || relative.isBlank()) {
                copy.setPayload(original.getPayload());
            } else {
                Path file = normalizedRoot.resolve(relative).normalize();
                if (!file.startsWith(normalizedRoot) || !Files.isRegularFile(file)) {
                    result.addError(SOURCE, "AI Test Data " + original.getId()
                        + " references a missing or out-of-run-root payload file: " + relative);
                } else {
                    try {
                        copy.setPayload(mapper.readTree(file.toFile()));
                    } catch (IOException exception) {
                        result.addError(SOURCE, "AI Test Data " + original.getId()
                            + " payload JSON cannot be read: " + exception.getMessage());
                    }
                }
            }
            hydratedData.add(copy);
        }

        CanonicalArtifactPackage hydrated = new CanonicalArtifactPackage();
        hydrated.setManifest(aiPackage.getManifest());
        hydrated.setBusinessRequirements(aiPackage.getBusinessRequirements());
        hydrated.setTestScenarios(aiPackage.getTestScenarios());
        hydrated.setTestCases(aiPackage.getTestCases());
        hydrated.setTestData(hydratedData);
        hydrated.setRequirementCrosswalk(aiPackage.getRequirementCrosswalk());
        result.merge(validate(hydrated));
        return result;
    }

    private static boolean hasFullAiEvidence(CanonicalArtifactPackage pkg, String family, String code, String requiredBrId) {
        List<CanonicalRequirement> requirements = safe(pkg.getBusinessRequirements());
        List<CanonicalScenario> scenarios = safe(pkg.getTestScenarios());
        List<CanonicalTestCase> cases = safe(pkg.getTestCases());
        List<CanonicalTestData> dataItems = safe(pkg.getTestData());

        for (CanonicalRequirement requirement : requirements) {
            if (!hasElement83FamilyAnchor(requirement.getSourceAnchors(), family)
                || !requirementCoversCode(requirement, code, requiredBrId)) continue;
            for (CanonicalScenario scenario : scenarios) {
                if (!safe(scenario.getRequirementIds()).contains(requirement.getId())
                    || !hasElement83FamilyAnchor(scenario.getSourceAnchors(), family)) continue;
                for (CanonicalTestCase testCase : cases) {
                    if (!safe(testCase.getScenarioIds()).contains(scenario.getId())
                        || !hasElement83FamilyAnchor(testCase.getSourceAnchors(), family)) continue;
                    for (CanonicalTestData testData : dataItems) {
                        if (!safe(testData.getTestCaseIds()).contains(testCase.getId())
                            || !hasElement83FamilyAnchor(testData.getSourceAnchors(), family)) continue;
                        if (payloadMatches(testData.getPayload(), family, code)
                            || payloadMatches(testData.getResponse(), family, code)) return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean requirementCoversCode(CanonicalRequirement requirement, String code, String requiredBrId) {
        if (requiredBrId.equals(requirement.getId())) return true;
        if (requirement.getTitle() != null && containsCodeToken(requirement.getTitle(), code)) return true;
        for (SourceAnchor anchor : safe(requirement.getSourceAnchors())) {
            if (anchor != null && hasElement83(anchor.getElement()) && containsCodeToken(anchor.getRule(), code)) return true;
        }
        return false;
    }

    private static boolean containsCodeToken(String text, String code) {
        if (text == null || code == null) return false;
        for (String token : text.split("[^A-Za-z0-9]+")) {
            if (token.equalsIgnoreCase(code)) return true;
        }
        return false;
    }

    private static boolean hasElement83FamilyAnchor(List<SourceAnchor> anchors, String family) {
        for (SourceAnchor anchor : safe(anchors)) {
            if (anchor == null || !hasElement83(anchor.getElement())) continue;
            if (anchorFamilyMatches(anchor, family)) return true;
        }
        return false;
    }

    private static boolean hasElement83(String element) {
        if (element == null) return false;
        for (String value : element.split("[,|]")) if ("83".equals(value.trim())) return true;
        return false;
    }

    private static boolean anchorFamilyMatches(SourceAnchor anchor, String family) {
        String segment = normalize(anchor.getSegment());
        String rule = normalize(anchor.getRule());
        String section = anchor.getSection() == null ? "" : anchor.getSection().trim();
        return switch (family) {
            case "financial" -> segment.contains("financialresponse") || segment.contains("loyaltycardtransactionresponse")
                || segment.contains("ecatelecheckservicetransactionresponse") || rule.contains("financialresponse")
                || rule.contains("responsecodefinancial") || section.equals("11.1.2")
                || section.equals("11.2.2") || section.equals("11.3.2");
            case "totals" -> segment.contains("totalsresponse") || segment.contains("totals")
                || rule.contains("responsecodetotals") || section.startsWith("11.4.2");
            case "electronic-mail" -> segment.contains("electronicmailresponse") || segment.contains("electronicmail")
                || rule.contains("responsecodeelectronicmail") || section.equals("11.5.2");
            case "communications-test" -> segment.contains("communicationstestresponse") || segment.contains("communicationstest")
                || rule.contains("responsecodecommunicationstest") || section.equals("11.6.2");
            case "proprietary-load" -> segment.contains("proprietarydataloadresponse") || segment.contains("proprietaryload")
                || rule.contains("responsecodeproprietaryload") || section.equals("11.7.7");
            case "transarmor" -> segment.contains("transarmor") || rule.contains("responsecodetransarmor")
                || section.equals("11.7.5");
            case "emv-key-load" -> segment.contains("emvkeyload") || segment.contains("capublickey")
                || rule.contains("responsecodeemvkeyload") || section.equals("11.9.2");
            default -> false;
        };
    }

    private static boolean payloadMatches(JsonNode payload, String family, String code) {
        if (payload == null || !payload.isObject()) return false;
        var fields = payload.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            String key = normalize(field.getKey());
            String childFamily = responseRootFamily(key);
            if (childFamily != null && childFamily.equals(family)) {
                String actualCode = findResponseCode(field.getValue());
                if (code.equals(actualCode)) return true;
            }
            if (payloadMatches(field.getValue(), family, code)) return true;
        }
        return false;
    }

    private static void validateObservedResponses(CanonicalTestData testData, JsonNode payload,
                                                  Atl105ResponseCodeFamilyOracle responseOracle,
                                                  ValidationResult result) {
        if (testData == null || payload == null) return;
        if (payload.isObject()) {
            var fields = payload.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                String key = normalize(field.getKey());
                String family = responseRootFamily(key);
                if (family != null) {
                    String code = findResponseCode(field.getValue());
                    if (code != null) {
                        ValidationResult codeResult = responseOracle.validate(family, code);
                        codeResult.errors().forEach(error -> result.addError(SOURCE,
                            "AI Test Data " + testData.getId() + ": " + error.reason()));
                    }
                } else if (hasDirectResponseCode(field.getValue())) {
                    result.addError(SOURCE, "AI Test Data " + testData.getId()
                        + " contains Element 83 ResponseCode under unrecognized response family root " + field.getKey());
                }
                validateObservedResponses(testData, field.getValue(), responseOracle, result);
            }
        } else if (payload.isArray()) {
            for (JsonNode child : payload) validateObservedResponses(testData, child, responseOracle, result);
        }
    }

    private static boolean hasDirectResponseCode(JsonNode node) {
        if (node == null || !node.isObject()) return false;
        var fields = node.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            if (RESPONSE_CODE_FIELD_NAMES.contains(normalize(field.getKey())) && field.getValue().isValueNode()) return true;
        }
        return false;
    }

    private static String responseRootFamily(String rootName) {
        if (rootName.contains("financialresponse") || rootName.contains("financialtransactionresponse")
            || rootName.contains("loyaltycardtransactionresponse")
            || rootName.contains("ecatelecheckservicetransactionresponse")) return "financial";
        if (rootName.contains("approvedtotalsresponse") || rootName.contains("declinedtotalsresponse")
            || rootName.equals("totalsresponse")) return "totals";
        if (rootName.contains("electronicmailresponse")) return "electronic-mail";
        if (rootName.contains("communicationstestresponse")) return "communications-test";
        if (rootName.contains("proprietarydataloadresponse") || rootName.contains("proprietaryloadresponse")) return "proprietary-load";
        if (rootName.contains("transarmorkey") || rootName.contains("transarmorloadresponse")) return "transarmor";
        if (rootName.contains("capublickeyfileloadresponse") || rootName.contains("emvkeyloadresponse")) return "emv-key-load";
        return null;
    }

    private static String findResponseCode(JsonNode response) {
        if (response == null) return null;
        if (response.isObject()) {
            var fields = response.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                if (RESPONSE_CODE_FIELD_NAMES.contains(normalize(field.getKey())) && field.getValue().isValueNode()) {
                    return field.getValue().asText();
                }
            }
            fields = response.fields();
            while (fields.hasNext()) {
                String found = findResponseCode(fields.next().getValue());
                if (found != null) return found;
            }
        } else if (response.isArray()) {
            for (JsonNode child : response) {
                String found = findResponseCode(child);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static String packageId(CanonicalArtifactPackage pkg) {
        return pkg == null || pkg.getManifest() == null || pkg.getManifest().getPackageId() == null
            ? "element-83-ai-coverage" : pkg.getManifest().getPackageId();
    }

    private static String normalize(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? List.of() : values;
    }
}
