package com.coreauth.validator.traceable;

/** One reference from a test artifact back to the specification that justifies it. */
public final class SpecRuleReference {
    private String type; // "element" | "appendix" | "combination"
    private Integer elementNumber;
    private String appendixId;
    private String page;
    private String description;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getElementNumber() { return elementNumber; }
    public void setElementNumber(Integer elementNumber) { this.elementNumber = elementNumber; }
    public String getAppendixId() { return appendixId; }
    public void setAppendixId(String appendixId) { this.appendixId = appendixId; }
    public String getPage() { return page; }
    public void setPage(String page) { this.page = page; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
