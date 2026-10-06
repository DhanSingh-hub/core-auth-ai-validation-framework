package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class AppendixILogicalDataValidator {
    public enum Status {
        PASS, FAIL, REVIEW_REQUIRED, NOT_ASSERTABLE
    }

    public record Finding(String ruleId, Status status, String reason) {
    }

    public record Result(List<Finding> findings) {
        public Result {
            findings = List.copyOf(findings);
        }

        public boolean representationValid() {
            return !findings.isEmpty() && findings.stream().allMatch(finding -> finding.status() == Status.PASS);
        }
    }

    private static final Set<String> PROGRAMS = Set.of("CHG", "CIT", "ECA", "IDT", "INC", "KWS",
            "PPL", "PWC", "SP2", "SPK", "SPP", "STB", "SVS", "VAL", "WIC", "WUG");
    private static final Set<String> PAN_MODES = Set.of("00", "01", "02", "04", "05", "07",
            "10", "79", "80", "82", "86", "90", "91", "95");
    private static final Set<Character> PIN_MODES = Set.of('0', '1', '2', '3', '6', '8');

    public Result validate(String tableId, String data, String network) {
        List<Finding> findings = new ArrayList<>();
        if (tableId == null || !tableId.matches("[0-9]{3}")) {
            return result("APPI-SELECTOR", Status.FAIL, "Selector must contain three decimal digits.");
        }
        if (!Set.of("001", "002", "003", "004", "005", "006", "007", "008", "009").contains(tableId)) {
            return result("APPI-T" + tableId, Status.NOT_ASSERTABLE,
                    "This logical validator has no implemented predicate for the selected table.");
        }
        if (data == null) {
            return result("APPI-T" + tableId, Status.FAIL, "Required table data is absent.");
        }
        if (data.isEmpty()) {
            return result("APPI-E112-LENGTH", Status.FAIL,
                    "An included variable-information record cannot declare a zero data length "
                            + "under Element 112's 001-999 range; no table-specific minimum is inferred.");
        }
        switch (tableId) {
            case "001" -> check(findings, "APPI-T001-R001",
                    data.matches("[0-9]{0,35}"), "Numeric UPC representation within 35 bytes.",
                    "UPC data must be numeric data within the source maximum.");
            case "002" -> {
                check(findings, "APPI-T002-R001", PROGRAMS.contains(data),
                        "Three-byte program code is source-listed.", "Program code is not source-listed.");
                if ("WUG".equals(data)) {
                    findings.add(new Finding("APPI-T002-R001", Status.REVIEW_REQUIRED,
                            "The source reserves this code; operational eligibility is not established."));
                }
            }
            case "003" -> {
                boolean postal = data.length() >= 9
                        && (data.substring(0, 9).matches("[A-Za-z0-9]{9}")
                        || data.substring(0, 9).matches("[A-Za-z0-9]{5} {4}"));
                check(findings, "APPI-T003-R001", postal && data.length() <= 29,
                        "Postal prefix and optional street suffix fit the layout; ZIP allocation is not checked.",
                        "Postal prefix or street width is invalid.");
            }
            case "004" -> {
                if (network == null || !Set.of("AMEX", "VISA", "MASTERCARD", "DISCOVER").contains(network)) {
                    findings.add(new Finding("APPI-T004-R001", Status.REVIEW_REQUIRED,
                            "A supported canonical network context is required for the width assertion."));
                } else {
                    int width = "AMEX".equals(network) ? 4 : 3;
                    check(findings, "APPI-T004-R001", data.matches("[0-9]{" + width + "}"),
                            "Verification-data width fits the declared network.",
                            "Verification-data type or network-specific width is invalid.");
                }
            }
            case "005" -> check(findings, "APPI-T005-R001",
                    data.length() == 3 && PAN_MODES.contains(data.substring(0, 2))
                            && PIN_MODES.contains(data.charAt(2)),
                    "POS entry components are in Appendix J's enumerations.",
                    "POS entry width or component code is invalid.");
            case "006" -> check(findings, "APPI-T006-R001", data.matches("[0-9]{3}"),
                    "Currency representation has three decimal digits.",
                    "Currency representation must have three decimal digits.");
            case "007" -> check(findings, "APPI-T007-R001", data.matches("[0-9]{4,6}"),
                    "SIC representation has four to six decimal digits.",
                    "SIC type or width is invalid.");
            case "008" -> check(findings, "APPI-T008-R001", data.matches("[0-9]{0,19}"),
                    "Account representation is numeric and within 19 bytes.",
                    "Account type or width is invalid.");
            case "009" -> {
                check(findings, "APPI-T009-R001", Set.of("0", "1", "5").contains(data),
                        "Logical partial-approval value is source-listed.",
                        "Logical partial-approval data must be one of the three source-listed values.");
                findings.add(new Finding("SEG111-SME-002", Status.REVIEW_REQUIRED,
                        "Table Length wire representation is unresolved; no serialized verdict is issued."));
            }
            default -> throw new IllegalStateException("Unhandled in-scope table");
        }
        return new Result(findings);
    }

    private static void check(List<Finding> findings, String ruleId, boolean valid,
                              String positive, String negative) {
        findings.add(new Finding(ruleId, valid ? Status.PASS : Status.FAIL, valid ? positive : negative));
    }

    private static Result result(String ruleId, Status status, String reason) {
        return new Result(List.of(new Finding(ruleId, status, reason)));
    }
}
