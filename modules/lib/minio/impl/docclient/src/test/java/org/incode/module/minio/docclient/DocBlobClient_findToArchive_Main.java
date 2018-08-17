package org.incode.module.minio.docclient;

import java.util.List;

public class DocBlobClient_findToArchive_Main {

    public static void main(String[] args) {
        final DocBlobClient docBlobClient = new DocBlobClient();
        docBlobClient.setBase("http://localhost:8080/restful/");
        docBlobClient.setUsername("estatio-admin");
        docBlobClient.setPassword("pass");
        docBlobClient.init();

        final List<DocBlob> toArchive = docBlobClient.findToArchive("test");
        for (final DocBlob docBlob : toArchive) {
            System.out.println(docBlob.toString());
        }
    }

}
