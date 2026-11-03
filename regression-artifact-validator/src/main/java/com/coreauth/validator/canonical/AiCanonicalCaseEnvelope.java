package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

/** Canonical, source-linked assessment envelope for one AI test case. */
public record AiCanonicalCaseEnvelope(
        int schemaVersion,
        String specification,
        String specificationVersion,
        String runId,
        String testCaseId,
        String scenarioId,
        List<String> requirementIds,
        Verdict verdict,
        String verdictScope,
        String verdictReason,
        String oracleGapId,
        List<SourceArtifact> sourceArtifacts,
        List<IndependentValidation> independentValidations,
        ResponseComparison responseComparison,
        CaseOutcomeOracleComparison caseOutcomeOracle,
        List<JsonNode> defectCandidates) {

    public enum Verdict { AGREE, DISAGREE, AI_DEFECT, ORACLE_GAP }

    public record SourceArtifact(
            String metadataPath,
            String payloadPath,
            String formatProfileId,
            String metadataSha256,
            String payloadSha256) { }

    public record IndependentValidation(
            String scenarioType,
            String status,
            int observationCount,
            Map<String, Long> observationsByStatus,
            List<RuleEvidence> ruleResults,
            long intakeFindingCount,
            long invalidFindingCount,
            long reviewRequiredFindingCount,
            String negativeClass,
            String violatedElement,
            String negativeTargetDisposition,
            String inputError) { }

    public record RuleEvidence(
            String segment,
            String element,
            String ruleId,
            String status,
            long findingCount) { }

    public record ResponseComparison(
            String status,
            String oracleStatus,
            String oracleFamily,
            boolean declarationConflict,
            String reason,
            String limitation) { }

    public record CaseOutcomeOracleComparison(
            String status,
            String oracleId,
            String reviewer,
            String reason,
            List<CaseOutcomeEvidence> evidence) { }

        public record CaseOutcomeEvidence(String type, String sourcePath, String sha256, String locator) { }
}
