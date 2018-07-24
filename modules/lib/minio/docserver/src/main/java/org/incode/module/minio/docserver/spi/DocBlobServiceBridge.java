package org.incode.module.minio.docserver.spi;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.Blob;

import org.incode.module.minio.docserver.dom.DocBlob;

public interface DocBlobServiceBridge {

    @Programmatic
    List<DocBlob> findToArchive();

    @Programmatic
    Blob blobFor(DocBlob docBlob);

    @Programmatic
    void archive(String minioDoc, String externalUrl);

    @DomainService(nature = NatureOfService.DOMAIN, menuOrder = "" + Integer.MAX_VALUE)
    class Noop implements DocBlobServiceBridge {

        public List<DocBlob> findToArchive() {
            return Collections.emptyList();
        }

        @Override
        public void archive(final String docBookmark, String externalUrl) {
            throw new UnsupportedOperationException();
        }

        @Override public Blob blobFor(final DocBlob docBlob) {
            throw new UnsupportedOperationException();
        }
    }

}
