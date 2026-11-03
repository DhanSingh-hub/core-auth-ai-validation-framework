package com.coreauth.validator.canonical;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Bounded structural and scenario checks for ATL105 Appendix Y (3-D Secure).
 *
 * <p>This oracle checks ATL105-visible values, locations, lengths, and explicit cryptogram
 * combinations. It cannot authenticate network cryptograms or independently confirm an issuer,
 * directory-server, MPI, or merchant's 3-D Secure behavior.
 */
public final class AppendixYSecureAuthenticationOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED
    }

    public enum VisaScenario {
        NOT_APPLICABLE,
        VERIFIED_BY_VISA_ONLY,
        PAYMENT_TOKEN_ONLY,
        VERIFIED_BY_VISA_AND_TOKEN,
        UNKNOWN
    }

    public record Scenario(
            String cardBrand,
            String eciTerminalType,
            String responseCryptogramType,
            VisaScenario visaScenario,
            Integer verifiedByVisaTable035DataBytes,
            String visaRevisedCavv,
            String tavvElement237,
            String amexSafeKeyElement203,
            Boolean amexAevvApplicable,
            Boolean amexAeskApplicable,
            Boolean amexOptBlue,
            String mastercardUcafTableId,
            String mastercardUcafTableLength,
            String mastercardUcafData,
            boolean mastercardAavPresent,
            boolean mastercardDsrpPresent,
            String mastercardMpiVersion) {
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    public static final String BR_ECI_AND_BRAND_CRYPTOGRAM = "BR-SEG100-APPY-ECI-CRYPTOGRAM";
    public static final String BR_VISA_SCENARIO = "BR-SEG100-APPY-VISA-DUAL-CRYPTOGRAM";
    public static final String BR_AMEX_SAFEKEY_FORMAT = "BR-SEG100-APPY-AMEX-SAFEKEY-FORMAT";
    public static final String BR_MASTERCARD_UCAF_DSRP = "BR-SEG100-APPY-MASTERCARD-UCAF-DSRP";
    public static final String BR_MPI_VERSION = "BR-SEG100-APPY-MASTERCARD-MPI-VERSION";
    public static final String BR_AMEX_OPTBLUE = "BR-SEG100-APPY-AMEX-OPTBLUE";
    public static final String BR_PROTOCOL_FLOW = "BR-SEG100-APPY-PROTOCOL-FLOW";
    public static final String BR_AVAILABILITY = "BR-SEG100-APPY-AVAILABILITY";
    public static final String BR_CRYPTO_AUTHENTICITY = "BR-SEG100-APPY-CRYPTOGRAPHIC-AUTHENTICITY";
    public static final String BR_DISCOVER_CRYPTOGRAM = "BR-SEG100-APPY-DISCOVER-CRYPTOGRAM-MAPPING";
    public static final String BR_MASTERCARD_REMOVED_AAV_VALUES =
            "BR-SEG100-APPY-MASTERCARD-REMOVED-AAV-VALUES";

    private static final Set<String> ECI_TERMINAL_TYPES = Set.of("25", "26", "27", "28", "29");
    private static final Set<String> VISA_BRANDS = Set.of("VISA");
    private static final Pattern BASE64 = Pattern.compile("[A-Za-z0-9+/]*={0,2}");
    private static final Pattern NUMERIC_VERSION = Pattern.compile("[0-9]+(?:\\.[0-9]+)*");

    public List<Assessment> assess(Scenario scenario) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessEciAndCryptogram(scenario));
        assessments.add(assessVisaScenario(scenario));
        assessments.add(assessAmexSafeKey(scenario));
        assessments.add(assessMastercardUcafDsrp(scenario));
        assessments.add(assessMastercardMpiVersion(scenario));
        assessments.add(assessAmexOptBlue(scenario));
        assessments.add(new Assessment(BR_PROTOCOL_FLOW, Status.REVIEW_REQUIRED, "external-3ds-protocol-flow",
                "Appendix Y describes Acquirer, Issuer, and Interoperability domains and a seven-step flow "
                        + "(cardholder entry, MPI request, DS routing, ACS response, issuer authentication, "
                        + "authorization response, merchant completion). These external interactions cannot "
                        + "be certified from ATL105 message fields alone."));
        assessments.add(new Assessment(BR_AVAILABILITY, Status.REVIEW_REQUIRED, "external-3ds-availability",
                "Appendix Y directs merchants to their 3-D Secure/First Data representative for service "
                        + "availability. Merchant participation and product eligibility require authoritative "
                        + "external configuration or enrollment evidence."));
        assessments.add(new Assessment(BR_CRYPTO_AUTHENTICITY, Status.REVIEW_REQUIRED, "cryptogram-authenticity",
                "Synthetic values can check encoding and placement only. CAVV, AAV, AEVV, TAVV, and AESK "
                        + "cryptographic validity requires the relevant network/service and issuer evidence."));
        assessments.add(assessDiscoverCryptogram(scenario));
        assessments.add(new Assessment(BR_MASTERCARD_REMOVED_AAV_VALUES, Status.REVIEW_REQUIRED,
                "mastercard-removed-aav-values",
                "Appendix I states that Identity Check values 'h' and 'j' have been removed from AAV and must "
                        + "not be sent, but this oracle does not assume an undocumented data position or "
                        + "encoding for those values."));
        return List.copyOf(assessments);
    }

    private static Assessment assessEciAndCryptogram(Scenario scenario) {
        if (scenario.eciTerminalType() == null) {
            return result(BR_ECI_AND_BRAND_CRYPTOGRAM, Status.REVIEW_REQUIRED, "eci-brand-cryptogram",
                    "No ECI/Terminal Type was supplied for this 3-D Secure scenario.");
        }
        if (!ECI_TERMINAL_TYPES.contains(scenario.eciTerminalType())) {
            return result(BR_ECI_AND_BRAND_CRYPTOGRAM, Status.FAIL, "eci-brand-cryptogram",
                    "Appendix Y lists ECI/Terminal Type values 25, 26, 27, 28, and 29; found '"
                            + scenario.eciTerminalType() + "'.");
        }
        String brand = normalizeBrand(scenario.cardBrand());
        String expectedCryptogram = switch (brand) {
            case "VISA" -> "CAVV";
            case "MASTERCARD" -> "AAV";
            case "AMEX" -> "AEVV";
            case "DISCOVER" -> null;
            default -> null;
        };
        if (expectedCryptogram == null) {
            return result(BR_ECI_AND_BRAND_CRYPTOGRAM, Status.REVIEW_REQUIRED, "eci-brand-cryptogram",
                    "The ECI/Terminal Type is in the Appendix Y range, but Appendix Y does not provide a "
                            + "cryptogram mapping for card brand '" + scenario.cardBrand() + "'.");
        }
        if (scenario.responseCryptogramType() == null
                || !expectedCryptogram.equalsIgnoreCase(scenario.responseCryptogramType())) {
            return result(BR_ECI_AND_BRAND_CRYPTOGRAM, Status.FAIL, "eci-brand-cryptogram",
                    brand + " 3-D Secure Authorization Response must use " + expectedCryptogram
                            + "; found '" + scenario.responseCryptogramType() + "'.");
        }
        return result(BR_ECI_AND_BRAND_CRYPTOGRAM, Status.PASS, "eci-brand-cryptogram",
                "ECI/Terminal Type '" + scenario.eciTerminalType() + "' is listed and the expected "
                        + expectedCryptogram + " mapping for " + brand + " is recorded. Cryptogram presence "
                        + "and authenticity are assessed separately.");
    }

    private static Assessment assessVisaScenario(Scenario scenario) {
        String brand = normalizeBrand(scenario.cardBrand());
        if (!VISA_BRANDS.contains(brand)) {
            if ("INTERLINK".equals(brand)) {
                return result(BR_VISA_SCENARIO, Status.REVIEW_REQUIRED, "visa-cavv-tavv-matrix",
                        "Element 195 mentions Visa/Interlink tokenized transactions, but Appendix Y's "
                                + "scenario matrix is Visa-specific; Interlink scenario applicability requires "
                                + "an authoritative source.");
            }
            return result(BR_VISA_SCENARIO, Status.PASS, "visa-cavv-tavv-matrix",
                    "The Visa-only CAVV/TAVV matrix does not apply to this card brand.");
        }
        VisaScenario visaScenario = scenario.visaScenario();
        if (visaScenario == null || visaScenario == VisaScenario.UNKNOWN
                || visaScenario == VisaScenario.NOT_APPLICABLE) {
            return result(BR_VISA_SCENARIO, Status.REVIEW_REQUIRED, "visa-cavv-tavv-matrix",
                    "Visa token/Verified-by-Visa scenario is not identified; the appendix's non-exhaustive "
                            + "matrix cannot be selected.");
        }
        boolean hasLegacyCavv = scenario.verifiedByVisaTable035DataBytes() != null;
        boolean hasRevisedCavv = scenario.visaRevisedCavv() != null;
        boolean hasTavv = scenario.tavvElement237() != null;
        return switch (visaScenario) {
            case VERIFIED_BY_VISA_ONLY -> {
                if (!hasLegacyCavv || scenario.verifiedByVisaTable035DataBytes() != 40
                        || hasRevisedCavv || hasTavv) {
                    yield result(BR_VISA_SCENARIO, Status.FAIL, "visa-cavv-tavv-matrix",
                            "Verified-by-Visa-only uses Table 035 XID(20)+CAVV(20), 40 binary bytes; "
                                    + "TAVV and revised-format CAVV are not applicable in this matrix row.");
                }
                yield result(BR_VISA_SCENARIO, Status.PASS, "visa-cavv-tavv-matrix",
                        "Verified-by-Visa-only has 40-byte Table 035 data and no token TAVV.");
            }
            case PAYMENT_TOKEN_ONLY -> {
                if (!hasRevisedCavv || scenario.visaRevisedCavv().length() != 20 || hasLegacyCavv || hasTavv) {
                    yield result(BR_VISA_SCENARIO, Status.FAIL, "visa-cavv-tavv-matrix",
                            "Payment-token-only uses revised-format CAVV in Element 195 (20 bytes); the matrix "
                                    + "marks TAVV not applicable for this row.");
                }
                yield result(BR_VISA_SCENARIO, Status.PASS, "visa-cavv-tavv-matrix",
                        "Payment-token-only has a 20-character revised-format CAVV and no TAVV.");
            }
            case VERIFIED_BY_VISA_AND_TOKEN -> {
                if (!hasRevisedCavv || scenario.visaRevisedCavv().length() != 20 || !hasTavv
                        || !validBase64(scenario.tavvElement237(), 28) || hasLegacyCavv) {
                    yield result(BR_VISA_SCENARIO, Status.FAIL, "visa-cavv-tavv-matrix",
                            "Combined Verified-by-Visa and Digital Wallet requires revised-format CAVV "
                                    + "(Element 195, 20 bytes) and TAVV (Element 237, 28 base64 characters).");
                }
                yield result(BR_VISA_SCENARIO, Status.PASS, "visa-cavv-tavv-matrix",
                        "Combined Verified-by-Visa and token scenario has revised-format CAVV and a 28-character "
                                + "base64 TAVV.");
            }
            default -> throw new IllegalStateException("Unhandled Visa scenario: " + visaScenario);
        };
    }

    private static Assessment assessAmexSafeKey(Scenario scenario) {
        if (!"AMEX".equals(normalizeBrand(scenario.cardBrand()))) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.PASS, "amex-safekey-format",
                    "SafeKey-specific field rules do not apply to this card brand.");
        }
        String data = scenario.amexSafeKeyElement203();
        if (Boolean.TRUE.equals(scenario.amexOptBlue()) && data == null) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.PASS, "amex-safekey-format",
                    "SafeKey is unavailable for Amex OptBlue merchants and no SafeKey data was supplied.");
        }
        if (Boolean.TRUE.equals(scenario.amexOptBlue()) && data != null) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.FAIL, "amex-safekey-format",
                    "Appendix Y states that American Express SafeKey is not available for OptBlue merchants.");
        }
        if (data == null) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.REVIEW_REQUIRED, "amex-safekey-format",
                    "No SafeKey data was supplied; whether SafeKey is expected depends on authentication and "
                            + "merchant eligibility context.");
        }
        if (data.length() != 58 || !data.startsWith("SK")) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.FAIL, "amex-safekey-format",
                    "SafeKey Element 203 must be 58 characters: fixed 'SK' followed by 56 data characters.");
        }
        if (scenario.amexAevvApplicable() == null || scenario.amexAeskApplicable() == null) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.REVIEW_REQUIRED, "amex-safekey-format",
                    "SafeKey applicability flags are needed to determine whether either 28-character block "
                            + "must contain data or be space-filled.");
        }
        String aevv = data.substring(2, 30);
        String aesk = data.substring(30, 58);
        if (scenario.amexAevvApplicable() && !validBase64(aevv, 28)
                || !scenario.amexAevvApplicable() && !isSpaceFilled(aevv)) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.FAIL, "amex-safekey-format",
                    "SafeKey AEVV block (bytes 3-30) must be 28 base64 characters when applicable or 28 "
                            + "spaces when not applicable.");
        }
        if (scenario.amexAeskApplicable() && !validBase64(aesk, 28)
                || !scenario.amexAeskApplicable() && !isSpaceFilled(aesk)) {
            return result(BR_AMEX_SAFEKEY_FORMAT, Status.FAIL, "amex-safekey-format",
                    "SafeKey AESK block (bytes 31-58) must be 28 base64 characters when applicable or 28 "
                            + "spaces when optional/not applicable.");
        }
        return result(BR_AMEX_SAFEKEY_FORMAT, Status.PASS, "amex-safekey-format",
                "SafeKey has the fixed 'SK' indicator, 28-character AEVV and AESK blocks, and required "
                        + "space-fill for inapplicable blocks.");
    }

    private static Assessment assessMastercardUcafDsrp(Scenario scenario) {
        if (!"MASTERCARD".equals(normalizeBrand(scenario.cardBrand()))) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.PASS, "mastercard-ucaf-dsrp",
                    "MasterCard UCAF/DSRP composition rules do not apply to this card brand.");
        }
        boolean hasUcaf = scenario.mastercardUcafTableId() != null
                || scenario.mastercardUcafTableLength() != null
                || scenario.mastercardUcafData() != null;
        if (scenario.mastercardAavPresent() && !hasUcaf) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.FAIL, "mastercard-ucaf-dsrp",
                    "MasterCard AAV must be carried in Segment 111 Table ID 036.");
        }
        if (hasUcaf && !"036".equals(scenario.mastercardUcafTableId())) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.FAIL, "mastercard-ucaf-dsrp",
                    "MasterCard AAV must be carried in Segment 111 Table ID 036.");
        }
        if (hasUcaf && !validUcaf(scenario.mastercardUcafTableLength(), scenario.mastercardUcafData())) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.FAIL, "mastercard-ucaf-dsrp",
                    "UCAF Table 036 must contain a three-character security level and either no UCAF data "
                            + "(length 003) or the 32-character UCAF data field (length 035).");
        }
        if (scenario.mastercardDsrpPresent()
                && !validBase64(scenario.tavvElement237(), 28)) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.FAIL, "mastercard-ucaf-dsrp",
                    "MasterCard token/DSRP cryptograms must use Segment 123 Element 237 (28 base64 characters).");
        }
        if (scenario.mastercardAavPresent() && scenario.mastercardDsrpPresent()
                && (scenario.mastercardUcafData() == null
                        || !scenario.mastercardUcafData().substring(3).startsWith("4328")
                        || !"21".equals(securityPrefix(scenario.mastercardUcafTableLength(),
                                scenario.mastercardUcafData())))) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.FAIL, "mastercard-ucaf-dsrp",
                    "When AAV and DSRP are both present, UCAF Security Level positions 1-2 must be '21'; "
                            + "AAV data must use Appendix I's subfield 43 / length 28 framing.");
        }
        if (scenario.mastercardAavPresent() && !scenario.mastercardDsrpPresent()
                && !"21".equals(securityPrefix(scenario.mastercardUcafTableLength(),
                        scenario.mastercardUcafData()))
                && !"24".equals(securityPrefix(scenario.mastercardUcafTableLength(),
                        scenario.mastercardUcafData()))) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.FAIL, "mastercard-ucaf-dsrp",
                    "UCAF Security Level positions 1-2 must be '21' or '24' when UCAF is present.");
        }
        if (scenario.mastercardAavPresent()
                && (!"035".equals(scenario.mastercardUcafTableLength())
                        || scenario.mastercardUcafData() == null
                        || !"2".equals(scenario.mastercardUcafData().substring(2, 3)))) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.FAIL, "mastercard-ucaf-dsrp",
                    "AAV present in UCAF requires the 32-character UCAF data field and Security Level "
                            + "position 3 value '2' (UCAF data present).");
        }
        if (!scenario.mastercardAavPresent() && !scenario.mastercardDsrpPresent()) {
            return result(BR_MASTERCARD_UCAF_DSRP, Status.REVIEW_REQUIRED, "mastercard-ucaf-dsrp",
                    "No MasterCard AAV/DSRP scenario was supplied.");
        }
        return result(BR_MASTERCARD_UCAF_DSRP, Status.PASS, "mastercard-ucaf-dsrp",
                "MasterCard AAV is in UCAF Table 036, DSRP uses Element 237, and the combined case uses "
                        + "Security Level '21'.");
    }

    private static Assessment assessMastercardMpiVersion(Scenario scenario) {
        if (!"MASTERCARD".equals(normalizeBrand(scenario.cardBrand()))) {
            return result(BR_MPI_VERSION, Status.PASS, "mastercard-mpi-version",
                    "MasterCard SecureCode MPI version requirement does not apply to this card brand.");
        }
        String version = scenario.mastercardMpiVersion();
        if (version == null || !NUMERIC_VERSION.matcher(version).matches()) {
            return result(BR_MPI_VERSION, Status.REVIEW_REQUIRED, "mastercard-mpi-version",
                    "Merchant Server Plug-In version evidence is absent or not a comparable numeric version.");
        }
        if (compareVersion(version, "1.0.2") < 0) {
            return result(BR_MPI_VERSION, Status.FAIL, "mastercard-mpi-version",
                    "MasterCard SecureCode requires 3-D Secure v1.0.2 compliant MPI software or better; "
                            + "found version " + version + ".");
        }
        return result(BR_MPI_VERSION, Status.PASS, "mastercard-mpi-version",
                "The supplied numeric MPI version is v1.0.2 or better.");
    }

    private static Assessment assessAmexOptBlue(Scenario scenario) {
        if (!"AMEX".equals(normalizeBrand(scenario.cardBrand()))) {
            return result(BR_AMEX_OPTBLUE, Status.PASS, "amex-optblue-availability",
                    "American Express OptBlue availability is not relevant to this card brand.");
        }
        if (scenario.amexOptBlue() == null) {
            return result(BR_AMEX_OPTBLUE, Status.REVIEW_REQUIRED, "amex-optblue-availability",
                    "Merchant OptBlue status is not supplied; Appendix Y states SafeKey is unavailable to "
                            + "OptBlue merchants.");
        }
        if (scenario.amexOptBlue() && scenario.amexSafeKeyElement203() != null) {
            return result(BR_AMEX_OPTBLUE, Status.FAIL, "amex-optblue-availability",
                    "SafeKey data must not be used for an Amex OptBlue merchant.");
        }
        return result(BR_AMEX_OPTBLUE, Status.PASS, "amex-optblue-availability",
                scenario.amexOptBlue()
                        ? "SafeKey data is absent for this OptBlue merchant."
                        : "The merchant is not identified as OptBlue.");
    }

    private static Assessment assessDiscoverCryptogram(Scenario scenario) {
        if (!"DISCOVER".equals(normalizeBrand(scenario.cardBrand()))) {
            return result(BR_DISCOVER_CRYPTOGRAM, Status.PASS, "discover-cryptogram-mapping",
                    "Discover-specific cryptogram mapping does not apply to this card brand.");
        }
        return result(BR_DISCOVER_CRYPTOGRAM, Status.REVIEW_REQUIRED, "discover-cryptogram-mapping",
                "Appendix Y includes Discover in its general 3-D Secure domains but omits Discover from the "
                        + "cryptogram mapping grid; do not infer a mapping from another card brand.");
    }

    private static boolean validUcaf(String length, String data) {
        if (length == null || data == null || data.length() < 3) {
            return false;
        }
        String security = data.substring(0, 3);
        if (!("21".equals(security.substring(0, 2)) || "24".equals(security.substring(0, 2)))
                || security.charAt(2) < '0' || security.charAt(2) > '7') {
            return false;
        }
        return ("003".equals(length) && data.length() == 3)
                || ("035".equals(length) && data.length() == 35 && data.substring(3).length() == 32);
    }

    private static String securityPrefix(String length, String data) {
        return validUcaf(length, data) ? data.substring(0, 2) : "";
    }

    private static boolean validBase64(String data, int expectedLength) {
        if (data == null || data.length() != expectedLength || !BASE64.matcher(data).matches()
                || data.length() % 4 != 0) {
            return false;
        }
        try {
            Base64.getDecoder().decode(data);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private static boolean isSpaceFilled(String data) {
        return data.chars().allMatch(character -> character == ' ');
    }

    private static String normalizeBrand(String brand) {
        return brand == null ? "" : brand.replace(" ", "").toUpperCase(Locale.ROOT);
    }

    private static int compareVersion(String left, String right) {
        String[] leftParts = left.split("\\.");
        String[] rightParts = right.split("\\.");
        int length = Math.max(leftParts.length, rightParts.length);
        for (int index = 0; index < length; index++) {
            BigInteger leftPart = index < leftParts.length ? new BigInteger(leftParts[index]) : BigInteger.ZERO;
            BigInteger rightPart = index < rightParts.length ? new BigInteger(rightParts[index]) : BigInteger.ZERO;
            int comparison = leftPart.compareTo(rightPart);
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }

    private static Assessment result(String id, Status status, String scope, String reason) {
        return new Assessment(id, status, scope, reason);
    }
}
