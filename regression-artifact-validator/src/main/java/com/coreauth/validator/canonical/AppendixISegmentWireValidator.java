package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;

public final class AppendixISegmentWireValidator {
    public AppendixILogicalDataValidator.Result validate(String wire, String network) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        if (wire == null || wire.length() < 16 || wire.length() > 999
                || !wire.startsWith("111\u001c") || wire.charAt(7) != '\u001c'
                || wire.charAt(wire.length() - 1) != '\u001c'
                || wire.chars().filter(c -> c == '\u001c').count() != 3
                || !wire.substring(4, 7).matches("[0-9]{3}")
                || Integer.parseInt(wire.substring(4, 7)) != wire.length()) {
            return fail("APPI-S111-WIRE", "Segment type, declared length or separator placement is invalid.");
        }
        if (wire.chars().anyMatch(c -> c != '\u001c' && (c < 32 || c > 126))) {
            return new AppendixILogicalDataValidator.Result(List.of(new AppendixILogicalDataValidator.Finding(
                    "APPI-S111-ENCODING", AppendixILogicalDataValidator.Status.NOT_ASSERTABLE,
                    "This candidate validator supports printable ASCII data only; other encodings are unassessed.")));
        }
        int offset = 8;
        int end = wire.length() - 1;
        AppendixILogicalDataValidator logical = new AppendixILogicalDataValidator();
        while (offset < end) {
            if (end - offset < 6 || !wire.substring(offset, offset + 6).matches("[0-9]{6}")) {
                return fail("APPI-E111-E112", "Each record requires contiguous three-digit selector and length.");
            }
            String tableId = wire.substring(offset, offset + 3);
            if ("009".equals(tableId)) {
                findings.add(new AppendixILogicalDataValidator.Finding(
                        "SEG111-SME-002", AppendixILogicalDataValidator.Status.REVIEW_REQUIRED,
                        "Table 009's conflicted wire length is not interpreted by this validator."));
                return new AppendixILogicalDataValidator.Result(findings);
            }
            int length = Integer.parseInt(wire.substring(offset + 3, offset + 6));
            if (length < 1 || length > 982 || offset + 6 + length > end) {
                return fail("APPI-E112-E113", "Record data length is zero, exceeds 982, or overruns the segment.");
            }
            findings.addAll(logical.validate(tableId, wire.substring(offset + 6, offset + 6 + length),
                    network).findings());
            offset += 6 + length;
        }
        findings.add(new AppendixILogicalDataValidator.Finding("APPI-S111-WIRE",
                AppendixILogicalDataValidator.Status.PASS,
                "Common segment framing matches section 12.10; full-message and table-context validity are not asserted."));
        return new AppendixILogicalDataValidator.Result(findings);
    }

    private static AppendixILogicalDataValidator.Result fail(String rule, String reason) {
        return new AppendixILogicalDataValidator.Result(List.of(new AppendixILogicalDataValidator.Finding(
                rule, AppendixILogicalDataValidator.Status.FAIL, reason)));
    }
}
