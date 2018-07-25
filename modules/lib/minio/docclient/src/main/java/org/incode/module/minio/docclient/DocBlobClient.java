package org.incode.module.minio.docclient;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.incode.module.minio.docclient.archive.ArchiveArgs;
import org.incode.module.minio.docclient.archive.StringValue;

import lombok.Setter;

public class DocBlobClient {

    private static final Logger LOG = LoggerFactory.getLogger(DocBlobClient.class);

    //region > constructor, fields
    private final ClientBuilder clientBuilder;

    public DocBlobClient() {
        clientBuilder = ClientBuilder.newBuilder();
    }

    /**
     * Will automatically call {@link #init()} since all properties already supplied.
     */
    public DocBlobClient(final String base, final String username, final String password) {
        this();

        setBase(base);
        setUsername(username);
        setPassword(password);

        init();
    }
    //endregion


    @Setter
    private String base;

    @Setter
    private String username;

    @Setter
    private String password;


    //region > init
    private URI findToArchiveUri;
    private URI archiveUri;

    /**
     * Should be called once all properties have been injected.
     */
    public void init() {
        findToArchiveUri = UriBuilder.fromUri(base + "services/incodeMinio.DocBlobService/actions/findToArchive/invoke").build();
        archiveUri = UriBuilder.fromUri(base + "services/incodeMinio.DocBlobService/actions/archive/invoke").build();
    }

    private void ensureInitialized() {
        if(username == null || password == null || base == null) {
            throw new IllegalStateException("Must initialize 'username', 'password' and 'base' properties");
        }
    }

    //endregion

    //region > findToArchive

    public List<DocBlob> findToArchive() {

        ensureInitialized();

        Client client = null;
        try {
            client = clientBuilder.build();

            final WebTarget webTarget = client.target(findToArchiveUri);

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));
            invocationBuilder.accept("application/json;profile=urn:org.apache.isis/v1;suppress=true");

            final Invocation invocation = invocationBuilder.buildGet();

            final Response response = invocation.invoke();

            final int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                LOG.warn("uri: " + findToArchiveUri + "; responseStatus: " + responseStatus);
                return Collections.emptyList();
            }

            final String json = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            DocBlob[] docBlobs = mapper.readValue(json, DocBlob[].class);
            return Arrays.asList(docBlobs);

        } catch(Exception ex) {
            LOG.error("uri: " + findToArchiveUri, ex);
        } finally {
            closeQuietly(client);
        }
        return null;
    }

    //endregion

    //region > archive

    public void archive(final DocBlob docBlob, final String externalUrl) {

        ensureInitialized();

        ArchiveArgs archiveArgs = new ArchiveArgs();
        archiveArgs.setDocBookmark(new StringValue(docBlob.getDocBookmark()));
        archiveArgs.setExternalUrl(new StringValue(externalUrl));

        Client client = null;
        try {
            client = clientBuilder.build();

            final WebTarget webTarget = client.target(archiveUri);

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));

            final String json = archiveArgs.asJson();

            final Invocation invocation = invocationBuilder.buildPut(
                    Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

            final Response response = invocation.invoke();

            final int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                LOG.warn("uri: " + archiveUri + "; " + archiveArgs.toString());
                return;
            }

        } catch(Exception ex) {
            LOG.error(archiveArgs.toString(), ex);
        } finally {
            closeQuietly(client);
        }
    }

    //endregion

    //region > helpers

    private static String encode(final String username, final String password) {
        return java.util.Base64.getEncoder().encodeToString(asBytes(username, password));
    }

    private static byte[] asBytes(final String username, final String password) {
        return String.format("%s:%s", username, password).getBytes();
    }

    private static void closeQuietly(final Client client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (Exception ex) {
            // ignore so as to avoid overriding any pending exceptions in calling 'finally' block.
        }
    }
    //endregion

}
