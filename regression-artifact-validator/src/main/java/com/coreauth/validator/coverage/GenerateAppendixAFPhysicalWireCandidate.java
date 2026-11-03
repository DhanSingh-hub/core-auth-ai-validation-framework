package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

public final class GenerateAppendixAFPhysicalWireCandidate {
    public static final String FILE_NAME = "appendix-af-physical-wire-candidate-v1.json";
    private static final char FIELD_SEPARATOR = '\u001c';
    private static final String AF_PDF_SHA256 = "568c04d3081cac6c5cc545adee9c6f8f3a34334e788fdf5ee0260ffef57c77af";
    private static final List<String> STANDARD_ORDER = List.of("85", "84", "44", "102", "78", "2", "12", "33",
            "79", "41", "58", "99", "17", "86", "5", "49", "121");
    private final ObjectMapper mapper = new ObjectMapper();

    private GenerateAppendixAFPhysicalWireCandidate() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode candidate = buildCandidate();
        Path output = Atl105Paths.testOutput().resolve(Path.of("test-solution-independent-review", FILE_NAME));
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), candidate);
        System.out.printf("Wrote %s bytes=%d status=%s response=%s%n", output,
                candidate.path("request").path("applicationPayloadByteCount").asInt(),
                candidate.path("status").asText(), candidate.path("response").path("status").asText());
    }

    public static ObjectNode buildCandidate() throws Exception {
        return new GenerateAppendixAFPhysicalWireCandidate().create();
    }

    private ObjectNode create() throws Exception {
        Path baseSource = Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt"));
        Path table82Source = Atl105Paths.docs().resolve(Path.of("specs", "kb", "appendix-af-table82-source-quotes.txt"));
        Path responseSource = Atl105Paths.docs().resolve(Path.of("specs", "kb", "appendix-af-response-wire-source-quotes.txt"));
        Path afCatalog = Atl105Paths.docs().resolve(Path.of("specs", "kb", "appendix-af-rule-catalog.json"));
        String baseText = Files.readString(baseSource, StandardCharsets.UTF_8);
        String table82Text = Files.readString(table82Source, StandardCharsets.UTF_8);
        String responseText = Files.readString(responseSource, StandardCharsets.UTF_8);
        ObjectNode catalog = (ObjectNode) mapper.readTree(afCatalog.toFile());
        if (!baseText.contains("All Financial Transaction Requests contain Element Nos. 55 and 63.")
                || !baseText.contains("Standard Message Data Segment Format")
                || !baseText.contains("Variable Information Indicator")
                || !table82Text.contains("N2 Fixed Value: 82")
                || !table82Text.contains("N3 Fixed Length : 004")
                                || !table82Text.contains("When Element No. 111 (Variable Information Indicator) has a value of 082 (Incremental Auth Data), the Incremental Auth Data is included here.")
                || !table82Text.contains("Terminal Transaction Number")
                || !responseText.contains("it has no Field Separators.")
                || !responseText.contains("Element 101 Terminal Display/Printer Message: position 71, length 31, required; repeats up to three times.")) {
            throw new IllegalStateException("Required source quotes for the wire candidate are not present");
        }
                if (!AF_PDF_SHA256.equalsIgnoreCase(catalog.path("sourceDocumentSha256").asText())) {
                        throw new IllegalStateException("Appendix AF source hash does not match the reviewed 2026-4 release");
                }

        ObjectNode standard = standardFields();
        standard.put("84", "000");
        String standardWire = serialize(STANDARD_ORDER, standard);
        int standardLength = asciiLength(standardWire);
        standard.put("84", String.format(java.util.Locale.ROOT, "%03d", standardLength));
        standardWire = serialize(STANDARD_ORDER, standard);
        if (asciiLength(standardWire) != standardLength) throw new IllegalStateException("Segment 100 length changed after rendering");

        String indicator = "082";
        String variableLength = "004";
        String terminalTransactionNumber = "0001";
        String variableBody = indicator + variableLength + terminalTransactionNumber;
        int segment111Length = asciiLength("111" + FIELD_SEPARATOR + "000" + FIELD_SEPARATOR + variableBody + FIELD_SEPARATOR);
        String segment111Wire = "111" + FIELD_SEPARATOR + String.format(java.util.Locale.ROOT, "%03d", segment111Length)
                + FIELD_SEPARATOR + variableBody + FIELD_SEPARATOR;

        String section1Wire = "ATL105" + FIELD_SEPARATOR + "02" + FIELD_SEPARATOR;
        String applicationPayload = section1Wire + standardWire + segment111Wire;
        byte[] applicationBytes = applicationPayload.getBytes(StandardCharsets.US_ASCII);
        if (applicationBytes.length != asciiLength(section1Wire) + standardLength + segment111Length) {
            throw new IllegalStateException("Request payload length does not reconcile to its source segments");
        }

        ObjectNode candidate = mapper.createObjectNode().put("artifact", "ATL105-APPENDIX-AF-PHYSICAL-WIRE-CANDIDATE")
                .put("status", "REVIEW_REQUIRED").put("specificationVersion", "2026-4")
                .put("baseEnvelopeVersion", "2026-3")
                .put("scope", "Synthetic Financial Transaction Request ATL105 application payload; not transport framing or host evidence")
                .put("executionReady", false).put("hostExecuted", false);
        ObjectNode evidence = candidate.putObject("sourceEvidence");
        evidence.put("baseSpecificationText", "docs/specs/extracted_text.txt").put("baseSpecificationSha256", sha256(baseSource));
        evidence.put("table82QuoteFile", "docs/specs/kb/appendix-af-table82-source-quotes.txt").put("table82QuoteFileSha256", sha256(table82Source));
        evidence.put("responseLayoutEvidenceFile", "docs/specs/kb/appendix-af-response-wire-source-quotes.txt")
                .put("responseLayoutEvidenceSha256", sha256(responseSource));
        evidence.put("afSourcePdfSha256Pinned", catalog.path("sourceDocumentSha256").asText());
        evidence.put("afSourcePdfPresentInWorkspace", false);

        ObjectNode request = candidate.putObject("request");
        request.put("messageFamily", "Financial Transaction Request");
        request.put("declaredSegmentCount", "02");
        request.put("applicationPayloadEscaped", escape(applicationPayload));
        request.put("applicationPayloadHex", HexFormat.of().withUpperCase().formatHex(applicationBytes));
        request.put("applicationPayloadByteCount", applicationBytes.length);
        request.put("tcpIpTpduFraming", "NOT_INCLUDED");
        ObjectNode dataSection1 = request.putObject("dataSection1");
        dataSection1.put("messageFormatVersionIdentifier", "ATL105").put("numberOfSegments", "02");
        ObjectNode standardOutput = request.putObject("segment100");
        standardOutput.put("segmentLength", standard.path("84").asText())
                .put("byteCount", standardLength).put("serializedWireEscaped", escape(standardWire));
        standardOutput.set("numberedElements", standard);
        ObjectNode variableOutput = request.putObject("segment111");
        variableOutput.put("segmentLength", String.format(java.util.Locale.ROOT, "%03d", segment111Length))
                .put("byteCount", segment111Length).put("serializedWireEscaped", escape(segment111Wire));
        variableOutput.putObject("variableInformationSection")
                .put("indicatorN3Candidate", indicator)
                .put("lengthN3", variableLength)
                .put("terminalTransactionNumberN4", terminalTransactionNumber)
                .put("afTransactionIdAlias", terminalTransactionNumber)
                .put("afTransactionIdSerializedAsSeparateField", false);
        variableOutput.put("table82IndicatorEncodingStatus", "SOURCE_CONFIRMED")
                .put("table82IndicatorEncodingReason", "ATL105 2026-4 Chapter 13.2 Element 113 explicitly says Element 111 value 082 is Incremental Auth Data; Appendix I Table 82 supplies Table ID 82, N3 length 004, and N4 Terminal Transaction Number.")
                .put("afTransactionIdMappingStatus", "SOURCE_DERIVED_REVIEW_REQUIRED")
                .put("afTransactionIdMappingReason", "Appendix AF says Table 82 Incremental Auth Data contains Transaction ID; Appendix I defines the table's N4 payload as Terminal Transaction Number. The candidate binds the logical alias to that one N4 value, serialized once; authorized SME/business confirmation remains pending.")
                .put("afTransactionIdSerializedAsTable82N4", true);

        ObjectNode testDataPayload = candidate.putObject("testDataPayload");
        testDataPayload.putObject("testControls").put("validateDataSection1", true).put("validateSerialization", true);
        ObjectNode logicalRequest = testDataPayload.putObject("request");
        logicalRequest.putObject("dataSection1").put("messageFormatVersionIdentifier", "ATL105")
                .put("numberOfSegments", "02").putObject("fieldSeparators")
                .put("betweenElements55And63", true).put("afterElement63", true);
        ObjectNode standardSegment = logicalRequest.putObject("dataSection2").putObject("standardSegment");
        standardSegment.put("segmentType", standard.path("85").asText())
                .put("segmentLength", standard.path("84").asText())
                .put("informationByte", standard.path("44").asText())
                .put("terminalIdentifier", standard.path("102").asText())
                .put("promptCode", standard.path("78").asText())
                .put("accountNumber", standard.path("2").asText())
                .put("cardDiscretionaryBlockData", standard.path("12").asText())
                .put("encryptedPinBlockData", standard.path("33").asText())
                .put("pumpLaneNumber", standard.path("79").asText())
                .put("fuelPurchaseAmount", standard.path("41").asText())
                .put("nonfuelAmount", standard.path("58").asText())
                .put("taxAmount", standard.path("99").asText())
                .put("cashAmount", standard.path("17").asText())
                .put("sequenceNumber", standard.path("86").asText())
                .put("approvalNumber", standard.path("5").asText())
                .put("localDateTime", standard.path("49").asText())
                .put("partialApprovalIndicator", standard.path("121").asText())
                .put("serializedSegment", standardWire);
        ArrayNode dataSection3 = logicalRequest.putArray("dataSection3");
        ObjectNode segment111 = dataSection3.addObject().put("segmentType", "111")
                .put("segmentLength", String.format(java.util.Locale.ROOT, "%03d", segment111Length))
                .put("wireFormat", segment111Wire);
        segment111.putArray("variableInformationSections").addObject()
                .put("variableInformationIndicator", indicator)
                .put("variableInformationLength", variableLength)
                .put("variableInformation", terminalTransactionNumber);

        ObjectNode layout = candidate.putObject("chapter11LayoutObservation")
                .put("specificationVersion", "2026-3").put("messageFamily", "Financial Transaction Request")
                .put("completeness", "PARTIAL");
        layout.putObject("elements").put("55", "ATL105").put("63", "02");
        ArrayNode layoutSegments = layout.putArray("segments");
        ObjectNode standardLayout = layoutSegments.addObject().put("segment", "100").put("dataSection", 2).put("fieldNumber", 3);
        standardLayout.putObject("observation").put("specificationVersion", "2026-3")
                .put("messageFamily", "Financial Transaction Request").put("segment", "100")
                .set("elements", standard.deepCopy());
        ((ObjectNode) standardLayout.path("observation")).put("dataSection", 2).put("fieldNumber", 3)
                .put("serializedSegment", standardWire);
        layoutSegments.addObject().put("segment", "111").put("dataSection", 3).put("fieldNumber", 8);
        ObjectNode response = candidate.putObject("response").put("status", "SYNTHETIC_LAYOUT_ONLY")
                .put("sourceLayout", "ATL105 2026-4 Section 11.1.2, PDF pages 171-172")
                .put("sourceEvidenceFile", "docs/specs/kb/appendix-af-response-wire-source-quotes.txt")
                .put("sourceEvidenceSha256", sha256(responseSource))
                .put("hostCapture", false).put("expectedBusinessOutcome", "NOT_ASSESSED")
                .put("wireSeparator", "NONE");
        ObjectNode responseElements = response.putObject("elements");
        responseElements.put("83", "2").put("30", "0").put("45", "101626").put("46", "1200")
                .put("86", "100001").put("6", "000001000").put("5", "SYN001")
                .put("2", "SYNTHETICACCOUNT000000000").put("26", "  ").put("7", "01")
                .put("8", "00").put("119", "1016").put("115", "0").put("61", "1")
                .put("101", padRight("SYNTHETIC MESSAGE - NO HOST", 31));
        List<String> responseOrder = List.of("83", "30", "45", "46", "86", "6", "5", "2", "26", "7", "8", "119", "115", "61", "101");
        StringBuilder responseWire = new StringBuilder();
        ArrayNode responseFieldLayout = response.putArray("fieldLayout");
        int responsePosition = 1;
        int[] responseWidths = {1, 1, 6, 4, 6, 9, 6, 25, 2, 2, 2, 4, 1, 1, 31};
        for (int i = 0; i < responseOrder.size(); i++) {
            String element = responseOrder.get(i);
            String value = responseElements.path(element).asText();
            if (value.length() != responseWidths[i]) throw new IllegalStateException("Synthetic response Element " + element + " width mismatch");
            responseWire.append(value);
            responseFieldLayout.addObject().put("element", element).put("position", responsePosition)
                    .put("length", responseWidths[i]);
            responsePosition += responseWidths[i];
        }
        byte[] responseBytes = responseWire.toString().getBytes(StandardCharsets.US_ASCII);
        if (responseBytes.length != 101) throw new IllegalStateException("Response source-layout length must be 101 bytes");
        response.put("applicationPayloadEscaped", responseWire.toString())
                .put("applicationPayloadHex", HexFormat.of().withUpperCase().formatHex(responseBytes))
                .put("applicationPayloadByteCount", responseBytes.length)
                .put("transportFraming", "NOT_INCLUDED");
        ObjectNode responseLayout = response.putObject("chapter11LayoutObservation")
                .put("specificationVersion", "2026-3").put("messageFamily", "Financial Transaction Response")
                .put("completeness", "PARTIAL");
        responseLayout.set("elements", responseElements.deepCopy());
        responseLayout.putArray("segments");
        candidate.putArray("limitations")
                .add("The full 2026-4 PDF is external to this repository; the candidate binds to its pinned hash and reviewed extracted source quotes.")
                .add("The source-derived AF Transaction ID to Table 82 N4 alias still awaits authorized SME/business confirmation.")
                .add("The response is synthetic layout data, not a captured host response or expected-outcome oracle; TCP/IP/TPDU framing is not included.")
                .add("Synthetic field values are for local structural wire checks only; no card/network compatibility or approval result is asserted.");
        return candidate;
    }

    private ObjectNode standardFields() {
        ObjectNode fields = mapper.createObjectNode();
        fields.put("85", "100").put("84", "000").put("44", "0").put("102", "TS06123456001")
                .put("78", "3020").put("2", "SYNTHETIC01").put("12", "1228").put("33", "")
                .put("79", "").put("41", "").put("58", "00001000").put("99", "").put("17", "")
                .put("86", "100001").put("5", "").put("49", "1016261200").put("121", "1");
        return fields;
    }

    private static String serialize(List<String> order, ObjectNode fields) {
        StringBuilder wire = new StringBuilder();
        for (String id : order) wire.append(fields.path(id).asText()).append(FIELD_SEPARATOR);
        return wire.toString();
    }

    private static int asciiLength(String value) { return value.getBytes(StandardCharsets.US_ASCII).length; }
        private static String padRight(String value, int width) {
                if (value.length() > width) throw new IllegalArgumentException("Value exceeds source field width " + width);
                return value + " ".repeat(width - value.length());
        }
    private static String escape(String value) { return value.replace(String.valueOf(FIELD_SEPARATOR), "\\u001c"); }
    private static String sha256(Path path) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
    }
}