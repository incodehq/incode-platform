package org.incode.module.minio.docserver.dom;

import javax.inject.Inject;

import org.apache.isis.applib.ViewModel;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.Blob;

import org.incode.module.minio.docserver.spi.DocServiceBridge;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "incodeMinio.MinioDoc"
)
@NoArgsConstructor
public class MinioDoc implements ViewModel {

    public MinioDoc(final String bookmark) {
        this.bookmark = bookmark;
    }

    @Programmatic
    @Override
    public void viewModelInit(final String viewModel) {
        bookmark = viewModel;
    }

    @Programmatic
    @Override
    public String viewModelMemento() {
        return bookmark;
    }

    public String title() {
        return getBlob().getName();
    }

    /**
     * Bookmark of the persisted Document entity in the Apache Isis app.
     */
    @Getter @Setter
    private String bookmark;

    /**
     * The corresponding Blob.
     *
     * <p>
     *     Blobs are excluded from the state (their OID) of view models, so we look up dynamically.
     * </p>
     */
    public Blob getBlob() {
        return docServiceBridge.blobFor(this);
    }


    @Inject
    DocServiceBridge docServiceBridge;


}
