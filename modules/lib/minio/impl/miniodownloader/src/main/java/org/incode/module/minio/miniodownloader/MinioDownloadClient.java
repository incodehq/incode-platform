package org.incode.module.minio.miniodownloader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.client.util.IOUtils;
import com.google.common.base.Strings;

import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

import org.incode.module.minio.common.util.TryCatch;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * Downloads blobs or clobs.
 *
 *
 */
public class MinioDownloadClient {


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
     * Populated on {@link #init()}.
     */
    MinioClient minioClient;

    /**
     * Optionally fine-tune the backoff algorithm
     */
    int backoffNumAttempts = 5;
    /**
     * Optionally fine-tune the backoff algorithm
     */
    long backoffSleepMillis = 200L;

    private TryCatch tryCatch;

    @SneakyThrows
    public void init() {

        ensureSet(this.url, "url");
        ensureSet(this.accessKey, "accessKey");
        ensureSet(this.secretKey, "secretKey");

        final TryCatch.Backoff backoff = new TryCatch.Backoff.Default() {
            @Override public void backoff(final int attempt) {
                sleep(attempt * backoffSleepMillis);
            }
        };

        tryCatch = new TryCatch(backoffNumAttempts, backoff);
        minioClient = newMinioClient();
    }

    MinioClient newMinioClient() throws InvalidEndpointException, InvalidPortException {
        return new MinioClient(url, accessKey, secretKey);
    }

    private static void ensureSet(final String field, final String fieldName) {
        if (Strings.isNullOrEmpty(field)) {
            throw new IllegalStateException(String.format("'%s' not set", fieldName));
        }
    }


    /**
     * @param url
     * @throws if url cannot be parsed to extract bucketName and objectName
     */
    public void check(final String url) {
        ParsedUrl.of(url);
    }

    @SneakyThrows
    public Blob downloadBlob(final String url) {
        return downloadBlob(null, url);
    }

    @SneakyThrows
    public Blob downloadBlob(final String documentNameIfAny, final String url) {
        final ParsedUrl parsedUrl = ParsedUrl.of(url);

        final ObjectStat objectStat = statObject(parsedUrl);
        final String contentType = objectStat.contentType();

        final byte[] bytes = downloadBytes(parsedUrl);

        final String documentName = inferDocumentName(documentNameIfAny, objectStat);
        return new Blob(documentName, contentType, bytes);
    }

    @SneakyThrows
    public Clob downloadClob(final String url) {
        return downloadClob(null, url);
    }

    @SneakyThrows
    public Clob downloadClob(final String documentNameIfAny, final String url) {
        final ParsedUrl parsedUrl = ParsedUrl.of(url);

        final ObjectStat objectStat = statObject(parsedUrl);
        final String contentType = objectStat.contentType();

        final byte[] bytes = downloadBytes(parsedUrl);

        // this is reciprocal of DomainObjectPropertyValue#asBytes()
        final String chars = new String(bytes, StandardCharsets.UTF_8);

        final String documentName = inferDocumentName(documentNameIfAny, objectStat);
        return new Clob(documentName, contentType, chars);
    }

    @SneakyThrows
    public byte[] downloadBytes(final String url) {
        final ParsedUrl parsedUrl = ParsedUrl.of(url);
        return downloadBytes(parsedUrl);
    }

    @SneakyThrows
    public String contentType(final String url) {
        final ParsedUrl parsedUrl = ParsedUrl.of(url);

        final ObjectStat objectStat = statObject(parsedUrl);
        return objectStat.contentType();
    }

    @SneakyThrows
    public String fileName(final String url) {
        final ParsedUrl parsedUrl = ParsedUrl.of(url);
        final ObjectStat objectStat = statObject(parsedUrl);
        return filenameFrom(objectStat);
    }

    @Data
    private static class ParsedUrl {
        private static final Pattern URL_PATTERN =
                Pattern.compile("(?<protocol>.+?)://(?<host>.+?)/(?<bucketName>.+?)/(?<objectName>.+)");

        static ParsedUrl of(final String url) {
            final Matcher matcher = URL_PATTERN.matcher(url);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(
                        String.format("Could not parse url '%s' to extract bucket and object names", url));
            }

            final String protocol = matcher.group("protocol");
            final String host = matcher.group("host");
            final String bucketName = matcher.group("bucketName");
            final String objectName = matcher.group("objectName");
            return new ParsedUrl(protocol, host, bucketName, objectName);

        }

        private final String protocol;
        private final String host;
        private final String bucketName;
        private final String objectName;
    }

    private byte[] downloadBytes(final ParsedUrl parsedUrl) throws Exception {
        return tryCatch.tryCatch(
                () -> {
                    final InputStream is = minioClient.getObject(parsedUrl.bucketName, parsedUrl.objectName);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    IOUtils.copy(is, baos);
                    return baos.toByteArray();
                },
                () -> {
                    minioClient = newMinioClient();
                    return null;
                }
            );
    }

    private ObjectStat statObject(final ParsedUrl parsedUrl) throws Exception {
        return tryCatch.tryCatch(
                () -> minioClient.statObject(parsedUrl.bucketName, parsedUrl.objectName),
                () -> {
                    minioClient = newMinioClient();
                    return null;
                }
        );
    }

    private static String inferDocumentName(final String documentNameIfAny, final ObjectStat objectStat) {
        if (documentNameIfAny != null) {
            return documentNameIfAny;
        } else {
            final String filename = filenameFrom(objectStat);
            if(filename != null) {
                return filename;
            } else {
                return "unknown";
            }
        }
    }

    private static final Pattern CONTENT_DISPOSITION_FILENAME_PATTERN = Pattern.compile(".*filename=\"(?<filename>.+)\".*");

    private static String filenameFrom(final ObjectStat objectStat) {
        final Map<String, List<String>> httpHeaders = objectStat.httpHeaders();
        return filenameFrom(httpHeaders);
    }

    static String filenameFrom(final Map<String, List<String>> httpHeaders) {
        final List<String> contentDispositionHeaders = httpHeaders.get("Content-Disposition");
        if(contentDispositionHeaders != null) {
            for (final String contentDispositionHeader : contentDispositionHeaders) {
                final Matcher matcher = CONTENT_DISPOSITION_FILENAME_PATTERN.matcher(contentDispositionHeader);
                if(matcher.matches()) {
                    return matcher.group("filename");
                }
            }
        }
        return null;
    }

}
