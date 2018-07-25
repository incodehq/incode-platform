package org.incode.module.minio.docclient.archive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.Getter;
import lombok.Setter;

public class ArchiveArgs {

    private static final ObjectWriter writer;
    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Getter @Setter
    StringValue docBookmark;
    @Getter @Setter
    StringValue externalUrl;

    public String asJson() {
        try {
            return writer.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "ArchiveArgs{" +
                "docBookmark=" + docBookmark +
                ", externalUrl=" + externalUrl +
                '}';
    }
}
