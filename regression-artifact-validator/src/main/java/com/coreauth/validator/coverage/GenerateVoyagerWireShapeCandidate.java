package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;

/** Builds a source-derived Voyager wire-shape fixture, not producer converter output. */
public final class GenerateVoyagerWireShapeCandidate {
    public static final String FILE_NAME = "atl105-voyager-wire-shape-candidate-v2.json";
    private static final char FIELD_SEPARATOR = '\u001c';
    private static final char PRODUCT_DATA_DELIMITER = '\\';
    private static final List<String> STANDARD_ORDER = List.of("85", "84", "44", "102", "78", "2", "12", "33",
            "79", "41", "58", "99", "17", "86", "5", "49", "121");
    private static final String PETROLEUM_PDF_SHA256 =
            "deb5def8085dbaddd8ebc87792e4712ddbc89bb7a591ff8f4c81112319f50404";
    private final ObjectMapper mapper = new ObjectMapper();

    private GenerateVoyagerWireShapeCandidate() { }

    public static void main(String[] args) throws Exception {
        ObjectNode candidate = buildCandidate();
        Path output = outputPath();
        Files.createDirectories(output.getParent());
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(output.toFile(), candidate);
        System.out.printf("Wrote %s bytes=%d status=%s%n", output,
                candidate.path("segment100").path("byteCount").asInt(), candidate.path("status").asText());
    }

    public static Path outputPath() {
        return Atl105Paths.docs().resolve(Path.of("specs", "kb", "petroleum-2025-1", "voyager-fleet", FILE_NAME));
    }

    public static ObjectNode buildCandidate() throws Exception {
        return new GenerateVoyagerWireShapeCandidate().create();
    }

