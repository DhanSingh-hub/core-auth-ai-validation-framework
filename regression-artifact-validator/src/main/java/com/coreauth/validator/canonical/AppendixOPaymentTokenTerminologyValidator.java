package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Independent, bounded checks for ATL105 Appendix O (Payment Token Terminologies).
 *
 * <p>Transcribed from {@code specifications/ATL105/docs/specs/extracted_text.txt} Appendix O-1
 * (source lines around 35085-35126, immediately preceding Appendix P). Appendix O is a glossary of
 * tokenization terms &mdash; Cryptogram Token Data, Payment Token, Payment Token Expiration Date,
 * Payment Token Requestor, Payment Token Service Provider, and POS Entry Mode &mdash; rather than a
 * code table, so this validator bounds each check to exactly what the glossary text asserts.
 *
 * <p>Out of scope for every check below: whether a given 6-digit BIN is actually registered/flagged
 * as a token BIN range (no BIN registry is part of Appendix O), whether a particular recognized POS
 * Entry Mode code specifically represents a tokenized presentment (Appendix O does not enumerate
 * which Appendix J codes qualify), and cryptographic authenticity of cryptogram data (Appendix O
 * only describes byte lengths and block framing, not cryptographic verification). "Payment Token
 * Requestor" and "Payment Token Service Provider" are pure glossary definitions with no field
 * format, length, or code values attached in the source, so they have no independently assertable
 * check and are intentionally not modeled here.
 */
public final class AppendixOPaymentTokenTerminologyValidator {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED,
        NOT_ASSERTABLE
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    /** Minimal tokenization context needed to evaluate every bounded Appendix O check at once. */
    public record TokenContext(
            String tokenAccountNumber,
            String cardholderPan,
            String tokenExpirationDate,
            String cryptogramBase64,
            String posEntryMode) {
    }

    /** Result of splitting cryptogram bytes into Token Data Block A (always) and Block B (56-byte case only). */
    public record CryptogramBlocks(String blockABase64, String blockBBase64) {
    }

    public static final String BR_TOKEN_ACCOUNT = "BR-SEG100-APPO-TOKEN-ACCOUNT";
    public static final String BR_TOKEN_EXPIRATION = "BR-SEG100-APPO-TOKEN-EXPIRATION";
    public static final String BR_CRYPTOGRAM = "BR-SEG100-APPO-CRYPTOGRAM";
    public static final String BR_CRYPTOGRAM_BLOCK_SPLIT = "BR-SEG100-APPO-CRYPTOGRAM-BLOCK-SPLIT";
    public static final String BR_TOKEN_NOT_PAN = "BR-SEG100-APPO-TOKEN-NOT-PAN";
    public static final String BR_TOKEN_LUHN = "BR-SEG100-APPO-TOKEN-LUHN";
    public static final String BR_ENTRY_MODE = "BR-SEG100-APPO-ENTRY-MODE";

    private final AppendixJPosEntryModeOracle posEntryModeOracle = new AppendixJPosEntryModeOracle();

