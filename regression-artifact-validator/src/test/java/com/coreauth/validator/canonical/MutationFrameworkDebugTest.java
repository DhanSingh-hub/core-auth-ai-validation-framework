package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * MutationFrameworkDebugTest: Debug the mutation framework
 */
@DisplayName("Mutation Framework Debug Tests")
class MutationFrameworkDebugTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Test
  void debugMutationDetection() {
    // Create a valid package with Segment 100 test data
    CanonicalArtifactPackage validPackage = createValidSegment100Package();

    System.out.println("Valid Package Test Data Count: " + validPackage.getTestData().size());

    // Get mutations
    List<Segment100MutationTester.MutationCase> mutations = Segment100MutationTester.generateStandardMutations();
    System.out.println("Standard Mutations: " + mutations.size());

    // Test first mutation
    Segment100MutationTester.MutationCase mut001 = mutations.get(0);
    System.out.println("\nTesting Mutation: " + mut001);

    Segment100MutationTester.MutationResult result = Segment100MutationTester.testMutation(mut001, validPackage);

    System.out.println("Mutation Result: detected=" + result.detected() + ", reason=" + result.detectionReason());
    assertThat(result.detected()).as("MUT-001 should be detected").isTrue();
  }

  @Test
  void debugRunFullSuite() {
    // Create a valid package with Segment 100 test data
    CanonicalArtifactPackage validPackage = createValidSegment100Package();

    // Get mutations
    List<Segment100MutationTester.MutationCase> mutations = Segment100MutationTester.generateStandardMutations();

    // Run full suite
    Segment100MutationTester.MutationReport report = Segment100MutationTester.runMutationSuite(validPackage, mutations);

    System.out.println("\nMutation Report:");
    System.out.println("  Total Mutations: " + report.metrics().totalMutations());
    System.out.println("  Detected: " + report.metrics().detectedMutations());
    System.out.println("  Detection Rate: " + report.metrics().detectionRate() + "%");

    assertThat(report.metrics().detectionRate()).as("Detection rate should be > 0").isGreaterThan(0);
  }

  private CanonicalArtifactPackage createValidSegment100Package() {
    CanonicalTestData td = new CanonicalTestData();
    td.setId("TEST-SEG100-VALID-001");

    // Create valid payload
    ObjectNode payload = mapper.createObjectNode();
    ObjectNode request = mapper.createObjectNode();
    ObjectNode ds1 = mapper.createObjectNode();
    ObjectNode ds2 = mapper.createObjectNode();
    ObjectNode segment = mapper.createObjectNode();

    ds1.put("messageFormatVersionIdentifier", "ATL105");
    ds1.put("numberOfSegments", "01"); // 1 segment in dataSection2

    segment.put("segmentType", "100");
    segment.put("terminalIdentifier", "TERM123");
    segment.put("promptCode", "POS");
    segment.put("sequenceNumber", "100001");
    segment.put("partialApprovalIndicator", "0");

    ds2.set("standardSegment", segment);
    request.set("dataSection1", ds1);
    request.set("dataSection2", ds2);
    payload.set("request", request);

    ObjectNode controls = mapper.createObjectNode();
    controls.put("validateCoreFields", true);
    payload.set("testControls", controls);

    td.setPayload(payload);
    td.setExpectedValidation("PASS");

    CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
    pkg.setTestData(List.of(td));
    return pkg;
  }
}
