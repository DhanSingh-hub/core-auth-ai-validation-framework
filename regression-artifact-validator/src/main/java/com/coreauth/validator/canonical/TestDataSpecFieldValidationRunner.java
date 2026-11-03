package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Field-by-field ATL105 validation of AI-generated EMV Financial Transaction Request test data
 * (QE-shaped TD JSON) against the ATL105 2026-3 Chapter 11/12/13 layouts and the trained appendix
 * oracles, plus fidelity of each TD value to its source test case. Writes one Markdown report per
 * test case and an index.
 *
 * <p>Usage: {@code <testDataDir> <testCasesJsonl> <outputDir> <TC-ID> [<TC-ID> ...]}
 */
public final class TestDataSpecFieldValidationRunner {

    enum Verdict { PASS, FAIL, REVIEW, NA }

    record Check(String area, String field, String element, String value, String rule, Verdict verdict,
                 String detail, String source) {
    }

    record FieldDef(String tdKey, String tcName, String element, String presence, String source) {
    }

    private static final String FS_NOTE = "Field Separator";
    private static final String MSG_ROOT = "EMV Financial Transaction Request";
    private static final String SEG100 = "Standard Message Data Segment";
    private static final String SEG111 = "Variable Information Data Segment";
    private static final String SEG130 = "EMV Request Data Segment";

    private static final List<FieldDef> SEG100_FIELDS = List.of(
            new FieldDef("SegmentType", "Segment Type", "85", "R", "12.1 F1"),
            new FieldDef("SegmentLength", "Segment Length", "84", "R", "12.1 F2"),
            new FieldDef("InformationByte", "Information Byte", "44", "R", "12.1 F3"),
            new FieldDef("TerminalIdentifier", "Terminal Identifier", "102", "R", "12.1 F4"),
            new FieldDef("PromptCode", "Prompt Code", "78", "R", "12.1 F5"),
            new FieldDef("AccountNumber", "Account Number", "2", "R", "12.1 F6"),
            new FieldDef("CardDiscretionaryBlockData", "Card Discretionary Block Data", "12", "C", "12.1 F7"),
            new FieldDef("EncryptedPINBlockData", "Encrypted PIN Block Data", "33", "C", "12.1 F8"),
            new FieldDef("PumpLaneNumber", "Pump/Lane Number", "79", "C", "12.1 F9"),
            new FieldDef("FuelPurchaseAmount", "Fuel Purchase Amount", "41", "C", "12.1 F10"),
            new FieldDef("NonfuelAmount", "Nonfuel Amount", "58", "C", "12.1 F11"),
            new FieldDef("TaxAmount", "Tax Amount", "99", "C", "12.1 F12"),
            new FieldDef("CashAmount", "Cash Amount", "17", "C", "12.1 F13"),
            new FieldDef("SequenceNumber", "Sequence Number", "86", "R", "12.1 F14"),
            new FieldDef("ApprovalNumber", "Approval Number", "5", "C", "12.1 F15"),
            new FieldDef("LocalDateandLocalTime", "Local Date and Local Time", "49", "C", "12.1 F16"),
            new FieldDef("PartialApprovalIndicator", "Partial Approval Indicator", "121", "R", "12.1 F17"));

    private static final List<FieldDef> SEG130_FIELDS = List.of(
            new FieldDef("SegmentType", "Segment Type", "85", "R", "12.20 F1"),
            new FieldDef("SegmentLength", "Segment Length", "84", "R", "12.20 F2"),
            new FieldDef("CAPublicKeyFileChecksum", "CA Public Key File Checksum", "187", "O", "12.20 F3"),
            new FieldDef("EMVCardSequenceNumber", "EMV Card Sequence Number", "188", "C", "12.20 F4"),
            new FieldDef("EMVChipDataLength", "EMV Chip Data Length", "189", "R", "12.20 F5"),
            new FieldDef("EMVChipData", "EMV Chip Data", "190", "R", "12.20 F6"),
            new FieldDef("EMVAdditionalInformationIndicator", "EMV Additional Information Indicator", "191", "R", "12.20 F7"),
            new FieldDef("EMVAdditionalInformationLength", "EMV Additional Information Length", "192", "R", "12.20 F8"),
            new FieldDef("EMVAdditionalInformation", "EMV Additional Information", "118", "R", "12.20 F9"));

    private static final Set<String> TABLE_031_CODES = Set.of("00", "01", "02", "03", "04", "05", "06", "07", "09", "12");

    private static final Map<String, String> TC_SEGMENT_NAMES = Map.of(
            "100", SEG100, "111", SEG111, "130", SEG130);