    /** Runs every independently-assertable Appendix O check against one token context. */
    public List<Assessment> assess(TokenContext context) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessTokenAccountRepresentation(context.tokenAccountNumber()));
        assessments.add(assessTokenLuhn(context.tokenAccountNumber()));
        assessments.add(assessTokenNotPan(context.tokenAccountNumber(), context.cardholderPan()));
        assessments.add(assessTokenExpiration(context.tokenExpirationDate()));
        assessments.add(assessCryptogram(context.cryptogramBase64()));
        assessments.add(assessCryptogramBlockSplit(context.cryptogramBase64()));
        assessments.add(assessEntryMode(context.posEntryMode()));
        return assessments;
    }

    /**
     * Payment Token: "a 13 to 19-digit numeric value that must pass basic validation rules of an
     * account number." Representation only; BIN-range designation is not part of Appendix O.
     */
    public Assessment assessTokenAccountRepresentation(String tokenAccountNumber) {
        if (tokenAccountNumber == null || !tokenAccountNumber.matches("[0-9]{13,19}")) {
            return assessment(BR_TOKEN_ACCOUNT, Status.FAIL, "REPRESENTATION",
                    "Payment Token must be a 13 to 19-digit numeric value.");
        }
        return assessment(BR_TOKEN_ACCOUNT, Status.PASS, "REPRESENTATION_ONLY",
                "Token account number is a 13-19 digit numeric value; membership in a designated token BIN "
                        + "range is not independently assertable from Appendix O alone (no BIN registry is part of it).");
    }

    /**
     * Payment Token: "must pass basic validation rules of an account number, including the LUHN
     * check." Algorithm only; token BIN-range designation remains out of scope.
     */
    public Assessment assessTokenLuhn(String tokenAccountNumber) {
        if (tokenAccountNumber == null || !tokenAccountNumber.matches("[0-9]{13,19}")) {
            return assessment(BR_TOKEN_LUHN, Status.FAIL, "REPRESENTATION",
                    "Payment Token must be a 13 to 19-digit numeric value before a LUHN check can apply.");
        }
        if (!passesLuhn(tokenAccountNumber)) {
            return assessment(BR_TOKEN_LUHN, Status.FAIL, "CHECK_DIGIT",
                    "Payment Token fails the LUHN (modulo-10) check digit.");
        }
        return assessment(BR_TOKEN_LUHN, Status.PASS, "LUHN_ALGORITHM_ONLY",
                "Payment Token passes the LUHN check digit; whether it falls within a BIN range flagged as a "
                        + "designated token BIN range is not independently assertable from Appendix O alone.");
    }

    /** Payment Token: "must not have the same value as a cardholder PAN." */
    public Assessment assessTokenNotPan(String tokenAccountNumber, String cardholderPan) {
        if (cardholderPan == null || cardholderPan.isBlank()) {
            return assessment(BR_TOKEN_NOT_PAN, Status.REVIEW_REQUIRED, "NOT_ASSERTABLE_WITHOUT_PAN",
                    "No cardholder PAN was supplied, so token-vs-PAN inequality cannot be evaluated.");
        }
        if (tokenAccountNumber != null && tokenAccountNumber.equals(cardholderPan)) {
            return assessment(BR_TOKEN_NOT_PAN, Status.FAIL, "VALUE_INEQUALITY_ONLY",
                    "Payment Token must not be the same value as the cardholder's actual PAN.");
        }
        return assessment(BR_TOKEN_NOT_PAN, Status.PASS, "VALUE_INEQUALITY_ONLY",
                "Payment Token value differs from the supplied cardholder PAN.");
    }

    /**
     * Payment Token Expiration Date: "populated in the PAN Expiration Date field during transaction
     * processing." Format check only; whether a given transaction "requires it" is conditional
     * language Appendix O does not define a trigger for, so an absent value stays REVIEW_REQUIRED
     * rather than FAIL.
     */
    public Assessment assessTokenExpiration(String tokenExpirationDate) {
        if (tokenExpirationDate == null || tokenExpirationDate.isBlank()) {
            return assessment(BR_TOKEN_EXPIRATION, Status.REVIEW_REQUIRED, "PRESENCE_NOT_DETERMINABLE",
                    "No Token Expiration Date was supplied; Appendix O does not define when population is required.");
        }
        if (!tokenExpirationDate.matches("[0-9]{2}(0[1-9]|1[0-2])")) {
            return assessment(BR_TOKEN_EXPIRATION, Status.FAIL, "REPRESENTATION",
                    "Token Expiration Date must be YYMM with a valid month 01-12 (same shape as PAN Expiration Date).");
        }
        return assessment(BR_TOKEN_EXPIRATION, Status.PASS, "FORMAT_ONLY",
                "Token Expiration Date is populated in YYMM format in the PAN Expiration Date field.");
    }

    /**
     * Cryptogram Token Data: "28 bytes or 56 bytes of Cryptogram Data in base64 format." Length only;
     * cryptographic authenticity is not evaluated.
     */
    public Assessment assessCryptogram(String cryptogramBase64) {
        Optional<byte[]> decoded = decodeCryptogram(cryptogramBase64);
        if (decoded.isEmpty()) {
            return assessment(BR_CRYPTOGRAM, Status.FAIL, "INVALID_BASE64",
                    "Cryptogram Data must be valid base64.");
        }
        int length = decoded.get().length;
        if (length != 28 && length != 56) {
            return assessment(BR_CRYPTOGRAM, Status.FAIL, "INVALID_LENGTH",
                    "Decoded Cryptogram Data must be exactly 28 or 56 bytes; was " + length + " bytes.");
        }
        return assessment(BR_CRYPTOGRAM, Status.PASS, "LENGTH_ONLY",
                "Cryptogram Data decodes to " + length + " bytes of base64 token data; cryptographic "
                        + "authenticity of the cryptogram content is not evaluated.");
    }

    /**
     * Cryptogram Token Data block framing: 56 bytes -> Token Data Block A then Block B; 28 bytes ->
     * Block A only.
     */
    public Assessment assessCryptogramBlockSplit(String cryptogramBase64) {
        Optional<CryptogramBlocks> blocks = deriveCryptogramBlocks(cryptogramBase64);
        if (blocks.isEmpty()) {
            return assessment(BR_CRYPTOGRAM_BLOCK_SPLIT, Status.FAIL, "INVALID_LENGTH_OR_BASE64",
                    "Cryptogram Data must decode to exactly 28 or 56 bytes before it can be split into token data blocks.");
        }
        CryptogramBlocks result = blocks.get();
        String reason = result.blockBBase64() == null
                ? "28 bytes of Cryptogram Data is interpreted as Token Data Block A only."
                : "56 bytes of Cryptogram Data is interpreted as Token Data Block A followed by Token Data Block B.";
        return assessment(BR_CRYPTOGRAM_BLOCK_SPLIT, Status.PASS, "BLOCK_FRAMING_ONLY", reason);
    }

    /** Splits decoded cryptogram bytes into Block A (first 28 bytes) and, for 56-byte input, Block B. */
    public Optional<CryptogramBlocks> deriveCryptogramBlocks(String cryptogramBase64) {
        Optional<byte[]> decoded = decodeCryptogram(cryptogramBase64);
        if (decoded.isEmpty()) {
            return Optional.empty();
        }
        byte[] bytes = decoded.get();
        if (bytes.length != 28 && bytes.length != 56) {
            return Optional.empty();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String blockA = encoder.encodeToString(java.util.Arrays.copyOfRange(bytes, 0, 28));
        String blockB = bytes.length == 56 ? encoder.encodeToString(java.util.Arrays.copyOfRange(bytes, 28, 56)) : null;
        return Optional.of(new CryptogramBlocks(blockA, blockB));
    }

    private static Optional<byte[]> decodeCryptogram(String cryptogramBase64) {
        if (cryptogramBase64 == null || cryptogramBase64.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Base64.getDecoder().decode(cryptogramBase64));
        } catch (IllegalArgumentException invalidBase64) {
            return Optional.empty();
        }
    }

    /**
     * POS Entry Mode: "A value that is used to indicate the token presentment mode through which the
     * token is presented for payment." Appendix O does not itself enumerate which Appendix J POS
     * Entry Mode codes represent a tokenized presentment, so this check only confirms the supplied
     * value is a recognized POS Entry Mode code (delegating to the Appendix J oracle); it does not
     * certify that the specific code necessarily reflects token presentment.
     */
    public Assessment assessEntryMode(String posEntryMode) {
        AppendixJPosEntryModeOracle.Result result = posEntryModeOracle.evaluate(posEntryMode);
        if (result.outcome() != AppendixJPosEntryModeOracle.Outcome.PASS) {
            return assessment(BR_ENTRY_MODE, Status.FAIL, "CODE_RECOGNITION_ONLY",
                    "POS Entry Mode value is not a recognized Appendix J code: " + result.outcome() + ".");
        }
        return assessment(BR_ENTRY_MODE, Status.PASS, "CODE_RECOGNITION_ONLY",
                "POS Entry Mode value is a recognized Appendix J code; Appendix O does not identify which codes "
                        + "specifically represent token presentment, so that narrower claim is not independently assertable.");
    }

    /** Generic LUHN (modulo-10) check digit over the full numeric string, including the check digit itself. */
    private static boolean passesLuhn(String digits) {
        int sum = 0;
        boolean doubleDigit = true;
        for (int i = digits.length() - 2; i >= 0; i--) {
            int digit = digits.charAt(i) - '0';
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        int checkDigit = digits.charAt(digits.length() - 1) - '0';
        return (sum + checkDigit) % 10 == 0;
    }

    private static Assessment assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
        return new Assessment(businessRequirementId, status, assertionScope, reason);
    }
}
