package org.incode.module.minio.docserver.dom;

import javax.inject.Inject;

import org.apache.isis.applib.ViewModel;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.Blob;

import org.incode.module.minio.docserver.spi.DocBlobServiceBridge;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "incodeMinio.DocBlob"
)
@NoArgsConstructor
public class DocBlob implements ViewModel {

    public DocBlob(final String objectName) {
        this.objectName = objectName;
    }

    @Programmatic
    @Override
    public void viewModelInit(final String memento) {
        this.objectName = memento;
    }

    @Programmatic
    @Override
    public String viewModelMemento() {
        return objectName;
    }

    public String title() {
        return getBlob().getName();
    }


    /**
     * Object name to use in Minio.
     *
     * Typically is the bookmark of the persisted entity in the Apache Isis app.
     */
    @Getter @Setter
    private String objectName;

    /**
     * The corresponding Blob.
     *
     * <p>
     *     Blobs are excluded from the state (their OID) of view models, so we look up dynamically.
     * </p>
     */
    public Blob getBlob() {
        return docBlobServiceBridge.blobFor(this);
    }


    @Inject
    DocBlobServiceBridge docBlobServiceBridge;

}
