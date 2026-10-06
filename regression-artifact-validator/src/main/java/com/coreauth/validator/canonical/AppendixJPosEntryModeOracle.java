package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Recognizes Appendix J code values; card, channel, and lifecycle eligibility is separate. */
public final class AppendixJPosEntryModeOracle {
    public enum Outcome {
        PASS,
        INVALID_FORMAT,
        INVALID_PAN_ENTRY_MODE,
        INVALID_TERMINAL_CAPABILITY_MODE
    }

    public record Result(Outcome outcome, String panEntryMode, String terminalCapabilityMode,
                        String contextStatus) {
    }

    private static final Map<String, String> PAN_ENTRY_MODES;
    private static final Map<String, String> TERMINAL_CAPABILITY_MODES;

    static {
        Map<String, String> panModes = new LinkedHashMap<>();
        panModes.put("00", "Unspecified");
        panModes.put("01", "Manual/keyed");
        panModes.put("02", "Magnetic stripe read with partial track data");
        panModes.put("04", "Bar Code Scan / Optical character recognition read for EMV");
        panModes.put("05", "ICC read - CVV data reliable");
        panModes.put("07", "Contactless chip read");
        panModes.put("10", "Credential on file");
        panModes.put("79", "Contact and Contactless chip fallback to manual");
        panModes.put("80", "Chip card capable - unaltered track data read");
        panModes.put("82", "Contactless mobile commerce device");
        panModes.put("86", "Dual interface contactless chip to contact chip");
        panModes.put("90", "Magnetic stripe read with full track data");
        panModes.put("91", "Full contactless magnetic stripe read with track data");
        panModes.put("95", "ICC read - CVV data unreliable");
        PAN_ENTRY_MODES = Collections.unmodifiableMap(panModes);

        Map<String, String> terminalModes = new LinkedHashMap<>();
        terminalModes.put("0", "Unspecified");
        terminalModes.put("1", "PIN entry capability (EMV Contact and Contactless)");
        terminalModes.put("2", "No PIN entry capability (EMV Contact and Contactless)");
        terminalModes.put("3", "mPOS Software-based PIN Entry Capability");
        terminalModes.put("6", "PIN pad inoperative");
        terminalModes.put("8", "Contactless Magnetic Stripe Read Capability");
        TERMINAL_CAPABILITY_MODES = Collections.unmodifiableMap(terminalModes);
    }

    public Map<String, String> panEntryModes() {
        return PAN_ENTRY_MODES;
    }

    public Map<String, String> terminalCapabilityModes() {
        return TERMINAL_CAPABILITY_MODES;
    }

    public Result evaluate(String value) {
        if (value == null || !value.matches("[0-9]{3}")) {
            return new Result(Outcome.INVALID_FORMAT, null, null, "NOT_EVALUATED");
        }

        String panEntryMode = value.substring(0, 2);
        String terminalMode = value.substring(2, 3);
        if (!PAN_ENTRY_MODES.containsKey(panEntryMode)) {
            return new Result(Outcome.INVALID_PAN_ENTRY_MODE, panEntryMode, terminalMode, "NOT_EVALUATED");
        }
        if (!TERMINAL_CAPABILITY_MODES.containsKey(terminalMode)) {
            return new Result(Outcome.INVALID_TERMINAL_CAPABILITY_MODE, panEntryMode, terminalMode, "NOT_EVALUATED");
        }
        return new Result(Outcome.PASS, panEntryMode, terminalMode, "NOT_EVALUATED");
    }
}