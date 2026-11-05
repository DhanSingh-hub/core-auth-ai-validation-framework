package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.TestDataSpecFieldValidationRunner.Check;
import com.coreauth.validator.canonical.TestDataSpecFieldValidationRunner.Verdict;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Turns failing and review checks into a plain-language "what is wrong / what it should be" section,
 * with the test data file and line number of each defect.
 */
final class PlainLanguageExplainer {

    private static final String SEG100 = "Standard Message Data Segment";
    private static final String SEG111 = "Variable Information Data Segment";
    private static final String SEG130 = "EMV Request Data Segment";
    private static final String SEG105 = "Totals Data Segment";
    private static final Pattern STEP = Pattern.compile("step (\\d+)");

    /** One defect explained for a reader who does not know the validator's rule IDs. */
    record Item(String group, int priority, int leg, List<String> path, String title, String generated,
                String problem, String shouldBe, String spec) {
    }

    private final String runYymm;

    PlainLanguageExplainer(String runYymm) {
        this.runYymm = runYymm;
    }

    String render(JsonNode tc, List<Path> files, List<List<Check>> legChecks, List<Check> caseChecks) {
        Map<String, Item> items = new LinkedHashMap<>();
        for (int i = 0; i < legChecks.size(); i++) {
            for (Check c : legChecks.get(i)) {
                add(items, c, i, tc);
            }
        }
        for (Check c : caseChecks) {
            Matcher arrow = Pattern.compile("-> step (\\d+)").matcher(c.area());
            Matcher m = STEP.matcher(c.area());
            int leg = arrow.find() ? Integer.parseInt(arrow.group(1)) - 1 : m.find() ? Integer.parseInt(m.group(1)) - 1 : 0;
            add(items, c, leg, tc);
        }

        StringBuilder b = new StringBuilder();
        b.append("\n## What is wrong in this test case and how to fix it\n\n");
        if (items.isEmpty()) {
            b.append("**No defects.** The generated test data matches the ATL105 specification for every field that can be "
                    + "checked from the spec. Only the confirmation items below remain.\n");
        } else {
            b.append("**").append(items.size()).append(" defect(s) to fix.** Each one shows where it is in the test data, what the "
                    + "AI generated, why that is wrong, and the value that should be there.\n");
            int n = 1;
            for (Item it : items.values()) {
                b.append("\n### Defect ").append(n++).append(" – ").append(it.title()).append("\n\n");
                b.append("| | |\n|---|---|\n");
                b.append("| **Where** | ").append(where(files, it)).append(" |\n");
                b.append("| **What the AI generated** | ").append(cell(it.generated())).append(" |\n");
                b.append("| **What is wrong** | ").append(cell(it.problem())).append(" |\n");
                b.append("| **What it should be** | ").append(cell(it.shouldBe())).append(" |\n");
                b.append("| **Spec reference** | ").append(cell(it.spec())).append(" |\n");
            }
        }
        b.append(reviewSection(legChecks, caseChecks));
        return b.toString();
    }

    // ------------------------------------------------------------------ defects

    private void add(Map<String, Item> items, Check c, int leg, JsonNode tc) {
        if (c.verdict() != Verdict.FAIL || "TC<->TD".equals(c.area())) {
            return;
        }
        Item it = explain(c, leg, tc);
        String key = it.group() + "#" + it.leg();
        Item old = items.get(key);
        if (old == null || it.priority() > old.priority()) {
            items.put(key, it);
        }
    }

