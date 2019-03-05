package org.incode.module.minio.minioclient;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import org.incode.module.minio.common.DomainObjectPropertyValue;
import org.incode.module.minio.common.util.TryCatch;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * Uploads property values (blobs or clobs) to a specific "instance" within a bucket.
 * If the property is a clob, then the string is UTF-8 encoded.
 *
 * <p>
 * The bucket/instance is granted anonymous read-only access.
 * </p>
 *
 * <p>
 * The object name is intended to be the OID of the entity acting as the metadata for the blob, eg "incodedocuments.Document:123"
 * This is (minimally) parsed, replacing any ":" for "/", eg  "incodedocuments.Document/123/property".
 * </p>
 *
 *
 */
public class MinioUploadClient {

    private static final String X_AMZ_META = "X-Amz-Meta-";

    /**
     * eg: "http://minio.mycompany.com:9000/"
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
     * Bucket to use, corresponding to the owning application, eg "estatio".
     *
     * <p>
     * It is the responsibility of the owning application to ensure that the URL (within the bucket) is unique
     * across _all_ possible instances of the application.  This is done by a combination of the
     * {@link #instance instance} and the bookmark of the original source entity.
     * </p>
     */
    @Setter
    private String bucket;

    /**
     * Top-level instance to use, eg "prod" or "dev".
     *
     * <p>
     * This distinguishes between different instances of the top-level application that owns the bucket.
     * </p>
     */
    @Setter
    private String instance;

    /**
     * Populated on {@link #init()}.
     */
    MinioClient minioClient;

    @SneakyThrows
    public void init() {

        ensureSet(this.url, "url");
        ensureSet(this.accessKey, "accessKey");
        ensureSet(this.secretKey, "secretKey");
        ensureSet(this.bucket, "bucket");
        ensureSet(this.instance, "instance");

        minioClient = newMinioClient();

        createBucketIfRequired(minioClient);
        setBucketPolicy();
    }

    MinioClient newMinioClient() throws InvalidEndpointException, InvalidPortException {
        return new MinioClient(this.url, this.accessKey, this.secretKey);
    }

    private static void ensureSet(final String field, final String fieldName) {
        if (Strings.isNullOrEmpty(field)) {
            throw new IllegalStateException(String.format("'%s' not set", fieldName));
        }
    }

    @SneakyThrows
    private void createBucketIfRequired(final MinioClient minioClient) {
        boolean bucketExists = this.minioClient.bucketExists(bucket);
        if (!bucketExists) {
            minioClient.makeBucket(bucket);
        }
    }

    @SneakyThrows
    private void setBucketPolicy() {
        final URL resource = Resources.getResource(getClass(), "bucket-policy.template.json");
        final String policyTemplate = Resources.toString(resource, Charsets.UTF_8);
        final String policy = policyTemplate.replace("${BUCKET_NAME}", bucket).replace("${PREFIX}", instance);
        minioClient.setBucketPolicy(bucket, policy);
    }

    public URL upload(final DomainObjectPropertyValue dopv) {
        return upload(dopv, Maps.newHashMap());
    }

    public URL upload(
            final DomainObjectPropertyValue dopv,
            final Map<String, String> metadata) {
        final String sourceBookmark = dopv.getSourceBookmark();
        final String sourceProperty = dopv.getSourceProperty();
        final byte[] bytes = dopv.asBytes();

        return upload(sourceBookmark, sourceProperty, dopv.getFileName(), dopv.getContentType(), bytes, metadata);
    }

    private URL upload(
            final String sourceBookmark,
            final String sourceProperty,
            final String fileName,
            final String contentType,
            final byte[] bytes,
            final Map<String, String> metadata) {

        final String path = String.format("%s/%s/%s", instance, sourceBookmark.replace(":", "/"), sourceProperty);

        return upload(path, fileName, contentType, bytes, metadata);
    }

    URL upload(
            final String path,
            final String fileName,
            final String contentType,
            final byte[] bytes,
            final Map<String, String> metadata) {
        try {
            final Map<String, String> prefixedMetadata = prefixed(bucket, metadata);
            prefixedMetadata.put("Content-Disposition", "inline; filename=\"" + sanitize(fileName) + "\"");
            return upload(path, contentType, bytes, prefixedMetadata);
        } catch (Exception ex) {
            // as a fallback, try to use save with no file name.
            // (if this fails, then we really will give up and throw the exception).
            final Map<String, String> prefixedMetadata = prefixed(bucket, metadata);
            return upload(path, contentType, bytes, prefixedMetadata);
        }
    }

    @SneakyThrows
    public URL upload(
            final String path,
            final String contentType,
            final byte[] bytes,
            final Map<String, String> metadata) {

        final Map<String, String> headers = Maps.newHashMap();
        headers.putAll(metadata);
        headers.put("Content-Type", contentType);

        return new TryCatch().tryCatch(
                () -> {
                    final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
                    minioClient.putObject(bucket, path, is, bytes.length, headers);

                    final String objectUrl = minioClient.getObjectUrl(bucket, path);
                    return new URL(objectUrl);
                },
                () -> {
                    minioClient = newMinioClient();
                    return null;
                }
        );
    }

    private static Map<String, String> prefixed(
            final String bucket,
            final Map<String, String> metadata) {
        final Map<String, String> prefixed = Maps.newHashMap();
        metadata.forEach((key, value) -> prefixed.put(prefix(bucket, key), value));
        return prefixed;
    }

    /**
     * Ensures that all metadata is prefixed with "X-Amz-Meta-[Bucket]-".
     *
     * <p>
     * where:
     * <li>
     * The "X-Amz-Meta" is mandatory for all custom metadata.
     * </li>
     * <li>
     * <p>
     * The "[Bucket]" allows other "foreign" applications to potentially add in their own metadata
     * in their own "namespace".
     * </p>
     * <p>
     * For example a scanning application might upload http://minioserver/scanner/0101312389, with metadata "X-Amz-Meta-Scanner-ScannedBy=john.doe".
     * </p>
     * <p>
     * Subsequently another application might add additional metadata, eg "X-Amz-Meta-InvoiceApp-ApprovedBy=freda.smith"
     * </p>
     * </li>
     * </p>
     * <p>
     * </p>
     *
     * <p>
     * Note that all checks are performed uppercase because HTTP headers are case insensitive.
     * </p>
     */
    static String prefix(final String bucket, final String key) {

        final String keyUpper = key.toUpperCase();
        final String amzMetaUpper = X_AMZ_META.toUpperCase();
        final String bucketUpper = bucket.toUpperCase() + "-";

        final String suffix;
        if (keyUpper.startsWith(amzMetaUpper)) {
            final String amzMetaBucketUpper = amzMetaUpper + bucketUpper;
            if (keyUpper.startsWith(amzMetaBucketUpper)) {
                // X-Amz-Meta-[Bucket]-Xxx
                suffix = key.substring(amzMetaBucketUpper.length());
            } else {
                // X-Amz-Meta-Xxx
                suffix = key.substring(X_AMZ_META.length());
            }
        } else {
            if (keyUpper.startsWith(bucketUpper)) {
                // [Bucket]-Xxx
                suffix = key.substring(bucketUpper.length());
            } else {
                // Xxx
                suffix = key;
            }
        }
        return X_AMZ_META + capitalize(bucket) + "-" + capitalize(suffix);
    }

    private static String capitalize(final String str) {
        if(str ==null|| str.length() ==0) {
            return str;
        }
        return Character.toTitleCase(str.charAt(0))+ str.substring(1);
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

}