    private final ObjectMapper mapper = new ObjectMapper();
    private final AppendixREmvChipDataOracle appendixR = new AppendixREmvChipDataOracle();
    private final AppendixTEmvAdditionalInformationOracle appendixT = new AppendixTEmvAdditionalInformationOracle();
    private final AppendixILogicalDataValidator appendixI = new AppendixILogicalDataValidator();
    private final AppendixITableCatalog appendixICatalog = new AppendixITableCatalog();
    private final AppendixJPosEntryModeOracle appendixJ = new AppendixJPosEntryModeOracle();
    private final AppendixGTransactionTypeCatalog appendixG = new AppendixGTransactionTypeCatalog();
    private final String runYymm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));

    public static void main(String[] args) throws IOException {
        if (args.length < 4) {
            System.err.println("Usage: <testDataDir> <testCasesJsonl> <outputDir> <TC-ID> [<TC-ID> ...]");
            System.exit(2);
        }
        Path testDataDir = Path.of(args[0]);
        Path testCases = Path.of(args[1]);
        Path outDir = Path.of(args[2]);
        List<String> ids = new ArrayList<>();
        for (String a : List.of(args).subList(3, args.length)) {
            if (a.startsWith("@")) {
                for (String tok : Files.readString(Path.of(a.substring(1))).split("\\s+")) {
                    if (!tok.isBlank()) {
                        ids.add(tok.trim());
                    }
                }
            } else {
                ids.add(a);
            }
        }
        new TestDataSpecFieldValidationRunner().run(testDataDir, testCases, outDir, ids);
    }

    void run(Path testDataDir, Path testCasesJsonl, Path outDir, List<String> ids) throws IOException {
        Files.createDirectories(outDir);
        Map<String, JsonNode> cases = loadCases(testCasesJsonl, ids);
        List<String[]> index = new ArrayList<>();
        Map<String, Set<String>> defectTcs = new LinkedHashMap<>();
        Map<String, Integer> defectHits = new LinkedHashMap<>();
        for (String id : ids) {
            JsonNode tc = cases.get(id);
            List<Path> legs = resolveLegs(testDataDir, id, tc);
            CaseReport report = validateCase(id, tc, legs);
            Path file = outDir.resolve(id + "_ATL105_Field_Validation.md");
            Files.writeString(file, report.markdown(), StandardCharsets.UTF_8);
            index.add(new String[] {id, text(tc, "display_id"), text(tc, "template_id"), text(tc, "test_objective"),
                    String.valueOf(report.count(Verdict.PASS)), String.valueOf(report.count(Verdict.FAIL)),
                    String.valueOf(report.count(Verdict.REVIEW)), String.valueOf(report.count(Verdict.NA)),
                    report.overall(), file.getFileName().toString(), String.join("; ", report.topDefects(6)),
                    tc == null ? "" : tc.path("dimensions").path("entry_mode").asText(""),
                    tc == null ? "" : tc.path("dimensions").path("txn_category").asText(""),
                    String.valueOf(legs.size())});
            for (Check f : report.all()) {
                if (f.verdict() == Verdict.FAIL && !"TC<->TD".equals(f.area())) {
                    String key = defectKey(f);
                    defectTcs.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(id);
                    defectHits.merge(key, 1, Integer::sum);
                }
            }
            System.out.println(id + " -> " + report.overall() + " (" + file + ")");
        }
        Files.writeString(outDir.resolve("README.md"), indexMarkdown(index, testDataDir, testCasesJsonl, defectTcs, defectHits),
                StandardCharsets.UTF_8);
    }

    /** Groups a failing check into a defect category independent of leg/section numbering. */
    private static String defectKey(Check f) {
        String field = f.field().replaceAll("Section\\[\\d+\\]\\.", "").replaceAll("\\(Table \\d+\\)", "").trim();
        String area = f.area().replaceAll("\\(step .*\\)", "").replaceAll("\\(T[0-9A-Z-]+\\)", "").trim();
        return area + " | " + field + " | " + (f.element().equals("-") ? "" : "E" + f.element()) + " | " + f.rule();
    }

    // ------------------------------------------------------------------ loading

    private Map<String, JsonNode> loadCases(Path jsonl, List<String> ids) throws IOException {
        Map<String, JsonNode> found = new LinkedHashMap<>();
        if (jsonl == null || !Files.exists(jsonl)) {
            return found;
        }
        try (BufferedReader reader = Files.newBufferedReader(jsonl, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null && found.size() < ids.size()) {
                String head = line.length() > 300 ? line.substring(0, 300) : line;
                for (String id : ids) {
                    if (!found.containsKey(id) && head.contains("\"" + id + "\"")) {
                        JsonNode node = parseLenient(line);
                        if (node != null && id.equals(node.path("id").asText())) {
                            found.put(id, node);
                        }
                    }
                }
            }
        }
        return found;
    }

    /** Parses a catalog line, repairing a doubled or missing leading brace (observed in hand-edited catalogs). */
    private JsonNode parseLenient(String line) {
        String trimmed = line.strip();
        List<String> candidates = List.of(trimmed, trimmed.replaceFirst("^\\{\\s*\\{", "{"), "{" + trimmed);
        for (String candidate : candidates) {
            try {
                JsonNode node = mapper.readTree(candidate);
                if (node != null && node.isObject()) {
                    if (!candidate.equals(trimmed)) {
                        repairedCatalogLines.add(node.path("id").asText());
                    }
                    return node;
                }
            } catch (IOException ignored) {
                // try the next repair candidate
            }
        }
        return null;
    }

    private final Set<String> repairedCatalogLines = new LinkedHashSet<>();

    private List<Path> resolveLegs(Path dir, String id, JsonNode tc) throws IOException {
        List<Path> legs = new ArrayList<>();
        if (tc != null && tc.has("test_data_leg_files")) {
            tc.get("test_data_leg_files").forEach(n -> legs.add(dir.resolve(Path.of(n.asText()).getFileName())));
        } else if (tc != null && tc.hasNonNull("test_data_file")) {
            legs.add(dir.resolve(Path.of(tc.get("test_data_file").asText()).getFileName()));
        }
        if (legs.isEmpty() || legs.stream().noneMatch(Files::exists)) {
            legs.clear();
            try (Stream<Path> files = Files.list(dir)) {
                files.filter(p -> p.getFileName().toString().startsWith("TD-" + id + ".")
                                && p.getFileName().toString().endsWith(".json")
                                && !p.getFileName().toString().endsWith(".meta.json"))
                        .sorted().forEach(legs::add);
            }
        }
        return legs;
    }

    // ------------------------------------------------------------------ case validation

    private CaseReport validateCase(String id, JsonNode tc, List<Path> legs) throws IOException {
        CaseReport report = new CaseReport(id, tc, legs);
        if (tc == null) {
            report.caseChecks.add(new Check("Traceability", "Test case", "-", id, "TC-LOOKUP", Verdict.FAIL,
                    "Test case not found in test_cases.jsonl; TD-to-TC fidelity cannot be checked.", "-"));
        }
        if (legs.isEmpty()) {
            boolean deviceOnly = tc != null && tc.path("tx_legs").isEmpty();
            report.caseChecks.add(new Check("Traceability", "Test data file", "-", "none", "TD-PRESENT",
                    deviceOnly ? Verdict.NA : Verdict.FAIL,
                    deviceOnly ? "Device-configuration scenario with no host message (tx_legs empty); no ATL105 message to validate. "
                            + "Objective: " + text(tc, "test_objective") + "; device checkpoints: " + tc.path("device_checkpoints")
                            : "TC declares " + (tc == null ? 0 : tc.path("tx_legs").size()) + " transaction leg(s) but no TD file exists.",
                    "TC tx_legs"));
            return report;
        }
        List<LegContext> contexts = new ArrayList<>();
        for (int i = 0; i < legs.size(); i++) {
            Path leg = legs.get(i);
            LegReport legReport = new LegReport(leg.getFileName().toString());
            report.legs.add(legReport);
            if (!Files.exists(leg)) {
                legReport.checks.add(new Check("File", "TD file", "-", leg.toString(), "TD-PRESENT", Verdict.FAIL,
                        "Test data file not found.", "-"));
                continue;
            }
            JsonNode td = mapper.readTree(leg.toFile());
            JsonNode tcRequest = tcRequestForLeg(tc, i);
            JsonNode tcStep = tc != null && tc.has("steps") ? tc.get("steps").get(i) : tc;
            JsonNode legDef = tc != null && tc.path("tx_legs").size() > i ? tc.path("tx_legs").get(i) : null;
            LegContext ctx = validateLeg(td, tc, tcStep, legDef, tcRequest, legReport);
            contexts.add(ctx);
        }
        if (tc != null && repairedCatalogLines.contains(id)) {
            report.caseChecks.add(new Check("Traceability", "test_cases.jsonl line", "-", id, "TC-CATALOG-JSON", Verdict.REVIEW,
                    "The catalog line for this TC is not valid JSON (doubled/missing leading '{'); it was parsed after a "
                            + "brace repair. Fix the catalog file.", "test_cases.jsonl"));
        }
        objectiveChecks(tc, contexts, report.caseChecks);
        tcRuleChecks(tc, contexts, report.caseChecks);
        linkDefChecks(tc, contexts, report.caseChecks);
        flowChecks(tc, contexts, report.caseChecks);
        return report;
    }

    private JsonNode tcRequestForLeg(JsonNode tc, int index) {
        if (tc == null) {
            return null;
        }
        if (tc.has("steps") && tc.get("steps").size() > index) {
            return tc.get("steps").get(index).get("request");
        }
        return tc.get("request");
    }

    /** Values extracted from one leg for cross-leg and objective checks. */
    static final class LegContext {
        final Map<String, String> seg100 = new LinkedHashMap<>();
        final Map<String, String> seg130 = new LinkedHashMap<>();
        final Map<String, String> table = new LinkedHashMap<>();
        Map<String, String> tlv = new LinkedHashMap<>();
        Set<String> segments = new LinkedHashSet<>();
        final Map<String, String> totals = new LinkedHashMap<>();
        final List<Map<String, String>> products = new ArrayList<>();
        String role = "";
        String txnCode = "";
        String entryCode = "";
        int of;
        boolean approvalLinked;
        boolean followOn;
        boolean emvMessage;
        boolean totalsMessage;
    }

    private static final String FTR_ROOT = "Financial Transaction Request";
    private static final String TOTALS_ROOT = "Totals Request";
    private static final String SEG102 = "Product Code Data Segment";
    private static final String SEG105 = "Totals Data Segment";
    /** Follow-on transaction types that reverse or void a prior transaction (Appendix G). */
    private static final Set<String> REVERSAL_FAMILY = Set.of("8", "Z", "S", "U");

    private LegContext validateLeg(JsonNode td, JsonNode tc, JsonNode tcStep, JsonNode legDef, JsonNode tcRequest, LegReport out) {
        LegContext ctx = new LegContext();
        List<Check> c = out.checks;
        String legMessage = legDef == null ? text(tcStep, "transaction_type") : text(legDef, "message");
        ctx.role = legDef == null ? "" : text(legDef, "role");
        ctx.txnCode = legDef == null ? "" : text(legDef, "code");
        ctx.followOn = legDef != null && legDef.path("follow_on").asBoolean(false);
        ctx.of = legDef == null ? 0 : legDef.path("of").asInt(0);
        if (legDef != null) {
            for (JsonNode ld : legDef.path("link_defs")) {
                if ("Approval Number".equals(ld.path("element_name").asText())) {
                    ctx.approvalLinked = true;
                }
            }
        }
        String entryMode = tc == null ? "" : tc.path("dimensions").path("entry_mode").asText("");
        ctx.entryCode = legDef != null && legDef.hasNonNull("entry_code") ? legDef.get("entry_code").asText()
                : "EMV_CONTACTLESS".equals(entryMode) ? "07" : "EMV_CONTACT".equals(entryMode) ? "05" : "";
        String expectedRoot = legMessage.startsWith("EMV") ? MSG_ROOT : TOTALS_ROOT.equals(legMessage) ? TOTALS_ROOT : FTR_ROOT;
        String rootKey = td.has(MSG_ROOT) ? MSG_ROOT : td.has(FTR_ROOT) ? FTR_ROOT : td.has(TOTALS_ROOT) ? TOTALS_ROOT : firstKey(td);
        JsonNode root = td.get(rootKey);
        ctx.emvMessage = MSG_ROOT.equals(rootKey);
        ctx.totalsMessage = TOTALS_ROOT.equals(rootKey);
        if (root == null || !(MSG_ROOT.equals(rootKey) || FTR_ROOT.equals(rootKey) || TOTALS_ROOT.equals(rootKey))) {
            c.add(new Check("Message", "Root", "-", rootKey, "11.8.1", Verdict.FAIL,
                    "Root object must be '" + MSG_ROOT + "', '" + FTR_ROOT + "' or '" + TOTALS_ROOT + "'.", "ATL105 11.1 / 11.4 / 11.8.1"));
            return ctx;
        }
        c.add(chk("Message", "Message type", "-", rootKey, "11.1/11.4/11.8.1", rootKey.equals(expectedRoot),
                "TC leg message '" + legMessage + "' (role " + ctx.role + ", Transaction Type '" + ctx.txnCode
                        + "') => physical message '" + expectedRoot + "'.", "ATL105 11.1 / 11.4 / 11.8.1"));
        if (ctx.totalsMessage) {
            validateTotalsMessage(root, tc, ctx, c);
            if (tcRequest != null) {
                fidelity(root, tcRequest, out.fidelity);
            }
            return ctx;
        }

        Iterator<String> names = root.fieldNames();
        while (names.hasNext()) {
            String key = names.next();
            if (root.get(key).isObject()) {
                String segType = root.get(key).path("SegmentType").asText();
                ctx.segments.add(segType.isEmpty() ? key : segType);
            }
        }

        // Data Section 1
        String e55 = root.path("MessageFormatVersionIdentifier").asText(null);
        c.add(chk("Data Section 1", "MessageFormatVersionIdentifier", "55", e55, "E55-FINANCIAL-REQUEST",
                "ATL105".equals(e55), "Fixed value 'ATL105' (AN6).", "ATL105 11.8.1 F1; 13 E55"));
        String e63 = root.path("NumberofSegments").asText(null);
        int segCount = ctx.segments.size();
        boolean e63ok = e63 != null && e63.matches("[0-9]{1,2}") && Integer.parseInt(e63) == segCount;
        c.add(chk("Data Section 1", "NumberofSegments", "63", e63, "E63-EMV-FINANCIAL-REQUEST-COUNT", e63ok,
                "N up to 2 digits; must equal segments present (" + segCount + ": " + ctx.segments + ").",
                "ATL105 11.8.1 F2; 13 E63"));
        c.add(chk("Data Section 1", "Section 3 slots", "-", String.valueOf(segCount - 1), ctx.emvMessage ? "SEG130-R-018" : "11.1-SECTION3",
                ctx.emvMessage ? segCount - 1 >= 1 && segCount - 1 <= 5 : segCount - 1 <= 6,
                ctx.emvMessage ? "EMV request Data Section 3 occupies Field Nos. 4-8 (1..5 segments)."
                        : "Financial Transaction Request Data Section 3 occupies Field Nos. 4-9 (0..6 segments).",
                "ATL105 11.1 / 11.8.1 p.11-43"));
        Set<String> allowedKeys = Set.of("MessageFormatVersionIdentifier", "NumberofSegments", SEG100, SEG111, SEG130,
                "Fleet Data Segment", "Product Code Data Segment", "EBT Data Segment", "Purchase Card Data Segment",
                "NFC Payment Tokenization Data Segment", "Moneris Data Segment (Request)", "Tax by Product Data Segment",
                "Enhanced Fleet Request", "Request InComm OTC Market Basket Data", "Network Token Data Request");
        root.fieldNames().forEachRemaining(k -> {
            if (!allowedKeys.contains(k)) {
                c.add(new Check("Message", k, "-", "-", "11.8.1-SECTION3", Verdict.FAIL,
                        "Key is not a Data Section 1 element or an EMV-request-permitted segment.", "ATL105 11.8.1"));
            }
        });

        // Segment presence vs. spec and TC decisions
        presenceCheck(c, root, tcStep, "100", SEG100, "R", "Required in Data Section 2 of every Financial Transaction Request.", "ATL105 11.1 / 11.8.1 p.11-42; 12.1");
        if (ctx.emvMessage) {
            presenceCheck(c, root, tcStep, "130", SEG130, "R", "Always appears in Data Section 3 of an EMV request.", "ATL105 12.20; SEG130-R-001");
        } else {
            boolean reversal = REVERSAL_FAMILY.contains(ctx.txnCode);
            boolean completion = ctx.role.startsWith("completion");
            boolean has130 = root.has(SEG130);
            Verdict v = has130 ? Verdict.FAIL : reversal ? Verdict.PASS : completion ? Verdict.REVIEW : Verdict.FAIL;
            c.add(new Check("Segment presence", SEG130 + " (130)", "-", has130 ? "present" : "absent", "SEG130-R-017/R-001", v,
                    has130 ? "Segment 130 belongs only to the EMV Financial Transaction Request."
                            : reversal ? "EMV data is not required on Reversal transactions (type '" + ctx.txnCode
                            + "'); absence on this follow-on is legal."
                            : completion ? "Completion carrying the original Approval Number (preauthorized, BUYPASS does not go out "
                            + "for authorization - E5) sent as a non-EMV Financial Transaction Request; ATL105 states the reversal "
                            + "exemption only - confirm with SME that EMV data may be omitted on completions."
                            : "Non-reversal leg sent without Segment 130 although the card was read by chip (SEG130-R-001).",
                    "ATL105 10.14.2.4; 12.20; 13 E5"));
        }
        presenceCheck(c, root, tcStep, "111", SEG111, "R*", "Table 031 must be populated for all transactions (BR-569-1).", "ATL105 App. I-20 (p.569)");

        int msgLen = 6 + 1 + (e63 == null ? 0 : e63.length()) + 1;
        Integer s100 = validateSeg100(root.get(SEG100), tc, ctx, c);
        Integer s102 = validateSeg102(root.get(SEG102), tc, ctx, c);
        Integer s111 = validateSeg111(root.get(SEG111), tc, ctx, c);
        Integer s130 = validateSeg130(root.get(SEG130), tc, ctx, c);
        if (s100 != null && s111 != null && s130 != null) {
            msgLen += s100 + s111 + s130 + (s102 == null ? 0 : s102);
            c.add(chk("Message", "Total message length", "-", String.valueOf(msgLen), "11.8.1-MAX-4000",
                    msgLen <= 4000, "Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes.",
                    "ATL105 11.8.1 note"));
        }

        if (tcRequest != null) {
            fidelity(root, tcRequest, out.fidelity);
        }
        return ctx;
    }

    private void presenceCheck(List<Check> c, JsonNode root, JsonNode tcStep, String seg, String key, String presence,
                               String rule, String source) {
        boolean present = root.has(key);
        String decision = tcStep == null ? "" : tcStep.path("segment_decisions").path(seg).path("decision").asText("");
        c.add(chk("Segment presence", key + " (" + seg + ")", "-", present ? "present" : "absent", "SEG-" + seg + "-PRESENCE",
                present, rule + (decision.isEmpty() ? "" : " TC decision: " + decision + "."), source));
    }

    // ------------------------------------------------------------------ Segment 100

    private Integer validateSeg100(JsonNode seg, JsonNode tc, LegContext ctx, List<Check> c) {
        if (seg == null) {
            return null;
        }
        String area = "Seg 100";
        String network = tc == null ? "" : tc.path("dimensions").path("network").asText("");
        String txnCategory = tc == null ? "" : tc.path("dimensions").path("txn_category").asText("");
        layoutCheck(area, seg, SEG100_FIELDS, c);
        Map<String, String> v = new LinkedHashMap<>();
        for (FieldDef f : SEG100_FIELDS) {
            JsonNode n = seg.get(f.tdKey());
            String value;
            if (n == null) {
                value = null;
            } else if (n.isObject()) {
                value = n.path("TransactionType").asText("") + n.path("CardType").asText("");
            } else {
                value = n.asText();
            }
            v.put(f.tdKey(), value);
        }
        ctx.seg100.putAll(v);

        String s = v.get("SegmentType");
        c.add(chk(area, "SegmentType", "85", s, "E85-SEG100-TYPE", "100".equals(s), "N3, fixed value 100.", "12.1 F1"));

        String ib = v.get("InformationByte");
        c.add(chk(area, "InformationByte", "44", ib, "SEG100-E44", ib != null && Set.of("?", "0", "1").contains(ib),
                "AN1: '?' download, '0' single-message, '1' multimessage.", "13 E44 (p.379)"));

        String tid = v.get("TerminalIdentifier");
        boolean tidFmt = tid != null && tid.matches("[A-Za-z0-9+*]{13,22}");
        c.add(chk(area, "TerminalIdentifier", "102", tid, "SEG100-E102-FORMAT", tidFmt,
                "AN up to 22: Device Type(2)+State Code(2)+Merchant No.(6-15)+Device No.(3) => 13..22 chars.",
                "13 E102 (p.410-411)"));
        if (tidFmt) {
            String state = tid.substring(2, 4);
            c.add(chk(area, "TerminalIdentifier.StateCode", "102", state, "APPD-ANSI-STATE",
                    AppendixDStateCodes.isValidAnsiCode(state),
                    "Positions 3-4 must be a valid ANSI state code (Appendix D).", "13 E102; App. D"));
            c.add(new Check(area, "TerminalIdentifier.DeviceType/Merchant", "102", tid.substring(0, 2) + " / "
                    + tid.substring(4, tid.length() - 3) + " / " + tid.substring(tid.length() - 3),
                    "SEG100-E102-ASSIGNED", Verdict.REVIEW,
                    "Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a "
                            + "placeholder (merchant_provenance=" + text(tc, "merchant_provenance") + ").", "13 E102"));
        }

        String pc = v.get("PromptCode");
        boolean pcFmt = pc != null && pc.matches("[0-9A-Z][0-9]{3}");
        c.add(chk(area, "PromptCode", "78", pc, "SEG100-E78-FORMAT", pcFmt,
                "AN4: pos 1 Transaction Type (App. G) + pos 2-4 Card Type (App. E).", "12.1 F5; 13 E78"));
        if (pcFmt) {
            String tt = pc.substring(0, 1);
            String ct = pc.substring(1);
            var entry = appendixG.classify(tt);
            String expectedTt = ctx.txnCode.isEmpty() ? ("PURCHASE".equals(txnCategory) ? "0" : tt) : ctx.txnCode;
            c.add(chk(area, "PromptCode.TransactionType", "78", tt, "APPG-TXN-TYPE", entry.isPresent() && expectedTt.equals(tt),
                    "Appendix G: " + entry.map(AppendixGTransactionTypeCatalog.Entry::type).orElse("unknown")
                            + "; TC leg (role " + ctx.role + ") requires '" + expectedTt + "'.", "App. G (p.545)"));
            boolean creditCard = AppendixETableLoadCardTypeCodes.cardTypeCodes().contains(ct)
                    && (!"MASTERCARD".equals(network) || "020".equals(ct));
            c.add(chk(area, "PromptCode.CardType", "78", ct, "APPE-CARD-TYPE", creditCard,
                    "Appendix E code 020 = Credit (MasterCard credit program).", "App. E-4"));
        }

        String pan = v.get("AccountNumber");
        boolean panFmt = pan != null && pan.matches("[0-9]{12,19}") && pan.length() <= 24;
        c.add(chk(area, "AccountNumber", "2", pan, "SEG100-R-041", panFmt,
                "ANS, non-TransArmor request up to 24 bytes; card PAN numeric.", "13 E2 (p.364)"));
        if (panFmt) {
            c.add(chk(area, "AccountNumber.Luhn", "2", pan, "PAN-LUHN", luhn(pan),
                    "PAN check digit (ISO/IEC 7812 mod-10).", "ISO/IEC 7812"));
            if ("MASTERCARD".equals(network)) {
                c.add(chk(area, "AccountNumber.IIN", "2", pan.substring(0, 6), "PAN-IIN-MASTERCARD", isMastercard(pan),
                        "MasterCard IIN ranges 51-55 / 2221-2720 (TC network MASTERCARD).", "BUYPASS IIN Reference Manual"));
            }
        }

        String cdbd = v.get("CardDiscretionaryBlockData");
        boolean expiryOnly = cdbd != null && cdbd.matches("[0-9]{4}");
        boolean cdbdFmt = cdbd != null && cdbd.matches("[0-9]{4}|[0-9]{7,51}");
        c.add(chk(area, "CardDiscretionaryBlockData", "12", cdbd, "SEG100-R-042", cdbdFmt,
                expiryOnly ? "AN up to 51; follow-on carries the expiration date only (4 digits)."
                        : "AN up to 51; Track 2 data after '=' => YYMM expiry + 3-digit service code + discretionary.",
                "12.1 F7; 13 E12 (p.364-365)"));
        if (cdbdFmt) {
            String yymm = cdbd.substring(0, 4);
            int mm = Integer.parseInt(yymm.substring(2));
            c.add(chk(area, "CardDiscretionaryBlockData.Expiry", "12", yymm, "TRACK2-EXPIRY-YYMM", mm >= 1 && mm <= 12,
                    "Expiry YYMM month must be 01-12" + (yymm.compareTo(runYymm) < 0 ? " (EXPIRED as of " + runYymm + ")."
                            : " (valid, not expired as of " + runYymm + ")."), "13 E12; Track 2 (ISO 7813)"));
        }
        if (cdbdFmt && !expiryOnly) {
            String svc = cdbd.substring(4, 7);
            c.add(chk(area, "CardDiscretionaryBlockData.ServiceCode", "12", svc, "TRACK2-SERVICE-CODE-CHIP",
                    svc.startsWith("2") || svc.startsWith("6"),
                    "Service code 1st digit 2/6 = chip card (consistent with EMV contact entry).", "ISO 7813"));
            if (pan != null) {
                int combined = pan.length() + 1 + cdbd.length();
                c.add(chk(area, "Account+Discretionary (Track 2)", "2+12", String.valueOf(combined), "TRACK2-COMBINED-37",
                        combined <= 37, "Account Number + '=' + Card Discretionary Block Data must not exceed 37 for Track 2.",
                        "13 E12 (p.364)"));
            }
        }

        emptyConditional(area, c, v, "EncryptedPINBlockData", "33", "AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry.");
        emptyConditional(area, c, v, "PumpLaneNumber", "79", "N2; fuel/pump context only.");
        emptyConditional(area, c, v, "FuelPurchaseAmount", "41", "N up to 8; fuel purchase only.");
        emptyConditional(area, c, v, "TaxAmount", "99", "N up to 8; optional tax amount.");
        emptyConditional(area, c, v, "CashAmount", "17", "N up to 8; cash back only.");
        emptyConditional(area, c, v, "LocalDateandLocalTime", "49", "N10; preauthorized transaction only.");

        String amt = v.get("NonfuelAmount");
        if ((amt == null || amt.isEmpty()) && "E".equals(ctx.txnCode)) {
            c.add(new Check(area, "NonfuelAmount", "58", "(empty)", "SEG100-E58-COND", Verdict.NA,
                    "Balance Inquiry (type E) carries no transaction amount; Element 58 is conditional.", "12.1 F11; 13 E58"));
        } else if (isRuntimePlaceholder(amt)) {
            c.add(new Check(area, "NonfuelAmount", "58", amt, "SEG100-E58-RUNTIME", Verdict.REVIEW,
                    "Runtime placeholder - value comes from the previous leg's response; must resolve to N 1-7 digits "
                            + "(non-Amex) at execution.", "13 E58 (p.384)"));
        } else {
            int max = "AMEX".equals(network) ? 8 : 7;
            boolean ok = amt != null && amt.matches("[0-9]{1," + max + "}") && Long.parseLong(amt) > 0;
            c.add(chk(area, "NonfuelAmount", "58", amt, "SEG100-E58-FORMAT", ok,
                    "N, up to " + max + " digits for " + (network.isEmpty() ? "card" : network)
                            + " (8 only for American Express), 2 assumed decimals, > 0.", "13 E58 (p.384)"));
        }

        String seq = v.get("SequenceNumber");
        c.add(chk(area, "SequenceNumber", "86", seq, "E86-FINANCIAL-REQUEST-SHAPE",
                seq != null && seq.matches("[0-9]{6}") && !"000000".equals(seq), "N6, 000001-999999.", "12.1 F14; 13 E86"));

        String appr = v.get("ApprovalNumber");
        boolean reversalLeg = ctx.approvalLinked || REVERSAL_FAMILY.contains(ctx.txnCode) && !"Z".equals(ctx.txnCode);
        if (reversalLeg) {
            if (isRuntimePlaceholder(appr)) {
                c.add(new Check(area, "ApprovalNumber", "5", appr, "SEG100-E5-FOLLOW-ON", Verdict.REVIEW,
                        "Required on this follow-on (" + ctx.role + ") to match the original (AN6 fixed, space-filled); runtime "
                                + "link to the original response - must resolve to the 6-character Approval Number at execution.",
                        "13 E5 (p.368); p.85"));
            } else {
                c.add(chk(area, "ApprovalNumber", "5", appr, "SEG100-E5-FOLLOW-ON", appr != null && appr.length() == 6,
                        "Required on this follow-on (" + ctx.role + ") for matching to the original; AN, fixed length 6.", "13 E5 (p.368)"));
            }
        } else if ("0".equals(ctx.txnCode) || ctx.txnCode.isEmpty()) {
            c.add(chk(area, "ApprovalNumber", "5", appr, "BR-368-2", appr == null || appr.isEmpty(),
                    "Must be empty on a new Purchase request (an Approval Number marks it preauthorized).", "13 E5 (p.368)"));
        } else if (appr == null || appr.isEmpty()) {
            c.add(new Check(area, "ApprovalNumber", "5", "(empty)", "SEG100-E5-COND", Verdict.NA,
                    "Not populated; not required for transaction type '" + ctx.txnCode + "'.", "13 E5 (p.368)"));
        } else {
            c.add(new Check(area, "ApprovalNumber", "5", appr, "SEG100-E5-COND", Verdict.REVIEW,
                    "Populated on transaction type '" + ctx.txnCode + "' without a TC link to an original response.", "13 E5 (p.368)"));
        }

        String pai = v.get("PartialApprovalIndicator");
        boolean paiOk = pai != null && (Set.of("0", "1").contains(pai) || ("5".equals(pai) && "AMEX".equals(network)));
        c.add(chk(area, "PartialApprovalIndicator", "121", pai, "SEG100-E121", paiOk,
                "N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only.", "13 E121 (p.440)"));

        // Segment Length (Element 84): every transmitted field is followed by a Field Separator; trailing
        // unneeded fields are not transmitted (12.1).
        int last = -1;
        for (int i = 0; i < SEG100_FIELDS.size(); i++) {
            String val = v.get(SEG100_FIELDS.get(i).tdKey());
            if (val != null && !val.isEmpty()) {
                last = i;
            }
        }
        int computed = 0;
        int placeholderChars = 0;
        List<String> placeholderFields = new ArrayList<>();
        for (int i = 0; i <= last; i++) {
            String val = v.get(SEG100_FIELDS.get(i).tdKey());
            if (isRuntimePlaceholder(val)) {
                placeholderChars += val.length();
                placeholderFields.add(SEG100_FIELDS.get(i).tdKey());
            }
            computed += (val == null ? 0 : val.length()) + 1;
        }
        String declared100 = v.get("SegmentLength");
        if (!placeholderFields.isEmpty() && declared100 != null && declared100.matches("[0-9]{3}")) {
            int implied = Integer.parseInt(declared100) - (computed - placeholderChars);
            String note = impliedWidthNote(placeholderFields, implied, network);
            boolean impossible = note.contains("EXCEEDS") || note.contains("INCONSISTENT");
            c.add(new Check(area, "SegmentLength (implied runtime width)", "84", declared100, "E84-SEG100-RUNTIME-WIDTH",
                    impossible ? Verdict.FAIL : Verdict.REVIEW, "Declared length implies the runtime value(s) " + placeholderFields
                    + " occupy " + implied + " character(s)" + note + (impossible
                    ? ". No valid runtime value can produce this length - it was computed from an out-of-range provisional value." : ".")
                    , "12.1 F2; 13 E5/E58"));
        }
        return segmentLength(area, declared100, 3, 619, computed, !placeholderFields.isEmpty(),
                "sum of transmitted fields (" + (last + 1) + ") + one " + FS_NOTE + " after each", c,
                "E84-SEG100-LENGTH", "12.1 F2; 11.8.1 max 619");
    }

    private static String impliedWidthNote(List<String> fields, int implied, String network) {
        int maxAmt = "AMEX".equals(network) ? 8 : 7;
        if (fields.size() == 2 && fields.contains("ApprovalNumber") && fields.contains("NonfuelAmount")) {
            int amountWidth = implied - 6;
            return " = Approval Number 6 + Nonfuel Amount " + amountWidth + (amountWidth <= maxAmt ? " (within Element 58 limit)"
                    : " (amount EXCEEDS Element 58 limit of " + maxAmt + " digits for " + network + ")");
        }
        if (fields.size() != 1) {
            return "";
        }
        return switch (fields.get(0)) {
            case "ApprovalNumber" -> implied == 6 ? " (consistent with Element 5 fixed length 6)"
                    : " (INCONSISTENT with Element 5 fixed length 6)";
            case "NonfuelAmount" -> implied <= ("AMEX".equals(network) ? 8 : 7) ? " (within Element 58 limit)"
                    : " (EXCEEDS Element 58 limit of " + ("AMEX".equals(network) ? 8 : 7) + " digits for " + network + ")";
            default -> "";
        };
    }

    private void emptyConditional(String area, List<Check> c, Map<String, String> v, String key, String el, String rule) {
        String val = v.get(key);
        if (val == null || val.isEmpty()) {
            c.add(new Check(area, key, el, "(empty)", "SEG100-E" + el + "-COND", Verdict.NA,
                    "Not populated; conditional field not applicable to this transaction. " + rule, "12.1"));
        } else {
            boolean ok = switch (el) {
                case "33" -> val.matches("[0-9A-Fa-f]{32,36}");
                case "79" -> val.matches("[0-9]{1,2}");
                case "49" -> val.matches("[0-9]{10}");
                default -> val.matches("[0-9]{1,8}");
            };
            c.add(chk(area, key, el, val, "SEG100-E" + el + "-FORMAT", ok, rule, "12.1; 13 E" + el));
        }
    }

    // ------------------------------------------------------------------ Segment 102

    private static final Set<String> SERVICE_LEVELS = Set.of("F", "S", "N", "X", "H", "O");
    private static final Set<String> UNITS = Set.of("C", "G", "H", "I", "K", "L", "M", "P", "Q", "U", "W", "Z", "O");
    private static final Set<String> HEALTHCARE_CODES = Set.of("890", "891", "892", "893", "894", "895");

    private Integer validateSeg102(JsonNode seg, JsonNode tc, LegContext ctx, List<Check> c) {
        if (seg == null) {
            return null;
        }
        String area = "Seg 102";
        c.add(chk(area, "SegmentType", "85", seg.path("SegmentType").asText(null), "E85-SEG102-TYPE",
                "102".equals(seg.path("SegmentType").asText()), "N3, fixed value 102.", "12.3 F1"));
        String sl = seg.path("ServiceLevel").asText(null);
        c.add(chk(area, "ServiceLevel", "87", sl, "SEG102-E87", sl != null && (SERVICE_LEVELS.contains(sl) || sl.matches("[0-9]")),
                "A1: F full, S self, N mini, X maxi, H high-speed, O other/fuel not present (0-9 private).", "12.3 F3; 13 E87"));
        String np = seg.path("NumberofProducts").asText(null);
        List<JsonNode> prods = new ArrayList<>();
        JsonNode arr = seg.has("ProductData") ? seg.get("ProductData") : seg.has("Products") ? seg.get("Products") : null;
        if (arr != null && arr.isArray()) {
            arr.forEach(prods::add);
        } else if (seg.has("ProductCode")) {
            prods.add(seg);
        }
        c.add(chk(area, "NumberofProducts", "62", np, "SEG102-E62",
                np != null && np.matches("(0[1-9]|10)") && Integer.parseInt(np) == prods.size(),
                "N2 01-10 (leading zero) and equal to the products present (" + prods.size() + ").", "12.3 F4; 13 E62"));
        int body = 0;
        long productTotal = 0;
        int i = 0;
        for (JsonNode p : prods) {
            i++;
            String f = prods.size() == 1 ? "" : "Product[" + i + "].";
            String code = p.path("ProductCode").asText("");
            String uom = p.path("UnitofMeasure").asText("");
            String qty = p.path("Quantity").asText("");
            String price = p.path("UnitPrice").asText("");
            String amount = p.path("ProductAmount").asText("");
            Map<String, String> m = new LinkedHashMap<>();
            m.put("ProductCode", code);
            m.put("ProductAmount", amount);
            ctx.products.add(m);
            c.add(chk(area, f + "ProductCode", "77", code, "SEG102-E77", code.matches("[0-9]{3}"),
                    "N3 product code (Appendix F" + (HEALTHCARE_CODES.contains(code) ? "; " + code + " = healthcare/QHP" : "") + ").",
                    "12.3 F5; 13 E77; App. F"));
            c.add(chk(area, f + "UnitofMeasure", "106", uom, "SEG102-E106", UNITS.contains(uom) || uom.matches("[0-9]"),
                    "A1: C,G,H,I,K,L,M,P,Q,U,W,Z,O (0-9 private).", "12.3 F6; 13 E106"));
            c.add(chk(area, f + "Quantity", "81", qty, "SEG102-E81", qty.matches("[0-3][0-9]{1,8}"),
                    "N up to 9; leading digit = assumed decimals (0-3) and a digit for every decimal position.", "12.3 F7; 13 E81"));
            c.add(chk(area, f + "UnitPrice", "107", price, "SEG102-E107", price.matches("[0-3][0-9]{1,8}"),
                    "N up to 9; leading digit = assumed decimals (0-3).", "12.3 F8; 13 E107"));
            c.add(chk(area, f + "ProductAmount", "76", amount, "SEG102-E76", amount.matches("[0-9]{1,12}") && !amount.matches("0+"),
                    "N 1-999999999999, two assumed decimals.", "12.3 F9; 13 E76"));
            if (qty.matches("[0-3][0-9]{1,8}") && price.matches("[0-3][0-9]{1,8}") && amount.matches("[0-9]{1,12}")) {
                java.math.BigDecimal q = new java.math.BigDecimal(new java.math.BigInteger(qty.substring(1)), qty.charAt(0) - '0');
                java.math.BigDecimal u = new java.math.BigDecimal(new java.math.BigInteger(price.substring(1)), price.charAt(0) - '0');
                java.math.BigDecimal ext = q.multiply(u).setScale(2, java.math.RoundingMode.HALF_UP);
                java.math.BigDecimal amt = new java.math.BigDecimal(new java.math.BigInteger(amount), 2);
                c.add(new Check(area, f + "Quantity x UnitPrice vs ProductAmount", "81/107/76",
                        q.toPlainString() + " x " + u.toPlainString() + " = " + ext.toPlainString() + " vs " + amt.toPlainString(),
                        "SEG102-EXTENSION", ext.compareTo(amt) == 0 ? Verdict.PASS : Verdict.REVIEW,
                        "Product Amount normally equals Quantity x Unit Price (discounts/coupons use negative codes).",
                        "12.3; 13 E76/E81/E107"));
                productTotal += Long.parseLong(amount);
            }
            body += code.length() + uom.length() + qty.length() + 1 + price.length() + 1 + amount.length() + 1;
        }
        long s100Total = 0;
        for (String k : List.of("FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount")) {
            String val = ctx.seg100.get(k);
            if (val != null && val.matches("[0-9]+")) {
                s100Total += Long.parseLong(val);
            }
        }
        c.add(chk(area, "Sum of ProductAmount vs Segment 100 amounts", "76 / 41+58+99+17", productTotal + " vs " + s100Total,
                "SEG102-TOTAL", productTotal == s100Total,
                "The total of product amounts must equal Elements 41 + 58 + 99 + 17 in Segment 100.", "12.3 (p.226)"));
        int computed = 3 + 1 + 3 + 1 + (sl == null ? 0 : sl.length()) + (np == null ? 0 : np.length()) + body;
        return segmentLength(area, seg.path("SegmentLength").asText(null), 3, 381, computed, false,
                "type+FS+length+FS+service level+count+per product (code+UoM+qty+'\\'+price+'\\'+amount+delimiter/FS); "
                        + "formula reproduces Appendix B example length 049", c, "E84-SEG102-LENGTH", "12.3 F2; App. B");
    }

    // ------------------------------------------------------------------ Totals Request / Segment 105

    private static final Set<String> CARD_LABELS = Set.of("CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR", "CK", "EF", "EC",
            "SV1", "SV2", "SV3", "SV4", "ECA", "EWIC", "EK");
    private static final Set<String> TOTALS_CODES = Set.of("111111", "222222", "333333", "444444", "555555", "999999");

    private void validateTotalsMessage(JsonNode root, JsonNode tc, LegContext ctx, List<Check> c) {
        String area = "Seg 105";
        ctx.segments.add("105");
        String e55 = root.path("MessageFormatVersionIdentifier").asText(null);
        c.add(chk("Data Section 1", "MessageFormatVersionIdentifier", "55", e55, "E55", "ATL105".equals(e55),
                "Fixed value 'ATL105'.", "11.4.1.1 F1"));
        String e63 = root.path("NumberofSegments").asText(null);
        c.add(chk("Data Section 1", "NumberofSegments", "63", e63, "E63-TOTALS-REQUEST-COUNT", "01".equals(e63),
                "Fixed value 01 (Totals Request carries only Segment 105, no Data Section 2).", "11.4.1.1 F2; 13 E63"));
        root.fieldNames().forEachRemaining(k -> {
            if (!Set.of("MessageFormatVersionIdentifier", "NumberofSegments", SEG105).contains(k)) {
                c.add(new Check("Message", k, "-", "-", "11.4.1.1", Verdict.FAIL,
                        "Totals Request contains only Data Section 1 and Segment 105.", "11.4.1.1"));
            }
        });
        JsonNode s = root.get(SEG105);
        if (s == null) {
            c.add(new Check("Segment presence", SEG105, "-", "absent", "11.4.1.1", Verdict.FAIL, "Segment 105 is required.", "11.4.1.1"));
            return;
        }
        Map<String, String> v = new LinkedHashMap<>();
        s.fieldNames().forEachRemaining(k -> v.put(k, s.get(k).isValueNode() ? s.get(k).asText() : s.get(k).toString()));
        ctx.totals.putAll(v);
        String network = tc == null ? "" : tc.path("dimensions").path("network").asText("");

        c.add(chk(area, "SegmentType", "85", v.get("SegmentType"), "E85-SEG105-TYPE", "105".equals(v.get("SegmentType")),
                "N3, fixed value 105.", "12.6 F1"));
        String ib = v.get("InformationByte");
        c.add(chk(area, "InformationByte", "44", ib, "E44-TOTALS-REQUEST", ib != null && ib.matches("[01]"),
                "0 single-message, 1 multimessage.", "12.6 F3; 13 E44"));
        String tid = v.get("TerminalIdentifier");
        c.add(chk(area, "TerminalIdentifier", "102", tid, "SEG105-E102", tid != null && tid.matches("[A-Za-z0-9+*]{13,22}"),
                "AN 13-22 (Device Type + State Code + Merchant No. + Device No.).", "12.6 F4; 13 E102"));
        c.add(chk(area, "PromptCode", "78", v.get("PromptCode"), "E78-TOTALS-REQUEST", "990".equals(v.get("PromptCode")),
                "Fixed value 990.", "12.6 F5"));
        String emp = v.get("EmployeeNumber");
        c.add(emp == null || emp.isEmpty()
                ? new Check(area, "EmployeeNumber", "32", "(empty)", "SEG105-E32", Verdict.NA,
                "Conditional; not populated (13 E32: use 1111 if no employee numbers are assigned).", "12.6 F6; 13 E32")
                : chk(area, "EmployeeNumber", "32", emp, "E32-TOTALS-REQUEST-POPULATED", emp.matches("[0-9]{4}"), "N4.", "12.6 F6; 13 E32"));
        String pw = v.get("Password");
        c.add(pw == null || pw.isEmpty()
                ? new Check(area, "Password", "65", "(empty)", "SEG105-E65", Verdict.REVIEW,
                "Layout marks Password 'C', but Element 65 states 'Password required for totals request' (matched against the "
                        + "merchant profile; default 123456). Confirm whether the merchant requires it.", "12.6 F7; 13 E65")
                : chk(area, "Password", "65", pw, "SEG105-E65", pw.matches(" {0,5}[0-9]{1,6}") && pw.length() <= 6,
                "N up to 6, right-aligned with spaces.", "12.6 F7; 13 E65"));
        String td = v.get("TotalsDate");
        boolean tdFmt = td != null && (TOTALS_CODES.contains(td) || td.matches("(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{2}"));
        c.add(chk(area, "TotalsDate", "105", td, "E105-TOTALS-REQUEST", tdFmt,
                "N6: MMDDYY, 111111, 222222 (read and cut), 333333/444444/555555 (most recent/2nd/3rd date with activity), 999999.",
                "12.6 F8; 13 E105"));
        java.util.regex.Matcher om = java.util.regex.Pattern.compile("\\b(\\d{6})\\b").matcher(text(tc, "test_objective"));
        if (om.find()) {
            c.add(chk(area, "TotalsDate vs TC objective", "105", td + " vs " + om.group(1), "TC-OBJECTIVE-TOTALS-DATE",
                    om.group(1).equals(td), "TC objective '" + text(tc, "test_objective") + "' names totals code " + om.group(1) + ".",
                    "TC; 13 E105"));
        }
        String hw = v.get("HardwareVersion");
        c.add(chk(area, "HardwareVersion", "43", hw, "E43-TOTALS-REQUEST", hw != null && hw.matches("[A-Za-z0-9]{4}|[A-Za-z0-9]{8}"),
                "AN, fixed 4 or 8.", "12.6 F9; 13 E43"));
        c.add(chk(area, "SoftwareVersion", "96", v.get("SoftwareVersion"), "E96-TOTALS-REQUEST",
                v.get("SoftwareVersion") != null && v.get("SoftwareVersion").matches("[A-Za-z0-9]{8}"), "AN, fixed 8.", "12.6 F10; 13 E96"));
        c.add(chk(area, "FirmwareVersion", "39", v.get("FirmwareVersion"), "E39-TOTALS-REQUEST",
                v.get("FirmwareVersion") != null && v.get("FirmwareVersion").matches("[A-Za-z0-9]{8}"), "AN, fixed 8.", "12.6 F11; 13 E39"));
        c.add(new Check(area, "Hardware/Software/Firmware values", "43/96/39", hw + " / " + v.get("SoftwareVersion") + " / "
                + v.get("FirmwareVersion"), "SEG105-DEVICE-VERSIONS", Verdict.REVIEW,
                "Format is checked; the values must be the certified device's real versions (synthesized here).", "13 E39/E43/E96"));
        String seq = v.get("SequenceNumber");
        c.add(chk(area, "SequenceNumber", "86", seq, "E86-TOTALS-REQUEST", seq != null && seq.matches("[0-9]{6}") && !"000000".equals(seq),
                "N6, 000001-999999.", "12.6 F12; 13 E86"));
        String cur = v.get("CurrencyCode");
        c.add(cur == null || cur.isEmpty()
                ? new Check(area, "CurrencyCode", "20", "(empty)", "SEG105-E20", Verdict.NA, "Optional; defaults to 840.", "12.6 F13")
                : chk(area, "CurrencyCode", "20", cur, "SEG105-E20", cur.matches("[0-9]{3}"),
                "N3 per Appendix L" + ("840".equals(cur) ? " (840 = USD)." : "."), "12.6 F13; 13 E20; App. L"));
        String gt = v.get("GrandTotal");
        c.add(chk(area, "GrandTotal", "42", gt, "SEG105-E42", gt != null && gt.matches("[0-9]{8}"),
                "N, fixed 8, two assumed decimals.", "12.6 F14; 13 E42"));

        String label = v.get("CardLabel");
        String count = v.get("CardTypeTotalCount");
        String amount = v.get("CardTypeTotalAmount");
        boolean hasCategory = label != null || count != null || amount != null;
        if (hasCategory) {
            String trimmed = label == null ? "" : label.trim();
            c.add(chk(area, "CardLabel (value)", "13", label, "SEG105-E13-VALUE", CARD_LABELS.contains(trimmed)
                            && (!"MASTERCARD".equals(network) || "CC".equals(trimmed)),
                    "Valid Card Label; MasterCard/Visa bucket = CC.", "12.6 F15; 13 E13"));
            c.add(chk(area, "CardLabel (width)", "13", "'" + label + "'", "SEG105-E13-WIDTH", label != null && label.length() == 4,
                    "AN, FIXED length 4, left-aligned and space-filled" + (label != null && label.length() != 4
                            ? "; '" + label + "' is " + label.length() + " byte(s) - expected '" + String.format("%-4s", trimmed) + "'." : "."),
                    "12.6 F15; 13 E13 (p.365)"));
            c.add(chk(area, "CardTypeTotalCount", "16", count, "SEG105-E16", count != null && count.matches("[0-9]{5}") && !"00000".equals(count),
                    "N, fixed 5, valid 00001-99999" + ("00000".equals(count)
                            ? "; a zero count is not a valid value - Field Nos. 15-17 are sent only when data occurs for a card type." : "."),
                    "12.6 (Category Total Data Section); 13 E16"));
            c.add(chk(area, "CardTypeTotalAmount", "15", amount, "SEG105-E15", amount != null && amount.matches("[0-9]{8}"),
                    "N, fixed 8, two assumed decimals.", "12.6 F17; 13 E15"));
            if (gt != null && amount != null && gt.matches("[0-9]+") && amount.matches("[0-9]+")) {
                c.add(chk(area, "GrandTotal vs sum of category amounts", "42/15", gt + " vs " + amount, "SEG105-GRAND-TOTAL",
                        Long.parseLong(gt) == Long.parseLong(amount), "With a single card-type bucket, Grand Total equals its total amount.",
                        "12.6; 13 E42/E15"));
            }
        }
        int fixed = 0;
        for (String k : List.of("SegmentType", "SegmentLength", "InformationByte", "TerminalIdentifier", "PromptCode", "EmployeeNumber",
                "Password", "TotalsDate", "HardwareVersion", "SoftwareVersion", "FirmwareVersion", "SequenceNumber", "CurrencyCode",
                "GrandTotal")) {
            fixed += (v.get(k) == null ? 0 : v.get(k).length()) + 1;
        }
        int categoryAsIs = hasCategory ? (label == null ? 0 : label.length()) + (count == null ? 0 : count.length())
                + (amount == null ? 0 : amount.length()) : 0;
        int categoryPadded = hasCategory ? 4 + (count == null ? 0 : count.length()) + (amount == null ? 0 : amount.length()) : 0;
        String declared = v.get("SegmentLength");
        c.add(chk(area, "SegmentLength (format)", "84", declared, "E84-SEG105-LENGTH", declared != null && declared.matches("[0-9]{3}"),
                "N3, 001-409.", "12.6 F2"));
        if (declared != null && declared.matches("[0-9]{3}")) {
            int d = Integer.parseInt(declared);
            int asIs = fixed + categoryAsIs;
            int padded = fixed + categoryPadded;
            Verdict lv;
            String why;
            if (d == asIs) {
                lv = Verdict.PASS;
                why = "matches the generated values (Fields 1-14 each + FS, Fields 15-17 without FS).";
            } else if (d == padded) {
                lv = Verdict.PASS;
                why = "matches only when Card Label is padded to its fixed 4 bytes (see CardLabel width).";
            } else if (d == padded + 1 || d == asIs + 1) {
                lv = Verdict.REVIEW;
                why = "matches only with a trailing Field Separator after Field 17" + (d == padded + 1 ? " and Card Label padded to 4 bytes" : "")
                        + "; 12.6 states FS after Fields 1-14 only - confirm whether the segment ends with an FS.";
            } else {
                lv = Verdict.FAIL;
                why = "does not match the generated values (Fields 1-14 each + FS = " + fixed + ", category = " + categoryAsIs + ").";
            }
            c.add(new Check(area, "SegmentLength (value)", "84", declared + " (computed " + asIs + "; with 4-byte Card Label "
                    + padded + ")", "E84-SEG105-LENGTH", lv, "Declared length " + why, "12.6 F2 and layout note"));
        }
    }

    // ------------------------------------------------------------------ Segment 111

    private Integer validateSeg111(JsonNode seg, JsonNode tc, LegContext ctx, List<Check> c) {
        if (seg == null) {
            return 0;
        }
        String area = "Seg 111";
        String network = tc == null ? "" : tc.path("dimensions").path("network").asText("");
        String entryMode = tc == null ? "" : tc.path("dimensions").path("entry_mode").asText("");
        String type = seg.path("SegmentType").asText(null);
        c.add(chk(area, "SegmentType", "85", type, "E85-SEG111-TYPE", "111".equals(type), "N3, fixed value 111.", "12.10 F1"));
        Set<String> known = Set.of("SegmentType", "SegmentLength", "VariableInformationSection");
        seg.fieldNames().forEachRemaining(k -> {
            if (!known.contains(k)) {
                c.add(new Check(area, k, "-", "-", "12.10-LAYOUT", Verdict.FAIL, "Key not in Segment 111 layout.", "12.10"));
            }
        });
        JsonNode sections = seg.path("VariableInformationSection");
        int body = 0;
        boolean has031 = false;
        int idx = 0;
        for (JsonNode sec : sections) {
            idx++;
            String ind = sec.path("VariableInformationIndicator").asText(null);
            String len = sec.path("VariableInformationLength").asText(null);
            String data = sec.path("VariableInformation").asText(null);
            String f = "Section[" + idx + "]";
            body += (ind == null ? 0 : ind.length()) + (len == null ? 0 : len.length()) + (data == null ? 0 : data.length());
            c.add(chk(area, f + ".VariableInformationIndicator", "111", ind, "SEG111-E111",
                    ind != null && ind.matches("[0-9]{3}") && appendixICatalog.classify(ind) != null,
                    "N3 Table ID (Appendix I catalog: " + (ind == null ? "-" : appendixICatalog.classify(ind)) + ").",
                    "12.10 F3; 13 E111"));
            boolean lenOk = len != null && len.matches("[0-9]{3}") && data != null && Integer.parseInt(len) == data.length()
                    && Integer.parseInt(len) >= 1;
            c.add(chk(area, f + ".VariableInformationLength", "112", len, "SEG111-E112", lenOk,
                    "N3 001-999 and equal to the length of Element 113 (" + (data == null ? 0 : data.length()) + ").",
                    "12.10 F4; 13 E112"));
            if (ind == null) {
                continue;
            }
            ctx.table.put(ind, data);
            switch (ind) {
                case "005" -> {
                    var res = appendixI.validate(ind, data, network);
                    res.findings().forEach(fd -> c.add(new Check(area, f + ".VariableInformation (Table 005)", "113", data,
                            fd.ruleId(), map(fd.status()), fd.reason(), "App. I Table 005 / App. J")));
                    var j = appendixJ.evaluate(data);
                    c.add(chk(area, f + ".POS Entry Mode (App. J)", "113", data, "APPJ-POS-ENTRY-MODE",
                            j.outcome() == AppendixJPosEntryModeOracle.Outcome.PASS,
                            "PAN entry '" + j.panEntryMode() + "' = " + appendixJ.panEntryModes().get(j.panEntryMode())
                                    + "; terminal capability '" + j.terminalCapabilityMode() + "' = "
                                    + appendixJ.terminalCapabilityModes().get(j.terminalCapabilityMode()) + ".",
                            "App. J (p.638)"));
                    if (!ctx.entryCode.isEmpty() && data != null && data.length() >= 2) {
                        c.add(chk(area, f + ".POS Entry Mode vs TC leg entry_code", "113", data.substring(0, 2), "LEG-ENTRY-MODE",
                                ctx.entryCode.equals(data.substring(0, 2)), "TC leg entry_code '" + ctx.entryCode + "' ("
                                        + appendixJ.panEntryModes().get(ctx.entryCode) + ") for entry_mode " + entryMode + ".",
                                "App. J; TC rule_checks"));
                    }
                }
                case "012" -> c.add(chk(area, f + ".VariableInformation (Table 012)", "113", data, "APPI-T012",
                        data != null && Set.of("M", "B", "E", "H").contains(data),
                        "Market-Specific Data Indicator, 1 character: M Medical (healthcare auto-substantiation), B Bill Payment, "
                                + "E eCommerce aggregate, H Hotel.", "App. I Table 012 (p.551)"));
                case "031" -> {
                    has031 = true;
                    c.add(chk(area, f + ".VariableInformation (Table 031)", "113", data, "BR-569-1",
                            data != null && data.length() == 2 && TABLE_031_CODES.contains(data),
                            "n2 fixed, Terminal Capability code in {00-07,09,12}"
                                    + ("12".equals(data) ? " (12 = Hybrid ICC contact & contactless)." : "."),
                            "App. I-20 (p.569)"));
                }
                default -> appendixI.validate(ind, data, network).findings().forEach(fd -> c.add(new Check(area,
                        f + ".VariableInformation", "113", data, fd.ruleId(), map(fd.status()), fd.reason(), "App. I")));
            }
        }
        c.add(chk(area, "Table 031 present", "111", has031 ? "yes" : "no", "TABLE-031-POPULATED", has031,
                "Terminal Capability (Table 031) must be populated for all transactions.", "App. I-20 (p.569) BR-569-1"));
        c.add(chk(area, "Variable Information Section total", "-", String.valueOf(body), "12.10-SECTION-991",
                body <= 991, "Repeated section total must not exceed 991 bytes.", "12.10"));
        int computed = 3 + 1 + 3 + 1 + body + (idx > 0 ? 1 : 0);
        return segmentLength(area, seg.path("SegmentLength").asText(null), 3, 999, computed, false,
                "type+FS+length+FS+sections+final FS", c, "E84-SEG111-LENGTH", "12.10 F2");
    }

    // ------------------------------------------------------------------ Segment 130

    private Integer validateSeg130(JsonNode seg, JsonNode tc, LegContext ctx, List<Check> c) {
        if (seg == null) {
            return 0;
        }
        String area = "Seg 130";
        layoutCheck(area, seg, SEG130_FIELDS, c);
        Map<String, String> v = new LinkedHashMap<>();
        SEG130_FIELDS.forEach(f -> v.put(f.tdKey(), seg.has(f.tdKey()) ? seg.get(f.tdKey()).asText() : null));
        ctx.seg130.putAll(v);

        c.add(chk(area, "SegmentType", "85", v.get("SegmentType"), "SEG130-R-002", "130".equals(v.get("SegmentType")),
                "N3, fixed value 130.", "12.20 F1"));

        String cks = v.get("CAPublicKeyFileChecksum");
        c.add(chk(area, "CAPublicKeyFileChecksum", "187", cks, "SEG130-R-005", cks != null && cks.matches("[0-9]{25}"),
                "N, fixed length 25; required in the EMV Financial Transaction Request (13 E187).", "12.20 F3; 13 E187 (p.469)"));
        c.add(new Check(area, "CAPublicKeyFileChecksum.Value", "187", cks, "SEG130-R-015", Verdict.REVIEW,
                "Must equal the checksum from the last CA Public Key File Load in use at host/device; value is a "
                        + "QE-sample (library_provisional) - confirm against the host key file.", "13 E187; App. S"));

        String csn = v.get("EMVCardSequenceNumber");
        boolean csnOk = csn != null && (csn.matches("0[0-9]{2}") || "   ".equals(csn));
        c.add(chk(area, "EMVCardSequenceNumber", "188", csn, "SEG130-R-006/E188", csnOk,
                "AN, FIXED length 3, right-aligned zero-padded (000-099) or 3 spaces when Tag 5F34 absent"
                        + (csnOk ? "." : "; '" + csn + "' is " + (csn == null ? 0 : csn.length()) + " byte(s) - expected '"
                        + (csn != null && csn.matches("[0-9]{1,2}") ? String.format("%03d", Integer.parseInt(csn)) : "0nn") + "'."),
                "12.20 F4; 13 E188 (p.470)"));

        String cdl = v.get("EMVChipDataLength");
        String chip = v.get("EMVChipData");
        c.add(chk(area, "EMVChipDataLength", "189", cdl, "SEG130-R-007", cdl != null && cdl.matches("[0-9]{3}"),
                "N3, 000-999.", "12.20 F5; 13 E189"));
        int chipBytes = chip == null ? 0 : chip.length() / 2;
        boolean hex = chip != null && chip.matches("([0-9A-Fa-f]{2})+");
        c.add(chk(area, "EMVChipData", "190", abbreviate(chip), "SEG130-R-008", hex && chipBytes <= 999,
                "ANSB up to 999 bytes; hex-nibble representation of binary TLV (" + chipBytes + " bytes).",
                "12.20 F6; 13 E190 (p.471)"));
        if (cdl != null && cdl.matches("[0-9]{3}")) {
            c.add(chk(area, "EMVChipDataLength vs EMVChipData", "189/190", cdl + " vs " + chipBytes, "BR-SEG100-APPR-CHIP-LENGTH",
                    Integer.parseInt(cdl) == chipBytes, "Element 189 must equal the byte length of Element 190.", "13 E189/E190"));
        }
        // Trained Appendix R oracle (packed BCD prefix candidate path, as in the Appendix R-1 example '01\06').
        var rCtx = new AppendixREmvChipDataOracle.TransactionContext(Boolean.TRUE);
        for (var a : appendixR.assessWithPackedLengthPrefix(cdl, chip, rCtx)) {
            if (a.businessRequirementId().equals(AppendixREmvChipDataOracle.BR_LENGTH_PREFIX)) {
                continue;
            }
            c.add(new Check(area, "EMVChipData (Appendix R oracle)", "190", "-", a.businessRequirementId(), map(a.status()),
                    a.reason(), "App. R-1; 13 E190"));
        }
        var prefix = appendixR.decodePackedPrefixCandidate(chip);
        if (prefix.tlvDecode() != null && prefix.tlvDecode().wellFormed()) {
            prefix.tlvDecode().entries().forEach(e -> ctx.tlv.putIfAbsent(e.tag(), e.valueHex()));
        } else {
            var bare = appendixR.decode(chip);
            if (bare.wellFormed()) {
                bare.entries().forEach(e -> ctx.tlv.putIfAbsent(e.tag(), e.valueHex()));
            }
        }
        emvCrossField(area, ctx, tc, c);

        String ind = v.get("EMVAdditionalInformationIndicator");
        String alen = v.get("EMVAdditionalInformationLength");
        String info = v.get("EMVAdditionalInformation");
        int infoLen = info == null ? 0 : info.length();
        String tableId = info != null && info.length() >= 3 ? info.substring(0, 3) : info;
        String tableValue = info == null ? null : info.length() > 3 ? info.substring(3).replaceFirst("^[0-9]{1,3}", "") : "";
        for (var a : appendixT.assessSection(ind, alen, tableId, tableValue)) {
            // The oracle reports table-specific rules as FAIL when the table does not apply; the Indicator decides applicability.
            boolean notApplicable = (AppendixTEmvAdditionalInformationOracle.BR_CARC.equals(a.businessRequirementId()) && !"002".equals(ind))
                    || (AppendixTEmvAdditionalInformationOracle.BR_EMV_TABLE_DATA.equals(a.businessRequirementId()) && !"001".equals(ind));
            c.add(new Check(area, "EMV Additional Information (Appendix T oracle)", "191/192/118",
                    a.assertionScope(), a.businessRequirementId(), notApplicable ? Verdict.NA : map(a.status()),
                    notApplicable ? "Not applicable: Indicator is '" + ind + "'." : a.reason(), "App. T-1; 13 E191/E192"));
        }
        c.add(chk(area, "EMVAdditionalInformationIndicator", "191", ind, "E191", "001".equals(ind) || "002".equals(ind),
                "N3; 001 EMV table data (App. T also defines 002 CARC).", "12.20 F7; 13 E191 (p.472)"));
        c.add(chk(area, "EMVAdditionalInformationLength", "192", alen, "E192",
                alen != null && alen.matches("[0-9]{3}") && Integer.parseInt(alen) >= 1 && Integer.parseInt(alen) <= 985
                        && Integer.parseInt(alen) == infoLen,
                "N3 001-985 and equal to the length of Element 118 (" + infoLen + ").", "12.20 F8; 13 E192 (p.473)"));
        boolean table001 = "001".equals(ind);
        boolean emvTableOk = !table001 || (info != null && info.matches("(001[0-9]{0,3})?(EMVYES|EMVNOT)"));
        c.add(chk(area, "EMVAdditionalInformation", "118", abbreviate(info), "SEG130-R-020/BR-SEG100-APPT-EMV-TABLE-DATA",
                emvTableOk, table001 ? "Table 001 (EMV Table Data) value must be exactly 6 chars 'EMVYES' or 'EMVNOT' "
                        + "(observed " + infoLen + " random alphanumeric chars; length should be 006)."
                        : "Table-specific payload per Appendix T.", "App. T-1 (p.694); 13 E118"));
        c.add(chk(area, "EMV Additional Information Section total", "-", String.valueOf(3 + 3 + infoLen),
                "SEG130-R-011", 3 + 3 + infoLen <= 2000, "Repeated section total max 2,000 bytes.", "12.20"));

        boolean sectionPresent = ind != null && !ind.isEmpty();
        int computed = 0;
        for (int i = 0; i < 6; i++) {
            String key = SEG130_FIELDS.get(i).tdKey();
            String val = v.get(key);
            int len = "EMVChipData".equals(key) ? chipBytes : (val == null ? 0 : val.length());
            computed += len + 1;
        }
        if (sectionPresent) {
            computed += (ind == null ? 0 : ind.length()) + (alen == null ? 0 : alen.length()) + infoLen + 1;
        }
        return segmentLength(area, v.get("SegmentLength"), 4, 3043, computed, false,
                "6 fixed fields each + " + FS_NOTE + " (chip data counted as binary bytes) + EMV Additional Information "
                        + "section + final " + FS_NOTE, c, "SEG130-R-003/R-004", "12.20 F2; SEG130-R-013");
    }

    private void emvCrossField(String area, LegContext ctx, JsonNode tc, List<Check> c) {
        Map<String, String> t = ctx.tlv;
        if (t.isEmpty()) {
            c.add(new Check(area, "EMV TLV cross-field", "190", "-", "SEG130-R-009", Verdict.FAIL,
                    "TLV could not be decoded; cross-field checks not possible.", "App. R"));
            return;
        }
        String amt = ctx.seg100.get("NonfuelAmount");
        String a9f02 = t.get("9F02");
        if ("E".equals(ctx.txnCode) && (amt == null || amt.isEmpty())) {
            c.add(chk(area, "Tag 9F02 for Balance Inquiry", "190/58", a9f02, "SEG130-R-009",
                    a9f02 != null && a9f02.matches("0+"),
                    "Balance Inquiry carries no amount in Segment 100, so Amount Authorized (9F02) must be zero.",
                    "SEG130-R-009; EMV Book 3"));
        } else if (isRuntimePlaceholder(amt)) {
            c.add(new Check(area, "Tag 9F02 vs NonfuelAmount", "190/58", a9f02 + " vs " + amt, "SEG130-R-009", Verdict.FAIL,
                    "Request amount is resolved at runtime (remaining balance), but chip Amount Authorized is fixed at "
                            + (a9f02 == null ? "-" : Long.parseLong(a9f02)) + " (the step-1 full amount); 9F02 must be "
                            + "regenerated from the resolved remainder.", "SEG130-R-009; generator EMV_CHIP_DATA_PER_TEST_CASE"));
        } else if (a9f02 != null && amt != null && amt.matches("[0-9]+")) {
            long total = 0;
            List<String> parts = new ArrayList<>();
            for (String k : List.of("FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount")) {
                String val = ctx.seg100.get(k);
                if (val != null && val.matches("[0-9]+")) {
                    total += Long.parseLong(val);
                    parts.add(k + "=" + val);
                }
            }
            c.add(chk(area, "Tag 9F02 vs Segment 100 amounts", "190/41+58+99+17", a9f02 + " vs " + total, "SEG130-R-009",
                    a9f02.matches("[0-9]+") && Long.parseLong(a9f02) == total,
                    "Amount Authorized (9F02) must equal the Segment 100 transaction total (" + String.join(" + ", parts) + ").",
                    "SEG130-R-009 (KB: REVIEW_REQUIRED formula); generator rule '9F02 = request amount'"));
        }
        String pc = ctx.seg100.get("PromptCode");
        if (pc != null && !pc.isEmpty() && t.containsKey("9C")) {
            String tt = pc.substring(0, 1);
            String expected9c = switch (tt) {
                case "7" -> "20";
                case "E" -> "30";
                default -> "00";
            };
            String cash = ctx.seg100.get("CashAmount");
            if ("00".equals(expected9c) && cash != null && cash.matches("[0-9]+") && Long.parseLong(cash) > 0) {
                expected9c = "09";
            }
            boolean ok9c = expected9c.equals(t.get("9C")) || "30".equals(expected9c) && "31".equals(t.get("9C"));
            c.add(chk(area, "Tag 9C vs PromptCode.TransactionType", "190/78", t.get("9C") + " vs " + tt,
                    "SEG130-R-009", ok9c,
                    "Transaction type '" + tt + "' (" + appendixG.classify(tt).map(AppendixGTransactionTypeCatalog.Entry::type).orElse("?")
                            + ") => 9C = " + ("30".equals(expected9c) ? "30/31" : expected9c) + " (00 goods/services, 09 purchase with "
                            + "cashback, 20 refund/return, 30/31 available-funds/balance inquiry).",
                    "SEG130-R-009; EMV Book 3 Annex A (ISO 8583 processing code)"));
            String a9f03 = t.get("9F03");
            if (a9f03 != null) {
                long cashVal = cash != null && cash.matches("[0-9]+") ? Long.parseLong(cash) : 0;
                c.add(chk(area, "Tag 9F03 vs CashAmount", "190/17", a9f03 + " vs " + (cash == null || cash.isEmpty() ? "(empty)" : cash),
                        "SEG130-R-009", a9f03.matches("[0-9]+") && Long.parseLong(a9f03) == cashVal,
                        "Amount, Other (9F03) carries the cashback amount; must equal Segment 100 Cash Amount (Element 17).",
                        "SEG130-R-009; EMV Book 3"));
            }
        }
        String aid = t.getOrDefault("9F06", t.get("84"));
        String network = tc == null ? "" : tc.path("dimensions").path("network").asText("");
        c.add(chk(area, "AID (9F06/84)", "190", aid, "BR-SEG100-APPR-MANDATORY-IDENTIFIER",
                aid != null && (!"MASTERCARD".equals(network) || aid.startsWith("A000000004")),
                "AID present; MasterCard RID A000000004 expected for network " + network + ".", "13 E190; App. R"));
        if (t.containsKey("9F06") && t.containsKey("84")) {
            c.add(chk(area, "Tag 9F06 vs Tag 84", "190", t.get("9F06") + " vs " + t.get("84"), "EMV-AID-DFNAME",
                    t.get("9F06").equals(t.get("84")), "Terminal AID and DF Name should identify the same application.", "EMV Book 1"));
        }
        c.add(chk(area, "Tag 5F2A / 9F1A", "190", t.get("5F2A") + " / " + t.get("9F1A"), "SEG130-R-009",
                "0840".equals(t.get("5F2A")) && "0840".equals(t.get("9F1A")), "USD currency (0840) and US terminal country (0840).",
                "App. R-1 example; SEG130-R-009"));
        String csn = ctx.seg130.get("EMVCardSequenceNumber");
        String t5f34 = t.get("5F34");
        if (t5f34 != null && t5f34.matches("[0-9]{2}")) {
            String expected = String.format("%03d", Integer.parseInt(t5f34));
            boolean sameValue = csn != null && csn.trim().matches("[0-9]{1,3}") && Integer.parseInt(csn.trim()) == Integer.parseInt(t5f34);
            c.add(chk(area, "Element 188 value vs Tag 5F34", "188/190", csn + " vs " + t5f34, "E188-5F34", sameValue,
                    "Element 188 must carry the chip's Tag 5F34 value (wire form '" + expected + "'; width checked separately).",
                    "13 E188 (p.470)"));
        }
        c.add(chk(area, "Tag 9F27 (CID)", "190", t.get("9F27"), "EMV-CID-ARQC", "80".equals(t.get("9F27")),
                "Online request => Cryptogram Information Data 80 (ARQC).", "EMV Book 3"));
        c.add(new Check(area, "Tag 9F26/9F10/9F36/9F37/95", "190", t.get("9F26"), "SEG130-R-010", Verdict.REVIEW,
                "Cryptogram, IAD, ATC, UN and TVR are EMV_PLACEHOLDER values; authenticity needs a card simulator/HSM.",
                "SEG130-R-010; App. R"));
        StringBuilder decoded = new StringBuilder();
        t.forEach((k, val) -> decoded.append(k).append('=').append(val).append(' '));
        c.add(new Check(area, "Decoded TLV", "190", decoded.toString().trim(), "BR-SEG100-APPR-TLV-FORMAT", Verdict.PASS,
                t.size() + " TLV entries decoded.", "13 E190"));
        if ("07".equals(ctx.entryCode)) {
            List<String> clTags = List.of("9F6E", "9F6D", "9F7C", "9F39").stream().filter(t::containsKey).toList();
            c.add(new Check(area, "Contactless chip profile", "190/113", "9F34=" + t.get("9F34") + " 9F35=" + t.get("9F35")
                    + " 9F33=" + t.get("9F33") + " CL-tags=" + clTags, "EMV-CONTACTLESS-PROFILE", Verdict.REVIEW,
                    "Entry mode 07 (contactless chip) but the chip payload carries the same tag set/values as the contact "
                            + "template (no contactless-kernel tags such as 9F6E/9F6D/9F7C, CVM 9F34 = signature). ATL105 App. R "
                            + "does not mandate a tag list; confirm the payload against a Mastercard contactless (PayPass) kernel trace.",
                    "App. R-1 note (tag list not exhaustive); App. J code 07"));
        }
    }

    // ------------------------------------------------------------------ objective / flow checks

    private void objectiveChecks(JsonNode tc, List<LegContext> legs, List<Check> c) {
        if (tc == null || legs.isEmpty()) {
            return;
        }
        String template = text(tc, "template_id");
        LegContext leg = legs.get(0);
        String cvm = leg.tlv.get("9F34");
        String cvmCode = cvm == null || cvm.length() < 2 ? null : String.format("%02X", Integer.parseInt(cvm.substring(0, 2), 16) & 0x3F);
        String tvr = leg.tlv.get("95");
        String pin = leg.seg100.get("EncryptedPINBlockData");
        String area = "Objective (" + template + ")";
        c.add(new Check(area, "Test objective", "-", text(tc, "test_objective"), template, Verdict.NA,
                "catalog_status=" + text(tc, "catalog_status") + "; status=" + text(tc, "status")
                        + "; open_items=" + tc.path("open_items"), "TC"));
        if (tvr != null && tvr.length() == 10) {
            int b3 = Integer.parseInt(tvr.substring(4, 6), 16);
            boolean onlinePinFlag = (b3 & 0x04) != 0;
            boolean pinSent = pin != null && !pin.isEmpty();
            // TVR is declared EMV_PLACEHOLDER by the generator (simulator-supplied), so inconsistencies are review items.
            c.add(new Check(area, "TVR byte 3 'Online PIN entered' vs PIN block", "190/33",
                    tvr + " / PIN " + (pinSent ? "sent" : "absent"), "EMV-TVR-CVM-CONSISTENCY",
                    onlinePinFlag == pinSent ? Verdict.PASS : Verdict.REVIEW,
                    "TVR byte 3 bit 3 (0x04) " + (onlinePinFlag ? "is set (online PIN entered)" : "is not set")
                            + " while Element 33 is " + (pinSent ? "populated" : "empty")
                            + "; TVR is an EMV_PLACEHOLDER - the card simulator value must be consistent with the CVM.",
                    "EMV Book 3 Annex C5; 13 E33"));
        }
        switch (template) {
            case "T1-PUR" -> c.add(chk(area, "CVM for base purchase", "190", cvm, "T1-PUR-CVM",
                    cvmCode != null && !"02".equals(cvmCode) || pin != null && !pin.isEmpty(),
                    "Base purchase: CVM other than online PIN, or online PIN with PIN block.", "EMV Book 3 Annex C3"));
            case "T2-EXPIRED-EMV" -> {
                String cdbd = leg.seg100.get("CardDiscretionaryBlockData");
                String yymm = cdbd == null || cdbd.length() < 4 ? null : cdbd.substring(0, 4);
                c.add(chk(area, "Card expiry for expired-card test", "12", yymm, "T2-EXPIRED-EMV",
                        yymm != null && yymm.compareTo(runYymm) < 0,
                        "Expired-card scenario requires Track 2 expiry (YYMM) earlier than " + runYymm
                                + "; the device must still send it online (p.160, 13 E12 'send a value even for expired cards').",
                        "ATL105 p.160; 13 E12"));
                boolean expiredBit = tvr != null && tvr.length() == 10 && (Integer.parseInt(tvr.substring(2, 4), 16) & 0x40) != 0;
                c.add(new Check(area, "TVR 'Expired application' bit", "190", tvr, "EMV-TVR-EXPIRED",
                        expiredBit ? Verdict.PASS : Verdict.REVIEW,
                        "Expired card => TVR byte 2 bit 7 (0x40) 'Expired application' should be set (not set); Tag 5F24 "
                                + (leg.tlv.containsKey("5F24") ? "present." : "absent from chip data. TVR is an EMV_PLACEHOLDER."),
                        "EMV Book 3 Annex C5"));
            }
            case "T3-ONLINE-PIN" -> {
                c.add(chk(area, "EncryptedPINBlockData", "33", pin == null || pin.isEmpty() ? "(empty)" : pin, "T3-ONLINE-PIN-E33",
                        pin != null && pin.matches("[0-9A-Fa-f]{32,36}"),
                        "Online PIN: Element 33 must carry the DUKPT KSN + PIN block (AN36).", "12.1 F8; 13 E33; TC execution"));
                c.add(chk(area, "CVM Results (9F34)", "190", cvm, "T3-ONLINE-PIN-CVM", "02".equals(cvmCode),
                        "Online PIN => CVM code 02 (enciphered PIN verified online); observed code " + cvmCode
                                + (" 1E".contains(cvmCode == null ? "x" : cvmCode) ? " (signature)." : "."), "EMV Book 3 Annex C3"));
                c.add(chk(area, "Table 005 PIN capability", "113", leg.table.get("005"), "APPJ-PIN-CAP",
                        leg.table.get("005") != null && leg.table.get("005").endsWith("1"),
                        "Terminal must indicate PIN entry capability (position 3 = 1).", "App. J"));
            }
            case "T3-OFFLINE-PIN" -> {
                c.add(chk(area, "EncryptedPINBlockData", "33", pin == null || pin.isEmpty() ? "(empty)" : pin, "T3-OFFLINE-PIN-E33",
                        pin == null || pin.isEmpty(), "Offline PIN verified by the chip => no PIN block sent.", "13 E33; p.162"));
                c.add(chk(area, "CVM Results (9F34)", "190", cvm, "T3-OFFLINE-PIN-CVM",
                        cvmCode != null && Set.of("01", "03", "04", "05").contains(cvmCode),
                        "Offline PIN => CVM code 01/04 (plaintext/enciphered offline PIN, optionally +signature 03/05); "
                                + "observed code " + cvmCode + ("1E".equals(cvmCode) ? " (signature)." : "."), "EMV Book 3 Annex C3"));
            }
            case "T4-PUR-SPLIT" -> c.add(chk(area, "Step 1 PartialApprovalIndicator", "121", leg.seg100.get("PartialApprovalIndicator"),
                    "T4-PAI", "1".equals(leg.seg100.get("PartialApprovalIndicator")),
                    "Partial approval precondition: Element 121 = 1.", "13 E121 (p.440); p.84"));
            default -> {
            }
        }
    }

    /** Role-specific cross-leg checks not expressible through the TC's own rule_checks/link_defs. */
    private void flowChecks(JsonNode tc, List<LegContext> legs, List<Check> c) {
        if (legs.size() < 2) {
            return;
        }
        for (int i = 1; i < legs.size(); i++) {
            LegContext f = legs.get(i);
            if (f.totalsMessage) {
                totalsFlowCheck(legs, i, c);
                continue;
            }
            if (!f.followOn || f.of < 1 || f.of > legs.size() || f.of - 1 == i) {
                continue;
            }
            LegContext o = legs.get(f.of - 1);
            String area = "Flow (step " + (i + 1) + " " + f.role + " of step " + f.of + ")";
            c.add(chk(area, "AccountNumber = original", "2", o.seg100.get("AccountNumber") + " / " + f.seg100.get("AccountNumber"),
                    "FOLLOW-ON-SAME-CARD", eq(o.seg100.get("AccountNumber"), f.seg100.get("AccountNumber")),
                    "A follow-on must identify the same card as the transaction it matches.", "13 E2 / E5 (matching to the original)"));
            String exp1 = o.seg100.get("CardDiscretionaryBlockData");
            String exp2 = f.seg100.get("CardDiscretionaryBlockData");
            c.add(chk(area, "Expiry = original expiry", "12", (exp1 == null ? "-" : exp1.substring(0, Math.min(4, exp1.length())))
                            + " / " + exp2, "FOLLOW-ON-SAME-EXPIRY",
                    exp1 != null && exp2 != null && exp2.length() >= 4 && exp1.startsWith(exp2.substring(0, 4)),
                    "Follow-on must carry the original card's expiration date (same credentials as the original).", "13 E12 (p.364-365)"));
            c.add(chk(area, "POS entry mode = original", "113", o.table.get("005") + " / " + f.table.get("005"),
                    "FOLLOW-ON-SAME-ENTRY", eq(o.table.get("005"), f.table.get("005")),
                    "Follow-on reports the original's Table 005 entry mode (TC leg entry_code).", "App. J; TC tx_legs"));
            c.add(chk(area, "PartialApprovalIndicator = original", "121", o.seg100.get("PartialApprovalIndicator") + " / "
                            + f.seg100.get("PartialApprovalIndicator"), "FOLLOW-ON-SAME-PAI",
                    eq(o.seg100.get("PartialApprovalIndicator"), f.seg100.get("PartialApprovalIndicator")),
                    "Element 121 is required on all transactions; follow-on keeps the original merchant setting.", "13 E121 (p.440)"));
        }
        LegContext s1 = legs.get(0);
        LegContext s2 = legs.get(1);
        String area = "Flow (step 1 -> step 2, " + s2.role + ")";
        if (!"split_tender".equals(s2.role)) {
            return;
        }
        String a1 = s1.seg100.get("NonfuelAmount");
        String a2 = s2.seg100.get("NonfuelAmount");
        String tcStep2 = tc == null ? null : tc.path("steps").path(1).path("request").path(SEG100).path("Nonfuel Amount").path("value").asText(null);
        if (isRuntimePlaceholder(a2)) {
            c.add(new Check(area, "Step 2 NonfuelAmount (remainder)", "58", a2, "SPLIT-TENDER-REMAINDER", Verdict.REVIEW,
                    "Runtime expression is correct in intent (requested - Approved Amount E6). TC provisional value '" + tcStep2
                            + "' is NOT a valid remainder of " + a1 + (tcStep2 != null && tcStep2.matches("[0-9]+")
                            && Long.parseLong(tcStep2) >= Long.parseLong(a1) ? " (exceeds the original amount" : " (")
                            + (tcStep2 != null && tcStep2.length() > 7 ? "; 8 digits > 7-byte MasterCard limit)." : ")."),
                    "p.84; 13 E58"));
        } else if (a1 != null && a2 != null && a1.matches("[0-9]+") && a2.matches("[0-9]+")) {
            c.add(chk(area, "Step 2 NonfuelAmount < Step 1 amount", "58", a2 + " < " + a1, "SPLIT-TENDER-REMAINDER",
                    Long.parseLong(a2) < Long.parseLong(a1), "Remainder = requested - approved partial amount.", "p.84"));
        }
        String c1 = s1.tlv.get("9F37");
        String c2 = s2.tlv.get("9F37");
        c.add(chk(area, "Step 2 chip data is card B's own", "190", c1 + " / " + c2, "SPLIT-TENDER-CHIP",
                c1 == null || !c1.equals(c2), "Second card must carry its own unpredictable number/cryptogram.", "EMV Book 2"));
        c.add(chk(area, "Step 2 9F02 = step 2 amount", "190", s2.tlv.get("9F02"), "SPLIT-TENDER-9F02",
                !isRuntimePlaceholder(a2) && s2.tlv.get("9F02") != null && a2 != null && a2.matches("[0-9]+")
                        && Long.parseLong(s2.tlv.get("9F02")) == Long.parseLong(a2),
                "Card B Amount Authorized must equal the remaining balance, not the original amount.", "SEG130-R-009"));
    }

    private static final Map<String, String> TC_TO_TD_100 = new LinkedHashMap<>();

    static {
        SEG100_FIELDS.forEach(f -> TC_TO_TD_100.put(f.tcName(), f.tdKey()));
    }

    /** Executes the TC's declared rule_checks against the generated test data. */
    private void tcRuleChecks(JsonNode tc, List<LegContext> legs, List<Check> c) {
        if (tc == null) {
            return;
        }
        for (JsonNode rc : tc.path("rule_checks")) {
            int step = rc.path("step").asInt(1);
            String id = rc.path("id").asText();
            String op = rc.path("op").asText();
            String area = "TC rule_checks (step " + step + ")";
            String basis = rc.path("basis").asText("");
            if (step < 1 || step > legs.size()) {
                c.add(new Check(area, id, "-", "-", op, Verdict.FAIL, "Step " + step + " has no test data leg.", basis));
                continue;
            }
            LegContext leg = legs.get(step - 1);
            List<String> allowed = new ArrayList<>();
            rc.path("allowed").forEach(a -> allowed.add(a.asText()));
            String value = null;
            boolean holds;
            switch (op) {
                case "value_slice_in" -> {
                    String el = rc.path("element").asText();
                    String raw = TC_TO_TD_100.containsKey(el) ? fieldOf(leg, TC_TO_TD_100.get(el))
                            : leg.totals.get(el.replaceAll("[^A-Za-z0-9]", ""));
                    value = "Card Label".equals(el) && raw != null ? raw.trim() : slice(raw, rc);
                    holds = value != null && allowed.contains(value);
                }
                case "product_total_equals_amounts" -> {
                    long p = leg.products.stream().map(m -> m.get("ProductAmount"))
                            .filter(x -> x != null && x.matches("[0-9]+")).mapToLong(Long::parseLong).sum();
                    long s = 0;
                    for (String k : List.of("FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount")) {
                        String x = leg.seg100.get(k);
                        if (x != null && x.matches("[0-9]+")) {
                            s += Long.parseLong(x);
                        }
                    }
                    value = p + " vs " + s;
                    holds = !leg.products.isEmpty() && p == s;
                }
                case "table_slice_in" -> {
                    value = slice(leg.table.get(rc.path("table_id").asText()), rc);
                    holds = value != null && allowed.contains(value);
                }
                case "segment_present" -> {
                    value = leg.segments.toString();
                    holds = leg.segments.contains(rc.path("segment").asText());
                }
                case "field_absent" -> {
                    value = leg.seg100.get(TC_TO_TD_100.get(rc.path("element").asText()));
                    holds = value == null || value.isEmpty();
                }
                case "value_differs_from_step" -> {
                    String key = TC_TO_TD_100.get(rc.path("element").asText());
                    int other = rc.path("other_step").asInt();
                    String otherVal = other >= 1 && other <= legs.size() ? legs.get(other - 1).seg100.get(key) : null;
                    value = leg.seg100.get(key) + " vs step " + other + " " + otherVal;
                    holds = leg.seg100.get(key) != null && !leg.seg100.get(key).equals(otherVal);
                }
                default -> {
                    c.add(new Check(area, id, "-", "-", op, Verdict.REVIEW, "Unsupported rule_check op.", basis));
                    continue;
                }
            }
            boolean expectHold = !"violate".equals(rc.path("expect").asText("hold"));
            c.add(new Check(area, id + " " + rc.path("element").asText(rc.path("table_id").asText(rc.path("segment").asText())),
                    "-", value, op, holds == expectHold ? Verdict.PASS : Verdict.FAIL,
                    "Expected " + (expectHold ? "to hold" : "to be violated") + (allowed.isEmpty() ? "" : "; allowed " + allowed) + ".",
                    basis));
        }
    }

    /** Compares a Totals Request leg with the transactions sent earlier in the same flow. */
    private void totalsFlowCheck(List<LegContext> legs, int totalsIndex, List<Check> c) {
        long[] count = new long[totalsIndex];
        long[] amount = new long[totalsIndex];
        boolean runtime = false;
        List<String> trail = new ArrayList<>();
        for (int i = 0; i < totalsIndex; i++) {
            LegContext l = legs.get(i);
            String amt = l.seg100.get("NonfuelAmount");
            if ("0".equals(l.txnCode) || "7".equals(l.txnCode)) {
                if (isRuntimePlaceholder(amt)) {
                    runtime = true;
                } else if (amt != null && amt.matches("[0-9]+")) {
                    count[i] = 1;
                    amount[i] = "7".equals(l.txnCode) ? -Long.parseLong(amt) : Long.parseLong(amt);
                }
                trail.add("step " + (i + 1) + " " + l.role + " " + amt);
            } else if (REVERSAL_FAMILY.contains(l.txnCode) && l.of >= 1 && l.of <= totalsIndex) {
                count[l.of - 1] = 0;
                amount[l.of - 1] = 0;
                trail.add("step " + (i + 1) + " " + l.role + " cancels step " + l.of);
            }
        }
        long expCount = java.util.Arrays.stream(count).sum();
        long expAmount = java.util.Arrays.stream(amount).sum();
        LegContext t = legs.get(totalsIndex);
        String gotCount = t.totals.get("CardTypeTotalCount");
        String gotGrand = t.totals.get("GrandTotal");
        boolean match = !runtime && gotCount != null && gotGrand != null && gotCount.matches("[0-9]+") && gotGrand.matches("[0-9]+")
                && Long.parseLong(gotCount) == expCount && Long.parseLong(gotGrand) == Math.abs(expAmount);
        c.add(new Check("Flow (totals step " + (totalsIndex + 1) + ")", "Totals vs earlier legs", "16/42",
                "count " + gotCount + ", grand " + gotGrand + " | expected count " + expCount + ", net " + expAmount,
                "TOTALS-FLOW-CONSISTENCY", match ? Verdict.PASS : Verdict.REVIEW,
                "Expected from earlier legs assuming every request is approved (" + String.join("; ", trail) + ")"
                        + (runtime ? "; runtime amounts present." : ". Host decisions can change the real totals."),
                "12.6; 13 E15/E16/E42"));
    }

    private static String fieldOf(LegContext leg, String key) {
        if (key == null) {
            return null;
        }
        return leg.seg100.containsKey(key) ? leg.seg100.get(key) : leg.totals.get(key);
    }

    private static String slice(String value, JsonNode rc) {
        int start = rc.path("start").asInt(1);
        int end = rc.path("end").asInt(start);
        if (value == null || value.length() < end) {
            return value;
        }
        return value.substring(start - 1, end);
    }

    /** Executes the TC's declared inter-leg link definitions against the generated test data. */
    private void linkDefChecks(JsonNode tc, List<LegContext> legs, List<Check> c) {
        if (tc == null) {
            return;
        }
        JsonNode txLegs = tc.path("tx_legs");
        for (int i = 0; i < txLegs.size() && i < legs.size(); i++) {
            for (JsonNode ld : txLegs.get(i).path("link_defs")) {
                int from = ld.path("from_step").asInt(1);
                if (from < 1 || from > legs.size()) {
                    continue;
                }
                String element = ld.path("element_name").asText();
                String key = TC_TO_TD_100.get(element);
                String cur = fieldOf(legs.get(i), key);
                String prev = fieldOf(legs.get(from - 1), key);
                String type = ld.path("type").asText();
                String area = "TC link_defs (step " + (i + 1) + " <- step " + from + ")";
                String src = "p." + ld.path("source_page").asText("-") + ": " + ld.path("rule").asText("");
                Check check = switch (type) {
                    case "carry_request_field", "retransmission" -> chk(area, element + (type.equals("retransmission") ? " retransmitted" : " carried"),
                            "-", prev + " / " + cur, type, eq(prev, cur), "Value must be identical to step " + from + ".", src);
                    case "must_differ" -> chk(area, element + " differs", "-", prev + " / " + cur, type, !eq(prev, cur),
                            "Value must differ from step " + from + ".", src);
                    case "amount_relation" -> {
                        String rel = ld.path("relation").asText();
                        if (isRuntimePlaceholder(cur) || isRuntimePlaceholder(prev)) {
                            yield new Check(area, element + " " + rel, "-", prev + " / " + cur, type, Verdict.REVIEW,
                                    "Runtime value; relation '" + rel + "' to step " + from + " can only be checked at execution.", src);
                        }
                        boolean ok = "equal".equals(rel) ? eq(prev, cur)
                                : "partial".equals(rel) ? prev != null && cur != null && prev.matches("[0-9]+") && cur.matches("[0-9]+")
                                && Long.parseLong(cur) > 0 && Long.parseLong(cur) < Long.parseLong(prev) : true;
                        yield chk(area, element + " " + rel, "-", prev + " / " + cur, type, ok,
                                "Amount relation '" + rel + "' to step " + from + ("partial".equals(rel) ? " (0 < amount < original)." : "."), src);
                    }
                    case "carry_response_field" -> new Check(area, element + " from step " + from + " response", "-", cur, type,
                            isRuntimePlaceholder(cur) ? Verdict.REVIEW : cur == null || cur.isEmpty() ? Verdict.FAIL : Verdict.REVIEW,
                            isRuntimePlaceholder(cur) ? "Correctly left as a runtime link (response values are processor-generated)."
                                    : cur == null || cur.isEmpty() ? "Value is missing; it must be linked to the step " + from + " response."
                                    : "Concrete value present; confirm it equals the step " + from + " response at execution.", src);
                    default -> new Check(area, element, "-", cur, type, Verdict.REVIEW, "Unsupported link type.", src);
                };
                c.add(check);
            }
        }
    }

    // ------------------------------------------------------------------ TC <-> TD fidelity

    private void fidelity(JsonNode td, JsonNode tcRequest, List<Check> out) {
        compareSegment(td.get(SEG100), tcRequest.get(SEG100), SEG100_FIELDS, SEG100, out);
        compareSegment(td.get(SEG130), tcRequest.get(SEG130), SEG130_FIELDS, SEG130, out);
        JsonNode tc111 = tcRequest.get(SEG111);
        JsonNode td111 = td.get(SEG111);
        if (tc111 != null) {
            List<String[]> pairs = new ArrayList<>();
            int i = 0;
            if (td111 != null) {
                for (JsonNode s : td111.path("VariableInformationSection")) {
                    String sfx = i == 0 ? "" : " [" + (i + 1) + "]";
                    pairs.add(new String[] {"Variable Information Indicator" + sfx, s.path("VariableInformationIndicator").asText(null)});
                    pairs.add(new String[] {"Variable Information Length" + sfx, s.path("VariableInformationLength").asText(null)});
                    pairs.add(new String[] {"Variable Information" + sfx, s.path("VariableInformation").asText(null)});
                    i++;
                }
                pairs.add(new String[] {"Segment Type", td111.path("SegmentType").asText(null)});
            }
            for (String[] p : pairs) {
                String tcVal = tc111.path(p[0]).path("value").isNull() ? null : tc111.path(p[0]).path("value").asText(null);
                out.add(fid(SEG111, p[0], p[1], tcVal));
            }
        }
        tcRequest.fieldNames().forEachRemaining(segName -> {
            if (TC_SEGMENT_NAMES.containsValue(segName)) {
                return;
            }
            JsonNode tcSeg = tcRequest.get(segName);
            boolean tcSends = tcSeg.path("Segment Type").path("value").isTextual();
            boolean tdHas = td.has(segName);
            out.add(new Check("TC<->TD", segName, "-", tdHas ? "present" : "absent", "TC-SEGMENT-DECISION",
                    tcSends == tdHas ? Verdict.PASS : Verdict.FAIL,
                    "TC " + (tcSends ? "sends" : "omits") + " this segment; TD " + (tdHas ? "includes" : "omits") + " it.", "TC request"));
            if (tcSends && tdHas) {
                Map<String, JsonNode> tdFields = new LinkedHashMap<>();
                td.get(segName).fieldNames().forEachRemaining(k -> tdFields.put(norm(k), td.get(segName).get(k)));
                tcSeg.fieldNames().forEachRemaining(name -> {
                    JsonNode node = tcSeg.get(name);
                    if (!node.isObject() || !node.has("value") || "derived_at_wire_encoding".equals(node.path("method").asText())) {
                        return;
                    }
                    JsonNode tdNode = tdFields.get(norm(name));
                    String tdVal = tdNode == null || tdNode.isContainerNode() ? null : tdNode.asText();
                    String tcVal = node.get("value").isNull() ? null : node.get("value").asText();
                    out.add(fid(segName, name + " [" + node.path("method").asText("") + "]", tdVal, tcVal));
                });
            }
        });
    }

    private static String norm(String s) {
        return s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }

    private void compareSegment(JsonNode tdSeg, JsonNode tcSeg, List<FieldDef> defs, String segName, List<Check> out) {
        if (tcSeg == null) {
            return;
        }
        for (FieldDef f : defs) {
            JsonNode tcNode = tcSeg.path(f.tcName());
            String tcVal = tcNode.path("value").isNull() || tcNode.path("value").isMissingNode() ? null : tcNode.path("value").asText();
            String method = tcNode.path("method").asText("");
            String tdVal = null;
            if (tdSeg != null && tdSeg.has(f.tdKey())) {
                JsonNode n = tdSeg.get(f.tdKey());
                tdVal = n.isObject() ? n.path("TransactionType").asText("") + n.path("CardType").asText("") : n.asText();
            }
            if ("derived_at_wire_encoding".equals(method)) {
                out.add(new Check("TC<->TD", segName + "." + f.tcName(), f.element(), tdVal, "TC-DERIVED", Verdict.NA,
                        "TC defers this value to wire encoding; TD computed '" + tdVal + "' (verified in spec checks).", "TC method"));
                continue;
            }
            out.add(fid(segName, f.tcName() + " [" + method + "]", tdVal, tcVal));
        }
    }

    private Check fid(String seg, String field, String tdVal, String tcVal) {
        String td = tdVal == null || tdVal.isEmpty() ? null : tdVal;
        boolean match = eq(td, tcVal);
        boolean runtime = isRuntimePlaceholder(td);
        return new Check("TC<->TD", seg + "." + field, "-", abbreviate(td) + " | TC: " + abbreviate(tcVal), "TC-TD-FIDELITY",
                match ? Verdict.PASS : runtime ? Verdict.REVIEW : Verdict.FAIL,
                match ? "TD value equals TC request value." : runtime
                        ? "TD carries the runtime expression; TC carries a provisional value."
                        : "TD value differs from TC request value.", "TC request");
    }

    // ------------------------------------------------------------------ helpers

    private void layoutCheck(String area, JsonNode seg, List<FieldDef> defs, List<Check> c) {
        List<String> expected = defs.stream().map(FieldDef::tdKey).toList();
        List<String> actual = new ArrayList<>();
        seg.fieldNames().forEachRemaining(actual::add);
        List<String> missing = expected.stream().filter(k -> !actual.contains(k)).toList();
        List<String> extra = actual.stream().filter(k -> !expected.contains(k)).toList();
        List<String> orderedActual = actual.stream().filter(expected::contains).toList();
        List<String> orderedExpected = expected.stream().filter(actual::contains).toList();
        boolean ok = missing.isEmpty() && extra.isEmpty() && orderedActual.equals(orderedExpected);
        c.add(chk(area, "Field layout (" + expected.size() + " fields)", "-", actual.size() + " keys", "LAYOUT-" + area.replace(" ", ""),
                ok, "All spec fields present in spec order" + (missing.isEmpty() ? "" : "; missing " + missing)
                        + (extra.isEmpty() ? "" : "; not in spec " + extra)
                        + (orderedActual.equals(orderedExpected) ? "" : "; order differs"), "ATL105 Chapter 12 layout table"));
        for (FieldDef f : defs) {
            if ("R".equals(f.presence())) {
                String val = seg.has(f.tdKey()) ? (seg.get(f.tdKey()).isObject() ? "obj" : seg.get(f.tdKey()).asText()) : null;
                if (val == null || val.isEmpty()) {
                    c.add(new Check(area, f.tdKey(), f.element(), "(empty)", "REQUIRED-FIELD", Verdict.FAIL,
                            "Required (R) field is not populated.", f.source()));
                }
            }
        }
    }

    private Integer segmentLength(String area, String declared, int width, int max, int computed, boolean placeholder,
                                  String formula, List<Check> c, String rule, String source) {
        boolean fmt = declared != null && declared.matches("[0-9]{" + width + "}");
        c.add(chk(area, "SegmentLength (format)", "84", declared, rule, fmt, "N" + width + ", range 1-" + max + ".", source));
        if (!fmt) {
            return computed;
        }
        int d = Integer.parseInt(declared);
        if (placeholder) {
            c.add(new Check(area, "SegmentLength (value)", "84", declared + " (computed with placeholder text: " + computed + ")",
                    rule, Verdict.REVIEW, "Contains a runtime value; declared length cannot be verified until resolved ("
                    + formula + ").", source));
            return d;
        }
        c.add(chk(area, "SegmentLength (value)", "84", declared + " (computed " + computed + ")", rule,
                d == computed && d <= max, "Must include Segment Type, all fields and " + FS_NOTE + "s: " + formula + ".", source));
        return d;
    }

    private static Check chk(String area, String field, String el, String value, String rule, boolean ok, String detail, String src) {
        return new Check(area, field, el, value, rule, ok ? Verdict.PASS : Verdict.FAIL, detail, src);
    }

    private static Verdict map(Enum<?> status) {
        return switch (status.name()) {
            case "PASS" -> Verdict.PASS;
            case "FAIL" -> Verdict.FAIL;
            default -> Verdict.REVIEW;
        };
    }

    private static boolean isRuntimePlaceholder(String v) {
        return v != null && v.startsWith("<") && v.endsWith(">");
    }

    private static boolean eq(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    static boolean luhn(String pan) {
        int sum = 0;
        boolean dbl = false;
        for (int i = pan.length() - 1; i >= 0; i--) {
            int d = pan.charAt(i) - '0';
            if (dbl) {
                d *= 2;
                if (d > 9) {
                    d -= 9;
                }
            }
            sum += d;
            dbl = !dbl;
        }
        return sum % 10 == 0;
    }

    private static boolean isMastercard(String pan) {
        int two = Integer.parseInt(pan.substring(0, 2));
        int four = Integer.parseInt(pan.substring(0, 4));
        return (two >= 51 && two <= 55) || (four >= 2221 && four <= 2720);
    }

    private static String text(JsonNode n, String field) {
        return n == null || n.path(field).isMissingNode() || n.path(field).isNull() ? "" : n.path(field).asText();
    }

    private static String firstKey(JsonNode n) {
        Iterator<String> it = n.fieldNames();
        return it.hasNext() ? it.next() : "-";
    }

    private static String abbreviate(String s) {
        if (s == null) {
            return "(null)";
        }
        return s.length() > 70 ? s.substring(0, 60) + "...(len " + s.length() + ")" : s;
    }

    private static String md(String s) {
        return s == null ? "" : s.replace("|", "\\|").replace("\n", " ");
    }

    // ------------------------------------------------------------------ reporting

    static final class LegReport {
        final String file;
        final List<Check> checks = new ArrayList<>();
        final List<Check> fidelity = new ArrayList<>();

        LegReport(String file) {
            this.file = file;
        }
    }

    final class CaseReport {
        final String id;
        final JsonNode tc;
        final List<Path> files;
        final List<LegReport> legs = new ArrayList<>();
        final List<Check> caseChecks = new ArrayList<>();

        CaseReport(String id, JsonNode tc, List<Path> files) {
            this.id = id;
            this.tc = tc;
            this.files = files;
        }

        List<Check> all() {
            List<Check> all = new ArrayList<>();
            legs.forEach(l -> {
                all.addAll(l.checks);
                all.addAll(l.fidelity);
            });
            all.addAll(caseChecks);
            return all;
        }

        long count(Verdict v) {
            return all().stream().filter(c -> c.verdict() == v).count();
        }

        String overall() {
            if (!all().isEmpty() && all().stream().allMatch(c -> c.verdict() == Verdict.NA)) {
                return "NOT_APPLICABLE";
            }
            return count(Verdict.FAIL) > 0 ? "FAIL" : count(Verdict.REVIEW) > 0 ? "PASS_WITH_REVIEW" : "PASS";
        }

        List<String> topDefects(int n) {
            Set<String> out = new LinkedHashSet<>();
            all().stream().filter(c -> c.verdict() == Verdict.FAIL && !"TC<->TD".equals(c.area()))
                    .forEach(c -> out.add((c.element().equals("-") ? "" : "E" + c.element() + " ") + c.field()));
            return out.stream().limit(n).toList();
        }

        String markdown() {
            StringBuilder b = new StringBuilder();
            b.append("# ATL105 Field-Level Test Data Validation - ").append(id).append("\n\n");
            b.append("| Item | Value |\n|---|---|\n");
            row(b, "Test case", id + " (" + text(tc, "display_id") + ")");
            row(b, "Name", text(tc, "name"));
            row(b, "Scenario", text(tc, "scenario_id"));
            row(b, "Template / type", text(tc, "template_id") + " / " + text(tc, "tc_type_name"));
            row(b, "Objective", text(tc, "test_objective"));
            row(b, "Transaction type", text(tc, "transaction_type"));
            row(b, "TC status", text(tc, "status") + " (catalog: " + text(tc, "catalog_status") + ")");
            row(b, "Open items", tc == null ? "" : tc.path("open_items").toString());
            row(b, "Test data file(s)", String.join(", ", files.stream().map(p -> p.getFileName().toString()).toList()));
            row(b, "Specification", "BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T)");
            row(b, "Validator", "regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles");
            row(b, "Run", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            row(b, "Overall verdict", "**" + overall() + "**");
            row(b, "Counts", "PASS " + count(Verdict.PASS) + " / FAIL " + count(Verdict.FAIL) + " / REVIEW "
                    + count(Verdict.REVIEW) + " / N/A " + count(Verdict.NA));
            b.append("\n## Defects (FAIL)\n\n");
            List<Check> fails = all().stream().filter(c -> c.verdict() == Verdict.FAIL).toList();
            if (fails.isEmpty()) {
                b.append("None.\n");
            } else {
                b.append("| # | Area | Field | Element | Value | Rule | Finding | Spec source |\n|---|---|---|---|---|---|---|---|\n");
                int i = 1;
                for (Check c : fails) {
                    b.append("| ").append(i++).append(" | ").append(md(c.area())).append(" | ").append(md(c.field())).append(" | ")
                            .append(md(c.element())).append(" | `").append(md(c.value())).append("` | ").append(md(c.rule()))
                            .append(" | ").append(md(c.detail())).append(" | ").append(md(c.source())).append(" |\n");
                }
            }
            b.append("\n## Review items\n\n");
            List<Check> reviews = all().stream().filter(c -> c.verdict() == Verdict.REVIEW).toList();
            if (reviews.isEmpty()) {
                b.append("None.\n");
            } else {
                table(b, reviews);
            }
            int legNo = 1;
            for (LegReport leg : legs) {
                b.append("\n## Leg ").append(legNo++).append(": `").append(leg.file).append("`\n\n");
                b.append("### Field-by-field ATL105 checks\n\n");
                table(b, leg.checks);
                b.append("\n### Test data vs test case request (generation fidelity)\n\n");
                table(b, leg.fidelity);
            }
            b.append("\n## Test objective and flow checks\n\n");
            table(b, caseChecks);
            b.append("\n---\nVerdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; "
                    + "REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); "
                    + "N/A = conditional field correctly not populated / informational.\n");
            return b.toString();
        }

        private void row(StringBuilder b, String k, String v) {
            b.append("| ").append(k).append(" | ").append(md(v)).append(" |\n");
        }

        private void table(StringBuilder b, List<Check> checks) {
            b.append("| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |\n|---|---|---|---|---|---|---|---|\n");
            for (Check c : checks) {
                b.append("| ").append(md(c.area())).append(" | ").append(md(c.field())).append(" | ").append(md(c.element()))
                        .append(" | `").append(md(c.value())).append("` | ").append(md(c.rule())).append(" | **")
                        .append(c.verdict()).append("** | ").append(md(c.detail())).append(" | ").append(md(c.source())).append(" |\n");
            }
        }
    }

    private String indexMarkdown(List<String[]> rows, Path testDataDir, Path testCases,
                                 Map<String, Set<String>> defectTcs, Map<String, Integer> defectHits) {
        StringBuilder b = new StringBuilder("# ATL105 Test Data Field Validation - Result Index\n\n");
        b.append("- Test data folder: `").append(testDataDir).append("`\n");
        b.append("- Test case catalog: `").append(testCases).append("`\n");
        b.append("- Specification: BUYPASS ATL105 Message Format Specifications 2026-3 (specifications/ATL105/docs/specs)\n");
        b.append("- Generated: ").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n\n");

        int total = rows.size();
        long withTd = rows.stream().filter(r -> !"0".equals(r[13])).count();
        long legsTotal = rows.stream().mapToLong(r -> Long.parseLong(r[13])).sum();
        long fail = rows.stream().filter(r -> "FAIL".equals(r[8])).count();
        long review = rows.stream().filter(r -> "PASS_WITH_REVIEW".equals(r[8])).count();
        long pass = rows.stream().filter(r -> "PASS".equals(r[8])).count();
        b.append("## Coverage\n\n| Measure | Value |\n|---|---|\n");
        b.append("| Test cases in scope | ").append(total).append(" |\n");
        b.append("| Test cases with test data (validated field by field) | ").append(withTd).append(" (")
                .append(pct(withTd, total)).append(") |\n");
        b.append("| Test cases without test data (device-only / not generated) | ").append(total - withTd).append(" |\n");
        b.append("| Test data files validated | ").append(legsTotal).append(" |\n");
        b.append("| Result: FAIL / PASS_WITH_REVIEW / PASS | ").append(fail).append(" / ").append(review).append(" / ").append(pass)
                .append(" |\n\n");

        Map<String, long[]> byGroup = new LinkedHashMap<>();
        for (String[] r : rows) {
            long[] g = byGroup.computeIfAbsent(r[11] + " | " + r[12], k -> new long[4]);
            g[0]++;
            g["FAIL".equals(r[8]) ? 1 : "PASS_WITH_REVIEW".equals(r[8]) ? 2 : 3]++;
        }
        b.append("## Results by entry mode and transaction category\n\n| Entry mode | Category | TCs | FAIL | PASS_WITH_REVIEW | PASS / N/A |\n|---|---|---|---|---|---|\n");
        byGroup.forEach((k, g) -> b.append("| ").append(k).append(" | ").append(g[0]).append(" | ").append(g[1]).append(" | ")
                .append(g[2]).append(" | ").append(g[3]).append(" |\n"));

        b.append("\n## Defect frequency (FAIL checks, grouped)\n\n| # | Area | Field | Element | Rule | TCs affected | % of TCs with TD | Occurrences |\n|---|---|---|---|---|---|---|---|\n");
        List<Map.Entry<String, Set<String>>> sorted = new ArrayList<>(defectTcs.entrySet());
        sorted.sort((a, z) -> Integer.compare(z.getValue().size(), a.getValue().size()));
        int n = 1;
        for (Map.Entry<String, Set<String>> e : sorted) {
            String[] p = e.getKey().split(" \\| ", -1);
            b.append("| ").append(n++).append(" | ").append(md(p[0])).append(" | ").append(md(p[1])).append(" | ").append(md(p[2]))
                    .append(" | ").append(md(p[3])).append(" | ").append(e.getValue().size()).append(" | ")
                    .append(pct(e.getValue().size(), withTd)).append(" | ").append(defectHits.get(e.getKey())).append(" |\n");
        }

        b.append("\n## Per test case\n\n| Entry mode | Category | TC | Display | Template | Objective | Legs | PASS | FAIL | REVIEW | N/A | Overall | Report | Key defects |\n");
        b.append("|---|---|---|---|---|---|---|---|---|---|---|---|---|---|\n");
        for (String[] r : rows) {
            b.append("| ").append(r[11]).append(" | ").append(r[12]).append(" | ").append(r[0]).append(" | ").append(md(r[1])).append(" | ")
                    .append(md(r[2])).append(" | ").append(md(r[3])).append(" | ").append(r[13]).append(" | ").append(r[4]).append(" | ")
                    .append(r[5]).append(" | ").append(r[6]).append(" | ").append(r[7]).append(" | **").append(r[8]).append("** | [")
                    .append(r[9]).append("](./").append(r[9]).append(") | ").append(md(r[10])).append(" |\n");
        }
        return b.toString();
    }

    private static String pct(long a, long b) {
        return b == 0 ? "0%" : String.format("%.1f%%", 100.0 * a / b);
    }
}
