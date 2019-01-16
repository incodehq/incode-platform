package org.incode.module.minio.minioarchlib;

import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.incode.module.minio.common.DomainObjectPropertyValue;
import org.incode.module.minio.dopclient.DomainObjectPropertyClient;
import org.incode.module.minio.minioclient.MinioUploadClient;

import lombok.Setter;

public class MinioArchiver  {

    private static final Logger LOG = LoggerFactory.getLogger(MinioArchiver.class);

    @Setter
    private MinioUploadClient minioUploadClient;

    @Setter
    private DomainObjectPropertyClient domainObjectPropertyClient;

    /**
     * Will archive as many as the DomainObjectPropertyServer provides.
     *
     * <p>
     *     The DomainObjectPropertyServer may limit the number, to avoid large memory requirements.
     * </p>
     */
    public int archive(final String caller) {

        final List<DomainObjectPropertyValue> values = domainObjectPropertyClient.findToArchive(caller);
        if(values == null) {
            // not yet available...
            return 0;
        }

        final int numDocs = values.size();
        LOG.info("{} documents found to archive", numDocs);

        int i=0;
        for (final DomainObjectPropertyValue dopv : values) {

            LOG.info("{} of {}: {}#{}: {}", (++i), numDocs, dopv.getSourceBookmark(), dopv.getSourceProperty(), dopv.getFileName());

            final URL url = minioUploadClient.upload(dopv);
            domainObjectPropertyClient.archive(dopv, url.toExternalForm());
        }
        return numDocs;
    }

}
