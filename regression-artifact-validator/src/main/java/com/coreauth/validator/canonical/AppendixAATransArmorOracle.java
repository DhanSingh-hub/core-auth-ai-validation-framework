package com.coreauth.validator.canonical;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Bounded Appendix AA field checks; does not encrypt, decrypt or certify network-issued values. */
public final class AppendixAATransArmorOracle {
    public enum Status { PASS, FAIL, REVIEW_REQUIRED }
    public enum Method { RSA_PKI, TA_VE, TDES_DUKPT, ONGUARD_FPE, AES_DUKPT, TOKEN_ONLY }
    public enum Stage { INITIAL, SUBSEQUENT, KEY_LOAD, INITIALIZATION }
    public record Scenario(Method method, Stage stage, String accountNumber, String sequenceNumber,
                           String originalResponseToken, String expirationMmyy, String originalExpirationMmyy,
                           Boolean manuallyKeyed, String merchantNumber, String plaintextBeforeEncryption,
                           String loadedKeyId, String processorCode, String pumpLaneNumber,
                           String cardAcceptorTerminalId, Map<String, String> additionalData,
                           Boolean tokenRetrievalFailed, String declineCode, String loadType,
                           Boolean loadApproved, String responseCode, String terminalIdentifier,
                           String downloadIndicator, Boolean keyUpdateScheduled,
                           Boolean keyLoadPerformed, Boolean regiStartPerformed, String storedOnFileToken) {
        public Scenario {
            Objects.requireNonNull(method, "method");
            Objects.requireNonNull(stage, "stage");
            additionalData = Map.copyOf(Objects.requireNonNull(additionalData, "additionalData"));
        }
    }
    public record Assessment(String businessRequirementId, Status status, String reason) { }
    public static final List<String> RULES = List.of("ACCOUNT-FORMAT", "EDATA-METHOD",
            "EXPIRATION-TOKEN-PERSISTENCE", "DECLINE-TOKEN-RETRIEVAL", "KEY-LOAD-RESPONSE",
            "KEY-REFRESH", "PKI-PREPROCESSING", "ADDITIONAL-DATA", "INITIALIZATION", "EXTERNAL-VALIDITY");
    private static final Set<Character> IDENTIFIERS = Set.of('0', '1', '2', '3', '4', '6', '7', 'M');

    public List<Assessment> assess(Scenario s) {
        Objects.requireNonNull(s, "scenario");
        return List.of(header(s), method(s), persistence(s), decline(s), load(s),
                refresh(s), preprocessing(s), additional(s), initialization(s),
                result("EXTERNAL-VALIDITY", Status.REVIEW_REQUIRED,
                        "Synthetic structure does not prove encryption/decryption, key/KSN validity, assigned "
                                + "Token Type/merchant domain/brand/processor or device identity, actual token "
                                + "association, or durable storage. Use authoritative service and boarding evidence."));
    }

