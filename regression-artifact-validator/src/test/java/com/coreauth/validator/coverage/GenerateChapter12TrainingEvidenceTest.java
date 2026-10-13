package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateChapter12TrainingEvidenceTest {
    @Test
    void persistsRealExecutionsAndSourceContinuousChainsWithoutClaimingWholeSpecCoverage(@TempDir Path temp) throws Exception {
        Path packageFile = temp.resolve("package.json");
        Path reportFile = temp.resolve("report.json");
        var report = new GenerateChapter12TrainingEvidence().generate(packageFile, reportFile);
        assertThat(report.path("completeSpecCoverage").asBoolean()).isFalse();
        assertThat(report.path("fullMessageIntakeWired").asBoolean()).isFalse();
        assertThat(report.path("summary").path("businessRequirements").asInt()).isEqualTo(1271);
        assertThat(report.path("summary").path("executedObservations").asInt()).isEqualTo(1299);
        assertThat(report.path("summary").path("approvedRequirements").asInt()).isZero();
        assertThat(report.path("sourceSha256").asText()).hasSize(64);
        assertThat(report.path("sourceLocalProbesSha256").asText()).hasSize(64);
        var facets = report.path("sourceFacetReconciliation");
        assertThat(facets.path("facetCategorySlots").asInt()).isEqualTo(343);
        assertThat(facets.path("atomicClauseDenominatorEstablished").asBoolean()).isFalse();
        assertThat(facets.path("fullyClosedFacetCategories").asInt()).isZero();
        assertThat(facets.path("boundedFacetCategories").asInt()).isEqualTo(7);
        assertThat(facets.path("unreconciledFacetCategories").asInt()).isEqualTo(336);
        assertThat(facets.path("sourceOwnerAndExternalBoundaries")).hasSize(14);
        assertThat(facets.path("sourceTextUnits").asInt()).isEqualTo(451);
        assertThat(facets.path("retainedSourceLines").asInt()).isEqualTo(4899);
        var source = java.nio.file.Files.readAllLines(com.coreauth.validator.paths.Atl105Paths.docs().resolve("specs").resolve("extracted_text.txt"));
        var atomic = report.path("atomicSourceClauseReconciliation");
        assertThat(atomic.path("globalAtomicDenominatorEstablished").asBoolean()).isFalse();
        assertThat(atomic.path("wholeChapterComplete").asBoolean()).isFalse();
        assertThat(atomic.path("enumerationScope").asText()).contains("Partial");
        assertThat(atomic.path("enumeratedSections")).hasSize(4);
        assertThat(atomic.path("unenumeratedSections")).hasSize(45);
        assertThat(atomic.path("summary").path("enumeratedClauses").asInt()).isEqualTo(125);
        assertThat(atomic.path("summary").path("boundedImplementation").asInt()).isEqualTo(85);
        assertThat(atomic.path("summary").path("openImplementation").asInt()).isEqualTo(30);
        assertThat(atomic.path("summary").path("sourceConflict").asInt()).isEqualTo(7);
        assertThat(atomic.path("summary").path("externalEvidence").asInt()).isEqualTo(3);
        var atomicIds = new java.util.HashSet<String>();
        var executedByCase = new java.util.HashMap<String, com.fasterxml.jackson.databind.JsonNode>();
        for (var execution : report.path("executions")) executedByCase.put(execution.path("testCaseId").asText(), execution);
        int atomicLinks = 0;
        for (var clause : atomic.path("clauses")) {
            assertThat(atomicIds.add(clause.path("id").asText())).isTrue();
            assertThat(clause.path("requirement").asText()).isNotBlank();
            assertThat(clause.path("boundary").asText()).isNotBlank();
            int start = clause.path("sourceEvidence").path("startLine").asInt();
            int end = clause.path("sourceEvidence").path("endLine").asInt();
            assertThat(clause.path("sourceEvidence").path("lines")).hasSize(end - start + 1);
            for (int line = start; line <= end; line++)
                assertThat(clause.path("sourceEvidence").path("lines").get(line - start).asText()).isEqualTo(source.get(line - 1));
            if ("BOUNDED_EXECUTION".equals(clause.path("status").asText())) {
                assertThat(java.nio.file.Files.isRegularFile(Path.of(clause.path("implementation").asText()))).isTrue();
                assertThat(clause.path("executedChains")).isNotEmpty();
            }
            for (var chain : clause.path("executedChains")) {
                var execution = executedByCase.get(chain.path("testCaseId").asText());
                assertThat(execution).isNotNull();
                for (String key : java.util.List.of("businessRequirementId", "scenarioId", "testCaseId", "testDataId"))
                    assertThat(chain.path(key).asText()).isNotBlank().isEqualTo(execution.path(key).asText());
                assertThat(chain.path("actualStatus")).isEqualTo(chain.path("expectedStatus"));
                if (chain.has("expectedTargetStatus"))
                    assertThat(chain.path("actualTargetStatus")).isEqualTo(chain.path("expectedTargetStatus"));
                atomicLinks++;
            }
        }
        assertThat(atomicLinks).isEqualTo(253);
        var candidates = atomic.path("remainingSourceClauseInventory");
        assertThat(candidates.path("sectionsScanned").asInt()).isEqualTo(49);
        assertThat(candidates.path("candidateRecords").asInt()).isEqualTo(683);
        assertThat(candidates.path("exhaustiveAtomicDenominatorVerified").asBoolean()).isFalse();
        assertThat(candidates.path("sections")).hasSize(49);
        var candidateIds = new java.util.HashSet<String>();
        for (var section : candidates.path("sections")) for (var candidate : section.path("clauseCandidates")) {
            assertThat(candidateIds.add(candidate.path("id").asText())).isTrue();
            assertThat(candidate.path("requirement").asText()).isNotBlank();
            assertThat(candidate.path("atomicSplitVerified").asBoolean()).isFalse();
            assertThat(candidate.path("executedChains")).isEmpty();
            int start = candidate.path("sourceEvidence").path("startLine").asInt();
            int end = candidate.path("sourceEvidence").path("endLine").asInt();
            assertThat(candidate.path("sourceEvidence").path("lines")).hasSize(end - start + 1);
            for (int line = start; line <= end; line++)
                assertThat(candidate.path("sourceEvidence").path("lines").get(line - start).asText()).isEqualTo(source.get(line - 1));
        }
        assertThat(candidateIds).hasSize(683);
        var allSource = atomic.path("allSectionSourceInventory");
        assertThat(allSource.path("sections")).hasSize(49);
        assertThat(allSource.path("globalAtomicDenominatorEstablished").asBoolean()).isFalse();
        assertThat(allSource.path("wholeChapterComplete").asBoolean()).isFalse();
        var inventorySummary = allSource.path("summary");
        assertThat(inventorySummary.path("primaryFieldRowsSplit").asInt()).isEqualTo(397);
        assertThat(inventorySummary.path("fieldRowsWithoutDeclaredElementNumber").asInt()).isEqualTo(2);
        assertThat(inventorySummary.path("explicitAttributeRecords").asInt()).isEqualTo(1333);
        assertThat(inventorySummary.path("sourceStatementRecords").asInt()).isEqualTo(990);
        assertThat(inventorySummary.path("unresolvedSourceSpans").asInt()).isEqualTo(307);
        assertThat(inventorySummary.path("supportingLayoutSpans").asInt()).isEqualTo(417);
        assertThat(inventorySummary.path("accountedRetainedSourceLines").asInt()).isEqualTo(4899);
        assertThat(inventorySummary.path("unaccountedRetainedSourceLines").asInt()).isZero();
        assertThat(inventorySummary.path("verifiedStructuralOrReviewedSplits").asInt()).isEqualTo(977);
        assertThat(inventorySummary.path("sourceRecordsNeedingAtomicAdjudication").asInt()).isEqualTo(1346);
        assertThat(inventorySummary.path("openReconciliationSourceRecords").asInt()).isEqualTo(2319);
        assertThat(inventorySummary.path("externalReferenceSourceRecords").asInt()).isEqualTo(4);
        assertThat(allSource.path("sourceConflictAndExternalBoundaries")).hasSize(14);
        assertThat(allSource.path("openImplementationBySection")).hasSize(49);
        var inventoryIds = new java.util.HashSet<String>();
        int sectionNumber = 0, widthClauses = 0, classificationClauses = 0, reviewedSplits = 0;
        for (var section : allSource.path("sections")) {
            assertThat(section.path("section").asText()).isEqualTo("12." + (++sectionNumber));
            assertThat(section.path("atomicExhaustivenessVerified").asBoolean()).isFalse();
            assertThat(section.path("sourceLineAccounting").path("unaccountedRetainedLines")).isEmpty();
            var accounted = new java.util.HashSet<Integer>();
            for (String array : java.util.List.of("sourceClauses", "unresolvedSourceSpans", "supportingLayoutSpans"))
                for (var clause : section.path(array)) {
                    assertThat(inventoryIds.add(clause.path("id").asText())).isTrue();
                    assertThat(clause.path("section")).isEqualTo(section.path("section"));
                    assertThat(clause.path("requirement").asText()).isNotBlank();
                    assertThat(clause.path("sourceQuote").asText()).isNotBlank();
                    assertThat(clause.path("executedChains")).isEmpty();
                    int start = clause.path("sourceEvidence").path("startLine").asInt();
                    int end = clause.path("sourceEvidence").path("endLine").asInt();
                    assertThat(start).isGreaterThanOrEqualTo(section.path("sourceEvidence").path("startLine").asInt());
                    assertThat(end).isLessThanOrEqualTo(section.path("sourceEvidence").path("endLine").asInt());
                    assertThat(clause.path("sourceEvidence").path("lines")).hasSize(end - start + 1);
                    for (int line = start; line <= end; line++) {
                        accounted.add(line);
                        assertThat(clause.path("sourceEvidence").path("lines").get(line - start).asText()).isEqualTo(source.get(line - 1));
                    }
                    assertThat(String.join(" ", source.subList(start - 1, end)).replaceAll("\\s+", " ").trim())
                            .contains(clause.path("sourceQuote").asText());
                    if ("sourceClauses".equals(array))
                        assertThat(clause.path("status").asText()).isIn("OPEN_IMPLEMENTATION", "EXTERNAL_EVIDENCE");
                    if ("DECLARED_FIELD_LENGTH".equals(clause.path("sourceClauseKind").asText())) widthClauses++;
                    if ("FIELD_ENTRY_CLASSIFICATION".equals(clause.path("sourceClauseKind").asText())) classificationClauses++;
                    if ("REVIEWED_INDEPENDENT_CLAUSE".equals(clause.path("sourceClauseKind").asText())) reviewedSplits++;
                }
            for (var facetSection : facets.path("sections"))
                if (facetSection.path("section").equals(section.path("section")))
                    for (var unit : facetSection.path("sourceTextUnits"))
                        for (int line = unit.path("sourceEvidence").path("startLine").asInt();
                             line <= unit.path("sourceEvidence").path("endLine").asInt(); line++)
                            assertThat(accounted).contains(line);
            for (var linked : section.path("authoredExecutedAndBoundaryClauses"))
                assertThat(atomicIds).contains(linked.path("id").asText());
        }
        assertThat(widthClauses).isEqualTo(397);
        assertThat(classificationClauses).isEqualTo(397);
        assertThat(reviewedSplits).isEqualTo(76);
        var semantic = allSource.path("semanticAdjudication");
        assertThat(semantic.path("globalAtomicDenominatorEstablished").asBoolean()).isFalse();
        assertThat(semantic.path("executedLinksAreReusedNotNewObservations").asBoolean()).isTrue();
        var semanticSummary = semantic.path("summary");
        assertThat(semanticSummary.path("reviewedResidualSpans").asInt()).isEqualTo(307);
        assertThat(semanticSummary.path("remainingResidualSpans").asInt()).isZero();
        assertThat(semanticSummary.path("reviewedDecisions").asInt()).isEqualTo(734);
        assertThat(semanticSummary.path("reviewedNonStructuralRecords").asInt()).isEqualTo(1346);
        assertThat(semanticSummary.path("remainingNonStructuralRecords").asInt()).isZero();
        assertThat(semanticSummary.path("reviewedStructuralRecords").asInt()).isEqualTo(977);
        assertThat(semanticSummary.path("remainingStructuralRecordsNeedingSemanticReview").asInt()).isZero();
        assertThat(semanticSummary.path("reviewedIndependentClauses").asInt()).isEqualTo(1994);
        assertThat(semanticSummary.path("explicitRepeatedObligationAliases").asInt()).isEqualTo(16);
        assertThat(semanticSummary.path("reviewedDistinctObligationsInPartialScope").asInt()).isEqualTo(1978);
        assertThat(semanticSummary.path("verifiedExecutedChainLinks").asInt()).isEqualTo(253);
        assertThat(semanticSummary.path("uniqueReusedCanonicalCases").asInt()).isEqualTo(201);
        assertThat(semantic.path("reviewedClauseStatusCounts").path("RECONCILIATION_REQUIRED").asInt()).isEqualTo(1907);
        assertThat(semantic.path("reviewedClauseStatusCounts").path("BOUNDED_EXECUTION").asInt()).isEqualTo(14);
        assertThat(semantic.path("reviewedClauseStatusCounts").path("BOUNDED_EXECUTED").asInt()).isEqualTo(53);
        assertThat(semantic.path("reviewedClauseStatusCounts").path("OPEN_EXECUTION_EVIDENCE").asInt()).isZero();
        assertThat(semantic.path("reviewedClauseStatusCounts").path("SOURCE_CONFLICT").asInt()).isEqualTo(13);
        assertThat(semantic.path("reviewedClauseStatusCounts").path("EXTERNAL_EVIDENCE").asInt()).isEqualTo(7);
        assertThat(semantic.path("reviewedClauseStatusCounts").path("OPEN_IMPLEMENTATION").asInt()).isZero();
        assertThat(semantic.path("reviewedClauses")).filteredOn(c -> "12.1".equals(c.path("section").asText()))
                .allSatisfy(c -> assertThat(c.path("implementationStatus").asText()).isEqualTo("BOUNDED_EXECUTED"));
        assertThat(semantic.path("deduplicatedReconciledScope").path("sections")).hasSize(1);
        assertThat(semantic.path("deduplicatedReconciledScope").path("sections").get(0).asText()).isEqualTo("12.1");
        assertThat(semantic.path("deduplicatedReconciledScope").path("distinctObligations").asInt()).isEqualTo(37);
        assertThat(semantic.path("deduplicatedReconciledScope").path("wholeChapterDenominator").asBoolean()).isFalse();
        for (String id : java.util.List.of("153-PRIMARY-COLUMNS", "157-DECIMAL-EXAMPLES",
                "134-PURPOSES")) {
            assertThat(semantic.path("reviewedDecisions")).anySatisfy(decision -> {
                assertThat(decision.path("id").asText()).isEqualTo(id);
                assertThat(decision.path("classification").asText()).isEqualTo(switch (id) {
                    case "153-PRIMARY-COLUMNS" -> "SUPPORTING_LAYOUT";
                    case "157-DECIMAL-EXAMPLES" -> "ILLUSTRATIVE_EXAMPLE";
                    default -> "SUPPORTING_DESCRIPTION";
                });
            });
        }
        for (String id : java.util.List.of("SEM-12.17-WIRE-NUMBERING",
                "SEM-12.17-CATEGORY-NUMBERING", "SEM-12.27-REITERATION",
                "SEM-12.29-REITERATION", "SEM-12.31-WON-CONFLICT", "SEM-12.47-CAP-CONFLICT")) {
            assertThat(semantic.path("reviewedClauses")).anySatisfy(clause -> {
                assertThat(clause.path("id").asText()).isEqualTo(id);
                assertThat(clause.path("implementationStatus").asText()).isEqualTo("SOURCE_CONFLICT");
                assertThat(clause.path("executedChains")).isEmpty();
            });
        }
        for (String id : java.util.List.of("DL7-LENGTH-TLV-1",
                "DL8-SPECIAL-LENGTH-LIMIT-ASSOCIATION-2")) {
            assertThat(semantic.path("reviewedClauses")).anySatisfy(clause -> {
                assertThat(clause.path("id").asText()).isEqualTo(id);
                assertThat(clause.path("requirement").asText()).contains("exclusive of the Data Type Indicator");
            });
        }
        var semanticIds = new java.util.HashSet<String>();
        var semanticInputIds = new java.util.HashSet<String>();
        for (var decision : semantic.path("reviewedDecisions")) {
            assertThat(decision.path("rationale").asText()).isNotBlank();
            for (var input : decision.path("sourceRecords"))
                assertThat(semanticInputIds.add(input.path("id").asText())).isTrue();
            if ("112-OPTIONAL-INFORMATION-LIST".equals(decision.path("id").asText())) {
                assertThat(decision.path("classification").asText()).isEqualTo("SUPPORTING_DESCRIPTION");
                assertThat(decision.path("sourceRecords")).hasSize(33);
            }
        }
        for (var clause : semantic.path("reviewedClauses")) {
            assertThat(semanticIds.add(clause.path("id").asText())).isTrue();
            int start = clause.path("sourceEvidence").path("startLine").asInt();
            int end = clause.path("sourceEvidence").path("endLine").asInt();
            assertThat(clause.path("sourceEvidence").path("lines")).hasSize(end - start + 1);
            for (int line = start; line <= end; line++)
                assertThat(clause.path("sourceEvidence").path("lines").get(line - start).asText()).isEqualTo(source.get(line - 1));
            for (var reference : clause.path("additionalSourceReferences")) {
                int first = reference.path("startLine").asInt(), last = reference.path("endLine").asInt();
                assertThat(reference.path("reason").asText()).isNotBlank();
                assertThat(reference.path("lines")).hasSize(last - first + 1);
                for (int line = first; line <= last; line++)
                    assertThat(reference.path("lines").get(line - first).asText()).isEqualTo(source.get(line - 1));
            }
            assertThat(clause.path("implementationStatus").asText()).isIn(
                    "RECONCILIATION_REQUIRED", "BOUNDED_EXECUTION", "BOUNDED_EXECUTED",
                    "OPEN_IMPLEMENTATION", "OPEN_EXECUTION_EVIDENCE", "SOURCE_CONFLICT", "EXTERNAL_EVIDENCE");
            if ("12.1".equals(clause.path("section").asText())) {
                assertThat(clause.path("implementationStatus").asText()).isNotEqualTo("RECONCILIATION_REQUIRED");
                assertThat(clause.has("sameObligationAs")).isFalse();
                assertThat(clause.path("implementationReview").path("analysis").asText()).isNotBlank();
                assertThat(clause.path("implementationReview").path("evidence")).isNotEmpty();
                if (clause.path("implementationStatus").asText().startsWith("OPEN_"))
                    assertThat(clause.path("gap").asText()).isNotBlank();
            }
            for (var chain : clause.path("executedChains")) {
                var execution = executedByCase.get(chain.path("testCaseId").asText());
                assertThat(execution).isNotNull();
                for (String field : java.util.List.of("businessRequirementId", "scenarioId", "testDataId",
                        "targetRuleId", "scope", "actualStatus", "actualTargetStatus"))
                    assertThat(chain.path(field)).isEqualTo(execution.path(field));
                if (chain.has("verifiedPredicate")) {
                    var predicate = chain.path("verifiedPredicate");
                    assertThat(predicate.path("testCaseId")).isEqualTo(execution.path("testCaseId"));
                    assertThat(execution.path("findings")).anySatisfy(finding -> {
                        for (String field : java.util.List.of("ruleId", "scope", "status"))
                            assertThat(finding.path(field)).isEqualTo(predicate.path(field));
                    });
                }
            }
            if ("SEM-12.1-WIDTH-78".equals(clause.path("id").asText())) {
                assertThat(clause.path("implementationStatus").asText()).isEqualTo("BOUNDED_EXECUTED");
                assertThat(clause.path("requirement").asText()).contains("financial", "four");
                assertThat(clause.path("additionalSourceReferences").get(0).path("lines"))
                        .anySatisfy(line -> assertThat(line.asText()).contains("4-character Prompt"));
            }
            if ("SEM-12.3-PRIMARY-FUEL".equals(clause.path("id").asText())) {
                assertThat(clause.path("authoredAssessment").asText()).isEqualTo("OPEN_IMPLEMENTATION");
                assertThat(clause.path("implementationStatus").asText()).isEqualTo("RECONCILIATION_REQUIRED");
                assertThat(clause.path("implementationCandidate").asText()).endsWith("ProductSegmentPayloadValidator.java");
                assertThat(clause.path("executedChains")).isEmpty();
            }
            if ("SEM-12.31-PROMPT-WIDTH".equals(clause.path("id").asText()))
                assertThat(clause.path("sourceQuote").asText()).contains("Prompt Var. Max 4");
            if ("SEM-12.33-EQUAL-FILLER".equals(clause.path("id").asText()))
                assertThat(clause.path("sourceQuote").asText()).contains("Filler 1", "=");
            if ("SEM-12.9-ALT-WIDTH-REPEAT".equals(clause.path("id").asText())) {
                assertThat(clause.path("sourceQuote").asText()).contains("Fixed: 1 Byte");
                assertThat(clause.path("requirement").asText()).contains("one byte");
                assertThat(clause.path("sameObligationAs").asText()).isEqualTo("SEM-12.9-ALT-WIDTH");
            }
            if ("SEM-12.10-BODY-CAP".equals(clause.path("id").asText()))
                assertThat(clause.path("requirement").asText()).contains("including its finalFS", "991");
            if ("SEM-12.16-LOAD-VERSION-ECHO".equals(clause.path("id").asText())) {
                assertThat(clause.path("sourceQuote").asText()).contains("echoed back in each subsequent");
                assertThat(clause.path("requirement").asText()).startsWith("Every subsequent");
            }
            if ("SEM-12.16-903-REQUEST".equals(clause.path("id").asText()))
                assertThat(clause.path("sameObligationAs").asText()).isEqualTo("118-DIRECTION-RULES-4");
        }
        assertThat(semanticInputIds).hasSize(307 + 1346 + 977);
        for (int i = 0; i < semantic.path("remainingBySection").size(); i++) {
            var remaining = semantic.path("remainingBySection").get(i);
            assertThat(remaining.path("unreviewedResidualSpanIds")).isEmpty();
            assertThat(remaining.path("unreviewedNonStructuralRecordIds")).isEmpty();
            assertThat(remaining.path("unreviewedStructuralRecordIds")).isEmpty();
            assertThat(remaining.path("nonStructuralSourceRecordReviewComplete").asBoolean()).isTrue();
            assertThat(remaining.path("allSourceRecordReviewComplete").asBoolean()).isTrue();
            assertThat(remaining.path("sectionAtomicDenominatorEstablished").asBoolean())
                    .isEqualTo("12.1".equals(remaining.path("section").asText()));
        }
        assertThat(allSource.path("sections")).anySatisfy(section -> {
            assertThat(section.path("section").asText()).isEqualTo("12.4");
            assertThat(section.path("sourceClauses")).anySatisfy(clause -> {
                assertThat(clause.path("field").asText()).isEqualTo("Field2/Element84 (Segment Length)");
                assertThat(clause.path("sourceClauseKind").asText()).isEqualTo("DECLARED_FIELD_LENGTH");
                assertThat(clause.path("requirement").asText()).contains("Len. 3 or 4");
            });
        });
        var decisions = new ObjectMapper().readTree(com.coreauth.validator.paths.Atl105Paths.docs()
                .resolve("specs").resolve("kb").resolve("chapter-12-semantic-adjudications.json").toFile());
        com.fasterxml.jackson.databind.node.ObjectNode stale = decisions.deepCopy();
        stale.put("sourceSha256", "0".repeat(64));
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, report.path("executions"), stale))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("source changed");
        com.fasterxml.jackson.databind.node.ObjectNode wrongCount = decisions.deepCopy();
        ((com.fasterxml.jackson.databind.node.ObjectNode) wrongCount.path("decisions").get(0)).put("expectedRecords", 999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, report.path("executions"), wrongCount))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("selector changed");
        com.fasterxml.jackson.databind.node.ObjectNode duplicate = decisions.deepCopy();
        ((com.fasterxml.jackson.databind.node.ArrayNode) duplicate.path("decisions")).add(duplicate.path("decisions").get(0).deepCopy());
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, report.path("executions"), duplicate))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("Invalid semantic decision");
        com.fasterxml.jackson.databind.node.ArrayNode wrongExecutions = report.path("executions").deepCopy();
        for (var execution : wrongExecutions) if (execution.path("testCaseId").asText()
                .equals("TC-TEST-CH12-100-ACTUAL-WIRE-100-VALID-FIELDS"))
            ((com.fasterxml.jackson.databind.node.ObjectNode) execution).put("scope", "wrong-target-scope");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, wrongExecutions, decisions))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("target execution missing/mismatched");
        com.fasterxml.jackson.databind.node.ObjectNode driftedCode = decisions.deepCopy();
        ((com.fasterxml.jackson.databind.node.ObjectNode) driftedCode.path("codeReviews")
                .path("100-type").path("evidence").get(0)).put("contains", "NOT THE REVIEWED PREDICATE");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, report.path("executions"), driftedCode))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("Implementation review anchor drift");
        com.fasterxml.jackson.databind.node.ObjectNode missingEquivalence = decisions.deepCopy();
        ((com.fasterxml.jackson.databind.node.ObjectNode) missingEquivalence.path("codeReviews").path("100-required"))
                .remove("predicateEquivalence");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, report.path("executions"), missingEquivalence))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("Predicate execution links need code equivalence");
        com.fasterxml.jackson.databind.node.ArrayNode wrongPredicateExecutions = report.path("executions").deepCopy();
        for (var execution : wrongPredicateExecutions) if (execution.path("testCaseId").asText()
                .equals("TC-TEST-CH12-100-ACTUAL100-NONTEXT-FIELDS"))
            for (var finding : execution.path("findings")) if ("standard-required-86".equals(finding.path("scope").asText()))
                ((com.fasterxml.jackson.databind.node.ObjectNode) finding).put("status", "CHECKS_PASSED");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, wrongPredicateExecutions, decisions))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("Predicate finding missing/ambiguous");
        com.fasterxml.jackson.databind.node.ObjectNode prematureSection = decisions.deepCopy();
        ((com.fasterxml.jackson.databind.node.ArrayNode) prematureSection.path("finalReconciliationSections")).add("12.2");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Chapter12AtomicClauseReconciliation.semanticAdjudication(
                allSource, atomic.path("clauses"), source, report.path("executions"), prematureSection))
                .isInstanceOf(java.io.IOException.class).hasMessageContaining("Final section still has unresolved");
        assertThat(allSource.path("sections")).anySatisfy(section -> {
            assertThat(section.path("section").asText()).isEqualTo("12.47");
            assertThat(section.path("unresolvedSourceSpans")).anySatisfy(span ->
                    assertThat(span.path("sourceQuote").asText()).contains("Segment No. DL1", "Card Type value of 173"));
        });
        assertThat(allSource.path("sections")).anySatisfy(section -> {
            assertThat(section.path("section").asText()).isEqualTo("12.30");
            assertThat(section.path("sourceClauses")).anySatisfy(clause -> {
                assertThat(clause.path("sourceClauseKind").asText()).isEqualTo("DECLARED_FIELD_LENGTH");
                assertThat(clause.path("field").asText()).isEqualTo("Field6/Element224 (Tax Type 1)");
                assertThat(clause.path("requirement").asText()).contains("Len. 3");
            });
        });
        for (String name : java.util.List.of("12.36", "12.37"))
            assertThat(allSource.path("sections")).anySatisfy(section -> {
                assertThat(section.path("section").asText()).isEqualTo(name);
                assertThat(section.path("sourceClauses")).anySatisfy(clause -> {
                    assertThat(clause.path("sourceClauseKind").asText()).isEqualTo("FIELD_ENTRY_CLASSIFICATION");
                    assertThat(clause.path("field").asText()).isEqualTo("Field3/ElementNumberNotDeclared (Market Basket Data)");
                    assertThat(clause.path("requirement").asText()).contains("classification R");
                });
                assertThat(section.path("sourceClauses")).anySatisfy(clause -> {
                    assertThat(clause.path("sourceClauseKind").asText()).isEqualTo("FIXED_VALUE");
                    assertThat(clause.path("field").asText()).contains("Field1/Element85");
                    assertThat(clause.path("sourceQuote").asText()).contains("Fixed Value", name.equals("12.36") ? "151" : "152");
                });
            });
        int retained = 0;
        for (var section : facets.path("sections")) {
            assertThat(section.path("facets")).hasSize(7);
            assertThat(section.path("facets")).allSatisfy(facet -> assertThat(facet.path("semanticClosure").asBoolean()).isFalse());
            for (var unit : section.path("sourceTextUnits")) {
                int start = unit.path("sourceEvidence").path("startLine").asInt();
                int end = unit.path("sourceEvidence").path("endLine").asInt();
                assertThat(unit.path("lines")).hasSize(end - start + 1);
                for (int line = start; line <= end; line++) {
                    assertThat(unit.path("lines").get(line - start).asText()).isEqualTo(source.get(line - 1));
                    retained++;
                }
            }
            for (var facet : section.path("facets")) for (var predicate : facet.path("boundedSourcePredicates"))
                for (var chain : predicate.path("executedChains")) {
                    for (String key : java.util.List.of("businessRequirementId", "scenarioId", "testCaseId", "testDataId"))
                        assertThat(chain.path(key).asText()).isNotBlank();
                }
        }
        assertThat(retained).isEqualTo(4899);
        var reconciliation = report.path("sourceSectionReconciliation");
        assertThat(reconciliation.path("status").asText()).isEqualTo("IN_PROGRESS");
        assertThat(reconciliation.path("sourceSections").asInt()).isEqualTo(49);
        assertThat(reconciliation.path("sections")).hasSize(49);
        assertThat(reconciliation.path("sections")).allSatisfy(section -> {
            assertThat(section.path("status").asText()).isEqualTo("SOURCE_FACETS_NOT_CLOSED");
            assertThat(section.path("sourceEvidence").path("endLine").asInt())
                    .isGreaterThan(section.path("sourceEvidence").path("startLine").asInt());
        });
        assertThat(reconciliation.path("sections")).anySatisfy(section -> {
            assertThat(section.path("section").asText()).isEqualTo("12.31");
            assertThat(section.path("catalogRules")).anySatisfy(rule -> {
                assertThat(rule.path("ruleId").asText()).isEqualTo("SEG145-R-008");
                assertThat(rule.path("status").asText()).isEqualTo("BOUNDED_EXECUTION_ONLY");
                assertThat(rule.path("executions")).isNotEmpty();
            });
        });
        assertThat(report.path("executions")).anySatisfy(execution -> {
            assertThat(execution.path("ruleId").asText()).isEqualTo("SEG119-R-008");
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            assertThat(execution.path("candidateMatchStatus").asText()).isEqualTo("REVIEW_REQUIRED");
        });
        assertThat(report.path("executions")).anySatisfy(execution -> {
            assertThat(execution.path("ruleId").asText()).isEqualTo("SEG115-R-003");
            assertThat(execution.path("existingTestArtifactAnchorCandidates")).isNotEmpty();
            assertThat(execution.path("candidateMatchStatus").asText()).isEqualTo("REVIEW_REQUIRED");
        });
        var loaded = new CanonicalPackageLoader().load(packageFile);
        assertThat(new CanonicalTraceabilityValidator().validate(loaded).errors()).isEmpty();
        assertThat(new CanonicalTraceabilityValidator().validateForExecution(loaded).errors()).isNotEmpty();
        var validator = new Chapter12SegmentPayloadValidator();
        var actualAdapter = new com.coreauth.validator.canonical.Segment119ActualPayloadAdapter(new ObjectMapper()
                .readTree(com.coreauth.validator.paths.Atl105Paths.testOutput().resolve("test-solution-independent-review")
                        .resolve("all-element-inventory.json").toFile()));
        for (var data : loaded.getTestData()) {
            var payload = data.getPayload();
            if (payload.has("actualMessage")) {
                var result = new com.coreauth.validator.canonical.Chapter12LegacyActualPayloadAdapter().validate(
                        payload.path("actualMessage"), payload.path("segment").asText(), payload.path("messageFamily").asText(), payload.path("actualEvidence"));
                assertThat(result.status().name()).isEqualTo("FAIL".equals(data.getExpectedValidation()) ? "INVALID" : "REVIEW_REQUIRED");
                continue;
            }
            if (payload.has("productPayload")) {
                var result = new com.coreauth.validator.canonical.ProductActualPayloadAdapter().validatePayload(
                        payload.path("productPayload"), payload.path("segment").asText());
                assertThat(result.isValid()).isEqualTo(!"FAIL".equals(data.getExpectedValidation()));
                continue;
            }
            var result = payload.has("actualPayload") ? "119".equals(payload.path("segment").asText())
                    ? actualAdapter.validate(payload.path("actualPayload"), payload.path("messageFamily").asText(), payload.path("actualEvidence"))
                    : new com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter().validate(payload.path("actualPayload"),
                    payload.path("segment").asText(), payload.path("messageFamily").asText(), payload.path("actualEvidence"),
                    payload.get("actualStandard"), payload.get("actualProducts")) : validator.validate(payload);
            assertThat(result.status().name()).isIn("INVALID", "REVIEW_REQUIRED", "CHECKS_PASSED");
        }
        int predicates = 0;
        int actualChapter12Executions = 0;
        var actualChapter12Segments = new java.util.HashSet<String>();
        for (var execution : report.path("executions")) {
            if ("ACTUAL_NAMED_CHAPTER12_OBSERVATION".equals(execution.path("validationPath").asText())) {
                actualChapter12Executions++;
                actualChapter12Segments.add(execution.path("observedSegment").asText());
            }
            if (execution.has("expectedTargetStatus")) {
                predicates++;
                assertThat(execution.path("actualTargetStatus")).isEqualTo(execution.path("expectedTargetStatus"));
            }
        }
        assertThat(predicates).isEqualTo(1155);
        assertThat(actualChapter12Executions).isEqualTo(375);
        assertThat(actualChapter12Segments).containsExactlyInAnyOrder("103", "115", "116", "123", "130", "131", "132", "134", "135", "136",
                "139", "140", "141", "142", "143", "145", "146", "148", "149", "150", "151", "152", "153", "155", "156",
                "100", "DL1", "DL2", "DL3", "DL4", "DL5", "DL6", "DL7", "DL8");
        assertThat(new ObjectMapper().readTree(reportFile.toFile())).isEqualTo(report);
    }
}
