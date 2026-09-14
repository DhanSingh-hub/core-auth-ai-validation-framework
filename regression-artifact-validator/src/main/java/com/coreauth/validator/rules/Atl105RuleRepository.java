package com.coreauth.validator.rules;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Loads the ATL105 "Field Values" reference CSV (observed segment/element value
 * population, exported from the BUYPASS ATL105 flow analysis) and derives a
 * {@link FieldRule} per (context, segment, element).
 *
 * <p>Rule derivation heuristic (no formal spec available yet, phase 1):
 * <ul>
 *   <li>Fields with a small closed set of distinct values (e.g. SegmentType, InformationByte,
 *       SegmentLength) become {@code ENUM} rules against the observed values.</li>
 *   <li>Fields with a large/open set of distinct values (e.g. TerminalID, SequenceNumber,
 *       amount fields) become {@code PATTERN} rules: a regex is inferred from the observed
 *       values' length and character class (digits / alphanumeric).</li>
 *   <li>If no consistent shape can be inferred, the field falls back to {@code ANY} (no check).</li>
 *   <li>A field is allowed to be empty if any CSV row recorded an empty Value for it
 *       (typically annotated "empty / not populated" in the Note column).</li>
 * </ul>
 *
 * Now that {@link Atl105SpecRuleRepository} provides authoritative rules derived from the
 * actual ATL105 specification, this repository is used as a fallback (via
 * {@link CompositeRuleRepository}) for fields the spec repository doesn't cover, and as a
 * sanity check against real observed traffic for the fields it does.
 */
public final class Atl105RuleRepository implements RuleSource {

    private static final int ENUM_MAX_DISTINCT = 25;

    private final Map<String, FieldRule> rulesByKey;

    private Atl105RuleRepository(Map<String, FieldRule> rulesByKey) {
        this.rulesByKey = rulesByKey;
    }

    public static Atl105RuleRepository loadFromCsv(Path csvPath)
            throws IOException, com.opencsv.exceptions.CsvValidationException {
        Map<String, List<String>> valuesByKey = new LinkedHashMap<>();
        Map<String, Long> countByKey = new HashMap<>();
        Set<String> emptyAllowedKeys = new HashSet<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvPath.toFile()))) {
            String[] header = reader.readNext(); // Segment,Context,SegmentType,Element,Value,Occurrences,Status,Note
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length < 6) continue;
                String segment = row[0];
                String context = row[1];
                String element = row[3];
                String value = row[4];
                long occurrences = parseLongSafe(row[5]);

                String key = context + "|" + segment + "|" + element;
                if (value == null || value.isEmpty()) {
                    emptyAllowedKeys.add(key);
                    continue;
                }
                valuesByKey.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
                countByKey.merge(key, occurrences, Long::sum);
            }
        }

        Map<String, FieldRule> rules = new HashMap<>();
        for (Map.Entry<String, List<String>> e : valuesByKey.entrySet()) {
            String key = e.getKey();
            String[] parts = key.split("\\|", 3);
            String context = parts[0], segment = parts[1], element = parts[2];
            Set<String> distinct = new LinkedHashSet<>(e.getValue());
            boolean allowEmpty = emptyAllowedKeys.contains(key);

            FieldRule rule;
            if (distinct.size() <= ENUM_MAX_DISTINCT) {
                rule = new FieldRule(context, segment, element, FieldRule.Kind.ENUM,
                        distinct, null, allowEmpty);
            } else {
                Pattern inferred = inferPattern(distinct);
                rule = inferred != null
                        ? new FieldRule(context, segment, element, FieldRule.Kind.PATTERN,
                                Collections.emptySet(), inferred, allowEmpty)
                        : new FieldRule(context, segment, element, FieldRule.Kind.ANY,
                                Collections.emptySet(), null, allowEmpty);
            }
            rules.put(key, rule);
        }

        // Elements that only ever appeared empty still get a rule (empty-only field).
        for (String key : emptyAllowedKeys) {
            rules.computeIfAbsent(key, k -> {
                String[] parts = k.split("\\|", 3);
                return new FieldRule(parts[0], parts[1], parts[2], FieldRule.Kind.ANY,
                        Collections.emptySet(), null, true);
            });
        }

        return new Atl105RuleRepository(rules);
    }

    /** Returns the rule for (context, segment, element), or empty if no reference data exists for it. */
    public Optional<FieldRule> find(String context, String segment, String element) {
        return Optional.ofNullable(rulesByKey.get(context + "|" + segment + "|" + element));
    }

    public int ruleCount() {
        return rulesByKey.size();
    }

    private static long parseLongSafe(String s) {
        try {
            return Long.parseLong(s.trim());
        } catch (Exception ex) {
            return 0L;
        }
    }

    /** Infers a fixed-length numeric or alphanumeric pattern if all sample values share a shape. */
    private static Pattern inferPattern(Collection<String> values) {
        boolean allNumeric = true;
        boolean allAlnum = true;
        int len = -1;
        boolean sameLen = true;

        for (String v : values) {
            if (!v.matches("[0-9]+")) allNumeric = false;
            if (!v.matches("[A-Za-z0-9]+")) allAlnum = false;
            if (len == -1) len = v.length();
            else if (len != v.length()) sameLen = false;
        }

        if (!allAlnum) return null; // too varied (punctuation etc.) - no safe pattern
        String charClass = allNumeric ? "[0-9]" : "[A-Za-z0-9]";
        String quantifier = sameLen ? "{" + len + "}" : "+";
        return Pattern.compile("^" + charClass + quantifier + "$");
    }
}
