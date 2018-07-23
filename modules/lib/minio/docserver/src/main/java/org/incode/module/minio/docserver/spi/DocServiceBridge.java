package org.incode.module.minio.docserver.spi;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.Blob;

import org.incode.module.minio.docserver.dom.MinioDoc;

public interface DocServiceBridge {

    @Programmatic
    List<MinioDoc> findToArchive();

    @Programmatic
    Blob blobFor(MinioDoc minioDoc);

    @Programmatic
    void archive(String minioDoc, String externalUrl);

    @DomainService(nature = NatureOfService.DOMAIN, menuOrder = "" + Integer.MAX_VALUE)
    class Noop implements DocServiceBridge {

        public List<MinioDoc> findToArchive() {
            return Collections.emptyList();
        }

        @Override
        public void archive(final String docBookmark, String externalUrl) {
            throw new UnsupportedOperationException();
        }

        @Override public Blob blobFor(final MinioDoc minioDoc) {
            throw new UnsupportedOperationException();
        }
    }

}
