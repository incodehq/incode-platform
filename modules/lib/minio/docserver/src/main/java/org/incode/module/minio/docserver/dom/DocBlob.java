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

    public DocBlob(final String docBookmark) {
        this.docBookmark = docBookmark;
    }

    @Programmatic
    @Override
    public void viewModelInit(final String memento) {
        this.docBookmark = memento;
    }

    @Programmatic
    @Override
    public String viewModelMemento() {
        return docBookmark;
    }

    public String title() {
        return getBlob().getName();
    }


    /**
     * Bookmark of the persisted entity whose blob can be archived subsequently
     * (using {@link DocBlobService#archive(String, String)})
     */
    @Getter @Setter
    private String docBookmark;

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
