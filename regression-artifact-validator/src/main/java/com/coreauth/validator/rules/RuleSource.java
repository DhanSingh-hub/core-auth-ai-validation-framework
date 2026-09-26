package com.coreauth.validator.rules;

import java.util.Optional;

/** Common contract for anything that can answer "what rule applies to this field". */
public interface RuleSource {
    Optional<FieldRule> find(String context, String segment, String element);
}
