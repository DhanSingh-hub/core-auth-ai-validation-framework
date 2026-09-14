package com.coreauth.validator.traceable;

import java.util.List;

/** Identifies which specification (and which rules within it) a test artifact traces back to. */
public final class SpecReference {
    private String specName;
    private String specVersion;
    private List<SpecRuleReference> rules;

    public String getSpecName() { return specName; }
    public void setSpecName(String specName) { this.specName = specName; }
    public String getSpecVersion() { return specVersion; }
    public void setSpecVersion(String specVersion) { this.specVersion = specVersion; }
    public List<SpecRuleReference> getRules() { return rules; }
    public void setRules(List<SpecRuleReference> rules) { this.rules = rules; }
}
