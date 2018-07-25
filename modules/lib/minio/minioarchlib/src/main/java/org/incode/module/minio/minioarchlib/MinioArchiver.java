package org.incode.module.minio.minioarchlib;

import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.incode.module.minio.docclient.DocBlob;
import org.incode.module.minio.docclient.DocBlobClient;
import org.incode.module.minio.minioclient.MinioBlobClient;

import lombok.Setter;

public class MinioArchiver  {

    private static final Logger LOG = LoggerFactory.getLogger(MinioArchiver.class);

    @Setter
    private MinioBlobClient minioBlobClient;

    @Setter
    private DocBlobClient docBlobClient;

    public void archiveAll() {
        final List<DocBlob> docBlobs = docBlobClient.findToArchive();
        if(docBlobs == null) {
            // not yet available...
            return;
        }

        final int numDocs = docBlobs.size();
        LOG.info("{} documents found to archive", numDocs);

        int i=0;
        for (final DocBlob docBlob : docBlobs) {

            LOG.info("{} of {}: {}: {}", (++i), numDocs, docBlob.getDocBookmark(), docBlob.getBlobFileName());

            final String docBookmark = docBlob.getDocBookmark();
            final URL url =
                    minioBlobClient.upload(
                            docBookmark, docBlob.getBlobContentType(), docBlob.getBlobByteArray(),
                            docBlob.getBlobFileName());
            docBlobClient.archive(docBlob, url.toExternalForm());
        }
    }

}
