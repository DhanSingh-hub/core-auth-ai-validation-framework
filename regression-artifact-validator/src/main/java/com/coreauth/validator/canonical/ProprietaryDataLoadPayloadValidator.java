package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/** Validates the Section 11.7.6/11.7.7 Segment 118 request/response envelopes. */
public final class ProprietaryDataLoadPayloadValidator {
    public static final String REQUEST_ROOT = "Proprietary Data Load Request";
    public static final String RESPONSE_ROOT = "Proprietary Data Load Response";
    private static final String SOURCE = "ProprietaryDataLoadPayload";
    private static final Set<String> REQUEST_PROMPTS = Set.of("903", "905");
    private static final Set<String> RESPONSE_PROMPTS = Set.of("901", "902", "904");
    private final ObjectMapper objectMapper;

    public ProprietaryDataLoadPayloadValidator() { this(new ObjectMapper()); }
    ProprietaryDataLoadPayloadValidator(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }
    public ValidationResult validateFile(Path file) {
        ValidationResult result = new ValidationResult(file == null ? "proprietary-data-load-payload" : file.getFileName().toString());
        if (file == null) { result.addError(SOURCE, "AI JSON file is required"); return result; }
        try { validatePayload(objectMapper.readTree(file.toFile()), result); }
        catch (IOException e) { result.addError(SOURCE, "AI JSON is not readable: " + e.getMessage()); }
        return result;
    }
    public ValidationResult validatePayload(JsonNode payload) { ValidationResult r=new ValidationResult("proprietary-data-load-payload"); validatePayload(payload,r); return r; }
    private static void validatePayload(JsonNode p, ValidationResult r) {
        if (p==null||!p.isObject()) { r.addError(SOURCE,"AI JSON root must be an object"); return; }
        boolean req=p.path(REQUEST_ROOT).isObject(), resp=p.path(RESPONSE_ROOT).isObject();
        if(req==resp){r.addError(SOURCE,"AI JSON must contain exactly one Proprietary Data Load Request or Response root");return;}
        if(req) validateRequest(p.path(REQUEST_ROOT),r); else validateResponse(p.path(RESPONSE_ROOT),r);
    }
    private static void validateRequest(JsonNode n,ValidationResult r){
        required(n,"MessageType","ATL105","PDL-REQ-001",r); exact(n,"NumSegments","1","PDL-REQ-002",r);
        if(n.has("Standard Message Data Segment"))r.addError(SOURCE,"Request must not contain Segment 100 (PDL-REQ-003)");
        JsonNode s=n.path("Proprietary Data Load Segment"); if(!s.isObject()){r.addError(SOURCE,"Segment 118 is required in Data Section 3 Field 3 (PDL-REQ-003)");return;}
        String prompt=s.path("PromptCode").asText(null);if(!REQUEST_PROMPTS.contains(prompt))r.addError(SOURCE,"Request PromptCode must be 903 or 905 (PDL-REQ-004)");
        if(!s.has("SegmentType")||!"118".equals(s.path("SegmentType").asText()))r.addError(SOURCE,"SegmentType must be 118 (PDL-REQ-005)");
    }
    private static void validateResponse(JsonNode n,ValidationResult r){
        required(n,"ResponseCode",null,"PDL-RESP-001",r);required(n,"DownloadIndicator",null,"PDL-RESP-001",r);required(n,"InitiationDate",null,"PDL-RESP-001",r);required(n,"InitiationTime",null,"PDL-RESP-001",r);required(n,"SequenceNumber",null,"PDL-RESP-001",r);
        JsonNode s=n.path("Proprietary Data Load Segment");if(!s.isObject()){r.addError(SOURCE,"Segment 118 is required in Data Section 3 Field 6 (PDL-RESP-002)");return;}String prompt=s.path("PromptCode").asText(null);if(!RESPONSE_PROMPTS.contains(prompt))r.addError(SOURCE,"Response PromptCode must be 901, 902 or 904 (PDL-RESP-003)");
    }
    private static void required(JsonNode n,String f,String expected,String rule,ValidationResult r){if(!n.has(f)||n.path(f).isNull()||(expected!=null&&!expected.equals(n.path(f).asText())))r.addError(SOURCE,f+" is invalid or missing ("+rule+")");}
    private static void exact(JsonNode n,String f,String expected,String rule,ValidationResult r){required(n,f,expected,rule,r);}
}
