package org.incode.module.minio.dopserver.dom;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

import org.incode.module.minio.dopserver.spi.DomainObjectProperty;
import org.incode.module.minio.dopserver.spi.DomainObjectPropertyServiceBridge;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Holds the content of a property of some domain object.
 *
 * <p>
 * The REST API will automatically serialize this view model so it can be consumed by the REST client
 * running in the integration solution (eg Apache Camel).
 * </p>
 */
@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "incodeMinio.DomainObjectPropertyValue"
)
@NoArgsConstructor
public class DomainObjectPropertyValueViewModel {

    public DomainObjectPropertyValueViewModel(final DomainObjectProperty domainObjectProperty) {
        this.sourceBookmark = domainObjectProperty.getBookmark();
        this.sourceProperty = domainObjectProperty.getProperty();
    }

    public String title() {
        return String.format("%s#%s", getSourceBookmark(), getSourceProperty());
    }


    /**
     * Bookmark of the domain entity instance which holds the blob to be archived.
     */
    @Property
    @Getter @Setter
    private String sourceBookmark;

    /**
     * The property of the domain entity entity which holds the blob to be archived.
     */
    @Property
    @Getter @Setter
    private String sourceProperty;

    /**
     * Whether this property is a blob or a clob.
     */
    @Property
    @Getter @Setter
    private DomainObjectProperty.Type type;


    /**
     * The corresponding Blob, looked up from the source domain object.
     *
     * <p>
     *     Non-null only if {@link #getType()} is {@link DomainObjectProperty.Type#BLOB blob}.
     * </p>
     */
    @Property(notPersisted = true)
    public Blob getBlob() {
        switch (getType()) {
        case BLOB:
            return domainObjectPropertyServiceBridge.blobFor(new DomainObjectProperty(getSourceBookmark(), getSourceProperty(), getType()));
        case CLOB:
        default:
            return null;
        }
    }
    public boolean hideBlob() {
        return getType() != DomainObjectProperty.Type.BLOB;
    }

    /**
     * The corresponding Clob, looked up from the source domain object.
     *
     * <p>
     *     Non-null only if {@link #getType()} is {@link DomainObjectProperty.Type#CLOB clob}.
     * </p>
     */
    @Property(notPersisted = true)
    public Clob getClob() {
        switch (getType()) {
        case CLOB:
            return domainObjectPropertyServiceBridge.clobFor(new DomainObjectProperty(getSourceBookmark(), getSourceProperty(), getType()));
        case BLOB:
            return null;
        default:
            throw new IllegalStateException(String.format("Unknown type: %s", getType()));
        }
    }
    public boolean hideClob() {
        return getType() != DomainObjectProperty.Type.CLOB;
    }

    @Inject
    DomainObjectPropertyServiceBridge domainObjectPropertyServiceBridge;

}
