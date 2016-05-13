package org.isisaddons.module.publishmq.dom.statusclient;

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

public class StatusMessageClient {

    private static final Logger LOG = LoggerFactory.getLogger(StatusMessageClient.class);

    private final ClientBuilder clientBuilder;

    private final String base;
    private final String username;
    private final String password;

    private UriBuilder uriBuilder;

    public StatusMessageClient(final String base, final String username, final String password) {
        this.base = base;
        this.username = username;
        this.password = password;
        clientBuilder = ClientBuilder.newBuilder();
        uriBuilder = UriBuilder.fromUri(base + "services/StatusMessageService/actions/log/invoke");
    }

    public void log(StatusMessage.Builder statusMessageBuilder) {
        log(statusMessageBuilder.build());
    }
    public void log(StatusMessage statusMessage) {

        Client client = clientBuilder.build();
        try {
            final WebTarget webTarget = client.target(uriBuilder.build());

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));

            final StatusMessage entity = statusMessage;
            final String json = entity.asJson();

            final Invocation invocation = invocationBuilder.buildPost(
                    Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

            final Response response = invocation.invoke();

            final int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                // if failed to log message via REST service, then fallback by logging to slf4j
                LOG.warn(statusMessage.toString());
            }
        } finally {
            closeQuietly(client);
        }
    }

    private String encode(final String username, final String password) {
        return java.util.Base64.getEncoder().encodeToString(asBytes(username, password));
    }

    private byte[] asBytes(final String username, final String password) {
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
}
