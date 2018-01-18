package org.isisaddons.module.jaxrsclient.dom;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

public class JaxRsClientTest {

    @Before
    public void setUp() throws Exception {
        assumeThat(isInternetReachable(), is(true));
    }

    @Test
    public void invoke() {

        UriBuilder uriBuilder =
                UriBuilder.fromUri(
                        "http://jsonplaceholder.typicode.com/posts/2");
        final URI uri = uriBuilder.build();

        JaxRsClient jaxRsClient = new JaxRsClient.Default();

        final JaxRsResponse invoke = jaxRsClient.invoke(uri, SomeDto.class, null, null);

        final int status = invoke.getStatus();
        assertThat(status).isEqualTo(200);

        final String entity = invoke.readEntity(String.class);
        assertThat(entity).isNotBlank();
        assertThat(entity).isNotNull();

        System.out.println(entity);
    }

    public static class SomeDto {}

    /**
     * Tries to retrieve some content, 1 second timeout.
     */
    private static boolean isInternetReachable()
    {
        try {
            final URL url = new URL("http://www.google.com");
            final HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setConnectTimeout(1000);
            urlConnect.getContent();
            urlConnect.disconnect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}