    private ObjectNode create() throws Exception {
        Path atl105_3 = Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt"));
        Path atl105_4 = Atl105Paths.docs().resolve(Path.of("specs", "extracted_text_new.txt"));
        Path petroleumPdf = Atl105Paths.docs().resolve(Path.of(
                "specs", "BUYPASS_Platform_Petroleum_Industry_Processing_Specifications__2025-1.pdf"));
        String text3 = Files.readString(atl105_3, StandardCharsets.UTF_8);
        String text4 = Files.readString(atl105_4, StandardCharsets.UTF_8);
        requireSourceEvidence(text3, "2026-3");
        requireSourceEvidence(text4, "2026-4");
        requireFleetAndProductEvidence(text3, "2026-3");
        requireFleetAndProductEvidence(text4, "2026-4");
        String petroleumHash = sha256(petroleumPdf);
        if (!PETROLEUM_PDF_SHA256.equals(petroleumHash)) {
            throw new IllegalStateException("Petroleum source PDF hash does not match the reviewed Voyager training package");
        }
        Path petroleumContext = Atl105Paths.docs().resolve(Path.of(
                "specs", "kb", "petroleum-2025-1", "voyager-fleet", "context-input.json"));
        JsonNode petroleumInput = mapper.readTree(petroleumContext.toFile());
        if (!petroleumInput.toString().contains("The Product Code Data must be sent in all fleet transactions.")) {
            throw new IllegalStateException("Voyager context source does not require Product Code Data");
        }

        ObjectNode fields = mapper.createObjectNode();
        fields.put("85", "100").put("84", "000").put("44", "0").put("102", "TS06123456001")
                .put("78", "0060").put("2", "8512345678901234567").put("12", "1228").put("33", "")
                .put("79", "").put("41", "00001000").put("58", "").put("99", "")
                .put("17", "").put("86", "100001").put("5", "").put("49", "1016261200").put("121", "1");
        String segmentWire = serialize(STANDARD_ORDER, fields);
        int segmentByteCount = asciiLength(segmentWire);
        fields.put("84", String.format(Locale.ROOT, "%03d", segmentByteCount));
        segmentWire = serialize(STANDARD_ORDER, fields);
        if (asciiLength(segmentWire) != segmentByteCount
                || Integer.parseInt(fields.path("84").asText()) != segmentByteCount) {
            throw new IllegalStateException("Segment 100 declared length does not match the rendered ASCII segment");
        }

        ObjectNode fleetFields = mapper.createObjectNode().put("85", "101").put("84", "008");
        String fleetWire = serialize(List.of("85", "84"), fleetFields);
        int fleetByteCount = asciiLength(fleetWire);
        fleetFields.put("84", String.format(Locale.ROOT, "%03d", fleetByteCount));
        fleetWire = serialize(List.of("85", "84"), fleetFields);
        if (asciiLength(fleetWire) != fleetByteCount
                || Integer.parseInt(fleetFields.path("84").asText()) != fleetByteCount) {
            throw new IllegalStateException("Segment 101 declared length does not match its rendered ASCII segment");
        }

        String productBody = "S01" + "001" + "G" + "3001000" + PRODUCT_DATA_DELIMITER
                + "3001000" + PRODUCT_DATA_DELIMITER + "00001000" + FIELD_SEPARATOR;
        int productByteCount = asciiLength("102" + FIELD_SEPARATOR + "000" + FIELD_SEPARATOR + productBody);
        String productWire = "102" + FIELD_SEPARATOR + String.format(Locale.ROOT, "%03d", productByteCount)
                + FIELD_SEPARATOR + productBody;
        if (asciiLength(productWire) != productByteCount) {
            throw new IllegalStateException("Segment 102 declared length does not match its rendered ASCII segment");
        }

        String section1Wire = "ATL105" + FIELD_SEPARATOR + "03" + FIELD_SEPARATOR;
        String payloadWire = section1Wire + segmentWire + fleetWire + productWire;
        byte[] segmentBytes = segmentWire.getBytes(StandardCharsets.US_ASCII);
        byte[] fleetBytes = fleetWire.getBytes(StandardCharsets.US_ASCII);
        byte[] productBytes = productWire.getBytes(StandardCharsets.US_ASCII);
        byte[] payloadBytes = payloadWire.getBytes(StandardCharsets.US_ASCII);
        if (payloadBytes.length != asciiLength(section1Wire) + segmentByteCount + fleetByteCount + productByteCount) {
            throw new IllegalStateException("Application payload length does not reconcile to its segments");
        }

        ObjectNode candidate = mapper.createObjectNode()
                .put("artifact", "ATL105-VOYAGER-WIRE-SHAPE-CANDIDATE")
                .put("status", "SYNTHETIC_REVIEW_REQUIRED")
                .put("executionReady", false)
                .put("producerConverterUsed", false)
                .put("hostExecuted", false)
                .put("businessValidity", "NOT_ASSESSED")
                .put("scope", "Synthetic ATL105 Voyager three-segment application-payload shape; not producer output or an execution-ready transaction");
        ObjectNode evidence = candidate.putObject("sourceEvidence");
        evidence.put("atl105_2026_3_path", relative(atl105_3)).put("atl105_2026_3_sha256", sha256(atl105_3))
                .put("atl105_2026_4_path", relative(atl105_4)).put("atl105_2026_4_sha256", sha256(atl105_4))
                .put("petroleum_2025_1_pdf_path", relative(petroleumPdf)).put("petroleum_2025_1_pdf_sha256", petroleumHash)
                .put("atl105StandardLayout", "Section 12.1, printed pages 12-7 to 12-9")
                .put("atl105FleetSegmentRequirement", "Section 12.2, printed page 12-9")
                .put("atl105ProductSegmentLayout", "Section 12.3, printed pages 12-12 to 12-14; Appendix B-4 to B-5 example")
                .put("petroleumVoyagerPromptCode", "Chapter 9, printed page 9-1; synthetic fleet Prompt Code 0060")
                .put("petroleumProductRequirement", "Chapter 9, printed page 9-4; all fleet transactions require Product Code Data");

        ObjectNode section1 = candidate.putObject("section1")
                .put("messageFormatVersionIdentifier", "ATL105")
                .put("numberOfSegments", "03");
        section1.put("serializedWireEscaped", escape(section1Wire));
        ObjectNode segment = candidate.putObject("segment100");
        segment.put("segmentLength", fields.path("84").asText())
                .put("byteCount", segmentByteCount)
                .put("serializedWireEscaped", escape(segmentWire))
                .put("serializedWireHex", HexFormat.of().withUpperCase().formatHex(segmentBytes))
                .set("numberedElements", fields);
        ObjectNode fleet = candidate.putObject("segment101");
        fleet.put("segmentLength", fleetFields.path("84").asText())
                .put("byteCount", fleetByteCount)
                .put("serializedWireEscaped", escape(fleetWire))
                .put("serializedWireHex", HexFormat.of().withUpperCase().formatHex(fleetBytes))
                .set("numberedElements", fleetFields);
        ObjectNode product = candidate.putObject("segment102");
        product.put("segmentLength", String.format(Locale.ROOT, "%03d", productByteCount))
                .put("byteCount", productByteCount)
                .put("serializedWireEscaped", escape(productWire))
                .put("serializedWireHex", HexFormat.of().withUpperCase().formatHex(productBytes))
                .put("serviceLevel", "S").put("numberOfProducts", "01");
        product.putArray("products").addObject()
                .put("productCode", "001").put("unitOfMeasure", "G")
                .put("quantity", "3001000").put("unitPrice", "3001000").put("productAmount", "00001000");
        candidate.put("applicationPayloadEscaped", escape(payloadWire))
                .put("applicationPayloadHex", HexFormat.of().withUpperCase().formatHex(payloadBytes))
                .put("applicationPayloadByteCount", payloadBytes.length)
                .put("transportFraming", "NOT_INCLUDED");

        ObjectNode observation = candidate.putObject("validatorObservation")
                .put("specificationVersion", "2026-3")
                .put("messageFamily", "Financial Transaction Request")
                .put("segment", "100")
                .put("dataSection", 2)
                .put("fieldNumber", 3)
                .put("serializedSegment", segmentWire);
        observation.set("elements", fields.deepCopy());
        ArrayNode dataSection3 = candidate.putArray("dataSection3");
        dataSection3.addObject().put("dataSection", 3).put("fieldNumber", 4)
                .put("segment", "101").put("serializedWireEscaped", escape(fleetWire))
                .put("segmentLength", fleetFields.path("84").asText());
        dataSection3.addObject()
                .put("dataSection", 3).put("fieldNumber", 5)
                .put("segment", "102").put("serializedWireEscaped", escape(productWire))
                .put("segmentLength", String.format(Locale.ROOT, "%03d", productByteCount));
        candidate.set("segment101ValidatorPayload", segment101ValidatorPayload(fields, fleetFields));
        candidate.set("segment102ValidatorPayload", segment102ValidatorPayload(fields, productByteCount));

        candidate.putArray("limitations")
                .add("This is an independently authored source-derived fixture, not output from the unavailable ATL105 producer converter.")
                .add("Segments 101 and 102 use a minimal synthetic layout for structural checks; conditional fleet values and production business validity are not certified.")
                .add("The account number, product, sequence, terminal and transaction values are synthetic; issuer, check-digit and host outcome are not certified.")
                .add("The application payload excludes TCP/IP and TPDU framing.");
        return candidate;
    }

