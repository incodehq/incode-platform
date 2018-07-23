package org.incode.module.minio.docserver.dom;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.minio.docserver.spi.DocServiceBridge;

@DomainService(
        nature = NatureOfService.VIEW_REST_ONLY,
        objectType = "incodeMinio.MinioDocService"
)
public class MinioDocService {

    @Action(semantics = SemanticsOf.SAFE)
    public List<MinioDoc> findToArchive() {
        return docServiceBridge.findToArchive();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public void archive(final String docBookmark, final String externalUrl) {
        docServiceBridge.archive(docBookmark, externalUrl);
    }

    @Inject
    DocServiceBridge docServiceBridge;

}


