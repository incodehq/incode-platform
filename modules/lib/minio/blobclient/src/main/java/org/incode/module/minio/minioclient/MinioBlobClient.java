package org.incode.module.minio.minioclient;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import io.minio.MinioClient;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * Uploads object blobs to a specific "prefix" within a bucket.
 *
 * <p>
 * The bucket/prefix is granted anonymous read-only access.
 * </p>
 *
 * <p>
 * The object name is intended to be the OID of the entity acting as the metadata for the blob, eg "incodeDocuments.Document:123"
 * This is (minimally) parsed, replacing any ":" for "/", eg  "incodeDocuments.Document/123".
 * </p>
 *
 * <p>
 *
 * </p>
 *
 *
 */
public class MinioBlobClient {

    private static final String X_AMZ_META = "X-Amz-Meta-";

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
     * Bucket to use, eg "estatio_prod".
     *
     * <p>
     *     This combines the application (eg "estatio") with the environment (eg "prod" or "test").
     * </p>
     */
    @Setter
    private String bucket;

    /**
     * Top-level prefix to use, eg "db".
     *
     * <p>
     *     This provides an additional level of namespacing, but is also primarily to simply bucket policies.
     * </p>
     */
    @Setter
    private String prefix;

    /**
     * Populated on {@link #init()}.
     */
    MinioClient minioClient;

    @SneakyThrows
    public void init()  {
        minioClient = new MinioClient(url, accessKey, secretKey);

        createBucketIfRequired();
        setBucketPolicy();
    }

    @SneakyThrows
    void createBucketIfRequired() {
        boolean bucketExists = minioClient.bucketExists(bucket);
        if (!bucketExists) {
            minioClient.makeBucket(bucket);
        }
    }

    @SneakyThrows
    void setBucketPolicy() {
        final URL resource = Resources.getResource(getClass(), "bucket-policy.template.json");
        final String policyTemplate = Resources.toString(resource, Charsets.UTF_8);
        final String policy = policyTemplate.replace("${BUCKET_NAME}", bucket).replace("${PREFIX}", prefix);
        minioClient.setBucketPolicy(bucket, policy);
    }

    @SneakyThrows
    public URL upload(
            final String objectName,
            final String contentType,
            final byte[] bytes,
            final String metaFileName) {
        return upload(objectName, contentType, bytes, ImmutableMap.of("File-Name", metaFileName));
    }

    /**
     *
     * @param objectName
     * @param contentType
     * @param bytes
     * @param metadata - all keys are automatically prefixed "X-Amz-Meta-"
     * @return
     */
    @SneakyThrows
    public URL upload(
            final String objectName,
            final String contentType,
            final byte[] bytes,
            final Map<String,String> metadata) {

        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);

        final Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.putAll(ImmutableMap.of("Content-Type", contentType));
        for (final Map.Entry<String, String> entry : metadata.entrySet()) {
            httpHeaders.put(prefixIfRequired(entry.getKey()), entry.getValue());
        }

        final String prefixedObjectName = prefix + "/" + objectName.replace(":", "/");
        minioClient.putObject(bucket, prefixedObjectName, is, bytes.length, httpHeaders);

        final String objectUrl = minioClient.getObjectUrl(bucket, prefixedObjectName);
        return new java.net.URL(objectUrl);
    }

    private static String prefixIfRequired(final String key) {
        return key.toLowerCase().startsWith(X_AMZ_META.toLowerCase()) ? key : X_AMZ_META + key;
    }

}
