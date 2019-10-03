package org.isisaddons.module.publishmq.dom.outboxclient;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.common.collect.ImmutableMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.isisaddons.module.publishmq.dom.outboxclient.delete.DeleteMessage;
import org.incode.platform.publish.outbox.schema.InteractionsDtoUtil;
import org.incode.platform.publish.outbox.schema.ixl.v1.InteractionType;
import org.incode.platform.publish.outbox.schema.ixl.v1.InteractionsDto;

import lombok.Setter;

public class OutboxClient {

    private static final Logger LOG = LoggerFactory.getLogger(OutboxClient.class);

    private final ClientBuilder clientBuilder;

    public OutboxClient() {
        clientBuilder = ClientBuilder.newBuilder();
    }

    /**
     * Will automatically call {@link #init()} since all properties already supplied.
     */
    public OutboxClient(final String base, final String username, final String password) {
        this();

        setBase(base);
        setUsername(username);
        setPassword(password);

        init();
    }
    //endregion

    private UriBuilder pendingUriBuilder;
    private UriBuilder deleteUriBuilder;

    @Setter
    private String base;

    @Setter
    private String username;

    @Setter
    private String password;

    //region > init

    /**
     * Should be called once all properties have been injected.
     */
    public void init() {
        this.pendingUriBuilder = UriBuilder.fromUri(base + "services/isispublishmq.OutboxEventService/actions/pending/invoke");
        this.deleteUriBuilder = UriBuilder.fromUri(base + "services/isispublishmq.OutboxEventService/actions/delete/invoke");
    }

    private void ensureInitialized() {
        if(username == null || password == null || base == null) {
            throw new IllegalStateException("Must initialize 'username', 'password' and 'base' properties");
        }
    }

    //endregion

    //region > pending

    public List<InteractionDto> pending() {

        ensureInitialized();

        final URI uri = pendingUriBuilder.build();

        Client client = null;
        try {
            client = clientBuilder.build();

            final WebTarget webTarget = client.target(uri);

            final Invocation.Builder invocationBuilder = webTarget.request()
                    .header("Authorization", "Basic " + encode(username, password))
                    .accept(mediaTypeFor(InteractionsDto.class));

            final Invocation invocation = invocationBuilder.buildGet();
            final Response response = invocation.invoke();

            final int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                LOG.warn(invocation.toString());
            }

            final InteractionsDto interactionsDto = response.readEntity(InteractionsDto.class);
            final List<InteractionType> interactions = interactionsDto.getInteraction();
            return interactions.stream().map(InteractionsDtoUtil::toDto).collect(Collectors.toList());

        } catch(Exception ex) {
            LOG.error(String.format("Failed to GET from %s", uri.toString()), ex);
        } finally {
            closeQuietly(client);
        }
        return Collections.emptyList();
    }

    private static MediaType mediaTypeFor(final Class<?> dtoClass) {
        return new MediaType("application", "xml",
                ImmutableMap.of(
                        "profile", "urn:org.restfulobjects:repr-types/action-result",
                        "x-ro-domain-type", dtoClass.getName()));
    }


    //endregion

    //region > delete
    public void delete(final String transactionId, final int sequence) {

        ensureInitialized();
        final DeleteMessage entity = new DeleteMessage(transactionId, sequence);

        Client client = null;
        try {
            client = clientBuilder.build();

            final WebTarget webTarget = client.target(deleteUriBuilder.build());

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));

            final String json = entity.asJson();

            final Invocation invocation = invocationBuilder.buildPut(
                    Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

            final Response response = invocation.invoke();

            final int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                // if failed to log message via REST service, then fallback by logging to slf4j
                LOG.warn(entity.toString());
            }
        } catch(Exception ex) {
            LOG.error(entity.toString(), ex);
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
