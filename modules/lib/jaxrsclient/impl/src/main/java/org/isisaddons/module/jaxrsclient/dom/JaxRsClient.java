package org.isisaddons.module.jaxrsclient.dom;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.collect.ImmutableMap;

/**
 * Originally introduced so can be mocked out in tests.
 */
public interface JaxRsClient {

    JaxRsResponse invoke(URI uri, Class<?> dtoClass, String username, String password);

    JaxRsResponse post(URI uri, String bodyJson, String username, String password);

    class Default implements JaxRsClient {

        protected final ClientBuilder clientBuilder;

        public Default() {
            clientBuilder = ClientBuilder.newBuilder();
        }

        @Override
        public JaxRsResponse invoke(
                final URI uri,
                final Class<?> dtoClass,
                final String username,
                final String password) {

            final Client client = this.clientBuilder.build();

            try {
                final WebTarget webTarget = client.target(uri);
                configureInvocationBuilder(webTarget);

                final Invocation.Builder invocationBuilder = webTarget.request();
                invocationBuilder.accept(mediaTypeFor(dtoClass));
                addBasicAuth(username, password, invocationBuilder);

                final Invocation invocation = invocationBuilder.buildGet();

                final Response response = invocation.invoke();
                return new JaxRsResponse.Default(response);
            } finally {
                closeQuietly(client);
            }
        }

        @Override
        public JaxRsResponse post(final URI uri, final String bodyJson, final String username, final String password) {

            final Client client = this.clientBuilder.build();

            try {
                final WebTarget webTarget = client.target(uri);

                final Invocation.Builder invocationBuilder = webTarget.request();
                configureInvocationBuilder(invocationBuilder);
                addBasicAuth(username, password, invocationBuilder);

                final Entity<String> entity = Entity.entity(bodyJson, MediaType.APPLICATION_JSON_TYPE);

                final Invocation invocation = invocationBuilder.buildPost(entity);

                final Response response = invocation.invoke();
                return new JaxRsResponse.Default(response);
            } finally {
                closeQuietly(client);
            }
        }

        private Invocation.Builder addBasicAuth(
                final String username,
                final String password,
                final Invocation.Builder invocationBuilder) {
            return invocationBuilder.header("Authorization", "Basic " + encode(username, password));
        }

        /**
         * Optional hook, eg allow timeouts to be set:
         *
         * <pre>
         *  HTTPConduit conduit = WebClient.getConfig(webTarget).getHttpConduit();
         *  conduit.getClient().setConnectionTimeout(1000 * 3);
         *  conduit.getClient().setReceiveTimeout(1000 * 3);
         *  conduit.getClient().setAllowChunking(false);
         * </pre>
         */
        protected void configureInvocationBuilder(final Object invocationBuilder) {
        }

        private static MediaType mediaTypeFor(final Class<?> dtoClass) {
            return new MediaType("application", "xml",
                    ImmutableMap.of(
                            "profile", "urn:org.restfulobjects:repr-types/object",
                            "x-ro-domain-type", dtoClass.getName()));
        }


        private static String encode(final String username, final String password) {
            return org.apache.cxf.common.util.Base64Utility.encode(asBytes(username, password));
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
            } catch(Exception ex) {
                // ignore so as to avoid overriding any pending exceptions in calling 'finally' block.
            }
        }
    }
}

