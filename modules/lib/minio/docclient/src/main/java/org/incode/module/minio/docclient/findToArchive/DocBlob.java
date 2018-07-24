package org.incode.module.minio.docclient.findToArchive;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import lombok.Getter;

/**
 * The arguments for MinioDoc#archive(...)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocBlob {

    private static final ObjectWriter writer;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Getter
    String objectName;
    @Getter
    String blob;

    public String getFileName() {
        return part(0);
    }
    public String getContentType() {
        return part(1);
    }
    public String getByteArrayEncoded() {
        return part(2);
    }
    public byte[] getByteArray() {
        final String byteArrayEncoded = getByteArrayEncoded();
        if(byteArrayEncoded == null) {
            return new byte[0];
        }
        return Base64.getDecoder().decode(byteArrayEncoded);
    }

    String part(final int i) {
        final Iterable<String> split = Splitter.on(':').split(blob);
        final String[] parts = Iterables.toArray(split, String.class);
        return parts.length >= i+1 ? parts[i] : null;
    }

    @Override
    public String toString() {
        return String.format(
                "DocBlob{objectName='%s', fileName='%s', contentType='%s', byteArray.length=%d}",
                objectName, getFileName(), getContentType(), getByteArray().length);
    }
}
