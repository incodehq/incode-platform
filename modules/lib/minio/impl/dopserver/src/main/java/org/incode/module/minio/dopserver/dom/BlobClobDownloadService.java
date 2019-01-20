package org.incode.module.minio.dopserver.dom;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import javax.activation.MimeType;

import com.google.common.base.Splitter;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

/**
 * Utility service provided for convenience of consuming application.
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        objectType = "incodeMinio.BlobClobDownloadService"
)
public class BlobClobDownloadService {

    @Programmatic
    public Blob downloadBlob(
            final String documentName,
            final String externalUrl)
            throws IOException, ApplicationException {

        final URL url = new URL(externalUrl);

        final HttpURLConnection httpConn = openConnection(url);
        final String contentType = httpConn.getContentType();
        final MimeType mimeType = determineMimeType(contentType);
        httpConn.disconnect();

        final ByteSource byteSource = Resources.asByteSource(url);
        final byte[] bytes = byteSource.read();

        return new Blob(documentName, mimeType.getBaseType(), bytes);
    }

    @Programmatic
    public Clob downloadClob(
            final String documentName,
            final String externalUrl)
            throws IOException, ApplicationException {

        final URL url = new URL(externalUrl);

        final HttpURLConnection httpConn = openConnection(url);
        final String contentType = httpConn.getContentType();
        final MimeType mimeType = determineMimeType(contentType);
        final Charset charset = determineCharset(contentType);
        httpConn.disconnect();

        final CharSource charSource = Resources.asCharSource(url, charset);
        final String chars = charSource.read();

        return new Clob(documentName, mimeType.getBaseType(), chars);
    }

    private static HttpURLConnection openConnection(final URL url) throws IOException, ApplicationException {
        final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        final int responseCode = httpConn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new ApplicationException(
                        TranslatableString.tr(
                                "Could not download URL: {responseCode}: {url}",
                                "responseCode", "" + responseCode,
                                "url", url.toExternalForm()),
                    BlobClobDownloadService.class, "openConnection");
        }
        return httpConn;
    }

    private static MimeType determineMimeType(final String contentType) {
        final String mimeType = parseMimeType(contentType);
        try {
            return new MimeType(mimeType);
        } catch (Exception e) {
            throw new ApplicationException(TranslatableString.tr(
                    "Could not download from URL (mime type not recognized: {mimeType})",
                    "mimeType", mimeType),
                    BlobClobDownloadService.class, "determineMimeType");
        }
    }

    // text/plain; charset=UTF-8
    private static String parseMimeType(final String contentType) {
        final Iterable<String> values = Splitter.on(";").split(contentType);
        for (String value : values) {
            // is simply the first part
            return value.trim();
        }

        throw new ApplicationException(TranslatableString.tr(
                "Could not download from URL (mime type not recognized within content-type header '{contentType}')",
                "contentType", contentType),
                BlobClobDownloadService.class, "parseMimeType");
    }


    private static Charset determineCharset(final String contentType) {

        final String charsetName = parseCharset(contentType);
        try {
            return Charset.forName(charsetName);
        } catch (Exception e) {
            throw new ApplicationException(TranslatableString.tr(
                    "Could not download from URL (charset '{charsetName}' not recognized)",
                    "charsetName", charsetName),
                    BlobClobDownloadService.class, "determineCharset");
        }
    }

    // text/plain; charset=UTF-8
    private static String parseCharset(final String contentType) {
        final Iterable<String> values = Splitter.on(";").split(contentType);

        for (String value : values) {
            value = value.trim();

            if (value.toLowerCase().startsWith("charset=")) {
                return value.substring("charset=".length());
            }
        }

        throw new ApplicationException(TranslatableString.tr(
                "Could not download from URL (charset not recognized within content-type header '{contentType}')",
                "contentType", contentType),
                BlobClobDownloadService.class, "parseCharset");
    }

}
