package com.coreauth.validator.validation;

import java.time.DateTimeException;
import java.time.LocalDate;

/** Two-digit year representation checks, not settlement-window or century assertions. */
public final class Atl105CalendarDate {
    private Atl105CalendarDate() { }

    public static boolean isValidMmddyy(String value) {
        if (value == null || !value.matches("[0-9]{6}")) return false;
        try {
            LocalDate.of(2000 + Integer.parseInt(value.substring(4)),
                    Integer.parseInt(value.substring(0, 2)), Integer.parseInt(value.substring(2, 4)));
            return true;
        } catch (DateTimeException exception) {
            return false;
        }
    }

    public static boolean needsCenturyForLeapDay(String value) {
        return "022900".equals(value);
    }
}
