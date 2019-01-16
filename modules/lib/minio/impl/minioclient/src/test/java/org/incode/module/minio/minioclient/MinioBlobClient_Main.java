package org.incode.module.minio.minioclient;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import org.incode.module.minio.common.DomainObjectPropertyValue;

public class MinioBlobClient_Main {

    public static void main(String[] args) throws IOException {

        final MinioUploadClient minioUploadClient = new MinioUploadClient();
        minioUploadClient.setUrl("http://minio.int.prd.ecpnv.com");
        //minioUploadClient.setUrl("http://minio.int.prd.ecpnv.com:9000");
        //minioUploadClient.setUrl("http://10.101.51.46:9000");
        minioUploadClient.setAccessKey("minio");
        minioUploadClient.setSecretKey("minio123");
        minioUploadClient.setBucket("estatio-test");
        minioUploadClient.setPrefix("db");
        minioUploadClient.init();

        final Map<String,String> fileNames = ImmutableMap.of(
                "incodeDocument.Document:5", "river-windrush-in-burford.jpg",
                "incodeDocument.Document:6", "church-at-burford.jpg",
                "incodeDocument.Document:7", "jazz-tree.jpg",
                "incodeDocument.Document:8", "woods.jpg" );

        final String sourceProperty = "blob";
        for (final String sourceBookmark : fileNames.keySet()) {

            final String fileName = fileNames.get(sourceBookmark);

            final URL resource = Resources.getResource(MinioBlobClient_Main.class, "photos/" + fileName);
            final byte[] bytes = Resources.toByteArray(resource);

            final URL url = minioUploadClient
                    .upload(new DomainObjectPropertyValue(sourceBookmark, sourceProperty, fileName, "image/jpeg", bytes));

            System.out.println(url);
        }
    }

}
