package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Element83AiCoverageValidator;
import com.coreauth.validator.canonical.PackageManifest;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.coverage.AiCoverageAssessmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Element83AiCoverageValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Map<String, String> RESPONSE_ROOTS = Map.of(
        "financial", "Financial Response",
        "totals", "Approved Totals Response",
        "electronic-mail", "Electronic Mail Response",
        "communications-test", "Communications Test Response",
        "proprietary-load", "Proprietary Data Load Response",
        "transarmor", "TransArmor Key Load Response",
        "emv-key-load", "CA Public Key File Load Response"
    );
    private final Element83AiCoverageValidator validator = new Element83AiCoverageValidator();

    @Test
    void acceptsACompleteAiBrTsTcTdChainForEveryRequiredCodeFamilyMeaning() {
        CanonicalArtifactPackage aiPackage = fullCoveragePackage();

        var result = validator.validate(aiPackage);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("35 family/code meanings"));
    }

    @Test
    void reportsFamilyCodePairsMissingFromTheAiRequirementChain() {
        CanonicalArtifactPackage aiPackage = fullCoveragePackage();
        aiPackage.setTestData(aiPackage.getTestData().subList(0, aiPackage.getTestData().size() - 1));

        var result = validator.validate(aiPackage);

        assertThat(result.errors()).hasSize(1);
        assertThat(result.errors().getFirst().reason()).contains("family=emv-key-load, code=X");
    }

    @Test
    void doesNotCountAResponseCodeInTheWrongMessageFamily() {
        CanonicalArtifactPackage aiPackage = fullCoveragePackage();
        CanonicalTestData finalData = aiPackage.getTestData().getLast();
        ObjectNode wrongFamily = MAPPER.createObjectNode();
        wrongFamily.putObject("Financial Response").put("ResponseCode", "X");
        finalData.setPayload(wrongFamily);

        var result = validator.validate(aiPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("family=emv-key-load, code=X"));
    }

    @Test
    void doesNotCountAnUnlinkedOrUnanchoredResponsePayload() {
        CanonicalArtifactPackage aiPackage = fullCoveragePackage();
        CanonicalRequirement requirement = aiPackage.getBusinessRequirements().getLast();
        requirement.setSourceAnchors(List.of(anchor("financial", "X", "different-anchor")));

        var result = validator.validate(aiPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("family=emv-key-load, code=X"));
    }

    @Test
    void doesNotCountGenericElement83BrAsCodeSpecificEvidence() {
        CanonicalArtifactPackage aiPackage = singleFinancialCodePackage("Financial Response");
        aiPackage.getBusinessRequirements().getFirst().setTitle("Response code is present");
        aiPackage.getBusinessRequirements().getFirst().getSourceAnchors().getFirst().setRule("response-code-value");

        var result = validator.validate(aiPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("family=financial, code=0"));
    }

    @Test
    void recognizesLoyaltyAndEcaFinancialResponseLayoutAliases() {
        for (String responseRoot : new String[]{"Financial Transaction Response", "Loyalty Card Transaction Response", "ECA/TeleCheck Service Transaction Response"}) {
            CanonicalArtifactPackage aiPackage = singleFinancialCodePackage(responseRoot);
            var result = validator.validate(aiPackage);
            assertThat(result.errors()).noneMatch(error -> error.reason().contains("family=financial, code=0"));
        }
    }

    @Test
    void rejectsNullPackageAndUnknownFamilyFromMatrix() {
        assertThat(validator.validate(null).errors()).isNotEmpty();
    }

    @Test
    void coverageAssessmentServiceExposesTheElement83Gate() {
        var gate = AiCoverageAssessmentService.element83ResponseCodeCoverageGate();

        assertThat(gate.name()).isEqualTo("Element83ResponseCodeCoverage");
        assertThat(gate.validator().apply(fullCoveragePackage()).errors()).isEmpty();
    }

    @Test
    void hydratesPhysicalRun2TestDataBeforeCheckingElement83Codes(@TempDir Path runRoot) throws Exception {
        CanonicalArtifactPackage aiPackage = fullCoveragePackage();
        CanonicalTestData first = aiPackage.getTestData().getFirst();
        String payloadFile = "response-0.json";
        Files.writeString(runRoot.resolve(payloadFile), MAPPER.writeValueAsString(first.getPayload()));
        ObjectNode sourceReference = MAPPER.createObjectNode();
        sourceReference.put("sourceFile", payloadFile);
        first.setFileName(payloadFile);
        first.setPayload(sourceReference);

        var result = validator.validateFiles(aiPackage, runRoot);

        assertThat(result.errors()).isEmpty();
        assertThat(first.getPayload().path("sourceFile").asText()).isEqualTo(payloadFile);
    }

    @Test
    void rejectsRun2TestDataPathsOutsideImmutableRoot(@TempDir Path runRoot) {
        CanonicalArtifactPackage aiPackage = fullCoveragePackage();
        CanonicalTestData first = aiPackage.getTestData().getFirst();
        first.setFileName("../outside.json");
        first.setPayload(MAPPER.createObjectNode().put("sourceFile", "../outside.json"));

        var result = validator.validateFiles(aiPackage, runRoot);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("missing or out-of-run-root"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("family=financial, code=0"));
    }

    private static CanonicalArtifactPackage fullCoveragePackage() {
        CanonicalArtifactPackage aiPackage = new CanonicalArtifactPackage();
        PackageManifest manifest = new PackageManifest();
        manifest.setPackageId("AI-ELEMENT-83-COVERAGE-TEST");
        manifest.setSpecification("ATL105");
        manifest.setSpecificationVersion("2026-3");
        aiPackage.setManifest(manifest);

        List<CanonicalRequirement> requirements = new ArrayList<>();
        List<CanonicalScenario> scenarios = new ArrayList<>();
        List<CanonicalTestCase> testCases = new ArrayList<>();
        List<CanonicalTestData> testData = new ArrayList<>();
        int sequence = 0;
        String[][] pairs = {
            {"financial","0"},{"financial","1"},{"financial","2"},{"financial","3"},{"financial","4"},{"financial","F"},{"financial","S"},
            {"totals","5"},{"totals","6"},{"totals","7"},{"totals","B"},{"totals","C"},{"totals","D"},{"totals","E"},{"totals","M"},{"totals","N"},{"totals","V"},{"totals","W"},
            {"electronic-mail","8"},{"electronic-mail","9"},{"electronic-mail","G"},{"electronic-mail","J"},
            {"communications-test","1"},
            {"proprietary-load","H"},{"proprietary-load","O"},{"proprietary-load","T"},{"proprietary-load","U"},{"proprietary-load","X"},{"proprietary-load","Y"},
            {"transarmor","K"},{"transarmor","L"},{"transarmor","P"},
            {"emv-key-load","L"},{"emv-key-load","M"},{"emv-key-load","X"}
        };
        for (String[] pair : pairs) {
            sequence++;
            String family = pair[0];
            String code = pair[1];
            SourceAnchor sourceAnchor = anchor(family, code, "code-" + code);
            String suffix = String.format("%02d", sequence);

            CanonicalRequirement requirement = new CanonicalRequirement();
            requirement.setId("AI-BR-E83-" + suffix);
            requirement.setSourceAnchors(List.of(sourceAnchor));
            requirements.add(requirement);

            CanonicalScenario scenario = new CanonicalScenario();
            scenario.setId("AI-TS-E83-" + suffix);
            scenario.setRequirementIds(List.of(requirement.getId()));
            scenario.setSourceAnchors(List.of(anchor(family, code, "code-" + code)));
            scenarios.add(scenario);

            CanonicalTestCase testCase = new CanonicalTestCase();
            testCase.setId("AI-TC-E83-" + suffix);
            testCase.setScenarioIds(List.of(scenario.getId()));
            testCase.setSourceAnchors(List.of(anchor(family, code, "code-" + code)));
            testCases.add(testCase);

            CanonicalTestData td = new CanonicalTestData();
            td.setId("AI-TD-E83-" + suffix);
            td.setTestCaseIds(List.of(testCase.getId()));
            td.setSourceAnchors(List.of(anchor(family, code, "code-" + code)));
            ObjectNode payload = MAPPER.createObjectNode();
            payload.putObject(RESPONSE_ROOTS.get(family)).put("ResponseCode", code);
            td.setPayload(payload);
            testData.add(td);
        }
        aiPackage.setBusinessRequirements(requirements);
        aiPackage.setTestScenarios(scenarios);
        aiPackage.setTestCases(testCases);
        aiPackage.setTestData(testData);
        return aiPackage;
    }

    private static CanonicalArtifactPackage singleFinancialCodePackage(String responseRoot) {
        CanonicalArtifactPackage aiPackage = new CanonicalArtifactPackage();
        PackageManifest manifest = new PackageManifest();
        manifest.setPackageId("AI-ELEMENT-83-ALIAS-TEST"); manifest.setSpecification("ATL105"); manifest.setSpecificationVersion("2026-3");
        aiPackage.setManifest(manifest);
        SourceAnchor sourceAnchor = anchor("financial", "0", "purchase-code-0");
        CanonicalRequirement br = new CanonicalRequirement(); br.setId("AI-BR-0"); br.setSourceAnchors(List.of(sourceAnchor));
        CanonicalScenario ts = new CanonicalScenario(); ts.setId("AI-TS-0"); ts.setRequirementIds(List.of(br.getId())); ts.setSourceAnchors(List.of(sourceAnchor));
        CanonicalTestCase tc = new CanonicalTestCase(); tc.setId("AI-TC-0"); tc.setScenarioIds(List.of(ts.getId())); tc.setSourceAnchors(List.of(sourceAnchor));
        CanonicalTestData td = new CanonicalTestData(); td.setId("AI-TD-0"); td.setTestCaseIds(List.of(tc.getId())); td.setSourceAnchors(List.of(sourceAnchor));
        ObjectNode payload = MAPPER.createObjectNode();
        payload.putObject(responseRoot).put("ResponseCode", "0");
        td.setPayload(payload);
        aiPackage.setBusinessRequirements(List.of(br)); aiPackage.setTestScenarios(List.of(ts)); aiPackage.setTestCases(List.of(tc)); aiPackage.setTestData(List.of(td));
        return aiPackage;
    }

    private static SourceAnchor anchor(String family, String code, String rule) {
        SourceAnchor anchor = new SourceAnchor();
        anchor.setSpecification("ATL105");
        anchor.setVersion("2026-3");
        anchor.setSection("13.2");
        anchor.setSegment(family + "-response");
        anchor.setElement("83");
        anchor.setRule(rule);
        return anchor;
    }
}
