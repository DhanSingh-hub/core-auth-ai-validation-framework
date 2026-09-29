package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTestCaseFilter;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment100CoverageAnalyzer;
import com.coreauth.validator.canonical.Segment100CoverageReportWriter;
import com.coreauth.validator.canonical.Segment100CoverageRelease;
import com.coreauth.validator.canonical.TcpIpMessageLengthValidator;
import com.coreauth.validator.canonical.TcpIpHeaderValidator;
import com.coreauth.validator.canonical.MessageFormatVersionValidator;
import com.coreauth.validator.canonical.DataSection1StructureValidator;
import com.coreauth.validator.canonical.Segment100IdentityValidator;
import com.coreauth.validator.canonical.TerminalIdentifierValidator;
import com.coreauth.validator.canonical.PromptCodeValidator;
import com.coreauth.validator.canonical.AccountNumberEntryValidator;
import com.coreauth.validator.canonical.SequenceLifecycleValidator;
import com.coreauth.validator.canonical.PartialApprovalValidator;
import com.coreauth.validator.canonical.LifecycleCorrelationValidator;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.Segment100CompatibilityValidator;
import com.coreauth.validator.canonical.Segment100PayloadValidator;
import com.coreauth.validator.canonical.Segment100SerializationValidator;
import com.coreauth.validator.canonical.Segment100ArtifactComparison;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CanonicalTraceabilityValidatorTest {

        private static final String SERIALIZATION_PAYLOAD = """
                        {
                            "request": {
                                "tcpIpHeader": {"messageLength":"CALCULATE_FROM_FOLLOWING_PAYLOAD","byteOrder":"network-big-endian","tpduProtocolId":"60","tpduDestinationAddress":"0000","tpduSourceAddress":"0000"},
                                "dataSection1": {"messageFormatVersionIdentifier":"ATL105","numberOfSegments":"01","fieldSeparators":{"betweenElements55And63":true,"afterElement63":true}},
                                "dataSection2": {"standardSegment": {"segmentType":"100","segmentLength":"CALCULATE_FROM_SEGMENT_CONTENT","informationByte":"0","terminalIdentifier":"TSCA000001","promptCode":"0020","accountNumber":"4111111111111111","cardDiscretionaryBlockData":"","encryptedPinBlockData":"","pumpLaneNumber":"","fuelPurchaseAmount":"","nonfuelAmount":"00001000","taxAmount":"","cashAmount":"","sequenceNumber":"100001","approvalNumber":"","localDateTime":"0101261200","partialApprovalIndicator":"1"}},
                                "dataSection3": []
                            },
                            "testControls": {"validateSerialization":true,"serialization":{"trailingOptionalFieldsOmitted":true}}
                        }
                        """;

    private static final String VALID_PACKAGE = """
            {
              "manifest": {"packageId":"ATL105-SEG100-001","specification":"ATL105","specificationVersion":"2026"},
              "businessRequirements": [{"id":"BR-AI-017","title":"ATL105 identifier","category":"core-structure","applicability":"all-financial-requests","priority":"high","executionStatus":"EXECUTION_READY","sourceAnchors":[{"specification":"ATL105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}]}],
              "requirementCrosswalk": [{"testRequirementId":"BR-BASELINE-017","aiRequirementIds":["BR-AI-017"],"matchStatus":"CONFIRMED","matchReason":"Shared ATL105 source anchor"}],
              "testScenarios": [{"id":"SCN-TV-009","requirementIds":["BR-AI-017"],"sourceAnchors":[{"specification":"atl105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}]}],
              "testCases": [{"id":"TC-OTHER-42","scenarioIds":["SCN-TV-009"],"expectedOutcome":"PASS","sourceAnchors":[{"specification":"ATL105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}]}],
              "testData": [{"id":"TD-LOCAL-7","testCaseIds":["TC-OTHER-42"],"expectedValidation":"PASS","sourceAnchors":[{"specification":"ATL105","version":"2026","section":"1","element":"55","rule":"message-format-identifier"}],"payload":{"request":{"messageType":"ATL105"}}}]
            }
            """;

    @Test
    void acceptsDifferentLocalIdsWhenSourceAnchorsMatch() throws Exception {
        Path file = Files.createTempFile("atl105-canonical-", ".json");
        Files.writeString(file, VALID_PACKAGE);

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsConfirmedRequirementCrosswalkWithRealAiRequirement() throws Exception {
        Path file = Files.createTempFile("atl105-crosswalk-valid-", ".json");
        Files.writeString(file, VALID_PACKAGE);

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void requiresOwnerForDeferredSmeCrosswalkReview() throws Exception {
        Path file = Files.createTempFile("atl105-crosswalk-review-", ".json");
        Files.writeString(file, VALID_PACKAGE);

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        artifactPackage.getRequirementCrosswalk().get(0).setMatchStatus(com.coreauth.validator.canonical.RequirementMatchStatus.REVIEW_REQUIRED);
        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("reviewOwner is required"));
    }

    @Test
    void validatesStrictExecutionContractAcrossBrToTestData() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
        artifactPackage.getManifest().setArtifactContractVersion("2");
        artifactPackage.getManifest().setStrictExecutionContract(true);
        artifactPackage.getTestScenarios().get(0).setStatus(com.coreauth.validator.canonical.ArtifactStatus.EXECUTION_READY);
        artifactPackage.getTestCases().get(0).setStatus("EXECUTION_READY");
        artifactPackage.getTestCases().get(0).setTestDataFile("TD-LOCAL-7.json");
        CanonicalTestData data = artifactPackage.getTestData().get(0);
        data.setFileName("TD-LOCAL-7.json");
        data.setReadiness(com.coreauth.validator.canonical.TestDataReadiness.EXECUTABLE);
        data.setResponse(new ObjectMapper().readTree("{\"financialResponse\":{\"responseCode\":\"00\"}}"));

        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsExecutableTestDataWithoutResponseEnvelope() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
        artifactPackage.getManifest().setStrictExecutionContract(true);
        artifactPackage.getTestScenarios().get(0).setStatus(com.coreauth.validator.canonical.ArtifactStatus.EXECUTION_READY);
        artifactPackage.getTestCases().get(0).setStatus("EXECUTION_READY");
        artifactPackage.getTestCases().get(0).setTestDataFile("TD-LOCAL-7.json");
        CanonicalTestData data = artifactPackage.getTestData().get(0);
        data.setFileName("TD-LOCAL-7.json");
        data.setReadiness(com.coreauth.validator.canonical.TestDataReadiness.EXECUTABLE);

        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("response envelope"));
    }

    @Test
    void requiresNotBeforeDateWhenStatusIsScheduled() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
        artifactPackage.getBusinessRequirements().get(0).setExecutionStatus("SCHEDULED");

        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("notBeforeDate is required when status is SCHEDULED"));
    }

    @Test
    void rejectsExecutionReadyBeforeFutureNotBeforeDate() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
        artifactPackage.getBusinessRequirements().get(0).setExecutionStatus("EXECUTION_READY");
        artifactPackage.getBusinessRequirements().get(0).setNotBeforeDate("9999-06-01");

        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("cannot be EXECUTION_READY before notBeforeDate"));
    }

    @Test
    void acceptsScheduledStatusWithFutureNotBeforeDate() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
        artifactPackage.getBusinessRequirements().get(0).setExecutionStatus("SCHEDULED");
        artifactPackage.getBusinessRequirements().get(0).setNotBeforeDate("9999-06-01");

        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).noneMatch(error -> error.reason().contains("notBeforeDate")
                || error.reason().contains("EXECUTION_READY before"));
    }

    @Test
    void comparesAiRequirementsWithIndependentAnchorsNotLocalIds() throws Exception {
        Path file = Files.createTempFile("atl105-comparison-", ".json");
        Files.writeString(file, VALID_PACKAGE);

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        SourceAnchor independent = anchor("1", "55", "message-format-identifier");
        independent.setSpecification("ATL105");
        independent.setVersion("2026");
        Segment100ArtifactComparison.ComparisonReport report = new Segment100ArtifactComparison()
                .compare(artifactPackage, java.util.List.of(independent));

        assertThat(report.matched()).hasSize(1);
        assertThat(report.missing()).isEmpty();
        assertThat(report.extra()).isEmpty();
        assertThat(report.precision()).isEqualTo(1.0);
        assertThat(report.recall()).isEqualTo(1.0);
    }

    @Test
    void reportsMissingAndExtraAiRequirementAnchors() throws Exception {
        Path file = Files.createTempFile("atl105-comparison-diff-", ".json");
        Files.writeString(file, VALID_PACKAGE);

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        SourceAnchor expected = anchor("1", "63", "number-of-segments");
        expected.setSpecification("ATL105");
        expected.setVersion("2026");
        SourceAnchor actual = anchor("1", "55", "message-format-identifier");
        actual.setSpecification("ATL105");
        actual.setVersion("2026");
        Segment100ArtifactComparison.ComparisonReport report = new Segment100ArtifactComparison()
                .compare(artifactPackage, java.util.List.of(expected));

        assertThat(report.matched()).isEmpty();
        assertThat(report.missing()).contains(expected.canonicalKey());
        assertThat(report.extra()).contains(actual.canonicalKey());
        assertThat(report.hasDifferences()).isTrue();
    }

    @Test
    void rejectsMissingLinks() throws Exception {
        Path file = Files.createTempFile("atl105-canonical-invalid-", ".json");
        Files.writeString(file, VALID_PACKAGE);

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        artifactPackage.getTestScenarios().get(0).setRequirementIds(java.util.List.of("BR-MISSING"));
        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.isValid()).isFalse();
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("references missing requirement"));
    }

    @Test
    void rejectsAnchorMismatchAcrossLinkedArtifacts() throws Exception {
        Path file = Files.createTempFile("atl105-canonical-mismatch-", ".json");
        Files.writeString(file, VALID_PACKAGE);

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        com.coreauth.validator.canonical.SourceAnchor anchor = new com.coreauth.validator.canonical.SourceAnchor();
        anchor.setSpecification("ATL105");
        anchor.setVersion("2026");
        anchor.setSection("2");
        anchor.setElement("100");
        anchor.setRule("different-rule");
        artifactPackage.getTestScenarios().get(0).setSourceAnchors(java.util.List.of(anchor));

        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.isValid()).isFalse();
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("no sourceAnchor in common"));
    }

    @Test
    void validatesSegment100CompatibilityPackage() throws Exception {
        Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                "segment-100-compatibility-package.json").normalize();

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);

        assertThat(result.errors()).isEmpty();
        assertThat(artifactPackage.getBusinessRequirements()).hasSize(6);
        assertThat(artifactPackage.getTestScenarios()).hasSize(7);
        assertThat(artifactPackage.getTestCases()).hasSize(7);
        assertThat(artifactPackage.getTestData()).hasSize(7);
    }

    @Test
    void enforcesExactlyOneSegment100ForStandardFinancialRequests() throws Exception {
        Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                "segment-100-compatibility-package.json").normalize();
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        CanonicalTestData data = artifactPackage.getTestData().get(0);
        ObjectNode payload = (ObjectNode) data.getPayload();
        ArrayNode section3 = (ArrayNode) payload.path("request").path("dataSection3");
        ObjectNode duplicate = section3.addObject();
        duplicate.put("segmentType", "100");

        ValidationResult result = new Segment100CompatibilityValidator().validate(artifactPackage);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("exactly one Segment 100"));
    }

    @Test
    void delegatesSegment100CardinalityForNonStandardMessageCategories() throws Exception {
        Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                "segment-100-compatibility-package.json").normalize();
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        ObjectNode payload = (ObjectNode) artifactPackage.getTestData().get(0).getPayload();
        ObjectNode controls = (ObjectNode) payload.putIfAbsent("testControls", null);
        controls.put("messageCategory", "TOTALS_REQUEST");

        ValidationResult result = new Segment100CompatibilityValidator().validate(artifactPackage);

        assertThat(result.warnings()).anyMatch(warning -> warning.contains("TOTALS_REQUEST"));
    }

    @Test
    void validatesAndFiltersRemainingSegment100Package() throws Exception {
        Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                "segment-100-remaining-package.json").normalize();

        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
        ValidationResult result = new CanonicalTraceabilityValidator().validate(artifactPackage);
        CanonicalTestCaseFilter filter = new CanonicalTestCaseFilter();
        ValidationResult compatibility = new Segment100CompatibilityValidator().validate(artifactPackage);

        assertThat(result.errors()).isEmpty();
        assertThat(compatibility.errors()).isNotEmpty();
        assertThat(compatibility.errors()).anyMatch(error -> error.reason().contains("missing required segment"));
        assertThat(compatibility.errors()).anyMatch(error -> error.reason().contains("declares 2 segments"));
        assertThat(artifactPackage.getTestCases()).hasSize(7);
        assertThat(filter.filter(artifactPackage.getTestCases(),
                new CanonicalTestCaseFilter.Criteria("compatibility", "missing-companion", "high", "active", "FAIL")))
                .extracting("id")
                .containsExactlyInAnyOrder("TC-SEG100-EMV-NEG-001", "TC-SEG100-FLEET-NEG-001", "TC-SEG100-FUEL-NEG-001");
        assertThat(filter.filter(artifactPackage.getTestCases(),
                new CanonicalTestCaseFilter.Criteria(null, "review", null, null, null)))
                .extracting("id")
                .containsExactly("TC-SEG100-UNKNOWN-001");
    }

        @Test
        void validatesOptedInSegment100CoreFields() throws Exception {
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
                ObjectMapper mapper = new ObjectMapper();
                artifactPackage.getTestData().get(0).setPayload(mapper.readTree("""
                                {
                                    "request": {
                                        "dataSection1": {"messageFormatVersionIdentifier":"ATL105","numberOfSegments":"01"},
                                        "dataSection2": {"standardSegment": {
                                            "segmentType":"100", "terminalIdentifier":"TSCA000001", "promptCode":"0020",
                                            "sequenceNumber":"100001", "partialApprovalIndicator":"1"
                                        }}
                                    },
                                    "testControls": {"validateCoreFields":true}
                                }
                                """));

                ValidationResult result = new Segment100PayloadValidator().validate(artifactPackage);

                assertThat(result.errors()).isEmpty();
        }

        @Test
        void rejectsInvalidOptedInSegment100CoreFields() throws Exception {
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
                ObjectMapper mapper = new ObjectMapper();
                artifactPackage.getTestData().get(0).setPayload(mapper.readTree("""
                                {
                                    "request": {
                                        "dataSection1": {"messageFormatVersionIdentifier":"ATL104","numberOfSegments":"0"},
                                        "dataSection2": {"standardSegment": {
                                            "segmentType":"101", "terminalIdentifier":"bad id", "promptCode":"2",
                                            "sequenceNumber":"ABC", "partialApprovalIndicator":"9"
                                        }}
                                    },
                                    "testControls": {"validateCoreFields":true}
                                }
                                """));

                ValidationResult result = new Segment100PayloadValidator().validate(artifactPackage);

                assertThat(result.errors()).hasSize(7);
        }

            @Test
            void validatesSegment100SerializationRules() throws Exception {
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
                artifactPackage.getTestData().get(0).setPayload(new ObjectMapper().readTree(SERIALIZATION_PAYLOAD));

                ValidationResult result = new Segment100SerializationValidator().validate(artifactPackage);

                assertThat(result.errors()).isEmpty();
            }

            @Test
            void rejectsInvalidSegment100SerializationRules() throws Exception {
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
                String invalid = SERIALIZATION_PAYLOAD.replace("network-big-endian", "little-endian")
                    .replace("\"partialApprovalIndicator\":\"1\"", "\"partialApprovalIndicator\":\"\"");
                artifactPackage.getTestData().get(0).setPayload(new ObjectMapper().readTree(invalid));

                ValidationResult result = new Segment100SerializationValidator().validate(artifactPackage);

                assertThat(result.errors()).hasSize(2);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("byteOrder"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("empty trailing field"));
            }

            @Test
            void reportsSegment100RuleCoverage() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-compatibility-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);

                com.coreauth.validator.canonical.SourceAnchor covered = anchor("11.8.1", null, "section-3-required-for-emv");
                covered.setElement(null);
                covered.setSegment("130");
                com.coreauth.validator.canonical.SourceAnchor missing = anchor("12.1", "100", "wire-length-calculation");
                Segment100CoverageAnalyzer.CoverageReport report = new Segment100CoverageAnalyzer()
                        .analyze(artifactPackage, java.util.List.of(covered, missing));

                assertThat(report.count("COVERED")).isEqualTo(1);
                assertThat(report.count("MISSING")).isEqualTo(1);
                assertThat(report.isComplete()).isFalse();
            }

                @Test
                void writesSegment100CoverageReports() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                    "segment-100-compatibility-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                com.coreauth.validator.canonical.SourceAnchor covered = anchor("11.8.1", null, "section-3-required-for-emv");
                covered.setElement(null);
                covered.setSegment("130");
                Segment100CoverageAnalyzer.CoverageReport report = new Segment100CoverageAnalyzer()
                    .analyze(artifactPackage, java.util.List.of(covered));
                Path outputDirectory = Files.createTempDirectory("segment-100-coverage-");

                Segment100CoverageReportWriter.ReportFiles files = new Segment100CoverageReportWriter()
                    .write(report, "ATL105-SEG100-COMPATIBILITY-001", outputDirectory);

                assertThat(files.json()).exists();
                assertThat(files.markdown()).exists();
                assertThat(Files.readString(files.json())).contains("\"status\" : \"APPROVED\"");
                assertThat(Files.readString(files.markdown())).contains("# Segment 100 Coverage Report");
                }

            @Test
            void generatesCommittedSegment100CoverageReports() throws Exception {
                Path root = Path.of(System.getProperty("user.dir"));
                Path packageOne = root.resolve("specifications/ATL105/test-output/test-json/segment-100-compatibility-package.json");
                Path packageTwo = root.resolve("specifications/ATL105/test-output/test-json/segment-100-remaining-package.json");
                Path packageThree = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-01-message-length-package.json");
                Path packageFour = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-02-tpdu-header-package.json");
                Path packageFive = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-03-element-55-package.json");
                Path packageSix = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-04-data-section-1-package.json");
                Path packageSeven = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-05-identity-length-package.json");
                Path packageEight = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-06-terminal-identifier-package.json");
                Path packageNine = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-07-prompt-code-package.json");
                Path packageTen = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-08-account-entry-package.json");
                Path packageEleven = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-09-sequence-lifecycle-package.json");
                Path packageTwelve = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-10-partial-approval-package.json");
                Path packageThirteen = root.resolve("specifications/ATL105/test-output/test-json/segment-100-item-11-final-closure-package.json");
                Path packageFourteen = root.resolve("specifications/ATL105/test-output/test-json/segment-100-gap-closure-package.json");
                Path packageFifteen = root.resolve("specifications/ATL105/test-output/test-json/segment-100-ewic-gap-package.json");
                Path packageSixteen = root.resolve("specifications/ATL105/test-output/test-json/segment-100-response-code-package.json");
                Path catalog = root.resolve("specifications/ATL105/docs/specs/kb/segment-100/coverage/segment-100-rule-catalog.json").normalize();
                Path output = root.resolve("specifications/ATL105/test-output/traceability-matrix/segment-100");

                Segment100CoverageReportWriter.ReportFiles files = new Segment100CoverageRelease().generate(
                    java.util.List.of(packageOne, packageTwo, packageThree, packageFour, packageFive, packageSix, packageSeven, packageEight, packageNine, packageTen, packageEleven, packageTwelve, packageThirteen, packageFourteen, packageFifteen, packageSixteen), catalog, output);

                assertThat(files.json()).exists();
                assertThat(files.markdown()).exists();
                assertThat(Files.readString(files.json())).contains("SEG100-R-001");
                assertThat(Files.readString(files.markdown())).contains("APPROVED_WITH_REVIEW_ITEMS");
            }

            @Test
            void validatesSegment100MessageLengthCases() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-01-message-length-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                TcpIpMessageLengthValidator validator = new TcpIpMessageLengthValidator();

                CanonicalTestData valid = artifactPackage.getTestData().get(0);
                artifactPackage.setTestData(java.util.List.of(valid));
                assertThat(validator.validate(artifactPackage).errors()).isEmpty();

                artifactPackage.setTestData(java.util.List.of(
                        new CanonicalPackageLoader().load(file).getTestData().get(1),
                        new CanonicalPackageLoader().load(file).getTestData().get(2)));
                ValidationResult invalid = validator.validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(invalid.errors()).hasSize(3);
                assertThat(invalid.errors()).anyMatch(error -> error.reason().contains("declares 3 payload bytes"));
                assertThat(invalid.errors()).anyMatch(error -> error.reason().contains("byteOrder"));
            }

            @Test
            void validatesSegment100TpduHeaderCases() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-02-tpdu-header-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                TcpIpHeaderValidator validator = new TcpIpHeaderValidator();

                assertThat(traceability.errors()).isEmpty();
                assertThat(validator.validate(artifactPackage).errors()).hasSize(3);
                assertThat(validator.validate(artifactPackage).errors())
                        .anyMatch(error -> error.reason().contains("tpduProtocolId"))
                        .anyMatch(error -> error.reason().contains("tpduDestinationAddress"))
                        .anyMatch(error -> error.reason().contains("tpduSourceAddress"));
            }

            @Test
            void validatesSegment100Element55Cases() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-03-element-55-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new MessageFormatVersionValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(1);
                assertThat(result.errors().get(0).reason()).contains("Element 55");
            }

            @Test
            void validatesSegment100Element63AndSeparators() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-04-data-section-1-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new DataSection1StructureValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(3);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("declares 1 segments"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("between Elements 55 and 63"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("after Element 63"));
            }

            @Test
            void acceptsUnpaddedSingleDigitElement63() throws Exception {
                ValidationResult result = validateElement63("1", "");

                assertThat(result.errors()).isEmpty();
            }

            @Test
            void rejectsElement63OutsideStandardRange() throws Exception {
                assertThat(validateElement63("0", "").errors())
                        .anyMatch(error -> error.reason().contains("must be 1-7 for a standard request"));
                assertThat(validateElement63("08", "").errors())
                        .anyMatch(error -> error.reason().contains("must be 1-7 for a standard request"));
                assertThat(validateElement63("123", "").errors())
                        .anyMatch(error -> error.reason().contains("must be one or two digits"));
            }

            @Test
            void capsElement63AtSixForEmvRequests() throws Exception {
                ValidationResult result = validateElement63("07", "{\"segmentType\":\"130\"}");

                assertThat(result.errors())
                        .anyMatch(error -> error.reason().contains("must be 1-6 for an EMV request"));
            }

            private ValidationResult validateElement63(String numberOfSegments, String section3Segment) throws Exception {
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(writePackage(VALID_PACKAGE));
                artifactPackage.getTestData().get(0).setPayload(new ObjectMapper().readTree("""
                        {
                          "request": {
                            "dataSection1": {"numberOfSegments": "%s",
                              "fieldSeparators": {"betweenElements55And63": true, "afterElement63": true}},
                            "dataSection2": {"standardSegment": {"segmentType": "100"}},
                            "dataSection3": [%s]
                          },
                          "testControls": {"validateDataSection1": true}
                        }
                        """.formatted(numberOfSegments, section3Segment)));
                return new DataSection1StructureValidator().validate(artifactPackage);
            }

            @Test
            void validatesSegment100IdentityAndLength() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-05-identity-length-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new Segment100IdentityValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(2);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("Segment Type"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("Segment Length"));
            }

            @Test
            void validatesTerminalIdentifierAndDependencies() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-06-terminal-identifier-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new TerminalIdentifierValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(3);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("1-22 alphanumeric"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("load"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("declared components"));
            }

            @Test
            void validatesPromptCodeAndDependencies() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-07-prompt-code-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new PromptCodeValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(2);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("unsupported transaction type"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("special, not a financial"));
            }

            @Test
            void validatesAccountNumberEntryDependencies() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-08-account-entry-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new AccountNumberEntryValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(2);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("track2"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("tokenized mode"));
            }

            @Test
            void validatesSequenceAndLifecycleCorrelation() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-09-sequence-lifecycle-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new SequenceLifecycleValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(3);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("six digits"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("original Sequence Number"));
            }

            @Test
            void validatesPartialApprovalIndicatorAndContext() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-10-partial-approval-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult result = new PartialApprovalValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(result.errors()).hasSize(2);
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("must be 0, 1, or 5"));
                assertThat(result.errors()).anyMatch(error -> error.reason().contains("Amex prepaid"));
            }

            @Test
            void validatesFinalClosureArtifacts() throws Exception {
                Path file = Path.of(System.getProperty("user.dir"), "specifications", "ATL105", "test-output", "test-json",
                        "segment-100-item-11-final-closure-package.json").normalize();
                CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(file);
                ValidationResult traceability = new CanonicalTraceabilityValidator().validate(artifactPackage);
                ValidationResult serialization = new Segment100SerializationValidator().validate(artifactPackage);
                ValidationResult lifecycle = new LifecycleCorrelationValidator().validate(artifactPackage);

                assertThat(traceability.errors()).isEmpty();
                assertThat(serialization.errors()).isEmpty();
                assertThat(lifecycle.errors()).hasSize(1);
                assertThat(lifecycle.errors().get(0).reason()).contains("reuse original Sequence Number");
            }

            private static com.coreauth.validator.canonical.SourceAnchor anchor(String section, String element, String rule) {
                com.coreauth.validator.canonical.SourceAnchor anchor = new com.coreauth.validator.canonical.SourceAnchor();
                anchor.setSpecification("ATL105");
                anchor.setVersion("2026-3");
                anchor.setSection(section);
                anchor.setElement(element);
                anchor.setRule(rule);
                return anchor;
            }

        private static Path writePackage(String json) throws Exception {
                Path file = Files.createTempFile("atl105-payload-", ".json");
                Files.writeString(file, json);
                return file;
        }
}
