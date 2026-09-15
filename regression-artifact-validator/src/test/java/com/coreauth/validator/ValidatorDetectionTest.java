package com.coreauth.validator;

import com.coreauth.validator.canonical.*;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * ValidatorDetectionTest: Debug why mutations aren't being detected
 */
@DisplayName("Validator Mutation Detection Tests")
class ValidatorDetectionTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Test
  void validPayloadPasses() {
    // Create valid test data
    ObjectNode payload = createValidPayload();
    CanonicalTestData td = createTestData("VALID-001", payload, "PASS");
    CanonicalArtifactPackage pkg = createPackage(List.of(td));

    // Validate
    ValidationResult result = new Segment100PayloadValidator().validate(pkg);

    System.out.println("Valid Payload Errors: " + result.errors());
    assertThat(result.errors()).isEmpty();
  }

  @Test
  void segmentTypeViolationDetected() {
    // Create payload with wrong segment type
    ObjectNode payload = createValidPayload();
    ((ObjectNode) payload.at("/request/dataSection2/standardSegment")).put("segmentType", "101");

    CanonicalTestData td = createTestData("MUTATED-001", payload, "FAIL");
    CanonicalArtifactPackage pkg = createPackage(List.of(td));

    // Validate
    ValidationResult result = new Segment100PayloadValidator().validate(pkg);

    System.out.println("Segment Type Violation Errors: " + result.errors());
    System.out.println("Segment Type Violation Error Details:");
    result.errors().forEach(e -> System.out.println("  - " + e.reason()));

    assertThat(result.errors()).isNotEmpty();
  }

  @Test
  void sequenceNumberFormatViolationDetected() {
    // Create payload with wrong sequence number format (5 digits instead of 6)
    ObjectNode payload = createValidPayload();
    ((ObjectNode) payload.at("/request/dataSection2/standardSegment")).put("sequenceNumber", "10001");

    CanonicalTestData td = createTestData("MUTATED-002", payload, "FAIL");
    CanonicalArtifactPackage pkg = createPackage(List.of(td));

    // Validate
    ValidationResult result = new Segment100PayloadValidator().validate(pkg);

    System.out.println("Sequence Number Format Errors: " + result.errors());
    assertThat(result.errors()).isNotEmpty();
  }

  @Test
  void messageFormatViolationDetected() {
    // Create payload with wrong message format
    ObjectNode payload = createValidPayload();
    ((ObjectNode) payload.at("/request/dataSection1")).put("messageFormatVersionIdentifier", "ATL104");

    CanonicalTestData td = createTestData("MUTATED-004", payload, "FAIL");
    CanonicalArtifactPackage pkg = createPackage(List.of(td));

    // Validate
    ValidationResult result = new Segment100PayloadValidator().validate(pkg);

    System.out.println("Message Format Errors: " + result.errors());
    assertThat(result.errors()).isNotEmpty();
  }

  @Test
  void terminalIdOmissionDetected() {
    // Create payload without terminal identifier
    ObjectNode payload = createValidPayload();
    ((ObjectNode) payload.at("/request/dataSection2/standardSegment")).remove("terminalIdentifier");

    CanonicalTestData td = createTestData("MUTATED-005", payload, "FAIL");
    CanonicalArtifactPackage pkg = createPackage(List.of(td));

    // Validate
    ValidationResult result = new Segment100PayloadValidator().validate(pkg);

    System.out.println("Terminal ID Omission Errors: " + result.errors());
    assertThat(result.errors()).isNotEmpty();
  }

  private ObjectNode createValidPayload() {
    ObjectNode payload = mapper.createObjectNode();
    ObjectNode request = mapper.createObjectNode();
    ObjectNode ds1 = mapper.createObjectNode();
    ObjectNode ds2 = mapper.createObjectNode();
    ObjectNode segment = mapper.createObjectNode();

    // Build valid structure
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

    // Enable validation
    ObjectNode controls = mapper.createObjectNode();
    controls.put("validateCoreFields", true);
    payload.set("testControls", controls);

    return payload;
  }

  private CanonicalTestData createTestData(String id, ObjectNode payload, String expectedValidation) {
    CanonicalTestData td = new CanonicalTestData();
    td.setId(id);
    td.setPayload(payload);
    td.setExpectedValidation(expectedValidation);
    return td;
  }

  private CanonicalArtifactPackage createPackage(List<CanonicalTestData> testData) {
    CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
    pkg.setTestData(testData);
    return pkg;
  }
}
