package com.coreauth.validator.traceable;

import com.coreauth.validator.rules.Atl105SpecRuleRepository;
import com.coreauth.validator.rules.FieldRule;
import com.coreauth.validator.validation.ValidationResult;

import java.io.IOException;
import java.util.Optional;

/**
 * Checks that a {@link TestScenario} describes a combination the spec actually supports.
 * transactionType is checked against Appendix G (fully transcribed, so this is a hard error).
 * cardType is only shape-checked: Appendix E is not fully transcribed in the KB yet, so an
 * unrecognized cardType is a warning, not an error, to avoid false rejections.
 */
public final class ScenarioValidator {

    private final Atl105SpecRuleRepository specRules;

    private ScenarioValidator(Atl105SpecRuleRepository specRules) {
        this.specRules = specRules;
    }

    public static ScenarioValidator loadDefault() throws IOException {
        return new ScenarioValidator(Atl105SpecRuleRepository.loadDefault());
    }

    public void validate(TestScenario scenario, ValidationResult result) {
        if (scenario == null) {
            result.addError("TestScenario", "test case has no testScenario - cannot verify it exercises a real business scenario");
            return;
        }
        if (scenario.getId() == null || scenario.getId().isEmpty()) {
            result.addError("TestScenario", "testScenario.id is required");
        }
        if (scenario.getClassification() == null
                || !(scenario.getClassification().equals("positive") || scenario.getClassification().equals("negative"))) {
            result.addError("TestScenario", "testScenario.classification must be 'positive' or 'negative', got: "
                    + scenario.getClassification());
        }

        if (scenario.getTransactionType() != null) {
            Optional<FieldRule> rule = specRules.find("*", "*", "PromptCode > TransactionType");
            if (rule.isPresent()) {
                String problem = rule.get().check(scenario.getTransactionType());
                if (problem != null) {
                    result.addError("TestScenario", "transactionType invalid per Appendix G: " + problem);
                }
            }
        }

        if (scenario.getCardType() != null && !scenario.getCardType().matches("^[A-Za-z0-9]{1,4}$")) {
            result.addError("TestScenario", "cardType '" + scenario.getCardType()
                    + "' does not match the expected shape (1-4 alphanumeric chars per Appendix E)");
        } else if (scenario.getCardType() != null) {
            result.addWarning("cardType '" + scenario.getCardType()
                    + "' has the right shape but was not checked against the full Appendix E code list (not yet fully transcribed in the KB)");
        }
    }
}
