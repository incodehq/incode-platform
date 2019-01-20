package org.incode.module.minio.dopserver.spi;

import org.apache.isis.applib.services.bookmark.Bookmark;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Identifies a particular property of an individual source domain entity instance, along with
 * whether it is a blob or a clob.
 */
@Data
@AllArgsConstructor
public class DomainObjectProperty {

    public enum Type {
        BLOB,CLOB
    }

    private final String bookmark;
    private final String property;
    private final Type type;

    public DomainObjectProperty(final Bookmark bookmark, final String property, final Type type) {
        this(bookmark.toString(), property, type);
    }
}
