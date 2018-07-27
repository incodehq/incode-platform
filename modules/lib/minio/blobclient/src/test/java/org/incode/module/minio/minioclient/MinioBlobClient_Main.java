package org.incode.module.minio.minioclient;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

public class MinioBlobClient_Main {

    public static void main(String[] args) throws IOException {

        final MinioBlobClient minioBlobClient = new MinioBlobClient();
        minioBlobClient.setUrl("http://minio.int.prd.ecpnv.com");
        //minioBlobClient.setUrl("http://minio.int.prd.ecpnv.com:9000");
        //minioBlobClient.setUrl("http://10.101.51.46:9000");
        minioBlobClient.setAccessKey("minio");
        minioBlobClient.setSecretKey("minio123");
        minioBlobClient.setBucket("estatio-test");
        minioBlobClient.setPrefix("db");
        minioBlobClient.init();

        final Map<String,String> fileNames = ImmutableMap.of(
                "incodeDocument.Document:5", "river-windrush-in-burford.jpg",
                "incodeDocument.Document:6", "church-at-burford.jpg",
                "incodeDocument.Document:7", "jazz-tree.jpg",
                "incodeDocument.Document:8", "woods.jpg" );

        for (final String oid : fileNames.keySet()) {

            final String fileName = fileNames.get(oid);

            final URL resource = Resources.getResource(MinioBlobClient_Main.class, "photos/" + fileName);
            final byte[] bytes = Resources.toByteArray(resource);

            final URL url = minioBlobClient.upload(oid, "image/jpeg", bytes, fileName);

            System.out.println(url);
        }
    }

}
