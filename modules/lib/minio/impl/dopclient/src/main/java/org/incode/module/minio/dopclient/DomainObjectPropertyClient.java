package org.incode.module.minio.dopclient;

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

import org.incode.module.minio.common.DomainObjectPropertyValue;
import org.incode.module.minio.dopclient.archive.ArchiveArgs;
import org.incode.module.minio.dopclient.archive.StringValue;

import lombok.Setter;

public class DomainObjectPropertyClient {

    private static final Logger LOG = LoggerFactory.getLogger(DomainObjectPropertyClient.class);

    //region > constructor, fields
    private final ClientBuilder clientBuilder;

    public DomainObjectPropertyClient() {
        clientBuilder = ClientBuilder.newBuilder();
    }

    /**
     * Will automatically call {@link #init()} since all properties already supplied.
     */
    public DomainObjectPropertyClient(final String base, final String username, final String password) {
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
        ensureSet(this.base, "base");
        ensureSet(this.username, "username");
        ensureSet(this.password, "password");

        findToArchiveUri = UriBuilder.fromUri(this.base + "services/incodeMinio.DocBlobService/actions/findToArchive/invoke").build();
        archiveUri = UriBuilder.fromUri(this.base + "services/incodeMinio.DocBlobService/actions/archive/invoke").build();
    }

    private static void ensureSet(final String field, final String fieldName) {
        if(field == null) {
            throw new IllegalStateException(String.format("'%s' not set", fieldName));
        }
    }

    private void ensureInitialized() {
        if(username == null || password == null || base == null) {
            throw new IllegalStateException("Must initialize 'username', 'password' and 'base' properties");
        }
    }

    //endregion

    //region > findToArchive

    public List<DomainObjectPropertyValue> findToArchive(String caller) {

        ensureInitialized();

        Client client = null;
        try {
            client = clientBuilder.build();

            final WebTarget webTarget = client.target(findToArchiveUri).queryParam("caller", caller);

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
            final ObjectMapper mapper = new ObjectMapper();
            final DomainObjectPropertyValue[] domainObjectPropertyValues = mapper.readValue(json, DomainObjectPropertyValue[].class);
            return Arrays.asList(domainObjectPropertyValues);

        } catch(Exception ex) {
            LOG.error("uri: " + findToArchiveUri, ex);
        } finally {
            closeQuietly(client);
        }
        return null;
    }

    //endregion

    //region > archive

    public void archive(final DomainObjectPropertyValue domainObjectPropertyValue, final String externalUrl) {

        ensureInitialized();

        final ArchiveArgs archiveArgs = new ArchiveArgs();
        archiveArgs.setSourceBookmark(new StringValue(domainObjectPropertyValue.getSourceBookmark()));
        archiveArgs.setSourceProperty(new StringValue(domainObjectPropertyValue.getSourceProperty()));
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
