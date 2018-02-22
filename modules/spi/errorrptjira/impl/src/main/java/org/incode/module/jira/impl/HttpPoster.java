package org.incode.module.jira.impl;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class HttpPoster {

    private final UriBuilder uriBuilder;
    private final String username;
    private final String password;

    private final ClientBuilder clientBuilder = ClientBuilder.newBuilder();

    protected HttpPoster(final String base, final String username, final String password) {
        this.uriBuilder = UriBuilder.fromUri(base + ErrorReportingServiceForJira.JIRA_REST_RESOURCE);
        this.username = username;
        this.password = password;
    }

    public Optional<String> post(final String json) {

        Client client = null;
        try {
            client = clientBuilder.build();

            final WebTarget webTarget = client.target(uriBuilder.build());

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));

            final Invocation invocation = invocationBuilder.buildPost(
                    Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

            final Response response = invocation.invoke();
            final Response.StatusType statusInfo = response.getStatusInfo();

            if (statusInfo.getFamily() != Response.Status.Family.SUCCESSFUL) {
                return Optional.empty();
            }

            return Optional.of(response.readEntity(String.class));

        } finally {
            closeQuietly(client);
        }
    }

    protected static void closeQuietly(final Client client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (Exception ex) {
            // ignore so as to avoid overriding any pending exceptions in calling 'finally' block.
        }
    }

    protected static String encode(final String username, final String password) {
        return java.util.Base64.getEncoder().encodeToString(asBytes(username, password));
    }

    protected static byte[] asBytes(final String username, final String password) {
        return String.format("%s:%s", username, password).getBytes();
    }


}
