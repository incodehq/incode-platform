package org.incode.module.jira.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonMarshaller {

    private static final ObjectWriter writer;
    private static final ObjectReader reader;
    static {
        writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        reader = new ObjectMapper().reader().forType(Map.class);
    }

    public Optional<String> asJson(final Map<String, Object> body) {
        try {
            return Optional.of(writer.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public JsonNode asJsonNode(final String json) {
        try {
            return reader.readTree(json);
        } catch (IOException e) {
            return null;
        }
    }
}
