package org.incode.module.minio.minioclient;

import java.io.ByteArrayInputStream;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import io.minio.MinioClient;
import lombok.Setter;
import lombok.SneakyThrows;

public class BlobUploader {

    /**
     * eg: "http://minio.mycompany.com:9001/"
     */
    @Setter
    private String url;

    /**
     * a "username" for accessing Minio
     */
    @Setter
    private String accessKey;

    /**
     * a "password" for accessing Minio
     */
    @Setter
    private String secretKey;

    /**
     * path to use as a prefix for all object names.
     *
     * <p>
     *     For example, setting to "prod" will result in all object named "foo.jpg" being saved as "prod/foo.jpg"
     * </p>
     */
    @Setter
    private String prefix;

    /**
     * Populated on {@link #init()}, the bucket policy to apply enabling anonymous access to the "prefix/..." object names.
     */
    private String policyTemplate;

    MinioClient minioClient;

    @SneakyThrows
    public void init()  {
        minioClient = new MinioClient(url, accessKey, secretKey);
        final URL resource = Resources.getResource(getClass(), "bucket-policy.template.json");
        this.policyTemplate = Resources.toString(resource, Charsets.UTF_8);
    }

    @SneakyThrows
    public URL upload(
            final String bucket,
            final String localObjectName,
            final String contentType,
            final byte[] bytes) {

        boolean bucketExists = minioClient.bucketExists(bucket);
        if (!bucketExists) {
            minioClient.makeBucket(bucket);
            final String policy = policyTemplate.replace("${BUCKET_NAME}", bucket).replace("${PREFIX}", prefix);
            minioClient.setBucketPolicy(bucket, policy);
        }

        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);

        final ImmutableMap<String, String> map = ImmutableMap.of("Content-Type", contentType);

        final String prefixedObjectName = objectNameFor(localObjectName);

        minioClient.putObject(bucket, prefixedObjectName, is, bytes.length, map);

        return new java.net.URL(minioClient.getObjectUrl(bucket, prefixedObjectName));
    }

    private String objectNameFor(final String objectName) {
        if (prefix == null) {
            return objectName;
        }
        return prefix + "/" + objectName;
    }
}
