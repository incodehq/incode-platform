package org.incode.module.minio.docclient;

import org.incode.module.minio.docclient.archive.ArchiveArgs;

public class DocBlobClient_archive_Main {

    public static void main(String[] args) {
        final DocBlobClient docBlobClient = new DocBlobClient();
        docBlobClient.setBase("http://localhost:8080/restful/");
        docBlobClient.setUsername("estatio-admin");
        docBlobClient.setPassword("pass");
        docBlobClient.init();

        docBlobClient.archive(
                ArchiveArgs.builder("incodedocuments.Document:27")
                        .withExternalUrl("http://minio.int.prd.ecpnv.com/estatio-test/db/incodeDocument.Document/5")
        );
    }

}
