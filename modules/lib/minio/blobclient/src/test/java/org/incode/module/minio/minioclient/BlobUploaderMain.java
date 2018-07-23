package org.incode.module.minio.minioclient;

import java.io.IOException;
import java.net.URL;

import com.google.common.io.Resources;

public class BlobUploaderMain {

    public static void main(String[] args) throws IOException {

        final BlobUploader blobUploader = new BlobUploader();
        blobUploader.setUrl("http://minio.int.prd.ecpnv.com:9001/");
        blobUploader.setAccessKey("minio");
        blobUploader.setSecretKey("minio123");
        blobUploader.setPrefix("prod");
        blobUploader.init();


        final String bucket = "estatio";
        final String[] fileNames = { "river-windrush-in-burford.jpg", "church-at-burford.jpg", "jazz-tree.jpg", "woods.jpg" };

        for (String fileName : fileNames) {
            final URL resource = Resources.getResource(BlobUploaderMain.class, "photos/" + fileName);
            final byte[] bytes = Resources.toByteArray(resource);

            final URL url = blobUploader.upload(bucket, fileName, "image/jpeg", bytes);

            System.out.println(url);
        }
    }

}
