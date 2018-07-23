package org.incode.module.minio.docclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Setter;

public class MinioDocClient {

    private static final Logger LOG = LoggerFactory.getLogger(MinioDocClient.class);

    //region > constructor, fields
    private final ClientBuilder clientBuilder;

    public MinioDocClient() {
        clientBuilder = ClientBuilder.newBuilder();
    }

    /**
     * Will automatically call {@link #init()} since all properties already supplied.
     */
    public MinioDocClient(final String base, final String username, final String password) {
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
    private UriBuilder uriBuilder;

    /**
     * Should be called once all properties have been injected.
     */
    public void init() {
        uriBuilder = UriBuilder.fromUri(base + "services/incodeMinio.MinioDocService/actions/archive/invoke");
    }

    private void ensureInitialized() {
        if(username == null || password == null || base == null) {
            throw new IllegalStateException("Must initialize 'username', 'password' and 'base' properties");
        }
    }

    //endregion

    //region > archive

    public void archive(ArchiveMessage.Builder archiveMessageBuilder) {
        archive(archiveMessageBuilder.build());
    }

    public void archive(ArchiveMessage archiveMessage) {

        ensureInitialized();

        Client client = null;
        try {
            client = clientBuilder.build();

            final WebTarget webTarget = client.target(uriBuilder.build());

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));

            final ArchiveMessage entity = archiveMessage;
            final String json = entity.asJson();

            final Invocation invocation = invocationBuilder.buildPut(
                    Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

            final Response response = invocation.invoke();

            final int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                // if failed to log message via REST service, then fallback by logging to slf4j
                LOG.warn(archiveMessage.toString());
            }
        } catch(Exception ex) {
            LOG.error(archiveMessage.toString(), ex);
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
