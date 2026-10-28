package com.coreauth.validator.canonical;

import java.util.Set;

/** Source-located Appendix I Table IDs; source gaps are kept distinct from invalid selectors. */
public final class AppendixITableCatalog {
    public enum Status {
        SOURCE_LOCATED,
        SOURCE_UNLOCATED,
        UNLISTED,
        INVALID_FORMAT
    }

    private static final Set<String> SOURCE_LOCATED = Set.of(
            "001", "002", "003", "004", "005", "006", "007", "008", "009", "010", "011", "012",
            "013", "014", "015", "016", "017", "018", "019", "020", "021", "022", "024", "025",
            "026", "027", "028", "029", "030", "031", "032", "033", "034", "035", "036", "037",
            "038", "039", "040", "041", "042", "043", "044", "045", "046", "047", "048", "049",
            "050", "051", "052", "053", "054", "055", "056", "057", "058", "059", "060", "062",
            "063", "064", "065", "066", "067", "068", "069", "070", "071", "072", "073", "075",
            "076", "077", "078", "079", "080", "081");
    private static final Set<String> SOURCE_UNLOCATED = Set.of("023", "061", "074");

    public Status classify(String tableId) {
        if (tableId == null || !tableId.matches("[0-9]{3}")) return Status.INVALID_FORMAT;
        if (SOURCE_LOCATED.contains(tableId)) return Status.SOURCE_LOCATED;
        if (SOURCE_UNLOCATED.contains(tableId)) return Status.SOURCE_UNLOCATED;
        return Status.UNLISTED;
    }

    public Set<String> sourceLocatedIds() {
        return SOURCE_LOCATED;
    }

    public Set<String> sourceUnlocatedIds() {
        return SOURCE_UNLOCATED;
    }
}