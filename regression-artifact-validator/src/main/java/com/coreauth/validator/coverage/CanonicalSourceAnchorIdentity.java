package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.SourceAnchor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

final class CanonicalSourceAnchorIdentity {
    private CanonicalSourceAnchorIdentity() { }

    static String key(ObjectMapper mapper, JsonNode anchor) throws IOException {
        ObjectNode identity = mapper.createObjectNode();
        for (String field : new String[]{"specification", "version", "section", "segment", "element", "rule"}) {
            if (anchor.has(field)) identity.set(field, anchor.get(field));
        }
        return mapper.treeToValue(identity, SourceAnchor.class).canonicalKey();
    }
}