    private Item explain(Check c, int leg, JsonNode tc) {
        String v = c.value() == null ? "" : c.value();
        String entryMode = tc == null ? "" : tc.path("dimensions").path("entry_mode").asText("");
        String objective = tc == null ? "" : tc.path("test_objective").asText("");
        switch (c.rule()) {
            case "SEG130-R-006/E188" -> {
                String fixed = v.matches("[0-9]{1,2}") ? String.format("%03d", Integer.parseInt(v)) : "0nn";
                return new Item("E188", 1, leg, List.of(SEG130, "EMVCardSequenceNumber"),
                        "EMV Card Sequence Number is " + v.length() + " characters instead of 3",
                        "`" + v + "`",
                        "This field must always be exactly 3 characters, padded with zeros on the left. The AI copied the chip's "
                                + "card sequence number (Tag 5F34 = `" + v + "`) without padding it. The number itself is right; "
                                + "only the width is wrong, so every field after it is shifted by one position.",
                        "`" + fixed + "`. After the fix, add 1 to the Segment 130 `SegmentLength`.",
                        "ATL105 Ch. 13 Element 188 (p.470): *\"Fixed length of three bytes, right-aligned and padded with zeroes … "
                                + "Valid Codes/Values: 000—099\"*");
            }
            case "BR-SEG100-APPT-TABLE-ID-MATCH", "BR-SEG100-APPT-EMV-TABLE-DATA", "SEG130-R-020/BR-SEG100-APPT-EMV-TABLE-DATA" -> {
                boolean main = c.rule().startsWith("SEG130-R-020");
                return new Item("E118", main ? 2 : 1, leg, List.of(SEG130, "EMVAdditionalInformation"),
                        "EMV Additional Information contains random text instead of `EMVYES` / `EMVNOT`",
                        main ? "`" + v + "`" : "984 random letters/digits",
                        "`EMVAdditionalInformationIndicator` = `001` tells the host that this field holds *EMV Table Data*. For "
                                + "that table only two values exist: `EMVYES` or `EMVNOT` (6 characters). The AI ignored the allowed "
                                + "values and filled the field to its maximum size (984) with random characters, which the host "
                                + "cannot interpret.",
                        "`EMVAdditionalInformation` = `EMVYES` (or `EMVNOT`), `EMVAdditionalInformationLength` = `006`, and the "
                                + "Segment 130 `SegmentLength` recomputed. (Whether a Table ID/Table Length prefix is also needed "
                                + "is an open SME question – see the findings summary Q-01.)",
                        "Element 191: `001` = EMV table data → Element 118: *\"EMV table data is included here … refer to "
                                + "Appendix T\"* → Appendix T-1 (p.694): EMV Table Data request values **EMVYES / EMVNOT**");
            }
            case "T2-EXPIRED-EMV" -> {
                return new Item("EXPIRED", 1, leg, List.of(SEG100, "CardDiscretionaryBlockData"),
                        "\"Expired card\" test uses a card that is not expired",
                        "Expiry `" + v + "` (YYMM) = " + yymm(v),
                        "The purpose of this test is to prove that an **expired** chip card is still sent online and the host "
                                + "decides. The card expiry in the Track 2 data (first 4 digits of `CardDiscretionaryBlockData`) is "
                                + yymm(v) + ", which is in the future, so the card is valid and the test cannot prove what it "
                                + "claims.",
                        "A card whose Track 2 expiry is before " + yymm(runYymm) + " (for example `2510…` = October 2025). The "
                                + "chip data should also contain Tag 5F24 (expiry date) with the same date, and the TVR should flag "
                                + "'expired application'. The test case lists 'Expired EMV test card' as an open item, so use the "
                                + "client's expired test card.",
                        "ATL105 p.160 (device sends expired EMV cards online); Ch. 13 Element 12: *\"merchants must send a value "
                                + "even for expired cards\"*");
            }
            case "T3-OFFLINE-PIN-CVM" -> {
                return new Item("OFFLINE-PIN", 1, leg, List.of(SEG130, "EMVChipData"),
                        "\"Offline PIN\" test shows a signature, not a PIN",
                        "Tag 9F34 (CVM Results) = `" + v + "`",
                        "The test is for *EMV offline PIN*, but CVM Results `" + v + "` means the cardholder **signed** (CVM "
                                + "code 1E = signature). Nothing in the data shows that a PIN was verified, so this is just a normal "
                                + "signature purchase.",
                        "Tag 9F34 with CVM code 01 or 04 (PIN verified offline by the chip), e.g. `410302` or `440302`. "
                                + "`EncryptedPINBlockData` stays empty because an offline PIN is never sent to the host.",
                        "EMV Book 3 Annex C3 (CVM codes); ATL105 Element 33 / p.162");
            }
            case "T3-ONLINE-PIN-E33", "T3-ONLINE-PIN-CVM" -> {
                return new Item("ONLINE-PIN", "T3-ONLINE-PIN-E33".equals(c.rule()) ? 2 : 1, leg,
                        List.of(SEG100, "EncryptedPINBlockData"),
                        "\"Online PIN\" test sends no PIN",
                        "`EncryptedPINBlockData` empty and/or CVM = signature (`" + v + "`)",
                        "The test is for *EMV online PIN*, which means the PIN block must be sent to the host. The data has no "
                                + "PIN block, and the chip says the cardholder signed.",
                        "`EncryptedPINBlockData` = DUKPT KSN + PIN block (36 characters), and Tag 9F34 with CVM code 02 (online "
                                + "PIN). If the test PIN card/key is not available yet, mark the test 'blocked – needs client input'.",
                        "ATL105 Ch. 12.1 F8 / Ch. 13 Element 33; EMV Book 3 Annex C3");
            }
            case "SEG105-E13-WIDTH" -> {
                String label = v.replace("'", "").trim();
                return new Item("E13", 1, leg, List.of(SEG105, "CardLabel"),
                        "Totals Card Label is " + label.length() + " characters instead of 4",
                        "`" + label + "`",
                        "The Card Label must always be 4 characters. `" + label + "` is the right label (American Express "
                                + "bucket = TE, Mastercard/Visa = CC) but has only " + label.length() + " characters, so the count "
                                + "and amount fields after it are shifted.",
                        "`" + String.format("%-4s", label) + "` (the label followed by spaces up to 4 characters)",
                        "ATL105 Ch. 13 Element 13 (p.365): fixed length of four alphanumeric characters, left-aligned, "
                                + "space-filled");
            }
            case "APPJ-KEYED-NO-TRACK", "APPJ-FALLBACK-79-80" -> {
                String[] parts = v.split(" / ");
                String code = parts[0];
                String track = parts.length > 1 ? parts[1] : "";
                String mmyy = track.length() >= 4 ? track.substring(2, 4) + track.substring(0, 2) : "MMYY";
                int shrink = Math.max(0, track.length() - 4);
                boolean fallback = "EMV_FALLBACK".equals(entryMode);
                boolean keyedObjective = objective.toLowerCase(Locale.ROOT).contains("keyed");
                String explainTrack = "`" + track + "` = expiry `" + (track.length() >= 4 ? track.substring(0, 4) : "") + "` + service code `"
                        + (track.length() >= 7 ? track.substring(4, 7) : "") + "` + discretionary data";
                String should = fallback && !keyedObjective
                        ? "Change Table 005 to **`80" + code.substring(2) + "`** (chip card – unaltered track data read, used "
                        + "for EMV fallback to **swipe**) and keep the Track 2 data. "
                        + "Alternatively, if the test really means a keyed fallback, keep `" + code + "` and set "
                        + "`CardDiscretionaryBlockData` = `" + mmyy + "` (MMYY), with Segment 100 `SegmentLength` reduced by " + shrink + "."
                        : "Keep Table 005 `" + code + "` and set `CardDiscretionaryBlockData` to the keyed expiry date only, in "
                        + "MMYY format: **`" + mmyy + "`**. Segment 100 `SegmentLength` then becomes " + shrink + " smaller.";
                return new Item("KEYED-TRACK", "APPJ-FALLBACK-79-80".equals(c.rule()) ? 2 : 1, leg,
                        List.of(SEG111, "\"005\"", "VariableInformation\""),
                        fallback ? "Chip-fallback entry mode `79` (\"fallback to manual\") sent with swiped card data"
                                : "Keyed entry mode sent with swiped card data",
                        "Table 005 (POS entry mode) = `" + code + "`, `CardDiscretionaryBlockData` = " + explainTrack,
                        "The first two digits of Table 005, `" + code.substring(0, 2) + "`, tell the host the card number was "
                                + "**typed in by hand**" + (fallback ? " after the chip could not be read" : "") + ". But "
                                + "`CardDiscretionaryBlockData` holds full Track 2 data (service code and discretionary data), "
                                + "which exists only when the card is **swiped**. The two fields contradict each other, so the "
                                + "host receives an impossible combination.",
                        should,
                        "ATL105 Appendix J-1 (p.631): `79` = *\"Contact and Contactless chip fallback to manual\"*; `80` = "
                                + "*\"Chip card capable – unaltered track data read (used for contact EMV fall back to swiped)\"*. "
                                + "Ch. 13 Element 12 (p.13-32): *\"For a card whose number has been manually entered, the "
                                + "expiration date is in MMYY format\"*");
            }
            case "FOLLOW-ON-SAME-EXPIRY", "E12-KEYED-EXPIRY-MMYY" -> {
                boolean flow = "FOLLOW-ON-SAME-EXPIRY".equals(c.rule());
                String[] parts = v.split(" / ");
                String original = flow && parts.length > 1 ? parts[0].trim() : null;
                String follow = flow && parts.length > 1 ? parts[1].trim() : v;
                String problem;
                String should;
                if (original != null) {
                    problem = "The original purchase sent the expiry as `" + original + "`. This follow-on (reversal) must send "
                            + "**the same card details** so the host can match it to the original, but it sends `" + follow + "`"
                            + (original.length() == 4 && follow.equals(original.substring(2) + original.substring(0, 2))
                            ? " – the same date with month and year swapped (MMYY → YYMM). Read in the keyed MMYY format, `"
                            + follow + "` would be month " + follow.substring(0, 2) + ", which does not exist." : ".");
                    should = "**`" + original + "`** – exactly the value used in the original purchase.";
                } else {
                    String mmyy = follow.length() == 4 ? follow.substring(2) + follow.substring(0, 2) : "MMYY";
                    problem = "This leg uses a keyed POS entry mode (Table 005 `01`, `10` or `79`), so its expiry must be "
                            + "MMYY. `" + follow + "` is in YYMM order (month " + follow.substring(Math.min(2, follow.length()))
                            + " is not a valid month when read as MMYY). If the original purchase also has a defect, this is a "
                            + "knock-on effect of it.";
                    should = "Use the same expiry as the original in the format that matches the entry mode: keyed → **`" + mmyy
                            + "`** (MMYY). If the original is changed to a swiped entry mode (e.g. `80`/`90`), keep `" + follow
                            + "` and use the same entry mode as the original.";
                }
                return new Item("FOLLOW-EXPIRY", flow ? 2 : 1, leg, List.of(SEG100, "CardDiscretionaryBlockData"),
                        "Follow-on leg sends the expiry date in a different format from the original",
                        "`CardDiscretionaryBlockData` = `" + follow + "`" + (original != null ? " (original purchase: `" + original + "`)" : ""),
                        problem, should,
                        "ATL105 Ch. 13 Element 12 (p.13-32): keyed expiry is MMYY; *\"In the event of a 'Retry' of a "
                                + "'Follow-on' transaction (Reversals, … Timeout reversals) … the merchant should use the same "
                                + "credentials … as were used in the initial attempt\"*");
            }
            case "SPLIT-TENDER-LENGTH", "E84-SEG100-RUNTIME-WIDTH" -> {
                boolean flow = "SPLIT-TENDER-LENGTH".equals(c.rule());
                String declared = v.replaceAll("\\s.*", "");
                Matcher range = Pattern.compile("must be (\\d+)-(\\d+)").matcher(c.detail());
                Matcher prov = Pattern.compile("provisional amount '([0-9]+)'").matcher(c.detail());
                String provisional = prov.find() ? prov.group(1) : null;
                String should = range.find()
                        ? "Calculate `SegmentLength` **after** the real remainder is filled in. For a remainder of 1–4 digits it is "
                        + "between **`" + String.format("%03d", Integer.parseInt(range.group(1))) + "`** and **`"
                        + String.format("%03d", Integer.parseInt(range.group(2))) + "`**. Also change the provisional amount in "
                        + "the test case to a value smaller than the original amount."
                        : "Calculate `SegmentLength` after the real remainder is filled in, using a valid amount.";
                return new Item("SPLIT-LENGTH", flow ? 2 : 1, leg, List.of(SEG100, "SegmentLength"),
                        "Second card's message length was calculated with an impossible amount",
                        "`SegmentLength` = `" + declared + "`" + (provisional != null ? "; amount used for the calculation = `"
                                + provisional + "` (" + money(provisional) + ")" : ""),
                        "In a split-tender sale, card A pays part of the bill and card B pays **the rest**. Card B's amount is "
                                + "correctly left as a run-time value (`<RemainingBalance …>`). But the AI calculated card B's "
                                + "`SegmentLength` (the character count of Segment 100) using a made-up 8-digit amount"
                                + (provisional != null ? " `" + provisional + "` (" + money(provisional) + ")" : "")
                                + " from the test case. What is left of the original bill can never be that large, so this length "
                                + "can never be right.",
                        should,
                        "ATL105 Ch. 12.1 F2 (Segment Length = sum of the fields + separators); p.84 (remaining balance = "
                                + "requested − approved partial amount); Ch. 13 Element 58");
            }
            case "SPLIT-TENDER-9F02" -> {
                return chipAmount(leg, v);
            }
            case "APPJ-CAP-8-HIERARCHY" -> {
                String code = v.split(" ")[0];
                return new Item("CAP8", 1, leg, List.of(SEG111, "\"005\"", "VariableInformation\""),
                        "Terminal capability `8` used on a chip-capable terminal",
                        "Table 005 = `" + code + "`, Table 031 = `12`",
                        "The third digit of Table 005, `8`, means \"this terminal has only a contactless magnetic-stripe reader "
                                + "and **no chip capability**\". But Table 031 = `12` in the same message says the terminal reads "
                                + "contact **and** contactless chips. For a chip-capable terminal the spec requires `1`, not `8`.",
                        "**`" + code.substring(0, 2) + "1`** (keep Table 031 = `12`). If the test really needs a non-chip RFID "
                                + "terminal, keep `" + code + "` and set Table 031 to that terminal's capability instead.",
                        "ATL105 Appendix J-2 (p.632) footnote 1: *\"code 8 … if a Contactless MSR is attached and it has no "
                                + "chip capability … For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 "
                                + "and not 8\"*");
            }
            default -> {
                if (c.field().startsWith("Tag 9F02 vs NonfuelAmount")) {
                    return chipAmount(leg, v.replaceAll(" vs .*", ""));
                }
                return new Item(c.rule() + "|" + c.field(), 1, leg, List.of(), c.area() + " – " + c.field(),
                        "`" + v + "`", c.detail(), "See the spec reference.", c.source());
            }
        }
    }

