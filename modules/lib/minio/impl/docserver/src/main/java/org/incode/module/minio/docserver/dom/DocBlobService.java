package org.incode.module.minio.docserver.dom;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.minio.docserver.spi.DocBlobServiceBridge;

@DomainService(
        nature = NatureOfService.VIEW_REST_ONLY,
        objectType = "incodeMinio.DocBlobService"
)
public class DocBlobService {

    @Action(semantics = SemanticsOf.SAFE)
    public List<DocBlob> findToArchive(String caller) {
        return docBlobServiceBridge.findToArchive(caller);
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public void archive(final String docBookmark, final String externalUrl) {
        docBlobServiceBridge.archive(docBookmark, externalUrl);
    }

    @Inject
    DocBlobServiceBridge docBlobServiceBridge;

}


