package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HexFormat;

final class AppendixSyntheticFixtureDrafts {
    private AppendixSyntheticFixtureDrafts() { }

    static ObjectNode create(JsonNode candidate, JsonNode requirement, ObjectMapper mapper) throws Exception {
        JsonNode testCaseIds = candidate.path("candidateTestCaseIds");
        JsonNode anchors = requirement.path("sourceAnchors");
        String id = candidate.path("sourceArtifactId").asText();
        if (!"EXECUTABLE".equals(candidate.path("producerClaimedReadiness").asText())
                || id.isBlank() || !testCaseIds.isArray() || testCaseIds.isEmpty()
                || !anchors.isArray() || anchors.isEmpty()) return null;

        ObjectNode draft = mapper.createObjectNode();
        draft.put("id", id);
        draft.set("testCaseIds", testCaseIds.deepCopy());
        draft.set("sourceAnchors", anchors.deepCopy());
        draft.put("expectedValidation", "REVIEW");
        draft.put("fileName", id + ".json");
        draft.put("readiness", "REVIEW_REQUIRED");
        draft.set("payload", payloadFor(id, mapper));
        draft.putObject("response");
        return draft;
    }

    private static ObjectNode payloadFor(String id, ObjectMapper mapper) throws Exception {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode request = payload.putObject("request");
        ObjectNode controls = payload.putObject("testControls");
        request.put("specificationVersion", "2026-3");

        switch (id) {
            case "TD-SEG100-APPO-TOKEN-ACCOUNT" -> {
                standardSegment(request).put("accountNumber", "5555555555554444");
                controls.put("validateTokenLengthAndLuhn", true);
                controls.put("tokenBinConfiguration", "REVIEW_REQUIRED");
            }
            case "TD-SEG100-APPO-TOKEN-NOT-PAN" -> {
                standardSegment(request).put("accountNumber", "5555555555554444");
                controls.put("cardholderPan", "4111111111111111");
                controls.put("assertTokenDiffersFromPan", true);
            }
            case "TD-SEG100-APPO-TOKEN-LUHN" -> {
                standardSegment(request).put("accountNumber", "5555555555554445");
                controls.put("validateTokenLuhn", true);
                controls.put("tokenBinConfiguration", "REVIEW_REQUIRED");
            }
            case "TD-SEG100-APPO-TOKEN-EXPIRATION" -> {
                standardSegment(request).put("panExpirationDate", "2512");
                controls.put("validateTokenExpirationPopulation", true);
            }
            case "TD-SEG100-APPO-CRYPTOGRAM-FORMAT" -> {
                standardSegment(request).put("tokenCryptogram", base64Bytes(28));
                controls.put("expectedCryptogramBytes", 28);
            }
            case "TD-SEG100-APPO-CRYPTOGRAM-BLOCK-SPLIT" -> {
                standardSegment(request).put("tokenCryptogram", base64Bytes(56));
                controls.put("expectedBlockABytes", 28);
                controls.put("expectedBlockBBytes", 28);
            }
            case "TD-SEG100-APPO-ENTRY-MODE" -> {
                standardSegment(request).put("accountNumber", "5555555555554444");
                standardSegment(request).put("posEntryMode", "10");
                controls.put("tokenPresentmentMode", "REVIEW_REQUIRED");
            }
            case "TD-SEG100-APPR-EMV-COMPANION" -> {
                request.putObject("dataSection1").put("numberOfSegments", "02");
                standardSegment(request).put("segmentType", "100");
                ArrayNode section3 = request.putArray("dataSection3");
                section3.addObject().put("segmentType", "130");
                controls.putArray("expectedSegments").add("100").add("130");
            }
            case "TD-SEG100-APPR-CHIP-LENGTH", "TD-SEG100-APPR-TLV-FORMAT" -> {
                ObjectNode emv = request.putObject("emvRequestData");
                emv.put("segmentType", "130");
                emv.put("emvChipDataLength", "010");
                emv.put("emvChipData", "9F0607A0000000980840");
                controls.put("validateChipDataLengthAndTlv", true);
            }
            case "TD-SEG100-APPR-CROSS-FIELD" -> {
                ObjectNode segment100 = standardSegment(request);
                segment100.put("transactionAmount", "000000000900");
                segment100.put("transactionType", "00");
                segment100.put("currencyCode", "0978");
                segment100.put("terminalCountryCode", "0840");
                ObjectNode emv = request.putObject("emvRequestData");
                emv.put("emvChipDataLength", "022");
                emv.put("emvChipData", "9F02060000000009009C01005F2A0209789F1A020840");
                controls.put("comparePlaintextEmvFields", true);
            }
            case "TD-SEG100-APPS-CA-FILE-HEADER" -> {
                ObjectNode file = request.putObject("caPublicKeyFile");
                file.put("fileName", "CA_KEYS");
                file.put("content", "CA_KEYS,0100\r\n");
                controls.put("validateCaKeyFileHeader", true);
            }
            case "TD-SEG100-APPS-CA-KEY-RECORD" -> {
                String rid = "A000000003";
                String index = "01";
                String modulus = "ABCD";
                String exponent = "03";
                String checksum = HexFormat.of().withUpperCase().formatHex(MessageDigest.getInstance("SHA-1")
                        .digest((rid + index + modulus + exponent).getBytes(StandardCharsets.US_ASCII)));
                ObjectNode record = request.putObject("caPublicKeyRecord");
                record.put("expiryDate", "12312030");
                record.put("hashAlgorithm", "01");
                record.put("publicKeyAlgorithm", "01");
                record.put("rid", rid);
                record.put("index", index);
                record.put("modulus", modulus);
                record.put("exponent", exponent);
                record.put("checksum", checksum);
                controls.put("validateSyntheticCaKeyRecord", true);
            }
            case "TD-SEG100-APPT-EMV-TABLE-DATA" -> {
                tableData(request, "001", "6", "EMVYES");
                controls.put("validateEmvTableData", true);
            }
            case "TD-SEG100-APPT-CARC" -> {
                tableData(request, "002", "1", "A");
                controls.put("validateCarcFieldLengthOnly", true);
            }
            case "TD-SEG100-APPT-ECHO" -> {
                ObjectNode response = request.putObject("authorizationResponse");
                tableData(response, "001", "6", "EMVYES");
                ObjectNode followUp = request.putObject("followUpAdvice");
                tableData(followUp, "001", "6", "EMVYES");
                controls.put("assertByteForByteEcho", true);
            }
            case "TD-SEG100-APPY-ECI-CRYPTOGRAM" -> {
                ObjectNode response = request.putObject("financialResponse");
                response.put("cardBrand", "VISA");
                response.put("eci", "25");
                response.put("brandCryptogram", base64Bytes(28));
                controls.put("validateEciRangeAndBrandField", true);
                controls.put("cryptogramAuthenticity", "OUT_OF_SCOPE");
            }
            case "TD-SEG100-APPY-AMEX-SAFEKEY-FORMAT" -> {
                ObjectNode response = request.putObject("financialResponse");
                response.put("safeKeyIndicator", "SK");
                response.put("safeKeyData", base64Bytes(56));
                response.put("inapplicablePrefix", "                            ");
                controls.put("validateSafeKeyStructure", true);
            }
            default -> { return null; }
        }
        return payload;
    }

    private static ObjectNode standardSegment(ObjectNode request) {
        return request.withObject("dataSection2").withObject("standardSegment");
    }

    private static void tableData(ObjectNode parent, String tableId, String tableLength, String value) {
        ObjectNode table = parent.putObject("emvAdditionalInformation");
        table.put("tableId", tableId);
        table.put("tableLength", tableLength);
        table.put("tableData", value);
    }

    private static String base64Bytes(int count) {
        byte[] bytes = new byte[count];
        for (int index = 0; index < count; index++) bytes[index] = (byte) (index + 1);
        return Base64.getEncoder().encodeToString(bytes);
    }
}