    private Item chipAmount(int leg, String value) {
        return new Item("SPLIT-9F02", 1, leg, List.of(SEG130, "EMVChipData"),
                "Second card's chip data still shows the full original amount",
                "Tag 9F02 (Amount Authorized) = `" + value + "` (" + money(value) + ")",
                "Card B pays only what is left of the bill, but its chip data says it is paying " + money(value) + " – the "
                        + "full amount, copied from card A. The chip amount and the message amount will not match.",
                "Tag 9F02 = card B's remainder as 12 digits (e.g. `000000000500` for $5.00), generated after the remainder "
                        + "is known. The chip data length fields must be updated with it.",
                "SEG130-R-009 (chip amount = request amount); ATL105 p.84 (split tender remainder)");
    }

    // ------------------------------------------------------------------ review items

    private static String[] reviewText(Check c) {
        return switch (c.rule()) {
            case "SEG100-E102-ASSIGNED" -> new String[] {"Terminal ID / merchant number",
                    "The value is a placeholder. BUYPASS assigns the real device type, merchant and device number.",
                    "Client merchant list"};
            case "SEG130-R-015" -> new String[] {"CA Public Key File Checksum",
                    "This is a sample value. It must equal the checksum of the CA key file currently loaded at the host.",
                    "Host / key management team"};
            case "BR-SEG100-APPR-PACKED-PREFIX-BOUNDARY" -> new String[] {"Chip data length prefix",
                    "The 2-byte length prefix at the start of EMVChipData matches the spec example, but the spec does not "
                            + "state explicitly that it is counted in EMVChipDataLength.", "SME"};
            case "BR-SEG100-APPR-CROSS-FIELD" -> new String[] {"Chip tags vs Segment 100 values",
                    "Appendix R does not state this rule. The important pairs (amount, transaction type, currency) are "
                            + "checked separately in this report.", "Informational"};
            case "SEG130-R-010" -> new String[] {"Cryptogram, ATC, unpredictable number, TVR",
                    "These are dummy chip values. A real card or card simulator must produce them.", "Card simulator"};
            case "BR-SEG100-APPT-TABLE-LENGTH-FIELD" -> new String[] {"Table Length inside EMV Additional Information",
                    "Appendix T does not say how many digits this field has.", "BUYPASS / SME"};
            case "BR-SEG100-APPT-ECHO" -> new String[] {"Echo of EMV table data",
                    "The value must be echoed in later messages. This can only be checked across a full run.", "Execution"};
            case "EMV-TVR-CVM-CONSISTENCY" -> new String[] {"TVR says \"online PIN entered\" but no PIN is sent",
                    "The TVR is a dummy value. Once simulated, it must agree with the cardholder verification method.",
                    "Card simulator"};
            case "EMV-TVR-EXPIRED" -> new String[] {"TVR does not flag the expired card",
                    "The TVR is a dummy value. For an expired card it must set 'expired application'.", "Card simulator"};
            case "SEG100-E5-FOLLOW-ON", "carry_response_field" -> new String[] {"Value taken from the previous response",
                    "Correctly left as a run-time value (e.g. Approval Number from step 1). It can only be checked during "
                            + "execution.", "Execution"};
            case "SEG100-E58-RUNTIME" -> new String[] {"Amount filled at run time",
                    "Correct approach. The real amount must fit the Element 58 length limit.", "Execution"};
            case "SPLIT-TENDER-REMAINDER" -> new String[] {"Split-tender remainder",
                    "The run-time formula is right, but the sample amount in the test case is not a valid remainder.",
                    "AI team (fix the test case sample)"};
            case "E84-SEG100-LENGTH" -> new String[] {"Segment 100 length with a run-time value",
                    "Cannot be confirmed until the run-time value is filled in.", "Execution"};
            case "E84-SEG100-RUNTIME-WIDTH" -> new String[] {"Length reserved for a run-time value",
                    "The declared length leaves room for a run-time value of a plausible size.", "Execution"};
            case "SEG105-E65" -> new String[] {"Totals password empty",
                    "Element 65 says a password is required for totals; the layout marks it optional.",
                    "Merchant profile / SME"};
            case "SEG105-DEVICE-VERSIONS" -> new String[] {"Hardware/software/firmware versions",
                    "The format is right, but the values are synthesized. They must be the certified device's real versions.",
                    "Device team"};
            case "E84-SEG105-LENGTH" -> new String[] {"Totals segment length",
                    "Matches only with a trailing separator and a 4-character card label.", "SME"};
            case "APPJ-82-PROFILE" -> new String[] {"Contactless mobile (entry mode 82) data",
                    "Sent as a swipe-style request without chip data. ATL105 does not define the Amex mobile-wallet data; "
                            + "the test case is pending SME.", "SME"};
            case "EMV-CONTACTLESS-PROFILE" -> new String[] {"Contactless chip data",
                    "The chip data is identical to the contact-chip template (no contactless-kernel tags, signature CVM).",
                    "Contactless kernel trace"};
            case "T9-AVS-DATA" -> new String[] {"AVS data missing",
                    "AVS test without address-verification data. The test case is waiting for the client's test "
                            + "card/address.", "Client"};
            default -> new String[] {c.field(), c.detail(), "SME"};
        };
    }

