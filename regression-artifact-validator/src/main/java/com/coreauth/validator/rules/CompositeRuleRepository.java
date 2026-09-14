package com.coreauth.validator.rules;

import java.util.Optional;

/**
 * Tries each {@link RuleSource} in order and returns the first rule found.
 * Used to let the authoritative spec-derived rules ({@link Atl105SpecRuleRepository}) take
 * precedence over the CSV-observed-data heuristics ({@link Atl105RuleRepository}).
 */
public final class CompositeRuleRepository implements RuleSource {

    private final RuleSource[] sources;

    public CompositeRuleRepository(RuleSource... sources) {
        this.sources = sources;
    }

    @Override
    public Optional<FieldRule> find(String context, String segment, String element) {
        for (RuleSource source : sources) {
            Optional<FieldRule> rule = source.find(context, segment, element);
            if (rule.isPresent()) {
                return rule;
            }
        }
        return Optional.empty();
    }
}
