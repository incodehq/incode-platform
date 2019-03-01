package org.incode.module.minio.miniodownloader;

import java.io.IOException;

public class MinioDownloadClient_Main {

    public static void main(String[] args) throws IOException {

        final MinioDownloadClient downloadClient = new MinioDownloadClient();

        downloadClient.setUrl("http://minio.int.ecpnv.com:9000");

        downloadClient.setAccessKey(System.getProperty("minioAccessKey"));
        downloadClient.setSecretKey(System.getProperty("minioSecretKey"));

        downloadClient.init();

        downloadClient.downloadBlob("http://minio.int.ecpnv.com:9000/estatio/staging-ita/incodedocuments.Document/190/blob");
    }

}
