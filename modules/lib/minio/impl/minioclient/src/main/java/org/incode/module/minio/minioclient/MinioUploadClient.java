package org.incode.module.minio.minioclient;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import org.incode.module.minio.common.DomainObjectPropertyValue;

import io.minio.MinioClient;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * Uploads property values (blobs or clobs) to a specific "prefix" within a bucket.
 * If the property is a clob, then the string is UTF-8 encoded.
 *
 * <p>
 * The bucket/prefix is granted anonymous read-only access.
 * </p>
 *
 * <p>
 * The object name is intended to be the OID of the entity acting as the metadata for the blob, eg "incodeDocuments.Document:123"
 * This is (minimally) parsed, replacing any ":" for "/", eg  "incodeDocuments.Document/123/property".
 * </p>
 *
 *
 */
public class MinioUploadClient {

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

        ensureSet(this.url, "url");
        ensureSet(this.accessKey, "accessKey");
        ensureSet(this.secretKey, "secretKey");
        ensureSet(this.bucket, "bucket");
        ensureSet(this.prefix, "prefix");

        minioClient = new MinioClient(url, accessKey, secretKey);

        createBucketIfRequired();
        setBucketPolicy();
    }

    private static void ensureSet(final String field, final String fieldName) {
        if(field == null) {
            throw new IllegalStateException(String.format("'%s' not set", fieldName));
        }
    }

    @SneakyThrows
    private void createBucketIfRequired() {
        boolean bucketExists = minioClient.bucketExists(bucket);
        if (!bucketExists) {
            minioClient.makeBucket(bucket);
        }
    }

    @SneakyThrows
    private void setBucketPolicy() {
        final URL resource = Resources.getResource(getClass(), "bucket-policy.template.json");
        final String policyTemplate = Resources.toString(resource, Charsets.UTF_8);
        final String policy = policyTemplate.replace("${BUCKET_NAME}", bucket).replace("${PREFIX}", prefix);
        minioClient.setBucketPolicy(bucket, policy);
    }

    @SneakyThrows
    public URL upload(final DomainObjectPropertyValue dopv) {
        final String sourceBookmark = dopv.getSourceBookmark();
        final String property = dopv.getSourceProperty();
        final DomainObjectPropertyValue.Type type = dopv.getType();

        final byte[] bytes;
        switch (type) {
        case BLOB:
            bytes = dopv.getBlobByteArray();
            break;
        case CLOB:
            bytes = dopv.getClobCharacters().getBytes(StandardCharsets.UTF_8);
            break;
        default:
            throw new IllegalStateException(String.format("Unknown type: '%s'", type));
        }

        return upload(sourceBookmark, property, dopv.getFileName(), dopv.getContentType(), bytes);
    }

    @SneakyThrows
    URL upload(
            final String objectName,
            final String property,
            final String fileName,
            final String contentType,
            final byte[] bytes) {
        try {
            final Map<String, String> headers = ImmutableMap.of("File-Name", sanitize(fileName));
            return upload(objectName, property, contentType, bytes, headers);
        } catch(Exception ex) {
            // as a fallback, try to use save with no file name.
            // (if this fails, then we really will give up and throw the exception).
            return upload(objectName, property, contentType, bytes, Collections.emptyMap());
        }
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
    URL upload(
            final String objectName,
            final String property,
            final String contentType,
            final byte[] bytes,
            final Map<String, String> metadata) {

        final Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.putAll(ImmutableMap.of("Content-Type", contentType));
        for (final Map.Entry<String, String> entry : metadata.entrySet()) {
            httpHeaders.put(prefixIfRequired(entry.getKey()), entry.getValue());
        }

        final String path = String.format("%s/%s/%s", prefix, objectName.replace(":", "/"), property);

        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        minioClient.putObject(bucket, path, is, bytes.length, httpHeaders);

        final String objectUrl = minioClient.getObjectUrl(bucket, path);
        return new java.net.URL(objectUrl);
    }

    /**
     * as per https://docs.aws.amazon.com/AmazonS3/latest/dev/UsingMetadata.html
     *
     * @param str
     * @return
     */
    static String sanitize(final String str) {
        return str
                    .replace("&","_")
                    .replace("@","_")
                    .replace(":","_")
                    .replace(",","_")
                    .replace("$","_")
                    .replace("=","_")
                    .replace("+","_")
                    .replace("?","_")
                    .replace(";","_")
                    .replace("&","_")
                    .replaceAll("[ ]+"," ")
                    .replace("\\","")
                    .replace("^","")
                    .replace("`","")
                    .replace(">","")
                    .replace("<","")
                    .replace("{","")
                    .replace("}","")
                    .replace("[","")
                    .replace("]","")
                    .replace("#","")
                    .replace("%","")
                    .replace("\"","")
                    .replace("~","")
                    .replace("|","")
                ;
    }


    private static String prefixIfRequired(final String key) {
        return key.toLowerCase().startsWith(X_AMZ_META.toLowerCase()) ? key : X_AMZ_META + key;
    }

}
