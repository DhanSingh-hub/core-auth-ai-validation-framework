package com.coreauth.validator.canonical;

/** Metadata required to identify the source specification for a package. */
public final class PackageManifest {
    private String packageId;
    private String specification;
    private String specificationVersion;

    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }
    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }
    public String getSpecificationVersion() { return specificationVersion; }
    public void setSpecificationVersion(String specificationVersion) { this.specificationVersion = specificationVersion; }
}