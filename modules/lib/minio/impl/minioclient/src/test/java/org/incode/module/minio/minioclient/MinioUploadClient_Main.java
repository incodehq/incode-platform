package org.incode.module.minio.minioclient;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import org.incode.module.minio.common.DomainObjectPropertyValue;

import static org.incode.module.minio.common.DomainObjectPropertyValue.Type.BLOB;

public class MinioUploadClient_Main {

    public static void main(String[] args) throws IOException {

        final MinioUploadClient minioUploadClient = new MinioUploadClient();
        //minioUploadClient.setUrl("http://minio.int.prd.ecpnv.com");
        //minioUploadClient.setUrl("http://minio.int.prd.ecpnv.com:9000");

        //minioUploadClient.setUrl("http://10.211.52.11:9000"); // docker
        minioUploadClient.setUrl("http://10.211.51.11:9000"); // vpn
        minioUploadClient.setAccessKey(System.getProperty("minioAccessKey"));
        minioUploadClient.setSecretKey(System.getProperty("minioSecretKey"));
        minioUploadClient.setBucket("estatio");
        minioUploadClient.setInstance("test-it");
        minioUploadClient.init();

        final Map<String,String> fileNames = ImmutableMap.of(
                "incodeDocument.Document:1", "river-windrush-in-burford.jpg",
                "incodeDocument.Document:2", "church-at-burford.jpg",
                "incodeDocument.Document:3", "jazz-tree.jpg",
                "incodeDocument.Document:4", "woods.jpg" );

        final String sourceProperty = "photo";
        for (final String sourceBookmark : fileNames.keySet()) {

            final String fileName = fileNames.get(sourceBookmark);

            final URL resource = Resources.getResource(MinioUploadClient_Main.class, "photos/" + fileName);
            final byte[] bytes = Resources.toByteArray(resource);

            final URL url = minioUploadClient
                    .upload(new DomainObjectPropertyValue(
                                    sourceBookmark, sourceProperty, BLOB,
                                    fileName, "image/jpeg", bytes), ImmutableMap.of("foo", "bar")
                    );

            System.out.println(url);
        }
    }

}
