package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Independent boundary registry for Segment 100 specialized appendix domains. */
public final class Segment100SpecializedDomainRegistry {
    private static final Map<String, Domain> DOMAINS;

    static {
        Map<String, Domain> domains = new LinkedHashMap<>();
        domains.put("PREMIUM_GIFT_CARD", new Domain("N", "First Data Premium Gift Card magnetic-stripe data", "APPENDIX_SPEC_AND_AI_ARTIFACT"));
        domains.put("PAYMENT_TOKEN", new Domain("O", "Payment token account, expiration, cryptogram, and presentment", "TOKENIZATION_SPEC_AND_AI_ARTIFACT"));
        domains.put("TRANSARMOR_ADMIN", new Domain("P", "TransArmor VeriFone administrative responses", "EXTERNAL_TRANSARMOR_SPEC"));
        domains.put("CA_PUBLIC_KEYS", new Domain("S", "EMV CA Public Key file and key-load records", "CA_KEY_ARTIFACT_AND_EMV_CONFIG"));
        domains.put("DIGITAL_WALLET", new Domain("U", "Visa pass-through and staged digital wallets", "WALLET_SPEC_AND_AI_ARTIFACT"));
        domains.put("MONERIS", new Domain("V", "Moneris request and response data segments", "MONERIS_SPEC_AND_AI_ARTIFACT"));
        DOMAINS = Collections.unmodifiableMap(domains);
    }

    public Map<String, Domain> domains() {
        return DOMAINS;
    }

    public Domain domain(String id) {
        return DOMAINS.get(id);
    }

    public record Domain(String appendix, String description, String requiredEvidence) { }
}
