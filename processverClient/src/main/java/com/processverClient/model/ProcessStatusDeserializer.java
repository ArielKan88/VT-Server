package com.processverClient.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.processver.model.ProcessStatus;
import com.processver.model.VirusScanInfo;

import java.io.IOException;
import java.util.*;

public class ProcessStatusDeserializer extends JsonDeserializer<ProcessStatus> {
    @Override
    public ProcessStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        ObjectMapper mapper =  new ObjectMapper();
        final Map<String, VirusScanInfo> scans = mapper.convertValue(node.get("scans"), Map.class);

        final String scan_id = node.get("scan_id").asText();
        final String sha1 = node.get("sha1").asText();
        final String resource = node.get("resource").asText();
        final Integer response_code = node.get("response_code").asInt();
        final String scan_date = node.get("scan_date").asText();
        final String permalink = node.get("permalink").asText();
        final String verbose_msg = node.get("verbose_msg").asText();
        final Integer total = node.get("total").asInt();
        final Integer positives = node.get("positives").asInt();
        final String sha256 = node.get("sha256").asText();
        final String md5 = node.get("md5").asText();

        ProcessStatus pStatus = new ProcessStatus();
        pStatus.setScans(scans);
        pStatus.setScanId(scan_id);
        pStatus.setSha1(sha1);
        pStatus.setResource(resource);
        pStatus.setResponseCode(response_code);
        pStatus.setScanDate(scan_date);
        pStatus.setPermalink(permalink);
        pStatus.setVerboseMessage(verbose_msg);
        pStatus.setTotal(total);
        pStatus.setPositives(positives);
        pStatus.setSha256(sha256);
        pStatus.setMd5(md5);

        return pStatus;
    }
}
