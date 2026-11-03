package com.coreauth.validator.validation;

public final class Atl105DecimalPrefix {
    private Atl105DecimalPrefix() { }

    public static boolean isValid(String value) {
        return value != null && value.matches("[0-3][0-9]{1,8}") && value.length() - 1 >= value.charAt(0) - '0';
    }
}
