package com.coreauth.validator.canonical;

import java.util.Locale;

/** Stable semantic identity shared by independently generated artifacts. */
public final class SourceAnchor {
    private String specification;
    private String version;
    private String section;
    private String segment;
    private String element;
    private String rule;

    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public String getSegment() { return segment; }
    public void setSegment(String segment) { this.segment = segment; }
    public String getElement() { return element; }
    public void setElement(String element) { this.element = element; }
    public String getRule() { return rule; }
    public void setRule(String rule) { this.rule = rule; }

    public String canonicalKey() {
        return String.join("|", normalize(specification), normalize(version), normalize(section),
                normalize(segment), normalize(element), normalize(rule));
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}