    private static String reviewSection(List<List<Check>> legChecks, List<Check> caseChecks) {
        Map<String, String[]> rows = new LinkedHashMap<>();
        List<Check> all = new ArrayList<>();
        legChecks.forEach(all::addAll);
        all.addAll(caseChecks);
        for (Check c : all) {
            if (c.verdict() != Verdict.REVIEW) {
                continue;
            }
            if ("TC<->TD".equals(c.area())) {
                rows.putIfAbsent("TC<->TD", new String[] {"Run-time placeholders in the test data",
                        "The test case holds a sample value; the test data correctly uses a run-time placeholder.", "Execution"});
                continue;
            }
            rows.putIfAbsent(c.rule(), reviewText(c));
        }
        StringBuilder b = new StringBuilder("\n### Items to confirm (not defects)\n\n");
        if (rows.isEmpty()) {
            return b.append("None.\n").toString();
        }
        b.append("These cannot be decided from the specification alone. They do not make the test data wrong.\n\n");
        b.append("| Item | Why it is open | Who confirms |\n|---|---|---|\n");
        rows.values().forEach(r -> b.append("| ").append(cell(r[0])).append(" | ").append(cell(r[1])).append(" | ")
                .append(cell(r[2])).append(" |\n"));
        return b.toString();
    }

