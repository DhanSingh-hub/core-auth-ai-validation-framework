package com.coreauth.validator.coverage;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Locates source body headings without treating a citation address as a proved rule. */
final class Atl105SourceBodyLocator {
    private static final Pattern ELEMENT = Pattern.compile("Chapter ?13-Element(\\d+)");

    private Atl105SourceBodyLocator() { }

    static Optional<Location> find(String source, String token) {
        String section = token.trim();
        if ("Totals Request".equalsIgnoreCase(section)) section = "11.4.1.1";
        Pattern heading;
        Matcher element = ELEMENT.matcher(section);
        if (element.matches()) {
            heading = Pattern.compile("(?m)^[ \\t]*Number: " + element.group(1) + " Name: [^\\r\\n]+");
        } else if (section.matches("\\d+(?:\\.\\d+)+")) {
            heading = Pattern.compile("(?m)^[ \\t]*" + Pattern.quote(section) + "(?=[ \\t]+)[^\\r\\n]*");
        } else {
            return Optional.empty();
        }
        Matcher matches = heading.matcher(source);
        int start = -1;
        String label = null;
        while (matches.find()) {
            String candidate = matches.group().trim();
            if (candidate.matches(".*\\.{3,}.*") || candidate.matches(".*(?:\\.\\s+){2,}.*")) continue;
            if (start >= 0) return Optional.empty();
            start = matches.start();
            label = candidate;
        }
        if (start < 0) return Optional.empty();
        int line = 1 + (int) source.substring(0, start).chars().filter(character -> character == '\n').count();
        String preview = source.substring(start, Math.min(source.length(), start + 360)).replaceAll("\\s+", " ").trim();
        return Optional.of(new Location(label, line, preview));
    }

    record Location(String heading, int line, String preview) { }
}