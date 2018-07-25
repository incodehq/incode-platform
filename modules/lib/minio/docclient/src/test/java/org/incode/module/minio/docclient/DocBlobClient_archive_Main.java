package org.incode.module.minio.docclient;

public class DocBlobClient_archive_Main {

    public static void main(String[] args) {
        final DocBlobClient docBlobClient = new DocBlobClient();
        docBlobClient.setBase("http://localhost:8080/restful/");
        docBlobClient.setUsername("estatio-admin");
        docBlobClient.setPassword("pass");
        docBlobClient.init();

        final DocBlob docBlob = new DocBlob();
        docBlob.docBookmark = "incodedocuments.Document:27";
        docBlobClient.archive(docBlob, "http://minio.int.prd.ecpnv.com/estatio-test/db/incodeDocument.Document/5");
    }

}
