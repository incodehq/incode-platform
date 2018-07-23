package org.incode.module.minio.docclient;

public class MinioDocClient_Main {

    public static void main(String[] args) {
        final MinioDocClient minioDocClient = new MinioDocClient();
        minioDocClient.setBase("http://localhost:8080/restful/");
        minioDocClient.setUsername("estatio-admin");
        minioDocClient.setPassword("pass");
        minioDocClient.init();

        minioDocClient.archive(
                ArchiveMessage.builder("incodedocuments.Document:27")
                        .withExternalUrl("http://minio.int.prd.ecpnv.com:9001/estatio/prod/AuditReport.pdf")
        );
    }

}
