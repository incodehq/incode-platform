package org.incode.module.minio.docclient.archive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.Getter;

/**
 * The arguments for MinioDoc#archive(...)
 */
public class ArchiveArgs {

    private static final ObjectWriter writer;

    public static class Builder {
        private final ArchiveArgs archiveArgs = new ArchiveArgs();

        Builder(final String bookmark) {
            archiveArgs.docBookmark = new StringValue(bookmark);
        }
        public Builder withExternalUrl(String externalUrl) {
            archiveArgs.externalUrl = new StringValue(externalUrl);
            return this;
        }
        public ArchiveArgs build() {
            return archiveArgs;
        }
    }

    public static Builder builder(String bookmark) {
        return new Builder(bookmark);
    }

    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Getter
    StringValue docBookmark;
    @Getter
    StringValue externalUrl;

    public String asJson() {
        try {
            return writer.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public String toString() {
        return "ArchiveArgs{" +
                "docBookmark=" + docBookmark +
                ", externalUrl=" + externalUrl +
                '}';
    }
}
