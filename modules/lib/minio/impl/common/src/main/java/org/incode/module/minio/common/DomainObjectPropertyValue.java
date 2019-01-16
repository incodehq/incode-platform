package org.incode.module.minio.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a property value of a domain object to be archived.
 *
 * <p>
 *     Is the client-side equivalent of <code>DomainObjectPropertyValueViewModel</code>, and has
 *     Jackson annotations so that can be easily unmarshalled from the response from the REST API.
 * </p>
 */
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainObjectPropertyValue {

    private static final ObjectWriter writer;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    public enum Type {
        BLOB,CLOB
    }

    /**
     * Convenience constructor.
     */
    public DomainObjectPropertyValue(
            final String sourceBookmark,
            final String sourceProperty,
            final String blobFileName,
            final String blobContentType,
            final byte[] blobBytes) {
        this.sourceBookmark = sourceBookmark;
        this.sourceProperty = sourceProperty;
        this.blob = new ParsedBlob(blobFileName, blobContentType, blobBytes).asEncodedString();
    }


    /**
     * Identity of the source domain entity that holds the blob.
     */
    @Getter @Setter
    String sourceBookmark;
    /**
     * The name of the property of the domain entity that holds the blob.
     *
     * <p>
     *     Note that this should follow JavaBeans conventions and so omit the <code>get</code> prefix.
     *     For example, it should be "memento" rather than "getMemento".
     * </p>
     */
    @Getter @Setter
    String sourceProperty;

    /**
     * Whether the value of this property is a {@link Type#BLOB blob} or a {@link Type#CLOB clob}.
     */
    @Getter @Setter
    Type type;

    /**
     * Encoded representation of the blob, <tt>name:contentType:base64OfByteArray</tt>
     *
     * <p>
     *     Only non-null for a {@link #getType() type} of {@link Type#BLOB}.
     * </p>
     */
    @Getter @Setter
    String blob;

    /**
     * Encoded representation of the blob, <tt>name:contentType:characters</tt>
     *
     * <p>
     *     Only non-null for a {@link #getType() type} of {@link Type#CLOB}.
     * </p>
     */
    @Getter @Setter
    String clob;

    @JsonIgnore
    private ParsedBlob parsedBlob;

    @JsonIgnore
    private ParsedClob parsedClob;

    /**
     * Derived, is the filename of the content (from either {@link #getBlob()} or {@link #getClob()},
     * dependent on {@link #getType()}).
     */
    @JsonIgnore
    public String getFileName() {
        switch (getType()) {
        case BLOB:
            if (parsedBlob == null) {
                parsedBlob = ParsedBlob.from(blob);
            }
            return parsedBlob.fileName;
        case CLOB:
            if (parsedClob == null) {
                parsedClob = ParsedClob.from(clob);
            }
            return parsedClob.fileName;
        default:
            throw new IllegalStateException(String.format("Unknown type: %s", getType()));
        }
    }

    /**
     * Derived, is the content type (from either {@link #getBlob()} or {@link #getClob()},
     *      * dependent on {@link #getType()}).
     */
    @JsonIgnore
    public String getContentType() {
        switch (getType()) {
        case BLOB:
            if (parsedBlob == null) {
                parsedBlob = ParsedBlob.from(blob);
            }
            return parsedBlob.contentType;
        case CLOB:
            if (parsedClob == null) {
                parsedClob = ParsedClob.from(clob);
            }
            return parsedClob.contentType;
        default:
            throw new IllegalStateException(String.format("Unknown type: %s", getType()));
        }
    }

    /**
     * Derived, is the byte array from {@link #getBlob()}, unencoded from its base 64 representation,
     * or <code>null</code> if {@link #getType()} indicates this is a {@link Type#CLOB clob}.
     */
    @JsonIgnore
    public byte[] getBlobByteArray() {
        switch (getType()) {
        case BLOB:
            if (parsedBlob == null) {
                parsedBlob = ParsedBlob.from(blob);
            }
            return parsedBlob.bytes;
        case CLOB:
            return null;
        default:
            throw new IllegalStateException(String.format("Unknown type: %s", getType()));
        }
    }

    /**
     * Derived, is the characters from {@link #getClob()}, or <code>null</code> if {@link #getType()} indicates
     * this is a {@link Type#BLOB blob}.
     */
    @JsonIgnore
    public String getClobCharacters() {
        switch (getType()) {
        case BLOB:
            return null;
        case CLOB:
            if (parsedClob == null) {
                parsedClob = ParsedClob.from(clob);
            }
            return parsedClob.chars;
        default:
            throw new IllegalStateException(String.format("Unknown type: %s", getType()));
        }
    }

    @Override
    public String toString() {
        return String.format(
                "DomainObjectPropertyValue{sourceBookmark='%s', sourceProperty='%s', type='%s', fileName='%s', contentType='%s', length=%d}",
                getSourceBookmark(), getSourceProperty(), getType(),
                getFileName(), getContentType(), getType() == Type.BLOB ? getBlobByteArray().length : getClobCharacters().length());
    }
}