    private static boolean transaction(Scenario s) {
        return s.stage() == Stage.INITIAL || s.stage() == Stage.SUBSEQUENT;
    }
    private static boolean validHeader(Scenario s) {
        String value = s.accountNumber();
        return value != null && value.length() >= 25 && value.startsWith("TAP")
                && value.substring(3, 9).matches("[0-9]{6}")
                && IDENTIFIERS.contains(value.charAt(9))
                && value.substring(21, 25).matches("[A-Za-z0-9]{4}");
    }
    private static Assessment header(Scenario s) {
        if (!transaction(s)) return na("ACCOUNT-FORMAT");
        if (!validHeader(s)) return result("ACCOUNT-FORMAT", Status.FAIL,
                "Element 2 requires TAP + sequence(6) + EDATA identifier(1) + Key ID(11) + Token Type(4).");
        if (!s.accountNumber().substring(3, 9).equals(s.sequenceNumber())) return result("ACCOUNT-FORMAT",
                Status.FAIL, "TAP sequence must match Element 86.");
        String edata = s.accountNumber().substring(25);
        if (edata.isEmpty()) return result("ACCOUNT-FORMAT", Status.FAIL, "EDATA is absent.");
        if (s.accountNumber().chars().anyMatch(c -> c > 127)) return result("ACCOUNT-FORMAT", Status.REVIEW_REQUIRED,
                "Non-ASCII EDATA needs an explicit wire-byte encoding; character count is not byte count.");
        if (s.stage() == Stage.INITIAL && s.method() != Method.TOKEN_ONLY) {
            String encrypted = encryptedBlock(s);
            if (encrypted.isEmpty()) return result("ACCOUNT-FORMAT", Status.FAIL, "Encrypted EDATA block is absent.");
            if (encrypted.length() > 400) return result("ACCOUNT-FORMAT", Status.FAIL,
                    "Encrypted EDATA exceeds the source maximum of 400 bytes.");
        }
        return result("ACCOUNT-FORMAT", Status.PASS,
                "TAP structure, sequence and bounded ASCII EDATA match; First Data Token Type assignment is not certified.");
    }
    private static Assessment method(Scenario s) {
        if (!transaction(s)) return na("EDATA-METHOD");
        if (!validHeader(s)) return result("EDATA-METHOD", Status.FAIL, "A valid TAP header is required.");
        char identifier = s.accountNumber().charAt(9);
        String key = s.accountNumber().substring(10, 21);
        String edata = s.accountNumber().substring(25);
        boolean missingEvidence = false;
        if (s.stage() == Stage.SUBSEQUENT) {
            if (identifier != '0') return result("EDATA-METHOD", Status.FAIL, "Subsequent token EDATA identifier must be 0.");
            return result("EDATA-METHOD", Status.REVIEW_REQUIRED,
                    "The table says subsequent Key ID is zero-filled (with/without semicolon), but PKI/TA-VE examples "
                            + "retain initial Key IDs. Resolve this conflict before asserting a subsequent Key ID rule.");
        }
        boolean expected = switch (s.method()) {
            case RSA_PKI, TDES_DUKPT, ONGUARD_FPE, AES_DUKPT -> identifier == '1' || identifier == '2' || identifier == '3';
            case TOKEN_ONLY -> identifier == '4';
            case TA_VE -> identifier == '6' || identifier == '7' || identifier == 'M';
        };
        if (!expected) return result("EDATA-METHOD", Status.FAIL, "EDATA identifier does not match declared encryption method/stage.");
        if (s.method() != Method.TOKEN_ONLY && encryptedBlock(s).isEmpty()) return result("EDATA-METHOD",
                Status.FAIL, "EDATA must contain encrypted data, not just a processor prefix or FS/expiration suffix.");
        if (Boolean.TRUE.equals(s.manuallyKeyed()) && (identifier == '1' || identifier == '2')
                || Boolean.FALSE.equals(s.manuallyKeyed()) && identifier == '3') {
            return result("EDATA-METHOD", Status.FAIL, "EDATA track/manual identifier conflicts with supplied entry context.");
        }
        if (s.method() == Method.TDES_DUKPT || s.method() == Method.ONGUARD_FPE || s.method() == Method.AES_DUKPT) {
            if (!"00000000000".equals(key)) return result("EDATA-METHOD", Status.FAIL,
                    "Initial TDES/DUKPT, OnGuard FPE and AES DUKPT use zero-filled header Key ID.");
        }
        if (s.method() == Method.RSA_PKI) {
            missingEvidence = s.loadedKeyId() == null;
            if (s.loadedKeyId() != null && !key.equals(s.loadedKeyId())) return result("EDATA-METHOD",
                    Status.FAIL, "PKI Key ID differs from Key Load response.");
        }
        if (s.method() == Method.TA_VE) {
            if (key.charAt(5) != ';') return result("EDATA-METHOD", Status.FAIL,
                    "TA-VE Key ID is Merchant Domain(5) + semicolon + Merchant Brand(5).");
            int colon = edata.indexOf(':');
            if (colon < 1 || colon > 16 || !edata.substring(0, colon).matches("[A-Za-z0-9]+")
                    || colon == edata.length() - 1) return result("EDATA-METHOD", Status.FAIL,
                    "TA-VE EDATA requires 1-16 alphanumeric processor-code characters, colon and encrypted data.");
            missingEvidence = s.processorCode() == null;
            if (s.processorCode() != null && !edata.substring(0, colon).equals(s.processorCode())) return result("EDATA-METHOD", Status.FAIL,
                    "TA-VE processor code differs from supplied boarding evidence.");
            if (identifier == '6' && (s.terminalIdentifier() == null || s.terminalIdentifier().isBlank())) {
                missingEvidence = true;
            }
            if (identifier == '7' && (s.pumpLaneNumber() == null || s.pumpLaneNumber().isBlank())
                    || identifier == 'M' && (s.cardAcceptorTerminalId() == null || s.cardAcceptorTerminalId().isBlank())) {
                return result("EDATA-METHOD", Status.FAIL, "TA-VE identifier 7 requires Element 79; M requires Segment 111 Table 018.");
            }
        }
        if (identifier == '3' || s.method() == Method.TA_VE && Boolean.TRUE.equals(s.manuallyKeyed())) {
            int separator = edata.lastIndexOf('\u001c');
            if (separator <= 0 || !edata.substring(separator + 1).matches("(0[1-9]|1[0-2])[0-9]{2}")) {
                return result("EDATA-METHOD", Status.FAIL, "Manual encrypted PAN must be followed by FS and unencrypted MMYY.");
            }
            if (s.expirationMmyy() != null && !edata.substring(separator + 1).equals(s.expirationMmyy())) {
                return result("EDATA-METHOD", Status.FAIL, "Manual EDATA expiry differs from supplied expiration.");
            }
        } else if (s.method() == Method.TA_VE && s.manuallyKeyed() == null) {
            missingEvidence = true;
        }
        if (s.method() == Method.TOKEN_ONLY) {
            return result("EDATA-METHOD", Status.REVIEW_REQUIRED,
                    "Identifier 4 declares unencrypted Track1/Track2/manual PAN. Detailed track composition and "
                            + "actual token retrieval require the entry-mode profile and external service evidence.");
        }
        if (missingEvidence) return result("EDATA-METHOD", Status.REVIEW_REQUIRED,
                "Structural checks passed but loaded PKI Key ID, boarded processor code, BUYPASS terminal "
                        + "identity or TA-VE manual/track evidence is incomplete.");
        return result("EDATA-METHOD", Status.PASS,
                "Identifier, bounded Key ID and contextual EDATA composition match; encrypted content and assigned identities need external evidence.");
    }
    private static Assessment persistence(Scenario s) {
        if (s.stage() != Stage.SUBSEQUENT) return na("EXPIRATION-TOKEN-PERSISTENCE");
        if (!validHeader(s) || s.accountNumber().charAt(9) != '0') return result("EXPIRATION-TOKEN-PERSISTENCE",
                Status.FAIL, "Subsequent account must contain TAP token identifier 0.");
        if (s.expirationMmyy() == null || !s.expirationMmyy().matches("(0[1-9]|1[0-2])[0-9]{2}")) {
            return result("EXPIRATION-TOKEN-PERSISTENCE", Status.FAIL,
                    "Subsequent transaction must send unencrypted expiration through Element 12's application-specific layout.");
        }
        String token = s.originalResponseToken() != null ? s.originalResponseToken() : s.storedOnFileToken();
        if (token == null || token.isBlank() || s.originalExpirationMmyy() == null) return result(
                "EXPIRATION-TOKEN-PERSISTENCE", Status.REVIEW_REQUIRED, "Original response token/stored expiration evidence is absent.");
        if (s.originalResponseToken() != null && s.storedOnFileToken() != null
                && !s.originalResponseToken().equals(s.storedOnFileToken())) {
            return result("EXPIRATION-TOKEN-PERSISTENCE", Status.REVIEW_REQUIRED,
                    "Initial-response and on-file token evidence disagree; resolve credential association externally.");
        }
        return condition("EXPIRATION-TOKEN-PERSISTENCE",
                s.accountNumber().substring(25).equals(token)
                        && s.expirationMmyy().equals(s.originalExpirationMmyy()),
                "Initial response token and stored expiration are reused.", "Token or expiration differs from supplied original evidence.");
    }
    private static Assessment decline(Scenario s) {
        if (s.method() != Method.TOKEN_ONLY || s.stage() != Stage.INITIAL) return na("DECLINE-TOKEN-RETRIEVAL");
        if (s.tokenRetrievalFailed() == null) return result("DECLINE-TOKEN-RETRIEVAL", Status.REVIEW_REQUIRED,
                "BUYPASS token-retrieval outcome needs external evidence.");
        if (!s.tokenRetrievalFailed()) return result("DECLINE-TOKEN-RETRIEVAL", Status.REVIEW_REQUIRED,
                "Resolvable token does not imply approval; other decline reasons remain possible.");
        return condition("DECLINE-TOKEN-RETRIEVAL", "4M".equals(s.declineCode()),
                "Failed token retrieval returns 4M.", "Failed token retrieval must return Decline Code 4M (Element 26).");
    }
    private static Assessment load(Scenario s) {
        if (s.stage() != Stage.KEY_LOAD) return na("KEY-LOAD-RESPONSE");
        if (!"K".equals(s.loadType())) return result("KEY-LOAD-RESPONSE", Status.FAIL, "Element 48 load type must be K.");
        if (s.method() == Method.RSA_PKI && (s.terminalIdentifier() == null || !s.terminalIdentifier().startsWith("++"))) {
            return result("KEY-LOAD-RESPONSE", Status.FAIL, "PKI Load Request Element 102 Device Type must be ++.");
        }
        if (s.loadApproved() == null) return result("KEY-LOAD-RESPONSE", Status.REVIEW_REQUIRED,
                "Approved/rejected load outcome needs authoritative evidence; merchant eligibility and signing-key validity are external.");
        return condition("KEY-LOAD-RESPONSE", (s.loadApproved() ? "K" : "L").equals(s.responseCode()),
                "Element 83 matches the supplied load outcome.",
                "Approved load returns K; rejected load returns L (merchant not enabled or invalid/outdated signing key).");
    }
    private static Assessment refresh(Scenario s) {
        if (!"8".equals(s.downloadIndicator())) return na("KEY-REFRESH");
        if (s.keyUpdateScheduled() == null) return result("KEY-REFRESH", Status.REVIEW_REQUIRED,
                "Element 30 value 8 requires Key and Key ID update; verify terminal behavior externally.");
        return condition("KEY-REFRESH", s.keyUpdateScheduled(), "Key/Key ID update is recorded.",
                "Element 30 value 8 signals expiring/expired Key ID and requires updating Key and Key ID.");
    }
    private static Assessment preprocessing(Scenario s) {
        if (s.method() != Method.RSA_PKI || s.stage() != Stage.INITIAL) return na("PKI-PREPROCESSING");
        if (s.merchantNumber() == null || s.merchantNumber().length() < 6 || s.plaintextBeforeEncryption() == null) {
            return result("PKI-PREPROCESSING", Status.REVIEW_REQUIRED,
                    "Controlled pre-encryption evidence is needed for 00 + first six BUYPASS Merchant Number bytes; do not log real PAN/plaintext.");
        }
        if (s.merchantNumber().length() > 15 || !s.merchantNumber().matches("[A-Za-z0-9]{6,15}")) {
            return result("PKI-PREPROCESSING", Status.FAIL,
                    "Element 102 Merchant Number evidence must contain 6-15 alphanumeric characters.");
        }
        String prefix = "00" + s.merchantNumber().substring(0, 6);
        return condition("PKI-PREPROCESSING", s.plaintextBeforeEncryption().startsWith(prefix)
                        && s.plaintextBeforeEncryption().length() > prefix.length(),
                "Supplied synthetic pre-encryption data has the merchant prefix.",
                "PKI initial Account Number must be prepended with 00 + first six Merchant Number bytes before encryption.");
    }
    private static Assessment additional(Scenario s) {
        if (s.stage() != Stage.INITIAL) return na("ADDITIONAL-DATA");
        boolean ksnRequired = s.method() == Method.TDES_DUKPT || s.method() == Method.ONGUARD_FPE || s.method() == Method.AES_DUKPT;
        if (ksnRequired && !bounded(s.additionalData().get("01"), 40)) return result("ADDITIONAL-DATA", Status.FAIL,
                "Table 052 sub-table 01 KSN is required for AES DUKPT, TDES and OnGuard (up to 40 bytes).");
        if (s.method() == Method.AES_DUKPT && !bounded(s.additionalData().get("02"), 8)) return result(
                "ADDITIONAL-DATA", Status.FAIL, "AES DUKPT requires Table 052/02 Device Type (up to 8 bytes).");
        for (var field : s.additionalData().entrySet()) {
            if ("01".equals(field.getKey()) && !bounded(field.getValue(), 40)
                    || "02".equals(field.getKey()) && !bounded(field.getValue(), 8)) {
                return result("ADDITIONAL-DATA", Status.FAIL, "Additional TransArmor field exceeds its bounded ASCII width.");
            }
        }
        return result("ADDITIONAL-DATA", Status.REVIEW_REQUIRED,
                "Known KSN/Device Type requirements checked; Table 052 TLV wording, longer Key ID/special device-type "
                        + "variants and cryptographic KSN validity require implementation evidence. No invented sub-table is used.");
    }
    private static boolean bounded(String value, int max) {
        return value != null && !value.isEmpty() && value.length() <= max
                && value.chars().allMatch(c -> c >= 32 && c <= 126);
    }
    private static String encryptedBlock(Scenario s) {
        String block = s.accountNumber().substring(25);
        if (s.method() == Method.TA_VE && block.indexOf(':') >= 0) {
            block = block.substring(block.indexOf(':') + 1);
        }
        if (s.accountNumber().charAt(9) == '3'
                || s.method() == Method.TA_VE && Boolean.TRUE.equals(s.manuallyKeyed())) {
            int separator = block.lastIndexOf('\u001c');
            if (separator >= 0) block = block.substring(0, separator);
        }
        return block;
    }
    private static Assessment initialization(Scenario s) {
        if (s.stage() != Stage.INITIALIZATION) return na("INITIALIZATION");
        if (Boolean.FALSE.equals(s.keyLoadPerformed()) || Boolean.FALSE.equals(s.regiStartPerformed())) {
            return result("INITIALIZATION", Status.FAIL, "Initialization calls for both Key Load and device RegiStart.");
        }
        return result("INITIALIZATION", Status.REVIEW_REQUIRED,
                "Key Load obtains Key/Key ID; RegiStart registers the device. Protocol is defined in the external "
                        + "ATL105 Specifications Update for TransArmor Processing, not reproduced by Appendix AA.");
    }
    private static Assessment na(String rule) { return result(rule, Status.PASS, "Not applicable to the declared method/stage."); }
    private static Assessment condition(String rule, boolean valid, String positive, String negative) {
        return result(rule, valid ? Status.PASS : Status.FAIL, valid ? positive : negative);
    }
    private static Assessment result(String rule, Status status, String reason) {
        return new Assessment("BR-SEG100-APPAA-" + rule, status, reason);
    }
}
