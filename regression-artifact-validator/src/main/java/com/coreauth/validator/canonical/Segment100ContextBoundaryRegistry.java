package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Registry for Segment 100 contexts that require specialized evidence or review. */
public final class Segment100ContextBoundaryRegistry {
    private static final Map<String, Context> CONTEXTS;

    static {
        Map<String, Context> contexts = new LinkedHashMap<>();
        contexts.put("MASTERCARD_AUTHORIZATION", new Context("Appendix G", "Preauthorization versus final authorization context", "REVIEW_REQUIRED"));
        contexts.put("POS_CONDITION_ENTRY_MODE", new Context("Appendix J/Q", "Table 005 entry mode and Table 030 POS condition context", "REVIEW_REQUIRED"));
        contexts.put("TOKENIZATION", new Context("Appendix O", "Token account, expiration, cryptogram, and token presentment", "REVIEW_REQUIRED"));
        contexts.put("APPENDIX_RESPONSE_LAYOUT", new Context("Appendix H/K/T", "Decline, additional-information, and EMV response layouts", "REVIEW_REQUIRED"));
        contexts.put("PRODUCT_CARD_LIFECYCLE", new Context("Appendix E/G and lifecycle rules", "Product/card-specific completion, reversal, void, and timeout behavior", "REVIEW_REQUIRED"));
        CONTEXTS = Collections.unmodifiableMap(contexts);
    }

    public Map<String, Context> contexts() {
        return CONTEXTS;
    }

    public record Context(String source, String description, String status) { }
}
