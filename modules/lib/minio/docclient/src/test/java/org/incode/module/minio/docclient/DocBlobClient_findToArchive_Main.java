package org.incode.module.minio.docclient;

import java.util.List;

import org.incode.module.minio.docclient.findToArchive.DocBlob;

public class DocBlobClient_findToArchive_Main {

    public static void main(String[] args) {
        final DocBlobClient docBlobClient = new DocBlobClient();
        docBlobClient.setBase("http://localhost:8080/restful/");
        docBlobClient.setUsername("estatio-admin");
        docBlobClient.setPassword("pass");
        docBlobClient.init();

        final List<DocBlob> toArchive = docBlobClient.findToArchive();
        for (final DocBlob docBlob : toArchive) {
            System.out.println(docBlob.toString());
        }
    }

}
