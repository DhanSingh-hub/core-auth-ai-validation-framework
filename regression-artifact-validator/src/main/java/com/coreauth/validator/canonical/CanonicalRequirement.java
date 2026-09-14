package com.coreauth.validator.canonical;

import java.util.List;

public final class CanonicalRequirement {
    private String id;
    private String title;
    private List<SourceAnchor> sourceAnchors;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<SourceAnchor> getSourceAnchors() { return sourceAnchors; }
    public void setSourceAnchors(List<SourceAnchor> sourceAnchors) { this.sourceAnchors = sourceAnchors; }
}