    private ObjectNode segment101ValidatorPayload(ObjectNode standardFields, ObjectNode fleetFields) {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100").put("SegmentLength", standardFields.path("84").asText());
        ObjectNode prompt = standard.putObject("PromptCode");
        prompt.put("TransactionType", "0").put("CardType", "060");
        ObjectNode fleet = request.putObject("Fleet Data Segment");
        fleet.put("SegmentType", "101").put("SegmentLength", fleetFields.path("84").asText());
        return payload;
    }

    private ObjectNode segment102ValidatorPayload(ObjectNode standardFields, int productByteCount) {
        ObjectNode payload = mapper.createObjectNode();
        ObjectNode standard = payload.putObject("Standard Segment");
        standard.put("FuelPurchaseAmount", standardFields.path("41").asText())
                .put("NonfuelAmount", standardFields.path("58").asText())
                .put("TaxAmount", standardFields.path("99").asText())
                .put("CashAmount", standardFields.path("17").asText());
        standard.putObject("PromptCode").put("CardType", "060");
        payload.putObject("context").put("comdata", false);
        ObjectNode productSegment = payload.putObject("Product Code Data Segment");
        productSegment.put("SegmentType", "102")
                .put("SegmentLength", String.format(Locale.ROOT, "%03d", productByteCount))
                .put("ServiceLevel", "S").put("NumberOfProducts", "01");
        productSegment.putArray("Products").addObject()
                .put("ProductCode", "001").put("UnitOfMeasure", "G")
                .put("Quantity", "3001000").put("UnitPrice", "3001000").put("ProductAmount", "00001000");
        return payload;
    }

    private static void requireSourceEvidence(String text, String release) {
        for (String quote : List.of(
                "The following table illustrates the layout of the Standard Message Data Segment:",
                "Note: All fields are separated by a Field Separator.  When a field is not populated, still send",
                "Identifies the data segment\u2019s length,",
                "including Segment Type\u2019s length and",
                "Field Separators.")) {
            if (!text.contains(quote)) {
                throw new IllegalStateException("ATL105 " + release + " is missing required Segment 100 source evidence: " + quote);
            }
        }
    }

    private static void requireFleetAndProductEvidence(String text, String release) {
        for (String quote : List.of(
                "The Fleet Data Segment is included in all fleet card transaction requests.",
                "All fields are separated by Field Separators.  When a field is not populated, still send the",
                "A Product Data Field Delimiter always follows Quantity and Unit Price.",
                "Field Separator follows Product Amount",
                "very last element in the")) {
            if (!text.contains(quote)) {
                throw new IllegalStateException("ATL105 " + release + " is missing required fleet/product evidence: " + quote);
            }
        }
    }

    private static String serialize(List<String> order, ObjectNode fields) {
        StringBuilder wire = new StringBuilder();
        for (String id : order) wire.append(fields.path(id).asText()).append(FIELD_SEPARATOR);
        return wire.toString();
    }

    private static int asciiLength(String value) {
        return value.getBytes(StandardCharsets.US_ASCII).length;
    }

    private static String escape(String value) {
        return value.replace(String.valueOf(FIELD_SEPARATOR), "\\u001c");
    }

    private static String relative(Path path) {
        return Atl105Paths.root().getParent().relativize(path).toString().replace('\\', '/');
    }

    private static String sha256(Path path) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
    }
}
