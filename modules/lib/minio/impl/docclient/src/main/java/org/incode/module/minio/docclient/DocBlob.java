package org.incode.module.minio.docclient;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocBlob {

    private static final ObjectWriter writer;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Getter
    String docBookmark;

    /**
     * Encoded representation of the blob, <tt>name:contentType:base64OfByteArray</tt>
     */
    @Getter
    String blob;


    /**
     * The objectName to use within Minio.
     *
     * <p>
     * By convention this is just the {@link #getDocBookmark()}; it is the responsibility of the
     * Apache Isis application to act as the "meta-data". However, the Minio client will be configured
     * to specify which bucket to store the object in, and may also add a "prefix" to the object name,
     * eg for permissioning.
     * </p>
     *
     * @return
     */
    public String getLocalObjectName() {
        return docBookmark;
    }

    /**
     * Extracts the filename from {@link #getBlob()}.
     */
    public String getBlobFileName() {
        return part(0);
    }
    /**
     * Extracts the content type from {@link #getBlob()}.
     */
    public String getBlobContentType() {
        return part(1);
    }
    /**
     * Extracts the byte array from {@link #getBlob()}, unencoded from its base 64 representation.
     */
    public byte[] getBlobByteArray() {
        final String byteArrayEncoded = part(2);
        if(byteArrayEncoded == null) {
            return new byte[0];
        }
        return Base64.getDecoder().decode(byteArrayEncoded);
    }

    private String part(final int i) {
        final String[] parts = blob.split("[:]");
        return parts.length >= i+1 ? parts[i] : null;
    }

    @Override
    public String toString() {
        return String.format(
                "DocBlob{docBookmark='%s', fileName='%s', contentType='%s', byteArray.length=%d}",
                docBookmark, getBlobFileName(), getBlobContentType(), getBlobByteArray().length);
    }
}
