package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;

import java.util.ArrayList;
import java.util.List;

/** Section 12.31/12.32 prompt framing and edit-mask grammar, not host prompt authorization. */
final class EnhancedFleetPromptValidator {
    private EnhancedFleetPromptValidator() { }

    static List<Finding> validate(String value, boolean request) {
        String rule = request ? "SEG145-R-008" : "SEG146-R-004";
        List<Finding> findings = new ArrayList<>();
        boolean framed = value != null && value.length() > 2 && value.startsWith("|") && value.endsWith("|");
        findings.add(new Finding(rule, "prompt-framing", framed ? Status.CHECKS_PASSED : Status.INVALID,
                "Prompt entries require leading/ending pipes and colon-separated tokens and values"));
        if (!framed) return List.copyOf(findings);
        for (String entry : value.substring(1, value.length() - 1).split("\\|", -1)) {
            int colon = entry.indexOf(':');
            boolean shape = colon >= 1 && colon <= 4 && entry.substring(0, colon).matches("[A-Za-z0-9]+")
                    && entry.length() - colon - 1 <= 20 && (request || entry.length() > colon + 1);
            findings.add(new Finding(rule, "prompt-entry", shape ? Status.CHECKS_PASSED : Status.INVALID,
                    "Prompt token has 1-4 characters and a value/format of at most 20; a format is nonempty"));
            if (!shape || request) continue;
            boolean valid = true;
            for (String mask : entry.substring(colon + 1).split(";", -1)) {
                valid &= mask.matches("[?ABNIOZ]|T[NS]|[MX][0-9]+|V[^;|]+|P[@#*A-Za-z0-9]+");
            }
            findings.add(new Finding(rule, "edit-mask-grammar", valid ? Status.CHECKS_PASSED : Status.INVALID,
                    "Each semicolon-separated edit mask uses the published grammar"));
        }
        findings.add(new Finding(rule, "prompt-policy", Status.REVIEW_REQUIRED,
                "Authorizer token enumeration, prior re-prompt formats, captured-value and subsequent-send execution require further observations"));
        return List.copyOf(findings);
    }
}
