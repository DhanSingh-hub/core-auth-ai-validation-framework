package com.coreauth.validator.canonical;

import java.util.List;

/** Producer-neutral contract for an AI-generated artifact package. */
public final class CanonicalArtifactPackage {
    private PackageManifest manifest;
    private List<CanonicalRequirement> businessRequirements;
    private List<CanonicalScenario> testScenarios;
    private List<CanonicalTestCase> testCases;
    private List<CanonicalTestData> testData;
    private List<RequirementCrosswalkEntry> requirementCrosswalk;

    public PackageManifest getManifest() { return manifest; }
    public void setManifest(PackageManifest manifest) { this.manifest = manifest; }
    public List<CanonicalRequirement> getBusinessRequirements() { return businessRequirements; }
    public void setBusinessRequirements(List<CanonicalRequirement> businessRequirements) { this.businessRequirements = businessRequirements; }
    public List<CanonicalScenario> getTestScenarios() { return testScenarios; }
    public void setTestScenarios(List<CanonicalScenario> testScenarios) { this.testScenarios = testScenarios; }
    public List<CanonicalTestCase> getTestCases() { return testCases; }
    public void setTestCases(List<CanonicalTestCase> testCases) { this.testCases = testCases; }
    public List<CanonicalTestData> getTestData() { return testData; }
    public void setTestData(List<CanonicalTestData> testData) { this.testData = testData; }
    public List<RequirementCrosswalkEntry> getRequirementCrosswalk() { return requirementCrosswalk; }
    public void setRequirementCrosswalk(List<RequirementCrosswalkEntry> requirementCrosswalk) { this.requirementCrosswalk = requirementCrosswalk; }
}