    // ------------------------------------------------------------------ helpers

    private static String where(List<Path> files, Item it) {
        if (it.leg() < 0 || it.leg() >= files.size()) {
            return "Test case level";
        }
        Path file = files.get(it.leg());
        String jsonPath = it.path().isEmpty() ? "" : " → " + String.join(" → ",
                it.path().stream().map(p -> "\"005\"".equals(p) ? "Table 005 entry" : "`" + p.replace("\"", "") + "`").toList());
        int line = lineOf(file, it.path());
        return "`" + file.getFileName() + "`" + (line > 0 ? " line " + line : "") + jsonPath;
    }

    /** Finds each needle in order, starting after the previous one, and returns the 1-based line of the last. */
    private static int lineOf(Path file, List<String> needles) {
        if (needles.isEmpty() || !Files.exists(file)) {
            return -1;
        }
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            int from = 0;
            int found = -1;
            for (String needle : needles) {
                String n = needle.startsWith("\"") || needle.endsWith("\"") ? needle : "\"" + needle + "\"";
                found = -1;
                for (int i = from; i < lines.size(); i++) {
                    if (lines.get(i).contains(n)) {
                        found = i;
                        break;
                    }
                }
                if (found < 0) {
                    return -1;
                }
                from = found + 1;
            }
            return found + 1;
        } catch (IOException e) {
            return -1;
        }
    }

    private static String yymm(String v) {
        if (v == null || !v.matches("[0-9]{4}")) {
            return v;
        }
        int m = Integer.parseInt(v.substring(2));
        return m >= 1 && m <= 12 ? Month.of(m).getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " 20" + v.substring(0, 2) : v;
    }

    private static String money(String digits) {
        if (digits == null || !digits.matches("[0-9]+")) {
            return digits;
        }
        long cents = Long.parseLong(digits);
        return String.format(Locale.US, "$%,d.%02d", cents / 100, cents % 100);
    }

    private static String cell(String s) {
        return s == null ? "" : s.replace("|", "\\|").replace("\n", " ");
    }
}
