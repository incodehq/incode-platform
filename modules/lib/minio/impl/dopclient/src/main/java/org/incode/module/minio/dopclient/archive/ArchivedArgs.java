package org.incode.module.minio.dopclient.archive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ArchivedArgs {

    private static final ObjectWriter writer;
    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Getter @Setter
    StringValue sourceBookmark;
    @Getter @Setter
    StringValue sourceProperty;
    @Getter @Setter
    TypeValue type;
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
        return "ArchivedArgs{" +
                "sourceBookmark=" + sourceBookmark +
                "sourceProperty=" + sourceProperty +
                "type=" + type +
                ", externalUrl=" + externalUrl +
                '}';
    }
}
