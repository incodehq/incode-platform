package org.incode.module.minio.minioarchlib;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.incode.module.minio.common.DomainObjectPropertyValue;
import org.incode.module.minio.dopclient.DomainObjectPropertyClient;
import org.incode.module.minio.minioclient.MinioUploadClient;

import lombok.Setter;

public class MinioArchiveOnDemand {

    private static final Logger LOG = LoggerFactory.getLogger(MinioArchiveOnDemand.class);

    @Setter
    private MinioUploadClient minioUploadClient;

    @Setter
    private DomainObjectPropertyClient domainObjectPropertyClient;

    /**
     * Will archive a single domain object property.
     */
    public void archive(final String caller, final String sourceBookmark, final String sourceProperty ) {

        final DomainObjectPropertyValue dopv =
                domainObjectPropertyClient.readSingle(caller, sourceBookmark, sourceProperty);
        if(dopv == null) {
            // not yet available or not found.
            return;
        }

        LOG.info("{}#{}: {}", dopv.getSourceBookmark(), dopv.getSourceProperty(), dopv.getFileName());

        final URL url = minioUploadClient.upload(dopv);
        domainObjectPropertyClient.archived(dopv, url.toExternalForm());
    }

}
